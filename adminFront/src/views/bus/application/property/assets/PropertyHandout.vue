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
      <el-form-item label="退库处理人" prop="operateUid" v-if="activeIndex == '2'">
        <el-select
          v-model="queryParams.operateUid"
          placeholder="请选择退库处理人"
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
      <el-form-item label="领用人" prop="receiveUid" v-if="activeIndex == '1'">
        <el-select
          v-model="queryParams.receiveUid"
          placeholder="请选择领用人"
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
      <el-form-item label="使用部门" prop="departmentName" v-if="activeIndex == '1'">
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
      <el-form-item label="派发日期" prop="handoutTime" v-if="activeIndex == '1'">
        <el-date-picker
          v-model="queryParams.handoutTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="退库日期" prop="returnTime" v-if="activeIndex == '2'">
        <el-date-picker
          v-model="queryParams.returnTime"
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
          @click="openForm('create', undefined)"
          v-hasPermi="['bus:property-handout:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:property-handout:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="单据编号" align="center" prop="number">
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
      <el-table-column
        label="领用人"
        align="center"
        prop="receiveUid"
        :formatter="tableColumnEmptyPlaceholder"
        v-if="activeIndex == '1'"
      >
        <template #default="scope">
          <template v-if="scope.row.receiveUid">
            <template v-for="user in userOptions" :key="user.id">
              <span v-if="user.id === scope.row.receiveUid">{{ user.nickname }}</span>
            </template>
          </template>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column
        label="使用部门"
        align="center"
        prop="departmentName"
        :formatter="tableColumnEmptyPlaceholder"
        v-if="activeIndex == '1'"
      />
      <el-table-column
        label="退库后使用部门"
        align="center"
        prop="departmentName"
        :formatter="tableColumnEmptyPlaceholder"
        v-if="activeIndex == '2'"
      />
      <el-table-column
        label="派发时间"
        align="center"
        prop="handoutTime"
        :formatter="dateFormatter"
        width="180px"
        v-if="activeIndex == '1'"
      />
      <el-table-column
        label="退库日期"
        align="center"
        prop="returnTime"
        :formatter="dateFormatter"
        width="180px"
        v-if="activeIndex == '2'"
      />
      <el-table-column
        label="处理人"
        align="center"
        prop="operateUid"
        :formatter="tableColumnEmptyPlaceholder"
        ><template #default="scope">
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
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)"> 审批信息 </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:property-handout:delete']"
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
  <ApprovalInformation ref="approvalRef" :userOptions="userOptions" />
  <!-- 派发 -->
  <CreateHandoutPropertyForm ref="createHandoutPropertyFormRef" @success="getList" />
  <!-- 退库 -->
  <ReturnInventoryForm ref="returnInventoryFormRef" @success="getList" />
</template>

<script setup lang="ts">
import CreateHandoutPropertyForm from './component/createHandoutPropertyForm.vue'
import ReturnInventoryForm from './component/returnInventoryForm.vue'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { defaultProps, handleTree } from '@/utils/tree'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyHandoutApi, PropertyHandoutVO } from '@/api/bus/property/propertyHandout'
import PropertyHandoutForm from './PropertyHandoutForm.vue'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import ApprovalInformation from './component/approvalInformation.vue'

/** 资产派发/退库 列表 */
defineOptions({ name: 'PropertyHandout' })

const deptTree = ref() // 部门树形结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<PropertyHandoutVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  villageId: undefined,
  buildId: undefined,
  roomId: undefined,
  propertyIds: undefined,
  processCode: undefined,
  type: undefined,
  number: undefined,
  status: undefined,
  departmentId: undefined,
  receiveUid: undefined,
  handoutTime: [],
  returnTime: [],
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
    const data = await PropertyHandoutApi.getPropertyHandoutPage(queryParams)
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
  approvalRef.value.open(activeIndex.value == '1' ? 'handout_property' : 'return_property', row)
}

/** 添加/修改操作 */
const formRef = ref()
/** 派发-1 / 退库-2  */
const createHandoutPropertyFormRef = ref()
const returnInventoryFormRef = ref()
const openForm = async (type: string, row) => {
  // formRef.value.open(type, id)
  let list = []
  if (activeIndex.value == '1') {
    createHandoutPropertyFormRef.value.open(type, list, row)
  } else {
    returnInventoryFormRef.value.open(type, list, row)
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyHandoutApi.deletePropertyHandout(id)
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
    const data = await PropertyHandoutApi.exportPropertyHandout(queryParams)
    download.excel(data, '资产派发/退库.xls')
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
  console.log(query)
  if (query.status) {
    queryParams.status = Number(query.status)
  }
  if (query.activeIndex) {
    activeIndex.value = query.activeIndex
  }
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
