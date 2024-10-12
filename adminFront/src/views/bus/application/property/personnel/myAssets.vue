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
      <el-form-item label="资产编码" prop="propertyNumber">
        <el-input
          v-model="queryParams.propertyNumber"
          placeholder="请输入资产编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产分类" prop="type">
        <el-tree-select
          v-model="queryParams.type"
          :data="locationList"
          :render-after-expand="false"
          :props="{ label: 'name', value: 'id', children: 'children' }"
          placeholder="请选择资产分类"
          clearable
          :check-strictly="true"
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
      <el-form-item label="资产状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择资产状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.PROPERTYSTATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="管理员" prop="adminId">
        <el-select
          v-model="queryParams.adminId"
          placeholder="请选择管理员"
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
      <el-form-item label="所在位置" prop="positionId">
        <el-tree-select
          v-model="queryParams.positionId"
          :data="positionList"
          :render-after-expand="false"
          :props="{ label: 'name', value: 'id', children: 'children' }"
          placeholder="请选择所在位置"
          clearable
          :check-strictly="true"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column
        label="资产编码"
        align="center"
        prop="propertyNumber"
        :formatter="tableColumnEmptyPlaceholder"
        width="240"
      >
        <template #default="scope">
          <span
            :class="scope.row.propertyNumber ? `c-[var(--el-color-primary)] cursor-pointer` : ''"
            @click="handlePropertyDetail(scope.row.id)"
            >{{ scope.row.propertyNumber || '--' }}</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="资产名称"
        align="center"
        prop="name"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="资产分类"
        align="center"
        prop="categoryName"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="资产状态" align="center" prop="status" width="160">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PROPERTYSTATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="品牌"
        align="center"
        prop="brand"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="所在位置"
        align="center"
        prop="positionName"
        :formatter="tableColumnEmptyPlaceholder"
      />
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
  <PropertyForm
    ref="formRef"
    :locationList="locationList"
    :positionList="positionList"
    @success="getList"
  />
</template>

<script setup lang="ts">
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import PropertyForm from '../assets/PropertyForm.vue'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import download from '@/utils/download'
import printJS from 'print-js'
import { PropertyTagApi } from '@/api/bus/property/setting/propertyTag'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { PropertyLocationApi } from '@/api/bus/property/setting/propertyLocation'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
/** 资产管理 列表 */
defineOptions({ name: 'MyAssets' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const deptTree = ref() // 部门树形结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const loading = ref(true) // 列表的加载中
const list = ref<PropertyVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  propertyNumber: undefined,
  labelLink: undefined,
  type: undefined,
  name: undefined,
  status: undefined,
  brand: undefined,
  modelName: undefined,
  deviceCode: undefined,
  processCode: undefined,
  adminId: undefined,
  adminUid: undefined,
  villageId: undefined,
  buildId: undefined,
  roomId: undefined,
  buildBind: undefined,
  positionId: undefined,
  positionName: undefined,
  purchaseTime: [],
  purchaseType: undefined,
  purchaseAmount: undefined,
  stockTime: [],
  expectMonths: undefined,
  remark: undefined,
  imageHash: undefined,
  imageUrl: undefined,
  userId: undefined,
  departmentId: undefined,
  receiveTime: [],
  maintainTime: [],
  maintainInfo: undefined,
  depreciationMonths: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  knowledgeBase: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PropertyApi.getPropertyPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 资产详情 */
const handlePropertyDetail = async (id: number) => {
  openForm('detail', id)
}

const selectedRow = ref([] as PropertyVO[])
const handleSelectionChange = (val: PropertyVO[]) => {
  console.log(val)
  selectedRow.value = val
}

/**获取分类列表 */
const locationList = ref([])
const getLocationList = async () => {
  const data = await PropertyApi.getPropertyTypeTree({})
  locationList.value = data
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

/** 获得位置列表 */
const positionList = ref([])
const getPositionList = async () => {
  const data = await PropertyLocationApi.getPropertyPositionTree({})
  positionList.value = data
}
/** 初始化 **/
onMounted(async () => {
  queryParams.userId = userStore.getUser.id

  // 获得部门树
  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())
  userOptions.value = await UserApi.getSimpleUserList()
  getPositionList()
  getLocationList()
  getList()
})
</script>
