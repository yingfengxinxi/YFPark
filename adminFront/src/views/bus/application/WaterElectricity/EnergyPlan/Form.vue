<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-drawer :title="dialogTitle" v-model="dialogVisible" size="50%">
    <div class="flex box mb-[18px]">
      <div class="w-[33%] text-center flex justify-center gap-[10px] pos-relative">
        <div
          class="border border-[#999] border-solid w-[24px] h-[24px] leading-[24px] rounded-[50%] item"
          :class="activeNum >= 1 ? 'active' : ''"
        >
          1
        </div>
        <div class="leading-[24px] text-[14px]"> 计划设置 </div>
      </div>
      <div class="w-[33%] text-center flex justify-center gap-[10px] pos-relative">
        <div
          class="border border-[#999] border-solid w-[24px] h-[24px] leading-[24px] rounded-[50%] item"
          :class="activeNum >= 2 ? 'active' : ''"
        >
          2
        </div>
        <div class="leading-[24px] text-[14px]"> 抄表楼层 </div>
      </div>
      <div class="w-[33%] text-center flex justify-center gap-[10px]">
        <div
          class="border border-[#999] border-solid w-[24px] h-[24px] leading-[24px] rounded-[50%]"
          :class="activeNum >= 3 ? 'active' : ''"
        >
          3
        </div>
        <div class="leading-[24px] text-[14px]"> 通知设置 </div>
      </div>
    </div>
    <div class="flex pos-relative flex-wrap mb-[10px]">
      <div
        class="bg-[#FDF6EC] text-[#FFBF29] text-[12px] p-[10px] rounded flex items-center gap-[8px] pos-relative w-[100%]"
        v-if="showtag && formType != 'create'"
      >
        <Icon icon="ep:info-filled" />
        编辑计划对已生成的任务无效，会在下次生成任务时生效，且当天不会生成两个任务
      </div>
      <Icon
        icon="ep:close"
        color="#000"
        class="pos-absolute pos-right-0 pos-top-[50%] transform-translate--50% pos-top-0"
        style="position: absolute"
        v-if="showtag && formType != 'create'"
        @click="showtag = false"
      />
    </div>

    <div v-if="activeNum == 1">
      <el-form
        ref="formRef"
        :model="formData"
        label-width="100px"
        v-loading="formLoading"
        label-position="top"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="计划编号" prop="planNumber">
              <el-input
                placeholder="计划编号自动生成"
                disabled
                v-model="formData.planNumber"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item
              label="时间要求"
              prop="timeLimit"
              :rules="[{ required: true, message: '请输入时间', trigger: 'change' }]"
            >
              <el-input
                v-model="formData.timeLimit"
                placeholder="请输入时间"
                type="number"
                :min="0"
              >
                <template #append>小时</template>
              </el-input>
            </el-form-item></el-col
          >
          <el-col :span="12">
            <el-form-item
              label="抄表部门"
              prop="departmentId"
              :rules="[
                {
                  required: true,
                  message: '请选择抄表部门',
                  trigger: 'change'
                }
              ]"
            >
              <el-tree-select
                v-model="formData.departmentId"
                :data="departmentOptions"
                :props="defaultProps"
                placeholder="请选择抄表部门"
                clearable
                :check-strictly="true"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item
              label="抄表岗位"
              prop="stationIds"
              :rules="[{ required: true, message: '请选择抄表岗位', trigger: 'change' }]"
            >
              <el-select
                placeholder="请选择抄表岗位"
                multiple
                clearable
                v-model="formData.stationIds"
              >
                <el-option
                  v-for="item in stationOptions"
                  :key="item.value"
                  :label="item.name"
                  :value="item.id.toString()"
                /> </el-select></el-form-item
          ></el-col>
          <el-col :span="24">
            <el-form-item
              label="抄表范围"
              prop="energyType"
              :rules="[{ required: true, message: '请选择抄表范围', trigger: 'change' }]"
            >
              <el-checkbox-group v-model="formData.energyType">
                <el-checkbox
                  v-for="(item, index) in energyTypeList"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
                /> </el-checkbox-group></el-form-item
          ></el-col>
        </el-row>
        <div class="p-[1px] rounded" style="border: 1px solid #e4e7ed">
          <el-card shadow="never">
            <template #header>
              <div> 计划周期 </div>
            </template>
            <el-form label-position="top">
              <el-form-item label="重复频率">
                <div class="flex gap-[10px]">
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
                    @change="changeCycle2"
                    v-model="formData.planRule.cycle2"
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
                <div class="flex gap-[10px]" v-if="formData.planRule.cycle2 == '2'">
                  <div
                    v-for="(item, index) in getStrDictOptions('RULE_WEEK')"
                    :key="index"
                    class="border border-[#999] border-solid h-[20px] leading-[20px] text-[12px] px-[10px] rounded-[2px] cursor-pointer"
                    :class="
                      formData.planRule.rule.includes(item.value) ? 'bg-[#409eff] text-white' : ''
                    "
                    @click="handleWeekRule(item.value)"
                  >
                    {{ item.label }}
                  </div>
                </div>
                <!-- 月规则 -->
                <div class="flex gap-[10px] flex-wrap" v-if="formData.planRule.cycle2 == '3'">
                  <div
                    v-for="(item, index) in getStrDictOptions('RULE_MONTH')"
                    :key="index"
                    class="border border-[#999] border-solid h-[20px] leading-[20px] text-[12px] px-[10px] rounded-[2px] cursor-pointer"
                    :class="
                      formData.planRule.rule.includes(item.value) ? 'bg-[#409eff] text-white' : ''
                    "
                    @click="handleWeekRule(item.value)"
                  >
                    {{ item.label }}
                  </div>
                </div>
              </el-form-item>
              <el-form-item label="执行时间">
                <div class="flex gap-[10px] flex-wrap">
                  <div
                    v-for="(item, index) in getStrDictOptions('EXECUTION_TIME')"
                    :key="index"
                    class="border border-[#999] border-solid h-[20px] leading-[20px] text-[12px] px-[10px] rounded-[2px] cursor-pointer"
                    @click="formData.planRule.executeTime = item.value"
                    :class="
                      formData.planRule.executeTime == item.value ? 'bg-[#409eff] text-white' : ''
                    "
                  >
                    {{ item.label }}
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </el-card>
        </div>
      </el-form>
    </div>
    <div v-if="activeNum == 2">
      <div class="text-[14px] mb-[10px]">负责位置范围</div>
      <div class="border border-solid border-[#E4E7ED] rounded">
        <el-card shadow="never">
          <template #header>
            <div class="flex justify-between w-[100%] items-center">
              <span>{{ changeTree ? '选择楼层' : '已选楼层' }}</span>
              <el-button type="primary" text @click="changeTree = !changeTree">{{
                changeTree ? '查看已选' : '选择楼层'
              }}</el-button>
            </div>
          </template>
          <div
            class="bg-[#E6F7FF] border-[#91D5FF] border-solid border text-[14px] mb-[10px] p-[10px] rounded-[2px]"
          >
            不能跨楼宇选择
          </div>
          <selectTree ref="selectTreeRef" @submitTree="handleTreeSelect" :changeTree="changeTree" />
        </el-card>
      </div>
    </div>
    <div v-if="activeNum == 3">
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
          <div class="flex gap-[10px] w-[100%]">
            <span class="min-w-[2em]">提前</span>
            <el-input style="width: 100px" type="number" v-model="formData.remindJson.noticeTime" />
            <el-select v-model="formData.remindJson.noticeTimeUnit" style="width: 100px">
              <el-option
                v-for="(item, index) in getStrDictOptions('NOTICE_TIME_UNIT')"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <span class="min-w-[2em]">提醒</span>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <el-button @click="dialogVisible = false" v-if="activeNum == 1">取 消</el-button>
      <el-button v-if="activeNum != 1" @click="LastStep">上一步</el-button>
      <el-button type="primary" v-if="activeNum != 3" @click="NextStep">下一步</el-button>
      <el-button @click="submitForm" type="primary" :disabled="formLoading" v-if="activeNum == 3"
        >确 定</el-button
      >
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import { energyPlanApi, VO } from '@/api/bus/WaterElectricity/EnergyPlan/index.ts'
import { patrolPlanEquipmentApi } from '@/api/bus/patrol/planEquipment'
import { defaultProps, handleTree } from '@/utils/tree'
import { energyType } from '@/api/bus/WaterElectricity/EnergyType/index.ts'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import selectTree from './components/selectTree.vue'
/** 自定义抄表计划 表单 */
defineOptions({ name: 'Form' })
const changeTree = ref(true) // 树的选中状态
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const layerList = ref([]) // 选中的楼层
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  timeLimit: 1,
  departmentId: null,
  departmentParentId: null,
  stationIds: [],
  energyType: [],
  planRule: {
    cycle1: '',
    cycle2: '',
    rule: [1],
    executeTime: '',
    cycleTxt: ''
  },
  villageId: '',
  buildId: '',
  remindJson: {
    noticeTime: 1,
    noticeType: [],
    noticeTimeUnit: '1'
  }
})
const formRef = ref() // 表单 Ref
const showtag = ref(true) // 显示标签
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type) + '抄表计划'
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await energyPlanApi.get(id)
      formData.value.planRule = JSON.parse(formData.value.planRule)
      formData.value.planRule.rule = formData.value.planRule.rule.split(',')
      formData.value.energyType = formData.value.energyType.split(',').map(Number)
      formData.value.remindJson = JSON.parse(formData.value.remindJson)
      formData.value.remindJson.noticeType = formData.value.remindJson.noticeType.split(',')
      formData.value.stationIds = formData.value.stationIds.split(',')
      formData.value.layerIds = formData.value.layerIds.split(',')
      formLoading.value = false
    } catch (error) {
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 提交请求
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(formData.value))
    data.planRule.rule = formData.value.planRule.rule.join(',')
    data.remindJson.noticeType = formData.value.remindJson.noticeType.join(',')
    // 重复频率
    const pinlv = `${
      getStrDictOptions('CYCLE1').find((item) => item.value == formData.value.planRule.cycle1)
        ?.label
    }${
      getStrDictOptions('CYCLE2').find((item) => item.value == formData.value.planRule.cycle2)
        ?.label
    }`
    // 重复规则--周
    const weekDays = getStrDictOptions('RULE_WEEK')
    const weekDaysMap = new Map(weekDays.map((day) => [day.value, day.label]))
    const result = formData.value.planRule.rule.map((num) => `周${weekDaysMap.get(num)}`)
    // 重复规则--月
    const monthDays = getStrDictOptions('RULE_MONTH')
    const monthDaysMap = new Map(monthDays.map((day) => [day.value, day.label]))
    const result2 = formData.value.planRule.rule.map((num) => `${monthDaysMap.get(num)}号`)
    data.planRule.cycleTxt =
      pinlv +
      (formData.value.planRule.cycle2 == '2' ? result.join('、') : result2.join('、')) +
      formData.value.planRule.executeTime
    data.layerIds = layerList.value.map((item: any) => item.id).join(',')
    data.buildId = layerList.value[0].buildId
    data.villageId = layerList.value[0].villageId
    ///////JSON
    data.energyType = data.energyType.join(',')
    data.planRule = JSON.stringify(data.planRule)
    data.remindJson = JSON.stringify(data.remindJson)
    data.stationIds = data.stationIds.join(',')
    data.departmentParentId = ''
    if (formType.value === 'create') {
      await energyPlanApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await energyPlanApi.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
//选中取消重复规则
const handleWeekRule = (val: any) => {
  const index = formData.value.planRule.rule.indexOf(val)
  if (index > -1) {
    formData.value.planRule.rule.splice(index, 1)
  } else {
    formData.value.planRule.rule.push(val)
  }
}
//更改重复频率
const changeCycle2 = (val: any) => {
  formData.value.planRule.rule = ['1']
}
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
//获取抄表范围
const energyTypeList = ref([])
const getenergyTypeList = async () => {
  const data = await energyType.getList()
  energyTypeList.value = data
}

onMounted(() => {
  GetstationOptions()
  GetdepartmentOptions()
  getenergyTypeList()
})
/** 重置表单 */
const resetForm = () => {
  activeNum.value = 1
  showtag.value = true
  formData.value = {
    timeLimit: 1,
    departmentId: null,
    departmentParentId: null,
    stationIds: [],
    energyType: [],
    planRule: {
      cycle1: '1',
      cycle2: '2',
      rule: ['1'],
      executeTime: '01:00' //执行时间,单选
    },
    villageId: '',
    buildId: '',
    layerIds: [],
    remindJson: {
      noticeTime: 1,
      noticeType: ['0'],
      noticeTimeUnit: '1'
    }
  }
  formRef.value?.resetFields()
}
//获取选择的楼层
const handleTreeSelect = (data: any) => {
  layerList.value = data
}
const selectTreeRef = ref()
const activeNum = ref(1)
const NextStep = () => {
  if (activeNum.value == 1) {
    formRef.value.validate((valid: boolean) => {
      if (valid) {
        activeNum.value = 2
        setTimeout(() => {
          selectTreeRef.value.select(formData.value.layerIds)
        }, 500)
      }
    })
  } else if (activeNum.value == 2) {
    if (layerList.value.length == 0) {
      console.log(layerList.value)
      message.error('请选择楼层')
      return
    }
    activeNum.value = 3
  }
}
const LastStep = () => {
  if (activeNum.value == 3) {
    activeNum.value--
    setTimeout(() => {
      selectTreeRef.value.select(formData.value.layerIds)
    }, 500)
  } else {
    activeNum.value--
  }
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
