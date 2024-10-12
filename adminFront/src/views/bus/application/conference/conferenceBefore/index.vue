<template>
  <div class="bg-white p-10px">
    <el-row :gutter="10">
      <el-col :span="4">
        <el-select
          placeholder="全部项目"
          clearable
          v-model="queryParams.villageId"
          @change="getList"
        >
          <el-option
            v-for="(item, index) in getProjectList"
            :key="index"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-input placeholder="搜索手机号" v-model="queryParams.userMobile">
          <template #append>
            <Icon icon="ep:search" @click="getList" />
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-input placeholder="搜索单号" v-model="queryParams.orderNo">
          <template #append>
            <Icon icon="ep:search" @click="getList" />
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-date-picker
          type="daterange"
          range-separator="至"
          start-placeholder="下单开始时间"
          end-placeholder="下单结束时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          format="YYYY-MM-DD HH:mm:ss"
          @change="changeDate"
          v-model="time"
          style="width: calc(100% - 20px)"
      /></el-col>
      <el-col :span="2">
        <el-select placeholder="全部状态" v-model="queryParams.status" @change="getList" clearable>
          <el-option
            v-for="(item, index) in statusList"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-col>
    </el-row>
  </div>
  <el-table :data="tableList" class="mt-18px">
    <el-table-column prop="orderNo" align="center" label="订单编号" fixed />
    <el-table-column prop="categoryName" align="center" label="预约会议室" />
    <el-table-column prop="ownerName" align="center" label="对应租客" />
    <el-table-column prop="ownerName" align="center" label="预约人姓名" />
    <el-table-column prop="userMobile" align="center" label="手机号码" />
    <el-table-column prop="orderTotal" align="center" label="订单金额" />
    <el-table-column prop="status" align="center" label="状态">
      <template #default="{ row }">
        <el-tag v-if="row.status === 0" type="info">待支付</el-tag>
        <el-tag v-if="row.status === 1" type="warning">已支付</el-tag>
        <el-tag v-if="row.status === 2" type="success">已核销</el-tag>
        <el-tag v-if="row.status === 3" type="danger">已过期</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="name" align="center" label="操作" fixed="right">
      <template #default="{ row }">
        <el-button text type="primary" size="small" @click="changeDetails(row.id)">详情</el-button>
      </template>
    </el-table-column>
  </el-table>
  <Pagination
    :total="total"
    v-model:page="queryParams.pageNo"
    v-model:limit="queryParams.pageSize"
    @pagination="getList"
  />
  <Detail ref="conferenceDetails_ref" />
</template>
<script lang="ts" setup>
import { conferenceApi } from '@/api/conference/index'
import Detail from './component/conference_details.vue'
import { BuildApi } from '@/api/bus/village/index'
import { getStrDictOptions } from '@/utils/dict'
import { onMounted } from 'vue'
const tableList = ref([])
const time = ref([])
const queryParams = ref({
  villageId: '',
  userMobile: '',
  orderNo: '',
  startTime: '',
  endTime: '',
  pageNo: 1,
  pageSize: 10,
  status: '',
  appSign: 'metting'
})
const activeCode = ref('')
const total = ref(0)
const getList = () => {
  conferenceApi.getresvplace(queryParams.value).then((res) => {
    tableList.value = res.list
    total.value = res.total
  })
}
const conferenceDetails_ref = ref()
const changeDetails = (id) => {
  conferenceDetails_ref.value.open(id)
}
const changeDate = () => {
  queryParams.value.startTime = time.value[0]
  queryParams.value.endTime = time.value[1]
  getList()
}
onMounted(() => {
  getList()
  getProject()
  getstatusList()
})
//获取项目列表
const getProjectList = ref([])
const getProject = () => {
  BuildApi.getVillageList({}).then((res) => {
    getProjectList.value = res
  })
}
const statusList = ref([])
const getstatusList = async () => {
  const data = await getStrDictOptions('RESV_BOOKING_STATUS')
  statusList.value = data
}
</script>
