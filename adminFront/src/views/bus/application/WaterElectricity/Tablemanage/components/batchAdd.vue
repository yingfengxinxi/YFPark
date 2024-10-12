<template>
  <div v-loading="loading" class="min-h-[1200px]">
    <div>
      <el-button type="primary" plain @click="emit('goback')">
        <Icon icon="ep:arrow-left" class="mr-[6px]" />
        返回列表
      </el-button>
    </div>
    <div class="flex justify-between items-center mt-[10px]">
      <div
        class="w-[400px] bg-[#E6F7FF] py-[10px] px-[16px] border-[#2681F3] border-solid border rounded-[2px] flex items-center gap-[10px]"
      >
        <Icon icon="ep:info-filled" color="#2681F3" :size="14" />
        <div class="text-[#222526] text-[12px] leading-[20px]">
          仅能够添加已经对接成功的硬件设备，单一设备仅能绑定一个表。每次最多添加20项。
          <span class="text-[#FF4949]">红色</span>字体为必填项
        </div>
      </div>
      <el-button type="primary" plain @click="batchAdd"> 添加资产到设备 </el-button>
    </div>
    <el-table :data="list" border class="mt-[10px]">
      <el-table-column type="index" width="50" label="序号" align="center" />
      <el-table-column prop="deviceSerial" label="设备序列号" width="120" align="center" />
      <el-table-column prop="deviceType" label="设备类型" width="150" align="center">
        <template #header>
          <span class="text-[#FF4949]">设备类型</span>
        </template>
        <template #default="scope">
          <el-select
            v-model="scope.row.deviceType"
            placeholder="请选择"
            :disabled="!scope.row.propertyId"
          >
            <el-option
              v-for="(item, index) in getStrDictOptions('BAILING_ORG_CONFIG_DEVICE_TYPE')"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="表类型" width="150" align="center">
        <template #header>
          <span class="text-[#FF4949]">表类型</span>
        </template>
        <template #default="scope">
          <el-select
            v-model="scope.row.type"
            placeholder="请选择"
            :disabled="!scope.row.propertyId"
            @change="getpartList(scope.row)"
          >
            <el-option
              v-for="(item, index) in EnergyTypeList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="表名称" width="180" align="center">
        <template #header>
          <span class="text-[#FF4949]">表名称</span>
        </template>
        <template #default="scope">
          <el-input
            placeholder="请输入表名称"
            v-model="scope.row.name"
            :disabled="!scope.row.propertyId"
          />
        </template>
      </el-table-column>
      <el-table-column prop="number" label="表具编号" width="180" align="center">
        <template #header>
          <span class="text-[#FF4949]">表具编号</span>
        </template>
        <template #default="scope">
          <el-input
            placeholder="请输入表具编号"
            v-model="scope.row.number"
            :disabled="!scope.row.propertyId"
          />
        </template>
      </el-table-column>
      <el-table-column prop="purpose" label="用途" width="180" align="center">
        <template #header>
          <span class="text-[#FF4949]">用途</span>
        </template>
        <template #default="scope">
          <el-select
            v-model="scope.row.purpose"
            placeholder="请选择"
            :disabled="!scope.row.propertyId"
          >
            <el-option
              v-for="(item, index) in getStrDictOptions('PURPODE')"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="绑定位置范围" width="180" align="center">
        <template #header>
          <span class="text-[#FF4949]">绑定位置范围</span>
        </template>
        <template #default="scope">
          <div @click="selectroom(scope.$index)" class="min-w-[150px] min-h-[30px]">
            <span v-for="(item, index) in scope.row.builds" :key="index">
              {{ item.villageName }}/{{ item.buildName }}/{{ item.layerName }}/{{ item.roomName }},
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="magnification" label="倍率" width="120" align="center">
        <template #header>
          <span class="text-[#FF4949]">倍率</span>
        </template>
        <template #default="scope">
          <el-input
            placeholder="请输入"
            v-model="scope.row.magnification"
            type="number"
            :min="0"
            :disabled="!scope.row.propertyId"
          />
        </template>
      </el-table-column>
      <el-table-column prop="maxReading" label="最大读数" width="120" align="center">
        <template #header>
          <span class="text-[#FF4949]">最大读数</span>
        </template>
        <template #default="scope">
          <el-input
            placeholder="请输入"
            v-model="scope.row.maxReading"
            type="number"
            :min="0"
            :disabled="!scope.row.propertyId"
          />
        </template>
      </el-table-column>
      <el-table-column prop="originalReading" label="原始读数" width="120" align="center">
        <template #header>
          <span class="text-[#FF4949]">原始读数</span>
        </template>
        <template #default="scope">
          <el-input
            placeholder="请输入"
            v-model="scope.row.originalReading"
            type="number"
            :min="0"
            :disabled="!scope.row.propertyId"
          />
        </template>
      </el-table-column>
      <el-table-column prop="meterTime" label="抄录时间" width="180" align="center">
        <template #header>
          <span class="text-[#FF4949]">抄录时间</span>
        </template>
        <template #default="scope">
          <el-date-picker
            v-model="scope.row.meterTime"
            type="date"
            placeholder="请选择"
            style="width: 155px"
            :disabled="!scope.row.propertyId"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </template>
      </el-table-column>
      <el-table-column prop="publicType" label="公摊方式" width="160" align="center">
        <template #header>
          <span class="text-[#FF4949]">公摊方式</span>
        </template>
        <template #default="scope">
          <el-select
            v-model="scope.row.publicType"
            placeholder="请选择"
            v-if="scope.row.purpose === '3'"
          >
            <el-option
              v-for="item in getStrDictOptions('PUBLIC_TYPE1')"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-select
            v-model="scope.row.publicType"
            placeholder="请选择"
            v-if="scope.row.purpose === '2'"
          >
            <el-option
              v-for="item in getStrDictOptions('PUBLIC_TYPE')"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="partIds" label="关联分表" width="180" align="center">
        <template #default="scope">
          <el-select
            v-model="scope.row.partIds"
            placeholder="请选择"
            :disabled="!scope.row.propertyId"
            v-if="
              (scope.row.purpose == '2' && scope.row.publicType != '1') || scope.row.purpose == '3'
            "
          >
            <el-option
              v-for="(item, index) in scope.row.partList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100" align="center">
        <template #default="scope">
          <el-button type="danger" text size="mini" @click="handleEdit(scope.$index)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="flex justify-end mt-[10px]">
      <el-button type="primary" @click="submit"> 提交 </el-button>
    </div>
  </div>

  <batchAddTable ref="batchAddTableRef" @selectlist="selectlist" />
  <el-dialog v-model="dialogVisible" title="选择资产" width="40%">
    <el-scrollbar height="400px">
      <div class="border-[#E4E7ED] border border-solid min-h-[380px] rounded">
        <el-card shadow="never">
          <template #header>
            <div class="flex justify-between w-[100%] items-center">
              <span>{{ changeTree ? '选择楼层' : '已选楼层' }}</span>
              <el-button type="primary" text @click="changeTree = !changeTree">{{
                changeTree ? '查看已选' : '选择楼层'
              }}</el-button>
            </div>
          </template>
          <DeptTree_select
            :notdis="false"
            @changeData="changeData"
            ref="deptTree_selectRef"
            v-if="dialogVisible"
            :change_tree="changeTree"
          />
        </el-card>
      </div>
    </el-scrollbar>
    <template #footer>
      <div>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submithome">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { energyType } from '@/api/bus/WaterElectricity/EnergyType/index'
