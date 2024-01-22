package com.project.youtubestats.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.youtubestats.dataTypeObjects.Channel;
import com.project.youtubestats.dataTypeObjects.Observation;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.project.youtubestats.json.json.*;

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

        channels.add(createChannelFromSqlOutputLine(rs));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return channels;
  }

  public static Channel getChannelByLink(String givenChannelLink){

    String sqlStatement = "SELECT * FROM channel where channel_link='"+givenChannelLink+"';";
    try (Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");

         PreparedStatement ps = conn.prepareStatement(sqlStatement);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {

        Channel channel = createChannelFromSqlOutputLine(rs);;
        return channel;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new Channel();
  }

  private static Channel createChannelFromSqlOutputLine(ResultSet rs) throws SQLException {
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
   return channel;
  }

  public static ArrayList<Observation> getObservationsForChannel(Channel channel, observationType observationType){
    ArrayList<Observation> observations= new ArrayList<>();
    String channelId = channel.getChannelId();

    String sqlStatement = "select * from channel_" +
      observationType +
      " cot inner join " +
      observationType +
      " ot on cot.id_observation=ot.id" +
      " where id_channel='"+channelId+"';";
    try (Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");

         PreparedStatement ps = conn.prepareStatement(sqlStatement);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        String observationDate = rs.getString("obs_date");
        int observationValue = rs.getInt("obs_value");
        Observation observation = new Observation(observationDate,observationValue);
        observations.add(observation);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return observations;
  }


  public static void main(String[] args) throws JsonProcessingException {

    //ArrayList<Channel> channels = new ArrayList<>();
    //channels = getAllChannelsFollowed();
    Channel channel = getChannelByLink("https://www.youtube.com/@kaiserbauch9092");
    System.out.println(createChannelWithMetricsJson(channel));


  }
}
