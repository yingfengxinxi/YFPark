import request from '@/config/axios'

// 应用巡检任务日志 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  application: string // 应用标识
  planId: number // 计划id
  planKey: string // 计划key
  crontab: string // 周期
  rule: number // 规则
  buildTime: Date // 生成时间
  taskTime: Date // 任务执行时间
  status: string // 状态
  sort: number // 执行顺序
  total: number // 累计执行
}

// 应用巡检任务日志 API
export const Api = {
  // 查询应用巡检任务日志分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/patrolTaskEquipmentLog/page`, params })
  },

  // 查询应用巡检任务日志详情
  get: async (id: number) => {
    return await request.get({ url: `bus/patrolTaskEquipmentLog/get?id=` + id })
  },

  // 新增应用巡检任务日志
  create: async (data: VO) => {
    return await request.post({ url: `bus/patrolTaskEquipmentLog/create`, data })
  },

  // 修改应用巡检任务日志
  update: async (data: VO) => {
    return await request.put({ url: `bus/patrolTaskEquipmentLog/update`, data })
  },

  // 删除应用巡检任务日志
  delete: async (id: number) => {
    return await request.delete({ url: `bus/patrolTaskEquipmentLog/delete?id=` + id })
  },

  // 导出应用巡检任务日志 Excel
  export: async (params) => {
    return await request.download({ url: `bus/patrolTaskEquipmentLog/export-excel`, params })
  }
}
