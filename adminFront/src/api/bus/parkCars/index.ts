/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 车辆 A/** @type {*} */
export const ParkCarsApi = {
  /**
   * @description  添加车辆 (月租车白名单 - 创建月租车白名单)
   * /admin-api/bus/parkCars/create
   * @returns
   */
  addParkCars: async (data: any) => {
    return await request.post({ url: '/bus/parkCars/create', data })
  },
  /**
   * @description  车辆详情 (管理后台 - 月租车白名单 - 车辆详情)
   * /bus/parkCars/get
   * @param id 车辆id
   */
  getParkCars: async (id: number) => {
    return await request.get({ url: '/bus/parkCars/get', params: { id } })
  },
  /**
   * @description  修改车辆 (月租车白名单 - 修改月租车白名单 - 更新车辆)
   * /bus/parkCars/update
   * @returns
   */
  updateParkCars: async (data: any) => {
    return await request.put({ url: '/bus/parkCars/update', data })
  },
  /***
   * @description  删除车辆 (月租车白名单 - 删除月租车白名单)
   * /bus/parkCars/delete
   * @param id 车辆id
   * @returns
   */
  deleteParkCars: async (id: number) => {
    return await request.get({ url: '/bus/parkCars/delete', params: { id } })
  },

  /**
   * @description  车辆操作日志 (车辆操作日志 - 车辆操作日志)
   * /bus/parkCarsOperatorLog/page
   * @param params params.pageNum 页码
   * @param params params.pageSize 每页条数
   * @param params params.carsId 车辆id
   * @returns
   */
  getParkCarsOperatorLog: async (params: any) => {
    return await request.get({ url: '/bus/parkCarsOperatorLog/page', params })
  },
  /**
   * @description  车辆订单 (车的收费订单 - 车辆订单记录->【车辆详情订单分页列表】)
   * /bus/parkCarOrder/page
   * @param params params.pageNum 页码
   * @param params params.pageSize 每页条数
   * @param params params.carNumber 车牌号
   * @param params params.parkId 停车场id
   * @returns
   */
  getParkCarOrder: async (params: any) => {
    return await request.get({ url: '/bus/parkCarOrder/page', params })
  },
  /**
   * @description 创建车的收费订单【完成缴费】  (车的收费订单)
   * /admin-api/bus/parkCarOrder/create
   * @data data.villageId 项目ID
   * @data data.parkId 停车场id
   * @data data.carNumber 车牌号
   * @data data.orderType 订单类别1月租车(月租缴费)，2储值车(储值金额)，3临时车
   * @data data.orderMoney 订单金额
   */
  createParkCarOrder: async (data: any) => {
    return await request.post({ url: '/bus/parkCarOrder/create', data })
  }
}
