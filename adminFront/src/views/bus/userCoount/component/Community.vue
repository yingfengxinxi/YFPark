<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
let datas = [
  { count: 1048, value: '孕产妇' },
  { count: 735, value: '3岁以下儿童' },
  { count: 580, value: '老年人' },
  { count: 484, value: '残疾人' },
  { count: 300, value: '慢性病人' }
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
  } as EChartsOption)
)
</script>
<style lang="scss" scoped></style>
