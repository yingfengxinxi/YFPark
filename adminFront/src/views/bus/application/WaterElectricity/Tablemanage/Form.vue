<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-drawer :title="dialogTitle" v-model="dialogVisible" size="40%">
    <el-form
      ref="formRef"
      :model="formData"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-row :gutter="20">
        <el-col :span="24" v-if="addTypeValue == 'Brain'">
          <div
            class="border-[#2681F3] border-l-[4px] border-solid border-0 pl-[8px] text-[14px] font-semibold mb-[20px]"
          >
            智能参数
          </div>
        </el-col>
        <el-col :span="12" v-if="addTypeValue == 'Brain'">
          <el-form-item label="设备厂商" prop="">
            <el-select placeholder="请选择设备厂商" v-model="formData.deviceId">
              <el-option
                v-for="(item, index) in getStrDictOptions('BAILING_ORG_CONFIG')"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="addTypeValue == 'Brain'">
          <el-form-item label="设备类型" prop="">
            <el-select placeholder="请选择设备类型" v-model="formData.deviceType">
              <el-option
                v-for="(item, index) in getStrDictOptions('BAILING_ORG_CONFIG_DEVICE_TYPE')"
                :key="index"
                :label="item.label"
                :value="item.value"
            /></el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="addTypeValue == 'Brain'">
          <el-form-item
            label="设备序列号"
            prop="deviceSerial"
            :rules="[{ required: true, message: '请输入设备序列号', trigger: 'blur' }]"
          >
            <el-input v-model="formData.deviceSerial" placeholder="请输入设备序列号" />
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="addTypeValue == 'Brain'">
          <el-form-item
            label="付费类型"
            prop="paymentType"
            :rules="[{ required: true, message: '请选择付费类型', trigger: 'blur' }]"
          >
            <el-select placeholder="请选择付费类型" v-model="formData.paymentType">
              <el-option
                v-for="(item, index) in getStrDictOptions('ENERGY_PAYMENT_TYPE')"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <div
            class="border-[#2681F3] border-l-[4px] border-solid border-0 pl-[8px] text-[14px] font-semibold mb-[20px]"
            :class="addTypeValue == 'Brain' ? 'mt-0 ' : 'mt-[20px]'"
          >
            基本信息
          </div>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="表类型"
            prop="type"
            :rules="[{ required: true, message: '请选择表类型', trigger: 'blur' }]"
          >
            <el-select placeholder="请选择表类型" v-model="formData.type" disabled>
              <el-option
                v-for="(item, index) in EnergyTypeList"
                :key="index"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="用途"
            prop="purpose"
            :rules="[{ required: true, message: '请输入用途', trigger: 'blur' }]"
          >
            <el-radio-group v-model="formData.purpose" size="large" @change="changePurpose">
              <el-radio-button
                v-for="(item, index) in getStrDictOptions('PURPODE')"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="表名称"
            prop="name"
            :rules="[{ required: true, message: '请输入表名称', trigger: 'blur' }]"
          >
            <el-input v-model="formData.name" placeholder="请输入表名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="表具编号"
            prop="number"
            :rules="[{ required: true, message: '请输入表名称', trigger: 'blur' }]"
          >
            <el-input v-model="formData.number" placeholder="请输入表名称" />
          </el-form-item>
        </el-col>
        <!-- 总表 -->
        <el-col :span="24" v-if="formData.purpose == '2'">
          <el-form-item
            label="公摊方式"
            prop="publicType"
            :rules="[{ required: true, message: '请选择公摊方式', trigger: 'blur' }]"
          >
            <el-select v-model="formData.publicType" placeholder="请选择公摊方式">
              <el-option
                v-for="item in getStrDictOptions('PUBLIC_TYPE')"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <!-- 公摊表 -->
        <el-col :span="24" v-if="formData.purpose == '3'">
          <el-form-item
            label="公摊方式"
            prop="publicType"
            :rules="[{ required: true, message: '请选择公摊方式', trigger: 'blur' }]"
          >
            <el-select v-model="formData.publicType" placeholder="请选择公摊方式">
              <el-option
                v-for="item in getStrDictOptions('PUBLIC_TYPE1')"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="倍率"
            prop="magnification"
            :rules="[{ required: true, message: '请输入倍率', trigger: 'blur' }]"
          >
            <template #label>
              <span
                >倍率
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="倍率是指按读数收取费用的倍数，一般填写1。"
                  placement="top"
                >
                  <Icon icon="fa:question-circle-o" :size="12" />
                </el-tooltip>
              </span>
            </template>
            <el-input v-model="formData.magnification" placeholder="请输入倍率" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="最大读数"
            prop="maxReading"
            :rules="[{ required: true, message: '请输入最大读数', trigger: 'blur' }]"
          >
            <template #label>
              <span
                >最大读数

                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="表具本身的最大读数，超过了最大读数表具自身会从0开始计算。一般是表显的数字，例如99999.99。涉及到读数归零后的计算，必须准确填写。"
                  placement="top"
                >
                  <Icon icon="fa:question-circle-o" :size="12" />
                </el-tooltip>
              </span>
            </template>
            <el-input v-model="formData.maxReading" placeholder="请输入最大读数">
              <template #append>
                {{ EnergyTypeList.find((item) => item.id === formData.type)?.unit }}
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <div
            class="border-[#2681F3] border-l-[4px] border-solid border-0 pl-[8px] text-[14px] font-semibold mb-[20px] mt-[20px]"
          >
            绑定位置范围
          </div>
        </el-col>
        <el-col :span="24">
          <div class="border-[#E4E7ED] border border-solid mt-[8px] min-h-[300px] rounded">
            <el-card shadow="never">
              <template #header>
                <div class="flex justify-between w-[100%] items-center">
                  <span>{{ changeTree ? '选择楼层' : '已选楼层' }}</span>
                  <el-button type="primary" text @click="changeTree = !changeTree">{{
                    changeTree ? '查看已选' : '选择楼层'
                  }}</el-button>
                </div>
              </template>
              <DeptTree_select
                :notdis="false"
                @changeData="changeData"
                ref="deptTree_selectRef"
                v-if="dialogVisible"
                :change_tree="changeTree"
              />
            </el-card>
          </div>
        </el-col>
        <el-col
          :sapn="24"
          class="mt-[20px]"
          v-if="(formData.purpose == '2' && formData.publicType != '1') || formData.purpose == '3'"
        >
          <el-form-item
            label="关联分表"
            prop="partIds"
            :rules="[{ required: true, message: '请选择关联分表', trigger: 'blur' }]"
          >
            <el-select v-model="formData.partIds" multiple placeholder="请选择关联分表">
              <el-option
                v-for="item in energyList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24" class="mt-[20px]">
          <el-form-item label="备注">
            <el-input
              v-model="formData.remark"
              placeholder="请输入备注"
              type="textarea"
              :rows="4"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import DeptTree_select from '@/views/contract/contractList/component/DeptTree_select.vue'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { energyType } from '@/api/bus/WaterElectricity/EnergyType/index'
