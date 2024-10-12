/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
export interface contractOrderBillVO {
  feeType: string
  receivable: string
  payStartDate: string
  payEndDate: string
  billStatus: string
}

export const contractOrderApi = {
  /**
   * @description: 租客账单列表 【机构合同账单明细】
   *  /admin-api/bus/contractOrderBill/getByOwnerIdRoomIdBillList
   * @param {string} params.idisDisplayReceived 已显示已收账单0=隐藏1=显示
   * @param {string} params.ownerId 租客id
   * @param {string} params.roomId 房间id
   * @param {string} params.isAccountsReceivable  已显示未到应收期账单0=隐藏1=显示
   *
   */
  getByOwnerIdRoomIdBillList: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/getByOwnerIdRoomIdBillList`, params })
  },

  /**
   * 创建账单明细
   * /admin-api/bus/contractOrderBill/create
   * @param data
   * @returns
   */
  contractOrderBillCreate: async (data: any) => {
    return await request.post({ url: `/bus/contractOrderBill/create`, data })
  },

  /**
   * 缴费通知单顶部
   * /bus/contractOrderBill/getNotificationNumber?ids=1&buildType=1
   * @param {string} params.ids 账单Id 用逗号隔开
   * @param {string} params.buildType 账单生成方式
   */
  getNotificationNumber: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/getNotificationNumber`, params })
  },
  /**
   * 收银台 -租客详情账单列表
   * /bus/contractOrderBill/getByOwnerIdRoomIdCashierBillList
   * @param {string} params.ownerId 租客id
   * @param {string} params.isAccountsReceivable  已显示未到应收期账单0=隐藏1=显示
   * @param {string} params.isDisplayReceived 已显示已收账单0=隐藏1=显示
   *
   */
  getByOwnerIdRoomIdCashierBillList: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getByOwnerIdRoomIdCashierBillList`,
      params
    })
  },
  /**
   * @description: 关闭账单
   * /bus/contractOrderBill/close
   * @param {string} params.billId 账单id
   * @param {string} params.remark 关闭原因
   * get
   * return
   */
  close: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/close?billId`, params })
  },
  /**
   * @description: 账单详情
   * /bus/contractOrderBill/billInformation
   * @param {string} params.billId 账单id
   * get
   * return
   */
  billInformation: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/billInformation`, params })
  },
  /**
   * @description: 扫码支付  【机构合同账单明细 - 财务记录账单记录列表详情-微信扫码付款 】
   * /bus/contractOrderBill/scanCodeBillPay?billId=1,12,3,4
   * @param {string} params.billId 账单id
   * get
   * return
   */
  scanCodeBillPay: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/scanCodeBillPay`, params })
  },
  /**
   * @description: 账单详情-账单明细 【财务记录账单记录列表详情-账单明细】
   * /bus/contractOrderBill/billDetails
   * @param {string} params.billId 账单id
   * get
   * return
   */
  billDetails: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/billDetails`, params })
  },

  /**
   * 财务记录账单记录列表详情 - 调整滞纳金
   * /bus/contractOrderBill/collectLateFee?billId=&lateFee=
   * @param {string} params.billId 账单id
   * @param {string} params.lateFee 滞纳金
   */
  collectLateFee: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/collectLateFee`, params })
  },

  /**
   * 收支流水详情中匹配列表
   * /bus/contractOrderBill/matchBillList
   * @param {string} params.ownerId 账单id
   * @param {string} params.pageNo 页码
   * @param {string} params.pageSize 页大小
   * return
   */
  matchBillList: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/matchBillList`, params })
  },

  /**
   * 财务记录顶部统计
   * bus/contractOrderBill/getOwnerIdOverdueIds
   * @param {string} params.ownerId 租客id
   * @param {string} params.roomId 房间id
   * return
   */
  getOwnerIdOverdueIds: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/getOwnerIdOverdueIds`, params })
  },

  /**
   * 财务记录列表
   *  bus/contractOrderBill/ownerFinanceList
   * @param {string} params.ownerId 租客id
   * @param {string} params.roomId 房间id
   * @param {string} params.pageNo 页码
   * @param {string} params.pageSize 页大小
   * @param {string} params.year 年
   * @param {string} params.month 月
   *  return
   */
  ownerFinanceList: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/ownerFinanceList`, params })
  },

  /**
   * 新建收付款账单
   * /bus/contractOrderBill/createBill
   * @param data
   * @returns
   */
  createBill: async (data: any) => {
    return await request.post({ url: `/bus/contractOrderBill/createBill`, data })
  },

  /**
   * 财务-收入列表-详情
   * /bus/contractOrderBill/incomeDetail?billId=220
   * @param {string} params.billId 账单id
   * return
   */
  incomeDetail: async (params: any) => {
    return await request.get({ url: `/bus/contractOrderBill/incomeDetail`, params })
  },

  /** 财务报表-应收报表列表
   * bus/contractOrderBill/getAccountsReceivableReportPage
   * @param {string} params.pageNo 页码
   * @param {string} params.pageSize 页大小
   * return
   */
  getAccountsReceivableReportPage: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getAccountsReceivableReportPage`,
      params
    })
  },

  /** 财务报表 - 应收报表-导出
   * bus/contractOrderBill/exportAccountsReceivableReport
   * @param params
   * return
   */
  exportAccountsReceivableReport: async (params: any) => {
    return await request.download({
      url: `/bus/contractOrderBill/exportAccountsReceivableReport`,
      params
    })
  },
  /**
   *  财务报表 - 应收报表 - 应收顶部汇总
   *  bus/contractOrderBill/getAccountsReceivableSummary
   * @param params.method 统计方式
   * @param params.payDate 统计月份
   * return
   */
  getAccountsReceivableSummary: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getAccountsReceivableSummary`,
      params
    })
  },

  /** 财务报表-应收报表-图表-已收金额线下/线上占比
   * bus/contractOrderBill/getAccountsReceivableAmountMap
   * @param params.method 统计方式
   * @param params.payDate 统计月份
   * return
   */
  getAccountsReceivableAmountMap: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getAccountsReceivableAmountMap`,
      params
    })
  },
  /** 财务报表-应收报表-图表-费用类型趋势
   * bus/contractOrderBill/getAccountsReceivableTrendCostTypeMap
   * @param params.method 统计方式
   * @param params.payDate 统计月份
   * return
   */
  getAccountsReceivableTrendCostTypeMap: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getAccountsReceivableTrendCostTypeMap`,
      params
    })
  },
  /** 财务报表 - 应支报表-导出
  bus/contractOrderBill/exportAccountsPayableReport
   * @param params
   * return
   */
  exportAccountsPayableReport: async (params: any) => {
    return await request.download({
      url: `/bus/contractOrderBill/exportAccountsPayableReport`,
      params
    })
  },

  /** 财务报表-应支报表列表
   * bus/contractOrderBill/getAccountsPayableReportPage
   * @param {string} params.pageNo 页码
   * @param {string} params.pageSize 页大小
   * return
   */
  getAccountsPayableReportPage: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getAccountsPayableReportPage`,
      params
    })
  },
  /**
   *  财务报表 - 应支报表 - 应支顶部汇总
   *  bus/contractOrderBill/getAccountsPayableSummary
   * @param params.method 统计方式
   * @param params.payDate 统计月份
   * return
   */
  getAccountsPayableSummary: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getAccountsPayableSummary`,
      params
    })
  },
  /** 财务报表-应支报表-图表-已收金额线下/线上占比
   *  bus/contractOrderBill/getAccountsPayableAmountMap
   * @param params.method 统计方式
   * @param params.payDate 统计月份
   * return
   */
  getAccountsPayableAmountMap: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getAccountsPayableAmountMap`,
      params
    })
  },
  /** 财务报表-应支报表-图表-费用类型趋势
   * bus/contractOrderBill/getAccountsPayableTrendCostTypeMap
   * @param params.method 统计方式
   * @param params.payDate 统计月份
   * return
   */
  getAccountsPayableTrendCostTypeMap: async (params: any) => {
    return await request.get({
      url: `/bus/contractOrderBill/getAccountsPayableTrendCostTypeMap`,
      params
    })
  }
}
