import request from '@/config/axios'

// 工单规则设置 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  application: string // 应用标识
  name: string // 规则名称
  snatchLimit: number // 抢单数上限;/个
  preposeTime: number // 抢单前置时长;/分钟
  robTime: number // 抢单限制时长;单位/分钟
  refundTime: number // 可退款时长;/分钟
  cancelTime: number // 取消订单时长;/分钟
  restartTime: number // 重新打开时长;/分钟
  buildBind: string // 绑定的楼宇信息json
  isDefault: string // 是否为默认配置
}

// 工单规则设置 API
export const WorkOrderRuleApi = {
  // 查询工单规则设置分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/workOrderRule/page`, params })
  },

  // 查询工单规则设置详情
  get: async (id: number) => {
    return await request.get({ url: `bus/workOrderRule/get?id=` + id })
  },

  // 新增工单规则设置
  create: async (data: VO) => {
    return await request.post({ url: `bus/workOrderRule/create`, data })
  },

  // 修改工单规则设置
  update: async (data: VO) => {
    return await request.put({ url: `bus/workOrderRule/update`, data })
  },

  // 删除工单规则设置
  delete: async (id: number) => {
    return await request.delete({ url: `bus/workOrderRule/delete?id=` + id })
  },

  // 导出工单规则设置 Excel
  export: async (params) => {
    return await request.download({ url: `bus/workOrderRule/export-excel`, params })
  }
}
