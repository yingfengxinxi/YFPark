<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap class="m-10px" v-loading="OwnerLoading">
    <template v-if="ownerId">
      <div class="flex justify-between items-center">
        <div class="c-#000000a6 fw-600 font-size-15px">{{ ownerInfo?.name }}</div>
        <div
          class="c-#2681f3 hover-c-#52a5ff font-size-14px cursor-pointer"
          @click="TenantDetailsBtn(ownerInfo.id, ownerInfo.isPersonal)"
          >租客详情</div
        >
      </div>
      <div class="m-t-20px">
        <div class="flex justify-between items-center flex-wrap">
          <div class="flex items-center">
            <span>账单列表</span>
            <div class="p-l-10px p-r-10px bg-#f2f2f2 b-rd-20px flex items-center m-l-20px">
              <el-switch
                v-model="isDisplayReceived"
                inline-prompt
                style="--el-switch-on-color: #2681f3; --el-switch-off-color: #00000040"
                active-text="显示"
                inactive-text="隐藏"
                active-value="1"
                inactive-value="0"
                @change="getByOwnerIdRoomIdBillList"
              />
              <div class="p-8px p-t-0px p-b-0px font-size-12px">{{
                isDisplayReceived == '1' ? '已显示已收账单' : '已隐藏已收账单'
              }}</div>
            </div>
            <div class="p-l-10px p-r-10px bg-#f2f2f2 b-rd-20px flex items-center m-l-20px">
              <el-switch
                v-model="isAccountsReceivable"
                inline-prompt
                style="--el-switch-on-color: #2681f3; --el-switch-off-color: #00000040"
                active-text="显示"
                inactive-text="隐藏"
                active-value="1"
                inactive-value="0"
                @change="getByOwnerIdRoomIdBillList"
              />
              <div class="p-8px p-t-0px p-b-0px font-size-12px">{{
                isAccountsReceivable == '1' ? '已显示未到应收期账单' : '已隐藏未到应收期账单'
              }}</div>
            </div>
          </div>
          <div>
            <el-button type="primary" v-if="multipleSelection.length > 0" @click="collectClick">
              收款
            </el-button>
            <el-button type="primary" @click="exportBill()">
              {{ checkoutIndex ? '取消多选' : '多选账单' }}
            </el-button>
            <el-button type="primary" class="font-size-14px" @click="Addform()">
              <Icon icon="ep:circle-plus" color="#fff" class="mr-5px" />
              生成账单
            </el-button>
          </div>
        </div>
        <el-table
          class="m-t-10px"
          ref="tableRef"
          v-loading="loading"
          :data="ByOwnerIdRoomIdBillList"
          :stripe="true"
          row-key="id"
          @select="handleSelect"
          @selection-change="handleSelectionChange"
          @row-click="toggleRowExpansion"
          @select-all="selectAll"
          border
          :tree-props="{ children: 'data' }"
        >
          <el-table-column
            type="selection"
            width="55"
            v-if="checkoutIndex"
            :selectable="selectable"
          />
          <el-table-column label="缴费次数" align="center" width="120">
            <template #default="scoped">
              <span v-if="scoped.row.index == 0">第{{ scoped.row.num }}次 </span>
              <template v-else>
                <template v-for="(item, index) in costTypeChildrenList" :key="index">
                  <template v-for="(item1, index1) in item.children" :key="index1">
                    <span v-if="item1.value == scoped.row.feeType">{{ item1.label }}</span>
                  </template>
                </template>
              </template>
            </template>
          </el-table-column>
          <el-table-column label="应收时间" prop="payDate" align="center" width="120" />
          <el-table-column label="有效期" align="center" width="160">
            <template #default="scoped">
              <span v-if="scoped.row.termValidity">{{ scoped.row.termValidity }}</span>
              <template v-else>
                {{ scoped.row.payStartDate }}~{{ scoped.row.payEndDate }}
              </template>
            </template>
          </el-table-column>
          <el-table-column label="应收租金" prop="receivable" align="center" width="120" />
          <el-table-column label="滞纳金" prop="lateFee" align="center" width="120" />
          <el-table-column
            label="应收金额"
            prop="receivablePayableAmount"
            align="center"
            width="120"
          />
          <el-table-column label="收款状态" align="center" width="100">
            <template #default="scoped">
              <template v-if="scoped.row.index == 0">
                <span :class="scoped.row.billStatus == '已收' ? 'c-#52c41a' : 'c-#f5222d'">{{
                  scoped.row.billStatus
                }}</span>
              </template>
              <template v-else>
                <span :class="scoped.row.billStatus == '1' ? 'c-#52c41a' : 'c-#f5222d'">{{
                  scoped.row.billStatusName
                }}</span>
              </template>
            </template>
          </el-table-column>
          <el-table-column label="实收金额" prop="receivablePayment" align="center" width="120" />
          <el-table-column label="催收通知" align="center" width="100" type="index">
            <template #default="scoped">
              <template v-if="scoped.row.index == 0">
                <span
                  v-if="scoped.row.isCs == '1' && scoped.row.billStatus != '已收'"
                  class="c-#52c41a"
                  >已催收</span
                >
                <span
                  v-else-if="scoped.row.isCs == '1' && scoped.row.billStatus == '已收'"
                  class="c-#faad14 cursor-pointer"
                >
                  未催收
                  <Icon icon="ep:circle-plus" color="#00000040" />
                </span>
                <template v-else>
                  <span
                    class="c-#faad14 flex items-center cursor-pointer"
                    @click.stop="collection(scoped.row)"
                    >未催收 <Icon icon="ep:circle-plus" color="#2681f3" class="m-l-5px"
                  /></span>
                </template>
              </template>
              <template v-else>
                <span v-if="scoped.row.isCs == 1 && scoped.row.billStatus != '1'" class="c-#52c41a"
                  >已催收</span
                >
                <span
                  v-else-if="scoped.row.isCs == 1 && scoped.row.billStatus == '1'"
                  class="c-#faad14 cursor-pointer"
                >
                  未催收
                  <Icon icon="ep:circle-plus" color="#00000040" />
                </span>
                <template v-else>
                  <span
                    class="c-#faad14 flex items-center cursor-pointer"
                    @click.stop="collection(scoped.row)"
                    >未催收 <Icon icon="ep:circle-plus" color="#2681f3" class="m-l-5px"
                  /></span>
                </template>
              </template>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </template>
    <el-empty :image-size="80" description="暂无数据" v-else />
    <addCheckForm ref="addCheckFormRef" @success="getByOwnerIdRoomIdBillList" />
    <collectionForm ref="collectionFormRef" @success="getByOwnerIdRoomIdBillList" />
  </ContentWrap>
  <TenantDetails ref="TenantDetailsRef" />
  <Collection ref="CollectionRef" @close="clearChoose" @success="getByOwnerIdRoomIdBillList" />
  <CheckOrderDetail ref="CheckOrderDetailRef" />
