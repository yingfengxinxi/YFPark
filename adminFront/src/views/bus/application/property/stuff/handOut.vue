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
      <el-menu-item index="1">派发</el-menu-item>
      <el-menu-item index="2">退库</el-menu-item>
    </el-menu>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px m-t-20px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
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
      <template v-if="activeIndex === '1'">
        <el-form-item label="领用人" prop="receiveUid">
          <el-select v-model="queryParams.receiveUid" placeholder="请选择" class="!w-240px">
            <el-option
              v-for="item in userOptions"
              :key="item.id"
              :label="item.nickname"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="领用部门" prop="departmentId">
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
        <el-form-item label="出库仓库" prop="depositoryId">
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
      </template>
      <template v-else>
        <el-form-item label="退库人" prop="retreatUid">
          <el-select v-model="queryParams.retreatUid" placeholder="请选择" class="!w-240px">
            <el-option
              v-for="item in userOptions"
              :key="item.id"
              :label="item.nickname"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="退库部门" prop="departmentId">
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
        <el-form-item label="入库仓库" prop="depositoryId">
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
      </template>
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
      <el-form-item label="处理人" prop="muserUid">
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
      <el-table-column label="单据编号" align="center" prop="processNumber" width="240">
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
      <template v-if="activeIndex === '1'">
        <el-table-column label="领用人" align="center" prop="receiveUid"
          ><template #default="scope">
            <template v-if="scope.row.receiveUid">
              <template v-for="user in userOptions" :key="user.id">
                <span v-if="user.id === scope.row.receiveUid">{{ user.nickname }}</span>
              </template>
            </template>
            <span v-else>--</span>
          </template>
        </el-table-column>
        <el-table-column
          label="领用部门"
          align="center"
          prop="departmentName"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          label="出库仓库"
          align="center"
          prop="depositoryName"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          label="派发时间"
          align="center"
          prop="handoutTime"
          :formatter="dateFormatter"
          width="180px"
        />
        <el-table-column
          label="派发备注"
          align="center"
          prop="remark"
          :formatter="tableColumnEmptyPlaceholder"
        />
      </template>
      <template v-else>
        <el-table-column label="退库人" align="center" prop="receiveUid"
          ><template #default="scope">
            <template v-if="scope.row.retreatUid">
              <template v-for="user in userOptions" :key="user.id">
                <span v-if="user.id === scope.row.retreatUid">{{ user.nickname }}</span>
              </template>
            </template>
            <span v-else>--</span>
          </template>
        </el-table-column>
        <el-table-column
          label="退库部门"
          align="center"
          prop="departmentName"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          label="入库仓库"
          align="center"
          prop="depositoryName"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          label="退库时间"
          align="center"
          prop="retreatDate"
          :formatter="dateFormatter"
          width="180px"
        >
          <template #default="scope">
            {{ dateFormatter2(scope.row, 'retreatDate', scope.row.retreatDate) || '--' }}
          </template>
        </el-table-column>
        <el-table-column
          label="退库备注"
          align="center"
          prop="remark"
          :formatter="tableColumnEmptyPlaceholder"
        />
      </template>
      <el-table-column label="处理人" align="center" prop="muserUid"
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
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import { defaultProps, handleTree } from '@/utils/tree'
import ApprovalInformation from './component/approvalInformation.vue'
import { PropertyStuffDepositoryApi, PropertyStuffDepositoryVO } from '@/api/bus/stuff/depository'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import download from '@/utils/download'
import { PropertyStuffHandoutApi, PropertyStuffHandoutVO } from '@/api/bus/stuff/handOut'
import { PropertyStuffRetreatApi, PropertyStuffRetreatVO } from '@/api/bus/stuff/retreat'
import Form from './component/Form.vue'

/** 耗材业务派发 列表 */
defineOptions({ name: 'PropertyStuffHandout' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const deptTree = ref() //部门树结构
const stockTree = ref() //仓库树结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const loading = ref(true) // 列表的加载中
const list = ref<PropertyStuffHandoutVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  processCode: undefined,
  processNumber: undefined,
  receiveUid: undefined,
  retreatUid: undefined,
  departmentId: undefined,
  departmentName: undefined,
  depositoryId: undefined,
  handoutTime: [],
  status: undefined,
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
    const data =
      activeIndex.value == '1'
        ? await PropertyStuffHandoutApi.getPropertyStuffHandoutPage(queryParams)
        : await PropertyStuffRetreatApi.getPropertyStuffRetreatPage(queryParams)
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
const openForm = (type: string, row?: any) => {
  formRef.value.open(
    activeIndex.value == '1' ? 'stuff_hand_out' : 'stuff_retreat_out',
    type,
    row ? row.processCode : undefined,
    row
  )
}

/** 审批信息 */
const approvalRef = ref()
const handleDetail = async (row: any) => {
  approvalRef.value.open(activeIndex.value == '1' ? 'stuff_hand_out' : 'stuff_retreat_out', row)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    activeIndex.value == '1'
      ? await PropertyStuffHandoutApi.deletePropertyStuffHandout(id)
      : PropertyStuffRetreatApi.deletePropertyStuffRetreat(id)
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
    const data = await PropertyStuffHandoutApi.exportPropertyStuffHandout(queryParams)
    download.excel(data, '耗材业务派发.xls')
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
  if (query.status) queryParams.status = Number(query.status)
  if (query.activeIndex) activeIndex.value = query.activeIndex as string
  // 获得部门树
  stockTree.value = await PropertyStuffDepositoryApi.getPropertyStuffDepositoryTree()

  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())

  // 获得用户列表
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
