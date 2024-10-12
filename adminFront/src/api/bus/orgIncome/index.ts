import request from '@/config/axios'

// 机构收入 VO
export interface orgIncomeVO {
  id: number // 编号
  villageId: number // 项目id
  contractId: number // 合同id
  billId: number // 账单id
  billType: string // 账单类型;1=收款;2=付款;
  streamMiddleId: number // 子流水id
  contractNumber: string // 合同编号
  buildId: number // 楼宇id
  industryId: number // 租客所属行业ID
  roomNumber: string // 楼层-房间号;{"room": [{"id":"1001","room_id":"","room_name":"","layer_id":"","unit_id":"","zone_id":""}]}
  ownerId: number // 租客id
  incomeType: string // 收入类型;1=应收(含税);2=应收(不含税);3=实收(含税);4=实收(不含税);
  isConfirm: string // 是否确认;1=当月待确认;1=当月已确认;
  confirmTime: Date // 确认时间
  confirmUid: string // 确认人员uid
  unconfirmTime: Date // 取消确认时间
  unconfirmUid: string // 取消确认人员uid
  contractCostType: string // 合同费用类型
  ownerCostType: string // 租客费用类型
  costType: string // 账单费用类型
  tradeAmount: number // 交易金额
  amount: number // 含/不含税金额
  taxAmount: number // 税额
  tradeTime: Date // 交易时间
  isUnrealRoom: string // 房屋虚拟标识.0=真实房屋;1=非真实房屋
  unrealRoom: string // 虚拟房屋信息
}

// 机构收入 API
export const orgIncomeApi = {
  /** 顶部统计
   *  bus/orgIncome/getAmountStatistics
   * return
   */
  getAmountStatistics: async () => {
    return await request.get({ url: `/bus/orgIncome/getAmountStatistics` })
  },
  // 查询机构收入分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgIncome/page`, params })
  },

  // 查询机构收入详情
  get: async (id: number) => {
    return await request.get({ url: `/bus/orgIncome/get?id=` + id })
  },

  // 新增机构收入
  create: async (data: orgIncomeVO) => {
    return await request.post({ url: `/bus/orgIncome/create`, data })
  },

  // 修改机构收入
  update: async (data: VO) => {
    return await request.put({ url: `/bus/orgIncome/update`, data })
  },

  // 删除机构收入
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/orgIncome/delete?id=` + id })
  },

  // 导出机构收入 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/orgIncome/export-excel`, params })
  },
  /** 收支确认保存接口
   * bus/orgIncomeConfig/create
   * @param data.lmtDay 确认天数
   * return
   */
  createConfig: async (data: any) => {
    return await request.post({ url: `/bus/orgIncomeConfig/create`, data })
  },
  /**
   * 收支确认配置查询
   * bus/orgIncomeConfig/getOneInfo
   * return
   */
  getConfig: async () => {
    return await request.get({ url: `/bus/orgIncomeConfig/getOneInfo` })
  },
  /**确认，取消
   * 批量确认/单个取消接口
   * bus/orgIncome/confirmIncome?ids=1,2,3,4&isConfirm=
   * @param data.ids 确认id集合
   * @param data.isConfirm 确认状态
   * return
   */
  confirmIncome: async (data: any) => {
    return await request.post({ url: `/bus/orgIncome/confirmIncome`, data })
  },
  /** 收支列表详情
   * /admin-api/bus/orgIncome/get
   * @param id
   * return
   */
  getDetail: async (id: number) => {
    return await request.get({ url: `/bus/orgIncome/get?id=` + id })
  }
}
