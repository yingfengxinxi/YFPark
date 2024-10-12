<template>
  <!-- 搜索工作栏 -->
  <el-form
    class="-mb-15px m-t-20px"
    :model="queryParams"
    ref="queryFormRef"
    :inline="true"
    label-width="100px"
  >
    <el-form-item label="单据编号" prop="number">
      <el-input
        v-model="queryParams.number"
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
    <el-form-item label="入库处理人" prop="cuserUid">
      <el-select
        v-model="queryParams.cuserUid"
        placeholder="请选择入库处理人"
        @keyup.enter="handleQuery"
        class="!w-240px"
      >
        <el-option
          v-for="item in userOptions"
          :key="item.id"
          :label="item.nickname"
          :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
      <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      <!-- <el-button type="primary" plain @click="openForm('create')">
        <Icon icon="ep:plus" class="mr-5px" /> 新增
      </el-button> -->
    </el-form-item>
  </el-form>
  <el-table
    class="m-t-40px"
    v-loading="loading"
    :data="list"
    :stripe="true"
    :show-overflow-tooltip="true"
  >
    <el-table-column
      label="单据编号"
      align="center"
      prop="number"
      :formatter="tableColumnEmptyPlaceholder"
    >
      <template #default="scope">
        <template v-if="scope.row.number">
          <el-button link type="primary" @click="openForm('detail', scope.row)">{{
            scope.row.number
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
    <el-table-column label="入库处理人" align="center" prop="cuserUid">
      <template #default="scope">
        <template v-if="scope.row.cuserUid">
          <template v-for="user in userOptions" :key="user.id">
            <span v-if="user.id === scope.row.cuserUid">{{ user.nickname }}</span>
          </template>
        </template>
        <span v-else>--</span>
      </template>
    </el-table-column>
    <el-table-column
      label="入库处理时间"
      align="center"
      prop="operateTime"
      :formatter="dateFormatter"
      width="180px"
    >
      <template #default="scope">
        {{
          scope.row.operateTime ? dayjs(scope.row.operateTime).format('YYYY-MM-DD HH:mm:ss') : '--'
        }}
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center">
      <template #default="scope">
        <el-button link type="primary" @click="handleDetail(scope.row)"> 审批信息 </el-button>
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
  <PropertyDepositoryForm ref="formRef" @success="getList" />
  <ApprovalInformation ref="approvalRef" :userOptions="userOptions" />
</template>

<script setup lang="ts">
import dayjs from 'dayjs'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyDepositoryApi, PropertyDepositoryVO } from '@/api/bus/property/propertyDepository'
import PropertyDepositoryForm from '../../../../assets/PropertyDepositoryForm.vue'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import ApprovalInformation from '../../../../assets/component/approvalInformation.vue'

import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
/** 资产仓库信息 列表 */
defineOptions({ name: 'PropertyDepository' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const list = ref<PropertyDepositoryVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  number: undefined,
  status: undefined,
  operateUid: undefined,
  operateTime: [],
  remark: undefined,
  processCode: undefined,
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
    const data = await PropertyDepositoryApi.getPropertyDepositoryPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

watch(
  () => props.activeIndex,
  (val) => {
    if (val == '0') {
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

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = async (type: string, row?: any) => {
  // formRef.value.open(type, id)

  formRef.value.open(type, row)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyDepositoryApi.deletePropertyDepository(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 审批信息 */
const approvalRef = ref()
const handleDetail = async (row: any) => {
  approvalRef.value.open('put_depository', row)
}
/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await PropertyDepositoryApi.exportPropertyDepository(queryParams)
    download.excel(data, '资产仓库信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
</script>
