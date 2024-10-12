<template>
  <ContentWrap>
    <el-row justify="space-around" align="middle">
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        {{ TopNumCount?.overdueNumber }}
        <div class="c-#00000073 font-size-14px m-b-5px">总逾期收款单数</div>
      </el-col>
      <el-divider direction="vertical" class="!h-40px" />
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        {{ TopNumCount?.receivable }}
        <div class="c-#00000073 font-size-14px m-b-5px">总逾期收款金额</div>
      </el-col>
      <el-divider direction="vertical" class="!h-40px" />
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        {{ TopNumCount?.ownerCount }}
        <div class="c-#00000073 font-size-14px m-b-5px">逾期租客数</div>
      </el-col>
      <el-divider direction="vertical" class="!h-40px" />
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        {{ TopNumCount?.lateFee }}
        <div class="c-#00000073 font-size-14px m-b-5px">滞纳金金额</div>
      </el-col>
    </el-row>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <div class="flex justify-between flex-items-center mb-20px">
      <el-form
        ref="queryFormRef"
        :model="queryParams"
        label-position="left"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="费用类型" prop="feeType">
          <el-select
            v-model="queryParams.feeType"
            placeholder="请选择费用类型"
            clearable
            class="!w-200px"
          >
            <el-option
              v-for="dict in feeTypeList"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
            type="date"
            v-model="queryParams.payStartDate"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            placeholder="选择日期"
            clearable
            class="!w-200px"
          />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            type="date"
            v-model="queryParams.payEndDate"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            placeholder="选择日期"
            clearable
            class="!w-200px"
          />
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            type="daterange"
            v-model="createTime"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            placeholder="选择日期"
            clearable
            @change="getDateRangeCreate"
            class="!w-200px"
          />
        </el-form-item>
        <el-form-item label="结清状态">
          <el-select
            v-model="queryParams.billStatus"
            placeholder="请选择"
            clearable
            class="!w-200px"
          >
            <el-option
              v-for="item in getIntDictOptions(DICT_TYPE.BILL_STATUS)"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="应收时间">
          <el-date-picker
            type="daterange"
            v-model="payDate"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            placeholder="选择日期"
            clearable
            @change="getDateRange"
            class="!w-200px"
          />
        </el-form-item>
        <el-form-item label="项目名称">
          <el-select v-model="queryParams.parkIdList" class="!w-200px" multiple>
            <el-option
              v-for="(item, index) in projectList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="楼宇名称">
          <el-select v-model="queryParams.billidList" class="!w-200px" multiple>
            <el-option
              v-for="(item, index) in buildingList"
              :key="index"
              :label="item.buildName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="租客名称" prop="ownerName">
          <el-input placeholder="请填写租客名称" v-model="queryParams.ownerName" class="!w-200px"
        /></el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      row-key="id"
      @row-click="OrderDetail"
    >
      <el-table-column label="对方名称" prop="ownerName" width="200px" fixed>
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click.stop="OwnerDetail(scope.row.ownerId, scope.row.isPersonal)"
            >{{ scope.row.ownerName }}</el-button
          >
        </template>
      </el-table-column>
      <el-table-column label="楼宇名称" prop="buildName" width="140px" />
      <el-table-column label="合同编号" prop="contractNumber" width="140px">
        <template #default="scope">
          <el-button link type="primary" @click.stop="handleContractDetail(scope.row.contractId)">{{
            scope.row.contractNumber
          }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="结清状态" prop="billStatusName" width="100" />
      <el-table-column label="账单类型" prop="billTypeName" width="100" />
      <el-table-column label="费用类型" prop="feeTypeName" width="140px" />
      <el-table-column label="租赁数" prop="leaseArea" width="140px" />
      <el-table-column label="账单来源" prop="billSourceName" width="140px" />
      <el-table-column label="应付金额" prop="receivable" width="140px" />
      <el-table-column label="实付金额" prop="receivablePayment" width="140px" />
      <el-table-column label="税额" prop="taxAmount" width="140px" />
      <el-table-column label="开始日期" prop="payStartDate" width="140px" />
      <el-table-column label="结束日期" prop="payEndDate" width="140px" />
      <el-table-column label="缴费通知单" prop="noticeCount" width="140px" />
      <el-table-column label="操作" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="handleCollectionForm(scope.row)"> 催缴 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
  <TenantDetails ref="TenantDetailsRef" @select-pick="getList" />
  <ContractDetail ref="ContractDetailRef" @select-pick="getList" />
  <CheckOrderDetail ref="CheckOrderDetailRef" />
  <collectionForm ref="collectionFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { BillApi } from '@/api/bus/bill/index'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import ContractDetail from '../../../../contract/contractList/contractDetail_drawer.vue'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import CheckOrderDetail from '@/views/bus/owner/component/checkOrderDetail.vue'
import collectionForm from '@/views/bus/owner/component/collectionForm.vue'
/** 账单收据编号规则 列表 */
defineOptions({ name: 'OverrdueAccount' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const payDate = ref()
const createTime = ref()
const loading = ref(true) // 列表的加载中
const list = ref<[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  feeType: undefined,
  ownerName: undefined,
  startCreateTime: undefined,
  endCreateTime: undefined,
  endPayDate: undefined,
  startPayDate: undefined,
  payStartDate: undefined,
  payEndDate: undefined,
  billStatus: undefined,
  adjustType: undefined,
  parkIdList: undefined,
  billidList: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const TopNumCount = ref<any>({})

const props = defineProps<{
  feeTypeList: Array<any>
  projectList: Array<any>
  buildingList: Array<any>
}>()
/** 查询顶部数据 */
const getTopNumCount = async () => {
  const data = await BillApi.getBillBeOverdueTotalMoney({ billType: 1 })
  TopNumCount.value = data
}

const ContractDetailRef = ref()
const handleContractDetail = async (id: number) => {
  ContractDetailRef.value.open(id)
}

const TenantDetailsRef = ref()
const OwnerDetail = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}

/**生成缴费通知单 */
const collectionFormRef = ref()
const handleCollectionForm = async (row) => {
  collectionFormRef.value.open(
    { buildName: row.buildName },
    { id: row.ownerId, contractId: row.contractId },
    { id: row.billId, ...row }
  )
}

const getDateRangeCreate = (row: any) => {
  if (row.length) {
    queryParams.startCreateTime = row[0]
    queryParams.endCreateTime = row[1]
  } else {
    queryParams.startCreateTime = undefined
    queryParams.endCreateTime = undefined
  }
}
const getDateRange = (row: any) => {
  if (row.length) {
    queryParams.startPayDate = row[0]
    queryParams.endPayDate = row[1]
  } else {
    queryParams.startPayDate = undefined
    queryParams.endPayDate = undefined
  }
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    let params = JSON.parse(JSON.stringify({ billType: 1, ...queryParams }))
    // params.parkIdList = params.parkIdList.join(',')
    // params.billidList = params.billidList.join(',')
    const data = await BillApi.getBillBeOverdueListPage(params)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

const CheckOrderDetailRef = ref()
/** 财务账单详情 */
const OrderDetail = (row: any) => {
  CheckOrderDetailRef.value.open(row.billId)
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 初始化 **/
onMounted(() => {
  getTopNumCount()
  getList()
})
</script>
