import request from '@/config/axios'

// 位置 VO
export interface PropertyLocationVO {
  id: number // 编号
  orgId: number // 机构ID
  number: string // 位置编码
  name: string // 位置名称
  level: string // 位置级别字符串，逗号拼接上级id
  parentId: number // 父级id
  param: string // 参数
  remark: string // 备注
  sort: number // 排序
  status: number // 显示状态，2禁用 1显示，0隐藏
}

// 位置 API
export const PropertyLocationApi = {
  // 查询位置分页
  getPropertyLocationPage: async (params: any) => {
    return await request.get({ url: `/bus/property-location/page`, params })
  },

  // 查询位置详情
  getPropertyLocation: async (id: number) => {
    return await request.get({ url: `/bus/property-location/get?id=` + id })
  },

  // 新增位置
  createPropertyLocation: async (data: PropertyLocationVO) => {
    return await request.post({ url: `/bus/property-location/create`, data })
  },

  // 修改位置
  updatePropertyLocation: async (data: PropertyLocationVO) => {
    return await request.put({ url: `/bus/property-location/update`, data })
  },

  // 删除位置
  deletePropertyLocation: async (id: number) => {
    return await request.delete({ url: `/bus/property-location/delete?id=` + id })
  },

  // 导出位置 Excel
  exportPropertyLocation: async (params) => {
    return await request.download({ url: `/bus/property-location/export-excel`, params })
  },
  //获取资产位置树
  getPropertyPositionTree: async (params: any) => {
    return await request.post({ url: `/bus/property-location/getTree`, params })
  }
}
