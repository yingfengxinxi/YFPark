/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 资产管理 VO
export interface PropertyVO {
  id: number // 编号
  orgId: number // 机构ID
  propertyNumber: string // 资产编码
  labelLink: string // 资产标签链接
  type: number // 资产分类
  name: string // 资产名称
  status: number // 资产状态
  brand: string // 品牌
  modelName: string // 型号
  deviceCode: string // 设备序列号
  processCode: string // 流程编号
  adminId: number // 管理员id
  adminUid: number // 管理员uid
  villageId: number // 目标房源项目id
  buildId: number // 目标房源楼宇id
  roomId: string // 目标房源房间id
  buildBind: string // 绑定的目标房源json
  positionId: number // 所在位置配置信息id
  positionName: string // 资产操作时的位置信息
  purchaseTime: Date // 购置时间
  purchaseType: number // 购置方式
  purchaseAmount: number // 购置金额(含税)
  stockTime: Date // 入库时间
  expectMonths: number // 预计使用期限(月)
  remark: string // 备注
  imageHash: string // 图片hash值
  imageUrl: string // 资产图片url
  userId: number // 使用人
  departmentId: number // 使用部门id
  receiveTime: Date // 领用日期
  maintainTime: Date // 保养到期时间
  maintainInfo: string // 保养说明
  depreciationMonths: number // 预计折旧期限(月)
  cuserUid: number // 操作人uid
  muserUid: number // 修改人uid
  knowledgeBase: string // 所属资产的知识库
}

