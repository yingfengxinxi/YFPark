<template>
  <div class="px-[10px]">
    <el-row :gutter="20" class="bg-white p-18px">
      <el-col :span="6">
        <div class="detail_header_title"> 收款账单总金额 </div>
        <div class="detail_header_content"> {{ total.receivableTotalMoney1 }} </div>
      </el-col>
      <el-col :span="6">
        <div class="detail_header_title"> 收款账单总实收 </div>
        <div class="detail_header_content"> {{ total.receivablePaymentTotalMoney1 }} </div>
      </el-col>
      <el-col :span="6">
        <div class="detail_header_title"> 付款账单总金额 </div>
        <div class="detail_header_content"> {{ total.receivableTotalMoney2 }} </div>
      </el-col>
      <el-col :span="6">
        <div class="detail_header_title"> 付款账单总实付 </div>
        <div class="detail_header_content"> {{ total.receivablePaymentTotalMoney2 }} </div>
      </el-col>
    </el-row>
  </div>
  <!-- 非押金类 -->
  <div class="mt-18px">
    <div class="flex gap-[20px] items-center bg-white p-18px">
      <span>非押金类</span>
      <el-radio-group v-model="segmented_value_unSecurity" size="large" @change="unSecurity_get">
        <el-radio-button
          v-for="(item, index) in segmented_option"
          :key="index"
          :label="item.label"
          :value="item.value"
        />
      </el-radio-group>
      <div style="width: 160px">
        <el-date-picker
          v-model="segmented_value_unSecurity_date"
          type="daterange"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          @change="unSecurity_get"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          @clear="unSecurity_get"
        />
      </div>
    </div>
    <div>
      <el-table style="width: 100%" :data="unSecurity_list">
        <el-table-column prop="collectedAmount" label="需收金额" align="center" />
        <el-table-column prop="isLateFee" label="是否预期" align="center" />
        <el-table-column prop="jiaAdjustPrice" label="调增金额" align="center">
          <template #default="{ row }">
            <span v-if="row.jiaAdjustPrice">{{ row.jiaAdjustPrice }}</span>
            <span v-else>0.00</span>
          </template>
        </el-table-column>
        <el-table-column prop="jianAdjustPrice" label="调减金额" align="center">
          <template #default="{ row }">
            <span v-if="row.jianAdjustPrice">{{ row.jianAdjustPrice }}</span>
            <span v-else>0.00</span>
          </template>
        </el-table-column>
        <el-table-column prop="lateFee" label="违约金" align="center" />
        <el-table-column prop="payDate" label="应付时间" align="center" />
        <el-table-column prop="payEndDate" label="缴费结束时间" align="center" />
        <el-table-column prop="payStartDate" label="缴费开始时间" align="center" />
        <el-table-column prop="receivable" label="应付金额" align="center" />
        <el-table-column prop="receivablePayment" label="实付金额" align="center" />
      </el-table>
    </div>
  </div>
  <!-- 押金类 -->
  <div class="mt-18px">
    <div class="flex gap-[20px] items-center bg-white p-18px">
      <span>押金类</span>
      <el-radio-group v-model="segmented_value_Security" size="large" @change="Security_get">
        <el-radio-button
          v-for="(item, index) in segmented_option"
          :key="index"
          :label="item.label"
          :value="item.value"
        />
      </el-radio-group>
      <div style="width: 160px">
        <el-date-picker
          v-model="segmented_value_Security_date"
          type="daterange"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          @change="Security_get"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          @clear="Security_get"
        />
      </div>
    </div>
    <div>
      <el-table style="width: 100%" :data="Security_list">
        <el-table-column prop="collectedAmount" label="需收金额" align="center" />
        <el-table-column prop="isLateFee" label="是否预期" align="center" />
        <el-table-column prop="jiaAdjustPrice" label="调增金额" align="center">
          <template #default="{ row }">
            <span v-if="row.jiaAdjustPrice">{{ row.jiaAdjustPrice }}</span>
            <span v-else>0.00</span>
          </template>
        </el-table-column>
        <el-table-column prop="jianAdjustPrice" label="调减金额" align="center">
          <template #default="{ row }">
            <span v-if="row.jianAdjustPrice">{{ row.jianAdjustPrice }}</span>
            <span v-else>0.00</span>
          </template>
        </el-table-column>
        <el-table-column prop="lateFee" label="违约金" align="center" />
        <el-table-column prop="payDate" label="应付时间" align="center" />
        <el-table-column prop="payEndDate" label="缴费结束时间" align="center" />
        <el-table-column prop="payStartDate" label="缴费开始时间" align="center" />
        <el-table-column prop="receivable" label="应付金额" align="center" />
        <el-table-column prop="receivablePayment" label="实付金额" align="center" />
      </el-table>
    </div>
  </div>
</template>
<script setup lang="ts">
import { Api } from '@/api/contract/contractList/index'
let segmented_value_unSecurity = ref('1')
let segmented_value_unSecurity_date = ref([])
let segmented_value_Security = ref('1')
let segmented_value_Security_date = ref([])

const segmented_option = [
  {
    label: '收入账单',
    value: '1'
  },
  {
    label: '支出账单',
    value: '2'
  }
]
// 非押金类
const unSecurity_list = ref([])
const unSecurity_get = (val) => {
  getList_unSecurity()
}
const getList_unSecurity = async (detailId) => {
  if (!segmented_value_unSecurity_date.value) {
    segmented_value_unSecurity_date.value = ['', '']
  }
  Api.getByBillTypeContractIdBillInfoList({
    startPayDate: segmented_value_unSecurity_date.value[0],
    endPayDate: segmented_value_unSecurity_date.value[1],
    contractId: props.detailId,
    isBond: 0,
    billType: segmented_value_unSecurity.value
  }).then((res) => {
    unSecurity_list.value = res
  })
}
// 押金类
const Security_list = ref([])
const Security_get = (val) => {
  getList_Security()
}
const getList_Security = async (detailId) => {
  if (!segmented_value_Security_date.value) {
    segmented_value_Security_date.value = ['', '']
  }
  Api.getByBillTypeContractIdBillInfoList({
    startPayDate: segmented_value_Security_date.value[0],
    endPayDate: segmented_value_Security_date.value[1],
    contractId: props.detailId,
    isBond: 1,
    billType: segmented_value_Security.value
  }).then((res) => {
    Security_list.value = res
  })
}
//账单列表账单详情
let total = ref([])
const getTotal = (detailId) => {
  Api.getByBillTypeContractIdBillMoneyTotal(detailId).then((res) => {
    total.value = res
  })
}
//接收id
let props = defineProps({
  detailId: {
    type: String,
    default: ''
  }
})
watch(
  () => props.detailId,
  (val) => {
    getList_unSecurity(val)
    getTotal(val)
  }
)
</script>
<style lang="scss" scoped>
.detail_header_title {
  font-size: 14px;
  color: #999;
  margin: 5px;
}
.detail_header_content {
  font-size: 14px;
  color: #333;
  margin: 15px 0 0 5px;
}
</style>
