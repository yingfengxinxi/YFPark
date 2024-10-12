<template>
  <div class="page">
    <ContentWrap>
      <div class="row">
        <div>
          <Icon icon="ep:arrow-left" @click="emit('back', false)" class="mr-5px" />
          IC卡列表
        </div>
        <el-button type="primary" plain>
          <Icon icon="ep:plus" class="mr-5px" />
          添加IC卡
        </el-button>
      </div>
    </ContentWrap>
    <ContentWrap>
      <el-form label-position="top" :model="form" ref="queryFormRef" class="queryForm">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="租客:">
              <el-input v-model="form.name" placeholder="请选择租客" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="IC卡号:">
              <el-input v-model="form.iphone" placeholder="请输入IC卡号" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <div class="search_box">
              <el-button @click="resetQuery">重置</el-button>
              <el-button type="primary" @click="handleQuery">搜索</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </ContentWrap>
    <ContentWrap>
      <el-table :data="list" v-loading="loading" :stripe="true" :show-overflow-tooltip="true">
        <el-table-column label="楼宇" align="center" prop="ownerId" />
        <el-table-column label="租客" align="center" prop="icCard" />
        <el-table-column label="用户名称" align="center" prop="status" />
        <el-table-column label="用户手机号" align="center" prop="ownerId" />
        <el-table-column label="IC卡号" align="center" prop="icCard" />
        <el-table-column label="状态" align="center" prop="status" />
        <el-table-column label="操作" align="center">
          <template #default="scope">
            {{ scope.row }}
            <el-button link type="primary"> 编辑 </el-button>
            <el-button type="danger"> 删除 </el-button>
          </template>
        </el-table-column>
      </el-table>
    </ContentWrap>
  </div>
</template>
<script setup lang="ts">
import { VillageUserApi, VillageUserVO } from '@/api/bus/villageUser'
import { on } from '../../../../utils/domUtils'
import { onMounted } from 'vue'

const form = ref({
  ownerId: '',
  icCard: '',
  pageNo: 1,
  pageSize: 10
})
const list = ref([])
const loading = ref(false)
const getList = () => {
  loading.value = true
  VillageUserApi.getIcCardList(form.value)
    .then((res) => {
      list.value = res.data.list
    })
    .finally(() => {
      loading.value = false
    })
}
onMounted(() => {
  getList()
})
</script>
<style>
.page {
  .row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 16px;
    font-weight: 600;
    color: #333;
  }
  .search_box {
    display: flex;
    justify-content: flex-start;
    align-items: flex-end;
    height: 100%;
    padding-bottom: 18px;
    box-sizing: border-box;
  }
}
</style>
