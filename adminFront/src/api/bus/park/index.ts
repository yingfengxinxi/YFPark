/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 停车场 API
export const ParkApi = {
  /**
   * @description  获取根据项目id停车场列表 （父级停车场列表）
   * /bus/park/getList?villageId=
   * @param params.villageId 项目id
   */
  getParkingList: async (id) => {
    return await request.get({ url: `/bus/park/getList?villageId=` + id })
  },
  /**
   * @description  添加停车场 (停车场)
   * /admin-api/bus/park/create
   * @returns
   */
  addPark: async (data: any) => {
    return await request.post({ url: '/bus/park/create', data })
  },

  /**
   * @description  修改
   * /bus/park/update
   * @returns
   */
  updatePark: async (data: any) => {
    return await request.put({ url: `/bus/park/update`, data })
  },
  /**
   * @description  删除
   * /bus/park/delete
   * @returns
   */
  deletePark: async (id: number) => {
    return await request.delete({ url: `/bus/park/delete?id=` + id })
  },

  // 导出租客信息 Excel
  /**
   * 导出停车场（一个项目可以有多个场） Excel
   * /bus/park/export-excel
   */
  exportPark: async (params) => {
    return await request.download({ url: `/bus/park/export-excel`, params })
  }
}
