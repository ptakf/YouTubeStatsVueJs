package com.project.youtubestats.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.youtubestats.dataTypeObjects.Channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class json {

  private json() {
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

  public static  String createChannelObject(Channel channel){
    String jsonString = "";
  try {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode rootNode = mapper.createObjectNode();

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

  public static String concatChannelsObjects(ArrayList<Channel> channels){
    String jsonString = "["+"\n";

    for (Channel channel : channels) {
      jsonString=jsonString+createChannelObject(channel)+","+"\n";
    }

    jsonString= jsonString.substring(0,jsonString.length()-2)+"\n"+"]";
    return  jsonString;
  }

  public static void main(String[] args) throws IOException {

  }


}


