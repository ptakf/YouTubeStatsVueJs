<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps(['channel'])
const channel = props.channel

const emit = defineEmits(['removedChannelEvent'])

function onRemovedChannel(channel: any) {
  if (confirm('Are you sure you want to remove this channel?')) {
    emit('removedChannelEvent', channel)
  }
}

const channelPauseStatusStyle = computed(() => {
  return channel.getOnPause() ? { 'background-color': '#b7b7b7', 'border-color': '#a2a2a2' } : {}
})
</script>

<template>
  <li
    class="list-group-item d-flex justify-content-between align-items-center mb-2"
    :style="channelPauseStatusStyle"
  >
    <div>
      <RouterLink :to="'/channel/' + channel.getId()" class="text-decoration-none"
        >{{ channel.getChannelUserAlias() }}
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          fill="currentColor"
          class="bi bi-arrow-up-right-square"
          viewBox="0 0 16 16"
        >
          <path
            fill-rule="evenodd"
            d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm5.854 8.803a.5.5 0 1 1-.708-.707L9.243 6H6.475a.5.5 0 1 1 0-1h3.975a.5.5 0 0 1 .5.5v3.975a.5.5 0 1 1-1 0V6.707z"
          />
        </svg>
      </RouterLink>
    </div>
    <div class="d-flex gap-2">
      <RouterLink
        :to="'/channel/' + channel.getId() + '/edit'"
        type="button"
        class="btn btn-secondary"
      >
        Edit
      </RouterLink>

      <button type="button" class="btn btn-warning" @click="onRemovedChannel(channel)">
        Remove
      </button>
    </div>
  </li>
</template>
