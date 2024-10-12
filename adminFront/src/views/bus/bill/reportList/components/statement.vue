<template>
  <!-- 列表 -->
  <ContentWrap>
    <div class="flex justify-between flex-items-center">
      <el-form ref="queryFormRef" :model="queryParams" label-width="80px" :inline="true">
        <el-form-item label="统计方式" prop="method">
          <el-select
            v-model="queryParams.method"
            placeholder="请选择统计方式"
            clearable
            class="!w-200px"
          >
            <el-option label="按有效期开始时间" value="start_date" />
            <el-option
              :label="activeIndex == '1' ? '按应支时间统计' : '按应收时间统计'"
              value="receivable_payment_date"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="统计月份" prop="payDate">
          <el-date-picker
            type="month"
            v-model="queryParams.payDate"
            format="YYYY-MM"
            value-format="YYYY-MM"
            clearable
            class="datepicker"
            icon-position="right"
          />
        </el-form-item>
        <template v-if="viewType != '2'">
          <el-form-item label="租客" prop="ownerName">
            <el-input placeholder="请输入租客" v-model="queryParams.ownerName" class="!w-200px"
          /></el-form-item>
          <el-form-item label="联系电话" prop="phone">
            <el-input placeholder="请输入联系电话" v-model="queryParams.phone" />
          </el-form-item>
          <el-form-item label="费用类型" prop="costType">
            <el-select
              v-model="queryParams.costType"
              placeholder="请选择费用类型"
              clearable
              class="!w-200px"
            >
              <el-option
                v-for="dict in feeTypeList"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
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
          <el-form-item label="房号" prop="roomNumberName">
            <el-input
              placeholder="请填写房号"
              v-model="queryParams.roomNumberName"
              class="!w-200px"
          /></el-form-item>
          <el-form-item label="收款方式" prop="remitType">
            <el-select
              v-model="queryParams.remitType"
              placeholder="请选择收款方式"
              clearable
              class="!w-200px"
            >
              <el-option
                v-for="item in getIntDictOptions(DICT_TYPE.REMIT_TYPE)"
                :label="item.label"
                :value="item.value"
                :key="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="结清状态" prop="remitType">
            <el-select
              v-model="queryParams.billStatus"
              placeholder="请选择收款方式"
              clearable
              class="!w-200px"
            >
              <el-option
                v-for="item in getIntDictOptions(DICT_TYPE.BILL_STATUS)"
                :label="item.label"
                :value="item.value"
                :key="item.value"
              />
            </el-select>
          </el-form-item>
        </template>
        <el-form-item>
          <el-button @click="handleQuery" type="primary"
            ><Icon icon="ep:search" class="mr-5px" /> 查询</el-button
          >
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
          <template v-if="viewType != '2'">
            <el-button @click="handleExport">导出报表</el-button>
          </template>
        </el-form-item>
      </el-form>
    </div>
    <template v-if="viewType != '2'">
      <el-table
        v-loading="loading"
        :data="list"
        :stripe="true"
        :show-overflow-tooltip="true"
        row-key="id"
        class="mt-20px"
      >
        <el-table-column type="selection" prop="" label="" width="50" fixed />
        <el-table-column label="租客" prop="ownerName" fixed width="200px">
          <template #default="scope">
            <template v-if="scope.row.ownerId">
              <el-button
                link
                type="primary"
                @click.stop="OwnerDetail(scope.row.ownerId, scope.row.isPersonal)"
                >{{ scope.row.ownerName }}</el-button
              >
            </template>
            <template v-else> -- </template>
          </template>
        </el-table-column>
        <el-table-column
          prop="ownerContactName"
          label="默认联系人"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          prop="phone"
          label="联系电话"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          prop="buildName"
          label="楼宇名称"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column label="房号" width="200" align="center" prop="roomNumber">
          <template #default="scope">
            <template v-if="!scope.row.roomNumber"> -- </template>
            <template v-else>
              <span v-for="(item, index) in JSON.parse(scope.row.roomNumber)" :key="index">
                <el-button type="text" @click.stop="room_openForm(item.roomId)">
                  {{ item.roomName }}
                  <span v-if="index + 1 < JSON.parse(scope.row.roomNumber).length">,</span>
                </el-button>
              </span>
            </template>
          </template>
        </el-table-column>
        <el-table-column prop="adjustPrice" label="有效期" width="200px">
          <template #default="scope">
            <template v-if="!scope.row.payStartDate"> -- </template>
            <template v-else> {{ scope.row.payStartDate }} - {{ scope.row.payEndDate }} </template>
          </template>
        </el-table-column>
        <el-table-column
          prop="costTypeName"
          label="费用类型"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          prop="receivable"
          label="应收金额"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          prop="payDate"
          label="应收时间"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          prop="lateFee"
          label="滞纳金"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          prop="receivablePayment"
          label="实收款"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column
          prop="remitType"
          label="收款方式"
          width="200px"
          :formatter="tableColumnEmptyPlaceholder"
        />
        <el-table-column label="缴费通知单" width="200">
          <template #default="scoped">
            <el-button type="primary" @click="handleCollectionForm(scoped.row)"
              >生成缴费通知单</el-button
            >
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
    </template>
  </ContentWrap>
  <template v-if="viewType == '2'">
    <ContentWrap title="应支汇总" v-if="activeIndex == '1'">
      <el-row justify="space-between" align="middle">
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount.receivable }}
          <div class="c-#00000073 font-size-18px m-t-5px">总应支出</div>
        </el-col>
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount?.wyReceivable }}
          <div class="c-#00000073 font-size-18px m-t-5px">物业费支出</div>
        </el-col>
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount?.zlReceivable }}
          <div class="c-#00000073 font-size-18px m-t-5px">其他费支出</div>
        </el-col>
        <el-divider direction="vertical" class="!h-50px" />
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount?.wxReceivablePayment }}
          <div class="c-#00000073 font-size-18px m-t-5px">未知金额</div>
        </el-col>
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount?.receivablePayment }}
          <div class="c-#00000073 font-size-18px m-t-5px">已支金额</div>
        </el-col>
      </el-row>
    </ContentWrap>
    <ContentWrap title="应收汇总" v-else>
      <el-row justify="space-between" align="middle">
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount.receivable }}
          <div class="c-#00000073 font-size-18px m-t-5px">总应收入</div>
        </el-col>
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount?.wyReceivable }}
          <div class="c-#00000073 font-size-18px m-t-5px">物业费收入</div>
        </el-col>
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount?.zlReceivable }}
          <div class="c-#00000073 font-size-18px m-t-5px">其他费收入</div>
        </el-col>
        <el-divider direction="vertical" class="!h-50px" />
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount?.wxReceivablePayment }}
          <div class="c-#00000073 font-size-18px m-t-5px">未收金额</div>
        </el-col>
        <el-col :span="4" class="text-center flex-1 font-size-24px">
          {{ TopNumCount?.receivablePayment }}
          <div class="c-#00000073 font-size-18px m-t-5px">已收金额</div>
        </el-col>
      </el-row>
    </ContentWrap>
    <el-row :gutter="20">
      <el-col :span="8">
        <ContentWrap title="已收金额线下/线上占比">
          <div>
            <Echart :height="300" :options="AccountsReceivableAmountMapOption" />
            <el-row
              :gutter="20"
              justify="space-between"
              class="text-center"
              v-if="AccountsReceivableAmountMapOption.series[0].data"
            >
              <el-col :span="11">
                <div class="flex-1">
                  <div class="font-size-20px m-b-10px">{{
                    AccountsReceivableAmountMapOption.series[0].data[1]?.value
                  }}</div>
                  <div class="flex justify-center items-center">
                    <div class="bg-#3aa1ff w-12px h-12px b-rd-12px m-r-5px"></div>
                    {{ AccountsReceivableAmountMapOption.series[0].data[1]?.name }}
                  </div>
                </div>
              </el-col>
              <el-divider direction="vertical" class="!h-50px" />
              <el-col :span="11">
                <div class="flex-1">
                  <div class="font-size-20px m-b-10px">{{
                    AccountsReceivableAmountMapOption.series[0].data[1]?.value
                  }}</div>
                  <div class="flex justify-center items-center">
                    <div class="bg-#79e0b8 w-12px h-12px b-rd-12px m-r-5px"></div>
                    {{ AccountsReceivableAmountMapOption.series[0].data[1]?.name }}
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </ContentWrap>
      </el-col>
      <el-col :span="16">
        <ContentWrap title="费用类型趋势">
          <template #header>
            <div class="flex-1 flex justify-end"> 单位: 元 </div>
          </template>
          <Echart :height="350" :options="AccountsReceivableTrendCostTypeMapOption" />
        </ContentWrap>
      </el-col>
    </el-row>
  </template>

  <TenantDetails ref="TenantDetailsRef" @select-pick="getList" />
  <CheckOrderDetail ref="CheckOrderDetailRef" />
  <RoomStatusDetail ref="RoomStatusDetailRef" @success="getList" />
  <collectionForm ref="collectionFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { EChartsOption } from 'echarts'
