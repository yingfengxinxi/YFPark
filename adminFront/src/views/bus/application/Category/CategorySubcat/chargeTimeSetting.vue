<template>
  <div>
    <ElDrawer title="设置预约时间段" v-model="drawerVisible" size="25%">
      <!-- 点击日期显示 -->
      <div
        class="border-b border-solid border-#999 border-0 pb-20px text-16px color-#7A6666"
        v-if="typeValue == 'day'"
        >当前日期:{{ rowValue.orderTime }}</div
      >
      <!-- 点击设置按钮显示 -->
      <div
        class="border-b border-solid border-#999 border-0 pb-20px text-16px color-#7A6666"
        v-if="typeValue == 'duo'"
      >
        <el-form label-position="top">
          <el-form-item
            label="设置可预约日期"
            :rules="[{ required: true, message: '请选择日期', trigger: 'change' }]"
          >
            <el-date-picker
              v-model="rowValue.orderTime"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              style="width: calc(100% - 20px)"
            />
          </el-form-item>
        </el-form>
        <div>
          <el-checkbox-group v-model="rowValue.weekList">
            <el-checkbox
              v-for="(item, index) in getStrDictOptions('CATEGORYFEESETWEEK')"
              :label="item.label"
              :value="item.value"
              :key="index"
            />
          </el-checkbox-group>
        </div>
        <div class="flex gap-10px text-12px">
          <Icon icon="ep:question-filled" />
          若选择周几后，会在选择的可预约日期内，将选定的周几设置预约时间段
        </div>
      </div>
      <el-form label-position="top" class="mt-20px">
        <el-form-item
          label="每日开放预约时间:"
          :rules="[{ required: true, message: '请选择时间', trigger: 'change' }]"
        >
          <el-time-picker
            v-model="rowValue.openTime"
            is-range
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            format="HH:mm"
            value-format="HH:mm"
            :clearable="false"
          />
        </el-form-item>
        <el-form-item
          label="设置间隔时段"
          :rules="[{ required: true, message: '请选择时间', trigger: 'change' }]"
        >
          <el-select v-model="rowValue.time">
            <el-option
              v-for="(item, index) in getStrDictOptions('GAP_TIME')"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
            <el-option
              v-for="(item, index) in TimeList"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
            <template #footer>
              <div class="color-#409EFF items-center" @click="addtime">
                <Icon icon="ep:plus" color="#409EFF" class="transform-translate-y-1.8px" />
                自定义时间
              </div>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item
          label="间隔时段预约上限数"
          :rules="[{ required: true, message: '请选择时间', trigger: 'change' }]"
        >
          <el-input type="number" :min="0" v-model="rowValue.orderLimit" />
        </el-form-item>
        <el-checkbox v-model="rowValue.duoselect" v-if="typeValue == 'duo'">
          提交后保留当前填写的内容继续添加
        </el-checkbox>
      </el-form>
      <template #footer>
        <div class="flex justify-end">
          <el-button @click="drawerVisible = false">取消</el-button>
          <el-button type="primary" @click="submit">确认</el-button>
        </div>
      </template>
    </ElDrawer>
    <!-- /////添加间隔时段 -->
    <el-dialog v-model="addTimedialog" title="设置间隔时间" width="500">
      <div>
        <el-form ref="TimeformRef">
          <el-form-item label="自定义时间">
            <div class="flex gap-10px">
              <el-input
                v-model="Timeform.time"
                type="number"
                placeholder="请输入时间"
                :min="0"
                style="width: 140px"
              />
              <el-select
                style="width: 100px"
                v-model="Timeform.unit"
                placeholder="请选择单位"
                @change="changeTimeform"
              >
                <el-option label="小时" value="hour" />
                <el-option label="分钟" value="minute" />
              </el-select>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button>确认</el-button>
          <el-button type="primary" @click="submitTime">确认</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import { CategorySubcatApi, VO } from '@/api/bus/Category/CategorySubcat/index'
