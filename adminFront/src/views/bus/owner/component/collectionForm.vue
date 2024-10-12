<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="collectionForm">
    <!-- 表单弹窗：添加/修改 -->
    <el-drawer
      v-model="drawer_show"
      append-to-body
      :with-header="true"
      size="800px"
      title="生成缴费通知单"
      @closed="closed"
    >
      <el-form
        labelWidth="100%"
        label-position="top"
        ref="formRef"
        :model="form"
        :rules="formRules"
        v-loading="formLoading"
      >
        <el-form-item label="生成方式:" prop="">
          <el-radio-group v-model="BuildType" @change="changeBuildType">
            <el-radio-button
              v-for="item in buildTypeList"
              :key="item.value"
              :value="item.value"
              :label="item.label"
              :disabled="!ordersInfo.data"
          /></el-radio-group>
        </el-form-item>
        <el-row :gutter="20" class="p-20px bg-#ECECEC">
          <el-col :span="12">
            <div class="bg-#fff p-20px">
              <div class="p-b-14px font-size-14px">即将生成通知单份数</div>
              <div class="font-size-18px c-#000">{{ NotificationNumber?.count || '--' }}</div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="bg-#fff p-20px">
              <div class="p-b-14px font-size-14px">合计应收总数</div>
              <div class="font-size-18px c-#000">{{ NotificationNumber?.money || '--' }} 元</div>
            </div>
          </el-col>
        </el-row>
        <el-form-item label="通知方式" prop="noticeType" class="m-t-20px">
          <el-checkbox
            v-model="checkAll"
            :indeterminate="isIndeterminate"
            @change="handleCheckAllChange"
            class="!m-r-10px"
          >
            全选
          </el-checkbox>
          <el-checkbox-group v-model="messageType" @change="handleCheckedTypesChange">
            <el-checkbox
              v-for="item in getStrDictOptions(DICT_TYPE.NOTICE_TYPE)"
              :label="item.label"
              :value="item.value"
              :key="item.value"
            />
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="通知单设置" prop="accountId">
          <el-table
            :data="tableData"
            border
            style="width: 100%"
            show-overflow-tooltip
            :header-cell-style="{
              color: '#000000d9',
              fontSize: '14px',
              fontWeight: '500',
              backgroundColor: '#fafafa'
            }"
          >
            <el-table-column prop="name" label="楼宇名称" width="120">
              <template #default="">
                <span>{{ RoomInfo?.buildName }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="textId" label="文本模版" width="160">
              <template #default="scope">
                <el-select v-model="scope.row.textId" placeholder="">
                  <el-option
                    v-for="item in getStrDictOptions(DICT_TYPE.NOTIFICAION_TEXT)"
                    :label="item.label"
                    :value="item.value"
                    :key="item.value"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column prop="" label="短信发送" />
            <el-table-column prop="" label="邮箱发送" />
            <el-table-column prop="" label="公众号发送" />
            <el-table-column prop="accountId" label="收支账户" width="160">
              <template #default="scope">
                <el-select v-model="scope.row.accountId" placeholder="">
                  <el-option
                    v-for="item in OrgAccountList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否附上收款二维码">
              <template #label>
                是否附上收款二维码
                <el-tooltip
                  content="支持在生成的通知单模板的下方附上收款二维码，未配置支付方式或打印模板里未添加收款二维码关键词时开启也不会生效"
                  placement="top"
                >
                  <Icon class="" icon="fa:question-circle-o" />
                </el-tooltip>
              </template>
              <el-radio-group v-model="form.hasQrcode">
                <el-radio-button label="否" value="0" />
                <el-radio-button label="是" value="1" />
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="">
              <template #label>
                通知单生成日期
                <el-tooltip
                  content="通知单生成日期将影响通知单模板中的生成日期变量"
                  placement="top"
                >
                  <Icon class="" icon="fa:question-circle-o" />
                </el-tooltip>
              </template>
              <el-date-picker
                v-model="form.buildDate"
                type="date"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                placeholder="请选择日期"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="closed">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </div>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
defineOptions({ name: 'CollectionForm' })
import { formatDate } from '@/utils/formatTime'
import download from '@/utils/download'
import { downloadFile } from '@/utils/downloadFile'
const message = useMessage() // 消息弹窗
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { BuildApi } from '@/api/bus/village'
import { BillNoticeApi, BillNoticeVO } from '@/api/bus/orgBillNotice'
import { contractOrderApi, contractOrderBillVO } from '@/api/bus/contractOrderBill'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
let drawer_show = ref(false)
const form = ref({
  accountId: undefined,
  noticeType: undefined,
  hasQrcode: 0,
  buildDate: '',
  buildId: undefined,
  ownerId: undefined,
  contractId: undefined,
  buildType: undefined,
  contractBillId: undefined
})

const BuildType = ref()
const messageType = ref([])
const checkAll = ref(false)
const isIndeterminate = ref(true)
const tableData = ref([
  {
    buildName: '2016-05-022016-05-022016-05-022016-05-02',
    textId: null,
    accountId: ''
  }
])
const formRules = reactive({
  noticeType: [{ required: true, message: '通知方式不能为空', trigger: 'blur' }],
  accountId: [{ required: true, message: '通知单设置不能为空', trigger: 'blur' }]
})
const changeBuildType = (val: number) => {
  BuildType.value = val
  getNotificationNumber()
}
const buildTypeList = ref([])
const getBuildType = async () => {
  const data = await getStrDictOptions(DICT_TYPE.BUILD_TYPE)
  buildTypeList.value = data
  BuildType.value = data[0].value
}
const OrgAccountList = ref([])
const getOrgAccountList = async () => {
  const data = await BuildApi.orgAccountList({ pageNo: 1, pageSize: 10, build: form.value.buildId })

  OrgAccountList.value = data
  tableData.value[0].accountId = data[0].id
  form.value.accountId = data[0].id
  console.log(data[0].id, 'getOrgAccountList', data[0])
}
const handleCheckAllChange = (val: boolean) => {
  if (val) {
    messageType.value = getStrDictOptions(DICT_TYPE.NOTICE_TYPE).map((item) => item.value)
  } else {
    messageType.value = []
  }
  isIndeterminate.value = false
  form.value.noticeType = JSON.stringify(messageType.value)
}
const handleCheckedTypesChange = (value: string[]) => {
  const checkedCount = value.length
  isIndeterminate.value =
    checkedCount > 0 && checkedCount < getStrDictOptions(DICT_TYPE.NOTICE_TYPE).length
  checkAll.value = checkedCount === getStrDictOptions(DICT_TYPE.NOTICE_TYPE).length
  form.value.noticeType = messageType.value.join(',')
}
const RoomInfo = ref({})
const ids = ref('')
const ordersInfo = ref({})
const ownerInfos = ref({})
/** 打开抽屉 */
const open = async (info, ownerInfo, orderInfo) => {
  messageType.value = []
  await getBuildType()
  console.log(info, 'info', ownerInfo)
  RoomInfo.value = info
  ownerInfos.value = ownerInfo
  ordersInfo.value = orderInfo
  console.log(orderInfo, 'orderInfo')
  if (orderInfo?.data) {
    if (orderInfo.data[0]?.data) {
      orderInfo.data.forEach((item) => {
        ids.value = ids.value
          ? ids.value.concat(',') + item.data.map((element) => element.id).join(',')
          : item.data.map((element) => element.id).join(',')
        // item.data.forEach((item1) => {
        //   item1.textId = getStrDictOptions(DICT_TYPE.NOTIFICAION_TEXT)[0].value
        // })
      })
    } else {
      ids.value = orderInfo.data.map((item) => item.id).join(',')
    }
  } else {
    ids.value = orderInfo.id
  }
  console.log(ids, 'ids')
  resetForm()
  tableData.value[0].textId = getStrDictOptions(DICT_TYPE.NOTIFICAION_TEXT)[0].value
  form.value.buildDate = formatDate(new Date(), 'YYYY-MM-DD')
  form.value.buildId = info.buildId
  form.value.ownerId = ownerInfo.id
  form.value.contractId = ownerInfo.contractId
  form.value.contractBillId = ids.value
  getOrgAccountList()
  getNotificationNumber()
  drawer_show.value = true
}
const closed = async () => {
  emit('update:show', false)
}
const emit = defineEmits(['update:show', 'success'])
const formRef = ref()
const formLoading = ref(false)
const submit = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    form.value.buildType = BuildType.value
    console.log(form.value, 'form.value')
    let res = await BillNoticeApi.batchCreate(form.value)
    downloadFile(res, '缴费通知单.zip')
    message.success('催款成功')
    formLoading.value = false
    drawer_show.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const NotificationNumber = ref(
  {} as {
    count: number
    money: number
  }
)
/** 查询顶部统计 */
const getNotificationNumber = async () => {
  try {
    const data = await contractOrderApi.getNotificationNumber({
      ids: ids.value,
      buildType: BuildType.value
    })
    NotificationNumber.value = data
  } finally {
  }
}
onMounted(() => {})

/** 重置表单 */
const resetForm = () => {
  form.value = {
    accountId: undefined,
    noticeType: undefined,
    hasQrcode: 0,
    buildDate: '',
    buildId: undefined,
    ownerId: undefined,
    contractId: undefined,
    buildType: undefined,
    contractBillId: undefined
  }
  formRef.value?.resetFields()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped>
.drawer_footer {
  display: flex;
  justify-content: flex-end;
  position: fixed;
  bottom: 40px;
  right: 40px;
}
.drawer {
  max-height: 75vh;
  overflow-y: auto;
  overflow-x: hidden;

  .drawer_header-title {
    margin-bottom: 20px;
    font-size: 16px;
    font-weight: 600;
    color: #4a4a4a;
  }
}
</style>
<style>
.collectionForm .el-drawer__header {
  margin-bottom: 20px !important;
}
</style>