import { orgBillAdjustApi } from '@/api/bus/orgBillAdjust'
import { formatDate } from '@/utils/formatTime'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import CheckOrderDetail from '@/views/bus/owner/component/checkOrderDetail.vue'
import RoomStatusDetail from '@/views/village/managementCenter/RoomStatusDetail.vue'
import collectionForm from '@/views/bus/owner/component/collectionForm.vue'
import { contractOrderApi } from '@/api/bus/contractOrderBill'
import download from '@/utils/download'
import { values } from 'lodash-es'
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
  method: 'start_date',
  payDate: formatDate(new Date(), 'YYYY-MM'),
  ownerName: undefined,
  phone: undefined,
  roomNumberName: undefined,
  billStatus: undefined,
  remitType: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const TopNumCount = ref<any>({})
const props = defineProps({
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
  },
  feeTypeList: {
    type: Object,
    default: function () {
      return []
    }
  },
  activeIndex: {
    type: String,
    default: '0'
  },
  viewType: String
})
const AccountsReceivableAmountMapOption = reactive<EChartsOption>({
  tooltip: {
    trigger: 'item'
  },
  color: ['#3aa1ff', '#79e0b8'],
  series: [
    {
      radius: ['50%', '70%'],
      type: 'pie',
      label: {
        formatter: '{name|{b}}\n{value|{d}}',
        rich: {
          name: {
            fontSize: 12,
            color: '#666'
          },
          value: {
            fontSize: 19,
            color: '#666'
          }
        },
        lineHeight: 25,
        show: true,
        position: 'outside'
      },
      data: []
    }
  ]
}) as EChartsOption

