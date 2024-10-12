<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 新建楼宇 -->
  <el-drawer
    v-model="collectShow"
    direction="rtl"
    size="900px"
    title="收支流水列表"
    @close="cancelClick"
  >
    <template #default>
      <!-- <el-form ref="formRef">
        <el-form-item>
          <el-input v-model="ownerInfos.name">
            <template #append>
              <el-button type="primary" icon="Search" @click="getStreamList">
                <Icon icon="ep:search" :size="12" />
              </el-button>
            </template>
          </el-input>
        </el-form-item>
      </el-form> -->
      <el-table
        class="m-t-10px"
        ref="tableRef"
        :data="list"
        cell-class-name="text-12px"
        border
        :header-cell-style="{
          color: '#000000d9',
          fontSize: '14px',
          fontWeight: '500',
          backgroundColor: '#fafafa'
        }"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" fixed />
        <el-table-column label="入账日期" prop="matchDate" width="120" fixed />
        <el-table-column label="借贷标" prop="loanType" align="center" width="120" fixed />
        <el-table-column label="对方单位名称" prop="companyName" align="center" width="120" fixed />
        <el-table-column label="自建/导入" prop="streamSource" width="120" />
        <el-table-column label="发生额" prop="amount" width="120" />
        <el-table-column label="已匹配金额" prop="matchPrice" width="120" />
        <el-table-column label="未匹配金额" prop="nomatchPrice" width="120" />
        <el-table-column label="摘要" prop="abstractc" width="120" />
        <el-table-column label="匹配状态" width="120">
          <template #scoped="scoped">
            {{ scoped.row.matchStatus }}
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getStreamList"
      />
      <el-dialog title="流水匹配" width="600px" v-model="visible" @close="onClose" z-index="100">
        <el-form ref="formRef" :model="formData" :rules="formRules" label-width="80px">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="匹配金额" prop="matchPrice">
                <el-input-number
                  v-model="formData.matchPrice"
                  type="number"
                  size="small"
                  controls-position="right"
                  :precision="2"
                  :min="0.0"
                  :max="MaxPrice"
                  class="!w-100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="匹配时间" prop="matchDate">
                <el-date-picker
                  v-model="formData.matchDate"
                  type="date"
                  size="small"
                  class="!w-100%"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-table
          class="m-t-10px"
          ref="chooseTableRef"
          :data="chooseTableList"
          cell-class-name="text-12px"
          border
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="账单需收" prop="amountToBeCollected" />
          <el-table-column label="流水未匹配" prop="nomatchPrice" />
        </el-table>
        <template #footer>
          <div class="dialog-footer">
            <el-button size="small" @click="handleCancel">取 消</el-button>
            <el-button
              size="small"
              type="primary"
              @click="handleOk"
              :loading="confirmLoading"
              v-hasPermi="['bus:orgBillStreamMiddle:addMiddle']"
              >确 定</el-button
            >
          </div>
        </template>
      </el-dialog>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="cancelClick">取消</el-button>
        <el-button type="primary" @click="confirmClick" :disabled="!multipleSelection.length"
          >匹配</el-button
        >
      </div>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
defineOptions({ name: 'Collection' })
import { formatDate } from '@/utils/formatTime'
const message = useMessage() // 消息弹窗
import * as AreaApi from '@/api/system/area'
import ContentWrap from '@/components/ContentWrap/src/ContentWrap.vue'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { contractOrderApi, contractOrderBillVO } from '@/api/bus/contractOrderBill'
import { orgBillStreamApi } from '@/api/bus/orgBillStream'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { orgBillStreamMiddleApi } from '@/api/bus/orgBillStreamMiddle'
const { t } = useI18n() // 国际化
const collectShow = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
// const RoomInfo = ref()
const total = ref(0) // 列表的总页数
const collectionList = ref([
  {
    feeType: '0',
    receivable: 0.0,
    PendingCollection: 0.0,
    receivablePayment: 0.0,
    receivablePayableAmount: 0.0,
    SurplusToBeBollected: 0.0,
    payDate: '',
    lateFee: 0.0,
    amountToBeCollected: 0
  }
])
const multipleSelection = ref([])
const queryParams = reactive({
  ownerId: '',
  billType: 1,
  matchDateStr: '',
  pageNo: 1,
  pageSize: 10
})
const list = ref([])
const chooseTableList = ref([
  {
    amountToBeCollected: 0,
    nomatchPrice: 0
  }
])
const formData = ref({
  matchPrice: 0.0,
  matchDate: '',
  billId: '',
  streamIds: ''
})
const formRules = reactive({
  matchPrice: [{ required: true, message: '请输入匹配金额', trigger: 'blur' }],
  matchDate: [{ required: true, message: '请选择匹配时间', trigger: 'blur' }]
})
const MaxPrice = ref(0)
const visible = ref(false)
const confirmLoading = ref(false)
const chooseTableRef = ref()
const ViewType = ref('')
/** 打开抽屉 */
const open = async (type?: string, ownerInfo?: any, list?: any) => {
  ViewType.value = type
  // resetForm()
  // RoomInfo.value = roomInfo
  // ownerInfos.value = ownerInfo
  collectionList.value = list
  collectShow.value = true
  // queryParams.villageId = roomInfo.villageId
  queryParams.ownerId = ownerInfo.id
  queryParams.matchDateStr = list[0].matchDate
  getStreamList()
}

