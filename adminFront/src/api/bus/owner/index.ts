import request from '@/config/axios'

// 租客信息 VO
export interface OwnerVO {
  id: number // 编号
  orgId: number // 机构ID
  name: string // 租客名称
  isPersonal: number // 是否个人，1个人，0公司
  isSuggest: number // 是否推荐;0=不推荐;1=推荐;
  type: number // 目标对象;0=正常租客;1=收款对象;2=付款对象;
  sham: number // 虚拟身份注册;0=无;1=虚拟个人注册;2=虚拟企业注册;
  contactId: number // 联系人
  contactSignId: number // 租客合同签署人id
  contactNoticeId: number // 缴费通知单联系人
  approvalContactId: number // 审批联系人id
  certificateNumber: string // 证件号码
  industryId: number // 行业分类id
  villageId: number // 租客绑定的项目id
  buildBind: string // 租客绑定的项目楼宇房源信息
  tel: string // 联系方式
  email: string // 邮箱
  tenantNo: string // 租客编码
  websiteLink: string // 官网地址
  logo: string // 企业logo
  companyDesc: string // 企业简介
  businessInfoFoundingTime: Date // 成立日期
  businessInfoBusinessTerm: Date // 营业期限
  registeredCapital: number // 注册资金
  invoiceInfo: string // 开票信息
  businessInfo: string // 工商信息
  tagInfo: string // 租客标签
  diyField: string // 自定义字段
  isAdvanceNotice: number // 是否开启公司代付通知
  isArchive: number // 是否归档
}

// 租客信息 API
export const OwnerApi = {
  // 查询租客信息分页
  getOwnerPage: async (params: any) => {
    return await request.get({ url: `/bus/owner/page`, params })
  },

  // 查询租客信息详情
  getOwner: async (id: number) => {
    return await request.get({ url: `/bus/owner/get?id=` + id })
  },

  // 新增租客信息
  createOwner: async (data: OwnerVO) => {
    return await request.post({ url: `/bus/owner/create`, data })
  },

  // 修改租客信息
  updateOwner: async (data: OwnerVO) => {
    return await request.put({ url: `/bus/owner/update`, data })
  },

  // 删除租客信息
  deleteOwner: async (id: number) => {
    return await request.delete({ url: `/bus/owner/delete?id=` + id })
  },

  // 导出租客信息 Excel
  exportOwner: async (params) => {
    return await request.download({ url: `/bus/owner/export`, params })
  },
  //下载导入模板
  downloadTemplate: async (params) => {
    return await request.download({ url: `/bus/owner/get-import-template`, params })
  },
  //租客信息统计
  getCountOwnerMap: async (params) => {
    return await request.get({ url: `/bus/owner/getCountOwnerMap`, params })
  },
  // 导入租客信息 Excel
  import: async (data) => {
    return await request.upload({
      url: `/bus/owner/import`,
      data
    })
  },
  //根据姓名获取租客信息
  getOwnerListByName: async (name: string) => {
    return await request.get({ url: `/bus/owner/getOwnerListByName?name=` + name })
  },
  //根据id获取租客信息
  getOwnerById: async (id: number) => {
    return await request.get({ url: `/bus/owner/get?id=` + id })
  },
  // 上传图片
  uploadFile: async (data) => {
    return await request.upload({ url: `/infra/file/upload`, data })
  },
  //获取行业分类
  getTagIndustryList: async (name) => {
    return await request.get({ url: `/bus/tag-industry/getTagIndustryList?name=` + name })
  },
  getTagIndustryListID: async (id) => {
    return await request.get({ url: `/bus/tag-industry/getTagIndustryList?id=` + id })
  },

  //获得租客列表
  getOwnerList: async () => {
    return await request.get({ url: `/bus/owner/getOwnerList` })
  },

  // 根据房间id查询租客信息 :bus/owner/getByRoomIdOwnerList?roomId=
  getByRoomIdOwnerList: async (id) => {
    return await request.get({ url: `/bus/owner/getByRoomIdOwnerList?roomId=` + id })
  },

  /**
   * 收银台-租客详情顶部数据接口
   * /admin-api/bus/owner/getByIdOwnerInfo
   * @param id
   */
  getByIdOwnerInfo: async (id) => {
    return await request.get({ url: `/bus/owner/getByIdOwnerInfo?id=` + id })
  },
  //通过手机号获取租客信息
  getOwnerByTel: async (iphone) => {
    return await request.get({ url: `bus/village-user/getUserOwnerList?phone=` + iphone })
  },
}
