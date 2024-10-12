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
        <el-form-item label="选项">
          <div class="flex flex-col gap-10px" style="width: 100%">
            <div
              v-for="(item, index) in Settingitem.remark.setting.options"
              :key="index"
              style="width: 100%"
              class="flex justify-between items-center gap-20px"
            >
              <el-input v-model="item.name" @change="changeItem" />
              <Icon icon="ep:delete" @click="delOption(index)" />
            </div>
            <div
              class="flex items-center justify-center border-#999 border border-solid w-100% cursor-pointer"
              @click="pushOption"
            >
              <Icon icon="ep:plus" />
              添加选项
            </div>
          </div>
        </el-form-item>
        <el-form-item label="默认值设置">
          <el-switch
            v-model="Settingitem.remark.setting.setDefault"
            inline-prompt
            :active-text="'是'"
            :inactive-text="'否'"
            :active-value="true"
            :inactive-value="false"
            @change="changeItem"
          />
          <el-select
            v-model="Settingitem.remark.setting.defaultValue"
            placeholder="请选择默认值"
            @change="changeItem"
            v-if="Settingitem.remark.setting.setDefault"
            class="mt-18px"
          >
            <el-option
              v-for="(item, index) in Settingitem.remark.setting.options"
              :key="index"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
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
      unit: '',
      options: [],
      required: false
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
const changeItem = async () => {
  const data = JSON.parse(JSON.stringify(Settingitem.value))
  data.remark = JSON.stringify(data.remark)
  emit('change', data)
}
const pushOption = () => {
  Settingitem.value.remark.setting.options.push({ name: '' })
  changeItem()
}
const delOption = (index: number) => {
  Settingitem.value.remark.setting.options.splice(index, 1)
  changeItem()
}
</script>
