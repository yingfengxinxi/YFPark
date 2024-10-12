<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <el-form label-position="top" ref="searchBox">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="合同编号：" prop="">
            <el-input placeholder="请输入合同编号" v-model="queryParams.contractNumber" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="账单状态:">
            <el-select
              v-model="queryParams.billStatusList"
              placeholder="请选择账单状态"
              clearable
              @change="change_Input($event, 'billStatusList')"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.BILL_STATUS)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="应收/付时间:">
            <el-date-picker
              type="date"
              v-model="queryParams.startPayDate"
              align="right"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              clearable
              class="flex-1"
              @change="change_Input($event, 'startPayDate')"
            />
            ~
            <el-date-picker
              type="date"
              v-model="queryParams.endPayDate"
              align="right"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              clearable
              class="flex-1"
              @change="change_Input($event, 'endPayDate')"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="合同状态:">
            <el-select
              v-model="queryParams.contractStatusList"
              placeholder="请选择合同状态"
              clearable
              @change="change_Input($event, 'contractStatusList')"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.CONTRACT_STATUS)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="账单来源:">
            <el-select
              v-model="queryParams.billSource"
              placeholder="请选择账单来源"
              clearable
              @change="change_Input($event, 'billSource')"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.BILL_SOURCE)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="费用类型:">
            <el-select
              v-model="queryParams.feeType"
              placeholder="请选择费用类型"
              clearable
              @change="change_Input($event, 'feeType')"
            >
              <el-option
                v-for="dict in feeTypeList"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="起始时间:">
            <el-date-picker
              type="date"
              v-model="queryParams.startCreateTime"
              align="right"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              clearable
              class="flex-1"
              @change="change_Input($event, 'startCreateTime')"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="结束时间:">
            <el-date-picker
              type="date"
              v-model="queryParams.endCreateTime"
              align="right"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              clearable
              class="flex-1"
              @change="change_Input($event, 'endCreateTime')"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="选择项目">
            <el-select v-model="queryParams.parkIdList" @change="change_parkIds" multiple>
              <el-option
                v-for="(item, index) in projectList"
                :key="index"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="选择楼宇">
            <el-select v-model="queryParams.buildIdList" @change="change_buildName" multiple>
              <el-option
                v-for="(item, index) in buildingList"
                :key="index"
                :label="item.buildName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <div class="flex items-end">
            <el-button class="mt-[30px]" type="primary" @click="resetForm">重置</el-button>
            <el-button
              class="mt-[30px] m-r-10px"
              type="primary"
              @click="emit('search', form_search, queryParams)"
              >搜索</el-button
            >
            <el-dropdown>
              <span class="m-r-14px"
                ><el-button type="primary">
                  <Icon icon="ep:plus" color="#fff" class="m-r-6px" />
                  账单
                </el-button></span
              >
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-button
                      text
                      @click="emit('addCollectionBill')"
                      v-hasPermi="['bus:orgBillCostCategory:delete']"
                      >添加收款账单</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      text
                      @click="emit('addPaymentInvoice')"
                      v-hasPermi="['bus:orgBillCostCategory:delete']"
                      >添加付款账单</el-button
                    >
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
            <el-dropdown>
              <span
                ><el-button plain>
                  <Icon icon="ep:arrow-down" class="m-r-6px" />
                  更多
                </el-button></span
              >
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-button text class="m-l-10px" @click="emit('Image_show')"> 导入 </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button text @click="emit('Approval_show')">审批配置</el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" setup>
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
const queryParams = ref({
  billStatusList: undefined,
  startPayDate: undefined,
  endPayDate: undefined,
  contractStatusList: undefined,
  billSource: undefined,
  feeType: undefined,
  startCreateTime: undefined,
  endCreateTime: undefined,
  parkIdList: undefined,
  buildIdList: undefined
})
const form_search = ref({
  billStatusList: undefined,
  startPayDate: undefined,
  endPayDate: undefined,
  contractStatusList: undefined,
  billSource: undefined,
  feeType: undefined,
  startCreateTime: undefined,
  endCreateTime: undefined,
  parkIdList: undefined,
  buildIdList: undefined
})
import { Api } from '@/api/contract/contractList/index'
const emit = defineEmits([
  'search',
  'Image_show',
  'Approval_show',
  'addPaymentInvoice',
  'addCollectionBill'
])
const dictType_value = ref('')
const props = withDefaults(
  defineProps<{
    dictType: string
    feeTypeList: Object
  }>(),
  {
    dictType: ''
  }
)
watch(
  () => props.dictType,
  async (newVal) => {
    dictType_value.value = newVal
  },
  { immediate: true }
)

const change_Input = (val: any, type: string) => {
  console.log(val, type)
  form_search.value[type] = val
}

//获得项目列表
const projectList = ref([])
const project_remoteMethod = () => {
  Api.getVillageList().then((res) => {
    projectList.value = res
  })
}
//获取楼宇列表
const buildingList = ref([])
const building_remoteMethod = () => {
  Api.getBuildingList().then((res) => {
    buildingList.value = res
  })
}
const change_buildName = (val: any) => {
  if (val === null) {
    form_search.value.buildIdList = ''
    return
  }
  form_search.value.buildIdList = val.join(',')
}

//选择项目
const change_parkIds = (val: any) => {
  if (val === null) {
    form_search.value.parkIdList = ''
    return
  }
  form_search.value.parkIdList = val.join(',')
}
const searchBox = ref<FormInstance>()
const resetForm = () => {
  searchBox.value.resetFields()
  queryParams.value = {
    billStatusList: undefined,
    startPayDate: undefined,
    endPayDate: undefined,
    contractStatusList: undefined,
    billSource: undefined,
    feeType: undefined,
    startCreateTime: undefined,
    endCreateTime: undefined,
    parkIdList: undefined,
    buildIdList: undefined
  }
  form_search.value = {
    billStatusList: undefined,
    startPayDate: undefined,
    endPayDate: undefined,
    contractStatusList: undefined,
    billSource: undefined,
    feeType: undefined,
    startCreateTime: undefined,
    endCreateTime: undefined,
    parkIdList: undefined,
    buildIdList: undefined
  }
  emit('search', form_search.value, queryParams.value)
}
onMounted(() => {
  project_remoteMethod()
  building_remoteMethod()
})
</script>
