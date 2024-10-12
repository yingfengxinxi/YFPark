<template>
  <div class="checkOrderDetail">
    <!-- 表单弹窗：添加/修改 -->
    <el-drawer v-model="drawer_show" :with-header="true" size="1200px" :show-close="false">
      <template #title>
        <div class="flex justify-between items-center">
          <span class="font-size-18px flex items-center c-#000000a6 fw-bold">
            <Icon icon="ep:arrow-left-bold" :size="20" @click="closed" class="m-r-10px" />
            账单详情</span
          >
          <div class="flex justify-end items-center" v-if="orderInfo.lateFeePayStatus != 2">
            <el-button @click="handleCollectionForm">生成缴费通知单</el-button>
            <el-button type="primary" @click="handlepay" :loading="confirmPayLoading"
              >扫码支付</el-button
            >
            <el-button type="danger" @click="handlOpenForm">关闭账单</el-button>
            <el-button @click="handleMoneyUpdate" v-if="orderInfo.lateFeePayStatus != 0"
              >调整滞纳金</el-button
            >
            <el-dropdown v-if="orderInfo.lateFeePayStatus != 0" class="m-l-10px">
              <el-button>结算滞纳金</el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    v-if="checkPermi(['bus:orgBillStreamMiddle:addMiddle'])"
                    @click="openBillStream"
                  >
                    匹配
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['bus:orgBillStream:lateSettle'])"
                    @click="addStreamMiddle"
                  >
                    添加流水
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </template>

      <div class="bg-#fff b-1px b-dashed b-#ddd b-l-0px b-r-0px b-b-0px shadow shadow-op-80">
        <div
          class="p-20px p-l-30px p-r-30px fw-bold b-1px b-dashed b-#ddd b-l-0px b-r-0px b-t-0px font-size-22px"
          >付款方：{{ orderInfo.ownerName }}
        </div>
        <div class="flex items-center p-20px p-l-30px p-r-30px line-height-normal">
          <div v-if="orderInfo.billStatus" class="m-r-30px">
            <div class="c-#00000073 font-size-14px m-b-14px">账单状态</div>
            <div class="c-#000000d9 font-size-24px">{{ orderInfo.billStatus }}</div>
          </div>
          <div v-if="orderInfo.receivable" class="m-r-30px">
            <div class="c-#00000073 font-size-14px m-b-14px">应收金额（元）</div>
            <div class="c-#000000d9 font-size-24px">{{ orderInfo.receivable }}</div>
          </div>
          <div v-if="orderInfo.amountToBeCollected" class="m-r-30px">
            <div class="c-#00000073 font-size-14px m-b-14px">需收金额（元）</div>
            <div class="c-#000000d9 font-size-24px">{{ orderInfo.amountToBeCollected }}</div>
          </div>
          <div v-if="orderInfo.generateLateFee" class="m-r-30px">
            <div class="c-#00000073 font-size-14px m-b-14px">产生滞纳金</div>
            <div class="c-#000000d9 font-size-24px">{{ orderInfo.generateLateFee }}</div>
          </div>
          <div v-if="orderInfo.lateFee" class="m-r-30px">
            <div class="c-#00000073 font-size-14px m-b-14px">
              应收滞纳金金额（元）
              <el-tooltip content="滞纳金=（应收金额 - 已缴金额）*拖欠时间*比例" placement="top"
                ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
              /></el-tooltip>
            </div>
            <div class="c-#000000d9 font-size-24px">{{ orderInfo.lateFee }}</div>
          </div>
          <div v-if="orderInfo.lateFee" class="m-r-30px">
            <div class="c-#00000073 font-size-14px m-b-14px">
              需收滞纳金金额（元）
              <el-tooltip content="滞纳金=（应收金额 - 已缴金额）*拖欠时间*比例" placement="top"
                ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
              /></el-tooltip>
            </div>
            <div class="c-#000000d9 font-size-24px">{{ orderInfo.lateFee }}</div>
          </div>
          <div v-if="orderInfo.payDate">
            <div class="c-#00000073 font-size-14px m-b-14px">应收日期</div>
            <div class="c-#000000d9 font-size-24px">{{ orderInfo.payDate }}</div>
          </div>
        </div>
      </div>
      <div class="grid grid-cols-2 gap-20px m-20px font-size-14px m-b-0px">
        <ContentWrap title="账单信息" class="!m-b-0px">
          <el-descriptions direction="vertical" :column="3">
            <el-descriptions-item
              label="费用类型："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ orderInfo.feeType || '--' }}</el-descriptions-item
            >
            <el-descriptions-item
              label="计费周期："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ orderInfo.billingCycle || '--' }}</el-descriptions-item
            >
            <el-descriptions-item
              label="账单金额（元）："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ orderInfo.receivable || '--' }}</el-descriptions-item
            >
            <el-descriptions-item
              label="创建时间："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ dateFormatter(orderInfo.createTime) || '--' }}</el-descriptions-item
            >
            <el-descriptions-item
              label="付款方："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
            >
              <span
                :class="orderInfo.ownerName ? `c-[var(--el-color-primary)] cursor-pointer` : ''"
                @click="OwnerDetail(orderInfo.ownerId, orderInfo.isPersonal)"
                >{{ orderInfo.ownerName || '--' }}</span
              >
            </el-descriptions-item>
            <el-descriptions-item
              label="合同编号："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
            >
              <div class="flex justify-start" v-if="orderInfo.contractNumber">
                <span
                  class="c-[var(--el-color-primary)] cursor-pointer"
                  @click="handleContractDetail(orderInfo.contractId)"
                  >{{ orderInfo.contractNumber }}</span
                >
                <Icon
                  icon="ep:copy-document"
                  :size="12"
                  color="var(--el-color-primary)"
                  class="m-l-10px m-r-10px cursor-pointer"
                  @click="copy(orderInfo.contractNumber)"
                />
              </div>
              <template v-else> -- </template>
            </el-descriptions-item>
            <el-descriptions-item
              label="账单编号："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
            >
              <div class="flex justify-start" v-if="orderInfo.orderNumber">
                <span>{{ orderInfo.orderNumber }}</span>
                <Icon
                  icon="ep:copy-document"
                  :size="12"
                  color="var(--el-color-primary)"
                  class="m-l-10px m-r-10px cursor-pointer"
                  @click="copy(orderInfo.orderNumber)"
                />
              </div>
              <template v-else> -- </template>
            </el-descriptions-item>
            <el-descriptions-item
              label="起算天数："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ orderInfo.startingLateFeeDay || '--' }}</el-descriptions-item
            >
            <el-descriptions-item
              label="滞纳金比例："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ orderInfo.lateFeeRatio || '--' }}</el-descriptions-item
            >
            <el-descriptions-item
              label="滞纳金上限："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ orderInfo.upperLimitLateFee || '--' }}</el-descriptions-item
            >
            <el-descriptions-item
              label="经办人："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ orderInfo.operatorName || '--' }}</el-descriptions-item
            >
            <el-descriptions-item
              label="账单备注："
              label-class-name="!c-#00000073"
              class-name="w-33% break-all"
              >{{ orderInfo.remark || '--' }}</el-descriptions-item
            >
          </el-descriptions>
        </ContentWrap>
        <ContentWrap title="房源信息" class="!m-b-0px">
          <el-table :data="contractSelectedPropertieList" border>
            <el-table-column label="所属楼宇">
              <template #default="scope">
                <span>{{ scope.row.buildName || '--' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="楼层/房号">
              <template #default="scope">
                <span
                  >{{ scope.row.floor }} /
                  <span class="c-[var(--el-color-primary)] cursor-pointer">
                    {{ scope.row.roomName }}</span
                  >
                </span>
              </template>
            </el-table-column>
            <el-table-column label="面积">
              <template #default="scope">
                <span>{{ scope.row.rentalArea }}㎡</span>
              </template>
            </el-table-column>
          </el-table>
        </ContentWrap>
      </div>
      <ContentWrap class="m-20px" title="账单明细">
        <el-table
          :data="billDetailsList.dataList"
          v-loading="billDetailsLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="费用类型" prop="feeType" />
          <el-table-column label="应收/付金额（元）" prop="receivable" />
          <el-table-column label="税率" prop="taxRate" />
          <el-table-column label="税额" prop="taxAmount" />
          <el-table-column label="开始日期" prop="payStartDate" />
          <el-table-column label="结束日期" prop="payEndDate" />
          <el-table-column label="账单备注" prop="remark" />
        </el-table>
        <div class="flex justify-end font-size-14px bg-#fafafa p-17px">
          <div class="m-r-20px">调整递增: {{ billDetailsList.adjustAmountJia }} 元</div>
          <div class="m-r-20px">调整递减: {{ billDetailsList.adjustAmountJian }} 元</div>
          <div>本次结清合计: {{ billDetailsList.settlementAmount }} 元</div>
        </div>
      </ContentWrap>
      <ContentWrap class="m-20px" title="流水信息">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            <div class="m-r-20px">
              <el-checkbox
                v-model="BillStreamMiddleParams.matchStatus"
                :value="1"
                @change="getBillStreamMiddleCancel"
                :true-value="1"
                :false-value="0"
              >
                <span class="font-size-14px">显示已匹配流水</span>
              </el-checkbox>
            </div>
            <el-button @click="openBillStream()" v-hasPermi="['bus:orgBillStreamMiddle:addMiddle']">
              匹配
            </el-button>
          </div>
        </template>
        <el-table
          :data="BillStreamMiddleList"
          border
          v-loading="BillStreamMiddleLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="对方单位名称" prop="companyName" />
          <el-table-column label="费用类型" prop="costType">
            <template #default="scoped">
              <template v-for="(item, index) in costTypeChildrenList" :key="index">
                <template v-for="(item1, index1) in item.children" :key="index1">
                  <span v-if="item1.value == scoped.row.costType">{{ item1.label }}</span>
                </template>
              </template>
            </template>
          </el-table-column>
          <el-table-column label="入账日期" prop="incomeDate" :formatter="dateFormatter2" />
          <el-table-column label="发生额" prop="amount" />
          <el-table-column label="匹配金额" prop="matchPrice" />
          <el-table-column label="匹配日期" prop="matchDate" />
          <el-table-column label="取消匹配日期" prop="cancelMatchDate" />
          <el-table-column label="匹配状态" prop="adjustStatus">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.ADJUST_STATUS" :value="scope.row.adjustStatus" />
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right">
            <template #default="scope">
              <template v-if="scope.row.adjustStatus === 1"> -- </template>
              <template v-else>
                <el-button
                  link
                  type="danger"
                  v-hasPermi="['bus:orgBillStreamMiddle:addMiddle']"
                  @click="handleMatchRemove(scope.row.id)"
                  v-if="scope.row.matchStatus != 4"
                >
                  取消匹配</el-button
                >
              </template>
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="BillStreamMiddleTotal"
          v-model:page="BillStreamMiddleParams.pageNo"
          v-model:limit="BillStreamMiddleParams.pageSize"
          @pagination="getBillStreamMiddle"
        />
      </ContentWrap>
      <ContentWrap class="m-20px" title="调整">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            <div class="m-r-20px">
              <el-checkbox
                v-model="orgBillAdjustParams.adjustStatus"
                :value="4"
                @change="getOrgBillAdjust"
                :true-value="4"
              >
                <span class="font-size-14px">显示作废调整</span>
              </el-checkbox>
            </div>
            <el-button @click="addOrgBillAdjust()" v-hasPermi="['bus:orgBillAdjust:create']">
              <Icon icon="ep:plus" color="#00000040" class="m-r-6px" />
              调整
            </el-button>
          </div>
        </template>
        <el-table
          :data="orgBillAdjustList"
          border
          v-loading="orgBillAdjustLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="调整日期" prop="adjustDate" :formatter="dateFormatter2" />
          <el-table-column label="调整类型" prop="adjustType">
            <template #default="scope">
              <dict-text :type="DICT_TYPE.ADJUST_TYPE" :value="scope.row.adjustType" />
            </template>
          </el-table-column>
          <el-table-column label="调整方式" prop="adjustMode">
            <template #default="scope">
              <dict-text :type="DICT_TYPE.ADJUST_MODE" :value="scope.row.adjustMode" />
            </template>
          </el-table-column>
          <el-table-column label="调整金额（元）" prop="adjustPrice" />
          <el-table-column label="备注内容" prop="remark" />
          <el-table-column label="作废调整日期" prop="cancelAdjustDate" />
          <el-table-column label="调整状态" prop="adjustStatus">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.ADJUST_STATUS" :value="scope.row.adjustStatus" />
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right">
            <template #default="scope">
              <template v-if="scope.row.adjustStatus === 1"> -- </template>
              <template v-else>
                <el-button link type="danger" @click="handleAdjustRemove(scope.row.id)">
                  删除</el-button
                >
              </template>
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="orgBillAdjustTotal"
          v-model:page="orgBillAdjustParams.pageNo"
          v-model:limit="orgBillAdjustParams.pageSize"
          @pagination="getOrgBillAdjust"
        />
      </ContentWrap>
      <ContentWrap class="m-20px" title="收据记录">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            <el-button
              @click="addOrgBillReceipt()"
              v-hasPermi="['bus:orgBillReceipt:isCheckReceipt']"
            >
              <Icon icon="ep:plus" color="#00000040" class="m-r-6px" />
              开具收据
            </el-button>
          </div>
        </template>
        <el-table
          :data="orgBillReceiptList"
          border
          v-loading="orgBillReceiptLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="收据编号" prop="receiptNumber" />
          <el-table-column label="开据时间" prop="issuerTime" :formatter="dateFormatter" />
          <el-table-column label="汇款方式" prop="remitType">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.REMIT_TYPE" :value="scope.row.remitType" />
            </template>
          </el-table-column>
          <el-table-column label="费用类型" prop="costType">
            <template #default="scoped">
              <template v-for="(item, index) in costTypeChildrenList" :key="index">
                <template v-for="(item1, index1) in item.children" :key="index1">
                  <span v-if="item1.value == scoped.row.costType">{{ item1.label }}</span>
                </template>
              </template>
            </template>
          </el-table-column>
          <el-table-column label="开据金额" prop="applyReceiptAmount" />
          <el-table-column label="状态" prop="status" />
          <el-table-column label="开据人" prop="issuerUid" />
          <el-table-column label="操作" fixed="right">
            <template #default="scope">
              <el-button link type="primary" @click="receiptDetail(scope.row.id)">
                查看详情</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </ContentWrap>
      <ContentWrap class="m-20px" title="附件信息">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            <el-button @click="addFile()">
              <Icon icon="ep:plus" color="#00000040" class="m-r-6px" />
              添加附件
            </el-button>
          </div>
        </template>
        <el-table
          :data="BillAnnexList"
          border
          v-loading="BillAnnexListLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="文件名" prop="name" />
          <el-table-column label="操作人" prop="id" />
          <el-table-column label="操作时间" prop="createTime" :formatter="dateFormatter" />
          <el-table-column label="操作" fixed="right">
            <template #default="scope">
              <el-link
                :href="scope.row.filePath"
                :underline="false"
                download
                target="_blank"
                type="primary"
                class="m-r-14px"
              >
                下载
              </el-link>
              <el-button link type="danger" @click="handleBillAnnexRemove(scope.row.id)">
                删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="BillAnnexListTotal"
          v-model:page="BillAnnexListParams.pageNo"
          v-model:limit="BillAnnexListParams.pageSize"
          @pagination="getBillAnnexList"
        />
      </ContentWrap>
      <ContentWrap class="m-20px" title="备注信息">
        <template #header>
          <div class="flex-1 flex justify-end items-center">
            <el-button @click="addRemark()">
              <Icon icon="ep:plus" color="#00000040" class="m-r-6px" />
              添加备注
            </el-button>
          </div>
        </template>
        <el-table
          :data="RemarkList"
          border
          v-loading="RemarkLoading"
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="备注人" prop="creator" />
          <el-table-column label="备注内容" prop="remark" />
          <el-table-column label="备注时间" prop="createTime" :formatter="dateFormatter" />
          <el-table-column label="操作" fixed="right">
            <template #default="scope">
              <el-button link type="primary" @click="handleRemarkEdit(scope.row.id)">
                <Icon icon="ep:edit" class="m-r-6px" />
              </el-button>
              <el-button link type="danger" @click="handleRemarkRemove(scope.row.id)">
                <Icon icon="ep:delete" class="m-r-6px"
              /></el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="RemarkTotal"
          v-model:page="RemarkParams.pageNo"
          v-model:limit="RemarkParams.pageSize"
          @pagination="getBillAnnexList"
        />
      </ContentWrap>
    </el-drawer>
  </div>
  <el-dialog title="关闭账单原因" width="600px" v-model="OpenRemark" @close="handleRemarkCancel">
    <el-input placeholder="请输入关闭账单原因" v-model="remark" type="textarea" :rows="5" />
    <template #footer>
      <div class="dialog-footer">
        <el-button size="small" @click="handleRemarkCancel">取 消</el-button>
        <el-button size="small" type="primary" @click="handlClose" :loading="confirmRemarkLoading"
          >确 定</el-button
        >
      </div>
    </template>
  </el-dialog>
  <el-dialog title="扫码支付" width="600px" v-model="OpenPay" @close="handlePayCancel">
    <div class="m-20px flex items-center justify-center flex-col">
      <el-image :src="PayCode" v-loading="confirmPayLoading" />
      <el-button size="small" type="primary" @click="handlepay" :loading="confirmPayLoading"
        >点击刷新</el-button
      >
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button size="small" @click="handlePayCancel">取 消</el-button>
        <el-button
          size="small"
          type="primary"
          @click="handlDownloadPay"
          :loading="confirmPayLoading"
          >确 定</el-button
        >
      </div>
    </template>
  </el-dialog>
  <Dialog v-model="MoneyUpdateVisible" title="调整滞纳金" width="600px">
    <el-form ref="form" :model="MoneyUpdateForm" label-width="100px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="产生滞纳金" prop="generateLateFee">
            <el-input
              placeholder="请输入产生滞纳金"
              v-model="MoneyUpdateForm.generateLateFee"
              disabled
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="应收滞纳金" prop="generateLateFee">
            <el-input-number
              :min="0"
              v-model="MoneyUpdateForm.lateFee"
              :step="1"
              placeholder="请输入应收滞纳金"
              :precision="2"
              class="!w-100%"
              controls-position="right"
            /> </el-form-item
        ></el-col>
      </el-row> </el-form
    ><template #footer>
      <span class="dialog-footer">
        <el-button @click="MoneyUpdateVisible = false">取消</el-button>
        <el-button type="primary" @click="MoneyUpdateSubmit"> 确定 </el-button>
      </span>
    </template>
  </Dialog>
  <StreamMiddle ref="StreamMiddleRef" @success="getBillInformation" />
  <TenantDetails ref="TenantDetailsRef" @select-pick="getList" />
  <ContractDetail ref="ContractDetailRef" @select-pick="getList" />
  <runningWater ref="runningWaterRef" @success="getBillStreamMiddle" />
  <collectionForm ref="collectionFormRef" @success="getBillInformation" />
  <AddBillAnnexForm ref="AddBillAnnexFormRef" @success="getBillAnnexList" />
  <AddRemarkForm ref="AddRemarkFormRef" @success="getRemark" />
  <addOrgBillAdjustForm ref="addOrgBillAdjustFormRef" @success="UpdateBillAdjust" />
  <addOrgBillReceiptForm ref="addOrgBillReceiptFormRef" @success="getOrgBillReceipt" />
  <ReceiptDetail ref="receiptDetailRef" @success="getOrgBillReceipt" />
</template>
<script setup lang="ts">
const message = useMessage() // 消息弹窗
import { checkPermi } from '@/utils/permission'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import StreamMiddle from '@/views/bus/bill/streamMiddle/form.vue'
import collectionForm from './collectionForm.vue'
import AddBillAnnexForm from './addBillAnnexForm.vue'
import AddRemarkForm from './addRemarkForm.vue'
import addOrgBillAdjustForm from './addOrgBillAdjustForm.vue'
import addOrgBillReceiptForm from './addOrgBillReceiptForm.vue'
import runningWater from './runningWater.vue'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import ContractDetail from '@/views/contract/contractList/contractDetail_drawer.vue'
import ReceiptDetail from '@/views/bus/bill/receipt/detail.vue'
import { contractOrderApi } from '@/api/bus/contractOrderBill'
import { orgBillStreamMiddleApi } from '@/api/bus/orgBillStreamMiddle'
import { orgBillAnnexApi } from '@/api/bus/orgBillAnnex'
import { orgRemarkApi } from '@/api/bus/orgRemark'
import { orgBillAdjustApi } from '@/api/bus/orgBillAdjust'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { contractSelectedPropertieApi } from '@/api/bus/contractSelectedPropertie'
import { orgBillReceiptApi } from '@/api/bus/orgBillReceipt'
import { formatDate } from '@/utils/formatTime'
import { orgBillCostTypeApi } from '@/api/bus/orgBillCostType'
import { useClipboard } from '@vueuse/core'
const drawer_show = ref(false)
const { t } = useI18n() // 国际化

const orderId = ref(0)
const OpenRemark = ref(false)
const confirmRemarkLoading = ref(false)
const orderInfo = ref({})
const billDetailsList = ref({})
const billDetailsLoading = ref(false)
const collectionFormRef = ref()
const OpenPay = ref(false)
const confirmPayLoading = ref(false)
const PayCode = ref('')
const MoneyUpdateVisible = ref(false)
const MoneyUpdateForm = ref({
  generateLateFee: '',
  lateFee: ''
})
const BillStreamMiddleList = ref([])
const BillStreamMiddleParams = ref({
  pageNo: 1,
  pageSize: 10,
  matchStatus: '',
  billId: undefined
})
const BillStreamMiddleLoading = ref(false)
const BillStreamMiddleTotal = ref(0)
const BillAnnexList = ref([])
const BillAnnexListLoading = ref(false)
const BillAnnexListParams = ref({
  pageNo: 1,
  pageSize: 10
})
const BillAnnexListTotal = ref(0)
const RemarkList = ref([])
const RemarkLoading = ref(false)
const RemarkParams = ref({
  pageNo: 1,
  pageSize: 10
})
const RemarkTotal = ref(0)
const orgBillAdjustList = ref([])
const orgBillAdjustLoading = ref(false)
const orgBillAdjustParams = ref({
  pageNo: 1,
  pageSize: 10,
  adjustStatus: undefined
})
const orgBillAdjustTotal = ref(0)

const orgBillReceiptList = ref([])
const orgBillReceiptLoading = ref(false)
const costTypeChildrenList = ref([])

const contractSelectedPropertieList = ref([])
const contractSelectedPropertieLoading = ref(false)
/** 费用类型 */
const getCostTypeChildrenList = async () => {
  try {
    const data = await orgBillCostTypeApi.getCostTypeChildrenList()
    costTypeChildrenList.value = data
  } finally {
  }
}
/** 房源信息 */
const contractSelectedPropertie = async () => {
  try {
    contractSelectedPropertieLoading.value = true
    const data = await contractSelectedPropertieApi.getContractIdRoomInfoList(
      orderInfo.value.contractId
    )
    console.log(data)
    contractSelectedPropertieList.value = data
  } catch (error) {
  } finally {
    contractSelectedPropertieLoading.value = false
  }
}
/**生成缴费通知单 */
const handleCollectionForm = async () => {
  collectionFormRef.value.open(
    { buildName: orderInfo.value.buildName },
    { id: orderInfo.value.ownerId, contractId: orderInfo.value.contractId },
    { id: orderInfo.value.billId, ...orderInfo.value }
  )
}

/** 调整滞纳金 */
const handleMoneyUpdate = async () => {
  MoneyUpdateForm.value.generateLateFee = orderInfo.value.generateLateFee
  MoneyUpdateVisible.value = true
}
/** 调整滞纳金确定 */
const MoneyUpdateSubmit = async () => {
  try {
    await contractOrderApi.collectLateFee({
      billId: orderId.value,
      lateFee: MoneyUpdateForm.value.lateFee
    })
    message.success('操作成功')
    getBillInformation()
    billDetails()
    getOrgBillAdjust()
    getBillStreamMiddle()
  } catch (error) {
  } finally {
    MoneyUpdateVisible.value = false
  }
}
// 扫码支付弹窗关闭
const handlePayCancel = () => {
  OpenPay.value = false
  PayCode.value = ''
}

const handlDownloadPay = async () => {
  OpenPay.value = false
  PayCode.value = ''
  initData()
}

// 扫码支付
const handlepay = async () => {
  try {
    confirmPayLoading.value = true
    const data = await contractOrderApi.scanCodeBillPay({
      billId: orderId.value
    })
    console.log(data)
    PayCode.value = data
    OpenPay.value = true
  } catch (error) {
  } finally {
    confirmPayLoading.value = false
  }
}

/**账单详细信息详情 */
const getBillInformation = async () => {
  try {
    const data = await contractOrderApi.billInformation({
      billId: orderId.value
    })
    console.log(data)
    orderInfo.value = data
    contractSelectedPropertie()
  } catch (error) {}
}
/** 租客详情 */
const TenantDetailsRef = ref()
const OwnerDetail = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}
/** 合同 详情 */
const ContractDetailRef = ref()
const handleContractDetail = async (id: number) => {
  ContractDetailRef.value.open(id)
}
/** 复制 **/
const copy = async (text: string) => {
  if (navigator.clipboard) {
    const { copy } = useClipboard({ source: text })
    copy()
  } else {
    const input = document.createElement('input')
    input.setAttribute('value', text)
    document.body.appendChild(input)
    input.select()
    document.execCommand('copy')
    document.body.removeChild(input)
  }

  message.success(t('common.copySuccess'))
}
/**新建收支流水 */
const StreamMiddleRef = ref()
const addStreamMiddle = async () => {
  StreamMiddleRef.value.open(
    'lateSettle',
    orderInfo.value.villageId,
    orderInfo.value.buildId,
    orderInfo.value.roomNumber,
    orderInfo.value.ownerId,
    orderId.value,
    orderInfo.value.lateFee
  )
}
/**账单明细 */
const billDetails = async () => {
  try {
    billDetailsLoading.value = true
    const data = await contractOrderApi.billDetails({
      billId: orderId.value
    })
    console.log(data)
    billDetailsList.value = data
  } catch (error) {
  } finally {
    billDetailsLoading.value = false
  }
}
// 开启关闭账单原因弹窗
const remark = ref('')
const handlOpenForm = async () => {
  if (BillStreamMiddleList.value.length) {
    message.warning('请先取消匹配所有流水信息')
    return
  }
  OpenRemark.value = true
  remark.value = ''
}
const handleRemarkCancel = () => {
  OpenRemark.value = false
}
/**关闭账单 */
const handlClose = async () => {
  try {
    confirmRemarkLoading.value = true
    const data = {
      billId: orderId.value,
      remark: remark.value
    }
    await contractOrderApi.close(data)
    message.success('关闭成功')
    handleRemarkCancel()
  } catch (error) {
  } finally {
    confirmRemarkLoading.value = false
  }
}

/** 流水信息列表 */
const getBillStreamMiddle = async () => {
  BillStreamMiddleParams.value.billId = orderId.value
  console.log(BillStreamMiddleParams.value)
  const data = await orgBillStreamMiddleApi.getBillStreamListPage(BillStreamMiddleParams.value)
  BillStreamMiddleList.value = data.list
}

/** 显示取消匹配列表 */
const getBillStreamMiddleCancel = async (val) => {
  console.log(val, BillStreamMiddleParams.value.matchStatus)
  if (val == '0') {
    BillStreamMiddleParams.value.matchStatus = ''
  }
  getBillStreamMiddle()
}

/** 匹配流水 */
const runningWaterRef = ref()
const openBillStream = async () => {
  runningWaterRef.value.open('addCollectionMiddle', { id: orderInfo.value.ownerId }, [
    {
      matchDate: formatDate(new Date(), 'YYYY-MM-DD'),
      id: orderId.value,
      amountToBeCollected: orderInfo.value.amountToBeCollected
    }
  ])
}

/** 匹配 */
const handleMatchBill = async (data) => {
  console.log(data, 'data')
  try {
    const res = await orgBillStreamMiddleApi.matchBill(data)
    console.log(res)
    return 'success'
    // if (data.list.length > 0) {
    //   message.success('匹配成功')
    //   initData()
    // } else {
    //   message.error('匹配失败')
    // }
  } catch (error) {
    return 'error'
  }
}

/** 取消匹配 */
const handleMatchRemove = async (data) => {
  try {
    await message.delConfirm('当前账单已开据收据,请谨慎进行操作')
    const res = await orgBillStreamMiddleApi.cancelMatch({
      id: data,
      cancelMatchDate: formatDate(new Date(), 'YYYY-MM-DD')
    })
    getBillStreamMiddle()
    console.log(res)
    message.success('取消匹配成功')
  } catch (error) {
    message.error('取消匹配失败')
  }
}

/** 更新调整 */
const UpdateBillAdjust = async () => {
  initData()
}
/** 调整列表 */

const getOrgBillAdjust = async () => {
  try {
    orgBillAdjustLoading.value = true
    const data = await orgBillAdjustApi.getPage({
      billId: orderId.value,
      pageNo: orgBillAdjustParams.value.pageNo,
      pageSize: orgBillAdjustParams.value.pageSize,
      adjustStatus: orgBillAdjustParams.value.adjustStatus
    })
    orgBillAdjustList.value = data.list
  } catch (error) {
  } finally {
    orgBillAdjustLoading.value = false
  }
}

/** 调整删除 */

const handleAdjustRemove = async (id: any) => {
  try {
    await orgBillAdjustApi.delete(id)
    message.success('删除成功')
    getOrgBillAdjust()
  } catch (error) {}
}
/** 新增调整 */
const addOrgBillAdjustFormRef = ref()
const addOrgBillAdjust = async () => {
  addOrgBillAdjustFormRef.value.open(orderId.value, orderInfo.value)
}
/** 收据列表 */
const getOrgBillReceipt = async () => {
  try {
    orgBillReceiptLoading.value = true
    const data = await orgBillReceiptApi.getReceiptRecord({
      billId: orderId.value
    })
    orgBillReceiptList.value = data.list
  } catch (error) {
  } finally {
    orgBillReceiptLoading.value = false
  }
}

/** 开具收据 */
const addOrgBillReceiptFormRef = ref()
const addOrgBillReceipt = async () => {
  try {
    const data = await orgBillReceiptApi.isCheckReceipt({
      billId: orderInfo.value.billId
    })
    console.log(data)
    addOrgBillReceiptFormRef.value.open(orderId.value)
  } catch (err) {
    // message.error(err)
  }
}

/** 收据详情 */
const receiptDetailRef = ref()
const receiptDetail = async (id: any) => {
  receiptDetailRef.value.open(id)
}
// 账单附件列表
const getBillAnnexList = async () => {
  try {
    BillAnnexListLoading.value = true
    const data = await orgBillAnnexApi.getBillAnnexList({
      sourceId: orderId.value,
      type: 1,
      pageNo: BillAnnexListParams.value.pageNo,
      pageSize: BillAnnexListParams.value.pageSize
    })
    BillAnnexList.value = data.list
    BillAnnexListTotal.value = data.total
  } catch (error) {
  } finally {
    BillAnnexListLoading.value = false
  }
}
//新增附件
const AddBillAnnexFormRef = ref()
const addFile = async () => {
  AddBillAnnexFormRef.value.open(orderId.value, 1)
}

// 删除附件
const handleBillAnnexRemove = async (id: any) => {
  try {
    await orgBillAnnexApi.delete(id)
    message.success('删除成功')
    getBillAnnexList()
  } catch (error) {}
}

/** 备注信息 */

const getRemark = async () => {
  try {
    RemarkLoading.value = true
    const data = await orgRemarkApi.getPage({
      businessId: orderId.value,
      type: 1,
      pageNo: RemarkParams.value.pageNo,
      pageSize: RemarkParams.value.pageSize
    })
    RemarkList.value = data.list
    RemarkTotal.value = data.total
  } catch (error) {
  } finally {
    RemarkLoading.value = false
  }
}

/** 新增备注信息 */
const AddRemarkFormRef = ref()
const addRemark = async () => {
  AddRemarkFormRef.value.open('create', orderId.value, '', 1)
}
/** 编辑备注信息 */
const handleRemarkEdit = async (id: any) => {
  console.log(id, 'id')
  AddRemarkFormRef.value.open('update', orderId.value, id, 1)
}

/** 删除备注信息 */
const handleRemarkRemove = async (id: any) => {
  try {
    await orgRemarkApi.delete({ id: id })
    message.success('删除成功')
    getRemark()
  } catch (error) {}
}

/** 打开抽屉 */
const open = async (id: number) => {
  drawer_show.value = true
  console.log(id, 'id')
  orderId.value = id
  initData()
}
const initData = async () => {
  await getCostTypeChildrenList()
  await getBillInformation()
  await billDetails()
  await getBillStreamMiddle()
  await getOrgBillAdjust()
  await getOrgBillReceipt()
  await getBillAnnexList()
  await getRemark()
}
const closed = async () => {
  drawer_show.value = false
}
const emit = defineEmits(['update:show', 'success'])
const submit = async () => {}
onMounted(() => {})

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped>
.checkOrderDetail :deep(.el-drawer__body) {
  padding: 0;
  background: var(--app-content-bg-color) !important;
}

:deep(.el-descriptions-item__content),
v-deep .el-descriptions__content {
  max-width: 195px; // 如果下方最大宽度不起效果，就加上此样式
}
</style>
