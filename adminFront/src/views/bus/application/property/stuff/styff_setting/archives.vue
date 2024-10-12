<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="物料编码" prop="number">
        <el-input
          v-model="queryParams.number"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="物料分类" prop="categoryId">
        <el-select
          v-model="queryParams.categoryId"
          placeholder="请选择物料分类"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        >
          <el-option
            v-for="item in catrgoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')">
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:property-stuff:export']"
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
        label="物料编码"
        align="center"
        prop="number"
        :formatter="tableColumnEmptyPlaceholder"
        width="200"
      />
      <el-table-column
        label="物料名称"
        align="center"
        prop="name"
        :formatter="tableColumnEmptyPlaceholder"
        width="200"
      />
      <el-table-column label="物料分类" align="center" prop="categoryId" width="200">
        <template #default="scope">
          <template v-if="scope.row.categoryId">
            <template v-for="item in catrgoryList" :key="item.id">
              <span v-if="item.id === scope.row.categoryId">{{ item.name }}</span>
            </template>
          </template>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column
        label="商品条码"
        align="center"
        prop="barCode"
        :formatter="tableColumnEmptyPlaceholder"
        width="200"
      />
      <el-table-column
        label="品牌"
        align="center"
        prop="brand"
        :formatter="tableColumnEmptyPlaceholder"
        width="200"
      />
      <el-table-column
        label="规格型号"
        align="center"
        prop="modelName"
        :formatter="tableColumnEmptyPlaceholder"
        width="200"
      />
      <el-table-column
        label="计量单位"
        align="center"
        prop="meterUnit"
        :formatter="tableColumnEmptyPlaceholder"
        width="200"
      />
      <el-table-column
        label="小数位数(数量)"
        align="center"
        prop="quantityDigit"
        :formatter="tableColumnEmptyPlaceholder"
        width="160"
      />
      <el-table-column
        label="小数位数(单价)"
        align="center"
        prop="priceDigit"
        :formatter="tableColumnEmptyPlaceholder"
        width="160"
      />
      <el-table-column label="成本计算方法" align="center" prop="computeMethod" width="140">
        <template #default="scope">
          <template v-if="scope.row.computeMethod">
            <dict-text :type="DICT_TYPE.STUFF_COST" :value="scope.row.computeMethod" />
          </template>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column
        label="入库单价"
        align="center"
        prop="price"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="安全库存上限数量"
        align="center"
        prop="safeStockUp"
        :formatter="tableColumnEmptyPlaceholder"
        width="160"
      />
      <el-table-column
        label="安全库存下限数量"
        align="center"
        prop="safeStockLower"
        :formatter="tableColumnEmptyPlaceholder"
        width="160"
      />
      <el-table-column
        label="限领数量(人/月)"
        align="center"
        prop="receiveLimit"
        :formatter="tableColumnEmptyPlaceholder"
        width="160"
      />

      <el-table-column label="是否固定入库单价" align="center" prop="lockPrice" width="160">
        <template #default="scope">
          {{ scope.row.lockPrice == 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column label="是否允许退库" align="center" prop="hasReturn" width="160">
        <template #default="scope">
          <template v-if="String(scope.row.hasReturn)">
            <dict-text :type="DICT_TYPE.STUFF_HAS_RETURN" :value="String(scope.row.hasReturn)" />
          </template>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column label="耗材状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PROPERTY_SHOW_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" width="200" />
      <el-table-column label="操作" align="center" fixed="right" width="160">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('update', scope.row.id)">
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            @click="forbidden(scope.row)"
            v-hasPermi="['bus:property-category:update']"
          >
            {{ scope.row.status == 1 ? '禁用' : '启用' }}
          </el-button>
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
  <ArchivesForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { PropertyStuffApi, PropertyStuffVO } from '@/api/bus/stuff/index'
import ArchivesForm from './archivesForm.vue'
import { PropertyStuffCategoryApi, PropertyStuffCategoryVO } from '@/api/bus/stuff/category'

/** 耗材档案信息 列表 */
defineOptions({ name: 'PropertyStuff' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<PropertyStuffVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  number: undefined,
  name: undefined,
  categoryId: undefined,
  barCode: undefined,
  brand: undefined,
  modelName: undefined,
  meterUnit: undefined,
  quantityDigit: undefined,
  priceDigit: undefined,
  computeMethod: undefined,
  lockPrice: undefined,
  price: undefined,
  safeStockUp: undefined,
  safeStockLower: undefined,
  receiveLimit: undefined,
  hasReturn: undefined,
  imageUrl: undefined,
  status: undefined,
  remark: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const catrgoryList = ref<PropertyStuffCategoryVO[]>([])

/** 禁用 */
const forbidden = async (row: PropertyStuffCategoryVO) => {
  try {
    // 禁用的二次确认
    await message.delConfirm(
      `您确定要${row.status == 1 ? '禁用' : '启用'}档案【${row.name}】及全部库存吗？`
    )
    // 发起禁用
    await PropertyStuffApi.updatePropertyStuff({
      ...row,
      status: row.status === 1 ? 0 : 1
    })
    message.success('保存成功')
    // 刷新列表
    await getList()
  } catch {}
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PropertyStuffApi.getPropertyStuffPage(queryParams)
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
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyStuffApi.deletePropertyStuff(id)
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
    const data = await PropertyStuffApi.exportPropertyStuff(queryParams)
    download.excel(data, '耗材档案信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(async () => {
  catrgoryList.value = await PropertyStuffCategoryApi.getPropertyStuffCategoryTree()
  getList()
})
</script>
