<template>
  <div class="tenantDetails">
    <el-drawer
      v-model="TenantDetailsShow"
      direction="rtl"
      size="70%"
      :show-close="false"
      :with-header="false"
    >
      <ContractDetail :detailId="detailId" @close="(res) => (TenantDetailsShow = res)" />
    </el-drawer>
  </div>
</template>
<script lang="ts" setup>
import ContractDetail from './contractList_detail.vue'
defineOptions({ name: 'TenantDetails' })
const message = useMessage() // 消息弹窗
const TenantDetailsShow = ref(false)
const title = ref('')
const formLoading = ref(false)
const activeIndex = ref(0)
const table_detail_data = ref({})
const detailId = ref(0)
/** 打开抽屉 */
const open = async (id?: number) => {
  TenantDetailsShow.value = true
  detailId.value = id
  // getOwner()
}

const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await OwnerApi.deleteOwner(id)
    message.success(t('删除成功'))
    TenantDetailsShow.value = false
  } catch {}
}

const openForm = (id?: number) => {
  TenantDetailsShow.value = false
  // open(id, 1)
}
/** 初始化 **/
onMounted(() => {})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style scoped lang="scss">
.tenantDetails :deep(.el-drawer__body) {
  padding: 0;
  background: var(--app-content-bg-color) !important;
}
</style>
