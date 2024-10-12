<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 车辆订单编辑 -->
  <el-drawer class="addCar" v-model="CarOrderDetailShow" direction="rtl" size="70%" title="详情">
    <template #default>
      <el-form ref="formRef" :model="formData" label-position="top" label-width="100px" disabled="">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="订单名称" prop="carNumber">
              <el-input v-model="formData.orderName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订单号" prop="carNumber">
              <el-input v-model="formData.orderId" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称" prop="carNumber">
              <el-input v-model="formData.villageName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车场名称" prop="carNumber">
              <el-input v-model="formData.parkName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌号" prop="carNumber">
              <el-input v-model="formData.carNumber" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订单信息" prop="carNumber">
              <el-input v-model="formData.orderInfo" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订单金额" prop="carNumber">
              <el-input v-model="formData.orderMoney" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="支付时间" prop="carNumber">
              <el-input v-model="formData.payTime" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="支付方式" prop="carNumber">
              <el-input v-model="formData.payMethodTxt" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
defineOptions({ name: 'CarOrderDetail' })
const message = useMessage() // 消息弹窗
const formData = ref({})

const CarOrderDetailShow = ref(false)
function cancelClick() {
  CarOrderDetailShow.value = false
}

/** 打开抽屉 */
const open = async (form?: any) => {
  CarOrderDetailShow.value = true
  formData.value = form
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style scoped lang="scss">
::v-deep .el-drawer.rtl {
  background: #6aabc5;
}

.investmen-personne {
  width: 100%;
  // border: 1px solid #d9d9d9;
  border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
  padding: 5px 18px 4px 5px;
  cursor: pointer;
  line-height: 1.7;
  position: relative;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color)) inset;
}

.closeCircle {
  position: absolute;
  right: 2px;
  top: 0;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 16px;
  cursor: pointer;
}
</style>

<style>
.el-drawer__header {
  margin-bottom: 20px;
}
.el-drawer__body {
  padding-top: 0px;
}
</style>
