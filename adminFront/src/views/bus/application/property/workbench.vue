<template>
  <div class="container">
    <ContentWrap title="待审批单据">
      <template #header> {{ approvalTotal }} </template>
      <el-scrollbar>
        <div class="flex justify-between">
          <div
            class="item text-center cursor-pointer"
            v-for="(item, index) in pendingApprovalList"
            :key="index"
            @click="toRouter(item)"
          >
            <div class="item-icon w-50px h-50px b-rd-10px m-auto overflow-hidden">
              <img :src="getAssetsFile(item.icon)" class="w-100% h-100%" />
            </div>
            <div class="item-num m-t-4px">
              {{ item.num }}
            </div>
            <div class="item-title m-t-8px text-14px">
              {{ item.title }}
            </div>
          </div>
        </div>
      </el-scrollbar>
    </ContentWrap>
    <el-row :gutter="20">
      <el-col :span="11">
        <ContentWrap title="资产概况" v-loading="properRingLoading">
          <template #header>
            <div class="flex-1 flex justify-end">
              <div class="m-r-20px text-14px">总数量：{{ propertyNum }}</div>
              <el-radio-group v-model="queryParams.type" size="small" @change="getAllProperty">
                <el-radio-button label="数量" value="1" />
                <el-radio-button label="金额" value="2" />
              </el-radio-group>
            </div>
          </template>
          <Echart :height="200" :options="properRingOption" />
          <!-- <el-empty :image-size="80" description="暂无数据" v-else /> -->
        </ContentWrap>
      </el-col>
      <el-col :span="13">
        <ContentWrap title="预警与提醒">
          <template #header> 1 </template>
          <el-scrollbar>
            <div class="flex justify-around min-h-200px">
              <div
                class="item cursor-pointer flex items-center"
                v-for="(item, index) in HightLowList"
                :key="index"
                @click="toRouter(item)"
              >
                <div class="item-icon w-50px h-50px b-rd-10px m-auto overflow-hidden">
                  <img :src="getAssetsFile(item.icon)" class="w-100% h-100%" />
                </div>
                <div class="m-l-12px">
                  <div class="item-num text-20px">
                    {{ item.num }}
                  </div>
                  <div class="item-title m-t-4px text-14px">
                    {{ item.name }}
                  </div>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </ContentWrap>
      </el-col>
    </el-row>

    <ContentWrap title="耗材待审批单据">
      <template #header> {{ stockApprovalTotal }} </template>
      <el-scrollbar>
        <div class="flex justify-between">
          <div
            class="item text-center cursor-pointer"
            v-for="(item, index) in stockPendingApprovalList"
            :key="index"
            @click="toRouter(item)"
          >
            <div class="item-icon w-50px h-50px b-rd-10px m-auto overflow-hidden">
              <img :src="getAssetsFile(item.icon)" class="w-100% h-100%" />
            </div>
            <div class="item-num m-t-4px">
              {{ item.num }}
            </div>
            <div class="item-title m-t-8px text-14px">
              {{ item.title }}
            </div>
          </div>
        </div>
      </el-scrollbar>
    </ContentWrap>
  </div>
</template>

