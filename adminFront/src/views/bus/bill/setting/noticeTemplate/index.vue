<template>
  <div class="viewHeader bg-#fff p-20px m-b-20px"
    ><div class="c-#000000d9 fw-600 font-size-20px line-height-32px"> 缴费通知单设置</div>
    <div class="c-#000000a6 font-size-14px line-height-20px p-t-8px">
      用于系统根据账单内容自动生成可下载打印的通知单模板，支持显示付款二维码。目前系统支持短信、邮件自动发送缴费通知单，用户可通过链接进行线上缴纳。
    </div>
  </div>
  <!-- <ContentWrap>
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="通知单模板名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入通知单模板名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="模板上传地址" prop="templatePath">
        <el-input
          v-model="queryParams.templatePath"
          placeholder="请输入模板上传地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="应用楼宇" prop="buildBind">
        <el-input
          v-model="queryParams.buildBind"
          placeholder="请输入应用楼宇"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="操作时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['OrgBillNoticeTemplate::create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['OrgBillNoticeTemplate::export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap> -->

  <!-- 列表 -->
  <ContentWrap class="min-h-500px">
    <div class="grid grid-cols-3 grid-gap-20px c-#929292">
      <div
        @click="openForm('create')"
        v-hasPermi="['bus:orgBillNoticeTemplate:create']"
        class="b-dashed b-rd-4px border-#f0f0f0 h-230px bg-#fff box-border cursor-pointer flex justify-center items-center font-size-20px"
      >
        <Icon icon="ep:plus" :size="20" class="mr-5px" /> 新增模版
      </div>

      <div
        v-loading="loading"
        v-for="(item, index) in list"
        :key="index"
        class="b-rd-4px h-230px bg-#fff box-border flex justify-between flex-col TemplateItem"
      >
        <div class="flex-1 p-24px flex">
          <Icon icon="ep:list" :size="48" color="#2681f3" class="mr-5%" />
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
          <div
            class="w-33% m-12px m-l-0px m-r-0px border border-solid border-#f0f0f0 b-b-none b-l-none b-t-none hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="openWord(item.templatePath)"
          >
            <Icon icon="ep:view" :size="14" class="mr-5px" />在线预览
          </div>
          <div
            class="w-33% m-12px m-l-0px m-r-0px border border-solid border-#f0f0f0 b-b-none b-l-none b-t-none hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="downLoad(item)"
          >
            <Icon icon="ep:download" :size="14" class="mr-5px" />下载
          </div>
          <div
            class="w-33% m-12px m-l-0px m-r-0px border border-solid border-#f0f0f0 b-b-none b-l-none b-t-none hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="openForm('update', item.id)"
            v-hasPermi="['bus:orgBillNoticeTemplate:update']"
          >
            <Icon icon="ep:setting" :size="14" class="mr-5px" />编辑
          </div>
          <div
            class="w-33% m-12px m-l-0px m-r-0px hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="handleDelete(item.id)"
            v-hasPermi="['bus:orgBillNoticeTemplate:delete']"
          >
            <Icon icon="ep:delete" :size="14" class="mr-5px" />删除
          </div>
        </div>
      </div>
    </div>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="getList" />
  <!-- 预览 -->
  <el-dialog v-model="centerDialogVisible" width="70%" destroy-on-close center>
    <vue-office-docx
      :src="docx"
      style="height: 70vh"
      @rendered="renderedHandler"
      @error="errorHandler"
    />
  </el-dialog>
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { Api, VO } from '@/api/bus/orgBillNoticeTemplate'
import Form from './TemplateForm.vue'
import { downloadFile } from '@/utils/downloadFile'
//引入VueOfficeDocx组件
import VueOfficeDocx from '@vue-office/docx'
//引入相关样式
import '@vue-office/docx/lib/index.css'

/** 账单缴费通知单模板 列表 */
defineOptions({ name: 'NoticeSetting' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
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
    const data = await Api.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
const downLoad = async (item) => {
  try {
    downloadFile(item.templatePath, item.name + '模板.docx')
  } catch {
  } finally {
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

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await Api.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
// 预览
const docx = ref()
const centerDialogVisible = ref(false)
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
/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await Api.export(queryParams)
    download.excel(data, '账单缴费通知单模板.xls')
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
<style lang="scss" scoped>
.TemplateItem {
  border: 1px solid rgba(0, 0, 0, 10%);
  box-shadow:
    0 0 5px #0000001a,
    0 0 5px #0000001a;
}
</style>
