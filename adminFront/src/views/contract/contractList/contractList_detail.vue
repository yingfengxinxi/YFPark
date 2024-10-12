<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div v-show="!quitContrac_show && !copycontrac_show && !ApproveView_show">
    <div class="page">
      <div class="header_row flex">
        <div class="flex items-center">
          <Icon icon="ep:arrow-left" @click="emit('close')" class="mr-[5px] cursor-pointer" />
          <span class="font-900 text-[18px]">{{ detail_res.ownerName }}</span>
          <el-tag
            :type="statusData.find((item) => item.value == detail_res.status)?.colorType"
            v-if="statusData.find((item) => item.value == detail_res.status)?.label"
            class="ml-10px"
            >{{ statusData.find((item) => item.value == detail_res.status)?.label }}</el-tag
          >
        </div>
        <div class="gap-1 flex">
          <el-button
            type="primary"
            @click="ApproveView_function"
            v-if="statusChangeButtion(detail_res.status).view"
            v-hasPermi="['bus:contract-leave:getOneByContractId']"
            >查看审批</el-button
          >
          <el-button
            type="primary"
            plain
            v-if="statusChangeButtion(detail_res.status).viewNew"
            @click="show_contract_detail_new"
            >查看新合同</el-button
          >
          <el-button
            type="danger"
            plain
            @click="quit_function"
            v-if="statusChangeButtion(detail_res.status).quit"
            v-hasPermi="['bus:contract:rentTermination']"
            >退租</el-button
          >
          <el-button
            type="primary"
            plain
            @click="quit_function('view')"
            v-if="statusChangeButtion(detail_res.status).viewQuit"
            v-hasPermi="['bus:orgContractRetreat:get']"
            >查看退租协议</el-button
          >

          <el-button
            type="primary"
            plain
            @click="Stayover_function"
            v-if="
              statusChangeButtion(detail_res.status).statover && detail_res.isWhetherLease == '0'
            "
            v-hasPermi="['bus:contract:renewalLeaseBut']"
            >续租</el-button
          >
          <el-button
            type="danger"
            @click="void_function"
            v-if="statusChangeButtion(detail_res.status).void"
            v-hasPermi="['bus:contract:toVoidCheck']"
            >作废</el-button
          >
          <el-dropdown v-if="statusChangeButtion(detail_res.status).more" class="ml-3">
            <el-button type="primary" plain>
              更多<Icon icon="ep:arrow-down-bold" class="ml-1" />
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item
                  @click="change_function"
                  v-if="statusChangeButtion(detail_res.status).change"
                  v-hasPermi="['bus:contract:change']"
                  >变更</el-dropdown-item
                >
                <el-dropdown-item
                  @click="copy_function"
                  v-if="statusChangeButtion(detail_res.status).copy"
                  v-hasPermi="['bus:contract:create']"
                  >复制</el-dropdown-item
                >
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      <div class="header_title" v-if="contractUnitPriceList.length">
        <Icon icon="ep:bell-filled" color="#2681f3" />
        合同摘要:【起租日{{ detail_res.contractStartTime || '--' }}，租赁数为{{
          detail_res.leaseArea || '--'
        }}㎡。首期租赁{{ multipleClause_0[0]?.day }}月一付，租金单价{{ multipleClause_0[0]?.dMoney
        }}{{
          contractUnitPriceList.find((item) => item.sort == multipleClause_0[0].contractUnitPrice)
            .label
        }}。首期物业{{ multipleClause_1[0]?.day }}月一付，物业单价{{ multipleClause_1[0]?.dMoney
        }}{{
          contractUnitPriceList.find((item) => item.sort == multipleClause_1[0].contractUnitPrice)
            .label
        }}。】
      </div>
      <div class="header_title mt-3" v-if="contractOperateLogList.length > 0">
        <Icon icon="ep:bell-filled" color="#2681f3" />
        最新备注:【{{ contractOperateLogList[contractOperateLogList.length - 1].operateContent }}】
      </div>
      <div class="page_select_change">
        <div
          v-for="(item, index) in selectList"
          :key="index"
          class="page_select_change_item"
          :class="activeTab == index + 1 ? 'active' : ''"
          @click="activeTab = index + 1"
        >
          {{ item }}
        </div>
      </div>
    </div>
    <!-- 信息列表 -->
    <div v-show="activeTab == 1">
      <ContentWrapBorder :header_data="'基本信息'" class="jbxx_list mt-[18px]">
        <JbxxList :data="detail_res" />
      </ContentWrapBorder>
      <ContentWrapBorder :header_data="'房源信息'" class="jbxx_list">
        <FyxxList :data="detail_res" />
      </ContentWrapBorder>
      <ContentWrapBorder :header_data="'租客信息'" class="jbxx_list mt-[18px]">
        <ZkxxList :data="detail_res" />
      </ContentWrapBorder>
      <ContentWrapBorder :header_data="'滞纳金信息'" class="jbxx_list mt-[18px]">
        <ZnjxxList :data="detail_res" />
      </ContentWrapBorder>
      <ContentWrapBorder :header_data="'租赁条款'" class="jbxx_list mt-[18px]">
        <ZptkList :data="detail_res" />
      </ContentWrapBorder>
      <ContentWrapBorder :header_data="'物业条款'" class="jbxx_list mt-[18px]">
        <WytkList :data="detail_res" />
      </ContentWrapBorder>
      <!-- 备注信息 -->
      <div class="mt-4 bg-white p-[20px] rounded-[4px]">
        <div class="row flex justify-between items-center">
          <div>备注信息</div>
          <el-button type="primary" plain @click="contractLog_visible = true">
            <Icon icon="ep:circle-plus-filled" class="mr-2" />
            备注
          </el-button>
        </div>
        <el-table :data="contractOperateLogList">
          <el-table-column prop="creator" align="center" label="创建人" />
          <el-table-column prop="createTime" align="center" label="创建时间" />
          <el-table-column prop="operateContent" align="center" label="备注内容" />
          <el-table-column width="100px" align="center" label="操作">
            <template v-slot="{ row }">
              <el-button type="danger" plain @click="del_contractOperateLog(row.id)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 添加备注 -->
      <el-drawer v-model="contractLog_visible" title="添加备注" :before-close="handleClose">
        <el-form label-position="top" :model="form" ref="contractLogForm">
          <el-form-item
            label="备注内容"
            :rules="[
              {
                required: true,
                message: '请输入备注内容',
                trigger: 'blur'
              }
            ]"
          >
            <el-input type="textarea" v-model="form.operateContent" placeholder="添加备注信息" />
          </el-form-item>
        </el-form>
        <template #footer>
          <div style="flex: auto">
            <el-button @click="resetcontractLogForm">取消</el-button>
            <el-button type="primary" @click="createContractLog">保存</el-button>
          </div>
        </template>
      </el-drawer>
    </div>

    <!-- //============================================================================================= -->
    <!-- 合同明细 -->
    <div v-show="activeTab == 2" class="mt-18px">
      <ContractDetails_table :data="detail_res" />
    </div>
    <!-- //============================================================================================= -->
    <!-- 合同文本 -->
    <div v-show="activeTab == 3" class="mt-18px">
      <ContractText :data="detail_res" />
    </div>
    <!-- //============================================================================================= -->
    <!-- 账单列表 -->
    <div v-show="activeTab == 4" class="mt-18px">
      <Bill_list :detailId="detail_id" />
    </div>
  </div>
  <!-- //============================================================================================= -->

  <!-- 退租按钮 -->
  <div v-show="quitContrac_show">
    <QuitContracList ref="quitContrac_ref" @close="quit_clone" />
  </div>

  <!-- 复制合同 -->
  <Add_contracList ref="add_contracList" v-show="copycontrac_show" @close="copy_clone" />
  <!-- 审核详情 -->
  <ApproveView ref="ApproveView_ref" v-if="ApproveView_show" @close="ApproveView_close" />
  <!-- 续租合同 -->
  <el-drawer v-model="Stayover_show" title="合同续租">
    <template #header>
      <div class="text-16px"> 合同续租 </div>
    </template>
    <div class="bg-#F0F9FF p-2 rd-1" style="border: 1px solid #2681f3">
      <div class="header_title flex items-center gap-3">
        <Icon icon="ep:info-filled" color="#2681f3" />
        提示
      </div>
      <div class="pl-7 mt-3 color-#999999 text-14px">
        合同续租，将使用原有条款新建合同，审批通过后新合同将在租期起日开始执行
      </div>
    </div>
    <div class="mt-8">
      <el-form label-position="top">
        <el-form-item label="租期起始日期" required>
          <el-date-picker
            v-model="Stayover_time"
            type="daterange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            :disabled-date="StayoverStartTime"
          />
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <div>
        <el-button @click="Stayover_close"> 取消 </el-button>
        <el-button type="primary" @click="Stayover_submit"> 确定 </el-button>
      </div>
    </template>
  </el-drawer>
  <!-- 作废弹窗 -->
  <el-dialog title="作废原因" v-model="void_dialog" width="30%">
    <el-input
      v-model="void_form.content"
      placeholder="请输入作废原因"
      :autosize="{ minRows: 8, maxRows: 10 }"
      show-word-limit
      type="textarea"
    />
    <el-select placeholder="请选择作废合同审批流程" class="mt-18px" v-model="void_form.voidId">
      <el-option
        v-for="(item, index) in voidList"
        :key="index"
        :label="item.categoryName"
        :value="item.key"
      />
    </el-select>
    <el-checkbox
      label="同时作废所有关联账单、发票、收据并取绑所有流水"
      v-model="void_form.isToVoidBill"
      :true-label="1"
      :false-label="0"
    />
    <el-checkbox
      label="自动关闭账单绑定的流水"
      v-model="void_form.iSCloseFlow"
      :true-label="1"
      :false-label="0"
    />
    <template #footer>
      <div class="text-right">
        <el-button @click="void_dialog = false">取 消</el-button>
        <el-button type="primary" @click="void_submit">确 定</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 合同变更 -->
  <el-drawer v-model="Change_show" title="合同变更">
    <template #header>
      <div class="text-16px"> 合同变更 </div>
    </template>
    <div class="bg-#F0F9FF p-2 rd-1" style="border: 1px solid #2681f3">
      <div class="header_title flex items-center gap-3">
        <Icon icon="ep:info-filled" color="#2681f3" />
        提示
      </div>
      <div class="pl-7 mt-3 color-#999999 text-14px">
        合同变更需要退租原合同并创建新合同，审批通过后新合同将在租期起日开始执行。
      </div>
    </div>
    <div class="mt-8">
      <el-form label-position="top">
        <el-form-item label="变更日期" required>
          <el-date-picker
            v-model="changeTime"
            type="date"
            placeholder="请选择时间"
            :disabled-date="disbaleDate"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <div>
        <el-button @click="close_change"> 取消 </el-button>
        <el-button @click="toFormDetail" type="primary"> 确定 </el-button>
      </div>
    </template>
  </el-drawer>
  <!-- 获取新合同 -->
  <el-drawer
    title="查看新合同"
    v-model="show_contract_detail_dialog"
    size="80%"
    style="background-color: #f5f7f9"
  >
    <template #header>
      <span class="text-16px text-#000">查看新合同</span>
    </template>
    <ContractDetail
      :detailId="detail_id"
      :type="'new'"
      @close="show_contract_detail_dialog = false"
      ref="ContractDetail_ref"
    />
  </el-drawer>
