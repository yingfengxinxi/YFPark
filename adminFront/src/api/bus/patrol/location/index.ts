import request from '@/config/axios'

// 位置 VO
export interface VO {
  id: number // 编号
  orgId: number // 机构ID
  number: string // 位置编码
  name: string // 位置名称
  level: string // 位置级别字符串，逗号拼接上级id
  parentId: number // 父级id
  param: string // 参数
  remark: string // 备注
  sort: number // 排序
  status: string // 显示状态，2禁用 1显示，0隐藏
}

// 位置 API
export const LocaltionApi = {
  // 查询位置分页
  getPage: async (params: any) => {
    return await request.get({ url: `bus/patrolLocation/page`, params })
  },

  // 查询位置详情
  get: async (id: number) => {
    return await request.get({ url: `bus/patrolLocation/get?id=` + id })
  },

  // 新增位置
  create: async (data: VO) => {
    return await request.post({ url: `bus/patrolLocation/create`, data })
  },

  // 修改位置
  update: async (data: VO) => {
    return await request.put({ url: `bus/patrolLocation/update`, data })
  },

  // 删除位置
  delete: async (id: number) => {
    return await request.delete({ url: `bus/patrolLocation/delete?id=` + id })
  },

  // 导出位置 Excel
  export: async (params) => {
    return await request.download({ url: `bus/patrolLocation/export-excel`, params })
  },
  // 查询位置树
  getTree: async (params: any) => {
    return await request.get({ url: `bus/patrolLocation/getTree`, params })
  },
  // 禁用位置
  disable: async (id: number) => {
    return await request.get({ url: `bus/patrolLocation/disable?id=` + id })
  },
  // 启用位置
  enable: async (id: number) => {
    return await request.get({ url: `bus/patrolLocation/enable?id=` + id })
  },
  //批量导入
  batchCreate: async (data: any) => {
    return await request.post({ url: `bus/patrolLocation/batchCreate`, data })
  },
  //巡检点选择位置树状结构
  getPatrolLocationTree: async (params: any) => {
    return await request.post({ url: `/bus/property-location/getTree`, params })
  },
}
