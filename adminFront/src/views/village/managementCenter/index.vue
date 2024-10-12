<template>
  <el-container v-if="!showAddContract">
    <el-aside
      width="280px"
      class="bg-#fff p-10px border-r-solid"
      style="border-right-color: #f4f4f4d9"
    >
      <el-scrollbar
        :class="[
          `!h-[calc(100vh-var(--tags-view-height)-var(--app-footer-height)-var(--top-tool-height)-var(--tags-view-height)-52px)] `
        ]"
      >
        <DeptTree ref="DeptTreeRef" @node-click="handleDeptNodeClick" />
        <div class="roomList">
          <div v-for="(item, index) in roomList" :key="index">
            <Icon class="mr-5px" icon="fa-solid:building" />
            <div class="text">
              <div class="room">房间号：2 / 203</div>
              <div class="address">楼宇：清华大学园区 / 人工智能场地</div>
            </div>
          </div>
        </div>
      </el-scrollbar>
      <div class="flex justify-center color-blueGray p-t-10px" v-if="levelNum == 1">
        <el-button size="small" @click="addPark">
          <Icon class="mr-5px" icon="ep:plus" />新建项目</el-button
        >
        <el-button size="small" @click="addVillage">
          <Icon class="mr-5px" icon="ep:plus" />新建楼宇</el-button
        >
      </div>
      <div class="flex justify-center color-blueGray p-t-10px" v-if="levelNum == 2">
        <el-button size="small" @click="AddFloorClick">
          <Icon class="mr-5px" icon="ep:plus" />添加楼层</el-button
        >
        <el-button size="small" @click="Image_show = true">
          <Icon class="mr-5px" icon="ep:upload" />导入房源</el-button
        >
      </div>
      <div class="flex justify-center color-blueGray p-t-10px" v-if="levelNum == 3">
        <el-button size="small" @click="AddRoomClick">
          <Icon class="mr-5px" icon="ep:plus" />新增房源</el-button
        >
        <el-button size="small" @click="Image_show = true">
          <Icon class="mr-5px" icon="ep:upload" />导入房源</el-button
        >
      </div>
    </el-aside>
    <div class="flex-1 w-0">
      <div class="bg-#fff p-20px p-b-0">
        <el-row justify="space-between w100%">
          <el-col :span="16">
            <h4 class="m-0 flex items-center font-size-20px fw-700">
              <Icon
                class="mr-5px"
                :icon="
                  levelNum == 1
                    ? 'fa:bank'
                    : levelNum == 2
                      ? 'fa-solid:building'
                      : levelNum == 3
                        ? 'fa:database'
                        : 'fa-solid:door-open'
                "
              />
              {{
                levelNum == 1
                  ? detailInfo.name
                  : levelNum == 2
                    ? detailInfo.buildName
                    : levelNum == 3
                      ? detailInfo.layerName
                      : levelNum == 4
                        ? detailInfo.roomName
                        : '--'
              }}
            </h4>
          </el-col>
          <el-col :span="8" class="flex justify-end">
            <el-button
              type="danger"
              class="m-r-6px"
              v-if="levelNum != 4"
              plain
              @click="handleDelete()"
            >
              <Icon class="mr-5px" icon="ep:delete" />删除</el-button
            >
            <el-button type="primary" plain @click="edit()" class="m-r-6px">
              <Icon class="mr-5px" icon="ep:edit" />编辑</el-button
            >
            <el-button
              type="primary"
              class="m-r-6px"
              v-if="levelNum == 2"
              plain
              @click="editFloorList()"
            >
              <Icon class="mr-5px" icon="ep:coin" />楼层管理</el-button
            >
            <div>
              <el-button
                type="primary"
                v-if="levelNum == 4 && detailInfo.roomStatus == 10"
                class="m-r-6px"
                @click="toAddContract(detailInfo)"
              >
                <Icon icon="ep:tickets" />
                录入合同
              </el-button>
            </div>
            <el-dropdown type="primary" class="m-r-6px" v-if="levelNum == 4">
              <el-button type="primary">
                更多操作 <Icon icon="ep:arrow-down"><arrow-down /></Icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item><el-button link> 锁定 </el-button></el-dropdown-item>
                  <el-dropdown-item
                    ><el-button link @click="handleDelete()"> 删除 </el-button></el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
        <el-menu
          v-if="levelNum != 3 && levelNum != 4"
          :default-active="activeIndex"
          ellipsis
          class="el-menu-demo villageMenu"
          style="max-width: calc(100vw - 300px - var(--left-menu-max-width) - 40px)"
          mode="horizontal"
          popper-append-to-body
          teleported
          @select="handleSelect"
        >
          <template v-if="levelNum == 1">
            <el-menu-item index="0">项目概况</el-menu-item>
            <el-menu-item index="1">工单记录</el-menu-item>
            <el-menu-item index="2">资产记录</el-menu-item>
            <el-menu-item index="3">智能硬件</el-menu-item>
            <el-menu-item index="4">水电能耗</el-menu-item>
            <el-menu-item index="5">项目信息</el-menu-item>
          </template>
          <template v-else-if="levelNum == 2">
            <el-menu-item index="0">房态管理</el-menu-item>
            <el-menu-item index="1">项目概况</el-menu-item>
            <el-menu-item index="2">工单记录</el-menu-item>
            <el-menu-item index="3">资产记录</el-menu-item>
            <el-menu-item index="4">智能硬件</el-menu-item>
            <el-menu-item index="15">水电能耗</el-menu-item>
            <el-menu-item index="6">楼宇信息</el-menu-item>
          </template>
          <template v-else-if="levelNum == 4">
            <el-menu-item index="6">房源信息</el-menu-item>
            <el-menu-item index="7">租客账单</el-menu-item>
            <el-menu-item index="8">租客流水</el-menu-item>
            <el-menu-item index="9">租客合同</el-menu-item>
            <el-menu-item index="10">租客员工</el-menu-item>
            <el-menu-item index="11">财务记录</el-menu-item>
            <el-menu-item index="2">工单记录</el-menu-item>
            <el-menu-item index="3">资产记录</el-menu-item>
            <el-menu-item index="4">智能硬件</el-menu-item>
            <el-menu-item index="15">水电记录</el-menu-item>
            <el-menu-item index="12">绑定车辆</el-menu-item>
            <el-menu-item index="13">备忘提醒</el-menu-item>
            <el-menu-item index="14">房东信息</el-menu-item>
          </template>
        </el-menu>
        <el-menu
          v-if="levelNum == 4"
          :default-active="activeIndex"
          ellipsis
          class="el-menu-demo villageMenu"
          style="max-width: calc(100vw - 300px - var(--left-menu-max-width) - 40px)"
          mode="horizontal"
          popper-append-to-body
          teleported
          @select="handleSelect"
        >
          <el-menu-item index="6">房源信息</el-menu-item>
          <el-menu-item index="7">租客账单</el-menu-item>
          <el-menu-item index="8">租客流水</el-menu-item>
          <el-menu-item index="9">租客合同</el-menu-item>
          <el-menu-item index="10">租客员工</el-menu-item>
          <el-menu-item index="11">财务记录</el-menu-item>
          <el-menu-item index="2">工单记录</el-menu-item>
          <el-menu-item index="3">资产记录</el-menu-item>
          <el-menu-item index="4">智能硬件</el-menu-item>
          <el-menu-item index="15">水电记录</el-menu-item>
          <el-menu-item index="12">绑定车辆</el-menu-item>
          <el-menu-item index="13">备忘提醒</el-menu-item>
          <el-menu-item index="14">房东信息</el-menu-item>
        </el-menu>
      </div>
      <el-scrollbar
        :class="[
          `!h-[calc(100vh-var(--tags-view-height)-var(--app-footer-height)-var(--top-tool-height)-var(--tags-view-height)-110px)] `
        ]"
      >
        <!-- 房态管理 -->
        <RoomStatusManagement
          ref="RoomStatusManagementRef"
          v-if="levelNum == 3"
          :id="buildId"
          :layerId="queryParams.id"
          @addcon="toAddContract"
        />
        <!-- 房态管理 -->
        <RoomStatusManagement
          ref="RoomStatusManagementRef"
          v-if="levelNum == 2 && activeIndex == '0'"
          :id="queryParams.id"
          @addcon="toAddContract"
        />
        <template
          v-if="(activeIndex == '0' && levelNum == 1) || (activeIndex == 1 && levelNum == 2)"
        >
          <ProjectOverview
            :id="queryParams.id"
            :villageId="villageId"
            :level="levelNum"
            ref="ProjectOverviewRef"
        /></template>
        <ProjectInfo v-if="activeIndex == '5'" :info="detailInfo" />
        <BuildingInfo v-if="activeIndex == '6'" :level="levelNum" :info="detailInfo" />
        <RoomInfo v-if="activeIndex == '6'" :level="levelNum" :info="detailInfo" />
        <OwnerCheck v-if="activeIndex == '7'" :info="detailInfo" :roomId="detailInfo.id" />
        <OwnerStreamMiddle v-if="activeIndex == '8'" :info="detailInfo" :roomId="detailInfo.id" />
        <!-- 租客合同 -->
        <RoomContracList v-show="activeIndex == '9'" ref="RoomContracList_ref" class="m-10px" />
        <!-- 租客员工 -->
        <Tenantemployee v-if="activeIndex == '10'" :info="detailInfo" :roomId="detailInfo.id" />
        <!-- 财务记录 -->
        <FinancialRecord v-if="activeIndex == '11'" :info="detailInfo" :roomId="detailInfo.id" />
        <!-- 备忘提醒 -->
        <MemoReminder v-if="activeIndex == '13'" :info="detailInfo" :roomId="detailInfo.id" />
      </el-scrollbar>
    </div>
  </el-container>
  <!-- 表单弹窗：添加/修改 -->
  <SelectPark ref="SelectParkRef" @select-pick="addGarden" />
  <AddBuilding ref="AddBuildingRef" @success="Refresh" />
  <BuildForm ref="formRef" @success="getDetail" />
  <EditGarden ref="EditGardenRef" @success="Refresh" />
  <AddFloor ref="AddFloorRef" @success="Refresh" />
  <FloorList ref="FloorListRef" @success="getDetail" />
  <AddRoom ref="AddRoomRef" @success="Refresh" />
  <!-- 房源导入弹窗 -->
  <Import
    :title="'导入资源'"
    v-model:show="Image_show"
    @down-loadmb="downLoadmb"
    @change="change"
    @submit="submit"
  />
  <!-- 添加合同 -->
  <AddContract ref="AddContractRef" v-if="showAddContract" @close="backContract" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import Import from './import.vue'
