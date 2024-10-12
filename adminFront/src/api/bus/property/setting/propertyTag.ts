import request from '@/config/axios'

// 资产标签模板 VO
export interface PropertyTagVO {
  id: number // 编号
  orgId: number // 机构ID
  isApply: number // 0=未应用;1=应用
  isDefault: number // 0=自定义模板;1=系统默认模板
  templatePath: string // 模板链接
  sort: number // 排序
  name: string // 模板名称
  fieldLimit: number // 字段上限数量
  hasLogo: number // 有无logo;0=无;1=有
  applyJson: string // 应用数据json
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 资产标签模板 API
export const PropertyTagApi = {
  // 查询资产标签模板分页
  getPropertyTagPage: async (params: any) => {
    return await request.get({ url: `/bus/property-tag/page`, params })
  },

  // 查询资产标签模板详情
  getPropertyTag: async (id: number) => {
    return await request.get({ url: `/bus/property-tag/get?id=` + id })
  },

  // 新增资产标签模板
  createPropertyTag: async (data: PropertyTagVO) => {
    return await request.post({ url: `/bus/property-tag/create`, data })
  },

  // 修改资产标签模板
  updatePropertyTag: async (data: PropertyTagVO) => {
    return await request.put({ url: `/bus/property-tag/update`, data })
  },

  // 删除资产标签模板
  deletePropertyTag: async (id: number) => {
    return await request.delete({ url: `/bus/property-tag/delete?id=` + id })
  },

  // 导出资产标签模板 Excel
  exportPropertyTag: async (params) => {
    return await request.download({ url: `/bus/property-tag/export-excel`, params })
  },
}
