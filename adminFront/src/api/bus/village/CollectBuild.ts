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
import { padEnd } from 'lodash-es'

export interface CollectBuildVO {
  id?: number | null // 编号
  collectionName?: string | null // 集合名称,示例值(芋艿)
  collectionBuild?: string | null // 集合下的楼宇列表
  villageType?: string | null // 项目类型,示例值(2)
}

// 项目楼栋 API
export const CollectBuildApi = {
  // 新增项目楼栋 /admin-api/bus/village-collection/create
  createCollectBuild: async (data: CollectBuildVO) => {
    return await request.post({ url: `/bus/village-collection/create`, data })
  },
  // 查询项目楼栋详情 /admin-api/bus/village-collection/get
  getCollectBuild: async (id: number) => {
    return await request.get({ url: `/bus/village-collection/get?id=` + id })
  },

  // 修改项目楼栋 /admin-api/bus/village-collection/update
  updateCollectBuild: async (data: CollectBuildVO) => {
    return await request.put({ url: `/bus/village-collection/update`, data })
  },

  // 删除项目楼栋  /admin-api/bus/village-collection/delete
  deleteCollectBuild: async (id: number) => {
    return await request.delete({ url: `/bus/village-collection/delete?id=` + id })
  },

  // 导出项目楼栋 Excel  /admin-api/bus/village-collection/export-excel
  exportCollectBuild: async (params) => {
    return await request.download({ url: `/bus/village-collection/export-excel`, params })
  },

  // 项目集合 列表   /admin-api/bus/village-collection/page
  getVillageCollection: async (params) => {
    return await request.get({ url: `/bus/village-collection/page`, params })
  },

  // 完整集合列表  /admin-api/bus/village-collection/getList
  getVillageCollectionList: async (params) => {
    return await request.get({ url: `/bus/village-collection/getList`, params })
  }
}
