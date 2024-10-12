<template>
  <div>
    <el-drawer v-model="DrawerVisible" :title="'变更执勤人'" size="50%">
      <el-row v-if="userList.length">
        <el-col :span="6">
          <div class="text-14px text-#333 mb-4px"> 需变更巡检人 </div>
          <el-select v-model="formData.oldUid" @change="changeoldUid">
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.nickname"
              :value="item.id"
            />
          </el-select>
        </el-col>
        <el-col :span="2">
          <div class="h-100% pos-relative">
            <Icon
              icon="ep:right"
              :size="20"
              class="pos-absolute top-50% left-50% transform-translate--50%"
            />
          </div>
        </el-col>
        <el-col :span="6" class="pos-relative">
          <div class="text-14px text-#333 mb-4px"> 新的执勤人 </div>
          <el-input placeholder="请选择新的执勤人" v-model="formData.newName" />
          <div class="pos-absolute w-100% h-100% top-0" @click="selectUser"> </div>
        </el-col>
      </el-row>
      <el-empty :image-size="80" description="本任务无执勤人员" v-else />
      <template #footer>
        <div class="flex justify-end w-100%" v-if="userList.length">
          <el-button>取消</el-button>
          <el-button type="primary" @click="submit">提交</el-button>
        </div>
      </template>
    </el-drawer>
    <userSelect ref="formRef" @submit="Usersubmit" />
  </div>
</template>
<script setup lang="ts">
import { Api, VO } from '@/api/bus/patrol/taskEquipment'
import userSelect from '@/views/bus/application/conference/conferenceSettings/compontent/userSelect.vue'
const message = useMessage() // 消息弹窗
const DrawerVisible = ref(false)
const emit = defineEmits(['success'])
function open(id) {
  DrawerVisible.value = true
  resetForm()
  formData.value.taskId = id
  if (id) {
    getDutyUserList(id)
  }
}
const userList = ref([])
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const getDutyUserList = async (id) => {
  const res = await Api.getDutyUserList(id)
  userList.value = res
  if (res.length) {
    formData.value.oldUid = res[0].id
    formData.value.oldName = res[0].nickname
  }
}
const formData = ref({
  taskId: '',
  oldUid: undefined, //旧的执勤人
  newUid: undefined, //新的执勤人
  oldName: undefined, //旧的执勤人姓名
  newName: undefined //新的执勤人姓名
})
//选择新的执勤人
const formRef = ref()
const selectUser = () => {
  // console.log('选择执勤人')
  formRef.value.open([], 'dan')
}
const Usersubmit = (res) => {
  formData.value.newUid = res.id
  formData.value.newName = res.nickname
}
const submit = async () => {
  if (!formData.value.newUid) {
    message.error('请选择新的执勤人')
    return
  }

  const res = await Api.saveTaskPost(formData.value)
  if (res) {
    emit('success')
    message.success('变更成功')
    DrawerVisible.value = false
  }
}
const resetForm = () => {
  ;(formData.value = {
    taskId: '',
    oldUid: undefined, //旧的执勤人
    newUid: undefined, //新的执勤人
    oldName: undefined, //旧的执勤人姓名
    newName: undefined //新的执勤人姓名
  }),
    (userList.value = [])
}
const changeoldUid = (val) => {
  const user = userList.value.find((item) => item.id === val)
  formData.value.oldName = user.nickname
}
</script>
