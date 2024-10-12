import request from '@/config/axios'

// 耗材入库记录 VO
export interface PropertyStuffEnterVO {
  id: number // 编号
  orgId: number // 机构id
  number: string // 单据编号
  processCode: string // 流程编号
  depositoryId: number // 入库仓库id
  enterUid: number // 入库处理人uid
  enterTime: Date // 入库时间
  supplier: string // 供应商
  totalPrice: number // 合计金额
  status: number // 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
  remark: string // 备注
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 耗材入库记录 API
export const PropertyStuffEnterApi = {
  // 查询耗材入库记录分页
  getPropertyStuffEnterPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-enter/page`, params })
  },

  // 查询耗材入库记录详情
  getPropertyStuffEnter: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-enter/get?id=` + id })
  },

  // 新增耗材入库记录
  createPropertyStuffEnter: async (data: PropertyStuffEnterVO) => {
    return await request.post({ url: `/bus/property-stuff-enter/create`, data })
  },

  // 修改耗材入库记录
  updatePropertyStuffEnter: async (data: PropertyStuffEnterVO) => {
    return await request.put({ url: `/bus/property-stuff-enter/update`, data })
  },

  // 删除耗材入库记录
  deletePropertyStuffEnter: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-enter/delete?id=` + id })
  },

  // 导出耗材入库记录 Excel
  exportPropertyStuffEnter: async (params) => {
    return await request.download({ url: `/bus/property-stuff-enter/export-excel`, params })
  },
}
