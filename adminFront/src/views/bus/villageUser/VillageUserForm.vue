<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="人员针对房间的唯一编号" prop="uniqidId">
        <el-input v-model="formData.uniqidId" placeholder="请输入人员针对房间的唯一编号" />
      </el-form-item>
      <el-form-item label="用户表ID，可能为空" prop="userId">
        <el-input v-model="formData.userId" placeholder="请输入用户表ID，可能为空" />
      </el-form-item>
      <el-form-item label="归属租客ID" prop="ownerId">
        <el-input v-model="formData.ownerId" placeholder="请输入归属租客ID" />
      </el-form-item>
      <el-form-item label="楼栋ID" prop="buildId">
        <el-input v-model="formData.buildId" placeholder="请输入楼栋ID" />
      </el-form-item>
      <el-form-item label="分区ID" prop="zoneId">
        <el-input v-model="formData.zoneId" placeholder="请输入分区ID" />
      </el-form-item>
      <el-form-item label="项目ID" prop="villageId">
        <el-input v-model="formData.villageId" placeholder="请输入项目ID" />
      </el-form-item>
      <el-form-item label="人员名称（128位加密）" prop="name">
        <el-input v-model="formData.name" placeholder="请输入人员名称（128位加密）" />
      </el-form-item>
      <el-form-item label="人员手机号（128位加密）" prop="phone">
        <el-input v-model="formData.phone" placeholder="请输入人员手机号（128位加密）" />
      </el-form-item>
      <el-form-item label="住户联系邮箱" prop="email">
        <el-input v-model="formData.email" placeholder="请输入住户联系邮箱" />
      </el-form-item>
      <el-form-item label="通讯地址" prop="address">
        <el-input v-model="formData.address" placeholder="请输入通讯地址" />
      </el-form-item>
      <el-form-item label="是否为默认租客住户联系人" prop="isDefault">
        <el-input v-model="formData.isDefault" placeholder="请输入是否为默认租客住户联系人" />
      </el-form-item>
      <el-form-item label="0+房主，10+家属，20+租户，30+物业人员，40+服务人员，50+访客" prop="type">
        <el-select v-model="formData.type" placeholder="请选择0+房主，10+家属，20+租户，30+物业人员，40+服务人员，50+访客">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="有效期开始（特别指访客）" prop="effectiveTimeStart">
        <el-date-picker
          v-model="formData.effectiveTimeStart"
          type="date"
          value-format="x"
          placeholder="选择有效期开始（特别指访客）"
        />
      </el-form-item>
      <el-form-item label="有效期结束（特别指访客）" prop="effectiveTimeEnd">
        <el-date-picker
          v-model="formData.effectiveTimeEnd"
          type="date"
          value-format="x"
          placeholder="选择有效期结束（特别指访客）"
        />
      </el-form-item>
      <el-form-item label="证件类型（大陆身份证、港澳台、护照等）" prop="idcardType">
        <el-select v-model="formData.idcardType" placeholder="请选择证件类型（大陆身份证、港澳台、护照等）">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="证件号（128位加密）" prop="idcard">
        <el-input v-model="formData.idcard" placeholder="请输入证件号（128位加密）" />
      </el-form-item>
      <el-form-item label="出生年（年月日尽量从身份证中获取）" prop="birthYear">
        <el-input v-model="formData.birthYear" placeholder="请输入出生年（年月日尽量从身份证中获取）" />
      </el-form-item>
      <el-form-item label="出生月（年月日尽量从身份证中获取）" prop="birthMonth">
        <el-input v-model="formData.birthMonth" placeholder="请输入出生月（年月日尽量从身份证中获取）" />
      </el-form-item>
      <el-form-item label="出生日（年月日尽量从身份证中获取）" prop="birthDay">
        <el-input v-model="formData.birthDay" placeholder="请输入出生日（年月日尽量从身份证中获取）" />
      </el-form-item>
      <el-form-item label="性别（1男，2女，0未知）（年月日尽量从身份证中获取）" prop="sex">
        <el-radio-group v-model="formData.sex">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="学历信息" prop="eduType">
        <el-select v-model="formData.eduType" placeholder="请选择学历信息">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="工作年限" prop="workYear">
        <el-input v-model="formData.workYear" placeholder="请输入工作年限" />
      </el-form-item>
      <el-form-item label="毕业院校" prop="gradSchool">
        <el-input v-model="formData.gradSchool" placeholder="请输入毕业院校" />
      </el-form-item>
      <el-form-item label="技能证书" prop="skCert">
        <el-input v-model="formData.skCert" placeholder="请输入技能证书" />
      </el-form-item>
      <el-form-item label="身份证照片(包含手持身份证图片){front_url:,back_url:,hand_url:}" prop="idcardImg">
        <el-input v-model="formData.idcardImg" placeholder="请输入身份证照片(包含手持身份证图片){front_url:,back_url:,hand_url:}" />
      </el-form-item>
      <el-form-item label="照片网址（128加密）,后期若有多个用英文逗号分割" prop="photo">
        <el-input v-model="formData.photo" placeholder="请输入照片网址（128加密）,后期若有多个用英文逗号分割" />
      </el-form-item>
      <el-form-item label="照片数据状态（0+正常，10+审核中，20+失败）" prop="photoStatus">
        <el-radio-group v-model="formData.photoStatus">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="照片状态的描述文本（一般用于状态失败原因描述）" prop="photoStatusDesc">
        <el-input v-model="formData.photoStatusDesc" placeholder="请输入照片状态的描述文本（一般用于状态失败原因描述）" />
      </el-form-item>
      <el-form-item label="是否拥有代付权限" prop="inAdvance">
        <el-input v-model="formData.inAdvance" placeholder="请输入是否拥有代付权限" />
      </el-form-item>
      <el-form-item label="是否代付通知" prop="inAdvanceNotice">
        <el-input v-model="formData.inAdvanceNotice" placeholder="请输入是否代付通知" />
      </el-form-item>
      <el-form-item label="数据状态（0审核中，1正常，4拒绝）" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="租客端最后选择此身份的时间" prop="lastChooseTime">
        <el-date-picker
          v-model="formData.lastChooseTime"
          type="date"
          value-format="x"
          placeholder="选择租客端最后选择此身份的时间"
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
import { VillageUserApi, VillageUserVO } from '@/api/bus/villageUser'

