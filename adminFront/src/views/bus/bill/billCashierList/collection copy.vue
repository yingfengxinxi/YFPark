<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 新建楼宇 -->
  <el-drawer v-model="collectShow" direction="rtl" size="1200px" title="收款" @close="cancelClick">
    <template #default>
      <ContentWrap class="!bg-#f7f7f7">
        <el-row :gutter="0">
          <el-col :span="12"
            >收款状态：
            <el-button type="danger" size="small" plain>未收款</el-button>
          </el-col>
          <el-col :span="12"
            >应收房源：

            <span v-if="RoomInfo.villageName"> {{ RoomInfo.villageName + ' / ' }}</span>
            <span v-if="RoomInfo.buildName"> {{ RoomInfo.buildName + ' / ' }}</span>
            <span v-if="RoomInfo.layerName"> {{ RoomInfo.layerName + ' / ' }}</span>
            <span>{{ RoomInfo.roomName }}</span>
          </el-col>
        </el-row>
      </ContentWrap>
      <el-table
        class="m-t-10px"
        ref="tableRef"
        :data="collectionList"
        :stripe="true"
        row-key="id"
        default-expand-all
        cell-class-name="text-12px"
        border
        :indent="0"
        :tree-props="{ children: 'data' }"
        :header-cell-style="{
          color: '#000000d9',
          fontSize: '14px',
          fontWeight: '500',
          backgroundColor: '#fafafa'
        }"
      >
        <el-table-column label="费用">
          <template #default="scope">
            <span v-if="scope.row.index == 0">总计 </span>
            <template v-else>
              <template v-for="(item, index) in costTypeChildrenList" :key="index">
                <template v-for="(item1, index1) in item.children" :key="index1">
                  <span v-if="item1.value == scope.row.feeType"
                    >{{ item1.label }} ({{ scope.row.payStartDate }}~{{
                      scope.row.payEndDate
                    }})</span
                  >
                </template>
              </template>
              <el-button
                type="primary"
                @click="openBillStream(scope.row)"
                link
                v-if="scope.$index == 1"
                size="small"
                class="m-l-10px"
              >
                匹配流水
              </el-button>

              <el-button
                type="primary"
                @click="openBillStream(scope.row)"
                link
                v-else
                size="small"
                class="m-l-10px BillStream"
              >
                匹配流水
              </el-button>
            </template>
          </template>
        </el-table-column>
        <el-table-column
          label="实际应收"
          prop="receivablePayableAmount"
          align="center"
          width="120"
        />
        <el-table-column label="已收" prop="receivablePayment" align="center" width="120" />
        <el-table-column label="当前待收" prop="PendingCollection" align="center" width="120" />
        <el-table-column label="本次收款" align="center" width="180">
          <template #default="scope">
            <span v-if="scope.row.index == 0">
              <div class="flex items-center justify-between">
                <span class="c-#52c41a m-r-10px flex-1 text-center">{{ totalMoney }}</span>
                <el-button plain size="small" type="primary">收完</el-button>
              </div>
            </span>
            <span v-else>
              <div class="flex items-center justify-between">
                <el-input
                  type="number"
                  size="small"
                  controls-position="right"
                  :precision="2"
                  :min="0"
                  class="flex-1 m-r-10px"
                />
                <el-button plain size="small" type="primary">收完</el-button>
              </div>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="剩余待收" prop="SurplusToBeBollected" align="center" width="120">
          <template #default="scope">
            <span :class="scope.row.index == 0 ? '' : 'c-#52c41a'">{{
              scope.row.SurplusToBeBollected
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="入账时间" align="center" width="160">
          <template #default="scope">
            <template v-if="scope.row.index == 0"> -- </template>
            <template v-else>
              <el-date-picker
                v-model="scope.row.matchDate"
                type="date"
                placeholder="Pick a day"
                size="small"
                class="!w-100%"
              />
            </template>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="cancelClick">取消</el-button>
        <el-button type="primary" @click="confirmClick">保存</el-button>
      </div>
    </template>
  </el-drawer>
  <runningWater ref="runningWaterRef" />
</template>
<script setup lang="ts">
defineOptions({ name: 'Collection' })
import { formatDate } from '@/utils/formatTime'
const message = useMessage() // 消息弹窗
import * as AreaApi from '@/api/system/area'
import runningWater from '@/views/bus/owner/component/runningWater.vue'
import ContentWrap from '@/components/ContentWrap/src/ContentWrap.vue'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { contractOrderApi, contractOrderBillVO } from '@/api/bus/contractOrderBill'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
const { t } = useI18n() // 国际化
const collectShow = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const RoomInfo = ref()
const ownerInfos = ref()
const totalMoney = ref(0.0)
const collectionList = ref([
  {
    index: 0,
    feeType: '0',
    receivable: 0.0,
    PendingCollection: 0.0,
    receivablePayment: 0.0,
    receivablePayableAmount: 0.0,
    SurplusToBeBollected: 0.0,
    payDate: '',
    lateFee: 0.0,
    data: []
  }
])
const formData = ref()

const formRules = reactive({
  villageId: [{ required: true, message: '园区名称不能为空', trigger: 'blur' }],
  buildName: [{ required: true, message: '建筑名称不能为空', trigger: 'blur' }],
  districtId: [{ required: true, message: '所属地区不能为空', trigger: 'blur' }],
  // address: [{ required: true, message: '详细地址不能为空', trigger: 'blur' }],
  propertyRight: [{ required: true, message: '产权性质不能为空', trigger: 'blur' }],
  buildArea: [{ required: true, message: '建筑面积不能为空', trigger: 'blur' }],
  propertyArea: [{ required: true, message: '产权面积不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
/** 打开抽屉 */
const open = async (roomInfo?: any, ownerInfo?: any, info?: any[]) => {
  resetForm()
  if (info.data?.length)
    info.data.forEach((item) => {
      if (item.data) {
        item.data.forEach((element) => {
          element.matchDate = formatDate(new Date(), 'YYYY-MM-DD')
          element.PendingCollection =
            Number(element.receivablePayableAmount) - Number(element.receivablePayment)
          element.SurplusToBeBollected = Number(element.PendingCollection)
          collectionList.value[0].data.push(element)
        })
      } else {
        item.PendingCollection =
          Number(item.receivablePayableAmount) - Number(item.receivablePayment)
        item.SurplusToBeBollected = item.PendingCollection
        item.matchDate = formatDate(new Date(), 'YYYY-MM-DD')
        collectionList.value[0].data.push(item)
        // 实际已收
      }
    })
  collectionList.value[0].data = Array.from(new Set(collectionList.value[0].data))
  collectionList.value[0].data.forEach((item) => {
    collectionList.value[0].receivablePayableAmount =
      Number(collectionList.value[0].receivablePayableAmount) + Number(item.receivablePayableAmount)
    // 已收
    collectionList.value[0].receivablePayment =
      Number(collectionList.value[0].receivablePayment) + Number(item.receivablePayment)
    // 当前待收
    collectionList.value[0].PendingCollection =
      Number(collectionList.value[0].PendingCollection) + Number(item.PendingCollection)
    collectionList.value[0].SurplusToBeBollected =
      Number(collectionList.value[0].SurplusToBeBollected) + Number(item.SurplusToBeBollected)
  })
  RoomInfo.value = roomInfo
  ownerInfos.value = ownerInfo

  collectShow.value = true
}
const runningWaterRef = ref()
const openBillStream = async () => {
  runningWaterRef.value.open(RoomInfo.value, ownerInfos.value, collectionList.value[0].data)
}
function cancelClick() {
  collectShow.value = false
  emit('close')
}
const emit = defineEmits(['success', 'close']) // 定义 success 事件，用于操作成功后的回调
const confirmClick = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    emit('success', formType.value)
  } finally {
    formLoading.value = false
    activeIndex.value = '0'
    tableData.value = []
  }
}

const costTypeChildrenList = ref([])
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}

/** 初始化 **/
onMounted(() => {
  getCostTypeChildrenList()
})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 重置表单 */
const resetForm = () => {
  collectionList.value = [
    {
      index: 0,
      feeType: '0',
      receivable: 0.0,
      PendingCollection: 0.0,
      receivablePayment: 0.0,
      receivablePayableAmount: 0.0,
      SurplusToBeBollected: 0.0,
      payDate: '',
      lateFee: 0.0,
      data: []
    }
  ]
}

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
<style scope lang="scss">
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
<style scoped>
.el-input-number .el-input__inner {
  text-align: left !important;
}

.BillStream {
  display: none;
}
.el-table .el-table__row:hover .BillStream {
  display: inline-block;
}
</style>
