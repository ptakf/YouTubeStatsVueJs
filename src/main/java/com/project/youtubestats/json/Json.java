package com.project.youtubestats.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.youtubestats.dataTypeObjects.Channel;
import com.project.youtubestats.dataTypeObjects.Observation;
import com.project.youtubestats.database.MySql;
import com.project.youtubestats.database.MySqlRead;
import com.project.youtubestats.youTubeAPI.youTubeApi;

import java.io.IOException;
import java.util.ArrayList;

public final class Json {

  private Json() {
  }

  public static String parseChannelId(String jsonString){
    String channelId = "";

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Extract channelId from the JSON
       channelId = jsonNode
        .path("items")
        .path(0)
        .path("id")
        .path("channelId")
        .asText();


    } catch (Exception e) {
      e.printStackTrace();
    }

    return channelId;
  }

  public static String parseChannelTitle(String jsonString){
    String channelTitle = "";

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Extract channelId from the JSON
      channelTitle = jsonNode
        .path("items")
        .path(0)
        .path("snippet")
        .path("title")
        .asText();


    } catch (Exception e) {
      e.printStackTrace();
    }

    return channelTitle;
  }

  public static String parseChannelViewCount(String jsonString){
    String channelViewCount = "";

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Extract channelId from the JSON
      channelViewCount = jsonNode.path("items")
        .path(0)
        .path("statistics")
        .path("viewCount")
        .asText();


    } catch (Exception e) {
      e.printStackTrace();
    }

    return channelViewCount;
  }

  public static String parseChannelSubscriberCount(String jsonString){
    String channelSubscriberCount = "";

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Extract channelId from the JSON
      channelSubscriberCount = jsonNode
        .path("items")
        .path(0)
        .path("statistics")
        .path("subscriberCount")
        .asText();



    } catch (Exception e) {
      e.printStackTrace();
    }

    return channelSubscriberCount;
  }

  public static String parseChannelVideoCount(String jsonString){
    String channelVideoCount = "";

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Extract channelId from the JSON
      channelVideoCount = jsonNode
        .path("items")
        .path(0)
        .path("statistics")
        .path("videoCount")
        .asText();


    } catch (Exception e) {
      e.printStackTrace();
    }

    return channelVideoCount;
  }

  public static Channel parseChannelInfo(String jsonString){

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Now you can access the parsed data using JsonNode.path
      String channelLink = jsonNode.path("channel_link").asText();
      String channelUserAlias = jsonNode.path("channel_user_alias").asText();
      int collectOnceInNDays = jsonNode.path("collect_once_in_n_days").asInt();
      boolean collectVideoCount = jsonNode.path("collect_video_count").asBoolean();
      boolean collectViewCount = jsonNode.path("collect_view_count").asBoolean();
      boolean collectSubscriberCount = jsonNode.path("collect_subscriber_count").asBoolean();
      String startCollectFrom = jsonNode.path("start_collect_from").asText();
      boolean onPause = jsonNode.path("on_pause").asBoolean();
      String userComment = jsonNode.path("user_comment").asText();

     String channelId = youTubeApi.getChannelId(channelLink);
     String channelTitle = youTubeApi.getChannelTitle(channelId);

     Channel channel = new Channel(channelId,channelLink,channelUserAlias,channelTitle,collectOnceInNDays,collectVideoCount
     ,collectViewCount,collectSubscriberCount,startCollectFrom,onPause,userComment);

     return channel;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new Channel();
  }

  public static  String createChannelJson(Channel channel){
    String jsonString = "";
  try {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode rootNode = mapper.createObjectNode();

    rootNode.put("channel_id", channel.getChannelId());
    rootNode.put("channel_link", channel.getChannelLink());
    rootNode.put("channel_user_alias", channel.getChannelUserAlias());
    rootNode.put("collect_once_in_n_days", channel.getCollectOnceInNDays());
    rootNode.put("collect_video_count", channel.isCollectVideoCount());
    rootNode.put("collect_view_count", channel.isCollectViewCount());
    rootNode.put("collect_subscriber_count", channel.isCollectSubscriberCount());
    rootNode.put("start_collect_from", channel.getStartCollectFrom());
    rootNode.put("on_pause", channel.isOnPause());
    rootNode.put("user_comment", channel.getUserComment());

    jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
  } catch (JsonProcessingException e) {
    throw new RuntimeException(e);
  }

    return  jsonString;
  }

  public static String concatChannelsJsons(ArrayList<Channel> channels){
    String jsonString = "["+"\n";

    if(!channels.isEmpty()){
      for (Channel channel : channels) {
        jsonString=jsonString+ createChannelJson(channel)+","+"\n";
      }

      jsonString= jsonString.substring(0,jsonString.length() - 2)+"\n";
    }

    jsonString= jsonString +"]";

    return  jsonString;
  }


  public static String createObservationJson(ArrayList<Observation> observations) throws JsonProcessingException {
    String jsonString = "";
    if (!observations.isEmpty()) {
      ObjectMapper mapper = new ObjectMapper();
      ArrayNode arrayNode = mapper.createArrayNode();

      for (Observation observation : observations) {
        ObjectNode observationNode = mapper.createObjectNode();
        observationNode.put(observation.getDateOfObservation(), observation.getValueOfObservation());
        arrayNode.add(observationNode);
      }

      jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
    }

    return jsonString;
  }



  public static  String createChannelWithMetricsJson(Channel channel){
    String jsonString = "";
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode rootNode = mapper.createObjectNode();

      rootNode.put("channel_id", channel.getChannelId());
      rootNode.put("channel_link", channel.getChannelLink());
      rootNode.put("channel_user_alias", channel.getChannelUserAlias());
      if(channel.isCollectVideoCount()){
        rootNode.put("video_count",  createObservationJson(MySqlRead.getObservationsForChannel(channel, MySql.observationType.video)) );
      }
      if(channel.isCollectViewCount()){
        rootNode.put("view_count",createObservationJson(MySqlRead.getObservationsForChannel(channel, MySql.observationType.view)) );
      }
      if(channel.isCollectSubscriberCount()){
        rootNode.put("subscriber_count", createObservationJson(MySqlRead.getObservationsForChannel(channel, MySql.observationType.subscriber)) );
      }
      rootNode.put("on_pause", channel.isOnPause());
      rootNode.put("user_comment", channel.getUserComment());

      jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return  jsonString;
  }

}


