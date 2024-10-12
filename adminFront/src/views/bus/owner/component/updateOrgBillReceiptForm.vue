<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 申请开据的收据 -->
  <div class="OrgBillReceiptFormBox">
    <el-drawer
      v-model="drawer_show"
      append-to-body
      :with-header="true"
      size="600px"
      title="编辑收据"
      @closed="closed"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rulesForm"
        label-width="80px"
        label-position="top"
      >
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <span class="font-size-14px"> 收据信息 </span>
          </template>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="编号规则" prop="receiptRuleName">
                <el-input placeholder="" v-model="formData.receiptRuleName" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="收据编号" prop="receiptNumber">
                <el-input placeholder="" v-model="formData.receiptNumber" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="开据日期" prop="issuerTime">
                <el-date-picker
                  type="date"
                  v-model="formData.issuerTime"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
                  clearable
                  class="!w-100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="支付方式" prop="remitType">
                <el-select v-model="formData.remitType" placeholder="" clearable>
                  <el-option
                    v-for="item in getStrDictOptions(DICT_TYPE.REMIT_TYPE)"
                    :label="item.label"
                    :value="item.value"
                    :key="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="费用类型" prop="costTypeName">
                <el-input placeholder="" v-model="formData.costTypeName" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="费用名称" prop="costName">
                <el-input placeholder="" v-model="formData.costName" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="开据金额" prop="applicationInvoicedAmount">
                <el-input-number
                  :min="0"
                  :max="formData.invoicedAmount"
                  :precision="2"
                  v-model="formData.applicationInvoicedAmount"
                  :step="1"
                  controls-position="right"
                  class="!w-100%"
                />
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
            <span class="font-size-14px"> 交款方 </span>
          </template>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="交款单位" prop="paymentCompany">
                <el-input placeholder="" v-model="formData.paymentCompany" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="交款人" prop="paymentUname">
                <el-input placeholder="" v-model="formData.paymentUname" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="地址" prop="paymentAddress">
                <el-input placeholder="" v-model="formData.paymentAddress" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="电话" prop="paymentPhone">
                <el-input placeholder="" v-model="formData.paymentPhone" />
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
            <span class="font-size-14px"> 收款方 </span>
          </template>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="收款单位" prop="collectionCompanyId">
                <el-select v-model="formData.collectionCompanyId" placeholder="" clearable>
                  <el-option
                    v-for="item in formData.payeeUnitList"
                    :key="item.id"
                    :label="item.companyName"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="收款人" prop="collectionUname">
                <el-input placeholder="" v-model="formData.collectionUname" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="地址" prop="collectionAddress">
                <el-input placeholder="" v-model="formData.collectionAddress" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="电话" prop="collectionPhone">
                <el-input placeholder="" v-model="formData.collectionPhone" />
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
            <span class="font-size-14px"> 备注 </span>
          </template>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="" prop="remark">
                <el-input placeholder="请输入" v-model="formData.remark" type="textarea" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-card>
      </el-form>
      <template #footer>
        <el-button @click="closed">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </template>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
defineOptions({ name: 'AddOrgBillReceiptForm' })
import { DICT_TYPE, getIntDictOptions, getStrDictOptions } from '@/utils/dict'
import { orgBillReceiptApi } from '@/api/bus/orgBillReceipt'
import { formatDate } from '@/utils/formatTime'
import { co } from 'dist-test/assets/index-DLnEqsJX'
const message = useMessage() // 消息弹窗
let drawer_show = ref(false)
const formData = ref({})
const receiptNumber = ref('')

const rulesForm = {
  remitType: [{ required: true, message: '请选择支付方式', trigger: 'blur' }],
  applicationInvoicedAmount: [{ required: true, message: '请输入申请开据金额', trigger: 'blur' }],
  collectionCompanyId: [{ required: true, message: '请选择收款单位', trigger: 'blur' }],
  paymentCompany: [{ required: true, message: '请选择付款单位', trigger: 'blur' }]
}

/** 打开抽屉 */
const open = async (info?: any) => {
  formData.value = info
  console.log(info)
  console.log(formData.value)
  receiptNumber.value = info.receiptNumber
  if (!formData.value.issuerTime) formData.value.issuerTime = formatDate(new Date(), 'YYYY-MM-DD')
  drawer_show.value = true
}
const closed = async () => {
  drawer_show.value = false
}
const emit = defineEmits(['success'])
const formRef = ref()
const formLoading = ref(false)
const submit = async () => {
  await formRef.value.validate()
  try {
    formLoading.value = true
    if (formData.value.receiptNumber != receiptNumber.value) formData.value.numberType = 2
    emit('success', formData.value)
    drawer_show.value = false
  } finally {
    formLoading.value = false
  }
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped></style>
<style scoped lang="scss">
.OrgBillReceiptFormBox .el-drawer__header {
  margin-bottom: 20px;
  padding: 0 !important;
}
.OrgBillReceiptFormBox :deep(.el-drawer__body) {
  padding: 0;
  background: #000 !important;
}
::v-deep .el-drawer.rtl {
  background: #6aabc5;
}
</style>
