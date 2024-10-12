<template>
  <div>
    <div class="collect">
      <Collect :data="TopData" />
    </div>
    <!-- 任务统计 -->
    <el-card shadow="never" class="mt-18px">
      <template #header>
        <div class="text-16px w-100% pos-relative"
          >任务统计
          <el-date-picker
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            class="ml-20px"
            v-model="TaskDataTimeList"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            @change="changeTaskDataTime"
          />
          <el-button class="pos-absolute pos-right-0" @click="toList">详情</el-button>
        </div>
      </template>
      <TaskStatistics ref="TaskStatisticsref" />
    </el-card>
    <!-- 巡检点统计 -->
    <el-card shadow="never" class="mt-18px">
      <template #header>
        <div class="text-16px w-100% pos-relative"
          >巡检点统计
          <el-date-picker
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            class="ml-20px"
            v-model="Inspectionform.Date"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            @change="changeInspectionDataTime"
          />
          <el-select
            class="ml-18px"
            style="width: 220px"
            v-model="Inspectionform.positionId"
            placeholder="请选择巡检点"
            @change="getInspectionData"
            clearable
          >
            <el-option
              v-for="(item, index) in InspectionPositionList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
          <el-button class="pos-absolute pos-right-0" @click="toList">详情</el-button>
        </div>
      </template>
      <el-row>
        <el-col :span="6">
          <div class="text-14px color-#999">应巡检次数</div>
          <div class="text-16px mt-8px">{{ InspectionData.computeTotal }}</div>
          <div class="text-14px color-#999 mt-14px">完成率</div>
          <div class="text-16px mt-8px">{{ InspectionData.successRate }}</div>
        </el-col>
        <el-col :span="6">
          <el-progress
            type="circle"
            :percentage="InspectionData?.list[0]?.value"
            :stroke-width="18"
          >
            <template #default="{ percentage }">
              <div class="mb-10px text-14px color-#999">{{ InspectionData?.list[0]?.name }}</div>
              <div class="text-16px">{{ percentage }}%</div>
            </template>
          </el-progress>
        </el-col>
        <el-col :span="6">
          <el-progress
            type="circle"
            :percentage="InspectionData?.list[1]?.value"
            :stroke-width="18"
            color="#5AD8A6"
          >
            <template #default="{ percentage }">
              <div class="mb-10px text-14px color-#999">{{ InspectionData?.list[1]?.name }}</div>
              <div class="text-16px">{{ percentage }}%</div>
            </template>
          </el-progress>
        </el-col>
        <el-col :span="6">
          <el-progress
            type="circle"
            :percentage="InspectionData?.list[2]?.value"
            :stroke-width="18"
            color="#FF7F50"
          >
            <template #default="{ percentage }">
              <div class="mb-10px text-14px color-#999">{{ InspectionData?.list[2]?.name }}</div>
              <div class="text-16px">{{ percentage }}%</div>
            </template>
          </el-progress>
        </el-col>
      </el-row>
    </el-card>
    <!-- 异常统计 -->
    <el-card shadow="never" class="mt-18px">
      <template #header>
        <div class="text-16px w-100% pos-relative flex items-center"
          >异常统计
          <div>
            <el-date-picker
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              class="ml-20px w-100px"
              v-model="abnormalform.date"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              @change="changeabnormaltime"
            />
          </div>

          <el-select
            class="ml-18px"
            v-model="abnormalform.planId"
            style="width: 220px"
            placeholder="请选择计划"
            @change="getabnormalData"
            clearable
          >
            <el-option
              v-for="(item, index) in abnormalPlanList"
              :key="index"
              :label="item.planName"
              :value="item.id"
            />
          </el-select>
          <el-radio-group v-model="abnormalform.type" @change="getabnormalData" class="ml-20px">
            <el-radio-button label="全部" value="0" />
            <el-radio-button label="已整改" value="1" />
            <el-radio-button label="未整改" value="2" />
          </el-radio-group>
          <el-button
            class="pos-absolute pos-right-0 z-99"
            @click="exportabnormal"
            :loading="exportabnormalLoading"
            v-hasPermi="['bus:patrolRecordEquipment:warnRecordExport']"
            >导出</el-button
          >
        </div>
      </template>
      <div class="px-20px">
        <el-table :data="abnormalData" @row-click="rowClick">
          <el-table-column prop="position" label="巡检点名称" align="center" />
          <el-table-column prop="planName" label="计划名称" align="center" />
          <el-table-column prop="time" label="巡检时间" align="center" />
          <el-table-column prop="name" label="巡检人" align="center" />
          <el-table-column prop="launchName" label="整体改进人" align="center" />
          <el-table-column prop="statusName" label="状态" align="center" />
        </el-table>
        <Pagination
          :total="abnormalform.total"
          v-model:page="abnormalform.pageNo"
          v-model:limit="abnormalform.pageSize"
          @pagination="getabnormalData"
        />
      </div>
    </el-card>
    <!-- 岗位统计 -->
    <el-card shadow="never" class="mt-18px">
      <template #header>
        <div class="text-16px w-100% pos-relative"
          >岗位统计
          <el-date-picker
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            class="ml-20px"
            v-model="postform.date"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            @change="changePostDate"
          />
          <el-button
            class="pos-absolute pos-right-0 z-999"
            @click="exportPostData"
            v-hasPermi="['bus:patrolRecordEquipment:postStationExport']"
            >导出</el-button
          >
        </div>
      </template>
      <div class="px-10px">
        <el-table :data="PostDataList">
          <el-table-column prop="stationName" label="责任岗位" align="center" />
          <el-table-column prop="shouldTotal" label="应巡检次数" align="center" />
          <el-table-column prop="actualTotal" label="实际巡检次数" align="center" />
          <el-table-column prop="normalTotal" label="正常" align="center" />
          <el-table-column prop="normalTotal" label="异常" align="center" />
          <el-table-column prop="skipTotal" label="跳过" align="center" />
        </el-table>
      </div>
    </el-card>
  </div>
  <!-- 任务巡检弹窗 -->
  <abnormalDeatil ref="abnormalDeatilRef" />
