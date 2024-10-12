/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 项目类型 VO
export interface VillageTypeVO {
  id: number // 编号
  name: string // 类型名称
  alias: string // 类型别名
  bgImg: string // 类型背景图片
  iconImg: string // 类型icon图片
  menu: string // 服务上报的菜单及应用
  filterMenu: string // 受限制过滤的菜单及应用
}

// 项目类型 API
export const VillageTypeApi = {
  // 查询项目类型分页
  getVillageTypePage: async (params: any) => {
    return await request.get({ url: `/bus/village-type/page`, params })
  },

  // 查询项目类型分页
  getVillageTypeNopage: async () => {
    return await request.get({
      url: `/bus/village-type/getList`,
      params: { pageNo: 1, pageSize: 100 }
    })
  },

  // 查询项目类型详情
  getVillageType: async (id: number) => {
    return await request.get({ url: `/bus/village-type/get?id=` + id })
  },

  // 新增项目类型
  createVillageType: async (data: VillageTypeVO) => {
    return await request.post({ url: `/bus/village-type/create`, data })
  },

  // 修改项目类型
  updateVillageType: async (data: VillageTypeVO) => {
    return await request.put({ url: `/bus/village-type/update`, data })
  },

  // 删除项目类型
  deleteVillageType: async (id: number) => {
    return await request.delete({ url: `/bus/village-type/delete?id=` + id })
  },

  // 导出项目类型 Excel
  exportVillageType: async (params) => {
    return await request.download({ url: `/bus/village-type/export-excel`, params })
  }
}
