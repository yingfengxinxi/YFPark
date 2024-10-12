import request from '@/config/axios'

// 巡检表单设置 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构Id
  application: string // 应用标识
  title: string // 标题
  content: string // 表单内容
  isDefault: string // 是否为默认配置0=否1=是
}

// 巡检表单设置 API
export const formApi = {
  // 查询巡检表单设置分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/patrolForm/page`, params })
  },

  // 查询巡检表单设置详情
  get: async (id: number) => {
    return await request.get({ url: `bus/patrolForm/get?id=` + id })
  },

  // 新增巡检表单设置
  create: async (data: VO) => {
    return await request.post({ url: `bus/patrolForm/create`, data })
  },

  // 修改巡检表单设置
  update: async (data: VO) => {
    return await request.put({ url: `bus/patrolForm/update`, data })
  },

  // 删除巡检表单设置
  delete: async (id: number) => {
    return await request.delete({ url: `bus/patrolForm/delete?id=` + id })
  },

  // 导出巡检表单设置 Excel
  export: async (params) => {
    return await request.download({ url: `bus/patrolForm/export-excel`, params })
  }
}
