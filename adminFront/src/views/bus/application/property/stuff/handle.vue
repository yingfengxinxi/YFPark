<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="120px"
    >
      <el-form-item label="单据编号" prop="processNumber">
        <el-input
          v-model="queryParams.processNumber"
          placeholder="请输入单据编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="发起部门" prop="departmentId">
        <el-tree-select
          v-model="queryParams.departmentId"
          :data="deptTree"
          :props="defaultProps"
          filterable
          default-expand-all
          check-strictly
          node-key="id"
          class="!w-240px"
          placeholder="请选择"
        />
      </el-form-item>
      <el-form-item label="处置仓库" prop="depositoryId">
        <el-tree-select
          v-model="queryParams.depositoryId"
          :data="stockTree"
          :props="defaultProps"
          filterable
          default-expand-all
          check-strictly
          node-key="id"
          placeholder="请选择"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="发起时间" prop="launchTime">
        <el-date-picker
          v-model="queryParams.launchTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
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
      <el-form-item label="处置发起人" prop="muserUid">
        <el-select v-model="queryParams.muserUid" placeholder="请选择" class="!w-240px">
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
        <el-button type="primary" plain @click="openForm('create')">
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button type="success" plain @click="handleExport" :loading="exportLoading">
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="单据编号" align="center" prop="processNumber" width="180">
        <template #default="scope">
          <template v-if="scope.row.processNumber">
            <el-button link type="primary" @click="openForm('detail', scope.row)">{{
              scope.row.processNumber
            }}</el-button>
          </template>
          <template v-else> -- </template>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status" width="180">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PROPERTY_AUDIT_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="发起部门"
        align="center"
        prop="departmentName"
        width="180"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="处置仓库"
        align="center"
        prop="depositoryName"
        width="180"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="发起时间"
        align="center"
        prop="launchTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="合计金额"
        align="center"
        prop="totalPrice"
        width="180"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="处置原因"
        align="center"
        prop="remark"
        width="180"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="处置发起人" align="center" prop="cuserUid" width="180"
        ><template #default="scope">
          <template v-if="scope.row.muserUid">
            <template v-for="user in userOptions" :key="user.id">
              <span v-if="user.id === scope.row.muserUid">{{ user.nickname }}</span>
            </template>
          </template>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="180">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)"> 审批信息 </el-button>
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

  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="getList" />
  <ApprovalInformation ref="approvalRef" :userOptions="userOptions" />
</template>

<script setup lang="ts">
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { dateFormatter } from '@/utils/formatTime'
import { defaultProps, handleTree } from '@/utils/tree'
import ApprovalInformation from './component/approvalInformation.vue'
import { PropertyStuffDepositoryApi, PropertyStuffDepositoryVO } from '@/api/bus/stuff/depository'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import download from '@/utils/download'
import { PropertyStuffHandleApi, PropertyStuffHandleVO } from '@/api/bus/stuff/handle'
import Form from './component/Form.vue'

/** 耗材业务处置 列表 */
defineOptions({ name: 'PropertyStuffHandle' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const deptTree = ref() //部门树结构
const stockTree = ref() //仓库树结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const loading = ref(true) // 列表的加载中
const list = ref<PropertyStuffHandleVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  processCode: undefined,
  processNumber: undefined,
  departmentId: undefined,
  departmentName: undefined,
  depositoryId: undefined,
  launchTime: [],
  totalPrice: undefined,
  status: undefined,
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
    const data = await PropertyStuffHandleApi.getPropertyStuffHandlePage(queryParams)
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

/** 审批信息 */
const approvalRef = ref()
const handleDetail = async (row: any) => {
  approvalRef.value.open('stuff_handle', row)
}
/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, row?: any) => {
  formRef.value.open('stuff_handle', type, row ? row.processCode : undefined, row)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyStuffHandleApi.deletePropertyStuffHandle(id)
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
    const data = await PropertyStuffHandleApi.exportPropertyStuffHandle(queryParams)
    download.excel(data, '耗材业务处置.xls')
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
onMounted(async () => {
  // 获得部门树
  stockTree.value = await PropertyStuffDepositoryApi.getPropertyStuffDepositoryTree()

  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())

  // 获得用户列表
  userOptions.value = await UserApi.getSimpleUserList()
  getList()
})
</script>
