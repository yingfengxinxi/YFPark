<template>
  <div>
    <el-row :gutter="18">
      <el-col :span="6">
        <div class="bg-white h-120px rounded">
          <div class="flex justify-between items-center h-100% p-14px box-border pos-relative">
            <div class="flex flex-col justify-between h-100%">
              <div class="text-14px text-#999"> 任务完成数 </div>
              <div class="text-18px font-600"> {{ data.overTaskCount }} </div>
              <div class="text-12px text-gray"> 完成率 {{ data.overTaskRate }}% </div>
            </div>
            <div class="text-center h-100%">
              <div class="w-64px h-64px bg-blue rounded pos-relative">
                <Icon
                  icon="ep:checked"
                  color="#fff"
                  :size="40"
                  class="pos-absolute pos-top-50% pos-left-38% transform-translate--50%"
                />
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="bg-white h-120px rounded">
          <div class="flex justify-between items-center h-100% p-14px box-border pos-relative">
            <div class="flex flex-col justify-between h-100%">
              <div class="text-14px text-#999"> 累计发现异常 </div>
              <div class="text-18px font-600"> {{ data.abnormalCount }} </div>
              <div class="text-12px text-gray"> 异常率 {{ data.abnormalRate }}% </div>
            </div>
            <div class="text-center h-100%">
              <div class="w-64px h-64px bg-blue rounded pos-relative">
                <Icon
                  icon="ep:failed"
                  color="#fff"
                  :size="40"
                  class="pos-absolute pos-top-50% pos-left-38% transform-translate--50%"
                />
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="bg-white h-120px rounded">
          <div class="flex justify-between items-center h-100% p-14px box-border pos-relative">
            <div class="flex flex-col justify-between h-100%">
              <div class="text-14px text-#999"> 超时任务数 </div>
              <div class="text-18px font-600"> {{ data.timeoutTaskCount }} </div>
              <div class="text-12px text-gray"> 超时率 {{ data.timeoutTaskRate }}% </div>
            </div>
            <div class="text-center h-100%">
              <div class="w-64px h-64px bg-blue rounded pos-relative">
                <Icon
                  icon="ep:clock"
                  color="#fff"
                  :size="40"
                  class="pos-absolute pos-top-50% pos-left-38% transform-translate--50%"
                />
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="bg-white h-120px rounded">
          <div class="flex justify-between items-center h-100% p-14px box-border pos-relative">
            <div class="flex flex-col justify-between h-100%">
              <div class="text-14px text-#999"> 整改完成率 </div>
              <div class="text-18px font-600"> {{ data.rectificationRate + '%' }} </div>
              <div class="text-12px text-gray"> 跳过率 {{ data.timeoutBdTaskRate }}% </div>
            </div>
            <div class="text-center h-100%">
              <div class="w-64px h-64px bg-blue rounded pos-relative">
                <Icon
                  icon="ep:list"
                  color="#fff"
                  :size="40"
                  class="pos-absolute pos-top-50% pos-left-38% transform-translate--50%"
                />
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" setup>
const props = defineProps({
  data: {
    type: Object,
    default: () => {}
  }
})
</script>
