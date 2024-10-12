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
<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="approvaling-container">
    <ContentWrap class="!m-0">
      <div class="flex justify-between items-center m-b-16px">
        <div class="title flex items-center text-18px">
          <img
            src="@/assets/imgs/home/InvestmentReminder.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>招商提醒</span>
        </div>
        <el-button link type="primary" class="!c-#0C69F7"> 更多 </el-button>
      </div>
      <div class="list">
        <template v-for="(item, index) in investmentReminderList" :key="index">
          <div
            class="item flex justify-between items-center c-#9799A6 !hover-c-#0766F7 h-48px lh-48px"
            v-if="index < 8"
          >
            <div class="line-clamp-1">{{ item.name }}</div>
            <el-tag type="warning" class="ml-13px text-14px">{{ item.type }}</el-tag>
            <div class="time text-12px ml-14px c-#9799A6">{{ item.time }}</div>
          </div>
        </template>
      </div>
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
defineOptions({ name: 'InvestmentReminder' })
const props = defineProps({
  investmentReminderList: {
    type: Array,
    default: function () {
      return []
    }
  }
})

watch(
  () => props.investmentReminderList,
  (newVal) => {
    console.log(newVal)
  }
)
</script>
<style scoped lang="scss">
.item:hover .time {
  color: #0766f7;
}
</style>
