<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 园区编辑 -->
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
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <span class="font-size-14px"> 基本信息 </span>
          </template>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="园区名称" prop="name">
                <el-input v-model="formData.name" placeholder="请输入园区名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="园区简称" prop="shortName">
                <el-input v-model="formData.shortName" placeholder="请输入园区简称" />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="归属地" prop="districtId">
                <el-cascader
                  ref="mycascader"
                  v-model="formData.districtId"
                  :options="areaList"
                  :props="defaultProps"
                  class="w-1/1"
                  clearable
                  filterable
                  placeholder="请选择城市"
                  @change="changeCasc()"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12"
              ><el-form-item label="园区标签">
                <el-select v-model="formData.tagIdArr" multiple placeholder="请选择园区标签">
                  <el-option
                    v-for="item in typeList"
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
            <el-col :span="12">
              <el-form-item label="详细地址" prop="address">
                <el-input v-model="formData.address" placeholder="请输入详细地址" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目简介">
                <el-input
                  :rows="3"
                  type="textarea"
                  v-model="formData.describe"
                  placeholder="请输入项目简介"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-card>

        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
          v-if="formType == 'create'"
        >
          <template #header>
            <span class="font-size-14px"> 楼宇管理 </span>
          </template>
          <el-row :gutter="20">
            <el-col
              :span="4"
              v-for="(item, index) in formData.buildList"
              :key="index"
              class="!flex justify-around flex-col !w-100px h-100px b-rd-8px border-1px border-solid border-#e5e5e5 cursor-pointer !p-20px"
              @click="editParkFloor(item)"
            >
              <div class="">{{ item.name }}</div>
              <div class="text-right">{{ item.layerList.length }} 层</div>
            </el-col>
            <el-col :span="4" class="">
              <div
                class="!w-100px h-100px b-rd-8px flex justify-center flex-items-center border-1px border-solid border-#e5e5e5 cursor-pointer"
                @click="addParkBuilding()"
                ><Icon class="" icon="ep:plus" size="32" color="#666666" />
              </div>
            </el-col>
          </el-row>
          <el-drawer
            v-model="addParkBuildingShow"
            :title="ParkBuildingTitle"
            :append-to-body="true"
            :before-close="handleClose"
          >
            <el-form
              ref="addParkBuildingFormRef"
              :model="addParkBuildingFormData"
              label-position="top"
              :rules="addParkBuildingFormRules"
              label-width="100px"
              v-loading="addParkformLoading"
            >
              <el-form-item label="楼宇编号" prop="number">
                <el-input v-model="addParkBuildingFormData.number" placeholder="请输入......" />
              </el-form-item>
              <el-form-item label="楼宇名称" prop="name">
                <el-input v-model="addParkBuildingFormData.name" placeholder="请输入......" />
              </el-form-item>
              <template v-if="!buildingDataList.length">
                <el-form-item label="楼层" prop="ground">
                  <el-input v-model="addParkBuildingFormData.ground" placeholder="请输入......">
                    <template #prepend>
                      <span class="c-#929292">地面</span>
                    </template>
                    <template #append>
                      <span class="c-#929292">层</span>
                    </template>
                  </el-input>
                </el-form-item>
                <el-form-item label="" prop="underground">
                  <el-input
                    v-model="addParkBuildingFormData.underground"
                    placeholder="请输入......"
                  >
                    <template #prepend>
                      <span class="c-#929292">地下</span>
                    </template>
                    <template #append>
                      <span class="c-#929292">层</span>
                    </template>
                  </el-input>
                </el-form-item>
                <el-form-item label="每层房间数" prop="roomNumber">
                  <el-input
                    v-model="addParkBuildingFormData.roomNumber"
                    placeholder="请输入......"
                  />
                </el-form-item>
                <el-form-item>
                  <el-button
                    class="w-100% m-t-20px"
                    type="primary"
                    :disabled="addParkBuildingDisabled"
                    @click="generate"
                    >生成</el-button
                  >
                </el-form-item>
              </template>

              <div v-if="buildingDataList.length">
                <el-row justify="space-between">
                  <el-col :span="10">楼层 </el-col>
                  <el-col :span="8" class="text-right"
                    ><el-link class="!c-#3288F3" @click="resetBuillage">重新生成</el-link></el-col
                  >
                </el-row>
                <div class="villageDataList p-20px bg-#F7F7F7 b-rd-8px m-t-20px">
                  <div class="length">共{{ buildingDataList.length }}层</div>
                  <div class="item" v-for="(item, index) in buildingDataList" :key="index">
                    <el-row
                      :gutter="20"
                      class="m-t-20px bg-#fff p-20px p-t-12px p-b-12px b-rd-8px"
                      justify="space-between"
                    >
                      <el-col :span="16" class="icon flex flex-items-center">
                        <Icon icon="fa-solid:bars" size="16" color="#666666" class="m-r-10px" />
                        {{ item.name }}
                      </el-col>
                      <el-col :span="2">
                        <div class="flex justify-end flex-items-center">
                          <el-popover
                            placement="top"
                            :visible="item.anotherNameShow"
                            :width="400"
                            trigger="click"
                          >
                            <template #reference>
                              <Icon
                                icon="ep:edit-pen"
                                size="16"
                                color="#3288F3"
                                @click="showAnotherName(index)"
                                class="m-r-10px cursor-pointer"
                              />
                            </template>
                            <el-form label-width="auto" class="demo-ruleForm">
                              <el-form-item>
                                <el-input
                                  v-model="anotherName"
                                  type="text"
                                  placeholder="自定义标题"
                                  autocomplete="off"
                                />
                              </el-form-item>
                              <el-form-item>
                                <el-button @click="anotherNameColse(index)">取消</el-button>
                                <el-button type="primary" @click="anotherNameSubmit(index)"
                                  >确定</el-button
                                >
                              </el-form-item>
                            </el-form>
                          </el-popover>

                          <Icon
                            icon="ep:close"
                            size="16"
                            color="#3288F3"
                            class="m-l-15px cursor-pointer"
                            @click="deleteBuillding(index)"
                          />
                        </div>
                      </el-col>
                    </el-row>
                    <template v-if="item.roomList && item.roomList.length">
                      <div
                        v-for="(itemRoom, indexRoom) in item.roomList"
                        :key="indexRoom"
                        class="m-l-20px"
                      >
                        <el-row
                          :gutter="20"
                          class="m-t-20px bg-#fff p-20px p-t-12px p-b-12px b-rd-8px"
                          justify="space-between"
                        >
                          <el-col :span="16" class="icon flex flex-items-center">
                            <Icon icon="fa:building" size="16" color="#666666" class="m-r-10px" />
                            {{ itemRoom.name }}
                          </el-col>
                          <el-col :span="2">
                            <div class="flex justify-end flex-items-center">
                              <el-popover
                                placement="top"
                                :visible="itemRoom.anotherNameShow"
                                :width="400"
                                trigger="click"
                              >
                                <template #reference>
                                  <Icon
                                    icon="ep:edit-pen"
                                    size="16"
                                    color="#3288F3"
                                    @click.stop="showAnotherName(index, indexRoom)"
                                    class="m-r-10px"
                                  />
                                </template>
                                <el-form label-width="auto" class="demo-ruleForm">
                                  <el-form-item>
                                    <el-input
                                      v-model="anotherName"
                                      type="text"
                                      placeholder="自定义标题"
                                      autocomplete="off"
                                    />
                                  </el-form-item>
                                  <el-form-item>
                                    <el-button @click="anotherNameColse(index, indexRoom)"
                                      >取消</el-button
                                    >
                                    <el-button
                                      type="primary"
                                      @click="anotherNameSubmit(index, indexRoom)"
                                      >确定</el-button
                                    >
                                  </el-form-item>
                                </el-form>
                              </el-popover>

                              <Icon
                                icon="ep:close"
                                size="16"
                                color="#3288F3"
                                class="m-l-15px"
                                @click="deleteBuillding(index, indexRoom)"
                              />
                            </div>
                          </el-col>
                        </el-row>
                      </div>
                    </template>
                  </div>
                </div>
              </div>
            </el-form>
            <template #footer>
              <div style="flex: auto">
                <el-button @click="addParkBuildingShow = false">取消</el-button>
                <el-button type="primary" @click="confirmClickBuild">保存</el-button>
              </div>
            </template>
          </el-drawer>
        </el-card>

        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <span class="font-size-14px">
              图片<span class="c-#929292 m-l-20px"
                >推荐上传16：9比例的图片, 可以拖动改变图片排序</span
              >
            </span>
          </template>
          <UploadImgs
            :drag="true"
            v-model="formData.extraConfig.iamges"
            height="100px"
            width="100px"
          />
        </el-card>
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <el-collapse accordion>
            <el-collapse-item name="1">
              <template #title>
                拓展信息
                <span class="c-#929292 m-l-20px"
                  >拓展信息将用于在招商平台展示，当无需使用招商平台时可以不进行填写</span
                >
              </template>
              <el-row :gutter="20">
                <el-col :span="6">
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
                <el-col :span="6">
                  <el-form-item label="排序值">
                    <!-- <el-input v-model="" /> -->
                    <el-input-number
                      class="!w-100%"
                      v-model="formData.sort"
                      placeholder="排序值越大展示越靠前"
                      controls-position="right"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="竣工时间">
                    <el-date-picker
                      class="w-100%"
                      v-model="formData.extraConfig.completedTime"
                      type="date"
                      size="default"
                      placeholder="选择日期"
                      value-format="yyyy-MM-dd"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="标准层高">
                    <el-input
                      v-model="formData.extraConfig.floor_height"
                      placeholder="请输入标准层高"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="物业">
                    <el-input v-model="formData.extraConfig.ownerName" />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="物业费">
                    <el-input
                      v-model="formData.extraConfig.ownerCost"
                      placeholder="例：5 元/㎡/月"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="车位数量">
                    <el-input
                      v-model="formData.extraConfig.parkingNumber"
                      placeholder="例：200个"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="车位月租金">
                    <el-input
                      v-model="formData.extraConfig.parkingMonthlyRent"
                      placeholder="请输入车位月租金"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="空调">
                    <el-input
                      v-model="formData.extraConfig.airConditioner"
                      placeholder="例：中央空调"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="空调费">
                    <el-input
                      v-model="formData.extraConfig.airConditionerCost"
                      placeholder="例：按用量计算"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="空调开放时间">
                    <el-input
                      v-model="formData.extraConfig.airConditionerTime"
                      placeholder="例：夏冬8:00-20:00"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="电梯">
                    <el-input v-model="formData.extraConfig.elevator" placeholder="例：4台" />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="网络">
                    <el-input v-model="formData.extraConfig.network" placeholder="例：电信光纤" />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="入驻企业">
                    <el-input v-model="formData.extraConfig.settledEnterprise" />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="招商时间">
                    <el-input
                      v-model="formData.extraConfig.attractInvestmentTime"
                      placeholder="例：早8点到晚7点"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="招商人员">
                    <el-row style="row-gap: 4px" class="investmen-personne" @click="showPerson()">
                      <span
                        v-if="!formData.extraConfig.department_id"
                        style="color: rgb(191, 191, 191)"
                        >请选择人员</span
                      >
                      <div v-else>
                        <el-col class="p-l-2px p-r-2px">
                          <div
                            class="p-l-7px p-r-7px b-#d9d9d9 b-1 border-solid border-rd-5px bg-#fafafa font-size-12px !flex-none"
                          >
                            <span>部门：</span>
                            <span>管理部门</span>
                          </div>
                        </el-col>
                        <el-col class="p-l-2px p-r-2px">
                          <div
                            class="p-l-7px p-r-7px b-#d9d9d9 b-1 border-solid border-rd-5px bg-#fafafa font-size-12px"
                          >
                            <span> 岗位：</span>
                            <span>工程师、工程师</span>
                          </div>
                        </el-col>
                        <el-col class="closeCircle">
                          <Icon class="" icon="ep:circle-close" />
                        </el-col>
                      </div>
                    </el-row>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="上传视频">
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
              </el-row>
            </el-collapse-item>
          </el-collapse>
        </el-card>
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <span class="font-size-14px"> 房源状态颜色配置 </span>
          </template>
          <ColorStatus ref="colorStatus" :list="formData.roomStatusColor" @success="setColor" />
        </el-card>
        <!-- <el-card
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <el-tabs class="demo-tabs">
            <el-tab-pane label="3D模型" name="first">User</el-tab-pane>
            <el-tab-pane label="2D鸟瞰图" name="second">Config</el-tab-pane>
          </el-tabs>
        </el-card> -->
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

  <InvestmenDialog ref="investmenDialogRef" @success="handleSuccess" />
