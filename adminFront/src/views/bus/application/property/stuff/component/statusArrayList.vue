<template>
  <div class="showList">
    <ElDialog v-model="showList" width="80vw" title="选择物料">
      <div>
        <el-table
          v-if="dialogType != 'stuff_retreat_out'"
          ref="tableRef"
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
          <el-table-column
            label="物料名称"
            :prop="dialogType == 'stuff_stock_enter' ? 'name' : 'stuff.name'"
          />
          <el-table-column
            label="物料编码"
            :prop="dialogType == 'stuff_stock_enter' ? 'number' : 'stuff.number'"
          />
          <template
            v-if="
              dialogType == 'stuff_stock_enter' ||
              dialogType == 'stuff_hand_out' ||
              dialogType == 'stuff_retreat_out' ||
              dialogType == 'stuff_transfer' ||
              dialogType == 'stuff_handle'
            "
          >
            <el-table-column
              label="品牌"
              :prop="dialogType == 'stuff_stock_enter' ? 'brand' : 'stuff.brand'"
            />
            <el-table-column
              label="规格型号"
              :prop="dialogType == 'stuff_stock_enter' ? 'modelName' : 'stuff.modelName'"
            />
            <el-table-column
              label="计量单位"
              :prop="dialogType == 'stuff_stock_enter' ? 'meterUnit' : 'stuff.meterUnit'"
            />
            <template v-if="dialogType !== 'stuff_stock_enter'">
              <el-table-column label="可用库存" prop="totalNum" />
            </template>
            <el-table-column
              label="物料照片"
              :prop="dialogType == 'stuff_stock_enter' ? 'modelName' : 'stuff.imageUrl'"
            >
              <template #default="scope">
                <span v-if="scope.row.imageUrl || (scope.row.stuff && scope.row.stuff.imageUrl)">
                  <el-image
                    preview-teleported="true"
                    style="width: 60px; height: 60px"
                    :src="scope.row.imageUrl"
                    :zoom-rate="1.2"
                    :max-scale="7"
                    :min-scale="0.2"
                    :preview-src-list="[scope.row.imageUrl || scope.row.stuff.imageUrl]"
                    :initial-index="0"
                    fit="cover"
                /></span>
                <span v-else>--</span>
              </template>
            </el-table-column>
          </template>
          <template v-else-if="dialogType == 'stuff_adjust'">
            <el-table-column label="物料分类" prop="categoryName" />
            <el-table-column label="调整前数量" prop="totalNum" />
            <el-table-column label="调整前单价" prop="stuff.price" />
            <el-table-column label="调整前总价" prop="totalPrice" />
          </template>
        </el-table>
        <el-table
          v-else
          ref="tableRef"
          v-loading="arrayLoading"
          :data="list"
          :stripe="true"
          :show-overflow-tooltip="true"
          :row-key="getRowKey"
          default-expand-all
          @selection-change="handleSelectionChangeAdmin"
        >
          <el-table-column type="selection" width="55" :selectable="selectable" />
          <el-table-column type="expand" width="50">
            <template #default="scope">
              <el-table
                :id="scope.row.processNumber"
                :ref="(el) => getimgManageRef(el, scope.row.processNumber)"
                class="m-t-10px"
                :data="scope.row.stuff"
                border
                :header-cell-style="{
                  color: '#000000d9',
                  fontSize: '14px',
                  fontWeight: '500',
                  backgroundColor: '#fafafa'
                }"
                :max-height="tableHeight"
                :row-key="getRowKey"
                @selection-change="handleSelectionChangeItem"
              >
                <el-table-column
                  type="selection"
                  width="55"
                  :reserve-selection="true"
                  :selectable="selectableItem"
                />
                <el-table-column label="物料名称" prop="name" />
                <el-table-column label="物料编码" prop="dnumber" />
                <el-table-column label="品牌" prop="brand" />
                <el-table-column label="规格型号" prop="modelName" />
                <el-table-column label="计量单位" prop="meterUnit" />
                <el-table-column label="可退库存" prop="num" />
                <el-table-column label="物料照片" prop="stuff.imageUrl">
                  <template #default="scope">
                    <span v-if="scope.row.imageUrl">
                      <el-image
                        preview-teleported="true"
                        style="width: 60px; height: 60px"
                        :src="scope.row.imageUrl"
                        :zoom-rate="1.2"
                        :max-scale="7"
                        :min-scale="0.2"
                        :preview-src-list="[scope.row.imageUrl]"
                        :initial-index="0"
                        fit="cover"
                    /></span>
                    <span v-else>--</span>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column label="单据编号" align="center" prop="processNumber" width="240" />
          <el-table-column label="领用人" align="center" prop="receiveUid"
            ><template #default="scope">
              <template v-if="scope.row.receiveUid">
                <template v-for="user in userOptions" :key="user.id">
                  <span v-if="user.id === scope.row.receiveUid">{{ user.nickname }}</span>
                </template>
              </template>
              <span v-else>--</span>
            </template>
          </el-table-column>
          <el-table-column
            label="领用部门"
            align="center"
            prop="departmentName"
            :formatter="tableColumnEmptyPlaceholder"
          />
          <el-table-column
            label="出库仓库"
            align="center"
            prop="depositoryName"
            :formatter="tableColumnEmptyPlaceholder"
          />
          <el-table-column
            label="派发时间"
            align="center"
            prop="handoutTime"
            :formatter="dateFormatter"
            width="180px"
          />
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
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { dateFormatter } from '@/utils/formatTime'
import { PropertyStuffApi } from '@/api/bus/stuff/index'
import { PropertyStuffStockApi, PropertyStuffStockVO } from '@/api/bus/stuff/stock'
import { PropertyStuffHandoutApi, PropertyStuffHandoutVO } from '@/api/bus/stuff/handOut'
defineOptions({ name: 'StatusArrayList' })
const arrayLoading = ref(false)
const list = ref([])
const total = ref(0)
const showList = ref(false)
const chooseList = ref([])
const tableHeight = ref(0)

