<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
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
    <el-form-item label="借用人" prop="lendUid" v-if="activeIndex == '3'">
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
    <el-form-item label="借用部门" prop="departmentId" v-if="activeIndex == '3'">
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

    <el-form-item>
      <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
      <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      <el-button type="primary" plain @click="openForm('create', undefined)">
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
    <el-table-column label="借用人" align="center" prop="lendUid" v-if="activeIndex == '3'"
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
      :label="activeIndex == '3' ? '借用部门' : '归还后使用部门'"
      align="center"
      prop="departmentName"
      :formatter="tableColumnEmptyPlaceholder"
    />
    <el-table-column label="操作" align="center" fixed="right">
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
  <!-- 借用/归还 -->
  <LendReturn ref="LendReturnFormRef" @success="getList" />
  <ApprovalInformation ref="approvalRef" :userOptions="userOptions" />
</template>

<script setup lang="ts">
import LendReturn from '../../../../assets/component/lendReturn.vue'
import ApprovalInformation from '../../../../assets/component/approvalInformation.vue'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { defaultProps, handleTree } from '@/utils/tree'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyLendoutApi, PropertyLendoutVO } from '@/api/bus/property/propertyLendout'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
/** 资产借出 列表 */
defineOptions({ name: 'PropertyLendout' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

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
const activeIndex = ref(prop.activeIndex)
/** 查询列表 */
const getList = async () => {
  loading.value = true

  queryParams.lendUid = userStore.getUser.id
  try {
    queryParams.type = activeIndex.value == '3' ? 1 : 2
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
  if (activeIndex.value == '3') {
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

watch(
  () => prop.activeIndex,
  (val) => {
    activeIndex.value = val
    getList()
  },
  { immediate: true, deep: true }
)
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
</script>
