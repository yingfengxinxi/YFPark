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
      <el-form-item label="仓库编码" prop="number">
        <el-input v-model="formData.number" placeholder="请输入仓库编号" />
      </el-form-item>
      <el-form-item label="仓库名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入仓库名称" />
      </el-form-item>
      <el-form-item label="上级仓库" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="depostoryList"
          :render-after-expand="false"
          :props="{ label: 'name', value: 'id', children: 'children' }"
          placeholder="请选择上级仓库"
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
import { PropertyStuffDepositoryApi, PropertyStuffDepositoryVO } from '@/api/bus/stuff/depository'
import { ElDrawer } from 'element-plus'

/** 耗材档案信息 表单 */
defineOptions({ name: 'PropertyStuffDepositoryForm' })

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
  parentId: undefined,
  status: undefined,
  level: undefined,
  remark: undefined,
  sort: undefined,
  cuserUid: undefined,
  muserUid: undefined
})
const formRules = reactive({
  number: [{ required: true, message: '仓库编码不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '仓库名称不能为空', trigger: 'blur' }],
  parentId: [{ required: true, message: '上级仓库不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const prop = defineProps({
  loadList: {
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
const depostoryList = ref()

watch(
  () => prop.loadList,
  (newValue) => {
    console.log(newValue, 'new')
    depostoryList.value = [...newValue]
    depostoryList.value.unshift({ id: 0, name: '无上级' })
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
      formData.value = await PropertyStuffDepositoryApi.getPropertyStuffDepository(id)
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
    const data = formData.value as unknown as PropertyStuffDepositoryVO
    if (formType.value === 'create') {
      data.status = 1
      await PropertyStuffDepositoryApi.createPropertyStuffDepository(data)
      message.success(t('common.createSuccess'))
    } else {
      await PropertyStuffDepositoryApi.updatePropertyStuffDepository(data)
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
    parentId: undefined,
    status: undefined,
    level: undefined,
    remark: undefined,
    sort: undefined,
    cuserUid: undefined,
    muserUid: undefined
  }
  formRef.value?.resetFields()
}
</script>
