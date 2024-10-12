/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 耗材业务退库 VO
export interface PropertyStuffRetreatVO {
  id: number // 编号
  orgId: number // 机构id
  processCode: string // 流程编号
  processNumber: string // 单据编号
  retreatUid: number // 退库人uid
  departmentId: number // 部门id
  departmentName: string // 部门名称
  depositoryId: number // 入库仓库id
  retreatDate: Date // 退库时间
  status: number // 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
  remark: string // 退库备注
  cuserUid: number // 处理人uid
  muserUid: number // 修改人uid
}

// 耗材业务退库 API
export const PropertyStuffRetreatApi = {
  // 查询耗材业务退库分页
  getPropertyStuffRetreatPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-retreat/page`, params })
  },

  // 查询耗材业务退库详情
  getPropertyStuffRetreat: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-retreat/get?id=` + id })
  },

  // 新增耗材业务退库
  createPropertyStuffRetreat: async (data: PropertyStuffRetreatVO) => {
    return await request.post({ url: `/bus/property-stuff-retreat/create`, data })
  },

  // 修改耗材业务退库
  updatePropertyStuffRetreat: async (data: PropertyStuffRetreatVO) => {
    return await request.put({ url: `/bus/property-stuff-retreat/update`, data })
  },

  // 删除耗材业务退库
  deletePropertyStuffRetreat: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-retreat/delete?id=` + id })
  },

  // 导出耗材业务退库 Excel
  exportPropertyStuffRetreat: async (params) => {
    return await request.download({ url: `/bus/property-stuff-retreat/export-excel`, params })
  }
}
