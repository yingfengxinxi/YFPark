<template>
  <ContentWrap class="flex items-center" v-show="!ShowCategoryLabel">
    <Icon icon="ep:arrow-left" @click="emit('success')" class="transform-translate-y-1px" />
    <span class="text-16px font-600 ml-10px">{{ formName }}</span>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap v-if="!ShowCategoryLabel">
    <div class="flex justify-between">
      工单类型
      <el-button
        type="primary"
        @click="openForm('create')"
        v-hasPermi="['bus:categorySubcat:create']"
      >
        <Icon icon="ep:plus" />工单子类
      </el-button>
    </div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="类型名称" align="center" prop="name" />
      <el-table-column label="负责岗位" align="center" prop="stationName" />
      <el-table-column label="负责部门" align="center" prop="departmentName" />
      <el-table-column label="标签管理" align="center">
        <template #default="scope">
          <el-button type="primary" text @click="goLabel(scope.row)"> 设置标签 </el-button>
        </template>
      </el-table-column>
      <el-table-column label="排序值" align="center" prop="sort" />
      <el-table-column label="累计工单数量" align="center" prop="orderNumber" />
      <el-table-column label="满意度" align="center" prop="satisfactionLevel" />
      <el-table-column label="启用状态" align="center" prop="status">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="1"
            inactive-value="0"
            @change="changestatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="chargeChange(scope.row)"
            v-hasPermi="['bus:categoryFeeSet:save']"
          >
            收费设置
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:categorySubcat:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:categorySubcat:delete']"
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
      @pagination="gecategorySubcat"
    />
  </ContentWrap>
  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="gecategorySubcat" />
  <!-- 收费设置 -->
  <chargeSetting ref="chargeSettingRef" />
  <!-- 标签管理 -->
  <div v-show="ShowCategoryLabel">
    <CategoryLabel ref="CategoryLabelRef" @success="tagback" />
  </div>
</template>

<script setup lang="ts">
import { CategorySubcatApi, VO } from '@/api/bus/Category/CategorySubcat/index'
import Form from './Form.vue'
import chargeSetting from './chargeSetting.vue'
import CategoryLabel from '../CategoryLabel/index.vue'
/** 工单分类子类信息 列表 */
defineOptions({ name: 'CategorySubcat' })
const emit = defineEmits(['success']) // 提供 emit 方法，用于触发父组件事件
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(false) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const applicationValue = ref('')
const formId = ref(0) //工单大类id
const formName = ref('')
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  application: applicationValue.value,
  categoryId: formId.value
})

function open(application: string, id: number, name: string) {
  formName.value = name
  applicationValue.value = application
  formId.value = id
  queryParams.categoryId = id
  queryParams.application = application
  gecategorySubcat()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 查询列表 */
const gecategorySubcat = async () => {
  loading.value = true
  try {
    const data = await CategorySubcatApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, applicationValue.value, formId.value, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await CategorySubcatApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await gecategorySubcat()
  } catch {}
}
//更改启用状态
const changestatus = async (row) => {
  try {
    await CategorySubcatApi.changeStatus({
      id: row.id,
      status: row.status
    })
    message.success(t('common.updateSuccess'))
  } catch {}
}

//收费设置
const chargeSettingRef = ref()
const chargeChange = async (row: any) => {
  chargeSettingRef.value.open(row.id, applicationValue.value, formId.value)
}
//设置标签
const CategoryLabelRef = ref()
const ShowCategoryLabel = ref(false)
const goLabel = async (row) => {
  ShowCategoryLabel.value = true
  CategoryLabelRef.value.open(row)
}
const tagback = () => {
  ShowCategoryLabel.value = false
  gecategorySubcat()
}
</script>
