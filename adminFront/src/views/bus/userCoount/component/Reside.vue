<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
let datas = [
  { value: 1048, name: '独居' },
  { value: 735, name: '与亲戚合住' },
  { value: 580, name: '居无定所' },
  { value: 484, name: '与子女合住' },
  { value: 300, name: '与配偶和住' },
  { value: 1048, name: '其他' },
  { value: 735, name: '租房' }
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
    await data.map((res, index) => {
      XLabel.value.push(res.value)
      let obj = {}
      obj.name = res.value
      obj.type = 'bar'
      obj.barWidth = 18
      obj.stack = '广告'
      obj.data = []
      for (var i = 0; i <= index; i++) {
        if (i != index) {
          obj.data.push(0)
        } else {
          obj.data.push(res.count)
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
      data: XLabel
    },
    series: seriesArr.value
  } as EChartsOption)
)
</script>
<style lang="scss" scoped></style>
