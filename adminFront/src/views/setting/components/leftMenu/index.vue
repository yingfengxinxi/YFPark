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
<script setup lang="ts">
const { push, currentRoute } = useRouter()
import type { RouteLocationNormalizedLoaded, RouteMeta } from 'vue-router'
const activeMessageName = ref('')
const openeds = ref([
  '/setting/busorg',
  '/setting/orgAccount',
  '/setting/VillageType',
  '/setting/village'
])
activeMessageName.value = currentRoute.value.path
const handleOpen = (key: string, keyPath: string[]) => {
  activeMessageName.value = key
  push(key)
}

watch(
  () => currentRoute.value,
  (route: RouteLocationNormalizedLoaded) => {
    activeMessageName.value = route.path
  },
  {
    immediate: true
  }
)
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>
<template>
  <el-aside width="140px" class="fixed">
    <el-menu
      :default-active="activeMessageName"
      :default-openeds="openeds"
      class="el-menu-vertical"
      @open="handleOpen"
      @close="handleClose"
      :router="true"
      unique-opened
    >
      <el-menu-item index="/setting/busorg">
        <template #title>
          <span>企业基本信息</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/setting/orgAccount">
        <template #title>收支账户配置</template>
      </el-menu-item>
      <el-menu-item index="3">
        <template #title>支付配置</template>
      </el-menu-item>
      <el-menu-item index="/setting/VillageType">
        <template #title><span>项目类型</span></template>
      </el-menu-item>
      <el-sub-menu index="/setting/village">
        <template #title>自定义标签</template>
        <el-menu-item index="/setting/village">项目标签</el-menu-item>
        <el-menu-item index="/setting/build">建筑标签</el-menu-item>
        <el-menu-item index="/setting/house">房源标签</el-menu-item>
        <el-menu-item index="/setting/owner">租客标签</el-menu-item>
        <el-menu-item index="/setting/contract">合同标签</el-menu-item>
        <el-menu-item index="/setting/industry">行业分类</el-menu-item>
        <el-menu-item index="/setting/termination">退租原因</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-aside>
</template>
<style scoped>
.el-menu-vertical {
  border-right: none !important;
}
</style>
