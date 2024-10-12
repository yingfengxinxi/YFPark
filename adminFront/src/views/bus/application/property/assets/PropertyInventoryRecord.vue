<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="机构id" prop="orgId">
        <el-input
          v-model="queryParams.orgId"
          placeholder="请输入机构id"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="盘点清单id" prop="inventoryId">
        <el-input
          v-model="queryParams.inventoryId"
          placeholder="请输入盘点清单id"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产id" prop="propertyId">
        <el-input
          v-model="queryParams.propertyId"
          placeholder="请输入资产id"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产分类id" prop="typeId">
        <el-input
          v-model="queryParams.typeId"
          placeholder="请输入资产分类id"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="资产状态" prop="propertyStatus">
        <el-select
          v-model="queryParams.propertyStatus"
          placeholder="请选择资产状态"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否在盘点范围之内" prop="isRange">
        <el-input
          v-model="queryParams.isRange"
          placeholder="请输入是否在盘点范围之内"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="是否修改资产信息 0否 1是" prop="isUpdate">
        <el-date-picker
          v-model="queryParams.isUpdate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="盘点备注" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入盘点备注"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="盘点标签" prop="tag">
        <el-input
          v-model="queryParams.tag"
          placeholder="请输入盘点标签"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="盘点时间" prop="inventoryTime">
        <el-date-picker
          v-model="queryParams.inventoryTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="盘点人" prop="inventoryUid">
        <el-input
          v-model="queryParams.inventoryUid"
          placeholder="请输入盘点人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="当前位置" prop="positionId">
        <el-input
          v-model="queryParams.positionId"
          placeholder="请输入当前位置"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="处理人" prop="handleUid">
        <el-input
          v-model="queryParams.handleUid"
          placeholder="请输入处理人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产所属部门id" prop="departmentId">
        <el-input
          v-model="queryParams.departmentId"
          placeholder="请输入资产所属部门id"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产盘点信息" prop="propertyInfo">
        <el-input
          v-model="queryParams.propertyInfo"
          placeholder="请输入资产盘点信息"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入资产名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产编码" prop="propertyNumber">
        <el-input
          v-model="queryParams.propertyNumber"
          placeholder="请输入资产编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['bus:property-inventory-record:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:property-inventory-record:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="机构id" align="center" prop="orgId" />
      <el-table-column label="盘点清单id" align="center" prop="inventoryId" />
      <el-table-column label="资产id" align="center" prop="propertyId" />
      <el-table-column label="资产分类id" align="center" prop="typeId" />
      <el-table-column label="0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束" align="center" prop="status" />
      <el-table-column label="资产状态" align="center" prop="propertyStatus" />
      <el-table-column label="是否在盘点范围之内" align="center" prop="isRange" />
      <el-table-column label="是否修改资产信息 0否 1是" align="center" prop="isUpdate" />
      <el-table-column label="盘点备注" align="center" prop="remark" />
      <el-table-column label="盘点图片" align="center" prop="image" />
      <el-table-column label="盘点标签" align="center" prop="tag" />
      <el-table-column
        label="盘点时间"
        align="center"
        prop="inventoryTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="盘点人" align="center" prop="inventoryUid" />
      <el-table-column label="当前位置" align="center" prop="positionId" />
      <el-table-column label="处理人" align="center" prop="handleUid" />
      <el-table-column label="资产所属部门id" align="center" prop="departmentId" />
      <el-table-column label="资产盘点信息" align="center" prop="propertyInfo" />
      <el-table-column label="资产名称" align="center" prop="name" />
      <el-table-column label="资产编码" align="center" prop="propertyNumber" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:property-inventory-record:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:property-inventory-record:delete']"
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
  <PropertyInventoryRecordForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import {
  PropertyInventoryRecordApi,
  PropertyInventoryRecordVO
} from '@/api/bus/property/propertyInventoryRecord'
import PropertyInventoryRecordForm from './PropertyInventoryRecordForm.vue'

/** 资产盘点记录 列表 */
defineOptions({ name: 'PropertyInventoryRecord' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<PropertyInventoryRecordVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  inventoryId: undefined,
  propertyId: undefined,
  typeId: undefined,
  status: undefined,
  propertyStatus: undefined,
  isRange: undefined,
  isUpdate: [],
  remark: undefined,
  image: undefined,
  tag: undefined,
  inventoryTime: [],
  inventoryUid: undefined,
  positionId: undefined,
  handleUid: undefined,
  departmentId: undefined,
  propertyInfo: undefined,
  name: undefined,
  propertyNumber: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PropertyInventoryRecordApi.getPropertyInventoryRecordPage(queryParams)
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
    await PropertyInventoryRecordApi.deletePropertyInventoryRecord(id)
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
    const data = await PropertyInventoryRecordApi.exportPropertyInventoryRecord(queryParams)
    download.excel(data, '资产盘点记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
/** 激活时 **/
onActivated(() => {
  getList()
})
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
