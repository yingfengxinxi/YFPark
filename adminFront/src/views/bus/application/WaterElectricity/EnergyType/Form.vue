<!-- /*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */ -->
<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="表单种类" prop="name">
        <el-input v-model="formData.name" placeholder="请输入表单种类" />
      </el-form-item>
      <el-form-item label="费用类型" prop="costType">
        <el-cascader
          v-model="formData.costType"
          ref="mycascader"
          :options="costTypeChildrenList"
          class="w-1/1"
          clearable
          filterable
          placeholder="请选择费用类型"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="equipType">
        <el-select v-model="formData.equipType" placeholder="请选择设备类型">
          <el-option
            v-for="(item, index) in getIntDictOptions('EQUIP_TYPE')"
            :key="index"
            :label="item.label"
            :value="item.value.toString()"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="计费单位" prop="unit">
        <el-input v-model="formData.unit" placeholder="请输入计费单位" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { energyType } from '@/api/bus/WaterElectricity/EnergyType/index'
import { getIntDictOptions } from '@/utils/dict'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'

/** 表种类管理 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  costType: undefined,
  equipType: undefined,
  unit: undefined
})
const formRules = reactive({
  name: [{ required: true, message: t('请输入表单名称'), trigger: 'blur' }],
  costType: [{ required: true, message: t('请选择费用类型'), trigger: 'change' }],
  equipType: [{ required: true, message: t('请选择设备类型'), trigger: 'change' }],
  unit: [{ required: true, message: t('请输入计费单位'), trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
//获取费用类型
const costTypeChildrenList = ref([])
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}
onMounted(() => {
  getCostTypeChildrenList()
})
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
      formData.value = await energyType.get(id)
      costTypeChildrenList.value.forEach((item) => {
        if (item.children) {
          item.children.forEach((childItem) => {
            if (childItem.value == formData.value.costType) {
              formData.value.costType = [item.value, childItem.value]
              return
            }
          })
        }
      })
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
    data.costType = data.costType[1]
    if (formType.value === 'create') {
      await energyType.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await energyType.update(data)
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
    costType: undefined,
    equipType: undefined,
    unit: undefined
  }
  formRef.value?.resetFields()
}
</script>
