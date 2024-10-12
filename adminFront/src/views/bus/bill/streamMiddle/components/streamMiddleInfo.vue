<template>
  <ContentWrap
    :class="ownerId ? 'm-t-20px' : ''"
    :style="ownerId ? 'border: 1px solid var(--el-border-color-lighter) !important;' : ''"
  >
    <el-row justify="space-around">
      <el-col
        :span="4"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-[var(--el-color-primary)] font-size-24px"
      >
        <div class="c-#00000073 font-size-14px m-b-5px flex items-center"
          >总发生额（{{ OwnerIdStreamTotalMoney?.totalCount || '0' }}）笔
          <Icon
            icon="ep:arrow-right"
            :size="16"
            color="var(--el-color-primary)"
            class="cursor-pointer"
            @click="handleMatch"
        /></div>
        <template v-if="OwnerIdStreamTotalMoney?.totalMoney">
          ￥{{ OwnerIdStreamTotalMoney?.totalMoney }}
        </template>
        <template v-else> -- </template>
      </el-col>
      <el-col
        :span="4"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-[var(--el-color-primary)] font-size-24px"
      >
        <div class="c-#00000073 font-size-14px m-b-5px"
          >完全匹配（{{ OwnerIdStreamTotalMoney?.matchCount1 || '0' }}笔）
        </div>
        {{ OwnerIdStreamTotalMoney?.matchMoney1 || '--' }}
      </el-col>
      <el-col
        :span="4"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-[var(--el-color-primary)] font-size-24px"
      >
        <div class="c-#00000073 font-size-14px m-b-5px"
          >部分匹配（{{ OwnerIdStreamTotalMoney?.matchCount3 || '0' }}笔）</div
        >
        {{ OwnerIdStreamTotalMoney?.matchMoney3 }}
      </el-col>
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        <div class="c-#00000073 font-size-14px m-b-5px"
          >未匹配（{{ OwnerIdStreamTotalMoney?.matchCount2 || '0' }}笔）</div
        >
        {{ OwnerIdStreamTotalMoney?.matchMoney2 }}
      </el-col>
    </el-row>
  </ContentWrap>
  <ContentWrap
    :style="ownerId ? 'border: 1px solid var(--el-border-color-lighter) !important;' : ''"
  >
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="借贷标" prop="loanType">
        <el-select
          v-model="queryParams.loanType"
          placeholder="请选择借贷标"
          clearable
          class="!w-190px"
        >
          <el-option
            v-for="item in getStrDictOptions(DICT_TYPE.LOAN_TYPE)"
            :label="item.label"
            :value="item.value"
            :key="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="汇款方式" prop="remitType">
        <el-select
          v-model="queryParams.remitType"
          placeholder="请选择汇款方式"
          clearable
          class="!w-190px"
        >
          <el-option
            v-for="item in getStrDictOptions(DICT_TYPE.REMIT_TYPE)"
            :label="item.label"
            :value="item.value"
            :key="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流水账户" prop="accountId">
        <el-select
          placeholder="请选择流水账户"
          v-model="queryParams.accountId"
          clearable
          class="!w-190px"
        >
          <el-option
            v-for="item in OrgAccountList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="匹配状态" prop="matchStatus">
        <el-select
          v-model="queryParams.matchStatus"
          placeholder="请选择匹配状态"
          clearable
          class="!w-190px"
        >
          <el-option
            v-for="item in getStrDictOptions(DICT_TYPE.MATCH_STATUS)"
            :label="item.label"
            :value="item.value"
            :key="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="对方单位名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入对方单位名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-190px"
        />
      </el-form-item>

      <el-form-item label="创建人" prop="creator">
        <el-select v-model="queryParams.creator" placeholder="请选择创建人" class="!w-190px">
          <el-option
            v-for="(item, index) in getoperatorIdList"
            :key="index"
            :value="item.id"
            :label="item.nickname"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="入账日期" prop="incomeDate">
        <el-date-picker
          v-model="incomeDate"
          value-format="YYYY-MM-DD"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          class="!w-190px"
          @change="changeIncomeDate"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="createTime"
          value-format="YYYY-MM-DD"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          class="!w-190px"
          @change="changeCreateTme"
        /> </el-form-item
      ><el-form-item label="发生额" prop="amount">
        <el-input-number
          :min="0"
          v-model="queryParams.startAmount"
          controls-position="right"
          :step="1"
          class="!w-115px"
        />
        ~
        <el-input-number
          :min="0"
          v-model="queryParams.endAmount"
          controls-position="right"
          :step="1"
          class="!w-115px"
        />
        <!-- <el-input
          v-model="queryParams.amount"
          placeholder="请输入发生额"
          clearable
          @keyup.enter="handleQuery"
          class="!w-190px"
        /> -->
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['bus:orgBillStream:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 收支流水
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:orgBillStream:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button plain @click="Image_show = true" v-hasPermi="['bus:orgBillStream:import']">
          <Icon icon="ep:upload" class="mr-5px" /> 导入
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap :style="ownerId ? 'border: 1px solid rgb(240, 240, 240) !important;' : ''">
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      @row-click="toggleRowExpansion"
    >
      <el-table-column label="房号" align="center" prop="roomNumber" width="180px">
        <template #default="scope">
          <template v-if="!scope.row.roomNumber"> -- </template>
          <template v-else>
            <span v-for="(item, index) in JSON.parse(scope.row.roomNumber)" :key="index">
              <el-button type="text" @click.stop="room_openForm(item.roomId)">
                {{ item.roomName }}
                <template v-if="index < JSON.parse(scope.row.roomNumber).length - 1"> , </template>
              </el-button>
            </span>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="对方账号" align="center" prop="otherAccount" width="140px">
        <template #default="scope">
          {{ scope.row.otherAccount || '--' }}
        </template>
      </el-table-column>
      <el-table-column
        label="入账日期"
        align="center"
        prop="incomeDate"
        :formatter="dateFormatter2"
        width="140px"
      />
      <el-table-column label="对方单位名称" align="center" prop="companyName" width="200px">
        <template #default="scope">
          <template v-if="scope.row.companyName">
            <el-button
              type="primary"
              text
              @click.stop="TenantDetailsBtn(scope.row.ownerId, scope.row.isPersonal)"
            >
              <!--  -->
              {{ scope.row.companyName }}
            </el-button>
          </template>
          <template v-else> -- </template>
        </template>
      </el-table-column>
      <el-table-column
        label="借贷标"
        align="center"
        prop="loanType"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="发生额"
        align="center"
        prop="amount"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="币种（单位）"
        align="center"
        prop="currency"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="汇款方式"
        align="center"
        prop="remitType"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="已匹配金额"
        align="center"
        prop="matchPrice"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="未匹配金额"
        align="center"
        prop="nomatchPrice"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="创建人"
        align="center"
        prop="creator"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
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
  <el-dialog title="明细" width="700px" v-model="visible" @close="onClose">
    <div style="margin-top: 20px">
      <el-radio-group v-model="loanType" @change="getOwnerIdLoanTypeInfoList">
        <el-radio-button label="收入" value="INCOME" />
        <el-radio-button label="支出" value="PAY" />
      </el-radio-group>
    </div>
    <el-table
      :data="OwnerIdLoanTypeInfoList"
      style="width: 100%"
      class="m-t-20px"
      :header-cell-style="{
        color: '#000000d9',
        fontSize: '14px',
        fontWeight: '500',
        backgroundColor: '#fafafa'
      }"
    >
      <el-table-column prop="remitTypeName" label="收款方式" />
      <el-table-column prop="accountName" label="收款账户" />
      <el-table-column prop="money" label="收款笔数" />
      <el-table-column prop="money" label="收款金额">
        <template #default="scope"> ￥{{ scope.row.money }} </template>
      </el-table-column>
    </el-table>
  </el-dialog>
  <!-- 房源导入弹窗 -->
  <Import
    :title="'导入流水'"
    v-model:show="Image_show"
    @down-loadmb="downLoadmb"
    @change="change"
    @submit="submit"
  />
  <!-- 表单弹窗：添加/修改 -->
  <BillStreamForm ref="formRef" @success="getList" />
  <RoomStatusDetail ref="RoomStatusDetailRef" @success="getList" />
  <TenantDetails ref="TenantDetailsRef" @select-pick="getList" />
  <StreamMiddle ref="StreamMiddleRef" @success="getList" />
