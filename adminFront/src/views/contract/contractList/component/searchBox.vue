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
          <el-form-item label="签订日期:">
            <el-date-picker
              v-model="queryParams.signingDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              @change="signingDate_change"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="租凭起始日期:">
            <el-date-picker
              v-model="queryParams.SigningDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              @change="leaseDate_change"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="退租日期:">
            <el-date-picker
              v-model="queryParams.LeaseRetreat"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              @change="leaseRetreat_change"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="合同创建日期:">
            <el-date-picker
              v-model="queryParams.contract"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              @change="contract_change"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否续租">
            <el-select v-model="queryParams.isWhetherLease" @change="WhetherLease">
              <el-option label="是" :value="1" />
              <el-option label="否" :value="0" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="经办人">
            <el-select
              v-model="queryParams.operatorId"
              @change="cheng_operatorId"
              placeholder="请选择经办人"
            >
              <el-option
                v-for="item in creatorList"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="创建人">
            <el-select
              v-model="queryParams.creator"
              @change="cheng_creator"
              placeholder="请选择创办人"
            >
              <el-option
                v-for="item in creatorList"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="合同状态">
            <el-select
              v-model="queryParams.statuss"
              placeholder="选择合同状态"
              @change="change_status"
              multiple
            >
              <el-option
                v-for="item in statusList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="选择项目">
            <el-select v-model="queryParams.parkIds" @change="change_parkIds" multiple>
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
            <el-select v-model="queryParams.buildIds" @change="change_buildName" multiple>
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
          <el-button class="mt-[30px]" type="primary" @click="resetForm">重置</el-button>
          <el-button
            class="mt-[30px]"
            type="primary"
            @click="emit('search', form_search, queryParams)"
            >搜索</el-button
          >
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" setup>
const queryParams = ref({
  signingDate: '',
  leaseDate: '',
  LeaseRetreat: '',
  contract: '',
  isWhetherLease: '',
  operatorId: '',
  creator: '',
  statuss: '',
  projectId: '',
  buildName: '',
  parkIds: ''
})
const form_search = ref({
  startSigningDate: '', //开始签订日期
  endSigningDate: '', //结束签订日期
  leaseStartTime: '', //租凭起始日期
  leaseEndTime: '', //租凭结束日期
  startLeaseRetreatTime: '',
  endLeaseRetreatTime: '',
  contractStartTime: '',
  contractEndTime: '',
  isWhetherLease: '',
  operatorId: '',
  creator: '',
  statuss: '',
  projectId: '',
  buildName: '',
  parkIds: ''
})
import { Api } from '@/api/contract/contractList/index'
const emit = defineEmits(['search'])
const dictType_value = ref('')
const props = withDefaults(
  defineProps<{
    dictType: string
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
//签订起始日期
const signingDate_change = (val: any) => {
  if (val === null) {
    form_search.value.startSigningDate = ''
    form_search.value.endSigningDate = ''
    return
  }
  form_search.value.startSigningDate = val[0]
  form_search.value.endSigningDate = val[1]
}
//租凭起始日期
const leaseDate_change = (val: any) => {
  if (val === null) {
    form_search.value.startSigningDate = ''
    form_search.value.endSigningDate = ''
    return
  }
  form_search.value.startSigningDate = val[0]
  form_search.value.endSigningDate = val[1]
}
//退租日期
const leaseRetreat_change = (val: any) => {
  if (val === null) {
    form_search.value.startLeaseRetreatTime = ''
    form_search.value.endLeaseRetreatTime = ''
    return
  }
  form_search.value.startLeaseRetreatTime = val[0]
  form_search.value.endLeaseRetreatTime = val[1]
}
//合同创建日期
const contract_change = (val: any) => {
  if (val === null) {
    form_search.value.startCreateTime = ''
    form_search.value.endCreateTime = ''
    return
  }
  form_search.value.startCreateTime = val[0]
  form_search.value.endCreateTime = val[1]
}
//是否续租
const WhetherLease = (val: any) => {
  if (val === null) {
    form_search.value.isWhetherLease = ''
    return
  }
  form_search.value.isWhetherLease = val
}
//获取合同状态
const statusList = ref([])
const status_remoteMethod = (query: string) => {
  Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: dictType_value.value
  }).then((res) => {
    statusList.value = res.list
  })
}
const change_status = (val: any) => {
  if (val === null) {
    form_search.value.statuss = ''
    return
  }
  form_search.value.statuss = val.join(',')
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
    form_search.value.buildIds = ''
    return
  }
  form_search.value.buildIds = val.join(',')
}
//获得经办人,创建人
const creatorList = ref([])
const cheng_operatorId = (val: any) => {
  if (val === null) {
    form_search.value.operatorId = ''
    return
  }
  form_search.value.creator = val
}
const cheng_creator = (val: any) => {
  if (val === null) {
    form_search.value.creator = ''
    return
  }
  form_search.value.creator = val
}
const remoteMethod_creatorList = (query: string) => {
  Api.getTenantIdUserInfoList(query).then((res) => {
    creatorList.value = res
  })
}
//选择项目
const change_parkIds = (val: any) => {
  if (val === null) {
    form_search.value.parkIds = ''
    return
  }
  form_search.value.parkIds = val.join(',')
}
const searchBox = ref<FormInstance>()
const resetForm = () => {
  searchBox.value.resetFields()
  queryParams.value = {
    signingDate: '',
    leaseDate: '',
    LeaseRetreat: '',
    contract: '',
    isWhetherLease: '',
    operatorId: '',
    creator: '',
    statuss: '',
    projectId: '',
    buildName: '',
    parkIds: ''
  }
  form_search.value = {
    startSigningDate: '', //开始签订日期
    endSigningDate: '', //结束签订日期
    leaseStartTime: '', //租凭起始日期
    leaseEndTime: '', //租凭结束日期
    startLeaseRetreatTime: '',
    endLeaseRetreatTime: '',
    contractStartTime: '',
    contractEndTime: '',
    isWhetherLease: '',
    operatorId: '',
    creator: '',
    statuss: '',
    projectId: '',
    buildName: '',
    parkIds: ''
  }
  emit('search', form_search.value, queryParams.value)
}
onMounted(() => {
  status_remoteMethod('')
  project_remoteMethod()
  building_remoteMethod()
  remoteMethod_creatorList('')
})
</script>
