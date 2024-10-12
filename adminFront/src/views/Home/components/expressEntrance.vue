<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="shortcut-container">
    <ContentWrap class="!m-0">
      <div class="flex justify-between items-center mb-18px">
        <div class="title flex items-center text-18px">
          <img
            src="@/assets/imgs/home/expressEntrance.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>快捷入口</span>
        </div>
        <el-button link type="primary" class="!c-#0C69F7"> 更多 </el-button>
      </div>
      <el-row :gutter="20" justify="space-between">
        <el-col :span="4" v-for="(item, index) in shortcut" :key="index">
          <el-link type="default" :underline="false" @click="setPush(item.url)">
            <div class="flex justify-between flex-col items-center">
              <img :src="getAssetsFile(item.icon)" class="w-50px h-50px m-b-8px" alt="" />
              <div
                class="c-#333333 h-24px lh-24px fw-400 text-ellipsis overflow-hidden whitespace-nowrap"
                >{{ item?.name }}</div
              >
            </div>
          </el-link>
        </el-col>
      </el-row>
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
import { getAssetsFile } from '@/utils/index'
import { propertyOf } from 'lodash-es'
import { useWatermark } from '@/hooks/web/useWatermark'

const router = useRouter() // 路由

defineOptions({ name: 'Shortcut' })
const props = defineProps({
  shortcut: {
    type: Array,
    default: function () {
      return []
    }
  }
})
watch(
  () => props.shortcut,
  (newVal) => {}
)
const setPush = (url) => {
  router.push({ path: url })
}
</script>
<style scoped lang="scss">
::v-deep .el-card__body {
  padding-bottom: 11px !important;
}
</style>
