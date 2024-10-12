<template>
  <!-- 列表 -->
  <ContentWrap v-if="!CategorySubcatShow">
    <span class="text-16px font-600">分类管理</span>
  </ContentWrap>
  <ContentWrap v-if="!CategorySubcatShow">
    <div class="flex justify-between">
      工单大类
      <el-button type="primary" @click="openForm('create')" v-hasPermi="['bus:category:create']">
        <Icon icon="ep:plus" />工单类型
      </el-button>
    </div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="大类名称" align="center" prop="name" />
      <el-table-column label="子任务数量" align="center" prop="subcatNumber">
        <template #default="scope">
          <span class="color-#409EFF cursor-pointer" @click="changeCategorySubcat(scope.row)">{{
            scope.row.subcatNumber + '(点击查看)'
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="运作模式" align="center" prop="type">
        <template #default="scope">
          <span v-if="scope.row.type == 1">派单+抢单</span>
          <span v-if="scope.row.type == 2">派单</span>
        </template>
      </el-table-column>
      <el-table-column label="排序值" align="center" prop="sort" />
      <el-table-column label="累计工单数量" align="center" prop="orderNumber" />
      <el-table-column label="绑定楼宇" align="center" prop="buildBind" />
      <el-table-column label="是否支持转单" align="center" prop="hasChange">
        <template #default="scope">
          <el-switch
            v-model="scope.row.hasChange"
            active-value="1"
            inactive-value="0"
            @change="changeRowhasChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:category:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:category:delete']"
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
  <!-- 分类子类 -->
  <CategorySubcat ref="categorySubcatRef" @success="goback" v-if="CategorySubcatShow" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { CategoryApi, VO } from '@/api/bus/Category/category/index'
import CategorySubcat from './../CategorySubcat/index.vue'
import Form from './Form.vue'
import { getStrDictOptions } from '@/utils/dict'
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
/** 工单分类配置 列表 */
defineOptions({ name: 'Category' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  application: application.value
})

/** 查询列表 */
const getList = async () => {
  if (queryParams.application !== application.value) {
    queryParams.application = application.value
  }
  loading.value = true
  try {
    const data = await CategoryApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, application.value, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await CategoryApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
//更改是否支持转单
const changeRowhasChange = async (row) => {
  try {
    await CategoryApi.updateHasChange({
      id: row.id,
      hasChange: row.hasChange
    })
    message.success(t('common.updateSuccess'))
  } catch {}
}
/////切换子类
const CategorySubcatShow = ref(false)
const categorySubcatRef = ref()
const changeCategorySubcat = (row) => {
  CategorySubcatShow.value = true
  setTimeout(() => {
    categorySubcatRef.value.open(application.value, row.id, row.name)
  }, 500)
}
const goback = () => {
  CategorySubcatShow.value = false
  getList()
}
/** 初始化 **/
onMounted(async () => {
  getapplication()
  getList()
})
</script>
