<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap class="m-10px" v-loading="loading">
    <template v-if="layerRespVOS.length && !loading">
      <div class="flex-row flex flex-wrap">
        <div
          class="item w-a flex flex-center flex-items-center font-size-12px border-1px b-solid border-#0000001a p-4px b-rd-20px c-#000000a6 p-l-10px p-r-10px m-r-10px m-b-10px cursor-pointer"
          v-for="(item, index) in roomStatusColor"
          :key="index"
        >
          <div
            class="w-16px h-16px m-r-10px"
            :style="{
              background: item.color
            }"
          ></div>
          {{ item.title }}
        </div>
      </div>
      <div>
        <el-row
          v-for="(item, index) in layerRespVOS"
          :key="index"
          class="p-t-5px p-b-5px"
          :gutter="10"
        >
          <el-col :span="3" class="bg-#f7f7f7 !flex flex-col justify-center flex-items-center">
            <div class="font-size-15px c-#000000a6 text-center font-bold">{{ item.layerName }}</div>
            <div class="font-size-12px c-#000000a6 text-center m-t-10px"
              >{{ item.rentalAreaAll }}㎡</div
            >
          </el-col>
          <el-col :span="21">
            <div class="room-right">
              <div
                class="room-box"
                v-for="(room, index) in item.roomRespVO"
                :key="index"
                :style="'width:' + (room.areaRatio || 10) + '%;'"
              >
                <el-tooltip>
                  <template #content>
                    <!--   -->
                    <div v-if="room.is_rent">
                      <!-- <div>{{ room.tenant_name }}</div> -->
                      <div>{{ room.roomName }}({{ room.buildArea }}㎡)</div>
                      <!-- <div>{{ getExpireTimeTxt(room) }}</div> -->
                    </div>
                    <div v-else>
                      <div>
                        <span style="min-width: 100px; display: inline-block">
                          {{ room.roomName }}
                          <span
                            v-if="
                              room.rent_area && (room.roomStatus == 20 || room.roomStatus == 30)
                            "
                          >
                            ({{ room.rent_area.lease_square }}m²/ {{ room.buildArea }}m²)
                          </span>
                          <span v-else-if="room.is_lock">
                            ({{ room.locked_area }}m²/{{ room.buildArea }}m²)
                          </span>
                          <span v-else>({{ room.buildArea }}m²)</span>
                        </span>
                        <span style="min-width: 100px; display: inline-block">
                          {{ room.tenant_name ? room.tenant_name : '未入住' }}</span
                        >
                      </div>
                      <div>
                        <span style="min-width: 100px; display: inline-block"
                          >人数：{{ room.peopleNum }}</span
                        >
                        <span style="min-width: 100px; display: inline-block"
                          >用途：{{ room.house_type_txt ? room.house_type_txt : '--' }}</span
                        >
                      </div>
                      <div v-if="room.over_fee">{{ room.over_fee }}元</div>
                    </div>
                  </template>
                  <div
                    v-if="room.is_rent"
                    class="room"
                    :style="'background:' + room.roomColor"
                    :class="{
                      bordered: room.showStatus != 4,
                      dark:
                        viewMode == 1 && currentStatus && currentStatus.key != room.roomColorType
                    }"
                    @click="onRoomClick(room, layer)"
                  >
                    <div class="tenant-name nowrap">
                      {{ room.tenant_name }}
                    </div>
                    <div class="tenant-name nowrap">
                      {{ room.roomName }}
                      ({{ room.buildArea }}㎡)
                    </div>
                    <div class="expire-time nowrap">
                      {{ getExpireTimeTxt(room) }}
                    </div>

                    <div class="room-bottom" v-if="room.show_tags && room.show_tags.length">
                      <div class="room-tags">
                        <span
                          v-for="(tag, tagIndex) of room.show_tags"
                          :key="tagIndex"
                          :style="{
                            backgroundColor:
                              tag === '逾' ? 'rgba(221, 25, 25, 0.4)' : 'rgba(0, 0, 0, 0.2)'
                          }"
                          class="tag"
                        >
                          {{ tag }}
                        </span>
                      </div>
                    </div>
                  </div>

                  <div
                    v-else
                    class="room dark"
                    :style="'background:' + room.roomColor"
                    :class="{
                      bordered: room.showStatus != 4,
                      block:
                        viewMode == 1 && currentStatus && currentStatus.key != room.roomColorType
                    }"
                    @click="onRoomClick(room, item)"
                  >
                    <!--  -->
                    <div class="textEllipsis" style="font-size: 13px">
                      <span style="width: 60%; display: inline-block" class="textEllipsis">
                        {{ room.roomName }}
                        <span
                          v-if="room.rent_area && (room.roomStatus == 20 || room.roomStatus == 30)"
                        >
                          ({{ room.rent_area.lease_square }}m²/{{ room.buildArea }}m²)
                        </span>
                        <span v-else-if="room.is_lock">
                          ({{ room.locked_area }}m²/{{ room.buildArea }}m²)
                        </span>
                        <span v-else>({{ room.buildArea }}m²)</span>
                      </span>
                      <span style="width: 40%; display: inline-block" class="textEllipsis">{{
                        room.tenant_name ? room.tenant_name : '未入住'
                      }}</span>
                    </div>
                    <div class="textEllipsis" style="font-size: 13px">
                      <span style="width: 60%; display: inline-block" class="textEllipsis"
                        >人数：{{ room.peopleNum }}</span
                      >
                      <span style="width: 40%; display: inline-block" class="textEllipsis"
                        >用途：{{ room.house_type_txt ? room.house_type_txt : '--' }}</span
                      >
                    </div>
                    <div v-if="room.over_fee" class="textEllipsis" style="font-size: 13px">
                      <span style="color: red"> 欠费金额：{{ room.over_fee }}元 </span>
                    </div>

                    <div class="room-bottom" v-if="room.show_tags && room.show_tags.length">
                      <div class="room-tags">
                        <span
                          v-for="(tag, tagIndex) of room.show_tags"
                          :key="tagIndex"
                          :style="{
                            backgroundColor:
                              tag === '逾' ? 'rgba(221, 25, 25, 0.4)' : 'rgba(0, 0, 0, 0.2)'
                          }"
                          class="tag"
                        >
                          {{ tag }}
                        </span>
                      </div>
                    </div>
                  </div>
                </el-tooltip>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </template>
    <el-empty
      class="m-t-20px"
      v-else-if="!layerRespVOS.length && !loading"
      :image-size="140"
      :description="emptyText"
    />
  </ContentWrap>
  <RoomStatusDetail ref="RoomStatusDetailRef" @success="getDetail" @addcon="addContract" />
  <el-drawer v-model="addContractShow" direction="rtl" size="90%" :with-header="false">
    <AddContract @close="[(addContractShow = false), getDetail()]" />
  </el-drawer>
