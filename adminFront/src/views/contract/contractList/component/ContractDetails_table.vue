<template>
  <ContentWrapBorder :header_data="'租赁条款明细报表'" class="htmx_list mt-[18px]">
    <el-collapse v-model="zptk">
      <el-collapse-item title="保证金" name="1">
        <el-table :data="detail_bzj_0">
          <el-table-column prop="payStartDate" align="center" label="还款周期开始时间" />
          <el-table-column prop="payEndDate" align="center" label="还款周期结束时间" />
          <el-table-column prop="feeType" align="center" label="费用类型">
            <template #default="{ row }">
              <span>{{ row.feeType == '7' ? '保证金' : '租金' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="payDate" align="center" label="付款日" />
          <el-table-column prop="finalUnitPrice" align="center" label="最终单价(含税)" />
          <el-table-column prop="receivable" align="center" label="应收(含税)" />
          <el-table-column prop="taxAmount" align="center" label="税额" />
        </el-table>
      </el-collapse-item>
      <el-collapse-item title="租金" name="2">
        <el-table :data="reportDetail_0">
          <el-table-column prop="payStartDate" align="center" label="还款周期开始时间" />
          <el-table-column prop="payEndDate" align="center" label="还款周期结束时间" />
          <el-table-column prop="feeType" align="center" label="费用类型">
            <template #default="{ row }">
              <span>{{ row.feeType == '7' ? '保证金' : '租金' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="payDate" align="center" label="付款日" />
          <el-table-column prop="finalUnitPrice" align="center" label="最终单价(含税)" />
          <el-table-column prop="receivable" align="center" label="应收(含税)" />
          <el-table-column prop="taxAmount" align="center" label="税额" />
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </ContentWrapBorder>
  <ContentWrapBorder :header_data="'物业条款明细报表'" class="htmx_list mt-[18px]">
    <el-collapse v-model="wytk">
      <el-collapse-item title="保证金" name="1">
        <el-table :data="detail_bzj_1">
          <el-table-column prop="payStartDate" align="center" label="还款周期开始时间" />
          <el-table-column prop="payEndDate" align="center" label="还款周期结束时间" />
          <el-table-column prop="feeType" align="center" label="费用类型">
            <template #default="{ row }">
              <span>{{ row.feeType == '7' ? '保证金' : '租金' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="payDate" align="center" label="付款日" />
          <el-table-column prop="finalUnitPrice" align="center" label="最终单价(含税)" />
          <el-table-column prop="receivable" align="center" label="应收(含税)" />
          <el-table-column prop="taxAmount" align="center" label="税额" />
        </el-table>
      </el-collapse-item>
      <el-collapse-item title="租金" name="2">
        <el-table :data="reportDetail_1">
          <el-table-column prop="payStartDate" align="center" label="还款周期开始时间" />
          <el-table-column prop="payEndDate" align="center" label="还款周期结束时间" />
          <el-table-column prop="feeType" align="center" label="费用类型">
            <template #default="{ row }">
              <span>{{ row.feeType == '7' ? '保证金' : '租金' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="payDate" align="center" label="付款日" />
          <el-table-column prop="finalUnitPrice" align="center" label="最终单价(含税)" />
          <el-table-column prop="receivable" align="center" label="应收(含税)" />
          <el-table-column prop="taxAmount" align="center" label="税额" />
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </ContentWrapBorder>
</template>
<script setup lang="ts">
import ContentWrapBorder from '@/views/bus/owner/component/ContentWrap_border.vue'
import { Api } from '@/api/contract/contractList/index'
import { forEach } from '../../../../utils/tree'
const clauseTypes = ref([])
const reportDetail_0 = ref([])
const reportDetail_1 = ref([])
const detail_bzj_0 = ref([]) //保证金
const detail_bzj_1 = ref([]) //保证金

const props = withDefaults(
  defineProps<{
    data: any
  }>(),
  {
    data: ''
  }
)
watch(
  () => props.data,
  (val) => {
    clauseTypes.value = JSON.parse(val.clauseTypes)
    reportDetail_0.value = JSON.parse(clauseTypes.value[0].reportDetail)
    reportDetail_1.value = JSON.parse(clauseTypes.value[1].reportDetail)
    detail_bzj_0.value = []
    detail_bzj_1.value = []
    //付款明细
    reportDetail_0.value.forEach((item: any, index) => {
      if (item.feeType == '7') {
        detail_bzj_0.value.push(item)
        delete reportDetail_0.value[index]
      }
    })
    reportDetail_1.value.forEach((item: any, index) => {
      if (item.feeType == '7') {
        detail_bzj_1.value.push(item)
        delete reportDetail_1.value[index]
      }
    })
  }
)
const zptk = ref(['1', '2'])
const wytk = ref(['1', '2'])
</script>
<style lang="scss" scoped>
.htmx_list {
  ::v-deep .bottom_row {
    padding-top: 0 !important;
  }
  ::v-deep .el-collapse-item__content {
    // padding: 10px 20px !important;
    padding-bottom: 0px !important;
  }
}
</style>
