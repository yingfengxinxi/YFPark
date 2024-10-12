<template>
  <div>
    <userSelect ref="UserformRef" @submit="Usersubmit" />
    <el-dialog v-model="dialogVisible" width="30%" :before-close="closePost">
      <template #title>
        <div class="flex gap-8px items-end">
          <span class="text-16px">设置客服工作人员</span>
          <span class="text-12px text-#929292"> 需要在组织架构中设置人员的岗位 </span>
        </div>
      </template>

      <div class="mt-30px">
        <el-form>
          <el-form-item label="选择岗位" required>
            <el-select v-model="Station">
              <el-option
                v-for="item in stationOptions"
                :key="item.value"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <el-button @click="closePost">取 消</el-button>
        <el-button type="primary" @click="submitPost">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import userSelect from '@/views/bus/application/conference/conferenceSettings/compontent/userSelect.vue'
import { workOrderAdminApi } from '@/api/bus/Category/UserSstting/index'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'

const message = useMessage() // 消息弹窗

const UserformRef = ref()
const applicationValue = ref()
const rowData = ref()
const Usersubmit = (res) => {
  workOrderAdminApi
    .create({
      application: applicationValue.value,
      uid: res.id,
      role: rowData.value.role
    })
    .then(() => {
      message.success('添加成功')
      emit('success')
    })
}
const submitPost = () => {
  workOrderAdminApi
    .create({
      application: applicationValue.value,
      postId: Station.value,
      role: rowData.value.role
    })
    .then(() => {
      message.success('添加成功')
      dialogVisible.value = false
      Station.value = ''
      emit('success')
    })
}
const closePost = () => {
  dialogVisible.value = false
  Station.value = ''
}
function openForm(res, application) {
  applicationValue.value = application
  rowData.value = res
  if (res.role == '1') {
    UserformRef.value.open(res, 'dan')
  } else if (res.role == '2') {
    dialogVisible.value = true
  }
}
////客服工作人员操作
const dialogVisible = ref(false)
///岗位下拉数据
const Station = ref([])
const stationOptions = ref([])
const GetstationOptions = async () => {
  const data = await patrolPlanEquipmentApi.getStationList()
  stationOptions.value = data
}
onMounted(() => {
  GetstationOptions()
})
defineExpose({ openForm }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['success']) // 定义 success 事件
</script>
