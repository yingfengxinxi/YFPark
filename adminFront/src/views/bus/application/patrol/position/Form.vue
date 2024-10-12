<template>
  <el-drawer :title="dialogTitle" v-model="dialogVisible" size="50%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-row style="width: 100%" gutter="20">
        <el-col :span="12">
          <el-form-item label="巡检点编码" required>
            <template #label>
              <span>巡检点编码</span>
              <el-button type="primary" text @click="bindAssets">绑定资产</el-button>
            </template>
            <el-input v-model="formData.number" placeholder="已开启自动编码" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="巡检点名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入巡检点名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="选择位置" prop="positionId">
            <el-tree-select
              v-model="formData.positionId"
              :data="positionList"
              :render-after-expand="false"
              :props="{ label: 'name', value: 'id', children: 'children' }"
              placeholder="请选择上级位置"
              clearable
              :check-strictly="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12" class="pos-relative">
          <el-form-item label="巡检内容" prop="formId">
            <div class="w-100% pos-relative">
              <Icon
                class="!pos-absolute z-999 pos-top--13px pos-left-37px"
                icon="ep:view"
                color="#409EFF"
                @click.stop="viewphone"
                v-if="formData.formId != undefined"
              />
              <el-select v-model="formData.formId" placeholder="请选择巡检内容">
                <el-option
                  v-for="item in formList"
                  :key="item.id"
                  :label="item.title"
                  :value="item.id"
                />
              </el-select>
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="NFC卡号" prop="nfcCardId">
            <el-input v-model="formData.nfcCardId" placeholder="请输入nfc卡号ID" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上传图片" prop="images">
            <el-upload
              class="uploader_BOX"
              :on-change="faceChange"
              :show-file-list="false"
              :auto-upload="false"
            >
              <img v-if="formData.images" :src="formData.images" alt="" class="img" />
              <img src="@/views/bus/owner/component/image/plus.png" v-else class="plus" alt="" />
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div v-if="formData.propertyId != undefined">
      <div
        class="pl-10px border-l-3px border-#409EFF border-solid border-0 text-14px font-600 flex justify-between items-center"
        >绑定的资产<span class="text-#f00 font-400 cursor-pointer" @click="unAssetsChange"
          >取消绑定</span
        ></div
      >
      <el-table :data="AssetsData">
        <el-table-column prop="propertyNumber" label="资产编码" width="220" align="center" />
        <el-table-column prop="categoryName" label="资产分类" align="center" />
        <el-table-column prop="name" label="资产名称" align="center" width="100" />
        <el-table-column prop="positionName" label="所在位置" align="center" />
        <el-table-column prop="brand" label="品牌" align="center" />
        <el-table-column prop="modelName" label="型号" align="center" />
        <el-table-column prop="deviceCode" label="设备序列号" align="center" width="200" />
      </el-table>
    </div>

    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </el-drawer>
  <!-- 绑定资产 -->
  <AssetsListTable ref="AssetsListTableRef" @submit="bindAssetsChange" />
  <!-- 预览表单 -->
  <el-dialog
    v-model="viewphoneVisible"
    width="305"
    style="background-color: rgba(0, 0, 0, 0); shadow: none"
  >
    <phone ref="phone_ref" v-if="viewphoneVisible" />
  </el-dialog>
</template>
<script setup lang="ts">
import { Api, VO } from '@/api/bus/patrol/position'
import { OwnerApi } from '@/api/bus/owner'
import { onMounted } from 'vue'
import { LocaltionApi } from '@/api/bus/patrol/location'
import AssetsListTable from '@/views/bus/application/property/assets/component/AssetsListTable.vue'
import { PropertyApi } from '@/api/bus/property/property'
import phone from '@/views/bus/application/patrol/form/phone.vue'
import { formApi } from '@/api/bus/patrol/form/index'
import { set } from 'nprogress'

/** 巡检点位数据 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  number: undefined,
  name: undefined,
  positionId: undefined,
  nfcCardId: undefined,
  images: undefined,
  propertyId: undefined,
  status: 0
})
const formRules = reactive({
  name: [{ required: true, message: '请输入巡检点名称', trigger: 'blur' }],
  positionId: [{ required: true, message: '请选择位置', trigger: 'blur' }],
  formId: [{ required: true, message: '请选择巡检内容', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await Api.get(id)
      if (formData.value.propertyId) {
        AssetsData.value[0] = await PropertyApi.getProperty(formData.value.propertyId)
      }
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as VO
    if (formType.value === 'create') {
      data.application = application.value
      await Api.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await Api.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
//上传logo
const faceChange = (file: any) => {
  const faceimg = new FormData()
  faceimg.append('file', file.raw)
  OwnerApi.uploadFile(faceimg).then((res) => {
    formData.value.images = res.data
  })
}
//获得巡检表单
const formList = ref([])
const getFormList = async () => {
  const data = await Api.getForm({
    application: application.value
  })
  formList.value = data
}
//获取位置列表
const positionList = ref([])
const getPositionList = async () => {
  const data = await LocaltionApi.getPatrolLocationTree()
  positionList.value = data
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    number: undefined,
    name: undefined,
    positionId: undefined,
    nfcCardId: undefined,
    images: undefined,
    propertyId: undefined,
    status: 0
  }
  formRef.value?.resetFields()
}
///绑定资产
const AssetsListTableRef = ref()
const bindAssets = () => {
  AssetsListTableRef.value.open()
}
const AssetsData = ref([])
const bindAssetsChange = (data: any) => {
  AssetsData.value[0] = data
  formData.value.propertyId = data.id
  formData.value.name = data.name
  formData.value.positionId = data.positionId
}
const unAssetsChange = () => {
  AssetsData.value = []
  formData.value.propertyId = undefined
  formData.value.name = undefined
  formData.value.positionId = undefined
}
//预览表单
const phone_ref = ref()
const viewphone = () => {
  formApi.get(formData.value.formId).then((res) => {
    viewphoneVisible.value = true
    setTimeout(() => {
      if (phone_ref.value) {
        const data = JSON.parse(res.content)
        phone_ref.value.pushList(data)
        data.forEach((item: any) => {
          phone_ref.value.pushList(item)
        })
      }
    }, 100)
  })
}
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
const viewphoneVisible = ref(false)
onMounted(async () => {
  await getapplication()
  getFormList()
  getPositionList()
})
</script>
<style scoped lang="scss">
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
.viewphone {
  background: rgba(0, 0, 0, 0.5);
}
::v-deep .el-dialog-wrapper {
  box-shadow: none !important;
}
</style>
