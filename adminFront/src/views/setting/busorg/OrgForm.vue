<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="机构缩写名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入机构缩写名称" />
      </el-form-item>
      <el-form-item label="管理员用户id" prop="adminUid">
        <el-input v-model="formData.adminUid" placeholder="请输入管理员用户id" />
      </el-form-item>
      <el-form-item label="机构全名称" prop="company">
        <el-input v-model="formData.company" placeholder="请输入机构全名称" />
      </el-form-item>
      <el-form-item label="省份id" prop="provinceId">
        <el-input v-model="formData.provinceId" placeholder="请输入省份id" />
      </el-form-item>
      <el-form-item label="市id" prop="cityId">
        <el-input v-model="formData.cityId" placeholder="请输入市id" />
      </el-form-item>
      <el-form-item label="省市地址;英文逗号,拼接" prop="districtName">
        <el-input v-model="formData.districtName" placeholder="请输入省市地址;英文逗号,拼接" />
      </el-form-item>
      <el-form-item label="机构介绍" prop="info">
        <el-input v-model="formData.info" placeholder="请输入机构介绍" />
      </el-form-item>
      <el-form-item label="机构logo" prop="logo">
        <el-input v-model="formData.logo" placeholder="请输入机构logo" />
      </el-form-item>
      <el-form-item label="正方形logo（建议透明的白色）" prop="squareLogo">
        <el-input v-model="formData.squareLogo" placeholder="请输入正方形logo（建议透明的白色）" />
      </el-form-item>
      <el-form-item label="机构总人数" prop="total">
        <el-input v-model="formData.total" placeholder="请输入机构总人数" />
      </el-form-item>
      <el-form-item label="联系电话" prop="tel">
        <el-input v-model="formData.tel" placeholder="请输入联系电话" />
      </el-form-item>
      <el-form-item label="联系地址" prop="address">
        <el-input v-model="formData.address" placeholder="请输入联系地址" />
      </el-form-item>
      <el-form-item label="机构类别" prop="type">
        <el-select v-model="formData.type" placeholder="请选择机构类别">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item
        label="机构专属域名前缀（若不填写则访问专属域名时展示平台信息，例如 admin-id.xxx.com）"
        prop="domainPrefix"
      >
        <el-input
          v-model="formData.domainPrefix"
          placeholder="请输入机构专属域名前缀（若不填写则访问专属域名时展示平台信息，例如 admin-id.xxx.com）"
        />
      </el-form-item>
      <el-form-item label="是否为OEM" prop="isOem">
        <el-input v-model="formData.isOem" placeholder="请输入是否为OEM" />
      </el-form-item>
      <el-form-item label="公众号的微信号" prop="wechatNumber">
        <el-input v-model="formData.wechatNumber" placeholder="请输入公众号的微信号" />
      </el-form-item>
      <el-form-item label="机构成员工号前缀(支持自定义)" prop="orgPrefix">
        <el-input v-model="formData.orgPrefix" placeholder="请输入机构成员工号前缀(支持自定义)" />
      </el-form-item>
      <el-form-item label="机构码(8位随机字符串)" prop="joinCode">
        <el-input v-model="formData.joinCode" placeholder="请输入机构码(8位随机字符串)" />
      </el-form-item>
      <el-form-item label="机构状态，1启动，0审核中，4禁用" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="字典（替换页面上的字）" prop="dictionary">
        <el-input v-model="formData.dictionary" placeholder="请输入字典（替换页面上的字）" />
      </el-form-item>
      <el-form-item label="支持开放的模块权限" prop="module">
        <el-input v-model="formData.module" placeholder="请输入支持开放的模块权限" />
      </el-form-item>
      <el-form-item label="允许的项目类型" prop="villageType">
        <el-select v-model="formData.villageType" placeholder="请选择允许的项目类型">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="过期日期（当天23:59才过期）" prop="overdueDay">
        <el-input v-model="formData.overdueDay" placeholder="请输入过期日期（当天23:59才过期）" />
      </el-form-item>
      <el-form-item label="招商电话" prop="clueTel">
        <el-input v-model="formData.clueTel" placeholder="请输入招商电话" />
      </el-form-item>
      <el-form-item label="招商时间" prop="clueTime">
        <el-date-picker
          v-model="formData.clueTime"
          type="date"
          value-format="x"
          placeholder="选择招商时间"
        />
      </el-form-item>
      <el-form-item label="注册来源(0自己注册，1企微，2后台添加，3钉钉...)" prop="regFrom">
        <el-input
          v-model="formData.regFrom"
          placeholder="请输入注册来源(0自己注册，1企微，2后台添加，3钉钉...)"
        />
      </el-form-item>
      <el-form-item label="最大可使用面积" prop="maxUseArea">
        <el-input v-model="formData.maxUseArea" placeholder="请输入最大可使用面积" />
      </el-form-item>
      <el-form-item label="最大可使用房间数" prop="maxUseRoom">
        <el-input v-model="formData.maxUseRoom" placeholder="请输入最大可使用房间数" />
      </el-form-item>
      <el-form-item label="最后登录时间" prop="lastLoginTime">
        <el-date-picker
          v-model="formData.lastLoginTime"
          type="date"
          value-format="x"
          placeholder="选择最后登录时间"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OrgApi, OrgVO } from '@/api/bus/tag/busorg/index'

