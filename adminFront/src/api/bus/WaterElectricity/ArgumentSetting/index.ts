import request from '@/config/axios'

// 自定义抄表计划 API
export const bailingOrgConfigApi = {
  getList: async (params: any) => {
    return await request.get({ url: `bus/bailingOrgConfig/getList`, params })
  },
  save: async (data: any) => {
    return await request.post({ url: `bus/bailingOrgConfig/save`, data })
  },
}
