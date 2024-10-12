<template>
  <div class="flex items-center justify-between px-8px py-14px">
    <div class="left">
      <div :class="detail.setting.required ? 'leftTitle' : 'leftTitle_false'">{{
        detail.setting.name
      }}</div>
      <div class="img w-70px h-70px bg-#f1f1f1 mt-10px pos-relative">
        <Icon icon="ep:plus" color="#999" :size="30" class="pos-absolute pos-top-50% pos-left-50% transform-translate-[-50%]" />
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { defineProps, watch } from 'vue'
const detail = ref<any>({})
const props = defineProps<{
  data: any
}>()
watch(
  () => props.data,
  (val) => {
    detail.value = JSON.parse(val.remark)
  },
  {
    immediate: true
  }
)
</script>
<style lang="scss" scoped>
.leftTitle {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  &::before {
    content: '*';
    color: #ff4d4f;
  }
}
.leftTitle_false {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}
</style>
