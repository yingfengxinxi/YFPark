import request from '@/config/axios'

//工单应用管理员Api
export const workOrderAdminApi = {
  //人员管理列表
  getadminCensus: async (params: any) => {
    return await request.get({ url: `/bus/workOrderAdmin/adminCensus`, params })
  },
  //获得管理员分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/workOrderAdmin/page`, params })
  },
  //添加管理员
  create: async (data: any) => {
    return await request.post({ url: `bus/workOrderAdmin/create`, data })
  },
  //删除人员岗位
  delete: async (id: number | string) => {
    return await request.delete({ url: `/bus/workOrderAdmin/delete?id=` + id })
  }
}
