<template>
  <el-row :gutter="0" justify="space-between" align="top">
    <el-col :span="20">
      <el-form ref="form" :model="queryParams" inline>
        <el-form-item>
          <el-input placeholder="请填写租客" v-model="queryParams.ownerName" class="!w-240px" />
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="queryParams.contractStatus"
            placeholder="请选择合同状态"
            clearable
            class="!w-240px"
          >
            <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.CONTRACT_STATUS)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="queryParams.costType"
            placeholder="请选择费用类型"
            clearable
            class="!w-240px"
          >
            <el-option
              v-for="item in costTypeChildrenList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="queryParams.isConfirm"
            placeholder="请选择是否确认"
            clearable
            class="!w-240px"
          >
            <el-option label="待确认" value="1" />
            <el-option label="已确认" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" @click="handleVerify">收支确认配置</el-button>
    </el-col>
  </el-row>
  <ContentWrap>
    <el-row justify="space-around" align="middle">
      <el-col :span="4" class="text-center flex-1 font-size-24px">
        <div class="c-#00000073 font-size-14px m-b-5px">当月应收(含税)</div>
        {{ TopNumCount?.sameMonthReceivableTaxIncludedMoney }}
      </el-col>
      <el-divider direction="vertical" class="!h-40px" />
      <el-col :span="4" class="text-center flex-1 font-size-24px">
        <div class="c-#00000073 font-size-14px m-b-5px">当月应收(不含税)</div>
        {{ TopNumCount?.sameMonthReceivableNoTaxIncludedMoney }}
      </el-col>
      <el-divider direction="vertical" class="!h-40px" />
      <el-col :span="4" class="text-center flex-1 font-size-24px">
        <div class="c-#00000073 font-size-14px m-b-5px">当月实收(含税)</div>
        {{ TopNumCount?.sameMonthNetReceiptsTaxIncludedMoney }}
      </el-col>
      <el-divider direction="vertical" class="!h-40px" />
      <el-col :span="4" class="text-center flex-1 font-size-24px">
        <div class="c-#00000073 font-size-14px m-b-5px">当月已确定收入</div>
        {{ TopNumCount?.sameMonthConfirmedTaxIncludedMoney }}
      </el-col>
      <el-divider direction="vertical" class="!h-40px" />
      <el-col :span="4" class="text-center flex-1 font-size-24px">
        <div class="c-#00000073 font-size-14px m-b-5px">当月待确定收入</div>
        {{ TopNumCount?.sameMonthToBeConfirmedTaxIncludedMoney }}
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <div class="flex justify-between flex-items-center viewHeader">
      <el-menu
        class="flex-1"
        :default-active="activeIndex"
        ellipsis
        mode="horizontal"
        popper-append-to-body
        teleported
        @select="handleSelect"
      >
        <el-menu-item index="0">收入列表</el-menu-item>
        <el-menu-item index="1">收入确认</el-menu-item>
        <el-menu-item index="2">支出确认</el-menu-item>
      </el-menu>
      <div>
        <el-date-picker
          type="monthrange"
          v-model="createTime"
          format="YYYY-MM"
          value-format="YYYY-MM"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          clearable
          @change="daterange"
        />
        <el-button type="primary" @click="handleExport" class="m-l-10px">导出</el-button>
        <el-button type="primary" @click="handleConfirm('')">批量确认</el-button>
      </div>
    </div>
    <el-table
      class="m-t-10px"
      ref="chooseTableRef"
      :data="list"
      cell-class-name="text-12px"
      show-overflow-tooltip
      :header-cell-style="{
        color: '#000000d9',
        fontSize: '14px',
        fontWeight: '500',
        backgroundColor: '#fafafa'
      }"
      @selection-change="handleSelectionChange"
      ><el-table-column type="selection" width="55" fixed />
      <el-table-column label="对方名称" prop="ownerName" width="140px" fixed>
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click.stop="OwnerDetail(scope.row.ownerId, scope.row.isPersonal)"
            >{{ scope.row.ownerName }}</el-button
          >
        </template>
      </el-table-column>
      <el-table-column
        label="费用类型"
        prop="costTypeName"
        width="140px"
        fixed
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="应收金额"
        prop="receivable"
        width="100px"
        fixed
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="匹配金额" prop="matchPrice" width="100px" fixed>
        <template #default="scope">
          {{ scope.row.matchPrice || '0' }}
        </template>
      </el-table-column>
      <el-table-column label="楼宇名称" prop="buildName" width="100px" fixed />
      <el-table-column label="房号" prop="roomNumber" width="100px" fixed />
      <el-table-column label="账单编号" prop="streamNumber" width="280px">
        <template #default="scope">
          <template v-if="scope.row.streamNumber">
            <el-button link type="primary" @click.stop="OrderDetail(scope.row)">{{
              scope.row.streamNumber
            }}</el-button>
          </template>
          <template v-else> -- </template>
        </template>
      </el-table-column>
      <el-table-column label="确认状态" prop="isConfirm" width="140px" />
      <el-table-column label="账单状态" prop="billStatus" width="140px" />
      <el-table-column label="流水状态" prop="matchStatus" width="140px">
        <template #default="scope">
          {{ scope.row.matchStatus || '未匹配' }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="费用周期" prop="payStartDate" width="140px">
        <template #default="scope">
          {{ scope.row.payStartDate }} - {{ scope.row.payEndDate }}
        </template>
      </el-table-column> -->
      <el-table-column
        label="费用周期"
        prop="costCycle"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="匹配日期"
        prop="matchDate"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="确认时间"
        prop="confirmTime"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="确认人员"
        prop="confirmUid"
        width="140px"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="操作" fixed="right" width="140px">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)"> 详情 </el-button>
          <el-button link type="primary" @click="handleConfirm(scope.row)"
            >{{ scope.row.isConfirm == '待确认' ? '确认' : '取消确认' }}
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
  <Form ref="formRef" @success="getList" />
  <TenantDetails ref="TenantDetailsRef" @select-pick="getList" />
  <CheckOrderDetail ref="CheckOrderDetailRef" />
  <Detail ref="DetailRef" @success="getList" />
