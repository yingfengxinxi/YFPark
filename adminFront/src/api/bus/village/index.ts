/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
import { padEnd } from 'lodash-es'

// 项目楼栋 VO
export interface BuildVO {
  id?: number | null // 编号
  buildNumber?: string | null // 楼栋编号
  buildName?: string | null // 楼栋名称
  logo?: string | null // 楼宇lOGO
  zoneId?: number | null // 分区ID
  villageId?: number | null // 项目ID
  haveUnit?: number | null // 有没有单元，1有，0没有
  districtTxt?: string | null // 项目省市区地址
  countryId?: number | 0 // 国家ID
  provinceId?: number | null // 省份ID
  cityId?: number | null // 城市ID
  districtId?: number | null // 区县ID
  streetId?: number | 1 // 街道/乡镇ID
  communityId?: number | 1 // 社区/村ID
  lng?: number | null // 落座经度（gcj02）
  lat?: number | null // 落座纬度（gcj02）
  address?: string | null // 地址
  floorHeight?: number | null // 标准层高
  propertyRight?: string | null // 产权性质
  buildArea?: number | null // 建筑面积
  propertyArea?: number | null // 产权面积
  rentableArea?: number | null // 可租面积
  selfUseArea?: number | null // 自用面积
  supportingArea?: number | null // 配套面积
  propertyNumber?: string | null // 产权编号
  landNumber?: string | null // 土地编号
  estateNumber?: string | null // 不动产证编号
  parkingArea?: number | null // 车位面积
  parkingCount?: number | null // 车位数量
  managementArea?: number | null // 管理面积
  roomCount?: number | null // 房间总数
  rentInArea?: number | null // 在租面积
  rentInRoom?: number | null // 在租房间数
  rentInContract?: number | null // 在租合同数
  invitationRoomCount?: number | null // 招商房间总数
  revenueTarget?: string | null // 营收目标(数组存储)
  accountDefault?: number | null // 默认收支账户
  extraConfig?: {
    totalLayer: undefined
    installation: []
    info: ''
  } // 默认配置
  tagInfo?: string | null // 楼宇标签
  isHot?: number | null // 是否热门
  sort?: number | null // 排序，越大越前
  status?: 1 // 数据状态(1使用，0隐藏)
  threeDimensionalFile?: string | null // 3D模型
  threeDimensionalId?: string | null // 3D模型物体ID
  dimensionalBgImg?: string | null // 实景图
}

export interface VillageBVO {
  id: undefined //编号,示例值(3871)
  name: undefined //项目名称,示例值(张三)
  shortName: undefined //项目名称缩写,示例值(芋艿)
  describe: undefined //项目介绍
  districtTxt: undefined //项目省市区地址
  address: undefined //详细地址
  lat: undefined //经度
  lng: undefined //纬度
  managementArea: undefined //管理面积
  rentableArea: undefined //可招商面积
  roomRentableCount: undefined //可招商房源数量
  roomCount: undefined //总房源数量,
  tagIdArr: []
  wechatNumber: undefined //项目公众号的微信号
  status: 1 //状态
  threeDimensionalFile: undefined //3D模型
  threeDimensionalId: undefined //3D模型id
  dimensionalBgImg: undefined //楼宇图片
  roomStatusColor: [
    {
      color: '#a9dcfd'
      isCustom: 0
      limit: 0
      title: '空置中'
      type: 'vacant'
    },
    {
      color: '#1890ff'
      isCustom: 0
      limit: 0
      title: '招商中'
      type: 'investment'
    },
    {
      color: '#ce9ced'
      isCustom: 0
      limit: 0
      title: '已锁定'
      type: 'lock'
    },
    {
      color: '#FFA940'
      isCustom: 0
      limit: 0
      title: '拖欠经营'
      type: 'overdue'
    },
    {
      color: '#fecb85'
      isCustom: 0
      limit: 90
      title: '90日内到期'
      type: 'expire'
    },
    {
      color: '#fdac93'
      isCustom: 0
      limit: 30
      title: '30日内到期'
      type: 'expire'
    },
    {
      color: '#ff7875'
      isCustom: 0
      limit: 0
      title: '已到期'
      type: 'expired'
    }
  ] //房源到期的颜色值数组
  orgId: 1 //当前管理的机构id
  countryId: 0 //国家id
  provinceId: undefined //省id
  cityId: undefined //市id
  districtId: undefined //区id
  streeId: 1 //街道/乡镇id
  communityId: 1 //社区、村id
  roomMinPrice: undefined //房间最低单价
  roomAveragePrice: undefined //房间平均单价
  trafficInfo: undefined //附近公交交通相关信息
  type: undefined //业态(项目类型
  extraConfig: {
    completedTime: undefined
    floor_height: undefined
    ownerName: undefined
    ownerCost: undefined
    parkingNumber: undefined
    parkingMonthlyRent: undefined
    airConditioner: undefined
    airConditionerCost: undefined
    airConditionerTime: undefined
    elevator: undefined
    network: undefined
    settledEnterprise: undefined
    attractInvestmentTime: undefined
    department_id: undefined
  } //额外扩展配置
  vrLink: undefined //vr链接
  video: undefined //视频地址
  vrVideoSort: 0 //vr视频排序
  monthHits: 0 //月浏览量
  sort: undefined // 排序值
  microServiceConfig: undefined //围绕项目服务的子项目配置信息
  areaId: undefined
  buildArr: []
}

