import request from '@/config/axios'

// 资产清单操作记录

// 资产清单操作记录 API
export const PropertyOperateLogApi = {
  /**
   * 获得资产操作日志分页
   * /admin-api/bus/property-operate-log/page
   * @param params.pageNo 当前页码,示例值(1)
   * @param params.pageSize 每页条数,示例值(10)
   * @returns
   */
  getPropertyOperateLogPage: async function (params: any) {
    return await request.get({ url: `/bus/property-operate-log/page`, params })
  }
}
