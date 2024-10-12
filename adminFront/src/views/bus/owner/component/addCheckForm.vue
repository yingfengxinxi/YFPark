<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <!-- 表单弹窗：添加/修改 -->
    <el-drawer
      v-model="drawer_show"
      append-to-body
      :with-header="true"
      size="500px"
      title="添加收款账单"
      @closed="closed"
    >
      <el-form
        labelWidth="100%"
        label-position="top"
        ref="formRef"
        :rules="formRules"
        :model="form"
        v-loading="formLoading"
      >
        <el-row :gutter="21">
          <el-col :span="24" v-if="ownerInfo.roomOwnerListVOList">
            <el-form-item label="绑定房间" prop="contractId">
              <el-select v-model="RoomId" placeholder="">
                <el-option
                  v-for="item in ownerInfo.roomOwnerListVOList"
                  @change="handleContractChange(item.contractId)"
                  :key="item.contractId"
                  :label="item.parkName + '/' + item.buildName + '/' + item.roomName"
                  :value="item.roomId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 基本信息填写----------------------------------------------------------- -->
          <el-col :span="12">
            <el-form-item label="付款方">
              <el-input v-model="ownerInfo.name" disabled placeholder="请输入付款方" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="币种">
              <el-input v-model="Currency" disabled placeholder="请输入付款方" />
              <!-- <el-select v-model="form.industryId">
                <el-option
                  v-for="item in industry_option"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select> -->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="费用类型" prop="feeType">
              <el-cascader
                v-model="form.feeType"
                :options="costTypeChildrenList"
                :show-all-levels="false"
                :props="{ emitPath: false }"
              />
              <!-- <el-select v-model="form.feeType">
                <el-option
                  v-for="item in costTypeChildrenList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select> -->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计费周期" prop="payStartDate">
              <el-date-picker
                v-model="termValidity"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                @change="handleDateChange"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应收(不含税)" prop="receivable">
              <el-input-number
                v-model="form.receivable"
                :precision="2"
                :step="1"
                :min="0"
                controls-position="right"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应收日期" prop="payDate">
              <el-date-picker
                v-model="form.payDate"
                type="date"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                placeholder="请选择日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="税率">
              <el-input-number
                v-model="form.taxRate"
                :precision="2"
                :step="1"
                controls-position="right"
                class="flex-1"
                :min="0"
                :max="100"
                placeholder="请输入税率"
                @blur="taxAmountChange"
              />
              <span class="m-l-10px c-#000000a6 font-size-14px">%</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="税额">
              <el-input-number
                v-model="form.taxAmount"
                :precision="2"
                :step="1"
                controls-position="right"
                class="flex-1"
                :min="0"
                placeholder="请输入税额"
              />
              <span class="m-l-10px c-#000000a6 font-size-14px">元</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="滞纳金起算天数">
              <el-input-number
                v-model="form.startingLateFeeDay"
                :step="1"
                controls-position="right"
                class="flex-1"
                :min="0"
                placeholder="请输入滞纳金起算天数"
              />
              <span class="m-l-10px c-#000000a6 font-size-14px">天</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="滞纳金比例">
              <el-input-number
                v-model="form.lateFeeRatio"
                placeholder="请输入"
                :precision="2"
                :step="1"
                :min="0"
                controls-position="right"
                class="flex-1"
              />
              <span class="m-l-10px c-#000000a6 font-size-14px">%/天</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="滞纳金上限">
              <el-input-number
                v-model="form.upperLimitLateFee"
                :precision="2"
                :step="1"
                controls-position="right"
                class="flex-1"
                :min="0"
                placeholder="请输入"
              />
              <span class="m-l-10px c-#000000a6 font-size-14px">%</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="账单备注" prop="">
              <el-input v-model="form.remark" :rows="3" type="textarea" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="closed">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </div>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
defineOptions({ name: 'AddCheckFom' })
const message = useMessage() // 消息弹窗
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { contractOrderApi } from '@/api/bus/contractOrderBill'
let drawer_show = ref(false)
let drawer_type = ref()

const RoomId = ref()

