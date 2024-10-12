<template>
  <div>
    <!-- 巡检计划 -->
    <el-drawer :title="dialogTitle" v-model="dialogVisible" size="50%">
      <div class="flex box mb-18px">
        <div class="w-33% text-center flex justify-center gap-10px pos-relative">
          <div
            class="border border-#999 border-solid w-24px h-24px leading-24px rounded-50% item"
            :class="activeNum >= 1 ? 'active' : ''"
          >
            1
          </div>
          <div class="leading-24px text-14px"> 计划设置 </div>
        </div>
        <div class="w-33% text-center flex justify-center gap-10px pos-relative">
          <div
            class="border border-#999 border-solid w-24px h-24px leading-24px rounded-50% item"
            :class="activeNum >= 2 ? 'active' : ''"
          >
            2
          </div>
          <div class="leading-24px text-14px"> 巡检路线 </div>
        </div>
        <div class="w-33% text-center flex justify-center gap-10px">
          <div
            class="border border-#999 border-solid w-24px h-24px leading-24px rounded-50%"
            :class="activeNum >= 3 ? 'active' : ''"
          >
            3
          </div>
          <div class="leading-24px text-14px"> 通知设置 </div>
        </div>
      </div>
      <!-- 计划设置模块 -->
      <div class="" v-show="activeNum == '1'">
        <el-form label-position="top" :model="formData" ref="step1ref">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="计划编号" required>
                <el-input placeholder="计划编号自动生成" v-model="formData.planNumber" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="计划名称"
                :rules="[{ required: true, message: '请输入计划名称', trigger: 'blur' }]"
                prop="planName"
              >
                <el-input v-model="formData.planName" placeholder="请输入计划名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="负责岗位"
                :rules="[{ required: true, message: '请选择负责岗位', trigger: 'blur' }]"
                prop="stationId"
              >
                <el-select v-model="formData.stationId" placeholder="请选择负责岗位">
                  <el-option
                    v-for="item in stationOptions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="负责部门"
                :rules="[{ required: true, message: '请选择负责部门', trigger: 'blur' }]"
                prop="departmentId"
              >
                <el-tree-select
                  v-model="formData.departmentId"
                  :data="departmentOptions"
                  :render-after-expand="false"
                  :check-strictly="true"
                  placeholder="请选择负责部门"
                  :props="{ label: 'name', value: 'id', children: 'children' }"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="开始日期"
                :rules="[{ required: true, message: '请选择开始日期', trigger: 'blur' }]"
                prop="startDate"
              >
                <el-date-picker
                  v-model="formData.startDate"
                  type="date"
                  placeholder="请选择开始日期"
                  style="width: 100%"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
                  :disabled-date="StartdisabledDate"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="截止日期"
                :rules="[
                  { required: formData.dateFlag != '1', message: '请选择截止日期', trigger: 'blur' }
                ]"
                prop="endDate"
              >
                <template #label>
                  <span class="pos-relative">
                    <span>截止日期</span>
                    <div class="pos-absolute pos-top--5px pos-left-70px">
                      <el-checkbox
                        label="永久"
                        true-value="1"
                        false-value="0"
                        v-model="formData.dateFlag"
                      />
                    </div>
                  </span>
                </template>
                <el-date-picker
                  v-model="formData.endDate"
                  type="date"
                  placeholder="请选择截止日期"
                  style="width: 100%"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
                  :disabled-date="EnddisabledDate"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="允许提前多久执行"
                :rules="[{ required: true, message: '请输入允许提前多久执行', trigger: 'blur' }]"
                prop="inAdvance"
              >
                <el-input
                  type="number"
                  :min="0"
                  v-model="formData.inAdvance"
                  placeholder="请输入允许提前多久执行"
                >
                  <template #append>
                    <div class="w-80px">
                      <el-select
                        style="width: 100%"
                        class="!w-120px"
                        v-model="formData.minuteTime"
                        placeholder="请输入允许提前多久执行单位"
                      >
                        <el-option
                          v-for="(item, index) in getStrDictOptions('MINUTE_TIME')"
                          :key="index"
                          :label="item.label"
                          :value="Number(item.value)"
                        />
                      </el-select>
                    </div>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="超时时间要求"
                :rules="[{ required: true, message: '请输入超时时间要求', trigger: 'blur' }]"
                prop="timeLimit"
              >
                <el-input
                  type="number"
                  :min="0"
                  placeholder="请输入超时时间要求"
                  v-model="formData.timeLimit"
                >
                  <template #append> 小时 </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="超时处理"
                :rules="[{ required: true, message: '请选择超时处理', trigger: 'blur' }]"
                prop="timeOutType"
              >
                <el-select v-model="formData.timeOutType" placeholder="请选择超时处理">
                  <el-option
                    v-for="item in getStrDictOptions('TIMEOUT_TYPE')"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="整改上报应用"
                :rules="[
                  {
                    required: true,
                    message: '请选择整改上报应用',
                    trigger: 'blur'
                  }
                ]"
                prop="workOrderApp"
              >
                <el-select v-model="formData.workOrderApp" placeholder="请选择整改上报应用">
                  <el-option
                    v-for="item in getStrDictOptions('WORK_ORDER_APP')"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="p-1px rounded" style="border: 1px solid #e4e7ed">
          <el-card shadow="never">
            <template #header>
              <div> 计划周期 </div>
            </template>
            <el-form label-position="top">
              <el-form-item label="重复频率">
                <div class="flex gap-10px">
                  <el-select style="width: 220px" v-model="formData.planRule.cycle1">
                    <el-option
                      v-for="(item, index) in getStrDictOptions('CYCLE1')"
                      :key="index"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                  <el-select
                    style="width: 220px"
                    v-model="formData.planRule.cycle2"
                    @change="changeCycle2"
                  >
                    <el-option
                      v-for="(item, index) in getStrDictOptions('CYCLE2')"
                      :key="index"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </div>
              </el-form-item>
              <el-form-item
                label="重复规则"
                v-if="formData.planRule.cycle2 == '3' || formData.planRule.cycle2 == '2'"
              >
                <!-- 周规则 -->
                <div class="flex gap-10px" v-if="formData.planRule.cycle2 == '2'">
                  <div
                    v-for="(item, index) in getStrDictOptions('RULE_WEEK')"
                    :key="index"
                    class="border border-#999 border-solid h-20px leading-20px text-12px px-10px rounded-2px cursor-pointer"
                    @click="handleWeekRule(item.value)"
                    :class="
                      formData.planRule.rule.includes(item.value) ? 'bg-#409eff text-white' : ''
                    "
                  >
                    {{ item.label }}
                  </div>
                </div>
                <!-- 月规则 -->
                <div class="flex gap-10px flex-wrap" v-if="formData.planRule.cycle2 == '3'">
                  <div
                    v-for="(item, index) in getStrDictOptions('RULE_MONTH')"
                    :key="index"
                    class="border border-#999 border-solid h-20px leading-20px text-12px px-10px rounded-2px cursor-pointer"
                    @click="handleWeekRule(item.value)"
                    :class="
                      formData.planRule.rule.includes(item.value) ? 'bg-#409eff text-white' : ''
                    "
                  >
                    {{ item.label }}
                  </div>
                </div>
              </el-form-item>
              <el-form-item label="执行时间">
                <div class="flex gap-10px flex-wrap">
                  <div
                    v-for="(item, index) in getStrDictOptions('EXECUTION_TIME')"
                    :key="index"
                    class="border border-#999 border-solid h-20px leading-20px text-12px px-10px rounded-2px cursor-pointer"
                    @click="formData.planRule.executeTime = item.value"
                    :class="
                      formData.planRule.executeTime == item.value ? 'bg-#409eff text-white' : ''
                    "
                  >
                    {{ item.label }}
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </el-card>
        </div>
      </div>
      <!-- 巡检路线模块 -->
      <div v-show="activeNum == '2'">
        <el-form label-position="top">
          <el-form-item label="巡检顺序">
            <el-radio-group size="large" v-model="formData.patrolOrder">
              <el-radio-button label="必须依次" value="1" />
              <el-radio-button label="可以随机" value="2" />
            </el-radio-group>
          </el-form-item>
          <el-form-item label="巡检点设置">
            <template #label>
              <div class="flex justify-between items-center w-100%">
                <div>巡检点设置</div>
                <el-button type="primary" @click="openPositionDialog"> 添加巡检点 </el-button>
              </div>
            </template>
            <el-table class="mt-10px" :data="PositionData">
              <el-table-column prop="number" label="巡检点编码" align="center" />
              <el-table-column prop="name" label="巡检点名称" align="center" />
              <el-table-column prop="positionName" label="位置" align="center" />
              <el-table-column prop="isScanCodeCheck" label="扫码打卡" align="center">
                <template #default="scope">
                  <el-switch
                    v-model="scope.row.isScanCodeCheck"
                    :active-value="1"
                    :inactive-value="0"
                  />
                </template>
              </el-table-column>
              <el-table-column prop="isNfcClock" label="NFC打卡" align="center">
                <template #default="scope">
                  <el-switch v-model="scope.row.isNfcClock" :active-value="1" :inactive-value="0" />
                </template>
              </el-table-column>
              <el-table-column prop="operation" label="操作" align="center" width="200">
                <template #default="scope">
                  <div class="flex gap-10px justify-center">
                    <div class="cursor-pointer" @click="viewphone(scope.row.formId)">
                      预览表单
                    </div>
                    <div
                      v-if="scope.$index != 0"
                      @click="MoveIndex('up', scope.$index)"
                      class="text-#409EFF cursor-pointer"
                      >上移</div
                    >
                    <div
                      v-if="scope.$index != PositionData.length - 1"
                      @click="MoveIndex('down', scope.$index)"
                      class="text-#409EFF cursor-pointer"
                      >下移</div
                    >
                    <div class="text-#f00 cursor-pointer" @click="delPositionData(scope.$index)"
                      >删除</div
                    >
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-form>
      </div>
      <!-- 通知设置 -->
      <div v-show="activeNum == '3'">
        <el-form label-position="top">
          <el-form-item label="提醒方式">
            <el-select style="width: 220px" v-model="formData.remindJson.noticeType" multiple>
              <el-option
                v-for="(item, index) in getStrDictOptions('REMIND')"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="任务发送">
            <div class="flex gap-10px w-100%">
              <span class="min-w-2em">提前</span>
              <el-input
                style="width: 100px"
                type="number"
                v-model="formData.remindJson.noticeTime"
              />
              <el-select v-model="formData.remindJson.noticeTimeUnit" style="width: 100px">
                <el-option
                  v-for="(item, index) in getStrDictOptions('NOTICE_TIME_UNIT')"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <span class="min-w-2em">提醒</span>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <el-button @click="dialogVisible = false" v-if="activeNum == 1">取 消</el-button>
        <el-button @click="UpStep" v-else>上一步</el-button>
        <el-button @click="submitForm" type="primary" :disabled="formLoading" v-if="activeNum == 3"
          >确 定</el-button
        >
        <el-button @click="NextStep" type="primary" v-else>下一步</el-button>
      </template>
    </el-drawer>
    <PositionDialog ref="PositionDialogRef" @submit="selectData" />

    <!-- 预览表单 -->
    <el-dialog
      v-model="viewphoneVisible"
      width="305"
      style="background-color: rgba(0, 0, 0, 0); shadow: none"
    >
      <phone ref="phone_ref" v-if="viewphoneVisible" />
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { patrolPlanEquipmentApi, VO } from '@/api/bus/patrol/planEquipment'
import { onMounted } from 'vue'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { formApi } from '@/api/bus/patrol/form/index'
import phone from '@/views/bus/application/patrol/form/phone.vue'

