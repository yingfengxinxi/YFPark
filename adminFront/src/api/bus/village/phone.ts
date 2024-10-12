/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 项目电话类型 VO
export interface PhoneCategoryVO {
  id: number // 编号
  orgId: number // 机构ID
  villageId: number // 项目ID
  catName: string // 分类名称
  sort: number // 排序值
  status: number // 状态（1正常，0隐藏）
}

// 项目电话类型 API
export const PhoneCategoryApi = {
  // 查询项目电话类型分页
  getPhoneCategoryPage: async (params: any) => {
    return await request.get({ url: `/bus/phone-category/getpage`, params })
  },

  // 查询项目电话类型详情
  getPhoneCategory: async (id: number) => {
    return await request.get({ url: `/bus/phone-category/get?id=` + id })
  },

  // 新增项目电话类型
  createPhoneCategory: async (data: PhoneCategoryVO) => {
    return await request.post({ url: `/bus/phone-category/create`, data })
  },

  // 修改项目电话类型
  updatePhoneCategory: async (data: PhoneCategoryVO) => {
    return await request.put({ url: `/bus/phone-category/update`, data })
  },

  // 删除项目电话类型
  deletePhoneCategory: async (id: number) => {
    return await request.delete({ url: `/bus/phone-category/delete?id=` + id })
  },

  // 导出项目电话类型 Excel
  exportPhoneCategory: async (params) => {
    return await request.download({ url: `/bus/phone-category/export-excel`, params })
  }
}

// 项目电话 VO
export interface VillagePhoneVO {
  id: number // 编号
  orgId: number // 机构ID
  villageId: number // 项目ID
  catId: number // 分类ID
  phoneName: string // 号码名称
  phone: string // 号码
  sort: number // 排序值
  status: number // 状态（1正常，0隐藏）
  urgent: number // 紧急电话（1是，0否）
}

// 项目电话 API
export const VillagePhoneApi = {
  // 查询项目电话分页
  getVillagePhonePage: async (params: any) => {
    return await request.get({ url: `/bus/village-phone/page`, params })
  },
  // 查询项目电话分页
  getVillagePhoneList: async (params: any) => {
    return await request.get({ url: `/bus/village-phone/list`, params })
  },

  // 查询项目电话详情
  getVillagePhone: async (id: number) => {
    return await request.get({ url: `/bus/village-phone/get?id=` + id })
  },

  // 新增项目电话
  createVillagePhone: async (data: VillagePhoneVO) => {
    return await request.post({ url: `/bus/village-phone/create`, data })
  },

  // 修改项目电话
  updateVillagePhone: async (data: VillagePhoneVO) => {
    return await request.put({ url: `/bus/village-phone/update`, data })
  },

  // 删除项目电话
  deleteVillagePhone: async (id: number) => {
    return await request.delete({ url: `/bus/village-phone/delete?id=` + id })
  },

  // 导出项目电话 Excel
  exportVillagePhone: async (params) => {
    return await request.download({ url: `/bus/village-phone/export-excel`, params })
  }
}
