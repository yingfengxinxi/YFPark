import request from '@/config/axios'

// 租客/业主标签 VO
export interface TagOwnerVO {
  id: number // 编号
  // orgId: number // 机构ID
  name: string // 标签名称
  desc: string // 标签描述
  color: string // 标签样式
  status: number // 状态，1启用，0禁用
}

// 租客/业主标签 API
export const TagOwnerApi = {
  // 查询租客/业主标签分页
  getTagOwnerPage: async (params: any) => {
    return await request.get({ url: `/bus/tag-owner/page`, params })
  },

  // 查询租客/业主标签详情
  getTagOwner: async (id: number) => {
    return await request.get({ url: `/bus/tag-owner/get?id=` + id })
  },

  // 新增租客/业主标签
  createTagOwner: async (data: TagOwnerVO) => {
    return await request.post({ url: `/bus/tag-owner/create`, data })
  },

  // 修改租客/业主标签
  updateTagOwner: async (data: TagOwnerVO) => {
    return await request.put({ url: `/bus/tag-owner/update`, data })
  },

  // 删除租客/业主标签
  deleteTagOwner: async (id: number) => {
    return await request.delete({ url: `/bus/tag-owner/delete?id=` + id })
  },

  // 导出租客/业主标签 Excel
  exportTagOwner: async (params) => {
    return await request.download({ url: `/bus/tag-owner/export-excel`, params })
  },

  //获取租客标签
  getTagOwnerList: async (name) => {
    return await request.get({ url: `/bus/tag-owner/getTagOwnerList?name=` + name })
  }
}
