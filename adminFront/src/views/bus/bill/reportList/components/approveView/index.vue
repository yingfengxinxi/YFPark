<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="flex bg-white p-20px items-center mb-18px pos-relative">
    <Icon
      icon="ep:arrow-left"
      font="20px"
      class="text-20px cursor-pointer"
      @click="emit('close')"
    />
    <span class="ml-10px font-600 text-16px">{{ changeHeaderText(detail_data.status) }}</span>
    <el-button class="pos-absolute right-18px" @click="show_contract_detail">查看原合同</el-button>
  </div>
  <div class="flex">
    <div class="left">
      <el-timeline>
        <el-timeline-item
          v-for="(activity, index) in activities"
          :type="now_index === activity.index ? 'primary' : 'default'"
          :hollow="true"
          :key="index"
        >
          <span
            class="cursor-pointer"
            :class="now_index == activity.index ? 'active' : ''"
            @click="to_activity(activity.index)"
            >{{ activity.content }}</span
          >
        </el-timeline-item>
      </el-timeline>
    </div>
    <div class="right">
      <el-scrollbar @scroll="scroll" height="calc(100vh-200px)" ref="scroll_ref">
        <div class="right_item">
          <el-card
            v-for="(item, index) in runningTasks"
            :key="index"
            v-loading="processInstanceLoading"
            class="box-card"
          >
            <template #header>
              <span class="el-icon-picture-outline text-16px">审批任务【{{ item.name }}】</span>
            </template>
            <el-col :offset="6" :span="16">
              <el-form
                :ref="'form' + index"
                :model="auditForms[index]"
                :rules="auditRule"
                label-width="100px"
              >
                <el-form-item v-if="processInstance && processInstance.name" label="流程名">
                  {{ processInstance.name }}
                </el-form-item>
                <el-form-item
                  v-if="processInstance && processInstance.startUser"
                  label="流程发起人"
                >
                  {{ processInstance?.startUser.nickname }}
                  <el-tag size="small" type="info">{{
                    processInstance?.startUser.deptName
                  }}</el-tag>
                </el-form-item>
                <el-card v-if="runningTasks[index].formId > 0" class="mb-15px !-mt-10px">
                  <template #header>
                    <span class="el-icon-picture-outline">
                      填写表单【{{ runningTasks[index]?.formName }}】
                    </span>
                  </template>
                  <form-create
                    v-model="approveForms[index].value"
                    v-model:api="approveFormFApis[index]"
                    :option="approveForms[index].option"
                    :rule="approveForms[index].rule"
                  />
                </el-card>
                <el-form-item label="审批建议" prop="reason">
                  <el-input
                    v-model="auditForms[index].reason"
                    placeholder="请输入审批建议"
                    type="textarea"
                  />
                </el-form-item>
                <el-form-item label="抄送人" prop="copyUserIds">
                  <el-select
                    v-model="auditForms[index].copyUserIds"
                    multiple
                    placeholder="请选择抄送人"
                  >
                    <el-option
                      v-for="item in userOptions"
                      :key="item.id"
                      :label="item.nickname"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-form>
              <div style="margin-bottom: 20px; margin-left: 10%; font-size: 14px">
                <el-button type="success" @click="handleAudit(item, true)">
                  <Icon icon="ep:select" />
                  通过
                </el-button>
                <el-button type="danger" @click="handleAudit(item, false)">
                  <Icon icon="ep:close" />
                  不通过
                </el-button>
                <el-button type="primary" @click="openTaskUpdateAssigneeForm(item.id)">
                  <Icon icon="ep:edit" />
                  转办
                </el-button>
                <el-button type="primary" @click="handleDelegate(item)">
                  <Icon icon="ep:position" />
                  委派
                </el-button>
                <el-button type="primary" @click="handleSign(item)">
                  <Icon icon="ep:plus" />
                  加签
                </el-button>
                <el-button type="warning" @click="handleBack(item)">
                  <Icon icon="ep:back" />
                  回退
                </el-button>
              </div>
            </el-col>
          </el-card>
        </div>
        <div class="right_item" :class="runningTasks.length == 0 ? '' : 'mt-[18px]'">
          <el-card>
            <template #header>
              <div class="card-header">
                <span class="text-16px">审批信息</span>
              </div>
            </template>
            <el-row :gutter="20">
              <!-- 退租审批 -->
              <el-col class="font-600 text-16px">合同信息</el-col>
              <el-col :span="6">
                <div class="detail_header_title"> 租客名称: </div>
                <div class="detail_header_content"> {{ detail_data.signedName }} </div>
              </el-col>
              <el-col :span="6">
                <div class="detail_header_title"> 合同编号: </div>
                <div class="detail_header_content"> {{ detail_data.contractNumber }} </div>
              </el-col>
              <el-col :span="6" v-if="detail_data.status == 4">
                <div class="detail_header_title"> 退租类型: </div>
                <div class="detail_header_content">
                  {{ quitType.find((item) => item.value == RetreatDetail.retreatType)?.label }}
                </div>
              </el-col>
              <el-col :span="6" v-if="detail_data.status == 4">
                <div class="detail_header_title"> 退租日期: </div>
                <div class="detail_header_content"> {{ RetreatDetail.retreatDate }} </div>
              </el-col>
              <el-col :span="6" v-if="detail_data.status == 4">
                <div class="detail_header_title"> 协议签订日: </div>
                <div class="detail_header_content">
                  {{ RetreatDetail.applyRetreatDate || '--' }}
                </div>
              </el-col>
              <el-col :span="6" v-if="detail_data.status == 4">
                <div class="detail_header_title"> 工商注册地址变更日期: </div>
                <div class="detail_header_content"> {{ RetreatDetail.bccChangeDate || '--' }} </div>
              </el-col>
              <el-col :span="6">
                <div class="detail_header_title"> 楼宇-楼层-房号: </div>
                <div
                  class="detail_header_content"
                  v-for="(item, index) in checkedBuild"
                  :key="index"
                >
                  {{ item.buildName }}-{{ item.layerName }}-{{ item.roomName }}
                </div>
              </el-col>
              <el-col :span="6" v-if="detail_data.status == 4">
                <div class="detail_header_title"> 退租原因: </div>
                <div class="detail_header_content">
                  {{ RetreatDetail.retreatReasonLabel }}
                </div>
              </el-col>
              <!-- 新建 -->
              <el-col :span="6">
                <div class="detail_header_title"> 合同租赁数: </div>
                <div class="detail_header_content"> {{ detail_data.leaseArea }} </div>
              </el-col>
              <el-col :span="6">
                <div class="detail_header_title"> 合同开始日: </div>
                <div class="detail_header_content"> {{ detail_data.contractStartTime }} </div>
              </el-col>
              <el-col :span="6">
                <div class="detail_header_title"> 合同结束日: </div>
                <div class="detail_header_content"> {{ detail_data.contractEndTime }} </div>
              </el-col>
              <el-col :span="6">
                <div class="detail_header_title"> 滞纳金比例: </div>
                <div class="detail_header_content"> {{ detail_data.lateFeeRatio + '%' }} </div>
              </el-col>
              <!-- 退租 -->
              <el-col :span="12" class="mt-28px shadow pt-10px" v-if="detail_data.status == 4">
                <el-row>
                  <el-col class="font-600 text-16px">账单结算</el-col>
                  <el-col class="mt-18px">
                    <el-table height="250" :data="RetreatDetail.billInfo">
                      <el-table-column prop="costTypeName" label="费用类型" />
                      <el-table-column prop="receivable" label="账单金额" />
                      <el-table-column prop="receivable" label="需收/付金额" />
                      <el-table-column prop="payDate" label="应收/付日期" />
                    </el-table>
                  </el-col>
                </el-row>
              </el-col>
              <el-col :span="12" class="mt-28px shadow pt-10px" v-if="detail_data.status == 4">
                <el-row>
                  <el-col class="font-600 text-16px">保证金</el-col>
                  <el-col class="mt-18px">
                    <el-table height="250">
                      <el-table-column prop="costTypeName" label="费用类型" />
                      <el-table-column prop="receivable" label="需收/付金额" />
                      <el-table-column prop="payDate" label="应收/付日期" />
                    </el-table>
                  </el-col>
                </el-row>
              </el-col>
              <el-col class="mt-18px">
                <el-row :data="fileList">
                  <el-col class="font-600 text-16px">合同附件</el-col>
                  <el-col class="mt-18px">
                    <el-table>
                      <el-table-column prop="date" label="文件名" />
                      <el-table-column prop="name" label="操作人" />
                      <el-table-column prop="address" label="操作时间" />
                      <el-table-column prop="address" label="操作" />
                    </el-table>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </el-card>
          <el-card class="mt-18px">
            <template #header>
              <div class="card-header">
                <span class="text-16px">条款信息</span>
              </div>
            </template>
            <el-row>
              <el-col class="font-600 text-16px">租赁条款</el-col>
              <el-col class="mt-18px">
                <ZptkList :data="detail_data" />
              </el-col>
              <el-col class="font-600 mt-28px text-16px">物业条款</el-col>
              <el-col class="mt-18px">
                <WytkList :data="detail_data" />
              </el-col>
            </el-row>
          </el-card>
        </div>
        <div class="right_item mt-18px">
          <ProcessInstanceTaskList :process-instance="processInstance" />
          <ProcessInstanceBpmnViewer
            :id="`${id}`"
            :bpmn-xml="bpmnXml"
            :process-instance="processInstance"
            :loading="processInstanceLoading"
          />
        </div>
      </el-scrollbar>
    </div>
  </div>
  <!-- 弹窗：转派审批人 -->
  <TaskTransferForm ref="taskTransferFormRef" @success="getDetail" />
  <!-- 弹窗：回退节点 -->
  <TaskReturnForm ref="taskReturnFormRef" @success="getDetail" />
  <!-- 弹窗：委派，将任务委派给别人处理，处理完成后，会重新回到原审批人手中-->
  <TaskDelegateForm ref="taskDelegateForm" @success="getDetail" />
  <!-- 弹窗：加签，当前任务审批人为A，向前加签选了一个C，则需要C先审批，然后再是A审批，向后加签B，A审批完，需要B再审批完，才算完成这个任务节点 -->
  <TaskSignCreateForm ref="taskSignCreateFormRef" @success="getDetail" />
  <!-- 查看原合同弹窗 -->
  <el-drawer
    title="查看原合同"
    v-model="show_contract_detail_dialog"
    size="80%"
    style="background-color: #f5f7f9"
  >
    <template #header>
      <span class="text-16px text-#000">查看原合同</span>
    </template>
    <ContractDetail
      :detailId="contractId"
      :type="'history'"
      @close="show_contract_detail_dialog = false"
      ref="ContractDetail_ref"
    />
  </el-drawer>
