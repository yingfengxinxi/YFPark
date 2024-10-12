import request from '@/config/axios'

// 资产管理子应用管理员配置 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  uid: number // 用户uid
  departmentId: string // 部门id
  level: string // 管理员级别,1=普通管理员;99=超级管理员
  name: string // 姓名
  avatar: string // 用户头像
  phone: string // 手机号码
  status: string // 状态;0=禁用,1=启用
  lastRole: string // 最后访问角色类型
  lastTime: Date // 最后访问登录时间
}

// 资产管理子应用管理员配置 API
export const Api = {
  // 查询资产管理子应用管理员配置分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/patrolAdmin/page`, params })
  },

  // 查询资产管理子应用管理员配置详情
  get: async (id: number) => {
    return await request.get({ url: `bus/patrolAdmin/get?id=` + id })
  },

  // 新增资产管理子应用管理员配置
  create: async (data: VO) => {
    return await request.post({ url: `bus/patrolAdmin/create`, data })
  },

  // 修改资产管理子应用管理员配置
  update: async (data: VO) => {
    return await request.put({ url: `bus/patrolAdmin/update`, data })
  },

  // 删除资产管理子应用管理员配置
  delete: async (id: number) => {
    return await request.delete({ url: `bus/patrolAdmin/delete?id=` + id })
  },

  // 导出资产管理子应用管理员配置 Excel
  export: async (params) => {
    return await request.download({ url: `bus/patrolAdmin/export-excel`, params })
  }
}