</template>
<script setup lang="ts">
defineOptions({ name: 'RoomStatusManagement' })
import { BuildApi } from '@/api/bus/village/building'
import { formatDate } from '@/utils/formatTime'
import AddContract from '@/views/contract/contractList/add_contractList.vue'
import { em } from 'dist-prod/assets/index-BUvKEheo'
import RoomStatusDetail from './RoomStatusDetail.vue'
const props = defineProps({
  id: {
    type: Number,
    default: null
  },
  layerId: {
    type: Number,
    default: null
  }
})
watch(
  () => props.id,
  (val, old) => {
    console.log(val) //正常接收
    if (val) {
      getDetail()
    }
  }
)

watch(
  () => props.layerId,
  (val, old) => {
    console.log(val) //正常接收
    if (val) {
      getDetail()
    }
  }
)
const currentStatus = ref(undefined)
const viewMode = ref(1)
const emptyText = ref('')
const loading = ref(false)
const roomStatusColor = ref([])
const layerRespVOS = ref([])
const getDetail = async () => {
  loading.value = true
  try {
    const data = await BuildApi.projectProfile({ id: props.id, layerId: props.layerId })
    roomStatusColor.value = JSON.parse(data.roomStatusColor)
    if (data.layerRespVOS && data.layerRespVOS.length > 0) {
      data.layerRespVOS.forEach((layer) => {
        let totalArea = Number(layer.rentalAreaAll)
        if (totalArea) {
          //  有房间含有面积
          const emptyRoom = layer.roomRespVO.filter(
            (item) => !item.buildArea || item.buildArea == 0
          )
          const emptyRoomArea = emptyRoom.length ? totalArea * 10 * 0.01 : 0
          layer.roomRespVO.forEach((room, roomIndex) => {
            if (Number(room.buildArea)) {
              // 计算比例
              room.areaRatio = parseRatio(
                ((Number(room.buildArea) + emptyRoomArea) / totalArea) * 100
              )
              if (Number(room.areaRatio) > 100) {
                room.areaRatio = 100
              }
              if (Number(room.areaRatio) < 10) {
                room.areaRatio = 10
              }
            } else {
              // 没有面积仍然算10%
              room.areaRatio = 10
            }
          })
          handleRoomFinalArea(layer.roomRespVO)
        } else {
          // 所有房间都是空的，超过10个房间按10%，不超过10个房间一行均分
          setEmptyRoomRatio(layer.roomRespVO)
        }
        if (layer?.two_dimensional?.length) {
          layer.two_dimensional.forEach((v) => {
            if (v.bindRoomId) {
              v = setCurrentRoomStyle(v, v.bindRoomId, layer.roomRespVO)
            }
          })
        } else {
          // 初始化 (默认排列房间)
          let editArr = []
          let percentTotal = 100
          let percentRow = 1
          let percentCol = 0
          let _roomList = (layer.roomRespVO || []).map((v) => {
            percentTotal = percentTotal - Number(v.areaRatio)
            if (percentTotal < 0) {
              percentTotal = 100
              percentRow++
              percentCol = 1
            } else {
              percentCol++
            }
            return { id: v.id, areaRatio: Number(v.areaRatio), row: percentRow, col: percentCol }
          })
          let gapWidth = 14
          let colHeight = 60
          for (let i = 0; i < _roomList.length; i++) {
            let columnsTotal = _roomList.filter((item) => item.row == _roomList[i].row)
            let widthTotal = 1000 - (columnsTotal.length + 1) * gapWidth
            let colWidth = Number(parseRatio((widthTotal * _roomList[i].areaRatio) / 100))
            let beforeTotalArea = 0
            if (_roomList[i].col > 1) {
              columnsTotal.forEach((item) => {
                if (item.col < _roomList[i].col) {
                  beforeTotalArea =
                    Number(beforeTotalArea) +
                    Number(parseRatio((widthTotal * item.areaRatio) / 100))
                }
              })
            }
            let surplusHeight = 666

            if (_roomList[i].row > 1) {
              surplusHeight =
                666 - (_roomList[i].row - 1) * colHeight - (_roomList[i].row + 1) * gapWidth
            }
            if (surplusHeight < colHeight) {
              break
            }
            _roomList[i].coor = [
              [
                gapWidth * _roomList[i].col + beforeTotalArea,
                _roomList[i].row * gapWidth + (_roomList[i].row - 1) * colHeight
              ],
              [
                gapWidth * _roomList[i].col + beforeTotalArea + colWidth,
                gapWidth * _roomList[i].row + _roomList[i].row * colHeight
              ]
            ]
            // console.log(beforeTotalArea,colWidth,widthTotal,_roomList[i].coor, 'beforeTotalArea====')
            _roomList[i].index = i
            _roomList[i].type = 1
            // _roomList[i].uuid = uuid.v4()
            _roomList[i].creating = false
            _roomList[i].dragging = false
            _roomList[i].active = false
            setCurrentRoomStyle(_roomList[i], _roomList[i].id, layer.roomRespVO)
            editArr.push(_roomList[i])
          }
          layer.two_dimensional = editArr
        }
      })
    }
    layerRespVOS.value = data.layerRespVOS || []
    console.log(layerRespVOS.value, 'layerRespVOS.value')
    if (props.layerId) {
      emptyText.value = '该楼层下暂无房源'
    } else {
      emptyText.value = '该楼宇下暂无房源'
    }
  } catch (e) {
    console.log(e)
  } finally {
    loading.value = false
  }
}
const addContractShow = ref(false)
const emit = defineEmits(['addcon']) // 定义 success 事件，用于操作成功后的回调
const addContract = (info) => {
  // addContractShow.value = true
  emit('addcon', info)
}

