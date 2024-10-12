import request from '@/config/axios'

//  数据看板API
export const workOrderMyBoardApi = {
  // 近半年工单数统计
  halfYearTread: async (params: any) => {
    return await request.get({ url: `bus/workOrderMyBoard/halfYearTread`, params })
  },
  //工单大类占比
  orderTypeRatio: async (params: any) => {
    return await request.get({ url: `bus/workOrderMyBoard/orderTypeRatio`, params })
  },
  //工单分类占比
  workOrderTypeRatio: async (params: any) => {
    return await request.get({ url: `bus/workOrderMyBoard/workOrderTypeRatio`, params })
  },
  //工单计数
  workOrderNumsTread: async (params: any) => {
    return await request.get({ url: `bus/workOrderMyBoard/workOrderNumsTread`, params })
  },
  //满意度指数
  satisfactionStatic: async (params: any) => {
    return await request.get({ url: `bus/workOrderMyBoard/satisfactionStatic`, params })
  },
  //顶部数据
  topStatic: async (params: any) => {
    return await request.get({ url: `bus/workOrderMyBoard/topStatic`, params })
  }
}
