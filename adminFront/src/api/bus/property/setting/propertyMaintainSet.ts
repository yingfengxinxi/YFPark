import request from '@/config/axios'

// 资产保养设置 VO
export interface PropertyMaintainSetVO {
  id: number // 编号
  orgId: number // 机构id
  maintainVillageName: string // 保养项目名称
  creatorName: string // 创建人名称
  status: number // 状态 0禁用 1启用
}

// 资产保养设置 API
export const PropertyMaintainSetApi = {
  /** 获取保养项目不带分页
   * /admin-api/bus/property-maintain-set/getList
   * @return Promise<any>
   */
  getPropertyMaintainSetList: async (params: any) => {
    return await request.get({ url: `/bus/property-maintain-set/getList` })
  },
  // 查询资产保养设置分页
  getPropertyMaintainSetPage: async (params: any) => {
    return await request.get({ url: `/bus/property-maintain-set/page`, params })
  },

  // 查询资产保养设置详情
  getPropertyMaintainSet: async (id: number) => {
    return await request.get({ url: `/bus/property-maintain-set/get?id=` + id })
  },

  // 新增资产保养设置
  createPropertyMaintainSet: async (data: PropertyMaintainSetVO) => {
    return await request.post({ url: `/bus/property-maintain-set/create`, data })
  },

  // 修改资产保养设置
  updatePropertyMaintainSet: async (data: PropertyMaintainSetVO) => {
    return await request.put({ url: `/bus/property-maintain-set/update`, data })
  },

  // 删除资产保养设置
  deletePropertyMaintainSet: async (id: number) => {
    return await request.delete({ url: `/bus/property-maintain-set/delete?id=` + id })
  },

  // 导出资产保养设置 Excel
  exportPropertyMaintainSet: async (params) => {
    return await request.download({ url: `/bus/property-maintain-set/export-excel`, params })
  }
  /** 资产保养 */
}
