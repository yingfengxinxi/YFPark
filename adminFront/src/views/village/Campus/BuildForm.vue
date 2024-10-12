<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="项目名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入项目名称" />
      </el-form-item>
      <el-form-item label="项目名称缩写" prop="shortName">
        <el-input v-model="formData.shortName" placeholder="请输入项目名称缩写" />
      </el-form-item>
      <el-form-item label="项目介绍" prop="describe">
        <el-input v-model="formData.describe" placeholder="请输入项目介绍" />
      </el-form-item>
      <el-form-item label="项目LOGO" prop="logo">
        <el-input v-model="formData.logo" placeholder="请输入项目LOGO" />
      </el-form-item>
      <el-form-item label="项目省市区地址" prop="districtTxt">
        <el-input v-model="formData.districtTxt" placeholder="请输入项目省市区地址" />
      </el-form-item>
      <el-form-item label="项目详细地址" prop="address">
        <el-input v-model="formData.address" placeholder="请输入项目详细地址" />
      </el-form-item>
      <el-form-item label="落座经度（gcj02）" prop="lng">
        <el-input v-model="formData.lng" placeholder="请输入落座经度（gcj02）" />
      </el-form-item>
      <el-form-item label="落座纬度（gcj02）" prop="lat">
        <el-input v-model="formData.lat" placeholder="请输入落座纬度（gcj02）" />
      </el-form-item>
      <el-form-item label="管理面积" prop="managementArea">
        <el-input v-model="formData.managementArea" placeholder="请输入管理面积" />
      </el-form-item>
      <el-form-item label="可招商面积" prop="rentableArea">
        <el-input v-model="formData.rentableArea" placeholder="请输入可招商面积" />
      </el-form-item>
      <el-form-item label="可招商房源数量" prop="roomRentableCount">
        <el-input v-model="formData.roomRentableCount" placeholder="请输入可招商房源数量" />
      </el-form-item>
      <el-form-item label="总房源数量" prop="roomCount">
        <el-input v-model="formData.roomCount" placeholder="请输入总房源数量" />
      </el-form-item>
      <el-form-item label="标签（数组存储）" prop="tagIdArr">
        <el-input v-model="formData.tagIdArr" placeholder="请输入标签（数组存储）" />
      </el-form-item>
      <el-form-item label="项目公众号的微信号" prop="wechatNumber">
        <el-input v-model="formData.wechatNumber" placeholder="请输入项目公众号的微信号" />
      </el-form-item>
      <el-form-item label="数据状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="3D模型" prop="threeDimensionalFile">
        <UploadFile v-model="formData.threeDimensionalFile" />
      </el-form-item>
      <el-form-item label="3D模型物体ID" prop="threeDimensionalId">
        <el-input v-model="formData.threeDimensionalId" placeholder="请输入3D模型物体ID" />
      </el-form-item>
      <el-form-item label="楼宇图片" prop="dimensionalBgImg">
        <el-input v-model="formData.dimensionalBgImg" placeholder="请输入楼宇图片" />
      </el-form-item>
      <el-form-item label="房源到期的颜色值数组" prop="roomStatusColor">
        <el-input v-model="formData.roomStatusColor" placeholder="请输入房源到期的颜色值数组" />
      </el-form-item>
      <el-form-item label="当前管理的机构ID" prop="orgId">
        <el-input v-model="formData.orgId" placeholder="请输入当前管理的机构ID" />
      </el-form-item>
      <el-form-item label="国家ID" prop="countryId">
        <el-input v-model="formData.countryId" placeholder="请输入国家ID" />
      </el-form-item>
      <el-form-item label="省份ID" prop="provinceId">
        <el-input v-model="formData.provinceId" placeholder="请输入省份ID" />
      </el-form-item>
      <el-form-item label="城市ID" prop="cityId">
        <el-input v-model="formData.cityId" placeholder="请输入城市ID" />
      </el-form-item>
      <el-form-item label="区县ID" prop="districtId">
        <el-input v-model="formData.districtId" placeholder="请输入区县ID" />
      </el-form-item>
      <el-form-item label="街道/乡镇ID" prop="streetId">
        <el-input v-model="formData.streetId" placeholder="请输入街道/乡镇ID" />
      </el-form-item>
      <el-form-item label="社区/村ID" prop="communityId">
        <el-input v-model="formData.communityId" placeholder="请输入社区/村ID" />
      </el-form-item>
      <el-form-item label="房间最低单价（月）" prop="roomMinPrice">
        <el-input v-model="formData.roomMinPrice" placeholder="请输入房间最低单价（月）" />
      </el-form-item>
      <el-form-item label="房间平均单价（月）" prop="roomAveragePrice">
        <el-input v-model="formData.roomAveragePrice" placeholder="请输入房间平均单价（月）" />
      </el-form-item>
      <el-form-item label="附近公共交通相关信息" prop="trafficInfo">
        <el-input v-model="formData.trafficInfo" placeholder="请输入附近公共交通相关信息" />
      </el-form-item>
      <el-form-item label="业态" prop="type">
        <el-select v-model="formData.type" placeholder="请选择业态">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="额外扩展配置" prop="extraConfig">
        <el-input v-model="formData.extraConfig" placeholder="请输入额外扩展配置" />
      </el-form-item>
      <el-form-item label="vr链接" prop="vrLink">
        <el-input v-model="formData.vrLink" placeholder="请输入vr链接" />
      </el-form-item>
      <el-form-item label="视频地址" prop="video">
        <el-input v-model="formData.video" placeholder="请输入视频地址" />
      </el-form-item>
      <el-form-item label="VR视频导致的排序（VR和视频30，VR20，视频10，没有0）" prop="vrVideoSort">
        <el-input
          v-model="formData.vrVideoSort"
          placeholder="请输入VR视频导致的排序（VR和视频30，VR20，视频10，没有0）"
        />
      </el-form-item>
      <el-form-item label="月浏览数（计划任务统计）" prop="monthHits">
        <el-input v-model="formData.monthHits" placeholder="请输入月浏览数（计划任务统计）" />
      </el-form-item>
      <el-form-item label="排序值" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序值" />
      </el-form-item>
      <el-form-item
        label="围绕项目服务的子项目配置信息，例如人脸识别设备"
        prop="microServiceConfig"
      >
        <el-input
          v-model="formData.microServiceConfig"
          placeholder="请输入围绕项目服务的子项目配置信息，例如人脸识别设备"
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
import { villageApi, villageVO } from '@/api/bus/Campus'

