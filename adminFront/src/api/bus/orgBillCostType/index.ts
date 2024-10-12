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

export const orgBillCostTypeApi = {
  /**
   * @description: 费用类型
   * bus/orgBillCostType/getCostTypeChildrenList
   *
   */
  getCostTypeChildrenList: async () => {
    return await request.get({ url: `/bus/orgBillCostType/getCostTypeChildrenList` })
  }
}