import { TablemanageApi } from '@/api/bus/WaterElectricity/Tablemanage/index'
/** 自定义抄表计划 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载状态
const formType = ref('') // 表单的类型
const formData = ref({
  purpose: 1
})
const changePurpose = (val) => {
  formData.value.publicType = ''
}
const formRef = ref() // 表单 Ref
//表类型
const EnergyTypeList = ref([])
const getEnergyTypeList = async () => {
  energyType.getList().then(async (res) => {
    EnergyTypeList.value = res
    await getList()
  })
}
//关联分类
const energyList = ref([])
const getEnergyList = async (data) => {
  TablemanageApi.getenergyList({
    type: data.type,
    villageId: data.villageId,
    buildId: data.buildId
  }).then((res) => {
    energyList.value = res
  })
}
const addTypeValue = ref('') // 新增类型
/** 打开弹窗 */
const open = async (type: string, typeId: string, addType: string, id?: number) => {
  resetForm()

  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  formData.value.type = typeId
  addTypeValue.value = addType
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      // formData.value = await Api.get(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(formData.value))
    data.villageId = roomList.value[0].villageId
    data.buildId = roomList.value[0].buildId
    data.roomIds = roomList.value.map((item) => item.id).join(',')
    data.layerIds = roomList.value.map((item) => item.layerId).join(',')
    data.builds = JSON.stringify(roomList.value)
    if (data.partIds) {
      data.partIds = data.partIds.join(',')
    }
    if (formType.value === 'create') {
      TablemanageApi.createTablemanage(data).then((res) => {
        message.success(t('common.createSuccess'))
      })
    } else {
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const roomList = ref([]) // 选择的楼层
const changeData = (data: any) => {
  roomList.value = data
  const form = {
    type: formData.value.type,
    villageId: data[0].villageId,
    buildId: data[0].buildId
  }
  getEnergyList(form)
}
const changeTree = ref(true) // 是否切换楼层

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    purpose: '1'
  }
  formRef.value?.resetFields()
}
onMounted(() => {
  getEnergyTypeList()
})
</script>
