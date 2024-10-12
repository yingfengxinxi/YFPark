/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 收支账户配置 VO
export interface VO {
  id: number // 编号
  name: string // 条目名称
  company: string // 收款公司
  bank: string // 开户银行
  bankAccount: string // 银行账号
  subject: string // 总分类账科目
  build: [] // 应用的楼宇
  status: number // 是否启用0=否1=是
}

// 收支账户配置 API
export const Api = {
  // 查询收支账户配置分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgAccount/getAccountPage`, params })
  },

  // 查询收支账户配置详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/orgAccount/get?id=` + id })
  },

  // 新增收支账户配置
  create: async (data: VO) => {
    return await request.post({ url: `/bus/orgAccount/create`, data })
  },

  // 修改收支账户配置
  update: async (data: VO) => {
    return await request.put({ url: `/bus/orgAccount/update`, data })
  },

  // 删除收支账户配置
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/orgAccount/delete?id=` + id })
  },

  // 导出收支账户配置 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/orgAccount/export-excel`, params })
  }
}