defineExpose({ getDetail }) // 提供 getTree ,用于更新
/** 状态展示文字 */
const getExpireTimeTxt = (room) => {
  //锁定房间
  if (room.is_lock) {
    // 如果是意向合同锁定
    if (room.extra_lock && room.extra_lock.length > 0) {
      room.lock_end_time = room.extra_lock[0].purposeUnlockTime
      room.extra_lock.forEach((item) => {
        // 找出最近时间
        if (moment(item.purposeUnlockTime).isBefore(room.lock_end_time)) {
          room.lock_end_time = item.purposeUnlockTime
        }
      })
      return `${room.lock_reason},锁定至${room.lock_end_time}`
    } else {
      return `${room.lock_reason}`
    }
  } else {
    const status = Number(room.showStatus)
    if (status > 3 && status < 7) {
      return `${formatDate(room.leaseEnd, 'YYYY-MM-DD')}到期`
    } else if (status == 1) {
      return `空置${room.vacant_days}日`
    } else if (status == 7) {
      return `${room.leaseEnd}已到期`
    }
    return ''
  }
}

// 整理房间的最终比例
const handleRoomFinalArea = (list) => {
  let total = 0
  let lastIndex = 0
  for (let i = 0; i < list.length; i++) {
    let item = list[i]
    total = total + Number(item.areaRatio)
    if (i == list.length - 1) {
      if (total >= 100) {
        // 只剩最后一个会折行
        setBeforeArea(list, lastIndex, i)
        setBeforeArea(list, i, i + 1)
      } else {
        // 最后一行铺不满的情况
        setBeforeArea(list, lastIndex, i + 1)
      }
    } else if (total >= 100) {
      // 面积已满一行
      setBeforeArea(list, lastIndex, i)
      total = Number(item.areaRatio)
      lastIndex = i
    }
  }
}
// 回溯处理
const setBeforeArea = (list, lastIndex, currentIndex) => {
  if (currentIndex == 0) {
    list[0].areaRatio = 100
    return
  }
  const hasAreaRoom = []
  let total = 0
  for (let i = lastIndex; i < currentIndex; i++) {
    const item = list[i]
    if (Number(item.buildArea)) {
      hasAreaRoom.push(item)
      total = Number(total) + Number(item.areaRatio)
    }
  }
  if (hasAreaRoom.length) {
    const emptyNum = currentIndex - lastIndex - hasAreaRoom.length
    let remainTotal = 100 - emptyNum * 10 // 除去空房间剩余总空间
    for (let item of hasAreaRoom) {
      item.areaRatio = parseRatio((Number(item.areaRatio) / total) * remainTotal)
    }
  } else {
    setEmptyRoomRatio(list, lastIndex, currentIndex)
  }
}

