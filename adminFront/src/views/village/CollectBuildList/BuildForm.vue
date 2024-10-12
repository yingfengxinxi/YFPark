<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-drawer v-model="dialogVisible" :title="dialogTitle" :append-to-body="true">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      label-position="top"
      v-loading="formLoading"
    >
      <el-form-item label="集合名称" prop="collectionName">
        <el-input v-model="formData.collectionName" placeholder="请输入集合名称" />
      </el-form-item>
      <el-form-item label="项目类型" prop="villageType">
        <template #lable>
          项目类型
          <el-tooltip content="“集合”是同一种项目类型的楼宇的集合" placement="bottom">
            <Icon class="" icon="fa:question-circle-o" />
          </el-tooltip>
        </template>
        <el-select
          v-model="formData.villageType"
          placeholder="请选择项目类型"
          clearable
          @change="changeVillageType"
        >
          <el-option
            v-for="dict in villageTypeList"
            :key="dict.alias"
            :label="dict.name"
            :value="dict.alias"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="选择楼宇" prop="collectionBuild">
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
    </template></el-drawer
  >
</template>
<script setup lang="ts">
import { BuildApi } from '@/api/bus/village'
import { CollectBuildApi, CollectBuildVO } from '@/api/bus/village/CollectBuild'
import { Management } from '@element-plus/icons-vue/dist/types'
import { VillageTypeApi } from '@/api/bus/tag/villageTypeList/index'
import { useUserStore } from '@/store/modules/user'

/** 项目楼栋 表单 */
defineOptions({ name: 'BuildForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const villageTypeList = ref([] as any[])
const treeRef = ref()
const userId = useUserStore().getUser.id // 当前登录的编号
const formData = ref({
  id: undefined,
  collectionName: undefined,
  villageType: undefined,
  collectionBuild: undefined
})
const formRules = reactive({
  collectionName: [{ required: true, message: '集合名称不能为空', trigger: 'blur' }],
  villageType: [{ required: true, message: '项目类型不能为空', trigger: ['blur', 'change'] }],
  collectionBuild: [{ required: true, message: '请选择楼宇', trigger: ['blur', 'change'] }]
})
const formRef = ref() // 表单 Ref
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)

const defaultProps = {
  //自定义label
  label: (data: { name: any }) => {
    return data.name || data.buildName // name为你要显示的名称 可以自定义，就是将name替换label
  },
  children: (data: { name: any }) => {
    return data.buildList // name为你要显示的名称 可以自定义，就是将name替换label
  }
}
const sourceData = ref([])

const treeLoading = ref(false)
/** 获得楼宇 */
const getTree = async () => {
  try {
    treeLoading.value = true
    const res = await BuildApi.getVillagePage({
      type: formData.value.villageType
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
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  formData.value.collectionBuild = villageTypeValue.value
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await CollectBuildApi.getCollectBuild(id)
      formData.value.collectionBuild = JSON.parse(formData.value.collectionBuild)
      treeRef.value.setCheckedKeys(formData.value.collectionBuild)
    } finally {
      formLoading.value = false
    }
  }

  const data = await VillageTypeApi.getVillageTypeNopage()
  villageTypeList.value = data
  formData.value.villageType = data[0].alias
  getTree()
}

const changeVillageType = () => {
  getTree()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if (treeRef.value.getCheckedKeys(true)) {
    let buildData = treeRef.value.getCheckedKeys(true).filter(function (num) {
      return num != undefined
    })
    formData.value.collectionBuild = buildData
  }
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as CollectBuildVO
    // data.collectionBuild =
    data.uid = userId
    data.collectionBuild = JSON.stringify(formData.value.collectionBuild)
    if (formType.value === 'create') {
      await CollectBuildApi.createCollectBuild(data)
      message.success(t('common.createSuccess'))
    } else {
      await CollectBuildApi.updateCollectBuild(data)
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
    collectionName: undefined,
    villageType: undefined,
    collectionBuild: undefined
  }
  treeRef.value?.setCheckedNodes([])
  formRef.value?.resetFields()
}
</script>
