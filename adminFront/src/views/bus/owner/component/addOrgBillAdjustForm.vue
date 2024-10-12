<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 调整 -->
  <div>
    <el-drawer
      v-model="drawer_show"
      append-to-body
      :with-header="true"
      size="700px"
      :title="drawer_title"
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
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="调整类型" prop="adjustType">
              <el-select v-model="form.adjustType" placeholder="请选择调整类型" clearable>
                <el-option
                  v-for="item in getIntDictOptions(DICT_TYPE.ADJUST_TYPE)"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="调整时间" prop="adjustDate">
              <el-date-picker
                class="!w-100%"
                v-model="form.adjustDate"
                type="date"
                size="default"
                placeholder="选择日期"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="调整方式" prop="adjustMode">
              <el-select
                v-model="form.adjustMode"
                placeholder="请选择调整方式"
                clearable
                @change="changeAdjustMode"
              >
                <el-option
                  v-for="item in getIntDictOptions(DICT_TYPE.ADJUST_MODE)"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="调整比例" prop="adjustProportion" v-if="form.adjustMode == 1">
              <el-input-number
                :min="0"
                v-model="form.adjustProportion"
                :step="1"
                controls-position="right"
                class="m-r-10px"
                @input="changeMoney"
              />
              %
              <span v-show="form.adjustProportion" class="m-l-20px c-#aaaaaa font-size-14px"
                >{{ adjustdPrice }}元</span
              >
            </el-form-item>
            <el-form-item label="调整金额" prop="adjustPrice" v-else>
              <el-input-number
                :min="0"
                v-model="form.adjustPrice"
                :step="1"
                controls-position="right"
                class="!w-100%"
                @input="adjustdPrice = receivableMoney - form.adjustPrice"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input placeholder="请输入备注" v-model="form.remark" type="textarea" rows="4" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="closed">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </template>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
defineOptions({ name: 'AddOrgBillAdjustForm' })
import { orgBillAdjustApi } from '@/api/bus/orgBillAdjust'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { formatDate } from '@/utils/formatTime'
import router from '@/router'
import { create } from 'domain'
const message = useMessage() // 消息弹窗
let drawer_show = ref(false)
const drawer_title = ref('')
const drawer_type = ref(undefined)
const businessId = ref(undefined)
const type = ref(undefined)
const adjustdPrice = ref(0)
const receivableMoney = ref()
const form = ref({
  id: undefined,
  adjustType: undefined,
  adjustMode: undefined,
  billId: undefined,
  adjustProportion: undefined,
  adjustPrice: undefined,
  adjustDate: '',
  remark: undefined
})
const formRules = reactive({
  adjustType: [{ required: true, message: '调整类型不能为空', trigger: 'blur' }],
  adjustMode: [{ required: true, message: '调整方式不能为空', trigger: 'blur' }],
  adjustDate: [{ required: true, message: '调整时间不能为空', trigger: 'blur' }],
  adjustPrice: [{ required: true, message: '调整金额不能为空', trigger: 'blur' }],
  adjustProportion: [{ required: true, message: '调整比例不能为空', trigger: 'blur' }]
})
/** 打开抽屉 */
const open = async (orderId?: number, orderInfo?: object) => {
  resetForm()
  adjustdPrice.value = 0
  drawer_show.value = true
  drawer_title.value = '新建调整'
  form.value.billId = orderId
  receivableMoney.value = orderInfo.receivable
  form.value.adjustDate = formatDate(new Date(), 'YYYY-MM-DD')
  console.log(new Date(), 'new Date()', form.value.adjustDate)
}

const changeAdjustMode = async (val) => {
  if (val == 1) {
    form.value.adjustPrice = undefined
  } else {
    form.value.adjustProportion = undefined
  }
}

const changeMoney = async (val) => {
  adjustdPrice.value = (Number(receivableMoney.value) * Number(val) * 0.01).toFixed(2)
}
const closed = async () => {
  drawer_show.value = false
}
const emit = defineEmits(['success'])
const formRef = ref()
const formLoading = ref(false)
const submit = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(form.value))
    // if (drawer_type.value == 'create') {
    let res = await orgBillAdjustApi.create(data)
    message.success('添加成功')
    drawer_show.value = false
    await orgBillAdjustApi.approved({ id: res })
    // } else {
    //   await orgRemarkApi.update(data)
    //   message.success('修改成功')
    //   drawer_show.value = false
    // }
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  form.value = {
    id: undefined,
    adjustType: undefined,
    adjustMode: undefined,
    billId: undefined,
    adjustProportion: undefined,
    adjustPrice: undefined,
    adjustDate: formatDate(new Date(), 'YYYY-MM-DD'),
    remark: undefined
  }
  formRef.value?.resetFields()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped></style>
<style>
.el-drawer__header {
  margin-bottom: 20px;
}
</style>
