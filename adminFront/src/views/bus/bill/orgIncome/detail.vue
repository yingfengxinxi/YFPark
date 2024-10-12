<template>
  <div class="receiptDetail">
    <el-drawer
      v-model="drawer_show"
      size="90%"
      :with-header="true"
      :show-close="false"
      @close="closed"
    >
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
              {{ drawer_title }}</span
            >
          </div>
          <div class="flex justify-between items-center">
            <h4
              class="font-size-22px fw-bold flex justify-between m-0 items-start m-t-30px c-[var(--el-text-color-primary)]"
            >
              <span>
                {{ isConfirm == '待确认' ? '当前需要确认的流水金额' : '当前已确认的流水金额' }}：{{
                  orgIncomeInfo?.amount
                }}
                元</span
              >
            </h4>
            <el-button type="primary" @click="handleConfirm">{{
              isConfirm == '待确认' ? '确认流水' : '取消确认'
            }}</el-button>
          </div>
        </div>
      </template>
      <ContentWrap
        :loading="detailLoading"
        title="账单信息"
        class="m-20px"
        v-if="orgIncomeInfo.billId"
      >
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            匹配日期: {{ billInfo?.matchDate || '--' }}
          </div>
        </template>
        <template #default>
          <el-row :gutter="20">
            <el-col :span="6" class="m-b-20px">
              <div class="c-#00000073 font-size-14px m-b-15px">账单状态</div>
              <div class="font-size-28px">{{ billInfo?.billStatus || '--' }}</div>
            </el-col>
            <el-col :span="6" class="m-b-20px">
              <div class="c-#00000073 font-size-14px m-b-15px">应收金额（元）</div>
              <div class="font-size-24px">{{ billInfo?.receivable || '--' }}</div>
            </el-col>
            <el-col :span="6" class="m-b-20px">
              <div class="c-#00000073 font-size-14px m-b-15px">需收金额（元）</div>
              <div class="font-size-24px">{{ billInfo.amountToBeCollected || '--' }}</div>
            </el-col>
            <el-col :span="6" class="m-b-20px">
              <div class="c-#00000073 font-size-14px m-b-15px">应收日期</div>
              <div class="font-size-24px">{{ billInfo?.payDate || '--' }}</div>
            </el-col>
            <el-col :span="6" class="m-b-20px">
              <div class="c-#00000073 font-size-14px m-b-15px">产生滞纳金</div>
              <div class="font-size-24px">{{ billInfo?.generateLateFee || '--' }}</div>
            </el-col>
            <el-col :span="6" class="m-b-20px">
              <div class="c-#00000073 font-size-14px m-b-15px">
                应收滞纳金金额（元）
                <el-tooltip
                  content="滞纳金=（应收金额 - 已缴金额）*拖欠时间*比例"
                  placement="top"
                  effect="dark"
                  ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                /></el-tooltip>
              </div>
              <div class="font-size-24px">{{ billInfo?.lateFee || '--' }}</div>
            </el-col>
            <el-col :span="6" class="m-b-20px">
              <div class="c-#00000073 font-size-14px m-b-15px"
                >需收滞纳金金额（元）
                <el-tooltip
                  content="滞纳金=（应收金额 - 已缴金额）*拖欠时间*比例"
                  placement="top"
                  effect="dark"
                  ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999" /></el-tooltip
              ></div>
              <div class="font-size-24px">{{ billInfo?.lateFee || '--' }}</div>
            </el-col>
          </el-row>
          <el-divider border-style="dashed" />
          <el-row :gutter="20" class="font-size-16px">
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">费用类型：</div>
              {{ billInfo?.feeTypeName || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">账单金额（元）：</div>
              {{ billInfo.receivable || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">入账日期：</div>
              {{ billInfo?.payTime || '--' }}
            </el-col>
            <el-col :span="4" class="word-break-all m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">付款方：</div>
              <el-button
                link
                type="primary"
                @click.stop="OwnerDetail(billInfo.ownerId, billInfo.isPersonal)"
                >{{ billInfo.payName }}</el-button
              >
            </el-col>
            <el-col :span="4" class="word-break-all m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">合同编号：</div>
              <el-button link type="primary" @click="handleContractDetail(billInfo.contractId)">
                {{ billInfo.contractNumber }}
                <Icon
                  icon="ep:copy-document"
                  :size="12"
                  color="var(--el-color-primary)"
                  class="m-l-10px m-r-10px cursor-pointer"
                  @click.stop="copy('')"
                />
              </el-button>
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">起算天数：</div>
              {{ billInfo?.startingLateFeeDay || '--' }}
            </el-col>

            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">滞纳金比例：</div>
              {{ billInfo?.lateFeeRatio || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">滞纳金上限：</div>
              {{ billInfo?.upperLimitLateFee || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">经办人：</div>
              {{ billInfo?.operatorName || '--' }}
            </el-col>
            <el-col :span="4" class="word-break-all m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">账单编号：</div>
              {{ billInfo?.orderNumber || '--'
              }}<Icon
                icon="ep:copy-document"
                :size="12"
                color="var(--el-color-primary)"
                class="m-l-10px m-r-10px cursor-pointer"
                @click="copy(billInfo?.orderNumber)"
              />
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">账单备注：</div>
              {{ billInfo?.remark || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">计费周期：</div>
              {{ billInfo?.payStartDate || '--' }} - {{ billInfo?.payEndDate || '--' }}
            </el-col>
          </el-row>
        </template>
      </ContentWrap>
      <ContentWrap
        :loading="detailLoading"
        title="流水信息"
        class="m-20px"
        v-if="orgIncomeInfo.streamMiddleId"
      >
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            <el-tag type="primary">完全匹配</el-tag>
          </div>
        </template>
        <template #default>
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="c-#00000073 font-size-14px m-b-15px">总金额（元）</div>
              <div class="font-size-24px">{{ streamInfo?.amount || '-- ' }}</div>
            </el-col>
            <el-col :span="6">
              <div class="c-#00000073 font-size-14px m-b-15px">已匹配金额（元）</div>
              <div class="font-size-24px">{{ streamInfo?.matchPrice || '--' }}</div>
            </el-col>
            <el-col :span="6">
              <div class="c-#00000073 font-size-14px m-b-15px">未匹配金额（元）</div>
              <div class="font-size-24px">{{ streamInfo?.nomatchPrice || '--' }}</div>
            </el-col>
          </el-row>
          <el-divider border-style="dashed" />
          <el-row :gutter="20">
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">借贷标：</div>
              {{ getDictLabel(DICT_TYPE.LOAN_TYPE, streamInfo?.loanType) }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">发生额：</div>
              {{ streamInfo?.amount || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">入账日期：</div>
              {{ streamInfo?.incomeDate || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">对方单位名称：</div>
              {{ streamInfo?.companyName || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">汇款方式：</div>
              {{ getDictLabel(DICT_TYPE.REMIT_TYPE, streamInfo?.remitType) }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">对方账号：</div>
              {{ streamInfo?.otherAccount || '--' }}
            </el-col>

            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">凭证号：</div>
              {{ streamInfo?.voucherNo || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">收据编号：</div>
              {{ streamInfo?.receiptNo || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">联系人：</div>
              {{ streamInfo?.linkName || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">流水账户：</div>
              {{ streamInfo?.accountName || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">摘要：</div>
              {{ streamInfo?.abstractc || '--' }}
            </el-col>
            <el-col :span="4" class="m-b-20px">
              <div class="c-#00000073 font-size-12px m-b-15px">备注：</div>
              {{ streamInfo?.remark || '--' }}
            </el-col>
          </el-row>
        </template>
      </ContentWrap>
    </el-drawer>
  </div>
  <TenantDetails ref="TenantDetailsRef" @select-pick="getDetail" />
  <ContractDetail ref="ContractDetailRef" @select-pick="getDetail" />
</template>
<script setup lang="ts">
import { DICT_TYPE, getDictLabel, getDictObj } from '@/utils/dict'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import { orgIncomeApi, orgIncomeVO } from '@/api/bus/orgIncome/index'
import ContractDetail from '@/views/contract/contractList/contractDetail_drawer.vue'
import { contractOrderApi } from '@/api/bus/contractOrderBill/index'
import { useClipboard } from '@vueuse/core'
import { orgBillStreamApi } from '@/api/bus/orgBillStream/index'
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const emit = defineEmits(['success'])
const detailLoading = ref(false)
const drawer_show = ref(false)
const drawer_title = ref('')
const IncomeId = ref()
const orgIncomeInfo = ref<orgIncomeVO>({})
const isConfirm = ref()
const billInfo = ref<any>({})
const streamInfo = ref<any>({})

/** 确认流水、取消确认 */
const handleConfirm = async () => {
  try {
    // 删除的二次确认
    await message.confirm(
      isConfirm.value == '待确认'
        ? '该匹配的流水确认后，流水已匹配的部分将不能重新匹配账单、不能关闭流水。对应被匹配的账单也不能关闭、调整、解除匹配该收支流水'
        : '取消后流水可以重新匹配账单，以及关闭。对应被匹配的账单也可以关闭、调整、解除匹配该流水'
    )
    // 发起删除
    await orgIncomeApi.confirmIncome({
      ids: IncomeId.value,
      isConfirm: isConfirm.value == '待确认' ? '2' : '1'
    })
    isConfirm.value = isConfirm.value == '待确认' ? '已确认' : '待确认'
    message.success(t('common.success'))
    // 刷新列表
    await getDetail()
  } catch {}
}

const open = async (id?: number, type?: number) => {
  IncomeId.value = id
  isConfirm.value = type
  drawer_show.value = true
  if (type == '待确认') {
    drawer_title.value = '收入确认详情'
  } else {
    drawer_title.value = '支出确认详情'
  }
  getDetail()
}

/** 账单信息 */
const getIncomeDetail = async () => {
  const data = await contractOrderApi.incomeDetail({ billId: orgIncomeInfo.value.billId })
  console.log(data, 'daata')
  billInfo.value = data
}

/** 流水信息 */
const getIncomeStreamDetail = async () => {
  const data = await orgBillStreamApi.incomeStreamDetail({
    streamMiddleId: orgIncomeInfo.value.streamMiddleId
  })
  streamInfo.value = data
}
const getDetail = async () => {
  try {
    detailLoading.value = true
    const data = await orgIncomeApi.getDetail(IncomeId.value)
    orgIncomeInfo.value = data
    if (data.billId) getIncomeDetail()
    if (data.streamMiddleId) getIncomeStreamDetail()
  } finally {
    detailLoading.value = false
  }
}
/** 租客详情 */
const TenantDetailsRef = ref()
const OwnerDetail = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}

const ContractDetailRef = ref()
const handleContractDetail = async (id: number) => {
  ContractDetailRef.value.open(id)
}
/** 复制 **/
const copy = async (text: string) => {
  if (navigator.clipboard) {
    const { copy } = useClipboard({ source: text })
    copy()
  } else {
    const input = document.createElement('input')
    input.setAttribute('value', text)
    document.body.appendChild(input)
    input.select()
    document.execCommand('copy')
    document.body.removeChild(input)
  }

  message.success(t('common.copySuccess'))
}

const closed = async () => {
  drawer_show.value = false
  emit('success')
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
.word-break-all {
  word-break: break-all;
}
</style>
