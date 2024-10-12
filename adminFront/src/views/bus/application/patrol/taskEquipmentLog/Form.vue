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
      <el-form-item label="应用标识" prop="application">
        <el-input v-model="formData.application" placeholder="请输入应用标识" />
      </el-form-item>
      <el-form-item label="计划id" prop="planId">
        <el-input v-model="formData.planId" placeholder="请输入计划id" />
      </el-form-item>
      <el-form-item label="计划key" prop="planKey">
        <el-input v-model="formData.planKey" placeholder="请输入计划key" />
      </el-form-item>
      <el-form-item label="周期" prop="crontab">
        <el-input v-model="formData.crontab" placeholder="请输入周期" />
      </el-form-item>
      <el-form-item label="规则" prop="rule">
        <el-input v-model="formData.rule" placeholder="请输入规则" />
      </el-form-item>
      <el-form-item label="生成时间" prop="buildTime">
        <el-date-picker
          v-model="formData.buildTime"
          type="date"
          value-format="x"
          placeholder="选择生成时间"
        />
      </el-form-item>
      <el-form-item label="任务执行时间" prop="taskTime">
        <el-date-picker
          v-model="formData.taskTime"
          type="date"
          value-format="x"
          placeholder="选择任务执行时间"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="执行顺序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入执行顺序" />
      </el-form-item>
      <el-form-item label="累计执行" prop="total">
        <el-input v-model="formData.total" placeholder="请输入累计执行" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { Api, VO } from '@/api/bus/patrol/taskEquipmentLog'

/** 应用巡检任务日志 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  application: undefined,
  planId: undefined,
  planKey: undefined,
  crontab: undefined,
  rule: undefined,
  buildTime: undefined,
  taskTime: undefined,
  status: undefined,
  sort: undefined,
  total: undefined
})
const formRules = reactive({
  planId: [{ required: true, message: '计划id不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
  total: [{ required: true, message: '累计执行不能为空', trigger: 'blur' }]
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
      formData.value = await Api.get(id)
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
    if (formType.value === 'create') {
      await Api.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await Api.update(data)
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
    planId: undefined,
    planKey: undefined,
    crontab: undefined,
    rule: undefined,
    buildTime: undefined,
    taskTime: undefined,
    status: undefined,
    sort: undefined,
    total: undefined
  }
  formRef.value?.resetFields()
}
</script>
