/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import request from '@/config/axios'
import { url } from 'inspector'

// BPM 流程监听器 API
export const simpleWorkflowApi = {
  /**
   * 获取角色
   * @param {*} data
   * @returns
   */
  getRoles: async (params: any) => {
    return await request.get({ url: `/roles.json`, params })
  },

  /**
   * 获取部门
   * @param {*} data
   * @returns
   */
  getDepartments: async (params: any) => {
    return await request.get({ url: `/departments.json`, params })
  },

  /**
   * 获取职员
   * @param {*} data
   * @returns
   */
  getEmployees: async (params: any) => {
    return await request.get({ url: `/employees.json`, params })
  },
  /**
   * 获取条件字段
   * @param {*} data
   * @returns
   */
  getConditions: async (params: any) => {
    return await request.get({ url: `/conditions.json`, params })
  },

  /**
   * 获取审批数据
   * @param {*} data
   * @returns
   */
  // export function getWorkFlowData(data) {
  getWorkFlowData: async (params: any) => {
    return await request.get({ url: `/data.json`, params })
  },
  /**
   * 设置审批数据
   * @param {*} data
   * @returns
   */
  setWorkFlowData: async (data: any) => {
    return await request.post({ url: `/`, data })
  }
}
