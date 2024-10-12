<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div
    class="border-solid border border-[#E4E7ED] rounded mb-[18px]"
    v-for="(item, index) in List"
    :key="index"
  >
    <el-card shadow="never">
      <template #header>
        <div class="flex justify-between">
          <span class="font-semibold"> {{ item.typeName }} </span>
          <el-button type="primary" text @click="changeeditStatus(item)" v-if="!item.changeStatus">
            修改
          </el-button>
          <el-button type="primary" text @click="submit(item)" v-else> 保存 </el-button>
        </div>
      </template>
      <div class="flex flex-col gap-[10px] select-none">
        <div class="flex pos-relative items-center gap-[10px] line-height-[38px]">
          <span class="text-[14px]">应用标识:</span>
          <el-input
            style="width: 400px"
            v-model="item.key"
            placeholder="请输入应用标识"
            v-if="item.changeStatus"
          />
          <div v-else class="flex items-center gap-[25px]">
            <span class="text-[14px]" v-if="item.KeyStatus">{{
              createObject(item.key)?.view
            }}</span>
            <span class="text-[14px]" v-else>{{ createObject(item.key)?.notview }}</span>
            <Icon
              icon="fa:eye"
              color="#2681F3"
              class="cursor-pointer"
              v-if="item.KeyStatus"
              size="14"
              @click="item.KeyStatus = !item.KeyStatus"
            />
            <Icon
              icon="fa:eye-slash"
              color="#2681F3"
              class="cursor-pointer"
              v-else
              size="14"
              @click="item.KeyStatus = !item.KeyStatus"
            />
          </div>
        </div>
        <div class="flex pos-relative items-center gap-[10px] line-height-[38px]">
          <span class="text-[14px]">应用标识:</span>
          <el-input
            style="width: 400px"
            v-model="item.value"
            placeholder="请输入应用密钥"
            v-if="item.changeStatus"
          />
          <div v-else class="flex items-center gap-[25px]">
            <span class="text-[14px]" v-if="item.valueStatus">{{
              createObject(item.value).view
            }}</span>
            <span class="text-[14px]" v-else>{{ createObject(item.value).notview }}</span>
            <Icon
              icon="fa:eye"
              color="#2681F3"
              class="cursor-pointer"
              v-if="item.valueStatus"
              size="14"
              @click="item.valueStatus = !item.valueStatus"
            />
            <Icon
              icon="fa:eye-slash"
              color="#2681F3"
              class="cursor-pointer"
              v-else
              size="14"
              @click="item.valueStatus = !item.valueStatus"
            />
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts" setup>
import { bailingOrgConfigApi } from '@/api/bus/WaterElectricity/ArgumentSetting/index.ts'
const List = ref([])
const message = useMessage() // 消息弹窗

const getList = async () => {
  List.value = await bailingOrgConfigApi.getList()
  List.value.forEach((item) => {
    item.changeStatus = false
    item.KeyStatus = false
    item.valueStatus = false
  })
}
const createObject = (input) => {
  if (input.length == 0) {
    return {
      view: '--',
      notview: '--'
    }
  }
  return {
    view: input,
    notview: input.slice(0, 2) + '*'.repeat(input.length - 2)
  }
}
const changeeditStatus = (item) => {
  item.changeStatus = !item.changeStatus
}
const submit = async (item) => {
  await bailingOrgConfigApi
    .save({
      type: item.type,
      key: item.key,
      value: item.value
    })
    .then(() => {
      message.success('保存成功')
      getList()
    })
}
const submitAll = async () => {
  const data = []
  await List.value.forEach((item) => {
    data.push({
      type: item.type,
      key: item.key,
      value: item.value
    })
  })
  await bailingOrgConfigApi.save(List.value)
  getList()
}
onMounted(() => {
  getList()
})
</script>
