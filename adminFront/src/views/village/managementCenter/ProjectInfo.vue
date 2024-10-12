<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap class="m-10px">
    <el-descriptions title="基础信息">
      <el-descriptions-item label="项目名称：">{{ info.name || '--' }}</el-descriptions-item>
      <el-descriptions-item label="项目简称：">{{ info.shortName || '--' }}</el-descriptions-item>
      <el-descriptions-item label="项目类型：">
        <template v-for="(item, index) in list" :key="index">
          <span v-if="item.alias == info.type">{{ item.name }}</span>
        </template>
      </el-descriptions-item>
      <el-descriptions-item label="项目地址：">
        {{ info.address || '--' }}
      </el-descriptions-item>
      <el-descriptions-item label="项目简介：">
        {{ info.describe || '--' }}
      </el-descriptions-item>
      <el-descriptions-item label="管理面积：">{{
        info.managementArea || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="可招商面积：">{{
        info.rentableArea || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="总资源数量：">{{
        info.roomCount ? info.roomCount + '间' : '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="可招商资源数量：">{{
        info.roomRentableCount ? info.roomRentableCount + '间' : '--'
      }}</el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="拓展信息" class="m-t-20px">
      <el-descriptions-item label="排序值：">{{ info.sort || '--' }}</el-descriptions-item>
      <el-descriptions-item label="竣工时间：">{{
        info.extraConfig?.completedTime || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="标准层高：">{{
        info.extraConfig?.floor_height || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="物业：">
        {{ info.extraConfig?.ownerName || '--' }}
      </el-descriptions-item>
      <el-descriptions-item label="物业费：">
        {{ info.extraConfig?.ownerCost || '--' }}
      </el-descriptions-item>
      <el-descriptions-item label="车位数量：">{{
        info.extraConfig?.parkingNumber || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="车位月租金：">{{
        info.extraConfig?.parkingMonthlyRent || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="空调：">{{
        info.extraConfig?.airConditioner || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="空调费：">{{
        info.extraConfig?.airConditionerCost || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="空调开放时间：">{{
        info.extraConfig?.airConditionerTime || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="电梯：">{{
        info.extraConfig?.elevator || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="网络：">{{
        info.extraConfig?.network || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="入驻企业：">{{
        info.extraConfig?.settledEnterprise || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="招商岗位：">{{
        info.extraConfig?.post_txt || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="招商部门：">{{
        info.extraConfig?.department_txt || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="招商时间：">{{
        info.extraConfig?.attractInvestmentTime || '--'
      }}</el-descriptions-item>
    </el-descriptions>
  </ContentWrap>
</template>
<script setup lang="ts">
defineOptions({ name: 'ProjectInfo' })
defineProps({
  info: {
    type: Object
  }
})
import { VillageTypeApi } from '@/api/bus/tag/villageTypeList/index'
const list = ref([] as any[])
const getList = async () => {
  const res = await VillageTypeApi.getVillageTypeNopage()
  list.value = res
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>

<!-- <style lang="scss" scoped></style> -->
