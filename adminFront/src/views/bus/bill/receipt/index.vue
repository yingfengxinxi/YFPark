<template>
  <ContentWrap>
    <el-row justify="space-around">
      <el-col
        :span="4"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-[var(--el-color-primary)] font-size-24px"
      >
        <div class="c-#00000073 font-size-14px m-b-5px">开具金额</div>
        <template v-if="TopMoneyInfo?.receiptAmount">
          ￥{{ TopMoneyInfo?.receiptAmount }}
        </template>
        <template v-else> -- </template>
      </el-col>
      <el-col
        :span="4"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-[var(--el-color-primary)] font-size-24px"
      >
        <div class="c-#00000073 font-size-14px m-b-5px">未发出的收据数</div>
        {{ TopMoneyInfo?.wreceiptNum }}
      </el-col>
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        <div class="c-#00000073 font-size-14px m-b-5px">开据数</div>
        {{ TopMoneyInfo?.receiptNum }}
      </el-col>
    </el-row>
  </ContentWrap>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="120px"
    >
      <el-form-item label="交款单位" prop="paymentCompany">
        <el-input
          v-model="queryParams.paymentCompany"
          placeholder="请输入交款单位"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="收据编号" prop="receiptNumber">
        <el-input
          v-model="queryParams.receiptNumber"
          placeholder="请输入收据编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item label="收据状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择收据状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="item in getIntDictOptions(DICT_TYPE.RECEIPT_STATUS)"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="收款单位" prop="collectionCompany">
        <el-input
          v-model="queryParams.collectionCompany"
          placeholder="请输入收款单位"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="费用名称" prop="costName">
        <el-input
          v-model="queryParams.costName"
          placeholder="请输入费用名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item label="开据人" prop="issuerUid">
        <el-select v-model="queryParams.issuerUid" placeholder="请选择开据人" class="!w-240px">
          <el-option
            v-for="(item, index) in getoperatorIdList"
            :key="index"
            :value="item.id"
            :label="item.nickname"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['OrgBillReceipt::create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['OrgBillReceipt::export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      @row-click="handleRowClick"
    >
      <el-table-column
        label="交款单位"
        align="center"
        prop="paymentCompany"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="开据日期"
        align="center"
        prop="issuerTime"
        :formatter="dateFormatter2"
      />
      <el-table-column
        label="收据编号"
        align="center"
        prop="receiptNumber"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="开据金额"
        align="center"
        prop="applyReceiptAmount"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="交款人"
        align="center"
        prop="paymentUname"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="收款单位"
        align="center"
        prop="collectionCompany"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="汇款方式" align="center" prop="remitType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.REMIT_TYPE" :value="scope.row.remitType" />
        </template>
      </el-table-column>
      <el-table-column
        label="楼宇名称"
        align="center"
        prop="buildName"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="收据状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.RECEIPT_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="费用名称"
        align="center"
        prop="costName"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="费用类型"
        align="center"
        prop="costType"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="开据人"
        align="center"
        prop="issuerUid"
        :formatter="tableColumnEmptyPlaceholder"
      />
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <ReceiptDetail ref="receiptDetailRef" @success="getList" />
</template>

<script setup lang="ts">
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { Api } from '@/api/contract/contractList/index'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import { orgBillReceiptApi, ReceiptVO } from '@/api/bus/orgBillReceipt/index'
import ReceiptDetail from './detail.vue'

/** 机构账单收据 列表 */
defineOptions({ name: 'OrgBillReceipt' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ReceiptVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  villageId: undefined,
  buildId: undefined,
  billId: undefined,
  subbillId: undefined,
  ruleId: undefined,
  roomNumber: undefined,
  paymentCompanyId: undefined,
  paymentCompany: undefined,
  paymentUname: undefined,
  paymentAddress: undefined,
  paymentPhone: undefined,
  receiptNumber: undefined,
  receiptInt: undefined,
  numberType: undefined,
  price: undefined,
  canReceiptAmount: undefined,
  applyReceiptAmount: undefined,
  currency: undefined,
  status: undefined,
  paymentUid: undefined,
  collectionCompanyId: undefined,
  collectionCompany: undefined,
  collectionUid: undefined,
  collectionUname: undefined,
  collectionAddress: undefined,
  collectionPhone: undefined,
  remark: undefined,
  remitType: undefined,
  costType: undefined,
  costName: undefined,
  taskKey: undefined,
  groupKey: undefined,
  issuerUid: undefined,
  issuerTime: [],
  applyTemplateId: undefined,
  applyTemplate: undefined,
  applyStatus: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const TopMoneyInfo = ref()

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await orgBillReceiptApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
const receiptDetailRef = ref()
const handleRowClick = (row: any) => {
  receiptDetailRef.value.open(row.id)
}

const getTopMoney = async () => {
  const data = await orgBillReceiptApi.getTopMoney()
  TopMoneyInfo.value = data
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await orgBillReceiptApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

//获取经办人
const getoperatorIdList = ref({
  id: '',
  name: ''
})
const getoperatorId = async () => {
  getoperatorIdList.value = await Api.getTenantIdUserInfoList()
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await orgBillReceiptApi.export(queryParams)
    download.excel(data, '机构账单收据.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getoperatorId()
  getList()
  getTopMoney()
})
</script>
