/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 所有账单
export const BillApi = {
  /**
   * @description  统计查询   财务所有账单->顶部收款/付款统计接口  (管理后台 - org-bill-controller)
   * bus/bill/getBillCollectionAllTotalMoney
   * @param params.billType 账单类型（1=收款账单2=付款账单）
   * @param params.isShow 已显示未到应收账单（false隐藏，true显示）
   * @returns
   */
  getBillCollectionAllTotalMoney: async (params: any) => {
    return await request.get({ url: '/bus/bill/getBillCollectionAllTotalMoney', params })
  },
  /**
   * @description  财务所有账单->收款账单/付款账单接口  (管理后台 - - org-bill-controller)
   *  bus/bill/getBillCollectionAllListPage
   * @param params.billType 账单类型（1=收款账单2=付款账单）
   * @param params.isShow 已显示未到应收账单（false隐藏，true显示）
   * return
   */
  getBillCollectionAllListPage: async (params: any) => {
    return await request.get({ url: '/bus/bill/getBillCollectionAllListPage', params })
  },
  /**
   * @description  财务所有账单->导出接口  (管理后台 -  - org-bill-controller)
   *  bus/bill/exportBillCollectionAll
   * @param params.billType 账单类型（1=收款账单2=付款账单）
   * @param params.isShow 已显示未到应收账单（false隐藏，true显示）
   * return
   */
  exportBillCollectionAll: async (params: any) => {
    return await request.get({ url: '/bus/bill/exportBillCollectionAll', params })
  },

  /** 所有账单 导入模版
   * bus/bill/get-import-template
   * return
   */
  getImportTemplate: async () => {
    return await request.get({ url: '/bus/bill/get-import-template' })
  },

  /** 所有账单 - 导入
   *  bus/bill/import
   * @param params.file 导入文件
   * return
   */
  importBill: async (params: any) => {
    return await request.post({ url: '/bus/bill/import', params })
  },

  /**
   * 逾期账单顶部统计
   * /bus/bill/getBillBeOverdueTotalMoney
   * @param params.billType 账单类型（1=收款账单2=付款账单）
   * return
   */
  getBillBeOverdueTotalMoney: async (params: any) => {
    return await request.get({ url: '/bus/bill/getBillBeOverdueTotalMoney', params })
  },

  /** 逾期账单列表
   * /bus/bill/getBillBeOverdueListPage
   * @param params.billType 账单类型（1=收款账单2=付款账单）
   * return
   */
  getBillBeOverdueListPage: async (params: any) => {
    return await request.get({ url: '/bus/bill/getBillBeOverdueListPage', params })
  },
  /** 审批配置查询
   *  bus/billApprovalConfig/get
   * return
   */
  getBillApprovalConfig: async () => {
    return await request.get({ url: '/bus/billApprovalConfig/get' })
  },

  /**  审批配置保存
   *  bus/billApprovalConfig/save?isUse=0
   * @param params.isUse 是否启用（0=关闭审批，1=开启审批）
   * return
   */
  saveBillApprovalConfig: async (params: any) => {
    return await request.get({ url: '/bus/billApprovalConfig/save', params })
  }
}
