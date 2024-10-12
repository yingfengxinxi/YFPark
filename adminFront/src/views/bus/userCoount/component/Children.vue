<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
let datas = [
  { count: 1048, value: '留守' },
  { count: 735, value: '孤儿' },
  { count: 580, value: '困境' },
  { count: 484, value: '实事无人抚养' }
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
    echartsOption.value = {
      tooltip: {
        show: false,
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      legend: {},
      grid: {
        left: '0%',
        top: '15%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'value',
        data: XLabel.value
      },
      yAxis: {
        type: 'category',
        data: ['留守', '孤儿', '困境', '实事无人抚养']
      },
      series: seriesArr.value
    } as EChartsOption
  }
)
// datas.map((item, index) => {
//   XLabel.push(item.name)
//   let obj = {}
//   obj.name = item.name
//   obj.type = 'bar'
//   obj.barWidth = 18
//   obj.stack = '广告'
//   obj.data = []
//   for (var i = 0; i <= index; i++) {
//     if (i != index) {
//       obj.data.push(0)
//     } else {
//       obj.data.push(item.value)
//     }
//   }
//   seriesArr.push(obj)
// })

// const echartsOption = reactive<EChartsOption>({
//   tooltip: {
//     show: false,
//     trigger: 'axis',
//     axisPointer: {
//       type: 'shadow'
//     }
//   },
//   legend: {},
//   grid: {
//     left: '0%',
//     top: '15%',
//     right: '4%',
//     bottom: '3%',
//     containLabel: true
//   },
//   xAxis: {
//     type: 'value',
//     data: XLabel
//   },
//   yAxis: {
//     type: 'category',
//     data: ['留守', '孤儿', '困境', '实事无人抚养']
//   },
//   series: seriesArr
// }) as EChartsOption
</script>
<style lang="scss" scoped></style>
