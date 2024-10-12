<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-row class="m-10px">
    <el-col class="flex justify-between flex-wrap gap-0">
      <el-card
        class="!border-rd-none w-25% p-0 box-border"
        style="border: 1px solid var(--el-card-border-color) !important"
        shadow="hover"
        :body-style="{ padding: '8px' }"
      >
        <div class="font-size-14px color-#00000073 m-t-8px">在租合同数</div>
        <div class="font-size-24px line-height-50px color-000000d9">{{
          infoOverview.contractCount || 0
        }}</div>
        <template #footer
          ><div class="font-size-14px c-#000">
            <span class="font-size-12px color-#00000073"> 房源数量: </span>
            {{ infoOverview.housesNum || 0 }}
          </div></template
        >
      </el-card>
      <el-card
        class="!border-rd-none w-25% p-0 box-border"
        style="border: 1px solid var(--el-card-border-color) !important"
        shadow="hover"
        :body-style="{ padding: '8px' }"
      >
        <div class="font-size-14px color-#00000073 m-t-8px">管理面积</div>
        <div class="font-size-24px line-height-50px color-000000d9"
          >{{ infoOverview.managementArea || 0.0 }}㎡</div
        >
        <template #footer
          ><div class="font-size-14px c-#000">
            <span class="font-size-12px color-#00000073"> 建筑面积: </span>
            {{ infoOverview.builtArea || 0.0 }}㎡
          </div></template
        >
      </el-card>
      <el-card
        class="!border-rd-none w-25% p-0 box-border"
        style="border: 1px solid var(--el-card-border-color) !important"
        shadow="hover"
        :body-style="{ padding: '8px' }"
      >
        <div class="font-size-14px color-#00000073 m-t-8px">在租实时均价</div>
        <div class="font-size-24px line-height-50px color-000000d9"
          >{{ infoOverview.averagePrice || 0.0 }}元/m²·天</div
        >
        <template #footer
          ><div class="font-size-14px c-#000">
            <span class="font-size-12px color-#00000073"> 在租面积：</span>
            {{ infoOverview.rentedArea || 0.0 }}m²
          </div></template
        >
      </el-card>
      <el-card
        class="!border-rd-none w-25% p-0 box-border"
        style="border: 1px solid var(--el-card-border-color) !important"
        shadow="hover"
        :body-style="{ padding: '8px' }"
      >
        <div class="font-size-14px color-#00000073 m-t-8px">出租率</div>
        <div class="font-size-24px line-height-50px color-#faad14"
          >{{ infoOverview.rentalRate || 0.0 }}%</div
        >
        <template #footer
          ><div class="font-size-14px c-#000">
            <span class="font-size-12px color-#00000073"> 待租面积：</span>
            {{ infoOverview.waitingArea || 0.0 }}m²
          </div></template
        >
      </el-card>
    </el-col>
    <el-col class="m-t-10px village_Tenant">
      <el-card
        class="!border-rd-4px w-100% box-border font-size-14px"
        style="border: 1px solid #f0f0f0 !important"
        :body-style="{ padding: '8px' }"
        shadow="never"
      >
        <template #header>
          <span class="font-size-12px color-#00000073"> 租客分析 </span>
        </template>
        <el-row>
          <el-col :span="8">
            <div class="Line_title">租客行业</div>
            <el-skeleton :loading="loadCountOwnerAnnularRing" animated>
              <Echart
                :height="100"
                :options="CountOwnerAnnularRingOption"
                v-if="CountOwnerAnnularRingOption.series[0].data.length"
              />
              <el-empty :image-size="80" description="暂无数据" v-else />
            </el-skeleton>
          </el-col>
          <el-col :span="8">
            <div class="Line_title">租客标签</div>
            <el-skeleton :loading="loadCountOwnerTagAnnularRing" animated>
              <Echart
                :height="200"
                :options="CountCountOwnerTagAnnularRing"
                v-if="CountCountOwnerTagAnnularRing.series[0].data.length"
              />
              <el-empty :image-size="80" description="暂无数据" v-else />
            </el-skeleton>
          </el-col>
          <el-col :span="8">
            <div class="Line_title mt-10px">租赁面积排行</div>
            <div v-loading="loadContractTop5">
              <div v-if="tenantList.length">
                <template v-for="(item, index) in tenantList" :key="index">
                  <el-row justify="space-between" class="!flex-nowrap mt-15px" v-if="index < 5">
                    <el-col style="flex: 0 0 40px">
                      <div
                        class="rank_index"
                        :class="
                          index == 0
                            ? 'bg-green'
                            : index == 1
                              ? 'bg-#2681f3'
                              : index == 2
                                ? 'bg-#7546c9'
                                : index == 3
                                  ? 'bg-#faad14'
                                  : 'bg-#ffadd2'
                        "
                        >{{ index + 1 }}</div
                      >
                    </el-col>
                    <el-col
                      class="noWrap c-#2681f3 cursor-pointer"
                      style="flex: 1 1 auto"
                      @click="TenantDetailsBtn(item.ownerId, item.isPersonal)"
                    >
                      <el-tooltip
                        class="box-item"
                        effect="dark"
                        :content="item.ownerName"
                        placement="top"
                      >
                        {{ item.ownerName }}
                      </el-tooltip>
                    </el-col>
                    <el-col class="text-right" style="flex: 0 0 100px">
                      {{ item.leaseArea }} ㎡</el-col
                    >
                  </el-row>
                </template>
              </div>
              <el-empty :image-size="80" description="暂无数据" v-else />
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col class="m-t-10px">
      <el-card
        class="!border-rd-4px w-100% box-border"
        style="border: 1px solid #f0f0f0 !important"
        :body-style="{ padding: '8px' }"
        shadow="never"
      >
        <template #header>
          <span class="font-size-12px color-#00000073"> 租金单价排行 </span>
        </template>
        <el-table
          v-loading="loadContractTop5Price"
          :data="tableData"
          border
          style="width: calc(100% - 20px)"
        >
          <el-table-column type="index" label="排名" width="60" />
          <el-table-column prop="ownerName" label="租客" width="220">
            <template #default="scope"
              ><el-link
                :underline="false"
                type="primary"
                @click="TenantDetailsBtn(scope.row.ownerId, scope.row.isPersonal)"
                >{{ scope.row.ownerName }}</el-link
              ></template
            >
          </el-table-column>
          <el-table-column prop="leaseUnitPrice" label="租赁单价" width="140">
            <template #default="scope">{{ scope.row.leaseUnitPrice }}元/㎡·天</template>
          </el-table-column>
          <el-table-column prop="leaseDay" label="租赁天数" width="100" />
          <el-table-column prop="leaseArea" label="租赁面积" width="100">
            <template #default="scope">{{ scope.row.leaseArea }}㎡</template>
          </el-table-column>
          <el-table-column prop="name" label="资源" width="auto" />
        </el-table>
      </el-card>
    </el-col>
  </el-row>
  <TenantDetails ref="TenantDetailsRef" @select-pick="addGarden" />
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
import TenantDetails from './TenantDetails.vue'
// const loading = ref(false) // 列表的加载中
defineOptions({ name: 'ProjectOverview' })
import { BuildApi, VillageBVO } from '@/api/bus/village'
// import { id } from 'element-plus/es/locale'

