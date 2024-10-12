<template>
  <el-drawer
    v-model="drawer"
    size="60%"
    style="background-color: #f5f5f5"
    :show-close="false"
    :with-header="false"
  >
    <template #header> </template>
    <div class="flex items-center gap-10px bg-#fff p-16px rounded">
      <span class="text-16px color-#000"> 处理工单 </span>
      <el-tag
        :type="
          getIntDictOptions('WORK_ORDER_STATUS').find(
            (item) => item.label == DetailData.workOrderStatusName
          )?.colorType
        "
      >
        {{ DetailData.workOrderStatusName }}</el-tag
      >
    </div>
    <div class="flex mt-16px rounded gap-16px">
      <div class="w-60% rounded overflow-hidden">
        <el-card shadow="never">
          <template #header>
            <span class="text-14px">工单详情</span>
          </template>
          <div>
            <el-row :gutter="10">
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">工单编号:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.number }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">工单类型:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.orderType }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">报修类型:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.repairTypeName }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">上报人:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.name }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">上报人手机:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.phone }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">所属楼宇:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.buildName || '--' }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">对应位置:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.position || '--' }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">工单来源:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.from || '--' }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">上报时间:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.appearTime || '--' }}
                  </span>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">上门时间:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.visitTime + `(${DetailData.dayOfWeek})` || '--' }}
                  </span>
                </div>
              </el-col>
              <el-col :span="24">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">完成时限:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.timeoutTime || '--' }}
                    <el-tag :type="DetailData.isTimeout == 0 ? 'primary' : 'danger'">
                      {{ DetailData.isTimeout == 0 ? '未超时' : '已超时' }}
                    </el-tag>
                  </span>
                </div>
              </el-col>
              <el-col :span="24">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">费用支付方:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important"
                    >{{ DetailData.paidPayerName || '--' }}
                  </span>
                </div>
              </el-col>
              <el-col :span="24">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">描述标签:</span
                  ><span
                    class="text-#5A5A75 flex flex-wrap gap-10px"
                    style="word-wrap: break-word !important"
                  >
                    <span v-if="!DetailData.labelJson"> -- </span>
                    <el-tag
                      v-for="(item, index) in DetailData.labelJson"
                      :key="index"
                      type="info"
                      class="mt-10px"
                    >
                      {{ item }}
                    </el-tag>
                  </span>
                </div>
              </el-col>
              <el-col :span="24">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">补充内容:</span
                  ><span class="text-#5A5A75" style="word-wrap: break-word !important">
                    {{ DetailData.remark || '--' }}
                  </span>
                </div>
              </el-col>
              <el-col :span="24">
                <div class="text-13px pb-20px">
                  <span class="whitespace-nowrap">上报内容:</span>
                  <div class="flex gap-10px flex-wrap">
                    <img
                      v-for="(item, index) in DetailData.images"
                      :key="index"
                      :src="item"
                      class="w-60px h-60px"
                    />
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
        <el-card
          shadow="never"
          class="mt-18px"
          v-if="DetailData.orderData.workOrderProposeOrderList.length"
        >
          <template #header>
            <div class="justify-between w-100% flex">
              <span class="text-14px">工单收费情况</span>
              <span class="text-14px font-600">
                已收款:<span class="color-#F56A00"
                  >{{ DetailData?.orderData?.actualPayment }}元</span
                >&nbsp;总金额:<span class="color-#2681F3"
                  >{{ DetailData?.orderData?.totalAmount }}元</span
                >
              </span>
            </div>
          </template>
          <div
            v-for="(item, index) in DetailData.orderData.workOrderProposeOrderList"
            :key="index"
            class="flex justify-between items-center text-14px"
          >
            <span
              >{{ item.orderType }}&nbsp;<span
                class="bg-#f00 text-12px text-#fff px-5px rounded py-2px"
                v-if="item.orderStatus == '待支付'"
                >{{ item.orderStatus }}</span
              ></span
            >
            <span>{{ item.needAmount + '元' }}</span>
          </div>
        </el-card>
      </div>
      <div class="w-40% bg-#fff">
        <el-card shadow="never">
          <template #header>
            <span class="text-14px">处理记录</span>
          </template>
        </el-card>
        <!-- 步骤条 -->
        <div class="step pl-10px">
          <div
            class="stepitem pb-30px min-h-150px"
            v-for="item in DetailData.orderProposeLogList"
            :key="item"
          >
            <div class="flex gap-10px">
              <span
                class="w-8px h-8px border-blue border-solid rounded-50% transform-translate-y-9px"
              >
              </span>
              <div class="leading-30px">
                <div class="text-13px" v-if="item.operateType">类型: {{ item.operateType }}</div>
                <div class="text-13px" v-if="item.operateTime">时间: {{ item.operateTime }}</div>
                <div class="text-13px" v-if="item.name || item.phone"
                  >处理人: {{ item.name }} {{ item.phone }}</div
                >
                <div class="text-13px" v-if="item.remark&&item.operateType == '直接回复'">回复内容:{{ item.remark }}</div>
                <div class="text-13px flex flex-col items-start" v-if="item.images"
                  >图例:
                  <div class="flex gap-10px">
                    <img
                      v-for="(items, i) in item.images"
                      :key="i"
                      :src="items"
                      class="w-60px h-60px rounded-2px"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 处理中
    <div class="mt-16px bg-#fff h-160px rounded flex flex-col gap-10px items-center justify-center">
      <div class="bg-#E6F7FF w-36px h-36px rounded-50% pos-relative">
        <Icon
          icon="ep:clock"
          color="#1890FF"
          :size="40"
          class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%"
        />
      </div>
      <div class="text-16px">由 {{ '李海华' }} 处理中</div>
      <div class="text-12px color-#8C8C8C">处理时间355小时47分钟</div>
    </div> -->
    <!-- 操作 -->
    <div
      class="mt-16px bg-#fff h-200px rounded"
      v-if="
        getIntDictOptions('WORK_ORDER_STATUS').find(
          (item) => item.label == DetailData.workOrderStatusName
        )?.value == 1
      "
    >
      <el-tabs tab-position="left" style="height: 200px" class="demo-tabs" v-model="tabsValue">
        <el-tab-pane label="指派给" name="1">
          <div class="w-100% h-200px pos-relative">
            <el-form class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%">
              <el-form-item label="物业人员:">
                <el-input
                  v-model="UserData.nickname"
                  placeholder="请选择人员"
                  style="width: 220px"
                />
                <div
                  class="w-100% h-100% z-999 pos-absolute pos-top-0 pos-left-0 cursor-pointer"
                  @click="selectUser"
                ></div>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
        <el-tab-pane label="直接回复" name="2">
          <div class="p-20px">
            <el-form>
              <el-form-item label="回复内容:">
                <el-input
                  type="textarea"
                  v-model="recoverData.remark"
                  placeholder="请输入回复内容"
                />
              </el-form-item>
              <el-form-item label="上报图例">
                <div
                  v-for="(item, index) in recoverData.images"
                  :key="index"
                  class="pos-relative imgBox"
                >
                  <img :src="item" class="w-80px h-80px" />

                  <div class="pos-del">
                    <Icon
                      icon="ep:delete"
                      color="#fff"
                      class="pos-absolute pos-top-50% pos-left-50% transform-translate--50% cursor-pointer"
                      @click="delImage(index)"
                    />
                  </div>
                </div>
                <el-upload
                  class="uploader_BOX"
                  :on-change="faceChange"
                  :show-file-list="false"
                  :auto-upload="false"
                  accept="image/*"
                >
                  <img src="@/views/bus/owner/component/image/plus.png" class="plus" alt="" />
                </el-upload>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 提交 -->
    <div
      class="flex justify-end w-100% bg-#fff p-10px mt-16px"
      v-if="
        getIntDictOptions('WORK_ORDER_STATUS').find(
          (item) => item.label == DetailData.workOrderStatusName
        )?.value == 1
      "
    >
      <el-button @click="drawer.value">取消</el-button>
      <el-button
        type="primary"
        @click="submit"
        v-hasPermi="['bus:workOrderPropose:assignWorkOrder']"
        >保存</el-button
      >
    </div>
  </el-drawer>
  <userSelect ref="UserformRef" @submit="Usersubmit" />
