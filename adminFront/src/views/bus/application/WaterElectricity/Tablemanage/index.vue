<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap v-if="!batchAddShow && !detailShow">
    <el-form>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="项目名称:">
            <el-select v-model="queryParams.villageId" @change="getList" clearable>
              <el-option
                v-for="(item, key) in getProjectList"
                :key="key"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="楼宇名称:">
            <el-select v-model="queryParams.buildId" @change="getList" clearable>
              <el-option
                v-for="(item, key) in buildingList"
                :key="key"
                :label="item.buildName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="表用途:">
            <el-select v-model="queryParams.purpose" @change="getList" clearable>
              <el-option
                v-for="(item, key) in getStrDictOptions('PURPODE')"
                :key="key"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="表名称:">
            <el-input v-model="queryParams.name">
              <template #append>
                <Icon icon="ep:search" @click="getList" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="表具编号:">
            <el-input v-model="queryParams.number">
              <template #append>
                <Icon icon="ep:search" @click="getList" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap v-if="!batchAddShow && !detailShow">
    <div class="flex justify-between mb-[20px] items-center">
      <div
        class="flex items-center gap-[2px] border border-b-[1px] border-[#F0F0F0] border-solid border-0 flex-1"
      >
        <div
          :class="ActiveIndex == index ? 'active' : 'unactive'"
          v-for="(item, index) in EnergyTypeList"
          :key="index"
          @click="getDetail(index)"
        >
          {{ item.name }}
        </div>
      </div>
      <div class="flex justify-end">
        <el-button>一键生成账单</el-button>
        <el-button @click="ImportRecords = true">导入抄表记录</el-button>
        <el-dropdown class="ml-[10px]">
          <el-button type="primary" plain>导入表<Icon icon="ep:arrow-down" /> </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="ImportExcel = true">Excel导入</el-dropdown-item>
              <el-dropdown-item @click="batchAddShow = true">批量添加</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <el-dropdown class="ml-[10px]">
          <el-button type="primary">新建表<Icon icon="ep:arrow-down" /> </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="openForm('Brain')">新建智能表</el-dropdown-item>
              <el-dropdown-item @click="openForm('Normal')">新建普通表</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      @row-click="rowClick"
    >
      <el-table-column label="表名称" align="center" prop="name" width="150" />
      <el-table-column label="表具编号" align="center" prop="number" width="220" />
      <el-table-column label="用途" align="center" prop="purpose" width="100" />
      <el-table-column label="绑定位置" align="center" prop="bindingLocation" width="260" />
      <el-table-column label="付费类型" align="center" prop="paymentTypeName" width="100" />
      <el-table-column label="剩余水量" align="center" prop="currentRemaining" width="120" />
      <el-table-column
        label="本期抄表日期"
        align="center"
        prop="thisTime"
        width="200"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="本期读数"
        align="center"
        prop="thisNumber"
        width="200"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="上期读数"
        align="center"
        prop="lastNumber"
        width="200"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="本期用量"
        align="center"
        prop="thisUse"
        width="200"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="本期负责人"
        align="center"
        prop="leadName"
        width="200"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="智能设备"
        align="center"
        prop="propertyName"
        width="300"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="状态" align="center" prop="propertyName" width="100" fixed="right">
        <template #default="{ row }">
          <el-tag type="success">
            {{ row.statusName }}
          </el-tag>
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
  <Form ref="formRef" />
  <!-- 导入抄表记录 -->
  <Import
    :title="'导入抄表记录'"
    v-model:show="ImportRecords"
    @down-loadmb="downloadmbRecords"
    @change="changeRecords"
    @submit="submitRecords"
  />
  <!-- Excel导入 -->
  <Import
    :title="'导入分表设备'"
    v-model:show="ImportExcel"
    v-model:updateSupport="updateSupport"
    @down-loadmb="downloadmbExcel"
    @change="changeExcel"
    @submit="submitExcel"
  />
  <div v-if="batchAddShow">
    <batchAdd @goback="goback" />
  </div>
  <detailForm ref="detailFormRef" v-if="detailShow" @goback="goback" />
</template>

