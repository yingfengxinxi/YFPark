<template>
  <div>
    <el-card shadow="never">
      <template #header>
        <span class="text-16px">{{ Settingitem?.label }}</span>
      </template>
      <el-form label-position="top">
        <el-form-item label="标题" required>
          <el-input
            v-model="Settingitem.remark.setting.name"
            placeholder="请输入标题"
            @change="changeItem"
          />
        </el-form-item>
        <el-form-item label="提示文字">
          <el-input
            v-model="Settingitem.remark.setting.placeholder"
            placeholder="请输入提示文字"
            @change="changeItem"
          />
        </el-form-item>
        <el-form-item label="是否必填">
          <el-switch
            v-model="Settingitem.remark.setting.required"
            inline-prompt
            :active-text="'是'"
            :inactive-text="'否'"
            :active-value="true"
            :inactive-value="false"
            @change="changeItem"
          />
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script lang="ts" setup>
import { ref, watch } from 'vue'
const Settingitem = ref<any>({
  label: '',
  remark: {
    setting: {
      name: '',
      placeholder: '',
      options: [
        {
          name: ''
        }
      ]
    }
  }
})
function setItem(params: type) {
  if (typeof params.remark == 'string') {
    params.remark = JSON.parse(params.remark)
  }
  Settingitem.value = JSON.parse(JSON.stringify(params))
}
defineExpose({ setItem })
const emit = defineEmits(['change'])
const changeItem = async (index: number, value: string) => {
  const data = JSON.parse(JSON.stringify(Settingitem.value))
  data.remark = JSON.stringify(data.remark)
  emit('change', data)
}
</script>
