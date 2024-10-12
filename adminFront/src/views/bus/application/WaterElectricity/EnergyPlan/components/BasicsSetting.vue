<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-drawer title="基础设置" v-model="dialogVisible">
    <el-form :model="form" label-position="top" ref="formRerf">
      <el-form-item
        label="误抄表预警"
        :rules="[{ required: true, message: '请输入误抄表预警值', trigger: 'blur' }]"
        prop="value"
      >
        <el-input v-model="form.value" placeholder="请输入误抄表预警值" type="number">
          <template #append>%</template>
        </el-input>
      </el-form-item>
    </el-form>
    <div class="text-gray-500 text-xs">
      当抄表比较上次抄表的值大于上面的比例，则会突出颜色提醒工作人员，尽量避免抄表错误
    </div>
    <template #footer>
      <div class="flex justify-end">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </div>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import { energyPlanApi, VO } from '@/api/bus/WaterElectricity/EnergyPlan/index.ts'
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false)
const form = ref({
  value: ''
})
function open() {
  dialogVisible.value = true
  energyPlanApi.getwarning().then((res) => {
    form.value.value = res
  })
}
defineExpose({ open })
const formRerf = ref(null)
const handleSubmit = () => {
  formRerf.value.validate((valid) => {
    if (valid) {
      energyPlanApi
        .savewarning({
          value: form.value.value
        })
        .then(() => {
          message.success('保存成功')
          dialogVisible.value = false
        })
    }
  })
}
</script>
