<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 新建楼宇 -->
  <el-drawer v-model="editGardenShow" direction="rtl" size="50%" :title="title">
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
        <el-tabs v-model="activeIndex" class="demo-tabs" @tab-click="handleClick">
          <el-tab-pane label="基础配置" name="0">
            <div>
              <div class="Line_title mt-10px">基础信息</div>
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="所属园区" prop="villageId">
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
                  <el-form-item label="建筑编号">
                    <el-input v-model="formData.buildNumber" placeholder="请输入建筑编号" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="建筑名称" prop="buildName">
                    <el-input v-model="formData.buildName" placeholder="请输入建筑名称" />
                  </el-form-item>
                </el-col>

                <el-col :span="16">
                  <el-form-item label="所属地区" prop="districtId">
                    <el-cascader
                      ref="mycascader"
                      v-model="formData.districtId"
                      :options="areaList"
                      :props="defaultProps"
                      class="w-1/1"
                      clearable
                      filterable
                      placeholder="请选择所属地区"
                      @change="changeCasc()"
                    />
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="建筑地址">
                    <el-input v-model="formData.address" placeholder="请输入建筑地址" />
                  </el-form-item>
                </el-col>
                <el-col :span="8"
                  ><el-form-item label="产权性质" prop="propertyRight">
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
                  <el-form-item label="不动产编号">
                    <el-input v-model="formData.estateNumber" placeholder="请输入..." />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="产权编号">
                    <el-input v-model="formData.propertyNumber" placeholder="请输入..." />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="土地编号">
                    <el-input v-model="formData.landNumber" placeholder="请输入..." />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="排序值">
                    <el-input-number
                      v-model="formData.sort"
                      class="!w-100%"
                      :min="1"
                      :max="10"
                      controls-position="right"
                      placeholder="排序值越大展示越靠前"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <div>
              <div class="Line_title mt-10px">建筑面积</div>
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="建筑面积" prop="buildArea">
                    <el-input v-model="formData.buildArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="产权面积" prop="propertyArea">
                    <el-input v-model="formData.propertyArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="可租面积">
                    <el-input v-model="formData.rentableArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="自用面积">
                    <el-input v-model="formData.selfUseArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="配套面积">
                    <el-input v-model="formData.supportingArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="车位面积">
                    <el-input v-model="formData.parkingArea" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="标准层高">
                    <el-input v-model="formData.floorHeight" placeholder="请输入...">
                      <template #append>㎡</template>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <div>
              <div class="Line_title mt-10px">默认收支账户</div>
              <el-row :gutter="20" justify="space-between">
                <el-col :span="12">
                  <el-form-item label="选择账户">
                    <el-select
                      v-model="formData.accountDefault"
                      placeholder="请选择..."
                      @change="changeAccount"
                    >
                      <!-- value-key="id" -->
                      <el-option
                        v-for="item in OrgAccountList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <div class="w-100% flex justify-end">
                    <el-tooltip
                      v-if="formType == 'create'"
                      content="如需新建收支账户，请在新建后编辑楼宇"
                      placement="top"
                    >
                      <el-button disabled @click="addAccount">
                        <Icon icon="ion:add" />
                        新建账户
                      </el-button>
                    </el-tooltip>
                    <el-button v-else @click="addAccount">
                      <Icon icon="ion:add" />
                      新建账户
                    </el-button>
                  </div>
                </el-col>
                <el-drawer
                  v-model="addAccountShow"
                  :title="ParkBuildingTitle"
                  :append-to-body="true"
                  :before-close="handleClose"
                >
                  <el-form
                    ref="addAccountFormRef"
                    :model="addAccountFormData"
                    label-position="top"
                    :rules="addAccountFormRules"
                    label-width="100px"
                    v-loading="addParkformLoading"
                  >
                    <el-form-item label="条目名称" prop="name">
                      <el-input v-model="addAccountFormData.name" placeholder="请输入条目名称" />
                    </el-form-item>
                    <el-form-item label="收款公司" prop="company">
                      <el-input v-model="addAccountFormData.company" placeholder="请输入收款公司" />
                    </el-form-item>
                    <el-form-item label="开户银行" prop="bank">
                      <el-input v-model="addAccountFormData.bank" placeholder="请输入开户银行" />
                    </el-form-item>
                    <el-form-item label="银行账号" prop="bankAccount">
                      <el-input
                        v-model="addAccountFormData.bankAccount"
                        placeholder="请输入银行账号"
                      />
                    </el-form-item>
                    <el-form-item label="总分类账科目" prop="subject">
                      <el-input
                        v-model="addAccountFormData.subject"
                        placeholder="请输入总分类账科目"
                      />
                    </el-form-item>
                    <el-form-item label="应用楼宇" prop="build">
                      <el-tree-select
                        class="w-100%"
                        v-model="addAccountFormData.build"
                        :data="buildingDataList"
                        placeholder="请选择应用楼宇"
                        :render-after-expand="false"
                        show-checkbox
                        multiple
                        node-key="buildId"
                        :props="menuProps"
                      />
                    </el-form-item>
                    <el-form-item label="状态" prop="status">
                      <el-switch
                        v-model="addAccountFormData.status"
                        width="60"
                        inline-prompt
                        active-value="1"
                        inactive-value="0"
                        active-text="开启"
                        inactive-text="关闭"
                      />
                    </el-form-item>
                  </el-form>
                  <template #footer>
                    <div style="flex: auto">
                      <el-button @click="addAccountShow = false">取消</el-button>
                      <el-button type="primary" @click="confirmClickBuild">保存</el-button>
                    </div>
                  </template>
                </el-drawer>
              </el-row>
              <el-row :gutter="20" class="mt-10px" v-if="formData.accountDefault">
                <el-col :span="8">
                  <div class="c-#000000a6 font-size-14px line-height-22px">
                    <div class="c-#000000d9">收款账户：</div>
                    {{ accountInfo.name || '' }}
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="c-#000000a6 font-size-14px line-height-22px">
                    <div class="c-#000000d9">开户银行：</div>
                    {{ accountInfo.bank || '' }}
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="c-#000000a6 font-size-14px line-height-22px">
                    <div class="c-#000000d9">银行账户：</div>
                    {{ accountInfo.bankAccount || '' }}
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-tab-pane>
          <el-tab-pane label="拓展信息" name="1">
            <div>
              <div class="Line_title mt-10px">拓展信息</div>
              <el-row :gutter="20" justify="space-between">
                <el-col :span="12">
                  <el-form-item label="建筑标签">
                    <el-select
                      class="w-100%"
                      v-model="formData.tagInfo"
                      multiple
                      placeholder="请选择建筑标签"
                    >
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
                <el-col :span="8">
                  <el-form-item label="总楼层">
                    <el-input v-model="formData.extraConfig.totalLayer" placeholder="请输入..." />
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item>
                    <template #label>
                      <span class="isRevise">
                        图片
                        <el-tooltip content="建议上传4:3的图片" placement="top">
                          <Icon class="" icon="fa:question-circle-o" />
                        </el-tooltip>
                      </span>
                    </template>
                    <UploadImg v-model="formData.logo" :limit="1" />
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="建筑介绍">
                    <Editor v-model="formData.extraConfig.info" height="150px" />
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
          <el-tab-pane label="营收目标" name="2">
            <div class="flex justify-end pb-10px">
              <el-button type="primary" @click="addTableData">
                <Icon class="mr-5px" icon="ep:plus" />添加目标</el-button
              >
            </div>
            <el-table :data="tableData" border style="width: 100%; margin: 0 auto">
              <el-table-column prop="year" label="年份" align="center" width="180">
                <template #default="scope">
                  <el-select
                    v-if="scope.row.status"
                    v-model="scope.row.year"
                    placeholder="YYYY"
                    @visible-change="yearChange($event)"
                    clearable
                  >
                    <el-option
                      v-for="item in years"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                  <span v-else> {{ scope.row.year }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="target" label="营收目标(万元)" align="center">
                <template #default="scope">
                  <el-input v-if="scope.row.status" v-model="scope.row.target"
                    ><template #append>万元</template></el-input
                  >
                  <span v-else> {{ scope.row.target }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" fixed="right" width="180">
                <template #default="scope">
                  <template v-if="scope.row.status">
                    <el-button @click="EndTableData(scope.row)" link icon="Delete" type="primary"
                      >完成</el-button
                    >
                    <el-button @click="deleteTableData(scope.row)" link icon="Delete" type="primary"
                      >取消</el-button
                    >
                  </template>
                  <el-button
                    v-else
                    @click="deleteTableData(scope.row)"
                    link
                    icon="Delete"
                    type="danger"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </template>
    <template #footer>
      <div class="flex justify-between">
        <div>
          <el-button plain :disabled="activeIndex == 0" @click="tabSelct(0)">上一项</el-button>
          <el-button type="primary" :disabled="activeIndex == 2" @click="tabSelct(1)"
            >下一项</el-button
          >
        </div>
        <div style="flex: auto">
          <el-button @click="cancelClick">取消</el-button>
          <el-button type="primary" @click="confirmClick">保存</el-button>
        </div>
      </div>
    </template>
  </el-drawer>

  <TagBuildForm ref="formRef_tagInfo" @success="getTypeList" />
  <InvestmenDialog ref="investmenDialogRef" @success="handleSuccess" />
</template>
<script setup lang="ts">
defineOptions({ name: 'AddBuilding' })
const message = useMessage() // 消息弹窗
import * as AreaApi from '@/api/system/area'
import { defaultProps } from '@/utils/tree'
import InvestmenDialog from './InvestmenDialog.vue'
import ColorStatus from './ColorStatus.vue'
import { TagBuildApi } from '@/api/bus/tag/build'
import { BuildApi, BuildVO } from '@/api/bus/village'
import { tr } from 'element-plus/es/locale'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import TagBuildForm from '@/views/setting/build/TagBuildForm.vue'
const { t } = useI18n() // 国际化
const editGardenShow = ref(false)
const addAccountShow = ref(false)
const ParkBuildingTitle = ref('添加楼宇')
const title = ref('新增园区')
const activeIndex = ref('0')
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const areaList = ref([]) // 地区列表
const buildingDataList = ref([] as any[])

import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const formData = ref<BuildVO>({
  id: undefined,
  buildNumber: undefined,
  logo: undefined,
  villageId: undefined,
  haveUnit: 0,
  districtTxt: undefined,
  countryId: 0,
  provinceId: undefined,
  cityId: undefined,
  districtId: undefined,
  streetId: 1,
  communityId: 1,
  address: undefined,
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
  revenueTarget: undefined,
  accountDefault: undefined,
  extraConfig: {
    totalLayer: undefined,
    installation: [],
    info: ''
  },
  tagInfo: undefined,
  sort: undefined,
  status: 1,
  threeDimensionalFile: undefined,
  threeDimensionalId: undefined,
  dimensionalBgImg: undefined
})
const accountInfo = ref({
  name: undefined,
  bank: undefined,
  bankAccount: undefined
})
const menuProps = {
  //自定义label
  label: (data: { name: any }) => {
    return data.name || data.buildName // name为你要显示的名称 可以自定义，就是将name替换label
  },
  children: (data: { name: any }) => {
    return data.buildList // name为你要显示的名称 可以自定义，就是将name替换label
  }
}

const anotherName = ref('') //别名
const anotherNameShow = ref(false) // 别名弹窗

const addAccountFormData = ref({
  name: undefined,
  company: undefined,
  bank: undefined,
  bankAccount: undefined,
  subject: undefined,
  build: undefined,
  status: 1
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
const addAccountFormRules = reactive({
  bankAccount: [
    { required: true, pattern: /^(?:[1-9][0-9]*)$/, message: '输入整数', trigger: 'blur' }
  ],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
})
const formRules = reactive({
  villageId: [{ required: true, message: '园区名称不能为空', trigger: 'blur' }],
  buildName: [{ required: true, message: '建筑名称不能为空', trigger: 'blur' }],
  districtId: [{ required: true, message: '所属地区不能为空', trigger: 'blur' }],
  // address: [{ required: true, message: '详细地址不能为空', trigger: 'blur' }],
  propertyRight: [{ required: true, message: '产权性质不能为空', trigger: 'blur' }],
  buildArea: [{ required: true, message: '建筑面积不能为空', trigger: 'blur' }],
  propertyArea: [{ required: true, message: '产权面积不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const formType = ref('') // 表单的类型：create - 新增；update - 修改
function cancelClick() {
  editGardenShow.value = false
  activeIndex.value = '0'
  tableData.value = []
}
const addParkformLoading = ref(false)
const addAccountFormRef = ref() // 表单 Ref

const years = ref([])
const formRef_tagInfo = ref()
const add_tagInfo = () => {
  formRef_tagInfo.value.open('create')
}
/** 打开抽屉 */
const open = async (status: string, id?: number, form?: any) => {
  getTypeList()
  getVillageList()
  getPropertyRightNatureList()
  getOrgAccountList()
  getTree()
  formType.value = status
  editGardenShow.value = true
  resetForm()
  if (status == 'create') {
    title.value = '新建建筑 '
  } else {
    title.value = '编辑建筑 '
    if (form.tagInfo.length && typeof form.tagInfo != 'object') {
      form.tagInfo = JSON.parse(form.tagInfo)
    }
    if (form.revenueTarget) {
      tableData.value = JSON.parse(form.revenueTarget)
      tableData.value.map((item: any) => {
        item.status = false
      })
    }
    formData.value = form
  }
}

const yearChange = async () => {
  var myDate = new Date()
  var startYear = myDate.getFullYear() // 起始年份
  var endYear = myDate.getFullYear() + 20 // 结束年份

  years.value = []
  for (var i = startYear; i <= endYear; i++) {
    years.value.push({ value: i, label: i })
  }
}

const initData = async () => {
  // 加载区域数据
  areaList.value = await AreaApi.getAreaTree()
}
/** 获取标签列表 **/
const typeList = ref([])
const getTypeList = async () => {
  const data = await TagBuildApi.getTagBuildList()
  typeList.value = data.map((item: any) => {
    return {
      value: item.id,
      label: item.name
    }
  })
}
const OrgAccountList = ref([])
const getOrgAccountList = async () => {
  const data = await BuildApi.orgAccountList({ pageNo: 1, pageSize: 10 })
  console.log(data, 'getOrgAccountList')
  OrgAccountList.value = data

  if (formData.value.accountDefault) changeAccount(formData.value.accountDefault)
}

const changeAccount = (id: Number) => {
  const index = OrgAccountList.value.findIndex((item: any) => item.id == id)
  accountInfo.value = OrgAccountList.value[index]
}

/** 获得楼宇 */
const getTree = async () => {
  try {
    const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
    res.villageRespVOS.forEach((item: any) => {
      item.buildId = item.name
      // item.value = item.id
      if (item.buildList.length) {
        item.buildList.forEach((element: any) => {
          element.buildId = element.id
        })
      }
    })
    buildingDataList.value = res.villageRespVOS
  } finally {
  }
}
const VillageList = ref([])
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
const PropertyRightNatureList = ref([])
// 产权性质 字典
const getPropertyRightNatureList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.PROPERTYRIGHTNATURE)
    PropertyRightNatureList.value = data
  } finally {
  }
}

const selectvillageId = (value) => {
  console.log(value, 'value')
}

const mycascader = ref()
const changeCasc = () => {
  console.log('****', mycascader.value.getCheckedNodes()[0])
  formData.value.provinceId = mycascader.value.getCheckedNodes()[0].pathValues[0]
  formData.value.cityId = mycascader.value.getCheckedNodes()[0].pathValues[1]
  formData.value.districtId = mycascader.value.getCheckedNodes()[0].pathValues[2]
  formData.value.districtTxt = mycascader.value.getCheckedNodes()[0].text
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
    data.tagInfo = JSON.stringify(data.tagInfo)
    data.villageName = villageIdRef.value?.currentPlaceholder
    data.revenueTarget = []
    tableData.value.forEach((item: any) => {
      if (!item.status) {
        data.revenueTarget.push({
          year: item.year,
          target: item.target
        })
      }
    })
    data.revenueTarget = JSON.stringify(data.revenueTarget)
    delete data.tagIdArr
    if (formType.value === 'create') {
      await BuildApi.createBuild(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    } else {
      await BuildApi.updateBuild(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    }
    emit('success', formType.value)
  } finally {
    formLoading.value = false
    activeIndex.value = '0'
    tableData.value = []
  }
}

const confirmClickBuild = async () => {
  await addAccountFormRef.value.validate()
  addParkformLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(addAccountFormData.value))
    data.build = JSON.stringify(data.build)
    await BuildApi.orgAccountCreate(data)
    message.success(t('common.updateSuccess'))
    addParkformLoading.value = false
    addAccountShow.value = false
    getOrgAccountList()
    // emit('success')
  } finally {
    addParkformLoading.value = false
  }
}
/** 初始化 **/
onMounted(() => {
  initData()
})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    buildNumber: undefined,
    logo: undefined,
    villageId: undefined,
    haveUnit: 0,
    districtTxt: undefined,
    countryId: 0,
    provinceId: undefined,
    cityId: undefined,
    districtId: undefined,
    streetId: 1,
    communityId: 1,
    address: undefined,
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
    revenueTarget: undefined,
    accountDefault: undefined,
    extraConfig: {
      totalLayer: undefined,
      installation: [],
      info: ''
    },
    tagInfo: undefined,
    sort: undefined,
    status: 1,
    threeDimensionalFile: undefined,
    threeDimensionalId: undefined,
    dimensionalBgImg: undefined
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

const tableData = ref([])
// 新增一行
const addTableData = () => {
  var myDate = new Date()
  var startYear = myDate.getFullYear()
  const newRow = {
    year: startYear,
    target: null,
    status: true
  }
  tableData.value.push(newRow)
}
// 删除
const deleteTableData = (row) => {
  const index = tableData.value.indexOf(row)
  if (index !== -1) {
    tableData.value.splice(index, 1)
  }
}

const EndTableData = (row) => {
  if (!row.year || !row.target) {
    return message.warning('请填写年份和营收目标~')
  }
  let data = checkData()
  if (data) {
    row.status = false
  } else {
    message.warning(row.year + '年份的营收目标您已经添加过了哦~')
  }
}

const checkData = () => {
  for (var i = 0; i < tableData.value.length; i++) {
    for (var j = i + 1; j < tableData.value.length; j++) {
      if (tableData.value[i].year == tableData.value[j].year) {
        return false
      }
    }
  }
  return true
}
</script>
<style scoped lang="scss">
.box {
  position: relative;

  .icon {
    position: absolute;
    bottom: 10px;
    right: 19px;
  }
}
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
