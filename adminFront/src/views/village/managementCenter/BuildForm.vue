<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="楼栋编号" prop="buildNumber">
        <el-input v-model="formData.buildNumber" placeholder="请输入楼栋编号" />
      </el-form-item>
      <el-form-item label="楼栋名称" prop="buildName">
        <el-input v-model="formData.buildName" placeholder="请输入楼栋名称" />
      </el-form-item>
      <el-form-item label="楼宇lOGO" prop="logo">
        <el-input v-model="formData.logo" placeholder="请输入楼宇lOGO" />
      </el-form-item>
      <el-form-item label="分区ID" prop="zoneId">
        <el-input v-model="formData.zoneId" placeholder="请输入分区ID" />
      </el-form-item>
      <el-form-item label="项目ID" prop="villageId">
        <el-input v-model="formData.villageId" placeholder="请输入项目ID" />
      </el-form-item>
      <el-form-item label="有没有单元，1有，0没有" prop="haveUnit">
        <el-input v-model="formData.haveUnit" placeholder="请输入有没有单元，1有，0没有" />
      </el-form-item>
      <el-form-item label="项目省市区地址" prop="districtTxt">
        <el-input v-model="formData.districtTxt" placeholder="请输入项目省市区地址" />
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
      <el-form-item label="落座经度（gcj02）" prop="lng">
        <el-input v-model="formData.lng" placeholder="请输入落座经度（gcj02）" />
      </el-form-item>
      <el-form-item label="落座纬度（gcj02）" prop="lat">
        <el-input v-model="formData.lat" placeholder="请输入落座纬度（gcj02）" />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="formData.address" placeholder="请输入地址" />
      </el-form-item>
      <el-form-item label="标准层高" prop="floorHeight">
        <el-input v-model="formData.floorHeight" placeholder="请输入标准层高" />
      </el-form-item>
      <el-form-item label="产权性质" prop="propertyRight">
        <el-input v-model="formData.propertyRight" placeholder="请输入产权性质" />
      </el-form-item>
      <el-form-item label="建筑面积" prop="buildArea">
        <el-input v-model="formData.buildArea" placeholder="请输入建筑面积" />
      </el-form-item>
      <el-form-item label="产权面积" prop="propertyArea">
        <el-input v-model="formData.propertyArea" placeholder="请输入产权面积" />
      </el-form-item>
      <el-form-item label="可租面积" prop="rentableArea">
        <el-input v-model="formData.rentableArea" placeholder="请输入可租面积" />
      </el-form-item>
      <el-form-item label="自用面积" prop="selfUseArea">
        <el-input v-model="formData.selfUseArea" placeholder="请输入自用面积" />
      </el-form-item>
      <el-form-item label="配套面积" prop="supportingArea">
        <el-input v-model="formData.supportingArea" placeholder="请输入配套面积" />
      </el-form-item>
      <el-form-item label="产权编号" prop="propertyNumber">
        <el-input v-model="formData.propertyNumber" placeholder="请输入产权编号" />
      </el-form-item>
      <el-form-item label="土地编号" prop="landNumber">
        <el-input v-model="formData.landNumber" placeholder="请输入土地编号" />
      </el-form-item>
      <el-form-item label="不动产证编号" prop="estateNumber">
        <el-input v-model="formData.estateNumber" placeholder="请输入不动产证编号" />
      </el-form-item>
      <el-form-item label="车位面积" prop="parkingArea">
        <el-input v-model="formData.parkingArea" placeholder="请输入车位面积" />
      </el-form-item>
      <el-form-item label="车位数量" prop="parkingCount">
        <el-input v-model="formData.parkingCount" placeholder="请输入车位数量" />
      </el-form-item>
      <el-form-item label="管理面积" prop="managementArea">
        <el-input v-model="formData.managementArea" placeholder="请输入管理面积" />
      </el-form-item>
      <el-form-item label="房间总数" prop="roomCount">
        <el-input v-model="formData.roomCount" placeholder="请输入房间总数" />
      </el-form-item>
      <el-form-item label="在租面积" prop="rentInArea">
        <el-input v-model="formData.rentInArea" placeholder="请输入在租面积" />
      </el-form-item>
      <el-form-item label="在租房间数" prop="rentInRoom">
        <el-input v-model="formData.rentInRoom" placeholder="请输入在租房间数" />
      </el-form-item>
      <el-form-item label="在租合同数" prop="rentInContract">
        <el-input v-model="formData.rentInContract" placeholder="请输入在租合同数" />
      </el-form-item>
      <el-form-item label="招商房间总数" prop="invitationRoomCount">
        <el-input v-model="formData.invitationRoomCount" placeholder="请输入招商房间总数" />
      </el-form-item>
      <el-form-item label="营收目标(数组存储)" prop="revenueTarget">
        <el-input v-model="formData.revenueTarget" placeholder="请输入营收目标(数组存储)" />
      </el-form-item>
      <el-form-item label="默认收支账户" prop="accountDefault">
        <el-input v-model="formData.accountDefault" placeholder="请输入默认收支账户" />
      </el-form-item>
      <el-form-item label="默认配置" prop="extraConfig">
        <el-input v-model="formData.extraConfig" placeholder="请输入默认配置" />
      </el-form-item>
      <el-form-item label="楼宇标签" prop="tagInfo">
        <el-input v-model="formData.tagInfo" placeholder="请输入楼宇标签" />
      </el-form-item>
      <el-form-item label="是否热门" prop="isHot">
        <el-input v-model="formData.isHot" placeholder="请输入是否热门" />
      </el-form-item>
      <el-form-item label="排序，越大越前" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序，越大越前" />
      </el-form-item>
      <el-form-item label="数据状态(1使用，0隐藏)" prop="status">
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
      <el-form-item label="实景图" prop="dimensionalBgImg">
        <el-input v-model="formData.dimensionalBgImg" placeholder="请输入实景图" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { BuildApi, BuildVO } from '@/api/bus/village'

