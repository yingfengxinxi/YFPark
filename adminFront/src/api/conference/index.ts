import request from '@/config/axios'
export const conferenceApi = {
  // 获取预约设置
  getsetting: async (params: any) => {
    return await request.get({ url: `/bus/resv-application/list`, params })
  },
  //更新预约设置
  updatesetting: async (data: any) => {
    return await request.put({ url: `/bus/resv-application/update`, data })
  },
  //获取计费规则列表
  resvbillrulepage: async (params: any) => {
    return await request.get({ url: `/bus/resv-bill-rule/page`, params })
  },
  //删除计费规则列表
  resvbillruledelete: async (id: any) => {
    return await request.delete({ url: `/bus/resv-bill-rule/delete?id=` + id })
  },
  //新增计费规则列表
  resvbillrulecreate: async (data: any) => {
    return await request.post({ url: `/bus/resv-bill-rule/create`, data })
  },
  //通过id获取计费规则列表
  resvbillruleget: async (id: any) => {
    return await request.get({ url: `/bus/resv-bill-rule/get?id=` + id })
  },
  //编辑计费规则列表
  resvbillruleupdate: async (data: any) => {
    return await request.put({ url: `/bus/resv-bill-rule/update`, data })
  },
  // 创建会议室分类
  createcategory: async (data: any) => {
    return await request.post({ url: `/bus/resv-place-category/create`, data })
  },
  //获得会议室分类列表
  getcategoryType: async (params: any) => {
    return await request.get({
      url: `/bus/resv-place-category/page`,
      params
    })
  },
  //删除会议室分类
  deletecategory: async (id: any) => {
    return await request.delete({
      url: `/bus/resv-place-category/delete?id=` + id
    })
  },
  // 通过id获取会议室分类
  getcategory: async (id: any) => {
    return await request.get({ url: `/bus/resv-place-category/get?id=` + id })
  },
  //编辑会议室分类
  updatecategory: async (data: any) => {
    return await request.put({ url: `/bus/resv-place-category/update`, data })
  },
  //获取预约会议室列表
  getresvplace: async (params: any) => {
    return await request.get({ url: `/bus/resv-booking/page`, params })
  },
  //获取退款处理
  getrefund: async (params: any) => {
    return await request.get({ url: `/bus/resv-booking-order-refund/page`, params })
  },
  //获得核销分页
  getverification: async (params: any) => {
    return await request.get({ url: `/bus/resv-booking-verification/page`, params })
  },
  //获取预约会议室详情
  getresvplaceDetail: async (id: any) => {
    return await request.get({ url: `/bus/resv-booking/get?id=` + id })
  },
  //创建会议室
  createresvplace: async (data: any) => {
    return await request.post({ url: `/bus/resv-place/create`, data })
  },
  //获取计费规则列表
  getresvbillrule: async (params: any) => {
    return await request.get({ url: `/bus/resv-bill-rule/list`, params })
  },
  //获得会议室列表
  getresvplaceList: async (params: any) => {
    return await request.get({ url: `/bus/resv-place/page`, params })
  },
  //删除会议室
  deleteresvplace: async (id: any) => {
    return await request.delete({ url: `/bus/resv-place/delete?id=` + id })
  },
  //编辑会议室
  updateresvplace: async (data: any) => {
    return await request.put({ url: `/bus/resv-place/update`, data })
  },
  //查找会议室列表详情
  getresvplaceListId: async (id: any) => {
    return await request.get({ url: `/bus/resv-place/get?id=` + id })
  },
  //获得会议室详情,通用
  getresvplaceDetailId: async (id: any) => {
    return await request.get({ url: `/bus/resv-pay-order/findOneBookingDetil?id=` + id })
  },
  //会议室退款审批
  updateresvplaceRefund: async (data: any) => {
    return await request.post({ url: `/bus/resv-pay-order/processRefundResvPayOrder`, data })
  },
}
