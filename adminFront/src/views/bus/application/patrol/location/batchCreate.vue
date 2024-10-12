<template>
  <div>
    <div class="flex justify-between items-center bg-white p-10px mb-10px rounded">
      <div class="flex items-center gap-10px">
        <Icon icon="ep:arrow-left" @click="emit('clone')" />
        返回位置设置
      </div>
      <div>
        <el-button type="primary" plain @click="addList">添加数据</el-button>
        <el-button type="primary" @click="submit"> 导入 </el-button>
      </div>
    </div>
    <div class="bg-#FFFBE6 p-10px mb-10px border-#FFE58F border border-solid">
      <div class="flex justify-between items-center"
        ><span class="text-12px font-800">填写说明</span>
        <span class="text-14px text-#409EFF select-none" @click="showList = !showList">{{
          showList ? '收起' : '展开'
        }}</span>
      </div>
      <div class="text-14px mt-10px select-none" v-if="showList">
        1、<span class="text-#f00">红色</span> 字体为必填项，黑色字体为选填项
      </div>
      <div class="text-14px mt-10px select-none" v-if="showList">
        2、分类编码&分类名称：必填项且唯一存在
      </div>
      <div class="text-14px mt-10px select-none" v-if="showList">
        3、上级分类：①请确保上级分类名称在系统或表格内已存在
        ②若新建一级分类，此项选择“无上级（一级）”
      </div>
    </div>
    <el-table :data="data">
      <el-table-column label="序号" type="index" align="center" width="80px" />
      <el-table-column label="位置编码" prop="name" align="center">
        <template #header>
          <span class="text-#f00">位置编码</span>
        </template>
        <template #default="{ row }">
          <el-input v-model="row.number" placeholder="请输入位置编码" />
        </template>
      </el-table-column>
      <el-table-column label="位置名称" prop="name" align="center">
        <template #header>
          <span class="text-#f00">位置名称</span>
        </template>
        <template #default="{ row }">
          <el-input v-model="row.name" placeholder="请输入位置名称" />
        </template>
      </el-table-column>
      <el-table-column label="上级位置" prop="name" align="center">
        <template #header>
          <span class="text-#f00">上级位置</span>
        </template>
        <template #default="{ row }">
          <el-tree-select
            v-model="row.parentId"
            :data="TreeData"
            :render-after-expand="false"
            :props="{ label: 'name', value: 'id', children: 'children' }"
            placeholder="请选择上级位置"
            clearable
            :check-strictly="true"
          />
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" align="center">
        <template #default="{ row }">
          <el-input v-model="row.remark" placeholder="请输入备注" />
        </template>
      </el-table-column>
      <el-table-column label="操作" type="index" align="center" width="120px">
        <template #default="scope">
          <el-button size="small" text type="danger" @click="handleDelete(scope.$index, scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { LocaltionApi, VO } from '@/api/bus/patrol/location'
import { setIndex } from '../../../../../components/Table/src/helper'
const message = useMessage() // 消息弹窗
const emit = defineEmits(['clone'])
const data = ref([
  {
    number: '',
    name: '',
    parentId: undefined,
    remark: ''
  }
])
const submit = () => {
  if (data.value.length === 0) {
    message.error('请添加数据')
    return
  }
  let submitValue = true
  data.value.forEach((item) => {
    if (item.number == '' || item.name == '' || item.parentId == undefined) {
      console.log(item.number, item.name, item.parentId)
      submitValue = false
      message.error('请填写完整数据')
    }
  })
  if (submitValue) {
    LocaltionApi.batchCreate(data.value).then((res) => {
      console.log(res)
      message.success('导入成功')
      emit('clone')
    })
  }
}
const addList = () => {
  data.value.push({
    number: '',
    name: '',
    parentId: undefined,
    remark: ''
  })
}
const TreeData = ref([])
const getTreeData = async () => {
  TreeData.value = [
    {
      id: 0,
      name: '无上级位置'
    }
  ]
  const data = await LocaltionApi.getTree()
  TreeData.value.push(...data)
}
const handleDelete = (index: number, row: VO) => {
  // console.log(index, row)
  data.value.splice(index, 1)
}
const showList = ref(false)
onMounted(() => {
  getTreeData()
})
</script>
