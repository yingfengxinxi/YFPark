<template>
  <ElDrawer :title="dialogTitle" v-model="dialogVisible" size="60%" :show-close="false">
    <template #header>
      <div>
        <div class="flex">
          <div>{{ dialogTitle }}</div>
        </div>
        <el-tabs v-model="tabType" @tab-click="switchUserInfo" class="m-t-20px">
          <el-tab-pane label="基本信息" name="basicInformation" />
          <el-tab-pane label="使用信息" name="usageInformation" />
          <el-tab-pane label="保养信息" name="maintenanceInformation" />
          <el-tab-pane label="折旧信息" name="depreciationInformation" />
          <el-tab-pane label="操作记录" name="relatedKnowledgeBase" v-if="formType == 'detail'" />
        </el-tabs>
      </div>
    </template>
    <el-scrollbar @scroll="scroll" ref="scroll_ref">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
        :disabled="disabled"
        label-position="top"
        class="w-95%"
      >
        <el-row :gutter="20" ref="basicInformation">
          <el-col :span="8">
            <el-form-item label="资产分类" prop="type">
              <el-tree-select
                v-model="formData.type"
                :data="locationList"
                :render-after-expand="false"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                placeholder="请选择资产分类"
                clearable
                :check-strictly="true"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资产名称" prop="name">
              <el-input v-model="formData.name" placeholder="请输入资产名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资产编码" prop="propertyNumber">
              <el-input v-model="formData.propertyNumber" placeholder="已开启自动编码" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="品牌" prop="brand">
              <el-input v-model="formData.brand" placeholder="请输入品牌" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="型号" prop="modelName">
              <el-input v-model="formData.modelName" placeholder="请输入型号" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备序列号" prop="deviceCode">
              <el-input v-model="formData.deviceCode" placeholder="请输入设备序列号" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="管理员" prop="adminId">
              <el-select
                v-model="formData.adminId"
                placeholder="请选择管理员"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                  @click="handleAdminChange(item)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所在位置" prop="positionId">
              <el-tree-select
                v-model="formData.positionId"
                :data="positionList"
                :render-after-expand="false"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                placeholder="请选择所在位置"
                clearable
                :check-strictly="true"
                class="!w-100%"
                @node-click="handlePositionChange"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="购置时间" prop="purchaseTime">
              <el-date-picker
                v-model="formData.purchaseTime"
                type="date"
                value-format="x"
                placeholder="选择购置时间"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="购置方式" prop="purchaseType">
              <el-select
                v-model="formData.purchaseType"
                placeholder="请选择购置方式"
                class="!w-100%"
              >
                <el-option
                  v-for="dict in getIntDictOptions(DICT_TYPE.PROPERTY_PURCHASETYPE)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="购置金额(含税)" prop="purchaseAmount">
              <el-input-number
                min="0"
                controls-position="right"
                v-model="formData.purchaseAmount"
                placeholder="请输入购置金额(含税)"
                class="!w-1/1"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="入库时间" prop="stockTime">
              <el-date-picker
                v-model="formData.stockTime"
                type="date"
                value-format="x"
                placeholder="选择入库时间"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计使用期限(月)" prop="expectMonths">
              <el-input-number
                :min="0"
                placeholder="请输入预计使用期限(月)"
                v-model="formData.expectMonths"
                :step="1"
                controls-position="right"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="formData.remark"
                type="textarea"
                rows="4"
                placeholder="请输入备注"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="资产照片" prop="imageUrl">
              <UploadImg
                v-model="formData.imageUrl"
                draggable="false"
                height="80px"
                width="100%"
                class="min-w-80px"
              />
              <!-- <el-input v-model="formData.imageUrl" placeholder="请输入资产图片url" /> -->
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <el-row :gutter="20" ref="usageInformation">
          <el-col :span="8" v-if="viewType != 'update'">
            <el-form-item label="使用人" prop="userId">
              <el-select
                v-model="formData.userId"
                placeholder="请选择使用人"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" v-if="viewType != 'update'">
            <el-form-item label="使用部门" prop="departmentId">
              <el-tree-select
                v-model="formData.departmentId"
                :data="deptTree"
                :props="defaultProps"
                filterable
                check-strictly
                node-key="id"
                placeholder="请选择使用部门"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="目标房源" prop="buildBind">
              <el-tree-select
                v-model="roomName"
                lazy
                :load="loadNode"
                :props="props"
                :data="deptList"
                class="!w-100%"
                placeholder="请选择目标房源"
                @node-click="handleNodeClick"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8" v-if="viewType != 'update'">
            <el-form-item label="领用日期" prop="receiveTime">
              <el-date-picker
                v-model="formData.receiveTime"
                type="date"
                value-format="x"
                placeholder="选择领用日期"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <el-row :gutter="20" ref="maintenanceInformation">
          <el-col :span="8">
            <el-form-item label="保养到期时间" prop="maintainTime">
              <el-date-picker
                v-model="formData.maintainTime"
                type="date"
                value-format="x"
                placeholder="选择保养到期时间"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="保养说明" prop="maintainInfo">
              <el-input
                v-model="formData.maintainInfo"
                type="textarea"
                rows="4"
                placeholder="请输入保养说明"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <el-row :gutter="20" ref="depreciationInformation">
          <el-col :span="8">
            <el-form-item label="预计折旧期限(月)" prop="depreciationMonths">
              <el-input-number
                :min="0"
                v-model="formData.depreciationMonths"
                :step="1"
                placeholder="请输入预计折旧期限(月)"
                controls-position="right"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <el-row :gutter="20" ref="relatedKnowledgeBase">
          <el-col :span="24">
            <el-table :data="operateLogData" border>
              <el-table-column label="操作人" prop="operateUser" width="100" />
              <el-table-column label="资产操作类型" align="center" prop="logType" width="160">
                <template #default="scope">
                  <dict-tag :type="DICT_TYPE.PROPERTY_TYPE" :value="scope.row.logType" />
                </template>
              </el-table-column>
              <el-table-column label="操作内容" prop="operateContent" />
              <el-table-column
                label="操作时间"
                prop="operateTime"
                width="160"
                :formatter="dateFormatter"
              />
            </el-table>
            <Pagination
              :total="total"
              v-model:page="queryParams.pageNo"
              v-model:limit="queryParams.pageSize"
              @pagination="getOperateLogData"
            />
          </el-col>
        </el-row>
      </el-form>
    </el-scrollbar>
    <template #footer v-if="formType !== 'detail'">
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { useUserStore } from '@/store/modules/user'
import { BuildApi } from '@/api/bus/village'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { TabsPaneContext } from 'element-plus'
import { PropertyOperateLogApi } from '@/api/bus/property/propertyOperateLog'

