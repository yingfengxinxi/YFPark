<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-dialog :title="title" width="600px" v-model="visible" @close="onClose">
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="80px"
      class="!w-80% m-auto m-t-20px"
    >
      <el-form-item label="名称" prop="name">
        <el-input placeholder="请填写名称" v-model="form.name" />
      </el-form-item>
      <el-form-item label="月份数" prop="month">
        <el-select placeholder="请选择月份" v-model="form.month">
          <el-option v-for="item in 12" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input-number
          :min="0"
          placeholder="最多保留两位小数"
          :precision="2"
          v-model="form.price"
          :step="1"
          controls-position="right"
          class="flex-1 m-r-10px"
        />元
      </el-form-item>
      <el-form-item label="说明" prop="info">
        <el-input placeholder="请输入套餐说明" v-model="form.info" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button size="small" @click="handleCancel">取 消</el-button>
        <el-button size="small" type="primary" @click="handleOk" :loading="confirmLoading"
          >确 定</el-button
        >
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
defineOptions({ name: 'SetMeal' })
const { t } = useI18n() // 国际化

const visible = ref(false)
const title = ref('')

const form = ref({
  name: undefined,
  month: undefined,
  price: undefined,
  info: undefined,
  status: 1
})

const rules = {
  name: [{ required: true, message: '请填写名称', trigger: 'blur' }],
  month: [{ required: true, message: '请选择月份数', trigger: 'change' }],
  price: [{ required: true, message: '请填写价格', trigger: 'blur' }]
}

const formType = ref('create')
const indexItem = ref(0)
const open = async (type: string, info?: any, index?: number) => {
  console.log(info, typeof index)
  visible.value = true
  resetForm()
  title.value = t('action.' + type)
  formType.value = type
  if (info) {
    indexItem.value = index
    form.value = info
  }
}

const onClose = () => {
  visible.value = false
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const formRef = ref()
const handleOk = async () => {
  await formRef.value?.validate()
  let data = JSON.parse(JSON.stringify(form.value))
  emit('success', formType.value, data, indexItem.value)
  onClose()
}
/** 重置表单 */
const resetForm = () => {
  form.value = {
    name: undefined,
    month: undefined,
    price: undefined,
    info: undefined,
    status: 1
  }
  formRef.value?.resetFields()
}
</script>