</template>
<script lang="ts" setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import ContentWrapBorder from '@/views/bus/owner/component/ContentWrap_border.vue'
//合同信息组件
import JbxxList from './component/jbxx_list.vue'
import FyxxList from './component/fyxx_list.vue'
import ZkxxList from './component/zkxx_list.vue'
import ZnjxxList from './component/znjxx_list.vue'
import ZptkList from './component/zptk_list.vue'
import WytkList from './component/wytk_list.vue'
//合同明细表格
import ContractDetails_table from './component/ContractDetails_table.vue'
import ContractText from './component/ContractText.vue'
import Bill_list from './component/Bill_list.vue'
//按钮组件
import QuitContracList from './component/button_change/quit_contracLIst.vue'
import Add_contracList from './add_contractList.vue'
//审批详情
import ApproveView from '@/views/contract/setting/approveView/index.vue'
import { Api } from '@/api/contract/contractList/index'
import { OwnerApi } from '@/api/bus/owner/index'
import { getStrDictOptions } from '@/utils/dict'
import { ref } from 'vue'
import statusChangeButtion from './component/utils/status'
import ContractDetail from './contractList_detail.vue'
const detail_res = ref<any>([])
const detail_id = ref('')
const message = useMessage() // 消息弹窗
//数据处理
const clauseTypes = ref([])
const taxClause_0 = ref([])
const bondClause_0 = ref([])
const multipleClause_0 = ref([])
const taxClause_1 = ref([])
const bondClause_1 = ref([])
const multipleClause_1 = ref([])
const statusData = ref([])
//props接收
const contractOperateLogList = ref([])
let props = defineProps({
  detailId: {
    type: String,
    default: ''
  },
  type: {
    type: String,
    default: ''
  }
})
watch(
  () => props.detailId,
  (val) => {
    setTimeout(() => {
      getDetailList(val)
    }, 100)
  },
  { immediate: true }
)
//获取详情
const getDetailList = (val) => {
  if (!val) return
  if (props.type == 'history') {
    Api.getSearchHistoricalContract(val).then(async (res) => {
      detail_res.value = res
      detail_id.value = val
      //数据处理
      clauseTypes.value = JSON.parse(res.clauseTypes)
      taxClause_0.value = JSON.parse(clauseTypes.value[0].taxClause)
      bondClause_0.value = JSON.parse(clauseTypes.value[0].bondClause)
      multipleClause_0.value = JSON.parse(clauseTypes.value[0].multipleClause)
      taxClause_1.value = JSON.parse(clauseTypes.value[1].taxClause)
      bondClause_1.value = JSON.parse(clauseTypes.value[1].bondClause)
      multipleClause_1.value = JSON.parse(clauseTypes.value[1].multipleClause)
      //调取字典接口
      getOwnerInfo(res.ownerId)
      getAgentInfo(res.operatorId)
      getProject_CALCULATION_ORDER('CALCULATION_ORDER', res.calculationOrder) //计算顺序
      getProject_IS_RECEIVABLE_INTEGER('IS_RECEIVABLE_INTEGER', res.isReceivableInteger) //单位保留小数
      getIndustry(res.industry)
      getbuildId(res.buildId)
      getContractOperate()
      getcontractUnitPriceList()
      statusData.value = await getStrDictOptions('CONTRACT_LIST_STATUS')
    })
  } else if (props.type == 'new') {
    console.log(detail_id.value, '=============================')
    Api.getChangeContract('201').then(async (res) => {
      detail_res.value = res
      detail_id.value = val
      //数据处理
      clauseTypes.value = JSON.parse(res.clauseTypes)
      taxClause_0.value = JSON.parse(clauseTypes.value[0].taxClause)
      bondClause_0.value = JSON.parse(clauseTypes.value[0].bondClause)
      multipleClause_0.value = JSON.parse(clauseTypes.value[0].multipleClause)
      taxClause_1.value = JSON.parse(clauseTypes.value[1].taxClause)
      bondClause_1.value = JSON.parse(clauseTypes.value[1].bondClause)
      multipleClause_1.value = JSON.parse(clauseTypes.value[1].multipleClause)
      //调取字典接口
      getOwnerInfo(res.ownerId)
      getAgentInfo(res.operatorId)
      getProject_CALCULATION_ORDER('CALCULATION_ORDER', res.calculationOrder) //计算顺序
      getProject_IS_RECEIVABLE_INTEGER('IS_RECEIVABLE_INTEGER', res.isReceivableInteger) //单位保留小数
      getIndustry(res.industry)
      getbuildId(res.buildId)
      getContractOperate()
      getcontractUnitPriceList()
      statusData.value = await getStrDictOptions('CONTRACT_LIST_STATUS')
      getvoidList() //获取作废流程列表
    })
  } else {
    Api.contractgetId(val).then(async (res) => {
      detail_res.value = res
      detail_id.value = val
      //数据处理
      clauseTypes.value = JSON.parse(res.clauseTypes)
      taxClause_0.value = JSON.parse(clauseTypes.value[0].taxClause)
      bondClause_0.value = JSON.parse(clauseTypes.value[0].bondClause)
      multipleClause_0.value = JSON.parse(clauseTypes.value[0].multipleClause)
      taxClause_1.value = JSON.parse(clauseTypes.value[1].taxClause)
      bondClause_1.value = JSON.parse(clauseTypes.value[1].bondClause)
      multipleClause_1.value = JSON.parse(clauseTypes.value[1].multipleClause)
      //调取字典接口
      getOwnerInfo(res.ownerId)
      getAgentInfo(res.operatorId)
      getProject_CALCULATION_ORDER('CALCULATION_ORDER', res.calculationOrder) //计算顺序
      getProject_IS_RECEIVABLE_INTEGER('IS_RECEIVABLE_INTEGER', res.isReceivableInteger) //单位保留小数
      getIndustry(res.industry)
      getbuildId(res.buildId)
      getContractOperate()
      getcontractUnitPriceList()
      statusData.value = await getStrDictOptions('CONTRACT_LIST_STATUS')
      getvoidList() //获取作废流程列表
    })
  }
}

