<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 附件账单上传 -->
  <div>
    <el-drawer
      v-model="drawer_show"
      append-to-body
      :with-header="true"
      size="700px"
      :title="drawer_title"
      @closed="closed"
    >
      <el-form
        labelWidth="100%"
        label-position="top"
        ref="formRef"
        :rules="formRules"
        :model="form"
        v-loading="formLoading"
      >
        <el-row :gutter="21">
          <el-col :span="24">
            <el-form-item label="备注内容" prop="remark">
              <el-input
                v-model="form.remark"
                type="textarea"
                placeholder="请输入备注内容"
                rows="4"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="closed">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </template>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
defineOptions({ name: 'AddBillAnnexForm' })
import { orgRemarkApi } from '@/api/bus/orgRemark'
const message = useMessage() // 消息弹窗
let drawer_show = ref(false)
const drawer_title = ref('')
const drawer_type = ref(undefined)
const businessId = ref(undefined)
const type = ref(undefined)
const form = ref({
  id: undefined,
  businessId: undefined,
  remark: undefined
})
const formRules = reactive({
  remark: [{ required: true, message: '备注内容不能为空', trigger: 'blur' }]
})
/** 打开抽屉 */
const open = async (Viewtype?: any, id?: any, RemarkId?: number, typeNum?: any) => {
  businessId.value = id
  type.value = typeNum
  drawer_type.value = Viewtype
  resetForm()
  drawer_show.value = true
  if (Viewtype == 'update') {
    form.value.businessId = RemarkId
    getOrgRemark()
    drawer_title.value = '修改备注'
  } else {
    drawer_title.value = '添加备注'
  }
}

const getOrgRemark = async () => {
  const data = await orgRemarkApi.getOrgRemark({ id: form.value.businessId })
  form.value = data
}
const closed = async () => {
  drawer_show.value = false
}
const emit = defineEmits(['success'])
const formRef = ref()
const formLoading = ref(false)
const submit = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(form.value))
    data.businessId = businessId.value
    data.type = type.value
    if (drawer_type.value == 'create') {
      await orgRemarkApi.create(data)
      message.success('添加成功')
      drawer_show.value = false
    } else {
      await orgRemarkApi.update(data)
      message.success('修改成功')
      drawer_show.value = false
    }
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  form.value = {
    id: undefined,
    businessId: undefined,
    remark: undefined
  }
  formRef.value?.resetFields()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped></style>
<style>
.el-drawer__header {
  margin-bottom: 20px;
}
</style>
