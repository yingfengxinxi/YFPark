/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */

import { ComponentStyle, DiyComponent } from '@/components/DiyEditor/util'
import { cloneDeep } from 'lodash-es'

/** 列表导航属性 */
export interface MenuListProperty {
  // 导航菜单列表
  list: MenuListItemProperty[]
  // 组件样式
  style: ComponentStyle
}

/** 列表导航项目属性 */
export interface MenuListItemProperty {
  // 图标链接
  iconUrl: string
  // 标题
  title: string
  // 标题颜色
  titleColor: string
  // 副标题
  subtitle: string
  // 副标题颜色
  subtitleColor: string
  // 链接
  url: string
}

export const EMPTY_MENU_LIST_ITEM_PROPERTY = {
  title: '标题',
  titleColor: '#515a6e',
  subtitle: '副标题',
  subtitleColor: '#bbb'
}

// 定义组件
export const component = {
  id: 'MenuList',
  name: '列表导航',
  icon: 'fa-solid:list',
  property: {
    list: [cloneDeep(EMPTY_MENU_LIST_ITEM_PROPERTY)],
    style: {
      bgType: 'color',
      bgColor: '#fff',
      marginBottom: 8
    } as ComponentStyle
  }
} as DiyComponent<MenuListProperty>
