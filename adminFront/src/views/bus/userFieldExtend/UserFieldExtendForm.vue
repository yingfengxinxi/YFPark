<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="机构id" prop="orgId">
        <el-input v-model="formData.orgId" placeholder="请输入机构id" />
      </el-form-item>
      <el-form-item label="项目id" prop="villageId">
        <el-input v-model="formData.villageId" placeholder="请输入项目id" />
      </el-form-item>
      <el-form-item label="关联 village_user_system_field_extend id" prop="sysFieldId">
        <el-input v-model="formData.sysFieldId" placeholder="请输入关联 village_user_system_field_extend id" />
      </el-form-item>
      <el-form-item label="字段名称" prop="fieldName">
        <el-input v-model="formData.fieldName" placeholder="请输入字段名称" />
      </el-form-item>
      <el-form-item label="表单名称" prop="inputName">
        <el-input v-model="formData.inputName" placeholder="请输入表单名称" />
      </el-form-item>
      <el-form-item label="表单名称自定义" prop="inputNameCus">
        <el-input v-model="formData.inputNameCus" placeholder="请输入表单名称自定义" />
      </el-form-item>
      <el-form-item label="输入框类型" prop="inputType">
        <el-select v-model="formData.inputType" placeholder="请选择输入框类型">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="表单默认值 " prop="defaultInputValue">
        <el-input v-model="formData.defaultInputValue" placeholder="请输入表单默认值 " />
      </el-form-item>
      <el-form-item label="表单默认值 " prop="defaultFieldValue">
        <el-input v-model="formData.defaultFieldValue" placeholder="请输入表单默认值 " />
      </el-form-item>
      <el-form-item label="是否启用，1-启用,0-禁用" prop="isEnable">
        <el-input v-model="formData.isEnable" placeholder="请输入是否启用，1-启用,0-禁用" />
      </el-form-item>
      <el-form-item label="是否必填 1-必填" prop="isRequired">
        <el-input v-model="formData.isRequired" placeholder="请输入是否必填 1-必填" />
      </el-form-item>
      <el-form-item label="是否允许修改表单名称，1允许，0不允许" prop="isAllowModifie">
        <el-input v-model="formData.isAllowModifie" placeholder="请输入是否允许修改表单名称，1允许，0不允许" />
      </el-form-item>
      <el-form-item label="JSON 字段集合" prop="jsonFields">
        <el-input v-model="formData.jsonFields" placeholder="请输入JSON 字段集合" />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { UserFieldExtendApi, UserFieldExtendVO } from '@/api/bus/userFieldExtend'

/** 用户扩展信息自定义系统设置 表单 */
defineOptions({ name: 'UserFieldExtendForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  villageId: undefined,
  sysFieldId: undefined,
  fieldName: undefined,
  inputName: undefined,
  inputNameCus: undefined,
  inputType: undefined,
  defaultInputValue: undefined,
  defaultFieldValue: undefined,
  isEnable: undefined,
  isRequired: undefined,
  isAllowModifie: undefined,
  jsonFields: undefined,
  sort: undefined,
})
const formRules = reactive({
  orgId: [{ required: true, message: '机构id不能为空', trigger: 'blur' }],
  villageId: [{ required: true, message: '项目id不能为空', trigger: 'blur' }],
  sysFieldId: [{ required: true, message: '关联 village_user_system_field_extend id不能为空', trigger: 'blur' }],
  fieldName: [{ required: true, message: '字段名称不能为空', trigger: 'blur' }],
  inputName: [{ required: true, message: '表单名称不能为空', trigger: 'blur' }],
  inputNameCus: [{ required: true, message: '表单名称自定义不能为空', trigger: 'blur' }],
  inputType: [{ required: true, message: '输入框类型不能为空', trigger: 'change' }],
  isEnable: [{ required: true, message: '是否启用，1-启用,0-禁用不能为空', trigger: 'blur' }],
  isRequired: [{ required: true, message: '是否必填 1-必填不能为空', trigger: 'blur' }],
  isAllowModifie: [{ required: true, message: '是否允许修改表单名称，1允许，0不允许不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '排序不能为空', trigger: 'blur' }],
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
      formData.value = await UserFieldExtendApi.getUserFieldExtend(id)
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
    const data = formData.value as unknown as UserFieldExtendVO
    if (formType.value === 'create') {
      await UserFieldExtendApi.createUserFieldExtend(data)
      message.success(t('common.createSuccess'))
    } else {
      await UserFieldExtendApi.updateUserFieldExtend(data)
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
    villageId: undefined,
    sysFieldId: undefined,
    fieldName: undefined,
    inputName: undefined,
    inputNameCus: undefined,
    inputType: undefined,
    defaultInputValue: undefined,
    defaultFieldValue: undefined,
    isEnable: undefined,
    isRequired: undefined,
    isAllowModifie: undefined,
    jsonFields: undefined,
    sort: undefined,
  }
  formRef.value?.resetFields()
}
</script>
