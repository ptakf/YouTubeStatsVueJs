package com.project.youtubestats.database;

import com.project.youtubestats.dataTypeObjects.Channel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.youtubestats.dataTypeObjects.Channel;
import com.project.youtubestats.dataTypeObjects.Observation;

import java.io.IOException;
import java.text.SimpleDateFormat;

import java.sql.*;
import java.util.ArrayList;

public class MySqlUpdate extends MySql {

  private MySqlUpdate() {
  }

  public static void updateChannel(Channel channel){
    try(Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");
        Statement stmt = conn.createStatement();
    ) {
      String sql = "UPDATE channel" +
        "  SET channel_user_alias ='" + channel.getChannelUserAlias()+
        "', channel_title='" +channel.getChannelTitle() +
        "',collect_once_in_n_days=" +
        channel.getCollectOnceInNDays() +
        ",collect_video_count=" +
        channel.isCollectVideoCount() +
        "," +
        "    collect_view_count=" +
        channel.isCollectViewCount()+
        ",collect_subscriber_count=" +
        channel.isCollectSubscriberCount() +
        ", start_collect_from='" + channel.getStartCollectFrom()+
        "', on_pause=" +
        channel.isOnPause() +
        ", user_comment='" + channel.getUserComment()+
        "'" +
        "  WHERE channel_link='" +
        channel.getChannelLink() +
        "';";


      stmt.executeUpdate(sql);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    Channel channel = new Channel("https://www.youtube.com/@kaiserbauch9092","Change!",2,false,
      true,false,"2008-10-29",false,"sheep");
    updateChannel(channel);
  }

}
