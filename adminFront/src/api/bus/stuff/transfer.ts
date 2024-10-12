import request from '@/config/axios'

// 耗材业务调拨 VO
export interface PropertyStuffTransferVO {
  id: number // 编号
  orgId: number // 机构id
  processCode: string // 流程编号
  processNumber: string // 单据编号
  outAdminUid: number // 调出管理员
  inAdminUid: number // 调入管理员
  outDepositoryId: number // 调出仓库
  inDepositoryId: number // 调入仓库
  totalNum: number // 合计数量
  totalPrice: number // 合计金额
  status: number // 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
  remark: string // 调拨备注
  cuserUid: number // 处理人uid
  muserUid: number // 修改人uid
}

// 耗材业务调拨 API
export const PropertyStuffTransferApi = {
  // 查询耗材业务调拨分页
  getPropertyStuffTransferPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-transfer/page`, params })
  },

  // 查询耗材业务调拨详情
  getPropertyStuffTransfer: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-transfer/get?id=` + id })
  },

  // 新增耗材业务调拨
  createPropertyStuffTransfer: async (data: PropertyStuffTransferVO) => {
    return await request.post({ url: `/bus/property-stuff-transfer/create`, data })
  },

  // 修改耗材业务调拨
  updatePropertyStuffTransfer: async (data: PropertyStuffTransferVO) => {
    return await request.put({ url: `/bus/property-stuff-transfer/update`, data })
  },

  // 删除耗材业务调拨
  deletePropertyStuffTransfer: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-transfer/delete?id=` + id })
  },

  // 导出耗材业务调拨 Excel
  exportPropertyStuffTransfer: async (params) => {
    return await request.download({ url: `/bus/property-stuff-transfer/export-excel`, params })
  },
}
