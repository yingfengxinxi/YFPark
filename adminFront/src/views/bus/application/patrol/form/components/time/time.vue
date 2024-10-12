<template>
  <div class="flex items-center justify-between px-8px py-14px">
    <div class="left">
      <div :class="detail.setting.required?'leftTitle':'leftTitle_false'">{{ detail.setting.name }}</div>
    </div>
    <div class="right flex items-center justify-end">
      <span class="text-#999 text-12px">{{ detail.setting.placeholder }}</span>
      <Icon icon="ep:arrow-right" color="#999" />
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
