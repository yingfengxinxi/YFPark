<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="receiptDetail">
    <el-drawer v-model="drawer_show" size="90%" :with-header="true" :show-close="false">
      <template #title>
        <div>
          <div class="flex justify-between items-center">
            <span class="font-size-18px flex items-center c-#000000a6 fw-bold">
              <Icon
                icon="ep:arrow-left-bold"
                :size="20"
                @click="closed"
                class="m-r-20px cursor-pointer"
              />
              收据详情</span
            >
            <div class="flex justify-end items-center">
              <el-button @click="generate">生成</el-button>
              <el-button
                @click="send"
                :disabled="receiptInfo.status == 1 && receiptInfo.status == 5"
                >发出</el-button
              >
              <el-button @click="recovery" :disabled="receiptInfo.status !== 1">回收</el-button>
              <el-button
                @click="toVoid"
                :disabled="receiptInfo.status == 1 && receiptInfo.status == 5"
                >作废</el-button
              >
            </div>
          </div>
          <h4
            class="font-size-22px fw-bold flex justify-between m-0 items-start m-t-30px c-[var(--el-text-color-primary)]"
          >
            <span> 收据编号：{{ receiptInfo?.receiptNumber }} </span>
            <span class="fw-normal">
              <span class="font-size-14px c-#aaaaaa">状态：</span>
              <dict-text
                class="!c-[var(--el-color-primary)] !font-size-28px"
                :type="DICT_TYPE.RECEIPT_STATUS"
                :value="receiptInfo.status"
              />
            </span>
          </h4>
          <div class="flex items-center m-t-20px">
            <span class="font-size-18px c-[var(--el-text-color-primary)] m-r-40px">
              <span class="font-size-14px c-#aaaaaa">开据日期：</span>
              {{ formatDate(receiptInfo.issuerTime, 'YYYY-MM-DD') }}
            </span>
            <span class="font-size-18px c-[var(--el-text-color-primary)] m-r-40px">
              <span class="font-size-14px c-#aaaaaa">开据金额：</span>
              {{ receiptInfo.price }}
            </span>
          </div>
          <div class="flex items-center m-t-20px">
            <span class="font-size-14px c-[var(--el-text-color-primary)] m-r-40px">
              支付方式：
              <dict-text :type="DICT_TYPE.REMIT_TYPE" :value="receiptInfo.remitType" />
            </span>
            <span class="font-size-14px c-[var(--el-text-color-primary)] m-r-40px">
              开据人：
              {{ receiptInfo.issuerUid }}
            </span>
            <span class="font-size-14px c-[var(--el-text-color-primary)] m-r-40px">
              费用名称：
              {{ receiptInfo.costName }}
            </span>
            <span class="font-size-14px c-[var(--el-text-color-primary)] m-r-40px">
              费用类型：
              {{ receiptInfo.costName }}
            </span>
          </div>
        </div>
      </template>
      <el-row :gutter="20" class="lh-30px m-20px m-b-0px !m-l-5px !m-r-5px">
        <el-col :span="12">
          <ContentWrap title="收款方">
            <el-row :gutter="20">
              <el-col :span="12" class="m-t-18px">
                收款单位
                <el-input placeholder="" v-model="receiptInfo.collectionCompany" disabled />
              </el-col>
              <el-col :span="12" class="m-t-18px">
                收款人
                <el-input placeholder="" v-model="receiptInfo.collectionUname" disabled />
              </el-col>
              <el-col :span="12" class="m-t-18px">
                电话
                <el-input placeholder="" v-model="receiptInfo.collectionPhone" disabled />
              </el-col>
              <el-col :span="12" class="m-t-18px">
                地址
                <el-input placeholder="" v-model="receiptInfo.collectionAddress" disabled />
              </el-col>
            </el-row>
          </ContentWrap>
        </el-col>
        <el-col :span="12">
          <ContentWrap title="交款方">
            <el-row :gutter="20">
              <el-col :span="12" class="m-t-18px">
                交款单位
                <el-input placeholder="" v-model="receiptInfo.paymentCompany" disabled />
              </el-col>
              <el-col :span="12" class="m-t-18px">
                交款人
                <el-input placeholder="" v-model="receiptInfo.paymentUname" disabled />
              </el-col>
              <el-col :span="12" class="m-t-18px">
                电话
                <el-input placeholder="" v-model="receiptInfo.paymentPhone" disabled />
              </el-col>
              <el-col :span="12" class="m-t-18px">
                地址
                <el-input placeholder="" v-model="receiptInfo.paymentAddress" disabled />
              </el-col>
            </el-row>
          </ContentWrap>
        </el-col>
      </el-row>
      <ContentWrap class="m-20px m-t-0px" title="款项明细">
        <el-alert
          :title="'金额合计：' + receiptInfo.price + '元'"
          type="info"
          :closable="false"
          class="custom-alert"
        />
        <el-table
          class="m-t-14px"
          :data="paymentDetail"
          border
          v-loading="detailLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="费用类型" prop="costName" />
          <el-table-column label="金额" prop="price" />
        </el-table>
      </ContentWrap>
      <ContentWrap class="m-20px" title="开据账单">
        <el-table
          :data="billingStatement"
          border
          v-loading="detailLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="项目" prop="villageName" />
          <el-table-column label="楼宇" prop="buildName" />
          <el-table-column label="房号" prop="roomNumber" />
          <el-table-column label="租客" prop="ownerName" />
          <el-table-column label="费用类型" prop="costName" />
          <el-table-column label="应收" prop="receivable" />
          <el-table-column label="实收" prop="canReceiptAmount" />
          <el-table-column label="开据金额" prop="applyReceiptAmount" />
          <el-table-column label="应收日期" prop="payDate" :formatter="dateFormatter2" />
          <el-table-column label="操作" fixed="right">
            <template #default="scope">
              <el-button link type="primary" @click="handleCheckOrderDetail(scope.row.billId)">
                查看详情</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </ContentWrap>
      <ContentWrap class="m-20px" title="备注">
        <el-input placeholder="" v-model="receiptInfo.remark" type="textarea" rows="4" disabled />
      </ContentWrap>
      <CheckOrderDetail ref="CheckOrderDetailRef" />
      <el-dialog title="生成收据" width="600px" v-model="visible" @close="onClose">
        <el-form ref="form" label-width="80px" label-position="top">
          <el-form-item label="收据模版">
            <el-select v-model="ByBuildsTemplate" placeholder="">
              <el-option
                v-for="item in ByBuildsTemplateList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button size="small" @click="handleCancel">取 消</el-button>
            <el-button size="small" type="primary" @click="handleOk" :loading="confirmLoading"
              >确 定并下载</el-button
            >
          </div>
        </template>
      </el-dialog>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
