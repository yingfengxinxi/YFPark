<template>
  <ContentWrap class="flex items-center" v-if="!ShowCategoryLabel">
    <span class="text-16px font-600">工单设置</span>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <div class="flex justify-between">
      工单列表
      <el-button
        type="primary"
        @click="openForm('create')"
        v-hasPermi="['bus:workOrderRule:create']"
      >
        <Icon icon="ep:plus" />工单规则
      </el-button>
    </div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="规则名称" align="center" prop="name" />
      <el-table-column label="绑定楼宇" align="center" prop="buildNames" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:workOrderRule:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-if="scope.row.isDelete == '1'"
            v-hasPermi="['bus:workOrderRule:delete']"
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
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { WorkOrderRuleApi, VO } from '@/api/bus/Category/WorkOrderRule/index'

import Form from './Form.vue'

/** 工单规则设置 列表 */
defineOptions({ name: 'WorkOrderRule' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  application: ''
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  queryParams.application = application.value
  loading.value = true
  try {
    const data = await WorkOrderRuleApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
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
    await WorkOrderRuleApi.delete(id)
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
