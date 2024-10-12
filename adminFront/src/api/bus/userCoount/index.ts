import request from '@/config/axios'
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
export const userCoountApi = {
  getCountUserExtends: async (data: OwnerVO) => {
    return await request.post({ url: `/bus/user-extends/getCountUserExtends`, data })
  }
}
