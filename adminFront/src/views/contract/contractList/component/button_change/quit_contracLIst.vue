<template>
  <div>
    <div class="flex bg-white px-20px pt-20px items-center">
      <Icon
        icon="ep:arrow-left"
        font="20px"
        class="text-20px cursor-pointer"
        @click="emit('close')"
      />
      <span class="ml-10px font-600 text-18px">{{
        typeValue == 'view' ? '退租协议' : '新建退租协议'
      }}</span>
    </div>
    <div class="flex items-center bg-white font-600 justify-between">
      <div class="flex items-center">
        <img src="../image/contracList.png" alt="" class="img" />
        <div>
          <div class="text-16px">租客:&nbsp;{{ detail.ownerName }}</div>
          <div class="text-14px">合同编号:&nbsp;{{ detail.contractNumber }} </div>
          <div class="text-12px" v-if="printTemplate_url"
            >打印模板:&nbsp;{{ printTemplate_url }}
          </div>
        </div>
      </div>
      <div>
        <el-button
          type="primary"
          @click="getRetreatTemplate_dialog = true"
          v-if="typeValue == 'view'"
          >生成打印模板</el-button
        >
      </div>
    </div>
  </div>
  <div class="mt-18px">
    <el-row :gutter="20">
      <el-col :span="12">
        <ContentWrapBorder :header_data="'退租信息'">
          <el-form :label-position="'top'" :model="form" ref="tzxx_form">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item
                  label="退租类型"
                  :rules="[{ required: true, message: '请选择退租类型', trigger: 'change' }]"
                  prop="retreatType"
                >
                  <el-select
                    v-model="form.retreatType"
                    placeholder="请选择退租类型"
                    clearable
                    :disabled="typeValue == 'view'"
                  >
                    <el-option
                      v-for="item in quitType"
                      :key="item.value"
                      :label="item.label"
                      :value="Number(item.value)"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  label="退租日期"
                  :rules="[{ required: true, message: '请选择退租日期', trigger: 'change' }]"
                  prop="retreatDate"
                >
                  <el-date-picker
                    v-model="form.retreatDate"
                    type="date"
                    placeholder="请选择退租日期"
                    style="width: 100%"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    :disabled="typeValue == 'view'"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工商注册地址变更日期">
                  <el-date-picker
                    v-model="form.bccChangeDate"
                    type="date"
                    placeholder="请选择工商注册地址变更日期"
                    style="width: 100%"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    :disabled="typeValue == 'view'"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="协议签订日期">
                  <el-date-picker
                    v-model="form.applyRetreatDate"
                    type="date"
                    placeholder="请选择协议签订日期"
                    style="width: 100%"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    :disabled="typeValue == 'view'"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  label="退租原因"
                  :rules="[{ required: true, message: '请选择退租原因', trigger: 'change' }]"
                  prop="retreatReason"
                >
                  <el-select v-model="form.retreatReason" multiple :disabled="typeValue == 'view'">
                    <el-option
                      v-for="item in retreatReason_list"
                      :key="item.id"
                      :value="item.id"
                      :label="item.name"
                    />
                    <template #footer>
                      <el-button type="primary" size="mini" @click="openTagTerminationForm"
                        >添加</el-button
                      >
                    </template>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </ContentWrapBorder>
      </el-col>
      <el-col :span="12">
        <ContentWrapBorder :header_data="'房源信息'">
          <el-table :data="checkedBuild">
            <el-table-column label="所属楼宇" prop="villageName" />
            <el-table-column label="楼层/房号">
              <template #default="{ row }">
                <span>{{ row.buildName }}/{{ row.layerName }}/{{ row.roomName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="面积" prop="rentalArea" />
          </el-table>
        </ContentWrapBorder>
      </el-col>
    </el-row>
  </div>
  <div class="mt-18px p-20px bg-white radiue">
    <div :header_data="'账单结算'">
      <div class="row flex justify-between items-center pb-2 border_b">
        <div class="text-16px"> 账单结算 </div>
        <div>
          <el-button @click="payment_drawer = true" v-if="typeValue != 'view'">添加收款</el-button
          ><el-button @click="collection_drawer = true" v-if="typeValue != 'view'"
            >添加付款</el-button
          >
        </div>
      </div>
      <el-table :data="check_list">
        <el-table-column label="费用类型" prop="costTypeName">
          <template #default="{ row, index }">
            <div class="flex items-center">
              <span>{{ row.costTypeName }}</span>
              <Icon
                icon="ep:delete"
                color="#67C25C"
                class="ml-3 cursor-pointer"
                @click="check_list_del(index)"
                v-if="typeValue != 'view'"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="计费周期" prop="id">
          <template #default="{ row }">
            <span>{{ row.payStartDateStr }} ~ {{ row.payEndDateStr }}</span>
          </template>
        </el-table-column>
        <el-table-column label="账单金额" prop="receivable" />
        <el-table-column label="应收/付金额" prop="receivables">
          <template #default="{ row }">
            <el-input v-model="row.receivables" type="number" :disabled="row.disabled" />
          </template>
        </el-table-column>
        <el-table-column label="实收/付金额" prop="receivablePayableAmount" />
        <el-table-column label="需收/付金额" prop="receivable">
          <template #default="{ row }">
            <div class="flex text-center gap-1">
              <el-tag type="success">
                {{ row.billType == 1 ? '收款' : '付款' }}
              </el-tag>
              <span>{{ row.receivable }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="应收/付日期" prop="payDate">
          <template #default="{ row }">
            <el-date-picker
              v-model="row.payDate"
              type="date"
              placeholder="请选择应收/付日期"
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              :disabled="typeValue == 'view'"
            />
          </template>
        </el-table-column>
        <template #append>
          <div class="flex justify-end py-3 bg-[#FAFAFA] pr-4" v-if="check_list.length > 0">
            合计需收:{{ check_list_total }}元
          </div>
        </template>
      </el-table>
    </div>
  </div>
  <div class="mt-18px">
    <ContentWrapBorder :header_data="'保证金'">
      <el-table :data="security_list">
        <el-table-column label="费用类型" prop="costTypeName" />
        <el-table-column label="计费周期" prop="id">
          <template #default="{ row }">
            <span>{{ row.payStartDateStr }} ~ {{ row.payEndDateStr }}</span>
          </template>
        </el-table-column>
        <el-table-column label="账单金额" prop="receivable" />
        <el-table-column label="应收/付金额" prop="receivables" />
        <el-table-column label="实收/付金额" prop="receivablePayableAmount" />
        <el-table-column label="需收/付金额" prop="receivable" />
        <el-table-column label="应收/付日期" prop="payDate">
          <template #default="{ row }">
            <el-date-picker
              v-model="row.payDate"
              type="date"
              placeholder="请选择应收/付日期"
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            />
          </template>
        </el-table-column>
        <template #append>
          <div class="flex justify-end py-3 bg-[#FAFAFA] pr-4" v-if="check_list.length > 0">
            合计需收:{{ security_list_total }}元
          </div>
        </template>
      </el-table>
    </ContentWrapBorder>
  </div>
  <el-row>
    <el-col :span="24" class="text-right mt-20px flex justify-between items-center">
      <div class="flex items-center"
        ><Icon icon="ep:info-filled" color="#2088F3" class="mr-1 !text-16px" /> 合计需收:{{
          check_list_total + security_list_total
        }}元
      </div>
      <div>
        <el-button type="primary" @click="submit" v-if="typeValue != 'view'">提交</el-button>
        <!-- <el-button @click="emit('close')">取消</el-button> -->
      </div>
    </el-col>
  </el-row>
  <!-- 添加付款抽屉 -->
  <el-drawer v-model="collection_drawer" title="添加付款账单" @closed="close_collection_drawer">
    <div>
      <el-form label-position="top" ref="collection" :model="collection_form">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="合同编号" required>
              <el-input disabled :value="detail.contractNumber" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="租客名称" required>
              <el-input disabled :value="detail.ownerName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="账单类型"
              :rules="[{ required: true, message: '请输入账单类型', trigger: 'change' }]"
              prop="billType"
            >
              <el-select disabled v-model="collection_form.billType">
                <el-option
                  v-for="item in BillType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="应付时间"
              :rules="[{ required: true, message: '请选择应付时间', trigger: 'change' }]"
              prop="payDate"
            >
              <el-date-picker
                v-model="collection_form.payDate"
                type="date"
                placeholder="请选择应付时间"
                style="width: 100%"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="费用类型"
              :rules="[{ required: true, message: '请选择费用类型', trigger: 'change' }]"
              prop="feeType"
            >
              <el-cascader
                ref="mycascader"
                v-model="collection_form.feeType"
                :options="feeTypeList"
                class="w-1/1"
                clearable
                filterable
                placeholder="请选择费用类型"
                @change="changeCasc"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="计费周期"
              :rules="[{ required: true, message: '请选择计费周期', trigger: 'change' }]"
              prop="payDateStr"
            >
              <el-date-picker
                v-model="collection_form.payDateStr"
                type="daterange"
                start-placeholder="计费周期开始时间"
                end-placeholder="计费周期结束时间"
                style="width: 100%"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                @change="changedaterange"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="应付金额"
              :rules="[{ required: true, message: '请输入应付金额', trigger: 'change' }]"
              prop="receivable"
            >
              <el-input v-model="collection_form.receivable" placeholder="请输入应付金额" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="账单备注">
              <el-input v-model="collection_form.remark" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <template #footer>
      <el-button @click="close_collection_drawer()">取 消</el-button>
      <el-button type="primary" @click="collection_form_click">确 定</el-button>
    </template>
  </el-drawer>
  <!-- 添加收款抽屉 -->
  <el-drawer v-model="payment_drawer" title="添加收款账单" @closed="close_payment_drawer">
    <div>
      <el-form label-position="top" ref="payment" :model="payment_form">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="合同编号" required>
              <el-input disabled :value="detail.contractNumber" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="租客名称" required>
              <el-input disabled :value="detail.ownerName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="账单类型"
              :rules="[{ required: true, message: '请输入账单类型', trigger: 'change' }]"
              prop="billType"
            >
              <el-select disabled v-model="payment_form.billType">
                <el-option
                  v-for="item in BillType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="应付时间"
              :rules="[{ required: true, message: '请选择应付时间', trigger: 'change' }]"
              prop="payDate"
            >
              <el-date-picker
                v-model="payment_form.payDate"
                type="date"
                placeholder="请选择应付时间"
                style="width: 100%"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="费用类型"
              :rules="[{ required: true, message: '请选择费用类型', trigger: 'change' }]"
              prop="feeType"
            >
              <el-cascader
                ref="mycascader"
                v-model="payment_form.feeType"
                :options="feeTypeList"
                class="w-1/1"
                clearable
                filterable
                placeholder="请选择费用类型"
                @change="changeCasc_payment"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="计费周期"
              :rules="[{ required: true, message: '请选择计费周期', trigger: 'change' }]"
              prop="payDateStr"
            >
              <el-date-picker
                v-model="payment_form.payDateStr"
                type="daterange"
                start-placeholder="计费周期开始时间"
                end-placeholder="计费周期结束时间"
                style="width: 100%"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                @change="changedaterange_payment"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="应付金额"
              :rules="[{ required: true, message: '请输入应付金额', trigger: 'change' }]"
              prop="receivable"
            >
              <el-input v-model="payment_form.receivable" placeholder="请输入应付金额" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="账单备注">
              <el-input v-model="payment_form.remark" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <template #footer>
      <el-button @click="close_payment_drawer()">取 消</el-button>
      <el-button type="primary" @click="payment_form_click">确 定</el-button>
    </template>
  </el-drawer>
  <!-- 选择流程示例编号 -->
  <el-dialog v-model="show_select_flow" title="流程示例" width="500">
    <el-form>
      <el-form-item>
        <!-- <el-input v-model="form.contractNumber" placeholder="请输入流程示例编号" /> -->
        <el-select v-model="contractNumber_flow">
          <el-option
            v-for="(item, index) in contractNumber_flow_options"
            :key="index"
            :label="item.name"
            :value="item.key"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submit_contract_flow"> 确认 </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 退租原因 -->
  <TagTerminationForm ref="formRef" @success="getretreatReason" />
  <!-- 生成打印模板 -->
  <el-dialog title="生成打印模板" v-model="getRetreatTemplate_dialog" width="30%">
    <el-form label-position="top">
      <el-form-item label="选择打印模板" required>
        <el-select v-model="printTemplate" placeholder="请选择打印模板">
          <el-option
            v-for="item in getRetreatTemplateList"
            :key="item.id"
            :label="item.templateName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="getRetreatTemplate_dialog = false">取 消</el-button>
      <el-button type="primary" @click="downloadTemplate">确 定</el-button>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
//新增退租原因
import TagTerminationForm from '@/views/setting/termination/TagTerminationForm.vue'
import ContentWrapBorder from '@/views/bus/owner/component/ContentWrap_border.vue'
import { TagTerminationApi } from '@/api/bus/tag/termination/index.ts'
import { Api } from '@/api/contract/contractList/index'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { OwnerApi } from '@/api/bus/owner/index'
import { downloadFile } from '@/utils/downloadFile'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { set } from 'lodash-es'
const detail = ref([])
const checkedBuild = ref([])
const check_list = ref([])
const security_list = ref([])
const message = useMessage() // 消息弹窗
const form = reactive({
  retreatType: '',
  retreatDate: '',
  bccChangeDate: '',
  applyRetreatDate: '',
  retreatReason: []
})
/////合同类型
const typeValue = ref('')
const open = (detailId, type) => {
  typeValue.value = type
  //清空数据
  setTimeout(() => {
    contractNumber_flow.value = ''
    form.retreatType = ''
    form.retreatDate = ''
    form.bccChangeDate = ''
    form.applyRetreatDate = ''
    form.retreatReason = []
    check_list.value = []
    security_list.value = []
    tzxx_form.value.resetFields()
    show_select_flow.value = false
  }, 50)
  //合同详情
  Api.contractgetId(detailId).then((res) => {
    quit_contrac_id.value = res.detailId
    detail.value = res
    checkedBuild.value = JSON.parse(res.checkedBuild)
    getOwnerInfo(res.ownerId)
    ///获取打印模板
    getPrintTemplatefunc(res.buildId)
  })
  if (type == 'view') {
    check_list.value = []
    security_list.value = []
    getRetreatdetail(detailId)
  } else {
    //账单列表
    Api.getByContractIdRentingTerminationBillList(detailId).then((res) => {
      check_list.value = res
      check_list.value.forEach((item) => {
        item.receivables = Number(item.receivable) - Number(item.receivablePayableAmount)
      })
    })
    // 保证金列表
    Api.getByContractIdRentingTerminationBondBillList(detailId).then((res) => {
      security_list.value = res
      console.log(res)
    })
  }
  //字典
  getquitType()
  //退租原因
  getretreatReason()
  //账单类型
  getBillType()
  //费用类型
  getfeeTypeList()
  //流程示例编号
  getcontractNumber_flow_options()
}
//抽屉方法
const collection_drawer = ref(false)
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['close', 'getList'])
//退租类型字典
const quitType = ref([])
const getquitType = async () => {
  Api.getProject({
    dictType: 'RETREAT_TYPE',
    pageNo: 1,
    pageSize: 10
  }).then((res) => {
    quitType.value = res.list
  })

  // const data = await getStrDictOptions(DICT_TYPE.RETREAT_TYPE)
  // quitType.value = data
}
//账单类型字典
const BillType = ref([])
const getBillType = async () => {
  Api.getProject({
    dictType: 'BILL_TYPE',
    pageNo: 1,
    pageSize: 10
  }).then((res) => {
    BillType.value = res.list
  })
  // const data = await getStrDictOptions(DICT_TYPE.BILL_TYPE)
  // BillType.value = data
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
const formRef = ref()
const openTagTerminationForm = () => {
  formRef.value.open('create')
}
const getOwnerInfo = async (id: string) => {
  if (!id) return
  const res = await OwnerApi.getOwner(id)
  detail.value.ownerName = res.name
}

const feeTypeList = ref([])

//费用类型
const getfeeTypeList = () => {
  orgBillCostTypeApi.getCostTypeChildrenList().then((res) => {
    feeTypeList.value = res
  })
}
const changeCasc = (res) => {
  console.log(res[0])
  Api.getCostType(res[1]).then((res) => {
    collection_form.value.costTypeName = res.name
  })
}
//计费周期
const changedaterange = (val) => {
  collection_form.value.payStartDateStr = val[0]
  collection_form.value.payEndDateStr = val[1]
}
//新建收款
const collection_form = ref({
  billType: '2', //账单类型
  payDate: '', //应付时间
  feeType: [], //费用类型
  receivable: '', //应付金额
  remark: '', //账单备注
  payDateStr: '', //计费周期
  payStartDateStr: '', //计费周期开始时间
  payEndDateStr: '', //计费周期结束时间
  disabled: true
})
const collection = ref()
const collection_form_click = async () => {
  if (!collection.value) return
  await collection.value.validate((valid, fields) => {
    if (valid) {
      collection_form.value.receivablePayableAmount = collection_form.value.receivable
      collection_form.value.receivables = collection_form.value.receivable
      const data = JSON.parse(JSON.stringify(collection_form.value))
      check_list.value.push(data)
      collection_drawer.value = false
      close_collection_drawer()
    }
  })
}
const close_collection_drawer = () => {
  collection_drawer.value = false
  collection.value.resetFields()
}
//收款抽屉
const payment_drawer = ref(false)
const close_payment_drawer = () => {
  payment_drawer.value = false
  payment.value.resetFields()
}
const payment_form = ref({
  billType: '1', //账单类型
  payDate: '', //应付时间
  feeType: [], //费用类型
  receivable: '', //应付金额
  remark: '', //账单备注
  payDateStr: '', //计费周期
  payStartDateStr: '', //计费周期开始时间
  payEndDateStr: '', //计费周期结束时间
  disabled: true
})
const payment = ref()
const payment_form_click = async () => {
  if (!payment.value) return
  await payment.value.validate((valid, fields) => {
    if (valid) {
      payment_form.value.receivablePayableAmount = payment_form.value.receivable
      payment_form.value.receivables = payment_form.value.receivable
      const data = JSON.parse(JSON.stringify(payment_form.value))
      check_list.value.push(data)
      payment_drawer.value = false
      close_payment_drawer()
    }
  })
}

const changeCasc_payment = (res) => {
  console.log(res[0])
  Api.getCostType(res[1]).then((res) => {
    payment_form.value.costTypeName = res.name
  })
}
//计费周期
const changedaterange_payment = (val) => {
  payment_form.value.payStartDateStr = val[0]
  payment_form.value.payEndDateStr = val[1]
}
//提交合同
const tzxx_form = ref()
const quit_contrac_id = ref('')
const contractNumber_flow = ref('')
const show_select_flow = ref(false)
const contractNumber_flow_options = ref([])
const getcontractNumber_flow_options = () => {
  Api.getcontract_flow({
    pageNo: 1,
    pageSize: 10,
    category: 'contractDel'
  }).then((res) => {
    contractNumber_flow_options.value = res
  })
}
const submit = () => {
  tzxx_form.value.validate((valid, fields) => {
    if (valid) {
      Api.orgContractRetreat({
        ownerId: detail.value.ownerId,
        contractId: detail.value.id,
        bondInfo: JSON.stringify(security_list.value),
        retreatType: form.retreatType,
        retreatReason: JSON.stringify(form.retreatReason),
        retreatDate: form.retreatDate,
        bccChangeDate: form.bccChangeDate,
        applyRetreatDate: form.applyRetreatDate,
        billInfo: JSON.stringify(check_list.value),
        id: detail.value.id
      }).then((res) => {
        quit_contrac_id.value = res
        show_select_flow.value = true
      })
    }
  })
}
const submit_contract_flow = () => {
  if (contractNumber_flow.value == '') {
    message.error('请选择流程示例编号')
    return
  }
  Api.createContractFlow({
    contractId: quit_contrac_id.value,
    contractNumber: detail.value.contractNumber,
    processDefinitionKey: contractNumber_flow.value
  }).then((res) => {
    message.success('操作成功')
    setTimeout(() => {
      emit('close')
      emit('getList')
    }, 1000)
  })
}
const check_list_del = (index) => {
  check_list.value.splice(index, 1)
}
//合计需收
const check_list_total = computed(() => {
  return check_list.value.reduce((total, item) => {
    return total + Number(item.receivables)
  }, 0)
})
//合计需收
const security_list_total = computed(() => {
  return security_list.value.reduce((total, item) => {
    return total + Number(item.receivable)
  }, 0)
})
//获取打印模板列表
const getRetreatTemplate_dialog = ref(false)
const getRetreatTemplateList = ref([])
const getPrintTemplatefunc = (id) => {
  Api.getRetreatTemplate(id).then((res) => {
    getRetreatTemplateList.value = res
  })
}
// 下载合同
const down_loadFile = async (row: any) => {
  try {
    const response = await fetch(row.filePath)
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    const blob = await response.blob()
    const url = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = row.name // 设置下载文件名
    document.body.appendChild(a) // 将链接添加到文档中
    a.click() // 模拟点击链接
    a.remove() // 点击后移除链接
    window.URL.revokeObjectURL(url) // 释放 URL 对象
  } catch (error) {
    console.error('下载文件时发生错误:', error)
  }
}
let printTemplate = ref('')
let printTemplate_url = ref('')
const downloadTemplate = () => {
  if (printTemplate.value == '') {
    message.error('请选择打印模板')
    return
  }
  Api.getRetreatTemplateUrl({
    contractTemplateId: printTemplate.value,
    contractId: detail.value.id
  }).then((res) => {
    printTemplate_url.value = res
    down_loadFile({ filePath: res, name: `${detail.value.contractNumber}退租协议` })
    getRetreatTemplate_dialog.value = false
  })
}
//获取退租详情
const RetreatDetail = ref({})
const getRetreatdetail = (id) => {
  Api.getRetreatContract(id).then((res) => {
    RetreatDetail.value = res
    form.retreatType = res.retreatType
    form.retreatDate = res.retreatDate
    form.bccChangeDate = res.bccChangeDate
    form.applyRetreatDate = res.applyRetreatDate
    form.retreatReason = JSON.parse(res.retreatReason)
    security_list.value = JSON.parse(res.bondInfo)
    check_list.value = JSON.parse(res.billInfo)
  })
}
</script>
<style scoped lang="scss">
.border_b {
  border-bottom: 1px solid #e8e8e8;
}
.img {
  width: 50px;
  height: 50px;
  transform: translateY(10px);
  margin-left: 10px;
}
</style>
