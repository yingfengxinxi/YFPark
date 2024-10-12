import request from '@/config/axios'
export const orgRemarkApi = {
  /**
   * 租客账单->详情->备注列表 (管理后台 - 机构业务备注 - 获得机构业务备注分页)
   * /bus/orgRemark/page
   *  @param {string} params.businessId 业务id;合同id/账单id/等等
   *  @param {string} params.type 业务类型1账单备注
   *  @param {string} params.pageNo 页码
   *  @param {string} params.pageSize 每页条数
   */
  getPage: async (params: any) => {
    return await request.get({ url: `/bus/orgRemark/page`, params })
  },
  /**
   * 租客账单->详情->备注新增 (管理后台 - 机构业务备注 - 创建机构业务备注 )
   * /admin-api/bus/orgRemark/create
   * @data {string} data.businessId 业务id;合同id/账单id/等等
   * @data {string} data.type 业务类型1账单备注
   * @data {string} data.remark 备注
   */
  create: async (data: any) => {
    return await request.post({ url: `/bus/orgRemark/create`, data })
  },
  /**
   * 租客账单->详情->备注删除 (管理后台 - 机构业务备注 - 删除机构业务备注 )
   * /admin-api/bus/orgRemark/delete
   * @data {string} data.id 备注id
   */
  delete: async (params: any) => {
    return await request.delete({ url: `/bus/orgRemark/delete`, params })
  },
  /**
   * 租客账单->详情->备注编辑 (管理后台 - 机构业务备注 - 编辑机构业务备注 )
   * /admin-api/bus/orgRemark/update
   * @data {string} data.id 备注id
   * @data {string} data.remark 备注
   * @data {string} data.businessId 业务id;合同id/账单id/等等
   * @data {string} data.type 业务类型1账单备注
   */
  update: async (data: any) => {
    return await request.put({ url: `/bus/orgRemark/update`, data })
  },
  /**
   * 租客账单->详情->备注详情 (管理后台 - 机构业务备注 - 机构业务备注详情 )
   * /admin-api/bus/orgRemark/get
   * @data {string} data.id 备注id
   */
  getOrgRemark: async (params: any) => {
    return await request.get({ url: `/bus/orgRemark/get`, params })
  }
}
