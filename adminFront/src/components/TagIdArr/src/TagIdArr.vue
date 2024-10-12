<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<script lang="ts" setup>
import { propTypes } from '@/utils/propTypes'

defineOptions({ name: 'TagIdArr' })

const props = defineProps({
  list: propTypes.array.def([]),
  size: propTypes.string.def('14')
})
// 监听模型绑定值变动
watch(
  () => props.list,
  (val: Array<any>) => {
    props.list.forEach((item: any) => {
      if (typeof item.color == 'string') {
        item.color = JSON.parse(item.color)
      }
    })
  },
  { immediate: true, deep: true }
)
</script>
<template>
  <template v-for="(item, index) in list" :key="index">
    <span
      class="border-1px inline-block b-solid b-rd-6px border-gray m-r-10px p-10px p-t-2px p-b-2px"
      :style="{
        background: item.color.bgColor,
        borderColor: item.color.borderColor,
        fontSize: size + 'px'
      }"
      >{{ item.name }}</span
    >
  </template>
</template>
