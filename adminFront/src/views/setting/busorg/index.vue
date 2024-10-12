<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="common-layout">
    <el-container>
      <leftMenu />
      <el-main class="!-pl--160px">
        <ContentWrap>
          <h3>企业信息设置</h3>
          <el-form
            ref="formRef"
            :model="formData"
            :rules="formRules"
            label-width="160px"
            v-loading="formLoading"
          >
            <el-form-item label="企业名称:" prop="company">
              <el-input v-model="formData.company" placeholder="请输入企业名称" />
            </el-form-item>
            <el-form-item label="企业简称:">
              <el-input v-model="formData.name" placeholder="请输入企业简称" />
            </el-form-item>
            <el-form-item label="企业邀请码:">
              <span>{{ formData.joinCode }}</span>
              <el-button text @click.stop="handleCopyComponent()">
                <Icon icon="ep:refresh" />
              </el-button>
              <el-button @click.stop="copy()">
                <Icon icon="ep:copy-document" />
                复制
              </el-button>
            </el-form-item>
            <el-form-item label="企业介绍:">
              <el-input
                v-model="formData.info"
                :rows="4"
                type="textarea"
                placeholder="请输入企业介绍"
              />
            </el-form-item>
            <el-form-item label="Logo">
              <template #label>
                Logo
                <el-tooltip content="建议上传白底照片，用于在招商平台展示" placement="top">
                  <Icon class="" icon="fa:question-circle-o" />
                </el-tooltip>
                :
              </template>
              <UploadImg v-model="formData.logo" :limit="1" />
            </el-form-item>
            <el-form-item label="Logo">
              <template #label>
                正方形Logo
                <el-tooltip content="建议上传200*200的透明背景白色图片" placement="top">
                  <Icon class="" icon="fa:question-circle-o" />
                </el-tooltip>
                :
              </template>
              <UploadImg v-model="formData.squareLogo" :limit="1" />
            </el-form-item>
            <el-form-item label="归属地:" prop="cityId">
              <el-cascader
                ref="mycascader"
                v-model="formData.cityId"
                :options="areaList"
                :props="defaultProps"
                class="w-1/1"
                clearable
                filterable
                placeholder="请选择城市"
                @change="changeCasc()"
              />
            </el-form-item>
            <el-form-item label="联系方式:">
              <el-input v-model="formData.tel" placeholder="请输入联系方式" />
            </el-form-item>
            <el-form-item label="联系地址:">
              <el-input v-model="formData.address" placeholder="请输入联系地址" />
            </el-form-item>
            <el-form-item label="联系地址:">
              <template #label>
                招商热线
                <el-tooltip
                  content="用于在招商平台作为热线电话，不使用找商品平台请忽略"
                  placement="top"
                >
                  <Icon class="" icon="fa:question-circle-o" />
                </el-tooltip>
                :
              </template>
              <el-row class="w-100%">
                <el-col :span="11">
                  <el-input v-model="formData.clueTel" placeholder="请输入招商热线" />
                </el-col>
                <el-col :span="2" class="text-center">---</el-col>
                <el-col :span="11">
                  <el-input
                    v-model="formData.clueTime"
                    placeholder="请输入招商时间，例：早9:00 - 晚8:30"
                  />
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item label="域名前缀:">
              <el-input
                v-model="formData.domainPrefix"
                placeholder="请输入域名前缀"
                :disabled="formData.id"
              />

              <div class="color-#A0A1A5 text-size-sm">企业的域名前缀提交后将不可更改</div>
            </el-form-item>
            <el-form-item label="工号前缀:">
              <el-input v-model="formData.orgPrefix" placeholder="请输入工号前缀" />
            </el-form-item>
            <!-- <el-form-item label="是否可修改真实姓名:">
              <div>
                <el-switch v-model="formData.changeName" />
              </div>
            </el-form-item> -->
            <el-form-item>
              <el-button
                @click="submitForm"
                v-hasPermi="['bus:org:update']"
                type="primary"
                :disabled="formLoading"
                >保 存</el-button
              >
            </el-form-item>
          </el-form>
        </ContentWrap>
      </el-main>
    </el-container>
  </div>
</template>
<script setup lang="ts">
import * as AreaApi from '@/api/system/area'
import { defaultProps } from '@/utils/tree'
import leftMenu from '../components/leftMenu/index.vue'
import { getTenantId } from '@/utils/auth'

import { OrgApi, OrgVO } from '@/api/bus/tag/busorg'

const { push, currentRoute } = useRouter()
const activeMessageName = ref('')
activeMessageName.value = currentRoute.value.path
const handleOpen = (key: string, keyPath: string[]) => {
  activeMessageName.value = key
  push(key)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
/** 机构 表单 */
defineOptions({ name: 'Org' })
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const areaList = ref([]) // 地区列表

const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref({
  id: undefined,
  name: undefined,
  Abbreviation: undefined,
  info: undefined,
  logo: undefined,
  squareLogo: undefined,
  cityId: undefined,
  districtName: undefined,
  provinceId: undefined,
  tel: undefined,
  address: undefined,
  clueTel: undefined,
  clueTime: undefined,
  domainPrefix: undefined,
  orgPrefix: undefined,
  changeName: false
})

const Code = ref('')
const formRules = reactive({
  company: [{ required: true, message: '企业名称不能为空', trigger: 'blur' }],
  cityId: [{ required: true, message: '归属地不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const initData = async () => {
  // 加载区域数据
  areaList.value = await AreaApi.getAreaTree()
  areaList.value.forEach((item) => {
    item.children.forEach((element) => {
      delete element.children
    })
  })
  console.log(areaList.value, '  area')
}

const GetDetail = async () => {
  const res = await OrgApi.getOrg(getTenantId())
  formData.value = res
}

const mycascader = ref()
const changeCasc = () => {
  console.log('****', mycascader.value.getCheckedNodes()[0])
  formData.value.provinceId = mycascader.value.getCheckedNodes()[0].pathValues[0]
  // formData.value.cityId = mycascader.value.getCheckedNodes()[0].pathValues[1]
  formData.value.districtName = mycascader.value.getCheckedNodes()[0].pathLabels.join(',')
}

// ** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  console.log(formData.value)
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  const data = formData.value as unknown as OrgVO
  try {
    await OrgApi.updateOrg(data)
    message.success(t('common.updateSuccess'))
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
/** 初始化 **/
onMounted(() => {
  initData()
  GetDetail()
})
</script>
<style scoped>
.el-scrollbar__view .el-main {
  padding-top: 0 !important;
}
</style>
