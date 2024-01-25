package com.project.youtubestats.dataActualization;

import com.project.youtubestats.dataTypeObjects.Channel;
import com.project.youtubestats.database.MySql;
import com.project.youtubestats.database.MySqlCreate;
import com.project.youtubestats.database.MySqlRead;
import com.project.youtubestats.youTubeAPI.youTubeApi;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class DataCollection {

  public static boolean requireDataCollection(Channel channel){
    if(!channel.isCollectVideoCount() && !channel.isCollectSubscriberCount() && !channel.isCollectViewCount()){
      return false;
    }
    if(channel.isOnPause()){
      return false;
    }
    return true;
  }

  public static boolean isEligibleForDataCollection(Channel channel){
    boolean isEligible = requireDataCollection(channel);
      if(!MySqlRead.isTimeToUpdate(channel)){
        isEligible=false;
      }
    return  isEligible;
  }

  public static void addObservationsForChannel(Channel channel) throws IOException {
    if(channel.isCollectSubscriberCount()){
      int subscriberCountObserved = Integer.parseInt(youTubeApi.getChannelSubscriberCount(channel.getChannelId()));
      MySqlCreate.AddObservation(channel, MySql.observationType.subscriber,subscriberCountObserved);
    }
    if(channel.isCollectVideoCount()){
      int videoCountObserved = Integer.parseInt(youTubeApi.getChannelVideoCount(channel.getChannelId()));
      MySqlCreate.AddObservation(channel, MySql.observationType.video,videoCountObserved);
    }
    if(channel.isCollectViewCount()){
      int viewCountObserved = Integer.parseInt(youTubeApi.getChannelViewCount(channel.getChannelId()));
      MySqlCreate.AddObservation(channel, MySql.observationType.view,viewCountObserved);
    }
  }

  @Scheduled(fixedRate = 1000*60*60*24) // 10000 for testing
  public void scheduledDataCollection() throws IOException {
    // Your task logic goes here
    System.out.println("Starting data collection...");

    ArrayList<Channel> channels = MySqlRead.getAllChannelsFollowed();

    for(Channel channel: channels){
        if(isEligibleForDataCollection(channel)){
          addObservationsForChannel(channel);
        }
    }

    System.out.println("Data is collected...");

  }
}
