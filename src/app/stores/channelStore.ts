import { defineStore } from 'pinia'
import { Channel } from '@/models/channel'

export const useChannelStore = defineStore('channelStore', {
  state: () => ({
    // channelList: [] as Channel[],
    // TODO: Remove this: <---
    channelList: [
      new Channel(
        '12342342',
        'wowzer',
        'noway',
        'dudeguy',
        true,
        true,
        false,
        1,
        '13-03-2023',
        true,
        'REAL MAN'
      ),
      new Channel(
        'false',
        'false',
        'false',
        'false',
        false,
        false,
        false,
        2,
        '22-11-2022',
        false,
        'false channel'
      )
    ]
    // --->
  }),
  actions: {
    addChannel(channel: Channel): void {
      this.channelList.push(channel)
    },
    getChannelList(): Channel[] {
      return this.channelList
    },
    removeChannel(removedChannel: Channel): void {
      this.channelList = this.channelList.filter((channel: Channel) => {
        return channel.getId() !== removedChannel.getId()
      })
    }
  }
})
