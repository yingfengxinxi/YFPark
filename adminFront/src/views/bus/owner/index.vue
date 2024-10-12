<template>
  <div v-if="!show_Archivist && !show_detail">
    <ContentWrap>
      <div class="table_row">
        <el-form
          class="-mb-15px"
          :model="queryParams"
          ref="queryFormRef"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="租客名称">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入租客名称"
              clearable
              @keyup.enter="handleQuery"
              class="!w-240px"
            />
            <!-- <el-input style="display: none" /> -->
          </el-form-item>
          <el-form-item>
            <el-button @click="handleQuery"
              ><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button
            >
            <el-button @click="resetQuery"
              ><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button
            >
          </el-form-item>
        </el-form>
        <el-button @click="show_Archivist = true">归档租客列表</el-button>
      </div>
      <!-- 搜索工作栏 -->
    </ContentWrap>
    <!-- 汇总 -->
    <ContentWrap>
      <General :row_data="total_row_data" />
    </ContentWrap>
    <!-- 列表 -->
    <ContentWrap>
      <div class="table_row">
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo !border-none villageMenu row_menu"
          mode="horizontal"
          @select="handleSelect"
        >
          <el-menu-item index="0">企业租客</el-menu-item>
          <el-menu-item index="1">个人租客</el-menu-item>
        </el-menu>
        <div>
          <el-button
            type="primary"
            plain
            @click="Image_show = true"
            v-hasPermi="['bus:owner:import']"
          >
            <Icon icon="ep:download" class="mr-5px" /> 导入
          </el-button>
          <el-button
            type="primary"
            plain
            @click="handleExport"
            :loading="exportLoading"
            v-hasPermi="['bus:owner:export']"
          >
            <Icon icon="ep:upload" class="mr-5px" /> 导出
          </el-button>
          <el-button type="primary" @click="addform" v-hasPermi="['bus:owner:create']">
            <Icon icon="ep:plus" class="mr-5px" /> 新增
          </el-button>
        </div>
      </div>
      <el-table
        v-loading="loading"
        class="list"
        :data="list"
        :stripe="true"
        @row-click="table_row_click"
      >
        <el-table-column
          label="租客名称"
          fixed
          width="200px"
          prop="name"
          align="center"
          show-overflow-tooltip
        />
        <el-table-column label="标签" width="200px" prop="tagInfo">
          <template #default="scope">
            <TagIdArrList :val="scope.row?.tagInfo" :list="industry" v-if="scope.row?.tagInfo" />
            <span v-else>--</span>
          </template>
        </el-table-column>
        <el-table-column
          label="租客编码"
          width="200px"
          align="center"
          prop="tenantNo"
          show-overflow-tooltip
        />
        <el-table-column
          label="营业期限"
          width="200px"
          align="center"
          prop="businessInfoBusinessTerm"
          v-if="activeIndex == '0'"
        />
        <el-table-column
          label="成立日期"
          width="200px"
          align="center"
          prop="businessInfoFoundingTime"
          v-if="activeIndex == '0'"
        />
        <el-table-column
          label="组织机构代码"
          width="200px"
          align="center"
          prop="orgId"
          v-if="activeIndex == '0'"
        />
        <el-table-column label="行业分类" width="200px" align="center" prop="industryId">
          <template #default="scope">
            <span v-for="(item, index) in Owner" :key="index">
              <span v-if="scope.row.industryId == item.id">{{ item.name }}</span>
            </span>
          </template>
        </el-table-column>
        <el-table-column
          label="联系人"
          width="200px"
          align="center"
          prop="contactName"
          show-overflow-tooltip
        />
        <el-table-column
          label="证件号码"
          width="200px"
          align="center"
          prop="certificateNumber"
          show-overflow-tooltip
        />
        <el-table-column
          label="租客电话"
          width="200px"
          align="center"
          prop="tel"
          show-overflow-tooltip
        />
        <el-table-column
          label="所属园区"
          width="200px"
          align="center"
          prop="villageName"
          show-overflow-tooltip
        />
        <el-table-column label="操作" fixed="right" width="200px" align="center">
          <template #default="scope">
            <el-button
              link
              type="primary"
              @click.stop="openForm(scope.row.id)"
              v-hasPermi="['bus:owner:update']"
            >
              编辑
            </el-button>
            <el-button
              link
              type="danger"
              @click.stop="handleDelete(scope.row.id)"
              v-hasPermi="['bus:owner:delete']"
            >
              删除
            </el-button>
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

    <!-- 模板导入弹窗 -->
    <Import
      :title="'导入文件'"
      v-model:show="Image_show"
      @down-loadmb="downLoadmb"
      @change="change"
      @submit="submit"
    />
  </div>
  <!-- 新增/编辑抽屉 -->
  <!-- <AddForm
    v-model:show="drawer_show"
    :type="drawer_type"
    @submit="createOwner_click"
    :id="drawer_id"
  /> -->
  <AddForm ref="FormRef" @success="createOwner_click" />
  <!-- 归档租客 -->
  <Archivist_table
    @back="show_Archivist = false"
    v-if="show_Archivist"
    @del="handleDelete"
    @change="openForm"
    :getlist="changeList"
    @row-click="table_row_click"
  />
  <!-- table详情 -->
  <Form_detail
    :table_detail_data="table_detail_data"
    :active_type="activeIndex"
    :detail_id="table_detail_data.id"
    v-if="show_detail"
    @back="(res) => (show_detail = res)"
    @del="handleDelete"
    @change="openForm"
  />
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { OwnerApi, OwnerVO } from '@/api/bus/owner'
import General from './component/index_General.vue'
import Form_detail from './form_detail.vue'
import Import from './component/import.vue'
import Editor from '@/components/Editor/src/Editor.vue'
import Archivist_table from './archivist_table.vue'
import { TagOwnerApi } from '@/api/bus/tag/owner'