// 资产管理 API
export const PropertyApi = {
  // 查询资产管理分页
  getPropertyPage: async (params: any) => {
    return await request.get({ url: `/bus/property/page`, params })
  },

  // 查询资产管理详情
  getProperty: async (id: number) => {
    return await request.get({ url: `/bus/property/get?id=` + id })
  },

  // 新增资产管理
  createProperty: async (data: PropertyVO) => {
    return await request.post({ url: `/bus/property/create`, data })
  },

  // 修改资产管理
  updateProperty: async (data: PropertyVO) => {
    return await request.put({ url: `/bus/property/update`, data })
  },

  // 删除资产管理
  deleteProperty: async (id: number) => {
    return await request.delete({ url: `/bus/property/delete?id=` + id })
  },

  // 导出资产管理 Excel
  exportProperty: async (params) => {
    return await request.download({ url: `/bus/property/export-excel`, params })
  },
  //设备巡检绑定资产弹窗资产列表
  getCheckPropertyPage: async (params: any) => {
    return await request.get({ url: `/bus/property/getBindAssetsPropertyPage`, params })
  },
  //获取资产分类树
  getPropertyTypeTree: async (params: any) => {
    return await request.post({ url: `/bus/property-category/getTree`, params })
  },
  getPropertyById: async (id: number) => {
    return await request.get({ url: `/bus/property-category/get?id=` + id })
  },

  /** 派发 / 退库 申请
   * /admin-api/bus/property/createHandoutProperty
   * @param data.id 资产id
   * @param data.type 1: 派发 2: 退库
   * @param data.propertyIds 资产id集合json,示例值(1,2,3)
   * @param data.orgId 机构id,示例值(12362)
   * return
   */
  createHandoutProperty: async (data: any) => {
    return await request.post({ url: `/bus/property/createHandoutProperty`, data })
  },

  /**  借出/归还 申请
   * /admin-api/bus/property/createLendoutProperty
   * @param data.id 资产id
   * @param data.type 1=借出;2=归还,示例值(1)
   * @param data.operateUid 处理人,示例值(10181)
   * @param data.lendUid 借用人uid,示例值(7141)
   * @param data.propertyIds 资产id集合json,示例值(1,2,3)
   * @param data.orgId 机构id,示例值(12362)
   * return
   */
  createLendoutProperty: async (data: any) => {
    return await request.post({ url: `/bus/property/createLendoutProperty`, data })
  },

  /**
   * 资产调拨申请
   * /admin-api/bus/property/createTransferProperty
   * @param data.outAdminUid 调出管理员uid,示例值(27333)
   * @param data.inAdminUid 调入管理员uid,示例值(27333)
   * @param data.inLocationId  调入位置id,示例值(18013)
   * @param data.propertyIds 资产id集合json,示例值(1,2,3)
   * @param data.remark 备注,示例值(备注)
   * @returns
   */
  createTransferProperty: async function (data: any) {
    return await request.post({ url: `/bus/property/createTransferProperty`, data })
  },
  /** 资产处置申请
   *  /admin-api/bus/property/createHandleProperty
   * @param data.departmentId 发起部门id,示例值(10382)
   * @param data.propertyIds propertyIds
   * @param data.status 单据状态;1=进行中,示例值(2)
   * @param data.handleAmount 处置金额
   * @param data.handleExpenses 处置费用
   * @param data.handleType 处置类型,示例值(1)
   * @param data.remark 处置原因,示例值(你说的对)
   * @returns
   */
  createHandleProperty: async function (data: any) {
    return await request.post({ url: `/bus/property/createHandleProperty`, data })
  },

  /**
   * 批量资产管理
   * /admin-api/bus/property/createListProperty
   * @returns
   */
  createListProperty: async function (data: any) {
    return await request.post({ url: `/bus/property/createListProperty`, data })
  },
  /** 根据审批流程id获取分页
   * /admin-api/bus/property/getPropertyPageByApprove
   * @param params.approveNumber 审批流程id,示例值(1)
   * @returns
   */
  getPropertyPageByApprove: async function (params: any) {
    return await request.get({ url: `/bus/property/getPropertyPageByApprove`, params })
  },
  /** 变更领用人申请
   * /admin-api/bus/property/createChangeProperty
   * @param data.propertyIds 资产id集合json,示例值(1,2,3)
   * @param data.afterUseUid 新的领用人,示例值(10181)
   * @param data.afterUseDepartmentId 变更后使用部门id,示例值(20832)
   * @param data.operateUid 处理人,示例值(21094)
   * @param data.status 单据状态;1=进行中,示例值(2)
   * @param data.remark 备注,示例值(备注)
   * @returns
   */
  createChangeProperty: async function (data: any) {
    return await request.post({ url: `/bus/property/createChangeProperty`, data })
  },
  /** 资产维修申请
   * /admin-api/bus/property/createRepairProperty
   * @param data.propertyIds 资产id集合json,示例值(1,2,3)
   * @param data.operateUid 处理人,示例值(21094)
   * @param data.status 单据状态;1=进行中,示例值(2)
   * @param data.repairDepartmentId 报修部门id,示例值(9700)
   * @param data.repairUid	 报修人uid,示例值(15497)
   * @param data.repairTime 报修时间
   * @param data.repairReason 报修原因,示例值(不好)
   * @param data.expectRepairPrice 预计维修金额,示例值(8161)
   * @param data.repairContent 维修内容
   * @returns
   */
  createRepairProperty: async function (data: any) {
    return await request.post({ url: `/bus/property/createRepairProperty`, data })
  },
  /** 资产保养申请
   * /admin-api/bus/property/createMaintainProperty
   * @param data.propertyIds 资产id集合json,示例值(1,2,3)
   * @param data.operateUid 处理人,示例值(21094)
   * @param data.status 单据状态;1=进行中,示例值(2)
   * @param data.maintainDepartmentId 保养部门id,示例值(9700)
   * @param data.maintainUid 保养人uid,示例值(15497)
   * @param data.maintainTime 保养时间
   * @param data.maintainReason 保养原因,示例值(不好)
   * @param data.expectMaintainPrice 预计保养金额,示例值(8161)
   * @param data.maintainContent 保养内容
   * @returns
   */
  createMaintainProperty: async function (data: any) {
    return await request.post({ url: `/bus/property/createMaintainProperty`, data })
  },

  /** 资产概况-工作台
   * /admin-api/bus/property/getAllProperty
   * @param params.type type=1统计数量，type=2统计金额
   * @returns
   */
  getAllProperty: async (params: any) => {
    return await request.get({ url: `/bus/property/getAllProperty`, params })
  },
  /** 我的资产统计
   * /admin-api/bus/property/getMyProperty
   * returns
   */
  getMyProperty: async function () {
    return await request.post({ url: `/bus/property/getMyProperty` })
  },
  /** 近30日需归还资产
   * /admin-api/bus/property/returnPropertyPage
   * returns
   */
  returnPropertyPage: async function () {
    return await request.post({ url: `/bus/property/returnPropertyPage` })
  }
}
