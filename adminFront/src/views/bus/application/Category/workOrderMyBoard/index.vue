<template>
  <el-card shadow="never">
    <template #header>
      <div class="flex justify-between items-center">
        <span class="text-15px">工单看板</span>
        <div class="w-200px">
          <el-tree-select
            v-model="departmentId"
            :data="departmentOptions"
            :props="defaultProps"
            placeholder="请选择负责部门"
            clearable
            :check-strictly="true"
            multiple
            @change="gettopStatic"
          />
        </div>
      </div>
    </template>
    <!-- 看板 -->
    <div class="grid grid-cols-5 h-120px gap-10px">
      <div
        class="border border-solid border-#F0F0F0 h-100% p-10px box-border flex flex-col justify-between"
      >
        <div class="flex justify-between">
          <div class="flex flex-col justify-between h-60px">
            <div class="text-#8C8C8C text-14px">工单总数</div>
            <div class="text-20px">{{
              topData.find((item) => item.key == 'totalOrderNums')?.value
            }}</div>
          </div>
          <div class="w-70px h-70px bg-#165DFF rounded pos-relative">
            <Icon
              icon="ep:pie-chart"
              color="#fff"
              :size="38"
              class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%"
            />
          </div>
        </div>
        <div class="text-14px color-#1890FF" @click="toList"> 点击前往工单中心 </div>
      </div>
      <div
        class="border border-solid border-#F0F0F0 h-100% p-10px box-border flex flex-col justify-between"
      >
        <div class="flex justify-between">
          <div class="flex flex-col justify-between h-60px">
            <div class="text-#8C8C8C text-14px">待派工单数</div>
            <div class="text-20px">{{
              topData.find((item) => item.key == 'waitOrderHandleNums')?.value
            }}</div>
          </div>
          <div class="w-70px h-70px bg-#165DFF rounded pos-relative">
            <Icon
              icon="ep:pie-chart"
              color="#fff"
              :size="38"
              class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%"
            />
          </div>
        </div>
        <div class="text-14px color-#1890FF" @click="toList"> 点击前往工单中心 </div>
      </div>
      <div
        class="border border-solid border-#F0F0F0 h-100% p-10px box-border flex flex-col justify-between"
      >
        <div class="flex justify-between">
          <div class="flex flex-col justify-between h-60px">
            <div class="text-#8C8C8C text-14px">未办结超时工单</div>
            <div class="text-20px">{{
              topData.find((item) => item.key == 'timeoutOrderNums')?.value
            }}</div>
          </div>
          <div class="w-70px h-70px bg-#165DFF rounded pos-relative">
            <Icon
              icon="fa:clock-o"
              color="#fff"
              :size="38"
              class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%"
            />
          </div>
        </div>
        <div class="text-14px">
          处理中工单数 {{ topData.find((item) => item.key == 'handelOrderNums')?.value }}
        </div>
      </div>
      <div
        class="border border-solid border-#F0F0F0 h-100% p-10px box-border flex flex-col justify-between"
      >
        <div class="flex justify-between">
          <div class="flex flex-col justify-between h-60px">
            <div class="text-#8C8C8C text-14px">当月工单超时率</div>
            <div class="text-20px">{{
              topData.find((item) => item.key == 'thisMonthTimeoutRate')?.value
            }}</div>
          </div>
          <div class="w-70px h-70px bg-#165DFF rounded pos-relative">
            <Icon
              icon="ep:tickets"
              color="#fff"
              :size="38"
              class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%"
            />
          </div>
        </div>
        <div class="text-14px">
          超时工单数 {{ topData.find((item) => item.key == 'timeoutOrderNums')?.value }}
        </div>
      </div>
      <div
        class="border border-solid border-#F0F0F0 h-100% p-10px box-border flex flex-col justify-between"
      >
        <div class="flex justify-between">
          <div class="flex flex-col justify-between h-60px">
            <div class="text-#8C8C8C text-14px">当月满意度</div>
            <div class="text-20px">{{
              topData.find((item) => item.key == 'thisMonthSatisfaction')?.value
            }}</div>
          </div>
          <div class="w-70px h-70px bg-#165DFF rounded pos-relative">
            <Icon
              icon="fa:star"
              color="#fff"
              :size="38"
              class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%"
            />
          </div>
        </div>
        <div class="text-14px">
          月环比 {{ topData.find((item) => item.key == 'chainMonthRatio')?.value }}
        </div>
      </div>
    </div>
  </el-card>
  <!-- row2 -->
  <div class="flex gap-18px mt-18px">
    <div class="bg-#fff h-100% w-60%">
      <el-card header="工单计数" shadow="never">
        <WorkorderTotal ref="WorkorderTotalRef" />
      </el-card>
    </div>
    <div class="bg-#fff h-100% w-40%">
      <el-card header="满意度指数" shadow="never">
        <satisfactionTotal ref="satisfactionTotalRef" />
      </el-card>
    </div>
  </div>
  <!-- row3 -->
  <div class="flex gap-18px mt-18px">
    <div class="bg-#fff h-100% w-50%">
      <el-card header="近半年工单数" shadow="never">
        <HalfayearTotal ref="HalfayearTotalRef" />
      </el-card>
    </div>
    <div class="bg-#fff h-100% w-50%">
      <el-card header="工单类型分类占比" shadow="never">
        <sortTotal ref="sortTotalRef" />
      </el-card>
    </div>
  </div>
  <!-- row4 -->
  <div class="mt-18px">
    <el-card header="工单大类占比" shadow="never">
      <workorderCategory ref="workorderCategoryRef" />
    </el-card>
  </div>
