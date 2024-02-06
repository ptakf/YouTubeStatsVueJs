<script setup lang="ts">
import { onBeforeMount } from 'vue'
import { Line } from 'vue-chartjs'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip
} from 'chart.js'

const props = defineProps(['chartStatistics'])

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip)

const chartOptions = {
  responsive: true
}

const chartData = {
  labels: [],
  datasets: []
}

onBeforeMount(() => {
  Object.assign(chartData, {
    labels: Object.keys(props.chartStatistics),
    datasets: [
      {
        data: Object.values(props.chartStatistics),
        label: 'Amount',
        fill: false,
        tension: 0.1,
        borderColor: '#600000',
        backgroundColor: '#600000'
      }
    ]
  })
})
</script>

<template>
  <div class="d-block">
    <Line :data="chartData" :options="chartOptions" class="w-100"> </Line>
  </div>
</template>
