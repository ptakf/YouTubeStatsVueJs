package com.project.youtubestats.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.youtubestats.dataTypeObjects.Channel;
import com.project.youtubestats.dataTypeObjects.Observation;
import java.text.SimpleDateFormat;

import java.sql.*;
import java.util.ArrayList;

public  class MySqlRead extends MySql {

  private MySqlRead() {
    super();
  }

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

  public static Channel getChannelById(String givenChannelId){

    String sqlStatement = "SELECT * FROM channel where id='"+givenChannelId+"';";
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
        String  observationDate = "";
        Date date = rs.getDate("obs_date");
        if (date != null) {
          // Define the format you want for your String
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

          // Convert the java.sql.Date to a String
          observationDate = dateFormat.format(date);


        } else {
          observationDate = "";
        }

        int observationValue = rs.getInt("obs_value");
        Observation observation = new Observation(observationDate,observationValue);
        observations.add(observation);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return observations;
  }

  public static Date lastUpdateTime(Channel channel){
    Date lastUpdTime = new Date(1,1,1);

    String sqlStatement = "select obs_date from channel_observation_subscriber_count cosc inner join observation_subscriber_count osc on cosc.id_observation=osc.id where id_channel='" +
      channel.getChannelId() +
      "' UNION ALL select obs_date from channel_observation_video_count covc inner join observation_video_count ovc on covc.id_observation=ovc.id where id_channel='" +
      channel.getChannelId() +
      "' UNION ALL select obs_date from channel_observation_view_count covc inner join observation_view_count ovc on covc.id_observation=ovc.id where id_channel='" +
      channel.getChannelId() +
      "' order by obs_date desc limit 1;";

    try (Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");

         PreparedStatement ps = conn.prepareStatement(sqlStatement);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
       Date lastUpdate = rs.getDate("obs_date");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return lastUpdTime;
  }

  public static boolean isTimeToUpdate(Channel channel){
   boolean isTime = true;
    String sqlStatement = "select DATE_ADD(ifnull('" +
      lastUpdateTime(channel) +
      "', '0001-01-01'), INTERVAL collect_once_in_n_days DAY)<=curdate() AND start_collect_from<=curdate() as logic_time from channel where id='" +
      channel.getChannelId() +
      "'; ";

    try (Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");

         PreparedStatement ps = conn.prepareStatement(sqlStatement);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        isTime = rs.getBoolean("logic_time");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return isTime;
  }

  public static void main(String[] args) throws JsonProcessingException {

    //ArrayList<Channel> channels = new ArrayList<>();
    //channels = getAllChannelsFollowed();
    Channel channel = getChannelById("UC2e2EM3TEB8J6ctwTFSjYjA");
    System.out.println(getObservationsForChannel(channel,observationType.subscriber));


  }
}
