<template>
  <el-dialog v-model="dialogVisible" title="选择巡检点" width="60%">
    <el-input class="!w-220px" placeholder="输入巡检点名称">
      <template #append>
        <Icon icon="ep:search" />
      </template>
    </el-input>
    <el-table
      class="mt-20px"
      :data="dataList"
      height="400"
      :row-key="(row) => row.id"
      ref="multipleTableRef"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" :reserve-selection="true" />
      <el-table-column prop="number" label="巡检点编码" align="center" />
      <el-table-column prop="name" label="巡检点名称" align="center" />
      <el-table-column prop="positionName" label="位置" align="center" />
      <el-table-column prop="formTitle" label="巡检内容" align="center" />
      <el-table-column label="状态" align="center" prop="status" width="180px">
        <template #default="scope">
          <el-switch v-model="scope.row.status" active-value="1" inactive-value="0" disabled />
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="bg-#fff h-40px">
      <Pagination
        :total="total"
        v-model:page="form.pageNo"
        v-model:limit="form.pageSize"
        @pagination="queryPositionList"
      />
    </div>
    <template #footer>
      <div class="flex justify-end w-100%">
        <el-button> 取消 </el-button>
        <el-button type="primary" @click="submitData"> 保存 </el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
const dialogVisible = ref(false)
import { Api, VO } from '@/api/bus/patrol/position'
import { onMounted } from 'vue'
import { set } from 'lodash-es'
const dataList = ref<VO[]>([])
const total = ref(0)
const form = ref({
  name: '',
  pageNo: 1,
  pageSize: 10
})
// 查询巡检点列表
const queryPositionList = async (seldata, type) => {
  form.value.application = application.value
  const res = await Api.getPage(form.value)
  dataList.value = res.list
  total.value = res.total
  if (type == 'open') {
    hxList(seldata, 'open')
  } else {
    hxList(selectData.value, 'list')
  }
}
const hxstatus = ref(true)
const hxList = async (data, type) => {
  if (type == 'open' && data?.length >= 1) {
    hxstatus.value = false
    setTimeout(() => {
      hxstatus.value = true
    }, 200)
    await selectData.value.map((item) => {
      multipleTableRef.value!.toggleRowSelection(item)
    })
  }
}
//所选择的表格
const selectData = ref<VO[]>([])
const handleSelectionChange = (val: VO[]) => {
  if (hxstatus.value) {
    selectData.value = val
  }
}

const multipleTableRef = ref()
async function open(data) {
  dialogVisible.value = true
  if (multipleTableRef.value) {
    hxstatus.value = false
    setTimeout(() => {
      hxstatus.value = true
    }, 200)
    multipleTableRef.value.setCurrentRow([])
    multipleTableRef.value.clearSelection()
  }
  await queryPositionList(data, 'open')
}
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['submit'])
const submitData = () => {
  emit('submit', selectData.value)
  dialogVisible.value = false
}
onMounted(async () => {
  await getapplication()
  queryPositionList()
})
</script>
