<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ElDrawer title="收支确认配置" v-model="dialogVisible">
    <div
      >当确认完匹配流水
      <el-input-number :min="0" v-model="lmtDay" :step="1" controls-position="right" />
      天后,不支持取消确认
    </div>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import { orgIncomeApi } from '@/api/bus/orgIncome/index'

/** 机构收入 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const lmtDay = ref(1)
/** 打开弹窗 */
const open = async () => {
  const res = await orgIncomeApi.getConfig()
  if (res) {
    lmtDay.value = res.lmtDay
  } else {
    lmtDay.value = 1
  }
  dialogVisible.value = true
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if (!lmtDay.value) {
    message.error('请输入天数')
    return
  }
  // 提交请求
  formLoading.value = true
  try {
    let data = {
      lmtDay: lmtDay.value
    }
    await orgIncomeApi.createConfig(data)
    message.success('保存成功')
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    villageId: undefined,
    contractId: undefined,
    billId: undefined,
    billType: undefined,
    streamMiddleId: undefined,
    contractNumber: undefined,
    buildId: undefined,
    industryId: undefined,
    roomNumber: undefined,
    ownerId: undefined,
    incomeType: undefined,
    isConfirm: undefined,
    confirmTime: undefined,
    confirmUid: undefined,
    unconfirmTime: undefined,
    unconfirmUid: undefined,
    contractCostType: undefined,
    ownerCostType: undefined,
    costType: undefined,
    tradeAmount: undefined,
    amount: undefined,
    taxAmount: undefined,
    tradeTime: undefined,
    isUnrealRoom: undefined,
    unrealRoom: undefined
  }
  formRef.value?.resetFields()
}
</script>
