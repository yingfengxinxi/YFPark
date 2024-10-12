import request from '@/config/axios'

// 资产维修 VO
export interface PropertyRepairVO {
  id: number // 编号
  orgId: number // 机构id
  propertyIds: string // 资产id
  number: string // 单据编号
  processCode: string // 流程编号
  status: number // 单据状态
  repairUid: number // 报修人uid
  repairDepartmentId: number // 报修部门id
  repairTime: Date // 报修时间
  repairReason: string // 报修原因
  operateUid: number // 处理人
  expectRepairPrice: string // 预计维修金额
  operateTime: Date // 处理时间
  repairContent: string // 维修内容
  workorderInfo: string // 工单信息
  remark: string // 备注
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 资产维修 API
export const PropertyRepairApi = {
  // 查询资产维修分页
  getPropertyRepairPage: async (params: any) => {
    return await request.get({ url: `/bus/property-repair/page`, params })
  },

  // 查询资产维修详情
  getPropertyRepair: async (id: number) => {
    return await request.get({ url: `/bus/property-repair/get?id=` + id })
  },

  // 新增资产维修
  createPropertyRepair: async (data: PropertyRepairVO) => {
    return await request.post({ url: `/bus/property-repair/create`, data })
  },

  // 修改资产维修
  updatePropertyRepair: async (data: PropertyRepairVO) => {
    return await request.put({ url: `/bus/property-repair/update`, data })
  },

  // 删除资产维修
  deletePropertyRepair: async (id: number) => {
    return await request.delete({ url: `/bus/property-repair/delete?id=` + id })
  },

  // 导出资产维修 Excel
  exportPropertyRepair: async (params) => {
    return await request.download({ url: `/bus/property-repair/export-excel`, params })
  },
}