/** 资产管理 表单 */
defineOptions({ name: 'PropertyForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const deptTree = ref() // 部门树形结构

const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  propertyNumber: undefined,
  labelLink: undefined,
  type: undefined,
  name: undefined,
  status: undefined,
  brand: undefined,
  modelName: undefined,
  deviceCode: undefined,
  processCode: undefined,
  adminId: undefined,
  adminName: undefined,
  adminUid: undefined,
  villageId: undefined,
  buildId: undefined,
  roomId: undefined,
  buildBind: undefined,
  positionId: undefined,
  positionName: undefined,
  purchaseTime: undefined,
  purchaseType: undefined,
  purchaseAmount: undefined,
  stockTime: undefined,
  expectMonths: undefined,
  remark: undefined,
  imageHash: undefined,
  imageUrl: undefined,
  userId: undefined,
  departmentId: undefined,
  receiveTime: undefined,
  maintainTime: undefined,
  maintainInfo: undefined,
  depreciationMonths: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  knowledgeBase: undefined
})
const disabled = ref(false)
const formRules = reactive({
  type: [{ required: true, message: '资产分类不能为空', trigger: 'blur' }],
  brand: [{ required: true, message: '品牌不能为空', trigger: 'blur' }],
  adminId: [{ required: true, message: '管理员不能为空', trigger: 'blur' }],
  positionId: [{ required: true, message: '所在位置不能为空', trigger: 'blur' }],
  purchaseType: [{ required: true, message: '购置方式不能为空', trigger: 'blur' }]
  // status: [{ required: true, message: '资产状态不能为空', trigger: 'blur' }],
  // adminUid: [{ required: true, message: '管理员uid不能为空', trigger: 'blur' }],
  // purchaseAmount: [{ required: true, message: '购置金额(含税)不能为空', trigger: 'blur' }],
  // expectMonths: [{ required: true, message: '预计使用期限(月)不能为空', trigger: 'blur' }],
  // userId: [{ required: true, message: '使用人不能为空', trigger: 'blur' }],
  // depreciationMonths: [{ required: true, message: '预计折旧期限(月)不能为空', trigger: 'blur' }],
  // cuserUid: [{ required: true, message: '操作人uid不能为空', trigger: 'blur' }],
  // muserUid: [{ required: true, message: '修改人uid不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const tabType = ref('basicInformation')
const basicInformation = ref()
const usageInformation = ref()
const maintenanceInformation = ref()
const depreciationInformation = ref()
const operateLogData = ref([])
const relatedKnowledgeBase = ref()
const roomName = ref()
const viewType = ref()
const treeLoading = ref(false)
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const deptList = ref([])
const idArr = ref<number[]>([])
const prop = defineProps({
  locationList: {
    type: Object,
    default: function () {
      return []
    }
  },
  positionList: {
    type: Object,
    default: function () {
      return []
    }
  }
})

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  propertyJson: undefined
})
const total = ref(0)

