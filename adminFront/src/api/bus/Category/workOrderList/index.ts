import request from '@/config/axios'

// 工单列表配置 API
export const workOrderProposeApi = {
  // 查询工单列表配置分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/workOrderPropose/page`, params })
  },
  //创建机构工单数据\
  create: async (data: any) => {
    return await request.post({ url: `bus/workOrderPropose/create`, data })
  },
  //获取顶部数据统计
  getStatic: async (params: any) => {
    return await request.get({ url: `bus/workOrderPropose/getStatic`, params })
  },
  //获取工单详情
  getDetail: async (id: string) => {
    return await request.get({ url: `bus/workOrderPropose/workDetail?id=` + id })
  },
  //指派工单
  assignWorkOrder: async (params: any) => {
    return await request.get({ url: `bus/workOrderPropose/assignWorkOrder`, params })
  },
  //直接回复
  handelWorkOrder: async (params: any) => {
    return await request.put({
      url: `bus/workOrderPropose/handelWorkOrder`,
      data: params,
      headersType: 'application/json'
    })
  },
  //工单通知
  getbroadcast: async (params: any) => {
    return await request.get({
      url: `bus/workOrderPropose/broadcast`,
      params
    })
  }
}
