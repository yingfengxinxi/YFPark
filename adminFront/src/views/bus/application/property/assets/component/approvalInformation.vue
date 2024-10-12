<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ElDrawer :title="dialogTitle" v-model="dialogVisible" size="60%">
    <div class="flex">
      <div class="w-15% min-w-100px sticky top-110px">
        <el-timeline>
          <el-timeline-item
            v-for="(activity, index) in activities"
            :type="now_index === activity.index ? 'primary' : 'default'"
            :hollow="true"
            :key="index"
          >
            <span
              class="cursor-pointer"
              :class="now_index == activity.index ? 'c-#409eff' : ''"
              @click="to_activity(activity.index)"
              >{{ activity.content }}</span
            >
          </el-timeline-item>
        </el-timeline>
      </div>
      <div class="flex-1 w-0 h-[calc(100vh-100px)]">
        <el-scrollbar @scroll="scroll" height="calc(100vh-100px)" ref="scroll_ref">
          <div class="right_item p-10px">
            <!--  style="border: 1px solid var(--el-card-border-color) !important" -->
            <el-card>
              <template #header>
                <div class="card-header">
                  <span class="text-16px">审批信息</span>
                </div>
              </template>
              <el-row :gutter="20" class="lh-30px">
                <!-- 退租审批 -->
                <el-col :span="24" class="font-600 text-16px m-b-20px"
                  >{{
                    viewTitle == 'put_depository'
                      ? '资产入库清单'
                      : viewTitle == 'change_property'
                        ? '变更领用人'
                        : viewTitle == 'handle_property'
                          ? '资产处置'
                          : viewTitle == 'handout_property'
                            ? '资产派发清单'
                            : viewTitle == 'return_property'
                              ? '资产退库清单'
                              : viewTitle == 'lendout_property'
                                ? '资产借出清单'
                                : viewTitle == 'revert_property'
                                  ? '资产归还清单'
                                  : viewTitle == 'repair_property'
                                    ? '资产维修'
                                    : viewTitle == 'transfer_property'
                                      ? '资产调拨'
                                      : viewTitle == 'property_maintain'
                                        ? '资产保养'
                                        : ''
                  }}
                  <dict-tag :type="DICT_TYPE.PROPERTY_AUDIT_STATUS" :value="info.status" />
                </el-col>
                <el-col :span="24" class="m-b-20px">
                  <div class="c-#999 text-14px"> 编号: </div>
                  <div class="text-18px"> {{ info?.id }}</div>
                </el-col>
                <el-col :span="24" class="m-b-20px">
                  <div class="c-#999 text-14px"> 单据编号: </div>
                  <div class="text-18px"> {{ info?.number }} </div>
                </el-col>
                <!-- 入库 -->
                <template v-if="viewTitle == 'put_depository'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 入库处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.cuserUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 派发 -->
                <template v-else-if="viewTitle == 'handout_property'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 领用人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.receiveUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 领用部门: </div>
                    <div class="text-18px">
                      {{ info?.departmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 派发处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.operateUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 退库 -->
                <template v-else-if="viewTitle == 'return_property'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 退库后使用部门: </div>
                    <div class="text-18px">
                      {{ info?.departmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 退库处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.operateUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 借用 -->
                <template v-else-if="viewTitle == 'lendout_property'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 借用人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.lendUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 借用部门: </div>
                    <div class="text-18px">
                      {{ info?.departmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 借出处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.operateUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 归还 -->
                <template v-else-if="viewTitle == 'revert_property'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 归还后使用部门: </div>
                    <div class="text-18px">
                      {{ info?.departmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 归还处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.operateUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 调拨 -->
                <template v-else-if="viewTitle == 'transfer_property'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 调出管理员: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.outAdminUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 调入管理员: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.inAdminUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 调入位置: </div>
                    <div class="text-18px">
                      {{ info?.inLocationName }}
                    </div>
                  </el-col>
                </template>
                <!-- 变更 -->
                <template v-else-if="viewTitle == 'change_property'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 变更后使用人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.afterUseUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 变更后使用部门: </div>
                    <div class="text-18px">
                      {{ info?.afterUseDepartmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 变更处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.operateUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 保养 -->
                <template v-else-if="viewTitle == 'property_maintain'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 保养人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.maintainUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 保养部门: </div>
                    <div class="text-18px">
                      {{ info?.departmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 保养处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.operateUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 维修 -->
                <template v-else-if="viewTitle == 'repair_property'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 报修人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.repairUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 报修部门: </div>
                    <div class="text-18px">
                      {{ info?.repairDepartmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 维修处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.operateUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 预计维修金额: </div>
                    <div class="text-18px">
                      {{ info?.expectRepairPrice || '--' }}
                    </div>
                  </el-col>
                </template>
                <!-- 处置 -->
                <template v-else-if="viewTitle == 'handle_property'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px">处置发起人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.cuserUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 发起部门: </div>
                    <div class="text-18px">
                      {{ info?.departmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 处置金额: </div>
                    <div class="text-18px">
                      {{ info?.handleAmount || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 处置费用: </div>
                    <div class="text-18px">
                      {{ info?.handleExpenses || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 处置类型: </div>
                    <div class="text-18px">
                      <dict-text :type="DICT_TYPE.DISPOSEEOF_TYPE" :value="info.handleType" />
                    </div>
                  </el-col>
                </template>

                <el-col :span="24" class="m-b-20px !w-100%">
                  <div class="c-#999 text-14px m-b-10px"> 资产列表: </div>
                  <el-table
                    :data="list"
                    border
                    v-loading="loading"
                    :stripe="true"
                    :show-overflow-tooltip="true"
                  >
                    <el-table-column label="#" prop="id" width="100" />
                    <el-table-column
                      label="资产编码"
                      align="center"
                      prop="propertyNumber"
                      :formatter="tableColumnEmptyPlaceholder"
                      width="160"
                    >
                      <template #default="scope">
                        <span>{{ scope.row.propertyNumber || '--' }}</span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="资产名称"
                      align="center"
                      prop="name"
                      :formatter="tableColumnEmptyPlaceholder"
                      width="160"
                    />
                    <el-table-column
                      label="资产分类"
                      align="center"
                      prop="categoryName"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column label="资产状态" align="center" prop="status" width="160">
                      <template #default="scope">
                        <dict-tag :type="DICT_TYPE.PROPERTYSTATUS" :value="scope.row.status" />
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="品牌"
                      align="center"
                      prop="brand"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="所在位置"
                      align="center"
                      prop="positionName"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />

                    <el-table-column label="入库时间" align="center" prop="stockTime" width="160px">
                      <template #default="scope">
                        <div v-if="scope.row.stockTime">{{
                          dateFormatter2(scope.row, 'stockTime', scope.row.stockTime)
                        }}</div>
                        <div v-else>--</div>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="型号"
                      align="center"
                      prop="modelName"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="设备序列号"
                      align="center"
                      prop="deviceCode"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="管理员"
                      align="center"
                      prop="adminName"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="购置时间"
                      align="center"
                      prop="purchaseTime"
                      width="160px"
                    >
                      <template #default="scope">
                        <div v-if="scope.row.purchaseTime">{{
                          dateFormatter2(scope.row, 'purchaseTime', scope.row.purchaseTime)
                        }}</div>
                        <div v-else>--</div>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="购置方式"
                      align="center"
                      prop="purchaseType"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    >
                      <template #default="scope">
                        <dict-text
                          :type="DICT_TYPE.PROPERTY_PURCHASETYPE"
                          :value="scope.row.purchaseType"
                        />
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="购置金额(含税)"
                      align="center"
                      prop="purchaseAmount"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="预计使用期限(月)"
                      align="center"
                      prop="expectMonths"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="备注"
                      align="center"
                      prop="remark"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="资产照片"
                      align="center"
                      prop="imageUrl"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    >
                      <template #default="scope">
                        <span v-if="scope.row.imageUrl">
                          <el-image
                            preview-teleported="true"
                            style="width: 60px; height: 60px"
                            :src="scope.row.imageUrl"
                            :zoom-rate="1.2"
                            :max-scale="7"
                            :min-scale="0.2"
                            :preview-src-list="[scope.row.imageUrl]"
                            :initial-index="0"
                            fit="cover"
                        /></span>
                        <span v-else>--</span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="使用人"
                      align="center"
                      prop="userId"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    >
                      <template #default="scope">
                        <template v-if="scope.row.userId">
                          <template v-for="user in userOptions" :key="user.id">
                            <span v-if="user.id === scope.row.userId">{{ user.nickname }}</span>
                          </template>
                        </template>
                        <span v-else>--</span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="目标房源"
                      align="center"
                      prop="buildBind"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    >
                      <template #default="scope">
                        <span v-if="scope.row.buildBind"
                          >{{ JSON.parse(scope.row.buildBind).buildName }}/{{
                            JSON.parse(scope.row.buildBind).layerName
                          }}/{{ JSON.parse(scope.row.buildBind).roomName }}</span
                        >
                        <span v-else>--</span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="使用部门"
                      align="center"
                      prop="departmentId"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="领用日期"
                      align="center"
                      prop="receiveTime"
                      :formatter="dateFormatter"
                      width="160px"
                    >
                      <template #default="scope">
                        <div v-if="scope.row.receiveTime">{{
                          dateFormatter(scope.row, 'receiveTime', scope.row.receiveTime)
                        }}</div>
                        <div v-else>--</div>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="保养到期时间"
                      align="center"
                      prop="maintainTime"
                      :formatter="dateFormatter"
                      width="160px"
                    >
                      <template #default="scope">
                        <div v-if="scope.row.maintainTime">{{
                          dateFormatter(scope.row, 'maintainTime', scope.row.maintainTime)
                        }}</div>
                        <div v-else>--</div>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="保养说明"
                      align="center"
                      prop="maintainInfo"
                      width="160"
                      :formatter="tableColumnEmptyPlaceholder"
                    />
                    <el-table-column
                      label="预计折旧期限(月)"
                      align="center"
                      prop="depreciationMonths"
                      width="160"
                    />
                  </el-table>
                  <Pagination
                    :total="total"
                    v-model:page="queryParams.pageNo"
                    v-model:limit="queryParams.pageSize"
                    @pagination="getList"
                  />
                </el-col>
              </el-row>
            </el-card>
            <!-- <el-card
              class="mt-18px"
              shadow="never"
              style="border: 1px solid var(--el-card-border-color) !important"
            >
              <template #header>
                <div class="card-header">
                  <span class="text-16px">资产列表</span>
                </div>
              </template>
              资产
            </el-card> -->
          </div>
          <div class="right_item mt-18px p-10px p-t-0px">
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
  </ElDrawer>
</template>
<script setup lang="ts">
import * as ProcessInstanceApi from '@/api/bpm/processInstance'
import * as DefinitionApi from '@/api/bpm/definition'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
//审批记录
import ProcessInstanceTaskList from '@/views/bpm/processInstance/detail/ProcessInstanceTaskList.vue'
//流程图
import ProcessInstanceBpmnViewer from '@/views/bpm/processInstance/detail/ProcessInstanceBpmnViewer.vue'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
/** 派发 表单 */
defineOptions({ name: 'ApprovalInformation' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('审批信息') // 弹窗的标题
const activities = ref([
  {
    index: 1,
    content: '审批信息'
  },
  {
    index: 2,
    content: '审批流程'
  }
])
const list = ref([])
const now_index = ref(1)
const prop = defineProps({
  userOptions: {
    type: Array,
    default: function () {
      return []
    }
  }
})
watch(
  () => prop.userOptions,
  () => {},
  { deep: true, immediate: true }
)

let id = ''
const info = ref({})
const scroll_ref_list = ref([])
const viewTitle = ref('')

let scroll_change = ref(true)
const scroll_ref = ref(null)
const scroll_height = ref(0)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  approveNumber: undefined
})
const total = ref(0)
const loading = ref(false)
//流程图
const processInstance = ref({})
const bpmnXml = ref('')
const processInstanceLoading = ref(false)
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

/** 查询列表 */
const getList = async () => {
  queryParams.approveNumber = info.value.processCode
  const res = await PropertyApi.getPropertyPageByApprove(queryParams)
  list.value = res.list
  total.value = res.total
}
/** 查询流程图 */
const getOneByContractId = async () => {
  ProcessInstanceApi.getProcessInstance(info.value.processCode).then((res) => {
    processInstance.value = res
    DefinitionApi.getProcessDefinition(res.processDefinition.id).then((res) => {
      bpmnXml.value = res.bpmnXml
    })
  })
}

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
/** 打开弹窗 */
const open = async (title, row) => {
  console.log(row, '审批信息')
  viewTitle.value = title
  id = row.id
  info.value = row
  dialogVisible.value = true
  await getList()
  await getOneByContractId()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
