<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <section class="dingflow-design">
      <div class="box-scale">
        <nodeWrap v-model:nodeConfig="nodeConfig" />
        <div class="end-node">
          <div class="end-node-circle"></div>
          <div class="end-node-text">流程结束</div>
        </div>
      </div>
    </section>
  </div>
</template>
<script lang="ts" setup>
import nodeWrap from '@/components/SimpleProcessDesigner/src/nodeWrap.vue'
defineOptions({ name: 'SimpleWorkflowDesignEditor' })
let nodeConfig = ref({
  nodeName: '发起人',
  type: 0,
  id: 'root',
  formPerms: {},
  nodeUserList: [],
  childNode: {}
})
</script>
<style>
@import url('@/components/SimpleProcessDesigner/theme/workflow.css');
</style>
