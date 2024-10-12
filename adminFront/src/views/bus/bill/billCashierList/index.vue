<template>
  <ContentWrap
    v-if="!show_detail"
    class="flex justify-center h-[calc(100vh-var(--app-content-padding)-var(--app-content-padding)-var(--logo-height)-var(--tags-view-height)-4px)]"
  >
    <div class="m-t-10vh w-40vw">
      <div class="c-#000000a6 font-size-40px m-b-20px text-center">收银台</div>
      <el-radio-group v-model="type" size="large" @change="typeChange">
        <el-radio-button value="1"
          >资源
          <el-tooltip content="仅搜索展示合同数>0的房间" placement="top"
            ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999" /></el-tooltip
        ></el-radio-button>
        <el-radio-button label="租客" value="2" />
        <el-radio-button label="停车费" value="3" />
      </el-radio-group>
      <div class="mt-4">
        <el-input
          v-model="SearchInput"
          :placeholder="placeholder"
          size="large"
          class="w-100%"
          clearable
        >
          <template #prepend>
            <el-select
              v-model="selectInput"
              size="large"
              placeholder="Select"
              @change="seelectChange"
              style="width: 115px"
            >
              <el-option label="资源编号" value="roomAliasId" v-if="type == '1'" />
              <el-option label="房号" value="roomName" v-if="type == '1'" />
              <el-option label="手机号" value="tel" v-if="type == '2'" />
              <el-option label="租客名" value="name" v-if="type == '2'" />
              <el-option label="车牌号" value="carNumber" v-if="type == '3'" />
              <el-option label="车主姓名" value="userName" v-if="type == '3'" />
              <el-option label="车主手机号" value="userPhone" v-if="type == '3'" />
            </el-select>
          </template>
          <template #append>
            <!-- <el-button :icon="Search" /> -->
            <el-button
              type="primary"
              :disabled="loading"
              :v-hasPermi="
                type == '1'
                  ? ['bus:room:resourceList']
                  : type == '2'
                    ? ['bus:owner:ownerSearchList']
                    : ['bus:parkCars:getList']
              "
              @click="getList()"
            >
              <Icon class="mr-5px" icon="ep:search" color="#fff"
            /></el-button>
          </template>
        </el-input>
      </div>
      <div
        v-if="loadList"
        v-loading="loading"
        class="h-340px mt-3 !b-#d9d9d9 !b-1 !b-solid !b-rd-4px p-t-20px p-b-20px"
      >
        <template v-if="type == '1' && !loading">
          <el-scrollbar height="300px" v-if="List.length" class="p-l-20px p-r-20px">
            <el-collapse v-model="activeNames" @change="handleChange">
              <el-collapse-item
                v-for="(item, index) in List"
                :key="index"
                :title="item.room"
                :name="index"
              >
                <div
                  class="cursor-pointer hover:bg-#e6f7ff lh-40px p-8px p-t-0px p-b-0px font-size-14px hover:c-#1890ff"
                  @click="getDetail(item)"
                >
                  {{ item.owner.name }}
                </div>
              </el-collapse-item>
            </el-collapse>
          </el-scrollbar>
          <el-empty class="" :image-size="80" description="暂无数据" v-else />
        </template>
        <template v-if="type == '2' && !loading">
          <el-scrollbar height="300px" v-if="List.length" class="p-l-20px p-r-20px">
            <div
              v-for="(item, index) in List"
              :key="index"
              class="cursor-pointer hover:bg-#e6f7ff lh-40px p-8px p-t-0px p-b-0px font-size-14px hover:c-#1890ff"
              @click="getDetail(item)"
            >
              {{ item.name }}
            </div>
          </el-scrollbar>
          <el-empty :image-size="80" description="暂无数据" v-else />
        </template>
        <template v-if="type == '3' && !loading">
          <el-scrollbar height="300px" v-if="List.length" class="p-l-20px p-r-20px">
            <el-row
              v-for="(item, index) in List"
              :key="index"
              class="cursor-pointer hover:bg-#e6f7ff lh-40px p-8px p-t-0px p-b-0px font-size-14px hover:c-#1890ff"
              @click="getDetail(item)"
            >
              <el-col :span="7">
                {{ item.carNumber }}
              </el-col>
              <el-col :span="9">
                <template v-for="dict in chargeTypeList" :key="dict.value">
                  <span v-if="item.chargeType === String(dict.value)">
                    {{ dict.label }}
                  </span>
                </template>
                - {{ item.villageName }}
              </el-col>
              <el-col :span="8"> {{ item.enableTime }} ~ {{ item.overdueTime }} </el-col>
            </el-row>
          </el-scrollbar>
          <div v-else class="flex items-center justify-center">
            <el-button type="primary" @click="handleAddCar">车辆不存在，录入？</el-button>
          </div>
        </template>
      </div>
    </div>
  </ContentWrap>
  <CashierDetail
    v-if="show_detail"
    :id="id"
    :typeId="type"
    @back="(res) => (show_detail = res)"
    @car-update="CarUpdate"
    ref="CashierDetailRef"
  />
  <addCar ref="addCarRef" />
