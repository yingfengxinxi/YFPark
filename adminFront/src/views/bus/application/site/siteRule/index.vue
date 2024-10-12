<template>
  <div class="bg-white p-10px flex">
    <el-row :gutter="10" class="w-100%">
      <el-col :span="4">
        <el-select
          placeholder="全部项目"
          v-model="queryParams.villageId"
          @change="getList"
          clearable
        >
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-input placeholder="搜索规则名称" v-model="queryParams.name">
          <template #append>
            <Icon icon="ep:search" @click="getList" />
          </template>
        </el-input>
      </el-col>
    </el-row>
    <el-button type="primary" @click="AddContract_func">新建规则</el-button>
  </div>
  <el-table :data="tableList" class="mt-18px">
    <el-table-column prop="name" align="center" label="计费规则名称" />
    <el-table-column prop="name" align="center" label="规则详情">
      <template #default="{ row }">
        <el-tooltip
          class="box-item"
          effect="dark"
          :content="`首计时${row.chargeStandard.first_billing_duration}分钟${row.chargeStandard.first_billing_price}元,超过每计时${row.chargeStandard.billing_duration}分钟收${row.chargeStandard.billing_price}元`"
          placement="top"
        >
          <el-button type="primary" text>查看</el-button>
        </el-tooltip>
      </template>
    </el-table-column>
    <el-table-column prop="villageName" align="center" label="所属项目">
      <template #default="{ row }">
        <el-tag type="info">{{ row.villageName }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="status" align="center" label="状态">
      <template #default="{ row }">
        <el-switch
          v-model="row.status"
          :active-value="1"
          :inactive-value="0"
          @change="statusChange(row)"
        />
      </template>
    </el-table-column>
    <el-table-column prop="name" align="center" label="操作">
      <template #default="{ row }">
        <el-button text type="primary" @click="AddContract_func(row.id, 'EDITOR')">编辑</el-button>
        <el-button text type="danger" @click="del_conferenceRule(row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <Pagination
    :total="total"
    v-model:page="queryParams.pageNo"
    v-model:limit="queryParams.pageSize"
    @pagination="getList"
  />
  <AddContract ref="addContract_ref" @getList="getList" />
</template>
<script lang="ts" setup>
import AddContract from './component/AddconferenceRule.vue'
import { FloorApi } from '@/api/bus/village/floor.ts'
import { ElMessageBox } from 'element-plus'
import { conferenceApi } from '@/api/conference/index'
import { onMounted } from 'vue'
const message = useMessage() // 消息弹窗
const tableList = ref([])
const queryParams = ref({
  villageId: '',
  name: '',
  pageNo: 1,
  pageSize: 10,
  appSign: 'library'
})
const total = ref(100)
const getList = () => {
  conferenceApi.resvbillrulepage(queryParams.value).then((res) => {
    tableList.value = res.list
    total.value = res.total
    tableList.value.map((item) => {
      item.chargeStandard = JSON.parse(item.chargeStandard)
    })
  })
}
//删除
const del_conferenceRule = (row) => {
  ElMessageBox.confirm('此操作将永久删除该规则, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    conferenceApi.resvbillruledelete(row.id).then(() => {
      message.success('删除成功')
      getList()
    })
  })
}
const addContract_ref = ref()
const AddContract_func = (data, type) => {
  addContract_ref.value.open(data, type)
}
onMounted(() => {
  getList()
  getProjectList()
})
//获得项目列表
const projectList = ref([])
const getProjectList = () => {
  FloorApi.getVillageList().then((res) => {
    projectList.value = res
  })
}
const statusChange = (row) => {
  conferenceApi.resvbillruleupdate(row).then(() => {
    message.success('修改成功')
  })
}
</script>