</template>
<script setup lang="ts">
import { Api } from '@/api/contract/contractList/index'
import * as ProcessInstanceApi from '@/api/bpm/processInstance'
import * as DefinitionApi from '@/api/bpm/definition'
import * as UserApi from '@/api/system/user'
import * as TaskApi from '@/api/bpm/task'
import { isEmpty } from '@/utils/is'
import ContractDetail from '@/views/contract/contractList/contractList_detail.vue'
const { proxy } = getCurrentInstance() as any
const userId = useUserStore().getUser.id // 当前登录的编号
import { useUserStore } from '@/store/modules/user'
const message = useMessage()
import { TagTerminationApi } from '@/api/bus/tag/termination/index.ts'

import TaskReturnForm from '@/views/bpm/processInstance/detail/dialog/TaskReturnForm.vue'
import TaskDelegateForm from '@/views/bpm/processInstance/detail/dialog/TaskDelegateForm.vue'
import TaskTransferForm from '@/views/bpm/processInstance/detail/dialog/TaskTransferForm.vue'
import TaskSignCreateForm from '@/views/bpm/processInstance/detail/dialog/TaskSignCreateForm.vue'
//审批记录
import ProcessInstanceTaskList from '@/views/bpm/processInstance/detail/ProcessInstanceTaskList.vue'
//流程图
import ProcessInstanceBpmnViewer from '@/views/bpm/processInstance/detail/ProcessInstanceBpmnViewer.vue'
//条款
import ZptkList from '@/views/contract/contractList/component/zptk_list.vue'
import WytkList from '@/views/contract/contractList/component/wytk_list.vue'
import { type } from '../../../../components/Cropper/src/types'

