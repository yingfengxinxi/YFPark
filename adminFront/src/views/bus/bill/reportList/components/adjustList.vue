<template>
  <ContentWrap>
    <el-row justify="space-around">
      <el-col
        :span="4"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-[var(--el-color-primary)] font-size-24px"
      >
        {{ TopNumCount.adjustContractNum }}
        <div class="c-#00000073 font-size-14px m-b-5px">调整总合同数</div>
      </el-col>
      <el-col
        :span="4"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-[var(--el-color-primary)] font-size-24px"
      >
        {{ TopNumCount?.adjustPriceTotal }}
        <div class="c-#00000073 font-size-14px m-b-5px">调整总金额</div>
      </el-col>
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        {{ TopNumCount?.adjustBillNum }}
        <div class="c-#00000073 font-size-14px m-b-5px">调整总账单数</div>
      </el-col>
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        {{ TopNumCount?.adjustMode2 }}
        <div class="c-#00000073 font-size-14px m-b-5px">比例调整数/占比</div>
      </el-col>
      <el-col :span="4" class="text-center flex-1 c-[var(--el-color-primary)] font-size-24px">
        {{ TopNumCount?.adjustMode1 }}
        <div class="c-#00000073 font-size-14px m-b-5px">金额调整数/占比</div>
      </el-col>
    </el-row>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <div class="flex justify-between flex-items-center mb-20px">
      <el-form ref="queryFormRef" :model="queryParams" label-width="80px" :inline="true">
        <el-form-item label="租客" prop="ownerName">
          <el-input placeholder="请填写租客" v-model="queryParams.ownerName" class="!w-200px"
        /></el-form-item>
        <el-form-item label="项目名称">
          <el-select v-model="queryParams.parkIdList" class="!w-200px" multiple>
            <el-option
              v-for="(item, index) in projectList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="楼宇名称">
          <el-select v-model="queryParams.billidList" class="!w-200px" multiple>
            <el-option
              v-for="(item, index) in buildingList"
              :key="index"
              :label="item.buildName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="合同编号" prop="contractNumber">
          <el-input
            placeholder="请填写合同编号"
            v-model="queryParams.contractNumber"
            class="!w-200px"
        /></el-form-item>
        <el-form-item label="账单编号" prop="orderNumber">
          <el-input placeholder="请填写账单编号" v-model="queryParams.orderNumber" class="!w-200px"
        /></el-form-item>
        <el-form-item label="调整类型">
          <el-select
            v-model="queryParams.adjustType"
            placeholder="请选择调整类型"
            clearable
            class="!w-200px"
          >
            <el-option
              v-for="item in getIntDictOptions(DICT_TYPE.ADJUST_TYPE)"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      row-key="id"
    >
      <el-table-column type="expand" width="50">
        <template #default="scope">
          <el-table
            :data="scope.row.adjustList"
            style="width: 100%"
            class="child-table !w-100%"
            row-key="id"
          >
            <el-table-column prop="" label="" width="50" />
            <el-table-column prop="adjustDate" label="调整日期" />
            <el-table-column prop="adjustType" label="调整类型">
              <template #default="scoped">
                <dict-text :type="DICT_TYPE.ADJUST_TYPE" :value="scoped.row.adjustType" />
              </template>
            </el-table-column>
            <el-table-column prop="adjustMode" label="调整方式">
              <template #default="scoped">
                <dict-text :type="DICT_TYPE.ADJUST_MODE" :value="scoped.row.adjustMode" />
              </template>
            </el-table-column>
            <el-table-column prop="adjustPrice" label="调整金额（元）" />
            <el-table-column prop="remark" label="备注内容" />
            <el-table-column prop="remark" label="调整状态">
              <template #default="scoped">
                <dict-tag :type="DICT_TYPE.ADJUST_STATUS" :value="scoped.row.adjustStatus" />
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default="scoped">
                <el-button
                  v-if="scoped.row.adjustStatus != 3 && scoped.row.adjustStatus != 4"
                  link
                  type="primary"
                  @click="applicationToVoid(scoped.row.id)"
                  v-hasPermi="['bus:orgBillAdjust:applicationToVoid']"
                  >作废</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="租客" prop="ownerName">
        <template #default="scope">
          <template v-if="scope.row.ownerId">
            <el-button
              link
              type="primary"
              @click="OwnerDetail(scope.row.ownerId, scope.row.isPersonal)"
              >{{ scope.row.ownerName }}</el-button
            >
          </template>
          <template v-else> -- </template>
        </template>
      </el-table-column>
      <el-table-column label="楼宇名称" prop="buildName" :formatter="tableColumnEmptyPlaceholder" />
      <el-table-column label="合同编号" prop="contractNumber">
        <template #default="scope">
          <template v-if="!scope.row.contractId"> -- </template>
          <template v-else>
            <el-button link type="primary" @click="handleContractDetail(scope.row.contractId)">{{
              scope.row.contractNumber
            }}</el-button>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="财务编号" prop="orderNumber">
        <template #default="scope">
          <template v-if="!scope.row.orderId"> -- </template>
          <template v-else>
            <el-button link type="primary" @click="OrderDetail(scope.row)">{{
              scope.row.orderNumber
            }}</el-button>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="账单状态" prop="billStatus" width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BILL_STATUS" :value="scope.row.billStatus" />
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
  <TenantDetails ref="TenantDetailsRef" @select-pick="getList" />
  <ContractDetail ref="ContractDetailRef" @select-pick="getList" />
  <CheckOrderDetail ref="CheckOrderDetailRef" />
</template>

<script setup lang="ts">
import { orgBillAdjustApi } from '@/api/bus/orgBillAdjust'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import ContractDetail from '../../../../contract/contractList/contractDetail_drawer.vue'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import CheckOrderDetail from '@/views/bus/owner/component/checkOrderDetail.vue'
/** 账单收据编号规则 列表 */
defineOptions({ name: 'AdjustList' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  ownerName: undefined,
  contractNumber: undefined,
  orderNumber: undefined,
  adjustType: undefined,
  parkIdList: undefined,
  billidList: undefined
})
const queryFormRef = ref() // 搜索的表单
const TopNumCount = ref<any>({})

defineProps({
  projectList: {
    type: Object,
    default: function () {
      return []
    }
  },
  buildingList: {
    type: Object,
    default: function () {
      return []
    }
  }
})

/** 查询顶部数据 */
const getTopNumCount = async () => {
  const data = await orgBillAdjustApi.getAdjustBillTopStatistic()
  TopNumCount.value = data
}

const ContractDetailRef = ref()
const handleContractDetail = async (id: number) => {
  ContractDetailRef.value.open(id)
}

const TenantDetailsRef = ref()
const OwnerDetail = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    let params = JSON.parse(JSON.stringify(queryParams))
    const data = await orgBillAdjustApi.getAdjustBillListPage(params)
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

const CheckOrderDetailRef = ref()
/** 财务账单详情 */
const OrderDetail = (row: any) => {
  CheckOrderDetailRef.value.open(row.billId)
}

/** 账单作废 */
const applicationToVoid = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm('是否确认作废该账单？')
    // 发起删除
    await orgBillAdjustApi.applicationToVoid({ id: id })
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 初始化 **/
onMounted(() => {
  getTopNumCount()
  getList()
})
</script>
