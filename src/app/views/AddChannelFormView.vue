<script setup lang="ts">
import { useChannelStore } from '@/stores/channelStore'
import { Channel } from '@/models/channel'
import router from '@/router'

const channelStore = useChannelStore()
var channel = new Channel()

const onSubmit = async () => {
  if (confirm('Are you sure you want to add this channel?')) {
    if (
      channelStore.channelList.filter((existingChannel: Channel) => {
        return channel.getId() === existingChannel.getId()
      }).length === 0
    ) {
      channel.setId(
        channel.getChannelLink().split('/')[channel.getChannelLink().split('/').length - 1]
      )

      channelStore.addChannel(channel).then(() => {
        router.push('/channel/list')
      })
    }
  }
}
</script>

<template>
  <h1 class="text-center mb-4">Add Channel</h1>

  <form id="addChannelForm" @submit.prevent="onSubmit">
    <div class="container p-0">
      <div class="row align-items-end">
        <div class="col-8 mb-4">
          <label for="channelLink" class="form-label">Channel URL</label>
          <input
            v-model="channel.channelLink"
            name="channelLink"
            id="channelLink"
            type="text"
            class="form-control"
            placeholder="Link to the YouTube channel"
            pattern="^https:\/\/www\.youtube\.com\/(channel\/[\w-]{24}|@\w*)$"
            required
          />
        </div>

        <div class="col-4 mb-4">
          <label for="channelUserAlias" class="form-label"
            >Channel Alias<span class="fst-italic text-gray"> &#40;optional&#41;</span>
          </label>
          <input
            v-model="channel.channelUserAlias"
            name="channelUserAlias"
            id="channelUserAlias"
            type="text"
            class="form-control"
            placeholder="In-app display name of the channel"
            pattern="[\w\W]{1,20}"
          />
        </div>
      </div>

      <div class="row align-items-end">
        <div class="col mb-4">
          <label for="collectOnceInNDays" class="form-label"
            >Data Collection Frequency
            <span class="fst-italic text-gray"> &#40;in days&#41;</span></label
          >
          <input
            v-model="channel.collectOnceInNDays"
            name="collectOnceInNDays"
            id="collectOnceInNDays"
            type="number"
            class="form-control"
            placeholder="How often to collect the data"
            pattern="^[1-9]\d*$"
            required
          />
        </div>

        <div class="col mb-4">
          <label for="startCollectingFrom">Start Collecting Data From</label>
          <input
            v-model="channel.startCollectingFrom"
            name="startCollectingFrom"
            id="startCollectingFrom"
            type="date"
            class="form-control"
            value="{{ currentDate | date: 'yyyy-MM-dd' }}"
            min="{{ currentDate | date: 'yyyy-MM-dd' }}"
            max="{{ maxDate | date: 'yyyy-MM-dd' }}"
            pattern="^(19|20)\d{2}-(0?[1-9]|1[0-2])-(0?[1-9]|[12]\d|3[01])$"
            required
          />
        </div>
      </div>

      <div class="row align-items-end">
        <div class="col mb-4">
          <div class="form-check">
            <input
              v-model="channel.collectVideoCount"
              name="collectVideoCount"
              id="collectVideoCount"
              type="checkbox"
              class="form-check-input"
              checked
            />
            <label for="collectVideoCount" class="form-check-label user-select-none"
              >Track Channel's Uploaded Video Count</label
            >
          </div>
        </div>

        <div class="col mb-4">
          <div class="form-check">
            <input
              v-model="channel.collectViewCount"
              name="collectViewCount"
              id="collectViewCount"
              type="checkbox"
              class="form-check-input"
              checked
            />
            <label for="collectViewCount" class="form-check-label user-select-none"
              >Track Channel's Views</label
            >
          </div>
        </div>

        <div class="col mb-4">
          <div class="form-check">
            <input
              v-model="channel.collectSubscriberCount"
              name="collectSubscriberCount"
              id="collectSubscriberCount"
              type="checkbox"
              class="form-check-input"
              checked
            />
            <label for="collectSubscriberCount" class="form-check-label user-select-none"
              >Track Channel's Subscriber Count</label
            >
          </div>
        </div>
      </div>

      <div class="mb-4">
        <label for="userComment" class="form-label"
          >User Comment<span class="fst-italic text-gray"> &#40;optional&#41;</span>
        </label>
        <textarea
          v-model="channel.userComment"
          name="userComment"
          id="userComment"
          class="form-control"
          maxlength="200"
          placeholder="Free text area (e.g. for notes related to the channel)"
        ></textarea>
      </div>

      <div class="mb-4">
        <div class="form-check">
          <input
            v-model="channel.onPause"
            name="onPause"
            id="onPause"
            type="checkbox"
            class="form-check-input"
          />
          <label for="onPause" class="form-check-label user-select-none"
            >Add Channel as Paused</label
          >
        </div>
      </div>

      <button type="submit" class="btn btn-primary">Submit</button>
    </div>
  </form>
</template>
