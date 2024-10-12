<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 新建房源 -->
  <el-drawer v-model="editGardenShow" direction="rtl" size="70%" :title="title">
    <!-- <template #header>
      <h4>s by slot</h4>
    </template> -->
    <template #default>
      <el-form
        ref="formRef"
        :model="formData"
        label-position="top"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
      >
        <el-tabs v-model="activeIndex" class="demo-tabs">
          <el-tab-pane label="基础配置" name="0">
            <div>
              <!-- <div class="Line_title mt-10px">基础信息</div> -->
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="园区" prop="villageId">
                    <el-select
                      ref="villageIdRef"
                      v-model="formData.villageId"
                      placeholder="请选择所属园区"
                    >
                      <el-option
                        v-for="item in VillageList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="建筑" prop="buildId">
                    <el-select ref="buildIdRef" v-model="formData.buildId" placeholder="请选择建筑">
                      <el-option
                        v-for="item in buildingDataList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="楼层" prop="layerId">
                    <el-select
                      ref="layerIdRef"
                      v-model="formData.layerId"
                      placeholder="请选择所属楼层"
                    >
                      <el-option
                        v-for="item in FloorDataList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="房号" prop="roomName">
                    <el-input v-model="formData.roomName" placeholder="请输入..." />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="房间数字编号" prop="roomNumber">
                    <template #label>
                      房间数字编号
                      <el-tooltip
                        content="用于房间列表进行排序，以及未来智能设备等第三方对接使用"
                        placement="top"
                      >
                        <Icon class="" icon="fa:question-circle-o" />
                      </el-tooltip>
                    </template>
                    <el-input v-model="formData.roomNumber" placeholder="请输入..." />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="计租面积" prop="rentalArea">
                    <template #label>
                      计租面积
                      <el-tooltip content="用于合同计算租金的面积" placement="top">
                        <Icon class="" icon="fa:question-circle-o" />
                      </el-tooltip>
                    </template>
                    <el-input v-model="formData.rentalArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="计费面积">
                    <template #label>
                      计费面积
                      <el-tooltip
                        content="用于合同计算物业费的面积，若为空则取计租面积"
                        placement="top"
                      >
                        <Icon class="" icon="fa:question-circle-o" />
                      </el-tooltip>
                    </template>
                    <el-input v-model="formData.chargingArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="建筑面积" prop="buildArea">
                    <el-input v-model="formData.buildArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="套内面积">
                    <el-input v-model="formData.insideArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="房源管理编号">
                    <template #label>
                      房源管理编号
                      <el-tooltip
                        content="用于收银台快捷搜索房源,例如使用项目编号-楼宇编号-楼层编号-房间编号"
                        placement="top"
                      >
                        <Icon class="" icon="fa:question-circle-o" />
                      </el-tooltip>
                    </template>
                    <el-input v-model="formData.roomAliasId" placeholder="请输入..." />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="虚拟资源">
                    <template #label>
                      虚拟资源
                      <el-tooltip content="不占用房屋面积统计、不占用计租率" placement="top">
                        <Icon class="" icon="fa:question-circle-o" />
                      </el-tooltip>
                    </template>
                    <el-select ref="villageIdRef" v-model="formData.isUnreal" placeholder="请选择">
                      <el-option key="1" label="是" value="1" />
                      <el-option key="0" label="否" value="0" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="资源交付时间">
                    <el-date-picker
                      class="!w-1/1"
                      v-model="formData.deliverTime"
                      type="datetime"
                      placeholder="请选择日期"
                      format="YYYY/MM/DD HH:mm:ss"
                      value-format="x"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </el-tab-pane>
          <el-tab-pane label="招商信息" name="1">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="产权性质" prop="propertyRight">
                  <el-select v-model="formData.propertyRight" placeholder="请选择产权性质">
                    <el-option
                      v-for="item in PropertyRightNatureList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="租赁状态">
                  <el-input v-model="roomStatusTxt" disabled placeholder="请输入..." />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招商状态">
                  <el-select
                    ref="villageIdRef"
                    v-model="formData.invitationStatus"
                    placeholder="请选择"
                  >
                    <el-option key="1" label="招商" value="1" />
                    <el-option key="0" label="不招商" value="0" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="可租日期">
                  <el-date-picker
                    class="!w-1/1"
                    v-model="formData.leaseEnd"
                    type="datetime"
                    placeholder="请选择日期"
                    format="YYYY/MM/DD HH:mm:ss"
                    value-format="x"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="装修情况">
                  <el-select v-model="formData.decoration" placeholder="请选择产权性质">
                    <el-option
                      v-for="item in decorationList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="资源状态">
                  <el-select v-model="formData.status" placeholder="请选择资源状态">
                    <el-option
                      v-for="item in statusList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="资源类型">
                  <el-select v-model="formData.houseType" placeholder="请选择产权性质">
                    <el-option
                      v-for="item in houseTypeList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="报价">
                  <el-input v-model="formData.preUnitPrice" placeholder="请输入报价">
                    <template #append>
                      <el-select
                        ref="layerIdRef"
                        v-model="formData.priceUnit"
                        placeholder=""
                        style="width: 85px"
                      >
                        <el-option
                          v-for="item in priceUnitList"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="低价">
                  <el-input v-model="formData.preUnitPriceMin" placeholder="请输入报价">
                    <template #append>
                      <el-select
                        ref="layerIdRef"
                        v-model="formData.priceUnitMin"
                        placeholder=""
                        style="width: 85px"
                      >
                        <el-option
                          v-for="item in priceUnitList"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="资源备案号">
                  <el-input v-model="formData.recordNo" placeholder="请输入..." />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="层高">
                  <template #label>
                    层高
                    <el-tooltip content="单位: m" placement="top">
                      <Icon class="" icon="fa:question-circle-o" />
                    </el-tooltip>
                  </template>
                  <el-input v-model="formData.floorHeight" placeholder="请输入..." />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="荷载值">
                  <el-input v-model="formData.loadMax" placeholder="请输入..." />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="标签">
                  <el-select
                    class="w-100%"
                    v-model="formData.tagIdArr"
                    multiple
                    placeholder="请选择标签"
                  >
                    <el-option
                      v-for="item in HouseTagList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                    <template #footer>
                      <el-button type="primary" size="small" @click="add_tagInfo">添加</el-button>
                    </template>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="资源招商编号">
                  <el-input
                    v-model="formData.extraConfig.investmentNumber"
                    placeholder="请输入..."
                  />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="图片">
                  <template #label>
                    图片
                    <el-tooltip content="建议上传16: 9的图片" placement="top">
                      <Icon class="" icon="fa:question-circle-o" />
                    </el-tooltip>
                  </template>
                  <UploadImg v-model="formData.images" :limit="6" />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="招商条件">
                  <el-input
                    v-model="formData.investmentConditions"
                    autosize
                    type="textarea"
                    placeholder="请输入招商条件"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="拓展信息" name="2">
            <div>
              <div class="Line_title mt-10px">拓展信息</div>
              <el-row :gutter="20" justify="space-between">
                <el-col :span="8">
                  <el-form-item label="使用率">
                    <el-input v-model="formData.extraConfig.utilization" placeholder="请输入..." />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="办公格局">
                    <el-input
                      v-model="formData.extraConfig.officePattern"
                      placeholder="例如：2+1"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="窗面朝向">
                    <el-input
                      v-model="formData.extraConfig.windowFaceOrientation"
                      placeholder="例如：南"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="免租期">
                    <el-input v-model="formData.extraConfig.rentFree" placeholder="例如：前2个月" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="最短租期">
                    <el-input
                      v-model="formData.extraConfig.minLeaseTerm"
                      placeholder="例如：12个月"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="">
                    <template #label>
                      排序值
                      <el-tooltip content="用于招商平台的展示优先级" placement="top">
                        <Icon class="" icon="fa:question-circle-o" />
                      </el-tooltip>
                    </template>
                    <el-input v-model="formData.sort" placeholder="排序值越大展示越靠前" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="工位数量">
                    <div class="flex items-center justify-between w-100%">
                      <el-input
                        v-model="formData.extraConfig.stationNumberMin"
                        placeholder=""
                        class="!w-45%"
                      />
                      -
                      <el-input
                        v-model="formData.extraConfig.stationNumberMax"
                        placeholder=""
                        class="!w-45%"
                      />
                    </div>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item>
                    <template #label>
                      VR链接
                      <el-tooltip content="输入后默认支持VR展示" placement="top">
                        <Icon class="" icon="fa:question-circle-o" />
                      </el-tooltip>
                    </template>
                    <el-input v-model="formData.vrLink" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="户型图说明">
                    <el-input v-model="formData.extraConfig.houseDescription" placeholder="" />
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item>
                    <template #label>
                      <span class="isRevise">
                        图片
                        <el-tooltip content="建议上传16:9的图片" placement="top">
                          <Icon class="" icon="fa:question-circle-o" />
                        </el-tooltip>
                      </span>
                    </template>
                    <UploadImg v-model="formData.extraConfig.house" :limit="1" />
                  </el-form-item>
                </el-col>
                <el-col :span="16">
                  <el-form-item>
                    <template #label>
                      <span class="isRevise"> 上传视频 </span>
                    </template>

                    <UploadFile
                      v-model="formData.video"
                      :file-type="['mp4']"
                      :is-show-tip="false"
                    />

                    <el-text class="w-full" size="small" type="info">
                      支持上传10M以内的mp4格式的视频
                    </el-text>
                  </el-form-item>
                </el-col>
                <!-- <el-col :span="24">
                  <el-form-item>
                    <template #label>
                      <span class="isRevise">
                        设施支持
                        <el-tooltip content=用于移动端的招商展示" placement="top">
                          <Icon class="" icon="fa:question-circle-o" />
                        </el-tooltip>
                      </span>
                    </template>
                    <div>
                      <el-checkbox v-model="checked1" label="中央空调" size="large" />
                      <el-checkbox v-model="checked1" label="电梯" size="large" />
                      <el-checkbox v-model="checked1" label="集中供暖" size="large" />
                      <el-checkbox v-model="checked1" label="安全监控" size="large" />
                      <el-checkbox v-model="checked1" label="员工餐厅" size="large" />
                    </div>
                  </el-form-item>
                </el-col> -->
              </el-row>
            </div>
          </el-tab-pane>
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

  <TagHouseForm ref="formRef_tagInfo" @success="getTypeList" />
  <InvestmenDialog ref="investmenDialogRef" @success="handleSuccess" />