</template>
<script setup lang="ts">
/** 租客账单 */
defineOptions({ name: 'OwnerCheck' })
import { contractOrderApi } from '@/api/bus/contractOrderBill'
import { OwnerApi } from '@/api/bus/owner'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import addCheckForm from './addCheckForm.vue'
import collectionForm from './collectionForm.vue'
import Collection from './collection.vue'
import CheckOrderDetail from './checkOrderDetail.vue'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
const props = defineProps({
  roomId: {
    type: Number,
    default: 0
  },
  info: {
    type: Object,
    default: () => {}
  },
  Id: {
    type: Number,
    default: 0
  }
})
const roomId = ref(props.roomId)
const ownerId = ref('')
const isDisplayReceived = ref('0')
const isAccountsReceivable = ref('0')
const loading = ref(false)
const OwnerLoading = ref(false)
const ByOwnerIdRoomIdBillList = ref([])

const multipleSelection = ref([])
// 这个方法主要是为了设置表格当前行是禁用状态可以可选择状态
const selectable = (row, index) => {
  return row.billStatus == '已收' || row.billStatus == '1' ? false : true
}
const CollectionRef = ref()
const collectClick = async () => {
  CollectionRef.value.open(props.info, ownerInfo.value, multipleSelection.value)
}
const clearChoose = () => {
  multipleSelection.value = []
  tableRef.value.clearSelection()
}
const handleSelectionChange = (val) => {
  multipleSelection.value = val
}

