<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 新增车辆 -->
  <el-drawer class="addCar" v-model="editGardenShow" direction="rtl" size="70%" :title="title">
    <template #default>
      <el-form
        ref="formRef"
        :model="formData"
        label-position="top"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
      >
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <span class="font-size-14px"> 基本信息 </span>
          </template>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="车牌号" prop="carNumber">
                <el-input v-model="formData.carNumber" placeholder="请输入园区名称" />
                <div class="cardFirst flex items-center justify-start m-t-20px flex-wrap">
                  <span
                    class="bg-#f1f1f1 inline-block m-b-8px p-0px p-l-8px p-r-8px m-r-10px b-rd-4px font-size-15px c-#000000a6 cursor-pointer"
                    v-for="dict in getIntDictOptions(DICT_TYPE.CARD_NUMBER)"
                    :key="dict.label"
                    @click="AddCarNum(dict.label)"
                    >{{ dict.label }}</span
                  >
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属项目" prop="villageId">
                <el-select
                  v-model="formData.villageId"
                  placeholder="请选择所属项目"
                  @change="getParking()"
                  class="w-100%"
                >
                  <el-option
                    v-for="item in buildingDataList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="parkId">
                <template #label>
                  所属停车场
                  <el-button
                    type="text"
                    class="font-size-14px !p-0px float-right !h-20px"
                    @click="addParkCharge"
                    >添加
                  </el-button>
                </template>
                <el-select
                  class="clear-both w-100%"
                  v-model="formData.parkId"
                  placeholder="请选择所属停车场"
                >
                  <el-option
                    v-for="item in parkList"
                    :key="item.id"
                    :label="item.parkName"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车主名称" prop="userName">
                <el-input placeholder="请输入车主名称" v-model="formData.userName" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车主手机号" prop="userPhone">
                <el-input placeholder="请输入车主手机号" v-model="formData.userPhone" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车辆类型" prop="carType">
                <el-select v-model="formData.carType" placeholder="请选择车辆类型" clearable>
                  <el-option
                    v-for="item in getIntDictOptions(DICT_TYPE.FREE_CAR)"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车辆收费类别" prop="chargeType">
                <el-select
                  v-model="formData.chargeType"
                  placeholder="请选择车辆收费类别"
                  clearable
                  :disabled="formType == 'update'"
                >
                  <el-option
                    v-for="item in getIntDictOptions(DICT_TYPE.CHARGE_TYPE)"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="绑定收费标准">
                <el-select
                  v-model="formData.feeChargeId"
                  placeholder="请选择绑定收费标准"
                  clearable
                >
                  <el-option
                    v-for="item in feeChargeList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="启用时间" prop="enableTime">
                <el-date-picker
                  v-model="formData.enableTime"
                  type="date"
                  placeholder="请选择启用时间"
                  class="!w-100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="截止时间" prop="enableTime">
                <el-date-picker
                  v-model="formData.overdueTime"
                  type="date"
                  placeholder="请选择截止时间"
                  class="!w-100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车辆余额" prop="balance">
                <el-input-number
                  :min="0"
                  v-model="formData.balance"
                  :step="1"
                  :precision="2"
                  class="!w-100%"
                  controls-position="right"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="关联车位" prop="parkSpace">
                <el-select v-model="formData.parkSpace" placeholder="请选择绑定关联车位" clearable>
                  <el-option
                    v-for="item in parkSpaceList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                    class="!w-100%"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车辆状态" prop="enable">
                <el-switch
                  inline-prompt
                  v-model="formData.enable"
                  :active-value="1"
                  :inactive-value="0"
                  active-text="正常"
                  inactive-text="禁用"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车辆备注" prop="carRemark">
                <el-input
                  type="textarea"
                  placeholder="请输入车辆备注"
                  v-model="formData.carRemark"
                  :rows="2"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="黑名单车辆" prop="needAlarm">
                <template #label>
                  黑名单车辆
                  <el-tooltip content="黑名单则不允许入场" placement="top"
                    ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                  /></el-tooltip>
                </template>
                <el-switch
                  v-model="formData.needAlarm"
                  inline-prompt
                  :active-value="1"
                  :inactive-value="0"
                  active-text="是"
                  inactive-text="否"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-card>
        <el-card
          v-if="formType == 'update'"
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <div>
              <span class="card-header-title">
                <span>一车多户</span>
              </span>
            </div>
          </template>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="车辆品牌" prop="spaceNum">
                <template #label
                  >拥有车位数

                  <el-tooltip
                    content="收取停车费是，费用会自动乘以此车位数。请谨慎填写。如果不涉及到一户多车，建议填写1"
                    placement="top"
                    ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                  /></el-tooltip>
                </template>
                <el-input placeholder="请输入拥有车位数" v-model="formData.spaceNum" />
              </el-form-item>
            </el-col>
            <!-- <el-col :span="12">
              <el-form-item label="一户多车" prop="spaceNum">
                <el-checkbox-group v-model="checkList">
                  <el-checkbox label="一户多车" value="Value 1" />
                </el-checkbox-group>
              </el-form-item>
            </el-col> -->
          </el-row>
        </el-card>
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <div>
              <span class="card-header-title">
                <span>车辆详细信息</span>
              </span>
            </div>
          </template>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="车辆品牌" prop="vehicleBrand">
                <el-input placeholder="请输入车辆品牌" v-model="formData.vehicleBrand" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车辆设备号" prop="vehicleEquipmentNumber">
                <el-input
                  placeholder="请输入车辆设备号"
                  v-model="formData.vehicleEquipmentNumber"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="行驶证号" prop="drivingLicenseNumber">
                <el-input placeholder="请输入行驶证号" v-model="formData.drivingLicenseNumber" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车辆颜色" prop="vehicleColor">
                <el-select v-model="formData.vehicleColor" placeholder="请选择车辆颜色" clearable>
                  <el-option
                    v-for="item in getIntDictOptions(DICT_TYPE.VEHICLE_COLOR)"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-card>
      </el-form>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="cancelClick">取消</el-button>
        <el-button type="primary" @click="confirmClick">保存</el-button>
      </div>
    </template>
  </el-drawer>
  <AddParkCharge ref="AddParkChargeRef" @success="getParking" />
