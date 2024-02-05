import { Channel } from '@/models/channel'
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
      const channelList = response.data.map((channel: any) => {
        const newChannel = new Channel()
        newChannel.setId(channel['channel_id'])
        newChannel.setChannelLink(channel['channel_link'])
        newChannel.setChannelTitle(channel['channel_title'])
        newChannel.setChannelUserAlias(channel['channel_user_alias'])
        newChannel.setCollectVideoCount(channel['collect_video_count'])
        newChannel.setCollectViewCount(channel['collect_view_count'])
        newChannel.setCollectSubscriberCount(channel['collect_subscriber_count'])

        return newChannel
      })

      return channelList
    } catch (error) {
      console.error('Error fetching channel list:', error)
    }
  }

  async removeChannel(channel: Channel): Promise<any> {
    await axios.delete(`/delete_channel/${channel.getId()}`, this.httpOptions)
    return channel
  }
}