</template>
<script setup lang="ts">
import { BillCashierListApi } from '@/api/bus/bill/billCashierList'

defineOptions({ name: 'BillCashierList' })
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
const type = ref('1')
const SearchInput = ref('')
const selectInput = ref('roomAliasId')
const loadList = ref(false)
import addCar from './addCar.vue'
import CashierDetail from './CashierDetail.vue'
const show_detail = ref(false)
const id = ref(0)
const loading = ref(false)
const typeChange = (val: string) => {
  console.log(val)
  loadList.value = false
  List.value = []
  SearchInput.value = ''
  if (val == '1') {
    selectInput.value = 'roomAliasId'
  } else if (val == '2') {
    selectInput.value = 'tel'
  } else {
    selectInput.value = 'carNumber'
  }
}
const placeholder = computed(() => {
  if (type.value == '1') {
    return '请输入资源编号搜索'
  } else if (type.value == '2') {
    return '请输入请输入完整的手机号搜索'
  } else {
    return '请输入完整的车牌号或车牌号后4为搜索'
  }
})

const List = ref([])
const getList = async () => {
  let params = {
    [selectInput.value]: SearchInput.value
  }

  try {
    if (!SearchInput.value) {
      return
    }
    loadList.value = true
    loading.value = true
    let res =
      type.value == '1'
        ? await BillCashierListApi.resourceList(params)
        : type.value == '2'
          ? await BillCashierListApi.ownerSearchList(params)
          : await BillCashierListApi.getList(params)
    console.log(List.value)

    List.value = type.value == '3' ? res.list : res
  } catch (error) {
    console.log(error)
  } finally {
    loading.value = false
  }
}

const getDetail = (item: any) => {
  console.log(item)
  if (type.value == '3') {
    id.value = item.id
  } else {
    id.value = item.owner.id
  }
  show_detail.value = true
}

const addCarRef = ref()
/** 添加车辆 */
const handleAddCar = () => {
  addCarRef.value.open('create')
}
const CashierDetailRef = ref()
const CarUpdate = (carInfo) => {
  console.log('上传', carInfo)
  addCarRef.value.open('update', carInfo)
  CashierDetailRef.value.getDetail()
}

onMounted(() => {
  console.log('mounted')
  getChrgeType()
})
const activeNames = ref(['1'])
const handleChange = (val: string[]) => {
  console.log(val)
}
const seelectChange = (val: string) => {
  SearchInput.value = ''
  loadList.value = false
}
const chargeTypeList = ref([])
const getChrgeType = async () => {
  chargeTypeList.value = await getIntDictOptions(DICT_TYPE.CHARGE_TYPE)
  console.log(chargeTypeList.value)
}
</script>
<style lang="scss" scoped>
.el-input :deep(.el-input-group__append) {
  background: var(--el-color-primary) !important;
  box-shadow: 0 0 0 0px var(--el-input-border-color, var(--el-border-color)) inset;
  border: none; /* 对border进行样式修改 */
}
::v-deep.el-radio-button {
  .el-radio-button__inner {
    //修改按钮样式
    background: none;
    padding-left: 30px;
    padding-right: 30px;
  }
  .el-radio-button__original-radio:checked + .el-radio-button__inner {
    // 修改按钮激活样式
    color: #52a5ff;
    box-sizing: border-box;
    border-color: #52a5ff;
  }
}
</style>