const message = useMessage() // 消息弹窗

const drawerVisible = ref(false)
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { Message } from '@/layout/components//Message'
const TimeList = ref([])
const form = ref({
  time: ''
})
const addTimedialog = ref(false)
const addtime = () => {
  Timeform.value.time = ''
  addTimedialog.value = true
}
//打开弹窗
const rowValue = ref({}) //选择天数据
const formIdValue = ref('') //工单子类id
const applicationValue = ref('') //应用标识
const formId_bigValue = ref('') //工单父类
const typeValue = ref('') //打开弹窗类型
function open(row, formId, application, formId_big, type) {
  rowValue.value = row
  formIdValue.value = formId
  applicationValue.value = application
  formId_bigValue.value = formId_big
  typeValue.value = type
  drawerVisible.value = true
  if (type == 'day') {
    rowValue.value.openTime = [
      formIdValue.value.openStartTime || '09:00',
      formIdValue.value.openEndTime || '18:00'
    ]
    rowValue.value.time = `${rowValue.value.num || '1'}-${rowValue.value.unit || 'hour'}`
    rowValue.value.orderLimit = Number(rowValue.value.orderLimit) || 1
  } else if (type == 'duo') {
    rowValue.value.time = '1-hour'
    rowValue.value.orderLimit = 1
    rowValue.value.openTime = ['09:00', '18:00']
    rowValue.value.orderTime = []
    rowValue.value.weekList = []
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
//添加时间下拉

const Timeform = ref({
  time: '',
  unit: 'hour',
  name: '小时'
})
const submitTime = () => {
  if (!Timeform.value.time) {
    message.error('请输入时间')
    return
  }
  TimeList.value.push({
    label: `${Timeform.value.time}${Timeform.value.name}`,
    value: `${Timeform.value.time}-${Timeform.value.unit}`
  })
  addTimedialog.value = false
}
const changeTimeform = () => {
  if (Timeform.value.unit == 'hour') {
    Timeform.name = '小时'
  } else {
    Timeform.name = '分钟'
  }
}
const submit = () => {
  if (typeValue.value == 'day') {
    CategorySubcatApi.categoryFeeSetCreate({
      categoryId: formId_bigValue.value,
      subcatId: formIdValue.value,
      application: applicationValue.value,
      isUse: 1,
      reservationDateSave: {
        orderStartTime: rowValue.value.orderTime,
        orderEndTime: rowValue.value.orderTime,
        orderLimit: rowValue.value.orderLimit,
        openStartTime: rowValue.value.openTime[0],
        openEndTime: rowValue.value.openTime[1],
        num: rowValue.value.time.split('-')[0],
        unit: rowValue.value.time.split('-')[1]
      }
    }).then((res) => {
      message.success('设置成功')
      emit('success')
      drawerVisible.value = false
    })
  } else if (typeValue.value == 'duo') {
    if (rowValue.value.orderTime.length == 0) {
      message.error('请选择日期')
      return
    }
    CategorySubcatApi.categoryFeeSetCreate({
      categoryId: formId_bigValue.value,
      subcatId: formIdValue.value,
      application: applicationValue.value,
      isUse: 1,
      reservationDateSave: {
        orderStartTime: rowValue.value.orderTime[0],
        orderEndTime: rowValue.value.orderTime[1],
        orderLimit: rowValue.value.orderLimit,
        openStartTime: rowValue.value.openTime[0],
        openEndTime: rowValue.value.openTime[1],
        num: rowValue.value.time.split('-')[0],
        unit: rowValue.value.time.split('-')[1],
        week: rowValue.value.weekList.join(',')
      }
    }).then((res) => {
      message.success('设置成功')
      emit('success')
      if (!rowValue.value.duoselect) {
        drawerVisible.value = false
      }
    })
  }
}
const emit = defineEmits(['success']) // 提供 emit 方法，用于触发父组件事件
</script>
