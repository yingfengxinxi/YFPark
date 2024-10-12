<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <div class="detail_header_title">合同编号:</div>
      <div class="detail_header_content" @click="copy(detailList.contractNumber)">
        {{ detailList.contractNumber || '--' }}
        <Icon icon="ep:copy-document" color="#2681F3" class="ml-2 cursor-pointer" />
      </div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">经办人:</div>
      <div class="detail_header_content">{{ detailList.operatorName || '--' }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">创建人:</div>
      <div class="detail_header_content">{{ detailList.creator }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">合同租赁数:</div>
      <div class="detail_header_content">{{ detailList.leaseArea || '--' }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">签订日:</div>
      <div class="detail_header_content">{{ detailList.signingDate || '--' }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">合同开始日:</div>
      <div class="detail_header_content">{{ detailList.contractStartTime || '--' }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">合同结束日:</div>
      <div class="detail_header_content">{{ detailList.contractEndTime || '--' }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">退租日:</div>
      <div class="detail_header_content">{{ detailList.leaseRetreatTime || '--' }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">单位保留小数:</div>
      <div class="detail_header_content">{{ detailList.unitPricePoint + '位' || '--' }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">计算精度:</div>
      <div class="detail_header_content">精度计算结果保留两位小数</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">计算顺序:</div>
      <div class="detail_header_content">{{ detailList.calculationOrderLabel || '--' }}</div>
    </el-col>
    <el-col :span="6">
      <div class="detail_header_title">应收取到(四舍五入):</div>
      <div class="detail_header_content">{{ detailList.isReceivableIntegerLabel || '--' }}</div>
    </el-col>
  </el-row>
</template>
<script setup lang="ts">
const message = useMessage() // 消息弹窗
import { useClipboard } from '@vueuse/core'
import { watch } from 'vue'
let detailList = ref({})
const copy = async (text) => {
  if (navigator.clipboard) {
    const { copy, copied, isSupported } = useClipboard({ source: text })
    await copy()
  } else {
    // copyText(text)
    const input = document.createElement('input')
    input.setAttribute('value', text)
    document.body.appendChild(input)
    input.select()
    document.execCommand('copy')
    document.body.removeChild(input)
  }
  message.success('复制成功')
}
let props = defineProps({
  data: {
    data: Object,
    default: ''
  }
})
watch(
  () => props.data,
  (val) => {
    detailList.value = val
  }
)
</script>
<style scoped>
.detail_header_title {
  font-size: 14px;
  color: #999;
  margin: 5px;
  margin-top: 10px;
}
.detail_header_content {
  font-size: 14px;
  color: #333;
  margin: 5px 0 0 5px;
}
</style>
