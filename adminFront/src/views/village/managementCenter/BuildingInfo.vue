<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap class="m-10px" v-if="props.level != 4">
    <el-descriptions title="基础信息">
      <el-descriptions-item label="所属项目">{{ info.villageName || '--' }}</el-descriptions-item>
      <el-descriptions-item label="建筑编号">{{ info.buildNumber || '--' }}</el-descriptions-item>
      <el-descriptions-item label="建筑地址">{{ info.address || '--' }}</el-descriptions-item>
      <el-descriptions-item label="产权性质">
        <template v-for="(item, index) in list" :key="index">
          <span v-if="item.value == info.propertyRight">{{ item.label }}</span>
        </template>
      </el-descriptions-item>
      <el-descriptions-item label="不动产编号">
        {{ info.estateNumber || '--' }}
      </el-descriptions-item>
      <el-descriptions-item label="产权编号">
        {{ info.propertyNumber || '--' }}
      </el-descriptions-item>
      <el-descriptions-item label="土地编号">{{ info.landNumber || '--' }}</el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="建筑面积" class="m-t-20px">
      <el-descriptions-item label="建筑面积">{{ info.buildArea || '--' }}</el-descriptions-item>
      <el-descriptions-item label="产权面积">{{ info.propertyArea || '--' }}</el-descriptions-item>
      <el-descriptions-item label="可租面积">{{ info.rentableArea || '--' }}</el-descriptions-item>
      <el-descriptions-item label="自用面积">{{ info.selfUseArea || '--' }}</el-descriptions-item>
      <el-descriptions-item label="配套面积">{{
        info.supportingArea || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="车位面积">{{ info.parkingArea || '--' }}</el-descriptions-item>
      <el-descriptions-item label="车位数量">{{ info.parkingCount || '--' }}</el-descriptions-item>
      <el-descriptions-item label="标准层高">{{ info.floorHeight || '--' }}</el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="拓展信息" :column="1" class="m-t-20px">
      <el-descriptions-item label="建筑标签">
        <TagIdArr :list="info?.tagIdArr" />
      </el-descriptions-item>
      <el-descriptions-item label="建筑简介">
        <div
          class="inline-block"
          v-html="info.extraConfig.info"
          v-if="info.extraConfig?.info"
        ></div>
        <template v-else>无</template>
      </el-descriptions-item>
      <el-descriptions-item label="建筑图片">
        <el-image
          v-if="info.logo"
          lazy
          style="width: 100px; height: 100px"
          :src="info.logo"
          :preview-src-list="srcList"
        />
        <template v-else>无</template>
      </el-descriptions-item>
      <!-- <el-descriptions-item label="竣工时间">{{
        info.extraConfig?.completedTime || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="标准层高">{{
        info.extraConfig?.floor_height || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="物业">
        {{ info.extraConfig?.ownerName || '--' }}
      </el-descriptions-item>
      <el-descriptions-item label="物业费">
        {{ info.extraConfig?.ownerCost || '--' }}
      </el-descriptions-item>
      <el-descriptions-item label="车位数量">{{
        info.extraConfig?.parkingNumber || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="车位月租金">{{
        info.extraConfig?.parkingMonthlyRent || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="空调">{{
        info.extraConfig?.airConditioner || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="空调费">{{
        info.extraConfig?.airConditionerCost || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="空调开放时间">{{
        info.extraConfig?.airConditionerTime || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="电梯">{{
        info.extraConfig?.elevator || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="网络">{{
        info.extraConfig?.network || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="入驻企业">{{
        info.extraConfig?.settledEnterprise || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="招商岗位">{{
        info.extraConfig?.post_txt || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="招商部门">{{
        info.extraConfig?.department_txt || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="招商时间">{{
        info.extraConfig?.attractInvestmentTime || '--'
      }}</el-descriptions-item> -->
    </el-descriptions>
  </ContentWrap>
</template>
<script setup lang="ts">
defineOptions({ name: 'BuildingInfo' })
const props = defineProps({
  info: {
    type: Object
  },
  level: {
    type: Number
  }
})
import { VillageTypeApi } from '@/api/bus/tag/villageTypeList/index'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
const list = ref([] as any[])
const getList = async () => {
  const res = await getStrDictOptions(DICT_TYPE.PROPERTYRIGHTNATURE)
  list.value = res
}

const info = props.info
const srcList = computed(() => {
  let data = []
  data.push(props.info.logo)
  return data
})

// 监听模型绑定值变动
watch(
  () => props.level,
  (val) => {},
  { immediate: true, deep: true }
)
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
