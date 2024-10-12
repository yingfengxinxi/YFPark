<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="bg-[#fff] p-[10px]">
    <div
      class="flex items-center gap-[2px] border border-b-[1px] border-[#F0F0F0] border-solid border-0"
    >
      <div
        :class="ActiveIndex == index ? 'active' : 'unactive'"
        v-for="(item, index) in energyTypeList"
        :key="index"
        @click="getDetail(index)"
      >
        {{ item.name }}
      </div>
    </div>
    <!-- 预付费 -->
    <div class="mt-[30px] pos-relative">
      <div
        class="bg-[#ECECEC] text-[#232323] text-[14px] w-[80px] text-center py-[10px] rounded-t-[8px]"
      >
        预付费
      </div>
      <div
        class="bg-[#ECECEC] px-[20px] py-[20px] pb-[40px] flex items-center gap-[10px] text-[#232323] text-[14px]"
      >
        预付费剩余<el-input style="width: 100px" v-model="detailDate.remindValue" />{{
          detailDate?.unit
        }}提醒
      </div>
    </div>
    <!-- 后付费 -->
    <div class="mt-[30px] pos-relative">
      <div
        class="bg-[#ECECEC] text-[#232323] text-[14px] w-[80px] text-center py-[10px] rounded-t-[8px]"
        >后付费</div
      >
      <div class="bg-[#ECECEC] px-[20px] py-[20px] pb-[40px] text-[#232323] text-[14px]">
        <div class="text-[#232323] text-[14px]"> 租客账单逾期 </div>
        <div class="mt-[20px]" v-if="detailDate.costType == 46">
          <el-radio-group v-model="detailDate.isBroken">
            <el-radio-button label="不自动断水" value="0" />
            <el-radio-button label="自动断水" value="1" />
          </el-radio-group>
        </div>
        <div class="mt-[20px]" v-if="detailDate.costType == 45">
          <el-radio-group v-model="detailDate.isBroken">
            <el-radio-button label="不自动断电" value="0" />
            <el-radio-button label="自动断电" value="1" />
          </el-radio-group>
        </div>
        <div class="mt-[20px] text-[12px] text-[#AAAAAA]" v-if="detailDate.isBroken == 1">
          *实现自动通水，需要在“财务-收支流水-租客预存”的自动缴费项目中开启该表对应的费用类型
        </div>
        <div v-if="detailDate.costType == 46 && detailDate.isBroken == 1">
          <div class="mt-[20px] text-[#232323] text-[14px] flex gap-[5px] items-center">
            以电控水

            <el-tooltip
              class="box-item"
              effect="dark"
              content="不支持远程阀控的水表，在欠费时自动断电"
              placement="top"
            >
              <Icon icon="fa:question-circle-o" :size="12" />
            </el-tooltip>
            <el-radio-group v-model="detailDate.cutType">
              <el-radio value="1" size="large">开启</el-radio>
              <el-radio value="0" size="large">关闭</el-radio>
            </el-radio-group>
          </div>
        </div>
        <div
          class="mt-[20px] text-[#232323] text-[14px] flex gap-[10px] items-center"
          v-if="detailDate.isBroken == 1"
        >
          后付费逾期宽限
          <el-input style="width: 120px" v-model="detailDate.overdueDay">
            <template #append> 天 </template>
          </el-input>
        </div>
        <div class="mt-[20px] text-[12px] text-[#AAAAAA]" v-if="detailDate.isBroken == 1">
          系统每天10时左右自动检测一次表欠费状态。存在欠费账单，则会自动给租客发送短信、站内信、公众号消息。欠费账单生成时间+宽限天数大于当前的则会自动断闸，缴纳欠款后自动合闸。
        </div>
      </div>
    </div>
    <!-- 保存 -->
    <div class="flex justify-end mt-[20px]">
      <el-button type="primary" @click="submit"> 保存 </el-button>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { energyType } from '@/api/bus/WaterElectricity/EnergyType/index'
const message = useMessage() // 消息弹窗
const ActiveIndex = ref(0)
const detailDate = ref({})
const energyTypeList = ref([])
const getDetail = (index: number) => {
  ActiveIndex.value = index
  detailDate.value = energyTypeList.value[index]
}
const getList = () => {
  energyType.getList().then((res) => {
    energyTypeList.value = res
    getDetail(ActiveIndex.value)
  })
}
const submit = () => {
  energyType
    .submitlockout({
      id: detailDate.value.id,
      remindValue: detailDate.value.remindValue,
      overdueDay: detailDate.value.overdueDay,
      isBroken: detailDate.value.isBroken,
      cutType: detailDate.value.cutType
    })
    .then((res) => {
      message.success('保存成功')
      getList()
    })
}
onMounted(() => {
  getList()
})
</script>
<style scoped lang="scss">
.unactive {
  background-color: #fafafa;
  padding: 8px 15px;
  color: #252548;
  font-size: 14px;
  cursor: pointer;
  border: 1px solid #f0f0f0;
  border-bottom: none;
  border-radius: 4px;
}
.active {
  background-color: #ffffff;
  padding: 8px 15px;
  color: #1892ff;
  font-size: 14px;
  border: 1px solid #f0f0f0;
  border-bottom: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
