<template>
  <Echart :height="300" :options="echartsOption" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
import { watch, onMounted } from 'vue'
let echartsOption = ref({})

function changeData(Arr) {
  const name = ['PC', '员工端', '租客端']
  const date = []
  const data = {
    data1: [],
    data2: [],
    data3: []
  }
  Arr.forEach((item) => {
    if (!date.includes(item.date)) {
      date.push(item.date)
    }

    if (item.name === 'PC') {
      data.data1.push(item.total)
    } else if (item.name === '员工端') {
      data.data2.push(item.total)
    } else if (item.name === '租客端') {
      data.data3.push(item.total)
    }
  })
  echartsOption.value = {
    xAxis: {
      type: 'category',
      data: date
    },
    yAxis: {
      type: 'value'
    },
    legend: {
      show: true,
      type: 'plain',
      data: name
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '12%',
      containLabel: true
    },
    series: [
      {
        data: data.data1,
        type: 'bar',
        name: 'PC'
      },
      {
        data: data.data2,
        type: 'bar',
        name: '员工端'
      },
      {
        data: data.data3,
        type: 'bar',
        name: '租客端'
      }
    ]
  } as EChartsOption
}
defineExpose({ changeData })
</script>
