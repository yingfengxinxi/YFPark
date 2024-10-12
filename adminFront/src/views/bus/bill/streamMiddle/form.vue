<template>
  <el-drawer v-model="drawer" :title="detail?.id ? '编辑收支流水' : '新建收支流水'" size="90%">
    <!-- 费用名称 -->
    <el-form label-position="top" :model="form" ref="form_ref">
      <el-card
        class="!border-rd-4px w-100% box-border font-size-14px"
        style="border: 1px solid #f0f0f0 !important"
        shadow="never"
      >
        <template #header>
          <span class="font-size-14px"> 流水信息 </span>
        </template>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-form-item
              label="借贷标"
              :rules="[{ required: true, message: '请选择借贷标', trigger: 'blur' }]"
              prop="loanType"
            >
              <el-select
                placeholder="请选择借贷标"
                v-model="form.loanType"
                class="!w-100%"
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
          <el-col :span="4">
            <el-form-item label="币种" prop="currency">
              <el-select placeholder="请选择币种" v-model="form.currency" class="!w-100%" disabled>
                <el-option
                  v-for="item in getStrDictOptions(DICT_TYPE.CURRENCY)"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item
              label="发生额"
              prop="amount"
              :rules="[{ required: true, message: '请输入发生额', trigger: 'blur' }]"
            >
              <el-input-number
                :min="0"
                :max="maxLateFee ? maxLateFee : Infinity"
                :precision="2"
                v-model="form.amount"
                :step="1"
                controls-position="right"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="4">
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
                clearable
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="4">
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
          <el-col :span="4">
            <el-form-item label="匹配日期" prop="matchDate">
              <el-date-picker
                type="date"
                v-model="form.matchDate"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                clearable
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="凭证号" prop="">
              <el-input placeholder="请输入凭证号" v-model="form.voucherNo" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-form-item
              label="对方单位名称"
              prop="companyId"
              :rules="[{ required: true, message: '请选择对方单位名称', trigger: 'blur' }]"
            >
              <el-select
                placeholder="请选择对方单位名称"
                v-model="form.companyId"
                class="!w-100%"
                :disabled="drawerType == 'lateSettle'"
              >
                <el-option
                  v-for="dict in onwerIdList"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item
              label="联系人"
              prop="linkName"
              :rules="[{ required: true, message: '请输入联系人', trigger: 'blur' }]"
            >
              <el-input placeholder="请输入联系人" v-model="form.linkName" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
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
          <el-col :span="4">
            <el-form-item
              label="开户银行"
              prop="streamAccount"
              :rules="[{ required: true, message: '请输入开户银行', trigger: 'blur' }]"
            >
              <el-input placeholder="请输入开户银行" v-model="form.streamAccount" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item
              label="对方账户"
              prop="otherAccount"
              :rules="[{ required: true, message: '请输入对方账户', trigger: 'blur' }]"
            >
              <el-input placeholder="请输入对方账户" v-model="form.otherAccount" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="摘要" prop="">
              <el-input placeholder="请输入" v-model="form.abstractc" type="textarea" rows="3" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="备注" prop="">
              <el-input placeholder="请输入" v-model="form.remark" type="textarea" rows="3" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>
      <el-card
        class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
        style="border: 1px solid #f0f0f0 !important"
        shadow="never"
      >
        <template #header>
          <div class="flex justify-between items-center">
            <span class="font-size-14px"> 附件信息 </span>
            <el-button @click="addFile()">
              <Icon icon="ep:plus" color="#00000040" class="m-r-6px" />
              添加附件
            </el-button>
          </div>
        </template>
        <el-table
          :data="BillAnnexList"
          border
          v-loading="BillAnnexListLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="文件名" prop="name" />
          <el-table-column label="操作" fixed="right" width="140px">
            <template #default="scope">
              <el-link
                :href="scope.row.filePath"
                :underline="false"
                download
                target="_blank"
                type="primary"
                class="m-r-14px"
              >
                下载
              </el-link>
              <el-button link type="danger" @click="handleBillAnnexRemove(scope.$index)">
                删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <AddBillAnnexForm ref="AddBillAnnexFormRef" @success="getBillAnnexList" />
    </el-form>
    <template #footer>
      <el-button @click="drawer = false">取 消</el-button>
      <el-button type="primary" @click="submit" :loading="formLoading">保 存</el-button>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import AddBillAnnexForm from '@/views/bus/owner/component/addBillAnnexForm.vue'
import { DICT_TYPE, getStrDictOptions, getDictValue } from '@/utils/dict'
import { formatDate } from '@/utils/formatTime'
import { OwnerApi } from '@/api/bus/owner/index'
import { BuildApi } from '@/api/bus/village'
import { orgBillAnnexApi } from '@/api/bus/orgBillAnnex'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import { orgBillStreamApi } from '@/api/bus/orgBillStream'

