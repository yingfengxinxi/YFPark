import request from '@/config/axios'

// 表种类管理 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  name: string // 表种类名称
  costType: string // 费用类型
  costTypeTxt: string // 费用类型文本
  equipType: string // 设备类型
  unit: string // 计费单位 如度、方等
  isBroken: string // 是否自动断电 0否 1是
  overdueDay: number // 可逾期天数
  cutType: string // 是否已电控水 0否 1是
  remindValue: number // 提醒值
}

// 表种类管理 API
export const energyType = {
  // 查询表种类管理分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/energyType/page`, params })
  },

  // 查询表种类管理详情
  get: async (id: number) => {
    return await request.get({ url: `bus/energyType/get?id=` + id })
  },

  // 新增表种类管理
  create: async (data: VO) => {
    return await request.post({ url: `bus/energyType/create`, data })
  },

  // 修改表种类管理
  update: async (data: VO) => {
    return await request.put({ url: `bus/energyType/update`, data })
  },

  // 删除表种类管理
  delete: async (id: number) => {
    return await request.delete({ url: `bus/energyType/delete?id=` + id })
  },

  // 导出表种类管理 Excel
  export: async (params) => {
    return await request.download({ url: `bus/energyType/export-excel`, params })
  },
  // 查询表种类管理列表
  getList: async () => {
    return await request.get({ url: `bus/energyType/getList` })
  },
  //保存阈值提醒
  submitlockout: async (data: any) => {
    return await request.put({ url: `bus/energyType/lockout`, data })
  }
}
