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
        <el-form-item label="最多上传数量">
          <el-input-number
            v-model="Settingitem.remark.setting.maxCount"
            controls-position="right"
            @change="changeItem"
          />
        </el-form-item>
        <el-form-item label="防作弊拍照">
          <template #label>
            <div class="flex items-center gap-10px">
              <span>防作弊拍照</span>
              <el-tooltip
                effect="dark"
                content="开启后，用户提交时会拍照，防止作弊"
                placement="top"
              >
                <Icon icon="fa:question-circle-o" color="#999" />
              </el-tooltip>
            </div>
          </template>
          <el-switch
            v-model="Settingitem.remark.setting.takePhotos"
            inline-prompt
            :active-text="'是'"
            :inactive-text="'否'"
            :active-value="true"
            :inactive-value="false"
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
