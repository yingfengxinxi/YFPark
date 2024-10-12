import request from '@/config/axios'

// 资产派发/退库 VO
export interface PropertyHandoutVO {
  id: number // 编号
  orgId: number // 机构id
  villageId: number // 项目id
  buildId: number // 楼宇id
  roomId: number // 房间id
  propertyIds: string // 资产id集合json
  processCode: string // 流程编号
  type: number // 数据类型;1=派发;2=退库
  number: string // 单据编号
  status: number // 单据状态
  departmentId: number // 所选部门id
  receiveUid: number // 领用人
  handoutTime: Date // 派发日期
  returnTime: Date // 退库日期
  operateUid: number // 处理人
  operateTime: Date // 处理时间
  remark: string // 处理备注
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 资产派发/退库 API
export const PropertyHandoutApi = {
  // 查询资产派发/退库分页
  getPropertyHandoutPage: async (params: any) => {
    return await request.get({ url: `/bus/property-handout/page`, params })
  },

  // 查询资产派发/退库详情
  getPropertyHandout: async (id: number) => {
    return await request.get({ url: `/bus/property-handout/get?id=` + id })
  },

  // 新增资产派发/退库
  createPropertyHandout: async (data: PropertyHandoutVO) => {
    return await request.post({ url: `/bus/property-handout/create`, data })
  },

  // 修改资产派发/退库
  updatePropertyHandout: async (data: PropertyHandoutVO) => {
    return await request.put({ url: `/bus/property-handout/update`, data })
  },

  // 删除资产派发/退库
  deletePropertyHandout: async (id: number) => {
    return await request.delete({ url: `/bus/property-handout/delete?id=` + id })
  },

  // 导出资产派发/退库 Excel
  exportPropertyHandout: async (params) => {
    return await request.download({ url: `/bus/property-handout/export-excel`, params })
  },
}
