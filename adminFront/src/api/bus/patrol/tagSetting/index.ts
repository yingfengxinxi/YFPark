import request from '@/config/axios'

// 巡检点位数据 API
export const settingApi = {
  //获得标签模板
  getTemplateList: async (params: any) => {
    return await request.get({ url: `bus/patrolTag/getList`, params })
  },
  //获得标签模板详情
  getTemplateDetail: async (id: number) => {
    return await request.get({
      url: `bus/patrolTag/get?id=` + id
    })
  },
  //更新标签模板
  updateTemplate: async (data: any) => {
    return await request.put({ url: `bus/patrolTag/update`, data })
  },
  //创建标签模板
  createTemplate: async (data: any) => {
    return await request.post({ url: `bus/patrolTag/create`, data })
  },
  // 删除标签模板
  deleteTemplate: async (id: number) => {
    return await request.delete({ url: `bus/patrolTag/delete?id=` + id })
  }
}
