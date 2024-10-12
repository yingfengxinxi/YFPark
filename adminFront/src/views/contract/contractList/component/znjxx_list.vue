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
      <div class="detail_header_title">起算天数:</div>
      <div class="detail_header_content">
        {{ props.data.startingLateFeeDay + '天' || '--' }}
      </div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">滞纳金比例:</div>
      <div class="detail_header_content">
        {{ props.data.lateFeeRatio + '%' || '--' }}
      </div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">滞纳金上限:</div>
      <div class="detail_header_content">
        {{ props.data.upperLimitLateFee + '%' || '--' }}
      </div>
    </el-col>
  </el-row>
</template>
<script setup lang="ts">
const message = useMessage() // 消息弹窗
let props = defineProps({
  data: {
    data: Object,
    default: ''
  }
})
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
