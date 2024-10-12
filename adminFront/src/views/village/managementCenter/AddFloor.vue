<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 新建楼层 -->
  <el-drawer
    v-model="editGardenShow"
    :append-to-body="true"
    direction="rtl"
    size="400px"
    :title="title"
  >
    <template #default>
      <el-form
        ref="formRef"
        :model="formData"
        label-position="top"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
      >
        <el-form-item prop="layerNumber">
          <template #label>
            楼层数字编号
            <el-tooltip
              content="用于楼层列表进行排序,以及未来智能设备等第三方对接使用。地下室使用负数表示"
              placement="top"
            >
              <Icon class="" icon="fa:question-circle-o" />
            </el-tooltip>
          </template>
          <el-input v-model="formData.layerNumber" placeholder="请输入楼层数字编号" />
        </el-form-item>
        <el-form-item label="楼层名称" prop="layerName">
          <el-input v-model="formData.layerName" placeholder="请输入楼层名称"
        /></el-form-item>
        <el-form-item>
          <template #label>
            楼层3D可视化模型
            <el-tooltip
              content="仅支持FBX格式文件<br />点击去编辑按钮进图编辑3D模型,点击其他区域可以重新上传替换文件"
              placement="top"
            >
              <Icon class="" icon="fa:question-circle-o" />
            </el-tooltip>
            <UploadFile
              v-model="formData.threeDimensionalFile"
              :file-type="['fbx']"
              :is-show-tip="false"
            />

            <el-text class="w-full" size="small" type="info"> 请上传fbx格式的3D可视化模型 </el-text>
          </template>
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="cancelClick">取消</el-button>
        <el-button type="primary" @click="confirmClick">保存</el-button>
      </div>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
defineOptions({ name: 'AddFloor' })
const message = useMessage() // 消息弹窗
import { FloorApi } from '@/api/bus/village/floor'
const { t } = useI18n() // 国际化
const editGardenShow = ref(false)
const title = ref('添加楼层')
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用

const formData = ref({
  id: undefined,
  layerNumber: undefined,
  layerName: undefined,
  status: 1
})

const formRules = reactive({
  layerNumber: [{ required: true, message: '楼层数字编号不能为空', trigger: 'blur' }],
  layerName: [{ required: true, message: '楼层名称不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const villageId = ref()
const buildId = ref()
function cancelClick() {
  editGardenShow.value = false
}
/** 打开抽屉 */
const open = async (status: string, village?: number, id?: number, form?: any) => {
  console.log(status, 'status', id)
  formType.value = status
  editGardenShow.value = true
  villageId.value = village
  buildId.value = id
  resetForm()
  if (status == 'create') {
    title.value = '添加楼层'
  } else {
    title.value = '编辑楼层'
    formData.value = form
  }
}

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const confirmClick = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(formData.value))
    data.villageId = villageId.value
    data.buildId = buildId.value
    if (formType.value === 'create') {
      await FloorApi.createlayer(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    } else {
      await FloorApi.updatelayer(data)
      message.success(t('common.updateSuccess'))
      formLoading.value = false
      editGardenShow.value = false
    }
    emit('success', formType.value)
  } finally {
    formLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    layerNumber: undefined,
    layerName: undefined,
    status: 1
  }
  formRef.value?.resetFields()
}
</script>
<style scoped lang="scss">
::v-deep .el-drawer.rtl {
  background: #6aabc5;
}

.investmen-personne {
  width: 100%;
  // border: 1px solid #d9d9d9;
  border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
  padding: 5px 18px 4px 5px;
  cursor: pointer;
  line-height: 1.7;
  position: relative;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color)) inset;
}

.closeCircle {
  position: absolute;
  right: 2px;
  top: 0;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 16px;
  cursor: pointer;
}
</style>
<style>
.el-input-number .el-input__inner {
  text-align: left !important;
}
</style>
