<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
import { watch } from 'vue'
let datas = [
  { count: 1048, value: '道教' },
  { count: 735, value: '佛教' },
  { count: 580, value: '基督教' },
  { count: 484, value: '伊斯兰教' },
  { count: 300, value: '天主教' },
  { count: 300, value: '其他' },
  { count: 300, value: '无' }
]

const props = withDefaults(
  defineProps<{
    data: array
  }>(),
  {}
)
let seriesArr = ref([])
let XLabel = ref([])
let echartsOption = ref({})
watch(
  () => props.data,
  async (newVal) => {
    let data = newVal
    await data.map((item, index) => {
      XLabel.value.push(item.value)
      let obj = {}
      obj.name = item.value
      obj.type = 'bar'
      obj.barWidth = 18
      obj.stack = '广告'
      obj.data = []
      for (var i = 0; i <= index; i++) {
        if (i != index) {
          obj.data.push(0)
        } else {
          obj.data.push(item.count)
        }
      }
      seriesArr.value.push(obj)
    })
  },
  (echartsOption.value = {
    tooltip: {
      show: false,
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      type: 'scroll'
    },
    grid: {
      left: '0%',
      top: '15%',
      right: '0%',
      bottom: '0%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: XLabel.value,
        axisTick: {
          alignWithLabel: true
        },
        axisLabel: {
          interval: 0,
          rotate: 45
        }
      }
    ],
    yAxis: [
      {
        type: 'value'
      }
    ],
    series: seriesArr.value
  })
)
</script>
<style lang="scss" scoped></style>
