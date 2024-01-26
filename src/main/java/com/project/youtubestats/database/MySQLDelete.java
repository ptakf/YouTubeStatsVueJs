package com.project.youtubestats.database;

import com.project.youtubestats.dataTypeObjects.Channel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDelete extends  MySql{

  private MySQLDelete() {

  }

    public static void deleteChannel(Channel channel){
      try(Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");
          Statement stmt = conn.createStatement();
      ) {
        String sql = "DELETE FROM channel WHERE id='" +
          channel.getChannelId() +
          "'" +
          ";";

        stmt.executeUpdate(sql);

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }


  }