// 项目楼栋 API
export const BuildApi = {
  // 查询项目楼栋分页
  getBuildPage: async (params: any) => {
    return await request.get({ url: `/bus/build/page`, params })
  },

  // 查询项目楼栋详情
  getBuild: async (id: number) => {
    return await request.get({ url: `/bus/build/get?id=` + id })
  },

  // 新增项目楼栋
  createBuild: async (data: BuildVO) => {
    return await request.post({ url: `/bus/build/create`, data })
  },

  // 楼栋概况  /admin-api/bus/build/projectBuild
  // getBuildProjectBuild: async (params) => {
  //   return await request.get({ url: `/bus/build/projectBuild`, params })
  // },
  getBuildProjectBuild: async (data) => {
    return await request.post({ url: `/bus/build/projectBuild`, data })
  },

  // 修改项目楼栋
  updateBuild: async (data: BuildVO) => {
    return await request.put({ url: `/bus/build/update`, data })
  },

  // 删除项目楼栋
  deleteBuild: async (id: number) => {
    return await request.delete({ url: `/bus/build/delete?id=` + id })
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

  // 项目楼宇列表  villageAndBuildListExDate
  getVillagePageExDate: async (data) => {
    return await request.post({
      url: `/bus/village/villageAndBuildListExDate`,
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
  },

  // 租客行业环形图  /admin-api/bus/owner/getCountOwnerAnnularRing
  getCountOwnerAnnularRing: async (data) => {
    return await request.post({
      url: `/bus/owner/getCountOwnerAnnularRing`,
      data
    })
  },
  // 租客标签环形图  /admin-api/bus/owner/getCountOwnerTagAnnularRing
  getCountOwnerTagAnnularRing: async (data) => {
    return await request.post({
      url: `/bus/owner/getCountOwnerTagAnnularRing`,
      data
    })
  },

  // 查询租赁面积排行列表 /admin-api/bus/contract/getContractTop5

  getContractTop5: async (data) => {
    return await request.post({
      url: `/bus/contract/getContractTop5`,
      data
    })
  },
  //租金单价排行列表    /admin-api/bus/contract/getContractTop5Price
  getContractTop5Price: async (data) => {
    return await request.post({
      url: `/bus/contract/getContractTop5Price`,
      data
    })
  },

  // 新建收支账户  /admin-api/bus/orgAccount/create
  orgAccountCreate: async (data) => {
    return await request.post({
      url: `/bus/orgAccount/create`,
      data
    })
  },

  // 获得所有收支账户配置【下拉】 /admin-api/bus/orgAccount/list
  orgAccountList: async (params) => {
    return await request.get({
      url: `/bus/orgAccount/list`,
      params
    })
  }
}
