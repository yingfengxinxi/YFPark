<template>
  <el-drawer v-model="drawer" :title="detail?.id ? '编辑分类' : '新增分类'">
    <el-form label-position="top">
      <el-form-item
        label="分类名称"
        :rules="[{ required: true, message: '请输入分类名称', trigger: 'blur' }]"
        prop="name"
      >
        <el-input placeholder="请输入分类名称" v-model="name" />
      </el-form-item>
      <el-form-item label="">
        <template #label>
          设为保证金类型
          <el-tooltip
            class="box-item"
            effect="dark"
            content="设置为保证金类型后,会在合同模板->合同条款中的保证金类型中显示"
            placement="top"
          >
            <Icon icon="ep:info-filled" class="transform-translate-y-2px pl-1" />
          </el-tooltip>
        </template>
        <el-switch v-model="isBond" active-value="1" inactive-value="0" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawer = false">取 消</el-button>
      <el-button type="primary" @click="submit">保 存</el-button>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import { Api, VO } from '@/api/contract/contractList/index'
const emit = defineEmits(['success'])
import { ElMessage, ElMessageBox } from 'element-plus'

const drawer = ref(false)
const name = ref('')
const detail = ref({})
function open(id: number | string) {
  name.value = ''
  isBond.value = 0
  drawer.value = true
  if (id) {
    Api.getCostTypeDetail(id).then((res: any) => {
      name.value = res.name
      isBond.value = res.isBond
      detail.value = res
    })
  }
}
const isBond = ref(0)
defineExpose({ open })
const submit = () => {
  if (!name.value) {
    ElMessage.error('请输入分类名称')
    return
  }
  if (detail.value.id) {
    Api.updateCostType({
      id: detail.value.id,
      name: name.value,
      isBond: isBond.value
    }).then((res: any) => {
      if (res) {
        drawer.value = false
        ElMessage.success('操作成功')

        emit('success')
      }
    })
    return
  } else {
    Api.createCostType({
      name: name.value,
      isBond: isBond.value
    }).then((res: any) => {
      if (res) {
        drawer.value = false
        emit('success')
        ElMessage.success('操作成功')
      }
    })
  }
}
</script>
