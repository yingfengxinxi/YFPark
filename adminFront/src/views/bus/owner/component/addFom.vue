<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <!-- 表单弹窗：添加/修改 -->
    <el-drawer v-model="drawer_show" :with-header="true" size="900px" @closed="closed">
      <template #title>
        <el-menu
          :default-active="drawer_show_index"
          class="el-menu-demo !border-none villageMenu row_menu !h-28px"
          mode="horizontal"
          @select="select_drawer"
        >
          <el-menu-item index="0">基本信息</el-menu-item>
          <el-menu-item index="1" v-if="form.isPersonal != 1">其他信息</el-menu-item>
        </el-menu>
      </template>
      <el-form
        labelWidth="100%"
        label-position="top"
        ref="formRef"
        :model="form"
        v-loading="formLoading"
      >
        <div class="drawer" v-if="drawer_show_index == '0'">
          <el-card
            class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
            style="border: 1px solid #f0f0f0 !important"
            shadow="never"
          >
            <template #header>
              <span class="font-size-14px"> 基本信息 </span>
            </template>
            <el-row :gutter="21">
              <!-- 基本信息填写----------------------------------------------------------- -->
              <el-col :span="8">
                <el-form-item
                  label="租客姓名"
                  prop="name"
                  :rules="[{ required: true, message: '请输入租客名称', trigger: 'blur' }]"
                >
                  <template #label>
                    租客姓名
                    <el-checkbox
                      class="m-l-20px !h-22px"
                      v-model="form.isPersonal"
                      label="个人"
                      size="large"
                      true-value="1"
                      false-value="0"
                    />
                  </template>
                  <el-input v-model="form.name" placeholder="请输入租客名称" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="行业分类">
                  <el-select
                    v-model="form.industryId"
                    filterable
                    remote
                    reserve-keyword
                    :remote-method="remoteMethod_industry"
                  >
                    <el-option
                      v-for="item in industry_option"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    />
                    <template #footer>
                      <el-button type="primary" size="small" @click="add_industry">添加</el-button>
                    </template>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="租客标签">
                  <el-select
                    v-model="form.tagInfo"
                    filterable
                    remote
                    multiple
                    collapse-tags
                    reserve-keyword
                    :remote-method="remoteMethod_tagInfo"
                  >
                    <el-option
                      v-for="item in tagInfo_option"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id.toString()"
                    />
                    <template #footer>
                      <el-button type="primary" size="small" @click="add_tagInfo">添加</el-button>
                    </template>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="租客编码">
                  <el-input v-model="form.tenantNo" placeholder="请输入租客编码" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="审批联系人">
                  <template #label>
                    审批联系人
                    <el-tooltip
                      content="在租客审批中如果处理人未租客时，请先取审批联系人，如果未设置则取默认联系人"
                      placement="top"
                    >
                      <Icon class="" icon="fa:question-circle-o" />
                    </el-tooltip>
                  </template>
                  <el-select
                    v-model="form.approvalContactId"
                    filterable
                    placeholder="请选择审批联系人名称"
                    no-data-text="暂无数据"
                  >
                    <el-option
                      v-for="item in ApprovalContactList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="8" v-if="drawer_type != 'add'">
                <el-form-item label="账单联系人">
                  <el-select
                    v-model="form.contactNoticeId"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="请选择账单联系人"
                    :remote-method="remoteMethod"
                  >
                    <el-option
                      v-for="item in contactName_options"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="drawer_type != 'add'">
                <el-form-item label="合同签署人">
                  <el-select
                    v-model="form.contactSignId"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="请选择账单联系人"
                    :remote-method="remoteMethod"
                  >
                    <el-option
                      v-for="item in contactName_options"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-card>
          <!-- 默认信息填写----------------------------------------------------------- -->

          <el-card
            class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
            style="border: 1px solid #f0f0f0 !important"
            shadow="never"
          >
            <template #header>
              <span class="font-size-14px"> 默认联系人 </span>
            </template>
            <el-row :gutter="21">
              <el-col :span="8">
                <el-form-item
                  label="默认联系人姓名"
                  prop="contactName"
                  :rules="[{ required: true, message: '请输入联系人姓名', trigger: 'blur' }]"
                  v-if="drawer_type == 'add'"
                >
                  <el-input v-model="form.contactName" placeholder="请输入默认联系人姓名" />
                </el-form-item>
                <el-form-item label="默认联系人姓名" v-else>
                  <el-select
                    v-model="form.contactId"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="请选择默认联系人姓名"
                    :remote-method="remoteMethod"
                  >
                    <el-option
                      v-for="item in contactName_options"
                      :key="item.contactId"
                      :label="item.name"
                      :value="item.contactId"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="drawer_type == 'add'">
                <el-form-item
                  label="联系方式"
                  prop="tel"
                  :rules="[
                    { required: true, message: '请输入联系方式', trigger: 'blur' },
                    {
                      pattern: /^1[3456789]\d{9}$/,
                      message: '请输入正确的手机号码',
                      trigger: 'blur'
                    }
                  ]"
                >
                  <el-input v-model="form.tel" placeholder="请输入联系方式" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="drawer_type == 'add'">
                <el-form-item
                  label="身份"
                  prop="villageType"
                  :rules="[{ required: true, message: '请选择身份', trigger: 'blur' }]"
                >
                  <el-select v-model="form.villageType">
                    <el-option label="老板/管理员" :value="0" />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="8" v-if="drawer_type == 'add'">
                <el-form-item label="证件类型">
                  <el-select v-model="form.idcardType">
                    <el-option label="身份证" value="idcard" />
                    <el-option label="港澳台通行证" value="gangaotai" />
                    <el-option label="护照" value="passport" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="证件号码" v-if="drawer_type == 'add'">
                  <el-input v-model="form.certificateNumber" placeholder="请输入证件号码" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="drawer_type == 'add'">
                <el-form-item label="邮箱">
                  <el-input v-model="form.email" placeholder="请输入邮箱" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-card>
          <el-card
            v-if="form.isPersonal != '1'"
            class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
            style="border: 1px solid #f0f0f0 !important"
            shadow="never"
          >
            <template #header>
              <span class="font-size-14px"> 账户信息 </span>
            </template>
            <el-row :gutter="21">
              <el-col :span="8" v-if="form.isPersonal != '1'">
                <el-form-item label="开户银行">
                  <el-input v-model="form.bank" placeholder="请输入开户行" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != '1'">
                <el-form-item label="账号">
                  <el-input v-model="form.account" placeholder="请输入账号" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != '1'">
                <el-form-item label="电话">
                  <el-input v-model="form.bankPhone" placeholder="请输入..." />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != '1'">
                <el-form-item label="纳税人识别号">
                  <el-input v-model="form.taxpayerIdentificationNumber" placeholder="请输入..." />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != '1'">
                <el-form-item label="默认发票类型">
                  <el-select v-model="form.invoiceType">
                    <el-option label="增值税普通发票" value="0" />
                    <el-option label="增值税专用发票" value="1" />
                    <el-option label="增值税电子普通发票" value="2" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != '1'">
                <el-form-item label="	开票地址">
                  <el-input v-model="form.invoiceInfo" placeholder="请输入开票地址" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="默认发票模版">
                  <template #label>
                    默认发票模版
                    <el-tooltip
                      content="当默认的发票模版不支持该租客所在的楼宇时将无法生效"
                      placement="top"
                    >
                      <Icon class="" icon="fa:question-circle-o" />
                    </el-tooltip>
                  </template>
                  <el-select
                    v-model="form.approvalContactId"
                    filterable
                    placeholder="请选择审批联系人名称"
                    no-data-text="暂无数据"
                  >
                    <el-option
                      v-for="item in InvoiceTemplateList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-card>

          <!-- 工商信息填写----------------------------------------------------------- -->
          <el-card
            v-if="form.isPersonal != 1"
            class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
            style="border: 1px solid #f0f0f0 !important"
            shadow="never"
          >
            <template #header>
              <span class="font-size-14px"> 工商信息 </span>
            </template>
            <el-row :gutter="21">
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="统一社会信用代码">
                  <el-input
                    v-model="form.businessInfo.unifiedSocialCreditCode"
                    placeholder="请输入统一社会信用代码"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="纳税人识别号">
                  <el-input
                    v-model="form.businessInfo.taxpayerIdNumber"
                    placeholder="请输入纳税人识别号"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="注册号">
                  <el-input
                    v-model="form.businessInfo.registrationNumber"
                    placeholder="请输入注册号"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="组织机构代码">
                  <el-input
                    v-model="form.businessInfo.organizationCode"
                    placeholder="请输入组织机构代码"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="法定代表人">
                  <el-input
                    v-model="form.businessInfo.legalPerson"
                    placeholder="请输入法定代表人"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="邮箱">
                  <el-input v-model="form.businessInfo.email" placeholder="请输入邮箱" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="国籍">
                  <el-input v-model="form.businessInfo.birthCountry" placeholder="请输入国籍：" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="注册资本（万）">
                  <el-input
                    v-model="form.businessInfo.registeredCapital"
                    placeholder="请输入注册资本（万）"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="经营状态">
                  <el-input
                    v-model="form.businessInfo.operatingState"
                    placeholder="请输入经营状态"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="登记机关">
                  <el-input
                    v-model="form.businessInfo.registrationAuthority"
                    placeholder="请输入登记机关"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="公司类型">
                  <el-input v-model="form.businessInfo.companyType" placeholder="请输入公司类型" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="人员规模">
                  <el-input v-model="form.businessInfo.staffSize" placeholder="请输入人员规模" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="英文名">
                  <el-input v-model="form.businessInfo.englishName" placeholder="请输入英文名" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="成立日期">
                  <el-date-picker
                    v-model="form.businessInfo.business_info_founding_time"
                    type="date"
                    placeholder="请选择成立日期"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    style="width: 100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="核准日期">
                  <el-date-picker
                    v-model="form.businessInfo.approvalTime"
                    type="date"
                    placeholder="请选择核准日期"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    style="width: 100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="营业期限">
                  <el-date-picker
                    v-model="form.businessInfo.business_info_business_term"
                    type="date"
                    placeholder="请选择营业期限"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    style="width: 100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="所属地区">
                  <el-input v-model="form.businessInfo.district" placeholder="请输入所属地区" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="注册地址">
                  <el-input v-model="form.businessInfo.address" placeholder="请输入注册地址" />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="实缴资本">
                  <el-input
                    v-model="form.businessInfo.actualCapital"
                    placeholder="请输入实缴资本"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="国税编号">
                  <el-input
                    v-model="form.businessInfo.nationalTaxNumber"
                    placeholder="请输入国税编号"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="form.isPersonal != 1">
                <el-form-item label="地税编号">
                  <el-input
                    v-model="form.businessInfo.localTaxNumber"
                    placeholder="请输入地税编号"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="21" v-if="form.isPersonal != 1">
                <el-form-item label="所属行业">
                  <el-input v-model="form.businessInfo.industry" placeholder="请输入所属行业" />
                </el-form-item>
              </el-col>
              <el-col :span="21" v-if="form.isPersonal != 1">
                <el-form-item label="经营范围">
                  <el-input
                    type="textarea"
                    v-model="form.businessInfo.businessScope"
                    placeholder="请输入经营范围"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-card>
          <el-form-item class="m-20px">
            <template #label>
              是否归档
              <el-tooltip content="控制是否在列表数据中显示,选择是则不显示" placement="top">
                <Icon class="" icon="fa:question-circle-o" />
              </el-tooltip>
            </template>
            <el-radio-group v-model="form.isArchive">
              <el-radio :value="0" size="large">否</el-radio>
              <el-radio :value="1" size="large">是</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        <div class="drawer" v-if="drawer_show_index == 1">
          <!-- 其他信息填写----------------------------------------------------------- -->
          <el-card
            v-if="form.isPersonal != '1'"
            class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
            style="border: 1px solid #f0f0f0 !important"
            shadow="never"
          >
            <template #header>
              <span class="font-size-14px"> 企业信息 </span>
            </template>
            <el-row :gutter="21">
              <el-col :span="24">
                <el-form-item label="企业logo">
                  <el-upload
                    class="uploader_BOX"
                    :on-change="faceChange"
                    :show-file-list="false"
                    :auto-upload="false"
                  >
                    <img v-if="form.logo" :src="form.logo" alt="" class="img" />
                    <img src="./image/plus.png" v-else class="plus" alt="" />
                  </el-upload>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label=""
                  ><template #label>
                    推荐展示
                    <el-tooltip content="推荐的企业会再租客PC端进行展示" placement="top">
                      <Icon class="" icon="fa:question-circle-o" />
                    </el-tooltip>
                  </template>
                  <el-select v-model="form.isSuggest" placeholder="请选择">
                    <el-option
                      v-for="item in [
                        { label: '推荐', value: 1 },
                        { label: '不推荐', value: 0 }
                      ]"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="官网地址">
                  <el-input v-model="form.websiteLink" placeholder="请输入官网地址" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否开启公司代付通知">
                  <el-radio-group v-model="form.isAdvanceNotice">
                    <el-radio :value="0" size="large">是</el-radio>
                    <el-radio :value="1" size="large">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="企业简介">
                  <Editor v-model:modelValue="form.companyDesc" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-card>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="closed">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </div>
    </el-drawer>
    <!-- 新增行业分类 -->
    <Industry ref="formRef_industry" />
    <!-- 新增租客标签 -->
    <TagInfo ref="formRef_tagInfo" />
  </div>
