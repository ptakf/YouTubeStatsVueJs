package com.project.youtubestats.database;


import java.sql.*;

public class TestSQL
{

  public static void main(String[] args) {
    String sqlSelectAllPersons = "SELECT * FROM test";

    String connectionUrl = "jdbc:mysql://youtubestats.c5aam8uoypor.eu-north-1.rds.amazonaws.com:3306/youtubestats?serverTimezone=CET";

  /*  try (Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");

         PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
       long id = rs.getLong("ID");
        String name = rs.getString("FIRST_NAME");
        String lastName = rs.getString("LAST_NAME");

        // do something with the extracted data...
        System.out.print(name);
        System.out.print(lastName);
        System.out.println(id);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } */

    try(Connection conn = DriverManager.getConnection(connectionUrl, "admin", "youtubestats");
        Statement stmt = conn.createStatement();
    ) {
      // Execute a query
      System.out.println("Inserting records into the table...");
      String name = "vasia";
      String sql = "INSERT INTO channel(id,collect_video_count) VALUES ('g4d3', true)";
      stmt.executeUpdate(sql);
      System.out.println("Inserted records into the table...");
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }
}
