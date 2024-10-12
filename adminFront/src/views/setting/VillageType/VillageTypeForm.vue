<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="140px"
      v-loading="formLoading"
    >
      <el-form-item label="项目类型名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入类型名称" />
      </el-form-item>
      <el-form-item label="菜单权限">
        <el-card class="cardHeight">
          <template #header>
            全选/全不选:
            <el-switch
              v-model="treeNodeAll"
              active-text="是"
              inactive-text="否"
              inline-prompt
              @change="handleCheckedTreeNodeAll"
            />
            全部展开/折叠:
            <el-switch
              v-model="menuExpand"
              active-text="展开"
              inactive-text="折叠"
              inline-prompt
              @change="handleCheckedTreeExpand"
            />
          </template>
          <el-tree
            ref="treeRef"
            :data="menuOptions"
            :props="defaultProps"
            empty-text="加载中，请稍候"
            node-key="id"
            show-checkbox
          />
        </el-card>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { defaultProps, handleTree } from '@/utils/tree'
import * as MenuApi from '@/api/system/menu'
import * as RoleApi from '@/api/system/role'
import { VillageTypeApi, VillageTypeVO } from '@/api/bus/tag/villageTypeList/index'
import * as PermissionApi from '@/api/system/permission'

/** 项目类型 表单 */
defineOptions({ name: 'VillageTypeForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  alias: undefined,
  bgImg: undefined,
  iconImg: undefined,
  menu: undefined,
  filterMenu: undefined,
  roleId: undefined,
  menuIds: []
})
const menuOptions = ref<any[]>([]) // 菜单树形结构
const roleOptions = ref<RoleApi.RoleVO[]>([]) // 角色列表
const menuExpand = ref(false) // 展开/折叠
const treeRef = ref() // 菜单树组件 Ref
const treeNodeAll = ref(false) // 全选/全不选
const formRules = reactive({
  name: [{ required: true, message: '类型名称不能为空', trigger: 'blur' }],
  alias: [{ required: true, message: '类型别名不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 加载 Menu 列表。注意，必须放在前面，不然下面 setChecked 没数据节点
  menuOptions.value = handleTree(await MenuApi.getSimpleMenusList())
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await VillageTypeApi.getVillageType(id) // 获得角色列表
      roleOptions.value = await RoleApi.getSimpleRoleList()
      roleOptions.value.forEach((item) => {
        if (item.name == formData.value.name) {
          formData.value.roleId = item.id
        }
      })
      formData.value.menuIds = await PermissionApi.getRoleMenuList(formData.value.roleId)
      // 设置选中
      formData.value.menuIds.forEach((menuId: number) => {
        treeRef.value.setChecked(menuId, true, false)
      })
      //   formData.value.menuIds = await PermissionApi.getRoleMenuList(row.id)
      // // 设置选中
      // formData.value.menuIds.forEach((menuId: number) => {
      //   treeRef.value.setChecked(menuId, true, false)
      // })
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
    const data = formData.value as unknown as VillageTypeVO
    if (formType.value === 'create') {
      await VillageTypeApi.createVillageType(data)
      const dataRole = {
        roleId: formData.roleId,
        menuIds: [
          ...(treeRef.value.getCheckedKeys(false) as unknown as Array<number>), // 获得当前选中节点
          ...(treeRef.value.getHalfCheckedKeys() as unknown as Array<number>) // 获得半选中的父节点
        ]
      }
      await PermissionApi.assignRoleMenu(dataRole)
      message.success(t('common.createSuccess'))
    } else {
      await VillageTypeApi.updateVillageType(data)
      const dataRole = {
        roleId: formData.value.roleId,
        menuIds: [
          ...(treeRef.value.getCheckedKeys(false) as unknown as Array<number>), // 获得当前选中节点
          ...(treeRef.value.getHalfCheckedKeys() as unknown as Array<number>) // 获得半选中的父节点
        ]
      }
      console.log(dataRole, 'dataRole')
      await PermissionApi.assignRoleMenu(dataRole)
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
  // 重置选项
  treeNodeAll.value = false
  menuExpand.value = false
  formData.value = {
    id: undefined,
    name: undefined,
    alias: undefined,
    bgImg: undefined,
    iconImg: undefined,
    menu: undefined,
    filterMenu: undefined,
    menuIds: []
  }
  formRef.value?.resetFields()
}

/** 全选/全不选 */
const handleCheckedTreeNodeAll = () => {
  treeRef.value.setCheckedNodes(treeNodeAll.value ? menuOptions.value : [])
}

/** 展开/折叠全部 */
const handleCheckedTreeExpand = () => {
  const nodes = treeRef.value?.store.nodesMap
  for (let node in nodes) {
    if (nodes[node].expanded === menuExpand.value) {
      continue
    }
    nodes[node].expanded = menuExpand.value
  }
}
</script>
<style lang="scss" scoped>
.cardHeight {
  width: 100%;
  max-height: 400px;
  overflow-y: scroll;
}
</style>
