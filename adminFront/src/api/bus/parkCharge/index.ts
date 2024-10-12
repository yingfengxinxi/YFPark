/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
// 停车场收费标准 VO
export interface VO {
  id: number // 编号
  chargeName: string // 收费标准名称
  parkId: number // 停车场ID
  villageId: number // 项目ID
  carType: string // 适用车的类型（1蓝牌、黄牌等，参照D1）
  monthFee: number // 月租费用（null不支持缴费）
  monthOverdueType: string // 月租车到期后类型（0临时车（可能不允许进入），1储值车）
  monthRechargeInput: string // 月租车自主缴费能输入月数（0不允许，1允许）
  monthRechargePackage: string // 月租车自主缴费套餐
  effectiveDate: Date // 生效日期
  freeMinute: number // 临时车免费时长（分钟）
  freeOutMinute: number // 临时车免费出场时长（分钟）
  chargeType: string // 临时车0标准收费，1按次收费，2按白天夜间收费，3按设定时间收费
  dayMaxFee: number // 临时车每日最高费用（0）不限
  chargeDetail: string // 临时车收费细则
  isDefault: string // 是否默认,1是0否
}

// 停车场收费标准 API
export const ParkChargeApi = {
  /**
   * @description  绑定收费标准 (停车场收费标准)
   * /admin-api/bus/parkCharge/getList
   * @param params.parkId 停车场id
   * @param params.villageId 项目ID
   * @param params.pageNum 页码
   * @param params.pageSize 每页条数
   * @returns
   */
  getList: async (params: any) => {
    return await request.get({ url: '/bus/parkCharge/getList', params })
  },
  // 查询停车场收费标准分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/parkCharge/page`, params })
  },

  // 查询停车场收费标准详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/parkCharge/get?id=` + id })
  },

  // 新增停车场收费标准
  create: async (data: VO) => {
    return await request.post({ url: `/bus/parkCharge/create`, data })
  },

  // 修改停车场收费标准
  update: async (data: VO) => {
    return await request.put({ url: `/bus/parkCharge/update`, data })
  },

  // 删除停车场收费标准
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/parkCharge/delete?id=` + id })
  },

  // 导出停车场收费标准 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/parkCharge/export-excel`, params })
  },

  // 获得月租缴费充值金额列表 /admin-api/bus/parkCharge/getMonthRechargePackageList
  getMonthRechargePackageList: async (params: any) => {
    return await request.get({ url: '/bus/parkCharge/getMonthRechargePackageList', params })
  }
}
