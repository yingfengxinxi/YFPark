/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'

// 项目用户/租客 VO
export interface VillageUserVO {
  id: number // 编号
  uniqidId: string // 人员针对房间的唯一编号
  userId: number // 用户表ID，可能为空
  ownerId: number // 归属租客ID
  buildId: number // 楼栋ID
  zoneId: number // 分区ID
  villageId: number // 项目ID
  name: string // 人员名称（128位加密）
  phone: string // 人员手机号（128位加密）
  email: string // 住户联系邮箱
  address: string // 通讯地址
  isDefault: number // 是否为默认租客住户联系人
  type: number // 0+房主，10+家属，20+租户，30+物业人员，40+服务人员，50+访客
  effectiveTimeStart: Date // 有效期开始（特别指访客）
  effectiveTimeEnd: Date // 有效期结束（特别指访客）
  idcardType: string // 证件类型（大陆身份证、港澳台、护照等）
  idcard: string // 证件号（128位加密）
  birthYear: number // 出生年（年月日尽量从身份证中获取）
  birthMonth: number // 出生月（年月日尽量从身份证中获取）
  birthDay: number // 出生日（年月日尽量从身份证中获取）
  sex: number // 性别（1男，2女，0未知）（年月日尽量从身份证中获取）
  eduType: number // 学历信息
  workYear: string // 工作年限
  gradSchool: string // 毕业院校
  skCert: string // 技能证书
  idcardImg: string // 身份证照片(包含手持身份证图片){"front_url":"","back_url":"","hand_url":""}
  photo: string // 照片网址（128加密）,后期若有多个用英文逗号分割
  photoStatus: number // 照片数据状态（0+正常，10+审核中，20+失败）
  photoStatusDesc: number // 照片状态的描述文本（一般用于状态失败原因描述）
  inAdvance: number // 是否拥有代付权限
  inAdvanceNotice: number // 是否代付通知
  status: number // 数据状态（0审核中，1正常，4拒绝）
  lastChooseTime: Date // 租客端最后选择此身份的时间
}

// 项目用户/租客 API
export const VillageUserApi = {
  // 查询项目用户/租客分页
  getVillageUserPage: async (params: any) => {
    return await request.get({ url: `/bus/village-user/page`, params })
  },

  // 查询项目用户/租客详情
  getVillageUser: async (id: number) => {
    console.log(id, 'ids')
    return await request.get({ url: `/bus/village-user/get?id=` + id })
  },

  // 新增项目用户/租客
  createVillageUser: async (data: VillageUserVO) => {
    return await request.post({ url: `/bus/village-user/create`, data })
  },

  // 修改项目用户/租客
  updateVillageUser: async (data: VillageUserVO) => {
    return await request.put({ url: `/bus/village-user/update`, data })
  },

  // 删除项目用户/租客
  deleteVillageUser: async (id: number) => {
    return await request.delete({ url: `/bus/village-user/delete?id=` + id })
  },

  //根据姓名获取租客信息
  getOwnerListByName: async (name: string) => {
    return await request.get({ url: `/bus/owner/getOwnerListByName?name=` + name })
  },
  // 获取租客员工模板
  get_import_template: async () => {
    return await request.download({ url: `/bus/village-user/get-import-template` })
  },
  //上传租客员工文件
  Fileimport: async (data) => {
    return await request.upload({ url: `/bus/village-user/import`, data })
  },
  //获得IC卡列表
  getIcCardList: async (params: any) => {
    return await request.get({ url: `/bus/user-ic-card/page`, params })
  },
  // 通过villageUserId获得用户信息扩展
  getUserExtendsByUser: async (params: any) => {
    return await request.get({ url: `/bus/user-extends/getUserExtendsByUser`, params })
  }
}
