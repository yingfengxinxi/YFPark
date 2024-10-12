<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 列表 -->
  <div>
    <div class="grid grid-cols-3 grid-gap-20px c-#929292">
      <div
        @click="openForm('create')"
        v-hasPermi="['bus:orgBillReceiptTemplate:create']"
        class="b-dashed b-rd-4px border-#f0f0f0 h-230px bg-#fff box-border cursor-pointer flex justify-center items-center font-size-20px"
      >
        <Icon icon="ep:plus" size="20" class="mr-5px" /> 新增模版
      </div>

      <div
        v-loading="loading"
        v-for="(item, index) in list"
        :key="index"
        class="b-rd-4px h-230px bg-#fff box-border flex justify-between flex-col"
      >
        <div class="flex-1 p-24px flex">
          <Icon icon="ep:list" size="48" color="#2681f3" class="mr-5%" />
          <div class="flex-1 overflow-hidden">
            <div
              class="c-#000000d9 font-size-16px m-b-8px whitespace-nowrap overflow-hidden text-ellipsis"
            >
              {{ item.name }}
            </div>
            <div class="c-#00000073 font-size-14px line-height-24px"
              >应用楼宇：{{ item.buildBind }}</div
            >
          </div>
        </div>
        <div
          class="flex text-center justify-between border border-solid border-#f0f0f0 b-b-none b-l-none b-r-none font-size-14px"
        >
          <!-- <el-link
            :href="item.templatePath"
            class="w-33% !c-#00000073 font-size-14px m-12px m-l-0px m-r-0px border border-solid border-#f0f0f0 b-b-none b-l-none b-t-none !hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            :underline="false"
            target="_blank"
            ><Icon icon="ep:view" size="14" class="mr-5px" />在线预览</el-link
          > -->
          <div
            class="w-33% m-12px m-l-0px m-r-0px border border-solid border-#f0f0f0 b-b-none b-l-none b-t-none hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="openWord(item.templatePath)"
          >
            <Icon icon="ep:view" size="14" class="mr-5px" />在线预览
          </div>
          <div
            class="w-33% m-12px m-l-0px m-r-0px border border-solid border-#f0f0f0 b-b-none b-l-none b-t-none hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="openForm('update', item.id)"
            v-hasPermi="['bus:orgBillReceiptTemplate:update']"
          >
            <Icon icon="ep:setting" size="14" class="mr-5px" />编辑
          </div>
          <div
            class="w-33% m-12px m-l-0px m-r-0px hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="handleDelete(item.id)"
            v-hasPermi="['bus:orgBillReceiptTemplate:delete']"
          >
            <Icon icon="ep:delete" size="14" class="mr-5px" />删除
          </div>
        </div>
      </div>
    </div>
    <!-- <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="收据模板名称" align="center" prop="name" />
      <el-table-column label="模板上传地址" align="center" prop="templatePath" />
      <el-table-column
        label="应用楼宇id,多个楼宇使用逗号隔开(1,2,3)"
        align="center"
        prop="buildBind"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['OrgBillReceiptTemplate::update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['OrgBillReceiptTemplate::delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table> -->
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
  <el-dialog v-model="centerDialogVisible" width="70%" destroy-on-close center>
    <vue-office-docx
      :src="docx"
      style="height: 70vh"
      @rendered="renderedHandler"
      @error="errorHandler"
    />
  </el-dialog>
  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { TemplateApi, TemplateVO } from '@/api/bus/orgBillReceiptRule'
import Form from './TemplateForm.vue'
//引入VueOfficeDocx组件
import VueOfficeDocx from '@vue-office/docx'
//引入相关样式
import '@vue-office/docx/lib/index.css'
/** 收据模板 列表 */
defineOptions({ name: 'OrgBillReceiptTemplate' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<TemplateVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  templatePath: undefined,
  buildBind: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await TemplateApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}
const docx = ref()
const centerDialogVisible = ref(false)
// 预览
const openWord = (url: string) => {
  console.log(url)
  centerDialogVisible.value = true
  docx.value = url
}

const renderedHandler = () => {
  console.log('渲染完成')
}
const errorHandler = () => {
  console.log('渲染失败')
}
/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await TemplateApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await TemplateApi.export(queryParams)
    download.excel(data, '收据模板.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
