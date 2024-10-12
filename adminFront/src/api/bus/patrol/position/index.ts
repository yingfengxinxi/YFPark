import request from '@/config/axios'

// 巡检点位数据 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构Id
  application: string // 应用标识
  number: string // 巡检点编码
  name: string // 巡检点名称
  positionId: number // 资产位置id
  positionName: string // 资产位置名称
  nfcCardId: string // nfc卡号ID
  formId: number // 巡检表单id
  images: string // 巡检点图片
  propertyJson: string // 绑定的资产json
  status: string // 启用状态;0=禁用,1=启用
}

// 巡检点位数据 API
export const Api = {
  // 查询巡检点位数据分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/patrolPosition/page`, params })
  },

  // 查询巡检点位数据详情
  get: async (id: number) => {
    return await request.get({ url: `bus/patrolPosition/get?id=` + id })
  },

  // 新增巡检点位数据
  create: async (data: VO) => {
    return await request.post({ url: `bus/patrolPosition/create`, data })
  },

  // 修改巡检点位数据
  update: async (data: VO) => {
    return await request.put({ url: `bus/patrolPosition/update`, data })
  },

  // 删除巡检点位数据
  delete: async (id: number) => {
    return await request.delete({ url: `bus/patrolPosition/delete?id=` + id })
  },

  // 导出巡检点位数据 Excel
  export: async (params) => {
    return await request.download({ url: `bus/patrolPosition/export-excel`, params })
  },
  //获取巡检表单
  getForm: async (params) => {
    return await request.get({ url: `bus/patrolForm/getList`, params })
  },
  //巡检点设置下拉
  getPatrolPosition: async (params) => {
    return await request.get({ url: `bus/patrolPosition/getList`, params })
  }
}
