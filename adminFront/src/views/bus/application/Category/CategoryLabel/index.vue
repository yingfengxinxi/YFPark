<template>
  <ContentWrap class="flex items-center" v-if="!ShowCategoryLabel">
    <Icon icon="ep:arrow-left" @click="emit('success')" class="transform-translate-y-1px" />
    <span class="text-16px font-600 ml-10px">{{ rowData.name }}</span>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <div class="flex justify-between">
      标签列表
      <el-button
        type="primary"
        @click="openForm('create')"
        v-hasPermi="['bus:categoryLabel:create']"
      >
        <Icon icon="ep:plus" />新增标签
      </el-button>
    </div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="标签名称" align="center" prop="name" />
      <el-table-column label="排序值" align="center" prop="sort" />
      <el-table-column
        label="最近操作时间"
        align="center"
        prop="lastTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:categoryLabel:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:categoryLabel:delete']"
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
import { categoryLabelApi, VO } from '@/api/bus/Category/CategoryLabel/index'
import Form from './Form.vue'

/** 工单分类标签配置 列表 */
defineOptions({ name: 'CategoryLabel' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(false) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  subcatId: undefined,
  application: undefined
})

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await categoryLabelApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, queryParams.subcatId, queryParams.application, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await categoryLabelApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
const emit = defineEmits(['success']) // 提供 emit 方法，用于触发父组件事件
//子类数据
const rowData = ref({})
function open(row) {
  rowData.value = row
  queryParams.subcatId = row.id
  queryParams.application = row.application
  getList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
