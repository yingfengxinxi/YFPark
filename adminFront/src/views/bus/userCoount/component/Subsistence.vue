<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
import { watch } from 'vue'
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
  async (newVal) => {
    await newVal.map((res) => {
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
          name: '是否低保户',
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
    } as EChartsOption
  }
)
</script>
