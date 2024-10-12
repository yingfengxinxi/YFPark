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
  <ContentWrap class="viewHeader">
    <el-menu
      class="flex-1"
      :default-active="activeIndex"
      ellipsis
      mode="horizontal"
      popper-append-to-body
      teleported
      @select="handleSelect"
    >
      <el-menu-item index="1">库存资产领用</el-menu-item>
      <el-menu-item index="2">库存资产退库</el-menu-item>
      <el-menu-item index="3">库存资产借用</el-menu-item>
      <el-menu-item index="4">库存资产归还</el-menu-item>
      <el-menu-item index="5">交接他人</el-menu-item>
      <el-menu-item index="6">资产报修</el-menu-item>
      <el-menu-item index="7">资产报失</el-menu-item>
    </el-menu>
    <Handout
      v-if="activeIndex == '1' || activeIndex == '2'"
      :activeIndex="activeIndex"
      :userOptions="userOptions"
      :deptTree="deptTree"
    />
    <Lendout
      v-else-if="activeIndex == '3' || activeIndex == '4'"
      :activeIndex="activeIndex"
      :userOptions="userOptions"
      :deptTree="deptTree"
    />
    <Change
      v-else-if="activeIndex == '5'"
      :activeIndex="activeIndex"
      :userOptions="userOptions"
      :deptTree="deptTree"
    />

    <Repair
      v-else-if="activeIndex == '6'"
      :activeIndex="activeIndex"
      :userOptions="userOptions"
      :deptTree="deptTree"
    />
    <Handle
      v-else-if="activeIndex == '7'"
      :activeIndex="activeIndex"
      :userOptions="userOptions"
      :deptTree="deptTree"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import Handout from './component/assets/handout.vue'
import Lendout from './component/assets/lendout.vue'
import Change from './component/assets/change.vue'
import Repair from './component/assets/repair.vue'
import Handle from './component/assets/handle.vue'
import { handleTree } from '@/utils/tree'

/** 资产派发/退库 列表 */
defineOptions({ name: 'PropertyList' })

const deptTree = ref() // 部门树形结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const activeIndex = ref('1')
const handleSelect = (key: string) => {
  activeIndex.value = key
}

/** 初始化 **/
onMounted(async () => {
  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())
  userOptions.value = await UserApi.getSimpleUserList()
})
</script>
<style lang="scss" scoped>
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  background: none !important;
}
</style>
