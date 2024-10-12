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
            <span class="font-size-18px flex items-center c-#000000a6 fw-bold">收支流水详情</span>
            <div class="flex justify-end items-center">
              <el-button type="danger" @click="deleteStream" v-if="BillStreamInfo?.isClose == '0'"
                >删除流水</el-button
              >
              <el-button
                plain
                type="danger"
                @click="closeStream"
                v-if="BillStreamInfo?.isClose == '1'"
                >关闭流水</el-button
              >
            </div>
          </div>
          <el-divider border-style="dashed" />
          <h4
            class="font-size-22px fw-bold flex justify-between m-0 items-start c-[var(--el-text-color-primary)] border-sas"
          >
            <span> 对方单位名称：{{ BillStreamInfo?.companyName }} </span>
          </h4>
          <el-divider border-style="dashed" />
          <div class="flex items-center m-t-20px text-center lh-40px">
            <div class="font-size-22px c-[var(--el-text-color-primary)] m-r-40px">
              <div class="font-size-14px c-#aaaaaa">总金额（元）</div>
              {{ BillStreamInfo.amount }}
            </div>
            <div class="font-size-22px c-[var(--el-text-color-primary)] m-r-40px">
              <div class="font-size-14px c-#aaaaaa">已匹配金额（元）</div>
              {{ BillStreamInfo.matchPrice }}
            </div>
            <div class="font-size-22px c-[var(--el-text-color-primary)] m-r-40px">
              <div class="font-size-14px c-#aaaaaa">未匹配金额（元）</div>
              {{ BillStreamInfo.nomatchPrice }}
            </div>
          </div>
        </div>
      </template>
      <ContentWrap class="m-20px" title="流水信息">
        <el-descriptions title="" direction="vertical" :column="4">
          <el-descriptions-item label="借贷标:" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.loanType || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="发生额:" label-class-name="!c-#aaaaaa">{{
            BillStreamInfo?.amount || '--'
          }}</el-descriptions-item>
          <el-descriptions-item label="入账时间" label-class-name="!c-#aaaaaa">{{
            BillStreamInfo?.incomeDate || '--'
          }}</el-descriptions-item>
          <el-descriptions-item label="对方单位名称" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.companyName || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="汇款方式" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.remitType || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="对方账号" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.otherAccount || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="凭证号" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.voucherNo || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="收据编号" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.receiptNo || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="联系人" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.linkName || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="流水账户" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.accountName || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="子账户名称" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.sonAccountName || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="子账户" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.sonAccount || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="摘要" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.abstractc || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="备注" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.remark || '--' }}
          </el-descriptions-item>
          <el-descriptions-item label="专项预存" label-class-name="!c-#aaaaaa">
            {{ BillStreamInfo?.singleType || '--' }}
          </el-descriptions-item>
        </el-descriptions>
      </ContentWrap>
      <ContentWrap class="m-20px" title="匹配账单">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            <div class="m-r-20px">
              <el-checkbox
                v-model="BillStreamMiddleParams.matchStatus"
                :value="1"
                @change="getBillStreamMiddleCancel"
                :true-value="1"
                :false-value="0"
              >
                <span class="font-size-14px">显示取消匹配账单</span>
              </el-checkbox>
            </div>
            <el-button @click="openBillStream()" v-hasPermi="['bus:orgBillStreamMiddle:addMiddle']">
              匹配
            </el-button>
          </div>
        </template>
        <el-table
          :data="orgBillStreamMiddleList"
          border
          v-loading="orgBillStreamMiddleLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="对方名称" prop="companyName">
            <template #default="scope">
              {{ scope.row.companyName || '--' }}
            </template>
          </el-table-column>
          <el-table-column label="费用类型" prop="costName">
            <template #default="scope">
              {{ scope.row.costName || '--' }}
            </template>
          </el-table-column>
          <el-table-column label="费用周期" prop="incomeDate">
            <template #default="scope">
              {{ formatDate(scope.row.payStartDate, 'YYYY-MM-DD') }} -
              {{ formatDate(scope.row.payEndDate, 'YYYY-MM-DD') }}
            </template>
          </el-table-column>
          <el-table-column label="应收金额" prop="receivable" />
          <el-table-column label="匹配金额" prop="matchPrice" />
          <el-table-column label="匹配日期" prop="matchDate" :formatter="dateFormatter2" />
          <el-table-column label="取消匹配日期" prop="cancelMatchDate" :formatter="dateFormatter2">
            <template #default="scope">
              {{ formatDate(scope.row.cancelMatchDate, 'YYYY-MM-DD') || '--' }}
            </template>
          </el-table-column>
          <el-table-column label="匹配状态" prop="matchStatus">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.MATCH_STATUS" :value="scope.row.matchStatus" />
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right">
            <template #default="scope">
              <template v-if="scope.row.matchStatus == 4"> -- </template>
              <template v-else>
                <el-button
                  link
                  type="danger"
                  v-hasPermi="['bus:orgBillStreamMiddle:addMiddle']"
                  @click="handleMatchRemove(scope.row.id)"
                  v-if="scope.row.matchStatus != 4"
                >
                  取消匹配</el-button
                >
              </template>
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="orgBillStreamMiddleTotal"
          v-model:page="BillStreamMiddleParams.pageNo"
          v-model:limit="BillStreamMiddleParams.pageSize"
          @pagination="getorgBillStreamMiddleList"
        />
      </ContentWrap>
      <ContentWrap class="m-20px" title="附件信息">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
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
          <el-table-column label="操作人" prop="id" />
          <el-table-column label="操作时间" prop="createTime" :formatter="dateFormatter" />
          <el-table-column label="操作" fixed="right">
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
              <el-button link type="danger" @click="handleBillAnnexRemove(scope.row.id)">
                删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="BillAnnexListTotal"
          v-model:page="BillAnnexListParams.pageNo"
          v-model:limit="BillAnnexListParams.pageSize"
          @pagination="getBillAnnexList"
        />
      </ContentWrap>

      <CheckOrderDetail ref="CheckOrderDetailRef" />
      <el-dialog title="关闭流水" width="600px" v-model="visible" @close="handleCancel">
        <el-input placeholder="请输入关闭流水原因" type="textarea" rows="4" v-model="closeReason" />

        <template #footer>
          <div class="dialog-footer">
            <el-button size="small" @click="handleCancel">取 消</el-button>
            <el-button size="small" type="primary" @click="handleOk" :loading="confirmLoading"
              >确 定</el-button
            >
          </div>
        </template>
      </el-dialog>
      <el-dialog
        title="删除流水"
        width="600px"
        v-model="deleteStreamVisible"
        @close="handleCancelDeleteStream"
      >
        <el-input
          placeholder="请输入删除原因"
          type="textarea"
          rows="4"
          v-model="deleteStreamReason"
        />

        <template #footer>
          <div class="dialog-footer">
            <el-button size="small" @click="handleCancelDeleteStream">取 消</el-button>
            <el-button
              size="small"
              type="primary"
              @click="handleOkDeleteStream"
              :loading="confirmDeleteStreamLoading"
              >确 定</el-button
            >
          </div>
        </template>
      </el-dialog>
      <MatchBillList ref="matchBillListRef" @success="getorgBillStreamMiddleList" />
      <AddBillAnnexForm ref="AddBillAnnexFormRef" @success="getBillAnnexList" />
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
import CheckOrderDetail from '@/views/bus/owner/component/checkOrderDetail.vue'
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter2, formatDate, dateFormatter } from '@/utils/formatTime'
import { orgBillStreamApi, BillStreamVO } from '@/api/bus/orgBillStream'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { orgBillReceiptTemplateApi } from '@/api/bus/orgBillReceiptTemplate'
import { orgBillStreamMiddleApi } from '@/api/bus/orgBillStreamMiddle'
import AddBillAnnexForm from '@/views/bus/owner/component/addBillAnnexForm.vue'
import { orgBillAnnexApi } from '@/api/bus/orgBillAnnex'
const message = useMessage() // 消息弹窗
const emit = defineEmits(['success'])
const detailLoading = ref(false)
const drawer_show = ref(false)
const streamId = ref()
const BillStreamInfo = ref<BillStreamVO>({})
const costTypeChildrenList = ref([])
const billingStatement = ref([])
const paymentDetail = ref([
  {
    costName: '',
    price: ''
  }
])

