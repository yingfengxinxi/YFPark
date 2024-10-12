<template>
  <div class="page">
    <div class="header text-black font-800 text-[14px]">{{ props.header_data }}</div>
    <div class="mt-4">
      <slot></slot>
    </div>
  </div>
</template>
<script lang="ts" setup>
let props = defineProps({
  header_data: {
    type: String,
    default: ''
  }
})
</script>
<style lang="scss" scoped>
.page {
  // border: 1px solid red;
  padding-left: 20px;
  .header {
    padding-left: 10px;
    border-left: 4px solid #2681f3;
  }
}
</style>
