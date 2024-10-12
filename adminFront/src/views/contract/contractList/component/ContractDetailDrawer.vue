<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="tenantDetails">
    <el-drawer
      v-model="ContactDetailShow"
      direction="rtl"
      size="70%"
      :show-close="false"
      :with-header="false"
    >
      <ContractDetail :detailId="ContactId" @close="handleClose" />
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
defineOptions({ name: 'ContractDetailDrawer' })
import ContractDetail from '../contractList_detail.vue'
const ContactDetailShow = ref(false)
const ContactId = ref()
/** 打开抽屉 */
const open = async (id?: number) => {
  ContactId.value = id
  ContactDetailShow.value = true
}

const emit = defineEmits(['close']) // 定义 succesemits 事件，用于操作成功后的回调const handleClose = () => {

const handleClose = () => {
  ContactDetailShow.value = false
  emit('close')
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