/** 项目楼栋 表单 */
defineOptions({ name: 'BuildForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  buildNumber: undefined,
  buildName: undefined,
  logo: undefined,
  zoneId: undefined,
  villageId: undefined,
  haveUnit: undefined,
  districtTxt: undefined,
  countryId: undefined,
  provinceId: undefined,
  cityId: undefined,
  districtId: undefined,
  streetId: undefined,
  communityId: undefined,
  lng: undefined,
  lat: undefined,
  address: undefined,
  floorHeight: undefined,
  propertyRight: undefined,
  buildArea: undefined,
  propertyArea: undefined,
  rentableArea: undefined,
  selfUseArea: undefined,
  supportingArea: undefined,
  propertyNumber: undefined,
  landNumber: undefined,
  estateNumber: undefined,
  parkingArea: undefined,
  parkingCount: undefined,
  managementArea: undefined,
  roomCount: undefined,
  rentInArea: undefined,
  rentInRoom: undefined,
  rentInContract: undefined,
  invitationRoomCount: undefined,
  revenueTarget: undefined,
  accountDefault: undefined,
  extraConfig: undefined,
  tagInfo: undefined,
  isHot: undefined,
  sort: undefined,
  status: undefined,
  threeDimensionalFile: undefined,
  threeDimensionalId: undefined,
  dimensionalBgImg: undefined
})
const formRules = reactive({
  haveUnit: [{ required: true, message: '有没有单元，1有，0没有不能为空', trigger: 'blur' }],
  countryId: [{ required: true, message: '国家ID不能为空', trigger: 'blur' }],
  provinceId: [{ required: true, message: '省份ID不能为空', trigger: 'blur' }],
  cityId: [{ required: true, message: '城市ID不能为空', trigger: 'blur' }],
  districtId: [{ required: true, message: '区县ID不能为空', trigger: 'blur' }],
  streetId: [{ required: true, message: '街道/乡镇ID不能为空', trigger: 'blur' }],
  communityId: [{ required: true, message: '社区/村ID不能为空', trigger: 'blur' }],
  roomCount: [{ required: true, message: '房间总数不能为空', trigger: 'blur' }],
  rentInRoom: [{ required: true, message: '在租房间数不能为空', trigger: 'blur' }],
  rentInContract: [{ required: true, message: '在租合同数不能为空', trigger: 'blur' }],
  invitationRoomCount: [{ required: true, message: '招商房间总数不能为空', trigger: 'blur' }],
  isHot: [{ required: true, message: '是否热门不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '排序，越大越前不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '数据状态(1使用，0隐藏)不能为空', trigger: 'blur' }],
  threeDimensionalId: [{ required: true, message: '3D模型物体ID不能为空', trigger: 'blur' }]
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
      formData.value = await BuildApi.getBuild(id)
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
    const data = formData.value as unknown as BuildVO
    if (formType.value === 'create') {
      await BuildApi.createBuild(data)
      message.success(t('common.createSuccess'))
    } else {
      await BuildApi.updateBuild(data)
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
    buildNumber: undefined,
    buildName: undefined,
    logo: undefined,
    zoneId: undefined,
    villageId: undefined,
    haveUnit: undefined,
    districtTxt: undefined,
    countryId: undefined,
    provinceId: undefined,
    cityId: undefined,
    districtId: undefined,
    streetId: undefined,
    communityId: undefined,
    lng: undefined,
    lat: undefined,
    address: undefined,
    floorHeight: undefined,
    propertyRight: undefined,
    buildArea: undefined,
    propertyArea: undefined,
    rentableArea: undefined,
    selfUseArea: undefined,
    supportingArea: undefined,
    propertyNumber: undefined,
    landNumber: undefined,
    estateNumber: undefined,
    parkingArea: undefined,
    parkingCount: undefined,
    managementArea: undefined,
    roomCount: undefined,
    rentInArea: undefined,
    rentInRoom: undefined,
    rentInContract: undefined,
    invitationRoomCount: undefined,
    revenueTarget: undefined,
    accountDefault: undefined,
    extraConfig: undefined,
    tagInfo: undefined,
    isHot: undefined,
    sort: undefined,
    status: undefined,
    threeDimensionalFile: undefined,
    threeDimensionalId: undefined,
    dimensionalBgImg: undefined
  }
  formRef.value?.resetFields()
}
</script>
