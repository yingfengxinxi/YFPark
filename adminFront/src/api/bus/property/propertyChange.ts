import request from '@/config/axios'

// 资产变更领用人 VO
export interface PropertyChangeVO {
  id: number // 编号
  orgId: number // 机构id
  propertyIds: string // 资产id
  number: string // 单据编号
  processCode: string // 流程编号
  status: number // 单据状态
  afterUseUid: number // 变更后使用人uid
  afterUseDepartmentId: number // 变更后使用部门id
  afterTime: Date // 变更时间
  operateUid: number // 处理人
  operateTime: Date // 处理时间
  remark: string // 变更说明
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 资产变更领用人 API
export const PropertyChangeApi = {
  // 查询资产变更领用人分页
  getPropertyChangePage: async (params: any) => {
    return await request.get({ url: `/bus/property-change/page`, params })
  },

  // 查询资产变更领用人详情
  getPropertyChange: async (id: number) => {
    return await request.get({ url: `/bus/property-change/get?id=` + id })
  },

  // 新增资产变更领用人
  createPropertyChange: async (data: PropertyChangeVO) => {
    return await request.post({ url: `/bus/property-change/create`, data })
  },

  // 修改资产变更领用人
  updatePropertyChange: async (data: PropertyChangeVO) => {
    return await request.put({ url: `/bus/property-change/update`, data })
  },

  // 删除资产变更领用人
  deletePropertyChange: async (id: number) => {
    return await request.delete({ url: `/bus/property-change/delete?id=` + id })
  },

  // 导出资产变更领用人 Excel
  exportPropertyChange: async (params) => {
    return await request.download({ url: `/bus/property-change/export-excel`, params })
  },
}
