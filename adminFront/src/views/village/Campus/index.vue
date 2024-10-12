<template>
  <ContentWrap>
    <el-row :gutter="20">
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >管理面积<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectManage.managementArea || 0 }} ㎡</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >可招商面积
        <el-tooltip content="当前录入的所有招商状态为招商的房源面积之和" placement="bottom">
          <Icon class="" icon="fa:question-circle-o" />
        </el-tooltip>
        <div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectManage.rentableArea || 0 }} ㎡</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >可招商占比<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectManage.rentableScale || 0 }} %</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >总房源数量<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectManage.roomCount || 0 }} 间</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >可招商房源数量<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectManage.roomRentableCount || 0 }} 间</div
        ></el-col
      >
    </el-row>
  </ContentWrap>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
      @submit.prevent="handleQuery()"
    >
      <el-form-item label="项目名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['bus:village:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新建园区
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true">
      <el-table-column label="园区名称" prop="name" :formatter="tableColumnEmptyPlaceholder" />
      <el-table-column
        label="管理面积（㎡）"
        prop="managementArea"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="可招商面积（㎡）"
        prop="rentableArea"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="总房源数量（间）"
        prop="roomCount"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="可招商房源数量（间）"
        prop="roomRentableCount"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="常用电话" width="180px">
        <template #default="scope">
          <el-button link type="primary" @click="lookupPhone(scope.row)"> 查看 </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <!-- <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:village:update']"
          >
            编辑
          </el-button> -->
          <el-button
            link
            type="primary"
            @click="openDetail('query', scope.row.id)"
            v-hasPermi="['bus:village:update']"
          >
            详情
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:village:delete']"
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

  <!-- 表单弹窗：添加/修改 -->
  <Detail ref="DetailRef" @success="getList" />
  <PhoneCategoryList ref="PhoneCategoryListRef" @success="getList" />
  <EditGarden ref="EditGardenRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import download from '@/utils/download'
import { villageApi, villageVO } from '@/api/bus/Campus'
import Detail from './Detail.vue'
import PhoneCategoryList from './PhoneCategoryList.vue'
import EditGarden from '../managementCenter/EditGarden.vue'
import { lookup } from 'dns'

/** 社区 列表 */
defineOptions({ name: 'Village' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<villageVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const villageVO = reactive({
  type: 'officePark'
})
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  type: villageTypeValue.value
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const projectManage = ref<any>({})
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await villageApi.getvillagePage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 查询统计 */
const getProjectManage = async () => {
  loading.value = true
  try {
    const data = await villageApi.projectManage(villageVO)
    projectManage.value = data
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
const EditGardenRef = ref()
const openForm = (type: string, id?: number) => {
  EditGardenRef.value.open(type, id)
}
const DetailRef = ref()
const openDetail = (type: string, id?: number) => {
  DetailRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await villageApi.deletevillage(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await villageApi.exportvillage(queryParams)
    download.excel(data, '社区.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const PhoneCategoryListRef = ref()
// 查看电话号码
const lookupPhone = (row: any) => {
  // message.success(row.id)
  PhoneCategoryListRef.value.open(row.id)
}

/** 初始化 **/
onMounted(() => {
  getList()
  getProjectManage()
})
</script>
