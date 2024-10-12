<template>
  <el-drawer v-model="dialogVisible" size="700px" :title="dialogTitle">
    <el-alert
      title="将展示在用户移动端的'常用电话'页面。如果项目不使用用户移动端，则无需添加。"
      type="info"
      closable
      class="custom-alert !m-b-10px"
    />
    <el-button
      type="primary"
      @click="openForm('create')"
      v-hasPermi="['bus:phone-category:create']"
    >
      <Icon icon="ep:plus" class="mr-5px" /> 新增
    </el-button>

    <!-- 列表 -->
    <ContentWrap>
      <el-table
        v-loading="loading"
        :data="list"
        :show-overflow-tooltip="true"
        row-key="id"
        default-expand-all
        border
        lazy
        :expanded-row-keys="expandedRows"
        :tree-props="{ children: 'villagePhoneDOS', hasChildren: 'hasChildren' }"
      >
        <el-table-column label="分类名称" prop="catName">
          <template #default="scope">
            {{ scope.row.catName || scope.row.phoneName }}
          </template>
        </el-table-column>
        <el-table-column label="电话" align="center" prop="phone" />
        <el-table-column label="状态" width="80px">
          <template #default="scope">
            <span v-if="scope.row.status == 1" class="c-#81b337">展开</span>
            <span v-else class="c-#bd3124">隐藏</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <template v-if="scope.row.phone">
              <el-button
                link
                type="primary"
                @click="AddPhone('update', scope.row.catId, scope.row.pid)"
                v-hasPermi="['bus:phone-category:update']"
              >
                编辑
              </el-button>
              <el-button
                link
                type="danger"
                @click="DeletePhone(scope.row.pid)"
                v-hasPermi="['bus:phone-category:delete']"
              >
                删除
              </el-button>
            </template>
            <template v-else>
              <el-button
                link
                type="primary"
                @click="openForm('update', scope.row.id)"
                v-hasPermi="['bus:phone-category:update']"
              >
                编辑
              </el-button>
              <el-button
                link
                type="primary"
                @click="AddPhone('create', scope.row.id)"
                v-hasPermi="['bus:village-phone:create']"
              >
                添加号码
              </el-button>
              <el-button
                link
                type="danger"
                @click="handleDelete(scope.row.id)"
                v-hasPermi="['bus:phone-category:delete']"
              >
                删除
              </el-button>
            </template>
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
  </el-drawer>
  <!-- 表单弹窗：添加/修改 -->
  <PhoneCategoryForm ref="formRef" @success="getList" />
  <!-- 号码添加/修改 -->
  <VillagePhoneForm ref="AddPhoneRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PhoneCategoryApi, PhoneCategoryVO, VillagePhoneApi } from '@/api/bus/village/phone'
import PhoneCategoryForm from './PhoneCategoryForm.vue'
import VillagePhoneForm from './VillagePhoneForm.vue'

/** 项目电话类型 列表 */
defineOptions({ name: 'PhoneCategory' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('项目常用电话') // 弹窗的标题
const loading = ref(true) // 列表的加载中
const list = ref<PhoneCategoryVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  villageId: undefined,
  catName: undefined,
  sort: undefined,
  status: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
// 获取子级数据
const loadData = async (row, treeNode, resolve) => {
  console.log(row, treeNode.level)
  const data = await VillagePhoneApi.getVillagePhoneList({
    pageNo: 1,
    pageSize: 10,
    villageId: queryParams.villageId
  })
  // data.map((item, i) => {
  //   return {
  //     ...item,
  //     hasChildren: false // 设置true就有折叠图标
  //   }
  // })
  let aa = [
    {
      catId: 3,
      createTime: 1720702606000,
      id: 8,
      orgId: null,
      phone: '19853633880',
      phoneName: '1111',
      sort: 1,
      status: 1,
      urgent: 1,
      villageId: 157,
      hasChildren: false
    }
  ]
  resolve(aa)
  console.log(data)
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PhoneCategoryApi.getPhoneCategoryPage(queryParams)
    // list.value = data.list.map((item, i) => {
    //   return {
    //     ...item,
    //     hasChildren: true // 设置true就有折叠图标
    //   }
    // })
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const AddPhoneRef = ref()
const AddPhone = async (type: string, catId?: number, pid?: number) => {
  console.log(pid, 'pid')
  AddPhoneRef.value.open(type, pid, catId, queryParams.villageId)
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
/** 打开弹窗 */
const open = async (id?: number) => {
  dialogVisible.value = true
  queryParams.villageId = id
  getList()
}
/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id, queryParams.villageId)
}
const DeletePhone = async (pid: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await VillagePhoneApi.deleteVillagePhone(pid)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PhoneCategoryApi.deletePhoneCategory(id)
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
    const data = await PhoneCategoryApi.exportPhoneCategory(queryParams)
    download.excel(data, '项目电话类型.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 初始化 **/
onMounted(() => {})
</script>
<style lang="scss" scoped>
.custom-alert {
  background-color: #f0f9ff;
  border: 1px solid #a3d6ff;
  color: #000000d9; /* 如果需要也可以改变文字颜色 */
  padding-right: 30px;
}
</style>
