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
export const orgBillAnnexApi = {
  /**
   * @description: 账单详情-附件信息  【机构账单收据 - 财务记录账单记录列表详情-账单明细】
   * /bus/orgBillAnnex/getBillAnnexList
   * @params params.sourceId 流水id [账单附件就是账单id,流水附件就是流水id]
   * @params params.sourceType 账单类型 [1:账单附件,2:流水附件]
   * @params params.pageNum 页码
   * @params params.pageSize 每页条数
   * get
   * return
   */
  getBillAnnexList: async (params: any) => {
    return await request.get({ url: `/bus/orgBillAnnex/getBillAnnexList`, params })
  },
  /**
   * @description: 账单详情-附件信息-新增  【机构账单收支流水附件】
   * /admin-api/bus/orgBillAnnex/create
   * @data data.sourceId 流水id [账单附件就是账单id,流水附件就是流水id]
   * @data data.type 账单类型 [1:账单附件,2:流水附件]
   * @data data.name 文件名称
   * @data data.filePath 文件地址
   * @data data.annexSource 1=pc;2=phone
   */
  create: async (data: any) => {
    return await request.post({ url: `/bus/orgBillAnnex/create`, data })
  },
  /**
   * @description: 账单详情-附件信息-删除  【机构账单收支流水附件】
   * /admin-api/bus/orgBillAnnex/delete
   * @data data.id附件id
   */
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillAnnex/delete?id=` + id })
  }
}
