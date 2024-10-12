<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div v-loading="OwnerLoading">
    <ContentWrap class="m-10px">
      <template v-if="ownerId">
        <div class="flex justify-between items-center">
          <div class="c-#000000a6 fw-600 font-size-15px">{{ ownerInfo?.name }}</div>
          <div
            class="c-#2681f3 hover-c-#52a5ff font-size-14px cursor-pointer"
            @click="TenantDetailsBtn(ownerInfo.id, ownerInfo.isPersonal)"
            >租客详情</div
          >
        </div>
        <div class="flex justify-between m-t-20px">
          <el-card
            class="!border-rd-none w-33% p-0 box-border"
            style="border: 1px solid var(--el-card-border-color) !important"
            shadow="hover"
            :body-style="{ padding: '8px' }"
          >
            <div class="font-size-14px color-#00000073 m-t-8px">逾期账单数</div>
            <div
              class="font-size-24px line-height-50px color-000000d9"
              v-if="infoOverview.beOverdueCount"
              >{{ infoOverview?.beOverdueCount.length }}</div
            >
            <div class="font-size-24px line-height-50px color-000000d9" v-else>0</div>
            <template #footer
              ><div class="font-size-14px">
                <el-button
                  type="primary"
                  size="small"
                  class="!p-0"
                  @click.stop="collection(infoOverview.beOverdueCount)"
                  text
                  >一键发送通知单</el-button
                >
              </div></template
            >
          </el-card>
          <el-card
            class="!border-rd-none w-33% p-0 box-border"
            style="border: 1px solid var(--el-card-border-color) !important"
            shadow="hover"
            :body-style="{ padding: '8px' }"
          >
            <div class="font-size-14px color-#00000073 m-t-8px">累计流水</div>
            <div class="font-size-24px line-height-50px color-000000d9">{{
              infoOverview.amount || 0.0
            }}</div>
            <template #footer
              ><div class="font-size-14px c-#000">
                <span class="font-size-12px color-#00000073"> 未匹配流水: </span>
                ￥{{ infoOverview.nomatchPrice || 0 }}
              </div></template
            >
          </el-card>
          <el-card
            class="!border-rd-none w-33% p-0 box-border"
            style="border: 1px solid var(--el-card-border-color) !important"
            shadow="hover"
            :body-style="{ padding: '8px' }"
          >
            <div class="font-size-14px color-#00000073 m-t-8px">需收金额</div>
            <div class="font-size-24px line-height-50px color-000000d9">{{
              infoOverview.amountCollected || 0
            }}</div>
            <template #footer
              ><div class="font-size-14px c-#000">
                <span class="font-size-12px color-#00000073"> 应收金额：</span>
                ￥{{ infoOverview.receivable || 0 }}
              </div></template
            >
          </el-card>
        </div>
        <el-card
          class="m-t-20px"
          shadow="never"
          style="border: 1px solid var(--el-card-border-color) !important"
        >
          <template #header> 财务记录</template>
          <el-form :inline="true" ref="form" :model="FinanceForm" label-width="80px">
            <el-form-item label="年份" prop="year">
              <el-date-picker
                v-model="FinanceForm.year"
                type="year"
                placeholder="请选择年份"
                value-format="YYYY"
                class="!w-240px"
                :clearable="false"
              />
            </el-form-item>
            <el-form-item label="月份" prop="month">
              <el-select
                v-model="monthArray"
                placeholder="请选择月份"
                clearable
                multiple
                class="!w-240px"
              >
                <el-option v-for="item in 12" :key="item" multiple :label="item" :value="item" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button @click="handleQuery" type="primary"
                ><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button
              >
            </el-form-item>
          </el-form>
          <el-table :data="FinanceList" border>
            <el-table-column label="账单编号" prop="id" width="100" />
            <el-table-column label="应收时间" prop="payDateStr" />
            <el-table-column label="费用类型" prop="feeType">
              <template #default="scope">
                <template v-for="(item, index) in costTypeChildrenList" :key="index">
                  <template v-for="(item1, index1) in item.children" :key="index1">
                    <span v-if="item1.value == scope.row.feeType">{{ item1.label }}</span>
                  </template>
                </template>
              </template>
            </el-table-column>
            <el-table-column label="账单状态" prop="billStatus" />
            <el-table-column label="应收金额" prop="receivable" />
          </el-table>
          <Pagination
            :total="FinanceTotal"
            v-model:page="FinanceForm.pageNo"
            v-model:limit="FinanceForm.pageSize"
            @pagination="ownerFinanceList"
          />
        </el-card>
      </template>
      <el-empty :image-size="80" description="暂无数据" v-else />
    </ContentWrap>
    <!-- <div class="m-10px m-t-20px" v-if="ownerId">
    </div> -->
  </div>
  <collectionForm ref="collectionFormRef" @success="initData" />
  <TenantDetails ref="TenantDetailsRef" @select-pick="getOwnerInfo" />
