<template>
  <ContentWrapBorder :data_header="'附件信息'">
    <el-table :data="fileData_list">
      <el-table-column align="center" label="文件名" prop="name" />
      <el-table-column align="center" label="操作时间" prop="createTime" />
      <el-table-column align="center" label="文件地址" width="600px" prop="filePath" />
      <el-table-column align="center" label="操作" prop="caozuo">
        <template #default="scope">
          <el-button type="primary" plain size="mini" @click="down_loadFile(scope.row)"
            >下载</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </ContentWrapBorder>
</template>
<script setup lang="ts">
import ContentWrapBorder from '@/views/bus/owner/component/ContentWrap_border.vue'

const fileData_list = ref([])
const props = withDefaults(
  defineProps<{
    data: any
  }>(),
  {
    data: ''
  }
)
watch(
  () => props.data,
  (val) => {
    fileData_list.value = JSON.parse(val.contractAnnex)
  }
)

// 调用以上方法进行下载
const down_loadFile = async (row) => {
  try {
    // 使用 fetch API 请求文件
    const response = await fetch(row.filePath)

    // 检查请求是否成功
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    // 获取文件的 Blob 对象
    const blob = await response.blob()

    // 创建一个 URL 对象
    const url = window.URL.createObjectURL(blob)

    // 创建一个隐藏的下载链接
    const a = document.createElement('a')
    a.href = url
    a.download = row.name // 设置下载文件名
    document.body.appendChild(a) // 将链接添加到文档中
    a.click() // 模拟点击链接
    a.remove() // 点击后移除链接
    window.URL.revokeObjectURL(url) // 释放 URL 对象
  } catch (error) {
    console.error('下载文件时发生错误:', error)
  }
}
</script>
