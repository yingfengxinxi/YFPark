import request from '@/config/axios'
export const dataBoardApi = {
  //顶部统计
  getpatrolTaskStaticTop: async (params: any) => {
    return await request.get({
      url: `bus/patrolTaskEquipment/patrolTaskStaticTop`,
      params
    })
  },
  //任务统计
  getpatrolTaskStatic: async (params: any) => {
    return await request.get({
      url: `/bus/patrolTaskEquipment/patrolTaskStatic`,
      params
    })
  },
  //巡检点统计
  patrolPositionStatic: async (params: any) => {
    return await request.get({
      url: `/bus/patrolTaskEquipment/patrolPositionStatic`,
      params
    })
  },
  //异常统计
  warnRecordStatic: async (params: any) => {
    return await request.get({
      url: `/bus/patrolRecordEquipment/warnRecordStatic`,
      params
    })
  },
  // 导出异常统计
  exportWarnRecordStatic: async (params: any) => {
    return await request.download({
      url: `/bus/patrolRecordEquipment/warnRecordExport`,
      params
    })
  },
  // 岗位统计列表
  getPostList: async (params: any) => {
    return await request.get({
      url: `/bus/patrolRecordEquipment/postStationStatic`,
      params
    })
  },
  // 导出岗位统计
  exportPostList: async (params: any) => {
    return await request.download({
      url: `/bus/patrolRecordEquipment/postStationExport`,
      params
    })
  },
}
