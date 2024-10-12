<template>
  <el-drawer :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-form-item
        label="分类名称"
        prop="name"
        :rules="[{ required: true, message: '请输入分类名称', trigger: 'blur' }]"
      >
        <el-input v-model="formData.name" placeholder="请输入子类名称" />
      </el-form-item>
      <el-form-item
        label="排序值"
        prop="sort"
        :rules="[{ required: true, message: '请输入排序值', trigger: 'blur' }]"
      >
        <el-input v-model="formData.sort" type="number" :min="0" placeholder="请输入排序值" />
      </el-form-item>
      <el-form-item
        label="报单人"
        prop="submitType"
        :rules="[{ required: true, message: '请选择运作模式', trigger: 'blur' }]"
      >
        <el-select v-model="formData.submitType" placeholder="请选择运作模式">
          <el-option label="所有人" value="1" />
          <el-option label="租客" value="2" />
          <el-option label="工作人员" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item
        label="负责岗位"
        prop="stationJson"
        :rules="[{ required: true, message: '请选择负责岗位', trigger: 'blur' }]"
      >
        <el-select v-model="formData.stationJson" placeholder="请选择负责岗位" multiple clearable>
          <el-option
            v-for="item in stationOptions"
            :key="item.value"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="负责部门"
        prop="departmentId"
        :rules="[{ required: true, message: '请选择负责部门', trigger: 'blur' }]"
      >
        <el-tree-select
          v-model="formData.departmentId"
          :data="departmentOptions"
          :props="defaultProps"
          placeholder="请选择负责部门"
          clearable
          :check-strictly="true"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import { CategorySubcatApi, VO } from '@/api/bus/Category/CategorySubcat/index'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'

import { defaultProps, handleTree } from '@/utils/tree'
import { onMounted } from 'vue'

/** 工单分类子类信息 表单 */
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
  categoryId: undefined,
  stationJson: undefined,
  departmentId: undefined,
  sort: undefined,
  submitType: undefined
})
const application = ref('')
const formId = ref(0)
const formRef = ref() // 表单 Ref
const UserData = ref({})
const submitUser = (data) => {
  UserData.value = data
}
/** 打开弹窗 */
const open = async (type: string, applicationValue: string, Id: string | number, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  application.value = applicationValue
  formId.value = Id
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await CategorySubcatApi.get(id)
      formData.value.stationJson = formData.value.stationJson.split(',').map(Number)
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
    data.application = application.value
    data.categoryId = formId.value
    data.stationJson = data.stationJson.join(',')
    if (formType.value === 'create') {
      await CategorySubcatApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await CategorySubcatApi.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
///岗位下拉数据
const stationOptions = ref([])
const GetstationOptions = async () => {
  const data = await patrolPlanEquipmentApi.getStationList()
  stationOptions.value = data
}
///部门下拉数据
const departmentOptions = ref([])
const GetdepartmentOptions = async () => {
  const data = await patrolPlanEquipmentApi.getDeptList()
  departmentOptions.value.push(...handleTree(data))
}
onMounted(() => {
  GetstationOptions()
  GetdepartmentOptions()
})
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    orgId: undefined,
    application: undefined,
    name: undefined,
    categoryId: undefined,
    stationJson: undefined,
    departmentId: undefined,
    labelIds: undefined,
    sort: undefined,
    submitType: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}
</script>
