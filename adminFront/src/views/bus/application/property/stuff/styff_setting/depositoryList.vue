<template>
  <div class="flex justify-between gap-18px rounded page">
    <ContentWrap class="!p-0px w-15% box-border">
      <div class="bg-white py-8px overflow-hidden">
        <el-scrollbar
          max-height="calc(100vh - var(--top-tool-height) - var(--tags-view-height) - 100px)"
        >
          <el-tree
            :data="Treedata"
            :props="defaultProps"
            @node-click="handleNodeClick"
            :highlight-current="true"
            ref="tree"
          />
        </el-scrollbar>
      </div>
    </ContentWrap>

    <!-- 列表 -->
    <ContentWrap class="w-85% p-20px">
      <!-- 搜索工作栏 -->
      <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="68px"
      >
        <el-form-item label="仓库编号" prop="number">
          <el-input
            v-model="queryParams.number"
            placeholder="请输入仓库编号"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="仓库名称" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入仓库名称"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
          <el-button type="primary" plain @click="openForm('create')">
            <Icon icon="ep:plus" class="mr-5px" /> 新增
          </el-button>
          <el-button
            type="success"
            plain
            @click="handleExport"
            :loading="exportLoading"
            v-hasPermi="['bus:property-stuff-depository:export']"
          >
            <Icon icon="ep:download" class="mr-5px" /> 导出
          </el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
        <el-table-column label="仓库编码" align="center" prop="number" />
        <el-table-column label="仓库名称" align="center" prop="name" />
        <el-table-column
          label="上级仓库"
          align="center"
          prop="parentName"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column label="状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :type="DICT_TYPE.PROPERTY_SHOW_STATUS" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column
          label="备注"
          align="center"
          prop="remark"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button link type="primary" @click="openForm('update', scope.row.id)">
              编辑
            </el-button>
            <el-button
              link
              type="primary"
              @click="forbidden(scope.row)"
              v-hasPermi="['bus:property-category:update']"
            >
              {{ scope.row.status == 1 ? '禁用' : '启用' }}
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
  </div>

  <!-- 表单弹窗：添加/修改 -->
  <DepositoryForm :loadList="Treedata" ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyStuffDepositoryApi, PropertyStuffDepositoryVO } from '@/api/bus/stuff/depository'
import DepositoryForm from './depositoryForm.vue'

/** 耗材档案信息 列表 */
defineOptions({ name: 'PropertyStuffDepository' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<PropertyStuffDepositoryVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  number: undefined,
  name: undefined,
  parentId: undefined,
  status: undefined,
  level: undefined,
  remark: undefined,
  sort: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
// 左侧树状结构
const Treedata = ref([])
const defaultProps = {
  children: 'children',
  label: 'name'
}
const tree = ref()
const handleNodeClick = (data) => {
  queryParams.parentId = data.id
  getList()
}
//获得树状数据
const GetTreeData = async () => {
  const data = await PropertyStuffDepositoryApi.getPropertyStuffDepositoryTree()
  Treedata.value = data
}
/** 禁用 */
const forbidden = async (row: PropertyStuffDepositoryVO) => {
  try {
    // 禁用的二次确认
    await message.delConfirm(
      `您确定要${row.status == 1 ? '禁用' : '启用'}分类【${row.name}】及全部下级分类吗？`
    )
    // 发起禁用
    await PropertyStuffDepositoryApi.updatePropertyStuffDepository({
      ...row,
      flag: true,
      status: row.status === 1 ? 2 : 1
    })
    message.success('保存成功')
    // 刷新列表
    await initData()
  } catch {}
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PropertyStuffDepositoryApi.getPropertyStuffDepositoryPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
const initData = async () => {
  await GetTreeData()
  await getList()
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
    await PropertyStuffDepositoryApi.deletePropertyStuffDepository(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await initData()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await PropertyStuffDepositoryApi.exportPropertyStuffDepository(queryParams)
    download.excel(data, '耗材档案信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  initData()
})
</script>