<script setup lang="ts">
import { getAssetsFile } from '@/utils/index'
import { PropertyApi } from '@/api/bus/property/property'
import { PropertyStuffApi } from '@/api/bus/stuff/index'
import { propertyApproveApi } from '@/api/bus/property/propertyApprove'
import { propertyStuffProcessApi } from '@/api/bus/propertyStuffProcess/propertyStuffProcess'
const { push } = useRouter()
defineOptions({ name: 'Workbench' })
const propertyNum = ref() //资产概况总数量
const queryParams = reactive({
  type: 1
})
const properRingLoading = ref(false)
const approvalTotal = ref(0)
const pendingApprovalList = ref([
  {
    key: 'put_depository',
    title: '入库待审批',
    num: 0,
    icon: 'property/approval/borrow.png',
    path: '/application/property/assets/property-depository?status=1'
  },
  {
    key: 'handout_property',
    title: '派发待审批',
    num: 0,
    icon: 'property/approval/transfer.png',
    path: '/application/property/assets/propertyHandout?status=1'
  },
  {
    key: 'return_property',
    title: '退库待审批',
    num: 0,
    icon: 'property/approval/return.png',
    path: '/application/property/assets/propertyHandout?status=1&activeIndex=2'
  },
  {
    key: 'lendout_property',
    title: '借出待审批',
    num: 0,
    icon: 'property/approval/lend.png',
    path: '/application/property/assets/property-lendout?status=1'
  },
  {
    key: 'revert_property',
    title: '归还待审批',
    num: 0,
    icon: 'property/approval/revert.png',
    path: '/application/property/assets/property-lendout?status=1&activeIndex=2'
  },
  {
    key: 'transfer_property',
    title: '调拨待审批',
    num: 0,
    icon: 'property/approval/hire.png',
    path: '/application/property/assets/property-transfer?status=1'
  },
  {
    key: 'change_property',
    title: '变更领用人',
    num: 0,
    icon: 'property/approval/cooperate.png',
    path: '/application/property/assets/property-change?status=1'
  },
  {
    key: 'report_repair_property',
    title: '维修待审批',
    num: 0,
    icon: 'property/approval/repair.png',
    path: '/application/property/assets/property-repair?status=1'
  },
  {
    key: 'property_maintain',
    title: '保养待审批',
    num: 0,
    icon: 'property/approval/handle.png',
    path: '/application/property/assets/property-maintain?status=1'
  }
])
const stockApprovalTotal = ref(0)
const stockPendingApprovalList = ref([
  {
    key: 'stuff_stock_enter',
    title: '耗材入库待审批',
    num: 0,
    icon: 'property/approval/stuff_stock_enter.png',
    path: '/application/property/stuff/enter?status=1'
  },
  {
    key: 'stuff_hand_out',
    title: '耗材派发待审批',
    num: 0,
    icon: 'property/approval/stuff_hand_out.png',
    path: '/application/property/stuff/handOut?status=1'
  },
  {
    key: 'stuff_retreat_out',
    title: '耗材退还待审批',
    num: 0,
    icon: 'property/approval/stuff_retreat_out.png',
    path: '/application/property/stuff/handOut?status=1&activeIndex=2'
  },
  {
    key: 'stuff_transfer',
    title: '耗材调拨待审批',
    num: 0,
    icon: 'property/approval/stuff_transfer.png',
    path: '/application/property/stuff/transfer?status=1'
  },
  {
    key: 'stuff_adjust',
    title: '耗材调整待审批',
    num: 0,
    icon: 'property/approval/stuff_adjust.png',
    path: '/application/property/stuff/adjust?status=1'
  }
])
const properRingOption = ref({
  legend: {
    orient: 'vertical',
    right: '0%',
    top: 'center',
    //格式化图例文本
    formatter(name) {
      let count = 0
      properRingOption.value.series[0].data.forEach((item) => {
        count = count + item.value
      })
      const val = properRingOption.value.series[0].data.filter((item) => {
        return item.name === name
      })
      // 确保 count 是有效的数字
      if (typeof count === 'number' && count !== 0) {
        const percentage = (val[0].value / count) * 100
        return name + '  ' + percentage.toFixed(2) + '%'
      } else {
        return name
      }
    }
  },
  title: {
    text: '总计',
    subtext: propertyNum.value,
    textStyle: {
      fontSize: 18,
      color: 'rgba(0,0,0,0.65)',
      fontWeight: 600
    },
    subtextStyle: {
      fontSize: 18,
      color: '#000000',
      fontWeight: 500
    },
    left: 'center',
    top: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '<br/>{b}: {c} ({d}%)'
  },
  color: ['#5B8FF9', '#62DAAB', '#657798', '#F7C122'],
  series: [
    {
      type: 'pie',
      radius: ['50%', '70%'],
      // center: ['22%', '50%'],
      label: {
        // show: false
        formatter: '{b}: {d}%'
      },
      data: [
        {
          value: 0,
          name: '空闲'
        },
        {
          value: 0,
          name: '在用'
        },
        {
          value: 0,
          name: '借用'
        },
        {
          value: 0,
          name: '审批中'
        }
      ]
    }
  ]
})
const HightLowList = ref([
  {
    name: '高于安全库存的物料',
    num: 0,
    icon: 'property/approval/stock_up.png',
    path: '/application/property/stuff/stok'
  },
  {
    name: '低于安全库存的物料',
    num: 0,
    icon: 'property/approval/stock_lower.png',
    path: '/application/property/stuff/stok'
  }
])
/** 资产概况 */
const getAllProperty = async () => {
  try {
    properRingLoading.value = true
    let data = await PropertyApi.getAllProperty(queryParams)
    propertyNum.value = 0
    properRingOption.value.series[0].data.forEach((item, index) => {
      item.value = data[index + 1] || 0
      propertyNum.value = item.value + propertyNum.value
    })
    properRingOption.value.title.subtext = propertyNum.value
  } finally {
    properRingLoading.value = false
  }
}
/** 预警与提醒 */
const getHightLow = async () => {
  let data = await PropertyStuffApi.getHightLow()
  HightLowList.value[0].num = data.height || 0
  HightLowList.value[1].num = data.low || 0
}
/** 资产待审批数据 */
const getApproveCount = async () => {
  let data = await propertyApproveApi.getApproveCount()
  if (data && JSON.stringify(data) != '{}')
    pendingApprovalList.value.forEach((item: any) => {
      item.num = data[item.key] ? data[item.key] : 0
      approvalTotal.value = approvalTotal.value + item.num
    })
}
/** 耗材待审批 */
const getProcessCount = async () => {
  let data = await propertyStuffProcessApi.getProcessCount()
  if (data && JSON.stringify(data) != '{}')
    stockPendingApprovalList.value.forEach((item: any) => {
      item.num = data[item.key] ? data[item.key] : 0
      stockApprovalTotal.value = stockApprovalTotal.value + item.num
    })
}
/** 路由跳转 */
const toRouter = (item: any) => {
  push(item.path)
}
onActivated(async () => {
  await getApproveCount()
  await getProcessCount()
  await getAllProperty()
  await getHightLow()
})
onMounted(async () => {
  await getApproveCount()
  await getProcessCount()
  await getAllProperty()
  await getHightLow()
})
// TypeScript component logic
</script>
