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
/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
export const orgBillAdjustApi = {
  /**
   * 创建机构账单调整 (管理后台 - 机构账单调整 )
   * /admin-api/bus/orgBillAdjust/create
   * @param data.billId 账单id required
   * @param data.adjustType 调整类型【数据字典ADJUDT_TYPE】 required
   * @param data.adjustMode 调整方式【数据字典ADJUDT_MODE】required
   * @param data.adjustProportion 调整原因 调整比例【只有调整模式是按比例调整时才会使用】
   * @param data.adjustPrice 调整金额【只有调整模式是按金额调整时才会使用】
   * @param data.remark 备注
   */
  create: async (data: any) => {
    return await request.post({ url: `/bus/orgBillAdjust/create`, data })
  },
  /**
   * 机构账单调整分页列表 (管理后台 - 机构账单调整 )
   * /admin-api/bus/orgBillAdjust/page
   * @param params.pageNum 页码
   * @param params.pageSize 每页条数
   * @param params.billId 账单id
   * @param params.adjustStatus 状态【选中显示作废调整时传值为4，不选时传值为空】
   */
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillAdjust/page`, params })
  },
  /**
   * 创建机构账单调整审核通过
   * /admin-api/bus/orgBillAdjust/approved
   * @param params.id 机构账单调整id required
   * return
   */
  approved: async (params: any) => {
    return await request.get({ url: `/bus/orgBillAdjust/approved`, params })
  },
  /**
   * @description 机构账单调整删除
   * /admin-api/bus/orgBillAdjust/delete
   * @param params.id 机构账单调整id required
   *  return
   */
  delete: async (params: any) => {
    return await request.get({ url: `/bus/orgBillAdjust/delete`, params })
  },
  /**
   * @description 财务报表-调整账单顶部统计
   * /admin-api/bus/orgBillAdjust/getAdjustBillTopStatistic
   *  return
   */
  getAdjustBillTopStatistic: async () => {
    return await request.get({ url: `/bus/orgBillAdjust/getAdjustBillTopStatistic` })
  },
  /**
   * @description 财务报表-调整账单列表
   * /admin-api/bus/orgBillAdjust/getAdjustBillListPage
   * @param params.pageNum 页码
   * @param params.pageSize 每页条数
   *  return
   */
  getAdjustBillListPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillAdjust/getAdjustBillListPage`, params })
  },
  /**
   * 作废 申请作废调整
   * /admin-api/bus/orgBillAdjust/applicationToVoid
   * @param params.id 机构账单调整id required
   * return
   */
  applicationToVoid: async (params: any) => {
    return await request.get({ url: `/bus/orgBillAdjust/applicationToVoid`, params })
  }
}