</template>
<script setup lang="ts">
defineOptions({ name: 'RoomForm' })
const message = useMessage() // 消息弹窗
import { TagHouseApi } from '@/api/bus/tag/house'
import * as AreaApi from '@/api/system/area'
import { defaultProps } from '@/utils/tree'
import InvestmenDialog from './InvestmenDialog.vue'
import ColorStatus from './ColorStatus.vue'
import { BuildApi, BuildVO } from '@/api/bus/village'
import { BuildingApi } from '@/api/bus/village/building'
import { FloorApi } from '@/api/bus/village/floor'
import { RoomApi, RoomBVO } from '@/api/bus/village/Room'
import { tr } from 'element-plus/es/locale'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import TagHouseForm from '@/views/setting/house/TagHouseForm.vue'
const { t } = useI18n() // 国际化
const editGardenShow = ref(false)
const addAccountShow = ref(false)
const ParkBuildingTitle = ref('添加楼宇')
const title = ref('新增园区')
const activeIndex = ref('0')
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const areaList = ref([]) // 地区列表
const roomStatusTxt = ref('待租')
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const formData = ref<RoomBVO>({
  id: undefined,
  roomNumber: undefined,
  roomName: undefined,
  roomAliasId: undefined,
  parentRoomId: undefined,
  subRoomCount: undefined,
  subRoomRentCount: undefined,
  buildArea: undefined,
  insideArea: undefined,
  rentalArea: undefined,
  rentalAreaIn: undefined,
  chargingArea: undefined,
  chargingAreaIn: undefined,
  leaseStart: undefined,
  leaseEnd: undefined,
  deliverTime: undefined,
  invitationStatus: undefined,
  investmentPolicy: undefined,
  investmentConditions: undefined,
  images: undefined,
  priceUnit: undefined,
  priceUnitMin: undefined,
  preUnitPrice: undefined,
  preUnitPriceMin: undefined,
  tagIdArr: undefined,
  contractInfo: undefined,
  contractCount: undefined,
  decoration: undefined,
  propertyRight: undefined,
  floorHeight: undefined,
  loadMax: undefined,
  layerId: undefined,
  unitId: undefined,
  buildId: undefined,
  zoneId: undefined,
  villageId: undefined,
  sort: undefined,
  status: '1',
  threeDimensionalId: undefined,
  roomStatus: 0,
  houseType: undefined,
  recordNo: undefined,
  promoterMoney: undefined,
  promoterMoneyUnit: undefined,
  extraConfig: undefined,
  vrLink: undefined,
  video: undefined,
  vrVideoSort: undefined,
  monthHits: undefined,
  splitParentArea: undefined,
  isLock: undefined,
  isUnreal: undefined,
  extraLock: undefined
})

