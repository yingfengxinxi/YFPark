<script lang="tsx">
import { PropType } from 'vue'
import { ElMenu, ElScrollbar } from 'element-plus'
import { useAppStore } from '@/store/modules/app'
import { usePermissionStore } from '@/store/modules/permission'
import { useRenderMenuItem } from './components/useRenderMenuItem'
import { isUrl } from '@/utils/is'
import { useDesign } from '@/hooks/web/useDesign'
import { LayoutType } from '@/types/layout'
import { getLastPath } from '@/utils/routerHelper'

const { getPrefixCls } = useDesign()

const prefixCls = getPrefixCls('menu')
export default defineComponent({
  // eslint-disable-next-line vue/no-reserved-component-names
  name: 'Menu',
  props: {
    menuSelect: {
      type: Function as PropType<(index: string, go: string) => void>,
      default: undefined
    }
  },
  setup(props) {
    const appStore = useAppStore()

    const layout = computed(() => appStore.getLayout)

    const { push, currentRoute, router } = useRouter()

    const permissionStore = usePermissionStore()

    console.log(router)

    const menuMode = computed((): 'vertical' | 'horizontal' => {
      // 竖
      const vertical: LayoutType[] = ['classic', 'topLeft', 'cutMenu']

      if (vertical.includes(unref(layout))) {
        return 'vertical'
      } else {
        return 'horizontal'
      }
    })

    const routers = computed(() =>
      unref(layout) === 'cutMenu' ? permissionStore.getMenuTabRouters : permissionStore.getRouters
    )

    const collapse = computed(() => appStore.getCollapse)

    const uniqueOpened = computed(() => appStore.getUniqueOpened)

    const activeMenu = computed(() => {
      const { meta, path, matched } = unref(currentRoute)
      // if set path, the sidebar will highlight the path you set
      console.log(meta, matched, 'meta', currentRoute)
      appStore.setShowTwoMenu(
        matched[0].children && matched[0].meta.hidden != true && matched[0].path != '/'
          ? true
          : false
      )
      if (meta.activeMenu) {
        return meta.activeMenu as string
      }
      return matched[0].path == '/' ? '/index' : matched[0].path
    })

    const menuSelect = (index: string, indexPath, item) => {
      console.log(index, indexPath, item, uniqueOpened.value)

      if (props.menuSelect) {
        props.menuSelect(index)
      }
      // 自定义事件
      if (isUrl(index)) {
        window.open(index)
      } else {
        const routeArray = routers.value.filter((v) => !v.meta?.hidden)
        const ArrayIndex = routeArray.findIndex((item) => item.path === index)
        const toPath =
          index == '/index' ? index : getLastPath(routeArray[ArrayIndex].children, index)
        console.log(toPath, 'toPath')
        push(toPath)
      }
    }

    const renderMenuWrap = () => {
      if (unref(layout) === 'top' || unref(layout) === 'topLeftBottom') {
        return renderMenu()
      } else {
        return <ElScrollbar>{renderMenu()}</ElScrollbar>
      }
    }

    const renderMenu = () => {
      return (
        <ElMenu
          defaultActive={unref(activeMenu)}
          mode={unref(menuMode)}
          collapse={
            unref(layout) === 'top' ||
            unref(layout) === 'cutMenu' ||
            unref(layout) === 'topLeftBottom'
              ? false
              : unref(collapse)
          }
          uniqueOpened={
            unref(layout) === 'topLeftBottom' || unref(layout) === 'top'
              ? false
              : unref(uniqueOpened)
          }
          backgroundColor="none"
          textColor="var(--top-header-text-color)"
          activeTextColor="var(--top-header-hover-color)"
          activeTextW
          onSelect={menuSelect}
        >
          {{
            default: () => {
              const { renderMenuItem } = useRenderMenuItem(unref(menuMode))
              return renderMenuItem(unref(routers))
            }
          }}
        </ElMenu>
      )
    }

    return () => (
      <div
        id={prefixCls}
        class={[
          `${prefixCls} ${prefixCls}__${unref(menuMode)}`,
          'h-[100%] overflow-hidden flex-col bg-[var(--top-menu-bg-color)]',
          {
            'w-[var(--left-menu-min-width)]': unref(collapse) && unref(layout) !== 'cutMenu',
            'w-[var(--left-menu-max-width)]': !unref(collapse) && unref(layout) !== 'cutMenu'
          }
        ]}
      >
        {renderMenuWrap()}
      </div>
    )
  }
})
</script>

<style lang="scss" scoped>
$prefix-cls: #{$namespace}-menu;

