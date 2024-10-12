import request from '@/config/axios'

// 耗材业务处置 VO
export interface PropertyStuffHandleVO {
  id: number // 编号
  orgId: number // 机构id
  processCode: string // 流程编号
  processNumber: string // 单据编号
  departmentId: number // 发起部门id
  departmentName: string // 发起部门名称
  depositoryId: number // 处置仓库id
  launchTime: Date // 发起时间
  totalPrice: number // 合计金额
  status: number // 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
  remark: string // 处置原因
  cuserUid: number // 发起人uid
  muserUid: number // 修改人uid
}

// 耗材业务处置 API
export const PropertyStuffHandleApi = {
  // 查询耗材业务处置分页
  getPropertyStuffHandlePage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-handle/page`, params })
  },

  // 查询耗材业务处置详情
  getPropertyStuffHandle: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-handle/get?id=` + id })
  },

  // 新增耗材业务处置
  createPropertyStuffHandle: async (data: PropertyStuffHandleVO) => {
    return await request.post({ url: `/bus/property-stuff-handle/create`, data })
  },

  // 修改耗材业务处置
  updatePropertyStuffHandle: async (data: PropertyStuffHandleVO) => {
    return await request.put({ url: `/bus/property-stuff-handle/update`, data })
  },

  // 删除耗材业务处置
  deletePropertyStuffHandle: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-handle/delete?id=` + id })
  },

  // 导出耗材业务处置 Excel
  exportPropertyStuffHandle: async (params) => {
    return await request.download({ url: `/bus/property-stuff-handle/export-excel`, params })
  },
}
