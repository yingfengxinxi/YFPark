<template>
  <div class="receiptDetail">
    <el-drawer v-model="drawer_show" size="90%" title="新建收支流水">
      <el-form :label-position="'top'" :model="form" ref="formRef">
        <el-row :gutter="20" class="!m-20px">
          <el-col :span="12">
            <ContentWrap :title="'账单信息'">
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item
                    label="关联合同"
                    prop="contractId"
                    :rules="[{ required: true, message: '请选择合同', trigger: 'blur' }]"
                  >
                    <el-select
                      v-model="form.contractId"
                      filterable
                      remote
                      placeholder="输入租客名称进行搜索"
                      :remote-show-suffix="true"
                      :remote-method="getOwnerNameContractList"
                      :loading="contractLoading"
                      style="width: 240px"
                      @change="changeContract"
                    >
                      <el-option
                        v-for="item in contractList"
                        :key="item.contractId"
                        :label="item.contractNumber"
                        :value="item.contractId"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="付款方"
                    prop="ownerName"
                    :rules="[{ required: true, message: '请选择付款方', trigger: 'blur' }]"
                  >
                    <el-input placeholder="请选择付款方" v-model="form.ownerName" disabled />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="费用类型"
                    prop="feeType"
                    :rules="[{ required: true, message: '请选择费用类型', trigger: 'blur' }]"
                  >
                    <el-select v-model="form.feeType" placeholder="请选择费用类型" clearable>
                      <el-option
                        v-for="dict in feeTypeList"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="币种" prop="currency">
                    <el-select
                      placeholder="请选择币种"
                      v-model="form.currency"
                      class="!w-100%"
                      disabled
                    >
                      <el-option
                        v-for="item in getStrDictOptions(DICT_TYPE.CURRENCY)"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="计费周期"
                    prop="payStartDate"
                    :rules="[{ required: true, message: '请选择计费周期', trigger: 'blur' }]"
                  >
                    <el-date-picker
                      v-model="termValidity"
                      type="daterange"
                      range-separator="-"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                      @change="handleDateChange"
                    />
                  </el-form-item>
                </el-col>
                <template v-if="ViewType === 'collection'">
                  <el-col :span="8">
                    <el-form-item
                      label="应收金额"
                      prop="receivable"
                      :rules="[{ required: true, message: '请输入应收金额', trigger: 'blur' }]"
                    >
                      <el-input-number
                        v-model="form.receivable"
                        controls-position="right"
                        :precision="2"
                        min="0"
                        placeholder="请输入应收金额"
                        class="!w-1/1"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      label="税率"
                      prop="taxRate"
                      :rules="[{ required: true, message: '请输入税率', trigger: 'blur' }]"
                    >
                      <el-input
                        v-model="form.taxRate"
                        type="number"
                        min="0"
                        placeholder="请输入税率"
                      >
                        <template #append>%</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      label="应收日期"
                      prop="payDate"
                      :rules="[{ required: true, message: '请选择应收日期', trigger: 'blur' }]"
                    >
                      <el-date-picker
                        type="date"
                        v-model="form.payDate"
                        label-format="yyyy-MM-dd"
                        placeholder="选择日期"
                        format="YYYY-MM-DD"
                        value-format="YYYY-MM-DD"
                        clearable
                        class="w-1/1"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      label="滞纳金起算天数"
                      prop="startingLateFeeDay"
                      :rules="[
                        { required: true, message: '请输入滞纳金起算天数', trigger: 'blur' }
                      ]"
                    >
                      <el-input
                        type="number"
                        min="0"
                        v-model="form.startingLateFeeDay"
                        placeholder="请输入起算天数"
                      >
                        <template #append>天</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      label="滞纳金比例"
                      prop="lateFeeRatio"
                      :rules="[{ required: true, message: '请输入滞纳金比例', trigger: 'blur' }]"
                    >
                      <el-input
                        type="number"
                        min="0"
                        v-model="form.lateFeeRatio"
                        placeholder="请输入滞纳金比例"
                      >
                        <template #append>%/天</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      label="滞纳金上限"
                      prop="upperLimitLateFee"
                      :rules="[{ required: true, message: '请输入滞纳金上限', trigger: 'blur' }]"
                    >
                      <el-input
                        type="number"
                        min="0"
                        v-model="form.upperLimitLateFee"
                        placeholder="请输入滞纳金上限"
                      >
                        <template #append>%</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </template>

                <template v-if="ViewType === 'payment'">
                  <el-col :span="8">
                    <el-form-item
                      label="应付金额"
                      prop="receivable"
                      :rules="[{ required: true, message: '请输入应付金额', trigger: 'blur' }]"
                    >
                      <el-input-number
                        v-model="form.receivable"
                        controls-position="right"
                        :precision="2"
                        min="0"
                        placeholder="请输入应收金额"
                        class="!w-1/1"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      label="应付日期"
                      prop="payDate"
                      :rules="[{ required: true, message: '请选择应付日期', trigger: 'blur' }]"
                    >
                      <el-date-picker
                        type="date"
                        v-model="form.payDate"
                        label-format="yyyy-MM-dd"
                        placeholder="选择日期"
                        format="YYYY-MM-DD"
                        value-format="YYYY-MM-DD"
                        clearable
                        class="w-1/1"
                      />
                    </el-form-item> </el-col
                ></template>
                <el-col :span="24">
                  <el-form-item label="账单备注" prop="remark">
                    <el-input
                      placeholder="请输入账单备注"
                      v-model="form.remark"
                      type="textarea"
                      rows="4"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </ContentWrap>
          </el-col>
          <el-col :span="12">
            <ContentWrap>
              <div class="flex justify-between items-center pb-8px border-bottom">
                <div class="text-16px">已选房源</div>
              </div>
              <DeptTreeSelect
                @change-data="DeptTreeSelect_data"
                ref="DeptTreeSelect_ref"
                :change_tree="change_tree_value"
              />
            </ContentWrap>
          </el-col>
          <el-col :span="24" class="m-t-20px">
            <el-button
              type="primary"
              class="w-100%"
              plain
              @click="addBillStream"
              v-if="!addBillStreamShow"
              >添加收支流水</el-button
            >
            <ContentWrap title="收支流水" v-else>
              <template #header>
                <div class="flex-1 flex justify-end">
                  <el-button text @click="deleteBillStream">
                    <Icon icon="ep:delete" color="red" />
                  </el-button>
                </div>
              </template>
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item
                    label="借贷类型"
                    prop="loanType"
                    :rules="[{ required: true, message: '请选择借贷类型', trigger: 'blur' }]"
                  >
                    <el-select
                      v-model="form.loanType"
                      placeholder="请选择借贷类型"
                      clearable
                      disabled
                    >
                      <el-option
                        v-for="item in getStrDictOptions(DICT_TYPE.LOAN_TYPE)"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="流水账户"
                    prop="accountId"
                    :rules="[{ required: true, message: '请选择流水账户', trigger: 'blur' }]"
                  >
                    <el-select
                      placeholder="请选择流水账户"
                      v-model="form.accountId"
                      class="!w-100%"
                      @change="getAccount"
                    >
                      <el-option
                        v-for="item in OrgAccountList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="入账日期"
                    prop="incomeDate"
                    :rules="[{ required: true, message: '请选择入账日期', trigger: 'blur' }]"
                  >
                    <el-date-picker
                      type="date"
                      v-model="form.incomeDate"
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                      placeholder="请选择入账日期"
                      clearable
                      class="!w-100%"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="对方单位名称"
                    prop="ownerName"
                    :rules="[{ required: true, message: '请选择对方单位名称', trigger: 'blur' }]"
                  >
                    <el-select
                      placeholder="请选择对方单位名称"
                      v-model="form.ownerName"
                      class="!w-100%"
                      disabled
                    >
                      <el-input placeholder="请输入对方单位名称" v-model="form.ownerName" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="发生额"
                    prop="amount"
                    :rules="[{ required: true, message: '请输入发生额', trigger: 'blur' }]"
                  >
                    <el-input-number
                      :min="0"
                      :precision="2"
                      v-model="form.amount"
                      placeholder="请输入发生额"
                      :step="1"
                      controls-position="right"
                      class="!w-100%"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="联系人" prop="linkName">
                    <el-input placeholder="请输入联系人" v-model="form.linkName" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="汇款方式"
                    prop="remitType"
                    :rules="[{ required: true, message: '请选择汇款方式', trigger: 'blur' }]"
                  >
                    <el-select v-model="form.remitType" placeholder="请选择汇款方式" clearable>
                      <el-option
                        v-for="item in getStrDictOptions(DICT_TYPE.REMIT_TYPE)"
                        :label="item.label"
                        :value="item.value"
                        :key="item.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8" v-if="ViewType === 'collection'">
                  <el-form-item
                    label="开户银行"
                    prop="streamAccount"
                    :rules="[{ required: true, message: '请输入开户银行', trigger: 'blur' }]"
                  >
                    <el-input placeholder="请输入开户银行" v-model="form.streamAccount" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label="对方账户"
                    prop="otherAccount"
                    :rules="[{ required: true, message: '请输入对方账户', trigger: 'blur' }]"
                  >
                    <el-input placeholder="请输入对方账户" v-model="form.otherAccount" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="凭证号" prop="">
                    <el-input placeholder="请输入凭证号" v-model="form.voucherNo" />
                  </el-form-item>
                </el-col>
                <el-col :span="16">
                  <el-form-item label="流水摘要" prop="">
                    <el-input
                      placeholder="请输入"
                      v-model="form.abstractc"
                      type="textarea"
                      rows="3"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="16">
                  <el-form-item label="流水备注" prop="">
                    <el-input
                      placeholder="请输入"
                      v-model="form.streamRemark"
                      type="textarea"
                      rows="3"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </ContentWrap>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button size="small" @click="closed">取 消</el-button>
        <el-button size="small" type="primary" :loading="formLoading" @click="handleSubmit"
          >确 定</el-button
        >
      </template>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