import { BuildApi, VillageBVO } from '@/api/bus/village'
import { TagBuildApi } from '@/api/bus/tag/build'
import { TagHouseApi } from '@/api/bus/tag/house'
import { FloorApi } from '@/api/bus/village/floor'
import BuildForm from './BuildForm.vue'
import DeptTree from './DeptTree.vue'
import ProjectOverview from './ProjectOverview.vue'
import RoomStatusManagement from './RoomSatusManagement.vue'
import SelectPark from './SelectPark.vue'
import ProjectInfo from './ProjectInfo.vue'
import BuildingInfo from './BuildingInfo.vue'
import RoomInfo from './RoomInfo.vue'
import EditGarden from './EditGarden.vue'
import AddBuilding from './AddBuilding.vue'
import AddFloor from './AddFloor.vue'
import FloorList from './FloorList.vue'
import AddRoom from './RoomForm.vue'
import OwnerCheck from '@/views/bus/owner/component/check.vue'
import RoomContracList from '@/views/bus/owner/component/user_getcontracList.vue'
import OwnerStreamMiddle from '@/views/bus/bill/streamMiddle/components/ownerStreamMiddle.vue'
import MemoReminder from './memoReminder.vue'
import FinancialRecord from './financialRecord.vue'
import { RoomApi } from '@/api/bus/village/Room'
import Tenantemployee from '@/views/bus/owner/Tenantemployee.vue'
import AddContract from '@/views/contract/contractList/add_contractList.vue'
import { get } from 'http'
/** 项目楼栋 列表 */
defineOptions({ name: 'ManagementCenter' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
// 导入模块数据
const fileList = ref([])
const Image_show = ref(false)
const loading = ref(true) // 列表的加载中
const queryParams = reactive({
  id: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const roomList = ref([] as any[])
const activeIndex = ref('0')
const levelNum = ref(1)
const detailInfo = ref<VillageBVO>({})
const RoomContracList_ref = ref()
const getContracList = () => {
  if (RoomContracList_ref.value) {
    RoomContracList_ref.value.getList(queryParams.id, 'room')
  }
}
const handleSelect = (key: string, keyPath: string[]) => {
  activeIndex.value = key
  if (key == '9') {
    getContracList()
  }
}
const downLoadmb = async () => {
  const data = await RoomApi.getImportTemplate()
  download.excel(data, '租客信息模板.xls')
}
const change = async (res) => {
  console.log(res)
  fileList.value.push(res.raw)
}

const submit = async (res) => {
  const formData = new FormData()
  const unRefList = unref(fileList)
  unRefList.map((file) => {
    console.log(file)
    formData.append('file', file)
  })
  await RoomApi.importRoom(formData)
    .then((res) => {
      message.success('文件上传成功')
      Image_show.value = false
    })
    .catch(() => {
      fileList.value = []
    })
}
/** 查询列表 */
const getDetail = async () => {
  loading.value = true
  try {
    const data =
      levelNum.value == 1
        ? await BuildApi.getvillage(queryParams.id)
        : levelNum.value == 2
          ? await BuildApi.getBuild(queryParams.id)
          : levelNum.value == 3
            ? await FloorApi.getlayer(queryParams.id)
            : await RoomApi.getRoom(queryParams.id)
    if (data.extraConfig) data.extraConfig = JSON.parse(data.extraConfig)
    if (data.tagInfo) data.tagInfo = JSON.parse(data.tagInfo)
    if (data.tagIdArr) data.tagIdArr = JSON.parse(data.tagIdArr)
    detailInfo.value = data
    if (detailInfo.value.tagInfo && detailInfo.value.tagInfo.length && levelNum.value == 2) {
      getTagBuild()
    }
    const name =
      levelNum.value == 1
        ? detailInfo.value.name
        : levelNum.value == 2
          ? detailInfo.value.buildName
          : levelNum.value == 3
            ? detailInfo.value.layerName
            : detailInfo.value.roomName
    DeptTreeRef.value.SetName(
      levelNum.value,
      TreeId.value,
      name,
      villageId.value,
      buildId.value,
      layerId.value
    )
  } finally {
    loading.value = false
  }
}
const RoomStatusManagementRef = ref()
const Refresh = (type) => {
  console.log(type, 'type', levelNum.value)
  if (type == 'create') {
    DeptTreeRef.value.Refresh(
      type,
      levelNum.value,
      detailInfo.value,
      villageId.value,
      buildId.value,
      layerId.value
    )
    if (levelNum.value == 2 || levelNum.value == 3) {
      RoomStatusManagementRef.value.getDetail()
    }
  } else if (type == 'delete') {
    // if (levelNum.value != 1) {
    DeptTreeRef.value.Refresh(
      type,
      levelNum.value,
      detailInfo.value,
      villageId.value,
      buildId.value,
      layerId.value
    )
    // }
  } else {
    getDetail()
  }
}
// 获取楼宇标签

const getTagBuild = async () => {
  try {
    const data = await TagBuildApi.getTagBuildListByIds(detailInfo.value.tagInfo)
    detailInfo.value.tagIdArr = data
  } catch {}
}

const FloorListRef = ref()
const editFloorList = () => {
  FloorListRef.value.open(queryParams.id, villageId.value, detailInfo.value)
}

const DeptTreeRef = ref()
// 删除
const handleDelete = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    levelNum.value == 1
      ? await BuildApi.deletevillage(queryParams.id)
      : levelNum.value == 2
        ? await BuildApi.deleteBuild(queryParams.id)
        : levelNum.value == 3
          ? await FloorApi.deletelayer(queryParams.id)
          : await RoomApi.deleteRoom(queryParams.id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    console.log('刷新')
    Refresh('delete')
    // DeptTreeRef.value.getTree()
    // await getList()
  } catch {}
}
const AddBuildingRef = ref()
// 编辑园区
const EditGardenRef = ref()
const edit = () => {
  if (levelNum.value == 1) {
    EditGardenRef.value.open(
      'updated',
      queryParams.id,
      JSON.parse(JSON.stringify(detailInfo.value))
    )
  } else if (levelNum.value == 2) {
    AddBuildingRef.value.open(
      'updated',
      queryParams.id,
      JSON.parse(JSON.stringify(detailInfo.value))
    )
  } else if (levelNum.value == 3) {
    AddFloorRef.value.open(
      'updated',
      buildId.value,
      villageId.value,
      JSON.parse(JSON.stringify(detailInfo.value))
    )
  } else if (levelNum.value == 4) {
    AddRoomRef.value.open(
      'updated',
      villageId.value,
      buildId.value,
      detailInfo.value.id,
      JSON.parse(JSON.stringify(detailInfo.value))
    )
  }
}
//录入合同
const showAddContract = ref(false)
const backContract = () => {
  showAddContract.value = false
  getDetail()
}
const AddContractRef = ref()
const toAddContract = (info) => {
  console.log('888888')
  console.log(detailInfo)
  const RoomData = {
    id: info.id,
    villageId: info.villageId,
    contractId: '',
    buildId: info.buildId,
    villageRoomId: info.roomName,
    villageName: info.villageName,
    roomName: info.roomName,
    roomId: info.roomNumber,
    roomNumber: info.roomNumber,
    rentalArea: info.rentalArea,
    buildName: info.buildName,
    layerName: info.layerName,
    level_key: `3-${info.id}`,
    level1_key: `1-${info.buildId}`,
    level2_key: `2-${info.layerId}`
  }
  showAddContract.value = true
  let list = []
  list.push(RoomData)
  setTimeout(() => {
    AddContractRef.value.addRoomToSelect(list)
  }, 50)
}
// 新建园区
const addGarden = (item: Object) => {
  EditGardenRef.value.open('create', item.alias)
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getDetail()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}
/** 新建园区 */
const SelectParkRef = ref()
const addPark = () => {
  SelectParkRef.value.open()
}
const AddFloorRef = ref()
const AddFloorClick = () => {
  AddFloorRef.value.open('create', villageId.value, queryParams.id, '')
}
// 新增房源
const AddRoomRef = ref()
const AddRoomClick = () => {
  AddRoomRef.value.open('create', villageId.value, buildId.value, queryParams.id, '')
}
/** 新建楼宇 */
const addVillage = () => {
  AddBuildingRef.value.open('create', '1')
}
const villageId = ref()
const buildId = ref()
const TreeId = ref()
const layerId = ref()
/** 处理部门被点击 */
const handleDeptNodeClick = async (row, level: number) => {
  console.log(row, '点击')
  queryParams.id = row.id
  levelNum.value = level
  TreeId.value = row.treeId
  if (row.villageId) {
    villageId.value = row.villageId
  }
  if (level == 1) {
    villageId.value = row.id
  }
  if (level == 2) {
    buildId.value = row.id
    villageId.value = row.villageId
  }
  if (level == 3) {
    buildId.value = row.buildId
    villageId.value = row.villageId
    layerId.value = row.id
  }
  activeIndex.value = '0'
  if (level == 4) {
    activeIndex.value = '6'
    layerId.value = row.layerId
    buildId.value = row.buildId
    villageId.value = row.villageId
  }
  await getDetail()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
// const handleDelete = async (id: number) => {
//   try {
//     // 删除的二次确认
//     await message.delConfirm()
//     // 发起删除
//     await BuildApi.deleteBuild(id)
//     message.success(t('common.delSuccess'))
//     // 刷新列表
//     await getDetail()
//   } catch {}
// }

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

const addContract = (info) => {
  console.log(info, 'info')
  // emit('addContract', detailInfo.value)
}

/** 初始化 **/
onMounted(() => {
  // getDetail()
})
</script>

<style lang="scss" scoped>
.villageMenu .el-menu-item:hover,
.villageMenu .el-menu-item:focus {
  background: none;
}
</style>
