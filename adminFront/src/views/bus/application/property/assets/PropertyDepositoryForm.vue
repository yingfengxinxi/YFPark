<template>
  <ElDrawer :title="dialogTitle" v-model="dialogVisible" size="80%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      label-position="top"
      v-loading="formLoading"
      :disabled="formType == 'detail'"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="单据编号" prop="number">
            <el-input v-model="formData.number" placeholder="编号自动生成" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入库处理人" prop="cuserUid">
            <el-select
              v-model="formData.cuserUid"
              placeholder="请选择入库处理人"
              class="!w-1/1"
              disabled
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
      </el-row>
      <div class="flex justify-end pb-10px">
        <el-button type="primary" @click="addTableData">
          <Icon class="mr-5px" icon="ep:plus" />添加目标</el-button
        >
      </div>
      <el-table
        :data="tableData"
        v-loading="loading"
        border
        class="!m-t-30px"
        style="width: 100%; margin: 0 auto"
        :header-cell-style="{
          color: '#000000d9',
          fontSize: '14px',
          fontWeight: '500'
        }"
      >
        <el-table-column type="index" label="序号" width="80" align="center" />
        <el-table-column label="资产编码" width="160" align="center">
          <template #default="scope">
            <el-input
              placeholder="编码自动生成"
              v-if="formType !== 'detail'"
              v-model="scope.row.propertyNumber"
              disabled
            />
            <span v-else>{{ scope.row.propertyNumber }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="资产分类" align="center" width="180">
          <template #header="{ column }">
            <span class="c-#f00">{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tree-select
              v-if="formType !== 'detail'"
              v-model="scope.row.type"
              :data="locationList"
              :render-after-expand="false"
              :props="{ label: 'name', value: 'id', children: 'children' }"
              placeholder="请选择资产分类"
              clearable
              :check-strictly="true"
              class="!w-100%"
              @node-click="handleTreeSelect($event, scope.row, 'categoryName')"
            />
            <span v-else> {{ scope.row.categoryName || '--' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="资产名称" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tooltip effect="dark" :content="scope.row.name" placement="top">
              <el-input
                placeholder="请输入资产名称"
                v-model="scope.row.name"
                v-if="formType != 'detail'"
              />
              <span v-else> {{ scope.row.name || '--' }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="brand" label="品牌" align="center" width="180">
          <template #header="{ column }">
            <span class="c-#f00">{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tooltip effect="dark" :content="scope.row.brand" placement="top">
              <el-input
                placeholder="请输入品牌"
                v-model="scope.row.brand"
                v-if="formType != 'detail'"
              />
              <span v-else> {{ scope.row.brand || '--' }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="modelName" label="型号" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tooltip effect="dark" :content="scope.row.modelName" placement="top">
              <el-input
                placeholder="请输入型号"
                v-model="scope.row.modelName"
                v-if="formType != 'detail'"
              />
              <span v-else> {{ scope.row.modelName || '--' }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="deviceCode" label="设备序列号" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tooltip effect="dark" :content="scope.row.deviceCode" placement="top">
              <el-input
                placeholder="请输入设备序列号"
                v-model="scope.row.deviceCode"
                v-if="formType != 'detail'"
              />
              <span v-else> {{ scope.row.deviceCode || '--' }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="adminId" label="管理员" align="center" width="180">
          <template #header="{ column }">
            <span class="c-#f00">{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-select
              v-model="scope.row.adminId"
              placeholder="请选择管理员"
              class="!w-1/1"
              v-if="formType != 'detail'"
            >
              <el-option
                v-for="item in userOptions"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
                @click="handleAdminChange(item, scope.row)"
              />
            </el-select>
            <span v-else> {{ scope.row.adminName || '--' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="positionId" label="所在位置" align="center" width="180">
          <template #header="{ column }">
            <span class="c-#f00">{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tree-select
              v-if="formType != 'detail'"
              v-model="scope.row.positionId"
              :data="positionList"
              :render-after-expand="false"
              :props="{ label: 'name', value: 'id', children: 'children' }"
              placeholder="请选择资产位置"
              clearable
              :check-strictly="true"
              @node-click="handleTreeSelect($event, scope.row, 'positionName')"
            />
            <span v-else> {{ scope.row.positionName || '--' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="purchaseTime" label="购置时间" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-date-picker
              v-model="scope.row.purchaseTime"
              type="date"
              value-format="x"
              placeholder="选择购置时间"
              class="!w-100%"
              v-if="formType != 'detail'"
            />
            <span v-else>
              {{ dateFormatter(scope.row, 'purchaseTime', scope.row.purchaseTime) || '--' }}</span
            >
          </template>
        </el-table-column>
        <el-table-column prop="purchaseType" label="购置方式" align="center" width="180">
          <template #header="{ column }">
            <span class="c-#f00">{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-select
              v-model="scope.row.purchaseType"
              placeholder="请选择购置方式"
              class="!w-100%"
              v-if="formType != 'detail'"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.PROPERTY_PURCHASETYPE)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
            <span v-else>
              <dict-text :type="DICT_TYPE.PROPERTY_PURCHASETYPE" :value="scope.row.purchaseType" />
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="purchaseAmount" label="购置金额" align="center" width="180">
          <template #header="{ column }">
            <span class="c-#f00">{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-input-number
              v-if="formType != 'detail'"
              min="0"
              controls-position="right"
              v-model="scope.row.purchaseAmount"
              placeholder="请输入购置金额(含税)"
              class="!w-1/1"
            />
            <span v-else> {{ scope.row.purchaseAmount || '--' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="stockTime" label="入库时间" align="center" width="180">
          <template #header="{ column }">
            <span class="c-#f00">{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-date-picker
              v-if="formType != 'detail'"
              v-model="scope.row.stockTime"
              type="date"
              value-format="x"
              placeholder="选择入库时间"
              class="!w-100%"
            />
            <span v-else>
              {{ dateFormatter(scope.row, 'stockTime', scope.row.stockTime) || '--' }}</span
            >
          </template>
        </el-table-column>
        <el-table-column prop="stockTime" label="预计使用期限(月)" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-input-number
              :min="0"
              v-if="formType != 'detail'"
              placeholder="请输入预计使用期限(月)"
              v-model="scope.row.expectMonths"
              :step="1"
              controls-position="right"
              class="!w-100%"
            />
            <span v-else> {{ scope.row.expectMonths || '--' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="stockTime" label="备注" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tooltip effect="dark" :content="scope.row.remark" placement="top">
              <el-input
                v-model="scope.row.remark"
                placeholder="请输入备注"
                v-if="formType != 'detail'"
              />
              <span v-else> {{ scope.row.remark || '--' }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="imageUrl" label="资产照片" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <UploadImg
              v-model="scope.row.imageUrl"
              draggable="false"
              height="40px"
              width="100%"
              class="min-w-40px"
              v-if="formType != 'detail'"
            />
            <span v-else>
              <template v-if="scope.row.imageUrl">
                <el-image
                  preview-teleported="true"
                  style="width: 60px; height: 60px"
                  :src="scope.row.imageUrl"
                  :zoom-rate="1.2"
                  :max-scale="7"
                  :min-scale="0.2"
                  :preview-src-list="[scope.row.imageUrl]"
                  :initial-index="0"
                  fit="cover"
                />
              </template>
              <template v-else> -- </template>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="userId" label="使用人" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-select
              v-model="scope.row.userId"
              placeholder="请选择使用人"
              class="!w-1/1"
              v-if="formType != 'detail'"
            >
              <el-option
                v-for="item in userOptions"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select>
            <span v-else>
              <template v-if="scope.row.userId">
                <template v-for="user in userOptions" :key="user.id">
                  <span v-if="user.id === scope.row.userId">{{ user.nickname }}</span>
                </template>
              </template>
              <span v-else>--</span>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="departmentId" label="使用部门" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tree-select
              v-model="scope.row.departmentId"
              :data="deptTree"
              :props="defaultProps"
              filterable
              check-strictly
              node-key="id"
              placeholder="请选择使用部门"
              v-if="formType != 'detail'"
            />

            <span v-else> {{ scope.row.deptName || '--' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="departmentId" label="目标房源" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-tree-select
              v-model="scope.row.roomName"
              lazy
              :load="loadNode"
              :props="props"
              :data="deptList"
              class="!w-100%"
              placeholder="请选择绑定的目标房源"
              @node-click="handleNodeClick($event, scope.row)"
              v-if="formType != 'detail'"
            />
            <span v-else>
              <template v-if="scope.row.buildBind">
                {{ scope.row.buildBind.villageName }}/{{ scope.row.buildBind.buildName }}/{{
                  scope.row.buildBind.layerName
                }}/{{ scope.row.buildBind.roomName }}
              </template>
              <template v-else> -- </template>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="receiveTime" label="领用日期" align="center" width="180">
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-date-picker
              v-if="formType != 'detail'"
              v-model="scope.row.receiveTime"
              type="date"
              value-format="x"
              placeholder="选择入库时间"
              class="!w-100%"
            />
            <span v-else>
              {{ dateFormatter(scope.row, 'receiveTime', scope.row.receiveTime) || '--' }}</span
            >
          </template>
        </el-table-column>
        <el-table-column
          prop="depreciationMonths"
          label="预计折旧期限(月)"
          align="center"
          width="180"
        >
          <template #header="{ column }">
            <span>{{ column.label }}</span>
          </template>
          <template #default="scope">
            <el-input-number
              :min="0"
              v-if="formType != 'detail'"
              placeholder="请输入预计折旧期限(月)"
              v-model="scope.row.depreciationMonths"
              :step="1"
              controls-position="right"
              class="!w-100%"
            />
            <span v-else> {{ scope.row.depreciationMonths || '--' }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          fixed="right"
          width="180"
          v-if="formType != 'detail'"
        >
          <template #default="scope">
            <el-button @click="deleteTableData(scope.row)" link icon="Delete" type="danger"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <Pagination
        v-if="formType == 'detail'"
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </el-form>
    <template #footer v-if="formType != 'detail'">
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import { defaultProps, handleTree } from '@/utils/tree'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { PropertyLocationApi } from '@/api/bus/property/setting/propertyLocation'
import { PropertyDepositoryApi, PropertyDepositoryVO } from '@/api/bus/property/propertyDepository'
import * as UserApi from '@/api/system/user'
import * as DeptApi from '@/api/system/dept'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { useUserStore } from '@/store/modules/user'
import { BuildApi } from '@/api/bus/village'
import { AnyTxtRecord } from 'dns'
import { pa } from 'element-plus/es/locale'

/** 资产仓库信息 表单 */
defineOptions({ name: 'PropertyDepositoryForm' })

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
const formRules = reactive({
  cuserUid: [{ required: true, message: '入库处理人不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单

const tableData = ref<PropertyVO[]>([])
// 定义属性
const loading = ref(false)

// 获取分类列表
const locationList = ref([])
//获得位置列表
const positionList = ref([])

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  approveNumber: undefined
})
const total = ref(0)

/** 管理员 */
const handleAdminChange = async (item, row) => {
  row.adminName = item.nickname
}

const handleNodeClick = async (data: any, row: any) => {
  console.log('handleNodeClick', data)
  if (data.layerId) {
    nextTick(() => {
      row.roomName.value =
        data.villageName + ' - ' + data.buildName + ' - ' + data.layerName + ' - ' + data.name
    })
    row.buildBind = JSON.stringify(data)
    row.roomId = data.id
    row.buildId = data.buildId
    row.villageId = data.villageId
  }
}

const props = ref({
  label: 'name',
  children: 'children',
  isLeaf: 'leaf',
  value: 'treeId'
})
const treeLoading = ref(false)
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const deptList = ref([])
const idArr = ref<number[]>([])
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

const addTableData = async () => {
  if (tableData.value.length == 10) {
    ElMessage.warning('最多添加10条数据')
    return
  }
  tableData.value.push({
    operateUid: undefined,
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
}

const handleTreeSelect = async (data: any, row: any, name: string) => {
  console.log(data, 'data')
  row[name] = data.name
  console.log(row, 'row')
}

// 删除
const deleteTableData = (row) => {
  const index = tableData.value.indexOf(row)
  if (index !== -1) {
    tableData.value.splice(index, 1)
  }
}
/** 打开弹窗 */
const open = async (type: string, row?: any) => {
  tableData.value = []
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type

  // 获得部门树
  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())
  // 获得用户列表
  userOptions.value = await UserApi.getSimpleUserList()
  locationList.value = await PropertyApi.getPropertyTypeTree({})
  positionList.value = await PropertyLocationApi.getPropertyPositionTree({})
  await resetForm()
  if (type === 'create') {
    formData.value.cuserUid = userStore.getUser.id
    await addTableData()
  }
  if (row) {
    formData.value = JSON.parse(JSON.stringify(row))
    queryParams.approveNumber = formData.value.processCode
    await getList()
  }
  loading.value = false
  await getTree()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const getList = async () => {
  try {
    loading.value = true
    const res = await PropertyApi.getPropertyPageByApprove(queryParams)
    tableData.value = res.list
    total.value = res.total
    tableData.value.forEach(async (item) => {
      if (item.buildBind) item.buildBind = JSON.parse(item.buildBind)
    })
  } finally {
    loading.value = false
  }
}
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  formLoading.value = true
  try {
    console.log(tableData.value)
    tableData.value.forEach((item) => {
      item.cuserUid = formData.value.cuserUid
      if (
        !item.type ||
        !item.brand ||
        !item.adminId ||
        !item.positionId ||
        !item.purchaseType ||
        !item.purchaseAmount ||
        !item.stockTime
      ) {
        throw new Error('End Loop')
      }
    })
  } catch (e) {
    if (e.message === 'End Loop') {
      formLoading.value = false
      return message.error('红色字体为必填项，请填写完整')
    }
  }
  // 提交请求
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(tableData.value))
    if (formType.value === 'create') {
      await PropertyApi.createListProperty(data)
      message.success(t('common.createSuccess'))
    } else {
      await PropertyDepositoryApi.updatePropertyDepository(data)
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
