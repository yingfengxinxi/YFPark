<template>
  <ElDrawer title="设置" v-model="drawerVisible" size="50%" :show-close="false">
    <template #header>
      <div>
        <div class="flex">
          <div>设置</div>
        </div>
        <el-tabs v-model="tabType" @tab-click="switchUserInfo" class="m-t-20px">
          <el-tab-pane label="预约时段" :name="1" />
          <el-tab-pane label="费用设置" :name="2" />
          <el-tab-pane label="耗材设置" :name="3" />
          <el-tab-pane label="服务项设置" :name="4" />
        </el-tabs>
      </div>
    </template>
    <!-- 预约时段 -->
    <div v-if="tabType == 1">
      <div class="flex gap-10px items-center select-none">
        <el-tooltip
          class="item"
          effect="dark"
          content="启用时间段模式后，租客需按照设定的时段预约工单，防止单个时间爆单的情况"
          placement="top"
        >
          <Icon icon="ep:question-filled" class="transform-translate-y-1px ml-6px" />
        </el-tooltip>
        <span class="text-14px">启用时间段模式</span>
        <el-switch
          v-model="switchValue"
          active-value="1"
          inactive-value="0"
          @change="changeSwitch"
        />
      </div>
      <div
        class="timeColor mt-10px flex gap-15px items-center pos-relative"
        v-if="switchValue == '1'"
      >
        <div class="pos-relative">
          <span class="item1"> 每日开放预约时间 </span>
        </div>
        <div class="pos-relative">
          <span class="item2"> 设置间隔时段 </span>
        </div>
        <div class="pos-relative">
          <span class="item3"> 间隔时段预约上限数 </span>
        </div>
        <el-button
          type="primary"
          class="pos-absolute pos-right-0 pos-top-0 transform-translate-y--50% z-999"
          @click="changeActiveSetting"
          >设置</el-button
        >
      </div>
      <!-- 日期列表 -->
      <div class="flex justify-between items-center w-100% mt-20px" v-if="switchValue == '1'">
        <Icon icon="ep:arrow-left" @click="Prevmonth" />
        <el-date-picker
          v-model="monthDate"
          type="month"
          placeholder="请选择月份"
          format="YYYY-MM"
          value-format="YYYY-MM"
          @change="getChargeSetting"
        />
        <Icon icon="ep:arrow-right" @click="Nextmonth" />
      </div>
      <div class="" v-if="switchValue == '1'">
        <div class="justify-between weekRow mt-20px text-end gap-8px mb-10px">
          <div v-for="(item, index) in week" :key="index" class="text-14px text-#">
            {{ item }}
          </div>
        </div>
      </div>
      <div class="justify-between weekRow text-end gap-8px" v-if="switchValue == '1'">
        <div v-for="(item, index) in DataList" :key="index" @click="changeActive(item, index)">
          <div
            class="aspect-ratio-1/1 text-14px color-#404040 dayItem"
            :class="{ active: ActiveIndex == index }"
            :style="{
              borderTop: item.day ? '1px solid #999' : 'none'
            }"
          >
            {{ item?.day }}
            <div class="h-100% flex flex-col items-center justify-center gap-4px">
              <div
                class="w-100% bg-#CFEFDF color-#36BB9A text-center"
                v-if="item.openStartTime && item.openEndTime"
              >
                {{ item.openStartTime + '~' + item.openEndTime }}
              </div>
              <div
                class="w-100% bg-#D2EAFB color-#4EA1F0 text-center"
                v-if="item.openStartTime && item.openEndTime"
              >
                {{ item.num }}{{ item.unit == 'hour' ? '小时' : '分钟' }}
              </div>
              <div
                class="w-100% bg-#FDE3CF color-#F56A00 text-center"
                v-if="item.openStartTime && item.openEndTime"
              >
                {{ item.orderLimit + '单' }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 费用设置 -->
    <div v-if="tabType == 2">
      <div>
        <span class="text-14px">收费方式</span>
        <el-radio-group v-model="Tab2Data.chargeType" class="ml-4">
          <el-radio label="1" size="small" :value="1"
            >完成后收费<el-tooltip
              class="item"
              effect="dark"
              content="租客下单无需支付费用，待工单服务完成后即支付实际费用"
              placement="top"
            >
              <Icon
                icon="ep:question-filled"
                class="transform-translate-y-1px ml-2px"
                :size="12"
              /> </el-tooltip
          ></el-radio>
          <el-radio label="2" size="small" :value="2"
            >下单即收费
            <el-tooltip
              class="item"
              effect="dark"
              content="租客下单需支付所选择的服务规格费用，如后续工单服务中还消耗了耗材，完结工单后还需支付耗材费用"
              placement="top"
            >
              <Icon icon="ep:question-filled" class="transform-translate-y-1px ml-2px" :size="12" />
            </el-tooltip>
          </el-radio>
        </el-radio-group>
      </div>
      <div class="border border-solid border-#E4E7ED rounded mt-10px">
        <el-card shadow="never">
          <template #header>
            <div class="">费用信息</div>
          </template>
          <!-- 下单即收费 -->
          <div>
            <div v-if="Tab2Data.chargeType == '2'">
              <span class="text-14px">收费类型</span>
              <el-radio-group v-model="Tab2Data.type" class="ml-4">
                <el-radio label="0" :value="0" size="small"
                  >订金<el-tooltip
                    class="item"
                    effect="dark"
                    content="租客下单只需支付所选择的服务规格的部分费用，剩余费用工单完结后支付"
                    placement="top"
                  >
                    <Icon
                      icon="ep:question-filled"
                      class="transform-translate-y-1px ml-2px"
                      :size="12"
                    /> </el-tooltip
                ></el-radio>
                <el-radio label="1" size="small" :value="1"
                  >全额
                  <el-tooltip
                    class="item"
                    effect="dark"
                    content="租客下单需支付所选择的服务规格的全部费用，如后续工单服务中还消耗了耗材，完结工单后还需支付耗材费用"
                    placement="top"
                  >
                    <Icon
                      icon="ep:question-filled"
                      class="transform-translate-y-1px ml-2px"
                      :size="12"
                    />
                  </el-tooltip>
                </el-radio>
              </el-radio-group>
            </div>
            <el-table :data="Tab2Data.chargeData">
              <el-table-column label="规格" align="center" prop="name">
                <template #header>
                  <div class="tableItem">规格</div>
                </template>
                <template #default="scope">
                  <el-input v-model="scope.row.name" placeholder="请输入规格" />
                </template>
              </el-table-column>
              <el-table-column label="规则说明" align="center" prop="desc">
                <template #default="scope">
                  <el-input v-model="scope.row.desc" placeholder="请输入规则说明" />
                </template>
              </el-table-column>
              <el-table-column label="价格" align="center" prop="price">
                <template #header>
                  <div class="tableItem">价格</div>
                </template>
                <template #default="scope">
                  <el-input
                    v-model="scope.row.price"
                    :min="0"
                    type="number"
                    placeholder="请输入价格"
                  >
                    <template #append>元</template>
                  </el-input>
                </template>
              </el-table-column>
              <el-table-column
                label="价格百分比"
                align="center"
                prop="percent"
                v-if="Tab2Data.chargeType == '2' && Tab2Data.type == '0'"
              >
                <template #default="scope">
                  <el-input
                    v-model="scope.row.percent"
                    type="number"
                    :min="0"
                    placeholder="请输入价格百分比"
                  >
                    <template #append> % </template>
                  </el-input>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template #default="scope">
                  <Icon icon="ep:delete" color="#f00" @click="delChargeData1(scope.$index)" />
                </template>
              </el-table-column>
              <template #append>
                <div
                  class="flex items-center justify-center py-10px border border-solid border-#EBEEF5 w-99% h-99% m-b-2px box-border cursor-pointer"
                  @click="addChargeData1"
                >
                  <Icon icon="ep:plus" />
                  添加
                </div>
              </template>
            </el-table>
          </div>
        </el-card>
      </div>
    </div>
    <!-- 耗材设置 -->
    <div v-if="tabType == 3">
      <div class="border border-solid border-#E4E7ED rounded">
        <el-card shadow="never">
          <template #header>
            <span
              >启用耗材
              <el-tooltip
                class="item"
                effect="dark"
                content="当工单服务过程中可能涉及更换零部件时，则可以启用耗材。服务人员可以在服务中添加工单实际使用的耗材。耗材在「资产管理」应用中管理。"
                placement="top"
              >
                <Icon
                  icon="ep:question-filled"
                  class="transform-translate-y-1px ml-2px"
                /> </el-tooltip
            ></span>
          </template>
          <div class="flex text-12px gap-4px text-#AAB2C8">
            <Icon icon="ep:question-filled" />
            当消耗工时为0时则无需收取工时费用，
            <span class="text-#2681F3 cursor-pointer" @click="settingacquiesce"
              >点击立即设置默认工时费用，</span
            >
            也可前往工时设置为每个岗位单独设置
          </div>
          <el-table :data="Tab3Data">
            <el-table-column align="center" prop="chargePrice" label="物料名称">
              <template #default="scope">
                <el-input v-model="scope.row.name" placeholder="请输入物料名称" disabled />
              </template>
            </el-table-column>
            <el-table-column align="center" prop="chargePrice" label="物料编码">
              <template #default="scope">
                <el-input v-model="scope.row.number" placeholder="请输入物料编码" disabled />
              </template>
            </el-table-column>
            <el-table-column align="center" prop="chargePrice" label="物料价格">
              <template #header>
                <div class="tableItem">物料价格</div>
              </template>
              <template #default="scope">
                <el-input
                  v-model="scope.row.price"
                  type="number"
                  :min="0"
                  placeholder="请输入物料价格"
                >
                  <template #append>元</template>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="chargePrice" label="消耗工时">
              <template #default="scope">
                <el-input
                  v-model="scope.row.workHour"
                  type="number"
                  :min="0"
                  placeholder="请输入消耗工时"
                >
                  <template #append>个</template>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="chargePrice" label="操作">
              <template #default="scope">
                <Icon icon="ep:delete" color="#f00" @click="delTab3Data(scope.$index)" />
              </template>
            </el-table-column>
            <template #append>
              <div
                class="flex items-center justify-center py-10px border border-solid border-#EBEEF5 w-99% h-99% m-b-2px box-border cursor-pointer"
                @click="addChargeData2"
              >
                <Icon icon="ep:plus" />
                添加
              </div>
            </template>
          </el-table>
        </el-card>
      </div>
    </div>
    <!-- 服务项设置 -->
    <div v-if="tabType == 4">
      <div class="border border-solid border-#E4E7ED rounded">
        <el-card shadow="never">
          <template #header>
            <span
              >自定义服务项
              <el-tooltip
                class="item"
                effect="dark"
                content="可通过自定义服务项添加耗材等无法满足自定义收费项，例如上门工单需要收取上门费用等。"
                placement="top"
              >
                <Icon
                  icon="ep:question-filled"
                  class="transform-translate-y-1px ml-2px"
                /> </el-tooltip
            ></span>
          </template>
          <el-table :data="Tab4data">
            <el-table-column align="center" prop="chargePrice" label="服务项名称">
              <template #header>
                <div class="tableItem">服务项名称</div>
              </template>
              <template #default="scope">
                <el-input v-model="scope.row.name" placeholder="请输入服务项名称" />
              </template>
            </el-table-column>
            <el-table-column align="center" prop="chargePrice" label="服务项价格">
              <template #header>
                <div class="tableItem">服务项价格</div>
              </template>
              <template #default="scope">
                <el-input
                  v-model="scope.row.price"
                  type="number"
                  :min="0"
                  placeholder="请输入服务项价格"
                >
                  <template #append>元</template>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="chargePrice" label="操作">
              <template #default="scope">
                <Icon icon="ep:delete" color="#f00" @click="delTab4Data(scope.$index)" />
              </template>
            </el-table-column>
            <template #append>
              <div
                class="flex items-center justify-center py-10px border border-solid border-#EBEEF5 w-99% h-99% m-b-2px box-border cursor-pointer"
                @click="addChargeData4"
              >
                <Icon icon="ep:plus" />
                添加
              </div>
            </template>
          </el-table>
        </el-card>
      </div>
    </div>
    <template #footer>
      <div class="flex justify-end" v-if="tabType != 1">
        <el-button type="primary" plain @click="drawerVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </div>
    </template>
  </ElDrawer>
  <!-- 设置时间段 -->
  <chargeTimeSetting ref="chargeTimeSettingRef" @success="getChargeSetting" />
  <!-- 选择使用物料 -->
  <materielDialog ref="materielDialogRef" @success="Tab3submit" />
  <!-- 设置默认工时费用 -->
  <acquiesceFrom ref="acquiesceFromRef" />
</template>
<script lang="ts" setup>
import { CategorySubcatApi, VO } from '@/api/bus/Category/CategorySubcat/index'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import chargeTimeSetting from './chargeTimeSetting.vue'
import materielDialog from './materielDialog.vue'
import { workOrderHourApi } from '@/api/bus/Category/workOrderHour/index'
import acquiesceFrom from '../workOrderHour/Form.vue'
import { id } from 'element-plus/es/locale'
const message = useMessage() // 消息弹窗
const drawerVisible = ref(false)
const tabType = ref(1)
const monthDate = ref('')
const week = ['日', '一', '二', '三', '四', '五', '六']
const switchValue = ref('0')

// 下一月
const Nextmonth = () => {
  const [year, month] = monthDate.value.split('-').map(Number)
  const date = new Date(year, month - 1)
  date.setMonth(date.getMonth() + 1)
  const newYear = date.getFullYear()
  const newMonth = date.getMonth() + 1
  const formattedMonth = newMonth.toString().padStart(2, '0')
  monthDate.value = `${newYear}-${formattedMonth}`
  getChargeSetting()
}
//上一月
const Prevmonth = () => {
  const [year, month] = monthDate.value.split('-').map(Number)
  const date = new Date(year, month - 1)
  date.setMonth(date.getMonth() - 1)
  const newYear = date.getFullYear()
  const newMonth = date.getMonth() + 1
  const formattedMonth = newMonth.toString().padStart(2, '0')
  monthDate.value = `${newYear}-${formattedMonth}`
  getChargeSetting()
}
//获取现在年份月份
const getNowFormatDate = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = now.getMonth() + 1
  const formattedMonth = month.toString().padStart(2, '0')
  return `${year}-${formattedMonth}`
}

