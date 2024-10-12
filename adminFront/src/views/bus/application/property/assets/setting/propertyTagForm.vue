<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="机构ID" prop="orgId">
        <el-input v-model="formData.orgId" placeholder="请输入机构ID" />
      </el-form-item>
      <el-form-item label="0=未应用;1=应用" prop="isApply">
        <el-input v-model="formData.isApply" placeholder="请输入0=未应用;1=应用" />
      </el-form-item>
      <el-form-item label="0=自定义模板;1=系统默认模板" prop="isDefault">
        <el-input v-model="formData.isDefault" placeholder="请输入0=自定义模板;1=系统默认模板" />
      </el-form-item>
      <el-form-item label="模板链接" prop="templatePath">
        <el-input v-model="formData.templatePath" placeholder="请输入模板链接" />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序" />
      </el-form-item>
      <el-form-item label="模板名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入模板名称" />
      </el-form-item>
      <el-form-item label="字段上限数量" prop="fieldLimit">
        <el-input v-model="formData.fieldLimit" placeholder="请输入字段上限数量" />
      </el-form-item>
      <el-form-item label="有无logo;0=无;1=有" prop="hasLogo">
        <el-input v-model="formData.hasLogo" placeholder="请输入有无logo;0=无;1=有" />
      </el-form-item>
      <el-form-item label="应用数据json" prop="applyJson">
        <el-input v-model="formData.applyJson" placeholder="请输入应用数据json" />
      </el-form-item>
      <el-form-item label="操作人uid" prop="cuserUid">
        <el-input v-model="formData.cuserUid" placeholder="请输入操作人uid" />
      </el-form-item>
      <el-form-item label="修改人uid" prop="muserUid">
        <el-input v-model="formData.muserUid" placeholder="请输入修改人uid" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { PropertyTagApi, PropertyTagVO } from '@/api/bus/property/setting/propertyTag'

/** 资产标签模板 表单 */
defineOptions({ name: 'PropertyTagForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  isApply: undefined,
  isDefault: undefined,
  templatePath: undefined,
  sort: undefined,
  name: undefined,
  fieldLimit: undefined,
  hasLogo: undefined,
  applyJson: undefined,
  cuserUid: undefined,
  muserUid: undefined,
})
const formRules = reactive({
  isApply: [{ required: true, message: '0=未应用;1=应用不能为空', trigger: 'blur' }],
  isDefault: [{ required: true, message: '0=自定义模板;1=系统默认模板不能为空', trigger: 'blur' }],
  fieldLimit: [{ required: true, message: '字段上限数量不能为空', trigger: 'blur' }],
  hasLogo: [{ required: true, message: '有无logo;0=无;1=有不能为空', trigger: 'blur' }],
  cuserUid: [{ required: true, message: '操作人uid不能为空', trigger: 'blur' }],
  muserUid: [{ required: true, message: '修改人uid不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await PropertyTagApi.getPropertyTag(id)
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
    const data = formData.value as unknown as PropertyTagVO
    if (formType.value === 'create') {
      await PropertyTagApi.createPropertyTag(data)
      message.success(t('common.createSuccess'))
    } else {
      await PropertyTagApi.updatePropertyTag(data)
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
    isApply: undefined,
    isDefault: undefined,
    templatePath: undefined,
    sort: undefined,
    name: undefined,
    fieldLimit: undefined,
    hasLogo: undefined,
    applyJson: undefined,
    cuserUid: undefined,
    muserUid: undefined,
  }
  formRef.value?.resetFields()
}
</script>
