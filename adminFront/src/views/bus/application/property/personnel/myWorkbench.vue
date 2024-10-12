<template>
  <div class="container">
    <el-row :gutter="20">
      <el-col :span="4" class="">
        <ContentWrap
          title=""
          class="h-100% !m-0 !b-rd-10px box-shadow flex justify-center direction-column items-center text-14px text-center"
        >
          <div>
            <el-avatar
              v-if="userInfo.avatar !== ''"
              size="large"
              style="width: 80px; height: 80px; line-height: 80px; font-size: 24px"
              :src="userInfo.avatar"
            />

            <el-avatar
              v-else
              size="large"
              style="width: 80px; height: 80px; line-height: 80px; font-size: 24px"
            >
              {{ userInfo.realName.charAt(0) }}
            </el-avatar>
            <div class="m-t-20px m-b-30px fw-600 text-16px"> {{ userInfo.nickname }}</div>
            <div>
              <span class="c-#1890ff cursor-pointer inline-block" @click="toRouter('MyAssets')"
                >我的资产 {{ myProperty?.myApproveCount || 0 }}</span
              >
            </div>
            <div class="m-t-14px">
              <span class="c-#1890ff cursor-pointer inline-block" @click="toRouter('MyProperty')"
                >我的申请 {{ myProperty?.myPropertyCount || 0 }}</span
              >
            </div>
          </div>
        </ContentWrap>
      </el-col>
      <el-col :span="20" class="">
        <el-row :gutter="10">
          <el-col :span="4" v-for="(item, index) in routerList" :key="index" class="m-t-10px">
            <ContentWrap
              class="!m-0 p-t-40px p-b-40px h-100% box-border itemRouter !b-rd-10px cursor-pointer"
              title=""
              @click="openForm(item.key)"
            >
              <Icon class="m-auto !block icon" :size="40" :icon="item.icon" />
              <div class="m-t-20px m-b-20px text-16px fw-600 text-center">{{ item.title }}</div>
              <div class="fw-500 text-16px text-center">{{ item.english }}</div>
            </ContentWrap>
          </el-col>
        </el-row>
      </el-col>
      <div class="clear-both"></div>
    </el-row>
    <ContentWrap title="近30日需归还资产" class="m-t-20px">
      <el-table
        :data="data"
        style="width: 100%; margin: 0 auto"
        v-loading="loading"
        :header-cell-style="{
          color: '#000000d9',
          fontSize: '14px',
          fontWeight: '500',
          backgroundColor: '#fafafa'
        }"
      >
        <el-table-column label="资产编码" prop="number" />
        <el-table-column label="资产名称" prop="name" />
        <el-table-column label="资产分类" prop="categoryName" />
        <el-table-column label="所在位置" prop="positionName" />
        <el-table-column label="预计归还时间" prop="returnTime" />
      </el-table>
    </ContentWrap>
  </div>
  <!-- 资产派发 -->
  <CreateHandoutPropertyForm ref="createHandoutPropertyFormRef" />
  <!-- 资产退库 -->
  <ReturnInventoryForm ref="returnInventoryFormRef" />
  <!-- 资产借用/归还 -->
  <LendReturn ref="LendReturnFormRef" />
  <!-- 资产报修 -->
  <Repair ref="RepairFormRef" />
  <!-- 资产处置 -->
  <DisposeOfForm ref="disposeOfFormRef" />
  <!-- 变更领用人 -->
  <ChangeForm ref="changeFormRef" />
  <!-- 耗材领用/退还 -->
  <Form ref="formRef" />
</template>

<script setup lang="ts">
import CreateHandoutPropertyForm from '../assets/component/createHandoutPropertyForm.vue'
import ReturnInventoryForm from '../assets/component/returnInventoryForm.vue'
import LendReturn from '../assets/component/lendReturn.vue'
import Repair from '../assets/component/repairForm.vue'
import DisposeOfForm from '../assets/component/disposeOfForm.vue'
import ChangeForm from '../assets/component/changeForm.vue'
import Form from '../stuff/component/Form.vue'
import { useUserStore } from '@/store/modules/user'
import { PropertyApi } from '@/api/bus/property/property'
const { push } = useRouter()
const userStore = useUserStore()
defineOptions({ name: 'MyWorkbench' })
const userInfo = userStore.getUser
const routerList = ref([
  {
    title: '资产派发',
    key: 'handout_property',
    icon: 'icon-jinru',
    english: 'Collecte'
  },
  {
    title: '资产借用',
    key: 'lendout_property',
    icon: 'icon-jinru',
    english: 'Borrow'
  },
  {
    title: '资产退还',
    key: 'return_property',
    icon: 'icon-tuikuan',
    english: 'Return'
  },
  {
    title: '资产报失',
    icon: 'icon-baoshi',
    english: 'Report Loss',
    key: 'handle_property'
  },
  {
    title: '变更领用人',
    icon: 'icon-pandian',
    key: 'change_property',
    english: 'Handover'
  },
  {
    title: '资产报修',
    icon: 'icon-pandian',
    key: 'report_repair_property',
    english: 'Report for Report'
  },
  {
    title: '耗材领用',
    icon: 'icon-pandian',
    key: 'stuff_hand_out',
    english: 'Consumable requisition'
  },
  {
    title: '耗材退还',
    key: 'stuff_retreat_out',
    icon: 'icon-pandian',
    english: 'Consumables Return'
  }
])
const loading = ref(false)
const data = ref([])
const myProperty = ref({
  myApproveCount: undefined,
  myPropertyCount: undefined
})
const toRouter = (routerName: string) => {
  push({ name: routerName })
}
const createHandoutPropertyFormRef = ref()
const returnInventoryFormRef = ref()
const LendReturnFormRef = ref()
const disposeOfFormRef = ref()
const RepairFormRef = ref()
const changeFormRef = ref()
const formRef = ref()

const openForm = async (key: string) => {
  switch (key) {
    case 'handout_property':
      createHandoutPropertyFormRef.value.open('create')
      break
    case 'lendout_property':
      LendReturnFormRef.value.open('create')
      break
    case 'return_property':
      returnInventoryFormRef.value.open('create')
      break
    case 'handle_property':
      disposeOfFormRef.value.open(1, 'create', [], {}, 1)
      break
    case 'change_property':
      changeFormRef.value.open('create')
      break
    case 'report_repair_property':
      RepairFormRef.value.open('create')
      break
    case 'stuff_hand_out':
      formRef.value.open('stuff_hand_out', 'create')
      break
    case 'stuff_retreat_out':
      formRef.value.open('stuff_retreat_out', 'create')
      break
    default:
      break
  }
}
/** 获取我的资产数量 */
const getMyProperty = async () => {
  const res = await PropertyApi.getMyProperty()
  console.log(res)
  myProperty.value = res
}
/** 近30天需归还资产 */
const getReturnProperty = async () => {
  try {
    loading.value = true
    const data = await PropertyApi.returnPropertyPage()
    data.value = data
  } finally {
    loading.value = false
  }
}
/** 激活时 */
onActivated(async () => {
  getMyProperty()
  getReturnProperty()
})
/** 初始化 */
onMounted(async () => {
  await getMyProperty()
  await getReturnProperty()
})
// TypeScript component logic
</script>
<style scoped lang="scss">
.itemRouter,
.box-shadow {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}
.itemRouter .icon {
  color: #1890ff;
}
.itemRouter:hover {
  color: #fff;
  background: linear-gradient(to right bottom, #0080f6, #8dbfec);
  .icon {
    color: #fff !important;
  }
}
</style>
