import request from '@/config/axios'

// 社区 VO
export interface villageVO {
  id: number // 编号
  name: string // 项目名称
  shortName: string // 项目名称缩写
  describe: string // 项目介绍
  logo: string // 项目LOGO
  districtTxt: string // 项目省市区地址
  address: string // 项目详细地址
  lng: number // 落座经度（gcj02）
  lat: number // 落座纬度（gcj02）
  managementArea: number // 管理面积
  rentableArea: number // 可招商面积
  roomRentableCount: number // 可招商房源数量
  roomCount: number // 总房源数量
  tagIdArr: string // 标签（数组存储）
  wechatNumber: string // 项目公众号的微信号
  status: number // 数据状态
  threeDimensionalFile: string // 3D模型
  threeDimensionalId: string // 3D模型物体ID
  dimensionalBgImg: string // 楼宇图片
  roomStatusColor: string // 房源到期的颜色值数组
  orgId: number // 当前管理的机构ID
  countryId: number // 国家ID
  provinceId: number // 省份ID
  cityId: number // 城市ID
  districtId: number // 区县ID
  streetId: number // 街道/乡镇ID
  communityId: number // 社区/村ID
  roomMinPrice: number // 房间最低单价（月）
  roomAveragePrice: number // 房间平均单价（月）
  trafficInfo: string // 附近公共交通相关信息
  type: string // 业态
  extraConfig: string // 额外扩展配置
  vrLink: string // vr链接
  video: string // 视频地址
  vrVideoSort: number // VR视频导致的排序（VR和视频30，VR20，视频10，没有0）
  monthHits: number // 月浏览数（计划任务统计）
  sort: number // 排序值
  microServiceConfig: string // 围绕项目服务的子项目配置信息，例如人脸识别设备
}

// 社区 API
export const villageApi = {
  // 查询社区分页
  getvillagePage: async (params: any) => {
    return await request.get({ url: `/bus/village/page`, params })
  },

  // 查询社区详情
  getvillage: async (id: number) => {
    return await request.get({ url: `/bus/village/get?id=` + id })
  },

  // 新增社区
  createvillage: async (data: villageVO) => {
    return await request.post({ url: `/bus/village/create`, data })
  },

  // 修改社区
  updatevillage: async (data: villageVO) => {
    return await request.put({ url: `/bus/village/update`, data })
  },

  // 删除社区
  deletevillage: async (id: number) => {
    return await request.delete({ url: `/bus/village/delete?id=` + id })
  },

  // 导出社区 Excel
  exportvillage: async (params) => {
    return await request.download({ url: `/bus/village/export-excel`, params })
  },

  // 园区列表统计
  projectManage: async (data) => {
    return await request.post({ url: `/bus/village/projectManage`, data})
  },
}