const formRules = reactive({
  villageId: [{ required: true, message: '园区名称不能为空', trigger: 'blur' }],
  buildId: [{ required: true, message: '建筑不能为空', trigger: 'blur' }],
  layerId: [{ required: true, message: '楼层不能为空', trigger: 'blur' }],
  roomName: [{ required: true, message: '房号不能为空', trigger: 'blur' }],
  roomNumber: [{ required: true, message: '房间数字编号不能为空', trigger: 'blur' }],
  rentalArea: [{ required: true, message: '计租面积不能为空', trigger: 'blur' }],
  buildArea: [{ required: true, message: '建筑面积不能为空', trigger: 'blur' }]
  // propertyArea: [{ required: true, message: '产权面积不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const formType = ref('create') // 表单的类型：create - 新增；update - 修改
function cancelClick() {
  editGardenShow.value = false
}
const addAccountFormRef = ref() // 表单 Ref
const formRef_tagInfo = ref()
const add_tagInfo = () => {
  formRef_tagInfo.value.open('create')
}
/** 打开抽屉 */
const open = async (
  status?: string,
  villageId?: string,
  build?: number,
  id?: number,
  form?: any
) => {
  console.log(villageId, 'status', id)
  activeIndex.value = '0'
  formType.value = status as string
  editGardenShow.value = true
  resetForm()
  formData.value.villageId = villageId
  formData.value.layerId = id
  formData.value.buildId = build
  getVillageList()
  getBildList()
  getTypeList()
  getFloorList()
  getPropertyRightNatureList()
  getDecorationList()
  getStatusList()
  getHouseTypeList()
  getPriceUnitList()
  if (status == 'create') {
    title.value = '新增房源'
  } else {
    title.value = '编辑房源'
    console.log(form.tagHouseList, 'form')
    if (form.tagHouseList && form.tagHouseList.length) form.tagIdArr = JSON.parse(form.tagIdArr)
    if (form.isUnreal) form.isUnreal = JSON.stringify(form.isUnreal)
    if (form.invitationStatus) form.invitationStatus = JSON.stringify(form.invitationStatus)
    if (form.status) form.status = JSON.stringify(form.status)
    roomStatusTxt.value =
      form.isLock == 1 ? '锁定' : form.status >= 20 ? '已租' : form.status >= 10 ? '已预订' : '待租'
    if (!form.extraConfig) {
      form.extraConfig = {}
    }
    formData.value = form
    console.log(formData.value)
  }
}

const initData = async () => {
  // 加载区域数据
  areaList.value = await AreaApi.getAreaTree()
}
/** 获取标签列表 **/
const HouseTagList = ref([])
const getTypeList = async () => {
  const data = await TagHouseApi.getTagHouseList({ pageNo: 1, pageSize: 10 })
  HouseTagList.value = data.map((item: any) => {
    return {
      value: item.id,
      label: item.name
    }
  })
}
const VillageList = ref([] as any[])
//获取项目列表
const getVillageList = async () => {
  const data = await BuildApi.getVillageList({ type: villageTypeValue.value })
  console.log(data, 'getVillageList')
  VillageList.value = data.map((item: any) => {
    return {
      value: item.id,
      label: item.name
    }
  })
}
// 获取楼栋列表
const buildingDataList = ref([] as any[])
const getBildList = async () => {
  const data = await BuildingApi.getBuildList({ villageId: formData.value.villageId })
  buildingDataList.value = data.map((item: any) => {
    return {
      value: item.id,
      label: item.buildName
    }
  })
}
// 获取楼层列表
const FloorDataList = ref([] as any[])
const getFloorList = async () => {
  const data = await FloorApi.getLayerList({
    villageId: formData.value.villageId,
    buildId: formData.value.buildId
  })
  FloorDataList.value = data.map((item: any) => {
    return {
      value: item.id,
      label: item.layerName
    }
  })
}
const PropertyRightNatureList = ref([] as any[])
// 产权性质 字典
const getPropertyRightNatureList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.PROPERTYRIGHTNATURE)
    PropertyRightNatureList.value = data
  } finally {
  }
}
const decorationList = ref([] as any[])
const getDecorationList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.DECORATION)
    decorationList.value = data
  } finally {
  }
}

