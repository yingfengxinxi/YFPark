<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-drawer title="抄表记录" v-model="dialogVisible" size="30%">
    <!-- 上次抄录 -->
    <div
      class="text-[14px] pl-[8px] !border-l-[4px] border-0 border-solid border-[#2681F3] font-semibold"
      >上次抄表记录</div
    >
    <el-form label-position="top" class="mt-[20px]">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="上次抄表时间">
            <el-date-picker
              v-model="detailData.lastTime"
              type="date"
              placeholder="上次抄表时间"
              disabled
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上次读数">
            <el-input v-model="detailData.lastNumber" placeholder="上次读数" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上次用量">
            <el-input v-model="detailData.lastUse" placeholder="上次用量" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <!-- 本次抄录 -->
    <div
      class="text-[14px] pl-[8px] !border-l-[4px] border-0 border-solid border-[#2681F3] font-semibold mt-[20px]"
      >本次抄表记录</div
    >
    <el-form label-position="top" class="mt-[20px]">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item
            label="本次抄表时间"
            :rules="[{ required: true, message: '请选择本次抄表时间' }]"
          >
            <el-date-picker
              v-model="detailData.thisTime"
              type="date"
              placeholder="本次抄表时间"
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="本次读数"
            :rules="[{ required: true, message: '请选择本次抄表时间' }]"
          >
            <el-input
              v-model="detailData.thisNumber"
              placeholder="本次读数"
              @blur="checkNumber"
              type="number"
            />
          </el-form-item>
          <div class="text-[#f00] text-[12px] leading-[24px] flex" v-if="WarningData.isForewarning">
            <div class="h-[24px] pos-relative mr-[6px]">
              <Icon
                icon="fa:question-circle-o"
                color="#f00"
                :size="14"
                class="pos-absolute pos-top-[50%] transform-translate-y-[-60%]"
              />
            </div>
            本次抄录使用量相对于上次抄表使用量{{
              detailData.thisNumber > detailData.lastNumber ? '增加' : '减少'
            }}了{{ WarningData.scale }}%,请您注意!
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="本次用量"
            :rules="[{ required: true, message: '请选择本次抄表时间' }]"
          >
            <el-input v-model="detailData.thisUse" placeholder="本次用量" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="上传抄表图片">
            <div class="pos-relative imgBox">
              <el-upload
                class="uploader_BOX"
                :on-change="image_change"
                :show-file-list="false"
                :auto-upload="false"
              >
                <img v-if="detailData.image" :src="detailData.image" alt="" class="img" />
                <img src="@/views/bus/owner/component/image/plus.png" v-else class="plus" alt="" />
              </el-upload>
              <div class="pos-del" v-if="detailData.image">
                <Icon
                  icon="ep:delete"
                  color="#fff"
                  class="pos-absolute pos-top-[50%] pos-left-[50%] transform-translate--50% cursor-pointer"
                  @click="delImage(index)"
                />
              </div>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 保存按钮 -->
    <template #footer>
      <div class="flex justify-end">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">确认</el-button>
      </div>
    </template>
  </el-drawer>
</template>
<script lang="ts" setup>
import { OwnerApi } from '@/api/bus/owner'
import { energyPlanApi, VO } from '@/api/bus/WaterElectricity/EnergyPlan/index.ts'
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false)
const form = ref({
  imageUrl: ''
})
const value1 = ref('')
//上传图片
const image_change = (res) => {
  const fileForm = new FormData()
  fileForm.append('file', res.raw)
  OwnerApi.uploadFile(fileForm).then((res) => {
    detailData.value.image = res.data
  })
}
const delImage = () => {
  form.value.imageUrl = ''
}
//是否超出预警
const WarningData = ref({
  isForewarning: false
})
const checkNumber = () => {
  detailData.value.thisUse = detailData.value.thisNumber - detailData.value.lastNumber
  energyPlanApi
    .misreadingForewarning({
      currentDosage: detailData.value.thisNumber || 0,
      lastDosage: detailData.value.lastNumber || 0
    })
    .then((res) => {
      WarningData.value = res
    })
}
const rowData = ref({})
const detailData = ref({})
function open(row) {
  resetForm()
  rowData.value = row
  dialogVisible.value = true
  energyPlanApi
    .completeTaskList({
      energyId: row.energyId
    })
    .then((res) => {
      detailData.value = res
    })
}
defineExpose({ open })
const submit = () => {
  energyPlanApi
    .energyRecordcreate({
      energyId: rowData.value.energyId,
      energyTaskId: rowData.value.energyTaskId,
      isMobile: 0,
      thisTime: detailData.value.thisTime,
      thisNumber: detailData.value.thisNumber,
      thisUse: detailData.value.thisUse,
      lastTime: detailData.value.lastTime,
      lastNumber: detailData.value.lastNumber,
      lastUse: detailData.value.lastUse,
      image: form.value.imageUrl
    })
    .then((res) => {
      message.success('保存成功')
      dialogVisible.value = false
    })
}
const resetForm = () => {
  detailData.value = {}
  rowData.value = {}
}
</script>
<style lang="scss" scoped>
.uploader_BOX {
  background: #f7f7f7;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 110px;
  height: 110px;
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
.line {
  line-height: 55px;
}
.userSpan {
  border: 1px solid #999;
  padding: 0 15px;
  background-color: #fff;
  border-radius: 20px;
  display: flex;
  align-items: center;
}
.del_icon {
  transform: translateY(1px);
  cursor: pointer;
}
.imgBox {
  width: 110px;
  height: 110px;
  border-radius: 4px;
  overflow: hidden;
  &:hover {
    .pos-del {
      display: block;
    }
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
</style>
