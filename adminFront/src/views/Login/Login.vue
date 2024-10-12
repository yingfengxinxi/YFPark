<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->

<template>
  <div
    :class="prefixCls"
    class="relative h-[100%] lt-md:px-10px lt-sm:px-10px lt-xl:px-10px lt-xl:px-10px bg"
  >
    <!-- 左上角的 logo + 系统标题 -->
    <div class="absolute flex items-center text-white pt-6 pl-6">
      <img alt="" class="mr-10px h-48px w-48px" src="@/assets/imgs/logoColor.png" />
      <span class="text-28px font-bold">{{ underlineToHump(appStore.getTitle) }}</span>
    </div>
    <div class="mx-auto h-full flex flex-justify-center">
      <div class="relative flex-1 p-30px dark:bg-[var(--login-bg-color)] lt-sm:p-10px">
        <!-- 右上角的主题、语言选择 -->
        <!-- <div
          class="flex items-center justify-between text-white at-2xl:justify-end at-xl:justify-end"
        >
          <div class="flex items-center at-2xl:hidden at-xl:hidden">
            <img alt="" class="mr-10px h-48px w-48px" src="@/assets/imgs/logo.png" />
            <span class="text-20px font-bold">{{ underlineToHump(appStore.getTitle) }}</span>
          </div>
          <div class="flex items-center justify-end space-x-10px">
            <ThemeSwitch />
            <LocaleDropdown class="dark:text-white lt-xl:text-white" />
          </div>
        </div> -->
        <!-- 右边的登录界面 -->
        <Transition appear enter-active-class="animate__animated animate__bounceInRight">
          <div
            class="m-auto h-full w-[100%] flex items-center at-2xl:max-w-500px at-lg:max-w-500px at-md:max-w-500px at-xl:max-w-500px"
          >
            <!-- 账号登录 -->
            <LoginForm
              class="m-auto h-auto p-20px lt-xl:(rounded-3xl light:bg-white) bg-[#fff] border-rd-2"
            />
            <!-- 手机登录 -->
            <MobileForm
              class="m-auto h-auto p-20px lt-xl:(rounded-3xl light:bg-white) bg-[#fff] border-rd-2"
            />
            <!-- 二维码登录 -->
            <!-- <QrCodeForm class="m-auto h-auto p-20px lt-xl:(rounded-3xl light:bg-white)" /> -->
            <!-- 注册 -->
            <RegisterForm
              class="m-auto h-auto p-20px lt-xl:(rounded-3xl light:bg-white) bg-[#fff] border-rd-2"
            />
            <!-- 三方登录 -->
            <SSOLoginVue
              class="m-auto h-auto p-20px lt-xl:(rounded-3xl light:bg-white) bg-[#fff] border-rd-2"
            />
          </div>
        </Transition>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { underlineToHump } from '@/utils'

import { useDesign } from '@/hooks/web/useDesign'
import { useAppStore } from '@/store/modules/app'
import { ThemeSwitch } from '@/layout/components/ThemeSwitch'
import { LocaleDropdown } from '@/layout/components/LocaleDropdown'

import { LoginForm, MobileForm, QrCodeForm, RegisterForm, SSOLoginVue } from './components'

defineOptions({ name: 'Login' })

const { t } = useI18n()
const appStore = useAppStore()
const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('login')
</script>

<style lang="scss" scoped>
$prefix-cls: #{$namespace}-login;

.#{$prefix-cls} {
  overflow: auto;

  &__left {
    &::before {
      position: absolute;
      top: 0;
      left: 0;
      z-index: -1;
      width: 100%;
      height: 100%;
      background-image: url('@/assets/svgs/login-bg.svg');
      background-position: center;
      background-repeat: no-repeat;
      content: '';
    }
  }
}

.bg {
  background: url('@/assets/imgs/bg.jpg');
}
</style>
