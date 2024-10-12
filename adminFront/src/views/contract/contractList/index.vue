<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap class="ContentWrap" v-if="!addContract && !detailVisible">
    <!-- 搜索工作栏 -->
    <div class="flex-justify-between flex pb-18px">
      <div class="w-[80%]">
        <el-row :gutter="20">
          <el-col :span="4">
            <el-select v-model="queryParams.ownerId" placeholder="请选择租客">
              <el-option
                v-for="(item, index) in ownerIdList"
                :key="index"
                :label="item.name"
                :value="item.id"
              /> </el-select
          ></el-col>
          <el-col :span="5">
            <el-input
              placeholder="请输入合同编号"
              style="width: 100%"
              v-model="queryParams.contractNumber"
            >
              <template #append>
                <Icon icon="ep:search" class="mr-5px" />
              </template>
            </el-input>
          </el-col>
          <el-col :span="7">
            <el-button @click="handleQuery"
              ><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button
            >
            <el-button @click="resetQuery"
              ><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button
            >
          </el-col>
        </el-row>
      </div>
      <div>
        <el-button type="primary " @click="addContract = true" v-hasPermi="['bus:contract:create']">
          <Icon icon="ep:plus" class="mr-5px" />
          合同
        </el-button>
        <el-button @click="importVisible = true" v-hasPermi="['bus:contract:import']"
          >导入合同</el-button
        >
      </div>
    </div>
  </ContentWrap>
  <!-- 筛选字段 -->
  <ContentWrap v-if="!addContract && !detailVisible">
    <div class="flex-justify-between flex">
      <div></div>
      <div
        class="flex flex-justify-center"
        style="user-select: none; min-width: 100px"
        @click="filterShow = !filterShow"
      >
        <Icon v-if="filterShow" icon="ep:arrow-up" class="pt-0.5 mr-5px" />
        <Icon v-if="!filterShow" icon="ep:arrow-down" class="pt-0.5 mr-5px" />
        {{ filterShow ? '收起' : '展开' }}
      </div>
    </div>

    <SearchBar v-show="filterShow" @search="search_data" :dictType="'contract_status'" />
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap v-if="!addContract && !detailVisible">
    <div class="flex justify-between items-center mb-3">
      <el-button text>合同列表</el-button>
      <el-button type="primary" plain @click="exportExcel" :loading="exportLoading"
      v-hasPermi="['bus:contract:export']"
        >导出报表</el-button
      >
    </div>
    <el-table v-loading="loading" :data="list" width="200" @row-click="table_row_click">
      <el-table-column label="租客名称" width="200" align="center" prop="ownerName">
        <template #default="scope">
          <el-button type="primary" text @click.stop="user_detail(scope.row.ownerId)">
            {{ scope.row.ownerName }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="合同编号" width="200" align="center" prop="contractNumber" />
      <el-table-column label="合同状态" width="200" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.statusColor" v-if="scope.row.statusColor">{{
            scope.row.status
          }}</el-tag>
          <el-tag v-else type="info">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="房号" width="200" align="center" prop="roomNumber">
        <template #default="scope">
          <span v-for="(item, index) in scope.row.parseRoomNumber" :key="index">
            <el-button type="text" @click.stop="room_openForm(item.roomId)">
              {{ item.roomName }}
              <span v-if="index + 1 < scope.row.parseRoomNumber.length">,</span>
            </el-button>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="开始日" width="200" align="center" prop="contractStartTime" />
      <el-table-column label="结束日" width="200" align="center" prop="contractEndTime" />
      <el-table-column label="租赁单价" width="200" align="center" prop="zlDjMoney" />
      <el-table-column label="签订日期" width="200" align="center" prop="signingDate" />
      <el-table-column label="楼宇名称" width="200" align="center" prop="buildName" />
      <el-table-column label="总面积" widt="200" align="center" prop="leaseArea" />
      <el-table-column label="合同来源" width="200" align="center" prop="dataSource" />
      <el-table-column label="退租时间" width="200" align="center" prop="leaseRetreatTime" />
      <el-table-column label="物业单价" width="200" align="center" prop="wyDjMoney" />
      <el-table-column label="物业保证金" width="200" align="center" prop="wyBondClause">
        <template #default="scope"> {{ scope.row.wyBondClause + '元' }}</template>
      </el-table-column>
      <el-table-column label="租赁保证金" width="200" align="center" prop="zlBondClause">
        <template #default="scope"> {{ scope.row.zlBondClause + '元' }}</template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template #default="scope">
          <el-button type="text" @click.stop="downloadFile(scope.row)">下载合同</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
  <AddContract @close="[(addContract = false), getList()]" v-if="addContract" @getList="getList" />
  <ContractDetail
    :detailId="detailId"
    @close="[(detailVisible = false), getList()]"
    v-if="detailVisible"
  />
  <Import v-model:show="importVisible" @downLoadmb="downLoadmb" @change="change" @submit="submit" />
  <!-- 点击用户抽屉 -->
  <el-drawer
    v-model="user_drawer"
    :with-header="false"
    :show-close="false"
    size="75%"
    class="bg-#F5F7F9"
    style="background-color: #f5f7f9"
  >
    <Form_detail
      :id="user_detailId"
      :table_detail_data="table_detail_data"
      :active_type="activeIndex"
      @del="handleDelete"
      @change="openForm"
      ref="user_form"
    />
  </el-drawer>
  <!-- 编辑抽屉 -->
  <AddForm ref="FormRef" @success="createOwner_click" />
  <!-- 点击房间抽屉 -->
  <RoomStatusDetail ref="RoomStatusDetailRef" @success="getDetail" />
</template>

