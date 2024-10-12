import request from '@/config/axios'

// 应用巡检任务 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  application: string // 应用标识
  planId: number // 计划id
  patrolOrder: string // 巡检顺序;1=必须依次;2=可以随机
  taskCycle: string // 任务周期
  taskKey: string // 任务key
  taskName: string // 任务名称
  taskNumber: string // 任务编号
  startTime: Date // 开始时间
  shouldTime: Date // 应开始时间
  remindTime: Date // 任务提醒时间
  stationId: number // 负责岗位id
  departmentId: number // 责任部门id
  postUids: string // 岗位部门人员uids
  endTime: Date // 结束时间
  timeoutTime: Date // 超时时间
  isTimeout: string // 是否超时
  isRemind: string // 是否发送提醒
  status: string // 任务状态;1=待开始;2=巡检中;
  hasLoop: string // 周期状态
  thirdTaskId: string // 第三方任务id
  isStop: number // 任务是否异常终止;
  stopLog: string // 任务终止异常log
}

// 应用巡检任务 API
export const Api = {
  // 查询应用巡检任务分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/patrolTaskEquipment/page`, params })
  },

  // 查询应用巡检任务详情
  get: async (id: number) => {
    return await request.get({ url: `bus/patrolTaskEquipment/get?id=` + id })
  },

  // 新增应用巡检任务
  create: async (data: VO) => {
    return await request.post({ url: `bus/patrolTaskEquipment/create`, data })
  },

  // 修改应用巡检任务
  update: async (data: VO) => {
    return await request.put({ url: `bus/patrolTaskEquipment/update`, data })
  },

  // 删除应用巡检任务
  delete: async (id: number) => {
    return await request.delete({ url: `bus/patrolTaskEquipment/delete?id=` + id })
  },
  // 导出应用巡检任务 Excel
  export: async (params) => {
    return await request.download({ url: `bus/patrolTaskEquipment/export-excel`, params })
  },
  //获取巡检任务顶部统计数据
  getTopData: async (params) => {
    return await request.get({
      url: `bus/patrolTaskEquipment/getTopStatistics`,
      params
    })
  },
  //获取执勤人员列表
  getDutyUserList: async (id) => {
    return await request.get({ url: `bus/patrolTaskEquipment/getTaskPost?id=` + id })
  },
  //变更执勤人员
  saveTaskPost: async (data) => {
    return await request.post({ url: `bus/patrolTaskEquipment/saveTaskPost`, data })
  },
  //巡检执行情况分页
  taskRecordList: async (params) => {
    return await request.get({ url: `bus/patrolRecordEquipment/taskRecordList`, params })
  }
}
