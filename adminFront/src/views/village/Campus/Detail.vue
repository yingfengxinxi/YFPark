<template>
  <el-drawer v-model="dialogVisible" size="700px" :title="dialogTitle">
    <el-descriptions class="margin-top" title="With border" :column="2" border>
      <template #title>
        <div class="Line_title">基本信息</div>
      </template>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 园区名称 </div>
        </template>
        {{ info.name }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 园区简称 </div>
        </template>
        {{ info.shortName }}
      </el-descriptions-item>
      <el-descriptions-item :span="2" class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 园区简介 </div>
        </template>
        {{ info.describe || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label" :span="2">
        <template #label>
          <div class="cell-item"> 所属地区 </div>
        </template>
        {{ info.districtTxt || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label" :span="2">
        <template #label>
          <div class="cell-item"> 详细地址 </div>
        </template>
        {{ info.address || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 管理面积（㎡） </div>
        </template>
        {{ info.managementArea || 0 }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 可招商面积（㎡） </div>
        </template>
        {{ info.rentableArea || 0 }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 总资源数量（间） </div>
        </template>
        {{ info.roomCount || 0 }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 可招商资源数量（间） </div>
        </template>
        {{ info.roomRentableCount || 0 }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 项目类型 </div>
        </template>
        <template v-for="(item, index) in list" :key="index">
          <span v-if="item.alias == info.type">{{ item.name }}</span>
        </template>
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 排序值 </div>
        </template>
        {{ info.sort || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 竣工时间 </div>
        </template>
        {{ info.extraConfig?.completedTime || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 标准层高 </div>
        </template>
        {{ info.extraConfig?.floor_height || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 物业 </div>
        </template>
        {{ info.extraConfig?.ownerName || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 物业费 </div>
        </template>
        {{ info.extraConfig?.ownerCost || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 车位数量 </div>
        </template>
        {{ info.extraConfig?.parkingNumber || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 车位月租金 </div>
        </template>
        {{ info.extraConfig?.parkingMonthlyRent || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 空调 </div>
        </template>
        {{ info.extraConfig?.airConditioner || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 空调费</div>
        </template>
        {{ info.extraConfig?.airConditionerCost || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 空调开放时间 </div>
        </template>
        {{ info.extraConfig?.airConditionerTime || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 电梯 </div>
        </template>
        {{ info.extraConfig?.elevator || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 网络 </div>
        </template>
        {{ info.extraConfig?.network || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 入驻企业 </div>
        </template>
        {{ info.extraConfig?.settledEnterprise || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 招商岗位 </div>
        </template>
        {{ info.extraConfig?.post_txt || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 招商部门 </div>
        </template>
        {{ info.extraConfig?.department_txt || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> VR链接 </div>
        </template>
        {{ info.vrLink || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 招商时间 </div>
        </template>
        {{ info.extraConfig?.attractInvestmentTime || '无' }}
      </el-descriptions-item>
      <el-descriptions-item class-name="my-class" label-class-name="my-label">
        <template #label>
          <div class="cell-item"> 图片 </div>
        </template>
        {{ info.logo || '无' }}
      </el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <div class="flex justify-between">
        <div>
          <el-button plain @click="closeVisible()">返回</el-button>
        </div>
        <div style="flex: auto">
          <el-button type="danger" plain @click="handleDelete()">删除</el-button>
          <el-button type="primary" @click="openEdit()">去编辑</el-button>
        </div>
      </div>
    </template>
    <EditGarden ref="EditGardenRef" @success="success" />
  </el-drawer>
</template>
<script setup lang="ts">
import { villageApi, villageVO } from '@/api/bus/Campus'
import EditGarden from '../managementCenter/EditGarden.vue'
import { BuildApi, VillageBVO } from '@/api/bus/village/index'
import { VillageTypeApi } from '@/api/bus/tag/villageTypeList/index'

/** 社区 表单 */
defineOptions({ name: 'Detail' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('项目详情') // 弹窗的标题
const Loading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const info = ref<VillageBVO>({})
const infoId = ref(0)
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  Loading.value = true
  infoId.value = id
  getDetailList()
}

const getDetailList = async () => {
  try {
    const data = await BuildApi.getvillage(infoId.value)
    info.value = data
    console.log(data)
  } finally {
    Loading.value = false
  }
}
const EditGardenRef = ref()
const openEdit = () => {
  EditGardenRef.value.open('updated', infoId.value, JSON.parse(JSON.stringify(info.value)))
  // dialogVisible.value = false
  // emit('openEdit', infoId.value)
}

const closeVisible = () => {
  dialogVisible.value = false
}

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
// 删除
const handleDelete = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await BuildApi.deletevillage(infoId.value)
    message.success(t('common.delSuccess'))
    dialogVisible.value = false
    // await getList()
    emit('success')
  } catch {}
}
const success = () => {
  dialogVisible.value = false
  emit('success')
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const list = ref([] as any[])
const getList = async () => {
  const res = await VillageTypeApi.getVillageTypeNopage()
  list.value = res
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
<style>
:deep() {
  .el-descriptions-item__content {
    max-width: 295px;
  }
}
.my-label {
  width: 120px;
  color: #000000d9;
  font-weight: normal;
  background: #fafafa !important;
}
.my-class {
  width: 210px;
  word-break: break-all;
  word-wrap: break-word;
  color: #000000a6;
}

/* .el-drawer__header {
  margin-bottom: 0;
} */
</style>