</template>
<script setup lang="ts">
defineOptions({ name: 'AddCar' })
const message = useMessage() // 消息弹窗
import * as AreaApi from '@/api/system/area'
import { BuildApi } from '@/api/bus/village'
import AddParkCharge from '@/views/bus/application/park/carPark/addParkCharge.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { ParkChargeApi } from '@/api/bus/parkCharge'
import { ParkApi } from '@/api/bus/park'
import { ParkCarsApi } from '@/api/bus/parkCars'
const { t } = useI18n() // 国际化
const editGardenShow = ref(false)
const title = ref('添加车辆')
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const areaList = ref([]) // 地区列表
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const buildingDataList = ref([] as any[])
const parkList = ref([])
const feeChargeList = ref([])
const parkSpaceList = ref([])
const formData = ref({
  carNumber: '', //车牌号 数据字典【CARD_NUMBER】
  villageId: undefined, //所属项目
  parkId: undefined, //所属停车场
  userName: undefined, //车主姓名
  userPhone: undefined, //车主手机号
  carType: undefined, //车辆类型,数据字典【FREE_CAR】
  chargeType: undefined, //车辆收费类别,数据字典【CHARGE_TYPE】
  feeChargeId: undefined, //收费规则id
  enableTime: undefined, //启用时间
  overdueTime: undefined, // 截止时间
  balance: undefined, //车辆余额
  parkSpace: undefined, //绑定的车位ID,多个用逗号隔开
  enable: 1, //车辆状态（0：无效，1：有效）
  carRemark: undefined, //车辆备注
  needAlarm: 0, //当前名单是否为黑名单（0：否，1：黑名单）
  vehicleBrand: undefined, //车辆品牌
  vehicleEquipmentNumber: undefined, //车辆设备号
  drivingLicenseNumber: undefined, //行驶证号
  vehicleColor: undefined //颜色,数据字典值【VEHICLE_COLOR】
})
var carPhoneValid = (rule, value, callback) => {
  const plateNumber =
    /^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})$/
  if (plateNumber.test(value)) {
    callback()
  } else {
    callback(new Error('请输入正确的车牌号'))
  }
}

