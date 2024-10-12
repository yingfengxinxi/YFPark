<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap>
    <div class="flex justify-between items-center pb-10px" v-if="type_table">
      <div class="c-#000000a6 fw-600 font-size-15px">{{ ownerInfo?.name }}</div>
      <div
        class="c-#2681f3 hover-c-#52a5ff font-size-14px cursor-pointer"
        @click.stop="TenantDetailsBtn(ownerInfo.id, ownerInfo.isPersonal)"
        >租客详情</div
      >
    </div>
    <!--  @row-click="table_row_click" -->
    <el-table :data="table_list" v-loading="load" @row-click="table_row_click">
      <el-table-column label="合同编号" align="center" prop="contractNumber">
        <template #default="scope">
          <el-button type="text" @click="table_row_click(scope.row)">
            {{ scope.row.contractNumber }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="合同状态" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.statusColor" v-if="scope.row.statusColor">{{
            scope.row.status
          }}</el-tag>
          <span v-else>{{ scope.row.status }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="房号" width="200" align="center" prop="roomNumber">
        <template #default="scope">
          <span v-for="(item, index) in scope.row.parseRoomNumber" :key="index">
            <el-button type="text">
              {{ item.roomName }}
              <span v-if="index + 1 < scope.row.parseRoomNumber.length">,</span>
            </el-button>
          </span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="开始日" width="200" align="center" prop="contractStartTime" />
      <el-table-column label="结束日" width="200" align="center" prop="contractEndTime" /> -->
      <el-table-column label="租赁单价" align="center" prop="zlDjMoney" />
      <el-table-column label="签订日期" align="center" prop="signingDate" />
      <el-table-column label="合同来源" align="center" prop="dataSource" />
      <!-- <el-table-column label="楼宇名称" width="200" align="center" prop="buildName" />
      <el-table-column label="总面积" widt="200" align="center" prop="leaseArea" />
      <el-table-column label="合同来源" width="200" align="center" prop="dataSource" />
      <el-table-column label="退租时间" width="200" align="center" prop="leaseRetreatTime" />
      <el-table-column label="物业单价" width="200" align="center" prop="wyDjMoney" />
      <el-table-column label="物业保证金" width="200" align="center" prop="wyBondClause">
        <template #default="scope"> {{ scope.row.wyBondClause + '元' }}</template>
      </el-table-column>
      <el-table-column label="租赁保证金" width="200" align="center" prop="zlBondClause">
        <template #default="scope"> {{ scope.row.zlBondClause + '元' }}</template>
      </el-table-column> -->
    </el-table>
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList(queryParams.ownerId, type_table)"
    />
    <el-drawer
      v-model="detailVisible"
      direction="rtl"
      size="70%"
      :show-close="false"
      :with-header="false"
    >
      <ContractDetail
        :detailId="detailId"
        @close="[(detailVisible = false), getList(queryParams.ownerId, type_table)]"
        v-if="detailVisible"
      />
    </el-drawer>
    <TenantDetails ref="TenantDetailsRef" @select-pick="getOwnerInfo" />
  </ContentWrap>
</template>
<script lang="ts" setup>
import { Api } from '@/api/contract/contractList/index'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import ContractDetail from '@/views/contract/contractList/contractList_detail.vue'
import { OwnerApi } from '@/api/bus/owner'

const table_list = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  ownerId: undefined
})
const type_table = ref('')
const total = ref(0)
const load = ref(false)
function getList(detailId, type: type) {
  type_table.value = type
  queryParams.ownerId = detailId
  load.value = true
  getOwnerInfo(detailId)
  table_list.value = []
  if (type == 'room') {
    Api.getPage({
      roomNumber: detailId,
      pageNo: queryParams.pageNo,
      pageSize: queryParams.pageSize
    }).then(async (res) => {
      table_list.value = res.list
      if (roomStatus.value.length === 0) {
        await getRoomStatus()
        table_list.value.map((item) => {
          item.statusColor = roomStatus.value.find(
            (status) => status.label === item.status
          ).colorType
          item.parseRoomNumber = JSON.parse(item.roomNumber)
        })
      } else {
        table_list.value.map((item) => {
          item.statusColor = roomStatus.value.find(
            (status) => status.label === item.status
          ).colorType
          item.parseRoomNumber = JSON.parse(item.roomNumber)
        })
      }
      load.value = false

      total.value = res.total
    })
  } else {
    Api.getPage({
      ownerId: detailId,
      pageNo: queryParams.pageNo,
      pageSize: queryParams.pageSize
    }).then(async (res) => {
      table_list.value = res.list
      if (roomStatus.value.length === 0) {
        await getRoomStatus()
        table_list.value.map((item) => {
          item.statusColor = roomStatus.value.find(
            (status) => status.label === item.status
          ).colorType
          item.parseRoomNumber = JSON.parse(item.roomNumber)
        })
      } else {
        table_list.value.map((item) => {
          item.statusColor = roomStatus.value.find(
            (status) => status.label === item.status
          ).colorType
          item.parseRoomNumber = JSON.parse(item.roomNumber)
        })
      }
      load.value = false
    })
  }
}
const roomStatus = ref([])
const getRoomStatus = async () => {
  const data = await Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'CONTRACT_LIST_STATUS'
  })

  roomStatus.value = data.list
}
//列表详情
const detailVisible = ref(false)
const detailId = ref<string>('0')
const table_row_click = (row: any) => {
  detailId.value = row.id.toString()
  detailVisible.value = true
}
onActivated(async () => {})
onMounted(async () => {
  await getRoomStatus()
  // getOwnerInfo()
})
defineExpose({ getList })
//租客详情
const TenantDetailsRef = ref()
const TenantDetailsBtn = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}
const ownerInfo = ref({})
const ownerId = ref('')
const getOwnerInfo = async (id) => {
  try {
    const data = await OwnerApi.getByRoomIdOwnerList(id)
    if (data.length) {
      ownerInfo.value = data[0]
      ownerId.value = data[0].id
    }
  } finally {
  }
}
</script>
