<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="viewHeader bg-#fff p-20px p-b-0px m-b-20px">
    <div class="flex justify-between flex-items-center">
      <el-menu
        class="flex-1"
        :default-active="activeIndex"
        ellipsis
        mode="horizontal"
        popper-append-to-body
        teleported
        @select="handleSelect"
      >
        <el-menu-item index="0">应收报表</el-menu-item>
        <el-menu-item index="1">应支报表</el-menu-item>
        <el-menu-item index="2">调整账单</el-menu-item>
      </el-menu>
      <el-radio-group v-model="viewType" v-if="activeIndex != '2'">
        <el-radio-button label="表格" value="1" />
        <el-radio-button label="图表" value="2" />
      </el-radio-group>
    </div>
  </div>
  <!-- <RuleList v-if="activeIndex == '0'" />
  <SellerList v-if="activeIndex == '1'" /> -->
  <Statement
    v-if="activeIndex == '0' || activeIndex == '1'"
    :activeIndex="activeIndex"
    :buildingList="buildingList"
    :projectList="projectList"
    :viewType="viewType"
    :feeTypeList="feeTypeList"
  />
  <AdjustList v-if="activeIndex == '2'" :buildingList="buildingList" :projectList="projectList" />
</template>

<script setup lang="ts">
/** 财务报表 列表 */
defineOptions({ name: 'ReportList' })
import AdjustList from './components/adjustList.vue'
import Statement from './components/statement.vue'
import { BuildApi } from '@/api/bus/village/index'
import { BuildingApi } from '@/api/bus/village/building'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
const activeIndex = ref('0')
const viewType = ref('1')
const handleSelect = (key: string) => {
  activeIndex.value = key
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

/** 费用类型 */
const feeTypeList = ref<any[]>([])
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    feeTypeList.value = data
  } finally {
  }
}
/** 初始化 **/
onMounted(() => {
  // project_remoteMethod()
  building_remoteMethod()
  getCostTypeChildrenList()
})
</script>
<style lang="scss" scoped>
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  background: none !important;
}
</style>