<script setup lang="ts">
import { Api, VO } from '@/api/contract/contractList/index'
import { OwnerApi } from '@/api/bus/owner/index'
import download from '@/utils/download'
import ContractDetail from './contractList_detail.vue'
import SearchBar from './component/searchBox.vue'
import AddContract from './add_contractList.vue'
import Import from '@/views/bus/owner/component/import.vue'
import Form_detail from '@/views/bus/owner/form_detail.vue'
import AddForm from '@/views/bus/owner/component/addFom.vue'
import RoomStatusDetail from '@/views/village/managementCenter/RoomStatusDetail.vue'
import { set } from 'lodash-es'

// import
/** 机构合同 列表 */
defineOptions({ name: 'Contract' })

const message = useMessage() // 消息弹窗

const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
let queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  ownerId: '',
  contractNumber: ''
})
const exportLoading = ref(false) // 导出的加载中
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await Api.getPage(queryParams.value)
    list.value = data.list
    total.value = data.total
    if (roomStatus.value.length === 0) {
      await getRoomStatus()
      list.value.map((item) => {
        item.statusColor = roomStatus.value.find((status) => status.label === item.status).colorType
        item.parseRoomNumber = JSON.parse(item.roomNumber)
      })
    } else {
      list.value.map((item) => {
        item.statusColor = roomStatus.value.find((status) => status.label === item.status).colorType
        item.parseRoomNumber = JSON.parse(item.roomNumber)
      })
    }
  } finally {
    loading.value = false
  }
}
//筛选列表展开修显示
const filterShow = ref(false)
/** 搜索按钮操作 */
const search_data = (params: any) => {
  queryParams.value = { ...queryParams.value, ...params }
  getList()
}
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}
/** 重置按钮操作 */
const resetQuery = () => {
  queryParams.value = {
    pageNo: 1,
    pageSize: 10,
    ownerId: '',
    contractNumber: ''
  }
  handleQuery()
}
//获取租客列表
const ownerIdList = ref({
  id: '',
  name: ''
})
const getOwnerList = async (query: string) => {
  OwnerApi.getOwnerListByName(query).then((res) => {
    ownerIdList.value = res
  })
}
//导出列表
const exportExcel = async () => {
  exportLoading.value = true
  try {
    const data = await Api.exportContractList(queryParams.value)
    download.excel(data, '合同列表.xlsx')
  } finally {
    exportLoading.value = false
  }
}
//列表详情
const detailVisible = ref(false)
const detailId = ref<string>('0')
const table_row_click = (row: any) => {
  detailId.value = row.id.toString()
  detailVisible.value = true
}
/** 初始化 **/
onMounted(async () => {
  await getRoomStatus()
  getList()
  getOwnerList('')
})
//新建合同
let addContract = ref(false)
//下载模板
let importVisible = ref(false)
const downLoadmb = async () => {
  const data = await Api.downloadTemplate()
  download.excel(data, '合同导入模板.xlsx')
}
const fileList = ref([])
//导入文件
const change = async (res) => {
  fileList.value.push(res.raw)
}
const submit = async (res) => {
  const formData = new FormData()
  const unRefList = unref(fileList)
  unRefList.map((file) => {
    formData.append('file', file)
  })
  await Api.uploadFile(formData)
    .then((res) => {
      message.success('文件上传成功')
      fileList.value = []
    })
    .catch(() => {
      fileList.value = []
    })
}
//用户抽屉
const user_drawer = ref(false)
let user_detailId = ref('')
const table_detail_data = ref({})
const activeIndex = ref(0)
const user_form = ref<any>(null)
const user_detail = async (user_detailID) => {
  user_detailId.value = user_detailID
  OwnerApi.getOwnerById(user_detailId.value).then((res) => {
    table_detail_data.value = res
    table_detail_data.value.businessInfo = JSON.parse(res.businessInfo)
    activeIndex.value = res.isPersonal
    user_drawer.value = true
    setTimeout(() => {
      user_form.value.getContracList(user_detailId.value)
    }, 100)
  })
}
//删除
const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await OwnerApi.deleteOwner(id)
    message.success(t('删除成功'))
    await getList()
    user_drawer.value = false
  } catch {}
}
//打开编辑弹窗
const FormRef = ref<any>(null)
const drawer_type = ref(null)
const drawer_id = ref(null)
const openForm = (id?: number) => {
  drawer_type.value = 'edit'
  drawer_id.value = 4
  FormRef.value.open(drawer_type.value, drawer_id.value)
}
const createOwner_click = () => {
  getList()
  user_drawer.value = false
}
//房源抽屉
const RoomStatusDetailRef = ref<any>(null)
const room_openForm = (id?: number) => {
  RoomStatusDetailRef.value.open({
    id: id
  })
}
//获得合同状态
const roomStatus = ref([])
const getRoomStatus = async () => {
  const data = await Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'CONTRACT_LIST_STATUS'
  })

  roomStatus.value = data.list
}
// 下载合同
const down_loadFile = async (row: any) => {
  try {
    const response = await fetch(row.filePath)
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    const blob = await response.blob()
    const url = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = row.name // 设置下载文件名
    document.body.appendChild(a) // 将链接添加到文档中
    a.click() // 模拟点击链接
    a.remove() // 点击后移除链接
    window.URL.revokeObjectURL(url) // 释放 URL 对象
  } catch (error) {
    console.error('下载文件时发生错误:', error)
  }
}
const downloadFile = async (url: any) => {
  down_loadFile({ filePath: url.pdfFileUrl, name: `${url.contractNumber}合同文本` })
}
</script>
<style lang="scss" scoped>
.ContentWrap {
  --el-card-padding: 20px 20px 0 20px;
}
</style>