import DeptTree_select from '@/views/contract/contractList/component/DeptTree_select.vue'
import batchAddTable from './batchAddTable.vue'
import { TablemanageApi } from '@/api/bus/WaterElectricity/Tablemanage/index'
const message = useMessage() // 消息弹窗
const list = ref([])
const handleEdit = (index) => {
  list.value[index - 1] = {
    index: index + 1
  }
}
//提交
const submit = () => {
  let data = []
  let submitValue = true
  list.value.forEach((item) => {
    if (item.propertyId) {
      delete item.partList
      item.builds = JSON.stringify(item.builds)
      data.push(item)
    }
  })
  if (data.length == 0) {
    return
  } else {
    data.forEach((item) => {
      if (
        !item.deviceType ||
        !item.type ||
        !item.name ||
        !item.number ||
        !item.purpose ||
        !item.magnification ||
        !item.maxReading ||
        !item.originalReading ||
        !item.meterTime
      ) {
        submitValue = false
      }
    })
  }
  if (!submitValue) {
    message.error('红色字体为必填项,请填写完整')
    return
  }
  TablemanageApi.batchCreateTablemanage(data).then(() => {
    message.success('提交成功')
    emit('goback')
    list.value = []
  })
}
//表类型
const EnergyTypeList = ref([])
const getEnergyTypeList = async () => {
  energyType.getList().then(async (res) => {
    EnergyTypeList.value = res
  })
}
const dialogVisible = ref(false)
const changeTree = ref(true)
const changeDatahome = ref([])
const changeData = (data) => {
  changeDatahome.value = data
}
//选择房间
const nowIndex = ref(-1)
const submithome = () => {
  list.value[nowIndex.value].builds = changeDatahome.value
  list.value[nowIndex.value].villageId = changeDatahome.value[0].villageId
  list.value[nowIndex.value].buildId = changeDatahome.value[0].buildId
  list.value[nowIndex.value].layerIds = changeDatahome.value.map((item) => item.layerId).join(',')
  list.value[nowIndex.value].roomIds = changeDatahome.value.map((item) => item.id).join(',')
  getpartList(list.value[nowIndex.value], nowIndex.value)
  changeDatahome.value = []
  dialogVisible.value = false
}
const deptTree_selectRef = ref(null)
const selectroom = (index) => {
  if (list.value[index].propertyId) {
    nowIndex.value = index
    dialogVisible.value = true
    setTimeout(() => {
      deptTree_selectRef.value.getDeptTreeSelect(list.value[index].builds)
    }, 30)
  }
}
//选择资产
const selectlist = async (data) => {
  let index = -1
  list.value.forEach((item, i) => {
    if (!item.propertyId && index == -1) {
      index = i
      console.log(index, i)
    }
  })
  setTimeout(() => {
    for (let i = 0; i < data.length; i++) {
      list.value[index + i] = {
        name: data[i].name,
        number: data[i].propertyNumber,
        deviceSerial: data[i].propertyNumber,
        purpose: '1',
        propertyId: data[i].id,
        partList: []
      }
    }
  }, 300)
}
//获取关联分表
const getpartList = (row, index) => {
  if (!row.type || !row.villageId || !row.buildId) {
    return
  }
  TablemanageApi.getenergyList({
    type: row.type,
    villageId: row.villageId,
    buildId: row.buildId
  }).then((res) => {
    row.partList = res
  })
}
const batchAddTableRef = ref(null)
const batchAdd = () => {
  batchAddTableRef.value.open()
}
const emit = defineEmits(['goback'])
const loading = ref(false)
onMounted(async () => {
  loading.value = true
  for (let index = 0; index < 20; index++) {
    setTimeout(() => {
      list.value.push({
        index: index + 1,
        propertyId: undefined,
        partList: []
      })
    }, 200)
  }
  setTimeout(() => {
    loading.value = false
  }, 1000)
  getEnergyTypeList()
})
</script>
