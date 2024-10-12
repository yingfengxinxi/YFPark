<template>
  <ContentWrap class="viewHeader">
    <el-menu
      class="flex-1"
      :default-active="activeIndex"
      ellipsis
      mode="horizontal"
      popper-append-to-body
      teleported
      @select="handleSelect"
    >
      <el-menu-item index="1">借出</el-menu-item>
      <el-menu-item index="2">归还</el-menu-item>
    </el-menu>
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
      <el-form-item label="借用人" prop="lendUid" v-if="activeIndex == '1'">
        <el-select
          v-model="queryParams.lendUid"
          placeholder="请选择借用人"
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
      <el-form-item label="借用部门" prop="departmentId" v-if="activeIndex == '1'">
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
      <el-form-item label="归还处理人" prop="operateUid" v-if="activeIndex == '2'">
        <el-select
          v-model="queryParams.operateUid"
          placeholder="请选择归还处理人"
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

      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create', undefined)"
          v-hasPermi="['bus:property-lendout:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:property-lendout:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="单据编号" align="center" prop="number" width="160px">
        <template #default="scope">
          <template v-if="scope.row.number">
            <el-button link type="primary" @click="openForm('detail', scope.row)">{{
              scope.row.number
            }}</el-button>
          </template>
          <template v-else> -- </template>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status" width="160px">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PROPERTY_AUDIT_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="借用人"
        align="center"
        prop="lendUid"
        width="160px"
        v-if="activeIndex == '1'"
        ><template #default="scope">
          <template v-if="scope.row.lendUid">
            <template v-for="user in userOptions" :key="user.id">
              <span v-if="user.id === scope.row.lendUid">{{ user.nickname }}</span>
            </template>
          </template>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="activeIndex == '1' ? '借用部门' : '归还后使用部门'"
        align="center"
        prop="departmentName"
        width="160px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="借出时间"
        align="center"
        prop="lendTime"
        :formatter="dateFormatter"
        width="180px"
        v-if="activeIndex == '1'"
      />
      <el-table-column
        :label="activeIndex == '1' ? '预计归还时间' : '归还时间'"
        align="center"
        prop="expectRevertTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="处理人" align="center" prop="operateUid" width="160px">
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
        label="处理时间"
        align="center"
        prop="operateTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        width="160px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="目标房源"
        align="center"
        prop="buildBind"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
        v-if="activeIndex == '1'"
      >
        <template #default="scope">
          <span v-if="scope.row.buildBind"
            >{{ JSON.parse(scope.row.buildBind).buildName }}/{{
              JSON.parse(scope.row.buildBind).layerName
            }}/{{ JSON.parse(scope.row.buildBind).roomName }}</span
          >
          <span v-else>--</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" fixed="right" width="160">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)"> 审批信息 </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:property-lendout:delete']"
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
  <!-- 借用/归还 -->
  <LendReturn ref="LendReturnFormRef" @success="getList" />
  <ApprovalInformation ref="approvalRef" :userOptions="userOptions" />
</template>

<script setup lang="ts">
import LendReturn from './component/lendReturn.vue'
import ApprovalInformation from './component/approvalInformation.vue'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { defaultProps, handleTree } from '@/utils/tree'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyLendoutApi, PropertyLendoutVO } from '@/api/bus/property/propertyLendout'

/** 资产借出 列表 */
defineOptions({ name: 'PropertyLendout' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const deptTree = ref() // 部门树形结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const loading = ref(true) // 列表的加载中
const list = ref<PropertyLendoutVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  villageId: undefined,
  buildId: undefined,
  roomId: undefined,
  propertyIds: undefined,
  number: undefined,
  processCode: undefined,
  status: undefined,
  lendUid: undefined,
  departmentId: undefined,
  lendTime: [],
  expectRevertTime: [],
  operateUid: undefined,
  operateTime: [],
  remark: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const activeIndex = ref('1')
const handleSelect = (key: string) => {
  activeIndex.value = key
  getList()
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    queryParams.type = activeIndex.value
    const data = await PropertyLendoutApi.getPropertyLendoutPage(queryParams)
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

/** 审批信息 */
const approvalRef = ref()
const handleDetail = async (row: any) => {
  approvalRef.value.open(activeIndex.value == '1' ? 'lendout_property' : 'revert_property', row)
}

/** 添加/修改操作 */
const LendReturnFormRef = ref()
const openForm = async (type: string, row) => {
  // formRef.value.open(type, id)
  let list = []
  if (activeIndex.value == '1') {
    LendReturnFormRef.value.open(1, type, list, row)
  } else {
    LendReturnFormRef.value.open(2, type, list, row)
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyLendoutApi.deletePropertyLendout(id)
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
    const data = await PropertyLendoutApi.exportPropertyLendout(queryParams)
    download.excel(data, '资产借出.xls')
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
<style lang="scss" scoped>
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  background: none !important;
}
</style>
