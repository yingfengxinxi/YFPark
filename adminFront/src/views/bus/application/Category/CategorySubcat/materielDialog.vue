<template>
  <div>
    <el-dialog title="选择物料" v-model="dialogVisible" width="65%">
      <div class="flex items-center gap-8px w-50%">
        <el-input
          v-model="queryParams.stuffName"
          placeholder="物料名称"
          clearable
          @clear="getList"
          style="width: 380px"
        >
          <template #append>
            <Icon icon="ep:search" @click="getList" />
          </template>
        </el-input>
        <el-input
          v-model="queryParams.stuffNumber"
          placeholder="物料编码"
          clearable
          @clear="getList"
          style="width: 380px"
        >
          <template #append>
            <Icon icon="ep:search" @click="getList" />
          </template>
        </el-input>
        <el-input
          v-model="queryParams.brand"
          placeholder="品牌"
          clearable
          @clear="getList"
          style="width: 380px"
        >
          <template #append>
            <Icon icon="ep:search" @click="getList" />
          </template>
        </el-input>
      </div>
      <el-table :data="tableList" @selection-change="handleSelectionChange" ref="tableRef">
        <el-table-column type="selection" width="55" />
        <el-table-column label="物料名称" align="center" prop="stuffName" />
        <el-table-column label="物料编码" align="center" prop="stuffNumber" />
        <el-table-column label="物料品牌" align="center" prop="brand" />
        <el-table-column label="规格型号" align="center" prop="modelName" />
        <el-table-column label="物料价格" align="center" prop="chargePrice" />
        <el-table-column label="计量单位" align="center" prop="meterUnit" />
        <el-table-column label="物料图片" align="center" prop="imageUrl">
          <template #default>
            <el-button type="primary" text> 查看图片 </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <div class="w-100% bg-#fff h-50px">
        <Pagination
          :total="queryParams.total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <template #footer>
        <div class="flex justify-end">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submit">确认</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
const dialogVisible = ref(false)
import { CategorySubcatApi, VO } from '@/api/bus/Category/CategorySubcat/index'
import { onMounted } from 'vue'
const tableList = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  stuffName: '',
  stuffNumber: '',
  brand: '',
  total: 0
})
const tableRef = ref(null)
function open() {
  dialogVisible.value = true
  getList()
  setTimeout(() => {
    tableRef.value.clearSelection()
  }, 50)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['success'])
const selectData = ref([])
const getList = async () => {
  const data = await CategorySubcatApi.getMaterial(queryParams)
  tableList.value = data.list
  queryParams.total = data.total
}
const handleSelectionChange = (val: any) => {
  selectData.value = val
}
const submit = () => {
  emit('success', selectData.value)
  dialogVisible.value = false
}
onMounted(() => {
  getList()
})
</script>
