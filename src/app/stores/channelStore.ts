import { defineStore } from 'pinia'
import { Channel } from '@/models/channel'
import { ServerApi } from '@/composables/serverApi'

const serverApi = new ServerApi()

export const useChannelStore = defineStore('channelStore', {
  state: () => ({
    channelList: [] as Channel[]
  }),
  actions: {
    async addChannel(channel: Channel): Promise<Channel> {
      try {
        return await serverApi.addChannel(channel)
      } catch (error) {
        console.error('Error adding channel:', error)
        return new Channel()
      }
    },
    async getChannelList(): Promise<Channel[]> {
      try {
        this.channelList = await serverApi.getChannelList()
        return this.channelList
      } catch (error) {
        console.error('Error fetching channel list:', error)
        return []
      }
    },
    getChannel(id: string): Channel {
      return this.channelList.find((channel: Channel) => channel.getId() === id) as Channel
    },
    async removeChannel(channel: Channel): Promise<Channel> {
      try {
        return await serverApi.removeChannel(channel)
      } catch (error) {
        console.error('Error removing channel:', error)
        return new Channel()
      }
    }
  }
})
