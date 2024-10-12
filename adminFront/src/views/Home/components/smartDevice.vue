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
      <div class="flex justify-between items-center mb-17px">
        <div class="title flex items-center text-18px">
          <img
            src="@/assets/imgs/home/smartDevice.png"
            class="mr-15px w-30px h-30px text-18rpx fw-500"
            alt=""
            srcset=""
          />
          <span>智能设备</span>
        </div>
        <el-button link type="primary" class="!c-#0C69F7"> 更多 </el-button>
      </div>
      <div class="device-box">
        <div class="select-list">
          <div
            class="select-item"
            :class="activeSelect === index + 1 ? 'active-select' : ''"
            v-for="(item, index) in smartDeviceList"
            :key="index"
            @click="changeType(index)"
          >
            {{ item.name }}
          </div>
        </div>
        <div class="select-view">
          <div class="list-item" v-for="(item, index) in viewList" :key="index">
            <a-tooltip placement="top" :title="item.num">
              <div class="flex items-center">
                <img :src="getAssetsFile(item.img)" class="w-50px h-50px mr-18px" alt="" />
                <div>
                  <div class="list-dec">{{ item.title }}</div>
                  <div class="item-num" :style="{ color: item.color }"
                    >{{ item.num }}
                    <span class="c-#979CAA text-14px fw-400"> 个 </span>
                  </div>
                </div>
              </div>
            </a-tooltip>
          </div>
        </div>
      </div>
    </ContentWrap>
  </div>
</template>
<script lang="ts" setup>
import { getAssetsFile } from '@/utils/index'
defineOptions({ name: 'SmartDevice' })
const props = defineProps({
  smartDeviceList: {
    type: Array,
    default: function () {
      return []
    }
  }
})
const viewList = ref([
  {
    title: '数量',
    num: 0,
    img: 'home/numIcon.png',
    color: '#f7b500'
  },
  {
    title: '在线',
    num: 0,
    img: 'home/onlineTotalIcon.png',
    color: '#3ac240'
  },
  {
    title: '离线',
    num: 0,
    img: 'home/offlineTotalIcon.png',
    color: '#f44745'
  }
])
const activeSelect = ref(1)

const getData = (indexs) => {
  viewList.value.forEach((item, index) => {
    if (item.title === '数量') {
      item.num = props.smartDeviceList[indexs].list[index]?.num || 0
    }
    if (item.title === '在线') {
      item.num = props.smartDeviceList[indexs].list[index]?.num || 0
    }
    if (item.title === '离线') {
      item.num = props.smartDeviceList[indexs].list[index]?.num || 0
    }
  })
}
watch(
  () => props.smartDeviceList,
  (newVal) => {
    console.log(newVal, '999')
    getData(0)
  }
)
const changeType = (index: number) => {
  activeSelect.value = index + 1
  getData(index)
}
</script>
<style lang="scss" scoped>
::v-deep .el-card__body {
  padding: 18px 20px !important;
}
.device-box {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
.select-list {
  width: 100%;
  height: 30px;
  background-color: #f7f7f7;
  border-radius: 30px;
  display: flex;
  .select-item {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
}
.active-select {
  background-color: #2b7aff;
  color: #ffffff;
  border-radius: 30px;
}
.select-view {
  flex: 1;
  padding: 17px 20px;
  display: flex;
  justify-content: space-between;
  padding-bottom: 0px;
  .list-item {
    display: flex;
    max-width: 33%;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;

    .item-num {
      font-size: 22px;
      max-width: 100%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      word-break: break-all;
    }
  }
}
</style>
