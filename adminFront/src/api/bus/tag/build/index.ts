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

// 楼宇标签 VO
export interface TagBuildVO {
  id: number // 编号
  // orgId: number // 机构ID
  name: string // 标签名称
  desc: string // 标签描述
  color: string // 标签样式
  userShow: number // 用户是否可见（1可见，0不可见）
  status: number // 状态，1启用，0禁用
}

// 楼宇标签 API
export const TagBuildApi = {
  // 查询楼宇标签分页
  getTagBuildPage: async (params: any) => {
    return await request.get({ url: `/bus/tag-build/page`, params })
  },

  // 查询楼宇标签详情
  getTagBuild: async (id: number) => {
    return await request.get({ url: `/bus/tag-build/get?id=` + id })
  },

  // 新增楼宇标签
  createTagBuild: async (data: TagBuildVO) => {
    return await request.post({ url: `/bus/tag-build/create`, data })
  },

  // 修改楼宇标签
  updateTagBuild: async (data: TagBuildVO) => {
    return await request.put({ url: `/bus/tag-build/update`, data })
  },

  // 删除楼宇标签
  deleteTagBuild: async (id: number) => {
    return await request.delete({ url: `/bus/tag-build/delete?id=` + id })
  },

  // 导出楼宇标签 Excel
  exportTagBuild: async (params) => {
    return await request.download({ url: `/bus/tag-build/export-excel`, params })
  },

  // 楼宇标签列表  /admin-api/bus/tag-build/getTagBuildList
  getTagBuildList: async (params: any) => {
    return await request.get({ url: `/bus/tag-build/getTagBuildList`, params })
  },

  // 通过id集合获得楼宇标签列表  /admin-api/bus/tag-build/getTagBuildListByIds
  getTagBuildListByIds: async (params: any) => {
    return await request.get({
      url: `/bus/tag-build/getTagBuildListByIds`,
      params: {
        ids: params.join(',')
      }
    })
  }
}