const closeReason = ref('')
const deleteStreamReason = ref('')
const orgBillStreamMiddleList = ref([])
const orgBillStreamMiddleLoading = ref(false)
const BillStreamMiddleParams = reactive({
  pageNo: 1,
  pageSize: 10,
  streamId: streamId.value,
  matchStatus: ''
})
const orgBillStreamMiddleTotal = ref(0)

const visible = ref(false)
const deleteStreamVisible = ref(false)
const confirmLoading = ref(false)
const BillAnnexListLoading = ref(false)
const BillAnnexListParams = reactive({
  pageNo: 1,
  pageSize: 10
})
const BillAnnexListTotal = ref(0)
const BillAnnexList = ref([])
const open = async (id?: number) => {
  getCostTypeChildrenList()
  streamId.value = id
  BillStreamMiddleParams.streamId = id
  getDetail()
  getorgBillStreamMiddleList()
  getBillAnnexList()
  drawer_show.value = true
}

/** 取消匹配 */
const handleMatchRemove = async (data) => {
  try {
    const res = await orgBillStreamMiddleApi.cancelMatch({
      id: data,
      cancelMatchDate: formatDate(new Date(), 'YYYY-MM-DD')
    })
    getorgBillStreamMiddleList()
    console.log(res)
    message.success('取消匹配成功')
  } catch (error) {
    message.error('取消匹配失败')
  }
}

