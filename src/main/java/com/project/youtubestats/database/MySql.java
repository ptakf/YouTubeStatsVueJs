package com.project.youtubestats.database;

  class MySql {
   protected static String  connectionUrl = "jdbc:mysql://youtubestats.c5aam8uoypor.eu-north-1.rds.amazonaws.com:3306/youtubestats?serverTimezone=CET";

   public enum observationType{
     video("observation_video_count"),
     view("observation_view_count"),
     subscriber("observation_subscriber_count");
     private final String text;

     /**
      * @param text
      */
     observationType(final String text) {
       this.text = text;
     }

     @Override
     public String toString() {
       return text;
     }
   }
}
