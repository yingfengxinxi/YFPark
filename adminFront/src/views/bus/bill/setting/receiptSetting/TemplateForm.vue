<template>
  <ElDrawer v-model="dialogVisible" :z-index="200" size="700px" direction="rtl" :title="dialogTitle"
    ><el-tabs v-model="activeIndex" class="demo-tabs">
      <el-tab-pane label="复制关键字" name="0">
        <el-descriptions direction="vertical" :column="1" border>
          <el-descriptions-item label="收据信息">
            <template v-for="(item, index) in ReceiptInfo" :key="index"
              ><el-tooltip :content="item.label" placement="top">
                <el-button plain class="m-r-16px m-b-16px" @click="copy(item.value)">
                  {{ item.value }}
                </el-button>
              </el-tooltip>
            </template>
          </el-descriptions-item>
          <el-descriptions-item label="交收款方">
            <template v-for="(item, index) in SubmitToPayee" :key="index"
              ><el-tooltip :content="item.label" placement="top">
                <el-button plain class="m-r-16px m-b-16px" @click="copy(item.value)">
                  {{ item.value }}
                </el-button></el-tooltip
              >
            </template>
          </el-descriptions-item>
          <el-descriptions-item label="房源信息">
            <template v-for="(item, index) in RoomInfo" :key="index">
              <el-tooltip :content="item.label" placement="top">
                <el-button plain class="m-r-16px m-b-16px" @click="copy(item.value)">
                  {{ item.value }}
                </el-button></el-tooltip
              >
            </template></el-descriptions-item
          >
          <el-descriptions-item label="账单信息">
            <template v-for="(item, index) in BillingInfo" :key="index"
              ><el-tooltip :content="item.label" placement="top">
                <el-button plain class="m-r-16px m-b-16px" @click="copy(item.value)">
                  {{ item.value }}
                </el-button></el-tooltip
              >
            </template>
          </el-descriptions-item></el-descriptions
        >
      </el-tab-pane>
      <el-tab-pane label="上传收据打印模版" name="1">
        <el-form
          ref="formRef"
          :model="formData"
          :rules="formRules"
          label-width="100px"
          label-position="top"
          v-loading="formLoading"
        >
          <el-form-item label="收款模板名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入收据模板名称" />
          </el-form-item>
          <el-form-item label="模板上传地址" prop="templatePath">
            <!-- <el-upload
              class="w-100%"
              action
              :limit="1"
              ref="uploadFile"
              drag
              accept=".docx"
              :multiple="true"
              :auto-upload="false"
              :on-change="onChanage"
            >
              <Icon :icon="'ep:upload-filled'" size="54" color="#52a5ff" />
              <div> 点击或将文件拖拽到这里上传 </div>
              <div class="c-#00000073 font-size-14px"> 文档只支持docx格式 </div>
            </el-upload> -->
            <UploadFile
              v-model="formData.templatePath"
              :file-type="['docx']"
              :is-show-tip="false"
              :limit="1"
            />

            <el-text class="w-full" size="small" type="info"> 文档只支持docx格式 </el-text>
          </el-form-item>
          <el-form-item label="应用楼宇" prop="buildBind">
            <el-tree
              ref="treeRef"
              class="w-100%"
              :data="sourceData"
              show-checkbox
              node-key="buildId"
              v-loading="treeLoading"
              default-expand-all
              :props="defaultProps"
            >
              <template #default="{ node }">
                <span class="slot-t-node">
                  <Icon
                    class="m-r-6px"
                    :icon="
                      node.level == 1
                        ? 'fa:bank'
                        : node.level == 2
                          ? 'fa-solid:building'
                          : node.level == 3
                            ? 'fa:database'
                            : 'fa-solid:door-open'
                    "
                    size="12"
                    color="#666666"
                  />
                  <span>{{ node.label }}</span>
                </span>
              </template>
            </el-tree>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>

    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import { TemplateApi, TemplateVO } from '@/api/bus/orgBillReceiptRule'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { BuildApi } from '@/api/bus/village'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)

import { useClipboard } from '@vueuse/core'
/** 收据模板 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  templatePath: undefined,
  buildBind: undefined
})
const formRules = reactive({})
const formRef = ref() // 表单 Ref

const activeIndex = ref('0')
const ReceiptInfo = ref([] as any[])
const SubmitToPayee = ref([] as any[])
const RoomInfo = ref([] as any[])
const BillingInfo = ref([] as any[])

const sourceData = ref([])
const treeLoading = ref(false)
const defaultProps = {
  //自定义label
  label: (data: { name: any }) => {
    return data.name || data.buildName // name为你要显示的名称 可以自定义，就是将name替换label
  },
  children: (data: { name: any }) => {
    return data.buildList // name为你要显示的名称 可以自定义，就是将name替换label
  }
}
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type) + '收据模版'
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await TemplateApi.get(id)
      formData.value.buildBind = formData.value.buildBind.split(',')
      treeRef.value.setCheckedKeys(formData.value.buildBind)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const treeRef = ref()
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if (treeRef.value.getCheckedKeys(true)) {
    let buildData = treeRef.value.getCheckedKeys(true).filter(function (num) {
      return num != undefined
    })
    formData.value.buildBind = buildData
  }
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as TemplateVO
    data.buildBind = formData.value.buildBind.join(',')
    if (formType.value === 'create') {
      await TemplateApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await TemplateApi.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const fileList = ref([])
const onChanage = (res) => {
  console.log(res, 'ress')
  fileList.value = res
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: undefined,
    templatePath: undefined,
    buildBind: undefined
  }
  treeRef.value?.setCheckedNodes([])
  formRef.value?.resetFields()
}

/** 复制 **/
const copy = async (text: string) => {
  if (navigator.clipboard) {
    const { copy } = useClipboard({ source: text })
    copy()
  } else {
    const input = document.createElement('input')
    input.setAttribute('value', text)
    document.body.appendChild(input)
    input.select()
    document.execCommand('copy')
    document.body.removeChild(input)
  }

  message.success(t('common.copySuccess'))
}
/**收据信息 */
const getReceiptInfo = async () => {
  const data = await getStrDictOptions(DICT_TYPE.RECEIPTINFORMATION)
  ReceiptInfo.value = data
}

const getSubmitToPayee = async () => {
  const data = await getStrDictOptions(DICT_TYPE.PAYMENTRECIPIENT)
  SubmitToPayee.value = data
}

const getRoomInfo = async () => {
  const data = await getStrDictOptions(DICT_TYPE.PROPERTYINFORMATION)
  RoomInfo.value = data
}

const getBillingInfo = async () => {
  const data = await getStrDictOptions(DICT_TYPE.BILLINFORMATION)
  BillingInfo.value = data
}

/** 获得楼宇 */
const getTree = async () => {
  try {
    treeLoading.value = true
    const res = await BuildApi.getVillagePage({
      type: villageTypeValue.value
    })
    res.villageRespVOS.forEach((item: any) => {
      if (item.buildList && item.buildList.length) {
        item.buildList.forEach((element: any) => {
          element.buildId = element.id
        })
      }
    })
    sourceData.value = res.villageRespVOS
  } finally {
    treeLoading.value = false
  }
}
/** 初始化 */
onMounted(async () => {
  await getReceiptInfo()
  await getSubmitToPayee()
  await getReceiptInfo()
  await getRoomInfo()
  await getBillingInfo()
  await getTree()
})
</script>