const handleSelectionChange = (val) => {
  multipleSelection.value = val
}
const getStreamList = async () => {
  try {
    const data = await orgBillStreamApi.getStreamList(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
  }
}
function cancelClick() {
  collectShow.value = false
  emit('close')
}
const emit = defineEmits(['success', 'close', 'change']) // 定义 success 事件，用于操作成功后的回调
const handleOk = async () => {
  formLoading.value = true
  try {
    if (ViewType.value == 'addCollectionMiddle') {
      await orgBillStreamMiddleApi.addCollectionMiddle(formData.value)
    } else {
      await orgBillStreamMiddleApi.matchBill(formData.value)
    }
    message.success('匹配成功')
    collectShow.value = false
    emit('success')
  } catch {
    message.error('匹配失败')
  } finally {
    visible.value = false
    formLoading.value = false
    tableData.value = []
  }
}
const confirmClick = async () => {
  resetForm()
  formData.value.billId = collectionList.value.map((item) => item.id).join(',')
  formData.value.streamIds = multipleSelection.value.map((item) => item.id).join(',')
  collectionList.value.forEach((item) => {
    chooseTableList.value[0].amountToBeCollected =
      Number(item.amountToBeCollected) + Number(chooseTableList.value[0].amountToBeCollected)
  })
  multipleSelection.value.forEach((item) => {
    chooseTableList.value[0].nomatchPrice =
      Number(item.nomatchPrice) + Number(chooseTableList.value[0].nomatchPrice)
  })
  formData.value.matchDate = formatDate(new Date(), 'YYYY-MM-DD')
  if (chooseTableList.value[0].amountToBeCollected > chooseTableList.value[0].nomatchPrice) {
    formData.value.matchPrice = chooseTableList.value[0].nomatchPrice
    MaxPrice.value = formData.value.matchPrice
  } else {
    MaxPrice.value = chooseTableList.value[0].amountToBeCollected
    formData.value.matchPrice = chooseTableList.value[0].amountToBeCollected
  }
  visible.value = true
}

const handleCancel = () => {
  visible.value = false
}

const formRef = ref()
const resetForm = () => {
  formData.value = {
    matchPrice: 0.0,
    matchDate: '',
    billId: '',
    streamIds: ''
  }
  chooseTableList.value[0].amountToBeCollected = 0
  chooseTableList.value[0].nomatchPrice = 0
  formRef.value?.resetFields()
}

/** 初始化 **/
onMounted(() => {})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

// 切换选项卡
const tabSelct = (index: number) => {
  if (index == 1) {
    activeIndex.value = Number(activeIndex.value) + 1 + ''
  } else {
    activeIndex.value = Number(activeIndex.value) - 1 + ''
  }
}

const tableData = ref([])
// 新增一行
const addTableData = () => {
  var myDate = new Date()
  var startYear = myDate.getFullYear()
  const newRow = {
    year: startYear,
    target: null,
    status: true
  }
  tableData.value.push(newRow)
}
// 删除
const deleteTableData = (row) => {
  const index = tableData.value.indexOf(row)
  if (index !== -1) {
    tableData.value.splice(index, 1)
  }
}

const EndTableData = (row) => {
  if (!row.year || !row.target) {
    return message.warning('请填写年份和营收目标~')
  }
  let data = checkData()
  if (data) {
    row.status = false
  } else {
    message.warning(row.year + '年份的营收目标您已经添加过了哦~')
  }
}

const checkData = () => {
  for (var i = 0; i < tableData.value.length; i++) {
    for (var j = i + 1; j < tableData.value.length; j++) {
      if (tableData.value[i].year == tableData.value[j].year) {
        return false
      }
    }
  }
  return true
}
</script>
<style scoped lang="scss">
.box {
  position: relative;

  .icon {
    position: absolute;
    bottom: 10px;
    right: 19px;
  }
}
::v-deep .el-drawer.rtl {
  background: #6aabc5;
}

.investmen-personne {
  width: 100%;
  // border: 1px solid #d9d9d9;
  border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
  padding: 5px 18px 4px 5px;
  cursor: pointer;
  line-height: 1.7;
  position: relative;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color)) inset;
}

.closeCircle {
  position: absolute;
  right: 2px;
  top: 0;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 16px;
  cursor: pointer;
}
</style>
<style>
.el-input-number .el-input__inner {
  text-align: left !important;
}
</style>