watch(
  () => prop.locationList,
  () => {},
  {
    deep: true,
    immediate: true
  }
)

watch(
  () => prop.positionList,
  () => {},
  {
    deep: true,
    immediate: true
  }
)

const scroll = async (e) => {
  const formRefTop = formRef.value.$el.getBoundingClientRect().top
  const basicInformationTop = basicInformation.value.$el.getBoundingClientRect().top - formRefTop
  const usageInformationTop = usageInformation.value.$el.getBoundingClientRect().top - formRefTop
  const maintenanceInformationTop =
    maintenanceInformation.value.$el.getBoundingClientRect().top - formRefTop
  const depreciationInformationTop =
    depreciationInformation.value.$el.getBoundingClientRect().top - formRefTop
  // const relatedKnowledgeBaseTop = relatedKnowledgeBase.value.$el.getBoundingClientRect().top - formRefTop
  if (e.scrollTop >= depreciationInformationTop) {
    tabType.value = 'depreciationInformation'
  } else if (e.scrollTop >= maintenanceInformationTop) {
    tabType.value = 'maintenanceInformation'
  } else if (e.scrollTop >= usageInformationTop) {
    tabType.value = 'usageInformation'
  } else if (e.scrollTop >= basicInformationTop) {
    tabType.value = 'basicInformation'
  } else {
    tabType.value = 'basicInformation'
  }
}
const switchUserInfo = async (tab: TabsPaneContext) => {
  switch (tab.paneName) {
    case 'basicInformation':
      basicInformation.value.$el.scrollIntoView(true)
      break
    case 'usageInformation':
      usageInformation.value.$el.scrollIntoView(true)
      break
    case 'maintenanceInformation':
      maintenanceInformation.value.$el.scrollIntoView(true)
      break
    case 'depreciationInformation':
      depreciationInformation.value.$el.scrollIntoView(true)
      break
    case 'relatedKnowledgeBase':
      relatedKnowledgeBase.value.$el.scrollIntoView(true)
      break
  }
}

const getOperateLogData = async () => {
  const res = await PropertyOperateLogApi.getPropertyOperateLogPage(queryParams)
  operateLogData.value = res.list
  total.value = res.total
}

const handlePositionChange = async (item) => {
  // formData.value.positionId = item.value
  formData.value.positionName = item.name
  console.log(formData.value)
}
/** 管理员 */
const handleAdminChange = async (item) => {
  console.log(item)
  // formData.value.adminId = item.value
  formData.value.adminName = item.nickname
}

const handleNodeClick = async (data: any) => {
  console.log('handleNodeClick', data)
  if (data.layerId) {
    nextTick(() => {
      roomName.value =
        data.villageName + ' - ' + data.buildName + ' - ' + data.layerName + ' - ' + data.name
    })
    formData.value.buildBind = JSON.stringify(data)
    formData.value.roomId = data.id
    formData.value.buildId = data.buildId
    formData.value.villageId = data.villageId
  }
}

