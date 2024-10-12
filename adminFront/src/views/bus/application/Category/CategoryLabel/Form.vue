<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="标签名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入标签名称" />
      </el-form-item>
      <el-form-item label="排序值" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序值" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { categoryLabelApi, VO } from '@/api/bus/Category/CategoryLabel/index'

/** 工单分类标签配置 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  application: undefined,
  name: undefined,
  subcatId: undefined,
  sort: undefined
})
const formRules = reactive({
  sort: [{ required: true, message: '排序值不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '标签名称不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const formIdValue = ref('') // 子类工单
const applicationValue = ref('') //应用标识
/** 打开弹窗 */
const open = async (type: string, formId: string, application: string, id?: number) => {
  formIdValue.value = formId
  applicationValue.value = application
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await categoryLabelApi.get(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as VO
    data.subcatId = formIdValue.value
    data.application = applicationValue.value
    if (formType.value === 'create') {
      await categoryLabelApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await categoryLabelApi.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    orgId: undefined,
    application: undefined,
    name: undefined,
    subcatId: undefined,
    sort: undefined,
    lastTime: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}
</script>
