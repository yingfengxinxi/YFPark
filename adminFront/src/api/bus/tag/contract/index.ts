/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 合同标签 VO
export interface TagContractVO {
  id: number // 编号
  // orgId: number // 机构ID
  name: string // 标签名称
  desc: string // 标签描述
  color: string // 标签样式
  status: number // 状态，1启用，0禁用
}

// 合同标签 API
export const TagContractApi = {
  // 查询合同标签分页
  getTagContractPage: async (params: any) => {
    return await request.get({ url: `/bus/tag-contract/page`, params })
  },

  // 查询合同标签详情
  getTagContract: async (id: number) => {
    return await request.get({ url: `/bus/tag-contract/get?id=` + id })
  },

  // 新增合同标签
  createTagContract: async (data: TagContractVO) => {
    return await request.post({ url: `/bus/tag-contract/create`, data })
  },

  // 修改合同标签
  updateTagContract: async (data: TagContractVO) => {
    return await request.put({ url: `/bus/tag-contract/update`, data })
  },

  // 删除合同标签
  deleteTagContract: async (id: number) => {
    return await request.delete({ url: `/bus/tag-contract/delete?id=` + id })
  },

  // 导出合同标签 Excel
  exportTagContract: async (params) => {
    return await request.download({ url: `/bus/tag-contract/export-excel`, params })
  },

  // 合同标签无分页 /admin-api/bus/tag-contract/getTagContractList
  getTagContractList: async (params) => {
    return await request.get({ url: `/bus/tag-contract/getTagContractList`, params })
  }
}