const AccountsReceivableTrendCostTypeMapOption = reactive<EChartsOption>({
  legend: {
    data: []
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    data: [],
    type: 'category'
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      type: 'bar',
      data: [],
      barWidth: '20px'
    }
  ]
}) as EChartsOption
/** 导出报表 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    if (props.activeIndex == '0') {
      const data = await contractOrderApi.exportAccountsReceivableReport(queryParams)
      download.excel(data, '应收报表.xlsx')
    } else {
      const data = await contractOrderApi.exportAccountsPayableReport(queryParams)
      download.excel(data, '应支报表.xlsx')
    }
  } catch {
  } finally {
    exportLoading.value = false
  }
}

//房源抽屉
const RoomStatusDetailRef = ref<any>(null)
const room_openForm = (id?: number) => {
  RoomStatusDetailRef.value.open({
    id: id
  })
}

/**生成缴费通知单 */
const collectionFormRef = ref()
const handleCollectionForm = async (row) => {
  collectionFormRef.value.open(
    { buildName: row.buildName, buildId: row.buildId },
    { id: row.ownerId, contractId: row.contractId },
    { id: row.billId }
  )
}
/** 查询环形图 */
const getAccountsReceivableAmountMap = async () => {
  const data =
    props.activeIndex == '1'
      ? await contractOrderApi.getAccountsPayableAmountMap({
          method: queryParams.method,
          payDate: queryParams.payDate
        })
      : await contractOrderApi.getAccountsReceivableAmountMap({
          method: queryParams.method,
          payDate: queryParams.payDate
        })
  console.log(data, 'data')
  AccountsReceivableAmountMapOption.series[0]['data'] = data.dataList
}

/** 查询柱状图 */
const getAccountsReceivableTrendCostTypeMap = async () => {
  const data =
    props.activeIndex == '1'
      ? await contractOrderApi.getAccountsPayableTrendCostTypeMap({
          method: queryParams.method,
          payDate: queryParams.payDate
        })
      : await contractOrderApi.getAccountsReceivableTrendCostTypeMap({
          method: queryParams.method,
          payDate: queryParams.payDate
        })
  console.log(data, 'data111')
  AccountsReceivableTrendCostTypeMapOption.xAxis.data = data.xAxisData
  // AccountsReceivableTrendCostTypeMapOption.yAxis.data = data.xAxisData
  data.seriesData.forEach((element, index) => {
    AccountsReceivableTrendCostTypeMapOption.series[0]['data'].push({
      value: element,
      name: data.xAxisData[index]
    })
  })
  console.log(AccountsReceivableTrendCostTypeMapOption.series[0]['data'])

  // data.seriesData.forEach((element, index) => {})
}
/** 查询顶部数据 */
const getTopNumCount = async () => {
  const data =
    props.activeIndex == '1'
      ? await contractOrderApi.getAccountsPayableSummary({
          method: queryParams.method,
          payDate: queryParams.payDate
        })
      : await contractOrderApi.getAccountsReceivableSummary({
          method: queryParams.method,
          payDate: queryParams.payDate
        })
  TopNumCount.value = data
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
    // params.parkIdList = params.parkIdList.join(',')
    // params.billidList = params.billidList.join(',')
    const data =
      props.activeIndex == '0'
        ? await contractOrderApi.getAccountsReceivableReportPage(params)
        : await contractOrderApi.getAccountsPayableReportPage(params)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  if (props.viewType == '1') {
    getList()
  } else {
    getTopNumCount()
    getAccountsReceivableAmountMap()
    getAccountsReceivableTrendCostTypeMap()
  }
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
  // getTopNumCount()
  // getList()
})

watch(
  () => props.viewType,
  (val) => {
    // getList()
    if (val == '1') {
      getList()
    } else {
      getTopNumCount()
      getAccountsReceivableAmountMap()
      getAccountsReceivableTrendCostTypeMap()
    }
  }
)

watch(
  () => props.activeIndex,
  () => {
    if (props.viewType == '1') {
      getList()
    } else {
      getTopNumCount()
      getAccountsReceivableAmountMap()
      getAccountsReceivableTrendCostTypeMap()
    }
  },
  {
    immediate: true,
    deep: true
  }
)
</script>