</template>
<script setup lang="ts">
import { defaultProps, handleTree } from '@/utils/tree'

import WorkorderTotal from './components/WorkorderTotal.vue'
import satisfactionTotal from './components/satisfactionTotal.vue'
import HalfayearTotal from './components/HalfayearTotal.vue'
import sortTotal from './components/sortTotal.vue'
import workorderCategory from './components/workorderCategory.vue'
import { workOrderMyBoardApi } from '@/api/bus/Category/workOrderMyBoard/index'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'
const applicationValue = ref('')
const WorkorderTotalRef = ref(null)
const satisfactionTotalRef = ref(null)
const HalfayearTotalRef = ref(null)
const sortTotalRef = ref(null)
const workorderCategoryRef = ref(null)
const route = useRoute() // 路由对象
const router = useRouter() // 路由

//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  applicationValue.value = applicationName.split('=')[1]
}
onMounted(async () => {
  await getapplication()
  gethalfYearTread()
  getorderTypeRatio()
  getworkOrderTypeRatio()
  getworkOrderNumsTread()
  getsatisfactionStatic()
  gettopStatic()
  GetdepartmentOptions()
})
const topData = ref([
  {
    key: 'totalOrderNums',
    value: 0
  },
  {
    key: 'waitOrderHandleNums',
    value: 0
  },
  {
    key: 'timeoutOrderNums',
    value: 0
  },
  {
    key: 'handelOrderNums',
    value: 0
  },
  {
    key: 'thisMonthTimeoutRate',
    value: 0
  },
  {
    key: 'thisMonthSatisfaction',
    value: 0
  },
  {
    key: 'chainMonthRatio',
    value: 0
  }
])
//近半年工单数
const gethalfYearTread = () => {
  workOrderMyBoardApi
    .halfYearTread({
      application: applicationValue.value
    })
    .then((res) => {
      HalfayearTotalRef.value.changeData(res)
    })
}
//工单大类占比
const getorderTypeRatio = () => {
  workOrderMyBoardApi
    .orderTypeRatio({
      application: applicationValue.value
    })
    .then((res) => {
      workorderCategoryRef.value.changeData(res.list)
    })
}
//工单分类占比
const getworkOrderTypeRatio = () => {
  workOrderMyBoardApi
    .workOrderTypeRatio({
      application: applicationValue.value
    })
    .then((res) => {
      sortTotalRef.value.changeData(res.list)
    })
}
//工单计数
const getworkOrderNumsTread = () => {
  workOrderMyBoardApi
    .workOrderNumsTread({
      application: applicationValue.value
    })
    .then((res) => {
      WorkorderTotalRef.value.changeData(res)
    })
}
//满意度指数
const getsatisfactionStatic = () => {
  workOrderMyBoardApi
    .satisfactionStatic({
      application: applicationValue.value
    })
    .then((res) => {
      satisfactionTotalRef.value.changeData(res)
    })
}
//顶部数据
const gettopStatic = () => {
  workOrderMyBoardApi
    .topStatic({
      application: applicationValue.value,
      departmentIds: departmentId.value.join(',')
    })
    .then((res) => {
      topData.value = res
    })
}
///部门下拉数据
const departmentId = ref([])
const departmentOptions = ref([])
const GetdepartmentOptions = async () => {
  const data = await patrolPlanEquipmentApi.getDeptList()
  departmentOptions.value.push(...handleTree(data))
}
//跳转工单中心
const toList = () => {
  if (applicationValue.value == 'WORK_ORDER_REPAIR') {
    router.push({
      path: '/application/CategoryWYBX/workOrderList'
    })
  } else if (applicationValue.value == 'WORK_ORDER_PROPOSE') {
    router.push({
      path: '/application/CategoryTSJY/workOrderList'
    })
  }
}
</script>
