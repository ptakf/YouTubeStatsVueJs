package com.project.youtubestats.dataTypeObjects;

import lombok.*;
import com.project.youtubestats.youTubeAPI.*;

import java.io.IOException;

@Setter
@Getter
public class Channel {


  private String channelId;
  private String channelLink;
  private String  channelUserAlias;
  private String  channelTitle;
  private int collectOnceInNDays;
  private boolean collectVideoCount;
  private boolean collectViewCount;
  private boolean collectSubscriberCount;
  private String startCollectFrom;
  private boolean onPause;
  private String userComment;

  public Channel(String channelLink, String channelUserAlias, int collectOnceInNDays, boolean collectVideoCount, boolean collectViewCount, boolean collectSubscriberCount, String startCollectFrom, boolean onPause, String userComment) throws IOException {
    this.channelLink = channelLink;
    this.channelUserAlias = channelUserAlias;
    this.collectOnceInNDays = collectOnceInNDays;
    this.collectVideoCount = collectVideoCount;
    this.collectViewCount = collectViewCount;
    this.collectSubscriberCount = collectSubscriberCount;
    this.startCollectFrom = startCollectFrom;
    this.onPause = onPause;
    this.userComment = userComment;

    this.channelId = youTubeApi.getChannelId(channelLink);
    this.channelTitle = youTubeApi.getChannelTitle(channelId);
  }

  public Channel(String channelId, String channelLink, String channelUserAlias, String channelTitle, int collectOnceInNDays, boolean collectVideoCount, boolean collectViewCount, boolean collectSubscriberCount, String startCollectFrom, boolean onPause, String userComment) {
    this.channelId = channelId;
    this.channelLink = channelLink;
    this.channelUserAlias = channelUserAlias;
    this.channelTitle = channelTitle;
    this.collectOnceInNDays = collectOnceInNDays;
    this.collectVideoCount = collectVideoCount;
    this.collectViewCount = collectViewCount;
    this.collectSubscriberCount = collectSubscriberCount;
    this.startCollectFrom = startCollectFrom;
    this.onPause = onPause;
    this.userComment = userComment;
  }


  public String getSubscriberCount() {
    String subscriberCount = "";
      if(collectSubscriberCount){
        SubscriberCountMetrics metrics = new SubscriberCountMetrics();
        subscriberCount= metrics.getObservations();
      }
      return subscriberCount;
  }

  @Override
  public String toString() {
    return "Channel{" +
      "channelId='" + channelId + '\'' +
      ", channelLink='" + channelLink + '\'' +
      ", channelUserAlias='" + channelUserAlias + '\'' +
      ", channelTitle='" + channelTitle + '\'' +
      ", collectOnceInNDays=" + collectOnceInNDays +
      ", collectVideoCount=" + collectVideoCount +
      ", collectViewCount=" + collectViewCount +
      ", collectSubscriberCount=" + collectSubscriberCount +
      ", startCollectFrom='" + startCollectFrom + '\'' +
      ", onPause=" + onPause +
      ", userComment='" + userComment + '\'' +
      '}';
  }

/*

  "channel_title": "string",
  "video_count": [ {
    "date_in_string": 123,

  }
    ],
  "view_count": [{
    "date_in_string": 123,

  }
    ],
  "subscriber_count": [{
    "date_in_string": 123,

  }
*/

}
