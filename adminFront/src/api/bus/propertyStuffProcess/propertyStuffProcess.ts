/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
export const propertyStuffProcessApi = {
  /** 查询资产待审批数据
   * /admin-api/bus/property-stuff-process/getProcessCount
   * returns
   */
  getProcessCount: async function () {
    return await request.post({ url: `/bus/property-stuff-process/getProcessCount` })
  }
}