</template>
<script setup lang="ts">
import Collect from './components/collect.vue'
import TaskStatistics from './components/TaskStatistics.vue'
import abnormalDeatil from './components/abnormalDeatil.vue'
import { dataBoardApi } from '@/api/bus/patrol/dataBoard/index'
import { Api, VO } from '@/api/bus/patrol/position'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'
import download from '@/utils/download'
const message = useMessage() // 消息弹窗
const router = useRouter() // 路由
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
import { onMounted } from 'vue'
onMounted(async () => {
  await getapplication()
  getTopData()
  getTaskData()
  getInspectionData()
  getInspectionPosition()
  getabnormalData()
  getabnormalPlanList()
  getPostData()
})
//获得顶部数据统计
const TopData = ref({})
const getTopData = async () => {
  const res = await dataBoardApi.getpatrolTaskStaticTop({
    application: application.value
  })
  TopData.value = res
}
//获得任务统计数据
const TaskData = ref({})
const TaskDataTime = ref({
  startDate: '',
  endDate: ''
})
const changeTaskDataTime = async (val) => {
  if (!val) {
    TaskDataTime.value = {
      startDate: '',
      endDate: ''
    }
    getTaskData()
    return
  }
  TaskDataTime.value = {
    startDate: val[0],
    endDate: val[1]
  }
  getTaskData()
}
const TaskDataTimeList = ref([])
const TaskStatisticsref = ref()
const getTaskData = async () => {
  TaskDataTime.value.application = application.value
  const res = await dataBoardApi.getpatrolTaskStatic(TaskDataTime.value)
  TaskData.value = res
  TaskStatisticsref.value.changeData(res)
}
//巡检点统计
const InspectionData = ref({
  computeTotal: 0,
  successRate: 0,
  list: [
    {
      name: '正常',
      value: 0
    },
    {
      name: '异常',
      value: 0
    },
    {
      name: '跳过',
      value: 0
    }
  ]
})
const getInspectionData = async () => {
  Inspectionform.value.application = application.value
  const res = await dataBoardApi.patrolPositionStatic(Inspectionform.value)
  InspectionData.value = res
}
const InspectionPositionList = ref([])
const getInspectionPosition = async () => {
  const res = await Api.getPatrolPosition({
    application: application.value
  })
  InspectionPositionList.value = res
}
const changeInspectionDataTime = async (val) => {
  if (!val) {
    Inspectionform.Date = ''
    Inspectionform.value.startDate = ''
    Inspectionform.value.endDate = ''
    getInspectionData()
    return
  }
  Inspectionform.value.startDate = val[0]
  Inspectionform.value.endDate = val[1]
  getInspectionData()
}
const Inspectionform = ref({
  Date: '',
  startDate: '',
  endDate: '',
  positionId: ''
})
//异常统计
const abnormalData = ref([])
const abnormalform = ref({
  startDate: '',
  endDate: '',
  planId: '',
  type: 0,
  pageNo: 1,
  pageSize: 10,
  total: 0
})
const changeabnormaltime = async (val) => {
  if (!val) {
    abnormalform.value.startDate = ''
    abnormalform.value.endDate = ''
    getabnormalData()
    return
  }
  abnormalform.value.startDate = val[0]
  abnormalform.value.endDate = val[1]
  getabnormalData()
}
const getabnormalData = async () => {
  abnormalform.value.application = application.value
  const res = await dataBoardApi.warnRecordStatic(abnormalform.value)
  abnormalData.value = res.list
  abnormalform.value.total = res.total
}
const abnormalPlanList = ref([])
const getabnormalPlanList = async () => {
  const res = await patrolPlanEquipmentApi.getPlanList()
  abnormalPlanList.value = res
}
//导出异常统计
const exportabnormalLoading = ref(false)
const exportabnormal = async () => {
  exportabnormalLoading.value = true
  const res = await dataBoardApi.exportWarnRecordStatic()
  download.excel(res, '异常统计数据.xls')
  exportabnormalLoading.value = false
}
///获得岗位统计
const getPostData = async () => {
  postform.value.application = application.value
  const res = await dataBoardApi.getPostList(postform.value)
  postform.value.total = res.total
  PostDataList.value = res
}
const PostDataList = ref([])
const postform = ref({
  startDate: '',
  endDate: '',
  date: ''
})
const changePostDate = async (val) => {
  if (!val) {
    postform.value.startDate = ''
    postform.value.endDate = ''
    getPostData()
    return
  }
  postform.value.startDate = val[0]
  postform.value.endDate = val[1]
  getPostData()
}
///导出岗位统计
const exportPostData = async () => {
  const res = await dataBoardApi.exportPostList(postform.value)
  download.excel(res, '岗位统计数据.xls')
}
//跳转详情
const toList = () => {
  if (application.value == 'EQUIPMENT_INSPECTION') {
    router.push({
      path: '/application/patrolSBXJ/taskEquipment'
    })
  }else if(application.value == 'SECURITY_INSPECTION'){
    router.push({
      path: '/application/patrolAFXG/taskEquipment'
    })
  }
}
//异常详情
const abnormalDeatilRef = ref()
const rowClick = (row) => {
  abnormalDeatilRef.value.open(row.taskId)
}
</script>
