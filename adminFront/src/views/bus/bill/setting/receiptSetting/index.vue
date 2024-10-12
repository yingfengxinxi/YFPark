<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="viewHeader bg-#fff p-20px p-b-0px m-b-20px"
    ><div class="c-#000000d9 fw-600 font-size-20px line-height-32px"> 收据设置</div>
    <div class="c-#000000a6 font-size-14px line-height-20px p-t-8px">
      用于将系统中的账单的关键字如账单金额，收款时间等，以模版的形式记录并生成规范化的收据，并能够直接下载打印收据发给租客。
    </div>
    <div class="flex justify-between flex-items-center">
      <el-menu
        class="flex-1"
        :default-active="activeIndex"
        ellipsis
        mode="horizontal"
        popper-append-to-body
        teleported
        @select="handleSelect"
      >
        <el-menu-item index="0">收据编号</el-menu-item>
        <el-menu-item index="1">收款方信息</el-menu-item>
        <el-menu-item index="2">收据模版</el-menu-item>
      </el-menu>
      <el-button v-if="activeIndex == '2'" type="primary" plain @click="downLoad()">
        <Icon icon="ep:download" class="mr-5px" />下载样例模版
      </el-button>
    </div>
  </div>
  <RuleList v-if="activeIndex == '0'" />
  <SellerList v-if="activeIndex == '1'" />
  <TemplateList v-if="activeIndex == '2'" />
</template>

<script setup lang="ts">
/** 租客信息 列表 */
defineOptions({ name: 'ReceiptSetting' })
const activeIndex = ref('0')
import RuleList from './Rule.vue'
import SellerList from './Seller.vue'
import TemplateList from './Template.vue'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { downloadFile } from '@/utils/downloadFile'
import download from '@/utils/download'

const handleSelect = (key: string, keyPath: string[]) => {
  activeIndex.value = key
}
const downLoad = async () => {
  try {
    // 导出的二次确认
    // await message.exportConfirm()
    // 发起导出
    // exportLoading.value = true
    // const data = await TemplateApi.export(queryParams)
    const data = await getStrDictOptions(DICT_TYPE.RECEIPTEXAMPLEURL)
    console.log('data', data[0])
    if (data[0]?.label) {
      downloadFile(data[0].label, '收据模板.docx')
      // download.docx(data[0].label, '收据模板.docx')
    }
  } catch {
  } finally {
    // exportLoading.value = false
  }
}
/** 初始化 **/
onMounted(async () => {})
</script>
<style lang="scss" scoped>
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.viewHeader .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  background: none !important;
}
</style>