// import DeptTreeSelect from '@/views/contract/contractList/component/DeptTree_select.vue'
import DeptTreeSelect from './DeptTree_select.vue'
import { getStrDictOptions, DICT_TYPE, getDictValue } from '@/utils/dict'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { BuildApi } from '@/api/bus/village'
import { Api } from '@/api/contract/contractList/index'
import { contractOrderApi } from '@/api/bus/contractOrderBill'
import { t } from '@/hooks/web/useI18n'
const message = useMessage() // 消息弹窗
const emit = defineEmits(['success'])
const detailLoading = ref(false)
const drawer_show = ref(false)
const termValidity = ref([])
const ViewType = ref('')
const change_tree_value = ref(false)
const form = ref({
  contractId: undefined,
  feeType: undefined,
  ownerId: undefined,
  ownerName: undefined,
  currency: undefined,
  payStartDate: undefined,
  payEndDate: undefined,
  receivable: undefined,
  taxRate: undefined,
  payDate: undefined,
  startingLateFeeDay: undefined,
  lateFeeRatio: undefined,
  upperLimitLateFee: undefined,
  remark: undefined,
  loanType: undefined,
  accountId: undefined,
  incomeDate: undefined,
  amount: undefined,
  linkName: undefined,
  remitType: undefined,
  streamAccount: undefined,
  otherAccount: undefined,
  voucherNo: undefined,
  abstractc: undefined,
  streamRemark: undefined
})
const feeTypeList = ref([])
const DeptTreeSelect_data = async () => {}
//费用类型
const getfeeTypeList = () => {
  orgBillCostTypeApi.getCostTypeChildrenList().then((res) => {
    feeTypeList.value = res
    console.log(feeTypeList.value, 'feeTypeList')
  })
}

