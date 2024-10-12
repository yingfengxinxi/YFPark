import request from '@/config/axios'

// 耗材业务调整 VO
export interface PropertyStuffAdjustVO {
  id: number // 编号
  orgId: number // 机构id
  processCode: string // 流程编号
  processNumber: string // 单据编号
  depositoryId: number // 调整仓库id
  adjustTime: Date // 调整时间
  status: number // 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
  remark: string // 调整备注
  cuserUid: number // 处理人uid
  muserUid: number // 修改人uid
}

// 耗材业务调整 API
export const PropertyStuffAdjustApi = {
  // 查询耗材业务调整分页
  getPropertyStuffAdjustPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-adjust/page`, params })
  },

  // 查询耗材业务调整详情
  getPropertyStuffAdjust: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-adjust/get?id=` + id })
  },

  // 新增耗材业务调整
  createPropertyStuffAdjust: async (data: PropertyStuffAdjustVO) => {
    return await request.post({ url: `/bus/property-stuff-adjust/create`, data })
  },

  // 修改耗材业务调整
  updatePropertyStuffAdjust: async (data: PropertyStuffAdjustVO) => {
    return await request.put({ url: `/bus/property-stuff-adjust/update`, data })
  },

  // 删除耗材业务调整
  deletePropertyStuffAdjust: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-adjust/delete?id=` + id })
  },

  // 导出耗材业务调整 Excel
  exportPropertyStuffAdjust: async (params) => {
    return await request.download({ url: `/bus/property-stuff-adjust/export-excel`, params })
  },
}