/** 机构 表单 */
defineOptions({ name: 'OrgForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  adminUid: undefined,
  company: undefined,
  provinceId: undefined,
  cityId: undefined,
  districtName: undefined,
  info: undefined,
  logo: undefined,
  squareLogo: undefined,
  total: undefined,
  tel: undefined,
  address: undefined,
  type: undefined,
  domainPrefix: undefined,
  isOem: undefined,
  wechatNumber: undefined,
  orgPrefix: undefined,
  joinCode: undefined,
  status: undefined,
  dictionary: undefined,
  module: undefined,
  villageType: undefined,
  overdueDay: undefined,
  clueTel: undefined,
  clueTime: undefined,
  regFrom: undefined,
  maxUseArea: undefined,
  maxUseRoom: undefined,
  lastLoginTime: undefined
})
const formRules = reactive({
  adminUid: [{ required: true, message: '管理员用户id不能为空', trigger: 'blur' }],
  provinceId: [{ required: true, message: '省份id不能为空', trigger: 'blur' }],
  cityId: [{ required: true, message: '市id不能为空', trigger: 'blur' }],
  districtName: [{ required: true, message: '省市地址;英文逗号,拼接不能为空', trigger: 'blur' }],
  total: [{ required: true, message: '机构总人数不能为空', trigger: 'blur' }],
  isOem: [{ required: true, message: '是否为OEM不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '机构状态，1启动，0审核中，4禁用不能为空', trigger: 'blur' }],
  regFrom: [
    {
      required: true,
      message: '注册来源(0自己注册，1企微，2后台添加，3钉钉...)不能为空',
      trigger: 'blur'
    }
  ]
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
      formData.value = await OrgApi.getOrg(id)
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
    const data = formData.value as unknown as OrgVO
    if (formType.value === 'create') {
      await OrgApi.createOrg(data)
      message.success(t('common.createSuccess'))
    } else {
      await OrgApi.updateOrg(data)
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
    adminUid: undefined,
    company: undefined,
    provinceId: undefined,
    cityId: undefined,
    districtName: undefined,
    info: undefined,
    logo: undefined,
    squareLogo: undefined,
    total: undefined,
    tel: undefined,
    address: undefined,
    type: undefined,
    domainPrefix: undefined,
    isOem: undefined,
    wechatNumber: undefined,
    orgPrefix: undefined,
    joinCode: undefined,
    status: undefined,
    dictionary: undefined,
    module: undefined,
    villageType: undefined,
    overdueDay: undefined,
    clueTel: undefined,
    clueTime: undefined,
    regFrom: undefined,
    maxUseArea: undefined,
    maxUseRoom: undefined,
    lastLoginTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
