<template>
  <el-drawer :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-form-item
        label="抢单上限数"
        prop="name"
        :rules="[{ required: true, message: '请输入抢单上限数', trigger: 'blur' }]"
        v-if="formData.isDefault == 0"
      >
        <template #label
          >抢单上限数
          <el-tooltip class="item" effect="dark" content="输入0默认为无上限" placement="top">
            <Icon icon="ep:question-filled" class="transform-translate-y-1px ml-2px" :size="12" />
          </el-tooltip>
        </template>
        <el-input v-model="formData.snatchLimit" placeholder="请输入抢单上限数" />
      </el-form-item>
      <el-form-item
        label="规则名称"
        prop="name"
        :rules="[{ required: true, message: '请输入规则名称', trigger: 'blur' }]"
        v-if="formData.isDefault != 0"
      >
        <el-input v-model="formData.name" placeholder="请输入规则名称" />
      </el-form-item>
      <el-form-item
        label="抢单前置时长"
        prop="preposeTime"
        :rules="[{ required: true, message: '请输入抢单前置时长', trigger: 'blur' }]"
        v-if="formData.isDefault != 0"
      >
        <el-input type="number" :min="0" v-model="formData.preposeTime">
          <template #append>分钟</template>
        </el-input>
      </el-form-item>
      <el-form-item
        label="抢单限制时长"
        prop="robTime"
        :rules="[{ required: true, message: '请输入抢单限制时长', trigger: 'blur' }]"
        v-if="formData.isDefault != 0"
      >
        <el-input type="number" :min="0" v-model="formData.robTime">
          <template #append>分钟</template>
        </el-input>
      </el-form-item>
      <el-form-item
        label="可退款时长"
        prop="refundTime"
        :rules="[{ required: true, message: '请输入可退款时长', trigger: 'blur' }]"
      >
        <div class="flex justify-center gap-10px w-100% items-center text-14px">
          <span class="whitespace-nowrap">工单办结后,超过</span>
          <el-input type="number" :min="0" v-model="formData.refundTime">
            <template #append>天</template>
          </el-input>
          <span class="whitespace-nowrap">不支持退款</span>
        </div>
      </el-form-item>
      <el-form-item
        label="取消订单时长"
        prop="cancelTime"
        :rules="[{ required: true, message: '请输入取消订单时长', trigger: 'blur' }]"
      >
        <div class="flex justify-center gap-10px w-100% items-center text-14px">
          <span class="whitespace-nowrap">工单待支付状态下,超过</span>
          <el-input type="number" :min="0" v-model="formData.cancelTime">
            <template #append>分钟</template>
          </el-input>
          <span class="whitespace-nowrap">自动取消工单</span>
        </div>
      </el-form-item>
      <el-form-item
        label="重新打开时长"
        prop="restartTime"
        :rules="[{ required: true, message: '请输入重新打开时长', trigger: 'blur' }]"
      >
        <div class="flex justify-center gap-10px w-100% items-center text-13px">
          <span class="whitespace-nowrap">工单已办结,已回复状态下,超过</span>
          <el-input type="number" :min="0" v-model="formData.restartTime">
            <template #append>天</template>
          </el-input>
          <span class="whitespace-nowrap">不可重新打开</span>
        </div>
      </el-form-item>
      <el-form-item
        label="应用楼宇"
        prop="buildBind"
        v-if="formData.isDefault != 0 || formType == 'create'"
      >
        <el-tree
          ref="treeRef"
          class="w-100%"
          :data="treedata"
          show-checkbox
          node-key="id"
          v-loading="treeLoading"
          default-expand-all
          :props="defaultProps"
          @change="getCheckedKeys"
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
  </el-drawer>
</template>
<script setup lang="ts">
import { WorkOrderRuleApi, VO } from '@/api/bus/Category/WorkOrderRule/index'
import { BuildApi } from '@/api/bus/village'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()

/** 工单规则设置 表单 */
defineOptions({ name: 'Form' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  application: 'WORK_ORDER_REPAIR',
  name: undefined,
  preposeTime: undefined,
  robTime: undefined,
  refundTime: undefined,
  cancelTime: undefined,
  restartTime: undefined,
  buildBind: undefined,
  isDefault: 0,
  snatchLimit: 0
})

const formRef = ref() // 表单 Ref
const treeRef = ref() // 树形 Ref
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
      formData.value = await WorkOrderRuleApi.get(id)
      formData.value.buildBind = formData.value.buildBind.split(',')
      setTimeout(() => {
        if (formData.value.buildBind.length > 0) {
          treeRef.value.setCheckedKeys(formData.value.buildBind)
        } else {
          treeRef.value.setCheckedKeys([])
        }
      }, 500)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if (treeRef.value.getCheckedKeys(true)) {
    let buildData = treeRef.value.getCheckedKeys(true).filter(function (num) {
      return num != undefined
    })
    formData.value.buildBind = buildData.join(',')
  }
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as VO
    if (formType.value === 'create') {
      await WorkOrderRuleApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await WorkOrderRuleApi.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const defaultProps = {
  //自定义label
  label: (data: { name: any }) => {
    return data.name || data.buildName // name为你要显示的名称 可以自定义，就是将name替换label
  },
  children: (data: { name: any }) => {
    return data.buildList // name为你要显示的名称 可以自定义，就是将name替换label
  }
}
const villageTypeValue = computed(() => userStore.getvillageType)
const treedata = ref([])
const getTree = async () => {
  const data = await BuildApi.getVillagePage({ type: villageTypeValue.value })
  const treeList = data.villageList
  treeList.forEach((item) => {
    if (item.buildList) {
      item.buildList.forEach((element) => {
        element.name = element.buildName
        item.id = item.id.toString()
      })
    } else {
      item.buildList = []
    }
  })
  treedata.value = treeList
}
onMounted(() => {
  getTree()
})

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    application: 'WORK_ORDER_REPAIR',
    name: undefined,
    preposeTime: undefined,
    robTime: undefined,
    refundTime: undefined,
    cancelTime: undefined,
    restartTime: undefined,
    buildBind: undefined,
    isDefault: 0,
    snatchLimit: 0
  }
  formRef.value?.resetFields()
}
</script>
