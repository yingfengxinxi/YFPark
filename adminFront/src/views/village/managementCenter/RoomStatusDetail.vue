<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ElDrawer
    class="!bg-#f4f4f4"
    v-model="RoomShow"
    :z-index="200"
    direction="rtl"
    size="70%"
    :show-close="false"
    custom-class="adrawer"
    :with-header="false"
    :before-close="handleClose"
  >
    <div class="flex-1 w-100%">
      <div class="bg-#fff p-20px p-b-0">
        <el-row justify="space-between w100%">
          <el-col :span="16">
            <h4 class="m-0 flex items-center font-size-20px fw-bold">
              <Icon class="mr-5px" icon="fa-solid:door-open" />
              {{ detailInfo.roomName || '--' }}
            </h4>
          </el-col>
          <el-col :span="8" class="flex justify-end">
            <el-button type="primary" class="m-r-6px" plain @click="edit()">
              <Icon class="mr-5px" icon="ep:edit" />编辑</el-button
            >
            <!-- 
              v-if="detailInfo.roomStatus < 10" -->
            <el-button type="primary" class="m-r-6px" plain @click="toAddContract()">
              <Icon class="mr-5px" icon="ep:edit" />录入合同</el-button
            >
            <el-dropdown type="primary" class="m-r-6px" v-if="levelNum == 4">
              <el-button type="primary">
                更多操作 <Icon icon="ep:arrow-down"><arrow-down /></Icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item><el-button link> 锁定 </el-button></el-dropdown-item>
                  <el-dropdown-item
                    ><el-button link @click="handleDelete"> 删除 </el-button></el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
        <el-menu
          :default-active="activeIndex"
          ellipsis
          class="el-menu-demo !border-none villageMenu"
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
          <el-menu-item index="5">水电记录</el-menu-item>
          <el-menu-item index="12">绑定车辆</el-menu-item>
          <el-menu-item index="13">备忘提醒</el-menu-item>
          <el-menu-item index="14">房东信息</el-menu-item>
        </el-menu>
      </div>
      <RoomInfo v-if="activeIndex == '6'" :level="levelNum" :info="detailInfo" />
      <!-- 租客合同 -->
      <RoomContracList v-show="activeIndex == '9'" ref="RoomContracList_ref" class="m-t-10px" />
      <OwnerCheck v-if="activeIndex == '7'" :info="detailInfo" :roomId="detailInfo.id" />
      <OwnerStreamMiddle v-if="activeIndex == '8'" :info="detailInfo" :roomId="detailInfo.id" />
      <!-- 财务记录 -->
      <FinancialRecord v-if="activeIndex == '11'" :info="detailInfo" :roomId="detailInfo.id" />
      <!-- 备忘提醒 -->
      <MemoReminder v-if="activeIndex == '13'" :info="detailInfo" :roomId="detailInfo.id" />
      <!-- 租客员工 -->
      <Tenantemployee v-if="activeIndex == '10'" :info="detailInfo" :roomId="detailInfo.id" />
    </div>
    <AddRoom ref="AddRoomRef" @success="getDetail" />
  </ElDrawer>
</template>

<script setup lang="ts">
import { RoomApi, RoomBVO } from '@/api/bus/village/Room'
import RoomContracList from '@/views/bus/owner/component/user_getcontracList.vue'
import MemoReminder from './memoReminder.vue'
import OwnerCheck from '@/views/bus/owner/component/check.vue'
import Tenantemployee from '@/views/bus/owner/Tenantemployee.vue'
import FinancialRecord from './financialRecord.vue'
import RoomInfo from './RoomInfo.vue'
import AddRoom from './RoomForm.vue'
import OwnerStreamMiddle from '@/views/bus/bill/streamMiddle/components/ownerStreamMiddle.vue'

const emit = defineEmits(['addcon', 'success']) // 定义 success 事件，用于操作成功后的回调
/** 项目楼栋 列表 */
defineOptions({ name: 'RoomStatusDetail' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const RoomShow = ref(false)
const loading = ref(true) // 列表的加载中
const queryParams = reactive({
  id: undefined
})
const activeIndex = ref('6')
const detailInfo = ref<RoomBVO>({})
const levelNum = ref(4)
const handleClose = () => {
  RoomShow.value = false
}
const handleSelect = async (key: string, keyPath: string[]) => {
  activeIndex.value = key
  if (key == '9') {
    getContracList()
  }
}

/** 打开抽屉 */
const open = async (item?: any) => {
  RoomShow.value = true
  queryParams.id = item.id
  getDetail()
  getContracList()
}

const toAddContract = () => {
  console.log('录入合同子级')
  emit('addcon', detailInfo.value)
}

/** 查询列表 */
const getDetail = async () => {
  loading.value = true
  try {
    const data = await RoomApi.getRoom(queryParams.id)
    if (data.extraConfig) data.extraConfig = JSON.parse(data.extraConfig)
    if (data.tagIdArr) data.tagIdArr = JSON.parse(data.tagIdArr)
    detailInfo.value = data
  } finally {
    loading.value = false
  }
}
//租客合同
const RoomContracList_ref = ref()

const getContracList = () => {
  if (RoomContracList_ref.value) {
    RoomContracList_ref.value.getList(queryParams.id, 'room')
  }
}
// 删除
const handleDelete = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await RoomApi.deleteRoom(queryParams.id)
    message.success(t('common.delSuccess'))
    // await getList()
    RoomShow.value = false
    emit('success')
  } catch {}
}
const AddRoomRef = ref()
const edit = () => {
  AddRoomRef.value.open(
    'updated',
    detailInfo.value.villageId,
    detailInfo.value.buildId,
    detailInfo.value.id,
    JSON.parse(JSON.stringify(detailInfo.value))
  )
}

/** 初始化 **/
onMounted(() => {
  // getDetail()
})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>

<style lang="scss" scoped>
.villageMenu .el-menu-item:hover,
.villageMenu .el-menu-item:focus {
  background: none;
}
</style>