</template>
<script setup lang="ts">
const props = defineProps({
  ownerId: {
    type: Number,
    required: true,
    default: () => undefined
  },
  info: {
    type: Object,
    required: true,
    default: () => {}
  }
})
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import Import from '@/views/bus/owner/component/import.vue'
import { getStrDictOptions, DICT_TYPE, getDictLabel } from '@/utils/dict'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import { BuildApi } from '@/api/bus/village'
import { orgBillStreamApi, BillStreamVO } from '@/api/bus/orgBillStream'
import BillStreamForm from '../form.vue'
import { Api } from '@/api/contract/contractList/index'
import RoomStatusDetail from '@/views/village/managementCenter/RoomStatusDetail.vue'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import StreamMiddle from '../detail.vue'
defineOptions({ name: 'StramMiddleInfo' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<BillStreamVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const incomeDate = ref('')
const createTime = ref('')
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  ownerId: undefined,
  creator: undefined,
  villageId: undefined,
  buildId: undefined,
  buildIds: undefined,
  buildBind: undefined,
  roomNumber: undefined,
  billId: undefined,
  accountId: undefined,
  accountName: undefined,
  streamNumber: undefined,
  streamSource: undefined,
  billType: undefined,
  isClose: undefined,
  loanType: undefined,
  isConfirm: undefined,
  amount: undefined,
  incomeDate: undefined,
  startIncomeDate: undefined,
  endIncomeDate: undefined,
  cancelMatchDate: [],
  companyId: undefined,
  companyName: undefined,
  companyType: undefined,
  streamAccount: undefined,
  matchDate: [],
  matchStatus: undefined,
  matchPrice: undefined,
  nomatchPrice: undefined,
  refundPrice: undefined,
  currency: undefined,
  costType: undefined,
  linkName: undefined,
  remitType: undefined,
  otherAccount: undefined,
  sonAccount: undefined,
  sonAccountName: undefined,
  sonAccountStatus: undefined,
  voucherNo: undefined,
  receiptNo: undefined,
  abstractc: undefined,
  isUnrealRoom: undefined,
  unrealRoom: undefined,
  remark: undefined,
  closeReason: undefined,
  extra: undefined,
  createTime: [],
  approvalStatus: undefined,
  approvalCancel: undefined,
  reason: undefined,
  singleType: undefined,
  startAmount: undefined,
  endAmount: undefined,
  startCreateTime: undefined,
  endCreateTime: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const OwnerIdStreamTotalMoney = ref()
const OwnerIdLoanTypeInfoList = ref()
const loanType = ref('INCOME')
const visible = ref(false)
const Image_show = ref(false)
const fileList = ref([])
const downLoadmb = async () => {
  const data = await orgBillStreamApi.getImportTemplate()
  download.excel(data, '模板.xlsx')
}
const change = async (res) => {
  console.log(res)
  fileList.value.push(res.raw)
}

const submit = async (res) => {
  const formData = new FormData()
  const unRefList = unref(fileList)
  unRefList.map((file) => {
    console.log(file)
    formData.append('file', file)
  })
  await orgBillStreamApi
    .import(formData)
    .then((res) => {
      message.success('文件上传成功')
      Image_show.value = false
      getList()
    })
    .catch(() => {
      fileList.value = []
    })
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await orgBillStreamApi.getBillStreamPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const StreamMiddleRef = ref()
const toggleRowExpansion = (row: any) => {
  StreamMiddleRef.value.open(row.id)
}

const changeIncomeDate = (val: any) => {
  incomeDate.value = val
  console.log(val)
  queryParams.startIncomeDate = val[0]
  queryParams.endIncomeDate = val[1]
}

const changeCreateTme = (val: any) => {
  if (val) {
    queryParams.startCreateTime = val[0]
    queryParams.endCreateTime = val[1]
  } else {
    queryParams.startCreateTime = undefined
    queryParams.endCreateTime = undefined
  }
}

const getOwnerIdStreamTotalMoney = async () => {
  try {
    console.log(props.ownerId, 'ownerId')
    const data = await orgBillStreamApi.getOwnerIdStreamTotalMoney(
      props.ownerId ? { ownerId: props.ownerId } : {}
    )
    console.log(data)
    OwnerIdStreamTotalMoney.value = data
  } catch {}
}

//房源抽屉
const RoomStatusDetailRef = ref<any>(null)
const room_openForm = (id?: number) => {
  RoomStatusDetailRef.value.open({
    id: id
  })
}

const TenantDetailsRef = ref()
const TenantDetailsBtn = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}

/**  顶部统计总发生额明细 */
const handleMatch = async () => {
  try {
    await getOwnerIdLoanTypeInfoList()
    visible.value = true
  } catch {}
}

//获取创建人下拉
const getoperatorIdList = ref({
  id: '',
  name: ''
})
const getoperatorId = async () => {
  getoperatorIdList.value = await Api.getTenantIdUserInfoList()
}

const onClose = () => {
  visible.value = false
  OwnerIdLoanTypeInfoList.value = []
}

const getOwnerIdLoanTypeInfoList = async () => {
  try {
    const data = await orgBillStreamApi.getOwnerIdLoanTypeInfoList({
      loanType: loanType.value,
      ownerId: props?.ownerId
    })
    OwnerIdLoanTypeInfoList.value = data
  } catch {}
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  incomeDate.value = ''
  createTime.value = ''
  queryParams.startCreateTime = undefined
  queryParams.endCreateTime = undefined
  queryParams.startAmount = undefined
  queryParams.endAmount = undefined
  queryParams.startIncomeDate = undefined
  queryParams.endIncomeDate = undefined
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = () => {
  console.log('监听ownerId', props.info)
  formRef.value.open(
    'createBillStream',
    props.info?.villageId,
    props.info?.buildId,
    props.info?.id,
    props.info?.ownerId,
    props.info?.orderId,
    props.info?.lateFee
  )
}

/** 收支账户 */
const OrgAccountList = ref([])
const getOrgAccountList = async () => {
  const data = await BuildApi.orgAccountList({ pageNo: 1, pageSize: 10 })
  OrgAccountList.value = data
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await orgBillStreamApi.exportBillStream(queryParams)
    download.excel(data, '机构账单收支流水.xlsx')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

watch(
  () => props.ownerId,
  (val) => {
    queryParams.ownerId = val
    console.log('监听ownerId', val)
    getOwnerIdStreamTotalMoney()
    getList()
  },
  {
    immediate: true,
    deep: true
  }
)
/** 初始化 **/
onMounted(() => {
  console.log('初始化')
  getoperatorId()
  getOrgAccountList()
})
</script>