// 父子级不关联
const handleSelect = (selection, row) => {
  if (row.data && row.data.length > 0) {
    const isSelect = selection.some((el) => {
      const tableDataPaths = [row.num]
      return tableDataPaths.includes(el.num)
    })
    setChildren(row.data, isSelect)
  } else {
    const isCancel = selection.some((el) => {
      const tableDataPaths = [row.id]
      return tableDataPaths.includes(el.id)
    })
    tableRef.value.toggleRowSelection(row, isCancel)
  }
}

const tableRef = ref()
const CheckOrderDetailRef = ref()
const toggleRowExpansion = (row: any) => {
  if (row.data) {
    tableRef.value.toggleRowExpansion(row)
  } else {
    CheckOrderDetailRef.value.open(row.id)
  }
}

// 选择全部
const selectAll = (selection) => {
  // tabledata第一层只要有在selection里面就是全选
  const isSelect = selection.some((el) => {
    const tableDataPaths = ByOwnerIdRoomIdBillList.value.map((j) => j.id)
    return tableDataPaths.includes(el.id)
  })
  // tableDate第一层只要有不在selection里面就是全不选
  const isCancel = !ByOwnerIdRoomIdBillList.value.every((el) => {
    const selectPaths = selection.map((j) => j.id)
    return selectPaths.includes(el.id)
  })
  if (isCancel) {
    ByOwnerIdRoomIdBillList.value.map((el) => {
      if (el.data) {
        // 解决子组件没有被勾选到
        setChildren(el.data, false)
      }
    })
  }
  if (isSelect) {
    selection.map((el) => {
      if (el.data) {
        // 解决子组件没有被勾选到
        setChildren(el.data, true)
      }
    })
  }
}
const setChildren = (children, type: boolean) => {
  // 编辑多个子层级
  children.map((j) => {
    toggleSelection(j, type)
    if (j.data) {
      setChildren(j.data, type)
    }
  })
}
const toggleSelection = (row, select: boolean) => {
  // 编辑多个子层级
  if (row) {
    tableRef.value?.toggleRowSelection(row, select)
  }
}
const getByOwnerIdRoomIdBillList = async () => {
  try {
    loading.value = true
    const data = await contractOrderApi.getByOwnerIdRoomIdBillList({
      ownerId: ownerId.value,
      roomId: roomId.value,
      isDisplayReceived: isDisplayReceived.value,
      isAccountsReceivable: isAccountsReceivable.value
    })
    data.forEach((item) => {
      item.index = 0
      item.data.forEach((item1) => {
        item1.index = 1
      })
    })
    ByOwnerIdRoomIdBillList.value = data
  } finally {
    loading.value = false
  }
}
const TenantDetailsRef = ref()
const TenantDetailsBtn = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}
const checkoutIndex = ref(false)
const exportBill = async () => {
  checkoutIndex.value = !checkoutIndex.value
  tableRef.value!.clearSelection()
}
const costTypeChildrenList = ref([])
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}

/** 催收 */
const collectionFormRef = ref()
const collection = async (item) => {
  collectionFormRef.value.open(props.info, ownerInfo.value, item)
}

const addCheckFormRef = ref()
/** 生成账单 */
const Addform = () => {
  addCheckFormRef.value.open(ownerInfo.value)
}
const ownerInfo = ref({})
const getOwnerInfo = async () => {
  OwnerLoading.value = true
  try {
    const data = await OwnerApi.getByRoomIdOwnerList(roomId.value)
    if (data.length) {
      ownerInfo.value = data[0]
      ownerId.value = data[0].id
      getByOwnerIdRoomIdBillList()
    }
  } finally {
    OwnerLoading.value = false
  }
}
watch(
  () => props.roomId,
  (val) => {
    if (JSON.stringify(val)) {
      getOwnerInfo()
      // activeIndex.value = Number(props.active_type)
    }
  },
  {
    immediate: true,
    deep: true
  }
)
watch(
  () => props.Id,
  (val) => {
    if (JSON.stringify(val)) {
      ownerId.value = val
      getCostTypeChildrenList()
      if (ownerId.value) {
        getByOwnerIdRoomIdBillList()
      }
    }
  },
  {
    immediate: true,
    deep: true
  }
)
onMounted(() => {})
</script>
<style lang="css" scoped></style>
