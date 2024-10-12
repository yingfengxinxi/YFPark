/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 账单收据编号规则 VO
export interface RuleVO {
  id: number // 编号
  name: string // 收据编号规则名称
  prefix: string // 收据编号前缀
  startNumber: string // 开始编号
  endNumber: string // 结束编号
  buildBind: string // 应用楼宇id,多个楼宇使用逗号隔开(1,2,3)
}

// 账单收据编号规则 API
export const RuleApi = {
  // 查询账单收据编号规则分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillReceiptRule/page`, params })
  },

  // 查询账单收据编号规则详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/orgBillReceiptRule/get?id=` + id })
  },

  // 新增账单收据编号规则
  create: async (data: VO) => {
    return await request.post({ url: `/bus/orgBillReceiptRule/create`, data })
  },

  // 修改账单收据编号规则
  update: async (data: VO) => {
    return await request.put({ url: `/bus/orgBillReceiptRule/update`, data })
  },

  // 删除账单收据编号规则
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillReceiptRule/delete?id=` + id })
  },

  // 导出账单收据编号规则 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/orgBillReceiptRule/export-excel`, params })
  }
}

// 收款方信息

// 收据收款方信息 VO
export interface SellerVO {
  id: number // 编号
  companyName: string // 收款方单位名称
  sellerName: string // 收款人名称
  address: string // 地址
  phone: string // 电话
  buildBind: string // 应用楼宇id,多个楼宇使用逗号隔开(1,2,3)
}

// 收据收款方信息 API
export const SellerApi = {
  // 查询收据收款方信息分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillReceiptSeller/page`, params })
  },

  // 查询收据收款方信息详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/orgBillReceiptSeller/get?id=` + id })
  },

  // 新增收据收款方信息
  create: async (data: SellerVO) => {
    return await request.post({ url: `/bus/orgBillReceiptSeller/create`, data })
  },

  // 修改收据收款方信息
  update: async (data: SellerVO) => {
    return await request.put({ url: `/bus/orgBillReceiptSeller/update`, data })
  },

  // 删除收据收款方信息
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillReceiptSeller/delete?id=` + id })
  },

  // 导出收据收款方信息 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/orgBillReceiptSeller/export-excel`, params })
  }
}

// 收据模板 VO
export interface TemplateVO {
  id: number // 编号
  name: string // 收据模板名称
  templatePath: string // 模板上传地址
  buildBind: string // 应用楼宇id,多个楼宇使用逗号隔开(1,2,3)
}

// 收据模板 API
export const TemplateApi = {
  // 查询收据模板分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillReceiptTemplate//page`, params })
  },

  // 查询收据模板详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/orgBillReceiptTemplate//get?id=` + id })
  },

  // 新增收据模板
  create: async (data: VO) => {
    return await request.post({ url: `/bus/orgBillReceiptTemplate//create`, data })
  },

  // 修改收据模板
  update: async (data: VO) => {
    return await request.put({ url: `/bus/orgBillReceiptTemplate//update`, data })
  },

  // 删除收据模板
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillReceiptTemplate//delete?id=` + id })
  },

  // 导出收据模板 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/orgBillReceiptTemplate//export-excel`, params })
  }
}
