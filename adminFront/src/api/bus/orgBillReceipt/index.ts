/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
export interface ReceiptVO {
  billingStatement?: Object | null //开据账单
  invoicedAmount?: string | null // 可开据金额
  applicationInvoicedAmount?: string | null // 申请可开据金额
  id: number // 编号
  villageId: number // 项目id
  buildId: number // 楼宇id
  billId: number // 账单id
  subbillId: number // 子账单ID
  ruleId: number // 账单收据编号规则id
  roomNumber: string // 房号
  paymentCompanyId: number // 交款公司/单位id
  paymentCompany: string // 交款单位
  paymentUname: string // 交款人
  paymentAddress: string // 交款方地址
  paymentPhone: string // 交款方电话
  receiptNumber: string // 收据编号
  receiptInt: number // 编号数值
  numberType: string // 生成编号方式;1=规则生成;2=自定义
  price: number // 收据金额
  canReceiptAmount: number // 可开据金额
  applyReceiptAmount: number // 申请开据金额
  currency: string // 币种
  status: string // 收据状态;1=已发出;2=已回收;3=新建待审批;4=作废待审批;5=已作废;6=未发出;
  paymentUid: string // 交款人uid
  collectionCompanyId: number // 收款公司/单位id
  collectionCompany: string // 收款单位
  collectionUid: string // 收款人uid
  collectionUname: string // 收款方收款人
  collectionAddress: string // 收款方地址
  collectionPhone: string // 收款方电话
  remark: string // 备注
  remitType: string // 汇款方式
  costType: string // 费用类型
  costName: string // 费用名称
  taskKey: string // 开据批次凭证
  groupKey: string // 开据分组凭证
  issuerUid: string // 开据人
  issuerTime: Date // 开据时间
  applyTemplateId: number // 收据当前应用模板id
  applyTemplate: string // 收据当前应用模板生成数据
  applyStatus: string // 发票生成状态;0=未生成 1=生成中 2=已生成
}

/** 管理后台 - 机构账单收据 */
export const orgBillReceiptApi = {
  /**
   * @description 单列表-开据收据内容【申请开据的收据】
   * /admin-api/bus/orgBillReceipt/lssue
   * @param params.billIds 账单id集合,示例值(1)
   * return
   */
  getLssue: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceipt/lssue', params })
  },
  /**
   * 编辑->保存收据
   * /admin-api/bus/orgBillReceipt/create
   * @param data.receiptRuleId 编号规则id
   * @param data.receiptRuleName 编号规则名称
   * @param data.build 楼宇id
   * @param data.billId 账单id
   * @param data.receiptNumber 收据编号【两种生成方式1、调用生成编号接口生成2、在编辑中手动输入】
   * @param data.invoicedAmount 可开据金额
   * @param data.applicationInvoicedAmount 申请可开据金额
   * @param data.costType 费用类型
   * @param data.costName 费用名称
   * @param data.collectionCompanyId	 收款单位
   * @param data.paymentCompanyId	 交款方-交款单位Id
   * @param data.paymentCompany	 交款方-交款单位名称
   * @param data.numberType 生成编号方式;1=规则生成;2=自定义【调用接口后生成编号是1，从编辑输入编号是2】
   * return
   */
  create: async (data: any) => {
    return await request.post({ url: '/bus/orgBillReceipt/create', data })
  },

  /**
   * @description 账单详情-收据记录
   * /bus/orgBillReceipt/getReceiptRecord?billId=
   * @param params.billId 账单id
   * return
   */
  getReceiptRecord: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceipt/getReceiptRecord', params })
  },

  /**
   * 生成收据编号
   * /bus/orgBillReceipt/getReceiptNumber?buildId=楼宇Id
   * @param params.buildId 楼宇Id
   * return
   */
  getReceiptNumber: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceipt/getReceiptNumber', params })
  },

  /** 校验是否可开据
   * /bus/orgBillReceipt/isCheckReceipt?billId
   * @param params.billId 账单id
   * return
   */
  isCheckReceipt: async (params: any) => {
    return await request.delete({ url: '/bus/orgBillReceipt/isCheckReceipt', params })
  },

  /**
   * 收据详情
   * /admin-api/bus/orgBillReceipt/get
   * @param params.id 收据id
   * return
   */
  get: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceipt/get', params })
  },

  /**
   * 收据发出
   * /admin-api/bus/orgBillReceipt/send
   * @param params.id 收据id
   * return
   */
  send: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceipt/send', params })
  },

  /**
   * 收据生成
   * /admin-api/bus/orgBillReceipt/generate
   * @param params.id 收据id
   * @param params.applyTemplateId 收据模板id,示例值(1)
   * return
   */
  generate: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceipt/generate', params })
  },

  /**
   * 收据回收
   *  /admin-api/bus/orgBillReceipt/recovery
   * @param params.id 收据id
   * return
   */
  recovery: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceipt/recovery', params })
  },

  /**
   * 收据作废
   * /admin-api/bus/orgBillReceipt/toVoid
   * @param params.id 收据id
   * return
   */
  toVoid: async (params: any) => {
    return await request.get({ url: '/bus/orgBillReceipt/toVoid', params })
  },

  /**
   * 收据导入模版
   * /admin-api/bus/orgBillReceipt/get-import-template
   * return
   */
  getImportTemplate: async () => {
    return await request.get({ url: '/bus/orgBillReceipt/get-import-template' })
  },

  /** 收据导入
   *  /admin-api/bus/orgBillReceipt/import
   * @param data.file 导入文件
   * return
   */
  import: async (data: any) => {
    return await request.post({ url: '/bus/orgBillReceipt/import', data })
  },
  // 查询机构账单收据分页
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgBillReceipt/page`, params })
  },
  // 修改机构账单收据
  update: async (data: ReceiptVO) => {
    return await request.put({ url: `/bus/orgBillReceipt/update`, data })
  },

  // 删除机构账单收据
  delete: async (id: number) => {
    return await request.delete({ url: `/bus/orgBillReceipt/delete?id=` + id })
  },

  // 导出机构账单收据 Excel
  export: async (params) => {
    return await request.download({ url: `/bus/orgBillReceipt/export-excel`, params })
  },
  /**
   * 财务-收据记录分页-顶部统计
   * /admin-api/bus/orgBillReceipt/getTopMoney
   * return
   */
  getTopMoney: async () => {
    return await request.get({ url: '/bus/orgBillReceipt/getTopMoney' })
  }
}
