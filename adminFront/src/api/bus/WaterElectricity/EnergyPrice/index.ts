import request from '@/config/axios'

// 自定义价格标准 VO
export interface VO {
  id: number // 编号
  type: string // 设备表种类
  orgId: number // 机构ID
  villageId: number // 项目ID
  buildId: number // 楼宇ID
  name: string // 名称
  isStagePrice: string // 是否阶梯价 1是 0否
  taxRate: number // 倍率
  unitPrice: string // 单价标准，json存储
  builds: string // 授予用户管理的项目和楼宇，json存储
  roomIds: string // 绑定房间信息，1,2,3
  ratio: string // 滞纳金比例
  startDay: string // 起算天数
  toplimit: string // 滞纳金上限
  status: string // 状态，1启动，0审核中，4禁用
}

// 自定义价格标准 API
export const energyPriceApi = {
  // 查询自定义价格标准分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/energyPrice/page`, params })
  },

  // 查询自定义价格标准详情
  get: async (id: number) => {
    return await request.get({ url: `bus/energyPrice/get?id=` + id })
  },

  // 新增自定义价格标准
  create: async (data: VO) => {
    return await request.post({ url: `bus/energyPrice/create`, data })
  },

  // 修改自定义价格标准
  update: async (data: VO) => {
    return await request.put({ url: `bus/energyPrice/update`, data })
  },

  // 删除自定义价格标准
  delete: async (id: number) => {
    return await request.delete({ url: `bus/energyPrice/delete?id=` + id })
  },

  // 导出自定义价格标准 Excel
  export: async (params) => {
    return await request.download({ url: `bus/energyPrice/export-excel`, params })
  }
}
