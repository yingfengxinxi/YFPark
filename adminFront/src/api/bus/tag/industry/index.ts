/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 行业分类标签 VO
export interface TagIndustryVO {
  id: number // 编号
  // orgId: number // 机构ID
  name: string // 标签名称
  desc: string // 标签描述
  color: string // 标签样式
  status: number // 状态，1启用，0禁用
}

// 行业分类标签 API
export const TagIndustryApi = {
  // 查询行业分类标签分页
  getTagIndustryPage: async (params: any) => {
    return await request.get({ url: `/bus/tag-industry/page`, params })
  },

  // 查询行业分类标签详情
  getTagIndustry: async (id: number) => {
    return await request.get({ url: `/bus/tag-industry/get?id=` + id })
  },

  // 新增行业分类标签
  createTagIndustry: async (data: TagIndustryVO) => {
    return await request.post({ url: `/bus/tag-industry/create`, data })
  },

  // 修改行业分类标签
  updateTagIndustry: async (data: TagIndustryVO) => {
    return await request.put({ url: `/bus/tag-industry/update`, data })
  },

  // 删除行业分类标签
  deleteTagIndustry: async (id: number) => {
    return await request.delete({ url: `/bus/tag-industry/delete?id=` + id })
  },

  // 导出行业分类标签 Excel
  exportTagIndustry: async (params) => {
    return await request.download({ url: `/bus/tag-industry/export-excel`, params })
  },
  // 行业分类标签无分页 /admin-api/bus/tag-industry/getTagIndustryList
  getTagIndustryList: async (params) => {
    return await request.get({ url: `/bus/tag-industry/getTagIndustryList`, params })
  }
}
