<template>
  <div class="showList">
    <ElDialog v-model="showList" width="80vw" title="选择资产">
      <div>
        <el-table
          class="m-t-10px"
          :data="list"
          border
          :load="arrayLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
          :max-height="tableHeight"
          :row-key="getRowKey"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" :reserve-selection="true" />
          <el-table-column label="资产编码" prop="propertyNumber" />
          <el-table-column label="资产分类" prop="categoryName" />
          <el-table-column label="资产名称" prop="name" />
          <el-table-column label="所在位置" prop="positionName" />
          <el-table-column label="品牌" prop="brand" />
          <el-table-column label="型号" prop="modelName" />
        </el-table>
        <Pagination
          class="m-t-10px"
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="showList = false">取 消</el-button>
          <el-button @click="submitListForm" type="primary">确 定</el-button>
        </div>
      </template>
    </ElDialog>
  </div>
</template>
<script setup lang="ts">
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
import { PropertyApi } from '@/api/bus/property/property'
defineOptions({ name: 'StatusArrayList' })
const arrayLoading = ref(false)
const list = ref([])
const total = ref(0)
const showList = ref(false)
const chooseList = ref([])
const tableHeight = ref(0)

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  propertyNumber: undefined,
  labelLink: undefined,
  type: undefined,
  name: undefined,
  status: undefined,
  brand: undefined,
  modelName: undefined,
  deviceCode: undefined,
  processCode: undefined,
  adminId: undefined,
  adminUid: undefined,
  villageId: undefined,
  buildId: undefined,
  roomId: undefined,
  buildBind: undefined,
  positionId: undefined,
  positionName: undefined,
  purchaseTime: [],
  purchaseType: undefined,
  purchaseAmount: undefined,
  stockTime: [],
  expectMonths: undefined,
  remark: undefined,
  imageHash: undefined,
  imageUrl: undefined,
  userId: undefined,
  departmentId: undefined,
  receiveTime: [],
  maintainTime: [],
  maintainInfo: undefined,
  depreciationMonths: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  knowledgeBase: undefined,
  createTime: []
})

/** 查询列表 */
const getList = async () => {
  arrayLoading.value = true
  try {
    const data = await PropertyApi.getPropertyPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    arrayLoading.value = false
  }
}
const arrayNumber = ref(null)
/** 打开弹窗 */
const open = async (type?: number, number: number) => {
  arrayNumber.value = number ? number : null
  showList.value = true
  queryParams.status = type
  tableHeight.value = window.innerHeight * 0.7 - 60
  getList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const getRowKey = (row) => {
  return row.id
}

/** 多选 */
const handleSelectionChange = (val) => {
  chooseList.value = val
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitListForm = async () => {
  if (chooseList.value.length <= 0) {
    message.error('请选择资产')
    return
  }
  if (arrayNumber.value && chooseList.value.length > arrayNumber.value) {
    message.error('最多选择' + arrayNumber.value + '个资产')
    return
  }
  emit('success', chooseList.value)
  showList.value = false
}
</script>
<style scoped lang="scss">
// .dialog-footer {
//   position: absolute;
//   bottom: 20px;
//   right: 20px;
//   text-align: center;
//   background-color: #fff;
// }

::v-deep .el-dialog__body {
  height: 50vh;
  overflow: auto;
}
</style>
<style>
.showList .el-dialog {
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  margin: 0px !important;
}
.showList .el-dialog__body {
  height: 70vh;
  overflow: auto;
}
</style>
