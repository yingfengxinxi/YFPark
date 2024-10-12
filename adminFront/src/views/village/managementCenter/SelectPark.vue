<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-dialog v-model="dialogVisible" title="请选择您要添加的项目类型" width="80%">
    <el-row :gutter="20">
      <el-col
        v-for="(item, index) in list"
        :key="index"
        :span="6"
        class="h-120px m-b-20px hoverImg"
      >
        <div class="w-100% h-100% relative overflow-hidden" @click="selectPick(item)">
          <img :src="item.bgImg" alt="" class="bgImg" />
          <div class="absolute cursor-pointer box">
            <img :src="item.iconImg" alt="" class="iconImg" />
            {{ item.name }}
          </div>
        </div>
      </el-col>
    </el-row>
  </el-dialog>
</template>
<script setup lang="ts">
defineOptions({ name: 'SelectPark' })
import { VillageTypeApi } from '@/api/bus/tag/villageTypeList/index'
import { any } from 'vue-types'
const dialogVisible = ref(false)
const emits = defineEmits(['selectPick'])
const list = ref([] as any[])

/** 打开抽屉 */
const open = async () => {
  dialogVisible.value = true
  getList()
}
const getList = async () => {
  const res = await VillageTypeApi.getVillageTypeNopage()
  console.log(res)
  list.value = res
}
const selectPick = (item: any) => {
  emits('selectPick', item)
  dialogVisible.value = false
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>

<style lang="scss" scoped>
.bgImg {
  height: 100%;
  width: 100%;
  object-fit: cover;
  transition: all 0.3s;
}

.hoverImg:hover .bgImg {
  transform: scale(1.2);
}
.box {
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
  left: 0px;
  top: 0;
  width: 100%;
  height: 100%;
}

.iconImg {
  width: 18%;
  height: auto;
  min-height: 40px;
  margin-bottom: 10px;
}
</style>
