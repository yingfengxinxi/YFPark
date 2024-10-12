<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div v-loading="OwnerLoading">
    <ContentWrap class="m-10px">
      <template v-if="ownerId">
        <div class="flex justify-between items-center">
          <div class="c-#000000a6 fw-600 font-size-15px">{{ ownerInfo?.name }}</div>
          <div
            class="c-#2681f3 hover-c-#52a5ff font-size-14px cursor-pointer"
            @click="TenantDetailsBtn(ownerInfo.id, ownerInfo.isPersonal)"
            >租客详情</div
          >
        </div>
        <StreamMiddleInfo :info="info" :ownerId="ownerId" />
      </template>
      <el-empty :image-size="80" description="暂无数据" v-else />
    </ContentWrap>
    <!-- <div class="m-10px m-t-20px" v-if="ownerId">
    </div> -->
  </div>
  <TenantDetails ref="TenantDetailsRef" @select-pick="getOwnerInfo" />
</template>
<script setup lang="ts">
/** 租客账单 */
defineOptions({ name: 'OwnerStreamMiddle' })
import { OwnerApi } from '@/api/bus/owner'
import StreamMiddleInfo from './streamMiddleInfo.vue'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
const props = defineProps({
  roomId: {
    type: Number,
    default: 0
  },
  info: {
    type: Object,
    default: () => {}
  }
})
const roomId = ref(props.roomId)
const info = ref(props.info)
const ownerId = ref('')
const OwnerLoading = ref(false)

const TenantDetailsRef = ref()
const TenantDetailsBtn = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}
const costTypeChildrenList = ref([])
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}

const ownerInfo = ref({})
const getOwnerInfo = async () => {
  OwnerLoading.value = true
  try {
    const data = await OwnerApi.getByRoomIdOwnerList(roomId.value)
    if (data.length) {
      ownerInfo.value = data[0]
      ownerId.value = data[0].id
    }
  } finally {
    OwnerLoading.value = false
  }
}
watch(
  () => props.roomId,
  (val) => {
    if (JSON.stringify(val)) {
      console.log('更新', val)
      getOwnerInfo()
      // activeIndex.value = Number(props.active_type)
    }
  },
  {
    immediate: true,
    deep: true
  }
)
watch(
  () => props.Id,
  (val) => {
    if (JSON.stringify(val)) {
      console.log('更新', val)
      ownerId.value = val
      getCostTypeChildrenList()
      // activeIndex.value = Number(props.active_type)
    }
  },
  {
    immediate: true,
    deep: true
  }
)
onMounted(() => {})
</script>
<style lang="css" scoped></style>
