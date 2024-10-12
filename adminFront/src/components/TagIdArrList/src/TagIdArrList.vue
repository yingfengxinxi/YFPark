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
<script lang="ts" setup>
import { propTypes } from '@/utils/propTypes'

import { TagHouseApi } from '@/api/bus/tag/house'
import { TagVillageApi } from '@/api/bus/tag/village'
import { TagBuildApi } from '@/api/bus/tag/build'
import { TagOwnerApi } from '@/api/bus/tag/owner'
import { TagContractApi } from '@/api/bus/tag/contract'
import { TagIndustryApi } from '@/api/bus/tag/industry'
import { TagTerminationApi } from '@/api/bus/tag/termination'

defineOptions({ name: 'TagIdArrList' })
const tagList = ref([] as any[])
const chooseList = ref([] as any[])

const props = defineProps({
  list: propTypes.array.def([]),
  size: propTypes.string.def('14'),
  val: propTypes.array.def([])
})
/**
 * 类型
 * village ---- 项目标签
 * build ---- 建筑标签
 * house ---- 房源标签
 * owner ---- 租客标签
 * contract ---- 合同标签
 * industry ---- 行业分类标签
 *termination ---- 退组原因标签
 */

const getList = async (list) => {
  chooseList.value = []
  console.log(list, 'list')
  if (list.length == 0) return
  list.forEach((item) => {
    let index = props.val.findIndex((v) => v == item.id)
    console.log(item.color)
    item.color = typeof item.color == 'string' ? JSON.parse(item.color) : item.color
    if (index > -1) chooseList.value.push(item)
  })
  console.log(chooseList.value, '111')
}
// 监听模型绑定值变动
watch(
  () => props.list,
  (val: Array<any>) => {
    console.log(val)
    getList(val)
    // getList(props.type)
  },
  { immediate: true, deep: true }
)
watch(
  () => props.val,
  (val) => {
    if (val) {
      console.log(val, 'val')
      // props.val = JSON.parse(val)
      tagList.value = val
      getList(props.list)
      // getList(val)
    }
  },
  { immediate: true, deep: true }
)
</script>
<template>
  <template v-for="(item, index) in chooseList" :key="index">
    <span
      class="border-1px inline-block b-solid b-rd-6px border-gray m-r-5px p-10px p-t-2px p-b-2px m-t-6px"
      :style="[
        item.color
          ? {
              background: item.color.bgColor,
              borderColor: item.color.borderColor
            }
          : {},
        {
          fontSize: size + 'px'
        }
      ]"
      >{{ item.name }}</span
    >
  </template>
</template>
