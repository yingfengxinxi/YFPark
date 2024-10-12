<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->

<script lang="tsx">
import { defineComponent, computed } from 'vue'
import { Message } from '@/layout/components//Message'
import { Collapse } from '@/layout/components/Collapse'
// import { UserInfo } from '@/layout/components/UserInfo'
import { AminSet } from '@/layout/components/AdminSet'
import { SetDropdown } from '@/layout/components/SetDropdown'
import { Screenfull } from '@/layout/components/Screenfull'
import { Breadcrumb } from '@/layout/components/Breadcrumb'
import RouterSearch from '@/components/RouterSearch/index.vue'
import { useAppStore } from '@/store/modules/app'
import { useDesign } from '@/hooks/web/useDesign'

const { getPrefixCls, variables } = useDesign()

const prefixCls = getPrefixCls('tool-header')

const appStore = useAppStore()

// 面包屑
const breadcrumb = computed(() => appStore.getBreadcrumb)

// 折叠图标
const hamburger = computed(() => appStore.getHamburger)

// 全屏图标
const screenfull = computed(() => appStore.getScreenfull)

// 渐变主题图标颜色
const topHeaderBgColor = computed(() => appStore.getTheme.topHeaderBgColor)

// 搜索图片
const search = computed(() => appStore.search)

// 布局
const layout = computed(() => appStore.getLayout)

// 消息图标
const message = computed(() => appStore.getMessage)

export default defineComponent({
  name: 'ToolHeader',
  setup() {
    return () => (
      <div
        id={`${variables.namespace}-tool-header`}
        class={[
          prefixCls,
          'h-[var(--top-tool-height)] relative px-[var(--top-tool-p-x)] flex items-center justify-between',
          'dark:bg-[var(--el-bg-color)]'
        ]}
      >
        {layout.value !== ('top' && 'topLeftBottom') ? (
          <div class="h-full flex items-center">
            {hamburger.value && layout.value !== 'cutMenu' ? (
              <Collapse
                class={['custom-hover', { 'gradient-hover': topHeaderBgColor.value.length > 13 }]}
                color="var(--top-header-text-color)"
              ></Collapse>
            ) : undefined}
            {breadcrumb.value ? (
              <Breadcrumb
                class={['lt-md:hidden', { 'gradient-hover': topHeaderBgColor.value.length > 13 }]}
              ></Breadcrumb>
            ) : undefined}
          </div>
        ) : undefined}
        <div class="h-full flex items-center">
          {screenfull.value ? (
            <Screenfull
              class={['custom-hover', { 'gradient-hover': topHeaderBgColor.value.length > 13 }]}
              color="var(--top-header-text-color)"
            ></Screenfull>
          ) : undefined}
          {search.value ? (
            <RouterSearch
              isModal={false}
              color="var(--top-header-text-color)"
              class={[{ 'gradient-hover': topHeaderBgColor.value.length > 13 }]}
            />
          ) : undefined}
          {message.value ? (
            <Message
              class={['custom-hover', { 'gradient-hover': topHeaderBgColor.value.length > 13 }]}
              color="var(--top-header-text-color)"
            ></Message>
          ) : undefined}
          <AminSet class={[{ 'gradient-hover': topHeaderBgColor.value.length > 13 }]}></AminSet>
        </div>
      </div>
    )
  }
})
</script>

<style lang="scss" scoped>
$prefix-cls: #{$namespace}-tool-header;

.#{$prefix-cls} {
  transition: left var(--transition-time-02);
}
</style>
