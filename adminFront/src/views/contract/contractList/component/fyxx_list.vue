import { const } from '../../../mall/promotion/combination/activity/combinationActivity.data';
<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <div class="detail_header_title">所属楼宇:</div>
      <div class="detail_header_content">
        {{ props.data.buildName || '--' }}
      </div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">楼层/房号:</div>
      <div class="detail_header_content">
        <!-- {{ props.data.roomNumber || '--' }} -->
        <span v-for="(item, index) in checkedBuild" :key="index">
          {{ item.layerName }}/{{ item.villageRoomId }},
        </span>
      </div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">面积:</div>
      <div class="detail_header_content">
        {{ props.data.leaseArea || '--' }}
      </div>
    </el-col>
  </el-row>
</template>
<script setup lang="ts">
const message = useMessage() // 消息弹窗
const checkedBuild = ref([])
let props = defineProps({
  data: {
    data: Object,
    default: ''
  }
})
watch(
  () => props.data,
  (val) => {
    checkedBuild.value = JSON.parse(val.checkedBuild)
  }
)
</script>
<style scoped>
.detail_header_title {
  font-size: 14px;
  color: #999;
  margin: 5px;
}
.detail_header_content {
  font-size: 14px;
  color: #333;
  margin: 5px 0 0 5px;
}
</style>
