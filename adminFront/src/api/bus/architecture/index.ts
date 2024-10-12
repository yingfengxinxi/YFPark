import request from '@/config/axios'

// 项目楼栋 VO
export interface BuildVO {
  id: number // 编号
  buildNumber: string // 楼栋编号
  buildName: string // 楼栋名称
  logo: string // 楼宇lOGO
  zoneId: number // 分区ID
  villageId: number // 项目ID
  haveUnit: number // 有没有单元，1有，0没有
  districtTxt: string // 项目省市区地址
  countryId: number // 国家ID
  provinceId: number // 省份ID
  cityId: number // 城市ID
  districtId: number // 区县ID
  streetId: number // 街道/乡镇ID
  communityId: number // 社区/村ID
  lng: number // 落座经度（gcj02）
  lat: number // 落座纬度（gcj02）
  address: string // 地址
  floorHeight: number // 标准层高
  propertyRight: string // 产权性质
  buildArea: number // 建筑面积
  propertyArea: number // 产权面积
  rentableArea: number // 可租面积
  selfUseArea: number // 自用面积
  supportingArea: number // 配套面积
  propertyNumber: string // 产权编号
  landNumber: string // 土地编号
  estateNumber: string // 不动产证编号
  parkingArea: number // 车位面积
  parkingCount: number // 车位数量
  managementArea: number // 管理面积
  roomCount: number // 房间总数
  rentInArea: number // 在租面积
  rentInRoom: number // 在租房间数
  rentInContract: number // 在租合同数
  invitationRoomCount: number // 招商房间总数
  revenueTarget: string // 营收目标(数组存储)
  accountDefault: number // 默认收支账户
  extraConfig: string // 默认配置
  tagInfo: string // 楼宇标签
  isHot: number // 是否热门
  sort: number // 排序，越大越前
  status: number // 数据状态(1使用，0隐藏)
  threeDimensionalFile: string // 3D模型
  threeDimensionalId: string // 3D模型物体ID
  dimensionalBgImg: string // 实景图
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

  // 获取小区列表统计
  projectBuild: async (data) => {
    return await request.post({ url: `/bus/build/projectBuild`, data })
  },
}
