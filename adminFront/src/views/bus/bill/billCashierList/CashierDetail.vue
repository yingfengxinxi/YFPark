<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <ContentWrap>
    <div class="flex items-end">
      <div class="c-#000000a6 font-size-40px m-r-20px">收银台</div>
      <el-button plain @click="emit('back', false)">
        <Icon icon="ep:arrow-left" :size="14" color="#999" />
        返回首页</el-button
      >
    </div>
  </ContentWrap>
  <ContentWrap v-if="typeId == 3" v-loading="loading" class="cashier-detail">
    <el-descriptions class="margin-top" :column="3" border>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item"> 车牌号</div>
        </template>
        <span
          v-if="carInfo.carNumber"
          class="c-[var(--el-color-primary)] cursor-pointer"
          @click="CarUpdate(carInfo)"
          >{{ carInfo.carNumber }}</span
        >
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="车辆类型">
        <div>{{
          carInfo.chargeType == 1
            ? '月租车'
            : carInfo.chargeType == 2
              ? '储值车'
              : '登记/预约临时车'
        }}</div>
        <div>{{ carInfo.enableTime }} ~ {{ carInfo.overdueTime }} </div>
      </el-descriptions-item>
      <el-descriptions-item label="车辆余额">
        <span
          v-if="carInfo.balance"
          class="c-[var(--el-color-primary)] cursor-pointer"
          @click="OwnerDetails(Owner)"
          >{{ carInfo.balance }}</span
        >
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item label="所属车场">
        {{ carInfo.villageName }}/{{ carInfo.parkName }}
      </el-descriptions-item>
      <el-descriptions-item label="车主姓名">
        {{ carInfo.userName }}
      </el-descriptions-item>
      <el-descriptions-item label="车主手机号">
        {{ carInfo.userPhone || '--' }}
      </el-descriptions-item>
    </el-descriptions>
    <div class="m-t-20px">
      <span class="c-#000000a6 font-size-16px font-bold">关联房屋 </span>
      <el-tooltip
        content="此处的房屋将展示该租客关联的所有房屋，与左上角选中项目/楼宇无关。如果您未选中该楼宇，无法点击房号进行查看房屋详情。"
        placement="top"
        ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
      /></el-tooltip>
    </div>
    <el-table :data="Owner.roomOwnerListVOList" border class="m-t-10px">
      <el-table-column label="项目">
        <template #default="scope">
          <span>{{ scope.row.parkName || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="楼宇" prop="buildName">
        <template #default="scope">
          {{ scope.row.buildName || '--' }}
        </template>
      </el-table-column>

      <el-table-column label="楼层" prop="floor">
        <template #default="scope">
          {{ scope.row.floor || '--' }}
        </template>
      </el-table-column>
      <el-table-column label="房间号" prop="roomName">
        <template #default="scope">
          <span
            v-if="typeId == '1' && scope.row.roomId == props.id"
            class="c-[var(--el-color-primary)] cursor-pointer"
            @click="RoomDetail(scope.row)"
            >{{ scope.row.roomName }}</span
          >
          <span v-else>{{ scope.row.roomName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="租赁面积" prop="rentalArea">
        <template #default="scope">
          <span v-if="scope.row.rentalArea">{{ scope.row.rentalArea }}㎡</span>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column label="房源标签" prop="rentalArea">
        <template #default="scope">
          <span v-if="scope.row.rentalArea">{{ scope.row.rentalArea }}㎡</span>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column label="房源标签" prop="tagName">
        <template #default="scope">
          <span v-if="scope.row.tagName">{{ scope.row.tagName }}</span>
          <template v-else>--</template>
        </template>
      </el-table-column>
    </el-table>
    <div class="m-t-40px flex justify-between items-center">
      <span class="c-#000000a6 font-size-16px font-bold">车辆订单记录 </span>
      <div>
        <el-button type="primary" plain @click="storedValue(carInfo)">储值缴费</el-button>
        <el-button type="primary" @click="MonthlyRent(carInfo)">月租缴费</el-button>
      </div>
    </div>
    <el-table
      :data="carOrderList"
      border
      class="m-t-10px"
      :header-cell-style="{
        color: '#000000d9',
        fontSize: '14px',
        fontWeight: '500',
        backgroundColor: '#fafafa',
        textAlign: 'center'
      }"
    >
      <el-table-column label="项目名称" align="center">
        <template #default="scope">
          <span>{{ scope.row.villageName || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车场名称" prop="buildName" align="center">
        <template #default="scope">
          {{ scope.row.parkName || '--' }}
        </template>
      </el-table-column>

      <el-table-column label="订单类型" prop="floor" align="center">
        <template #default="scope">
          {{ getDictLabel(DICT_TYPE.CHARGE_TYPE, scope.row.orderType) }}
        </template>
      </el-table-column>
      <el-table-column label="订单信息" prop="roomName" align="center" width="340px">
        <template #default="scope">
          <div>订单名称：{{ scope.row.orderName }} </div>
          <div> 订单号：{{ scope.row.orderId }}</div>
        </template>
      </el-table-column>
      <el-table-column label="订单金额" prop="orderId" align="center">
        <template #default="scope">
          <span v-if="scope.row.orderMoney" class="c-[var(--el-color-primary)]">
            ￥{{ scope.row.orderMoney }}
          </span>
          <span v-else> -- </span>
        </template>
      </el-table-column>
      <el-table-column label="支付时间" prop="orderId" align="center">
        <template #default="scope">
          {{ scope.row.payTime || '--' }}
        </template>
      </el-table-column>
      <el-table-column label="支付方式" prop="orderId" align="center">
        <template #default="scope">
          {{ scope.row.payMethodTxt || '--' }}
        </template>
      </el-table-column>
      <el-table-column label="第三方订单号" prop="orderId" align="center">
        <template #default="scope">
          {{ scope.row.thirdOrderNo || '--' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" prop="orderId" align="center">
        <template #default="scope">
          <el-button link type="primary" @click.stop="openOrderForm(scope.row)"> 详情 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      :total="ParkCarOrderTotal"
      v-model:page="ParkCarOrderParams.pageNo"
      v-model:limit="ParkCarOrderParams.pageSize"
      @pagination="getCarOrderList"
    />
    <div class="m-t-80px">
      <span class="c-#000000a6 font-size-16px font-bold">车辆操作记录 </span>
    </div>
    <el-table
      :data="VehicleOperationRecord"
      border
      class="m-t-10px"
      :header-cell-style="{
        color: '#000000d9',
        fontSize: '14px',
        fontWeight: '500',
        backgroundColor: '#fafafa',
        textAlign: 'center'
      }"
    >
      <el-table-column label="操作人" width="140px" align="center">
        <template #default="scope">
          <span>{{ scope.row.creatorName || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作时间" prop="buildName" width="180px" align="center">
        <template #default="scope">
          {{ scope.row.createTime || '--' }}
        </template>
      </el-table-column>

      <el-table-column label="操作类型" prop="floor" width="200px" align="center">
        <template #default="scope">
          {{ getDictLabel(DICT_TYPE.PARK_CAR_LOG_TYPE, scope.row.type) }}
        </template>
      </el-table-column>
      <el-table-column label="操作内容" prop="roomName" width="440px" align="center">
        <template #default="scope"> {{ scope.row.content || '--' }} </template>
      </el-table-column>
      <el-table-column label="订单信息" prop="orderId" width="370px" align="center">
        <template #default="scope">
          {{ scope.row.orderId || '--' }}
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      :total="ParkCarsOperatorLogTotal"
      v-model:page="getParkCarsOperatorLogParams.pageNo"
      v-model:limit="getParkCarsOperatorLogParams.pageSize"
      @pagination="getVehicleOperationRecord"
    />
  </ContentWrap>
  <ContentWrap v-else v-loading="loading" class="cashier-detail">
    <el-descriptions class="margin-top" :column="3" border>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item"> 租客</div>
        </template>
        <span
          v-if="Owner.ownerName"
          class="c-[var(--el-color-primary)] cursor-pointer"
          @click="OwnerDetails(Owner)"
          >{{ Owner.ownerName }}</span
        >
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            合同有效期
            <el-tooltip
              content="仅展示最后一个合同的状态。若存在多笔，请点击租客名称查看更多合同信息。"
              placement="top"
              ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
            /></el-tooltip>
          </div>
        </template>
        <span
          v-if="Owner.contractValidityPeriod"
          class="c-[var(--el-color-primary)] cursor-pointer"
          @click="ContactDetails(Owner)"
          >{{ Owner.contractValidityPeriod }}</span
        >
        <template v-else> -- </template>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            合同状态
            <el-tooltip
              content="仅展示最后一个合同的状态。若存在多笔，请点击租客名称查看更多合同信息。"
              placement="top"
              ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
            /></el-tooltip>
          </div>
        </template>
        {{ Owner.statusName || '--' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item"> 默认联系人 </div>
        </template>
        {{ Owner.contactName || '--' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item"> 合同经办人 </div>
        </template>
        {{ Owner.operatorName || '--' }}
      </el-descriptions-item>
      <el-descriptions-item />
    </el-descriptions>
    <div class="m-t-20px">
      <span class="c-#000000a6 font-size-16px font-bold">关联房屋 </span>
      <el-tooltip
        content="此处的房屋将展示该租客关联的所有房屋，与左上角选中项目/楼宇无关。如果您未选中该楼宇，无法点击房号进行查看房屋详情。"
        placement="top"
        ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
      /></el-tooltip>
    </div>
    <el-table :data="Owner.roomOwnerListVOList" border class="m-t-10px">
      <el-table-column label="项目">
        <template #default="scope">
          <span>{{ scope.row.parkName || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="楼宇" prop="buildName">
        <template #default="scope">
          {{ scope.row.buildName || '--' }}
        </template>
      </el-table-column>

      <el-table-column label="楼层" prop="floor">
        <template #default="scope">
          {{ scope.row.floor || '--' }}
        </template>
      </el-table-column>
      <el-table-column label="房间号" prop="roomName">
        <template #default="scope">
          <span
            v-if="typeId == '1' && scope.row.roomId == props.id"
            class="c-[var(--el-color-primary)] cursor-pointer"
            @click="RoomDetail(scope.row)"
            >{{ scope.row.roomName }}</span
          >
          <span v-else>{{ scope.row.roomName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="租赁面积" prop="rentalArea">
        <template #default="scope">
          <span v-if="scope.row.rentalArea">{{ scope.row.rentalArea }}㎡</span>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column label="房源标签" prop="rentalArea">
        <template #default="scope">
          <span v-if="scope.row.rentalArea">{{ scope.row.rentalArea }}㎡</span>
          <template v-else>--</template>
        </template>
      </el-table-column>
      <el-table-column label="房源标签" prop="tagName">
        <template #default="scope">
          <span v-if="scope.row.tagName">{{ scope.row.tagName }}</span>
          <template v-else>--</template>
        </template>
      </el-table-column>
    </el-table>
    <Check
      ref="CheckRef"
      :ownerInfo="Owner"
      @room-detail="RoomDetail"
      @contact-detail="ContactDetails"
    />
  </ContentWrap>

  <TenantDetails ref="TenantDetailsRef" />
  <ContractDetailDrawer ref="ContractDetailDrawerRef" @close="getDetail()" />
  <RoomStatusDetail ref="RoomStatusDetailRef" @close="getDetail()" />"
  <storedValueForm ref="StoredValueFormRef" @success="getDetail()" />
  <MonthlyRentForm ref="MonthlyRentFormRef" @success="getDetail()" />
  <carOrderDetail ref="CarOrderDetailRef" />
</template>
<script setup lang="ts">
defineOptions({ name: 'CashierDetail' })
import { OwnerApi } from '@/api/bus/owner'
import Check from './check.vue'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import ContractDetailDrawer from '@/views/contract/contractList/component/ContractDetailDrawer.vue'
import RoomStatusDetail from '@/views/village/managementCenter/RoomStatusDetail.vue'
import { ParkCarsApi } from '@/api/bus/parkCars'
import { DICT_TYPE, getDictLabel } from '@/utils/dict'
import storedValueForm from './storedValueForm.vue'
import MonthlyRentForm from './monthlyRentForm.vue'
import carOrderDetail from './carOrderDetail.vue'
const loading = ref(false)
const Owner = ref({})

const props = defineProps({
  id: {
    type: String,
    default: () => ''
  },
  typeId: {
    type: String,
    default: () => ''
  }
})
const carInfo = ref({})
const VehicleOperationRecord = ref([])
const getParkCarsOperatorLogParams = {
  pageNo: 1,
  pageSize: 10,
  carsId: props.id
}
const ParkCarOrderParams = {
  pageNo: 1,
  pageSize: 10,
  parkId: 1,
  carNumber: 0
}
const ParkCarsOperatorLogTotal = ref(0)
const carOrderList = ref([])
const ParkCarOrderTotal = ref(0)
const emit = defineEmits(['back', 'change', 'del', 'CarUpdate'])

//车辆订单详情
const CarOrderDetailRef = ref()
const openOrderForm = async (form) => {
  CarOrderDetailRef.value.open(form)
}
// 租客详情顶部数据接口
const getDetail = async () => {
  try {
    loading.value = true

    if (props.typeId == '3') {
      carInfo.value = await ParkCarsApi.getParkCars(props.id)
      ParkCarOrderParams.carNumber = carInfo.value.carNumber
      ParkCarOrderParams.parkId = carInfo.value.parkId
      getCarOrderList()
      getVehicleOperationRecord()
    } else {
      const res = await OwnerApi.getByIdOwnerInfo(props.id)
      console.log(res, '租客详情')
      Owner.value = res
    }
  } catch (error) {
    console.log(error)
  } finally {
    loading.value = false
  }
}
const StoredValueFormRef = ref()
const storedValue = async (form) => {
  StoredValueFormRef.value.open(form)
}
const MonthlyRentFormRef = ref()
const MonthlyRent = async (form) => {
  MonthlyRentFormRef.value.open(form)
}
const RoomStatusDetailRef = ref()

/**车辆操作记录 */
const getVehicleOperationRecord = async () => {
  try {
    loading.value = true
    const res = await ParkCarsApi.getParkCarsOperatorLog(getParkCarsOperatorLogParams)
    VehicleOperationRecord.value = res.list
    ParkCarsOperatorLogTotal.value = res.total
  } catch (error) {
    console.log(error)
  } finally {
    loading.value = false
  }
}
/**车辆订单记录 */
const getCarOrderList = async () => {
  try {
    loading.value = true
    const res = await ParkCarsApi.getParkCarOrder(ParkCarOrderParams)
    carOrderList.value = res.list
    ParkCarOrderTotal.value = res.total
  } catch (error) {
    console.log(error)
  } finally {
    loading.value = false
  }
}

const CarUpdate = () => {
  console.log('上传')
  emit('CarUpdate', carInfo.value)
}
const RoomDetail = (data) => {
  console.log(data, 'zuke ')
  // emit('change', data)
  RoomStatusDetailRef.value.open({
    id: data.id || data.roomId
  })
}
const CheckRef = ref()
const initData = () => {
  getDetail()
  console.log(CheckRef.value, 'CheckRef.value')
  // CheckRef.value.getCostTypeChildrenList()
}
const TenantDetailsRef = ref()
const OwnerDetails = (data) => {
  console.log(data, 'zuke ')
  TenantDetailsRef.value.open(data.id, data.isPersonal)
}
const ContractDetailDrawerRef = ref()
const ContactDetails = (data) => {
  console.log(Owner, 'zuke ', data)
  ContractDetailDrawerRef.value.open(data.contractId)
}

watch(
  () => props.id,
  (newVal, oldVal) => {
    console.log(newVal)
    if (newVal) {
      initData()
    }
  },
  {
    immediate: true,
    deep: true
  }
)
</script>
<style scoped>
.cashier-detail .el-descriptions__table tr {
  display: flex;
}
.cashier-detail .el-descriptions__content {
  flex: 1;
  word-break: break-all;
}

.cashier-detail .el-descriptions__label {
  width: 120px;
}
</style>
