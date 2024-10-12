import request from '@/config/axios'
export const SurrenderApi = {
  // 查询账单缴费通知单模板分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/contractTemplate/retreatContractPage`, params })
  },

  // 查询账单缴费通知单模板详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/contractTemplate/retreatContractGet?id=` + id })
  },

  // 新增账单缴费通知单模板
  create: async (data: VO) => {
    return await request.post({ url: `/bus/contractTemplate/retreatContractAdd`, data })
  },

  // 修改账单缴费通知单模板
  update: async (data: VO) => {
    return await request.put({ url: `/bus/contractTemplate/retreatContractUpdate`, data })
  },

  // 删除账单缴费通知单模板
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/contractTemplate/retreatContractDelete?id=` + id })
  }
}
