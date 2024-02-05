import axios from 'axios'

export class ServerApi {
  httpOptions = {
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  }

  // async addChannel(channel: Channel): void {
  //   this.channelList.push(channel)
  // }

  async getChannelList(): Promise<any> {
    try {
      return await axios.get('/channels_json', this.httpOptions)
    } catch (error) {
      console.error('Error fetching channel list:', error)
    }
  }

  // function removeChannel(removedChannel: Channel): void {
  //   this.channelList = this.channelList.filter((channel: Channel) => {
  //     return channel.getId() !== removedChannel.getId()
  //   })
  // }
}

