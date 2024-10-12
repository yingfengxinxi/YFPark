<template>
  <div class="tenantDetails">
    <el-drawer
      v-model="TenantDetailsShow"
      direction="rtl"
      size="70%"
      :show-close="false"
      :with-header="false"
    >
      <Form_detail
        :table_detail_data="table_detail_data"
        :active_type="activeIndex"
        :detail_id="ownerId"
        @back="(res) => (TenantDetailsShow = res)"
        @del="handleDelete"
        @change="openForm"
      />
    </el-drawer>
  </div>
  <!-- 编辑抽屉 -->
  <AddForm ref="FormRef" @success="getOwner" />
</template>
<script lang="ts" setup>
import Form_detail from '../../bus/owner/form_detail.vue'
import { OwnerApi, OwnerVO } from '@/api/bus/owner'
import AddForm from '@/views/bus/owner/component/addFom.vue'
defineOptions({ name: 'TenantDetails' })
const message = useMessage() // 消息弹窗
const TenantDetailsShow = ref(false)
const title = ref('')
const formLoading = ref(false)
const activeIndex = ref(0)
const table_detail_data = ref({})
const ownerId = ref(0)
/** 打开抽屉 */
const open = async (id?: number, type?: number) => {
  TenantDetailsShow.value = true
  formLoading.value = true
  activeIndex.value = String(type)
  ownerId.value = id
  console.log(id, type)
  getOwner()
}
const getOwner = () => {
  try {
    OwnerApi.getOwnerById(ownerId.value).then((res) => {
      table_detail_data.value = res
      table_detail_data.value.businessInfo = JSON.parse(res.businessInfo)
    })
  } finally {
    formLoading.value = false
  }
}
const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await OwnerApi.deleteOwner(id)
    message.success(t('删除成功'))
    TenantDetailsShow.value = false
  } catch {}
}

const FormRef = ref()
const openForm = (id?: number) => {
  // TenantDetailsShow.value = false
  // open(id, 1)
  FormRef.value.open('edit', id)
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
