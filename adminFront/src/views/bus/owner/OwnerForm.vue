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
      <el-form-item label="租客名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入租客名称" />
      </el-form-item>
      <el-form-item label="是否个人，1个人，0公司" prop="isPersonal">
        <el-input v-model="formData.isPersonal" placeholder="请输入是否个人，1个人，0公司" />
      </el-form-item>
      <el-form-item label="是否推荐;0=不推荐;1=推荐;" prop="isSuggest">
        <el-input v-model="formData.isSuggest" placeholder="请输入是否推荐;0=不推荐;1=推荐;" />
      </el-form-item>
      <el-form-item label="目标对象;0=正常租客;1=收款对象;2=付款对象;" prop="type">
        <el-select v-model="formData.type" placeholder="请选择目标对象;0=正常租客;1=收款对象;2=付款对象;">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="虚拟身份注册;0=无;1=虚拟个人注册;2=虚拟企业注册;" prop="sham">
        <el-input v-model="formData.sham" placeholder="请输入虚拟身份注册;0=无;1=虚拟个人注册;2=虚拟企业注册;" />
      </el-form-item>
      <el-form-item label="联系人" prop="contactId">
        <el-input v-model="formData.contactId" placeholder="请输入联系人" />
      </el-form-item>
      <el-form-item label="租客合同签署人id" prop="contactSignId">
        <el-input v-model="formData.contactSignId" placeholder="请输入租客合同签署人id" />
      </el-form-item>
      <el-form-item label="缴费通知单联系人" prop="contactNoticeId">
        <el-input v-model="formData.contactNoticeId" placeholder="请输入缴费通知单联系人" />
      </el-form-item>
      <el-form-item label="审批联系人id" prop="approvalContactId">
        <el-input v-model="formData.approvalContactId" placeholder="请输入审批联系人id" />
      </el-form-item>
      <el-form-item label="证件号码" prop="certificateNumber">
        <el-input v-model="formData.certificateNumber" placeholder="请输入证件号码" />
      </el-form-item>
      <el-form-item label="行业分类id" prop="industryId">
        <el-input v-model="formData.industryId" placeholder="请输入行业分类id" />
      </el-form-item>
      <el-form-item label="租客绑定的项目id" prop="villageId">
        <el-input v-model="formData.villageId" placeholder="请输入租客绑定的项目id" />
      </el-form-item>
      <el-form-item label="租客绑定的项目楼宇房源信息" prop="buildBind">
        <el-input v-model="formData.buildBind" placeholder="请输入租客绑定的项目楼宇房源信息" />
      </el-form-item>
      <el-form-item label="联系方式" prop="tel">
        <el-input v-model="formData.tel" placeholder="请输入联系方式" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="formData.email" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="租客编码" prop="tenantNo">
        <el-input v-model="formData.tenantNo" placeholder="请输入租客编码" />
      </el-form-item>
      <el-form-item label="官网地址" prop="websiteLink">
        <el-input v-model="formData.websiteLink" placeholder="请输入官网地址" />
      </el-form-item>
      <el-form-item label="企业logo" prop="logo">
        <el-input v-model="formData.logo" placeholder="请输入企业logo" />
      </el-form-item>
      <el-form-item label="企业简介" prop="companyDesc">
        <el-input v-model="formData.companyDesc" placeholder="请输入企业简介" />
      </el-form-item>
      <el-form-item label="成立日期" prop="businessInfoFoundingTime">
        <el-date-picker
          v-model="formData.businessInfoFoundingTime"
          type="date"
          value-format="x"
          placeholder="选择成立日期"
        />
      </el-form-item>
      <el-form-item label="营业期限" prop="businessInfoBusinessTerm">
        <el-input v-model="formData.businessInfoBusinessTerm" placeholder="请输入营业期限" />
      </el-form-item>
      <el-form-item label="注册资金" prop="registeredCapital">
        <el-input v-model="formData.registeredCapital" placeholder="请输入注册资金" />
      </el-form-item>
      <el-form-item label="开票信息" prop="invoiceInfo">
        <el-input v-model="formData.invoiceInfo" placeholder="请输入开票信息" />
      </el-form-item>
      <el-form-item label="工商信息" prop="businessInfo">
        <el-input v-model="formData.businessInfo" placeholder="请输入工商信息" />
      </el-form-item>
      <el-form-item label="租客标签" prop="tagInfo">
        <el-input v-model="formData.tagInfo" placeholder="请输入租客标签" />
      </el-form-item>
      <el-form-item label="自定义字段" prop="diyField">
        <el-input v-model="formData.diyField" placeholder="请输入自定义字段" />
      </el-form-item>
      <el-form-item label="是否开启公司代付通知" prop="isAdvanceNotice">
        <el-input v-model="formData.isAdvanceNotice" placeholder="请输入是否开启公司代付通知" />
      </el-form-item>
      <el-form-item label="是否归档" prop="isArchive">
        <el-input v-model="formData.isArchive" placeholder="请输入是否归档" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OwnerApi, OwnerVO } from '@/api/bus/owner'

