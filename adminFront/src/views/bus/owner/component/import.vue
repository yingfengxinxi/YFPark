<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <el-dialog v-model="visible" width="640px" @clone="clone" @closed="closed">
      <template #header>
        <div class="header_title">
          {{ props.title }}
        </div>
      </template>
      <el-upload
        class="upload-demo"
        action
        :limit="1"
        ref="uploadFile"
        drag
        accept=".xls,.xlsx"
        :multiple="true"
        :auto-upload="false"
        :on-change="onChanage"
        :on-remove="onRemove"
      >
        <img src="./image/upload_img.png" class="img" alt="" />
        <div> 点击或将文件拖拽到这里上传 </div>
        <div> 支持扩展名:.xls,.xlsx </div>
      </el-upload>
      <!-- 水电表模块导入excel文件 -->
      <el-form v-if="props.updateSupport">
        <el-form-item label="是否自动生成资产">
          <template #label>
            <span
              >是否自动生成资产
              <Icon
                icon="fa:question-circle-o"
                color="#999999"
                class="transform-translate-y-[1px]"
                :size="14"
            /></span>
          </template>
          <el-switch
            v-model="updateSupportValue"
            inline-prompt
            active-text="是"
            inactive-text="否"
            :active-value="true"
            :inactive-value="false"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="footer_box">
          <el-button @click="emit('downLoadmb')">模板下载 </el-button>
          <div class="dialog-footer">
            <el-button @click="clone">取消</el-button>
            <el-button type="primary" @click="import_submit"> 确认 </el-button>
          </div>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    title: string
    show: boolean
    updateSupport: boolean
  }>(),
  {
    title: '导入文件',
    updateSupport: false
  }
)
const updateSupportValue = ref(false)
const emit = defineEmits(['update:show', 'downLoadmb', 'change', 'submit'])
const header = ref({
  'Content-Type': 'multipart/form-data'
})
const visible = ref(props.show)
const fileList = ref([])
const onChanage = (res) => {
  fileList.value = res
  emit('change', res)
}
const clone = () => {
  emit('update:show', false)
}
const closed = () => {
  emit('update:show', false)
}
const uploadFile = ref(null)
const import_submit = () => {
  if (fileList.value.length === 0) {
    ElMessage.error('请上传文件')
    return
  }
  if (props.updateSupport) {
    emit('submit', fileList.value, updateSupportValue.value)
  } else {
    emit('submit', fileList.value)
  }
  setTimeout(() => {
    uploadFile.value.clearFiles()
  }, 1000)
}
const onRemove = (file, fileList) => {
  fileList.value = fileList
}
watch(
  () => props.show,
  (val) => {
    visible.value = val
  }
)
</script>

<style>
.header_title {
  font-size: 16px;
}
.img {
  margin-bottom: 20px;
  width: 30px;
  height: 30px;
}
.footer_box {
  display: flex;
  justify-content: space-between;
}
</style>
