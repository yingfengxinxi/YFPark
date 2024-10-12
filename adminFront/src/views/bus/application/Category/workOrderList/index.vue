<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <!-- 搜索列表 -->
    <div class="search bg-white p-18px rounded flex gap-10px box-border pos-relative">
      <div>
        <el-date-picker
          v-model="time"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          style="width: 200px !important"
          @change="changeData"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </div>
      <div class="w-200px">
        <el-select
          style="width: 200px"
          placeholder="请选择工单订单情况"
          v-model="queryParams.paidStatus"
          @change="getPageData"
          clearable
        >
          <el-option
            v-for="(item, index) in getIntDictOptions('WORK_ORDER_PAID_STATUS')"
            :label="item.label"
            :value="item.value"
            :key="index"
          />
        </el-select>
      </div>
      <div>
        <el-select
          style="width: 200px"
          placeholder="请选择费用支付方"
          v-model="queryParams.paidPayer"
          @change="getPageData"
          clearable
        >
          <el-option
            v-for="(item, index) in getIntDictOptions('WORK_ORDER_PAID_PAYER')"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
      <div class="w-200px">
        <el-tree-select
          v-model="queryParams.departmentId"
          @change="getPageData"
          :data="departmentOptions"
          :props="defaultProps"
          placeholder="请选择部门"
          clearable
          :check-strictly="true"
          multiple
        />
      </div>
      <el-button
        class="pos-absolute pos-right-18px"
        type="primary"
        @click="addTable"
        v-hasPermi="['bus:workOrderPropose:create']"
      >
        创建
      </el-button>
    </div>
    <!-- 顶部统计 -->
    <div class="flex justify-between bg-#fff mt-16px h-100px rounded">
      <div class="text-center w-25% h-100% flex flex-col justify-evenly">
        <div class="text-13px color-#8C8C8C">工单总数</div>
        <div class="color-#409EFF font-600 text-24px">{{
          TopData.find((item) => item.key == 'allTotal')?.value
        }}</div>
      </div>
      <div class="jiange"></div>
      <div class="text-center w-25% h-100% flex flex-col justify-evenly">
        <div class="text-13px color-#8C8C8C">待处理</div>
        <div class="color-#409EFF font-600 text-24px">{{
          TopData.find((item) => item.key == 'waitTotal')?.value
        }}</div>
      </div>
      <div class="jiange"></div>

      <div class="text-center w-25% h-100% flex flex-col justify-evenly">
        <div class="text-13px color-#8C8C8C">超时工单</div>
        <div class="color-#409EFF font-600 text-24px">{{
          TopData.find((item) => item.key == 'timeoutTotal')?.value
        }}</div>
      </div>
      <div class="jiange"></div>

      <div class="text-center w-25% h-100% flex flex-col justify-evenly">
        <div class="text-13px color-#8C8C8C">处理中</div>
        <div class="color-#409EFF font-600 text-24px">{{
          TopData.find((item) => item.key == 'takeTotal')?.value
        }}</div>
      </div>
    </div>
    <!-- 列表 -->
    <div class="mt-18px rounded overflow-hidden bg-#fff">
      <el-table :data="List" v-loading="loading" @row-click="Rowclick">
        <el-table-column label="工单编号" width="300px" align="center" fixed="left" prop="number" />
        <el-table-column label="工单类型" width="200px" align="center" prop="orderType">
          <template #default="{ row }">
            <div
              class="color-#fff w-80px m-auto rounded"
              :class="row.orderType == 2 ? 'bg-#7CC3A1' : 'bg-#37DD29'"
              >{{
                getIntDictOptions('WORK_ORDER_ORDER_TYPE').find(
                  (item) => item.value == row.orderType
                )?.label
              }}</div
            >
          </template>
        </el-table-column>
        <el-table-column />
        <el-table-column label="支付情况" width="200px" align="center" prop="payInfo">
          <template #default="{ row }">
            <div v-if="row.payInfo">
              {{ row.payInfo[0].orderStatusName }}
            </div>
            <div v-else> -- </div>
          </template>
        </el-table-column>

        <el-table-column
          label="工单分类"
          width="200px"
          align="center"
          :formatter="tableColumnEmptyPlaceholder"
          prop="subcatName"
        />
        <el-table-column label="报修类型" width="200px" align="center" prop="repairType">
          <template #default="{ row }">
            {{
              getIntDictOptions('WORK_ORDER_REPAIR_TYPE').find(
                (item) => item.value == row.repairType
              )?.label
            }}
          </template>
        </el-table-column>
        <el-table-column label="上报时间" width="200px" align="center" prop="appearTime" />
        <el-table-column label="上报人" width="200px" align="center" prop="name" />
        <el-table-column label="上报人手机" width="200px" align="center" prop="phone" />
        <el-table-column
          label="项目"
          width="200px"
          align="center"
          prop="villageName"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          label="楼宇"
          width="200px"
          align="center"
          prop="buildName"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          label="对应位置"
          width="200px"
          align="center"
          prop="position"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column label="是否超时" width="200px" align="center" prop="isTimeout">
          <template #default="{ row }">
            <div class="">{{
              getIntDictOptions('WORK_ORDER_IS_TIMEOUT').find((item) => item.value == row.isTimeout)
                ?.label
            }}</div>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="200px" align="center" fixed="right" prop="status">
          <template #default="{ row }">
            <el-tag
              :type="
                getIntDictOptions('WORK_ORDER_STATUS').find((item) => item.value == row.status)
                  ?.colorType
              "
              >{{
                getIntDictOptions('WORK_ORDER_STATUS').find((item) => item.value == row.status)
                  ?.label
              }}</el-tag
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <Pagination
        v-model:limit="queryParams.pageSize"
        v-model:page="queryParams.pageNo"
        :total="total"
        @pagination="getList"
      />
    </div>
  </div>
  <!-- 新增弹窗 -->
  <AddForm ref="AddFormRef" @submit="getPageData" />
  <!-- 详情弹窗 -->
  <DetailForm ref="DetailFormRef" @submit="getPageData" />
