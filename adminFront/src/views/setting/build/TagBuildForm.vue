<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
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
      <el-form-item label="标签描述">
        <el-input v-model="formData.descVillage" placeholder="请输入标签描述" />
      </el-form-item>
      <el-form-item label="标签样式">
        <el-row class="w-100%">
          <el-col :span="4">背景色：</el-col>
          <el-col :span="7">
            <el-color-picker v-model="formData.color.bgColor" size="large" />
          </el-col>
          <el-col :span="4">边框色：</el-col>
          <el-col :span="7">
            <el-color-picker v-model="formData.color.borderColor" size="large" />
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getStrDictOptions(DICT_TYPE.SETTING_TAG_STATUS)"
            :key="dict.value"
            :label="dict.value"
            >{{ dict.label }}</el-radio
          >
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { TagBuildApi, TagBuildVO } from '@/api/bus/tag/build/index'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'

/** 楼宇标签 表单 */
defineOptions({ name: 'TagBuildForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  descVillage: undefined,
  status: undefined,
  userShow: 1,
  color: {
    bgColor: undefined,
    borderColor: undefined
  }
})
const formRules = reactive({
  name: [{ required: true, message: '标签名称不能为空', trigger: 'blur' }],
  descVillage: [{ required: true, message: '标签描述不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
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
      const data = await TagBuildApi.getTagBuild(id)
      formData.value = data
      formData.value.color = JSON.parse(data.color)
      formData.value.status = String(data.status)
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
    const data = formData.value as unknown as TagBuildVO

    data.color = JSON.stringify(data.color)
    if (formType.value === 'create') {
      console.log(data, 'data')
      await TagBuildApi.createTagBuild(data)
      message.success(t('common.createSuccess'))
    } else {
      await TagBuildApi.updateTagBuild(data)
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
    name: undefined,
    descVillage: undefined,
    status: undefined,
    userShow: 1,
    color: {
      bgColor: undefined,
      borderColor: undefined
    }
  }
  formRef.value?.resetFields()
}
</script>
