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
                    viewTitle == 'stuff_stock_enter'
                      ? '耗材入库清单'
                      : viewTitle == 'stuff_hand_out'
                        ? '耗材派发'
                        : viewTitle == 'stuff_retreat_out'
                          ? '耗材退库'
                          : viewTitle == 'stuff_transfer'
                            ? '耗材调拨'
                            : viewTitle == 'stuff_handle'
                              ? '耗材处置'
                              : viewTitle == 'stuff_adjust'
                                ? '耗材调整'
                                : viewTitle == 'stuff_receive'
                                  ? '耗材领用'
                                  : viewTitle == 'stuff_stock_receive'
                                    ? '库存耗材领用'
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
                  <div class="text-18px"> {{ info?.number || info?.processNumber }} </div>
                </el-col>
                <!-- 入库 -->
                <template v-if="viewTitle == 'stuff_stock_enter'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 入库处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.enterUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 入库仓库: </div>
                    <div class="text-18px">
                      {{ info?.depositoryName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 合计金额: </div>
                    <div class="text-18px">
                      {{ info?.totalPrice || '--' }}
                    </div>
                  </el-col>
                </template>
                <!-- 派发 -->
                <template v-else-if="viewTitle == 'stuff_hand_out'">
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
                    <div class="c-#999 text-14px"> 出库仓库: </div>
                    <div class="text-18px">
                      {{ info?.departmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 派发处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.muserUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 退库 -->
                <template v-else-if="viewTitle == 'stuff_retreat_out'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 退库人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.retreatUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 退库部门: </div>
                    <div class="text-18px">
                      {{ info?.departmentName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 入库仓库: </div>
                    <div class="text-18px">
                      {{ info?.depositoryName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 退库处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.muserUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>
                </template>
                <!-- 调拨 -->
                <template v-else-if="viewTitle == 'stuff_transfer'">
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
                    <div class="c-#999 text-14px"> 调入仓库: </div>
                    <div class="text-18px">
                      {{ info?.inDepositoryName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 调出仓库: </div>
                    <div class="text-18px">
                      {{ info?.outDepositorName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 合计数量: </div>
                    <div class="text-18px">
                      {{ info?.totalNum || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 合计金额: </div>
                    <div class="text-18px">
                      {{ info?.totalPrice || '--' }}
                    </div>
                  </el-col>
                </template>
                <!-- 处置 -->
                <template v-else-if="viewTitle == 'stuff_handle'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px">处置发起人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.muserUid">{{ user.nickname }}</span>
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
                    <div class="c-#999 text-14px"> 处置仓库: </div>
                    <div class="text-18px">
                      {{ info?.depositoryName || '--' }}
                    </div>
                  </el-col>
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 处置金额: </div>
                    <div class="text-18px">
                      {{ info?.totalPrice || '--' }}
                    </div>
                  </el-col>
                </template>
                <!-- 调整 stuff_adjust -->

                <template v-else-if="viewTitle == 'stuff_adjust'">
                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px">调整处理人: </div>
                    <div class="text-18px">
                      <template v-for="user in prop.userOptions" :key="user.id">
                        <span v-if="user.id === info?.muserUid">{{ user.nickname }}</span>
                      </template>
                    </div>
                  </el-col>

                  <el-col :span="24" class="m-b-20px">
                    <div class="c-#999 text-14px"> 调整仓库: </div>
                    <div class="text-18px">
                      {{ info?.depositoryName || '--' }}
                    </div>
                  </el-col>
                </template>

                <el-col :span="24" class="m-b-20px !w-100%">
                  <div class="c-#999 text-14px m-b-10px"> 物料列表: </div>
                  <el-table
                    :data="list"
                    v-loading="loading"
                    border
                    :stripe="true"
                    :show-overflow-tooltip="true"
                  >
                    <el-table-column label="物料名称" prop="name" width="140" />
                    <el-table-column label="物料编码" prop="number" width="140" />
                    <!-- 入库 -->
                    <template v-if="viewTitle == 'stuff_stock_enter'">
                      <el-table-column label="计量单位" prop="name" width="140" />
                      <el-table-column label="入库单价" prop="price" width="140" />
                      <el-table-column label="数量" prop="num" width="140" />
                      <el-table-column label="总价" prop="totalPrice" width="140" />
                      <el-table-column label="物料照片" prop="modelName" width="140">
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
                    </template>
                    <!-- 派发 -->
                    <template v-else-if="viewTitle == 'stuff_hand_out'">
                      <el-table-column label="计量单位" prop="name" width="140" />
                      <el-table-column label="可用库存" prop="num" width="140" />
                      <el-table-column label="派发数量" prop="handoutNum" width="140" />
                      <el-table-column label="品牌" prop="brand" width="140" />
                      <el-table-column label="规格型号" prop="modelName" width="140" />

                      <el-table-column label="物料照片" prop="modelName" width="140">
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
                    </template>
                    <!-- 退库 -->
                    <template v-else-if="viewTitle == 'stuff_retreat_out'">
                      <el-table-column label="计量单位" prop="name" width="140" />
                      <el-table-column label="可用库存" prop="num" width="140" />
                      <el-table-column label="退库数量" prop="retreatNum" width="140" />
                      <el-table-column label="品牌" prop="brand" width="140" />
                      <el-table-column label="规格型号" prop="modelName" width="140" />

                      <el-table-column label="物料照片" prop="modelName" width="140">
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
                    </template>
                    <!-- 调拨 -->
                    <template v-else-if="viewTitle == 'stuff_transfer'">
                      <el-table-column label="物料分类" prop="categoryName" width="140" />
                      <el-table-column label="可用库存" prop="num" width="140" />

                      <el-table-column label="单价" prop="price" width="140" />
                      <el-table-column label="本次调拨数量" prop="num" width="140" />
                      <el-table-column label="本次调拨金额" prop="totalPrice" width="140" />
                    </template>
                    <!-- 处置 -->
                    <template v-else-if="viewTitle == 'stuff_handle'">
                      <el-table-column label="物料分类" prop="categoryName" width="140" />
                      <el-table-column label="可用库存" prop="num" width="140" />
                      <el-table-column label="单价" prop="price" width="140" />
                      <el-table-column label="本次处置数量" prop="num" width="140" />
                      <el-table-column label="本次处置金额" prop="totalPrice" width="140" />
                    </template>
                    <!-- 调整 -->
                    <template v-else-if="viewTitle == 'stuff_adjust'">
                      <el-table-column label="物料分类" prop="categoryName" width="140" />
                      <el-table-column label="调整前数量" prop="handoutNum" width="140" />
                      <el-table-column label="调整前单价" prop="retreatNum" width="140" />
                      <el-table-column label="调整前总价" prop="totalPrice" width="140">
                        <template #default="scope">
                          {{ (scope.row.handoutNum * scope.row.retreatNum).toFixed(2) }}
                        </template>
                      </el-table-column>
                      <el-table-column label="本次调整数量" prop="num" width="140">
                        <template #default="scope">
                          {{ scope.row.num - scope.row.handoutNum }}
                        </template>
                      </el-table-column>
                      <el-table-column label="本次调整单价" prop="price" width="140">
                        <template #default="scope">
                          {{ scope.row.price - scope.row.retreatNum }}
                        </template>
                      </el-table-column>
                      <el-table-column label="本次调整总价" prop="totalPrice" width="140">
                        <template #default="scope">
                          {{
                            Number(scope.row.totalPrice) -
                            Number(scope.row.handoutNum * scope.row.retreatNum)
                          }}
                        </template>
                      </el-table-column>
                      <el-table-column label="调整后数量" prop="num" width="140" />
                      <el-table-column label="调整后单价" prop="price" width="140" />
                      <el-table-column label="调整后总价" prop="totalPrice" width="140" />
                    </template>
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
import { PropertyStuffProcessApi } from '@/api/bus/stuff/process'
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
  processCode: undefined
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
  queryParams.processCode = info.value.processCode
  const res = await PropertyStuffProcessApi.getPropertyStuffProcessPage(queryParams)
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
