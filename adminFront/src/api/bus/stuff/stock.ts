import request from '@/config/axios'

// 耗材即时库存 VO
export interface PropertyStuffStockVO {
  id: number // 编号
  orgId: number // 机构id
  stuffId: number // 耗材物料id
  depositoryId: number // 仓库id;需同步该信息
  processCode: string // 入库流程编号
  usableNum: number // 可用库存
  frozenNum: number // 冻结库存
  totalNum: number // 总库存
  totalPrice: number // 总金额
  chargePrice: number // 耗材定价
  remark: string // 备注
  extra: string // 其他
  isStockUp: number // 是否达到安全库存上限;默认安全
  isStockLower: number // 是否达到安全库存下限;默认安全
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
}

// 耗材即时库存 API
export const PropertyStuffStockApi = {
  // 查询耗材即时库存分页
  getPropertyStuffStockPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-stock/page`, params })
  },

  // 查询耗材即时库存详情
  getPropertyStuffStock: async (id: number) => {
    return await request.get({ url: `/bus/property-stuff-stock/get?id=` + id })
  },

  // 新增耗材即时库存
  createPropertyStuffStock: async (data: PropertyStuffStockVO) => {
    return await request.post({ url: `/bus/property-stuff-stock/create`, data })
  },

  // 修改耗材即时库存
  updatePropertyStuffStock: async (data: PropertyStuffStockVO) => {
    return await request.put({ url: `/bus/property-stuff-stock/update`, data })
  },

  // 删除耗材即时库存
  deletePropertyStuffStock: async (id: number) => {
    return await request.delete({ url: `/bus/property-stuff-stock/delete?id=` + id })
  },

  // 导出耗材即时库存 Excel
  exportPropertyStuffStock: async (params) => {
    return await request.download({ url: `/bus/property-stuff-stock/export-excel`, params })
  },
  /**
   * 根据审批id查询列表
   * /bus/property-stuff-stock/getPropertyStuffPageByApprove
   */
  getPropertyStuffPageByApprove: async (params: any) => {
    return await request.get({
      url: `/bus/property-stuff-stock/getPropertyStuffPageByApprove`,
      params
    })
  },

  /**
   * 耗材入库,派发
   * /admin-api/bus/property-stuff-stock/savePropertyStuff
   * @param params.processType 入库类型
   *stuff_stock_enter 耗材入库
   * stuff_hand_out 耗材派发
   *stuff_retreat_out 耗材退还（退库）
   stuff_transfer 耗材调拨
   stuff_handle 耗材处置
   stuff_adjust 耗材调整
   stuff_receive 耗材领用
   stuff_retreat_out 耗材退还
   stuff_stock_receive 库存耗材领用
   * @param params
   */
  savePropertyStuff: async (data: any) => {
    return await request.post({ url: `/bus/property-stuff-stock/savePropertyStuff`, data })
  }
}