//头部切换信息
const selectList = ref(['合同信息', '合同明细', '合同文本', '账单列表', '收入列表'])
let activeTab = ref(1)
const emit = defineEmits(['close'])

//通过id获取租客信息
const getOwnerInfo = async (id: string) => {
  if (!id) return
  const res = await OwnerApi.getOwner(id)
  detail_res.value.ownerName = res.name
}
//通过id获取经办人
const getAgentInfo = async (operatorId: string) => {
  const res = await OwnerApi.getTagIndustryListID(operatorId)
  res.forEach((item) => {
    if (item.id == operatorId) {
      detail_res.value.operatorName = item.nickname
    }
  })
}
//获取字典计算顺序
const getProject_CALCULATION_ORDER = async (type: string, sort: string) => {
  const res = await Api.getProject({
    dictType: type,
    sort: sort
  })
  res.list.forEach((item) => {
    if (item.sort == sort) {
      detail_res.value.calculationOrderLabel = item.label
    }
  })
}
//获取字典是否四舍五入
const getProject_IS_RECEIVABLE_INTEGER = async (type: string, sort: string) => {
  const res = await Api.getProject({
    dictType: type,
    sort: sort
  })
  res.list.forEach((item) => {
    if (item.sort == sort) {
      detail_res.value.isReceivableIntegerLabel = item.label
    }
  })
}
//获取行业
const getIndustry = async (industry: string) => {
  if (!industry) return
  const res = await OwnerApi.getTagIndustryListID(industry)
  // console.log(res)
  res.forEach((item) => {
    if (item.id == industry) {
      detail_res.value.industryLabel = item.name
    }
  })
}
//获取楼宇
const getbuildId = async (id) => {
  const res = await Api.getBuildingList()
  res.forEach((item) => {
    if (item.id == id) {
      detail_res.value.buildName = item.buildName
    }
  })
}
//获取租金单位
const contractUnitPriceList = ref([])
const getcontractUnitPriceList = () => {
  Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'CONTRACT_UNIT_PRICE'
  }).then((res) => {
    contractUnitPriceList.value = res.list
  })
}
//获得合同日志分页
let contractLog_visible = ref(false)
let ContractOperateLogpeopage = ref({
  pageNo: 1,
  pageSize: 10
})
var getContractOperate = async () => {
  const res = await Api.getcontractOperateLog({
    contractId: props.detailId,
    pageNo: ContractOperateLogpeopage.value.pageNo,
    pageSize: ContractOperateLogpeopage.value.pageSize
  })
  contractOperateLogList.value = res.list
}
//删除合同备注
const del_contractOperateLog = async (id) => {
  ElMessageBox.confirm('确认删除该合同备注吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    Api.deletecontractOperateLog(id).then(() => {
      getContractOperate()
      message.success('删除成功')
    })
  })
}
//关闭添加备注抽屉
const handleClose = () => {
  contractLog_visible.value = false
}
const form = ref({
  operateContent: ''
})
//重置添加备注表单
const resetcontractLogForm = () => {
  form.value.operateContent = ''
}
//添加备注
const contractLogForm = ref()
const createContractLog = async () => {
  if (!contractLogForm.value) return
  await contractLogForm.value.validate((valid) => {
    if (valid) {
      Api.createcontractOperateLog({
        contractId: props.detailId,
        operateContent: form.value.operateContent
      }).then(() => {
        contractLog_visible.value = false
        getContractOperate()
        resetcontractLogForm()
        message.success('添加成功')
      })
    }
  })
}
//退租
let quitContrac_show = ref(false)
let quitContrac_ref = ref()
const quit_function = (type: string) => {
  if (type == 'view') {
    quitContrac_show.value = true
    quitContrac_ref.value.open(props.detailId, type)
  } else {
    Api.rentTerminationContract(props.detailId).then((res) => {
      if (res) {
        quitContrac_show.value = true
        quitContrac_ref.value.open(props.detailId, type)
      }
    })
  }
}
//复制合同
let copycontrac_show = ref<Boolean>(false)
const add_contracList = ref()
const copy_function = () => {
  copycontrac_show.value = true
  add_contracList.value.detail_open(props.detailId, 'copy')
}
const copy_clone = () => {
  emit('close')
}
const quit_clone = () => {
  // emit('close')
  quitContrac_show.value = false
  getDetailList(props.detailId)
}
//下拉框时间
const handleClick = (res) => {
  console.log(res)
}
//续租
let Stayover_show = ref<Boolean>(false)
const Stayover_function = () => {
  Api.contractrenewalLeaseBut(props.detailId).then((res) => {
    if (res) {
      Stayover_time.value = []
      StayoverStartTime_value.value = res
      Stayover_show.value = true
    }
  })
}
const StayoverStartTime_value = ref('')
const StayoverStartTime = (res) => {
  return StayoverStartTime_value.value < res ? false : true
}
const Stayover_time = ref([])
const Stayover_submit = () => {
  if (Stayover_time.value.length == 0) {
    message.error('请选择租凭起始时间')
    return
  }
  copycontrac_show.value = true
  add_contracList.value.detail_open(props.detailId, 'Stayover', Stayover_time.value)
  Stayover_close()
}
const Stayover_close = () => {
  Stayover_time.value = []
  Stayover_show.value = false
}
//审核详情
const ApproveView_show = ref<Boolean>(false)
const ApproveView_ref = ref()
const ApproveView_function = () => {
  ApproveView_show.value = true
  setTimeout(() => {
    ApproveView_ref.value.open(props.detailId)
  }, 100)
}
const ApproveView_close = () => {
  ApproveView_show.value = false
  getDetailList(props.detailId)
}
/////作废操作
const void_dialog = ref<Boolean>(false)
const void_function = () => {
  Api.contractvoid(props.detailId).then(() => {
    void_dialog.value = true
  })
}
const void_form = ref({
  content: '',
  isToVoidBill: 0,
  iSCloseFlow: 0,
  voidId: ''
})
const void_submit = () => {
  if (!void_form.value.content) {
    message.error('请输入作废原因')
    return
  }
  if (!void_form.value.voidId) {
    message.error('请选择作废合同审批流程')
    return
  }
  Api.contractvoidSubmit({
    contractId: props.detailId,
    content: void_form.value.content,
    isToVoidBill: void_form.value.isToVoidBill,
    iSCloseFlow: void_form.value.iSCloseFlow
  }).then((res) => {
    getDetailList(props.detailId)
    Api.createContractFlow({
      contractId: props.detailId,
      contractNumber: detail_res.value.contractNumber,
      processDefinitionKey: void_form.value.voidId
    }).then(() => {
      void_dialog.value = false
      message.success('作废成功')
    })
  })
}
//获取作废流程列表
const voidList = ref([])
const getvoidList = () => {
  Api.getcontract_flow({
    pageNo: 1,
    pageSize: 100,
    category: 'contractDelete'
  }).then((res) => {
    voidList.value = res
  })
}
/////变更操作
////////////////////////////
const Change_show = ref<Boolean>(false)
function getTargetDate(startDate) {
  // 获取当前日期
  let today = new Date()

  // 获取起始日期的年、月、日
  let startYear = startDate.getFullYear()
  let startMonth = startDate.getMonth()
  let startDay = startDate.getDate()

  // 获取当前日期的年、月、日
  let currentYear = today.getFullYear()
  let currentMonth = today.getMonth()
  let currentDay = today.getDate()

  // 计算月份差值
  let monthDifference = (currentYear - startYear) * 12 + (currentMonth - startMonth)

  // 如果今天的日期比起始日期早，减去一个月
  if (currentDay < startDay) {
    monthDifference--
  }

  // 增加月份并处理溢出
  let targetYear = startYear + Math.floor((startMonth + monthDifference + 1) / 12)
  let targetMonth = (startMonth + monthDifference + 1) % 12

  // 处理目标日期所在月份的天数
  let daysInTargetMonth = new Date(targetYear, targetMonth + 1, 0).getDate()
  let targetDay = Math.min(startDay, daysInTargetMonth)

  let targetDate = new Date(targetYear, targetMonth, targetDay)

  // 确保返回的日期在今天或之后
  if (targetDate < today) {
    // 如果计算出的日期在今天之前，增加一个月
    targetMonth++
    if (targetMonth > 11) {
      targetMonth = 0
      targetYear++
    }
    daysInTargetMonth = new Date(targetYear, targetMonth + 1, 0).getDate()
    targetDay = Math.min(startDay, daysInTargetMonth)
    targetDate = new Date(targetYear, targetMonth, targetDay)
  }

  return targetDate
}

