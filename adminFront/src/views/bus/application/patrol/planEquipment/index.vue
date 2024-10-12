<template>
  <el-row>
    <el-col :span="8">
      <div class="h-100px bg-#fff text-center flex flex-col justify-evenly">
        <div class="text-14px color-#999">生效计划</div>
        <div class="text-18px">{{ TopCountData.planingNums }}个计划</div>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="h-100px bg-#fff text-center flex flex-col justify-evenly">
        <div class="text-14px color-#999">失效计划</div>
        <div class="text-18px">{{ TopCountData.planLoseNums }}个计划</div>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="h-100px bg-#fff text-center flex flex-col justify-evenly">
        <div class="text-14px color-#999">本周完成任务数</div>
        <div class="text-18px">{{ TopCountData.planCompleteNums }}个任务</div>
      </div>
    </el-col>
  </el-row>
  <!-- 列表 -->
  <ContentWrap class="mt-18px">
    <div class="flex justify-between mb-10px">
      <div>巡检计划</div>
      <div>
        <el-button
          type="primary"
          plain
          @click="handleExport"
          v-hasPermi="['bus:patrolPlanEquipment:export-excel']"
        >
          导出
        </el-button>

        <el-button
          type="primary"
          @click="openForm('add')"
          v-hasPermi="['bus:patrolPlanEquipment:create']"
        >
          添加
        </el-button>
      </div>
    </div>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      @row-click="handleRowClick"
    >
      <el-table-column label="计划名称" align="center" prop="planName" width="100" fixed />
      <el-table-column label="计划编号" align="center" prop="planNumber" width="340" />
      <el-table-column
        label="负责岗位-负责部门"
        align="center"
        prop="stationDepartmentName"
        width="200"
      />
      <el-table-column
        label="开始日期"
        align="center"
        prop="startDate"
        :formatter="YYYY - MM - DD"
        width="180px"
      />
      <el-table-column
        label="截止日期"
        align="center"
        prop="endDate"
        :formatter="YYYY - MM - DD"
        width="180px"
      />
      <el-table-column label="创建人" align="center" prop="creator" />
      <el-table-column label="周期" align="center" prop="planRule" width="280" />
      <el-table-column
        label="最近一次执行时间"
        align="center"
        prop="lastTime"
        :formatter="tableColumnEmptyPlaceholder"
        width="180px"
      />
      <el-table-column label="执行结果" align="center" prop="planStatus" width="100" />

      <el-table-column
        label="下次执行时间"
        align="center"
        prop="nextTime"
        :formatter="dateFormatter"
        width="180px"
      />

      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <div @click.stop="changeStatus(scope.row)">
            <el-switch active-value="1" inactive-value="0" v-model="scope.row.status" />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="150">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click.stop="openForm('update', scope.row.id)"
            v-hasPermi="['bus:patrolPlanEquipment:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click.stop="handleDelete(scope.row.id)"
            v-hasPermi="['bus:patrolPlanEquipment:delete']"
          >
            删除
          </el-button>
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
  <!-- 巡检计划 -->
  <RowForm ref="RowFormRef" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { patrolPlanEquipmentApi, VO } from '@/api/bus/patrol/planEquipment'
import Form from './Form.vue'
import RowForm from './RowForm.vue'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
/** 应用巡检计划 列表 */
defineOptions({ name: 'PatrolPlanEquipment' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  queryParams.application = application.value
  loading.value = true
  try {
    const data = await patrolPlanEquipmentApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
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
    await patrolPlanEquipmentApi.delete(id)
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
    const data = await patrolPlanEquipmentApi.export(queryParams)
    download.excel(data, '应用巡检计划.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
//获取顶部巡检计划统计数据
const TopCountData = ref({})
const getTopCountData = async () => {
  try {
    const data = await patrolPlanEquipmentApi.getTopCount({
      application: application.value
    })
    TopCountData.value = data
  } catch {}
}
//////
// 更改状态
const changeStatus = async (row: VO) => {
  // console.log(row)
  try {
    await patrolPlanEquipmentApi.updateStatus({
      id: row.id,
      status: row.status
    })
    message.success('操作成功')
  } catch {}
}
// 点击行,查看巡检计划详情
const RowFormRef = ref()
const handleRowClick = (row: VO) => {
  RowFormRef.value.open(row.id, row)
}
/** 初始化 **/
onMounted(async () => {
  await getapplication()
  getList()
  getTopCountData()
})
</script>