const form = ref({
  feeType: undefined,
  receivable: undefined,
  payStartDate: undefined,
  payEndDate: undefined,
  contractId: undefined,
  payDate: undefined,
  taxRate: undefined,
  taxAmount: undefined,
  remark: undefined,
  startingLateFeeDay: undefined,
  lateFeeRatio: undefined,
  upperLimitLateFee: undefined
})
const formRules = reactive({
  feeType: [{ required: true, message: '费用类型不能为空', trigger: 'blur' }],
  payStartDate: [{ required: true, message: '计费周期不能为空', trigger: 'blur' }],
  receivable: [{ required: true, message: '应收金额不能为空', trigger: 'blur' }],
  payDate: [{ required: true, message: '应收日期不能为空', trigger: 'blur' }]
})
const ownerInfo = ref({})
const Currency = ref('人民币')
const termValidity = ref([])
/** 打开抽屉 */
const open = async (Info?: any) => {
  console.log(Info)
  getCostTypeChildrenList()
  ownerInfo.value = Info
  if (Info.roomOwnerListVOList) {
    ownerInfo.value.name = Info.ownerName
    form.value.contractId = Info.roomOwnerListVOList[0].contractId
    RoomId.value = Info.roomOwnerListVOList[0].roomId
  }
  resetForm()
  drawer_show.value = true
  form.value.contractId = Info.contractId
}

const handleContractChange = (val) => {
  console.log(val, 'val')
  form.value.contractId = val
}

const taxAmountChange = (val) => {
  console.log(val, form.value.taxRate)
  if (Number(form.value.taxRate) == 0) {
    form.value.taxAmount = 0
  }
}
const handleDateChange = (value) => {
  console.log(form.value, 'value', value)
  termValidity.value = value
  form.value.payStartDate = value[0]
  form.value.payEndDate = value[1]
  console.log(form.value, 'value', value)
}
const closed = async () => {
  emit('update:show', false)
}
const emit = defineEmits(['update:show', 'success'])
const formRef = ref()
const formLoading = ref(false)
const submit = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    await formRef.value.validate((valid) => {
      if (valid) {
        const data = JSON.parse(JSON.stringify(form.value))
        data.businessInfo = JSON.stringify(data.businessInfo)
        data.bullType = 1
        contractOrderApi.contractOrderBillCreate(data).then((res) => {
          console.log(res, 'resss')
          drawer_show.value = false
          formLoading.value = false
          message.success('添加成功')
          setTimeout(() => {
            emit('success')
          }, 1000)
        })
      }
    })
  } finally {
    formLoading.value = false
  }
}

const costTypeChildrenList = ref([])
/** 费用类型 */
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}
onMounted(() => {})

/** 重置表单 */
const resetForm = () => {
  form.value = {
    feeType: undefined,
    receivable: undefined,
    payStartDate: undefined,
    payEndDate: undefined,
    contractId: undefined,
    payDate: undefined,
    taxRate: undefined,
    taxAmount: undefined,
    remark: undefined,
    startingLateFeeDay: undefined,
    lateFeeRatio: undefined,
    upperLimitLateFee: undefined
  }
  formRef.value?.resetFields()
  termValidity.value = []
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped>
.villageMenu .el-menu-item:hover,
.villageMenu .el-menu-item:focus {
  background: none;
}
.uploader_BOX {
  background: #f7f7f7;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 110px;
  height: 110px;
  display: flex;
  justify-content: center;
  align-items: center;
  .img {
    width: 100%;
    height: 100%;
  }
  .plus {
    width: 40px;
    height: 40px;
  }
}
.drawer_footer {
  display: flex;
  justify-content: flex-end;
  position: fixed;
  bottom: 40px;
  right: 40px;
}
.drawer {
  max-height: 75vh;
  overflow-y: auto;
  overflow-x: hidden;

  .drawer_header-title {
    margin-bottom: 20px;
    font-size: 16px;
    font-weight: 600;
    color: #4a4a4a;
  }
}
</style>
<style>
.el-drawer__header {
  margin-bottom: 20px;
}
</style>
