import request from '@/config/axios'

// 自定义抄表计划 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  planNumber: string // 计划编号
  planName: string // 计划名称
  stationIds: string // 所选责任岗位id
  departmentId: number // 责任部门
  timeLimit: number // 任务时限/小时
  energyType: string // 抄表范围json
  planRule: string // 计划周期规则
  lastTime: Date // 最近一次执行时间
  nextTime: Date // 下次执行时间
  endTime: Date // 计划执行结束时间
  buildsJson: string // 负责位置范围json
  remindJson: string // 提醒方式及提醒规则
  planStatus: string // 自定义抄表计划状态
  status: string // 计划启用状态;0=关闭;1=开启
}

// 自定义抄表计划 API
export const energyPlanApi = {
  // 查询自定义抄表计划分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/energyPlan/page`, params })
  },

  // 查询自定义抄表计划详情
  get: async (id: number) => {
    return await request.get({ url: `bus/energyPlan/get?id=` + id })
  },

  // 新增自定义抄表计划
  create: async (data: VO) => {
    return await request.post({ url: `bus/energyPlan/create`, data })
  },

  // 修改自定义抄表计划
  update: async (data: VO) => {
    return await request.put({ url: `bus/energyPlan/update`, data })
  },

  // 删除自定义抄表计划
  delete: async (id: number) => {
    return await request.delete({ url: `bus/energyPlan/delete?id=` + id })
  },

  // 导出自定义抄表计划 Excel
  export: async (params) => {
    return await request.download({ url: `bus/energyPlan/export-excel`, params })
  },

  //获取抄表预警
  getwarning: async (params: any) => {
    return await request.get({ url: `bus/energyForewarningValue/get`, params })
  },
  //提交抄表预警
  savewarning: async (data: any) => {
    return await request.post({ url: `bus/energyForewarningValue/save`, data })
  },
  //获取本期未抄录
  notCompleteTaskList: async (params: any) => {
    return await request.get({ url: `bus/energyTask/notCompleteTaskList`, params })
  },
  //获取本期未抄录详情
  completeTaskList: async (params: any) => {
    return await request.get({ url: `bus/energyRecord/last`, params })
  },
  //检测本次读数是否误抄
  misreadingForewarning: async (params: any) => {
    return await request.get({ url: `bus/energyForewarningValue/misreadingForewarning`, params })
  },
  //提交抄表记录
  energyRecordcreate: async (data: any) => {
    return await request.post({ url: `bus/energyRecord/create`, data })
  }
}
