<template>
  <el-dialog v-model="dialogVisible" title="选择资产" width="80%">
    <template #title>
      <div class="flex gap-[18px] items-center">
        <span>选择资产</span>
        <el-button type="primary" plain>添加资产</el-button>
        <Icon icon="ep:refresh" color="#1890FF" :size="18" id="refreshBtn" @click="rotateBox" />
      </div>
    </template>
    <el-form label-position="top">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="资产编码">
            <el-input placeholder="请输入资产编码" v-model="queryParams.propertyNumber" />
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
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="资产名称">
            <el-input placeholder="请输入资产名称" v-model="queryParams.name" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所在位置">
            <el-tree-select
              v-model="queryParams.positionId"
              :data="positionList"
              :render-after-expand="false"
              :props="{ label: 'name', value: 'id', children: 'children' }"
              placeholder="请选择所在位置"
              clearable
              :check-strictly="true"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="品牌">
            <el-input placeholder="请输入品牌" v-model="queryParams.brand" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="型号">
            <el-input placeholder="请输入型号" v-model="queryParams.modelName" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <div class="flex transform-translate-y-[30px]">
            <el-button>重置</el-button>
            <el-button type="primary">搜索</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>
    <el-scrollbar height="400px">
      <ContentWrap>
        <el-table
          :data="data"
          v-loading="loading"
          height="310px"
          @selection-change="handleSelectionChange"
          ref="tableRef"
        >
          <el-table-column type="selection" width="75" />
          <el-table-column label="资产编号" prop="propertyNumber" align="center" />
          <el-table-column label="资产分类" prop="categoryName" align="center" />
          <el-table-column label="资产名称" prop="name" align="center" />
          <el-table-column label="所在位置" prop="buildBind" align="center">
            <template #default="{ row }"
              >{{ row.buildBind.villageName }}/{{ row.buildBind.buildName }}/{{
                row.buildBind.layerName
              }}/{{ row.buildBind.name }}</template
            >
          </el-table-column>
          <el-table-column label="品牌" prop="brand" align="center" />
          <el-table-column label="型号" prop="modelName" align="center" />
          <el-table-column label="设备序列号" prop="deviceCode" align="center" />
        </el-table>
        <!-- 分页 -->
        <Pagination
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </ContentWrap>
    </el-scrollbar>
    <template #footer>
      <div class="flex justify-end">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="batchAdd">保存</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import { TablemanageApi } from '@/api/bus/WaterElectricity/Tablemanage/index'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { PropertyLocationApi } from '@/api/bus/property/setting/propertyLocation'

const dialogVisible = ref(false)
const data = ref()
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  types: undefined,
  positionId: undefined
})
const loading = ref(false)
const total = ref(0)
function open() {
  dialogVisible.value = true
}
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
  multipleSelection.value = val
}
const tableRef = ref(null)
const batchAdd = () => {
  emit('selectlist', multipleSelection.value)
  dialogVisible.value = false
  multipleSelection.value = []
  tableRef.value.clearSelection()
}
const emit = defineEmits(['selectlist'])
defineExpose({
  open
})
const getList = () => {
  loading.value = true
  TablemanageApi.bindAssetList(queryParams).then((res) => {
    data.value = res.list
    data.value.forEach((item) => {
      item.buildBind = JSON.parse(item.buildBind)
    })
    loading.value = false
    total.value = res.total
  })
}
//资产分类
const locationList = ref([])
const getLocationList = async () => {
  const data = await PropertyApi.getPropertyTypeTree({})
  locationList.value = data
}

/** 获得位置列表 */
const positionList = ref([])
const getPositionList = async () => {
  const data = await PropertyLocationApi.getPropertyPositionTree({})
  positionList.value = data
}
onMounted(() => {
  getList()
  getLocationList()
  getPositionList()
})
let rotationAngle = 0
const rotateBox = () => {
  const box = document.getElementById('refreshBtn')
  rotationAngle += 360 // 每次点击增加360度
  box.style.transform = `rotate(${rotationAngle}deg)`
  getList()
}
</script>
<style lang="scss" scoped>
#refreshBtn {
  transition: transform 0.7s ease-in-out;
}
</style>
