<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="shortcut-container w-100%">
    <ContentWrap class="!m-0">
      <div class="flex justify-between items-center mb-17px">
        <div class="title flex items-center text-18px">
          <img
            src="@/assets/imgs/home/smartDevice.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>房源概括</span>
        </div>
      </div>
      <Echart :height="310" :options="houseSummarizedOption" />
      <!-- <el-space alignment="start" :size="10" wrap>
        <div
          v-for="(item, index) in houseSummarizedOption.color"
          :key="index"
          class="flex justify-start items-center"
        >
          <div class="w-13px h-13px b-rd-50% m-r-5px" :style="'background:' + item"></div>
          <div class="text-12rpx c-#4E5969"
            >{{ houseSummarizedOption.series[0].data[index].name }}

            {{ houseSummarizedOption.series[0].data[index].value }}
            <template v-if="houseSummarizedOption.series[0].data[index].value">
              {{
                (
                  houseSummarizedOption.title.subtext /
                  houseSummarizedOption.series[0].data[index].value
                ).toFixed(2)
              }}%
            </template>
            <template v-else> 0.00% </template>
          </div>
        </div>
      </el-space> -->
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
defineOptions({ name: 'SmartDevice' })
const props = defineProps({
  houseSummarizedOption: {
    type: Object,
    default: function () {
      return {}
    }
  }
})
watch(
  () => props.houseSummarizedOption,
  (newVal) => {
    console.log(newVal, '999')
  }
)
</script>
<style lang="scss" scoped></style>