</template>
<script lang="ts" setup>
const drawer = ref(false)
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { OwnerApi } from '@/api/bus/owner'

import { workOrderProposeApi } from '@/api/bus/Category/workOrderList/index'
import userSelect from '@/views/bus/application/conference/conferenceSettings/compontent/userSelect.vue'

const emit = defineEmits(['submit'])
const DetailData = ref({})
const RowData = ref({})
const UserData = ref({})
const message = useMessage() // 消息弹窗
const UserformRef = ref()
const selectUser = () => {
  UserformRef.value.open('', 'dan')
}
const recoverData = ref({
  remark: '',
  images: []
})
const tabsValue = ref('1')
const Usersubmit = (res) => {
  UserData.value = res
}
//上传logo
const faceChange = (file: any) => {
  const faceimg = new FormData()
  faceimg.append('file', file.raw)
  OwnerApi.uploadFile(faceimg).then((res) => {
    recoverData.value.images.push(res.data)
  })
}
const delImage = (index: number) => {
  recoverData.value.images.splice(index, 1)
}
const submit = () => {
  if (tabsValue.value == '1') {
    if (!UserData.value.id) {
      message.error('请选择物业人员')
      return
    }
    workOrderProposeApi
      .assignWorkOrder({
        id: DetailData.value.id,
        uid: UserData.value.id
      })
      .then((res) => {
        message.success('指派成功')
        drawer.value = false
        emit('submit')
      })
  } else if (tabsValue.value == '2') {
    if (!recoverData.value.remark) {
      message.error('请输入回复内容')
      return
    }
    workOrderProposeApi
      .handelWorkOrder({
        id: DetailData.value.id,
        remark: recoverData.value.remark.toString(),
        images: recoverData.value.images.join(',')
      })
      .then((res) => {
        message.success('回复成功')
        drawer.value = false
        emit('submit')
      })
  }
}
const resetForm = () => {
  recoverData.value = {
    remark: '',
    images: []
  }
  UserData.value = {}
}
function open(id: string, row: any) {
  resetForm()
  RowData.value = row
  workOrderProposeApi.getDetail(id).then((res) => {
    DetailData.value = res
    DetailData.value.id = id
    if (DetailData.value.labelJson) {
      DetailData.value.labelJson = DetailData.value.labelJson.split(',')
    }
    if (DetailData.value.orderProposeLogList) {
      DetailData.value.orderProposeLogList.forEach((item) => {
        if (item.images) {
          item.images = item.images.split(',')
        }
      })
    }
    if (DetailData.value.orderData) {
      DetailData.value.orderData = JSON.parse(DetailData.value.orderData)
    }
    drawer.value = true
  })
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped>
.stepitem {
  position: relative;
  &::after {
    content: '';
    position: absolute;
    top: 22px;
    left: 5px;
    width: 1px;
    height: calc(100% - 12px);
    background-color: #d2d2d2;
  }
  &:last-child::after {
    display: none;
  }
}
.pos-del {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: none;
}
.imgBox {
  width: 80px;
  height: 80px;
  border-radius: 4px;
  overflow: hidden;
  &:hover {
    .pos-del {
      display: block;
    }
  }
}
.uploader_BOX {
  background: #f7f7f7;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 80px;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  .img {
    width: 100%;
    height: 100%;
  }
  .plus {
    width: 40px;
    height: 40px;
  }
}
</style>
