<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 储值缴费 -->
  <el-dialog align-center width="800px" v-model="visibleShow" @close="cancelClick">
    <template #header="{ titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass" class="m-t-0px m-b-0px text-center">储值缴费</h4>
      </div>
    </template>
    <ContentWrap>
      <h2 class="font-800 font-size-20px m-t-0px">请选择储值金额</h2>
      <div class="m-t-20px flex justify-start items-center">
        <div
          class="m-r-10px w-100px h-60px line-height-60px text-center border-1px-solid-#e6e6e6 c-#007aff bg-#e5f1ff b-rd-5px cursor-pointer"
          v-for="item in getIntDictOptions(DICT_TYPE.STORE_PACKAGE)"
          :key="item.value"
          @click="AddCarNum(item.label, item.value)"
          :class="active == item.label ? 'active' : ''"
        >
          {{ item.label }}
        </div>
        <div
          class="w-100px h-60px line-height-60px text-center border-1px-solid-#e6e6e6 c-#007aff bg-#e5f1ff b-rd-5px cursor-pointer"
          :class="active == '自定义' ? 'active' : ''"
          @click="AddCarNum('自定义', '')"
        >
          <el-input-number
            class="!bg-none !w-auto m-10px m-t-0px m-b-0px box-border c-#007aff"
            placeholder="其他金额"
            :controls="false"
            :step="1"
            :min="0"
            v-model="inputValue"
            @input="activeInput = $event"
          />
        </div>
      </div>
    </ContentWrap>
    <template #footer>
      <div class="dialog-footer flex justify-between">
        <div
          >缴费金额: <span class="c-#c33124 font-800">￥ {{ activeInput }}</span></div
        >
        <el-button size="small" type="primary" @click="confirmClick" :loading="formLoading"
          >确 定</el-button
        >
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
defineOptions({ name: 'StoredValueForm' })
const message = useMessage() // 消息弹窗
import * as AreaApi from '@/api/system/area'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { ParkCarsApi } from '@/api/bus/parkCars'
const { t } = useI18n() // 国际化
const visibleShow = ref(false)
const formLoading = ref(false)
const active = ref()
const inputValue = ref()
const activeInput = ref()
function cancelClick() {
  visibleShow.value = false
}
const info = ref()
/** 打开抽屉 */
const open = async (form) => {
  console.log(form, 'form')
  visibleShow.value = true
  info.value = form
  resetForm()
  active.value = getIntDictOptions(DICT_TYPE.STORE_PACKAGE)[0].label
  activeInput.value = getIntDictOptions(DICT_TYPE.STORE_PACKAGE)[0].value
}
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const confirmClick = async () => {
  if (!activeInput) {
    message.error('请输入储值金额')
    return
  }
  formLoading.value = true
  try {
    let data = {
      orderType: 2,
      villageId: info.value.villageId,
      parkId: info.value.parkId,
      carNumber: info.value.carNumber,
      orderMoney: activeInput.value
    }
    await ParkCarsApi.createParkCarOrder(data)
    message.success('充值成功')
    formLoading.value = false
    visibleShow.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const AddCarNum = (label: string, value: number) => {
  console.log(value)
  active.value = label
  activeInput.value = value
}

/** 初始化 **/
onMounted(() => {})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 重置表单 */
const resetForm = () => {
  inputValue.value = ''
  activeInput.value = ''
  active.value = ''
}
</script>
<style scoped lang="scss">
.active {
  border: 1px solid #007aff;
}
::v-deep .el-input__wrapper {
  background: none !important;
  border: none !important;
  box-shadow: none !important;
}
</style>
