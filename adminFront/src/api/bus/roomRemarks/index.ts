/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
/** 房源操作记录 */
export const roomRemarkApi = {
  /**
   * 房源->备忘提醒 (获得房源操作记录分页)
   * /bus/room-remarks/page
   *  @param {string} params.roomId 房源id
   *  @param {string} params.pageNo 页码
   *  @param {string} params.pageSize 每页条数
   */
  getPage: async (params: any) => {
    return await request.get({ url: '/bus/room-remarks/page', params })
  },
  /**
   * 房源->备忘提醒 (新增房源操作记录)
   * /bus/room-remarks/create
   *  @param {string} params.roomId 房源id
   *  @param {string} params.villageId 园区id
   *  @param {string} params.content 备注内容
   */
  create: async (data: any) => {
    return await request.post({ url: '/bus/room-remarks/create', data })
  }
}