/** 社区 表单 */
defineOptions({ name: 'VillageForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  shortName: undefined,
  describe: undefined,
  logo: undefined,
  districtTxt: undefined,
  address: undefined,
  lng: undefined,
  lat: undefined,
  managementArea: undefined,
  rentableArea: undefined,
  roomRentableCount: undefined,
  roomCount: undefined,
  tagIdArr: undefined,
  wechatNumber: undefined,
  status: undefined,
  threeDimensionalFile: undefined,
  threeDimensionalId: undefined,
  dimensionalBgImg: undefined,
  roomStatusColor: undefined,
  orgId: undefined,
  countryId: undefined,
  provinceId: undefined,
  cityId: undefined,
  districtId: undefined,
  streetId: undefined,
  communityId: undefined,
  roomMinPrice: undefined,
  roomAveragePrice: undefined,
  trafficInfo: undefined,
  type: undefined,
  extraConfig: undefined,
  vrLink: undefined,
  video: undefined,
  vrVideoSort: undefined,
  monthHits: undefined,
  sort: undefined,
  microServiceConfig: undefined
})
const formRules = reactive({
  status: [{ required: true, message: '数据状态不能为空', trigger: 'blur' }],
  threeDimensionalId: [{ required: true, message: '3D模型物体ID不能为空', trigger: 'blur' }],
  orgId: [{ required: true, message: '当前管理的机构ID不能为空', trigger: 'blur' }],
  countryId: [{ required: true, message: '国家ID不能为空', trigger: 'blur' }],
  provinceId: [{ required: true, message: '省份ID不能为空', trigger: 'blur' }],
  cityId: [{ required: true, message: '城市ID不能为空', trigger: 'blur' }],
  districtId: [{ required: true, message: '区县ID不能为空', trigger: 'blur' }],
  streetId: [{ required: true, message: '街道/乡镇ID不能为空', trigger: 'blur' }],
  communityId: [{ required: true, message: '社区/村ID不能为空', trigger: 'blur' }],
  type: [{ required: true, message: '业态不能为空', trigger: 'change' }],
  vrVideoSort: [
    {
      required: true,
      message: 'VR视频导致的排序（VR和视频30，VR20，视频10，没有0）不能为空',
      trigger: 'blur'
    }
  ],
  monthHits: [{ required: true, message: '月浏览数（计划任务统计）不能为空', trigger: 'blur' }]
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
      formData.value = await villageApi.getvillage(id)
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
    const data = formData.value as unknown as villageVO
    if (formType.value === 'create') {
      await villageApi.createvillage(data)
      message.success(t('common.createSuccess'))
    } else {
      await villageApi.updatevillage(data)
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
    shortName: undefined,
    describe: undefined,
    logo: undefined,
    districtTxt: undefined,
    address: undefined,
    lng: undefined,
    lat: undefined,
    managementArea: undefined,
    rentableArea: undefined,
    roomRentableCount: undefined,
    roomCount: undefined,
    tagIdArr: undefined,
    wechatNumber: undefined,
    status: undefined,
    threeDimensionalFile: undefined,
    threeDimensionalId: undefined,
    dimensionalBgImg: undefined,
    roomStatusColor: undefined,
    orgId: undefined,
    countryId: undefined,
    provinceId: undefined,
    cityId: undefined,
    districtId: undefined,
    streetId: undefined,
    communityId: undefined,
    roomMinPrice: undefined,
    roomAveragePrice: undefined,
    trafficInfo: undefined,
    type: undefined,
    extraConfig: undefined,
    vrLink: undefined,
    video: undefined,
    vrVideoSort: undefined,
    monthHits: undefined,
    sort: undefined,
    microServiceConfig: undefined
  }
  formRef.value?.resetFields()
}
</script>
