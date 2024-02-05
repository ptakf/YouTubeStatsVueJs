import { defineStore } from 'pinia'
import { Channel } from '@/models/channel'
import { ServerApi } from '@/composables/serverApi'

const serverApi = new ServerApi()

export const useChannelStore = defineStore('channelStore', {
  state: () => ({
    channelList: [] as Channel[]
  }),
  actions: {
    async getChannelList(): Promise<Channel[]> {
      try {
        const response = await serverApi.getChannelList()
        this.channelList = response.data.map((channel: any) => {
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
        return this.channelList
      } catch (error) {
        console.error('Error fetching channel list:', error)
        return []
      }
    }
  }
})
