<template>
  <ContentWrap class="viewHeader">
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
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

      <el-table-column
        label="物料编码"
        align="center"
        prop="number"
        :formatter="tableColumnEmptyPlaceholder"
      />

      <el-table-column
        label="物料名称"
        align="center"
        prop="name"
        :formatter="tableColumnEmptyPlaceholder"
      />

      <el-table-column
        label="物料分类"
        align="center"
        prop="categoryId"
        :formatter="tableColumnEmptyPlaceholder"
      >
        <template #default="scope">
          <template v-if="scope.row.stuff.categoryId">
            <template v-for="item in catrgoryList" :key="item.id">
              <span v-if="item.id === scope.row.stuff.categoryId">{{ item.name }}</span>
            </template>
          </template>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column
        label="出库仓库"
        align="center"
        prop="depositoryName"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="领用时间"
        align="center"
        prop="applyTime"
        :formatter="dateFormatter"
        width="180px"
      />

      <el-table-column
        label="领用数量"
        align="center"
        prop="handoutNum"
        :formatter="tableColumnEmptyPlaceholder"
      />

      <el-table-column
        label="合计金额"
        align="center"
        prop="depositoryName"
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
  <Form ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { dateFormatter } from '@/utils/formatTime'
import { defaultProps, handleTree } from '@/utils/tree'
import { PropertyStuffDepositoryApi, PropertyStuffDepositoryVO } from '@/api/bus/stuff/depository'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import download from '@/utils/download'
import { PropertyStuffHandoutApi, PropertyStuffHandoutVO } from '@/api/bus/stuff/handOut'
import { PropertyStuffRetreatApi, PropertyStuffRetreatVO } from '@/api/bus/stuff/retreat'
import Form from '../stuff/component/Form.vue'
import { PropertyStuffCategoryApi, PropertyStuffCategoryVO } from '@/api/bus/stuff/category'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()

/** 耗材业务派发 列表 */
defineOptions({ name: 'MyStuff' })

const catrgoryList = ref<PropertyStuffCategoryVO[]>([])
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

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PropertyStuffHandoutApi.getPropertyStuffHandoutPage(queryParams)

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
    [],
    row
  )
}
/** 初始化 **/
onMounted(async () => {
  queryParams.receiveUid = userStore.getUser.id
  // 获取物料分类
  catrgoryList.value = await PropertyStuffCategoryApi.getPropertyStuffCategoryTree()
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
