<template>
  <ElDrawer
    v-model="dialogVisible"
    :z-index="200"
    size="500px"
    direction="rtl"
    :title="dialogTitle"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      label-position="top"
      v-loading="formLoading"
    >
      <el-form-item label="收款方单位名称" prop="companyName">
        <el-input v-model="formData.companyName" placeholder="请输入收款方单位名称" />
      </el-form-item>
      <el-form-item label="收款人名称" prop="sellerName">
        <el-input v-model="formData.sellerName" placeholder="请输入收款人名称" />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="formData.address" placeholder="请输入地址" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="formData.phone" placeholder="请输入电话" />
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
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import { SellerApi, SellerVO } from '@/api/bus/orgBillReceiptRule'
import { BuildApi } from '@/api/bus/village'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)

/** 收据收款方信息 表单 */
defineOptions({ name: 'SellerForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  companyName: undefined,
  sellerName: undefined,
  address: undefined,
  phone: undefined,
  buildBind: undefined
})
const formRules = reactive({
  companyName: [{ required: true, message: '收款方单位名称不能为空', trigger: 'blur' }],
  sellerName: [{ required: true, message: '收款人名称不能为空', trigger: 'blur' }],
  buildBind: [{ required: true, message: '请选择楼宇', trigger: ['blur', 'change'] }]
})
const formRef = ref() // 表单 Ref
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
  dialogTitle.value = t('action.' + type)
  formType.value = type
  await getTree()
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SellerApi.get(id)
      formData.value.buildBind = formData.value.buildBind.split(',')
      treeRef.value.setCheckedKeys(formData.value.buildBind)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
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
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const treeRef = ref()
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
    const data = formData.value as unknown as SellerVO
    // data.buildBind = JSON.stringify(formData.value.buildBind)
    data.buildBind = formData.value.buildBind.join(',')
    if (formType.value === 'create') {
      await SellerApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await SellerApi.update(data)
      message.success(t('common.updateSuccess'))
    }
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
    companyName: undefined,
    sellerName: undefined,
    address: undefined,
    phone: undefined,
    buildBind: undefined
  }
  treeRef.value?.setCheckedNodes([])
  formRef.value?.resetFields()
}
</script>
