<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-row :gutter="20">
    <el-col :span="4" class="m-b-20px" v-for="(item, index) in props.list" :key="index">
      <el-card
        style="border: 1px solid #f0f0f0 !important"
        shadow="never"
        class="position-relative"
      >
        <div class="!flex flex-col flex-items-center">
          <el-color-picker
            size="large"
            v-model="item.color"
            show-alpha
            :predefine="predefineColors"
          />
          <div class="m-t-10px">{{ item.title }}</div>
        </div>
        <div
          v-if="item.isCustom == 1"
          class="!position-absolute top-5px right-5px cursor-pointer"
          @click="deleteColor(item, index)"
        >
          <Icon icon="ep:circle-close-filled" size="20" color="red" />
        </div>
      </el-card>
    </el-col>
    <el-col :span="4">
      <el-card
        style="border: 1px solid #f0f0f0 !important"
        class="cursor-pointer"
        shadow="never"
        @click="selectColor"
      >
        <div class="!flex flex-col flex-items-center">
          <Icon class="" icon="ep:plus" size="38" color="#666666" />
          <div class="m-t-10px color-#666666">新建</div>
        </div>
      </el-card>
    </el-col>
  </el-row>

  <el-dialog v-model="dialogColor" title="添加自定义状态" width="500">
    <!-- el-row -->

    <el-row :gutter="20" class="flex-items-center">
      <el-col :span="4">限制条件:</el-col>
      <el-col :span="10">
        <el-radio-group v-model="form.type">
          <el-radio-button label="空置" value="1" />
          <el-radio-button label="距到期" value="2" />
        </el-radio-group>
      </el-col>
      <el-col :span="10">
        <el-input-number
          v-model="form.day"
          :min="1"
          controls-position="right"
          @change="handleChange"
        />
      </el-col>
    </el-row>
    <el-row :gutter="20" class="flex-items-center m-t-20px">
      <el-col :span="4">状态颜色:</el-col>
      <el-col :span="20">
        <el-color-picker
          v-model="form.color"
          size="large"
          show-alpha
          :predefine="predefineColors"
        />
        <span class="m-l-20px c-coolGray">{{ color }}</span>
      </el-col>
    </el-row>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogColor = false">取消</el-button>
        <el-button type="primary" @click="addColor()"> 确定 </el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script setup type="ts">
defineOptions({ name: 'ColorStatus' })
const message = useMessage() // 消息弹窗
const predefineColors = ref([
  'rgb(194, 20, 1)',
  'rgb(255, 30, 2)',
  'rgb(255, 193, 42)',
  'rgb(255, 255, 58)',
  'rgb(144, 207, 91)',
  'rgb(0, 175, 87)',
  'rgb(0, 175, 238)',
  'rgb(0, 113, 190)',
  'rgb(0, 33, 95)',
  'rgb(114, 52, 157)'
])
const form = ref({
  type: 1,
  day: undefined,
  color: '#a9dcfd'
})
const dialogColor = ref(false)
const props = defineProps({
  list: {
    type: Array,
    required: true
  }
})

const selectColor = () => {
  resetForm()
  dialogColor.value = true
}

const deleteColor = async (item, index) => {
  list.splice(index, 1)
  emit('success', list)
}


const list = props.list

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const addColor = () => {
  console.log(form.value.day)
  if (!form.value.day) {
    message.warning(`您还未填写限制条件天数!`)
    return false
  }
  console.log(form.value.day,'form.value.day')
  const title = form.value.type == 1 ? `空置${form.value.day}日以上` : `${form.value.day}日内到期`
  const index = list.findIndex((item) =>
    item.title == title
  )
  console.log(index)
  if (index > 0) {
    message.warning(`您已经添加过此房源状态！`)
    return false
  }
  list.push({
    color: form.value.color,
    isCustom: 1,
    limit: form.value.day,
    title: title,
    type: 'custom'
  })
  dialogColor.value = false
  emit('success', list)
}

// 重置
const resetForm = () => {
  form.value = {
    type: 1,
    day: undefined,
    color: '#a9dcfd'
  }
}
</script>
