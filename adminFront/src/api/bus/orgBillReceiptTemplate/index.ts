/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

/** 管理后台 - 收据模版 */
export const orgBillReceiptTemplateApi = {
  /** 收据模版下拉
   *  bus/orgBillReceiptTemplate/getByBuildsTemplateList?buildBind=楼栋id
   * @param params.buildBind 楼栋id
   * return
   */
  getByBuildsTemplateList: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceiptTemplate/getByBuildsTemplateList', params })
  }
}