const formId = ref('') ///子任务id
const applicationValue = ref('') //应用标识
const formId_bigValue = ref('0') //父任务id
function open(id, application, formId_big) {
  applicationValue.value = application
  formId_bigValue.value = formId_big
  monthDate.value = getNowFormatDate()
  formId.value = id
  ActiveIndex.value = -1
  ActiveValue.value = ''
  DataList.value = []
  drawerVisible.value = true
  getChargeSetting(id)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
//获取收费设置
const DataList = ref([])
const getChargeSetting = (id) => {
  CategorySubcatApi.getCharge({
    subcatId: formId.value,
    date: monthDate.value
  }).then(async (res) => {
    if (typeof res.feeSet == 'string') {
      Tab2Data.value = JSON.parse(res.feeSet)
    }

    Tab3Data.value = JSON.parse(res?.stuffSet)
    Tab4data.value = JSON.parse(res?.serviceSet)
    const day = getDaysByMonth(monthDate.value)
    const dataList = []
    switchValue.value = res?.isUse || '0'
    //将数据进行合并
    for (let i = 1; i <= day; i++) {
      if (res?.reservationDateList) {
        res.reservationDateList.forEach((item) => {
          if (item.orderTime == `${monthDate.value}-${i.toString().padStart(2, '0')}`) {
            dataList[i - 1] = item
            dataList[i - 1].day = i.toString().padStart(2, '0')
          } else if (dataList[i - 1]) {
            return
          } else if (!dataList[i - 1]) {
            dataList[i - 1] = {
              orderTime: `${monthDate.value}-${i.toString().padStart(2, '0')}`,
              day: i.toString().padStart(2, '0')
            }
          }
        })
      } else {
        dataList[i - 1] = {
          orderTime: `${monthDate.value}-${i.toString().padStart(2, '0')}`,
          day: i.toString().padStart(2, '0')
        }
      }
    }
    //在数组中添加空状态,与星期对应
    const week = new Date(monthDate.value).getDay()
    for (let i = 0; i < week; i++) {
      dataList.unshift({})
    }
    console.log(dataList, 'dataList', week)
    DataList.value = dataList
  })
}
// 切换选中状态
const chargeTimeSettingRef = ref()
const ActiveValue = ref('')
const ActiveIndex = ref(-1)
///////////////////////////点击打开弹窗
const changeActive = (item, index) => {
  if (item.day == undefined) return
  ActiveValue.value = item
  ActiveIndex.value = index
  chargeTimeSettingRef.value.open(
    item,
    formId.value,
    applicationValue.value,
    formId_bigValue.value,
    'day'
  )
}
const changeActiveSetting = (duo) => {
  chargeTimeSettingRef.value.open(
    {
      openTime: [],
      time: '',
      orderLimit: -1
    },
    formId.value,
    applicationValue.value,
    formId_bigValue.value,
    'duo'
  )
}
// 获得该月天数
const getDaysByMonth = (date) => {
  const [year, month] = date.split('-').map(Number)
  return new Date(year, month, 0).getDate()
}
//切换时间段模式
const changeSwitch = () => {
  if (switchValue.value == '1') {
    return
  }
  CategorySubcatApi.categoryFeeSetCreate({
    categoryId: formId_bigValue.value,
    subcatId: formId.value,
    application: applicationValue.value,
    isUse: switchValue.value
  }).then((res) => {
    getChargeSetting()
  })
}
onMounted(async () => {
  monthDate.value = getNowFormatDate()
})
/**
 * 费用设置模块
 */
const Tab2Data = ref({
  chargeType: 1,
  chargeData: [],
  type: 0
})
const addChargeData1 = () => {
  Tab2Data.value.chargeData.push({
    name: '',
    desc: '',
    price: '',
    percent: ''
  })
}
const delChargeData1 = (index) => {
  Tab2Data.value.chargeData.splice(index, 1)
}
const submit = () => {
  if (tabType.value == 2) {
    if (Tab2Data.value.chargeData.length == 0) {
      message.error('请添加费用信息')
      return
    }
    let visble = true
    Tab2Data.value.chargeData.forEach((item) => {
      if (item.name == '' || item.price == '') {
        visble = false
      }
      if (Tab2Data.value.chargeType == '1' || Tab2Data.value.type == '1') {
        delete item.percent
      }
    })
    if (!visble) {
      message.error('带*号的为必填项,请填写完整')
      return
    }
    CategorySubcatApi.categoryFeeSetCreate({
      categoryId: formId_bigValue.value,
      subcatId: formId.value,
      application: applicationValue.value,
      feeSet: JSON.stringify(Tab2Data.value)
    }).then((res) => {
      message.success('设置成功')
      drawerVisible.value = false
    })
  } else if (tabType.value == 3) {
    if (Tab3Data.value.length == 0) {
      message.error('请添加耗材信息')
      return
    }
    let visble = true
    Tab3Data.value.forEach((item) => {
      if (item.price == '') {
        visble = false
      }
    })
    if (!visble) {
      message.error('带*号的为必填项,请填写完整')
      return
    }
    CategorySubcatApi.categoryFeeSetCreate({
      categoryId: formId_bigValue.value,
      subcatId: formId.value,
      application: applicationValue.value,
      stuffSet: JSON.stringify(Tab3Data.value)
    }).then((res) => {
      message.success('设置成功')
      drawerVisible.value = false
    })
  } else if (tabType.value == 4) {
    if (Tab4data.value.length == 0) {
      message.error('请添加服务项信息')
      return
    }
    let visble = true
    Tab4data.value.forEach((item) => {
      if (item.name == '' || item.price == '') {
        visble = false
      }
    })
    if (!visble) {
      message.error('带*号的为必填项,请填写完整')
      return
    }
    CategorySubcatApi.categoryFeeSetCreate({
      categoryId: formId_bigValue.value,
      subcatId: formId.value,
      application: applicationValue.value,
      serviceSet: JSON.stringify(Tab4data.value)
    }).then((res) => {
      message.success('设置成功')
      drawerVisible.value = false
    })
  }
}
/**
 * 耗材设置模块
 */
const Tab3Data = ref([])
const materielDialogRef = ref()
const addChargeData2 = () => {
  materielDialogRef.value.open()
}
const Tab3submit = (data) => {
  data.forEach((item) => {
    Tab3Data.value.push({
      stuffStockId: item.stuffStockId,
      depositoryId: item.depositoryId,
      name: item.stuffName,
      number: item.stuffNumber,
      price: '',
      workHour: ''
    })
  })
}
const delTab3Data = (index) => {
  Tab3Data.value.splice(index, 1)
}
const acquiesceFromRef = ref()
const settingacquiesce = () => {
  workOrderHourApi
    .getworkHourList({
      application: applicationValue.value
    })
    .then((res) => {
      acquiesceFromRef.value.open('update', applicationValue.value, res[0].id)
    })
}
//服务项设置
const Tab4data = ref([])
const addChargeData4 = () => {
  Tab4data.value.push({
    name: '',
    price: ''
  })
}
</script>
<style lang="scss" scoped>
.timeColor {
  color: #333;
  font-size: 14px;
  .item1 {
    padding-left: 20px;
    &::before {
      content: ' ';
      width: 12px;
      height: 12px;
      background-color: #cfefdf;
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
    }
  }
  .item2 {
    padding-left: 20px;
    &::before {
      content: ' ';
      width: 12px;
      height: 12px;
      background-color: #d2eafb;
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
    }
  }
  .item3 {
    padding-left: 20px;
    &::before {
      content: ' ';
      width: 12px;
      height: 12px;
      background-color: #fde3cf;
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
    }
  }
}
.weekRow {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
}
.dayItem:hover {
  background-color: #f5f5f5;
}
.active {
  background-color: #e6f7ff;
  color: #1890ff;
}

:deep(.el-drawer__header) {
  margin-bottom: 0px;
}
.tableItem {
  &::before {
    content: '*';
    color: red;
    margin-right: 5px;
  }
}
</style>
<style>
.el-drawer__header {
  margin-bottom: 0px;
}
</style>
