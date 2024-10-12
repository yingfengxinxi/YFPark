import request from '@/config/axios'

// 资产仓库信息 VO
export interface PropertyDepositoryVO {
  id: number // 编号
  orgId: number // 机构id
  number: string // 单据编号
  status: number // 单据状态
  operateUid: number // 入库处理人
  operateTime: Date // 入库处理时间
  remark: string // 入库备注
  processCode: string // 流程编号
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 资产仓库信息 API
export const PropertyDepositoryApi = {
  // 查询资产仓库信息分页
  getPropertyDepositoryPage: async (params: any) => {
    return await request.get({ url: `/bus/property-depository/page`, params })
  },

  // 查询资产仓库信息详情
  getPropertyDepository: async (id: number) => {
    return await request.get({ url: `/bus/property-depository/get?id=` + id })
  },

  // 新增资产仓库信息
  createPropertyDepository: async (data: PropertyDepositoryVO) => {
    return await request.post({ url: `/bus/property-depository/create`, data })
  },

  // 修改资产仓库信息
  updatePropertyDepository: async (data: PropertyDepositoryVO) => {
    return await request.put({ url: `/bus/property-depository/update`, data })
  },

  // 删除资产仓库信息
  deletePropertyDepository: async (id: number) => {
    return await request.delete({ url: `/bus/property-depository/delete?id=` + id })
  },

  // 导出资产仓库信息 Excel
  exportPropertyDepository: async (params) => {
    return await request.download({ url: `/bus/property-depository/export-excel`, params })
  },
}
