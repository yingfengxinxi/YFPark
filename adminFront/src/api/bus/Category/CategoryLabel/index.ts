import request from '@/config/axios'

// 工单分类标签配置 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  application: string // 应用标识
  name: string // 标签名称
  subcatId: number // 工单子类id
  sort: number // 排序值
  lastTime: Date // 最近操作时间
  status: string // 状态
}

// 工单分类标签配置 API
export const categoryLabelApi = {
  // 查询工单分类标签配置分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/categoryLabel/page`, params })
  },

  // 查询工单分类标签配置详情
  get: async (id: number) => {
    return await request.get({ url: `bus/categoryLabel/get?id=` + id })
  },

  // 新增工单分类标签配置
  create: async (data: VO) => {
    return await request.post({ url: `bus/categoryLabel/create`, data })
  },

  // 修改工单分类标签配置
  update: async (data: VO) => {
    return await request.put({ url: `bus/categoryLabel/update`, data })
  },

  // 删除工单分类标签配置
  delete: async (id: number) => {
    return await request.delete({ url: `bus/categoryLabel/delete?id=` + id })
  },

  // 导出工单分类标签配置 Excel
  export: async (params) => {
    return await request.download({ url: `bus/categoryLabel/export-excel`, params })
  },

  //更新工单分类标签状态配置
  updateStatus: async (params) => {
    return await request.put({ url: `bus/categoryLabel/update`, params })
  },
  //工单分类标签下拉框
  getList: async (params: any) => {
    return await request.get({ url: `bus/categoryLabel/getList`, params })
  }
}
