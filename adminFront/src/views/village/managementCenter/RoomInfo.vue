<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap class="m-10px" v-if="props.level == 4">
    <el-descriptions title="基础信息">
      <el-descriptions-item label="资源名称：" width="33%">{{
        info.roomName || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="房间数字编号：" width="33%">{{
        info.roomNumber || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="资源地址：" width="33%">
        <template v-if="info.villageName || info.buildName || info.layerName">
          <span v-if="info.villageName"> {{ info.villageName + ' / ' }}</span>
          <span v-if="info.buildName"> {{ info.buildName + ' / ' }}</span>
          {{ info.layerName }}
        </template>
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="资源管理编号：">{{
        info.roomAliasId || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="计租面积："
        >{{ info.rentalArea || '--' }} ㎡</el-descriptions-item
      >
      <el-descriptions-item label="计费面积："
        >{{ info.chargingArea || '--' }} ㎡</el-descriptions-item
      >
      <el-descriptions-item label="建筑面积："
        >{{ info.buildArea || '--' }} ㎡</el-descriptions-item
      >
      <el-descriptions-item label="套内面积："
        >{{ info.insideArea || '--' }} ㎡</el-descriptions-item
      >
      <el-descriptions-item label="虚拟资源：">{{
        info.isUnreal == 0 ? '否' : '是'
      }}</el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="招商信息" class="m-t-20px">
      <el-descriptions-item label="产权性质：">
        <template v-if="info.propertyRight">
          <template v-for="(item, index) in list" :key="index">
            <span v-if="item.value == info.propertyRight">{{ item.label }}</span>
          </template>
        </template>
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="资源状态：">
        <template v-for="(item, index) in statusList" :key="index">
          <span v-if="item.value == info.status">{{ item.label }}</span>
        </template>
      </el-descriptions-item>
      <el-descriptions-item label="租赁状态：">
        {{
          info.isLock == 1
            ? '锁定'
            : info.status >= 20
              ? '已租'
              : info.status >= 10
                ? '已预订'
                : '待租'
        }}
      </el-descriptions-item>
      <el-descriptions-item label="招商状态：">{{
        info.invitationStatus == 1 ? '招商' : '不招商'
      }}</el-descriptions-item>
      <el-descriptions-item label="可租日期：">{{
        formatDate(info.leaseEnd) || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="装修情况：">
        <template v-if="info.decoration">
          <template v-for="(item, index) in decorationList" :key="index">
            <span v-if="item.value == info.decoration">{{ item.label }}</span>
          </template>
        </template>
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="资源类型：">
        <template v-if="info.decoration">
          <template v-for="(item, index) in houseTypeList" :key="index">
            <span v-if="item.value == info.houseType">{{ item.label }}</span>
          </template>
        </template>
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="报价：">
        <template v-if="info.decoration"
          >{{ info.preUnitPrice }}
          <template v-for="(item, index) in priceUnitList" :key="index">
            <span v-if="item.value == info.priceUnit"> {{ item.label }}</span>
          </template>
        </template>
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="低价：">
        <template v-if="info.decoration"
          >{{ info.preUnitPriceMin }}
          <template v-for="(item, index) in priceUnitList" :key="index">
            <span v-if="item.value == info.priceUnitMin"> {{ item.label }}</span>
          </template>
        </template>
        <template v-else> -- </template></el-descriptions-item
      >
      <el-descriptions-item label="资源备案号：">{{ info.recordNo || '--' }}</el-descriptions-item>
      <el-descriptions-item label="层高：">{{ info.floorHeight || '--' }} m</el-descriptions-item>
      <el-descriptions-item label="荷载值：">{{ info.loadMax || '--' }}</el-descriptions-item>
      <el-descriptions-item label="VR链接：">{{ info.vrLink || '--' }}</el-descriptions-item>
      <el-descriptions-item label="资源标签：">
        <TagIdArr :list="props.info?.tagHouseList" v-if="props.info?.tagHouseList" />
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="资源图片：">
        <el-image
          v-if="info.logo"
          lazy
          style="width: 100px; height: 100px"
          :src="info.logo"
          :preview-src-list="srcList"
        />
        <template v-else>无</template>
      </el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="拓展信息" v-if="info.extraConfig" class="m-t-20px">
      <el-descriptions-item label="使用率：">{{
        info.extraConfig.utilization || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="办公格局：">{{
        info.extraConfig.officePattern || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="窗面朝向：">{{
        info.extraConfig.windowFaceOrientation || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="免租期：">{{
        info.extraConfig.rentFree || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="最短租期：">{{
        info.extraConfig.minLeaseTerm || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="排序值：">{{ info.sort || '--' }}</el-descriptions-item>
      <el-descriptions-item label="工位数量：">
        <template v-if="info.extraConfig.minLeaseTerm">
          {{ info.extraConfig.minLeaseTerm }} - {{ info.extraConfig.stationNumberMax }}
        </template>
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="户型图说明：">{{
        info.extraConfig.houseDescription || '--'
      }}</el-descriptions-item>
      <el-descriptions-item label="户型图：">
        <el-image
          v-if="info.extraConfig.house"
          lazy
          style="width: 100px; height: 100px"
          :src="info.extraConfig.house"
          :preview-src-list="srcListHouse"
        />
        <template v-else>无</template>
      </el-descriptions-item>
    </el-descriptions>
  </ContentWrap>
</template>
<script setup lang="ts">
defineOptions({ name: 'BuildingInfo' })
import { formatDate } from '@/utils/formatTime'
const props = defineProps({
  info: {
    type: Object,
    default: () => {}
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
const srcList = computed(() => {
  let data = []
  data.push(props.info.logo)
  return data
})
const srcListHouse = computed(() => {
  let data = []
  data.push(props.info.house)
  return data
})
const statusList = ref([])
const getStatusList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.RESOURCESTATE)
    statusList.value = data
  } finally {
  }
}
const decorationList = ref([])
const getDecorationList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.DECORATION)
    decorationList.value = data
  } finally {
  }
}
// 资源类型
const houseTypeList = ref([])
const getHouseTypeList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.HOUSETYPE)
    houseTypeList.value = data
  } finally {
  }
}
const info = ref(props.info)
watchEffect(() => {
  info.value = props.info
})
// 价格单位
const priceUnitList = ref([])
const getPriceUnitList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.PRICEUNIT)
    priceUnitList.value = data
    if (props.info && !props.info.priceUnitMin) info.priceUnitMin = data[0].value
    if (props.info && !props.info.priceUnit) info.priceUnit = data[0].value
  } finally {
  }
}
// 监听模型绑定值变动
watch(
  () => props.level,
  (val) => {
    if (val == 4) {
      getStatusList()
      getDecorationList()
      getHouseTypeList()
      getPriceUnitList()
    }
  },
  { immediate: true, deep: true }
)
// watch(
//   () => props.info,
//   (val) => {},
//   { immediate: true, deep: true }
// )
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
