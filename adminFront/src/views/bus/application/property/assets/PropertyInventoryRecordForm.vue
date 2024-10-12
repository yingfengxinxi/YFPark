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
      <el-form-item label="盘点清单id" prop="inventoryId">
        <el-input v-model="formData.inventoryId" placeholder="请输入盘点清单id" />
      </el-form-item>
      <el-form-item label="资产id" prop="propertyId">
        <el-input v-model="formData.propertyId" placeholder="请输入资产id" />
      </el-form-item>
      <el-form-item label="资产分类id" prop="typeId">
        <el-input v-model="formData.typeId" placeholder="请输入资产分类id" />
      </el-form-item>
      <el-form-item label="0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="资产状态" prop="propertyStatus">
        <el-radio-group v-model="formData.propertyStatus">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否在盘点范围之内" prop="isRange">
        <el-input v-model="formData.isRange" placeholder="请输入是否在盘点范围之内" />
      </el-form-item>
      <el-form-item label="是否修改资产信息 0否 1是" prop="isUpdate">
        <el-date-picker
          v-model="formData.isUpdate"
          type="date"
          value-format="x"
          placeholder="选择是否修改资产信息 0否 1是"
        />
      </el-form-item>
      <el-form-item label="盘点备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入盘点备注" />
      </el-form-item>
      <el-form-item label="盘点图片" prop="image">
        <UploadImg v-model="formData.image" />
      </el-form-item>
      <el-form-item label="盘点标签" prop="tag">
        <el-input v-model="formData.tag" placeholder="请输入盘点标签" />
      </el-form-item>
      <el-form-item label="盘点时间" prop="inventoryTime">
        <el-date-picker
          v-model="formData.inventoryTime"
          type="date"
          value-format="x"
          placeholder="选择盘点时间"
        />
      </el-form-item>
      <el-form-item label="盘点人" prop="inventoryUid">
        <el-input v-model="formData.inventoryUid" placeholder="请输入盘点人" />
      </el-form-item>
      <el-form-item label="当前位置" prop="positionId">
        <el-input v-model="formData.positionId" placeholder="请输入当前位置" />
      </el-form-item>
      <el-form-item label="处理人" prop="handleUid">
        <el-input v-model="formData.handleUid" placeholder="请输入处理人" />
      </el-form-item>
      <el-form-item label="资产所属部门id" prop="departmentId">
        <el-input v-model="formData.departmentId" placeholder="请输入资产所属部门id" />
      </el-form-item>
      <el-form-item label="资产盘点信息" prop="propertyInfo">
        <el-input v-model="formData.propertyInfo" placeholder="请输入资产盘点信息" />
      </el-form-item>
      <el-form-item label="资产名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入资产名称" />
      </el-form-item>
      <el-form-item label="资产编码" prop="propertyNumber">
        <el-input v-model="formData.propertyNumber" placeholder="请输入资产编码" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { PropertyInventoryRecordApi, PropertyInventoryRecordVO } from '@/api/bus/property/propertyInventoryRecord'

/** 资产盘点记录 表单 */
defineOptions({ name: 'PropertyInventoryRecordForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  inventoryId: undefined,
  propertyId: undefined,
  typeId: undefined,
  status: undefined,
  propertyStatus: undefined,
  isRange: undefined,
  isUpdate: undefined,
  remark: undefined,
  image: undefined,
  tag: undefined,
  inventoryTime: undefined,
  inventoryUid: undefined,
  positionId: undefined,
  handleUid: undefined,
  departmentId: undefined,
  propertyInfo: undefined,
  name: undefined,
  propertyNumber: undefined,
})
const formRules = reactive({
  orgId: [{ required: true, message: '机构id不能为空', trigger: 'blur' }],
  isUpdate: [{ required: true, message: '是否修改资产信息 0否 1是不能为空', trigger: 'blur' }],
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
      formData.value = await PropertyInventoryRecordApi.getPropertyInventoryRecord(id)
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
    const data = formData.value as unknown as PropertyInventoryRecordVO
    if (formType.value === 'create') {
      await PropertyInventoryRecordApi.createPropertyInventoryRecord(data)
      message.success(t('common.createSuccess'))
    } else {
      await PropertyInventoryRecordApi.updatePropertyInventoryRecord(data)
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
    inventoryId: undefined,
    propertyId: undefined,
    typeId: undefined,
    status: undefined,
    propertyStatus: undefined,
    isRange: undefined,
    isUpdate: undefined,
    remark: undefined,
    image: undefined,
    tag: undefined,
    inventoryTime: undefined,
    inventoryUid: undefined,
    positionId: undefined,
    handleUid: undefined,
    departmentId: undefined,
    propertyInfo: undefined,
    name: undefined,
    propertyNumber: undefined,
  }
  formRef.value?.resetFields()
}
</script>
