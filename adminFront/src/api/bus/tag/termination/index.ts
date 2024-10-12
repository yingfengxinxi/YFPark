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

// 退租原因标签 VO
export interface TagTerminationVO {
  id: number // 编号
  // orgId: number // 机构ID
  name: string // 标签名称
  desc: string // 标签描述
  color: string // 标签样式
  status: number // 状态，1启用，0禁用
}

// 退租原因标签 API
export const TagTerminationApi = {
  // 查询退租原因标签分页
  getTagTerminationPage: async (params: any) => {
    return await request.get({ url: `/bus/tagTermination/page`, params })
  },

  // 查询退租原因标签详情
  getTagTermination: async (id: number) => {
    return await request.get({ url: `/bus/tagTermination/get?id=` + id })
  },

  // 新增退租原因标签
  createTagTermination: async (data: TagTerminationVO) => {
    return await request.post({ url: `/bus/tagTermination/create`, data })
  },

  // 修改退租原因标签
  updateTagTermination: async (data: TagTerminationVO) => {
    return await request.put({ url: `/bus/tagTermination/update`, data })
  },

  // 删除退租原因标签
  deleteTagTermination: async (id: number) => {
    return await request.delete({ url: `/bus/tagTermination/delete?id=` + id })
  },

  // 导出退租原因标签 Excel
  exportTagTermination: async (params) => {
    return await request.download({ url: `/bus/tagTermination/export-excel`, params })
  },

  // 退组原因标签无分页 /admin-api/bus/tagTermination/getList
  getTagTerminationList: async (params) => {
    return await request.get({ url: `/bus/tagTermination/getList`, params })
  }
}