const statusList = ref([] as any[])
const getStatusList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.RESOURCESTATE)
    statusList.value = data
  } finally {
  }
}
// 资源类型
const houseTypeList = ref([] as any[])
const getHouseTypeList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.HOUSETYPE)
    houseTypeList.value = data
  } finally {
  }
}
// 价格单位
const priceUnitList = ref([] as any[])
const getPriceUnitList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.PRICEUNIT)
    priceUnitList.value = data
    if (!formData.value.priceUnitMin) formData.value.priceUnitMin = data[0].value
    if (!formData.value.priceUnit) formData.value.priceUnit = data[0].value
  } finally {
  }
}

const selectvillageId = (value) => {
  console.log(value, 'value')
}

const mycascader = ref()
const changeCasc = () => {
  // if (mycascader.value.getCheckedNodes()[0].data.level == 3) {
  console.log('****', mycascader.value.getCheckedNodes()[0])
  formData.value.provinceId = mycascader.value.getCheckedNodes()[0].pathValues[0]
  formData.value.cityId = mycascader.value.getCheckedNodes()[0].pathValues[1]
  formData.value.districtId = mycascader.value.getCheckedNodes()[0].pathValues[2]
  formData.value.districtTxt = mycascader.value.getCheckedNodes()[0].text
  // }
  // this.$nextTick(() => {
  //   let thsAreaCode = this.$refs['mycascader'].checkedValue
  //   this.form.organizationId = thsAreaCode[thsAreaCode.length - 1] // 取数组的最后一个
  // })
}
const handleSuccess = (data: any) => {
  formData.value.extra_config.department_id = data.department_id
  formData.value.extra_config.department_txt = data.department_txt
  formData.value.extra_config.post_id = data.post_id
  formData.value.extra_config.post_txt = data.post_txt
}

