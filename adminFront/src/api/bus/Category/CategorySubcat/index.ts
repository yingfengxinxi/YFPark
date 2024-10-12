import request from '@/config/axios'

// 工单分类子类信息 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  application: string // 应用标识
  name: string // 子类名称
  categorySubcatId: number // 工单大类id
  stationJson: string // 子类绑定的岗位信息json
  departmentId: number // 部门id
  labelIds: string // 标签id集合
  sort: number // 排序值
  submitType: string // 提交工单面向类型;1=所有人,2=租客,3=工作人员
  status: string // 启用状态;0=否;1=是
}

// 工单分类子类信息 API
export const CategorySubcatApi = {
  // 查询工单分类子类信息分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/categorySubcat/page`, params })
  },

  // 查询工单分类子类信息详情
  get: async (id: number) => {
    return await request.get({ url: `bus/categorySubcat/get?id=` + id })
  },

  // 新增工单分类子类信息
  create: async (data: VO) => {
    return await request.post({ url: `bus/categorySubcat/create`, data })
  },

  // 修改工单分类子类信息
  update: async (data: VO) => {
    return await request.put({ url: `bus/categorySubcat/update`, data })
  },

  // 删除工单分类子类信息
  delete: async (id: number) => {
    return await request.delete({ url: `bus/categorySubcat/delete?id=` + id })
  },

  // 导出工单分类子类信息 Excel
  export: async (params) => {
    return await request.download({ url: `bus/categorySubcat/export-excel`, params })
  },
  // 更改启用状态
  changeStatus: async (data: any) => {
    return await request.put({
      url: `bus/categorySubcat/subcatStatus`,
      data
    })
  },
  /**
   * 收费设置模块
   */
  // 查询工单分类子类收费设置
  getCharge: async (params: number) => {
    return await request.get({ url: `/bus/categoryFeeSet/getFeeSet`, params })
  },
  // 创建工单分类子类收费设置
  categoryFeeSetCreate: async (data: any) => {
    return await request.post({ url: `bus/categoryFeeSet/save`, data })
  },
  /***
   * 选择物料
   */
  // 查询工单分类子类物料
  getMaterial: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-stock/stockListPage`, params })
  }
}
