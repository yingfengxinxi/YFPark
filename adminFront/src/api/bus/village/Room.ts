/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
import { padEnd } from 'lodash-es'
export interface RoomBVO {
  id: undefined //编号,示例值(3871)
  roomNumber: undefined //房间编号
  roomName: undefined //房间名称
  roomAliasId: undefined //房间管理编号,示例值(1)
  parentRoomId: undefined //父级房间编号,示例值(1)
  subRoomCount: undefined //房间数量,示例值(1)
  subRoomRentCount: undefined //子房间已租总数
  buildArea: undefined //建筑面积,示例值(1.0)
  insideArea: undefined //套内面积,示例值(1.0)
  rentalArea: undefined //计租面积,示例值(1.0)
  rentalAreaIn: undefined //计租面积(在租)
  chargingArea: undefined //计费面积,示例值(1.0)
  chargingAreaIn: undefined //计费面积(在租)
  leaseStart: undefined //租赁开始时间
  leaseEnd: undefined //租赁结束时间
  deliverTime: undefined //交付时间
  invitationStatus: undefined // 1招商,0不招商(出租后默认设置不招商)
  investmentPolicy: undefined // 招商政策
  investmentConditions: undefined // 招商条件
  images: undefined // 图集数组
  priceUnit: undefined //价格单位
  priceUnitMin: undefined //低价单位
  preUnitPrice: undefined //预租单价,示例值(17991)
  preUnitPriceMin: undefined //最低价格
  tagIdArr: undefined //房源标签ID数组
  contractInfo: undefined //合同情况,记录时间,执行情况,用于判断是否逾期
  contractCount: undefined // 在租合同数，示例值(26264)
  decoration: undefined // 装修情况
  propertyRight: undefined // 产权性质
  floorHeight: undefined // 标准层高,示例值(1)
  loadMax: undefined // 荷载值
  layerId: undefined //楼层ID,示例值(1)
  unitId: undefined //单元ID,示例值(1)
  buildId: undefined //楼栋ID,示例值(1)
  zoneId: undefined //分区ID,示例值(1)
  villageId: undefined //项目ID,示例值(1)
  sort: undefined //排序,示例值(1)
  status: '1' //状态,示例值(1)
  threeDimensionalId: undefined //3D模型物体ID,示例值(10845)
  roomStatus: 0 //状态(<10空置状态，<20已预订，<30出租中),示例值(2)
  houseType: undefined //房源类型,示例值(1)
  recordNo: undefined //房源备案号
  promoterMoney: undefined //推广佣金
  promoterMoneyUnit: undefined //推广佣金单位
  extraConfig: {} //额外配置
  vrLink: undefined //VR链接
  video: undefined //视频链接
  vrVideoSort: undefined //VR视频导致的排序（VR和视频30，VR20，视频10，没有0）
  monthHits: undefined //月浏览数（计划任务统计）
  splitParentArea: undefined //楼层拆分
  isLock: undefined //是否锁定房源(1锁定，0正常(取消锁定))
  isUnreal: undefined //0=真实房间;1=非真实房间
  extraLock: undefined //锁定房源面积信息
}
// 项目楼栋 API
export const RoomApi = {
  // 获取房间列表  /admin-api/bus/room/getRoomListByLayerId
  getRoomListByLayerId: async (params) => {
    return await request.get({
      url: `/bus/room/getRoomListByLayerId`,
      params
    })
  },
  // 新增项目房间 /admin-api/bus/room/create
  createRoom: async (data: RoomBVO) => {
    return await request.post({ url: `/bus/room/create`, data })
  },

  // 房间详情 /admin-api/bus/room/get
  getRoom: async (id: number) => {
    return await request.get({ url: `/bus/room/get?id=` + id })
  },

  // 修改项目楼栋
  updateRoom: async (data: RoomBVO) => {
    return await request.put({ url: `/bus/room/update`, data })
  },

  // 删除项目楼栋
  deleteRoom: async (id: number) => {
    return await request.delete({ url: `/bus/room/delete?id=` + id })
  },

  // 导出项目楼栋 Excel
  exportRoom: async (params) => {
    return await request.download({ url: `/bus/room/export-excel`, params })
  },
  // 房间模版  /admin-api/bus/room/get-import-template
  getImportTemplate: async (params) => {
    return await request.download({ url: `/bus/room/get-import-template`, params })
  },
  // 导入房间 /admin-api/bus/room/import
  importRoom: async (data) => {
    return await request.upload({ url: `/bus/room/import`, data })
  }
}
