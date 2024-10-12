import request from '@/config/axios'

// 应用巡检计划 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  application: string // 应用标识
  planNumber: string // 计划编号
  planName: string // 计划名称
  stationJson: string // 责任岗位json
  stationId: number // 所选责任岗位id
  departmentId: number // 责任部门
  startDate: Date // 开始日期
  endDate: Date // 截止日期
  dateFlag: string // 日期永久标识
  timeoutType: string // 超时处理规则类型;1=不处理;2=自动完成;3=挂起
  timeLimit: number // 任务时限/小时
  workorderApp: string // 上报工单所属应用标识
  planRule: string // 计划周期规则
  lastTime: Date // 最近一次执行时间
  nextTime: Date // 下次执行时间
  endTime: Date // 计划执行结束时间
  patrolOrder: string // 巡检顺序;1=必须一次;2=可以随机
  patrolPosition: string // 巡检点json
  remindJson: string // 提醒方式及提醒规则
  planStatus: string // 巡检计划状态
  status: string // 计划启用状态;0=关闭;1=开启
  inAdvance: number // 提前完成的时间数量
  minutetime: number // 提前完成的时间单位，1是小时2是天3是分钟
}

// 应用巡检计划 API
export const patrolPlanEquipmentApi = {
  // 查询应用巡检计划分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/patrolPlanEquipment/page`, params })
  },

  // 查询应用巡检计划详情
  get: async (id: number) => {
    return await request.get({ url: `bus/patrolPlanEquipment/get?id=` + id })
  },

  // 新增应用巡检计划
  create: async (data: VO) => {
    return await request.post({ url: `bus/patrolPlanEquipment/create`, data })
  },

  // 修改应用巡检计划
  update: async (data: VO) => {
    return await request.put({ url: `bus/patrolPlanEquipment/update`, data })
  },

  // 删除应用巡检计划
  delete: async (id: number) => {
    return await request.delete({ url: `bus/patrolPlanEquipment/delete?id=` + id })
  },

  // 导出应用巡检计划 Excel
  export: async (params) => {
    return await request.download({ url: `bus/patrolPlanEquipment/export-excel`, params })
  },
  //巡检计划下拉
  getPlanList: async (params: any) => {
    return await request.get({ url: `/bus/patrolPlanEquipment/getList`, params })
  },
  // 顶部巡检计划统计
  getTopCount: async (params: any) => {
    return await request.get({ url: `/bus/patrolPlanEquipment/getTopCount`, params })
  },
  //岗位下拉
  getStationList: async (params: any) => {
    return await request.get({ url: `/system/post/list-all-simple`, params })
  },
  //部门下拉数据
  getDeptList: async (params: any) => {
    return await request.get({ url: `/system/dept/list-all-simple`, params })
  },
  //巡检计划更改状态
  updateStatus: async (params: any) => {
    return await request.put({ url: `/bus/patrolPlanEquipment/updateStatus`, params })
  },
}
