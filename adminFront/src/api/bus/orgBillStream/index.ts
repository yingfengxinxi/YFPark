import request from '@/config/axios'
// 机构账单收支流水 VO
export interface BillStreamVO {
  id: number // 编号
  villageId: number // 项目id
  buildId: number // 楼宇id
  buildIds: string // 绑定的楼宇json
  buildBind: string // 绑定楼宇信息json
  roomNumber: string // 房间号json
  billId: number // 账单id
  accountId: number // 收款(业主)流水账号id
  accountName: string // 收款流水账户名称
  streamNumber: string // 流水号
  streamSource: string // 流水来源;1=手动创建;
  billType: string // 账单类型;1=收款;2=付款;
  isClose: number // 流水是否开启;0=关闭;1=开启;默认开启
  loanType: string // 借贷类型
  isConfirm: number // 是否确认;1=待确认;2=已确认;
  amount: number // 发生额
  incomeDate: Date // 入账日期
  cancelMatchDate: Date // 取消匹配日期
  companyId: number // 对账收付款公司id
  companyName: string // 对方单位名称
  companyType: number // 收付款公司类型
  streamAccount: string // 租客开户银行名称
  matchDate: Date // 匹配日期
  matchStatus: string // 匹配状态;1=完全匹配;2=未匹配;3=部分匹配;
  matchPrice: number // 已匹配金额
  nomatchPrice: number // 未匹配金额
  refundPrice: number // 退款金额
  currency: string // 币种
  costType: string // 费用类型
  linkName: string // 联系人
  remitType: string // 汇款方式
  otherAccount: string // 对方账号
  sonAccount: string // 对方子账户
  sonAccountName: string // 对方子账户名称
  sonAccountStatus: string // 对方子账户余额调整状态
  voucherNo: string // 凭证号
  receiptNo: string // 收据编号
  abstractc: string // 摘要
  isUnrealRoom: number // 房屋虚拟标识.0=真实房屋;1=非真实房屋
  unrealRoom: string // 虚拟房屋信息
  remark: string // 备注
  closeReason: string // 关闭流水的原因
  extra: string // 预存的额外信息，比如电表id
  approvalStatus: string // 删除流水申请的状态0正常1删除中2已删除
  approvalCancel: string // 删除审批中是否允许撤销0不允许1允许
  reason: string // 删除流水的原因
  singleType: string // 专项名称
}

