/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
export const contractSelectedPropertieApi = {
  /** 根据合同id 获取房源信息
   * /bus/contractSelectedPropertie/getContractIdRoomInfoList?contractId=128
   * 楼宇:buildName, 楼层：floor, 房号:roomName  面积:rentalArea, 房号id: villageRoomId
   * @param contractId 合同id
   */
  getContractIdRoomInfoList: async (contractId: number) => {
    return request.get({
      url: `/bus/contractSelectedPropertie/getContractIdRoomInfoList?contractId=${contractId}`
    })
  }
}
