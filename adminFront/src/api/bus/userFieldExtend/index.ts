import request from '@/config/axios'

// 用户扩展信息自定义系统设置 VO
export interface UserFieldExtendVO {
  id: number // 编号
  orgId: number // 机构id
  villageId: number // 项目id
  sysFieldId: number // 关联 village_user_system_field_extend id
  fieldName: string // 字段名称
  inputName: string // 表单名称
  inputNameCus: string // 表单名称自定义
  inputType: string // 输入框类型
  defaultInputValue: string // 表单默认值
  defaultFieldValue: string // 表单默认值
  isEnable: number // 是否启用，1-启用,0-禁用
  isRequired: number // 是否必填 1-必填
  isAllowModifie: number // 是否允许修改表单名称，1允许，0不允许
  jsonFields: string // JSON 字段集合
  sort: number // 排序
}

// 用户扩展信息自定义系统设置 API
export const UserFieldExtendApi = {
  // 查询用户扩展信息自定义系统设置分页
  getUserFieldExtendPage: async (params: any) => {
    return await request.get({
      url: `/bus/user-field-extend/getUserFieldExtendList`,
      params
    })
  },

  // 查询用户扩展信息自定义系统设置详情
  getUserFieldExtend: async (id: number) => {
    return await request.get({ url: `/bus/user-field-extend/get?id=` + id })
  },

  // 新增用户扩展信息自定义系统设置
  createUserFieldExtend: async (data: UserFieldExtendVO) => {
    return await request.post({ url: `/bus/user-field-extend/create`, data })
  },

  // 修改用户扩展信息自定义系统设置
  updateUserFieldExtend: async (data: UserFieldExtendVO) => {
    return await request.put({ url: `/bus/user-field-extend/update`, data })
  },

  // 删除用户扩展信息自定义系统设置
  deleteUserFieldExtend: async (id: number) => {
    return await request.delete({ url: `/bus/user-field-extend/delete?id=` + id })
  },

  // 导出用户扩展信息自定义系统设置 Excel
  exportUserFieldExtend: async (params) => {
    return await request.download({ url: `/bus/user-field-extend/export-excel`, params })
  },
  // 查询用户扩展信息自定义系统设置列表
  getUserFieldExtendList: async (params: any) => {
    return await request.get({
      url: `/bus/user-field-extend/getUserFieldExtendList`,
      params
    })
  },
  //创建用户拓展
  createUserFieldExtendList: async (data: any) => {
    return await request.post({ url: `/bus/user-extends/create`, data })
  },
  // 获取用户拓展
  getUserid: async (params: any) => {
    return await request.get({
      url: `/bus/user-extends/get`,
      params
    })
  },
  //更改用户信息扩展
  updateUserFieldExtendList: async (data: any) => {
    return await request.put({ url: `/bus/user-extends/update`, data })
  }
}
