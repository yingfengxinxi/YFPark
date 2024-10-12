import request from '@/config/axios'

// 资产借出 VO
export interface PropertyLendoutVO {
  id: number // 编号
  orgId: number // 机构id
  villageId: number // 项目id
  buildId: number // 楼宇id
  roomId: number // 房间id
  propertyIds: string // 资产id集合json
  number: string // 单据编号
  processCode: string // 流程编号
  status: number // 单据状态
  lendUid: number // 借用人uid
  departmentId: number // 借用部门id
  lendTime: Date // 借出时间
  expectRevertTime: Date // 预计归还时间
  operateUid: number // 处理人
  operateTime: Date // 处理时间
  remark: string // 借出备注
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 资产借出 API
export const PropertyLendoutApi = {
  // 查询资产借出分页
  getPropertyLendoutPage: async (params: any) => {
    return await request.get({ url: `/bus/property-lendout/page`, params })
  },

  // 查询资产借出详情
  getPropertyLendout: async (id: number) => {
    return await request.get({ url: `/bus/property-lendout/get?id=` + id })
  },

  // 新增资产借出
  createPropertyLendout: async (data: PropertyLendoutVO) => {
    return await request.post({ url: `/bus/property-lendout/create`, data })
  },

  // 修改资产借出
  updatePropertyLendout: async (data: PropertyLendoutVO) => {
    return await request.put({ url: `/bus/property-lendout/update`, data })
  },

  // 删除资产借出
  deletePropertyLendout: async (id: number) => {
    return await request.delete({ url: `/bus/property-lendout/delete?id=` + id })
  },

  // 导出资产借出 Excel
  exportPropertyLendout: async (params) => {
    return await request.download({ url: `/bus/property-lendout/export-excel`, params })
  },
}
