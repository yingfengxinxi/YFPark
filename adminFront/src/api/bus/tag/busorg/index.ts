import request from '@/config/axios'

// 机构 VO
export interface OrgVO {
  id: number // 编号
  name: string // 机构缩写名称
  adminUid: string // 管理员用户id
  company: string // 机构全名称
  provinceId: number // 省份id
  cityId: number // 市id
  districtName: string // 省市地址;英文逗号,拼接
  info: string // 机构介绍
  logo: string // 机构logo
  squareLogo: string // 正方形logo（建议透明的白色）
  total: number // 机构总人数
  tel: string // 联系电话
  address: string // 联系地址
  type: string // 机构类别
  domainPrefix: string // 机构专属域名前缀（若不填写则访问专属域名时展示平台信息，例如 admin-id.xxx.com）
  isOem: number // 是否为OEM
  wechatNumber: string // 公众号的微信号
  orgPrefix: string // 机构成员工号前缀(支持自定义)
  joinCode: string // 机构码(8位随机字符串)
  status: number // 机构状态，1启动，0审核中，4禁用
  dictionary: string // 字典（替换页面上的字）
  module: string // 支持开放的模块权限
  villageType: string // 允许的项目类型
  overdueDay: Date // 过期日期（当天23:59才过期）
  clueTel: string // 招商电话
  clueTime: string // 招商时间
  regFrom: number // 注册来源(0自己注册，1企微，2后台添加，3钉钉...)
  maxUseArea: number // 最大可使用面积
  maxUseRoom: number // 最大可使用房间数
  lastLoginTime: Date // 最后登录时间
}

// 机构 API
export const OrgApi = {
  // 查询机构分页
  getOrgPage: async (params: any) => {
    return await request.get({ url: `/bus/org/page`, params })
  },

  // 查询机构详情
  getOrg: async (id: number) => {
    return await request.get({ url: `/bus/org/get?id=` + id })
  },

  // 新增机构
  createOrg: async (data: OrgVO) => {
    return await request.post({ url: `/bus/org/create`, data })
  },

  // 修改机构
  updateOrg: async (data: OrgVO) => {
    return await request.put({ url: `/bus/org/update`, data })
  },

  // 删除机构
  deleteOrg: async (id: number) => {
    return await request.delete({ url: `/bus/org/delete?id=` + id })
  },

  // 导出机构 Excel
  exportOrg: async (params) => {
    return await request.download({ url: `/bus/org/export-excel`, params })
  },
}
