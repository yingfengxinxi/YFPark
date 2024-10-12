<template>
  <!-- 搜索工作栏 -->
  <el-form
    class="-mb-15px m-t-20px"
    :model="queryParams"
    ref="queryFormRef"
    :inline="true"
    label-width="100px"
  >
    <el-form-item label="处置发起部门" prop="departmentId">
      <el-tree-select
        v-model="queryParams.departmentId"
        :data="deptTree"
        :props="defaultProps"
        filterable
        check-strictly
        node-key="id"
        clearable
        placeholder="请选择部门"
        class="!w-240px"
      />
    </el-form-item>
    <el-form-item label="单据编号" prop="processNumber">
      <el-input
        v-model="queryParams.processNumber"
        placeholder="请输入单据编号"
        clearable
        @keyup.enter="handleQuery"
        class="!w-240px"
      />
    </el-form-item>
    <el-form-item label="单据状态" prop="status">
      <el-select
        v-model="queryParams.status"
        placeholder="请选择单据状态"
        clearable
        class="!w-240px"
      >
        <el-option
          v-for="dict in getIntDictOptions(DICT_TYPE.PROPERTY_AUDIT_STATUS)"
          :key="dict.value"
          :label="dict.label"
          :value="dict.value"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
      <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      <el-button type="primary" plain @click="openForm('create')">
        <Icon icon="ep:plus" class="mr-5px" /> 新增
      </el-button>
    </el-form-item>
  </el-form>
  <el-table
    class="m-t-40px"
    v-loading="loading"
    :data="list"
    :stripe="true"
    :show-overflow-tooltip="true"
  >
    <el-table-column label="单据编号" align="center" prop="processNumber">
      <template #default="scope">
        <template v-if="scope.row.processNumber">
          <el-button link type="primary" @click="openForm('detail', scope.row)">{{
            scope.row.processNumber
          }}</el-button>
        </template>
        <template v-else> -- </template>
      </template>
    </el-table-column>
    <el-table-column label="单据状态" align="center" prop="status">
      <template #default="scope">
        <dict-tag :type="DICT_TYPE.PROPERTY_AUDIT_STATUS" :value="scope.row.status" />
      </template>
    </el-table-column>
    <el-table-column label="处置发起人" align="center" prop="cuserUid"
      ><template #default="scope">
        <template v-if="scope.row.cuserUid">
          <template v-for="user in userOptions" :key="user.id">
            <span v-if="user.id === scope.row.cuserUid">{{ user.nickname }}</span>
          </template>
        </template>
        <span v-else>--</span>
      </template>
    </el-table-column>
    <el-table-column label="发起部门" align="center" prop="departmentName" />

    <el-table-column label="处置类型" align="center" prop="handleType">
      <template #default="scope">
        <dict-tag :type="DICT_TYPE.DISPOSEEOF_TYPE" :value="scope.row.handleType" />
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

  <!-- 表单弹窗：添加/修改 -->
  <DisposeOfForm ref="disposeOfFormRef" @success="getList" />
  <ApprovalInformation ref="approvalRef" :userOptions="userOptions" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyHandleApi, PropertyHandleVO } from '@/api/bus/property/propertyHandle'
import DisposeOfForm from '../../../../assets/component/disposeOfForm.vue'
import ApprovalInformation from '../../../../assets/component/AssetsListTable.vue'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { defaultProps, handleTree } from '@/utils/tree'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
/** 资产处置单据记录 列表 */
defineOptions({ name: 'PropertyHandle' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const list = ref<PropertyHandleVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  departmentId: undefined,
  processNumber: undefined,
  status: undefined,
  handleAmount: undefined,
  handleExpenses: undefined,
  handleType: undefined,
  remark: undefined,
  applyTime: [],
  cuserUid: undefined,
  muserUid: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const props = defineProps({
  activeIndex: {
    type: String,
    default: '1'
  },
  userOptions: {
    type: Object,
    default: function () {
      return []
    }
  },
  deptTree: {
    type: Object,
    default: function () {
      return []
    }
  }
})

/** 查询列表 */
const getList = async () => {
  loading.value = true

  queryParams.muserUid = userStore.getUser.id
  try {
    const data = await PropertyHandleApi.getPropertyHandlePage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

watch(
  () => props.activeIndex,
  (newValue) => {
    if (newValue == '7') {
      getList()
    }
  },
  { immediate: true, deep: true }
)
/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 审批信息 */
const approvalRef = ref()
const handleDetail = async (row: any) => {
  console.log(row, 'row')
  approvalRef.value.open('handle_property', row)
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const disposeOfFormRef = ref()
const openForm = async (type: string, row?: any) => {
  // formRef.value.open(type, id)
  let list = []
  disposeOfFormRef.value.open(1, type, list, row)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyHandleApi.deletePropertyHandle(id)
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
    const data = await PropertyHandleApi.exportPropertyHandle(queryParams)
    download.excel(data, '资产处置单据记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
</script>
