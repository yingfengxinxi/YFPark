import request from '@/config/axios'

// 资产盘点记录 VO
export interface PropertyInventoryRecordVO {
  id: number // 编号
  orgId: number // 机构id
  inventoryId: number // 盘点清单id
  propertyId: number // 资产id
  typeId: number // 资产分类id
  status: number // 0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束
  propertyStatus: number // 资产状态
  isRange: number // 是否在盘点范围之内
  isUpdate: number // 是否修改资产信息 0否 1是
  remark: string // 盘点备注
  image: string // 盘点图片
  tag: string // 盘点标签
  inventoryTime: Date // 盘点时间
  inventoryUid: string // 盘点人
  positionId: number // 当前位置
  handleUid: number // 处理人
  departmentId: number // 资产所属部门id
  propertyInfo: string // 资产盘点信息
  name: string // 资产名称
  propertyNumber: string // 资产编码
}

// 资产盘点记录 API
export const PropertyInventoryRecordApi = {
  // 查询资产盘点记录分页
  getPropertyInventoryRecordPage: async (params: any) => {
    return await request.get({ url: `/bus/property-inventory-record/page`, params })
  },

  // 查询资产盘点记录详情
  getPropertyInventoryRecord: async (id: number) => {
    return await request.get({ url: `/bus/property-inventory-record/get?id=` + id })
  },

  // 新增资产盘点记录
  createPropertyInventoryRecord: async (data: PropertyInventoryRecordVO) => {
    return await request.post({ url: `/bus/property-inventory-record/create`, data })
  },

  // 修改资产盘点记录
  updatePropertyInventoryRecord: async (data: PropertyInventoryRecordVO) => {
    return await request.put({ url: `/bus/property-inventory-record/update`, data })
  },

  // 删除资产盘点记录
  deletePropertyInventoryRecord: async (id: number) => {
    return await request.delete({ url: `/bus/property-inventory-record/delete?id=` + id })
  },

  // 导出资产盘点记录 Excel
  exportPropertyInventoryRecord: async (params) => {
    return await request.download({ url: `/bus/property-inventory-record/export-excel`, params })
  },
}
