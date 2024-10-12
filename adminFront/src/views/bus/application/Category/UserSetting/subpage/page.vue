<template>
  <div>
    <ContentWrap class="flex items-center">
      <div class="flex gap-10px">
        <Icon icon="ep:arrow-left" class="transform-translate-y-2px" @click="emit('success')" />
        <span class="text-16px font-600">{{ formData.roleName }}</span>
      </div>
    </ContentWrap>
    <!-- 管理员表格 -->
    <ContentWrap>
      <div class="flex justify-between items-center">
        <div>
          {{ formData.role == 1 ? '人员列表' : '当前角色岗位' }}
        </div>
        <el-button
          type="primary"
          @click="addUser(formData, formData.application)"
          v-hasPermi="['bus:workOrderAdmin:create']"
        >
          <Icon icon="ep:plus" class="mr-2px" />
          {{ formData.role == 1 ? '添加人员' : '添加岗位' }}
        </el-button>
      </div>
      <el-table v-loading="loading" :data="list">
        <el-table-column label="姓名" align="center" prop="name" v-if="formData.role == 1" />
        <el-table-column label="岗位" align="center" prop="postName" v-if="formData.role == 2" />
        <el-table-column label="人数" align="center" prop="number" v-if="formData.role == 2" />
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button
              link
              type="danger"
              @click="handleDelete(scope.row.id)"
              v-hasPermi="['bus:workOrderAdmin:delete']"
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
    <Form ref="formRef" @success="getList" />
  </div>
</template>
<script lang="ts" setup>
import { workOrderAdminApi } from '@/api/bus/Category/UserSstting/index'
import Form from './Form.vue'
const { t } = useI18n() // 国际化
const loading = ref(false) // 表格加载中
const list = ref([]) // 表格数据
const total = ref(0) // 表格数据总数
const message = useMessage() // 消息弹窗
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  application: '',
  role: ''
}) // 查询参数
const formData = ref({}) // 表单数据
function openForm(res) {
  formData.value = res
  getList()
}
defineExpose({ openForm }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['success']) // 定义事件
const getList = () => {
  loading.value = true
  queryParams.application = formData.value.application
  queryParams.role = formData.value.role
  workOrderAdminApi.getPage(queryParams).then((res) => {
    list.value = res.list
    loading.value = false
  })
}
const formRef = ref()
const addUser = (res, application) => {
  formRef.value.openForm(res, application)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await workOrderAdminApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
</script>
