import request from '@/config/axios'

// 耗材档案信息 VO
export interface PropertyStuffVO {
  id: number // 编号
  orgId: number // 机构id
  number: string // 物料编码
  name: string // 物料名称
  categoryId: number // 物料分类id
  barCode: string // 商品条码
  brand: string // 品牌
  modelName: string // 规格型号
  meterUnit: string // 计量单位
  quantityDigit: number // 小数位数(数量)
  priceDigit: number // 小数位数(单价)
  computeMethod: number // 成本计算方法;1=加权平均,2=批次管理
  lockPrice: number // 是否固定入库单价;0=否1=是
  price: number // 入库单价;最多保留小数点后四位
  safeStockUp: number // 安全库存上限数量
  safeStockLower: number // 安全固定下限数量
  receiveLimit: number // 限领数量(人/月)
  hasReturn: number // 是否允许退库;0=否;1=是
  imageUrl: string // 物料照片;支持多张
  status: number // 耗材状态;1=启用;0=禁用
  remark: string // 备注
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 耗材档案信息 API
export const PropertyStuffApi = {
  // 查询耗材档案信息分页
  getPropertyStuffPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff/page`, params })
  },

  // 查询耗材档案信息详情
  getPropertyStuff: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff/get?id=` + id })
  },

  // 新增耗材档案信息
  createPropertyStuff: async (data: PropertyStuffVO) => {
    return await request.post({ url: `/bus/property-stuff/create`, data })
  },

  // 修改耗材档案信息
  updatePropertyStuff: async (data: PropertyStuffVO) => {
    return await request.put({ url: `/bus/property-stuff/update`, data })
  },

  // 删除耗材档案信息
  deletePropertyStuff: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff/delete?id=` + id })
  },

  // 导出耗材档案信息 Excel
  exportPropertyStuff: async (params) => {
    return await request.download({ url: `/bus/property-stuff/export-excel`, params })
  },
  /** 预警与提醒 - 工作台
   * /admin-api/bus/property-stuff/getHightLow
   * @return
   */
  getHightLow: async () => {
    return await request.get({ url: `/bus/property-stuff/getHightLow` })
  }
}
