<script setup lang="ts">
import { onBeforeMount } from 'vue'
import { Channel } from '../models/channel'
import ChannelListElement from '@/components/ChannelListElement.vue'
import { useChannelStore } from '@/stores/channelStore'

const channelStore = useChannelStore()
var channelList: Channel[] = []

onBeforeMount(() => {
  loadChannelList()
})

function onRemovedChannel(channel: any) {
  channelStore.removeChannel(channel)

  loadChannelList()
}

function loadChannelList() {
  channelList = channelStore.getChannelList()
}
</script>

<template>
  <h1 class="text-center mb-4">Channel List</h1>

  <ul class="list-group">
    <ChannelListElement
      v-for="channel of channelList"
      :key="channel.getId()"
      :channel="channel"
      @removedChannelEvent="onRemovedChannel(channel)"
    ></ChannelListElement>
  </ul>
</template>
