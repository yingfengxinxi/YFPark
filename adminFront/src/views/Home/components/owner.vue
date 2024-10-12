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
            src="@/assets/imgs/home/message.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>待发通知单租客</span>
        </div>
        <el-button link type="primary" class="!c-#0C69F7"> 更多 </el-button>
      </div>
      <div class="list">
        <template v-for="(item, index) in ownerList" :key="index">
          <div class="flex items-center c-#9799A6 hover-c-#0766F7 h-42px lh-42px" v-if="index < 8">
            <div class="w-14px h-14px b-rd-14px bg-#DFE4F0 mr-16px"></div>
            <div class="flex-1 line-clamp-1 text-14px">{{ item.name }}</div>
            <div class="text-12px ml-14px">{{ item.num }}</div>
          </div>
        </template>
      </div>
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
defineOptions({ name: 'Approvaling' })
const props = defineProps({
  ownerList: {
    type: Array,
    default: function () {
      return []
    }
  }
})

watch(
  () => props.ownerList,
  (newVal) => {
    console.log(newVal)
  }
)
</script>
