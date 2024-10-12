/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
import { padEnd } from 'lodash-es'

// 项目楼栋 API
export const FloorApi = {
  // 查询楼层分页  /admin-api/bus/layer/page
  getlayerPage: async (params: any) => {
    return await request.get({ url: `/bus/layer/page`, params })
  },

  // 查询楼层分页  /admin-api/bus/layer/getLayerList
  getLayerList: async (params: any) => {
    return await request.get({ url: `/bus/layer/getLayerList`, params })
  },

  // 查询项目楼栋详情/admin-api/bus/layer/get
  getlayer: async (id: number) => {
    return await request.get({ url: `/bus/layer/get?id=` + id })
  },

  // 创建项目楼层  /admin-api/bus/layer/create
  createlayer: async (data) => {
    return await request.post({ url: `/bus/layer/create`, data })
  },

  // 楼栋概况  /admin-api/bus/build/projectBuild
  // getBuildProjectBuild: async (params) => {
  //   return await request.get({ url: `/bus/build/projectBuild`, params })
  // },
  getBuildProjectBuild: async (data) => {
    return await request.post({ url: `/bus/build/projectBuild`, data })
  },

  // 修改项目楼栋
  updatelayer: async (data) => {
    return await request.put({ url: `/bus/layer/update`, data })
  },

  // 删除项目楼栋
  deletelayer: async (id: number) => {
    return await request.delete({ url: `/bus/layer/delete?id=` + id })
  },

  // 导出项目楼栋 Excel
  exportBuild: async (params) => {
    return await request.download({ url: `/bus/build/export-excel`, params })
  },

  // 项目集合 列表   /admin-api/bus/village-collection/page
  getVillageCollection: async (params) => {
    return await request.get({ url: `/bus/village-collection/page`, params })
  },

  // 项目楼宇列表 列表   /admin-api/bus/village/villageAndBuildList
  getVillagePage: async (data) => {
    return await request.post({
      url: `/bus/village/villageAndBuildList`,
      data
    })
  },
  // 创建项目  /admin-api/bus/village/saveVillage
  createvillage: async (data: any) => {
    return await request.post({ url: `/bus/village/saveVillage`, data })
  },

  // 获取项目列表  /admin-api/bus/village/getVillageList
  getVillageList: async (params) => {
    return await request.get({ url: `/bus/village/getVillageList`, params })
  },

  // 更新项目  /admin-api/bus/village/update
  updatevillage: async (data: any) => {
    return await request.put({ url: `/bus/village/update`, data })
  },

  // 项目详情  /admin-api/bus/village/get
  getvillage: async (id: number) => {
    return await request.get({ url: `/bus/village/get?id=` + id })
  },

  // 项目概况统计  /admin-api/bus/village/projectOverview
  getprojectOverview: async (id: number) => {
    return await request.get({ url: `/bus/village/projectOverview?id=` + id })
  },

  // 删除项目 /admin-api/bus/village/delete
  deletevillage: async (id: number) => {
    return await request.delete({ url: `/bus/village/delete?id=` + id })
  },

  // 导出项目楼栋 /admin-api/bus/village/export-excel
  exportvillage: async (params) => {
    return await request.download({ url: `/bus/village/export-excel`, params })
  },

  // 获取楼层列表  /admin-api/bus/layer/getLayerList
  getLayerList: async (params) => {
    return await request.get({
      url: `/bus/layer/getLayerList`,
      params
    })
  },

  // 获取房间列表  /admin-api/bus/room/getRoomListByLayerId
  getRoomListByLayerId: async (params) => {
    return await request.get({
      url: `/bus/room/getRoomListByLayerId`,
      params
    })
  }
}
