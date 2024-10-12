<template>
  <ContentWrap class="flex items-center">
    <span class="text-16px font-600">工时设置</span>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <div class="flex justify-between">
      工时列表
      <el-button
        type="primary"
        @click="openForm('create')"
        v-hasPermi="['bus:workOrderHour:create']"
      >
        <Icon icon="ep:plus" />新增工时
      </el-button>
    </div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="工时名称" align="center" prop="name" />
      <el-table-column label="工时费用" align="center" prop="hourFee" />
      <el-table-column label="对应岗位" align="center" prop="stationName" />
      <el-table-column label="对应部门" align="center" prop="departmentName" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="1"
            inactive-value="0"
            @change="changestatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:workOrderHour:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-if="scope.row.isDelete == '1'"
            v-hasPermi="['bus:workOrderHour:delete']"
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
</template>

<script setup lang="ts">
import { workOrderHourApi, VO } from '@/api/bus/Category/workOrderHour/index'
import Form from './Form.vue'

/** 工单工时配置 列表 */
defineOptions({ name: 'WorkOrderHour' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  console.log(applicationName)
  application.value = applicationName.split('=')[1]
}
const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  application: ''
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
//修改工单工时状态
const changestatus = async (row: VO) => {
  try {
    await workOrderHourApi.updateStatus({
      id: row.id,
      status: row.status,
      computeRule: row.computeRule,
      isDefault: row.isDefault
    })
    message.success(t('common.updateSuccess'))
  } catch {}
}
/** 查询列表 */
const getList = async () => {
  queryParams.application = application.value

  loading.value = true
  try {
    const data = await workOrderHourApi.getPage(queryParams)
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
  formRef.value.open(type, queryParams.application, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await workOrderHourApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(async () => {
  await getapplication()
  getList()
})
</script>
