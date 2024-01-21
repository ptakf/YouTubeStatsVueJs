package com.project.youtubestats.youTubeAPI;


import com.project.youtubestats.json.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public final class youTubeApi {

  public static String apiKey = "AIzaSyBUesDI2uMhxNgy-d6b5d0sSPUuxlcxGpA";

  private youTubeApi() {
  }

  private static String getChannelAlias(String linkToChannel) {

    int aliasAtIndex = linkToChannel.indexOf("@");

    if (aliasAtIndex != -1) {
      return linkToChannel.substring(aliasAtIndex + 1);
    } else {
      return "";
    }
  }

  private static String getJsonFromUrl(String UrlToCall) throws IOException {
    String jsonString = "";
    URL url = new URL(UrlToCall);

    try {
      // Open a connection to the URL
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      // Set the request method to GET
      connection.setRequestMethod("GET");

      // Get the response code
      int responseCode = connection.getResponseCode();

      // Check if the request was successful (status code 200)
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Create a BufferedReader to read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        // Read the response line by line
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }

        // Close the BufferedReader
        in.close();

        jsonString = response.toString();


        return jsonString;
      } else {
        // If the request was not successful, print the error code
        System.out.println("GET request failed. Response Code: " + responseCode);
      }

      // Close the connection
      connection.disconnect();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return jsonString;
  }

  public static String getChannelId(String linkToChannel) throws IOException {

    String channelId = "";
    String channelAlias = getChannelAlias(linkToChannel);
    String channelSearchURL = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&" +
      "q=https%3A%2F%2Fwww.youtube.com%2F%40" + channelAlias + "&type=channel&key=" + apiKey;
  try {
    String jsonString = getJsonFromUrl(channelSearchURL);
    channelId = json.parseChannelId(jsonString);
  } catch (Exception e){
    e.printStackTrace();
  }

    return channelId;
  }

  public static String getChannelTitle(String channelId) throws IOException {
    String channelTitle = "";

    String channelSearchURL = "https://youtube.googleapis.com/youtube/v3/channels?part=statistics&part=snippet&" +
      "id=" + channelId +
      "&key=" + apiKey;

    try {
      String jsonString = getJsonFromUrl(channelSearchURL);
      channelTitle = json.parseChannelTitle(jsonString);
    } catch (Exception e){
      e.printStackTrace();
    }

    return channelTitle;
  }

  public static String getChannelViewCount(String channelId) throws IOException {
    String channelViewCount = "";

    String channelSearchURL = "https://youtube.googleapis.com/youtube/v3/channels?part=statistics&part=snippet&" +
      "id=" + channelId +
      "&key=" + apiKey;

    try {
      String jsonString = getJsonFromUrl(channelSearchURL);
      channelViewCount = json.parseChannelViewCount(jsonString);
    } catch (Exception e){
      e.printStackTrace();
    }

    return channelViewCount;
  }

  public static String getChannelSubscriberCount(String channelId) throws IOException {
    String channelSubscriberCount = "";

    String channelSearchURL = "https://youtube.googleapis.com/youtube/v3/channels?part=statistics&part=snippet&" +
      "id=" + channelId +
      "&key=" + apiKey;

    try {
      String jsonString = getJsonFromUrl(channelSearchURL);
      channelSubscriberCount = json.parseChannelSubscriberCount(jsonString);
    } catch (Exception e){
      e.printStackTrace();
    }

    return channelSubscriberCount;
  }

  public static String getChannelVideoCount(String channelId) throws IOException {
    String channelVideoCount = "";

    String channelSearchURL = "https://youtube.googleapis.com/youtube/v3/channels?part=statistics&part=snippet&" +
      "id=" + channelId +
      "&key=" + apiKey;

    try {
      String jsonString = getJsonFromUrl(channelSearchURL);
      channelVideoCount = json.parseChannelVideoCount(jsonString);
    } catch (Exception e){
      e.printStackTrace();
    }

    return channelVideoCount;
  }



  public static void main(String[] args) throws IOException {
    String channelId = getChannelId("https://www.youtube.com/@Queen");
    System.out.println(channelId);
    System.out.println(getChannelVideoCount(channelId));
    System.out.println(getChannelTitle(channelId));
    System.out.println(getChannelViewCount(channelId));
    System.out.println(getChannelSubscriberCount(channelId));

    // UCGLsHbMw8WCOB6tu0sKyG9w
  }
}
