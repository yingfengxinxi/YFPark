import request from '@/config/axios'

// 自定义抄表计划 API
export const TablemanageApi = {
  getPage: async (params: any) => {
    return await request.get({ url: `bus/energy/page`, params })
  },
  //新增页面获取关联分表
  getenergyList: async (params: any) => {
    return await request.get({ url: `bus/energy/getList`, params })
  },
  //创建表管理
  createTablemanage: async (data: any) => {
    return await request.post({ url: `bus/energy/create`, data })
  },
  //获得抄表记录模板
  getTableRecordTemplate: async (params: any) => {
    return await request.download({ url: `bus/energyRecord/get-import-template`, params })
  },
  //导入抄表记录
  importTableRecord: async (data: any) => {
    return await request.upload({ url: `bus/energyRecord/import`, data })
  },
  //获得Excel模板
  getExcelTemplate: async (params: any) => {
    return await request.download({ url: `bus/energy/get-import-template`, params })
  },
  //导入Excel文件
  importExcel: async (data: any) => {
    return await request.upload({ url: `bus/energy/import`, data })
  },
  //抄表功能绑定资产设备
  bindAssetList: async (params: any) => {
    return await request.get({ url: `bus/property/getAssetManagementBindingDevicePage`, params })
  },
  //批量添加表管理
  batchCreateTablemanage: async (data: any) => {
    return await request.post({ url: `bus/energy/batchCreate`, data })
  },
  //获取表管理详情
  getTablemanageDetail: async (params: any) => {
    return await request.get({ url: `bus/energy/get`, params })
  },
  /**
   * 读表记录
   */
  //表管理详情-读表记录
  getcallbackRecordPage: async (params: any) => {
    return await request.get({ url: `bus/energyCallbackRecord/callbackRecordPage`, params })
  },
  //表管理详情-立即读表
  callbackNow: async (params: any) => {
    return await request.get({ url: `bus/energyCallbackRecord/read`, params })
  },
  /**
   * 操作记录
   */
  getRecords: async (params: any) => {
    return await request.get({
      url: `bus/hydropowerOperateRecord/page`,
      params
    })
  }
}
