/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 项目标签 VO
export interface TagVillageVO {
  id: number // 编号
  name: string // 标签名称
  desc: string // 标签描述
  color: string // 标签样式
  status: number // 状态，1启用，0禁用
}

// 项目标签 API
export const TagVillageApi = {
  // 查询项目标签分页
  getTagVillagePage: async (params: any) => {
    return await request.get({ url: `/bus/tag-village/page`, params })
  },

  // 查询项目标签无分页
  getTagVillagList: async () => {
    return await request.get({
      url: `/bus/tag-village/getList`,
      params: {
        pageNo: 1,
        pageSize: 100
      }
    })
  },

  // 查询项目标签详情
  getTagVillage: async (id: number) => {
    return await request.get({ url: `/bus/tag-village/get?id=` + id })
  },

  // 新增项目标签
  createTagVillage: async (data: TagVillageVO) => {
    return await request.post({ url: `/bus/tag-village/create`, data })
  },

  // 修改项目标签
  updateTagVillage: async (data: TagVillageVO) => {
    return await request.put({ url: `/bus/tag-village/update`, data })
  },

  // 删除项目标签
  deleteTagVillage: async (id: number) => {
    return await request.delete({ url: `/bus/tag-village/delete?id=` + id })
  },

  // 导出项目标签 Excel
  exportTagVillage: async (params) => {
    return await request.download({ url: `/bus/tag-village/export-excel`, params })
  }
}