export const orgBillStreamApi = {
  /**
   * 收款->匹配流水->收支流水列表分页 (管理后台 - 机构账单收支流水)
   * /admin-api/bus/orgBillStream/getStreamList
    @param {string} params.billType 账单类型;1=收款;2=付款; (默认1)
    @param {string} params.villageId 园区id
    @param {string} params.ownerId 租客id
    @param {string} params.matchDateStr 收账日期
    @param {string} params.pageNo 页码
    @param {string} params.pageSize 每页条数
   */
  getStreamList: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStream/getStreamList`, params })
  },

  /**
   *租客账单->收款->保存接口  (管理后台 - 机构账单收支流水)
   * /admin-api/bus/orgBillStream/create
   */
  orgBillStreamCreate: async (data: any) => {
    return await request.post({ url: `/bus/orgBillStream/create`, data })
  },
  /**
   * 结算滞纳金->新建流水
   * /admin-api/bus/orgBillStream/lateSettle
   * @param {string} params.annexList 附件[{"name":"文件名称","type":"1","filePath":"路径","annexSource":"1"}]
   * @param {string} params.villageId 园区id
   * @param {string} params.ownerId 租客id
   * @param {string} params.roomNumber 房屋id
   * @param {string} params.loanType 借贷标【查看数据字典：LOAN_TYPE】
   * @param {string} params.currency 币种【查看数据字典：CURRENCY】
   * @param {string} params.amount 发生额
   * @param {string} params.incomeDate 入账时间
   * @param {string} params.accountId 流水账户Id
   *  return
   */
  lateSettle: async (data: any) => {
    return await request.post({ url: `/bus/orgBillStream/lateSettle`, data })
  },

  /**
   * 财务 新建收支流水    (管理后台 - 机构账单收支流水)
   * /admin-api/bus/orgBillStream/createBillStream
   * @param {string} params.annexList 附件[{"name":"文件名称","type":"1","filePath":"路径","annexSource":"1"}]
   * @param {string} params.villageId 园区id
   * @param {string} params.ownerId 租客id
   * @param {string} params.roomNumber 房屋id
   * @param {string} params.loanType 借贷标【查看数据字典：LOAN_TYPE】
   * @param {string} params.currency 币种【查看数据字典：CURRENCY】
   * @param {string} params.amount 发生额
   * @param {string} params.incomeDate 入账时间
   * @param {string} params.accountId 流水账户Id
   * return
   */
  createBillStream: async (data: any) => {
    return await request.post({ url: `/bus/orgBillStream/createBillStream`, data })
  },

  /**
   * 收支流水分页列表 【财务查询收支流水时ownerId非必填，租客查询收支流水时ownerId必填】
   *bus/orgBillStream/getStreamPage?ownerId=&pageSize=10&pageNo=1
   * @param {string} params.ownerId 租客id
   * @param {string} params.pageSize 每页条数
   * @param {string} params.pageNo 页码
   */
  getBillStreamPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStream/getStreamPage`, params })
  },

  // 查询机构账单收支流水详情
  getBillStream: async (id: number) => {
    return await request.get({ url: `/bus/orgBillStream/get?id=` + id })
  },

  // 修改机构账单收支流水
  updateBillStream: async (data: BillStreamVO) => {
    return await request.put({ url: `/bus/orgBillStream/update`, data })
  },

  // 删除机构账单收支流水
  deleteBillStream: async (data: any) => {
    return await request.delete({ url: `/bus/orgBillStream/delete`, data })
  },

  // 导出机构账单收支流水 Excel【财务查询收支流水时ownerId非必填，租客查询收支流水时ownerId必填】
  exportBillStream: async (params) => {
    return await request.download({ url: `/bus/orgBillStream/export`, params })
  },

  /**
   * 获取导入模板
   * bus/orgBillStream/get-import-template
   * @param params
   * @returns
   */
  getImportTemplate: async () => {
    return await request.download({ url: `/bus/orgBillStream/get-import-template` })
  },

  /**
   * 导入流水数据
   * bus/orgBillStream/import
   * @param params
   * @returns
   */
  import: async (data: any) => {
    return await request.post({ url: `/bus/orgBillStream/import`, data })
  },

  /**
   * 关闭流水 [bus:orgBillStream:closeStream]
   *bus/orgBillStream/closeStream?id=&closeReason=
   * @param params.id 流水id
   * @param params.closeReason 关闭流水的原因
   * @returns
   */
  closeStream: async (data: any) => {
    return await request.put({ url: `/bus/orgBillStream/closeStream`, data })
  },

  /**
   * 收支流水顶部统计 【财务查询收支流水时ownerId非必填，租客查询收支流水时ownerId必填】
   * bus/orgBillStream/getOwnerIdStreamTotalMoney?ownerId=
   * return
   */

  getOwnerIdStreamTotalMoney: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStream/getOwnerIdStreamTotalMoney`, params })
  },

  /**
   * 收支流水顶部统计明细【财务查询收支流水时ownerId非必填，租客查询收支流水时ownerId必填】
   * bus/orgBillStream/getOwnerIdLoanTypeInfoList?ownerId=&loanType=
   * return
   */
  getOwnerIdLoanTypeInfoList: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStream/getOwnerIdLoanTypeInfoList`, params })
  },

  /**
   * 财务-收入列表 - 详情 - 流水信息
   * bus/orgBillStream/incomeStreamDetail?streamMiddleId=84
   * @param params.streamMiddleId 流水id
   * return
   */
  incomeStreamDetail: async (params: any) => {
    return await request.get({ url: `/bus/orgBillStream/incomeStreamDetail`, params })
  }
}
