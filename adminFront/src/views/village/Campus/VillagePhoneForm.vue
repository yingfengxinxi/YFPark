<template>
  <el-drawer v-model="dialogVisible" :title="dialogTitle" :append-to-body="true">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      label-position="top"
      v-loading="formLoading"
    >
      <el-form-item label="号码名称" prop="phoneName">
        <el-input v-model="formData.phoneName" placeholder="请输入号码名称" />
      </el-form-item>
      <el-form-item label="号码" prop="phone">
        <el-input v-model="formData.phone" placeholder="请输入号码" />
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
      <el-form-item label="物业统一电话" prop="urgent">
        <template #lable>
          物业统一电话
          <el-tooltip content="页面会在页面底部展现大的按钮" placement="bottom">
            <Icon class="" icon="fa:question-circle-o" />
          </el-tooltip>
        </template>
        <el-switch
          v-model="formData.urgent"
          inline-prompt
          active-text="开"
          active-value="1"
          inactive-value="0"
          inactive-text="关"
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
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { VillagePhoneApi, VillagePhoneVO } from '@/api/bus/village/phone'

/** 项目电话 表单 */
defineOptions({ name: 'VillagePhoneForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  villageId: undefined,
  catId: undefined,
  phoneName: undefined,
  phone: undefined,
  sort: undefined,
  status: undefined,
  urgent: undefined
})
const formRules = reactive({
  orgId: [{ required: true, message: '机构ID不能为空', trigger: 'blur' }],
  villageId: [{ required: true, message: '项目ID不能为空', trigger: 'blur' }],
  catId: [{ required: true, message: '分类ID不能为空', trigger: 'blur' }],
  phoneName: [{ required: true, message: '号码名称不能为空', trigger: 'blur' }],
  phone: [{ required: true, message: '号码不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '排序值不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态（1正常，0隐藏）不能为空', trigger: 'blur' }],
  urgent: [{ required: true, message: '紧急电话（1是，0否）不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number, catId?: number, village?: number) => {
  dialogVisible.value = true
  dialogTitle.value = type == 'create' ? '添加手机号' : '修改手机号'
  formType.value = type
  resetForm()
  formData.value.villageId = village
  formData.value.catId = catId
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await VillagePhoneApi.getVillagePhone(id)
      formData.value.urgent = JSON.stringify(formData.value.urgent)
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
    const data = formData.value as unknown as VillagePhoneVO
    if (formType.value === 'create') {
      await VillagePhoneApi.createVillagePhone(data)
      message.success(t('common.createSuccess'))
    } else {
      await VillagePhoneApi.updateVillagePhone(data)
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
    catId: undefined,
    phoneName: undefined,
    phone: undefined,
    sort: undefined,
    status: undefined,
    urgent: undefined
  }
  formRef.value?.resetFields()
}
</script>
