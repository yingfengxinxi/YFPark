<template>
  <ElDrawer :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-form-item label="位置编码" prop="number">
        <el-input v-model="formData.number" placeholder="请输入位置编码" />
      </el-form-item>
      <el-form-item label="位置名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入位置名称" />
      </el-form-item>
      <el-form-item label="上级位置" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="localLocationList"
          :render-after-expand="false"
          :props="{ label: 'name', value: 'id', children: 'children' }"
          placeholder="请选择上级位置"
          clearable
          :check-strictly="true"
          @node-click="handleAreaNodeClick"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import {
  PropertyLocationApi,
  PropertyLocationVO
} from '@/api/bus/property/setting/propertyLocation'

/** 位置 表单 */
defineOptions({ name: 'PropertyLocationForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  number: undefined,
  name: undefined,
  level: undefined,
  parentId: undefined,
  param: undefined,
  remark: undefined,
  sort: undefined,
  status: 1
})
const formRules = reactive({
  number: [{ required: true, message: '位置编码不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '位置名称不能为空', trigger: 'blur' }],
  parentId: [{ required: true, message: '上级位置不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const prop = defineProps({
  locationList: {
    type: Object,
    default: function () {
      return []
    }
  }
})

const handleAreaNodeClick = (data) => {
  if (!data.level) {
    formData.value.level = 0
  } else {
    formData.value.level = String(Number(data.level) + 1)
  }
}
const localLocationList = ref()

watch(
  () => prop.locationList,
  (newValue) => {
    console.log(newValue, 'new')
    localLocationList.value = [...newValue]
    localLocationList.value.unshift({ id: 0, name: '无上级' })
  }
)

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
      formData.value = await PropertyLocationApi.getPropertyLocation(id)
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
  if (Number(formData.value.level) > 4) {
    message.error('位置层级不能超过5级')
    return
  }
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as PropertyLocationVO
    if (formType.value === 'create') {
      await PropertyLocationApi.createPropertyLocation(data)
      message.success(t('common.createSuccess'))
    } else {
      await PropertyLocationApi.updatePropertyLocation(data)
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
    number: undefined,
    name: undefined,
    level: undefined,
    parentId: undefined,
    param: undefined,
    remark: undefined,
    sort: undefined,
    status: 1
  }
  formRef.value?.resetFields()
}
</script>
