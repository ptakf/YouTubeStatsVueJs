package com.project.youtubestats.controller;

import com.project.youtubestats.database.MySqlRead;
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
import org.springframework.web.bind.annotation.*;
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
  @PostMapping("/add_channel")
  public void addChannel(@RequestBody String jsonChannel) {
    // Process the inputString as needed
    String resultString = "Processed: " + jsonChannel;

    // Send the resultString as the response
    //return resultString;
  }

}


