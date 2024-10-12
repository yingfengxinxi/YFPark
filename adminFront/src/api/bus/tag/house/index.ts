/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 房源标签 VO
export interface TagHouseVO {
  id: number // 编号
  // orgId: number // 机构ID
  name: string // 标签名称
  desc: string // 标签描述
  color: string // 标签样式
  userShow: number // 用户是否可见（1可见，0不可见）
  status: number // 状态，1启用，0禁用
}

// 房源标签 API
export const TagHouseApi = {
  // 查询房源标签分页
  getTagHousePage: async (params: any) => {
    return await request.get({ url: `/bus/tag-house/page`, params })
  },

  // 查询房源标签详情
  getTagHouse: async (id: number) => {
    return await request.get({ url: `/bus/tag-house/get?id=` + id })
  },

  // 新增房源标签
  createTagHouse: async (data: TagHouseVO) => {
    return await request.post({ url: `/bus/tag-house/create`, data })
  },

  // 修改房源标签
  updateTagHouse: async (data: TagHouseVO) => {
    return await request.put({ url: `/bus/tag-house/update`, data })
  },

  // 删除房源标签
  deleteTagHouse: async (id: number) => {
    return await request.delete({ url: `/bus/tag-house/delete?id=` + id })
  },

  // 导出房源标签 Excel
  exportTagHouse: async (params) => {
    return await request.download({ url: `/bus/tag-house/export-excel`, params })
  },

  // 获取房间列表  /admin-api/bus/tag-house/getTagHouseList
  getTagHouseList: async (data: any) => {
    return await request.post({ url: `/bus/tag-house/getTagHouseList`, data })
  }
}