/** 项目用户/租客 表单 */
defineOptions({ name: 'VillageUserForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  uniqidId: undefined,
  userId: undefined,
  ownerId: undefined,
  buildId: undefined,
  zoneId: undefined,
  villageId: undefined,
  name: undefined,
  phone: undefined,
  email: undefined,
  address: undefined,
  isDefault: undefined,
  type: undefined,
  effectiveTimeStart: undefined,
  effectiveTimeEnd: undefined,
  idcardType: undefined,
  idcard: undefined,
  birthYear: undefined,
  birthMonth: undefined,
  birthDay: undefined,
  sex: undefined,
  eduType: undefined,
  workYear: undefined,
  gradSchool: undefined,
  skCert: undefined,
  idcardImg: undefined,
  photo: undefined,
  photoStatus: undefined,
  photoStatusDesc: undefined,
  inAdvance: undefined,
  inAdvanceNotice: undefined,
  status: undefined,
  lastChooseTime: undefined,
})
const formRules = reactive({
  isDefault: [{ required: true, message: '是否为默认租客住户联系人不能为空', trigger: 'blur' }],
  sex: [{ required: true, message: '性别（1男，2女，0未知）（年月日尽量从身份证中获取）不能为空', trigger: 'blur' }],
  photoStatus: [{ required: true, message: '照片数据状态（0+正常，10+审核中，20+失败）不能为空', trigger: 'blur' }],
  inAdvance: [{ required: true, message: '是否拥有代付权限不能为空', trigger: 'blur' }],
  inAdvanceNotice: [{ required: true, message: '是否代付通知不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '数据状态（0审核中，1正常，4拒绝）不能为空', trigger: 'blur' }],
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
      formData.value = await VillageUserApi.getVillageUser(id)
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
    const data = formData.value as unknown as VillageUserVO
    if (formType.value === 'create') {
      await VillageUserApi.createVillageUser(data)
      message.success(t('common.createSuccess'))
    } else {
      await VillageUserApi.updateVillageUser(data)
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
    uniqidId: undefined,
    userId: undefined,
    ownerId: undefined,
    buildId: undefined,
    zoneId: undefined,
    villageId: undefined,
    name: undefined,
    phone: undefined,
    email: undefined,
    address: undefined,
    isDefault: undefined,
    type: undefined,
    effectiveTimeStart: undefined,
    effectiveTimeEnd: undefined,
    idcardType: undefined,
    idcard: undefined,
    birthYear: undefined,
    birthMonth: undefined,
    birthDay: undefined,
    sex: undefined,
    eduType: undefined,
    workYear: undefined,
    gradSchool: undefined,
    skCert: undefined,
    idcardImg: undefined,
    photo: undefined,
    photoStatus: undefined,
    photoStatusDesc: undefined,
    inAdvance: undefined,
    inAdvanceNotice: undefined,
    status: undefined,
    lastChooseTime: undefined,
  }
  formRef.value?.resetFields()
}
</script>
