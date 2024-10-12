<template>
  <div>
    <el-drawer v-model="drawerValue" :size="leftIndex == -1 ? '45%' : '90%'" title="巡检执行情况">
      <div class="flex justify-between w-100% h-100% gap-10px">
        <div
          :class="leftIndex >= 0 ? 'w-50%' : '100%'"
          class="overflow-y-scroll overflow-x-hidden scroll"
        >
          <el-row :gutter="10">
            <el-col :span="8">
              <el-input placeholder="请输入巡检点名称" v-model="leftForm.name">
                <template #append>
                  <Icon icon="ep:search" @click="getList" />
                </template>
              </el-input>
            </el-col>
            <el-col :span="16">
              <div class="text-end">
                <el-radio-group v-model="leftForm.status" @change="getList">
                  <el-radio-button label="全部" value="" />
                  <el-radio-button
                    v-for="(item, index) in getStrDictOptions('PATROL_RECORD_EQUIPMENT_STATUS')"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                    v-show="item.value != '5'"
                  />
                </el-radio-group>
              </div>
            </el-col>
          </el-row>
          <div class="mt-10px">
            <div
              class="flex justify-between p-16px gap-20px box-border h-87px"
              @click="clickListItem(index)"
              :class="leftIndex === index ? 'bg-#F0F9FF' : 'bg-#fff'"
              v-for="(item, index) in leftList"
              :key="index"
            >
              <div class="w-55px h-55px bg-#DDDDDD rounded min-w-55px pos-relative">
                <Icon
                  icon="ep:add-location"
                  :size="26"
                  color="#858585"
                  class="pos-absolute top-50% left-50% transform-translate--50%"
                />
              </div>
              <div class="h-100% flex flex-col justify-around">
                <div class="text-14px font-600">{{ item.position }}</div>
                <div class="text-#fff text-12px text-center py-2px">
                  <el-tag
                    :type="
                      getStrDictOptions('PATROL_RECORD_EQUIPMENT_STATUS').find(
                        (items) => items.value == item.status
                      )?.colorType
                    "
                  >
                    {{
                      getStrDictOptions('PATROL_RECORD_EQUIPMENT_STATUS').find(
                        (items) => items.value == item.status
                      )?.label
                    }}
                  </el-tag>
                </div>
              </div>
              <div class="h-100% flex flex-col justify-around">
                <div class="text-14px text-#999">巡检人</div>
                <div class="text-14px text-#999">{{ item.name || '--' }}</div>
              </div>
              <div class="h-100% flex flex-col justify-around">
                <div class="text-14px text-#999">巡检时间</div>
                <div class="text-14px text-#999">{{ item.time || '--' }}</div>
              </div>
              <div class="h-100% flex flex-col justify-around">
                <div class="text-14px text-#999">扫码打卡</div>
                <div class="text-14px text-#999">{{ item.isSigned ? '否' : '是' }}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="h-100% w-1px bg-#999" v-if="leftIndex >= 0"> </div>
        <div class="w-50%" v-if="leftIndex >= 0">
          <div class="flex h-55px">
            <div class="w-55px h-55px bg-#DDD pos-relative rounded">
              <Icon
                icon="ep:add-location"
                :size="26"
                color="#858585"
                class="pos-absolute top-50% left-50% transform-translate--50%"
              />
            </div>
            <div class="flex flex-col justify-around ml-20px">
              <div class="text-14px font-600">{{ rightData.position }}</div>
              <div class="text-14px text-#999">{{ rightData?.property?.propertyNumber }}</div>
            </div>
          </div>
          <div class="mt-30px">
            <div class="font-600 pl-8px border-l-4px border-solid border-0 border-#2681F3"
              >巡检表单</div
            >
            <div class="text-#999 text-14px mt-20px"
              >巡检结果:
              {{
                getStrDictOptions('PATROL_RECORD_EQUIPMENT_STATUS').find(
                  (items) => items.value == rightData.status
                )?.label
              }}</div
            >
          </div>
          <div class="mt-30px">
            <div class="font-600 pl-8px border-l-4px border-solid border-0 border-#2681F3"
              >整改记录</div
            >
            <div class="text-#999 text-14px mt-20px">
              <span v-if="rightData.handleStatus == 1">待处理</span>
              <span v-if="rightData.handleStatus == 2">处理中</span>
              <span v-if="rightData.handleStatus == 3">已处理</span>
            </div>
          </div>
          <div class="mt-30px">
            <div class="font-600 pl-8px border-l-4px border-solid border-0 border-#2681F3"
              >绑定资产</div
            >
            <el-table class="mt-20px" :data="rightList">
              <el-table-column label="资产编码" align="center" prop="propertyNumber" />
              <el-table-column label="资产分类" align="center" prop="type">
                <template #default="scope">
                  {{ scope.row.typeName }}
                </template>
              </el-table-column>
              <el-table-column label="所在位置" align="center" prop="positionName" />
              <el-table-column label="品牌" align="center" prop="brand" />
              <el-table-column label="型号" align="center" prop="name" />
              <el-table-column label="状态" align="center" prop="status">
                <template #default="scope">
                  {{
                    getStrDictOptions('PROPERTYSTATUS').find(
                      (items) => items.value == scope.row.status
                    )?.label
                  }}
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </el-drawer>
  </div>
</template>
<script lang="ts" setup>
import { Api, VO } from '@/api/bus/patrol/taskEquipment'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { PropertyCategoryApi } from '@/api/bus/property/setting/propertyCategory'

const idvalue = ref(0)
const drawerValue = ref(false)
const leftList = ref([])
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
onMounted(() => {
  getapplication()
})
function open(id) {
  drawerValue.value = true
  idvalue.value = id
  Api.taskRecordList({
    taskId: id,
    status: leftForm.value.status,
    address: leftForm.value.name,
    pageNo: 1,
    pageSize: 100,
    application: application.value
  }).then((res) => {
    leftList.value = res.list
  })
  leftIndex.value = -1
  leftForm.value = {
    status: '',
    name: ''
  }
}
const leftIndex = ref(-1)
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const leftForm = ref({
  status: '',
  name: ''
})
const rightData = ref({
  position: '',
  status: '',
  handleStatus: 0,
  property: []
})
const rightList = ref([])
const clickListItem = (index: number) => {
  leftIndex.value = index
  rightData.value = leftList.value[index]
  if (leftList.value[index].property) {
    rightList.value[0] = leftList.value[index].property
    PropertyCategoryApi.getPropertyCategory(leftList.value[index].property.type).then((res) => {
      rightList.value[0].typeName = res.name
    })
  } else {
    rightList.value = []
  }
}
const getList = async () => {
  Api.taskRecordList({
    taskId: idvalue.value,
    status: leftForm.value.status,
    address: leftForm.value.name,
    pageNo: 1,
    pageSize: 100
  }).then((res) => {
    leftList.value = res.list
  })
}
</script>
<style lang="scss" scoped>
.scroll {
  overflow: auto; /* 保持内容可滚动 */
  scrollbar-width: none; /* 对于现代浏览器，隐藏滚动条 */
}
.scroll::-webkit-scrollbar {
  display: none;
}
.scroll {
  -ms-overflow-style: none; /* IE 和 Edge 下隐藏滚动条 */
}
</style>
