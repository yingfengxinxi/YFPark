/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 耗材档案信息 VO
export interface PropertyStuffDepositoryVO {
  id: number // 编号
  orgId: number // 机构id
  number: string // 仓库编号
  name: string // 仓库名称
  parentId: number // 上级仓库id
  status: number // 耗材仓库状态;1=启用,0=禁用
  level: string // 上下层级关系
  remark: string // 备注
  sort: number // 序号
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 耗材档案信息 API
export const PropertyStuffDepositoryApi = {
  // 查询耗材档案信息分页
  getPropertyStuffDepositoryPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-depository/page`, params })
  },

  // 查询耗材档案信息详情
  getPropertyStuffDepository: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-depository/get?id=` + id })
  },

  // 新增耗材档案信息
  createPropertyStuffDepository: async (data: PropertyStuffDepositoryVO) => {
    return await request.post({ url: `/bus/property-stuff-depository/create`, data })
  },

  // 修改耗材档案信息
  updatePropertyStuffDepository: async (data: PropertyStuffDepositoryVO) => {
    return await request.put({ url: `/bus/property-stuff-depository/update`, data })
  },

  // 删除耗材档案信息
  deletePropertyStuffDepository: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-depository/delete?id=` + id })
  },

  // 导出耗材档案信息 Excel
  exportPropertyStuffDepository: async (params) => {
    return await request.download({ url: `/bus/property-stuff-depository/export-excel`, params })
  },
  /** 耗材仓库树结构
   * /admin-api/bus/property-stuff-depository/getTree
   * @return
   */
  getPropertyStuffDepositoryTree: async () => {
    return await request.post({ url: `/bus/property-stuff-depository/getTree` })
  }
}
