package com.project.youtubestats.controller;

import com.project.youtubestats.dataActualization.DataCollection;
import com.project.youtubestats.dataTypeObjects.Channel;
import com.project.youtubestats.database.MySQLDelete;
import com.project.youtubestats.database.MySqlCreate;
import com.project.youtubestats.database.MySqlRead;
import com.project.youtubestats.database.MySqlUpdate;
import com.project.youtubestats.json.Json;
import org.springframework.stereotype.Controller;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


@RestController
public class MainController {

 public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/urlNotFound")
      .setViewName("forward:/index.html");
  }

  @Bean
  public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
    return container -> {
      container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,
        "/urlNotFound"));
    };
  }

  /*  @RequestMapping("/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }  */

  // List of all channels
    @GetMapping("/channels_json")
    public String sentChannels() {
        String jsonResponse = Json.concatChannelsJsons(MySqlRead.getAllChannelsFollowed());
        return jsonResponse;
    }
  //particular information about one channel with stats
  @GetMapping("/channel_details_json/{id}")
  public String sentChannelDetail(@PathVariable("id") String id) {
    String jsonResponse = Json.createChannelWithMetricsJson(MySqlRead.getChannelById(id));
    return jsonResponse;
  }

  //getting json with information about channel, returning nothing
  // if contains channel's url is already at database, then updates record in db
  @PostMapping("/add_channel")
  public void addChannel(@RequestBody String jsonChannel) throws IOException {
    // Process the inputString as needed
    Channel channel = Json.parseChannelInfo(jsonChannel);
    String channelLink = channel.getChannelLink();
    ArrayList<String> allLinks = MySqlRead.getAllChannelLinks();
    if (!allLinks.contains(channelLink)) {
      MySqlCreate.AddChannel(channel);

      if (DataCollection.isEligibleForDataCollection(channel)) {
        DataCollection.addObservationsForChannel(channel);
      }
    } else {
      MySqlUpdate.updateChannel(channel);
    }
  }

  @DeleteMapping("/delete_channel/{id}")
  public void deleteChannel (@PathVariable String id){

    try {
      Channel channel = MySqlRead.getChannelById(id);
      MySQLDelete.deleteChannel(channel);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

}


