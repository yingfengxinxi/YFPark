import request from '@/config/axios'

// 账单缴费通知单模板 VO
export interface VO {
  id: number // 编号
  name: string // 通知单模板名称
  templatePath: string // 模板上传地址
  buildBind: string // 应用楼宇
}

// 账单缴费通知单模板 API
export const Api = {
  // 查询账单缴费通知单模板分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillNoticeTemplate/page`, params })
  },

  // 查询账单缴费通知单模板详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/orgBillNoticeTemplate/get?id=` + id })
  },

  // 新增账单缴费通知单模板
  create: async (data: VO) => {
    return await request.post({ url: `/bus/orgBillNoticeTemplate/create`, data })
  },

  // 修改账单缴费通知单模板
  update: async (data: VO) => {
    return await request.put({ url: `/bus/orgBillNoticeTemplate/update`, data })
  },

  // 删除账单缴费通知单模板
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillNoticeTemplate/delete?id=` + id })
  },

  // 导出账单缴费通知单模板 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/orgBillNoticeTemplate/export-excel`, params })
  }
}