import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const loading = ref(false)

const loadCountOwnerAnnularRing = ref(false)
const loadCountOwnerTagAnnularRing = ref(false)
const loadContractTop5 = ref(false)
const loadContractTop5Price = ref(false)
const tenantList = ref([])

const tableData = ref([])

const TenantDetailsRef = ref()
const TenantDetailsBtn = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}

/** 饼图配置 */
const CountOwnerAnnularRingOption = reactive<EChartsOption>({
  legend: {
    orient: 'vertical',
    left: '50%',
    top: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      radius: ['50%', '70%'],
      center: ['22%', '50%'],
      type: 'pie',
      label: {
        show: false
      },
      data: []
    }
  ]
}) as EChartsOption

const CountCountOwnerTagAnnularRing = reactive<EChartsOption>({
  legend: {
    orient: 'vertical',
    left: '50%',
    top: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      radius: ['50%', '70%'],
      center: ['22%', '50%'],
      type: 'pie',
      label: {
        show: false
      },
      data: []
    }
  ]
}) as EChartsOption

const props = defineProps({
  id: {
    type: Number,
    default: null
  },
  level: {
    type: Number,
    default: 1
  },
  villageId: {
    type: Number,
    default: null
  }
})

watch(
  () => props.id,
  (val, old) => {
    if (val) {
      initData()
    }
  }
)

