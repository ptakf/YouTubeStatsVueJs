package com.project.youtubestats.database;
import com.project.youtubestats.dataTypeObjects.Channel;

import java.io.IOException;
import java.sql.*;
public final class MySqlCreate extends  MySql {

  public static void AddChannel(Channel channel){
    try(Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");
        Statement stmt = conn.createStatement();
    ) {
      // Execute a query
      System.out.println("Inserting records into the table...");


      String sql = "INSERT INTO channel VALUES ('" + channel.getChannelId() +
        "','" + channel.getChannelLink() +
        "','" + channel.getChannelUserAlias() +
        "','" + channel.getChannelTitle() +
        "'," + channel.getCollectOnceInNDays() +
        "," + channel.isCollectVideoCount() +
        "," + channel.isCollectViewCount() +
        "," + channel.isCollectSubscriberCount() +
        ",'" + channel.getStartCollectFrom() +
        "'," + channel.isOnPause() +
        ",'" + channel.getUserComment() +
        "')";

      stmt.executeUpdate(sql);
      System.out.println("Inserted records into the table...");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void AddObservation(Channel channel, observationType observationType, int observedValue ){
    try(Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");
        Statement stmt = conn.createStatement();
    ) {
      // Execute a query
      System.out.println("Inserting records into the table...");


      String sql = "INSERT INTO " + observationType+"(obs_date,obs_value) VALUES (curdate(),"+observedValue+")";

      stmt.executeUpdate(sql);

      int lastObservationId = MySqlRead.getLastAddedObservationId(observationType);

      sql = "INSERT INTO channel_" + observationType+" VALUES ('"+ channel.getChannelId()+"',"+lastObservationId+")";

      stmt.executeUpdate(sql);

      System.out.println("Inserted records into the table...");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    Channel channel = new Channel("https://www.youtube.com/@kaiserbauch9092","BestChannel",3,true,
      true,false,"2008-10-29",false,"allah akbar");
    AddObservation(channel,observationType.subscriber,46);
  }
}