//
const changeTime = ref('')
const close_change = () => {
  Change_show.value = false
  changeTime.value = ''
}
const changeRoom = ref(null)
const changeTime_Api = ref()
const change_function = () => {
  Api.contractchange(props.detailId).then((res) => {
    if (res) {
      changeRoom.value = true
      changeTime_Api.value = res
      Change_show.value = true
    } else {
      changeTime_Api.value = ''
      changeRoom.value = false
      Change_show.value = true
    }
  })
}
const disbaleDate = (time) => {
  return time.getTime() < changeTime_Api.value
}
const toFormDetail = () => {
  if (!changeTime.value) {
    message.error('请选择变更日期')
    return
  }
  copycontrac_show.value = true
  add_contracList.value.detail_open(props.detailId, 'change', changeTime.value, changeRoom.value)
  Change_show.value = false
}
//查看新合同
const show_contract_detail_dialog = ref<Boolean>(false)
const ContractDetail_ref = ref(null)
const show_contract_detail_new = () => {
  show_contract_detail_dialog.value = true
}
</script>
<style lang="scss" scoped>
.page {
  background-color: #fff;
  padding-bottom: 20px;
  border-radius: 5px;

  .header_row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 20px;
    background-color: #fff;
  }
  .header_title {
    border: 1px solid #2681f3;
    margin-left: 20px;
    margin-right: 20px;
    background-color: #f0f9ff;
    padding: 8px;
    border-radius: 5px;
    color: #999;
    font-size: 14px;
    display: flex;
    gap: 10px;
  }
  .page_select_change {
    display: flex;
    gap: 20px;
    font-size: 14px;
    padding: 20px 20px 0 20px;
    .page_select_change_item {
      cursor: pointer;
      padding-bottom: 10px;
      border-bottom: 2px solid #fff;
    }
    .active {
      color: #2681f3;
      border-bottom: 2px solid #2681f3;
    }
  }
}
.jbxx_list {
  .detail_header_title {
    font-size: 14px;
    color: #999;
    margin-bottom: 5px;
  }
  .detail_header_content {
    font-size: 16px;
    color: #333;
    margin-top: 10px;
  }
}
.ContentWrap {
  --el-card-padding: 20px 20px 0 20px;
}
.htmx_list {
  ::v-deep .bottom_row {
    padding-top: 0 !important;
  }
  ::v-deep .el-collapse-item__content {
    // padding: 10px 20px !important;
    padding-bottom: 0px !important;
  }
}
</style>
