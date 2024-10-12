<template>
  <div class="flex justify-between h-100%">
    <Echart :height="300" :options="echartsOption" style="width: 80%" />
    <div class="text-12px color-#8C8C8C py-10px pl-10px w-20%">
      <div class="pos-relative">
        <div class="itemTitle1">客服</div>
      </div>
      <div class="text-16px color-#000 my-10px">{{ total[0].totals }}</div>
      <div class="pos-relative">
        <div class="itemTitle2">物业</div>
      </div>
      <div class="text-16px color-#000 my-10px">{{ total[1].totals }}</div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
import { watch, onMounted } from 'vue'
let seriesArr = ref([])
let echartsOption = ref({})
const total = ref([{ totals: 0 }, { totals: 0 }])
onMounted(() => {})
function changeData(data) {
  const indicator = []
  const kefuList = []
  const wuyeList = []
  data.list.forEach((item) => {
    indicator.push({
      name: item.index
    })
    kefuList.push(item.kefu)
    wuyeList.push(item.wuye)
  })
  total.value = data.total

  echartsOption.value = {
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '12%',
      containLabel: true
    },
    tooltip: {
      trigger: 'axis'
    },
    radar: {
      indicator: indicator
    },
    series: [
      {
        type: 'radar',
        data: [
          {
            value: kefuList
          },
          {
            value: wuyeList
          }
        ]
      }
    ]
  } as EChartsOption
}
defineExpose({ changeData })
</script>
<style lang="scss" scoped>
.itemTitle1 {
  &::before {
    content: '';
    display: inline-block;
    width: 10px;
    height: 10px;
    background-color: #5470c6;
    border-radius: 50%;
    margin-right: 5px;
  }
}
.itemTitle2 {
  &::before {
    content: '';
    display: inline-block;
    width: 10px;
    height: 10px;
    background-color: #91cc75;
    border-radius: 50%;
    margin-right: 5px;
  }
}
</style>
