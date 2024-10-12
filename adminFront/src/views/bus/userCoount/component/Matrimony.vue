<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
const props = withDefaults(
  defineProps<{
    data: array
  }>(),
  {}
)
let seriesArr = ref([])
let echartsOption = ref({})
watch(
  () => props.data,
  async (params: type) => {
    await params.map((res: type) => {
      seriesArr.value.push({
        name: res.value,
        value: res.count
      })
    })
    echartsOption.value = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        type: 'scroll',
        left: 'left'
      },

      series: [
        {
          name: '婚姻状况',
          type: 'pie',
          radius: '60%',
          left: '0%',
          center: ['60%', '50%'],
          data: seriesArr.value,
          label: {
            normal: {
              show: false
            }
          },
          labelLine: {
            normal: {
              show: false
            }
          },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    } as EchartsOption
  }
)
</script>
