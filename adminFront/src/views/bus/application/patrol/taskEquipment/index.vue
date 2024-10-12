<template>
  <el-row>
    <el-col :span="6">
      <div class="h-100px bg-#fff text-center flex flex-col justify-evenly">
        <div class="text-14px color-#999">待开始</div>
        <div class="text-18px">{{ TopCountData.waitTaskCount }}个任务</div>
      </div>
    </el-col>
    <el-col :span="6">
      <div class="h-100px bg-#fff text-center flex flex-col justify-evenly">
        <div class="text-14px color-#999">已完成</div>
        <div class="text-18px">{{ TopCountData.overTaskCount }}个任务</div>
      </div>
    </el-col>
    <el-col :span="6">
      <div class="h-100px bg-#fff text-center flex flex-col justify-evenly">
        <div class="text-14px color-#999">巡检中</div>
        <div class="text-18px">{{ TopCountData.havingTaskCount }}个任务</div>
      </div>
    </el-col>
    <el-col :span="6">
      <div class="h-100px bg-#fff text-center flex flex-col justify-evenly">
        <div class="text-14px color-#999">超期未执勤</div>
        <div class="text-18px">{{ TopCountData.timeoutTaskCount }}个任务</div>
      </div>
    </el-col>
  </el-row>
  <!-- 列表 -->
  <ContentWrap class="mt-18px">
    <div class="flex justify-between mb-10px">
      <div>巡检任务</div>
      <div>
        <el-button
          type="primary"
          plain
          @click="handleExport"
          v-hasPermi="['bus:patrolTaskEquipment:export-excel']"
        >
          导出
        </el-button>
        <el-button
          type="primary"
          :disabled="currentRow.length != 1"
          @click="openForm"
          v-hasPermi="['bus:patrolTaskEquipment:saveTaskPost']"
        >
          变更执勤人
        </el-button>
      </div>
    </div>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      @select="handleCurrentChange"
      @row-click="rowClick"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="执行结果" align="center" prop="statusName" width="140">
        <template #default="{ row }">
          <el-tag
            :type="
              getStrDictOptions('PLAN_STATUS').find((item) => item.value === row.status)?.colorType
            "
          >
            {{ getStrDictOptions('PLAN_STATUS').find((item) => item.value === row.status)?.label }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="任务名称" align="center" prop="taskName" width="180" />
      <el-table-column label="任务编号" align="center" width="300" prop="taskNumber">
        <template #default="{ row }">
          <div>
            {{ row.taskNumber || '--' }}
            <Icon
              icon="ep:copy-document"
              color="#409EFF"
              v-if="row.taskNumber"
              @click.stop="copytaskNumber(row.taskNumber)"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="开始时间"
        align="center"
        prop="startTime"
        width="180px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="应开始时间"
        align="center"
        prop="shouldTime"
        width="180px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="负责岗位-负责部门"
        align="center"
        prop="stationDepartmentName"
        width="200"
      >
        <template #default="{ row }">
          <div
            >{{ row.stationDepartmentName }}

            <el-tooltip
              class="box-item"
              effect="dark"
              :content="'执勤人变动:' + row.changePersonnelName"
              placement="top"
            >
              <Icon
                icon="ep:avatar"
                color="#409EFF"
                class="ml-7px"
                v-if="row.changePersonnelList"
              />
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="完成时间"
        align="center"
        prop="endTime"
        width="180px"
        :formatter="tableColumnEmptyPlaceholder"
      />
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
  <abnormalDeatil ref="abnormalDeatilRef" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { Api, VO } from '@/api/bus/patrol/taskEquipment'
import Form from './Form.vue'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { useClipboard } from '@vueuse/core'
import abnormalDeatil from '@/views/bus/application/patrol/dataBoard/components/abnormalDeatil.vue'
/** 应用巡检任务 列表 */
defineOptions({ name: 'PatrolTaskEquipment' })
import { tableColumnEmptyPlaceholder } from '@/utils/index'

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const TopCountData = ref({})
const currentRow = ref([]) // 当前选中的行
const handleCurrentChange = (row: VO) => {
  currentRow.value = row
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  currentRow.value = []
  queryParams.application = application.value
  try {
    const data = await Api.getPage(queryParams)
    list.value = data.list
    total.value = data.total
    list.value.forEach((item) => {
      if (item.changePersonnelList) {
        const name = []
        item.changePersonnelList.forEach((i) => {
          name.push(`${i.oldName}→${i.newName}`)
        })
        item.changePersonnelName = name.join(',')
      }
    })
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
const openForm = () => {
  formRef.value.open(currentRow.value[0]?.id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await Api.delete(id)
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
    const data = await Api.export(queryParams)
    download.excel(data, '应用巡检任务.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
//获取顶部统计数据
const getTopCount = async () => {
  try {
    const data = await Api.getTopData({
      application: application.value
    })
    TopCountData.value = data
  } finally {
  }
}
//复制任务编号
const copytaskNumber = async (taskNumber) => {
  if (navigator.clipboard) {
    const { copy, copied, isSupported } = useClipboard({ source: taskNumber })
    await copy()
  } else {
    const input = document.createElement('input')
    input.setAttribute('value', taskNumber)
    document.body.appendChild(input)
    input.select()
    document.execCommand('copy')
    document.body.removeChild(input)
  }
  message.success('复制成功')
}
const abnormalDeatilRef = ref()
const rowClick = (row) => {
  abnormalDeatilRef.value.open(row.id)
}
/** 初始化 **/
onMounted(async () => {
  await getapplication()
  getList()
  getTopCount()
})
</script>
