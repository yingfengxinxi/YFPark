<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px m-t-20px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="120px"
    >
      <el-form-item label="变更后使用人" prop="afterUseUid">
        <el-select
          v-model="queryParams.afterUseUid"
          placeholder="请选择变更后使用人"
          class="!w-240px"
          clearable
        >
          <el-option
            v-for="item in userOptions"
            :key="item.id"
            :label="item.nickname"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单据编号" prop="number">
        <el-input
          v-model="queryParams.number"
          placeholder="请输入单据编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="变更后使用部门" prop="afterUseDepartmentId">
        <el-tree-select
          v-model="queryParams.afterUseDepartmentId"
          :data="deptTree"
          :props="defaultProps"
          filterable
          default-expand-all
          check-strictly
          node-key="id"
          placeholder="请选择部门"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item label="变更处理人" prop="operateUid">
        <el-select
          v-model="queryParams.operateUid"
          placeholder="请选择变更处理人"
          class="!w-240px"
          clearable
        >
          <el-option
            v-for="item in userOptions"
            :key="item.id"
            :label="item.nickname"
            :value="item.id"
          />
        </el-select>
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
        <el-button
          type="primary"
          plain
          @click="openForm('create', undefined)"
          v-hasPermi="['bus:property-change:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:property-change:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
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
      <el-table-column label="变更后使用人" align="center" prop="afterUseUid">
        <template #default="scope">
          <template v-if="scope.row.afterUseUid">
            <template v-for="user in userOptions" :key="user.id">
              <span v-if="user.id === scope.row.afterUseUid">{{ user.nickname }}</span>
            </template>
          </template>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column
        label="变更后使用部门"
        align="center"
        prop="afterUseDepartmentName"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="变更时间"
        align="center"
        prop="afterTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="变更处理人" align="center" prop="operateUid">
        <template #default="scope">
          <template v-if="scope.row.operateUid">
            <template v-for="user in userOptions" :key="user.id">
              <span v-if="user.id === scope.row.operateUid">{{ user.nickname }}</span>
            </template>
          </template>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column
        label="变更说明"
        align="center"
        prop="remark"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)"> 审批信息 </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:property-change:delete']"
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
  <ChangeForm ref="changeFormRef" @success="getList" />
  <ApprovalInformation ref="approvalRef" :userOptions="userOptions" />
</template>

<script setup lang="ts">
import ApprovalInformation from './component/approvalInformation.vue'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { defaultProps, handleTree } from '@/utils/tree'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyChangeApi, PropertyChangeVO } from '@/api/bus/property/propertyChange'
import ChangeForm from './component/changeForm.vue'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'

/** 资产变更领用人 列表 */
defineOptions({ name: 'PropertyChange' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const deptTree = ref() // 部门树形结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const loading = ref(true) // 列表的加载中
const list = ref<PropertyChangeVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  propertyIds: undefined,
  number: undefined,
  processCode: undefined,
  status: undefined,
  afterUseUid: undefined,
  afterUseDepartmentId: undefined,
  afterTime: [],
  operateUid: undefined,
  operateTime: [],
  remark: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PropertyChangeApi.getPropertyChangePage(queryParams)
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
const changeFormRef = ref()
const openForm = async (type: string, row) => {
  // formRef.value.open(type, id)
  let list = []

  changeFormRef.value.open(type, list, row)
}

/** 审批信息 */
const approvalRef = ref()
const handleDetail = async (row: any) => {
  approvalRef.value.open('change_property', row)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认承担
    await message.delConfirm()
    // 发起删除
    await PropertyChangeApi.deletePropertyChange(id)
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
    const data = await PropertyChangeApi.exportPropertyChange(queryParams)
    download.excel(data, '资产变更领用人.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
/** 激活时 **/
onActivated(() => {
  getList()
})

const { query } = useRoute()
/** 初始化 **/
onMounted(async () => {
  if (query.status) {
    queryParams.status = query.status
  }
  // 获得部门树
  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())
  userOptions.value = await UserApi.getSimpleUserList()
  getList()
})
</script>
