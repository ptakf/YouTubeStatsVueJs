package com.project.youtubestats.database;

import com.project.youtubestats.dataTypeObjects.Channel;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlRead extends MySql {

  public static int getLastAddedObservationId(observationType observationType){
    int result = 0;
    String sqlStatement = "SELECT * FROM "+observationType+" ORDER BY ID DESC LIMIT 1";
    try (Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");

         PreparedStatement ps = conn.prepareStatement(sqlStatement);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        result = rs.getInt("id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static ArrayList<Channel> getAllChannelsFollowed(){
    ArrayList<Channel> channels = new ArrayList<>();
    String sqlStatement = "SELECT * FROM channel";
    try (Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");

         PreparedStatement ps = conn.prepareStatement(sqlStatement);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
       String id = rs.getString("id");
       String channelLink = rs.getString("channel_link");
       String channelUserAlias = rs.getString("channel_user_alias");
       String channelTitle = rs.getString("channel_title");
       int collectOnceInNDays = rs.getInt("collect_once_in_n_days");
       boolean collectVideoCount = rs.getBoolean("collect_video_count");
       boolean collectViewCount = rs.getBoolean("collect_view_count");
       boolean collectSubscriberCount = rs.getBoolean("collect_subscriber_count");
       String startCollectFrom = rs.getString("start_collect_from");
       boolean onPause = rs.getBoolean("on_pause");
       String userComment = rs.getString("user_comment");

       Channel channel = new Channel(id,channelLink,channelUserAlias,channelTitle,collectOnceInNDays,collectVideoCount,collectViewCount,collectSubscriberCount,startCollectFrom,onPause,userComment);
       channels.add(channel);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return channels;
  }

 /* public static Channel getChannel() throws IOException {

    return
  } */


  public static void main(String[] args) {
    System.out.println(getLastAddedObservationId(observationType.subscriber));
  }
}