</template>
<script setup lang="ts">
defineOptions({ name: 'EditGarden' })
const message = useMessage() // 消息弹窗
import * as AreaApi from '@/api/system/area'
import { defaultProps } from '@/utils/tree'
import InvestmenDialog from './InvestmenDialog.vue'
import ColorStatus from './ColorStatus.vue'
import { TagVillageApi } from '@/api/bus/tag/village'
import { BuildApi, VillageBVO } from '@/api/bus/village'
import TagVillageForm from '@/views/setting/village/TagVillageForm.vue'
import { tr } from 'element-plus/es/locale'
const { t } = useI18n() // 国际化
const editGardenShow = ref(false)
const addParkBuildingShow = ref(false)
const ParkBuildingTitle = ref('添加楼宇')
const title = ref('新增园区')
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const areaList = ref([]) // 地区列表
const buildingDataList = ref([] as any[])
const formData = ref<VillageBVO>({})

const anotherName = ref('') //别名
const anotherNameShow = ref(false) // 别名弹窗

const addParkBuildingFormData = ref({
  name: undefined,
  number: undefined,
  underground: undefined,
  ground: undefined,
  roomNumber: undefined,
  sort: 1
})
const validateUnderground = (rule: any, value: any, callback: any) => {
  if (value > 10) {
    callback(new Error('地下楼层不能超过10层'))
  } else if (value == 0) {
    callback()
  } else {
    callback()
  }
}
const color = ref('rgb(169, 220, 253)')
const addParkBuildingFormRules = reactive({
  number: [
    {
      pattern: /^(?:[1-9][0-9]*)$/,
      message: '输入整数',
      trigger: 'blur'
    }
  ],
  name: [{ required: true, message: '楼宇名称不能为空', trigger: 'blur' }],
  ground: [{ required: true, pattern: /^(?:[1-9][0-9]*)$/, message: '输入整数', trigger: 'blur' }],
  underground: [
    { required: true, pattern: /^(?:[0-9][0-9]*)$/, message: '输入整数', trigger: 'blur' },
    {
      validator: validateUnderground,
      message: '地下楼层不能超过10层',
      trigger: 'blur'
    }
  ],
  roomNumber: [{ pattern: /^(?:[1-9][0-9]*)$/, message: '输入整数', trigger: 'blur' }]
})
const formRules = reactive({
  name: [{ required: true, message: '园区名称不能为空', trigger: 'blur' }],
  shortName: [{ required: true, message: '园区简称不能为空', trigger: 'blur' }],
  districtId: [{ required: true, message: '归属地不能为空', trigger: 'blur' }],
  address: [{ required: true, message: '详细地址不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const formType = ref('') // 表单的类型：create - 新增；update - 修改
function cancelClick() {
  editGardenShow.value = false
}
const addParkformLoading = ref(false)
const addParkBuildingFormRef = ref() // 表单 Ref
const generate = async (data: any) => {
  await addParkBuildingFormRef.value.validate()
  buildingDataList.value = []
  addParkformLoading.value = true
  const buildingData = ref([] as any[])
  for (let i = 1; i <= addParkBuildingFormData.value.ground; i++) {
    buildingData.value.push({
      name: i.toString(),
      number: i,
      roomList: [],
      sort: 0
    })
    for (let a = 1; a <= addParkBuildingFormData.value.roomNumber; a++) {
      const name = a
        .toString()
        .padStart(
          addParkBuildingFormData.value.roomNumber.length > 1
            ? addParkBuildingFormData.value.roomNumber.length
            : '2',
          '0'
        )
      buildingData.value[i - 1].roomList.push({
        name: i.toString() + name,
        number: Number(i + name),
        sort: 0
      })
    }
  }
  const undergroundList = ref([] as any[])
  for (let u = 1; u <= addParkBuildingFormData.value.underground; u++) {
    undergroundList.value.push({
      name: 'B' + u.toString(),
      number: Number('-' + u.toString()),
      sort: 0
    })
  }
  buildingDataList.value = buildingData.value.concat(undergroundList.value)
  addParkformLoading.value = false
}

const mycascader = ref()
const changeCasc = () => {
  // if (mycascader.value.getCheckedNodes()[0].data.level == 3) {
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

const addParkBuildingDisabled = computed(() => {
  if (
    addParkBuildingFormData.value.name &&
    addParkBuildingFormData.value.underground &&
    addParkBuildingFormData.value.ground
  ) {
    return false
  } else {
    return true
  }
})

import { getAccessToken } from '@/utils/auth'

const formRef_tagInfo = ref()
const add_tagInfo = () => {
  formRef_tagInfo.value.open('create')
}

/** 打开抽屉 */
const open = async (status: string, id?: number, form?: any) => {
  formType.value = status
  editGardenShow.value = true
  resetForm()
  if (status == 'create') {
    title.value = '新增园区 '
    formData.value.type = id
  } else {
    title.value = '编辑园区 '
    // if (typeof form.roomStatusColor == 'string') {
    //   form.roomStatusColor = JSON.parse(form.roomStatusColor)
    // }
    form.roomStatusColor =
      typeof form.roomStatusColor == 'string'
        ? JSON.parse(form.roomStatusColor)
        : form.roomStatusColor
    form.tagIdArr = typeof form.tagIdArr == 'string' ? JSON.parse(form.tagIdArr) : form.tagIdArr
    form.extraConfig =
      typeof form.extraConfig == 'string' ? JSON.parse(form.extraConfig) : form.extraConfig
    formData.value = form
    // formLoading.value = true
    // try {
    //   // formData.value = await BuildApi.getBuild(id)
    // } finally {
    //   formLoading.value = false
    // }
  }
}

const anotherNameColse = (num: Number, itemRoom: Number) => {
  if (itemRoom >= 0) {
    buildingDataList.value[num].roomList[itemRoom].anotherNameShow = false
  } else {
    buildingDataList.value[num].anotherNameShow = false
  }
  anotherName.value = ''
}

const showAnotherName = (num: Number, itemRoom: Number) => {
  if (itemRoom >= 0) {
    buildingDataList.value[num].roomList[itemRoom].anotherNameShow = true
    buildingDataList.value.forEach((item, index) => {
      if (num != index) {
        item.room.forEach((itemRoom, index) => {
          if (itemRoom.anotherNameShow) {
            itemRoom.anotherNameShow = false
          }
        })
      }
    })
  } else {
    buildingDataList.value[num].anotherNameShow = true
    buildingDataList.value.forEach((item, index) => {
      if (num != index) {
        item.anotherNameShow = false
      }
    })
  }
  anotherName.value = ''
}

const anotherNameSubmit = async (num: Number, itemRoom: Number) => {
  if (itemRoom >= 0) {
    buildingDataList.value[num].roomList[itemRoom].name =
      buildingDataList.value[num].roomList[itemRoom].number + '(' + anotherName.value + ')'
  } else {
    buildingDataList.value[num].name =
      buildingDataList.value[num].number + '(' + anotherName.value + ')'
  }
  anotherNameColse(num, itemRoom)
}

const deleteBuillding = async (num: Number, itemRoom: Number) => {
  if (itemRoom >= 0) {
    buildingDataList.value[num].roomList.splice(itemRoom, 1)
  } else {
    buildingDataList.value.splice(num, 1)
  }
}

const resetBuillage = async () => {
  buildingDataList.value = []
  addParkBuildingFormData.value.ground = undefined
  addParkBuildingFormData.value.underground = undefined
  addParkBuildingFormData.value.roomNumber = undefined
}

const initData = async () => {
  // 加载区域数据
  areaList.value = await AreaApi.getAreaTree()
}
/** 获取标签列表 **/
const typeList = ref([])
const getTypeList = async () => {
  const data = await TagVillageApi.getTagVillagList()
  typeList.value = data.map((item: any) => {
    return {
      value: item.id,
      label: item.name
    }
  })
}

const handleSuccess = (data: any) => {
  formData.value.extraConfig.department_id = data.department_id
  formData.value.extraConfig.department_txt = data.department_txt
  formData.value.extraConfig.post_id = data.post_id
  formData.value.extraConfig.post_txt = data.post_txt
}

const addParkBuilding = async (id: number) => {
  addParkBuildingShow.value = true
  if (id) {
    ParkBuildingTitle.value = '编辑楼宇'
  } else {
    if (formData.value.buildList.length >= 3) {
      return message.warning('更多房源导入请在“房源管理“中使用excel表格导入。')
    }
    ParkBuildingTitle.value = '添加楼宇'
  }
  addParkBuildingFormData.value = {
    id: id,
    name: undefined,
    number: undefined,
    sort: 0,
    roomList: []
  }
  buildingDataList.value = []
}

const editParkFloor = async (item: Object) => {
  addParkBuildingShow.value = true
  ParkBuildingTitle.value = '编辑楼层'
  addParkBuildingFormData.value = {
    name: item.name,
    number: item.number,
    sort: item.sort,
    roomList: item.list
  }
  buildingDataList.value = item.list
}

const setColor = async (data: any) => {
  formData.value.roomStatusColor = data
}

const investmenDialogRef = ref()
const showPerson = async () => {
  investmenDialogRef.value.open()
}

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const confirmClick = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(formData.value))
    console.log(data, 'data')
    data.roomStatusColor = JSON.stringify(data.roomStatusColor)
    data.tagIdArr = JSON.stringify(data.tagIdArr)
    data.extraConfig = JSON.stringify(data.extraConfig)
    if (formType.value === 'create') {
      let daa1 = await BuildApi.createvillage(data)
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

const confirmClickBuild = async () => {
  if (!buildingDataList.value.length) {
    message.error('请先生成楼层信息~')
    return
  }
  if (formData.value.buildList === undefined) {
    formData.value.buildList = []
  }
  let list = {
    number: addParkBuildingFormData.value.number,
    name: addParkBuildingFormData.value.name,
    layerList: buildingDataList.value,
    sort: addParkBuildingFormData.value.sort
  }
  formData.value.buildList.push(list)
  addParkBuildingShow.value = false
}
/** 初始化 **/
onMounted(() => {
  initData()
  getTypeList()
  // defaultProps.emitPath = true
})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined, //编号,示例值(3871)
    name: undefined, //项目名称,示例值(张三)
    shortName: undefined, //项目名称缩写,示例值(芋艿)
    describe: undefined, //项目介绍
    districtTxt: undefined, //项目省市区地址
    address: undefined, //详细地址
    lat: undefined, //经度
    lng: undefined, //纬度
    managementArea: undefined, //管理面积
    rentableArea: undefined, //可招商面积
    roomRentableCount: undefined, //可招商房源数量
    roomCount: undefined, //总房源数量,
    tagIdArr: [],
    wechatNumber: undefined, //项目公众号的微信号
    status: 1, //状态
    threeDimensionalFile: undefined, //3D模型
    threeDimensionalId: undefined, //3D模型id
    dimensionalBgImg: undefined, //楼宇图片
    roomStatusColor: [
      {
        color: '#a9dcfd',
        isCustom: 0,
        limit: 0,
        title: '空置中',
        type: 'vacant'
      },
      {
        color: '#1890ff',
        isCustom: 0,
        limit: 0,
        title: '招商中',
        type: 'investment'
      },
      {
        color: '#ce9ced',
        isCustom: 0,
        limit: 0,
        title: '已锁定',
        type: 'lock'
      },
      {
        color: '#FFA940',
        isCustom: 0,
        limit: 0,
        title: '拖欠经营',
        type: 'overdue'
      },
      {
        color: '#fecb85',
        isCustom: 0,
        limit: 90,
        title: '90日内到期',
        type: 'expire'
      },
      {
        color: '#fdac93',
        isCustom: 0,
        limit: 30,
        title: '30日内到期',
        type: 'expire'
      },
      {
        color: '#ff7875',
        isCustom: 0,
        limit: 0,
        title: '已到期',
        type: 'expired'
      }
    ], //房源到期的颜色值数组
    orgId: 1, //当前管理的机构id
    countryId: 0, //国家id
    provinceId: undefined, //省id
    cityId: undefined, //市id
    districtId: undefined, //区id
    streeId: 1, //街道/乡镇id
    communityId: 1, //社区、村id
    roomMinPrice: undefined, //房间最低单价
    roomAveragePrice: undefined, //房间平均单价
    trafficInfo: undefined, //附近公交交通相关信息
    type: undefined, //业态(项目类型
    extraConfig: {
      completedTime: undefined,
      floor_height: undefined,
      ownerName: undefined,
      ownerCost: undefined,
      parkingNumber: undefined,
      parkingMonthlyRent: undefined,
      airConditioner: undefined,
      airConditionerCost: undefined,
      airConditionerTime: undefined,
      elevator: undefined,
      network: undefined,
      settledEnterprise: undefined,
      attractInvestmentTime: undefined,
      department_id: undefined
    }, //额外扩展配置
    vrLink: undefined, //vr链接
    video: undefined, //视频地址
    vrVideoSort: 0, //vr视频排序
    monthHits: 0, //月浏览量
    sort: undefined, // 排序值
    microServiceConfig: undefined, //围绕项目服务的子项目配置信息
    buildList: []
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
