import request from '@/config/axios'

// 工单列表配置 API
export const workOrderProposeOrderApi = {
  // 订单记录分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/workOrderProposeOrder/page`, params })
  },
  // 订单记录详情
  getDetail: async (id: any) => {
    return await request.get({
      url: `bus/workOrderProposeOrder/orderRecordDetail?id=` + id,
      deptIdList: ' '
    })
  },
  //支付订单
  scanCodeBillPay: async (params: any) => {
    return await request.get({ url: `bus/workOrderProposeOrder/scanCodeBillPay`, params })
  },
  //订单统计
  getOrderStatistics: async (params: any) => {
    return await request.get({ url: `bus/workOrderProposeOrder/getOrderStatistics`, params })
  },
  //订单退款
  orderRefund: async (params: any) => {
    return await request.get({ url: `bus/workOrderProposeOrder/orderRefund`, params })
  },
}
