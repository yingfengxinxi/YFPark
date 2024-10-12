import request from '@/config/axios'

// 耗材业务派发 VO
export interface PropertyStuffHandoutVO {
  id: number // 编号
  orgId: number // 机构id
  processCode: string // 流程编号
  processNumber: string // 单据编号
  receiveUid: number // 领用人uid
  departmentId: number // 领用部门id
  departmentName: string // 领用部门名称
  depositoryId: number // 出库所属仓库id
  handoutTime: Date // 派发时间
  status: number // 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
  remark: string // 派发备注
  cuserUid: number // 处理人uid
  muserUid: number // 修改人uid
}

// 耗材业务派发 API
export const PropertyStuffHandoutApi = {
  // 查询耗材业务派发分页
  getPropertyStuffHandoutPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-handout/page`, params })
  },

  // 查询耗材业务派发详情
  getPropertyStuffHandout: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-handout/get?id=` + id })
  },

  // 新增耗材业务派发
  createPropertyStuffHandout: async (data: PropertyStuffHandoutVO) => {
    return await request.post({ url: `/bus/property-stuff-handout/create`, data })
  },

  // 修改耗材业务派发
  updatePropertyStuffHandout: async (data: PropertyStuffHandoutVO) => {
    return await request.put({ url: `/bus/property-stuff-handout/update`, data })
  },

  // 删除耗材业务派发
  deletePropertyStuffHandout: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-handout/delete?id=` + id })
  },

  // 导出耗材业务派发 Excel
  exportPropertyStuffHandout: async (params) => {
    return await request.download({ url: `/bus/property-stuff-handout/export-excel`, params })
  },
}
