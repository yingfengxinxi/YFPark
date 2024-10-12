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
            src="@/assets/imgs/home/Trent.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>空置预警</span>
        </div>
        <div class="flex items-center c-#333333">
          <div class="w-10px h-10px bg-#1771F7 mr-19px"></div>
          空置房间
        </div>
      </div>
      <Echart :height="308" :options="vacancyAlertOption" />
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
defineOptions({ name: 'VacancyAlert' })
const props = defineProps({
  vacancyAlertOption: {
    type: Object,
    default: function () {
      return {}
    }
  }
})
watch(
  () => props.vacancyAlertOption,
  (newVal) => {
    console.log(newVal, '999')
  }
)
</script>
<!-- <style lang="scss" scoped>
::v-deep .el-card__body {
  padding-bottom: 0px !important;
}
</style> -->
