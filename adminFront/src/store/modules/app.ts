import { defineStore } from 'pinia'
import { store } from '../index'
import { setCssVar, humpToUnderline } from '@/utils'
import { ElMessage } from 'element-plus'
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
import { ElementPlusSize } from '@/types/elementPlus'
import { LayoutType } from '@/types/layout'
import { ThemeTypes } from '@/types/theme'
import { set } from 'nprogress'

const { wsCache } = useCache()

interface AppState {
  breadcrumb: boolean
  breadcrumbIcon: boolean
  collapse: boolean
  uniqueOpened: boolean
  hamburger: boolean
  screenfull: boolean
  search: boolean
  message: boolean
  tagsView: boolean
  tagsViewIcon: boolean
  logo: boolean
  ShowTwoMenu: boolean
  fixedHeader: boolean
  greyMode: boolean
  pageLoading: boolean
  layout: LayoutType
  title: string
  userInfo: string
  isDark: boolean
  currentSize: ElementPlusSize
  sizeMap: ElementPlusSize[]
  mobile: boolean
  footer: boolean
  theme: ThemeTypes
  fixedMenu: boolean
  tableId: string
  isTried: boolean
  BillListColumn: any[]
}

export const useAppStore = defineStore('app', {
  state: (): AppState => {
    return {
      tableId: '',
      isTried: false,
      userInfo: 'userInfo', // 登录信息存储字段-建议每个项目换一个字段，避免与其他项目冲突
      sizeMap: ['default', 'large', 'small'],
      mobile: false, // 是否是移动端
      title: import.meta.env.VITE_APP_TITLE, // 标题
      pageLoading: false, // 路由跳转loading
      ShowTwoMenu: false, //是否存在二级导航
      breadcrumb: true, // 面包屑
      breadcrumbIcon: true, // 面包屑图标
      collapse: false, // 折叠菜单
      uniqueOpened: true, // 是否只保持一个子菜单的展开
      hamburger: true, // 折叠图标
      screenfull: true, // 全屏图标
      search: true, // 搜索图标
      message: true, // 消息图标
      tagsView: true, // 标签页
      tagsViewIcon: true, // 是否显示标签图标
      logo: true, // logo
      fixedHeader: true, // 固定toolheader
      footer: false, // 显示页脚
      greyMode: false, // 是否开始灰色模式，用于特殊悼念日
      fixedMenu: wsCache.get('fixedMenu') || false, // 是否固定菜单

      layout: wsCache.get(CACHE_KEY.LAYOUT) || 'topLeftBottom', // layout布局
      isDark: wsCache.get(CACHE_KEY.IS_DARK) || false, // 是否是暗黑模式
      currentSize: wsCache.get('default') || 'default', // 组件尺寸
      theme: wsCache.get(CACHE_KEY.THEME) || {
        // 主题色
        elColorPrimary: '#409eff',
        // 左侧菜单边框颜色
        leftMenuBorderColor: 'inherit',
        // 左侧菜单背景颜色
        leftMenuBgColor: '#fff',
        // 左侧菜单浅色背景颜色
        leftMenuBgLightColor: '#fff',
        // 左侧菜单选中背景颜色
        // leftMenuBgActiveColor: 'var(--el-color-primary)',
        // 顶部一级菜单选中背景颜色
        topHeaderBgActiveColor: 'var(--el-color-primary)',
        // 左侧菜单收起选中背景颜色
        leftMenuCollapseBgActiveColor: 'var(--el-color-primary)',
        // 左侧菜单字体颜色
        leftMenuTextColor: '#515a6e',
        // 左侧菜单选中字体颜色
        leftMenuTextActiveColor: 'var(--el-color-primary)',
        // 顶部一级菜单选中字体颜色
        topHeaderTextActiveColor: '#fff',
        // logo字体颜色
        logoTitleTextColor: '#fff',
        // logo边框颜色
        logoBorderColor: 'inherit',
        // 头部背景颜色
        topHeaderBgColor: '#1d42ab, #2173dc, #1e93ff',
        // 头部字体颜色
        topHeaderTextColor: 'hsla(0,0%,100%,0.7)',
        // 头部悬停颜色
        topHeaderHoverColor: '#f6f6f6',
        // 头部边框颜色
        topToolBorderColor: '#eee'
      },
      BillListColumn: wsCache.get(CACHE_KEY.BillListColumn) || [
        {
          label: '对方名称',
          show: true,
          disabled: true,
          default: true,
          value: 'ownerName'
        },
        {
          label: '楼宇名称',
          show: true,
          disabled: true,
          default: true,
          value: 'buildName'
        },
        {
          label: '合同编号',
          show: true,
          disabled: false,
          default: true,
          value: 'contractNumber'
        },
        // {
        //   label: '跟进人',
        //   show: true,
        //   disabled: true
        // },
        // {
        //   label: '支付方联系方式',
        //   show: true,
        //   disabled: true
        // },
        {
          label: '结清状态',
          show: true,
          disabled: false,
          default: true,
          value: 'billStatusName'
        },
        {
          label: '费用类型',
          show: true,
          disabled: false,
          default: true,
          value: 'feeTypeName'
        },
        {
          label: '租赁数',
          show: true,
          disabled: false,
          default: true,
          value: 'leaseArea'
        },
        {
          label: '缴费通知单',
          show: true,
          disabled: false,
          default: true,
          value: 'noticeCount'
        },
        {
          label: '账单来源',
          show: true,
          disabled: false,
          default: true,
          value: 'billSourceName'
        },
        {
          label: '账单类型',
          show: true,
          disabled: false,
          default: true,
          value: 'billTypeName'
        },
        // {
        //   label: '账单金额',
        //   show: true,
        //   disabled: true
        // },
        {
          label: '应收金额',
          show: true,
          disabled: false,
          default: true,
          value: 'receivable'
        },
        {
          label: '实收金额',
          show: true,
          disabled: false,
          default: true,
          value: 'receivablePayment'
        },
        // {
        //   label: '需收金额',
        //   show: true,
        //   disabled: true
        // },
        // {
        //   label: '税率',
        //   show: true,
        //   disabled: true
        // },
        {
          label: '税额',
          show: true,
          disabled: false,
          default: true,
          value: 'taxAmount'
        },
        // {
        //   label: '币种',
        //   show: true,
        //   disabled: true
        // },
        {
          label: '逾期天数',
          show: true,
          disabled: false,
          default: true,
          value: 'overdueDay'
        },
        {
          label: '开始日期',
          show: true,
          disabled: false,
          default: true,
          value: 'payStartDate'
        },
        {
          label: '结束时间',
          show: true,
          disabled: false,
          default: true,
          value: 'payEndDate'
        },
        {
          label: '应收日期',
          show: true,
          disabled: false,
          default: true,
          value: 'payDate'
        },
        {
          label: '调增金额',
          show: true,
          disabled: false,
          default: true,
          value: 'jiaAdjustPrice'
        },
        {
          label: '调减金额',
          show: true,
          disabled: false,
          default: true,
          value: 'jianAdjustPrice'
        },
        // {
        //   label: '转出金额',
        //   show: true,
        //   disabled: true
        // },
        // {
        //   label: '转入金额',
        //   show: true,
        //   disabled: true
        // },
        {
          label: '应收滞纳金金额',
          show: false,
          disabled: false,
          default: false,
          value: 'lateFee'
        }
      ]
    }
  },
  getters: {
    getBreadcrumb(): boolean {
      return this.breadcrumb
    },
    getBreadcrumbIcon(): boolean {
      return this.breadcrumbIcon
    },
    getCollapse(): boolean {
      return this.collapse
    },
    getUniqueOpened(): boolean {
      return this.uniqueOpened
    },
    getHamburger(): boolean {
      return this.hamburger
    },
    getScreenfull(): boolean {
      return this.screenfull
    },
    getMessage(): boolean {
      return this.message
    },
    getTagsView(): boolean {
      return this.tagsView
    },
    getTagsViewIcon(): boolean {
      return this.tagsViewIcon
    },
    getLogo(): boolean {
      return this.logo
    },
    getShowTwoMenu(): boolean {
      return this.ShowTwoMenu
    },
    getFixedHeader(): boolean {
      return this.fixedHeader
    },
    getGreyMode(): boolean {
      return this.greyMode
    },
    getFixedMenu(): boolean {
      return this.fixedMenu
    },
    getPageLoading(): boolean {
      return this.pageLoading
    },
    getLayout(): LayoutType {
      return this.layout
    },
    getTitle(): string {
      return this.title
    },
    getUserInfo(): string {
      return this.userInfo
    },
    getIsDark(): boolean {
      return this.isDark
    },
    getCurrentSize(): ElementPlusSize {
      return this.currentSize
    },
    getSizeMap(): ElementPlusSize[] {
      return this.sizeMap
    },
    getMobile(): boolean {
      return this.mobile
    },
    getTheme(): ThemeTypes {
      return this.theme
    },
    getFooter(): boolean {
      return this.footer
    },
    getBillListColumn(): any {
      return this.BillListColumn
    }
  },
  actions: {
    setTableId(payload) {
      this.tableId = payload
    },
    setIsTried(payload) {
      this.isTried = payload
    },
    setBreadcrumb(breadcrumb: boolean) {
      this.breadcrumb = breadcrumb
    },
    setBreadcrumbIcon(breadcrumbIcon: boolean) {
      this.breadcrumbIcon = breadcrumbIcon
    },
    setCollapse(collapse: boolean) {
      this.collapse = collapse
    },
    setUniqueOpened(uniqueOpened: boolean) {
      this.uniqueOpened = uniqueOpened
    },
    setHamburger(hamburger: boolean) {
      this.hamburger = hamburger
    },
    setScreenfull(screenfull: boolean) {
      this.screenfull = screenfull
    },
    setMessage(message: boolean) {
      this.message = message
    },
    setTagsView(tagsView: boolean) {
      this.tagsView = tagsView
    },
    setTagsViewIcon(tagsViewIcon: boolean) {
      this.tagsViewIcon = tagsViewIcon
    },
    setLogo(logo: boolean) {
      this.logo = logo
    },
    setShowTwoMenu(ShowTwoMenu: boolean) {
      this.ShowTwoMenu = ShowTwoMenu
    },
    setFixedHeader(fixedHeader: boolean) {
      this.fixedHeader = fixedHeader
    },
    setGreyMode(greyMode: boolean) {
      this.greyMode = greyMode
    },
    setFixedMenu(fixedMenu: boolean) {
      wsCache.set('fixedMenu', fixedMenu)
      this.fixedMenu = fixedMenu
    },
    setPageLoading(pageLoading: boolean) {
      this.pageLoading = pageLoading
    },
    setLayout(layout: LayoutType) {
      if (this.mobile && layout !== 'classic') {
        ElMessage.warning('移动端模式下不支持切换其他布局')
        return
      }
      this.layout = layout
      wsCache.set(CACHE_KEY.LAYOUT, this.layout)
    },
    setTitle(title: string) {
      this.title = title
    },
    setIsDark(isDark: boolean) {
      this.isDark = isDark
      if (this.isDark) {
        document.documentElement.classList.add('dark')
        document.documentElement.classList.remove('light')
      } else {
        document.documentElement.classList.add('light')
        document.documentElement.classList.remove('dark')
      }
      wsCache.set(CACHE_KEY.IS_DARK, this.isDark)
    },
    setCurrentSize(currentSize: ElementPlusSize) {
      this.currentSize = currentSize
      wsCache.set('currentSize', this.currentSize)
    },
    setMobile(mobile: boolean) {
      this.mobile = mobile
    },
    setTheme(theme: ThemeTypes) {
      this.theme = Object.assign(this.theme, theme)
      wsCache.set(CACHE_KEY.THEME, this.theme)
    },
    setCssVarTheme() {
      for (const key in this.theme) {
        setCssVar(`--${humpToUnderline(key)}`, this.theme[key])
      }
    },
    setFooter(footer: boolean) {
      this.footer = footer
    },
    setBillListColumn(payload: any) {
      this.BillListColumn = payload
      wsCache.set(CACHE_KEY.BillListColumn, payload)
    }
  },
  persist: false
})

export const useAppStoreWithOut = () => {
  return useAppStore(store)
}