</template>
<script setup lang="ts">
const message = useMessage() // 消息弹窗
import Industry from './industry.vue'
import TagInfo from './tagInfo.vue'
// import { onMounted, watch } from 'vue'
import { OwnerApi, OwnerVO } from '@/api/bus/owner'
import { TagOwnerApi } from '@/api/bus/tag/owner'
import Editor from '@/components/Editor/src/Editor.vue'

let drawer_show = ref(false)
let drawer_type = ref()

const form = ref<OwnerVO>({
  businessInfo: {},
  isPersonal: '0'
})
/** 打开抽屉 */
const open = async (type?: string, id?: string) => {
  drawer_type.value = type
  resetForm()
  form.value.isPersonal = '0'
  remoteMethod('')
  drawer_show.value = true
  if (id) {
    OwnerApi.getOwnerById(id).then((res) => {
      form.value = res
      form.value.tagInfo = form.value.tagInfo.split(',')
      form.value.contactId = Number(form.value.contactId)
      form.value.isPersonal = String(form.value.isPersonal)
      if (!form.value.isPersonal) {
        form.value.isPersonal = '0'
      }
      if (form.value.tagInfo[0] == '') {
        form.value.tagInfo = []
      } else {
        form.value.tagInfo.forEach((item: any) => {
          console.log(item, 'item')
          //   item = Number(item)
        })
      }
      console.log(form.value.tagInfo, 'form.value.tagInfo')
      const businessInfo = JSON.parse(res.businessInfo)
      form.value.businessInfo = businessInfo
    })
  }
}
const closed = async () => {
  emit('update:show', false)
}
const emit = defineEmits(['update:show', 'success'])
const formRef = ref()
const formLoading = ref(false)
const submit = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    await formRef.value.validate((valid) => {
      if (valid) {
        const data = JSON.parse(JSON.stringify(form.value))
        data.businessInfo = JSON.stringify(data.businessInfo)
        if (data.tagInfo?.length > 0) {
          data.tagInfo = data.tagInfo.toString()
        } else {
          data.tagInfo = ''
        }
        if (drawer_type.value === 'add') {
          OwnerApi.createOwner(data).then((res) => {
            drawer_show.value = false
            message.success('添加成功')
            formLoading.value = false
            emit('success', 0)
          })
        } else {
          OwnerApi.updateOwner(data).then((res) => {
            drawer_show.value = false
            message.success('修改成功')
            drawer_show.value = false
            formLoading.value = false
            emit('success', 1)
          })
        }
      }
    })
  } finally {
    formLoading.value = false
  }
}
const drawer_show_index = ref('0')
const addform = () => {
  drawer_type.value = 'add'
  drawer_show.value = true
  form.value = {
    businessInfo: {
      unifiedSocialCreditCode: ''
    }
  }
}
const select_drawer = (key) => {
  drawer_show_index.value = key
}
//上传logo
const faceChange = (file: any) => {
  const faceimg = new FormData()
  faceimg.append('file', file.raw)
  OwnerApi.uploadFile(faceimg).then((res) => {
    form.value.logo = res.data
  })
}
//选择人员
const contactName_options = ref([])

