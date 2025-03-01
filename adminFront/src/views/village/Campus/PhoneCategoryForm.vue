<template>
  <el-drawer v-model="dialogVisible" :title="dialogTitle" :append-to-body="true">
    <!-- <Dialog :title="dialogTitle" v-model="dialogVisible"> -->
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      label-position="top"
      v-loading="formLoading"
    >
      <el-form-item label="分类名称" prop="catName">
        <el-input v-model="formData.catName" placeholder="请输入分类名称" />
      </el-form-item>
      <el-form-item label="排序值（排序值越大，越靠前）" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序值" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.VILLAGEPHONESTATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
    </template>
    <!-- </Dialog> -->
  </el-drawer>
</template>
<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { PhoneCategoryApi, PhoneCategoryVO } from '@/api/bus/village/phone'

/** 项目电话类型 表单 */
defineOptions({ name: 'PhoneCategoryForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  villageId: undefined,
  catName: undefined,
  sort: undefined,
  status: undefined
})
const formRules = reactive({
  orgId: [{ required: true, message: '机构ID不能为空', trigger: 'blur' }],
  villageId: [{ required: true, message: '项目ID不能为空', trigger: 'blur' }],
  catName: [{ required: true, message: '分类名称不能为空', trigger: 'blur' }],
  sort: [
    { required: true, message: '排序值不能为空', trigger: 'blur' },
    { pattern: /^[0-9]*$/, message: '请输入整数', trigger: 'blur' }
  ],
  status: [{ required: true, message: '状态（1正常，0隐藏）不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number, village?: number) => {
  dialogVisible.value = true
  dialogTitle.value = type == 'create' ? '添加分类' : '修改分类'
  formType.value = type
  resetForm()
  formData.value.villageId = village
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await PhoneCategoryApi.getPhoneCategory(id)
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
    const data = formData.value as unknown as PhoneCategoryVO
    if (formType.value === 'create') {
      await PhoneCategoryApi.createPhoneCategory(data)
      message.success(t('common.createSuccess'))
    } else {
      await PhoneCategoryApi.updatePhoneCategory(data)
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
    catName: undefined,
    sort: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}
</script>
