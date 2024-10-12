<template>
  <!-- 列表 -->
  <div class="flex items-center justify-between mb-18px bg-white p-10px rounded px-18px">
    <span>巡检点列表</span>
    <div class="flex">
      <el-button
        type="primary"
        plain
        @click="handleExport"
        :loading="exportLoading"
        v-hasPermi="['bus:patrolPosition:export']"
      >
        导出
      </el-button>
      <el-button type="primary" plain @click="onPrintConfirm"> 打印标签 </el-button>
      <el-button
        type="primary"
        @click="openForm('create')"
        :loading="exportLoading"
        v-hasPermi="['bus:patrolPosition:create']"
      >
        新建
      </el-button>
    </div>
  </div>
  <ContentWrap>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      @select="selectData"
      @select-all="selectData"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="巡检点编码" align="center" prop="number" width="280px" />
      <el-table-column label="巡检点名称" align="center" prop="name" />
      <el-table-column label="位置" align="center" prop="positionName" width="280px" />
      <el-table-column label="巡检内容" align="center" prop="formTitle">
        <template #default="scope">
          {{ scope.row.formTitle }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="180px">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="1"
            inactive-value="0"
            @change="changeStatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:patrolPosition:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:patrolPosition:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
  <!-- 标签打印 -->
  <div id="mypdf" style="position: absolute; z-index: -999; width: 100%; height: 1000px">
    <div v-if="tagTemplate?.applyJson?.paper_type == '0'">
      <div v-for="(item, index) in tableSelectData" :key="index">
        <div
          style="
            page-break-after: always;
            box-sizing: border-box;
            border: 1px solid #999;
            margin-top: 10px;
            margin-left: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: white;
          "
          :style="{
            width: `${tagTemplate.applyJson.tag_width - 5}mm`,
            height: `${tagTemplate.applyJson.tag_height - 6}mm`
          }"
        >
          <div style="padding-left: 1px">
            <Qrcode :text="item.number" />
          </div>
          <div style="flex: 1; padding-right: 10px; box-sizing: border-box">
            <img
              v-if="tagTemplate.hasLogo == '0'"
              :src="tagTemplate.applyJson.logo"
              style="width: 100px; height: 55px"
            />
            <div
              v-for="(items, indexs) in tagTemplate.applyJson.fields"
              :key="indexs"
              style="display: flex; flex-warp: wrap"
            >
              <span style="white-space: nowrap">{{ items.label }}</span
              >:
              <span style="word-break: break-all; white-space: pre-wrap">{{
                item[items.value] || '--'
              }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="tagTemplate?.applyJson?.paper_type == '1'"
      style="display: grid; gap: 10px; grid-template-columns: repeat(2, 1fr); margin-right: 15px"
    >
      <div
        v-for="(item, index) in tableSelectData"
        :key="index"
        style="height: 200px"
        :class="index % 10 == 0 ? 'section' : ''"
      >
        <div
          style="
            box-sizing: border-box;
            border: 1px solid #999;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: white;
            height: 100%;
          "
        >
          <div style="padding-left: 1px">
            <Qrcode :text="item.number" :width="180" />
          </div>
          <div style="flex: 1; padding-right: 10px; box-sizing: border-box">
            <img
              v-if="tagTemplate.hasLogo == '0'"
              :src="tagTemplate.applyJson.logo"
              style="width: 100px; height: 55px"
            />
            <div
              v-for="(items, indexs) in tagTemplate.applyJson.fields"
              :key="indexs"
              style="display: flex; flex-warp: wrap"
            >
              <span style="white-space: nowrap">{{ items.label }}</span
              >:
              <span style="word-break: break-all; white-space: pre-wrap">{{
                item[items.value] || '--'
              }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="tagTemplate?.applyJson?.paper_type == '2'"
      style="display: grid; gap: 10px; grid-template-columns: repeat(3, 1fr); margin-right: 15px"
    >
      <div
        v-for="(item, index) in tableSelectData"
        :key="index"
        style="height: 120px"
        :class="index % 24 == 0 ? 'section' : ''"
      >
        <div
          style="
            box-sizing: border-box;
            border: 1px solid #999;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: white;
            width: 100%;
            height: 100%;
          "
        >
          <div style="padding-left: 1px">
            <Qrcode :text="item.number" :width="100" />
          </div>
          <div style="flex: 1; padding-right: 10px; box-sizing: border-box">
            <img
              v-if="tagTemplate.hasLogo == '0'"
              :src="tagTemplate.applyJson.logo"
              style="width: 80px; height: 45px"
            />
            <div
              v-for="(items, indexs) in tagTemplate.applyJson.fields"
              :key="indexs"
              style="display: flex; flex-warp: wrap"
            >
              <span style="white-space: nowrap; font-size: 12px">{{ items.label }}</span>
              <div style="word-break: break-all; white-space: pre-wrap; font-size: 12px">{{
                item[items.value] || '--'
              }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { Api, VO } from '@/api/bus/patrol/position'
import { settingApi } from '@/api/bus/patrol/tagSetting'
import { Qrcode } from '@/components/Qrcode'
import Form from './Form.vue'
import printJS from 'print-js'
// import { hexToRGB } from '../../../../../utils/color'
/** 巡检点位数据 列表 */
defineOptions({ name: 'PatrolPosition' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10
})
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  queryParams.application = application.value
  loading.value = true
  try {
    const data = await Api.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
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

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await Api.export(queryParams)
    download.excel(data, '巡检点位数据.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
const changeStatus = async (row: VO) => {
  await Api.update(row).then(() => {
    message.success('操作成功')
  })
}
//打印标签
/**
 * 确认打印
 */
const onPrintConfirm = async () => {
  await Getprinttemplate()
  if (tableSelectData.value.length === 0) {
    message.error('请选择要打印的数据')
    return
  }
  printJS({
    printable: 'mypdf', // 要打印内容的id
    type: 'html', // 可以打印html,img详细的可以在官方文档https://printjs.crabbly.com/中查询
    targetStyles: ['*'],
    style: `@page{
      size:auto;margin: 4mm;
    
    }
      @media print {
        .section {
          page-break-before: always !important;
        }
      }
    `, // 打印的内容是没有css样式的，此处需要string类型的css样式
    scanStyles: false,
    onLoadingEnd() {
      // showPrintNode.value = false
    }
  })
}
//获取打印模板
const tagTemplate = ref({
  applyJson: {
    fields: [],
    tag_width: 0,
    tag_height: 0,
    paper_type: '0'
  }
})
const tableSelectData = ref([])
const printList = ref([])
const Getprinttemplate = async () => {
  const data = await settingApi.getTemplateList({
    application: application.value
  })
  tagTemplate.value = data[0]
  tagTemplate.value.applyJson = JSON.parse(tagTemplate.value.applyJson)
}
const selectData = (val: any) => {
  tableSelectData.value = val
}
/** 初始化 **/
onMounted(async () => {
  await getapplication()
  getList()
  Getprinttemplate()
})
</script>
<style lang="scss">
@media print {
  .section {
    page-break-before: always !important;
    border: 1px solid red !important;
  }
}
</style>
