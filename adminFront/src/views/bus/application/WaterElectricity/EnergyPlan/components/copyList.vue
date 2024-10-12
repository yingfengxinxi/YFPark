<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <div class="mb-[18px]">
      <el-button @click="emit('goback')" type="primary">
        <Icon icon="ep:arrow-left" />
        返回抄表计划
      </el-button>
    </div>
    <ContentWrap>
      <div class="font-semibold pb-[18px]"> 本期未抄录 </div>
      <el-table :data="List" v-loading="loading">
        <el-table-column label="表名称" align="center" prop="name" />
        <el-table-column label="表具编号" align="center" prop="number" />
        <el-table-column label="用途" align="center" prop="purpose" />
        <el-table-column label="绑定位置范围" align="center" prop="bindingLocation" />
        <el-table-column
          label="最新抄录时间"
          align="center"
          prop="thisTime"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column label="上次读数" align="center" prop="thisNumber" />
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="primary" text @click="openForm(scope.row)"> 抄表 </el-button>
          </template>
        </el-table-column>
      </el-table>
    </ContentWrap>
  </div>
  <copyListForm ref="copyListFormRef" />
</template>
<script lang="ts" setup>
import { tableColumnEmptyPlaceholder } from '@/utils/index'

import { energyPlanApi, VO } from '@/api/bus/WaterElectricity/EnergyPlan/index.ts'
import copyListForm from './copyListForm.vue'
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10
})
const loading = ref(true)
const total = 1
const List = ref<vo[]>()
const getList = (row) => {
  energyPlanApi
    .notCompleteTaskList({
      planId: row.id
    })
    .then((res) => {
      List.value = res
      loading.value = false
    })
}
const emit = defineEmits(['goback'])
function open(row: VO) {
  getList(row)
}
defineExpose({ open })
const copyListFormRef = ref()
const openForm = (row: VO) => {
  // emit('openForm', row)
  copyListFormRef.value?.open(row)
}
</script>
