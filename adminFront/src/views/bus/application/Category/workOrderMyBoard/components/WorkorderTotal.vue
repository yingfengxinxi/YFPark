<template>
  <div class="flex justify-between h-100%">
    <div class="min-w-100px text-12px color-#8C8C8C py-10px pl-10px w-10%">
      <div>累计处理工单数</div>
      <div class="text-16px color-#000 my-10px">{{ seriesArr.workOrderTotal || '--' }}</div>
      <div>累计回复数</div>
      <div class="text-16px color-#000 my-10px">{{ seriesArr.replyOrderTotal || '--' }}</div>
    </div>
    <Echart :height="300" :options="echartsOption" style="width: 90%" />
  </div>
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
import { watch, onMounted } from 'vue'
let seriesArr = ref([])
let echartsOption = ref({})

onMounted(() => {})

function changeData(data) {
  seriesArr.value = data
  const nameArr = []
  const ValueArr = []
  data.list.forEach((item) => {
    nameArr.push(item.name)
    ValueArr.push(item.total)
  })
  echartsOption.value = {
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: nameArr
    },
    yAxis: {
      type: 'value',
      name: '近一周工单数'
    },
    grid: {
      left: '3%',
      right: '8%',
      bottom: '3%',
      top: '12%',
      containLabel: true
    },
    tooltip: {
      trigger: 'axis'
    },
    series: [
      {
        data: ValueArr,
        type: 'line',
        smooth: true,
        areaStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 0,
                color: '#5B8FF9' // 0% 处的颜色
              },
              {
                offset: 1,
                color: '#A1BFFC' // 100% 处的颜色
              }
            ],
            global: false // 缺省为 false
          }
        }
      }
    ]
  } as EChartsOption
}
defineExpose({ changeData })
</script>
