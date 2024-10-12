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
export const PropertyStuffProcessApi = {
  //  /bus/property-stuff-process/page
  getPropertyStuffProcessPage: async (params: any) => {
    return await request.get({ url: `/bus/property-stuff-process/page`, params })
  }
}