const activeNum = ref(1)
/** 应用巡检计划 表单 */
defineOptions({ name: 'Form' })
import { defaultProps, handleTree } from '@/utils/tree'
import PositionDialog from '@/views/bus/application/patrol/position/PositionDialog.vue'
import { forEach } from '../../../../../utils/tree'
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  stationParentId: '',
  departmentParentId: '',
  planNumber: '',
  planName: '',
  stationId: '',
  departmentId: '',
  startDate: '',
  endDate: '',
  dateFlag: 0,
  inAdvance: '',
  minuteTime: '1',
  timeLimit: '',
  timeOutType: '',
  workOrderApp: '',
  planRule: {
    cycle1: '1',
    cycle2: '2',
    rule: ['1'], //重复规则,多选
    executeTime: '01:00' //执行时间,单选
  },
  patrolOrder: '1', //巡检顺序
  remindJson: {
    noticeType: [],
    noticeTime: '1',
    noticeTimeUnit: '1'
  }
})
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    try {
      formData.value = await patrolPlanEquipmentApi.get(id)
      formData.value.planRule = JSON.parse(formData.value.planRule)
      formData.value.remindJson = JSON.parse(formData.value.remindJson)
      PositionData.value = formData.value.planPositionList
      formData.value.planRule.rule = formData.value.planRule.rule.split(',')
      formData.value.remindJson.noticeType = formData.value.remindJson.noticeType.split(',')
    } finally {
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
///岗位下拉数据
const stationOptions = ref([])
const GetstationOptions = async () => {
  const data = await patrolPlanEquipmentApi.getStationList()
  stationOptions.value = data
}
///部门下拉数据
const departmentOptions = ref([])
const GetdepartmentOptions = async () => {
  const data = await patrolPlanEquipmentApi.getDeptList()
  departmentOptions.value.push(...handleTree(data))
}
//禁用开始时间
const StartdisabledDate = (time: any) => {
  return time.getTime() < Date.now() - 8.64e7
}
//禁用结束时间
const EnddisabledDate = (time: any) => {
  // return time.getTime() <
  if (formData.value.startDate) {
    return time.getTime() < new Date(formData.value.startDate).getTime()
  } else {
    return time.getTime() < Date.now() - 8.64e7
  }
}
//更改重复频率
const changeCycle2 = (val: any) => {
  formData.value.planRule.rule = ['1']
}
//点击周规则
const handleWeekRule = (val: any) => {
  const index = formData.value.planRule.rule.indexOf(val)
  if (index > -1) {
    formData.value.planRule.rule.splice(index, 1)
  } else {
    formData.value.planRule.rule.push(val)
  }
}
//添加巡检点弹窗
const PositionDialogRef = ref()
const openPositionDialog = () => {
  PositionDialogRef.value.open(PositionData.value)
}
const PositionData = ref([])
const selectData = async (val: any) => {
  const data = []
  val.forEach((item) => {
    data.push({
      positionId: item.id,
      isScanCodeCheck: 0,
      isNfcClock: 0,
      number: item.number,
      name: item.name,
      positionName: item.positionName,
      formId: item.formId
    })
  })
  setTimeout(() => {
    PositionData.value = data
  }, 100)
}
const delPositionData = (index: number) => {
  PositionData.value.splice(index, 1)
}
//上移下移
const MoveIndex = (type, index) => {
  if (type == 'up') {
    ;[PositionData.value[index], PositionData.value[index - 1]] = [
      PositionData.value[index - 1],
      PositionData.value[index]
    ]
  } else {
    ;[PositionData.value[index], PositionData.value[index + 1]] = [
      PositionData.value[index + 1],
      PositionData.value[index]
    ]
  }
}
//下一步
const step1ref = ref()
const NextStep = () => {
  if (activeNum.value == 1) {
    if (formData.value.planRule.rule.length == 0 && formData.value.planRule.cycle2 != '1') {
      message.error('请选择重复规则')
      return
    }
    step1ref.value.validate((valid) => {
      if (valid) {
        activeNum.value = 2
      }
    })
  } else if (activeNum.value == 2) {
    if (PositionData.value.length == 0) {
      message.error('请添加巡检点')
      return
    }
    activeNum.value = 3
  }
}
//上一步
const UpStep = () => {
  if (activeNum.value == 3) {
    activeNum.value = 2
  } else if (activeNum.value == 2) {
    activeNum.value = 1
  }
}
const emit = defineEmits(['success'])
//提交表单
const submitForm = async () => {
  // 重复频率
  const pinlv = `${
    getStrDictOptions('CYCLE1').find((item) => item.value == formData.value.planRule.cycle1)?.label
  }${
    getStrDictOptions('CYCLE2').find((item) => item.value == formData.value.planRule.cycle2)?.label
  }`
  // 重复规则--周
  const weekDays = getStrDictOptions('RULE_WEEK')
  const weekDaysMap = new Map(weekDays.map((day) => [day.value, day.label]))
  const result = formData.value.planRule.rule.map((num) => `周${weekDaysMap.get(num)}`)
  // 重复规则--月
  const monthDays = getStrDictOptions('RULE_MONTH')
  const monthDaysMap = new Map(monthDays.map((day) => [day.value, day.label]))
  const result2 = formData.value.planRule.rule.map((num) => `${monthDaysMap.get(num)}号`)
  const cycleTxt =
    pinlv +
    (formData.value.planRule.cycle2 == '2' ? result.join('、') : result2.join('、')) +
    formData.value.planRule.executeTime

  if (formData.value.dateFlag == '1') {
    formData.value.endDate = ''
  }

  const newPositionData = []
  PositionData.value.forEach((item, index) => {
    newPositionData.push({
      positionId: item.positionId,
      isScanCodeCheck: item.isScanCodeCheck,
      isNfcClock: item.isNfcClock,
      number: item.number,
      name: item.name,
      positionName: item.positionName,
      formId: item.formId
    })
  })
  formData.value.planPositionList = PositionData.value
  const submitData = {
    stationId: formData.value.stationId,
    departmentId: formData.value.departmentId,
    planNumber: formData.value.planNumber,
    planName: formData.value.planName,
    startDate: formData.value.startDate,
    endDate: formData.value.endDate,
    dateFlag: formData.value.dateFlag,
    inAdvance: formData.value.inAdvance,
    minuteTime: formData.value.minuteTime,
    timeLimit: formData.value.timeLimit,
    timeOutType: formData.value.timeOutType,
    workOrderApp: formData.value.workOrderApp,
    patrolOrder: formData.value.patrolOrder,
    planPositionList: newPositionData,
    planRule: {
      rule: formData.value.planRule.rule.join(','),
      cycle1: formData.value.planRule.cycle1,
      cycle2: formData.value.planRule.cycle2,
      executeTime: formData.value.planRule.executeTime,
      cycleTxt: cycleTxt
    },
    remindJson: {
      noticeType: formData.value.remindJson.noticeType.join(','),
      noticeTime: formData.value.remindJson.noticeTime,
      noticeTimeUnit: formData.value.remindJson.noticeTimeUnit
    }
  }
  submitData.planRule = JSON.stringify(submitData.planRule)
  submitData.remindJson = JSON.stringify(submitData.remindJson)
  submitData.application = application.value
  if (formData.value.id) {
    submitData.id = formData.value.id
    patrolPlanEquipmentApi.update(submitData).then((res) => {
      message.success('编辑成功')
      dialogVisible.value = false
      emit('success')
      resetForm()
    })
  } else {
    patrolPlanEquipmentApi.create(submitData).then((res) => {
      message.success('操作成功')
      dialogVisible.value = false
      emit('success')
      resetForm()
    })
  }
}
////////预览表单
const phone_ref = ref()
const viewphone = (id) => {
  formApi.get(id).then((res) => {
    viewphoneVisible.value = true
    setTimeout(() => {
      if (phone_ref.value) {
        const data = JSON.parse(res.content)
        console.log(phone_ref.value)
        data.forEach((item: any) => {
          console.log(item)
          phone_ref.value.pushList(item)
        })
      }
    }, 200)
  })
}
const viewphoneVisible = ref(false)
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
onMounted(async () => {
  await getapplication()
  GetstationOptions()
  GetdepartmentOptions()
})
//重置表单
const resetForm = () => {
  formData.value = {
    stationParentId: '0',
    departmentParentId: '0',
    planNumber: '',
    planName: '',
    stationId: '',
    departmentId: '',
    startDate: '',
    endDate: '',
    dateFlag: 0,
    inAdvance: '',
    minuteTime: 1,
    timeLimit: '',
    timeOutType: '',
    workOrderApp: '',
    planRule: {
      cycle1: '1',
      cycle2: '2',
      rule: ['1'], //重复规则,多选
      executeTime: '01:00' //执行时间,单选
    },
    patrolOrder: '1', //巡检顺序
    remindJson: {
      noticeType: [],
      noticeTime: '1',
      noticeTimeUnit: '1'
    }
  }
  PositionData.value = []
  activeNum.value = 1
}
</script>
<style lang="scss" scoped>
.box {
  .item {
    &::before {
      content: '';
      width: calc(50%);
      position: absolute;
      top: 50%;
      right: -50%;
      transform: translateY(-50%) translateX(-50%);
      height: 1px;
      background-color: #ededed;
    }
  }
}
.active {
  background-color: #409eff;
  color: #fff;
  border-color: #409eff;
}
</style>