</template>
<script lang="ts" setup>
import { defaultProps, handleTree } from '@/utils/tree'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'
import { workOrderProposeApi } from '@/api/bus/Category/workOrderList/index'
import { onMounted, ref, onUnmounted, reactive } from 'vue'
import { getIntDictOptions } from '@/utils/dict'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { BuildApi } from '@/api/bus/village/building'
import { Api } from '@/api/contract/contractList/index'
import AddForm from './components/addForm.vue'
import DetailForm from './components/detailForm.vue'
const applicationValue = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  applicationValue.value = applicationName.split('=')[1]
}
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  paidStatus: '',
  paidPayer: '',
  departmentId: [],
  startTime: '',
  endTime: ''
})
const time = ref('')
const total = ref(0)
const form = reactive({
  departmentId: []
})
const changeData = (val: any) => {
  if (val == null) {
    queryParams.startTime = ''
    queryParams.endTime = ''
    getPageData()
    return
  }
  queryParams.startTime = val[0]
  queryParams.endTime = val[1]
  getPageData()
}
///部门下拉数据
const departmentId = ref([])
const departmentOptions = ref([])
const GetdepartmentOptions = async () => {
  const data = await patrolPlanEquipmentApi.getDeptList()
  departmentOptions.value.push(...handleTree(data))
}
//分页
const List = ref([])
const loading = ref(false)
const getList = async () => {
  queryParams.application = applicationValue.value
  loading.value = true
  const data = await workOrderProposeApi.getPage({
    ...queryParams,
    departmentId: queryParams.departmentId.join(',')
  })

  List.value = data.list
  List.value.forEach((item) => {
    item.payInfo = JSON.parse(item.payInfo)
    item.buildName = BuildList.value.find((build) => build.id == item.buildId)?.buildName
    item.villageName = VillageList.value.find((village) => village.id == item.villageId)?.name
  })
  total.value = data.total
  loading.value = false
}
//获取顶部数据统计
const TopData = ref([])
const getTopData = async () => {
  queryParams.application = applicationValue.value
  const data = await workOrderProposeApi.getStatic({
    ...queryParams,
    departmentId: queryParams.departmentId.join(',')
  })
  TopData.value = data
}
//点击行
const DetailFormRef = ref()
const Rowclick = (row: any) => {
  DetailFormRef.value.open(row.id, row)
}
//添加工单
const AddFormRef = ref()
const addTable = () => {
  AddFormRef.value.show(applicationValue.value)
}
//获得项目列表
const VillageList = ref([])
const getVillage = async () => {
  const data = await Api.getVillageList()
  VillageList.value = data
}
//获得楼宇列表
const BuildList = ref([])
const getBuild = async () => {
  const data = await BuildApi.getBuildingList()
  BuildList.value = data
}
const getPageData = async () => {
  getList()
  getTopData()
}
const NotData = ref()
let timer
onMounted(async () => {
  window.toDetail = () => {
    DetailFormRef.value.open(NotData.value[0].id, NotData.value[0])
  }
  await getapplication()
  await nextTick()

  await getVillage()
  await getBuild()
  GetdepartmentOptions()
  getList()
  getTopData()
  //轮询获取数据
  timer = setInterval(() => {
    workOrderProposeApi.getbroadcast().then((res) => {
      if (res.length > 0) {
        NotData.value = res
        ElNotification({
          title: '工单通知',
          dangerouslyUseHTMLString: true,
          message: `
            <div style="width:100%;position:reactive;height:60px">
              <strong>有一条新的待处理工单</strong>
              <br/>
              <div style="width: 80px; background-color: #409eff; text-align:center; line-height:26px;border-radius:4px;color:#fff;font-size:14px;margin-top:15px;position:absolute;right:10px;bottom:10px;" onClick="window.toDetail()" >立即查看</div>
            </div>
          `
        })
      }
    })
  }, 3000)
})
onUnmounted(() => {
  clearInterval(timer)
})
</script>
<style scoped>
.jiange {
  width: 1px;
  height: 80%;
  background-color: #e8e8e8;
  margin: 10px 0;
}
</style>
