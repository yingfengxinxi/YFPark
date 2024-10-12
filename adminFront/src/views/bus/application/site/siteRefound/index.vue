<template>
  <div class="bg-white p-10px">
    <div class="header">
      <div
        class="title"
        :class="queryParams.status == '0' ? 'active' : ''"
        @click="changeActive('0')"
        >待处理</div
      >
      <div
        class="title"
        :class="queryParams.status == '1' ? 'active' : ''"
        @click="changeActive('1')"
        >已处理</div
      >
      <div
        class="title"
        :class="queryParams.status == '2' ? 'active' : ''"
        @click="changeActive('2')"
        >驳回</div
      >
    </div>
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
    </el-row>
  </div>
  <el-table :data="tableList" class="mt-18px">
    <el-table-column prop="orderNo" align="center" label="订单编号" fixed />
    <el-table-column prop="categoryName" align="center" label="预约会议室" />
    <el-table-column prop="ownerName" align="center" label="对应租客" />
    <el-table-column prop="ownerName" align="center" label="预约人姓名" />
    <el-table-column prop="userMobile" align="center" label="手机号码" />
    <el-table-column prop="orderTotal" align="center" label="订单金额" />
    <el-table-column prop="name" align="center" label="操作" fixed="right">
      <template #default="{ row }">
        <el-button text type="primary" size="small" @click="examine(row)" v-if="row.status == 0"
          >审核</el-button
        >
        <el-button text type="primary" size="small" @click="changeDetails(row.bookingId)"
          >详情</el-button
        >
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
  <!-- 审核弹窗 -->
  <el-dialog title="审核退款" v-model="examine_show" width="30%">
    <el-form ref="examine_ref" :model="form">
      <el-form-item
        label="审核结果"
        prop="status"
        :rules="[{ required: true, message: '请选择审核结果', trigger: 'blur' }]"
      >
        <el-select v-model="form.status">
          <el-option label="通过" value="1" />
          <el-option label="驳回" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item
        v-if="form.status == 1"
        label="退款金额"
        prop="actualAmount"
        :rules="[{ required: true, message: '请输入退款金额', trigger: 'blur' }]"
      >
        <el-input v-model="form.actualAmount" placeholder="请输入退款金额" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button @click="examine_show_close">取消</el-button>
        <el-button type="primary" @click="examineSubmit">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import { conferenceApi } from '@/api/conference/index'
import { FloorApi } from '@/api/bus/village/floor'
import Detail from '@/views/bus/application/conference/conferenceBefore/component/conference_details.vue'
const activeCode = ref('1')
import { onMounted } from 'vue'
const examineDetail = ref({})
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
  status: '0',
  appSign: 'library',

})
const total = ref(0)
const getList = () => {
  conferenceApi.getrefund(queryParams.value).then((res) => {
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
})
//获取项目列表
const getProjectList = ref([])
const getProject = () => {
  FloorApi.getVillageList().then((res) => {
    getProjectList.value = res
  })
}
const changeActive = (index) => {
  queryParams.value.status = index
  getList()
}
//审核退款
const examine_show = ref(false)
const examine = (row) => {
  examine_show.value = true
  examineDetail.value = row
}
const form = ref({
  status: '',
  refundType: '',
  actualAmount: ''
})
const examine_ref = ref()
const examine_show_close = () => {
  examine_show.value = false
  examine_ref.value.resetFields()
  form.value = {
    status: '',
    refundType: '',
    actualAmount: ''
  }
}
const examineSubmit = () => {
  examine_ref.value.validate((valid) => {
    if (valid) {
      conferenceApi
        .updateresvplaceRefund({
          appSign: examineDetail.value.appSign,
          villageId: examineDetail.value.villageId,
          orderId: examineDetail.value.id,
          amount: examineDetail.value.orderTotal,
          actualAmount: form.value.actualAmount,
          ownerId: examineDetail.value.ownerId,
          status: form.value.status,
          refundType: form.value.actualAmount == examineDetail.value.orderTotal ? '1' : '2',
          bookingId: examineDetail.value.bookingId,
          id: examineDetail.value.id
        })
        .then(() => {
          examine_show.value = false
          getList()
          examine_show_close()
        })
    }
  })
}
</script>
<style lang="scss" scoped>
.header {
  border-bottom: 1px solid #ebeef5;
  margin-bottom: 20px;
  display: flex;
  justify-content: start;
  align-items: center;
  gap: 20px;
  .title {
    padding-bottom: 12px;
    font-size: 14px;
    border-bottom: 2px solid #fff;
    cursor: pointer;
  }
  .active {
    color: #409eff;
    border-bottom: 2px solid #409eff;
  }
}
</style>
