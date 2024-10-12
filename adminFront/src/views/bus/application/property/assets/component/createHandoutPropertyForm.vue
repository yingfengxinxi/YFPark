<template>
  <ElDrawer :title="dialogTitle" v-model="dialogVisible" size="60%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
      :disabled="formType == 'detail'"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="单据编号">
            <el-input
              placeholder="编码自动生成"
              v-model="formData.number"
              disabled
            /> </el-form-item
        ></el-col>
        <el-col :span="12">
          <el-form-item label="领用人" prop="receiveUid">
            <el-select v-model="formData.receiveUid" placeholder="请选择领用人">
              <el-option
                v-for="item in userOptions"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="12">
          <el-form-item label="领用部门" prop="departmentId">
            <el-tree-select
              v-model="formData.departmentId"
              :data="deptTree"
              :props="defaultProps"
              filterable
              default-expand-all
              check-strictly
              node-key="id"
              placeholder="请选择领用部门"
            /> </el-form-item
        ></el-col>
        <el-col :span="12">
          <el-form-item label="派发时间" prop="handoutTime">
            <el-date-picker
              v-model="formData.handoutTime"
              type="date"
              value-format="x"
              placeholder="选择派发时间"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="派发处理人" prop="operateUid">
            <el-select
              v-model="formData.operateUid"
              placeholder="请选择派发处理人"
              class="!w-1/1"
              disabled
            >
              <el-option
                v-for="item in userOptions"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="12">
          <el-form-item label="目标房源" prop="roomId">
            <el-tree-select
              v-model="roomName"
              lazy
              :load="loadNode"
              :props="props"
              :data="deptList"
              class="!w-100%"
              @node-click="handleNodeClick"
            /> </el-form-item
        ></el-col>
        <el-col :span="24">
          <el-form-item label="派发备注" prop="remark">
            <el-input
              placeholder="请输入"
              v-model="formData.remark"
              type="textarea"
              rows="4"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <ContentWrap title="资产列表" class="!mt-4">
        <template #header>
          <div class="flex-1 flex justify-end items-center" v-if="formType != 'detail'">
            <el-button type="primary" @click="addAssets()">
              <Icon icon="ep:plus" class="m-r-6px" />
              添加资产
            </el-button>
          </div>
        </template>
        <el-table
          :data="assetsList"
          v-loading="loading"
          border
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="资产编码" prop="propertyNumber" />
          <el-table-column label="资产分类" prop="categoryName" />
          <el-table-column label="资产名称" prop="name" />
          <el-table-column label="所在位置" prop="positionName" />
          <el-table-column label="品牌" prop="brand" />
          <el-table-column label="型号" prop="modelName" />
          <el-table-column label="操作" width="100" fixed="right" v-if="formType != 'detail'">
            <template #default="scope">
              <el-button text type="primary" @click="deleteAssetsItem(scope.row, scope.$index)"
                >移除</el-button
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
      </ContentWrap>
      <StatusArrayList ref="listRef" @success="submitListForm" />
    </el-form>
    <template #footer v-if="formType != 'detail'">
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { BuildApi } from '@/api/bus/village'
import { ElDrawer } from 'element-plus'
import { useUserStore } from '@/store/modules/user'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import StatusArrayList from './statusArrayList.vue'
import { PropertyLocationApi } from '@/api/bus/property/setting/propertyLocation'
import { is } from '@/utils/is'

/** 派发 表单 */
defineOptions({ name: 'CreateHandoutPropertyForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const deptTree = ref() // 部门树形结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('资产派发') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  number: undefined,
  receiveUid: undefined,
  departmentId: undefined,
  handoutTime: undefined,
  operateUid: undefined,
  villageId: undefined,
  buildId: undefined,
  roomId: undefined,
  propertyIds: undefined,
  remark: undefined
})
const roomName = ref()
const formRules = reactive({
  receiveUid: [{ required: true, message: '领用人不能为空', trigger: 'blur' }],
  departmentId: [{ required: true, message: '领用部门不能为空', trigger: 'blur' }],
  handoutTime: [{ required: true, message: '领用时间不能为空', trigger: 'blur' }],
  operateUid: [{ required: true, message: '派发处理人不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const treeLoading = ref(false)
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const deptList = ref([])
const idArr = ref<number[]>([])
const assetsList = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  approveNumber: undefined
})
const total = ref(0)
const loading = ref(false)
/** 打开弹窗 */
const open = async (type?: any, listArray?: any, row?: any) => {
  formType.value = type
  assetsList.value = listArray
  // 获得部门树
  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())
  // 获得用户列表
  userOptions.value = await UserApi.getSimpleUserList()
  resetForm()
  getTree()
  formData.value.operateUid = userStore.getUser.id
  if (type == 'detail') {
    formData.value = row
    queryParams.approveNumber = formData.value.processCode
    getList()
  }
  dialogVisible.value = true
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const getList = async () => {
  try {
    loading.value = true
    const res = await PropertyApi.getPropertyPageByApprove(queryParams)
    assetsList.value = res.list
    total.value = res.total
  } finally {
    loading.value = false
  }
}
/** 添加资产 */
const listRef = ref()
const addAssets = async () => {
  listRef.value.open(1)
}

/** 删除资产 */
const deleteAssetsItem = (row, index) => {
  assetsList.value.splice(index, 1)
}

const submitListForm = async (list) => {
  assetsList.value = [...new Set(list)]
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if (!assetsList.value.length) {
    message.warning('请先选择资产')
    return
  }
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    data.propertyIds = assetsList.value.map((item) => item.id).join(',')
    data.type = 1
    await PropertyApi.createHandoutProperty(data)
    message.success(t('common.updateSuccess'))

    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const handleNodeClick = async (data: any) => {
  console.log('handleNodeClick', data)
  if (data.layerId) {
    nextTick(() => {
      roomName.value =
        data.villageName + ' - ' + data.buildName + ' - ' + data.layerName + ' - ' + data.name
    })
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

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    number: undefined,
    receiveUid: undefined,
    departmentId: undefined,
    handoutTime: undefined,
    operateUid: undefined,
    villageId: undefined,
    buildId: undefined,
    roomId: undefined,
    propertyIds: undefined,
    remark: undefined
  }
  formRef.value?.resetFields()
}
</script>