const formRules = reactive({
  carNumber: [
    { required: true, message: '车牌号不能为空', trigger: 'blur' },
    { validator: carPhoneValid }
  ],
  villageId: [{ required: true, message: '所属项目不能为空', trigger: 'blur' }],
  parkId: [{ required: true, message: '所属停车场能为空', trigger: 'blur' }],
  userName: [{ required: true, message: '车主姓名不能为空', trigger: 'blur' }],
  userPhone: [{ required: true, message: '车主手机号不能为空', trigger: 'blur' }],
  carType: [{ required: true, message: '车辆类型不能为空', trigger: 'blur' }],
  chargeType: [{ required: true, message: '车辆收费类别不能为空', trigger: 'blur' }],
  enableTime: [{ required: true, message: '启用时间不能为空', trigger: 'blur' }],
  overdueTime: [{ required: true, message: '截止时间不能为空', trigger: 'blur' }],
  enable: [{ required: true, message: '请选择车辆状态', trigger: 'blur' }],
  needAlarm: [{ required: true, message: '请选择黑名单车辆', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const formType = ref('') // 表单的类型：create - 新增；update - 修改
function cancelClick() {
  editGardenShow.value = false
}

const AddParkChargeRef = ref()

const addParkCharge = async () => {
  AddParkChargeRef.value.open('create')
}

/** 获得楼宇 */
const getTree = async () => {
  try {
    const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
    buildingDataList.value = res.villageList
    formData.value.villageId = res.villageList[0].id
    getParking()
  } finally {
  }
}
// 根据项目id获取停车场
const getParking = async () => {
  try {
    const res = await ParkApi.getParkingList(formData.value.villageId)
    if (res.list.length > 0) formData.value.parkId = res.list[0].id
    parkList.value = res.list
    getParkCharge()
  } finally {
  }
}
const AddCarNum = async (val) => {
  if (formData.value.carNumber) {
    formData.value.carNumber = val + formData.value.carNumber.slice(1)
  } else {
    formData.value.carNumber = val
  }
}
/** 打开抽屉 */
const open = async (status: string, form?: any) => {
  formType.value = status
  editGardenShow.value = true
  resetForm()
  initData()
  if (status == 'create') {
    title.value = '添加车辆'
  } else {
    title.value = '编辑车辆'
    formData.value = form
    formData.value.carType = Number(formData.value.carType)
    formData.value.chargeType = Number(formData.value.chargeType)
    formData.value.feeChargeId = Number(formData.value.feeChargeId)
    formData.value.enable = Number(formData.value.enable)
  }
}

const initData = async () => {
  getTree()
}
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const confirmClick = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(formData.value))
    if (formType.value === 'create') {
      await ParkCarsApi.addParkCars(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    } else {
      await ParkCarsApi.updateParkCars(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    }
    emit('success', formType.value)
  } finally {
    formLoading.value = false
  }
}

// 获取绑定收费标准
const getParkCharge = async () => {
  try {
    const res = await ParkChargeApi.getList({
      parkId: formData.value.parkId,
      villageId: formData.value.villageId
    })
    formData.value.feeChargeId = res.list[0].id
    feeChargeList.value = res.list
  } finally {
  }
}

/** 初始化 **/
onMounted(() => {})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    carNumber: '', //车牌号
    villageId: undefined, //所属项目
    parkId: undefined, //所属停车场
    userName: undefined, //车主姓名
    userPhone: undefined, //车主手机号
    carType: undefined, //车辆类型,数据字典【FREE_CAR】
    chargeType: undefined, //车辆收费类别,数据字典【CHARGE_TYPE】
    feeChargeId: undefined, //收费规则id
    enableTime: undefined, //启用时间
    overdueTime: undefined, // 截止时间
    balance: undefined, //车辆余额
    parkSpace: undefined, //绑定的车位ID,多个用逗号隔开
    enable: 1, //车辆状态（0：无效，1：有效）
    carRemark: undefined, //车辆备注
    needAlarm: 0, //当前名单是否为黑名单（0：否，1：黑名单）
    vehicleBrand: undefined, //车辆品牌
    vehicleEquipmentNumber: undefined, //车辆设备号
    drivingLicenseNumber: undefined, //行驶证号
    vehicleColor: undefined //颜色,数据字典值【VEHICLE_COLOR】
  }
  formRef.value?.resetFields()
}
</script>
<style scoped lang="scss">
::v-deep .el-drawer.rtl {
  background: #6aabc5;
}

.investmen-personne {
  width: 100%;
  // border: 1px solid #d9d9d9;
  border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
  padding: 5px 18px 4px 5px;
  cursor: pointer;
  line-height: 1.7;
  position: relative;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color)) inset;
}

.closeCircle {
  position: absolute;
  right: 2px;
  top: 0;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 16px;
  cursor: pointer;
}
</style>

<style>
.el-drawer__header {
  margin-bottom: 20px;
}
.el-drawer__body {
  padding-top: 0px;
}
</style>
