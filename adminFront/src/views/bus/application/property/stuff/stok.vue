<template>
  <!-- 耗材即时库存 -->
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="140px"
    >
      <el-form-item label="耗材仓库" prop="depositoryId">
        <el-tree-select
          v-model="queryParams.depositoryId"
          :data="Treedata"
          :render-after-expand="false"
          :props="{ label: 'name', value: 'id', children: 'children' }"
          placeholder="请选择耗材仓库"
          clearable
          :check-strictly="true"
          class="!w-270px"
        />
      </el-form-item>

      <el-form-item label="可用库存" prop="usableNum">
        <el-input v-model="queryParams.usableNumStart" placeholder="" clearable class="!w-120px" />
        <span class="m-10px m-t-0 m-b-0">至</span>
        <el-input v-model="queryParams.usableNumEnd" placeholder="" clearable class="!w-120px" />
      </el-form-item>

      <el-form-item label="总库存" prop="totalNum">
        <el-input v-model="queryParams.totalNumStart" placeholder="" clearable class="!w-120px" />
        <span class="m-10px m-t-0 m-b-0">至</span>
        <el-input
          v-model="queryParams.totalNumEnd"
          placeholder="请输入总库存"
          clearable
          class="!w-120px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>

        <el-button type="success" plain @click="handleExport" :loading="exportLoading">
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button type="primary" @click="openForm('stuff_stock_enter', 'create')">
          耗材入库
        </el-button>
        <el-button type="primary" @click="openForm('stuff_hand_out', 'create')">
          耗材派发
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="物料编码" align="center" prop="stuff.number" width="190px" />
      <el-table-column
        label="物料名称"
        align="center"
        prop="stuff.name"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="物料分类" align="center" prop="categoryName" width="190px" />
      <el-table-column
        label="商品条码"
        align="center"
        prop="stuff.barCode"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="品牌"
        align="center"
        prop="stuff.brand"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="规格型号"
        align="center"
        prop="stuff.modelName"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="所在仓库"
        align="center"
        prop="depository.remark"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="可用库存"
        align="center"
        prop="usableNum"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="冻结库存"
        align="center"
        prop="frozenNum"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="总库存"
        align="center"
        prop="totalNum"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="总金额"
        align="center"
        prop="totalPrice"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="单价"
        align="center"
        prop="stuff.price"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="计量单位"
        align="center"
        prop="stuff.meterUnit"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="小数点位数(数量)"
        align="center"
        prop="stuff.quantityDigit"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="小数点位数(单价)"
        align="center"
        prop="stuff.priceDigit"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="成本计算方法"
        align="center"
        prop="extra"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      >
        <template #default="scope">
          <template v-if="scope.row.stuff.computeMethod">
            <dict-text :type="DICT_TYPE.STUFF_COST" :value="scope.row.stuff.computeMethod" />
          </template>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column
        label="安全库存上限数量"
        align="center"
        prop="stuff.safeStockUp"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="安全库存下线数量"
        align="center"
        prop="stuff.safeStockLower"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="限领数量(人/月)"
        align="center"
        prop="stuff.receiveLimit"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="是否固定入库单价"
        align="center"
        prop="muserUid"
        width="190px"
        :formatter="tableColumnEmptyPlaceholder"
      >
        <template #default="scope">
          {{ scope.row.lockPrice == 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column
        label="是否允许退库"
        align="center"
        prop="createTime"
        :formatter="tableColumnEmptyPlaceholder"
        width="180px"
      >
        <template #default="scope">
          <template v-if="String(scope.row.stuff.hasReturn)">
            <dict-text
              :type="DICT_TYPE.STUFF_HAS_RETURN"
              :value="String(scope.row.stuff.hasReturn)"
            />
          </template>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        :formatter="tableColumnEmptyPlaceholder"
        width="180px"
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
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyStuffDepositoryApi, PropertyStuffDepositoryVO } from '@/api/bus/stuff/depository'
import { PropertyStuffStockApi, PropertyStuffStockVO } from '@/api/bus/stuff/stock'
import { PropertyStuffCategoryApi, PropertyStuffCategoryVO } from '@/api/bus/stuff/category'
import Form from './component/Form.vue'
// import PropertyStuffStockForm   from './PropertyStuffStockForm.vue'

/** 耗材即时库存 列表 */
defineOptions({ name: 'PropertyStuffStock' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<PropertyStuffStockVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  totalNumEnd: undefined,
  totalNumStart: undefined,
  usableNumStart: undefined,
  usableNumEnd: undefined,
  orgId: undefined,
  stuffId: undefined,
  depositoryId: undefined,
  processCode: undefined,
  usableNum: undefined,
  frozenNum: undefined,
  totalNum: undefined,
  totalPrice: undefined,
  chargePrice: undefined,
  remark: undefined,
  extra: undefined,
  isStockUp: undefined,
  isStockLower: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const Treedata = ref([])

const catrgoryList = ref<PropertyStuffCategoryVO[]>([])
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PropertyStuffStockApi.getPropertyStuffStockPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

//获得树状数据
const GetTreeData = async () => {
  const data = await PropertyStuffDepositoryApi.getPropertyStuffDepositoryTree()
  Treedata.value = data
}
/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  queryParams.usableNumStart = undefined
  queryParams.usableNumEnd = undefined
  queryParams.totalNumStart = undefined
  queryParams.totalNumEnd = undefined
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (title: string, type: string, id?: number) => {
  formRef.value.open(title, type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyStuffStockApi.deletePropertyStuffStock(id)
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
    const data = await PropertyStuffStockApi.exportPropertyStuffStock(queryParams)
    download.excel(data, '耗材即时库存.xls')
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
  GetTreeData()
  catrgoryList.value = await PropertyStuffCategoryApi.getPropertyStuffCategoryTree()
  getList()
})
</script>