const processInstanceLoading = ref(false)
const auditRule = reactive({
  reason: [{ required: true, message: '审批建议不能为空', trigger: 'blur' }]
})
const contractId = ref('')
const activities = ref([
  {
    index: 1,
    content: '审核任务'
  },
  {
    index: 2,
    content: '审批信息'
  },
  {
    index: 3,
    content: '审批流程'
  }
])
let id = ''
const scroll_ref_list = ref([])
onMounted(() => {
  setTimeout(() => {
    scroll_ref_list.value = []
    const right_item = document.getElementsByClassName('right_item')
    for (let index = 0; index < right_item.length; index++) {
      if (index === 0) {
        scroll_ref_list.value.push(right_item[index].clientHeight)
      } else {
        let max = scroll_ref_list.value[scroll_ref_list.value.length - 1]
        scroll_ref_list.value.push(max + right_item[index].clientHeight)
      }
    }
  }, 2000)
})
const scroll = (e) => {
  if (scroll_change.value) {
    const scrollTop = e.scrollTop
    for (let index = 0; index < scroll_ref_list.value.length; index++) {
      if (scrollTop < scroll_ref_list.value[index]) {
        now_index.value = index + 1
        break
      }
    }
  }
}
let scroll_change = ref(true)
const now_index = ref(1)
const scroll_ref = ref(null)
const scroll_height = ref(0)
const to_activity = (index) => {
  scroll_ref_list.value = []
  const right_item = document.getElementsByClassName('right_item')
  for (let index = 0; index < right_item.length; index++) {
    if (index === 0) {
      scroll_ref_list.value.push(right_item[index].clientHeight)
    } else {
      let max = scroll_ref_list.value[scroll_ref_list.value.length - 1]
      scroll_ref_list.value.push(max + right_item[index].clientHeight + 18)
    }
  }
  scroll_change.value = false
  now_index.value = index
  scroll_height.value = scroll_ref_list.value[index - 2] || 0
  scroll_ref.value.scrollTo(0, scroll_height.value)
  setTimeout(() => {
    scroll_change.value = true
  }, 500)
}
//流程图
const processInstance = ref({})
const bpmnXml = ref('')
const processDefinition = ref({})
//打开关闭
const checkedBuild = ref([])
const detail_data = ref({})
const fileList = ref([])
const emit = defineEmits(['close'])
function open(detail_id) {
  contractId.value = detail_id
  Api.contractgetId(detail_id).then((res) => {
    detail_data.value = res
    checkedBuild.value = JSON.parse(res.checkedBuild)
    getRetreatdetail(detail_id)
  })
  getretreatReason()
  getquitType()
  //通过id获取审批记录
  Api.getOneByContractId({
    contractId: detail_id
  }).then((res) => {
    if (res.contractAnnex) {
      fileList.value = JSON.parse(res.contractAnnex)
    }
    id = res.processInstanceId
    getDetail()
    // 获得用户列表
    UserApi.getSimpleUserList().then((res) => {
      userOptions.value = res
    })

    ProcessInstanceApi.getProcessInstance(res.processInstanceId).then((res) => {
      processInstance.value = res
      processDefinition.value = res.processDefinition
      DefinitionApi.getProcessDefinition(res.processDefinition.id).then((res) => {
        bpmnXml.value = res.bpmnXml
      })
    })
  })
}
defineExpose({ open })
//审批
const handleAudit = async (task, pass) => {
  // 1.1 获得对应表单
  const index = runningTasks.value.indexOf(task)
  const auditFormRef = proxy.$refs['form' + index][0]
  // 1.2 校验表单
  const elForm = unref(auditFormRef)
  if (!elForm) return
  const valid = await elForm.validate()
  if (!valid) return

  // 2.1 提交审批
  const data = {
    id: task.id,
    reason: auditForms.value[index].reason,
    copyUserIds: auditForms.value[index].copyUserIds
  }
  if (pass) {
    // 审批通过，并且有额外的 approveForm 表单，需要校验 + 拼接到 data 表单里提交
    const formCreateApi = approveFormFApis.value[index]
    if (formCreateApi) {
      await formCreateApi.validate()
      data.variables = approveForms.value[index].value
    }
    await TaskApi.approveTask(data)
    message.success('审批通过成功')
    emit('close')
  } else {
    await TaskApi.rejectTask(data)
    message.success('审批不通过成功')
    emit('close')
  }
  // 2.2 加载最新数据
  getDetail()
}
/** 转派审批人 */
const taskTransferFormRef = ref()
const openTaskUpdateAssigneeForm = (id) => {
  taskTransferFormRef.value.open(id)
}
//委派
const taskDelegateForm = ref()
const handleDelegate = async (task) => {
  taskDelegateForm.value.open(task.id)
}
/** 处理审批加签的操作 */
const taskSignCreateFormRef = ref()
const handleSign = async (task: any) => {
  taskSignCreateFormRef.value.open(task.id)
}
/** 处理审批回退的操作 */
const taskReturnFormRef = ref()
const handleBack = async (task: any) => {
  taskReturnFormRef.value.open(task.id)
}
///---------------------------------------
/** 加载任务列表 */
const tasks = ref<TaskApi.TaskVO[]>([])
const runningTasks = ref<TaskApi.TaskVO[]>([])
const auditForms = ref<{ reason: string; copyUserIds: number[] }[]>([])
const approveForms = ref<any[]>([])
const approveFormFApis = ref<any[]>([])
const tasksLoad = ref(false)