const parseRatio = (number, decimal = 2) => {
  return (Math.floor(number * 100) / 100).toFixed(decimal)
}
const setCurrentRoomStyle = (info = {}, roomId, roomList = []) => {
  if (roomId) {
    let roomInfo = roomList.find((v) => v.id == roomId)
    info.bindRoomId = roomInfo.id
    info.bindRoomInfo = roomInfo
    let bindColor = roomInfo?.roomColor
    if (bindColor) {
      let roomFillColor = utils.getHexOpacityColor({
        color: bindColor,
        opacity: 0.9
      })
      info.strokeStyle = '#fff'
      info.roomLineColor = '#fff'
      info.fillStyle = roomFillColor
      info.roomFillColor = roomFillColor
    }
    info.label = roomInfo?.roomName || ''
    info.labelFont = '14px sans-serif'
  } else {
    info.label = ''
    info.strokeStyle = '#2681f3'
    info.fillStyle = '#fff'
    delete info.bindRoomId
    delete info.bindRoomInfo
    delete info.roomLineColor
    delete info.roomFillColor
  }
  return info
}
// 设置空房间比例 endIndex不包含
const setEmptyRoomRatio = (list, startIndex = 0, endIndex) => {
  endIndex = endIndex || list.length
  let length = endIndex - startIndex
  if (length < 10) {
    for (let i = startIndex; i < endIndex; i++) {
      list[i].areaRatio = parseRatio(100 / length)
    }
  } else {
    const remainder = length % 10
    const integer = length / 10
    for (let i = integer * 10; i < remainder; i++) {
      list[i].areaRatio = parseRatio(100 / remainder)
    }
  }
}
const RoomStatusDetailRef = ref()
const onRoomClick = (room, item) => {
  console.log(room, item, 'item')
  RoomStatusDetailRef.value.open(room)
}

/** 初始化 **/
onMounted(() => {
  getDetail()
})
</script>

<style lang="scss" scoped>
.room-right {
  display: flex;
  flex-wrap: wrap;
  margin-left: 6px;
  .room-box {
    padding: 5px 3px;
    min-width: 10%;
    position: relative;
    box-sizing: border-box;

    .room {
      height: 84px;
      padding: 4px 8px;
      border-radius: 6px;
      background: #a9dcfd;
      cursor: pointer;
      line-height: 20px;
      position: relative;
      &:hover {
        transform: scale(1.02);
        transition: all 0.2s;
        z-index: 100;
        // box-shadow: @box-shadow-base;
      }
      .tenant-name {
        color: #000;
      }
      .expire-time {
        color: rgba(0, 0, 0, 0.35);
        font-size: 12px;
      }
      .room-bottom {
        display: flex;
        align-items: center;
        justify-content: flex-end;
        font-size: 12px;
        position: absolute;
        right: 4px;
        bottom: 4px;
        .room-tags {
          .tag {
            padding: 2px 4px;
            color: #fff;
            margin-right: 4px;
            border-radius: 2px;
          }
        }
      }
    }
    .bordered {
      border: 3px dashed rgb(143, 163, 183);
    }
    .dark {
      opacity: 0.4;
    }
  }
}
.textEllipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
