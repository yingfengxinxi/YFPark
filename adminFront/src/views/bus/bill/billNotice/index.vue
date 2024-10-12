<template>
  <ContentWrap>
    <div class="c-#000000d9 fw-600 font-size-20px line-height-32px"> 收款通知</div>
  </ContentWrap>
  <ContentWrap>
    <el-row>
      <el-col
        :span="8"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-#000000d9 font-size-24px"
      >
        <div class="c-#00000073 font-size-14px m-b-5px">已生成</div>
        <!-- 更加安全的访问方式 -->
        {{ TopNumCount?.mailStatus === undefined ? '--' : TopNumCount.mailStatus }}
      </el-col>
      <el-col
        :span="8"
        class="text-center border-0 b-r-1px border-solid b-#f5f5f5 flex-1 c-#000000d9 font-size-24px"
      >
        <div class="c-#00000073 font-size-14px m-b-5px">短信发送成功</div>
        {{ TopNumCount?.smsSuccess || '--' }}
      </el-col>
      <el-col :span="8" class="text-center flex-1 c-#000000d9 font-size-24px">
        <div class="c-#00000073 font-size-14px m-b-5px">邮箱发送成功</div>
        {{ TopNumCount?.emailStatus || '--' }}
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="m-b-10px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="租客名称" prop="ownerName">
        <el-input
          v-model="queryParams.ownerName"
          placeholder="请输入名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="短信发送状态" prop="smsStatus">
        <el-select
          v-model="queryParams.smsStatus"
          placeholder="请选择短信发送状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.NOTICE_SMS_SEND_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱发送状态" prop="emailStatus">
        <el-select
          v-model="queryParams.emailStatus"
          placeholder="请选择邮箱发送状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.NOTICE_SMS_SEND_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="生成日期" prop="buildDate">
        <el-date-picker
          v-model="queryParams.buildDate"
          value-format="YYYY-MM-DD"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="租客名称" prop="ownerName" width="240px" />
      <el-table-column label="楼宇" prop="buildName" width="240px" />
      <el-table-column label="楼号/房号" prop="roomNumber" width="240px" />
      <el-table-column label="短信发送状态" prop="smsStatus" width="130px">
        <template #default="scope">
          <template v-if="scope.row.smsStatus">
            <dict-tag :type="DICT_TYPE.NOTICE_SMS_SEND_STATUS" :value="scope.row.smsStatus" />
          </template>
          <span v-if="!scope.row.smsStatus">--</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱发送状态" prop="emailStatus" width="130px">
        <template #default="scope">
          <template v-if="scope.row.emailStatus">
            <dict-tag :type="DICT_TYPE.NOTICE_SMS_SEND_STATUS" :value="scope.row.emailStatus" />
          </template>
          <span v-if="!scope.row.emailStatus">--</span>
        </template>
      </el-table-column>
      <el-table-column label="公众号发送状态" prop="mailStatus" width="130px">
        <template #default="scope">
          <template v-if="scope.row.mailStatus">
            <dict-tag :type="DICT_TYPE.NOTICE_SMS_SEND_STATUS" :value="scope.row.mailStatus" />
          </template>
          <span v-if="!scope.row.mailStatus">--</span>
        </template>
      </el-table-column>
      <el-table-column
        label="生成日期"
        prop="createTime"
        :formatter="dateFormatter2"
        width="180px"
      />
      <el-table-column label="操作" width="200px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="handleSend(scope.row)"
            :disabled="sendLoading"
            :loading="scope.row.sendLoading"
            v-hasPermi="['bus:orgBillNotice:resend']"
          >
            重新发送
          </el-button>
          <el-button
            link
            type="primary"
            :disabled="scope.row.downloadLoading"
            :loading="scope.row.downloadLoading"
            @click="handleDownload(scope.row)"
            v-hasPermi="['bus:orgBillNotice:download']"
          >
            下载
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
</template>

<script setup lang="ts">
defineOptions({ name: 'OrgBillNotice' })
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import { BillNoticeApi, BillNoticeVO } from '@/api/bus/orgBillNotice'
import { downloadFile } from '@/utils/downloadFile'

/** 收款通知记录 列表 */

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const sendLoading = ref(false)
const loading = ref(true) // 列表的加载中
const list = ref<BillNoticeVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  ownerName: undefined,
  buildIdList: [],
  smsStatus: undefined,
  emailStatus: undefined,
  mailStatus: undefined,
  startCreateTime: undefined,
  endCreateTime: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const downloadLoading = ref(false) //下载的加载中

const TopNumCount = ref(
  {} as {
    emailStatus: null
    mailStatus: null
    smsSuccess: null
  }
)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await BillNoticeApi.getBillNoticePage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 查询顶部统计 */
const getTopNumCount = async () => {
  try {
    const data = await BillNoticeApi.getTopNumCount(queryParams)
    TopNumCount.value = data
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
  getTopNumCount()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 重新发送 */
const handleSend = async (row) => {
  try {
    // 发起发送
    sendLoading.value = true
    row.sendLoading = true
    let res = await BillNoticeApi.resend(row.id)
    message.success('发送成功')
    downloadFile(res, '缴费通知单.zip')
    // 刷新列表
    await getList()
    await getTopNumCount()
  } catch {
  } finally {
    sendLoading.value = false
    row.sendLoading = false
  }
}

const handleDownload = async (row: any) => {
  try {
    row.downloadLoading = true
    const data = await BillNoticeApi.getBillNotice(row.id)
    if (data.filePath) downloadFile(data.filePath, '缴费通知单.pdf')
  } catch {
  } finally {
    row.downloadLoading = false
  }
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await BillNoticeApi.exportBillNotice(queryParams)
    download.excel(data, '收款通知记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
/** 激活时 */
onActivated(async () => {
  await getTopNumCount()
  await getList()
})
/** 初始化 **/
onMounted(() => {
  getList()
  getTopNumCount()
})
</script>
