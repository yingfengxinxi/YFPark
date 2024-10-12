<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <ContentWrap style="margin-bottom: 0">
      <div class="header_row"> {{ props.header_data }} </div>
      <div class="bottom_row">
        <slot></slot>
      </div>
    </ContentWrap>
  </div>
</template>
<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    header_data: string
  }>(),
  {
    header_data: ''
  }
)
</script>
<style lang="scss" scoped>
.header_row {
  padding-bottom: 16px;
  border-bottom: 1px solid #e8e8e8;
  font-size: 16px;
}
.bottom_row {
  padding-top: 16px;
}
</style>