const addAccount = async (id: number) => {
  addAccountShow.value = true
  ParkBuildingTitle.value = '新建账户'
}
const villageIdRef = ref()
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const confirmClick = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(formData.value))
    data.extraConfig = JSON.stringify(data.extraConfig)
    data.tagIdArr = JSON.stringify(data.tagIdArr)
    data.villageName = villageIdRef.value?.currentPlaceholder
    // delete data.tagIdArr
    if (formType.value === 'create') {
      await RoomApi.createRoom(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    } else {
      await RoomApi.updateRoom(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    }
    emit('success', formType.value)
  } finally {
    formLoading.value = false
  }
}

const confirmClickBuild = async () => {
  await addAccountFormRef.value.validate()
  addAccountShow.value = false
}
/** 初始化 **/
onMounted(() => {
  initData()
})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined, //编号,示例值(3871)
    roomNumber: undefined, //房间编号
    roomName: undefined, //房间名称
    roomAliasId: undefined, //房间管理编号,示例值(1)
    parentRoomId: undefined, //父级房间编号,示例值(1)
    subRoomCount: undefined, //房间数量,示例值(1)
    subRoomRentCount: undefined, //子房间已租总数
    buildArea: undefined, //建筑面积,示例值(1.0)
    insideArea: undefined, //套内面积,示例值(1.0)
    rentalArea: undefined, //计租面积,示例值(1.0)
    rentalAreaIn: undefined, //计租面积(在租)
    chargingArea: undefined, //计费面积,示例值(1.0)
    chargingAreaIn: undefined, //计费面积(在租)
    leaseStart: undefined, //租赁开始时间
    leaseEnd: undefined, //租赁结束时间
    deliverTime: undefined, //交付时间
    invitationStatus: undefined, // 1招商,0不招商(出租后默认设置不招商)
    investmentPolicy: undefined, // 招商政策
    investmentConditions: undefined, // 招商条件
    images: undefined, // 图集数组
    priceUnit: undefined, //价格单位
    priceUnitMin: undefined, //低价单位
    preUnitPrice: undefined, //预租单价,示例值(17991)
    preUnitPriceMin: undefined, //最低价格
    tagIdArr: undefined, //房源标签ID数组
    contractInfo: undefined, //合同情况,记录时间,执行情况,用于判断是否逾期
    contractCount: undefined, // 在租合同数，示例值(26264)
    decoration: undefined, // 装修情况
    propertyRight: undefined, // 产权性质
    floorHeight: undefined, // 标准层高,示例值(1)
    loadMax: undefined, // 荷载值
    layerId: undefined, //楼层ID,示例值(1)
    unitId: undefined, //单元ID,示例值(1)
    buildId: undefined, //楼栋ID,示例值(1)
    zoneId: undefined, //分区ID,示例值(1)
    villageId: undefined, //项目ID,示例值(1)
    sort: undefined, //排序,示例值(1)
    status: '1', //状态,示例值(1)
    threeDimensionalId: undefined, //3D模型物体ID,示例值(10845)
    roomStatus: 0, //状态(<10空置状态，<20已预订，<30出租中),示例值(2)
    houseType: undefined, //房源类型,示例值(1)
    recordNo: undefined, //房源备案号
    promoterMoney: undefined, //推广佣金
    promoterMoneyUnit: undefined, //推广佣金单位
    extraConfig: {}, //额外配置
    vrLink: undefined, //VR链接
    video: undefined, //视频链接
    vrVideoSort: undefined, //VR视频导致的排序（VR和视频30，VR20，视频10，没有0）
    monthHits: undefined, //月浏览数（计划任务统计）
    splitParentArea: undefined, //楼层拆分
    isLock: undefined, //是否锁定房源(1锁定，0正常(取消锁定))
    isUnreal: undefined, //0=真实房间;1=非真实房间
    extraLock: undefined //锁定房源面积信息
  }
  formRef.value?.resetFields()
}

// 切换选项卡
const tabSelct = (index: number) => {
  if (index == 1) {
    activeIndex.value = Number(activeIndex.value) + 1 + ''
  } else {
    activeIndex.value = Number(activeIndex.value) - 1 + ''
  }
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
.el-input-number .el-input__inner {
  text-align: left !important;
}
</style>
