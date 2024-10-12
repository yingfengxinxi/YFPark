import request from '@/config/axios'

// 工单工时配置 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构id
  application: string // 应用标识
  name: string // 工时名称
  hourFee: number // 工时费用
  computeRule: string // 工时计算规则;1=保留两位小数;2=去除小数部分;3=四舍五入去除小数;
  departmentId: number // 部门id
  stationId: number // 岗位信息json
  postUids: string // 岗位员工信息json
  status: string // 启用状态
  isDefault: string // 是否为默认配置
}

// 工单工时配置 API
export const workOrderHourApi = {
  // 查询工单工时配置分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/workOrderHour/page`, params })
  },

  // 查询工单工时配置详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/workOrderHour/get?id=` + id })
  },

  // 新增工单工时配置
  create: async (data: VO) => {
    return await request.post({ url: `/bus/workOrderHour/create`, data })
  },

  // 修改工单工时配置
  update: async (data: VO) => {
    return await request.put({ url: `/bus/workOrderHour/update`, data })
  },

  // 删除工单工时配置
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/workOrderHour/delete?id=` + id })
  },

  // 导出工单工时配置 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/workOrderHour/export-excel`, params })
  },
  //修改工单工时状态配置
  updateStatus: async (params) => {
    return await request.get({ url: `/bus/workOrderHour/updateStatus`, params })
  },
  //查询默认工时信息
  getworkHourList: async (params) => {
    return await request.get({ url: `/bus/workOrderHour/workHourList`, params })
  }
}