const getTaskList = async () => {
  runningTasks.value = []
  auditForms.value = []
  approveForms.value = []
  approveFormFApis.value = []
  try {
    // 获得未取消的任务
    tasksLoad.value = true
    const data = await TaskApi.getTaskListByProcessInstanceId(id)
    tasks.value = []
    // 1.1 移除已取消的审批
    data.forEach((task) => {
      if (task.status !== 4) {
        tasks.value.push(task)
      }
    })
    // 1.2 排序，将未完成的排在前面，已完成的排在后面；
    tasks.value.sort((a, b) => {
      // 有已完成的情况，按照完成时间倒序
      if (a.endTime && b.endTime) {
        return b.endTime - a.endTime
      } else if (a.endTime) {
        return 1
      } else if (b.endTime) {
        return -1
        // 都是未完成，按照创建时间倒序
      } else {
        return b.createTime - a.createTime
      }
    })

    // 获得需要自己审批的任务
    loadRunningTask(tasks.value)
  } finally {
    tasksLoad.value = false
  }
}

/**
 * 设置 runningTasks 中的任务
 */
const loadRunningTask = async (tasks) => {
  await tasks.forEach((task) => {
    if (!isEmpty(task.children)) {
      loadRunningTask(task.children)
    }
    // 2.1 只有待处理才需要
    if (task.status !== 1 && task.status !== 6) {
      return
    }
    // 2.2 自己不是处理人
    if (!task.assigneeUser || task.assigneeUser.id !== userId) {
      return
    }

    // 2.3 添加到处理任务
    runningTasks.value.push({ ...task })

    auditForms.value.push({
      reason: '',
      copyUserIds: []
    })
    // 2.4 处理 approve 表单
    if (task.formId && task.formConf) {
      const approveForm = {}
      setConfAndFields2(approveForm, task.formConf, task.formFields, task.formVariable)
      approveForms.value.push(approveForm)
    } else {
      approveForms.value.push({}) // 占位，避免为空
    }
  })
  if (runningTasks.value.length == 0) {
    activities.value = [
      {
        index: 2,
        content: '审批信息'
      },
      {
        index: 3,
        content: '审批流程'
      }
    ]
    now_index.value = 2
  }
}
//获得用户列表
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
/** 获得详情 */
const getDetail = () => {
  // 2. 获得流程任务列表（审批记录）

  processInstanceLoading.value = true
  setTimeout(() => {
    processInstanceLoading.value = false
  }, 2000)
  getTaskList()
}
const changeHeaderText = (index) => {
  if (index == 16) {
    return '新建合同审批'
  } else if (index == 4) {
    return '退租合同审批'
  } else if (index == 6) {
    return '退租协议'
  } else if (index == 14) {
    return '续租合同审批'
  }
}
//获取退租详情
const RetreatDetail = ref({})
const getRetreatdetail = (id) => {
  Api.getRetreatContract(id).then((res) => {
    RetreatDetail.value = res
    RetreatDetail.value.retreatReason = JSON.parse(res.retreatReason)
    RetreatDetail.value.billInfo = JSON.parse(res.billInfo)
    RetreatDetail.value.bondInfo = JSON.parse(res.bondInfo)
    const label = []
    retreatReason_list.value.forEach((item) => {
      if (RetreatDetail.value.retreatReason.includes(item.id)) {
        label.push(item.name)
      }
    })
    RetreatDetail.value.retreatReasonLabel = label.join(',')
  })
}
//退租类型字典
const quitType = ref([])
const getquitType = () => {
  Api.getProject({
    dictType: 'RETREAT_TYPE',
    pageNo: 1,
    pageSize: 10
  }).then((res) => {
    quitType.value = res.list
  })
}
//获取退租原因
const retreatReason_list = ref([])
const getretreatReason = () => {
  TagTerminationApi.getTagTerminationPage({
    pageNo: 1,
    pageSize: 10
  }).then((res) => {
    retreatReason_list.value = res.list
  })
}
///查看原合同弹窗
const ContractDetail_ref = ref(null)

const show_contract_detail = () => {
  show_contract_detail_dialog.value = true
  ContractDetail_ref.value.open(contractId.value)
}
const show_contract_detail_dialog = ref(false)
</script>
<style lang="scss" scoped>
.detail_header_title {
  font-size: 14px;
  color: #999;
  margin: 15px 5px 5px 5px;
}
.detail_header_content {
  font-size: 14px;
  color: #333;
  margin: 10px 0 0 5px;
}
.left {
  width: 15%;
  min-width: 100px;
  position: sticky;
  top: 110px;
  .active {
    color: #409eff;
  }
}
.right {
  width: 100%;
  min-width: 80vh;
  max-height: calc(100vh - 200px);
  overflow: auto;
}
::v-deep .el-timeline {
  padding: 0;
}
</style>
