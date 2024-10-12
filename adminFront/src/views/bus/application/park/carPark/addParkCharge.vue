<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 园区编辑 -->
  <el-drawer class="addCar" v-model="editGardenShow" direction="rtl" size="40%" :title="title">
    <template #default>
      <el-form
        ref="formRef"
        :model="formData"
        label-position="top"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
      >
        <el-tabs v-model="activeIndex" class="demo-tabs" @tab-click="handleClick">
          <el-tab-pane label="基本信息" name="0">
            <el-form-item label="所属项目" prop="villageId">
              <el-select v-model="formData.villageId" placeholder="请选择所属项目" clearable>
                <el-option
                  v-for="item in buildingDataList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="父级停车场" prop="parkId">
              <el-select
                class="clear-both w-100%"
                v-model="formData.parkId"
                placeholder="请选择所属停车场"
              >
                <el-option
                  v-for="item in parkList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="车场名称" prop="parkName">
              <el-input placeholder="请输入车场名称" v-model="formData.parkName" />
            </el-form-item>
            <el-form-item label="车场类型" prop="">
              <el-select v-model="formData.parkType" placeholder="请选择车场类型" clearable>
                <el-option
                  v-for="item in getIntDictOptions(DICT_TYPE.PARK_TYPE)"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="临时车政策" prop="">
              <el-select v-model="formData.tempCar" placeholder="请选择临时车政策" clearable>
                <el-option
                  v-for="item in getIntDictOptions(DICT_TYPE.TEMP_CAR)"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="免费车类型" prop="carType">
              <el-select v-model="formData.freeCar" placeholder="请选择免费车类型" clearable>
                <el-option
                  v-for="item in getIntDictOptions(DICT_TYPE.FREE_CAR)"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="一户多车配置" prop="moreCar">
              <el-radio-group v-model="formData.moreCar">
                <el-radio
                  v-for="item in getIntDictOptions(DICT_TYPE.MORE_CAR)"
                  :key="item.value"
                  :value="item.value"
                  >{{ item.label }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
            <el-form-item prop="mobilePayMonthFee">
              <template #label>
                车主月租缴费
                <el-tooltip content="关闭后，移动端将不支持车主自主对月租车进行续费" placement="top"
                  ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                /></el-tooltip>
              </template>
              <el-switch
                inline-prompt
                v-model="formData.mobilePayMonthFee"
                :active-value="1"
                :inactive-value="0"
                active-text="开"
                inactive-text="关"
              />
            </el-form-item>
            <el-form-item prop="mobilePayStoreFee">
              <template #label>
                车主余额储值
                <el-tooltip content="关闭后，移动端将不支持车主自主余额储值" placement="top"
                  ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                /></el-tooltip>
              </template>
              <el-switch
                inline-prompt
                v-model="formData.mobilePayStoreFee"
                :active-value="1"
                :inactive-value="0"
                active-text="开"
                inactive-text="关"
              />
            </el-form-item>
            <el-card
              class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
              style="border: 1px solid #f0f0f0 !important"
              shadow="never"
            >
              <template #header>
                <div>
                  <span class="card-header-title">
                    <span>支持租客直接绑定车辆</span>
                  </span>
                </div>
              </template>
              <el-form-item label="数量限制">
                <el-select v-model="formData.bindCarNumber" placeholder="请选择">
                  <el-option v-for="item in 20" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>
              <el-form-item label="月租车开放对象">
                <el-select v-model="formData.mustBindCompany" placeholder="请选择" clearable>
                  <el-option
                    v-for="item in getIntDictOptions(DICT_TYPE.FREE_CAR)"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <template #label> 免费车申请 </template>
                <el-switch
                  inline-prompt
                  v-model="formData.mobilePayStoreFee"
                  :active-value="1"
                  :inactive-value="0"
                  active-text="开启"
                  inactive-text="关闭"
                />
              </el-form-item>
            </el-card>
          </el-tab-pane>
          <el-tab-pane label="相机规则" name="1"> 相机规则 </el-tab-pane>
          <el-tab-pane label="车位限制" name="2"> 车位限制 </el-tab-pane>
          <el-tab-pane label="发票设置" name="3"> 发票设置 </el-tab-pane>
        </el-tabs>
      </el-form>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="cancelClick">取消</el-button>
        <el-button type="primary" @click="confirmClick">保存</el-button>
      </div>
    </template>
  </el-drawer>
  <TagVillageForm ref="formRef_tagInfo" @success="getTypeList" />
</template>
<script setup lang="ts">
defineOptions({ name: 'AddParkCharge' })
const message = useMessage() // 消息弹窗
import * as AreaApi from '@/api/system/area'
import { BuildApi } from '@/api/bus/village'
import TagVillageForm from '@/views/setting/village/TagVillageForm.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { BillCashierListApi } from '@/api/bus/bill/billCashierList'
import { ParkChargeApi } from '@/api/bus/parkCharge'
import { ParkApi } from '@/api/bus/park'
const { t } = useI18n() // 国际化
const activeIndex = ref('0')
const editGardenShow = ref(false)
const addParkBuildingShow = ref(false)
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
  id: undefined,
  villageId: undefined, //所属项目
  parkId: undefined, //所属停车场
  parkName: undefined, //车主姓名
  parkType: undefined, //车场类型,数据字典【PARK_TYPE】
  tempCar: undefined, //临时车政策,数据字典【TEMP_CAR】
  freeCar: undefined, //免费车类型(多个值用逗号(,)隔开)字典值【FREE_CAR】
  moreCar: undefined, //一户多车配置,数据字典【MORE_CAR】
  mobilePayMonthFee: 1, //车主月租缴费（0不开启，1必须开启）
  mobilePayStoreFee: 1, // 车主余额储值（0不开启，1必须开启）
  bindCarNumber: undefined, //数量限制
  mustBindCompany: undefined, //月租车开放对象
  freeCarApply: undefined //是否开启免费车申请1是0否不能为空
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
  parkId: [{ required: true, message: '父级停车场能为空', trigger: 'blur' }],
  parkName: [{ required: true, message: '车场名称不能为空', trigger: 'blur' }],
  tempCar: [{ required: true, message: '临时车政策不能为空', trigger: 'blur' }],
  moreCar: [{ required: true, message: '请选择一户多车配置', trigger: 'blur' }],
  mobilePayMonthFee: [{ required: true, message: '请选择车主月租缴费', trigger: 'blur' }],
  mobilePayStoreFee: [{ required: true, message: '请选择车主余额储值', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const formType = ref('') // 表单的类型：create - 新增；update - 修改
function cancelClick() {
  editGardenShow.value = false
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
    res.list.unshift({ id: 0, name: '无' })
    formData.value.parkId = res.list[0].id
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
const open = async (status: string, id?: number, form?: any) => {
  formType.value = status
  editGardenShow.value = true
  resetForm()
  initData()
  if (status == 'create') {
    title.value = '添加车场'
  } else {
    title.value = '编辑车场'
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
      await ParkApi.addPark(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    } else {
      await BuildApi.updatevillage(data)
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
    id: undefined,
    villageId: undefined, //所属项目
    parkId: undefined, //所属停车场
    parkName: undefined, //车主姓名
    parkType: undefined, //车场类型,数据字典【PARK_TYPE】
    tempCar: undefined, //临时车政策,数据字典【TEMP_CAR】
    freeCar: undefined, //免费车类型(多个值用逗号(,)隔开)字典值【FREE_CAR】
    moreCar: undefined, //一户多车配置,数据字典【MORE_CAR】
    mobilePayMonthFee: 1, //车主月租缴费（0不开启，1必须开启）
    mobilePayStoreFee: 1, // 车主余额储值（0不开启，1必须开启）
    bindCarNumber: undefined, //数量限制
    mustBindCompany: undefined, //月租车开放对象
    freeCarApply: undefined //是否开启免费车申请1是0否不能为空
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
