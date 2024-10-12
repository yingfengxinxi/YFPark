<template>
  <el-drawer v-model="drawer" :title="detail?.id ? '编辑费用类型' : '新增费用类型'">
    <!-- 费用名称 -->
    <el-form label-position="top" :model="form" ref="form_ref">
      <el-form-item
        label="费用类型名称"
        :rules="[{ required: true, message: '请输入费用类型名称', trigger: 'blur' }]"
        prop="name"
      >
        <el-input placeholder="请输入费用类型名称" v-model="form.name" />
      </el-form-item>
      <!-- 费用分类 -->
      <el-form-item
        label="费用分类"
        :rules="[{ required: true, message: '请选择费用分类', trigger: 'blur' }]"
        prop="categoryId"
      >
        <el-select placeholder="请输入费用类型名称" v-model="form.categoryId">
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="排序"
        :rules="[{ required: true, message: '请输入排序', trigger: 'blur' }]"
        prop="sort"
      >
        <el-input v-model="form.sort" placeholder="请输入排序" type="number" />
      </el-form-item>
      <el-form-item>
        <template #label>
          <div class="flex items-center gap-1"
            >特别重要

            <el-tooltip
              class="box-item"
              effect="dark"
              content="特别重要的费用类型，若欠费将会影响业务的使用。例如门锁、人脸门禁等将会失效。"
              placement="top"
              v-model="form.isImportant"
            >
              <Icon icon="ep:info-filled" />
            </el-tooltip>
          </div>
        </template>
        <el-select placeholder="是否特别重要" v-model="form.isImportant">
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item>

      <el-form-item label="状态">
        <el-switch v-model="form.status" active-value="1" inactive-value="2" />
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
const form = ref({
  name: '',
  categoryId: '',
  isImportant: 0,
  sort: ''
})
const detail = ref({})
function open(id: number | string) {
  drawer.value = true
  form.value = {
    name: '',
    categoryId: '',
    isImportant: '',
    sort: ''
  }
  if (id) {
    Api.getCostNameDetail(id).then((res: any) => {
      form.value = res
    })
  }
}
const isBond = ref(0)
defineExpose({ open })
const form_ref = ref()
const submit = () => {
  form_ref.value.validate((valid, fields) => {
    if (valid) {
      if (form.value.id) {
        Api.updateCostName(form.value).then((res: any) => {
          if (res) {
            drawer.value = false
            ElMessage.success('操作成功')
            emit('success')
          }
        })
      } else {
        Api.createCostName(form.value).then((res: any) => {
          if (res) {
            drawer.value = false
            emit('success')
            ElMessage.success('操作成功')
          }
        })
      }
    }
  })
}
//获得费用分类下拉
const categoryList = ref([])
Api.getCostTypeList().then((res: any) => {
  categoryList.value = res
})
</script>