import CheckOrderDetail from '@/views/bus/owner/component/checkOrderDetail.vue'
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter2, formatDate } from '@/utils/formatTime'
import { orgBillReceiptApi, ReceiptVO } from '@/api/bus/orgBillReceipt'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { orgBillReceiptTemplateApi } from '@/api/bus/orgBillReceiptTemplate'

const message = useMessage() // 消息弹窗
const emit = defineEmits(['success'])
const detailLoading = ref(false)
const drawer_show = ref(false)
const receiptId = ref()
const receiptInfo = ref<ReceiptVO>({})
const costTypeChildrenList = ref([])
const billingStatement = ref([])
const paymentDetail = ref([
  {
    costName: '',
    price: ''
  }
])

const visible = ref(false)
const confirmLoading = ref(false)
const ByBuildsTemplateList = ref([])
const ByBuildsTemplate = ref()
const open = async (id?: number) => {
  getCostTypeChildrenList()
  receiptId.value = id
  getDetail()
  drawer_show.value = true
}

const getDetail = async () => {
  try {
    detailLoading.value = true
    const data = await orgBillReceiptApi.get({ id: receiptId.value })
    receiptInfo.value = data
    detailLoading.value = false
    console.log(detailLoading.value, receiptInfo.value)
    billingStatement.value[0] = data.billingStatement
    paymentDetail.value[0].costName = data.costName
    paymentDetail.value[0].price = data.price
  } finally {
    detailLoading.value = false
  }
}

const CheckOrderDetailRef = ref()
const handleCheckOrderDetail = (id) => {
  CheckOrderDetailRef.value.open(id)
}

/** 费用类型 */
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}

const closed = async () => {
  drawer_show.value = false
}

/** 生成按钮 */
const generate = async () => {
  visible.value = true
  const data = await orgBillReceiptTemplateApi.getByBuildsTemplateList({
    buildBind: receiptInfo.value.buildId
  })
  ByBuildsTemplateList.value = data
}

const handleCancel = () => {
  visible.value = false
  ByBuildsTemplate.value = {}
}

/** 生成 */
const handleOk = async () => {
  console.log(ByBuildsTemplate.value)
  try {
    confirmLoading.value = true
    await orgBillReceiptApi.generate({
      id: receiptId.value,
      applyTemplateId: ByBuildsTemplate.value
    })
    message.success('生成成功')
    getDetail()
  } finally {
    visible.value = false
    confirmLoading.value = false
  }
}
/** 发出 */
const send = async () => {
  await message.delConfirm('是否发送未发出的收据？')
  await orgBillReceiptApi.send({ id: receiptId.value })
  message.success('发送成功')
  getDetail()
}

/** 回收 */
const recovery = async () => {
  await message.delConfirm('是否回收已发出的收据？')
  await orgBillReceiptApi.recovery({ id: receiptId.value })
  message.success('回收成功')
  getDetail()
}
/** 作废 */
const toVoid = async () => {
  await message.delConfirm('是否作废已发出的收据？')
  await orgBillReceiptApi.toVoid({ id: receiptId.value })
  message.success('作废成功')
  getDetail()
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
