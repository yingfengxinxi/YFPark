<template>
  <el-drawer v-model="Drawer" title="巡检计划" size="50%">
    <el-scrollbar @scroll="scroll" ref="scrollbarRef">
      <div class="flex gap-20px">
        <div class="left w-15% pos-fixed z-999">
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in activities"
              :type="now_index == activity.index ? 'primary' : 'default'"
              :hollow="true"
              :key="index"
            >
              <span
                class="cursor-pointer"
                :class="now_index == activity.index ? 'active' : ''"
                @click="to_activity(activity.index)"
                >{{ activity.content }}</span
              >
            </el-timeline-item>
          </el-timeline>
        </div>
        <div class="w-15%"> </div>
        <div class="right w-85% pb-30px">
          <div class="RightItem">
            <div class="pl-10px border-l-4px border-#409EFF border-solid border-0 font-600"
              >计划设置</div
            >
            <el-row class="mt-30px">
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >计划编号</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px break-all h-100%"
                  >{{ detailData.planNumber }}</div
                >
              </el-col>
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >计划名称</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px h-100%"
                  >{{ detailData.planName }}</div
                >
              </el-col>
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >责任岗位</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px h-100%"
                  >{{ listData.stationDepartmentName.split('-')[0] }}</div
                >
              </el-col>
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >责任部门</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >{{ listData.stationDepartmentName.split('-')[1] }}</div
                >
              </el-col>
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >开始时间</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >{{ listData.startDate }}</div
                >
              </el-col>
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >截止时间</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >{{ listData.endDate }}</div
                >
              </el-col>
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >超时处理</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px h-65px"
                  >{{
                    getStrDictOptions('TIMEOUT_TYPE').find(
                      (item) => item.value == detailData.timeOutType
                    )?.label
                  }}</div
                >
              </el-col>
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >时间要求</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px h-65px"
                  >{{ detailData.timeLimit + '小时' }}</div
                >
              </el-col>
              <el-col :span="8">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >周期</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px h-65px"
                  >{{ listData.planRule }}</div
                >
              </el-col>
            </el-row>
            <div class="pl-10px border-l-4px border-#409EFF border-solid border-0 font-600 mt-40px"
              >执勤人员</div
            >
            <el-table :data="detailData.userList">
              <el-table-column label="人员姓名" align="center" prop="nickName" />
              <el-table-column label="岗位" align="center" prop="postName" :formatter="tableColumnEmptyPlaceholder" />
              <el-table-column label="部门" align="center" prop="deptName" />
            </el-table>
            <div class="h-40px"> </div>
          </div>
          <div class="RightItem">
            <div class="pl-10px border-l-4px border-#409EFF border-solid border-0 font-600"
              >巡检路线</div
            >
            <div class="flex py-20px text-14px">
              巡检方式:<span class="color-#409EFF ml-10px">{{
                detailData.patrolOrder == 1 ? '必须依次' : '可以随机'
              }}</span>
            </div>
            <el-table :data="detailData.planPositionList">
              <el-table-column label="巡检点编码" align="center" prop="number" />
              <el-table-column label="巡检点名称" align="center" prop="name" />
              <el-table-column label="位置" align="center" prop="positionName" />
              <el-table-column label="扫码打卡" align="center" prop="isScanCodeCheck">
                <template #default="row">
                  <div :class="row?.isScanCodeCheck == 1 ? 'Icon1' : 'Icon0'">
                    {{ row.isScanCodeCheck == 1 ? '开启' : '关闭' }}
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="RightItem">
            <div class="pl-10px border-l-4px border-#409EFF border-solid border-0 font-600 mt-40px"
              >通知设置</div
            >
            <el-row class="mt-30px">
              <el-col :span="12">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >提醒方式</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >{{
                    getStrDictOptions('REMIND').find((item) => item.value == remindJson.noticeType)
                      ?.label
                  }}</div
                >
              </el-col>
              <el-col :span="12">
                <div
                  class="bg-#FAFAFA border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >任务发送</div
                >
                <div
                  class="border-1 border-solid border-#F0F0F0 p-14px color-#666 box-border text-14px"
                  >{{
                    '提前' +
                    remindJson.noticeTime +
                    getStrDictOptions('NOTICE_TIME_UNIT').find(
                      (item) => item.value == remindJson.noticeTimeUnit
                    )?.label
                  }}</div
                >
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </el-drawer>
</template>
<script lang="ts" setup>
import { patrolPlanEquipmentApi, VO } from '@/api/bus/patrol/planEquipment'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { ref } from 'vue'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
const Drawer = ref(false)
const detailData = ref<VO | null>(null)
const listData = ref<VO[]>([])
const remindJson = ref({})
function open(id, data) {
  setTimeout(() => {
    GetItemList()
  }, 3000)
  listData.value = data
  patrolPlanEquipmentApi.get(id).then((res) => {
    detailData.value = res
    remindJson.value = JSON.parse(res.remindJson)
    Drawer.value = true
  })
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
///滚动数据
const now_index = ref(0)
const activities = ref([
  { index: 0, content: '计划设置' },
  { index: 1, content: '巡检路线' },
  { index: 2, content: '通知设置' }
])
const scroll = (e) => {
  if (nowChange.value) {
    const scrollTop = e.scrollTop
    if (scrollTop == 0) {
      now_index.value = 0
      return
    } else {
      for (let index = 0; index < ItemList.value.length; index++) {
        if (scrollTop >= ItemList.value[index]) {
          now_index.value = index + 1
          break
        }
      }
    }
  }
}
const scrollbarRef = ref(null)
const nowChange = ref(true)
const to_activity = (index) => {
  nowChange.value = false
  setTimeout(() => {
    nowChange.value = true
  }, 200)
  now_index.value = index
  if (index == 0) {
    scrollbarRef.value!.setScrollTop(0)
    return
  } else {
    scrollbarRef.value!.setScrollTop(ItemList.value[index - 1])
    now_index.value = index
  }
}
const ItemList = ref([])
const GetItemList = async () => {
  ItemList.value = []
  const Item = document.getElementsByClassName('RightItem')
  for (let i = 0; i < Item.length; i++) {
    if (i == 0) {
      ItemList.value.push(Item[i].clientHeight)
    } else {
      ItemList.value.push(ItemList.value[i - 1] + Item[i].clientHeight)
    }
  }
}
</script>
<style scoped>
.Icon0 {
  &::before {
    content: '';
    display: inline-block;
    width: 8px;
    height: 8px;
    background-color: #f00;
    border-radius: 50%;
    margin-right: 5px;
  }
}
.Icon1 {
  &::before {
    content: '';
    display: inline-block;
    width: 8px;
    height: 8px;
    background-color: #409eff;
    border-radius: 50%;
    margin-right: 5px;
  }
}
::v-deep .el-timeline {
  padding: 0;
}
.RightItem {
  box-size: border-box;
}
</style>
