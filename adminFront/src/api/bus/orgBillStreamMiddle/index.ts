import request from '@/config/axios'
export const orgBillStreamMiddleApi = {
  /**
   * 租客账单->收款->匹配流水确认按钮 (管理后台 - 机构流水账单中间表【匹配】)
   * /admin-api/bus/orgBillStreamMiddle/addCollectionMiddle
   *  @param {string} params.billId 选中的账单id
   *  @param {string} params.streamId 选中的流水id
   *  @param {string} params.matchPrice 匹配金额
   *  @param {string} params.matchDateStr 匹配日期
   */
  addCollectionMiddle: async (params: any) => {
    return await request.post({ url: `/bus/orgBillStreamMiddle/addCollectionMiddle`, params })
  },

  /**
   * 收支流水详情->匹配->匹配确认按钮  (管理后台 - 机构流水账单中间表【匹配】)
   * /admin-api/bus/orgBillStreamMiddle/matchBill
   *  @param {string} params.billId 选中的账单id
   *  @param {string} params.streamId 选中的流水id
   *  @param {string} params.matchPrice 匹配金额
   *  @param {string} params.matchDateStr 匹配日期
   *  return
   */
  matchBill: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStreamMiddle/matchBill`, params })
  },

  /**
   * 获得已经匹配或者取消匹配的账单分页 (收支流水详情中的查询匹配列表)
   * /bus/orgBillStreamMiddle/page
   * @param {string} params.billId 账单id
   * @param {string} params.streamId 流水id
   * @param {string} params.matchStatus 匹配状态;1=完全匹配;2=未匹配;3=部分匹配;
   * @param {string} params.pageNo 页码
   * @param {string} params.pageSize 页大小
   * return
   */
  page: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStreamMiddle/page`, params })
  },

  /**
   * 取消匹配
   * /admin-api/bus/orgBillStreamMiddle/cancelMatch
   * @param {string} params.id 列表
   * @param {string} params.cancelMatchDate 取消匹配日期
   * return
   */
  cancelMatch: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStreamMiddle/cancelMatch`, params })
  },
  /**
   * 财务管理->账单详情->流水信息已经匹配账单分页
   * /bus/orgBillStreamMiddle/getBillStreamListPage?billId=&matchStatus=
   * @param {string} params.billId 账单id
   * @param {string} params.matchStatus 匹配状态;1=完全匹配;2=未匹配;3=部分匹配;
   * @param {string} params.pageNo 页码
   * @param {string} params.pageSize 页大小
   * */
  getBillStreamListPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStreamMiddle/getBillStreamListPage`, params })
  }
}
