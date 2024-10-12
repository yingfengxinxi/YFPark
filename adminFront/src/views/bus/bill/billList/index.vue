<template>
  <!-- <ContentWrap>
    <div class="flex justify-end">
      <el-dropdown>
        <span class="m-r-14px"
          ><el-button type="primary">
            <Icon icon="ep:plus" color="#fff" class="m-r-6px" />
            账单
          </el-button></span
        >
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>
              <el-button
                text
                @click="AddCollectionBill"
                v-hasPermi="['bus:orgBillCostCategory:delete']"
                >添加收款账单</el-button
              >
            </el-dropdown-item>
            <el-dropdown-item>
              <el-button
                text
                @click="AddPaymentInvoice"
                v-hasPermi="['bus:orgBillCostCategory:delete']"
                >添加付款账单</el-button
              >
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-dropdown>
        <span
          ><el-button plain>
            <Icon icon="ep:arrow-down" color="#fff" class="m-r-6px" />
            更多
          </el-button></span
        >
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>
              <el-button class="m-l-10px" @click="Image_show = true"> 导入 </el-button>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-button text @click="handleApproval">审批配置</el-button>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </ContentWrap> -->
  <!-- 筛选字段 -->
  <ContentWrap v-if="!addContract && !detailVisible">
    <!-- <div class="flex-justify-between flex">
      <div></div>
      <div
        class="flex flex-justify-center"
        style="user-select: none; min-width: 100px"
        @click="filterShow = !filterShow"
      >
        <Icon v-if="filterShow" icon="ep:arrow-up" class="pt-0.5 mr-5px" />
        <Icon v-if="!filterShow" icon="ep:arrow-down" class="pt-0.5 mr-5px" />
        {{ filterShow ? '收起' : '展开' }}
      </div>
    </div>
      v-show="filterShow" -->

    <SearchBar
      @search="search_data"
      @image_show="Image_show = true"
      @approval_show="handleApproval"
      @add-collection-bill="AddCollectionBill"
      @add-payment-invoice="AddPaymentInvoice"
      :feeTypeList="feeTypeList"
      :dictType="'Bill_status'"
    />
  </ContentWrap>
  <ContentWrap>
    <el-tabs
      v-model="activeIndex"
      tab-position="left"
      @tab-change="getTopList"
      class="demo-tabs lh-30px"
    >
      <el-tab-pane label="收款" name="1">
        <el-row :gutter="10" class="p-10px" justify="space-between">
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px"
              >应收（{{ topList.receivableCount || 0 }}笔）</div
            >
            <div class="c-#000000d9 font-size-22px">{{ topList.receivable || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px">滞纳金</div>
            <div class="c-#000000d9 font-size-22px">{{ topList.lateFee || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px">实收</div>
            <div class="c-#000000d9 font-size-22px">{{ topList.receivablePayment || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px">调增</div>
            <div class="c-#000000d9 font-size-22px">{{ Number(topList.jiaAdjustPrice) || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px">调减</div>
            <div class="c-#000000d9 font-size-22px">{{ Number(topList.jianAdjustPrice) || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px"
              >需收（{{ topList.collectedCount || 0 }}笔）</div
            >
            <div class="c-#000000d9 font-size-22px">{{ topList.collectedPrice || 0 }}</div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="付款" name="2">
        <el-row :gutter="10" class="p-10px" justify="space-between">
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px"
              >应付（{{ topList.receivableCount || 0 }}笔）</div
            >
            <div class="c-#000000d9 font-size-22px">{{ topList.receivable || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px">滞纳金</div>
            <div class="c-#000000d9 font-size-22px">{{ topList.lateFee || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px">实付</div>
            <div class="c-#000000d9 font-size-22px">{{ topList.receivablePayment || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px">调增</div>
            <div class="c-#000000d9 font-size-22px">{{ Number(topList.jiaAdjustPrice) || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px">调减</div>
            <div class="c-#000000d9 font-size-22px">{{ Number(topList.jianAdjustPrice) || 0 }}</div>
          </el-col>
          <el-col :span="4">
            <div class="c-#00000073 font-size-14px"
              >需付（{{ topList.collectedCount || 0 }}笔）</div
            >
            <div class="c-#000000d9 font-size-22px">{{ topList.collectedPrice || 0 }}</div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>
  <ContentWrap>
    <div class="flex justify-between">
      <el-tabs
        v-model="activeShow"
        tab-position="top"
        @tab-change="getList"
        class="demo-tabs lh-30px w-200px !border-0"
      >
        <el-tab-pane label="收款账单" name="1" />
        <el-tab-pane label="付款账单" name="2" />
      </el-tabs>
      <div class="flex items-center">
        <!-- 只添加了el-popover这些代码 -->
        <el-popover
          placement="bottom"
          title="表格配置"
          :width="200"
          trigger="click"
          @hide="handlePopoverHide"
        >
          <el-scrollbar height="300px">
            <div class="flex justify-between">
              <el-checkbox v-model="all" @change="allChange" label="全部" />
              <el-button type="primary" text @click="RestoreDefault">恢复默认</el-button>
            </div>
            <div v-for="(item, index) in columns" :key="index">
              <el-checkbox v-model="item.show" :label="item.label" :disabled="item.disabled" />
            </div>
          </el-scrollbar>
          <template #reference>
            <el-button>
              <Icon icon="ep:calendar" />
            </el-button>
          </template>
        </el-popover>
        <div class="p-l-10px p-r-10px bg-#f2f2f2 b-rd-20px flex items-center m-l-20px">
          <el-switch
            v-model="isShow"
            inline-prompt
            style="--el-switch-on-color: #2681f3; --el-switch-off-color: #00000040"
            active-text="显示"
            inactive-text="隐藏"
            @change="getList"
          />
          <div class="p-8px p-t-0px p-b-0px font-size-12px">{{
            isShow == true ? '已显示未到应收期账单' : '已隐藏未到应收期账单'
          }}</div>
        </div>
        <el-button plain class="m-l-30px" :loading="exportLoading" @click="exportBill"
          >导出</el-button
        >
      </div>
    </div>
    <div v-if="chooseLit.length" class="flex justify-between items-center m-b-10px">
      <el-alert
        :title="'已选择' + chooseLit.length + '项' + ' 总计需收：' + totalMoney + '元'"
        type="info"
        :closable="false"
        class="custom-alert flex-1"
      />
      <div class="m-l-30px">
        <el-button type="primary" plain @click="addOrgBillReceipt">开据收据</el-button>
        <el-button type="primary" plain>生成开票信息</el-button>
        <el-button type="primary" plain @click="handleCollectionForm">生成缴费通知单</el-button>
      </div>
    </div>
    <el-table
      :data="List"
      border
      @selection-change="handleSelectionChange"
      @row-click="toggleRowExpansion"
    >
      <el-table-column type="selection" width="55" fixed="left" />
      <el-table-column
        label="对方名称"
        width="160px"
        prop="ownerName"
        v-if="showColumn('ownerName')"
        fixed="left"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="楼宇名称"
        width="160px"
        prop="buildName"
        v-if="showColumn('buildName')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="合同编号"
        width="160px"
        prop="contractNumber"
        v-if="showColumn('contractNumber')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="费用类型"
        width="160px"
        prop="feeTypeName"
        v-if="showColumn('feeTypeName')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="租赁数"
        width="160px"
        prop="leaseArea"
        v-if="showColumn('leaseArea')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="缴费通知单"
        width="160px"
        prop="noticeCount"
        v-if="showColumn('noticeCount')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="账单来源"
        width="160px"
        prop="billSourceName"
        v-if="showColumn('billSourceName')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="账单类型"
        width="160px"
        prop="billTypeName"
        v-if="showColumn('billTypeName')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="应收金额"
        width="160px"
        prop="receivable"
        v-if="showColumn('receivable')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="实收金额"
        width="160px"
        prop="receivablePayment"
        v-if="showColumn('receivablePayment')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="税额"
        width="160px"
        prop="taxAmount"
        v-if="showColumn('taxAmount')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="逾期天数"
        width="160px"
        prop="overdueDay"
        v-if="showColumn('overdueDay')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="开始日期"
        width="160px"
        prop="payStartDate"
        v-if="showColumn('payStartDate')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="结束时间"
        width="160px"
        prop="payEndDate"
        v-if="showColumn('payEndDate')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="应收日期"
        width="160px"
        prop="payDate"
        v-if="showColumn('payDate')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="调增金额"
        width="160px"
        prop="jiaAdjustPrice"
        v-if="showColumn('jiaAdjustPrice')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="调减金额"
        width="160px"
        prop="jianAdjustPrice"
        v-if="showColumn('jianAdjustPrice')"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="应收滞纳金金额"
        width="160px"
        prop="lateFee"
        v-if="showColumn('lateFee')"
        :formatter="tableColumnEmptyPlaceholder"
      />
    </el-table>
    <Pagination
      :total="listTotal"
      v-model:page="listParams.pageNo"
      v-model:limit="listParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
  <addOrgBillReceiptForm ref="addOrgBillReceiptFormRef" @success="getList" />
  <collectionForm ref="collectionFormRef" @success="getList" />
  <CheckOrderDetail ref="CheckOrderDetailRef" />
  <AddCollectionBillForm ref="AddCollectionBillFormRef" @success="initData" />"
  <!-- 导入 -->
  <Import
    :title="'导入账单'"
    v-model:show="Image_show"
    @down-loadmb="downLoadmb"
    @change="change"
    @submit="submit"
  />
  <ElDialog v-model="Approval_show" title="审批配置" width="600px" class="h-400px">
    <div>
      开启审批
      <el-tooltip placement="top" effect="dark">
        <template #content>
          1.当开启审核，则会在该机构的审批应用中创建一个内置的“调整金审批”<br />
          2.只有开启了审批,调整才会进入审批流程。
        </template>
        <Icon class="" icon="fa:question-circle-o" :size="12" color="#999" /></el-tooltip
    ></div>
    <el-switch
      v-model="ApprovalNum"
      inline-prompt
      active-value="1"
      inactive-value="0"
      active-text="开启"
      inactive-text="关闭"
      :loading="Approval_Loading"
      @change="(val) => handleApprovalNum(val)"
    />
  </ElDialog>
</template>

<script setup lang="ts">
/** 机构账单收支流水 列表 */
defineOptions({ name: 'BillList' })
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import Import from '@/views/bus/owner/component/import.vue'
import download from '@/utils/download'
const message = useMessage() // 消息弹窗
import SearchBar from './components/searchBox.vue'
import AddCollectionBillForm from './components/AddCollectionBillForm.vue'
import { useAppStore } from '@/store/modules/app'
import addOrgBillReceiptForm from '@/views/bus/owner/component/addOrgBillReceiptForm.vue'
import CheckOrderDetail from '@/views/bus/owner/component/checkOrderDetail.vue'
import collectionForm from '@/views/bus/owner/component/collectionForm.vue'
const appStore = useAppStore()
import { BillApi } from '@/api/bus/bill/index'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { orgBillReceiptApi } from '@/api/bus/orgBillReceipt'
const columns = ref([])
const questColumn = computed(() => appStore.getBillListColumn)
const feeTypeList = ref([])
const Image_show = ref(false)
const topList = ref({
  collectedCount: undefined,
  collectedPrice: undefined,
  receivable: undefined,
  receivablePayment: undefined,
  receivableCount: undefined,
  lateFee: undefined,
  jiaAdjustPrice: undefined,
  jianAdjustPrice: undefined
})
const activeIndex = ref('1')
const isShow = ref(false)
const activeShow = ref('1')
const List = ref([])
const listParams = ref({
  pageNo: 1,
  pageSize: 10
})
const listTotal = ref(0)
const loading = ref(false)
const all = ref(false)
const chooseLit = ref([])
const totalMoney = ref(0)

//筛选列表展开修显示
const filterShow = ref(false)
const fileList = ref([])

/** 添加收款账单 */
const AddCollectionBillFormRef = ref()
const AddCollectionBill = () => {
  AddCollectionBillFormRef.value.open('collection')
}

const downLoadmb = async () => {
  const data = await BillApi.getImportTemplate()
  download.excel(data, '账单模板.xls')
}
const change = async (res) => {
  console.log(res)
  fileList.value.push(res.raw)
}

const submit = async (res) => {
  const formData = new FormData()
  const unRefList = unref(fileList)
  unRefList.map((file) => {
    formData.append('file', file)
  })
  await BillApi.importBill(formData)
    .then((res) => {
      message.success('文件上传成功')
      Image_show.value = false
      initData()
    })
    .catch(() => {
      fileList.value = []
    })
}
/** 添加付款账单 */
const AddPaymentInvoice = () => {
  AddCollectionBillFormRef.value.open('payment')
}

/** 审批配置 */
const Approval_show = ref(false)
const ApprovalNum = ref(0)
const handleApproval = async () => {
  try {
    const data = await BillApi.getBillApprovalConfig()
    ApprovalNum.value = data
    Approval_show.value = true
  } catch (e) {
    console.log(e)
  } finally {
    loading.value = false
  }
}

/** 调整审批 */
const Approval_Loading = ref(false)
const handleApprovalNum = async (val: number) => {
  try {
    Approval_Loading.value = true
    await BillApi.saveBillApprovalConfig({
      isUse: val
    })
    message.success('操作成功')
    // Approval_show.value = false
  } catch (e) {
    console.log(e)
  } finally {
    Approval_Loading.value = false
  }
}

/** 搜索按钮操作 */
const search_data = (params: any) => {
  console.log(params)
  if (params.startPayDate > params.endPayDate) {
    return message.error('开始时间不能大于结束时间')
  }
  if (params.startCreateTime > params.endCreateTime) {
    return message.error('开始时间不能大于结束时间')
  }
  listParams.value = { ...listParams.value, ...params }
  getList()
}

const CheckOrderDetailRef = ref()
const toggleRowExpansion = (row: any) => {
  CheckOrderDetailRef.value.open(row.billId)
}

const collectionFormRef = ref()
/**生成缴费通知单 */
const handleCollectionForm = async () => {
  if (chooseLit.value.length > 1) {
    return message.error('请选择一条数据')
  }
  collectionFormRef.value.open(
    { buildName: chooseLit.value[0].buildName },
    { id: chooseLit.value[0].ownerId, contractId: chooseLit.value[0].contractId },
    { id: chooseLit.value[0].billId, ...chooseLit.value[0] }
  )
}

/** 开具收据 */
const addOrgBillReceiptFormRef = ref()
const addOrgBillReceipt = async () => {
  try {
    if (chooseLit.value.length > 1) {
      return message.error('请选择一条数据')
    }
    const data = await orgBillReceiptApi.isCheckReceipt({
      billId: chooseLit.value[0].billId
    })
    console.log(data)
    addOrgBillReceiptFormRef.value.open(orderId.value)
  } catch (err) {
    // message.error(err)
  }
}
/** 全选 */
const allChange = (val: boolean) => {
  columns.value.forEach((item) => {
    if (item.disabled == false) {
      item.show = val
    }
  })
}

const handleSelectionChange = (val) => {
  chooseLit.value = val
  totalMoney.value = 0
  val.forEach((item) => {
    if (item.receivable) {
      totalMoney.value = (Number(item.receivable) + Number(totalMoney.value)).toFixed(2)
    }
  })
}
/** 恢复默认 */
const RestoreDefault = () => {
  all.value = false
  columns.value.forEach((item) => {
    if (item.default) {
      item.show = true
    }
  })
}

const handlePopoverHide = () => {
  appStore.setBillListColumn(JSON.parse(JSON.stringify(columns.value)))
}

const getTopList = async () => {
  const res = await BillApi.getBillCollectionAllTotalMoney({
    billType: activeIndex.value,
    isShow: isShow.value
  })
  topList.value = res
}
const getList = async () => {
  try {
    loading.value = true
    const data = await BillApi.getBillCollectionAllListPage({
      billType: activeShow.value,
      isShow: isShow.value,
      ...listParams.value
    })
    List.value = data.list
    listTotal.value = data.total
  } finally {
    loading.value = false
  }
}

//费用类型
const getfeeTypeList = () => {
  orgBillCostTypeApi.getCostTypeChildrenList().then((res) => {
    feeTypeList.value = res
    console.log(feeTypeList.value, 'feeTypeList')
  })
}

/** 导出 */
const exportLoading = ref(false)
const exportBill = async () => {
  try {
    exportLoading.value = true
    const data = await BillApi.exportBillCollectionAll({
      billType: activeShow.value,
      isShow: isShow.value,
      ...listParams.value
    })
    download.excel(data, activeShow.value == '1' ? '收款账单.xlsx' : '付款账单.xlsx')
  } catch (e) {
    console.log(e)
  } finally {
    exportLoading.value = false
  }
}
const initData = async () => {
  getTopList()
  getList()
}
onMounted(() => {
  // 获取列表数据
  getfeeTypeList()
  initData()
  columns.value = JSON.parse(JSON.stringify(appStore.getBillListColumn))
})
// 表格列是否显示的方法
const showColumn = (currentColumn) => {
  return questColumn.value.find((item) => item.value == currentColumn).show
}
</script>
<style lang="scss" scoped>
.custom-alert {
  background-color: #f0f9ff;
  border: 1px solid #a3d6ff;
  color: #000000d9; /* 如果需要也可以改变文字颜色 */
  padding-right: 30px;
}
</style>
