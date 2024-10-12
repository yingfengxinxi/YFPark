<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <el-row :gutter="24">
      <el-col :span="6">
        <div class="row_item">
          <div class="row_item_title">租客数量</div>
          <div class="row_item_content">{{ props.row_data.total || '0' }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="row_item">
          <div class="row_item_title">企业租客</div>
          <div class="row_item_content">{{ props.row_data.company || '0' }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="row_item">
          <div class="row_item_title">个人租客</div>
          <div class="row_item_content">{{ props.row_data.person || '0' }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="row_item">
          <div class="row_item_title">合同数量</div>
          <div class="row_item_content">{{ props.row_data.contactSign || '0' }}</div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    row_data: any
  }>(),
  {
    row_data: []
  }
)
</script>
<style lang="scss" scoped>
.row_item {
  text-align: center;
  .row_item_title {
    color: #00000073;
    font-weight: 600;
    font-size: 14px;
    margin-bottom: 10px;
  }
  .row_item_content {
    color: #000000d9;
    font-weight: 600;
    font-size: 24px;
  }
}
</style>
