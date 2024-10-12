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
      size="70%"
      :title="drawer_title"
      @closed="closed"
    >
      <ContentWrap title="申请开据的收据">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            <el-button type="primary" size="small" @click="getReceiptNumber">
              生成收据编号
            </el-button>
          </div>
        </template>
        <el-form ref="formRef" :model="list[0]" :rules="rulesForm">
          <el-table :data="list" border size="small" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column label="交款单位" prop="paymentCompany" />
            <el-table-column label="收据编号" prop="receiptNumber" />
            <el-table-column label="可开据金额" prop="invoicedAmount" />
            <el-table-column label="申请开据金额" prop="applicationInvoicedAmount" width="160px">
              <template #default="scope">
                <el-form-item
                  label=""
                  prop="applicationInvoicedAmount"
                  :rules="[{ required: true, message: '请输入申请开据金额', trigger: 'blur' }]"
                >
                  <el-input-number
                    :min="0"
                    :max="scope.row.invoicedAmount"
                    :precision="2"
                    v-model="list[0].applicationInvoicedAmount"
                    :step="1"
                    controls-position="right"
                    class="!w-100%"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="费用类型" prop="costTypeName" />
            <el-table-column label="收款单位" prop="collectionCompanyId">
              <template #default="scope">
                <el-form-item
                  label=""
                  prop="collectionCompanyId"
                  :rules="[{ required: true, message: '请选择收款单位', trigger: 'blur' }]"
                >
                  <el-select
                    v-model="list[0].collectionCompanyId"
                    placeholder="请选择收款单位"
                    class="!w-100%"
                  >
                    <el-option
                      v-for="item in scope.row.payeeUnitList"
                      :key="item.id"
                      :label="item.companyName"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="汇款方式" prop="collectionCompanyId">
              <template #default="">
                <el-form-item
                  label=""
                  prop="collectionCompanyId"
                  :rules="[{ required: true, message: '请选择汇款方式', trigger: 'blur' }]"
                >
                  <el-select v-model="list[0].remitType" placeholder="请选择汇款方式" clearable>
                    <el-option
                      v-for="item in getStrDictOptions(DICT_TYPE.REMIT_TYPE)"
                      :label="item.label"
                      :value="item.value"
                      :key="item.value"
                    />
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="备注" value="remark" />
            <el-table-column label="操作" width="100" fixed="right">
              <template #default="scope">
                <el-button type="primary" link @click="toDo(scope.row)">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form>
      </ContentWrap>
      <template #footer>
        <el-button @click="closed">取消</el-button>
        <el-button type="primary" @click="submit" v-hasPermi="['bus:orgBillReceipt:create']"
          >确定</el-button
        >
      </template>
    </el-drawer>
  </div>
  <UpdateOrgBillReceiptForm ref="formRef_updateOrgBill" @success="getInfo" />
</template>
<script setup lang="ts">
defineOptions({ name: 'AddOrgBillReceiptForm' })
import { DICT_TYPE, getIntDictOptions, getStrDictOptions } from '@/utils/dict'
import { orgBillReceiptApi } from '@/api/bus/orgBillReceipt'
import { formatDate } from '@/utils/formatTime'
import UpdateOrgBillReceiptForm from './updateOrgBillReceiptForm.vue'
const message = useMessage() // 消息弹窗
let drawer_show = ref(false)
const drawer_title = ref('')
const BillId = ref(0)

const rulesForm = {
  applicationInvoicedAmount: [{ required: true, message: '请输入申请开据金额', trigger: 'blur' }],
  collectionCompanyId: [{ required: true, message: '请选择收款单位', trigger: 'blur' }]
}

const list = ref([])
/** 打开抽屉 */
const open = async (orderId?: number) => {
  console.log(orderId)
  drawer_show.value = true
  drawer_title.value = '新增收据'
  BillId.value = orderId
  getList()
}
const formRef_updateOrgBill = ref()
const toDo = async (row) => {
  console.log(row)
  formRef_updateOrgBill.value.open(row)
}
const selectData = ref([])
const handleSelectionChange = (val) => {
  console.log(val)
  selectData.value = val
}

const getInfo = async (formData: any) => {
  list.value[0] = formData
}

const closed = async () => {
  drawer_show.value = false
}
const getReceiptNumber = async () => {
  const data = await orgBillReceiptApi.getReceiptNumber({
    buildId: list.value[0].build
  })
  list.value[0].receiptNumber = data
  list.value[0].numberType = 1
}
const emit = defineEmits(['success'])
const formRef = ref()
const formLoading = ref(false)
const submit = async () => {
  await formRef.value.validate()
  if (!selectData.value.length) {
    message.error('必须选择至少一条对其进行操作~')
    return
  }
  if (!list.value[0].receiptNumber) {
    message.error('请生成编号再进行开据~')
    return
  }
  try {
    formLoading.value = true
    console.log(list.value[0])
    await orgBillReceiptApi.create(list.value[0])
    message.success('添加成功')
    emit('success')
    drawer_show.value = false
  } finally {
    formLoading.value = false
  }
}

const getList = async () => {
  const data = await orgBillReceiptApi.getLssue({
    billIds: BillId.value
  })
  list.value[0] = data
  console.log(data)
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped>
::v-deep .el-card__body {
  padding: 0 !important;
}
</style>
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