/** 收支账户 */
const OrgAccountList = ref([])
const getOrgAccountList = async () => {
  const data = await BuildApi.orgAccountList({ pageNo: 1, pageSize: 10 })
  OrgAccountList.value = data
}

const deleteBillStream = () => {}

const addBillStreamShow = ref(false)
const addBillStream = () => {
  addBillStreamShow.value = true
}

const handleDateChange = (value) => {
  termValidity.value = value
  form.value.payStartDate = value[0]
  form.value.payEndDate = value[1]
  console.log(form.value, 'value', value)
}
const contractLoading = ref(false)
const contractList = ref([])
const DeptTreeSelect_ref = ref()
const changeContract = async (id) => {
  console.log(id)
  if (id) {
    change_tree_value.value = true
    form.value.ownerId = contractList.value.find((item) => item.contractId === id).ownerId
    form.value.ownerName = contractList.value.find((item) => item.contractId === id).ownerName
    let itemList = contractList.value.find((item) => item.contractId === id).checkedBuild
    DeptTreeSelect_ref.value.getDeptTreeSelect(JSON.parse(itemList))
  }
}

const getAccount = async (val) => {
  form.value.accountId = val
  form.value.accountName = OrgAccountList.value.filter((item) => item.id == val)[0].name
}
const getOwnerNameContractList = async (params) => {
  console.log(params, 'params')
  if (!params) return false
  try {
    contractLoading.value = true
    const data = await Api.getOwnerNameContractList({ ownerName: params })
    contractList.value = data
    console.log(data, 'data')
  } catch (error) {
    console.log(error)
  } finally {
    contractLoading.value = false
  }
}

