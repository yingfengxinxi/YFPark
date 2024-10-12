import request from '@/config/axios'

// 资产调拨 VO
export interface PropertyTransferVO {
  id: number // 编号
  orgId: number // 机构id
  propertyIds: string // 资产id
  number: string // 单据编号
  processCode: string // 流程编号
  status: number // 单据状态
  outAdminUid: number // 调出管理员uid
  inAdminUid: number // 调入管理员uid
  inLocationId: number // 调入位置id
  operateUid: number // 处理人
  operateTime: Date // 处理时间
  remark: string // 借出备注
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 资产调拨 API
export const PropertyTransferApi = {
  // 查询资产调拨分页
  getPropertyTransferPage: async (params: any) => {
    return await request.get({ url: `/bus/property-transfer/page`, params })
  },

  // 查询资产调拨详情
  getPropertyTransfer: async (id: number) => {
    return await request.get({ url: `/bus/property-transfer/get?id=` + id })
  },

  // 新增资产调拨
  createPropertyTransfer: async (data: PropertyTransferVO) => {
    return await request.post({ url: `/bus/property-transfer/create`, data })
  },

  // 修改资产调拨
  updatePropertyTransfer: async (data: PropertyTransferVO) => {
    return await request.put({ url: `/bus/property-transfer/update`, data })
  },

  // 删除资产调拨
  deletePropertyTransfer: async (id: number) => {
    return await request.delete({ url: `/bus/property-transfer/delete?id=` + id })
  },

  // 导出资产调拨 Excel
  exportPropertyTransfer: async (params) => {
    return await request.download({ url: `/bus/property-transfer/export-excel`, params })
  },
}
