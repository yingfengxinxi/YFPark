import request from '@/config/axios'

// 资产耗材分类 VO
export interface PropertyStuffCategoryVO {
  id: number // 编号
  orgId: number // 机构ID
  number: string // 分类编码
  name: string // 分类名称
  parentId: number // 父级id
  param: string // 参数
  level: string // 上下层级关系
  remark: string // 备注
  sort: number // 排序
  status: number // 显示状态，2禁用 1显示，0隐藏
}

// 资产耗材分类 API
export const PropertyStuffCategoryApi = {
  // 查询资产耗材分类分页
  getPropertyStuffCategoryPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-category/page`, params })
  },

  // 查询资产耗材分类详情
  getPropertyStuffCategory: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-category/get?id=` + id })
  },

  // 新增资产耗材分类
  createPropertyStuffCategory: async (data: PropertyStuffCategoryVO) => {
    return await request.post({ url: `/bus/property-stuff-category/create`, data })
  },

  // 修改资产耗材分类
  updatePropertyStuffCategory: async (data: PropertyStuffCategoryVO) => {
    return await request.put({ url: `/bus/property-stuff-category/update`, data })
  },

  // 删除资产耗材分类
  deletePropertyStuffCategory: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-category/delete?id=` + id })
  },

  // 导出资产耗材分类 Excel
  exportPropertyStuffCategory: async (params) => {
    return await request.download({ url: `/bus/property-stuff-category/export-excel`, params })
  },
  /** 耗材分类树结构
   * /admin-api/bus/property-stuff-category/getTree
   * @returns
   */
  getPropertyStuffCategoryTree: async () => {
    return await request.post({ url: `/bus/property-stuff-category/getTree` })
  }
}
