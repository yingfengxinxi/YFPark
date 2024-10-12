<template>
  <el-dialog v-model="showDialog" title="选择资产" width="70%" top="10vh">
    <div class="bg-white box-border">
      <el-form label-position="top">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="资产编码">
              <el-input v-model="queryParams.propertyNumber" placeholder="请输入资产编码" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="资产分类">
              <el-tree-select
                v-model="queryParams.types"
                :data="locationList"
                :render-after-expand="false"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                placeholder="请选择资产分类"
                clearable
                :check-strictly="true"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="资产名称">
              <el-input v-model="queryParams.name" placeholder="请输入资产名称" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所在位置">
              <el-tree-select
                v-model="queryParams.positionId"
                :data="positionList"
                :render-after-expand="false"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                placeholder="请选择资产位置"
                clearable
                :check-strictly="true"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="品牌">
              <el-input v-model="queryParams.brand" placeholder="请输入品牌" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="型号">
              <el-input v-model="queryParams.modelName" placeholder="请输入型号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <div class="flex justify-start items-center h-100% transform-translate-y-6px">
              <el-button @click="resetForm"> 重置 </el-button>
              <el-button type="primary" @click="getList"> 搜索 </el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
      <el-table
        :data="list"
        :height="300"
        highlight-current-row
        @current-change="handleCurrentChange"
      >
        <el-table-column property="propertyNumber" label="资产编码" width="220" align="center" />
        <el-table-column property="categoryName" label="资产分类" align="center" />
        <el-table-column property="name" label="资产名称" align="center" />
        <el-table-column property="positionName" label="所在位置" align="center" />
        <el-table-column property="brand" label="品牌" align="center" />
        <el-table-column property="modelName" label="型号" align="center" />
        <el-table-column property="deviceCode" label="设备序列号" align="center" />
      </el-table>
      <div class="h-50px">
        <Pagination
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
          class="bg-#fff"
        />
      </div>
    </div>
    <template #footer>
      <div class="flex justify-end">
        <el-button type="primary" @click="submitBtn">确 定</el-button>
        <el-button @click="showDialog = false">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { PropertyLocationApi } from '@/api/bus/property/setting/propertyLocation'
import { onMounted, defineExpose } from 'vue'
const emit = defineEmits(['submit'])
const showDialog = ref(false)
const list = ref([])
const total = ref(0)
const queryParams = ref({
  pageNo: 1,
  pageSize: 10
})
//获取资产列表
const getList = async () => {
  const data = await PropertyApi.getCheckPropertyPage(queryParams.value)
  list.value = data.list
  total.value = data.total
}
const resetForm = async () => {
  queryParams.value = {
    pageNo: 1,
    pageSize: 10
  }
  getList()
}
// 获取分类列表
const locationList = ref([])
const getLocationList = async () => {
  const data = await PropertyApi.getPropertyTypeTree()
  locationList.value = data
}
//获得位置列表
const positionList = ref([])
const getPositionList = async () => {
  const data = await PropertyLocationApi.getPropertyPositionTree({})
  positionList.value = data
}
// 单选数据
const selectData = ref([])
const handleCurrentChange = (data) => {
  selectData.value = data
}
//提交
const submitBtn = () => {
  showDialog.value = false
  emit('submit', selectData.value)
}
function open() {
  showDialog.value = true
  resetForm()
}
defineExpose({ open })
onMounted(() => {
  getList()
  getLocationList()
  getPositionList()
})
</script>
<style lang="scss" scoped></style>