const getDetail = async () => {
  try {
    detailLoading.value = true
    const data = await orgBillStreamApi.getBillStream(streamId.value)
    BillStreamInfo.value = data
    detailLoading.value = false
  } finally {
    detailLoading.value = false
  }
}
// 账单附件列表
const getBillAnnexList = async () => {
  try {
    BillAnnexListLoading.value = true
    const data = await orgBillAnnexApi.getBillAnnexList({
      sourceId: streamId.value,
      type: 2,
      pageNo: BillAnnexListParams.pageNo,
      pageSize: BillAnnexListParams.pageSize
    })
    BillAnnexList.value = data.list
    BillAnnexListTotal.value = data.total
  } catch (error) {
  } finally {
    BillAnnexListLoading.value = false
  }
}
//新增附件
const AddBillAnnexFormRef = ref()
const addFile = async () => {
  AddBillAnnexFormRef.value.open(streamId.value, 2)
}

// 删除附件
const handleBillAnnexRemove = async (id: any) => {
  try {
    await orgBillAnnexApi.delete(id)
    message.success('删除成功')
    getBillAnnexList()
  } catch (error) {}
}
const matchBillListRef = ref()
const openBillStream = async () => {
  matchBillListRef.value.open(
    streamId.value,
    BillStreamInfo.value.ownerId,
    BillStreamInfo.value.nomatchPrice
  )
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
/** 显示取消匹配列表 */
const getBillStreamMiddleCancel = async (val) => {
  if (val == '0') {
    BillStreamMiddleParams.value.matchStatus = ''
  }
  getorgBillStreamMiddleList()
}
/** 匹配账单 */
const getorgBillStreamMiddleList = async () => {
  const data = await orgBillStreamMiddleApi.page(BillStreamMiddleParams)
  console.log(data.list)
  orgBillStreamMiddleList.value = data.list
  orgBillStreamMiddleTotal.value = data.total
}

/** 生成按钮 */
const closeStream = async () => {
  visible.value = true
}

/** 删除流水 */
const deleteStream = async () => {
  deleteStreamVisible.value = true
  deleteStreamReason.value = ''
}

const handleCancel = () => {
  visible.value = false
  closeReason.value = ''
}

const handleCancelDeleteStream = () => {
  deleteStreamVisible.value = false
  deleteStreamReason.value = ''
}

/** 删除流水提交 */
const confirmDeleteStreamLoading = ref(false)
const handleOkDeleteStream = async () => {
  try {
    confirmDeleteStreamLoading.value = true
    await orgBillStreamApi.deleteBillStream({
      id: streamId.value,
      closeReason: closeReason.value
    })
    message.success('关闭成功')
    emit('success')
    drawer_show.value = false
  } finally {
    deleteStreamVisible.value = false
    confirmDeleteStreamLoading.value = false
  }
}

/** 关闭流水提交 */
const handleOk = async () => {
  try {
    confirmLoading.value = true
    await orgBillStreamApi.closeStream({
      id: streamId.value,
      closeReason: closeReason.value
    })
    message.success('关闭成功')
    getDetail()
  } finally {
    visible.value = false
    confirmLoading.value = false
  }
}
/** 发出 */
const send = async () => {
  await message.delConfirm('是否发送未发出的收据？')
  await orgBillStreamApi.send({ id: streamId.value })
  message.success('发送成功')
  getDetail()
}

/** 回收 */
const recovery = async () => {
  await message.delConfirm('是否回收已发出的收据？')
  await orgBillStreamApi.recovery({ id: streamId.value })
  message.success('回收成功')
  getDetail()
}
/** 作废 */
const toVoid = async () => {
  await message.delConfirm('是否作废已发出的收据？')
  await orgBillStreamApi.toVoid({ id: streamId.value })
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
