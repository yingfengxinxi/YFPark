<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
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
            src="@/assets/imgs/home/InterestRate.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>利率/均价</span>
        </div>
      </div>
      <el-row :gutter="15">
        <el-col :span="8">
          <div class="bg-#F6F9FF b-rd-4px h-158px">
            <Echart :height="130" :options="averagePriceOption" />
            <div class="text-center p-b-8px"
              >在租实时均价<span class="c-#2678FF">(㎡·月) </span></div
            >
          </div>
        </el-col>
        <el-col :span="16">
          <div class="">
            <Echart :height="158" :options="InterestRateOption" />
          </div>
        </el-col>
      </el-row>
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
defineOptions({ name: 'SmartDevice' })
const props = defineProps({
  InterestRateOption: {
    type: Object,
    default: function () {
      return {}
    }
  },
  averagePriceOption: {
    type: Object,
    default: function () {
      return {}
    }
  }
})
watch(
  () => props.InterestRateOption,
  (newVal) => {
    console.log(newVal, '999')
  }
)
watch(
  () => props.averagePriceOption,
  (newVal) => {
    console.log(newVal, '999')
  }
)
</script>
<style lang="scss" scoped>
::v-deep .el-card__body {
  padding-bottom: 12px !important;
}
</style>
