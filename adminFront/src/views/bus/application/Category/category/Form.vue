<template>
  <el-drawer :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-form-item
        label="大类名称"
        prop="name"
        :rules="[{ required: true, message: '请输入大类名称', trigger: 'blur' }]"
      >
        <el-input v-model="formData.name" placeholder="请输入大类名称" />
      </el-form-item>
      <el-form-item
        label="运作模式"
        prop="type"
        :rules="[{ required: true, message: '请选择运作模式', trigger: 'change' }]"
      >
        <el-select v-model="formData.type" placeholder="请选择运作模式">
          <el-option label="派单+抢单" value="1" />
          <el-option label="派单" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item
        label="完成时限"
        prop="overHour"
        :rules="[{ required: true, message: '请输入完成时限', trigger: 'blur' }]"
      >
        <template #label>
          <span>完成时限</span>
          <el-tooltip
            class="item"
            effect="dark"
            content="优先以上门时间为准，当未填写上门时间则按上报时间计算"
            placement="top"
          >
            <Icon icon="ep:question-filled" class="transform-translate-y-2px ml-6px" />
          </el-tooltip>
        </template>
        <el-input v-model="formData.overHour" type="number" min="0" placeholder="请输入完成时限">
          <template #append>小时</template>
        </el-input>
      </el-form-item>
      <el-form-item
        label="是否允许转单"
        prop="hasChange"
        :rules="[{ required: true, message: '请选择是否允许转单', trigger: 'change' }]"
      >
        <el-switch v-model="formData.hasChange" active-value="1" inactive-value="0" />
      </el-form-item>
      <el-form-item
        label="排序值"
        prop="sort"
        :rules="[{ required: true, message: '请输入排序值', trigger: 'blur' }]"
      >
        <el-input v-model="formData.sort" placeholder="请输入排序值" />
      </el-form-item>
      <el-form-item label="应用楼宇" prop="buildBind">
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
import { CategoryApi, VO } from '@/api/bus/Category/category/index'
import { BuildApi } from '@/api/bus/village'
import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
/** 工单分类配置 表单 */
defineOptions({ name: 'Form' })
const defaultProps = {
  //自定义label
  label: (data: { name: any }) => {
    return data.name || data.buildName // name为你要显示的名称 可以自定义，就是将name替换label
  },
  children: (data: { name: any }) => {
    return data.buildList // name为你要显示的名称 可以自定义，就是将name替换label
  }
}
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const applicationvalue = ref('')
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  name: undefined,
  type: undefined,
  overHour: undefined,
  hasChange: undefined,
  sort: undefined,
  buildBind: undefined
})

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, application: string, id?: number) => {
  dialogVisible.value = true
  formType.value = type
  dialogTitle.value = t('action.' + type)
  applicationvalue.value = application
  if (id) {
    const data = await CategoryApi.get(id)
    data.buildBind = data.buildBind.split(',')
    formData.value = data
    treeRef.value.setCheckedKeys(formData.value.buildBind)
  } else {
    resetForm()
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
    const data = formData.value as unknown as VO
    data.application = applicationvalue.value
    data.buildBind = data.buildBind.join(',')
    if (formType.value === 'create') {
      await CategoryApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await CategoryApi.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
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
    orgId: undefined,
    application: undefined,
    name: undefined,
    type: undefined,
    overHour: undefined,
    hasChange: undefined,
    sort: undefined,
    buildBind: undefined,
    subcatIds: undefined
  }
  formRef.value?.resetFields()
  treeRef.value.setCheckedKeys([])
}
</script>
