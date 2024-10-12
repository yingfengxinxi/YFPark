<template>
  <el-dialog title="选择部门/岗位" v-model="dialogVisible">
    <div
      class="bg-#E6F7FF p-10px rounded border border-#91D5FF border-solid flex items-center gap-10px"
      v-if="topTitle"
    >
      由于人员会频繁因为离职、请假、调离等原因变动，所以系统不采取选择人员的方式，而是采取了部门+岗位双配置的方式。未来只要新员工归属到此部门中的此岗位，则会自动被选择，无需手动选择人员。
      <Icon icon="ep:close" class="cursor-pointer" @click="topTitle = false" />
    </div>
    <div class="flex justify-between mt-20px min-h-300px gap-20px">
      <div class="w-50% border border-solid border-#E4E7ED rounded">
        <el-card shadow="never">
          <template #header> <span class="text-16px">选择部门</span> </template>
          <div class="min-h-200px overflow-y-scroll max-h-200px">
            <el-tree
              :data="departmentOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :default-expanded-keys="['0']"
              node-key="id"
              :highlight-current="true"
              ref="treeRef"
              @node-click="handleNodeClick"
            />
          </div>
        </el-card>
      </div>
      <div class="w-50% border border-solid border-#E4E7ED rounded">
        <el-card shadow="never">
          <template #header> <span class="text-16px">选择岗位</span> </template>
          <div class="min-h-200px overflow-y-scroll max-h-200px">
            <div
              v-for="(item, index) in stationOptions"
              :key="index"
              class="flex items-center gap-4px py-4px pl-10px cursor-pointer text-14px"
              :class="stationOptionsList.includes(item.id) ? 'active' : ''"
              @click="handleStationClick(item)"
            >
              <Icon icon="ep:user" />
              {{ item.name }}
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <template #footer>
      <div>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
const dialogVisible = ref(false)
const topTitle = ref(true)
import { patrolPlanEquipmentApi, VO } from '@/api/bus/patrol/planEquipment'
import { defaultProps, handleTree } from '@/utils/tree'
import { onMounted } from 'vue'
function open() {
  dialogVisible.value = true
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['success']) // 提供 emit 方法，用于触发父组件事件
///岗位下拉数据
const stationOptions = ref([])
const GetstationOptions = async () => {
  const data = await patrolPlanEquipmentApi.getStationList()
  stationOptions.value = data
}
///部门下拉数据
const departmentOptions = ref([])
const GetdepartmentOptions = async () => {
  const data = await patrolPlanEquipmentApi.getDeptList()
  departmentOptions.value.push(...handleTree(data))
}
const stationOptionsList = ref([])
const handleStationClick = (item) => {
  const index = stationOptionsList.value.indexOf(item.id)
  if (index === -1) {
    stationOptionsList.value.push(item.id)
  } else {
    stationOptionsList.value.splice(index, 1)
  }
}
onMounted(() => {
  GetstationOptions()
  GetdepartmentOptions()
})
const handleNodeClick = (key, node) => {
  departmentData.value = key
}
const departmentData = ref({})

const treeRef = ref()
const submit = async () => {
  ///返回岗位
  const station = []
  stationOptionsList.value.forEach((item) => {
    stationOptions.value.forEach((element) => {
      if (element.id === item) {
        station.push(element)
      }
    })
  })
  //返回部门
  console.log(station, departmentData.value)
  dialogVisible.value = false
  emit('success', { station, department: departmentData.value })
}
</script>
<style scoped lang="scss">
.active {
  background-color: #ecf5ff;
}
</style>