.#{$prefix-cls} {
  position: relative;
  transition: width var(--transition-time-02);

  :deep(.#{$elNamespace}-menu) {
    width: 100% !important;
    border-right: none;

    // 设置选中时子标题的颜色
    .is-active {
      & > .#{$elNamespace}-sub-menu__title {
        color: var(--top-header-text-active-color) !important;
      }
    }

    // 设置子菜单悬停的高亮和背景色
    .#{$elNamespace}-sub-menu__title,
    .#{$elNamespace}-menu-item {
      font-size: 15px;
      &:hover {
        color: var(--top-header-text-active-color) !important;
        background: var(--top-header-bg-color) !important;
      }
    }

    // 设置选中时的高亮背景和高亮颜色
    .#{$elNamespace}-menu-item.is-active {
      color: var(--top-header-text-active-color) !important;
      background-color: var(--top-header-bg-active-color) !important;
      font-weight: bolder;
      &:hover {
        font-weight: bolder;
        // background-color: var(--top-header-bg-active-color) !important;
      }
    }

    .#{$elNamespace}-menu-item.is-active {
      position: relative;
    }

    // 设置子菜单的背景颜色
    .#{$elNamespace}-menu {
      .#{$elNamespace}-sub-menu__title,
      .#{$elNamespace}-menu-item:not(.is-active) {
        background-color: var(--top-header-bg-light-color) !important;
      }
    }
  }

  // 折叠时的最小宽度
  :deep(.#{$elNamespace}-menu--collapse) {
    width: var(--left-menu-min-width);

    & > .is-active,
    & > .is-active > .#{$elNamespace}-sub-menu__title {
      position: relative;
      background-color: var(--left-menu-collapse-bg-active-color) !important;
    }
    // 设置子菜单的背景颜色
    .#{$elNamespace}-menu {
      background: red !important;
      .#{$elNamespace}-sub-menu__title,
      .#{$elNamespace}-menu-item:not(.is-active) {
        background-color: var(--left-menu-bg-light-color) !important;
      }
    }
  }

  // 折叠动画的时候，就需要把文字给隐藏掉
  :deep(.horizontal-collapse-transition) {
    // transition: 0s width ease-in-out, 0s padding-left ease-in-out, 0s padding-right ease-in-out !important;
    .#{$prefix-cls}__title {
      display: none;
    }
  }

  // 水平菜单
  &__horizontal {
    height: calc(var(--top-tool-height)) !important;

    :deep(.#{$elNamespace}-menu--horizontal) {
      height: calc(var(--top-tool-height));
      border-bottom: none;
      // 重新设置底部高亮颜色
      & > .#{$elNamespace}-sub-menu.is-active {
        .#{$elNamespace}-sub-menu__title {
          border-bottom-color: var(--el-color-primary) !important;
        }
      }

      .#{$elNamespace}-menu-item.is-active {
        position: relative;
        color: var(--el-color-primary) !important;
        background: var(--el-color-primary-light-9) !important;
        border-bottom-color: var(--el-color-primary) !important;

        &::after {
          display: none !important;
        }
      }

      .#{$prefix-cls}__title {
        /* stylelint-disable-next-line */
        max-height: calc(var(--top-tool-height) - 2px) !important;
        /* stylelint-disable-next-line */
        line-height: calc(var(--top-tool-height) - 2px);
      }
    }
  }
}
</style>

<style lang="scss">
$prefix-cls: #{$namespace}-menu-popper;

.el-popper {
  .el-menu {
    background: #2082ec !important;
    .el-menu-item {
      background: #2082ec !important;
      color: #bfcbd9;
      &:hover {
        color: #fff;
      }
    }

    .is-active {
      color: #fff;
      font-weight: bolder;
    }
  }
}

.#{$prefix-cls}--vertical,
.#{$prefix-cls}--horizontal {
  // 设置选中时子标题的颜色
  .is-active {
    & > .el-sub-menu__title {
      color: var(--top-header-text-active-color) !important;
    }
  }

  // 设置子菜单悬停的高亮和背景色
  .el-sub-menu__title,
  .el-menu-item {
    &:hover {
      color: var(--top-header-text-active-color) !important;
      background-color: var(--top-header-bg-color) !important;
    }
  }

  // 设置选中时的高亮背景
  .el-menu-item.is-active {
    position: relative;
    background-color: var(--top-header-active-color) !important;

    // &:hover {
    //   background-color: var(--top-header-bg-active-color) !important;
    // }
  }
}
</style>
