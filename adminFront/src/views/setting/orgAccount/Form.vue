<template>
  <el-drawer v-model="dialogVisible" :title="dialogTitle" :append-to-body="true">
    <el-form
      ref="formRef"
      :model="formData"
      label-position="top"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="条目名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入条目名称" />
      </el-form-item>
      <el-form-item label="收款公司" prop="company">
        <el-input v-model="formData.company" placeholder="请输入收款公司" />
      </el-form-item>
      <el-form-item label="开户银行" prop="bank">
        <el-input v-model="formData.bank" placeholder="请输入开户银行" />
      </el-form-item>
      <el-form-item label="银行账号" prop="bankAccount">
        <el-input v-model="formData.bankAccount" placeholder="请输入银行账号" />
      </el-form-item>
      <el-form-item label="总分类账科目" prop="subject">
        <el-input v-model="formData.subject" placeholder="请输入总分类账科目" />
      </el-form-item>
      <el-form-item label="应用楼宇" prop="build">
        <el-tree-select
          class="w-100%"
          v-model="formData.build"
          :data="buildingDataList"
          placeholder="请选择应用楼宇"
          :render-after-expand="false"
          show-checkbox
          multiple
          node-key="buildId"
          :props="menuProps"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-switch
          v-model="formData.status"
          width="60"
          inline-prompt
          active-value="1"
          inactive-value="0"
          active-text="开启"
          inactive-text="关闭"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :disabled="formLoading" @click="submitForm">保存</el-button>
      </div>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import { Api, VO } from '@/api/bus/tag/orgAccount'
import { BuildApi } from '@/api/bus/village'

/** 收支账户配置 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const buildingDataList = ref([] as any[])
const formData = ref({
  id: undefined,
  name: undefined,
  company: undefined,
  bank: undefined,
  bankAccount: undefined,
  subject: undefined,
  build: undefined,
  status: undefined
})
const formRules = reactive({
  bankAccount: [
    { required: true, pattern: /^(?:[1-9][0-9]*)$/, message: '输入整数', trigger: 'blur' }
  ],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const menuProps = {
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
  getTree()
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await Api.get(id)
      formData.value.build = formData.value.build.split(',')
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 获得楼宇 */
const getTree = async () => {
  try {
    const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
    res.villageRespVOS.forEach((item: any) => {
      item.buildId = item.name
      // item.value = item.id
      if (item.buildList.length) {
        item.buildList.forEach((element: any) => {
          element.buildId = element.id
        })
      }
    })
    buildingDataList.value = res.villageRespVOS
  } finally {
  }
}
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = JSON.parse(JSON.stringify(formData.value as unknown as VO))
    data.build = data.build.join(',')
    if (formType.value === 'create') {
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

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: undefined,
    company: undefined,
    bank: undefined,
    bankAccount: undefined,
    subject: undefined,
    build: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}
</script>
