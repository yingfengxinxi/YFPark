<template>
  <div class="digital-overview">
    <ContentWrap>
      <div class="flex justify-between items-center mb-18px">
        <div class="title flex items-center text-18px">
          <img
            src="@/assets/imgs/home/digitalOverviewIcon.png"
            class="mr-15px w-30px h-30px fw-500"
            alt=""
            srcset=""
          />
          <span>数字概览</span>
        </div>
        <el-button link type="primary" class="!c-#0C69F7 text-14px"> 更多 </el-button>
      </div>
      <el-row :gutter="20" justify="space-between">
        <el-col :span="6" class="mb-20px">
          <div
            class="bg-#E4F0FE relative b-rd-10px p-16px p-t-14px p-b-14px text-left h-130px box-border"
          >
            <div class="flex justify-between flex-col h-100%">
              <div class="c-#333333 text-16px lh-22px fw-400">目前已到期应收（元）</div>
              <div class="c-#333333 text-22px lh-29px fw-500 text-ellipsis overflow-hidden"
                >￥{{ diaitalOverview?.dueReceivable }}</div
              >
              <div class="">
                <el-button link type="primary" class="!c-#0C69F7 text-14px"> 详情> </el-button>
              </div>
            </div>
            <img
              src="@/assets/imgs/home/digitalOverviewBg1.png"
              class="w-52px h-33px absolute pos-right-0 pos-bottom-0"
              alt=""
            /> </div></el-col
        ><el-col :span="6" class="mb-20px">
          <div
            class="bg-#E0F8EB relative b-rd-10px p-16px p-t-14px p-b-14px text-left h-130px box-border"
          >
            <div class="flex justify-between flex-col h-100%">
              <div class="c-#333333 text-16px lh-22px fw-400">目前已到期应支（元）</div>
              <div class="c-#333333 text-22px lh-29px fw-500 text-ellipsis overflow-hidden"
                >￥{{ diaitalOverview?.dueAndPayable }}</div
              >
              <div class="">
                <el-button link type="primary" class="!c-#0C69F7 text-14px"> 详情> </el-button>
              </div>
            </div>
            <img
              src="@/assets/imgs/home/digitalOverviewBg2.png"
              class="w-52px h-33px absolute pos-right-0 pos-bottom-0"
              alt=""
            /> </div></el-col
        ><el-col :span="6" class="mb-20px">
          <div
            class="bg-#FFF4E8 relative b-rd-10px p-16px p-t-14px p-b-14px text-left h-130px box-border"
          >
            <div class="flex justify-between flex-col h-100%">
              <div class="c-#333333 text-16px lh-22px fw-400">未来30天应收（元）</div>
              <div class="c-#333333 text-22px lh-29px fw-500 text-ellipsis overflow-hidden"
                >￥{{ diaitalOverview?.futureReceivables }}</div
              >
              <div class="">
                <el-button link type="primary" class="!c-#0C69F7 text-14px"> 详情> </el-button>
              </div>
            </div>
            <img
              src="@/assets/imgs/home/digitalOverviewBg3.png"
              class="w-52px h-33px absolute pos-right-0 pos-bottom-0"
              alt=""
            /> </div></el-col
        ><el-col :span="6" class="mb-20px">
          <div
            class="bg-#FEE9E6 relative b-rd-10px p-16px p-t-14px p-b-14px text-left h-130px box-border"
          >
            <div class="flex justify-between flex-col h-100%">
              <div class="c-#333333 text-16px lh-22px fw-400">未来30天应支（元）</div>
              <div class="c-#333333 text-22px lh-29px fw-500 text-ellipsis overflow-hidden"
                >￥{{ diaitalOverview?.futureExpenditures }}</div
              >
              <div class="">
                <el-button link type="primary" class="!c-#0C69F7 text-14px"> 详情> </el-button>
              </div>
            </div>
            <img
              src="@/assets/imgs/home/digitalOverviewBg4.png"
              class="w-52px h-33px absolute pos-right-0 pos-bottom-0"
              alt=""
            /> </div></el-col
        ><el-col :span="6">
          <div
            class="bg-#FFF4E8 relative b-rd-10px p-16px p-t-14px p-b-14px text-left h-130px box-border"
          >
            <div class="flex justify-between flex-col h-100%">
              <div class="c-#333333 text-16px lh-22px fw-400">已逾期租客欠款（元）</div>
              <div class="c-#333333 text-22px lh-29px fw-500 text-ellipsis overflow-hidden"
                >￥{{ diaitalOverview?.werePastDue }}</div
              >
              <div class="">
                <el-button link type="primary" class="!c-#0C69F7 text-14px"> 详情> </el-button>
              </div>
            </div>
            <img
              src="@/assets/imgs/home/digitalOverviewBg5.png"
              class="w-52px h-33px absolute pos-right-0 pos-bottom-0"
              alt=""
            /> </div></el-col
        ><el-col :span="6">
          <div
            class="bg-#F8F4FF relative b-rd-10px p-16px p-t-14px p-b-14px text-left h-130px box-border"
          >
            <div class="flex justify-between flex-col h-100%">
              <div class="c-#333333 text-16px lh-22px fw-400">目前租客已到期（元）</div>
              <div class="c-#333333 text-22px lh-29px fw-500 text-ellipsis overflow-hidden"
                >￥{{ diaitalOverview?.hasExpired }}</div
              >
              <div class="">
                <el-button link type="primary" class="!c-#0C69F7 text-14px"> 详情> </el-button>
              </div>
            </div>
            <img
              src="@/assets/imgs/home/digitalOverviewBg6.png"
              class="w-52px h-33px absolute pos-right-0 pos-bottom-0"
              alt=""
            />
          </div> </el-col
        ><el-col :span="6">
          <div
            class="bg-#E4F0FE relative b-rd-10px p-16px p-t-14px p-b-14px text-left h-130px box-border"
          >
            <div class="flex justify-between flex-col h-100%">
              <div class="c-#333333 text-16px lh-22px fw-400">今日其他应收（元）</div>
              <div class="c-#333333 text-22px lh-29px fw-500 text-ellipsis overflow-hidden"
                >￥{{ diaitalOverview?.receivableToday }}</div
              >
              <div class="">
                <el-button link type="primary" class="!c-#0C69F7 text-14px"> 详情> </el-button>
              </div>
            </div>
            <img
              src="@/assets/imgs/home/digitalOverviewBg1.png"
              class="w-52px h-33px absolute pos-right-0 pos-bottom-0"
              alt=""
            /> </div></el-col
        ><el-col :span="6">
          <div
            class="bg-#E0F8EB relative b-rd-10px p-16px p-t-14px p-b-14px text-left h-130px box-border"
          >
            <div class="flex justify-between flex-col h-100%">
              <div class="c-#333333 text-16px lh-22px fw-400">今日其他应支（元）</div>
              <div class="c-#333333 text-22px lh-29px fw-500 text-ellipsis overflow-hidden"
                >￥{{ diaitalOverview?.shouldBePaidToday }}</div
              >
              <div class="">
                <el-button link type="primary" class="!c-#0C69F7 text-14px"> 详情> </el-button>
              </div>
            </div>
            <img
              src="@/assets/imgs/home/digitalOverviewBg2.png"
              class="w-52px h-33px absolute pos-right-0 pos-bottom-0"
              alt=""
            />
          </div>
        </el-col>
      </el-row>
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
defineOptions({ name: 'DiaitalOverview' })
const props = defineProps({
  diaitalOverview: {
    type: Object,
    default: function () {
      return {}
    }
  }
})

watch(
  () => props.diaitalOverview,
  (newValue) => {}
)
</script>