const message = useMessage() // 消息弹窗
const emit = defineEmits(['success'])
import { ElMessage, ElMessageBox } from 'element-plus'

const drawer = ref(false)
const formLoading = ref(false)
const form = ref({
  billId: undefined,
  villageId: undefined,
  buildId: undefined,
  roomNumber: undefined,
  loanType: undefined,
  currency: undefined,
  amount: undefined,
  incomeDate: formatDate(new Date(), 'YYYY-MM-DD'),
  matchDate: formatDate(new Date(), 'YYYY-MM-DD'),
  accountId: undefined,
  accountName: undefined,
  voucherNo: undefined,
  companyId: undefined,
  linkName: undefined,
  remitType: undefined,
  otherAccount: undefined,
  streamAccount: undefined,
  abstractc: undefined,
  remark: undefined,
  annexList: undefined
})
const detail = ref({})
const orderInfoId = ref(0)
const BillAnnexList = ref([])
const BillAnnexListLoading = ref(false)
const BillAnnexListTotal = ref(0)
const BillAnnexListParams = ref({
  pageNo: 1,
  pageSize: 10
})

const drawerType = ref<string>('')

const maxLateFee = ref(0)
function open(
  type?: string,
  villageId?: number,
  buildId?: number,
  roomNumber?: number,
  ownerId?: number,
  orderId?: number,
  lateFee?: string
) {
  drawerType.value = type ?? ''
  console.log(villageId, buildId, roomNumber, ownerId, orderId)
  orderInfoId.value = orderId ?? 0
  maxLateFee.value = lateFee ? parseFloat(lateFee) || 0.0 : 0.0
  getOrgAccountList()
  getOwnerList()
  resetForm()

  form.value.villageId = villageId
  form.value.buildId = buildId
  form.value.roomNumber = roomNumber
  form.value.companyId = ownerId
  form.value.billId = orderId
  form.value.amount = lateFee ?? 0.0
  form.value.loanType = getDictValue(DICT_TYPE.LOAN_TYPE, '贷（收入）')
  form.value.currency = getDictValue(DICT_TYPE.CURRENCY, '人民币')

  drawer.value = true
  // if (id) {
  //   Api.getCostNameDetail(id).then((res: any) => {
  //     form.value = res
  //   })
  // }
}
defineExpose({ open })

const getAccount = async (val) => {
  form.value.accountId = val
  form.value.accountName = OrgAccountList.value.filter((item) => item.id == val)[0].name
}
const form_ref = ref()
const submit = async () => {
  await form_ref.value.validate()
  try {
    formLoading.value = true
    const data = JSON.parse(JSON.stringify(form.value)) // 浅拷贝
    if (BillAnnexList.value.length) data.annexList = JSON.stringify(BillAnnexList.value)
    data.matchDate = formatDate(data.matchDate, 'YYYY-MM-DD HH:mm:ss')
    if (drawerType.value == 'lateSettle') {
      await orgBillStreamApi.lateSettle(data)
    } else {
      await orgBillStreamApi.createBillStream(data)
    }
    drawer.value = false
    message.success('操作成功')
    emit('success')
  } finally {
    formLoading.value = false
  }
}
// 删除附件
const handleBillAnnexRemove = async (index) => {
  BillAnnexList.value.splice(index, 1)
  // try {
  //   await orgBillAnnexApi.delete(id)
  //   message.success('删除成功')
  // } catch (error) {}
}
const getBillAnnexList = async (data) => {
  BillAnnexList.value.push(data)
}
//新增附件
const AddBillAnnexFormRef = ref()
const addFile = async () => {
  AddBillAnnexFormRef.value.open(orderInfoId.value, 2)
}

const onwerIdList = ref([] as any[])
/** 所属租客 */
const getOwnerList = async () => {
  try {
    const data = await OwnerApi.getOwnerList()
    console.log(data, 'data')
    onwerIdList.value = data
  } finally {
  }
}
/** 收支账户 */
const OrgAccountList = ref([])
const getOrgAccountList = async () => {
  const data = await BuildApi.orgAccountList({ pageNo: 1, pageSize: 10 })
  OrgAccountList.value = data
}

/** 重置表单 */
const resetForm = () => {
  form.value = {
    billId: undefined,
    villageId: undefined,
    buildId: undefined,
    roomNumber: undefined,
    loanType: undefined,
    currency: undefined,
    amount: undefined,
    incomeDate: formatDate(new Date(), 'YYYY-MM-DD'),
    matchDate: formatDate(new Date(), 'YYYY-MM-DD'),
    accountId: undefined,
    accountName: undefined,
    voucherNo: undefined,
    companyId: undefined,
    linkName: undefined,
    remitType: undefined,
    otherAccount: undefined,
    streamAccount: undefined,
    abstractc: undefined,
    remark: undefined,
    annexList: undefined
  }
  form_ref.value?.resetFields()
}
</script>