const open = async (type: string) => {
  resetForm()
  ViewType.value = type
  change_tree_value.value = false
  getfeeTypeList()
  getOrgAccountList()
  addBillStreamShow.value = false
  form.value.currency = getDictValue(DICT_TYPE.CURRENCY, '人民币')
  form.value.loanType = getDictValue(DICT_TYPE.LOAN_TYPE, '贷（收入）')
  drawer_show.value = true
  console.log(DeptTreeSelect_ref.value)
}

const closed = async () => {
  drawer_show.value = false
}
const formRef = ref()
const formLoading = ref(false)
const handleSubmit = async () => {
  await formRef.value.validate()
  try {
    formLoading.value = true
    let data = JSON.parse(JSON.stringify(form.value))
    data.billType = ViewType.value === 'collection' ? '1' : '2'
    data.isCreateStream = addBillStreamShow.value && form.value.amount ? 1 : 0
    await contractOrderApi.createBill(data)
    message.success('新增成功')
    closed()
    emit('success')
  } catch (error) {
    console.log(error)
  } finally {
    formLoading.value = false
  }
}

const resetForm = () => {
  form.value = {
    contractId: undefined,
    feeType: undefined,
    ownerId: undefined,
    ownerName: undefined,
    currency: undefined,
    payStartDate: undefined,
    payEndDate: undefined,
    receivable: undefined,
    taxRate: undefined,
    payDate: undefined,
    startingLateFeeDay: undefined,
    lateFeeRatio: undefined,
    upperLimitLateFee: undefined,
    remark: undefined,
    loanType: undefined,
    accountId: undefined,
    incomeDate: undefined,
    amount: undefined,
    linkName: undefined,
    remitType: undefined,
    streamAccount: undefined,
    otherAccount: undefined,
    voucherNo: undefined,
    abstractc: undefined,
    streamRemark: undefined
  }
  formRef.value?.resetFields()
}

defineExpose({ open })
</script>
<style scoped lang="scss">
.receiptDetail :deep(.el-drawer__body) {
  padding: 0;
  background: var(--app-content-bg-color) !important;
}
.custom-alert {
  background-color: #f0f9ff;
  border: 1px solid #a3d6ff;
  color: #000000d9; /* 如果需要也可以改变文字颜色 */
  padding-right: 30px;
}
</style>
