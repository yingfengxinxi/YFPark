/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 资产保养记录 VO
export interface PropertyMaintainVO {
  id: number // 编号
  orgId: number // 机构id
  maintainUid: number // 保养人uid
  departmentId: number // 保养人所在部门
  departmentName: string // 保养人所在部门名称
  maintainVillageId: string // 保养项目id
  propertyIds: string // 资产id集合json
  propertyData: string // 资产集合json
  number: string // 单据编号
  processCode: string // 流程编号
  status: number // 单据状态
  maintainTotalPrice: number // 保养总金额
  maintainDate: Date // 保养时间
  nextMaintainDate: Date // 下次保养时间
  operateUid: number // 处理人
  operateTime: Date // 处理时间
  remark: string // 保养备注
  cuserUid: number // 操作人
  muserUid: number // 修改人
}

// 资产保养记录 API
export const PropertyMaintainApi = {
  // 查询资产保养记录分页
  getPropertyMaintainPage: async (params: any) => {
    return await request.get({ url: `/bus/property-maintain/page`, params })
  },

  // 查询资产保养记录详情
  getPropertyMaintain: async (id: number) => {
    return await request.get({ url: `/bus/property-maintain/get?id=` + id })
  },

  // 新增资产保养记录
  createPropertyMaintain: async (data: PropertyMaintainVO) => {
    return await request.post({ url: `/bus/property-maintain/create`, data })
  },

  // 修改资产保养记录
  updatePropertyMaintain: async (data: PropertyMaintainVO) => {
    return await request.put({ url: `/bus/property-maintain/update`, data })
  },

  // 删除资产保养记录
  deletePropertyMaintain: async (id: number) => {
    return await request.delete({ url: `/bus/property-maintain/delete?id=` + id })
  },

  // 导出资产保养记录 Excel
  exportPropertyMaintain: async (params) => {
    return await request.download({ url: `/bus/property-maintain/export-excel`, params })
  }
}
