import request from '@/config/axios'

// 自定义抄表管理员 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  uid: number // 用户uid
  name: string // 管理员姓名
  role: string // 0=管理员,1=超级管理员;
  status: string // 状态;0=禁用,1=启用
}

// 自定义抄表管理员 API
export const EnergyAdmin = {
  // 查询自定义抄表管理员分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/energyAdmin/page`, params })
  },

  // 查询自定义抄表管理员详情
  get: async (id: number) => {
    return await request.get({ url: `bus/energyAdmin/get?id=` + id })
  },

  // 新增自定义抄表管理员
  create: async (data: VO) => {
    return await request.post({ url: `bus/energyAdmin/create`, data })
  },

  // 修改自定义抄表管理员
  update: async (data: VO) => {
    return await request.put({ url: `bus/energyAdmin/update`, data })
  },

  // 删除自定义抄表管理员
  delete: async (id: number) => {
    return await request.delete({ url: `bus/energyAdmin/delete?id=` + id })
  },

  // 导出自定义抄表管理员 Excel
  export: async (params) => {
    return await request.download({ url: `bus/energyAdmin/export-excel`, params })
  }
}
