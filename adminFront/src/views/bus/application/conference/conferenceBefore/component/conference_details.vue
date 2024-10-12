<template>
  <div>
    <el-drawer v-model="drawer" title="订单详情" @closed="closed">
      <div class="relative overflow-hidden">
        <div class="righttag">
          无状态
          <!-- <span>{{ statusList.find((item) => item.value == form.status).label }}</span> -->
        </div>
        <div class="p-10px">
          <div class="header_row pl-18px"> 预约会议室 </div>
          <div class="flex mt-16px items-center gap-10px">
            <img src="./image/siteCate.png" alt="" class="w-40px h-40px overflow-hidden rd-50%" />
            <span class="font-600 text-14px"
              >{{ form.resvPlaceCategory?.name }} - {{ form.resvPlace?.name }}</span
            >
          </div>
          <div class="mt-20px">
            <div class="font-600 text-14px">
              预约日期:{{ formatDate(form.createTime) }} ({{ form.weekText }})
            </div>
            <div class="text-14px mt-10px flex"
              >{{ form.resvPlace?.name }}({{ formatDate(form.date) }}~{{
                formatDate(form.endTime)
              }})<el-tag type="primary">--小时</el-tag></div
            >
          </div>
        </div>
        <div class="p-10px">
          <div class="header_row pl-18px"> 基本信息 </div>
          <div class="flex justify-between items-center border-bottom mt-8px">
            <span>订单编号</span>
            <span>{{ form.resvBookingOrder?.orderNo }}</span>
          </div>
          <div class="flex justify-between items-center border-bottom">
            <span>入场人数</span>
            <span>{{ form.resvPlaceCategory?.capacity }}</span>
          </div>
          <div class="flex justify-between items-center border-bottom">
            <span>支付金额</span>
            <span>{{ form.resvBookingOrder?.orderTotal }}元</span>
          </div>
          <div class="flex justify-between items-center border-bottom">
            <span>下单时间</span>
            <span>{{ formatDate(form.resvBookingOrder?.createTime) }}</span>
          </div>
          <div class="flex justify-between items-center border-bottom">
            <span>支付时间</span>
            <span>{{ formatDate(form.resvBookingOrder?.payTime) || '--' }}</span>
          </div>
          <div class="flex justify-between items-center border-bottom">
            <span>核销人员</span>
            <span>{{ form.resvBookingVerification?.handlerName || '--' }}</span>
          </div>
          <div class="flex justify-between items-center border-bottom">
            <span>核销时间</span>
            <span>{{ formatDate(form.resvBookingVerification?.handleTime) || '--' }}</span>
          </div>
        </div>
      </div>
    </el-drawer>
  </div>
</template>
<script lang="ts" setup>
import { conferenceApi } from '@/api/conference/index'
import { getStrDictOptions } from '@/utils/dict'
import { formatDate } from '@/utils/formatTime.ts'
const form = ref({})
const drawer = ref(false)
function open(id) {
  drawer.value = true
  conferenceApi.getresvplaceDetailId(id).then((res) => {
    form.value = res
  })
}
//重置表单
const drawer_ref = ref()
const closed = () => {
  drawer.value = false
}

defineExpose({ open })
//状态数组
const statusList = ref([])
const getstatusList = async () => {
  const data = await getStrDictOptions('RESV_BOOKING_STATUS')
  statusList.value = data
}
onMounted(() => {
  getstatusList()
})
</script>
<style lang="scss" scoped>
.header_row {
  border-left: 4px solid #2681f3;
  font-size: 14px;
  font-weight: 600;
}
.righttag {
  width: 100px;
  height: 100px;
  position: absolute;
  top: -60px;
  right: -60px;
  transform: rotate(45deg);
  span {
    position: absolute;
    bottom: 0;
    display: block;
    font-size: 14px;
    color: #fff;
    background: #1890ff;
    width: 100px;
    text-align: center;
    padding: 0 10px;
    box-sizing: border-box;
    font-weight: 600;
  }
}
.border-bottom {
  padding: 18px 0;
  color: #262626;
  font-size: 14px;
  border-bottom: 1px solid #d4d4d4;
}
</style>
