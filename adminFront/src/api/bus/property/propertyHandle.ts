import request from '@/config/axios'

// 资产处置单据记录 VO
export interface PropertyHandleVO {
  id: number // 编号
  orgId: number // 机构id
  departmentId: number // 发起部门id
  processNumber: string // 单据编号
  status: number // 单据状态;1=进行中
  handleAmount: number // 处置金额
  handleExpenses: number // 处置费用
  handleType: number // 处置类型
  remark: string // 处置原因
  applyTime: Date // 发起时间
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 资产处置单据记录 API
export const PropertyHandleApi = {
  // 查询资产处置单据记录分页
  getPropertyHandlePage: async (params: any) => {
    return await request.get({ url: `/bus/property-handle/page`, params })
  },

  // 查询资产处置单据记录详情
  getPropertyHandle: async (id: number) => {
    return await request.get({ url: `/bus/property-handle/get?id=` + id })
  },

  // 新增资产处置单据记录
  createPropertyHandle: async (data: PropertyHandleVO) => {
    return await request.post({ url: `/bus/property-handle/create`, data })
  },

  // 修改资产处置单据记录
  updatePropertyHandle: async (data: PropertyHandleVO) => {
    return await request.put({ url: `/bus/property-handle/update`, data })
  },

  // 删除资产处置单据记录
  deletePropertyHandle: async (id: number) => {
    return await request.delete({ url: `/bus/property-handle/delete?id=` + id })
  },

  // 导出资产处置单据记录 Excel
  exportPropertyHandle: async (params) => {
    return await request.download({ url: `/bus/property-handle/export-excel`, params })
  },
}
