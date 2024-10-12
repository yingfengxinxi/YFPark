/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 收款通知记录 VO
export interface BillNoticeVO {
  id: number // 编号
  ownerId: number // 租客id
  contractId: number // 合同id
  buildId: number // 楼宇id
  buildBind: string // 绑定的楼宇信息
  buildType: string // 生成方式;1=逐张生成;2=按租客合并;3=按合同合并;
  noticeType: string // 通知方式;1=下载打印;2=短信通知;3=邮件通知;4=站内信
  packType: string // 打包方式;1=逐张打包;2=合并zip
  smsStatus: string // 短信发送状态;1=失败;2=成功
  emailStatus: string // 邮箱发送状态;1=失败;2=成功
  wechatStatus: string // 微信发送状态;1=失败;2=成功
  hasQrcode: string // 附收款二维码;1=是;0=否
  formData: string // 通知单原始json
  billData: string // 通知单涉及源账单信息
  filePath: string // 通知单文件信息json
  taskKey: string // 生成批次标识task_key
  buildDate: Date // 生成日期
}

// 收款通知记录 API
export const BillNoticeApi = {
  /**
   * @description  查询收款通知记录分页  (管理后台 - 收款通知记录)
   * /bus/orgBillNotice/page
   * @param params
   * @returns
   */
  getBillNoticePage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillNotice/page`, params })
  },

  // 查询收款通知记录详情
  getBillNotice: async (id: number) => {
    return await request.get({ url: `/bus/orgBillNotice/get?id=` + id })
  },

  // 新增收款通知记录
  createBillNotice: async (data: BillNoticeVO) => {
    return await request.post({ url: `/bus/orgBillNotice/create`, data })
  },

  // 修改收款通知记录
  updateBillNotice: async (data: BillNoticeVO) => {
    return await request.put({ url: `/bus/orgBillNotice/update`, data })
  },

  // 删除收款通知记录
  deleteBillNotice: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillNotice/delete?id=` + id })
  },

  // 导出收款通知记录 Excel
  exportBillNotice: async (params) => {
    return await request.download({ url: `/bus/orgBillNotice/export-excel`, params })
  },

  //获得收款通知记录顶部统计  /admin-api/bus/orgBillNotice/getTopNumCount
  getTopNumCount: async (params: any) => {
    return await request.get({ url: `/bus/orgBillNotice/getTopNumCount`, params })
  },

  //批量创建收款通知记录 /admin-api/bus/orgBillNotice/batchCreate
  batchCreate: async (data: any) => {
    return await request.post({ url: `/bus/orgBillNotice/batchCreate`, data })
  },

  /**
   * @description  重新发送  (管理后台 - 收款通知记录)
   * /admin-api/bus/orgBillNotice/resend
   * @param id 编号,示例值(1024)
   */
  resend: async (id: number) => {
    return await request.get({ url: `/bus/orgBillNotice/resend?id=` + id })
  }
}