const queryParams = ref({
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
const prop = defineProps({
  userOptions: {
    type: Array,
    default: null
  }
})
watch(
  () => prop.userOptions,
  () => {},
  {
    immediate: true,
    deep: true
  }
)
const arrayNumber = ref(100)
const dialogType = ref(null)
/** 查询列表 */
const getList = async () => {
  arrayLoading.value = true
  try {
    const data =
      dialogType.value == 'stuff_stock_enter'
        ? await PropertyStuffApi.getPropertyStuffPage(queryParams.value)
        : dialogType.value == 'stuff_retreat_out'
          ? await PropertyStuffHandoutApi.getPropertyStuffHandoutPage(queryParams.value)
          : await PropertyStuffStockApi.getPropertyStuffStockPage(queryParams.value)
    if (dialogType.value == 'stuff_retreat_out') {
      data.list.forEach((item) => {
        item.stuff.forEach((element) => {
          element.processNumber = item.processNumber
        })
      })
    }
    list.value = data.list
    total.value = data.total
  } finally {
    arrayLoading.value = false
  }
}
/** 打开弹窗 */
const open = async (type?: number, Params: any) => {
  showList.value = true
  dialogType.value = type
  tableHeight.value = window.innerHeight * 0.7 - 60
  if (Params) {
    queryParams.value = {
      ...Params,
      pageNo: 1,
      pageSize: 10
    }
  }
  getList()
}
const tableRef = ref()
/** 重置选项 */
const resetSelect = () => {
  if (chooseList.value.length) {
    tableRef.value.clearSelection()
    if (dialogType.value == 'stuff_retreat_out') {
      list.value.forEach((row) => {
        imgManageRefList.value[row.processNumber].clearSelection()
      })
    }
    chooseList.value = []
  }
}
defineExpose({ open, resetSelect }) // 提供 open 方法，用于打开弹窗
const getRowKey = (row) => {
  return row.id
}

// 这个方法主要是为了设置表格当前行是禁用状态可以可选择状态
const selectable = (row, index) => {
  console.log(row, 'row')
  if (chooseList.value.length && row.processNumber != chooseList.value[0].processNumber) {
    row.stuff.forEach((item) => {
      item.disabled = true
    })
    return false
  } else {
    if (row.stuff) {
      row.stuff.forEach((item) => {
        item.disabled = undefined
      })
    }
    return true
  }
}

const selectableItem = (row) => {
  if (row.disabled) {
    return false
  } else {
    return true
  }
}

// 内层表格ref 实例
// 需要注意的是，el-table需要是展开状态（default-expand-all），折叠起来的话获取不到内层表格ref实例
const imgManageRefList = ref([])
const getimgManageRef = (el, processNumber) => {
  if (el) {
    imgManageRefList.value[processNumber] = el
  }
}

/** 外层勾选数据 */
const selectedOutRows = ref({}) //外层表格勾选数据
// 外层表格行勾选事件
const handleSelectionChangeAdmin = (selection) => {
  selectedOutRows.value = {} //先清空
  selection.forEach((item) => {
    if (!selectedOutRows.value[item.processNumber]) {
      if (item.stuff) {
        selectedOutRows.value[item.processNumber] = item.stuff.map((innerItem) => innerItem)
      }
    }
  })
}
// / 监听selectedOutRows的变化，实现自动勾选内层表格
watch(
  selectedOutRows,
  (newVal) => {
    // 先清除所有内层勾选
    list.value.forEach((row) => {
      imgManageRefList.value[row.processNumber].clearSelection()
    })
    console.log(newVal, 'newVal[processNumber]')
    // 再根据外层勾选去勾选内层表格
    for (const processNumber in newVal) {
      if (newVal[processNumber]) {
        // 这里外层表格每行数据的dataId是唯一的
        const outerRow = list.value.find((row) => row.processNumber === processNumber)
        if (outerRow) {
          // 将内层表格的数据设置为勾选状态
          outerRow.stuff.forEach((innerItem) => {
            // 这里需要根据实际的内层表格组件API进行调整
            imgManageRefList.value[processNumber].toggleRowSelection(innerItem, true)
          })
        }
      }
    }
  },
  { deep: true }
)
// 内层表格选择发生改变
const handleSelectionChangeItem = (selectionList) => {
  chooseList.value = []
  list.value.forEach((row) => {
    let list = imgManageRefList.value[row.processNumber].getSelectionRows()
    if (list.length) {
      list.forEach((item) => {
        chooseList.value.push(item)
      })
    }
    // 通过每个内层表格的ref实例的getSelectionRows方法获取已选中数据
  })
  console.log(chooseList.value, 'chooseList')
}
/** 多选 */
const handleSelectionChange = (val) => {
  if (dialogType.value == 'stuff_stock_enter') {
    chooseList.value = val
  } else if (dialogType.value == 'stuff_retreat_out') {
    console.log(val, 'val', chooseList.value)
    if (chooseList.value.length) {
      const index = chooseList.value.findIndex((item) => item.processNumber != val[0].processNumber)
      console.log(index, 'index')
      if (index >= 0) return false
    }
    chooseList.value = val
  } else if (dialogType.value == 'stuff_adjust') {
    chooseList.value = val.map((item) => {
      return {
        ...item.stuff,
        id: item.id,
        categoryName: item.categoryName || undefined,
        stuffId: item.stuffId,
        price: item.stuff.price,
        num: item.totalNum,
        totalPrice: item.totalPrice,
        handoutNum: item.totalNum,
        retreatNum: item.stuff.price
      }
    })
  } else {
    chooseList.value = val.map((item) => {
      return {
        ...item.stuff,
        id: item.id,
        stuffId: item.stuffId,
        price: item.chargePrice,
        totalNum: item.totalNum
      }
    })
  }
  console.log(chooseList.value, 'chooseList')
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
