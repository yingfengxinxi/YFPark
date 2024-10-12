<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <!-- <el-button type="primary" plain @click="openForm('create', undefined)">
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button> -->
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
      <el-table-column label="调出管理员" align="center" prop="outAdminUid">
        <template #default="scope">
          <template v-if="scope.row.outAdminUid">
            <template v-for="user in userOptions" :key="user.id">
              <span v-if="user.id === scope.row.outAdminUid">{{ user.nickname }}</span>
            </template>
          </template>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column label="调入管理员" align="center" prop="inAdminUid">
        <template #default="scope">
          <template v-if="scope.row.inAdminUid">
            <template v-for="user in userOptions" :key="user.id">
              <span v-if="user.id === scope.row.inAdminUid">{{ user.nickname }}</span>
            </template>
          </template>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column
        label="调入位置"
        align="center"
        prop="inLocationName"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="调拨备注"
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
            v-hasPermi="['bus:property-transfer:delete']"
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
  <AllotForm ref="allotFormRef" @success="getList" />
  <ApprovalInformation ref="approvalRef" :userOptions="userOptions" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyTransferApi, PropertyTransferVO } from '@/api/bus/property/propertyTransfer'
import AllotForm from '../../../../assets/component/allotForm.vue'
import ApprovalInformation from '../../../../assets/component/approvalInformation.vue'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { defaultProps, handleTree } from '@/utils/tree'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'

import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
/** 资产调拨 列表 */
defineOptions({ name: 'PropertyTransfer' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<PropertyTransferVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  propertyIds: undefined,
  number: undefined,
  processCode: undefined,
  status: undefined,
  outAdminUid: undefined,
  inAdminUid: undefined,
  inLocationId: undefined,
  operateUid: undefined,
  operateTime: [],
  remark: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const prop = defineProps({
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
    const data = await PropertyTransferApi.getPropertyTransferPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 审批信息 */
const approvalRef = ref()
const handleDetail = async (row: any) => {
  approvalRef.value.open('transfer_property', row)
}
watch(
  () => prop.activeIndex,
  (val) => {
    getList()
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
const allotFormRef = ref()
const openForm = async (type: string, row) => {
  let list = []
  allotFormRef.value.open(type, list, row)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyTransferApi.deletePropertyTransfer(id)
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
    const data = await PropertyTransferApi.exportPropertyTransfer(queryParams)
    download.excel(data, '资产调拨.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
</script>