const props = ref({
  label: 'name',
  children: 'children',
  isLeaf: 'leaf',
  value: 'treeId'
})
/** 获得部门树 */
const getTree = async () => {
  try {
    console.log('getTree', props.value)
    treeLoading.value = true
    const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
    treeLoading.value = false
    deptList.value = []
    idArr.value = []
    res.villageRespVOS.forEach((element) => {
      element.treeId = element.id
      idArr.value.push(element.treeId)
      // delete element.id
      if (element.buildList) {
        element.buildList.forEach((item) => {
          item.name = item.buildName
          item.treeId = 'building' + item.id
          // delete item.id
        })
        // element.children = handleTree(element.buildList)
      } else {
        element.buildList = []
      }
    })
    deptList.value.push(...handleTree(res.villageRespVOS))
    console.log(deptList.value, 'deptList.value')
  } finally {
    treeLoading.value = false
  }
}

const loadNode = async (node: Node, resolve?: (data: Tree[]) => void) => {
  console.log(node.data, 'node.data')
  // resolve(deptList.value)
  if (node.level === 0) {
    return resolve(deptList.value)
  } else if (node.level > 0 && node.level < 2) {
    //默认展开的层级,需要默认几层就判断一下.
    return resolve(node.data.buildList) //核心是这里,每次展开的时候loadNode方法就会调用一次,只需要把node.data.[这里是默认的child字段]  加载到resolve方法里就可以了.就可以实现默认加载N级,之后再使用懒加载
  } else if (node.level >= 2 && node.level < 3) {
    let dataArray = await BuildApi.getLayerList({
      buildId: node.data.id,
      pageNo: 1,
      pageSize: 10
    })
    if (dataArray.length) {
      dataArray.forEach((element) => {
        element.name = element.layerName
        element.treeId = 'layer' + element.id
      })
      node.data.layerList = dataArray
    }
    return resolve(dataArray)
  } else if (node.level >= 3) {
    let dataArray = await BuildApi.getRoomListByLayerId({
      layerId: node.data.id,
      pageNo: 1,
      pageSize: 10
    })
    if (dataArray.length) {
      dataArray.forEach((element) => {
        element.name = element.roomName
        element.treeId = 'room' + element.id
        element.leaf = true
      })
      node.data.roomList = dataArray
    }
    return resolve(dataArray)
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogTitle.value = t('action.' + type)
  viewType.value = type
  // 获得部门树
  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())
  // 获得用户列表
  userOptions.value = await UserApi.getSimpleUserList()

  //获取房源
  getTree()
  if (type === 'detail') {
    queryParams.propertyJson = id
    disabled.value = true
    await getOperateLogData()
  } else {
    disabled.value = false
  }
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await PropertyApi.getProperty(id)
    } finally {
      formLoading.value = false
    }
  }
  dialogVisible.value = true
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
    const data = formData.value as unknown as PropertyVO
    console.log(data)
    if (formType.value === 'create') {
      await PropertyApi.createProperty(data)
      message.success(t('common.createSuccess'))
    } else {
      await PropertyApi.updateProperty(data)
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
    orgId: undefined,
    propertyNumber: undefined,
    labelLink: undefined,
    type: undefined,
    name: undefined,
    status: undefined,
    brand: undefined,
    modelName: undefined,
    deviceCode: undefined,
    processCode: undefined,
    adminId: undefined,
    adminName: undefined,
    adminUid: undefined,
    villageId: undefined,
    buildId: undefined,
    roomId: undefined,
    buildBind: undefined,
    positionId: undefined,
    positionName: undefined,
    purchaseTime: undefined,
    purchaseType: undefined,
    purchaseAmount: undefined,
    stockTime: undefined,
    expectMonths: undefined,
    remark: undefined,
    imageHash: undefined,
    imageUrl: undefined,
    userId: undefined,
    departmentId: undefined,
    receiveTime: undefined,
    maintainTime: undefined,
    maintainInfo: undefined,
    depreciationMonths: undefined,
    cuserUid: undefined,
    muserUid: undefined,
    knowledgeBase: undefined
  }
  formRef.value?.resetFields()
}
</script>
<style scopd lang="scss">
.el-drawer__header {
  margin-bottom: 10px !important;
}
</style>
