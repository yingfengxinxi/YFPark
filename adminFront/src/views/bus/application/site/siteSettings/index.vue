<template>
  <div class="bg-white p-20px">
    <div class="flex gap-4px items-center text-14px">
      下单后<el-input style="width: 100px" v-model="unpaid_timeout_cancel.number" />
      <el-select style="width: 100px" v-model="unpaid_timeout_cancel.time_unit">
        <el-option
          v-for="(item, index) in ConferenceRuleList"
          :key="index"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      内未付款，则系统自动取消订单，并且待付款状态下锁定场地。
    </div>
    <div class="flex gap-4px items-center text-14px mt-18px">
      开启退款<el-switch v-model="refundSupported" :inactive-value="0" :active-value="1" />
    </div>
    <el-card
      class="mt-10px"
      style="background-color: #f2f2f2"
      shadow="never"
      v-if="refundSupported == 1"
    >
      <template #header> <span class="font-600 text-14px">退款设置</span> </template>
      <!-- 退款审核 -->
      <div class="text-14px color-#666 flex items-center gap-20px lh-loose"
        >退款开启审核
        <el-radio-group v-model="refundRule.reviewer">
          <el-radio value="0" size="large">不开启</el-radio>
          <el-radio value="1" size="large">开启</el-radio>
        </el-radio-group></div
      >
      <!-- 退款 -->
      <div class="text-14px color-#666 flex items-center gap-20px lh-loose"
        >距离预约单开始
        <el-input style="width: 100px" v-model="refundRule.start_time_limit.number" />
        <el-select style="width: 100px" v-model="refundRule.start_time_limit.time_unit">
          <el-option
            v-for="(item, index) in ConferenceRefundRuleList"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        内不支持退款
      </div>
      <!-- 状态 -->
      <div class="text-14px color-#666 flex items-center gap-20px lh-loose"
        >当订单状态为已核销、已过期时，对应操作时间超过
        <el-input
          style="width: 100px"
          v-model="refundRule.refund_deadline_day"
        />天后,则隐藏订单详情中的退款按钮，既不支持退款
      </div>
      <!-- 通知 -->
      <div class="text-14px color-#666 flex items-center gap-20px lh-loose"
        >退款通知
        <el-radio-group v-model="refundRule.is_notice_enabled">
          <el-radio :value="0" size="large">关闭</el-radio>
          <el-radio :value="1" size="large">开启</el-radio>
        </el-radio-group></div
      >
      <!-- 人员 -->
      <div
        class="text-14px color-#666 flex items-center gap-20px lh-loose"
        v-if="refundRule.is_notice_enabled"
      >
        通知人员
        <span v-for="(item, index) in selectDataList" :key="index" class="userSpan">
          {{ item.nickname }}
          <Icon icon="ep:close" color="#666666" class="del_icon" @click="UserSelect_del(index)" />
        </span>
        <el-button type="primary" plain @click="select_user">选择人员</el-button>
      </div>
    </el-card>
    <el-button class="mt-20px" type="primary" @click="submit">保存</el-button>
    <UserSelect ref="UserSelect_ref" @submit="selectData" />
  </div>
</template>
<script setup lang="ts">
import { conferenceApi } from '@/api/conference/index'
import { getStrDictOptions } from '@/utils/dict'
const message = useMessage() // 消息弹窗

import UserSelect from '../../conference/conferenceSettings/compontent/userSelect.vue'
const UserSelect_ref = ref<HTMLElement>()
const refundSupported = ref(0)
const refundRule = ref({})
const select_user = () => {
  UserSelect_ref.value.open(selectDataList.value)
}
const detail = ref({})
const unpaid_timeout_cancel = ref({
  number: 0,
  time_unit: 'minute'
})
onMounted(() => {
  //获取字典
  getConferenceRefundRule()
  getConferenceCancelRule()
  conferenceApi
    .getsetting({
      sign: 'library'
    })
    .then((res) => {
      detail.value = res[0]
      unpaid_timeout_cancel.value = JSON.parse(res[0].cancelOrderRule)
      //取消规则
      unpaid_timeout_cancel.value.number = unpaid_timeout_cancel.value.unpaid_timeout_cancel.number
      unpaid_timeout_cancel.value.time_unit =
        unpaid_timeout_cancel.value.unpaid_timeout_cancel.time_unit
      //是否支持退款
      refundSupported.value = res[0].refundSupported
      //退款规则
      refundRule.value = JSON.parse(res[0].refundRule)
      refundRule.value.is_review_enabled.split(',').map((item, index) => {
        selectDataList.value.push({
          id: item,
          nickname: refundRule.value.is_review_enabled_name.split(',')[index]
        })
      })
    })
})
//获取会议室取消订单规则
const ConferenceRuleList = ref([])
const getConferenceCancelRule = async () => {
  const data = await getStrDictOptions('CONFERENCE_RULES')
  ConferenceRuleList.value = data
}
//会议室退款规则
const ConferenceRefundRuleList = ref([])
const getConferenceRefundRule = async () => {
  const data = await getStrDictOptions('CONFERENCE_RULES_REFUND')
  ConferenceRefundRuleList.value = data
}
//选中用户
const selectDataList = ref([])
const selectData = (data) => {
  selectDataList.value = data
}
//删除用户
const UserSelect_del = (index) => {
  selectDataList.value.splice(index, 1)
}
//更新预约设置
const submit = () => {
  const userId = selectDataList.value.map((item) => item.id)
  const userName = selectDataList.value.map((item) => item.nickname)
  const cancelOrderRule = JSON.stringify({
    unpaid_timeout_cancel: unpaid_timeout_cancel.value
  })
  if (userId.length > 0) {
    refundRule.value.is_review_enabled = userId.join(',')
    refundRule.value.is_review_enabled_name = userName.join(',')
  }

  conferenceApi
    .updatesetting({
      shortName: detail.value.shortName,
      icon: detail.value.icon,
      name: detail.value.name,
      id: detail.value.id,
      sign: detail.value.sign,
      refundSupported: refundSupported.value,
      cancelOrderRule: cancelOrderRule,
      refundRule: JSON.stringify(refundRule.value),
      sign: 'library'
    })
    .then((res) => {
      message.success('保存成功')
    })
}
</script>
<style scoped lang="scss">
.userSpan {
  border: 1px solid #999;
  padding: 0 15px;
  background-color: #fff;
  border-radius: 20px;
  display: flex;
  align-items: center;
}
.del_icon {
  transform: translateY(1px);
  cursor: pointer;
}
</style>
