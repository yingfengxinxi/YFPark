<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="viewHeader bg-#fff p-20px p-b-0px m-b-20px">
    <!-- <el-tabs v-model="activeKey">
      <el-tab-pane name="1" class="!m-b-0px" label="逾期收款账单" />
      <el-tab-pane name="2" label="逾期付款账单" />
    </el-tabs> -->
    <el-menu
      class="flex-1"
      :default-active="activeKey"
      ellipsis
      mode="horizontal"
      popper-append-to-body
      teleported
      @select="handleSelect"
    >
      <el-menu-item index="1">逾期收款账单</el-menu-item>
      <el-menu-item index="2">逾期付款账单</el-menu-item>
    </el-menu>
  </div>
  <OverdueAccount
    v-if="activeKey == '1'"
    :feeTypeList="feeTypeList"
    :projectList="projectList"
    :buildingList="buildingList"
  />
  <OverdueBill
    v-if="activeKey == '2'"
    :feeTypeList="feeTypeList"
    :projectList="projectList"
    :buildingList="buildingList"
  />
</template>

<script setup lang="ts">
/** 机构账单收支流水 列表 */
defineOptions({ name: 'OverdueBillList' })
import OverdueBill from './components/overdueBill.vue'
import OverdueAccount from './components/overdueAccount.vue'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { BuildApi } from '@/api/bus/village/index'
import { BuildingApi } from '@/api/bus/village/building'
const activeKey = ref('1')
const feeTypeList = ref([])
const handleSelect = (key: string, keyPath: string[]) => {
  activeKey.value = key
}
//费用类型
const getfeeTypeList = () => {
  orgBillCostTypeApi.getCostTypeChildrenList().then((res) => {
    feeTypeList.value = res
    console.log(feeTypeList.value, 'feeTypeList')
  })
}
//获得项目列表
const projectList = ref([])
const project_remoteMethod = () => {
  BuildApi.getVillageList({}).then((res) => {
    projectList.value = res
  })
}
//获取楼宇列表
const buildingList = ref([])
const building_remoteMethod = () => {
  BuildingApi.getBuildingList().then((res) => {
    buildingList.value = res
  })
}
onMounted(() => {
  getfeeTypeList()
  project_remoteMethod()
  building_remoteMethod()
})
</script>
<style lang="scss" scoped>
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  background: none !important;
}

::v-deep .viewHeader .el-card__body {
  padding-bottom: 0px !important;
}
</style>
