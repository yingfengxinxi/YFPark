import request from '@/config/axios'

// 收银台 API
export const BillCashierListApi = {
  /**
   * @description  资源搜索  (管理后台 - 项目房间)
   * /bus/room/resourceList
   * @param params.roomName 房间名称
   * @param params.roomAliasId 房间管理编号
   * @returns
   */
  resourceList: async (params: any) => {
    return await request.get({ url: '/bus/room/resourceList', params })
  },
  /**
   * @description  租客查询  (管理后台 - 租客信息)
   *  bus/owner/ownerSearchList
   * @data data.name 租客姓名
   * @data data.tel 租客手机号
   */
  ownerSearchList: async (data: any) => {
    return await request.post({ url: '/bus/owner/ownerSearchList', data })
  },
  /**
   * @description  停车费查询  (管理后台 - 月租车白名单)
   *  bus/parkCars/getList
   * @param params.carNumber 车牌号
   * @param params.userName 车主姓名
   * @param params.userPhone 车主手机号
   */
  getList: async (params: any) => {
    return await request.get({ url: '/bus/parkCars/getList', params })
  }
}
