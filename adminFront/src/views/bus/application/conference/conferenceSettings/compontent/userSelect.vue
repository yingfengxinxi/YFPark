<template>
  <div>
    <el-dialog v-model="dialogVisible" title="请选择成员" width="1000">
      <div class="flex">
        <div class="w-30%">
          <el-scrollbar height="500px">
            <Tree @node-click="handleDeptNodeClick" />
          </el-scrollbar>
        </div>
        <div class="w-70%">
          <div>
            <el-table
              v-loading="loading"
              :data="list"
              @selection-change="handleSelectionChange"
              :row-key="getRowKeys"
              ref="multipleTableRef"
              :highlight-current-row="selectType == 'dan'"
              @current-change="handleCurrentChange"
            >
              <el-table-column
                type="selection"
                width="55"
                :reserve-selection="true"
                v-if="selectType == 'duo'"
              />
              <el-table-column label="用户编号" align="center" key="id" prop="id" />
              <el-table-column
                label="用户名称"
                align="center"
                prop="username"
                :show-overflow-tooltip="true"
              />
              <el-table-column
                label="用户昵称"
                align="center"
                prop="nickname"
                :show-overflow-tooltip="true"
              />
              <el-table-column
                label="部门"
                align="center"
                key="deptName"
                prop="deptName"
                :show-overflow-tooltip="true"
              />
              <el-table-column label="手机号码" align="center" prop="mobile" width="120" />
              <el-table-column
                label="创建时间"
                align="center"
                prop="createTime"
                :formatter="dateFormatter"
                width="180"
              />
            </el-table>
            <Pagination
              :total="total"
              v-model:page="queryParams.pageNo"
              v-model:limit="queryParams.pageSize"
              @pagination="getList"
            />
          </div>
        </div>
      </div>
      <div class="text-end">
        <el-button type="primary" plain @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="[emit('submit', selectData), (dialogVisible = false)]"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import Tree from './Tree.vue'
import * as UserApi from '@/api/system/user'
import { dateFormatter } from '@/utils/formatTime'
const selectType = ref<'dan' | 'duo'>('duo')
const dialogVisible = ref<boolean>(false)
async function open(data?, type?: 'dan' | 'duo') {
  selectType.value = type || 'duo'
  dialogVisible.value = true
  if (multipleTableRef.value) {
    multipleTableRef.value.setCurrentRow([])
    multipleTableRef.value.clearSelection()
  }
  await getList(data, 'open')
}
defineExpose({ open })
onMounted(() => {})
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  username: undefined,
  mobile: undefined,
  status: undefined,
  deptId: undefined,
  createTime: []
})
/** 处理部门被点击 */
const handleDeptNodeClick = async (row) => {
  queryParams.deptId = row.id
  await getList()
}
const loading = ref<boolean>(false)
const list = ref<UserApi.UserVO[]>([])
const total = ref<number>(0)
/** 查询列表 */
const getList = async (seldata, type) => {
  loading.value = true
  try {
    const data = await UserApi.getUserPage(queryParams)
    list.value = data.list
    total.value = data.total
    if (type == 'open') {
      hxList(seldata, 'open')
    } else {
      hxList(selectData.value, 'list')
    }
  } finally {
    loading.value = false
  }
}
const selectData = ref<any>([])
const handleSelectionChange = (val) => {
  if (selectType.value == 'duo') {
    selectData.value.push(...val)
    const uniqueArray = removeDuplicates(selectData.value)
    selectData.value = JSON.parse(JSON.stringify(uniqueArray))
  }
}
const handleCurrentChange = (val) => {
  if (selectType.value == 'dan') {
    selectData.value = val
  }
}
const getRowKeys = (row) => row.id
const emit = defineEmits(['submit'])
//回显
const multipleTableRef = ref(null)
const hxList = async (data, type) => {
  if (type == 'open' && data?.length >= 1) {
    await data.map((item) => {
      list.value.map((listItem) => {
        if (item.id == listItem.id) {
          selectData.value.push(listItem)
        }
      })
    })
    await selectData.value.map((item) => {
      multipleTableRef.value!.toggleRowSelection(item)
    })
  }
}
//判断是否有重复的项
function removeDuplicates(array) {
  const uniqueSet = new Set()
  const uniqueArray = array.filter((item) => {
    const itemString = JSON.stringify(item)
    if (uniqueSet.has(itemString)) {
      return false
    } else {
      uniqueSet.add(itemString)
      return true
    }
  })

  return uniqueArray
}
</script>
