<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <ColHeaderLeftBlue :header_data="'基本条款'">
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="detail_header_title">房源信息:</div>
            <div class="detail_header_content">
              <div v-for="(item, index) in checkedBuild" :key="index">
                {{ item.buildName }}/{{ item.layerName }}/{{ item.villageRoomId }},
              </div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="detail_header_title">租赁数:</div>
            <div class="detail_header_content">
              {{ props.data.leaseArea || '--' }}
            </div>
          </el-col>
          <el-col :span="12" class="mt-3">
            <div class="detail_header_title">含税规则:</div>
            <div class="detail_header_content">
              {{ taxClause_1.taxRuleLabel || '--' }}
            </div>
          </el-col>
        </el-row>
      </ColHeaderLeftBlue>
    </el-col>
    <el-col :span="12">
      <ColHeaderLeftBlue :header_data="'保证金条款'">
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="detail_header_title">保证金类型:</div>
            <div class="detail_header_content"> 物业保证金 </div>
          </el-col>
          <el-col :span="12">
            <div class="detail_header_title">保证金金额:</div>
            <div class="detail_header_content">{{ bondClause_1.bondPrice + '元' || '--' }}</div>
          </el-col>
        </el-row>
      </ColHeaderLeftBlue>
    </el-col>
    <el-col :span="24" class="mt-8">
      <ColHeaderLeftBlue :header_data="'物业条款'">
        <el-row
          :gutter="20"
          v-for="(item, index) in multipleClause_1"
          :key="index"
          class="bg-[#F0F9FF] p-2 mb-1"
        >
          <el-col :span="4">
            <div class="detail_header_title">开始时间:</div>
            <div class="detail_header_content">{{ item.startLeaseDate }}</div>
          </el-col>
          <el-col :span="4">
            <div class="detail_header_title">结束时间:</div>
            <div class="detail_header_content">{{ item.endLeaseDate }}</div>
          </el-col>
          <el-col :span="4">
            <div class="detail_header_title">付款时间:</div>
            <div class="detail_header_content">提前{{ item.payDay }}天</div>
          </el-col>
          <el-col :span="4">
            <div class="detail_header_title">合同单价:</div>
            <div class="detail_header_content">
              <span v-for="(Listitem, index) in contractUnitPriceList" :key="index">
                <span v-if="Listitem.sort == item.contractUnitPrice">
                  {{ item.dMoney }}{{ Listitem.label }}
                </span>
              </span>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="detail_header_title">计费类型:</div>
            <div class="detail_header_content"> 按月计费</div>
          </el-col>
          <el-col :span="4">
            <div class="detail_header_title">租期划分方式:</div>
            <div class="detail_header_content">按起始日划分</div>
          </el-col>
          <el-col :span="4">
            <div class="detail_header_title">天单价换算规则:</div>
            <div class="detail_header_content">按自然月换算</div>
          </el-col>
          <el-col :span="4">
            <div class="detail_header_title">年天数:</div>
            <div class="detail_header_content">{{ item.yearDays || '365' }}</div>
          </el-col>
          <el-col :span="4">
            <div class="detail_header_title">付款周期:</div>
            <div class="detail_header_content">{{ item.day }}月一付</div>
          </el-col>
        </el-row>
      </ColHeaderLeftBlue>
    </el-col>
  </el-row>
</template>
<script lang="ts" setup>
import { Api } from '@/api/contract/contractList/index'
import ColHeaderLeftBlue from './ColHeaderLeftBlue.vue'
const checkedBuild = ref([])
const clauseTypes = ref([])
const taxClause_1 = ref([])
const bondClause_1 = ref([])
const multipleClause_1 = ref([])
const contractUnitPriceList = ref([])
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
    checkedBuild.value = JSON.parse(val.checkedBuild)
    clauseTypes.value = JSON.parse(val.clauseTypes)
    taxClause_1.value = JSON.parse(clauseTypes.value[1].taxClause)
    bondClause_1.value = JSON.parse(clauseTypes.value[1].bondClause)
    multipleClause_1.value = JSON.parse(clauseTypes.value[1].multipleClause)

    gettaxRule(taxClause_1.value.taxRule)
  }
)
const getcontractUnitPriceList = () => {
  Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'CONTRACT_UNIT_PRICE'
  }).then((res) => {
    contractUnitPriceList.value = res.list
  })
}
// 获取含税规则字典
const gettaxRule = async (id) => {
  const res = await Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'TAX_INCLUSIVE_RULES'
  })
  taxClause_1.value.taxRuleLabel = res.list.find((item) => item.sort == id).label
}
onMounted(() => {
  getcontractUnitPriceList()
})
</script>
<style scoped>
.detail_header_title {
  font-size: 14px;
  color: #999;
  margin: 5px;
}
.detail_header_content {
  font-size: 14px;
  color: #333;
  margin: 10px 0 0 5px;
}
</style>