const initData = () => {
  getDetail()
  getCountOwnerAnnularRing()
  getCountCountOwnerTagAnnularRing()
  getContractTop5()
  getContractTop5Price()
}

const infoOverview = ref({
  contractCount: undefined, //在租合同数
  housesNum: undefined, //房源数量
  managementArea: undefined, //管理面积
  averagePrice: undefined, //在租实时均价
  builtArea: undefined, //建筑面积
  rentedArea: undefined, //在租面积
  rentalRate: undefined, //出租率
  waitingArea: undefined //待租面积
})

/** 查询列表 */
const getDetail = async () => {
  loading.value = true
  try {
    const data =
      props.level == 1
        ? await BuildApi.getprojectOverview(props.id)
        : await BuildApi.getBuildProjectBuild({
            id: props.id,
            type: villageTypeValue.value,
            villageId: props.villageId
          })
    infoOverview.value = data
  } catch (e) {
    console.log(e)
  } finally {
    loading.value = false
  }
}

// 租客行业环形图
const getCountOwnerAnnularRing = async () => {
  loadCountOwnerAnnularRing.value = true
  try {
    const data =
      props.level == 1
        ? await BuildApi.getCountOwnerAnnularRing({
            villageId: props.id
          })
        : await BuildApi.getCountOwnerAnnularRing({
            buildId: props.id
          })
    CountOwnerAnnularRingOption.series[0]['data'] = data
  } finally {
    loadCountOwnerAnnularRing.value = false
  }
}
//租客标签
const getCountCountOwnerTagAnnularRing = async () => {
  loadCountOwnerTagAnnularRing.value = true
  try {
    const data =
      props.level == 1
        ? await BuildApi.getCountOwnerTagAnnularRing({
            villageId: props.id
          })
        : await BuildApi.getCountOwnerTagAnnularRing({
            buildId: props.id
          })
    CountCountOwnerTagAnnularRing.series[0]['data'] = data
  } finally {
    loadCountOwnerTagAnnularRing.value = false
  }
}
// 租赁面积排行
const getContractTop5 = async () => {
  loadContractTop5.value = true
  try {
    const data =
      props.level == 1
        ? await BuildApi.getContractTop5({
            parkId: props.id
          })
        : await BuildApi.getContractTop5({
            buildId: props.id
          })
    tenantList.value = data
  } finally {
    loadContractTop5.value = false
  }
}
// 租赁单价排行
const getContractTop5Price = async () => {
  loadContractTop5Price.value = true
  try {
    const data =
      props.level == 1
        ? await BuildApi.getContractTop5Price({
            parkId: props.id
          })
        : await BuildApi.getContractTop5Price({
            buildId: props.id
          })
    tableData.value = data
  } finally {
    loadContractTop5Price.value = false
  }
}
defineExpose({ initData }) // 提供 open 方法，用于打开弹窗
/** 初始化 **/
onMounted(() => {
  if (props.id) {
    initData()
  }
})
</script>
<style lang="scss" scoped>
.el-card ::v-deep .el-card__footer,
.el-card ::v-deep .el-card__header {
  padding: 12px 8px !important;
}

.village_Tenant {
  // .village_Tenant_title {
  //   height: 40px;
  //   width: 100%;
  //   font-size: 14px;
  //   font-weight: bold;
  //   color: #000c;
  //   position: relative;
  //   padding: 0px 10px;
  //   display: flex;
  //   align-items: center;
  // }
  // .village_Tenant_title:before {
  //   position: absolute;
  //   content: '';
  //   left: 0;
  //   top: 12px;
  //   bottom: 12px;
  //   width: 4px;
  //   background-color: #2681f3;
  // }

  .rank_index {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-size: 12px;
  }

  .noWrap {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
}
</style>
