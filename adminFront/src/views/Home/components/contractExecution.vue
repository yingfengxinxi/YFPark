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
  <div class="shortcut-container w-100%">
    <ContentWrap class="!m-0">
      <div class="flex justify-between items-center mb-20px">
        <div class="title flex items-center text-18px">
          <img
            src="@/assets/imgs/home/contractExecution.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>合同执行</span>
        </div>
        <el-button link type="primary" class="!c-#0C69F7" @click="toContract()"> 更多 </el-button>
      </div>
      <el-row :gutter="15" justify="space-between">
        <el-col :span="6">
          <div class="flex items-center">
            <img src="@/assets/imgs/home/numIcon.png" class="w-50px h-50px mr-5px" alt="" />
            <div class="flex-1">
              <div class="">数量</div>
              <div class="item-num c-#2EC4B1 text-22px"
                >{{ contractExecutionInfo.undisposedNum || 0 }}
                <span class="c-#979CAA text-14px fw-400"> 个 </span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="flex items-center">
            <img src="@/assets/imgs/home/onGong.png" class="w-50px h-50px mr-5px" alt="" />
            <div class="flex-1">
              <div class="">执行中</div>
              <div class="item-num c-#0089FF text-22px"
                >{{ contractExecutionInfo.onGongNum || 0 }}
                <span class="c-#979CAA text-14px fw-400"> 个 </span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="flex items-center">
            <img
              src="@/assets/imgs/home/rentHasBeenWithdrawn.png"
              class="w-50px h-50px mr-5px"
              alt=""
            />
            <div class="flex-1">
              <div class="">已退租</div>
              <div class="item-num c-#FFC75B text-22px"
                >{{ contractExecutionInfo.rentHasBeenWithdrawnNum || 0 }}
                <span class="c-#979CAA text-14px fw-400"> 个 </span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="flex items-center">
            <img src="@/assets/imgs/home/Expired.png" class="w-50px h-50px mr-5px" alt="" />
            <div class="flex-1">
              <div class="">已到期</div>
              <div class="item-num c-#8276FE text-22px"
                >{{ contractExecutionInfo.ExpiredNum || 0 }}
                <span class="c-#979CAA text-14px fw-400"> 个 </span>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
defineOptions({ name: 'SmartDevice' })
const router = useRouter() // 路由
const props = defineProps({
  contractExecutionInfo: {
    type: Array,
    default: function () {
      return []
    }
  }
})
watch(
  () => props.contractExecutionInfo,
  (newVal) => {
    console.log(newVal, '999')
  }
)
const toContract = () => {
  router.push({ path: '/contract/contractList' })
}
</script>
<style lang="scss" scoped>
// ::v-deep .el-card__body {
//   padding: 18px 20px !important;
// }
</style>
