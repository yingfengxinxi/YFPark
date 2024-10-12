<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
import { type } from '../../../../types/auto-imports'
let datas = [
  { count: 1048, value: '小学' },
  { count: 735, value: '初中' },
  { count: 580, value: '高中' },
  { count: 484, value: '大专' },
  { count: 300, value: '本科' },
  { count: 300, value: '硕士' },
  { count: 300, value: '博士' },
  { count: 300, value: '文盲' }
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
    // let data = datas
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
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: XLabel.value
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
