<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 筛选 -->
  <div class="bg-#fff p-18px rounded">
    <el-form label-position="top">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="所属项目">
            <el-select v-model="queryParams.villageId">
              <el-option
                v-for="item in VillageList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所属楼宇">
            <el-select v-model="queryParams.buildId">
              <el-option
                v-for="item in BuildList"
                :key="item.id"
                :label="item.buildName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="租客名称">
            <el-input placeholder="请输入" v-model="queryParams.ownerName" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="订单编号">
            <el-input placeholder="请输入" v-model="queryParams.orderNumber" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="订单状态">
            <el-select v-model="queryParams.orderStatus">
              <el-option
                v-for="(item, index) in getIntDictOptions('WORK_ORDER_PAID_STATUS')"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="工单分类">
            <el-select v-model="queryParams.categoryId">
              <el-option
                v-for="(item, index) in CategoryList"
                :key="index"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="支付时间">
            <el-date-picker
              type="daterange"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              v-model="payTime"
              @change="changePayTime"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="订单类型">
            <el-select v-model="queryParams.orderType">
              <el-option
                v-for="(item, index) in getIntDictOptions('WORL_ORDER_TYPE')"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="负责部门">
            <el-tree-select
              v-model="queryParams.departmentId"
              :data="departmentOptions"
              :props="defaultProps"
              placeholder="请选择负责部门"
              clearable
              :check-strictly="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="生成付款单">
            <el-select v-model="queryParams.generateStatus">
              <el-option
                v-for="(item, index) in getIntDictOptions('WORK_ORDER_GENERATE_STATUS')"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <div class="flex h-100% items-center transform-translate-y-6px">
            <el-button type="primary" @click="getList">搜索</el-button>
            <el-button @click="reset">重置</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>
  </div>
  <!-- 统计 -->
  <div class="flex justify-between bg-#fff mt-16px h-100px rounded">
    <div class="text-center w-25% h-100% flex flex-col justify-evenly">
      <div class="text-13px color-#8C8C8C">工单费用</div>
      <div class="color-#409EFF font-600 text-24px"
        >￥{{ StatisticsList.find((item) => item.key == 'orderPrice')?.value }}</div
      >
    </div>
    <div class="jiange"></div>
    <div class="text-center w-25% h-100% flex flex-col justify-evenly">
      <div class="text-13px color-#8C8C8C">工单退款费用</div>
      <div class="color-#409EFF font-600 text-24px"
        >￥{{ StatisticsList.find((item) => item.key == 'orderRefundPrice')?.value }}</div
      >
    </div>
    <div class="jiange"></div>

    <div class="text-center w-25% h-100% flex flex-col justify-evenly">
      <div class="text-13px color-#8C8C8C">工单订单数</div>
      <div class="color-#409EFF font-600 text-24px">{{
        StatisticsList.find((item) => item.key == 'orderNum')?.value
      }}</div>
    </div>
    <div class="jiange"></div>
    <div class="text-center w-25% h-100% flex flex-col justify-evenly">
      <div class="text-13px color-#8C8C8C">工单退款订单数</div>
      <div class="color-#409EFF font-600 text-24px">{{
        StatisticsList.find((item) => item.key == 'orderRefundNum')?.value
      }}</div>
    </div>
  </div>
  <!-- 表格 -->
  <ContentWrap class="mt-16px">
    <div class="flex justify-between items-center">
      <div class="flex gap-20px color-#262626 text-14px my-10px ml-10px mb-20px">
        <span :class="active == 0 ? 'active' : 'unactive'" @click="AllList()">全部订单</span>
        <span :class="active == 1 ? 'active' : 'unactive'" @click="refundList()">已退款订单</span>
      </div>
      <el-button type="primary" @click="payment" v-hasPermi="['bus:workOrderProposeOrder:scanCodeBillPay']"
        >支付</el-button
      >
    </div>
    <el-table :data="tableData" v-loading="loading" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="orderNumber" label="订单编号" width="260px" align="center" fixed>
        <template #default="scope">
          <el-button type="primary" text @click="openDetail(scope.row)">{{
            scope.row.orderNumber
          }}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="orderType" label="订单类型" width="200px" align="center">
        <template #default="scope">
          <span
            class="bg-#13CCA0 color-#fff rounded px-8px py-4px"
            v-if="scope.row.orderType == 3"
            >{{
              getIntDictOptions('WORL_ORDER_TYPE').find((item) => item.value == scope.row.orderType)
                ?.label
            }}</span
          >
          <span
            class="bg-#409EFF color-#fff rounded px-8px py-4px"
            v-if="scope.row.orderType == 2"
            >{{
              getIntDictOptions('WORL_ORDER_TYPE').find((item) => item.value == scope.row.orderType)
                ?.label
            }}</span
          >
          <span
            class="bg-#FFD12D color-#fff rounded px-8px py-4px"
            v-if="scope.row.orderType == 2"
            >{{
              getIntDictOptions('WORL_ORDER_TYPE').find((item) => item.value == scope.row.orderType)
                ?.label
            }}</span
          >
        </template>
      </el-table-column>
      <el-table-column prop="workOrderNumber" label="工单编号" width="280px" align="center">
        <template #default="scope">
          <el-button type="primary" text @click="openWorkDetail(scope.row)">{{
            scope.row.workOrderNumber
          }}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="villageName"
        label="项目名称"
        width="200px"
        align="center"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        prop="buildName"
        label="楼宇名称"
        width="200px"
        align="center"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        prop="ownerName"
        label="租客名称"
        width="200px"
        align="center"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column prop="roomName" label="房号" width="200px" align="center" />
      <el-table-column prop="orderStatus" label="订单状态" width="200px" align="center">
        <template #default="scope">
          <span>{{
            getIntDictOptions('WORK_ORDER_PAID_STATUS').find(
              (item) => item.value == scope.row.orderStatus
            )?.label
          }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="generateStatus" label="生成付款单" width="200px" align="center">
        <template #default="scope">
          <span>{{
            getIntDictOptions('WORK_ORDER_GENERATE_STATUS').find(
              (item) => item.value == scope.row.generateStatus
            )?.label || '--'
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="工单分类"
        width="200px"
        align="center"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column prop="paymentAmount" label="支付金额" width="200px" align="center">
        <template #default="scope">
          <span>{{ scope.row.paymentAmount + '元' || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="paymentTime"
        label="支付时间"
        width="200px"
        align="center"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column prop="refundAmount" label="退款金额" width="200px" align="center">
        <template #default="scope">
          <span>{{ scope.row.refundAmount + '元' || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="refundTime"
        label="退款时间"
        width="200px"
        align="center"
        :formatter="tableColumnEmptyPlaceholder"
      />
    </el-table>
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 订单详情 -->
  <detailForm ref="detailFormRef" />
  <!-- 工单详情 -->
  <WorkDetailForm ref="WorkDetailFormRef" />
</template>
<script lang="ts" setup>
import { getIntDictOptions } from '@/utils/dict'
import { BuildApi } from '@/api/bus/village/building'
import { Api } from '@/api/contract/contractList/index'
import { workOrderProposeOrderApi } from '@/api/bus/Category/Orderrecord/index.ts'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { CategoryApi } from '@/api/bus/Category/category/index'
import detailForm from './components/detailForm.vue'
import { defaultProps, handleTree } from '@/utils/tree'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'
import WorkDetailForm from '../workOrderList/components/detailForm.vue'
import { values } from 'lodash-es'
const application = ref('')
const message = useMessage() // 消息弹窗
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
const tableData = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  application: 'WORK_ORDER_REPAIR'
})
const total = ref(0)
const getList = async () => {
  loading.value = true
  queryParams.application = application.value
  const data = await workOrderProposeOrderApi.getPage(queryParams)
  tableData.value = data.list
  tableData.value.forEach((item) => {
    item.buildName = BuildList.value.find((build) => build.id == item.buildId)?.buildName
    item.villageName = VillageList.value.find((village) => village.id == item.villageId)?.name
  })
  total.value = data.total
  loading.value = false
}
const payTime = ref([])
const changePayTime = (val) => {
  if (val == null) {
    queryParams.startPaymentTime = ''
    queryParams.endPaymentTime = ''
    return
  }
  queryParams.startPaymentTime = val[0]
  queryParams.endPaymentTime = val[1]
}
//打开详情
const detailFormRef = ref()
const openDetail = (row) => {
  detailFormRef.value.open(row.id)
}
//打开工单详情
const WorkDetailFormRef = ref()
const openWorkDetail = (row) => {
  WorkDetailFormRef.value.open(row.workorderId, row)
}
//获得工单统计
const StatisticsList = ref([])
const getWorkOrderStatistics = async () => {
  delete queryParams.pageNo
  delete queryParams.pageSize
  queryParams.application = application.value
  const data = await workOrderProposeOrderApi.getOrderStatistics(queryParams)
  StatisticsList.value = data
}
//获得项目列表
const VillageList = ref([])
const getVillage = async () => {
  const data = await Api.getVillageList()
  VillageList.value = data
}
//获得楼宇列表
const BuildList = ref([])
const getBuild = async () => {
  const data = await BuildApi.getBuildingList()
  BuildList.value = data
}
//获得分类列表
const CategoryList = ref([])
const getCategory = async () => {
  const BuildListAll = BuildList.value.map((item) => item.id)
  const data = await CategoryApi.getList({
    application: application.value,
    buildBind: BuildListAll.join(',')
  })
  CategoryList.value = data
}
///部门下拉数据
const departmentOptions = ref([])
const GetdepartmentOptions = async () => {
  const data = await patrolPlanEquipmentApi.getDeptList()
  departmentOptions.value.push(...handleTree(data))
}
const loading = ref(false)
const reset = () => {
  queryParams.villageId = ''
  queryParams.buildId = ''
  queryParams.ownerName = ''
  queryParams.orderNumber = ''
  queryParams.orderStatus = ''
  queryParams.categoryId = ''
  queryParams.orderType = ''
  queryParams.departmentId = ''
  queryParams.generateStatus = ''
  queryParams.startPaymentTime = ''
  queryParams.endPaymentTime = ''
  queryParams.pageNo = 1
  queryParams.pageSize = 10
  getList()
}
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
  multipleSelection.value = val
}
const payment = () => {
  if (multipleSelection.value.length === 0) {
    message.warning('请选择要支付的订单')
    return
  }
  const ids = multipleSelection.value.map((item) => item.id)
  workOrderProposeOrderApi.scanCodeBillPay({ ids: ids.join(',') }).then((res) => {})
}
const AllList = () => {
  active.value = 0
  queryParams.orderStatus = ''
  getList()
}
const active = ref(0)
const refundList = () => {
  active.value = 1
  queryParams.orderStatus = 3
  getList()
}
onMounted(async () => {
  await getapplication()
  await getVillage()
  await getBuild()
  getList()
  getCategory()
  GetdepartmentOptions()
  getWorkOrderStatistics()
})
</script>
<style scoped>
.jiange {
  width: 1px;
  height: 80%;
  background-color: #e8e8e8;
  margin: 10px 0;
}
.active {
  color: #1990ff;
  font-weight: 600;
  padding-bottom: 5px;
  border-bottom: 2px solid #1990ff;
  cursor: pointer;
}
.unactive {
  color: #8c8c8c;
  font-weight: 600;
  padding-bottom: 5px;
  border-bottom: 2px solid #fff;
  cursor: pointer;
}
</style>
