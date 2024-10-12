import request from '@/config/axios'
// 机构合同 VO

// 机构合同 API
export const Api = {
  // 查询机构合同分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/contract/page`, params })
  },
  // 查询机构合同列表----草稿
  getDraftPage: async (params: any) => {
    return await request.get({ url: `bus/contract/draftPage`, params })
  },
  //查询合同列表----归档
  getContractFilingPage: async (params: any) => {
    return await request.get({ url: `bus/contract/getContractFilingPage`, params })
  },
  // 项目字典
  getProject: async (params: any) => {
    return await request.get({ url: `/system/dict-data/page`, params })
  },
  // 获得项目列表
  getVillageList: async () => {
    return await request.get({ url: `/bus/village/getVillageList` })
  },
  // 获得楼宇列表
  getBuildingList: async () => {
    return await request.get({ url: `/bus/build/getBuildList` })
  },
  // 获得经办人,创建人
  getTenantIdUserInfoList: async () => {
    return await request.get({ url: `/system/user/getTenantIdUserInfoList` })
  },
  getTenantIdUserInfoListId: async (id) => {
    return await request.get({ url: `/system/user/getTenantIdUserInfoList?id=` + id })
  },
  //创建合同
  createContract: async (data: VO) => {
    return await request.post({ url: `/bus/contract/create`, data })
  },
  //修改合同
  renewalLease: async (data: VO) => {
    return await request.post({ url: `/bus/contract/renewalLease`, data })
  },
  //生成账单明细
  getOrderBillList: async (data) => {
    return await request.get({
      url: `/bus/contractOrderBill/getOrderBillList?count=${data.count}&termType=${data.termType}&termType=${data.termType}&leaseStartDate=${data.leaseStartDate}&leaseEndDate=${data.leaseEndDate}&day=${data.day}&bondMoney=${data.bondMoney}&payDay=${data.payDay}&unitPricePoint=${data.unitPricePoint}&calculationOrder=${data.calculationOrder}&taxInclusiveRules=${data.taxInclusiveRules}&taxInclusiveValue=${data.taxInclusiveValue}&totalArea=${data.totalArea}&dMoney=${data.dMoney}&isReceivableInteger=${data.isReceivableInteger}&feeType=${data.feeType}&contractUnitPrice=${data.contractUnitPrice}`
    })
  },
  //导出合同列表
  exportContractList: async (params) => {
    return await request.download({ url: `/bus/contract/export`, params })
  },
  //通过Id查询合同详情
  contractgetId: async (id: string) => {
    return await request.get({ url: `/bus/contract/get?id=` + id })
  },
  //获得合同日志
  getcontractOperateLog: async (params) => {
    return await request.get({ url: `/bus/contractOperateLog/page`, params })
  },
  //创建合同日志
  createcontractOperateLog: async (data) => {
    return await request.post({ url: `/bus/contractOperateLog/create`, data })
  },
  //删除合同日志
  deletecontractOperateLog: async (id) => {
    return await request.delete({ url: `/bus/contractOperateLog/delete?id=` + id })
  },
  //费用类型
  getFeeType: async () => {
    return await request.get({ url: `/bus/orgBillCostType` })
  },
  //账单列表
  getByBillTypeContractIdBillInfoList: async (params) => {
    return await request.get({
      url: `/bus/contractOrderBill/getByBillTypeContractIdBillInfoList`,
      params
    })
  },
  // 账单列表汇总
  getByBillTypeContractIdBillMoneyTotal: async (id) => {
    return await request.get({
      url: `bus/contractOrderBill/getByBillTypeContractIdBillMoneyTotal?contractId=` + id
    })
  },
  // 退租获取账单结算
  getByContractIdRentingTerminationBillList: async (id) => {
    return await request.get({
      url: `bus/contractOrderBill/getByContractIdRentingTerminationBillList?contractId=` + id
    })
  },
  // 退租获取保证金列表
  getByContractIdRentingTerminationBondBillList: async (id) => {
    return await request.get({
      url: `bus/contractOrderBill/getByContractIdRentingTerminationBondBillList?contractId=` + id
    })
  },
  //下载模板
  downloadTemplate: async (params) => {
    return await request.download({ url: `bus/contract/get-import-template`, params })
  },
  // 上传文件
  uploadFile: async (data) => {
    return await request.upload({ url: `bus/contract/import`, data })
  },
  //生成合同pdf
  createContractPdf: async (data) => {
    return await request.post({ url: `bus/contract/contractPdf`, data })
  },
  //费用类型
  getCostTypeChildrenList: async () => {
    return await request.get({ url: `bus/orgBillCostType/getCostTypeChildrenList` })
  },
  //获得费用类型
  getCostType: async (id) => {
    return await request.get({ url: `bus/orgBillCostType/get?id=` + id })
  },

  //审核合同获取流程编号
  getcontract_flow: async (params) => {
    return await request.get({ url: `/bpm/model/getList`, params })
  },
  // 创建合同审核
  createContractFlow: async (data) => {
    return await request.post({ url: `bus/contract-leave/create`, data })
  },
  //通过合同获取审批记录
  getOneByContractId: async (data) => {
    return await request.post({ url: `/bus/contract-leave/getOneByContractId`, data })
  },
  // 删除机构合同
  deleteContrat: async (id: number) => {
    return await request.delete({ url: `/bus/contract/delete?id=` + id })
  },
  // 编辑机构合同
  updateContrat: async (data: VO) => {
    return await request.put({ url: `/bus/contract/update`, data })
  },
  //物理删除创建的合同
  deleteByIdContract: async (id: number) => {
    return await request.delete({ url: `/bus/contract/deleteByIdContract?id=` + id })
  },
  //退租按钮,查看是否可以退租
  rentTerminationContract: async (id: string | number) => {
    return await request.get({ url: `bus/contract/rentTermination?contractId=` + id })
  },
  //创建退租合同
  orgContractRetreat: async (data) => {
    return await request.post({ url: `bus/orgContractRetreat/create`, data })
  },
  //获取退租合同模板List
  getRetreatTemplate: async (id) => {
    return await request.get({
      url: `/bus/contractTemplate/getRelationBuildIdTemplateList?relationBuild=` + id
    })
  },
  //获取退租模板链接
  getRetreatTemplateUrl: async (params) => {
    return await request.get({
      url: `bus/orgContractRetreat/downloadProtocol`,
      params
    })
  },
  //获取退租合同详情
  getRetreatContract: async (id) => {
    return await request.get({
      url: `bus/orgContractRetreat/get?id=` + id
    })
  },
  //作废按钮,查看是否可以作废
  contractvoid: async (id: number) => {
    return await request.get({ url: `/bus/contract/toVoidCheck?contractId=` + id })
  },
  //作废提交按钮
  contractvoidSubmit: async (data: any) => {
    return await request.post({ url: `/bus/contract/toVoid`, data })
  },
  //续租按钮,查看是否可以续租
  contractrenewalLeaseBut: async (id: number | string) => {
    return await request.get({ url: `/bus/contract/renewalLeaseBut?contractId=` + id })
  },
  //续租查看续租详情
  getSearchHistoricalContract: async (id: number | string) => {
    return await request.post({ url: `/bus/contract/getSearchHistoricalContract?id=` + id })
  },
  //变更按钮,查看是否可以变更
  contractchange: async (id: number | string) => {
    return await request.get({ url: `/bus/contract/change?contractId=` + id })
  },
  //变更提交按钮
  contractchangeSubmit: async (data: any) => {
    return await request.put({ url: `/bus/contract/changeUpdate  `, data })
  },
  //获取合同变更详情
  getChangeContract: async (id: any) => {
    return await request.get({ url: `/bus/contract/getNewContract?contractId=` + id })
  },
  //合同费用分类--------------------------------
  //获得费用分类
  getCostTypeList: async (params: any) => {
    return await request.get({ url: `/bus/orgBillCostCategory/list`, params })
  },
  // 获得费用名称
  getCostNameList: async (params: any) => {
    return await request.get({ url: `/bus/orgBillCostType/page`, params })
  },
  // 创建费用分类
  createCostType: async (data: any) => {
    return await request.post({ url: `/bus/orgBillCostCategory/create`, data })
  },
  // 删除分类
  deleteCostType: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillCostCategory/delete?id=` + id })
  },
  // 获取分类详情
  getCostTypeDetail: async (id: number) => {
    return await request.get({ url: `/bus/orgBillCostCategory/get?id=` + id })
  },
  //编辑分类
  updateCostType: async (data: any) => {
    return await request.put({ url: `/bus/orgBillCostCategory/update`, data })
  },
  // 创建费用类型
  createCostName: async (data: any) => {
    return await request.post({ url: `/bus/orgBillCostType/create`, data })
  },
  // 删除费用类型
  deleteCostName: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillCostType/delete?id=` + id })
  },
  // 获取费用类型详情
  getCostNameDetail: async (id: number) => {
    return await request.get({ url: `/bus/orgBillCostType/get?id=` + id })
  },
  //编辑费用类型
  updateCostName: async (data: any) => {
    return await request.put({ url: `/bus/orgBillCostType/update`, data })
  },

  /**合同列表不带分页
   * bus/contract/getOwnerNameContractList
   * @param params.ownerId string //业主id
   * return
   */
  getOwnerNameContractList: async (params: any) => {
    return await request.get({ url: `/bus/contract/getOwnerNameContractList`, params })
  }
}
