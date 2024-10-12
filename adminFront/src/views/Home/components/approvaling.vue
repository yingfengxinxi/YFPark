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
      <div class="flex justify-between items-center">
        <div class="title flex items-center text-18px">
          <img
            src="@/assets/imgs/home/Approvaling.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>进行中的审批</span>
        </div>
        <el-button link type="primary" class="!c-#0C69F7"> 更多 </el-button>
      </div>
      <div class="list">
        <template v-for="(item, index) in approvalingList" :key="index">
          <el-card
            class="mt-20px"
            shadow="hover"
            body-class="bg-#F7F8FA !p-16px !pl-8px !pr-8px !pb-12px b-rd-4px"
            v-if="index < 2"
          >
            <div class="flex items-center c-#333 text-16px fw-500">
              <img src="@/assets/imgs/home/contractIcon.png" class="w-30px h-30px mr-18px" alt="" />
              <div class="">{{ item.name }}</div>
              <el-tag type="success" class="ml-13px flex-1 line-clamp-1">{{ item.status }}</el-tag>
            </div>
            <div class="mt-9px line-clamp-1 text-14px c-#333">
              <span class="inline-block w-40px text-12px c-#8E9AB2 mr-4px">楼宇</span>
              {{ item.village }} </div
            ><div class="mt-10px line-clamp-1 text-14px c-#333">
              <span class="inline-block w-40px text-12px c-#8E9AB2 mr-4px">提交人</span>
              {{ item.created }}
            </div>
            <div class="mt-16px pl-9px pr-9px h-32px lh-32px b-rd-6px c-#8E9AB2 text-12px bg-#fff">
              {{ item.time }}
            </div>
          </el-card>
        </template>
      </div>
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
defineOptions({ name: 'Approvaling' })
const props = defineProps({
  approvalingList: {
    type: Array,
    default: function () {
      return []
    }
  }
})

watch(
  () => props.approvalingList,
  (newVal) => {
    console.log(newVal)
  }
)
</script>
<style scoped lang="scss">
// .approvaling-container;
::v-deep .el-card__body {
  padding: 14px 20px !important;
}
</style>
