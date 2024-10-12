<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 附件账单上传 -->
  <div>
    <el-drawer
      v-model="drawer_show"
      append-to-body
      :with-header="true"
      size="700px"
      title="上传文件"
      @closed="closed"
    >
      <el-form
        labelWidth="100%"
        label-position="top"
        ref="formRef"
        :rules="formRules"
        :model="form"
        v-loading="formLoading"
      >
        <el-row :gutter="21">
          <el-col :span="24">
            <el-form-item label="文件名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="上传附件" prop="">
              <UploadFile
                v-model="form.filePath"
                :file-type="['doc', 'docx', 'pdf', 'jpg', 'jpeg', 'png']"
                :limit="1"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="closed">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </template>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
defineOptions({ name: 'AddBillAnnexForm' })
import { orgBillAnnexApi } from '@/api/bus/orgBillAnnex'
const message = useMessage() // 消息弹窗
let drawer_show = ref(false)
const sourceId = ref(undefined)
const type = ref(undefined)
const form = ref({
  name: undefined,
  filePath: ''
})
const formRules = reactive({
  name: [{ required: true, message: '文件名称不能为空', trigger: 'blur' }],
  filePath: [{ required: true, message: '附件不能为空', trigger: 'blur' }]
})
/** 打开抽屉 */
const open = async (id?: any, typeNum?: any) => {
  sourceId.value = id
  type.value = typeNum
  resetForm()
  drawer_show.value = true
}
const closed = async () => {
  drawer_show.value = false
}
const emit = defineEmits(['success'])
const formRef = ref()
const formLoading = ref(false)
const submit = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(form.value))
    data.sourceId = sourceId.value
    data.type = type.value
    data.annexSource = 1
    await orgBillAnnexApi.create(data)
    drawer_show.value = false
    message.success('添加成功')
    formLoading.value = false
    emit('success', data)
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  form.value = {
    name: undefined,
    filePath: ''
  }
  formRef.value?.resetFields()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped></style>
<style>
.el-drawer__header {
  margin-bottom: 20px;
}
</style>
