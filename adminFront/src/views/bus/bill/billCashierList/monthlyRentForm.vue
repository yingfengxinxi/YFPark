<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 月租缴费 -->
  <el-dialog align-center width="800px" v-model="visibleShow" @close="cancelClick">
    <template #header="{ titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass" class="m-t-0px m-b-0px text-center">月租缴费</h4>
      </div>
    </template>
    <ContentWrap>
      <h2 class="font-800 font-size-20px m-t-0px">请选择月租套餐</h2>
      <div class="m-t-20px flex justify-start items-center" v-loading="loading">
        <div
          class="m-r-10px w-120px flex justify-center items-center flex-col h-60px line-height-20px text-center border-1px-solid-#e6e6e6 c-#007aff bg-#e5f1ff b-rd-5px cursor-pointer"
          v-for="(item, index) in list"
          :key="index"
          @click="AddCarNum(item.month, item)"
          :class="active == item.month ? 'active' : ''"
        >
          {{ item.name }}
          <div>{{ item.price }}元</div>
        </div>
        <div
          class="w-120px h-60px line-height-20px flex justify-center items-cente flex-col text-center border-1px-solid-#e6e6e6 c-#007aff bg-#e5f1ff b-rd-5px cursor-pointer"
          :class="active == '自定义' ? 'active' : ''"
          @click="AddCarNum('自定义', '')"
        >
          <el-input-number
            class="!bg-none !w-auto m-10px m-t-0px m-b-0px box-border c-#007aff text-center"
            placeholder="其他月份"
            :controls="false"
            :step="1"
            v-model="inputValue"
            @input="AddCarNum('自定义', $event)"
            :disabled="disabled"
          />
          <div>
            <template v-if="inputValue"> {{ activeInput }} 元 </template>
            <template v-else> 月租金额 </template>
          </div>
        </div>
      </div>
      <div class="flex m-t-50px">
        <div class="w-50%">
          <h2 class="font-800 font-size-20px m-t-0px">生效时间:</h2>

          <el-date-picker
            v-model="time"
            type="date"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            placeholder="请选择日期"
            :disabled="true"
          />
        </div>
        <div class="w-50%">
          <h2 class="font-800 font-size-20px m-t-0px">车位数量:</h2>
          {{ info.spaceNum }}
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
import { ParkChargeApi } from '@/api/bus/parkCharge'
import { formatDate } from '@/utils/formatTime'
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

const list = ref([])
const loading = ref(false)
const time = ref()

const disabled = ref(false)
const unitPrice = ref()
/** 打开抽屉 */
const open = async (form) => {
  console.log(form, 'form')
  visibleShow.value = true
  info.value = form
  resetForm()
  getList()
  time.value = formatDate(new Date(), 'YYYY-MM-DD')
}
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

const getList = async () => {
  try {
    loading.value = true
    const data = await ParkChargeApi.getMonthRechargePackageList({
      parkId: info.value.parkId
    })
    list.value = data
    console.log(data, 'data')
    if (data.length > 0) {
      active.value = data[0].name
      activeInput.value = Number(data[0].month) * Number(info.value.spaceNum)
    }
    const index = data.findIndex((item) => item.month == 1)
    if (index > -1) {
      unitPrice.value = data[index].price
    } else {
      console.log(index, 'index', unitPrice.value)
      disabled.value = true
    }
  } finally {
    loading.value = false
  }
}
const confirmClick = async () => {
  if (!activeInput) {
    message.error('月租金额不能为空')
    return
  }
  formLoading.value = true
  let orderInfo = {
    month: active.value == '自定义' ? inputValue.value : active.value,
    startDay: time.value
  }
  try {
    let data = {
      orderType: 1,
      villageId: info.value.villageId,
      parkId: info.value.parkId,
      carNumber: info.value.carNumber,
      orderMoney: activeInput.value,
      orderInfo: JSON.stringify(orderInfo)
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
const AddCarNum = (label: string, value: any) => {
  console.log(value)
  active.value = label
  if (label == '自定义') {
    if (disabled.value) {
      message.error('暂无一月份套餐金额,请前往计费规则进行设置')
      return false
    }
    if (value) {
      console.log('value', value, info.value.spaceNum, unitPrice.value)
      activeInput.value = value * info.value.spaceNum * unitPrice.value
    }
  } else {
    activeInput.value = value.month * info.value.spaceNum * value.price
  }
}

/** 初始化 **/
onMounted(() => {})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 重置表单 */
const resetForm = () => {
  inputValue.value = null
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
