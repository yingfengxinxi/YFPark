<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="shortcut-container w-100%">
    <ContentWrap class="!m-0 viewHeader">
      <div class="flex justify-between items-center mb-17px">
        <el-menu
          :default-active="activeIndex"
          :ellipsis="false"
          mode="horizontal"
          @select="handleSelect"
          class="w-100% flex items-center"
        >
          <img
            src="@/assets/imgs/home/InterestRate.png"
            class="mr-15px w-30px h-30px"
            alt=""
            srcset=""
          />
          <el-menu-item index="1" class="!p-0 !mr-16px">近6个月出租率走势</el-menu-item>
          <el-menu-item index="2" class="!p-0">近6个月合同成交走势</el-menu-item>
        </el-menu>
      </div>
      <Echart :height="343" :options="trendOption" />
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
import { emit } from 'process'

defineOptions({ name: 'Trend' })
const activeIndex = ref('1')
const props = defineProps({
  trendOption: {
    type: Object,
    default: function () {
      return {}
    }
  }
})
watch(
  () => props.trendOption,
  (newVal) => {
    console.log(newVal, '999')
  }
)
const emit = defineEmits(['select'])
const handleSelect = (key: string) => {
  activeIndex.value = key
  emit('select', key)
}
</script>
<style lang="scss" scoped>
::v-deep .el-card__body {
  padding-bottom: 0px !important;
}

.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  background: none !important;
}
.viewHeader .el-menu--horizontal {
  height: 40px;
}
.viewHeader .el-menu--horizontal .el-menu-item {
  line-height: 1 !important;
}
</style>
