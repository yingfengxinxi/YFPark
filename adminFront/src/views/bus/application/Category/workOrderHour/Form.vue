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
        label="工时名称"
        prop="name"
        :rules="[{ required: true, message: '请输入工时名称', trigger: 'blur' }]"
      >
        <el-input v-model="formData.name" placeholder="请输入工时名称" />
      </el-form-item>
      <el-form-item
        label="一个工时费用"
        prop="hourFee"
        :rules="[{ required: true, message: '请输入一个工时费用', trigger: 'blur' }]"
      >
        <el-input v-model="formData.hourFee" type="number" :min="0" placeholder="请输入工时费用">
          <template #append>元</template>
        </el-input>
      </el-form-item>
      <el-form-item
        label="工时计算规则"
        prop="computeRule"
        :rules="[{ required: true, message: '请选择工时计算规则', trigger: 'blur' }]"
      >
        <el-select v-model="formData.computeRule">
          <el-option
            v-for="(item, index) in getIntDictOptions('COMPUTE_RULE')"
            :key="index"
            :label="item.label"
            :value="item.value.toString()"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="对应部门"
        prop="departmentId"
        :rules="[{ required: true, message: '请选择对应部门', trigger: 'blur' }]"
        v-if="formData.isDefault == '0'"
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
      <el-form-item
        label="对应岗位"
        prop="stationId"
        :rules="[{ required: true, message: '请选择对应岗位', trigger: 'blur' }]"
        v-if="formData.isDefault == '0'"
      >
        <el-select v-model="formData.stationId" placeholder="请选择负责岗位" multiple clearable>
          <el-option
            v-for="item in stationOptions"
            :key="item.value"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import { workOrderHourApi, VO } from '@/api/bus/Category/workOrderHour/index'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'
import { onMounted } from 'vue'
import { defaultProps, handleTree } from '@/utils/tree'

/** 工单工时配置 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  application: undefined,
  name: undefined,
  hourFee: undefined,
  computeRule: undefined,
  departmentId: undefined,
  stationId: undefined,
  status: '1',
  isDefault: 0
})

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const applicationValue = ref('')
const open = async (type: string, application, id?: number) => {
  dialogVisible.value = true
  applicationValue.value = application
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await workOrderHourApi.get(id)
      formData.value.stationId = formData.value.stationId.split(',').map(Number)
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
    data.stationId = data.stationId.join(',')
    data.application = applicationValue.value

    if (formType.value === 'create') {
      await workOrderHourApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await workOrderHourApi.update(data)
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
    application: undefined,
    name: undefined,
    hourFee: undefined,
    computeRule: undefined,
    departmentId: undefined,
    stationId: undefined,
    status: '1',
    isDefault: 0
  }
  formRef.value?.resetFields()
}
</script>
