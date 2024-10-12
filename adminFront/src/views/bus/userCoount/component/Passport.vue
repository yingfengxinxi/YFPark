<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'

let datas = [
  { count: 1048, value: '一级护照' },
  { count: 735, value: '二级护照' },
  { count: 580, value: '三级护照' },
  { count: 484, value: '四级护照' },
  { count: 300, value: '五级护照' },
  { count: 1048, value: '六级护照' },
  { count: 735, value: '七级护照' },
  { count: 580, value: '八级护照' },
  { count: 484, value: '九级护照' },
  { count: 300, value: '正常' }
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
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: XLabel.value,
      axisLabel: {
        interval: 0,
        rotate: 45
      }
    },
    yAxis: {
      type: 'value',
      data: XLabel.value
    },
    series: seriesArr.value
  } as EChartsOption)
)
</script>
<style lang="scss" scoped></style>
