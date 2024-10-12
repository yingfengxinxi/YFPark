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
      <el-menu-item index="1">耗材领用</el-menu-item>
      <el-menu-item index="2">耗材退还</el-menu-item>
    </el-menu>
    <Handout
      v-if="activeIndex == '1' || activeIndex == '2'"
      :activeIndex="activeIndex"
      :userOptions="userOptions"
      :deptTree="deptTree"
      :stockTree="stockTree"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { PropertyStuffDepositoryApi, PropertyStuffDepositoryVO } from '@/api/bus/stuff/depository'
import { handleTree } from '@/utils/tree'
import Handout from './component/stuff/handOut.vue'

/** 资产派发/退库 列表 */
defineOptions({ name: 'StuffApplication' })

const deptTree = ref() // 部门树形结构
const stockTree = ref() //仓库树结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const activeIndex = ref('1')
const handleSelect = (key: string) => {
  activeIndex.value = key
}

/** 初始化 **/
onMounted(async () => {
  stockTree.value = await PropertyStuffDepositoryApi.getPropertyStuffDepositoryTree()
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
