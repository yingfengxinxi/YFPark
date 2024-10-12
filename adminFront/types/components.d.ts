/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
declare module 'vue' {
  export interface GlobalComponents {
    Icon: (typeof import('@/components/Icon'))['Icon']
    DictTag: (typeof import('@/components/DictTag'))['DictTag']
    DictText: (typeof import('@/components/DictText'))['DictText']
    TagIdArr: (typeof import('@/components/TagIdArr'))['TagIdArr']
    TagIdArrList: (typeof import('@/components/TagIdArr'))['TagIdArrList']
  }
}

export {}
