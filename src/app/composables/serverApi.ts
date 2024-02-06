import { Channel } from '@/models/channel'
import { Statistics } from '@/models/statistics'
import axios from 'axios'

export class ServerApi {
  httpOptions = {
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  }

  async addChannel(channel: Channel): Promise<any> {
    try {
      const channelJson: object = {
        channel_link: channel.getChannelLink(),
        channel_user_alias: channel.getChannelUserAlias(),
        collect_once_in_n_days: channel.getCollectOnceInNDays(),
        collect_video_count: channel.getCollectVideoCount(),
        collect_view_count: channel.getCollectViewCount(),
        collect_subscriber_count: channel.getCollectSubscriberCount(),
        start_collect_from: channel.getStartCollectingFrom(),
        on_pause: channel.getOnPause(),
        user_comment: channel.getUserComment()
      }

      await axios.post('/add_channel', JSON.stringify(channelJson), this.httpOptions)
      return channel
    } catch (error) {
      console.error('Error adding channel:', error)
    }
  }

  async getChannelList(): Promise<any> {
    try {
      const response = await axios.get('/channels_json', this.httpOptions)

      return response.data.map((channel: any) => {
        const newChannel = new Channel()
        newChannel.setId(channel['channel_id'])
        newChannel.setChannelLink(channel['channel_link'])
        newChannel.setChannelTitle(channel['channel_title'])
        newChannel.setChannelUserAlias(channel['channel_user_alias'])
        newChannel.setCollectVideoCount(channel['collect_video_count'])
        newChannel.setCollectViewCount(channel['collect_view_count'])
        newChannel.setCollectSubscriberCount(channel['collect_subscriber_count'])
        newChannel.setCollectOnceInNDays(channel['collect_once_in_n_days'])
        newChannel.setStartCollectingFrom(channel['start_collect_from'])
        newChannel.setOnPause(channel['on_pause'])
        newChannel.setUserComment(channel['user_comment'])

        return newChannel
      })
    } catch (error) {
      console.error('Error fetching channel list:', error)
    }
  }

  async removeChannel(channel: Channel): Promise<any> {
    try {
      await axios.delete(`/delete_channel/${channel.getId()}`, this.httpOptions)
      return channel
    } catch (error) {
      console.error('Error removing channel:', error)
    }
  }

  async getStatistics(channel: Channel): Promise<any> {
    try {
      await axios.get(`/channel_details_json/${channel.getId()}`).then((response: any) => {
        const statistics = new Statistics()

        if (response.video_count != undefined) {
          try {
            for (const videoCount of JSON.parse(response.data.video_count)) {
              Object.assign(statistics.videoCountDictionary, videoCount)
            }
          } catch (error) {
            console.error('Error loading video count:', error)
          }
        }
        if (response.view_count != undefined) {
          try {
            for (const viewCount of JSON.parse(response.data.view_count)) {
              Object.assign(statistics.viewCountDictionary, viewCount)
            }
          } catch (error) {
            console.error('Error loading view count:', error)
          }
        }
        if (response.subscriber_count != undefined) {
          try {
            for (const subscriberCount of JSON.parse(response.data.subscriber_count)) {
              Object.assign(statistics.subscriberCountDictionary, subscriberCount)
            }
          } catch (error) {
            console.error('Error loading subscriber count:', error)
          }
        }

        return statistics
      })
    } catch (error) {
      console.error('Error fetching channel statistics:', error)
    }
  }
}