// 审核联系人
const ApprovalContactList = ref([] as any[])
// 默认发票模版
const InvoiceTemplateList = ref([] as any[])
//新增标签分类
const formRef_tagInfo = ref()
const add_tagInfo = () => {
  formRef_tagInfo.value.open('create')
}
const formRef_industry = ref()
const add_industry = () => {
  formRef_industry.value.open('create')
}
//行业分类
const industry_option = ref([])
const remoteMethod_industry = (val) => {
  OwnerApi.getTagIndustryList(val).then((res) => {
    industry_option.value = res
  })
}
const remoteMethod = (val) => {
  OwnerApi.getOwnerListByName(val).then((res) => {
    contactName_options.value = res
    console.log(drawer_type.value, 'drawer_type.value')
    if (drawer_type.value == 'edit') {
      ApprovalContactList.value = res
    }
  })
}
//租客标签
const Owner = ref([])
const industry = ref([])
const tagInfo_option = ref([])
const remoteMethod_tagInfo = (val) => {
  TagOwnerApi.getTagOwnerList(val).then((res) => {
    tagInfo_option.value = res
  })
}
onMounted(() => {
  OwnerApi.getTagIndustryList('').then((res) => {
    industry_option.value = res
    Owner.value = res
  })
  //租客标签
  TagOwnerApi.getTagOwnerList('').then((res) => {
    tagInfo_option.value = res
    industry.value = res
  })
})

/** 重置表单 */
const resetForm = () => {
  form.value = {
    isArchive: 0,
    isSuggest: 0,
    businessInfo: {},
    isPersonal: '0'
  }
  formRef.value?.resetFields()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped>
.villageMenu .el-menu-item:hover,
.villageMenu .el-menu-item:focus {
  background: none;
}
.uploader_BOX {
  background: #f7f7f7;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 110px;
  height: 110px;
  display: flex;
  justify-content: center;
  align-items: center;
  .img {
    width: 100%;
    height: 100%;
  }
  .plus {
    width: 40px;
    height: 40px;
  }
}
.drawer_footer {
  display: flex;
  justify-content: flex-end;
  position: fixed;
  bottom: 40px;
  right: 40px;
}
.drawer {
  max-height: 75vh;
  overflow-y: auto;
  overflow-x: hidden;

  .drawer_header-title {
    margin-bottom: 20px;
    font-size: 16px;
    font-weight: 600;
    color: #4a4a4a;
  }
}
</style>
<style scoped>
.el-drawer__header {
  margin-bottom: 0;
}
</style>
