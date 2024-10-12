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
        <el-col :span="8">
          <el-form-item label="单据编号">
            <el-input
              placeholder="编码自动生成"
              v-model="formData.number"
              disabled
            /> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item label="保养人" prop="maintainUid">
            <el-select v-model="formData.maintainUid" placeholder="请选择保养人">
              <el-option
                v-for="item in userOptions"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item label="保养人所在部门" prop="departmentId">
            <el-tree-select
              v-model="formData.departmentId"
              :data="deptTree"
              :props="defaultProps"
              filterable
              check-strictly
              default-expand-all
              node-key="id"
              placeholder="请选择报修部门"
              @node-click="handleNodeClick"
            /> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item label="保养项目" prop="">
            <el-select v-model="formData.maintainVillageId" placeholder="请选择保养项目" clearable>
              <el-option
                v-for="item in maintainVillage"
                :key="item.id"
                :label="item.maintainVillageName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="保养总金额" prop="maintainTotalPrice">
            <el-input-number
              :min="0"
              placeholder="请输入"
              v-model="formData.maintainTotalPrice"
              :step="1"
              controls-position="right"
              class="!w-1/1"
            /> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item label="保养时间" prop="maintainDate">
            <el-date-picker
              v-model="formData.maintainDate"
              type="date"
              value-format="x"
              placeholder="选择保养时间"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="下次保养时间" prop="nextMaintainDate">
            <el-date-picker
              v-model="formData.nextMaintainDate"
              type="date"
              value-format="x"
              placeholder="选择下次保养时间"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="保养处理人" prop="operateUid">
            <el-select v-model="formData.operateUid" placeholder="请选择保养处理人" disabled>
              <el-option
                v-for="item in userOptions"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select> </el-form-item
        ></el-col>

        <el-col :span="24">
          <el-form-item label="保养备注" prop="remark">
            <el-input
              placeholder="请输入"
              v-model="formData.remark"
              type="textarea"
              rows="1"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <ContentWrap title="资产列表" class="!mt-4">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
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
import { formatDate } from '@/utils/formatTime'
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { PropertyMaintainSetApi } from '@/api/bus/property/setting/propertyMaintainSet'
import { ElDrawer } from 'element-plus'
import { useUserStore } from '@/store/modules/user'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import StatusArrayList from './statusArrayList.vue'

/** 派发 表单 */
defineOptions({ name: 'MaintainForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const deptTree = ref() // 部门树形结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('变更领用人') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const maintainVillage = ref([])
const formData = ref({
  id: undefined,
  number: undefined,
  maintainUid: undefined,
  operateUid: undefined,
  departmentId: undefined,
  departmentName: undefined,
  maintainVillageId: undefined,
  propertyIds: undefined,
  maintainDate: undefined,
  nextMaintainDate: undefined,
  remark: undefined,
  maintainTotalPrice: undefined
})
const roomName = ref()
const formRules = reactive({
  maintainUid: [{ required: true, message: '保养人不能为空', trigger: 'blur' }],
  operateUid: [{ required: true, message: '保养处理人不能为空', trigger: 'blur' }],
  departmentId: [{ required: true, message: '保养人所在部门不能为空', trigger: 'blur' }],
  nextMaintainDate: [{ required: true, message: '下次保养时间不能为空', trigger: 'blur' }],
  maintainDate: [{ required: true, message: '保养时间不能为空', trigger: 'blur' }]
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
  console.log(listArray)
  assetsList.value = listArray
  // 获得部门树
  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())
  // 获得用户列表
  userOptions.value = await UserApi.getSimpleUserList()
  //获取保养部门
  maintainVillage.value = await PropertyMaintainSetApi.getPropertyMaintainSetList()
  resetForm()
  formData.value.nextMaintainDate = new Date().getTime()
  formData.value.maintainDate = new Date().getTime()
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
  listRef.value.open(2)
}

const handleNodeClick = (item) => {
  console.log(item)
  if (item.name) {
    formData.value.departmentName = item.name
  } else {
    formData.value.departmentName = undefined
  }
  console.log(formData.value)
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
    data.status = 1
    data.propertyIds = assetsList.value.map((item) => item.propertyNumber).join(',')
    await PropertyApi.createMaintainProperty(data)
    message.success(t('common.operationSuccess'))

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
    number: undefined,
    maintainUid: undefined,
    operateUid: undefined,
    departmentId: undefined,
    departmentName: undefined,
    maintainVillageId: undefined,
    propertyIds: undefined,
    maintainDate: undefined,
    nextMaintainDate: undefined,
    remark: undefined,
    maintainTotalPrice: undefined
  }
  formRef.value?.resetFields()
}
</script>
