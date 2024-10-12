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
    append-to-body
    direction="rtl"
    size="80%"
    title="账单列表"
    @close="cancelClick"
  >
    <template #default>
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
        <el-table-column label="园区名称" prop="villageName" width="120" fixed />
        <el-table-column label="建筑名称" prop="buildName" align="center" width="120" fixed />
        <el-table-column label="付款方" prop="ownerName" align="center" width="120" fixed />
        <el-table-column label="房号" prop="roomName" width="120" />
        <el-table-column label="费用类型" prop="feeType" width="120" />
        <el-table-column label="应收金额" prop="receivable" width="120" />
        <el-table-column label="已匹配金额" prop="matchPrice" width="120" />
        <el-table-column label="应收时间" prop="payDate" :formatter="dateFormatter2" width="120" />
        <el-table-column label="计费周期" width="240">
          <template #default="scope">
            {{ formatDate(scope.row.payStartDate, 'YYYY-MM-DD') }} -
            {{ formatDate(scope.row.payEndDate, 'YYYY-MM-DD') }}
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
                  :disabled-date="disabledDate"
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
          <el-table-column label="账单未匹配" prop="amountToBeCollected" />
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
defineOptions({ name: 'MatchBillList' })
const message = useMessage() // 消息弹窗
import { dateFormatter2, formatDate } from '@/utils/formatTime'
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
const collectionList = ref([])
const multipleSelection = ref([])
const queryParams = reactive({
  ownerId: '',
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
  billIds: '',
  streamId: ''
})
const formRules = reactive({
  matchPrice: [{ required: true, message: '请输入匹配金额', trigger: 'blur' }],
  matchDate: [{ required: true, message: '请选择匹配时间', trigger: 'blur' }]
})
const MaxPrice = ref(0)
const visible = ref(false)
const confirmLoading = ref(false)
const chooseTableRef = ref()
const nomatchPrice = ref(0)
const streamId = ref('')
const disabledDate = (time) => {
  return time.getTime() < Date.now() - 8.64e7
}

/** 打开抽屉 */
const open = async (id?: string, ownerId?: string, Price?: number) => {
  streamId.value = id
  queryParams.ownerId = ownerId
  nomatchPrice.value = Price
  collectShow.value = true
  getStreamList()
}

const handleSelectionChange = (val) => {
  multipleSelection.value = val
  console.log(val)
}
const getStreamList = async () => {
  try {
    const data = await contractOrderApi.matchBillList(queryParams)
    list.value = data.list
    total.value = data.total
    console.log(data.list)
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
    await orgBillStreamMiddleApi.matchBill(formData.value)
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
  console.log(nomatchPrice.value, 'nomatchPrice.value')
  formData.value.streamId = streamId.value
  formData.value.billIds = multipleSelection.value.map((item) => item.billId).join(',')
  chooseTableList.value[0].nomatchPrice = nomatchPrice.value
  formData.value.matchDate = formatDate(new Date(), 'YYYY-MM-DD')
  chooseTableList.value[0].amountToBeCollected = multipleSelection.value.reduce(
    (pre, cur) => pre + (Number(cur.receivable) - Number(cur.matchPrice)),
    0
  )
  MaxPrice.value =
    chooseTableList.value[0].amountToBeCollected > chooseTableList.value[0].nomatchPrice
      ? chooseTableList.value[0].nomatchPrice
      : chooseTableList.value[0].amountToBeCollected
  formData.value.matchPrice = MaxPrice.value
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
    billIds: '',
    streamId: ''
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
