import request from '@/config/axios'

// 资产分类 VO
export interface PropertyCategoryVO {
  id: number // 编号
  orgId: number // 机构ID
  number: string // 分类编码
  name: string // 分类名称
  level: string // 分类级别字符串，逗号拼接上级id
  parentId: number // 父级id
  param: string // 参数
  remark: string // 备注
  sort: number // 排序
  status: number // 显示状态，2禁用 1显示，0隐藏
}

// 资产分类 API
export const PropertyCategoryApi = {
  // 查询资产分类分页
  getPropertyCategoryPage: async (params: any) => {
    return await request.get({ url: `/bus/property-category/page`, params })
  },

  // 查询资产分类详情
  getPropertyCategory: async (id: number) => {
    return await request.get({ url: `/bus/property-category/get?id=` + id })
  },

  // 新增资产分类
  createPropertyCategory: async (data: PropertyCategoryVO) => {
    return await request.post({ url: `/bus/property-category/create`, data })
  },

  // 修改资产分类
  updatePropertyCategory: async (data: PropertyCategoryVO) => {
    return await request.put({ url: `/bus/property-category/update`, data })
  },

  // 删除资产分类
  deletePropertyCategory: async (id: number) => {
    return await request.delete({ url: `/bus/property-category/delete?id=` + id })
  },

  // 导出资产分类 Excel
  exportPropertyCategory: async (params) => {
    return await request.download({ url: `/bus/property-category/export-excel`, params })
  },
}