<script setup lang="ts">
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { TablemanageApi } from '@/api/bus/WaterElectricity/Tablemanage/index'
import { energyType } from '@/api/bus/WaterElectricity/EnergyType/index'
import { BuildApi } from '@/api/bus/village/index'
import { Api } from '@/api/contract/contractList/index'
import Form from './Form.vue'
import Import from '@/views/bus/owner/component/import.vue'
import batchAdd from './components/batchAdd.vue'
import detailForm from './components/detailForm.vue'
/** 自定义抄表计划 列表 */
defineOptions({ name: 'Tablemanage' })
const batchAddShow = ref(false) // 批量添加弹窗
const detailShow = ref(false) // 详情弹窗
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const ActiveIndex = ref(0) // 当前选中的分类
const ImportRecords = ref(false) // 导入抄表记录弹窗
const ImportExcel = ref(false) // 导入分表设备弹窗
const updateSupport = ref(true) // 是否支持更新
const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  villageId: undefined,
  type: undefined,
  buildId: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const detailDate = ref()
const getDetail = (index: number) => {
  ActiveIndex.value = index
  detailDate.value = EnergyTypeList.value[index]
  queryParams.type = EnergyTypeList.value[index].id
  getList()
}
const detailFormRef = ref() // 详情弹窗的表单
const rowClick = (row: VO) => {
  detailShow.value = true
  setTimeout(() => {
    detailFormRef.value.open(row)
  }, 50)
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await TablemanageApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (addType: string) => {
  //新增传入数据type, typeId, addType, id
  formRef.value.open('create', queryParams.type, addType)
}
//下载抄表记录模板
const downloadmbRecords = async () => {
  TablemanageApi.getTableRecordTemplate().then((res) => {
    download.excel(res, '抄表记录模板.xls')
  })
}
//下载Excel模板
const downloadmbExcel = async () => {
  TablemanageApi.getExcelTemplate().then((res) => {
    download.excel(res, '分表设备导入模板.xls')
  })
}
//上传抄表记录
const RecordsFileList = ref({})
const changeRecords = (file: any) => {
  RecordsFileList.value = file.raw
}
const goback = () => {
  batchAddShow.value = false
  detailShow.value = false
  getList()
}
const submitRecords = () => {
  const data = new FormData()
  data.append('file', RecordsFileList.value)
  TablemanageApi.importTableRecord(data).then((res) => {
    message.success('上传成功')
    ImportRecords.value = false
    RecordsFileList.value = []
  })
}
//上传Excel
const ExcelFileList = ref({})
const changeExcel = (file: any) => {
  ExcelFileList.value = file.raw
}
const submitExcel = (file: any, updateSupportValue: boolean) => {
  const data = new FormData()
  data.append('file', ExcelFileList.value)
  data.append('updateSupport', updateSupportValue)
  TablemanageApi.importExcel(data).then((res) => {
    message.success('上传成功')
    ImportExcel.value = false
    ExcelFileList.value = []
  })
}
//获取智能表分类
const EnergyTypeList = ref([])
const getEnergyTypeList = async () => {
  energyType.getList().then(async (res) => {
    EnergyTypeList.value = res
    queryParams.type = res[0].id
    getList()
  })
}
//获取项目列表
const getProjectList = ref([])
const getProject = () => {
  BuildApi.getVillageList({}).then((res) => {
    getProjectList.value = res
    queryParams.villageId = res[0].id
  })
}
//获取楼宇列表
const buildingList = ref([])
const building_remoteMethod = () => {
  Api.getBuildingList().then((res) => {
    buildingList.value = res
  })
}
/** 初始化 **/
onMounted(async () => {
  getProject()
  getEnergyTypeList()
  building_remoteMethod()
})
</script>
<style scoped lang="scss">
.unactive {
  background-color: #fafafa;
  padding: 8px 15px;
  color: #252548;
  font-size: 14px;
  cursor: pointer;
  border: 1px solid #f0f0f0;
  border-bottom: none;
  border-radius: 4px;
}
.active {
  background-color: #ffffff;
  padding: 8px 15px;
  color: #1892ff;
  font-size: 14px;
  border: 1px solid #f0f0f0;
  border-bottom: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