import AddForm from './component/addFom.vue'
/** 租客信息 列表 */
defineOptions({ name: 'Owner' })
const show_detail = ref(false)
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const total_row_data = ref([])
const loading = ref(false) // 列表的加载中
const list = ref<OwnerVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const filterHandler = (value: string, row: User, column: TableColumnCtx<User>) => {
  const property = column['property']
  return row[property] === value
}
const activeIndex = ref('0')
const handleSelect = (key: string, keyPath: string[]) => {
  activeIndex.value = key
  getList()
}

const queryParams = ref({
  pageNo: 1,
  pageSize: 10
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const faceChange = (file: any) => {
  const faceimg = new FormData()
  faceimg.append('file', file.raw)
  OwnerApi.uploadFile(faceimg).then((res) => {
    form.value.logo = res.data
  })
}
/** 查询列表 */
const industry = ref([])
const Owner = ref([])
const getList = async () => {
  loading.value = true
  try {
    queryParams.value.isPersonal = activeIndex
    const data = await OwnerApi.getOwnerPage(queryParams.value)
    list.value = data.list
    total.value = data.total

    list.value.forEach((item) => {
      if (item.tagInfo) {
        item.tagInfo = item.tagInfo.split(',')
      } else {
        item.tagInfo = []
      }
    })
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  queryParams.value = {
    pageNo: 1,
    pageSize: 10
  }
  getList()
}

/** 添加/修改操作 */
const drawer_id = ref()
const formRef = ref()

const FormRef = ref()
const openForm = (id?: number) => {
  // drawer_show.value = true
  drawer_type.value = 'edit'
  drawer_id.value = id
  FormRef.value.open(drawer_type.value, drawer_id.value)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await OwnerApi.deleteOwner(id)
    message.success(t('删除成功'))
    await getList()
    show_detail.value = false
    changeList.value++
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    await message.exportConfirm()
    exportLoading.value = true
    const data = await OwnerApi.exportOwner(queryParams.value)
    download.excel(data, '租客信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
// 添加修改抽屉
const drawer_type = ref('')
const table_detail_data = ref({})
const table_row_click = (row) => {
  OwnerApi.getOwnerById(row.id).then((res) => {
    table_detail_data.value = res
    table_detail_data.value.businessInfo = JSON.parse(res.businessInfo)
    show_detail.value = true
  })
}
//新增
const drawer_show_index = ref('0')
const addform = () => {
  drawer_type.value = 'add'
  // drawer_show.value = true
  FormRef.value.open(drawer_type.value)
}
const select_drawer = (key) => {
  drawer_show_index.value = key
}
//抽屉详情
let drawer_show = ref(false)
//联系人
const contactName_options = ref([])

const remoteMethod = (val) => {
  OwnerApi.getOwnerListByName(val).then((res) => {
    contactName_options.value = res
  })
}
const changeList = ref(0)
//确认新增/修改
const createOwner_click = async (type) => {
  getList()
  if (type == 1) {
    changeList.value++
  }
}

// 导入模块数据
const fileList = ref([])
const Image_show = ref(false)
const downLoadmb = async () => {
  const data = await OwnerApi.downloadTemplate()
  download.excel(data, '租客信息模板.xls')
}
const change = async (res) => {
  fileList.value.push(res.raw)
}
const submit = async (res) => {
  const formData = new FormData()
  const unRefList = unref(fileList)
  unRefList.map((file) => {
    formData.append('file', file)
  })
  await OwnerApi.import(formData)
    .then((res) => {
      message.success('文件上传成功')
    })
    .catch(() => {
      fileList.value = []
    })
}

//归档租客
const show_Archivist = ref(false)
const tagInfo_option = ref([])

/** 获取行业分类 */
const getTagIndustryList = async (name) => {
  const data = await OwnerApi.getTagIndustryList(name)
  Owner.value = data
}
/** 获取行业标签 */
const getTagOwnerList = async (name) => {
  const data = await TagOwnerApi.getTagOwnerList(name)
  industry.value = data
}
/** 租客信息统计 */
const getCountOwnerMap = async () => {
  const data = await OwnerApi.getCountOwnerMap('')
  total_row_data.value = data
}
/** 激活时 */
onActivated(async () => {
  loading.value = true
  await getCountOwnerMap()
  await getTagIndustryList('')
  await getTagOwnerList('')
  await remoteMethod('')
  await getList()
})
/** 初始化 **/
onMounted(async () => {
  loading.value = true
  await getCountOwnerMap()
  await getTagIndustryList('')
  await getTagOwnerList('')
  await remoteMethod('')
  setTimeout(() => {
    getList()
  }, 1000)
  //租客信息统计
})
</script>
<style lang="scss" scoped>
.table_row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  .row_menu {
    min-width: 300px;
  }
}

.list ::v-deep .el-table__body tr:hover > td {
  cursor: pointer;
}
.villageMenu .el-menu-item:hover,
.villageMenu .el-menu-item:focus {
  background: none;
}
.drawer {
  max-height: 75vh;
  overflow-y: auto;
  overflow-x: hidden;

  .drawer_header-title {
    margin-bottom: 20px;
    font-size: 16px;
    font-weight: 600;
    color: #4a4a4a;
  }
}
.drawer_footer {
  display: flex;
  justify-content: flex-end;
  position: fixed;
  bottom: 40px;
  right: 40px;
}
.uploader_BOX {
  background: #f7f7f7;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 110px;
  height: 110px;
  display: flex;
  justify-content: center;
  align-items: center;
  .img {
    width: 100%;
    height: 100%;
  }
  .plus {
    width: 40px;
    height: 40px;
  }
}
</style>
