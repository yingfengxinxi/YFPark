<template>
  <el-drawer v-model="drawer" title="订单详情">
    <div
      class="pos-relative overflow-hidden pt-20px border-t-1px border-solid border-#D4D4D4 border-0"
    >
      <div class="rt-tag" v-if="detailData.orderStatusName == '待支付'">
        {{ detailData.orderStatusName }}
      </div>
      <div
        class="border-b-1 border-#D4D4D4 border-solid border-0 flex gap-10px py-20px pt-0 items-center"
      >
        <span class="color-#262626 text-14px"> 订单类型 </span>
        <span
          class="bg-#13CCA0 color-#fff rounded px-8px py-4px text-14px"
          v-if="detailData.orderTypeName == '尾款费用'"
        >
          {{ detailData.orderTypeName }}
        </span>
        <span
          class="bg-#409EFF color-#fff rounded px-8px py-4px text-14px"
          v-if="detailData.orderTypeName == '附加费用'"
        >
          {{ detailData.orderTypeName }}
        </span>
        <span
          class="bg-#FFD12D color-#fff rounded px-8px py-4px text-14px"
          v-if="detailData.orderTypeName == '预缴费用'"
        >
          {{ detailData.orderTypeName }}
        </span>
      </div>
      <div class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px">
        <span class="color-#262626 text-14px"> 订单编号 </span>
        <span class="color-#262626 text-14px"> {{ detailData.orderNumber || '--' }} </span>
      </div>
      <div
        class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px"
        v-if="detailData.orderStatusName == '已支付'"
      >
        <span class="color-#262626 text-14px"> 支付金额 </span>
        <span class="color-#262626 text-14px"> {{ detailData.paymentAmount || '--' }} </span>
      </div>
      <div
        class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px"
        v-if="detailData.orderStatusName == '已支付'"
      >
        <span class="color-#262626 text-14px"> 支付时间 </span>
        <span class="color-#262626 text-14px"> {{ detailData.paymentAmount || '--' }} </span>
      </div>
      <div
        class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px"
        v-if="detailData.orderStatusName == '已支付'"
      >
        <span class="color-#262626 text-14px"> 支付方式 </span>
        <span class="color-#262626 text-14px"> {{ detailData.paymentAmount || '--' }} </span>
      </div>
      <div class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px">
        <span class="color-#262626 text-14px"> 下单时间 </span>
        <span class="color-#262626 text-14px"> {{ detailData.createTime || '--' }} </span>
      </div>
      <div class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px">
        <span class="color-#262626 text-14px"> 下单用户 </span>
        <span class="color-#262626 text-14px"> {{ detailData.creator || '--' }} </span>
      </div>
      <div
        class="border-b-1 border-#D4D4D4 border-solid border-0 flex gap-10px items-center py-20px"
      >
        <span class="color-#262626 text-14px"> 工单信息 </span>
        <el-tag
          :type="
            getIntDictOptions('WORK_ORDER_STATUS').find(
              (item) => item.label == detailData.workOrderStatusName
            )?.colorType
          "
          >{{ detailData.workOrderStatusName }}</el-tag
        >
      </div>
      <div class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px">
        <span class="color-#262626 text-14px"> 工单编号 </span>
        <span class="color-#262626 text-14px"> {{ detailData.workOrderNumber || '--' }} </span>
      </div>
      <div class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px">
        <span class="color-#262626 text-14px"> 保修地址 </span>
        <span class="color-#262626 text-14px"> {{ detailData.warrantyAddress || '--' }} </span>
      </div>
      <div class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px">
        <span class="color-#262626 text-14px"> 租客名称 </span>
        <span class="color-#262626 text-14px"> {{ detailData.ownerName || '--' }} </span>
      </div>
      <div class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px">
        <span class="color-#262626 text-14px"> 报修类型 </span>
        <span class="color-#262626 text-14px"> {{ detailData.repairType || '--' }} </span>
      </div>
      <div class="border-b-1 border-#D4D4D4 border-solid border-0 flex justify-between py-20px">
        <span class="color-#262626 text-14px"> 工单分类 </span>
        <span class="color-#262626 text-14px"> {{ detailData.subcatName || '--' }} </span>
      </div>
    </div>
    <template #footer>
      <div v-if="detailData.orderStatusName == '已支付'">
        <el-button type="danger" @click="RefundDialog" v-hasPermi="['bus:workOrderProposeOrder:orderRefund']"
          >退款</el-button
        >
      </div>
    </template>
  </el-drawer>
  <el-dialog v-model="dialogTableVisible" title="确认退款" width="500px">
    <el-form>
      <el-form-item label="退款类型">
        <el-radio-group v-model="orderDate.refundStatus" @change="changerefundStatus">
          <el-radio
            v-for="(item, index) in getIntDictOptions('WORK_ORDER_REFUND_STATUS')"
            :key="index"
            :value="item.value"
            :label="item.label"
            >{{ item.label }}</el-radio
          >
        </el-radio-group>
      </el-form-item>
      <el-form-item label="退款金额">
        <el-input
          v-model="orderDate.refundAmount"
          placeholder="请输入退款金额"
          :disabled="orderDate.refundStatus == 2"
          type="number"
          :min="0"
          :max="detailData.paymentAmount"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button @click="dialogTableVisible = false">取消</el-button>
        <el-button type="primary" @click="refundsubmit">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import { workOrderProposeOrderApi } from '@/api/bus/Category/Orderrecord/index.ts'
import { getIntDictOptions } from '@/utils/dict'

const drawer = ref(false)
const detailData = ref<any>({})
const idValue = ref()
function open(id: string) {
  idValue.value = id
  drawer.value = true
  workOrderProposeOrderApi.getDetail(id).then((res) => {
    detailData.value = res
  })
}
defineExpose({ open })
//确认退款
const dialogTableVisible = ref(false)
const orderDate = ref<any>({
  refundStatus: 2,
  refundAmount: ''
})
const RefundDialog = () => {
  dialogTableVisible.value = true
  orderDate.value.refundStatus = 2
  orderDate.value.refundAmount = detailData.value.paymentAmount
}
const changerefundStatus = () => {
  if (orderDate.value.refundStatus == 2) {
    orderDate.value.refundAmount = detailData.value.paymentAmount
  } else {
    orderDate.value.refundAmount = 0
  }
}
const refundsubmit = () => {
  console.log(detailData)
  workOrderProposeOrderApi
    .orderRefund({
      id: idValue.value,
      refundStatus: orderDate.value.refundStatus,
      refundAmount: orderDate.value.refundAmount
    })
    .then((res) => {
      // if (res) {
      //   dialogTableVisible.value = false
      //   drawer.value = false
      //   emit('refresh')
      // }
    })
}
</script>
<style lang="scss" scoped>
.rt-tag {
  position: absolute;
  right: 0;
  top: 0;
  width: 100px;
  text-align: center;
  background-color: #f00;
  line-height: 20px;
  font-size: 12px;
  color: #fff;
  transform: rotate(45deg) translate(30px, -10px);
}
</style>