</template>

<script setup lang="ts">
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { dateFormatter } from '@/utils/formatTime'
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import CheckOrderDetail from '@/views/bus/owner/component/checkOrderDetail.vue'
import download from '@/utils/download'
import { orgIncomeApi, orgIncomeVO } from '@/api/bus/orgIncome/index'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import Form from './Form.vue'
import Detail from './detail.vue'

/** 机构收入 列表 */
defineOptions({ name: 'OrgIncome' })

const activeIndex = ref('1')
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<orgIncomeVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  billType: 1,
  ownerName: undefined,
  costType: undefined,
  startMonth: undefined,
  endMonth: undefined,
  isConfirm: undefined,
  contractStatus: undefined
})

const createTime = ref()
const handleSelect = (key: string, keyPath: string[]) => {
  activeIndex.value = key
  getList()
}
const daterange = (val: any) => {
  if (val.length == 0) {
    queryParams.startMonth = undefined
    queryParams.endMonth = undefined
    return
  }
  queryParams.startMonth = val[0]
  queryParams.endMonth = val[1]
  getList()
}
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const TopNumCount = ref<any>({})
const selectionList = ref([])
/** 多选 */
const handleSelectionChange = (val: any) => {
  console.log(val)
  selectionList.value = val
}
/** 确认流水、取消确认 */
const handleConfirm = async (row) => {
  try {
    let isConfirm = ''
    console.log(row, selectionList.value)
    if (!row && !selectionList.value.length) {
      message.warning('请选择要操作的数据')
      return
    }
    if (!row && selectionList.value.length) {
      const list = selectionList.value.map((item) => item.isConfirm)
      const listItemArray = [list[0]]
      isConfirm = list[0]
      let differentArr = list.filter((item) => listItemArray.indexOf(item) != -1)
      if (differentArr.length != selectionList.value.length) {
        message.warning('请选择相同状态的流水')
        return
      }
    }
    let ids = ''
    // 发起删除
    if (row) {
      ids = String(row.id)
      isConfirm = row.isConfirm
    } else {
      console.log(selectionList.value, 'selectionList')
      ids = selectionList.value.map((item) => item.id).join(',')
    }
    // 删除的二次确认
    await message.confirm(
      activeIndex.value == '1'
        ? '该匹配的流水确认后，流水已匹配的部分将不能重新匹配账单、不能关闭流水。对应被匹配的账单也不能关闭、调整、解除匹配该收支流水'
        : '取消后流水可以重新匹配账单，以及关闭。对应被匹配的账单也可以关闭、调整、解除匹配该流水'
    )

    await orgIncomeApi.confirmIncome({
      ids: ids,
      isConfirm: isConfirm == '待确认' ? '2' : '1'
    })
    message.success(t('common.success'))
    // 刷新列表
    await getList()
  } catch {}
}

const CheckOrderDetailRef = ref()
/** 财务账单详情 */
const OrderDetail = (row: any) => {
  if (!row.billId) {
    message.warning('暂无账单信息,请先匹配流水信息')
    return
  }
  CheckOrderDetailRef.value.open(row.billId)
}

/** 租客详情 */
const TenantDetailsRef = ref()
const OwnerDetail = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}
/** 查询顶部数据 */
const getTopNumCount = async () => {
  const data = await orgIncomeApi.getAmountStatistics()
  TopNumCount.value = data
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    if (activeIndex.value == '0') {
      return
    } else {
      queryParams.billType = Number(activeIndex.value)
    }
    const data = await orgIncomeApi.getPage(queryParams)
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

const handleVerify = async (row: any) => {
  formRef.value.open()
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
    await orgIncomeApi.delete(id)
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
    const data = await orgIncomeApi.export(queryParams)
    download.excel(data, activeIndex.value == '1' ? '收入确认.xlsx' : '支出确认.xlsx')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 费用类型 */
const costTypeChildrenList = ref<any[]>([])
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}

/** 详情 */
const DetailRef = ref()
const handleDetail = async (row: any) => {
  DetailRef.value.open(row.id, row.isConfirm)
}

/** 初始化 **/
onMounted(() => {
  getCostTypeChildrenList()
  getTopNumCount()
  getList()
})
</script>
<style scoped lang="scss">
::v-deep .el-menu--horizontal.el-menu {
  border: none;
}

.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  background: none !important;
}

::v-deep .viewHeader .el-card__body {
  padding-bottom: 0px !important;
}
</style>
