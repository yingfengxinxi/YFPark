<template>
  <ContentWrap>
    <el-row :gutter="20">
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >建筑数量<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectBuild.buildCount || 0 }} 栋</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >管理面积<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectBuild.managementArea || 0 }} ㎡</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >在租面积<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectBuild.rentInArea || 0 }} ㎡</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >在租合同数<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectBuild.rentInContract || 0 }} 份</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >待租面积<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectBuild.toRentArea || 0 }} ㎡</div
        ></el-col
      >
      <el-col :span="4" class="c-#00000073 font-size-14px"
        >本年总营收目标<div class="c-#000000d9 font-size-24px m-t-10px"
          >{{ projectBuild.revenueTargetYear || 0 }} 万元</div
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
    >
      <el-form-item label="楼栋编号" prop="buildNumber">
        <el-input
          v-model="queryParams.buildNumber"
          placeholder="请输入楼栋编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="楼栋名称" prop="buildName">
        <el-input
          v-model="queryParams.buildName"
          placeholder="请输入楼栋名称"
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
          v-hasPermi="['bus:build:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:build:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column
        label="楼栋名称"
        align="center"
        prop="buildName"
        width="200"
        :formatter="tableColumnEmptyPlaceholder"
        show-overflow-tooltip
      />
      <el-table-column
        label="所属园区"
        align="center"
        prop="villageName"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
        show-overflow-tooltip
      /><el-table-column
        label="楼栋编号"
        align="center"
        prop="buildNumber"
        width="140"
        :formatter="tableColumnEmptyPlaceholder"
        show-overflow-tooltip
      />
      <el-table-column label="建筑标签" align="center" prop="tagInfo" width="200">
        <template #default="scope">
          <template v-if="scope.row.tagArr && scope.row.tagArr.length">
            <template v-for="(item, index) in scope.row.tagArr" :key="index">
              <span
                class="border-1px inline-block b-solid b-rd-6px border-gray m-r-4px p-6px p-t-2px p-b-2px font-size-12px h-16px line-height-16px"
                >{{ item.name }}</span
              >
            </template>
          </template>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column
        label="建筑面积"
        align="center"
        prop="buildArea"
        width="140"
        show-overflow-tooltip
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="可租面积"
        align="center"
        prop="rentableArea"
        width="140"
        show-overflow-tooltip
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="管理面积"
        align="center"
        prop="managementArea"
        width="140"
        show-overflow-tooltip
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="房间总数"
        align="center"
        prop="roomCount"
        width="140"
        show-overflow-tooltip
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="出租率（%）"
        align="center"
        prop="rentInArea"
        width="140"
        show-overflow-tooltip
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="在租面积（㎡）"
        align="center"
        prop="rentInArea"
        width="140"
        show-overflow-tooltip
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="在租合同数（份）"
        align="center"
        prop="rentInContract"
        width="140"
        show-overflow-tooltip
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="操作" fixed="right" min-width="180">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id, scope.row)"
            v-hasPermi="['bus:build:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            @click="openDetail(scope.row)"
            v-hasPermi="['bus:build:update']"
          >
            楼层管理
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:build:delete']"
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
  <AddBuilding ref="AddBuildingRef" @success="getList" />
  <FloorList ref="FloorListRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import download from '@/utils/download'
import { BuildApi, BuildVO } from '@/api/bus/architecture'
import AddBuilding from '../managementCenter/AddBuilding.vue'
import FloorList from '../managementCenter/FloorList.vue'
import { TagBuildApi } from '@/api/bus/tag/build'

/** 项目楼栋 列表 */
defineOptions({ name: 'Build' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<BuildVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  buildNumber: undefined,
  buildName: undefined,
  logo: undefined,
  zoneId: undefined,
  villageId: undefined,
  haveUnit: undefined,
  districtTxt: undefined,
  countryId: undefined,
  provinceId: undefined,
  cityId: undefined,
  districtId: undefined,
  streetId: undefined,
  communityId: undefined,
  lng: undefined,
  lat: undefined,
  address: undefined,
  floorHeight: undefined,
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
  rentInContract: undefined,
  invitationRoomCount: undefined,
  revenueTarget: undefined,
  accountDefault: undefined,
  extraConfig: undefined,
  tagInfo: undefined,
  isHot: undefined,
  sort: undefined,
  status: undefined,
  threeDimensionalFile: undefined,
  threeDimensionalId: undefined,
  dimensionalBgImg: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const projectBuild = ref<any>({})
const tagBuildList = ref<any[]>([])

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await BuildApi.getBuildPage(queryParams)
    if (data.list.length > 0) {
      data.list.forEach((item) => {
        if (item.tagInfo) item.tagInfo = JSON.parse(item.tagInfo)
        item.tagArr = []
        if (item.tagInfo)
          item.tagInfo.forEach((element) => {
            // item.name = item.name.split('-')[1]
            const index = tagBuildList.value.findIndex((items) => items.id == element)
            if (index >= 0) {
              console.log(tagBuildList.value, 'tagBuildList.value', index)
              item.tagArr.push(tagBuildList.value[index])
            }
          })
      })
    }
    console.log(data, 'data')
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const getTagBuildList = async () => {
  const data = await TagBuildApi.getTagBuildList(queryParams.tagInfo)
  tagBuildList.value = data
}
const getProjectBuild = async () => {
  const data = await BuildApi.projectBuild(queryParams)
  projectBuild.value = data
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
const AddBuildingRef = ref()
const openForm = (type: string, id?: number, row?: any) => {
  // formRef.value.open(type, id)
  AddBuildingRef.value.open(type, id, row)
}

/** 楼层管理 */
const FloorListRef = ref()
const openDetail = (row) => {
  FloorListRef.value.open(row.id, row.villageId, row)
  // router.push({
  //   name: 'BuildDetail',
  //   params: { id }
  // })
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await BuildApi.deleteBuild(id)
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
    const data = await BuildApi.exportBuild(queryParams)
    download.excel(data, '项目楼栋.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getTagBuildList()
  getProjectBuild()
  getList()
})
</script>