</template>
<script setup lang="ts">
/** 租客账单 */
defineOptions({ name: 'OwnerStreamMiddle' })
import { formatDate } from '@/utils/formatTime'
import { OwnerApi } from '@/api/bus/owner'
import collectionForm from '@/views/bus/owner/component/collectionForm.vue'
import { contractOrderApi } from '@/api/bus/contractOrderBill'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { id } from 'element-plus/es/locale'
const props = defineProps({
  roomId: {
    type: Number,
    default: 0
  },
  info: {
    type: Object,
    default: () => {}
  }
})
const monthArray = ref([])
const roomId = ref(props.roomId)
const info = ref(props.info)
const ownerId = ref('')
const OwnerLoading = ref(false)
const infoOverview = ref({})

const FinanceList = ref([])
const FinanceForm = reactive({
  year: formatDate(new Date(), 'YYYY'),
  pageNo: 1,
  pageSize: 10
})
const FinanceLoading = ref(false)
const FinanceTotal = ref(0)

const TenantDetailsRef = ref()
const TenantDetailsBtn = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}
const costTypeChildrenList = ref([])
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}

/** 催收 */
const collectionFormRef = ref()
const collection = async (items) => {
  console.log(info.value)
  let list = []
  items.forEach((item) => {
    list.push({
      id: item
    })
  })
  collectionFormRef.value.open(info.value, ownerInfo.value, { data: list })
}

const ownerInfo = ref({})

const ownerFinanceList = async () => {
  try {
    FinanceLoading.value = true

    const data = await contractOrderApi.ownerFinanceList({
      roomId: roomId.value,
      ownerId: ownerId.value,
      month: monthArray.value.join(','),
      ...FinanceForm
    })
    FinanceList.value = data.list
    FinanceTotal.value = data.total
  } finally {
    FinanceLoading.value = false
  }
}

const getOwnerIdOverdueIds = async () => {
  try {
    const data = await contractOrderApi.getOwnerIdOverdueIds({
      roomId: roomId.value,
      ownerId: ownerId.value
    })
    infoOverview.value = data
  } finally {
  }
}

const initData = () => {
  getOwnerIdOverdueIds()
}

const getOwnerInfo = async () => {
  OwnerLoading.value = true
  try {
    const data = await OwnerApi.getByRoomIdOwnerList(roomId.value)
    if (data.length) {
      ownerInfo.value = data[0]
      ownerId.value = data[0].id
      getOwnerIdOverdueIds()
      ownerFinanceList()
    }
  } finally {
    OwnerLoading.value = false
  }
}
/** 搜索按钮操作 */
const handleQuery = () => {
  FinanceForm.pageNo = 1
  ownerFinanceList()
}
watch(
  () => props.roomId,
  (val) => {
    if (JSON.stringify(val)) {
      console.log('更新', val)
      getOwnerInfo()
      // activeIndex.value = Number(props.active_type)
    }
  },
  {
    immediate: true,
    deep: true
  }
)
onMounted(() => {
  getCostTypeChildrenList()
})
</script>
<style lang="css" scoped></style>