/** 租客信息 表单 */
defineOptions({ name: 'OwnerForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  name: undefined,
  isPersonal: undefined,
  isSuggest: undefined,
  type: undefined,
  sham: undefined,
  contactId: undefined,
  contactSignId: undefined,
  contactNoticeId: undefined,
  approvalContactId: undefined,
  certificateNumber: undefined,
  industryId: undefined,
  villageId: undefined,
  buildBind: undefined,
  tel: undefined,
  email: undefined,
  tenantNo: undefined,
  websiteLink: undefined,
  logo: undefined,
  companyDesc: undefined,
  businessInfoFoundingTime: undefined,
  businessInfoBusinessTerm: undefined,
  registeredCapital: undefined,
  invoiceInfo: undefined,
  businessInfo: undefined,
  tagInfo: undefined,
  diyField: undefined,
  isAdvanceNotice: undefined,
  isArchive: undefined,
})
const formRules = reactive({
  isPersonal: [{ required: true, message: '是否个人，1个人，0公司不能为空', trigger: 'blur' }],
  isSuggest: [{ required: true, message: '是否推荐;0=不推荐;1=推荐;不能为空', trigger: 'blur' }],
  type: [{ required: true, message: '目标对象;0=正常租客;1=收款对象;2=付款对象;不能为空', trigger: 'change' }],
  sham: [{ required: true, message: '虚拟身份注册;0=无;1=虚拟个人注册;2=虚拟企业注册;不能为空', trigger: 'blur' }],
  approvalContactId: [{ required: true, message: '审批联系人id不能为空', trigger: 'blur' }],
  isAdvanceNotice: [{ required: true, message: '是否开启公司代付通知不能为空', trigger: 'blur' }],
  isArchive: [{ required: true, message: '是否归档不能为空', trigger: 'blur' }],
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
      formData.value = await OwnerApi.getOwner(id)
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
    const data = formData.value as unknown as OwnerVO
    if (formType.value === 'create') {
      await OwnerApi.createOwner(data)
      message.success(t('common.createSuccess'))
    } else {
      await OwnerApi.updateOwner(data)
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
    name: undefined,
    isPersonal: undefined,
    isSuggest: undefined,
    type: undefined,
    sham: undefined,
    contactId: undefined,
    contactSignId: undefined,
    contactNoticeId: undefined,
    approvalContactId: undefined,
    certificateNumber: undefined,
    industryId: undefined,
    villageId: undefined,
    buildBind: undefined,
    tel: undefined,
    email: undefined,
    tenantNo: undefined,
    websiteLink: undefined,
    logo: undefined,
    companyDesc: undefined,
    businessInfoFoundingTime: undefined,
    businessInfoBusinessTerm: undefined,
    registeredCapital: undefined,
    invoiceInfo: undefined,
    businessInfo: undefined,
    tagInfo: undefined,
    diyField: undefined,
    isAdvanceNotice: undefined,
    isArchive: undefined,
  }
  formRef.value?.resetFields()
}
</script>
