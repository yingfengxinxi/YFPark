/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
const { wsCache } = useCache()
const config: {
  base_url: string
  result_code: number | string
  default_headers: AxiosHeaders
  request_timeout: number
  deptIdList: any
} = {
  /**
   * api请求基础路径
   */
  base_url: import.meta.env.VITE_BASE_URL + import.meta.env.VITE_API_URL,
  /**
   * 接口成功返回状态码
   */
  result_code: 200,

  /**
   * 接口请求超时时间
   */
  request_timeout: 30000,

  /**
   * 默认接口请求类型
   * 可选值：application/x-www-form-urlencoded multipart/form-data
   */
  default_headers: 'application/json',

  // 项目类型
  deptIdList: wsCache.get(CACHE_KEY.DEPTID_LIST) || ''
}

export { config }
