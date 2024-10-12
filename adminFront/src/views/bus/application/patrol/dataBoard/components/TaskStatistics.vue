<template>
  <div class="pos-relative">
    <div id="main" ref="main" style="height: 200px" v-if="showEcharts"></div>
    <div class="pos-absolute pos-top-50% pos-right-30px transform-translate-y--50%">
      <div
        class="flex items-center gap-10px my-8px"
        v-for="(item, index) in rightlistData"
        :key="index"
      >
        <div
          class="w-10px h-10px rounded-50%"
          :style="{
            backgroundColor: colorList[index]
          }"
        >
        </div>
        <span class="text-12px">{{ item.name }}</span>
        <span class="text-12px">{{ item.percent + '%' }}</span>
        <span class="text-12px">{{ item.total + '个' }}</span>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import * as echarts from 'echarts'
const showEcharts = ref(true)
const echartsOption = ref({})
const total = ref(0)
const rightlistData = ref([])
const echartsData = ref([])
const colorList = ['#5B8FF9', '#5AD8A6', '#FF7F50', '#5D7092', '#F6BD16']
async function changeData(data) {
  total.value = 0
  echartsData.value = []
  rightlistData.value = data
  await data.forEach((item) => {
    total.value += Number(item.total)
    echartsData.value.push({
      value: item.total,
      name: item.name
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
    color: colorList,
    series: [
      {
        name: '任务统计',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: false
          }
        },
        labelLine: {
          show: false
        },
        data: echartsData.value
      }
    ],
    title: {
      text: ['{name|任务总计}', `{val|${total.value}个}`].join('\n'),
      left: 'center',
      top: 'center',
      textStyle: {
        rich: {
          name: {
            fontSize: 12,
            fontWeight: 'normal',
            color: '#999',
            lineHeight: 30
          },
          val: {
            fontSize: 18,
            fontWeight: 'bold',
            color: '#333333',
            top: 20
          }
        }
      }
    }
  }
  var chartDom = document.getElementById('main')
  var myChart = echarts.init(chartDom)
  myChart.setOption(echartsOption.value, true)
}
defineExpose({ changeData })
</script>
