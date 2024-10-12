<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="bg-[#fff] mb-[18px] p-[18px] pb-0 rounded" v-if="!showcopyList">
    <el-form>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="项目:">
            <el-select v-model="queryParams.villageId" @change="getList" clearable>
              <el-option
                v-for="(item, key) in getProjectList"
                :key="key"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="楼宇:">
            <el-select @change="getList" v-model="queryParams.buildId" clearable>
              <el-option
                v-for="(item, key) in buildingList"
                :key="key"
                :label="item.buildName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="岗位:">
            <el-select
              placeholder="请选择抄表岗位"
              v-model="queryParams.stationIds"
              clearable
              @change="getList"
            >
              <el-option
                v-for="item in stationOptions"
                :key="item.value"
                :label="item.name"
                :value="item.id.toString()"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="部门:">
            <el-tree-select
              v-model="queryParams.departmentId"
              :data="departmentOptions"
              :props="defaultProps"
              placeholder="请选择抄表部门"
              clearable
              :check-strictly="true"
              @change="handleChange"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
  <ContentWrap v-if="!showcopyList">
    <div class="bg-[#fff] flex justify-end p-b-[10px]">
      <el-button type="primary" @click="setting">基础设置</el-button>
      <el-button type="primary" @click="openForm('create')">新建计划</el-button>
    </div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="任务计划" align="center" prop="planNumber" width="200" />
      <el-table-column label="项目" align="center" prop="villageName" width="140" />
      <el-table-column label="楼宇" align="center" prop="buildName" width="140" />
      <el-table-column label="楼层" align="center" prop="layerName" width="140" />
      <el-table-column
        label="负责岗位-负责部门"
        align="center"
        prop="stationDepartmentName"
        width="160"
      />
      <el-table-column label="周期" align="center" width="300">
        <template #default="scope">
          {{ scope.row.planRule.cycleTxt }}
        </template>
      </el-table-column>
      <el-table-column label="时间要求" align="center" prop="timeLimit">
        <template #default="scope">
          {{ scope.row.timeLimit + '小时' }}
        </template>
      </el-table-column>
      <el-table-column label="本期未抄录" align="center" prop="notMeterNum" width="120">
        <template #default="scope">
          <el-button type="primary" text @click="toCopyList(scope.row)">{{
            scope.row.notMeterNum
          }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="180">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('update', scope.row.id)">
            编辑
          </el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)"> 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="getList" />

  <!-- 基础设置 -->
  <BasicsSetting ref="basicsSettingRef" />

  <!-- 抄表计划列表 -->
  <copyList ref="copyListRef" v-if="showcopyList" @goback="goback" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { energyPlanApi, VO } from '@/api/bus/WaterElectricity/EnergyPlan/index.ts'
import Form from './Form.vue'
import BasicsSetting from './components/BasicsSetting.vue'
import copyList from './components/copyList.vue'
import { defaultProps, handleTree } from '@/utils/tree'
import { BuildApi } from '@/api/bus/village/index'
import { Api } from '@/api/contract/contractList/index'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'

/** 自定义抄表计划 列表 */
defineOptions({ name: 'EnergyPlan' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const showcopyList = ref(false) // 是否显示抄表计划列表
const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  villageId: ''
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await energyPlanApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
    list.value.forEach((item) => {
      item.planRule = JSON.parse(item.planRule)
      getnotMeterNum(item)
    })
  } finally {
    loading.value = false
  }
}
//获取本期未抄录数量
const getnotMeterNum = (row) => {
  energyPlanApi
    .notCompleteTaskList({
      planId: row.id
    })
    .then((res) => {
      // console.log(res, '====')
      list.value.forEach((item) => {
        if (item.id === row.id) {
          item.notMeterNum = res.length
        }
      })
    })
}
/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await energyPlanApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await energyPlanApi.export(queryParams)
    download.excel(data, '自定义抄表计划.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
//基础设置
const basicsSettingRef = ref()
const setting = () => {
  basicsSettingRef.value.open()
}
//抄录列表
const goback = () => {
  showcopyList.value = false
  getList()
}
const copyListRef = ref()
const toCopyList = (row: VO) => {
  showcopyList.value = true
  setTimeout(() => {
    copyListRef.value.open(row)
  }, 50)
}
///岗位下拉数据
const stationOptions = ref([])
const GetstationOptions = async () => {
  const data = await patrolPlanEquipmentApi.getStationList()
  stationOptions.value = data
}
///部门下拉数据
const departmentOptions = ref([])
const GetdepartmentOptions = async () => {
  const data = await patrolPlanEquipmentApi.getDeptList()
  departmentOptions.value.push(...handleTree(data))
}
//获取项目列表
const getProjectList = ref([])
const getProject = () => {
  BuildApi.getVillageList({}).then((res) => {
    getProjectList.value = res
  })
}
//获取楼宇列表
const buildingList = ref([])
const building_remoteMethod = () => {
  Api.getBuildingList().then((res) => {
    buildingList.value = res
  })
}
const handleChange = (value: string) => {
  getList()
}
/** 初始化 **/
onMounted(() => {
  getList()
  building_remoteMethod()
  GetstationOptions()
  GetdepartmentOptions()
  getProject()
})
</script>
