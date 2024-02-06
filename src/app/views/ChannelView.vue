<script setup lang="ts">
import { Channel } from '@/models/channel'
import { Statistics } from '@/models/statistics'
import router from '@/router'
import { useChannelStore } from '@/stores/channelStore'
import { onBeforeMount, ref } from 'vue'
import ChartComponent from '@/components/ChartComponent.vue'

const channelStore = useChannelStore()
const props = defineProps({ id: { type: String, required: true } })
const channel = new Channel()
const statistics = new Statistics()
var displayedChart = ref('videoCount')
var areStatisticsLoaded = ref(false)

onBeforeMount(() => {
  Object.assign(channel, channelStore.getChannel(props.id))
  channelStore.getStatistics(channel).then((response) => {
    Object.assign(statistics, response)
    areStatisticsLoaded.value = true
  })
})

function onRemovedChannel(channel: any) {
  if (confirm('Are you sure you want to remove this channel?')) {
    channelStore.removeChannel(channel).then(() => {
      router.push('/channel/list')
    })
  }
}

function changeDisplayedChart(chart: string): void {
  displayedChart.value = chart
}
</script>

<template>
  <div class="d-flex justify-content-between align-items-center mb-2 border-bottom">
    <h1 class="text-center">
      {{ channel.getChannelUserAlias() }}
      <span v-if="channel.getChannelTitle() != undefined && channel.getChannelTitle() != ''"
        >&#40;{{ channel.getChannelTitle() }}&#41;</span
      >
    </h1>

    <div class="d-flex gap-2">
      <RouterLink
        type="button"
        class="btn btn-secondary"
        :to="'/channel/' + channel.getId() + '/edit'"
      >
        Edit
      </RouterLink>

      <button type="button" class="btn btn-warning" @click="onRemovedChannel(channel)">
        Remove
      </button>
    </div>
  </div>

  <div v-if="channel.getUserComment() !== ''" class="mb-4">
    <span class="fst-italic">&quot;{{ channel.getUserComment() }}&quot;</span>
  </div>

  <h4>
    Channel tracking
    <template v-if="channel.getOnPause()">
      <span>paused. No data displayed.</span>
    </template>
    <template v-else>
      <span>
        resumed. Tracking:
        <div class="d-block mt-3 btn-group" role="group">
          <template v-if="channel.getCollectVideoCount()">
            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="buttonVideoCount"
              @click="changeDisplayedChart('videoCount')"
              checked
            />
            <label for="buttonVideoCount" class="btn btn-outline-primary"
              >Uploaded Video Count</label
            >
          </template>

          <template v-if="channel.getCollectViewCount()">
            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="buttonViewCount"
              @click="changeDisplayedChart('viewCount')"
            />
            <label for="buttonViewCount" class="btn btn-outline-primary">Channel's Views</label>
          </template>

          <template v-if="channel.getCollectSubscriberCount()">
            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="buttonSubscriberCount"
              @click="changeDisplayedChart('subscriberCount')"
            />
            <label for="buttonSubscriberCount" class="btn btn-outline-primary"
              >Subscriber Count</label
            >
          </template>
        </div>
      </span>
    </template>
  </h4>

  <template v-if="!channel.getOnPause()">
    <div class="d-flex justify-content-center align-items-center mt-5">
      <template v-if="areStatisticsLoaded">
        <template v-if="displayedChart === 'videoCount'">
          <ChartComponent
            :chartStatistics="statistics.getVideoCountDictionary()"
            class="flex-grow-1"
          ></ChartComponent>
        </template>
        <template v-else-if="displayedChart === 'viewCount'">
          <ChartComponent
            :chartStatistics="statistics.getViewCountDictionary()"
            class="flex-grow-1"
          ></ChartComponent>
        </template>
        <template v-else-if="displayedChart === 'subscriberCount'">
          <ChartComponent
            :chartStatistics="statistics.getSubscriberCountDictionary()"
            class="flex-grow-1"
          ></ChartComponent>
        </template>
      </template>
      <template v-else>
        <ChartComponent :chartStatistics="{}" class="flex-grow-1"></ChartComponent>
      </template>
    </div>
  </template>
</template>
