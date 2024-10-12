<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <StreamMiddleInfo ref="StreamMiddleInfoRef" />
  </div>
</template>

<script setup lang="ts">
import StreamMiddleInfo from './components/streamMiddleInfo.vue'
/** 机构账单收支流水 列表 */
defineOptions({ name: 'BillStream' })
</script>
