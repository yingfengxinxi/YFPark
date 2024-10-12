import request from '@/config/axios'

// 工单分类配置 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  application: string // 应用标识
  name: string // 大类名称
  type: string // 运作模式;1=派单+抢单2=派单
  overHour: number // 完成时限
  hasChange: string // 是否允许转单;1=开启,0=关闭
  sort: number // 排序值
  buildBind: string // 应用楼宇json
  subcatIds: string // 工单子类id集合
}

// 工单分类配置 API
export const CategoryApi = {
  // 查询工单分类配置分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/category/page`, params })
  },

  // 查询工单分类配置详情
  get: async (id: number) => {
    return await request.get({ url: `bus/category/get?id=` + id })
  },

  // 新增工单分类配置
  create: async (data: VO) => {
    return await request.post({ url: `bus/category/create`, data })
  },

  // 修改工单分类配置
  update: async (data: VO) => {
    return await request.put({ url: `bus/category/update`, data })
  },

  // 删除工单分类配置
  delete: async (id: number) => {
    return await request.delete({ url: `bus/category/delete?id=` + id })
  },

  // 导出工单分类配置 Excel
  export: async (params) => {
    return await request.download({ url: `bus/category/export-excel`, params })
  },
  //更改是否支持转单
  updateHasChange: async (data: any) => {
    return await request.put({ url: `bus/category/changeStatus`, data })
  },
  //工单分类下拉框
  getList: async (params: any) => {
    return await request.get({ url: `bus/category/getList`, params })
  }
}
