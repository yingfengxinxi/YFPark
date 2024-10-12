<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="bg-[#fff]">
    <div class="flex justify-between items-center p-[20px] px-[20px]">
      <div class="flex items-center gap-[10px]">
        <Icon icon="ep:arrow-left" :size="20" class="cursor-pointer" @click="emit('goback')" />
        <span class="font-semibold text-[18px]">吉美二号厂房卫生间水表</span>
      </div>
      <div class="flex items-center">
        <el-button type="danger" plain>删除</el-button>
        <el-button type="primary" plain>编辑</el-button>
        <el-dropdown class="ml-[10px]">
          <el-button>
            更多
            <Icon icon="ep:arrow-down" class="ml-[5px]" />
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>重置水表</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    {{ rowData }}
    <div class="flex justify-between px-[20px] gap-[20px] pb-[20px]">
      <Icon icon="ep:odometer" color="#707070" class="mt-[20px]" :size="50" />
      <div class="flex-1">
        <el-row :gutter="20">
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">表种类:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">用途:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">设备名称:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]" />
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">最大读数:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">倍率:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">备注:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]" />
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">项目:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">楼宇:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]">
            <div class="text-[16px] text-[#999]">楼层/房间:</div>
            <div class="mt-[6px] text-[18px]">--</div>
          </el-col>
          <el-col :span="6" class="mt-[20px]" />
        </el-row>
      </div>
      <div class="flex flex-col items-end">
        <div class="text-[14px] text-[#999]">状态:</div>
        <div class="text-[25px] mt-[14px] text-[#87D068]">正常</div>
      </div>
    </div>
    <!-- tab切换 -->
    <div class="flex gap-[20px] px-[20px]">
      <div :class="topChange == 0 ? 'active' : 'unactive'" @click="clickTop(0)"> 用量列表 </div>
      <div :class="topChange == 1 ? 'active' : 'unactive'" @click="clickTop(1)"> 账单详情 </div>
    </div>
  </div>
  <div class="flex mt-[10px] gap-[10px]">
    <div class="min-w-[120px]">
      <div :class="bottomChange == 0 ? 'leftActive' : 'leftunActive'" @click="clickBottom(0)">
        分摊记录
      </div>
      <div :class="bottomChange == 1 ? 'leftActive' : 'leftunActive'" @click="clickBottom(1)">
        抄表记录
      </div>
      <div :class="bottomChange == 2 ? 'leftActive' : 'leftunActive'" @click="clickBottom(2)">
        绑定分表
      </div>
      <div :class="bottomChange == 3 ? 'leftActive' : 'leftunActive'" @click="clickBottom(3)">
        充值记录
      </div>
      <div :class="bottomChange == 4 ? 'leftActive' : 'leftunActive'" @click="clickBottom(4)">
        读表记录
      </div>
      <div :class="bottomChange == 5 ? 'leftActive' : 'leftunActive'" @click="clickBottom(5)">
        操作记录
      </div>
    </div>
    <div class="flex-1 h-[100%] box-border">
      <el-card shadow="never" v-if="bottomChange == 2">
        <template #header>
          <div class="flex justify-between items-center">
            <span>绑定分类</span>
            <el-button type="primary" plain>添加</el-button>
          </div>
        </template>
        <div class="min-h-[200px] width">
          <el-table>
            <el-table-column label="表名称" align="center" />
            <el-table-column label="分摊比例" align="center" />
            <el-table-column label="最近抄表时间" align="center" />
            <el-table-column label="操作" align="center" />
          </el-table>
        </div>
      </el-card>
      <el-card shadow="never" v-if="bottomChange == 0">
        <template #header>
          <span>分摊记录</span>
        </template>
        <div class="min-h-[200px] width">
          <el-table>
            <el-table-column label="表名称" align="center" />
            <el-table-column label="起始时间" align="center" />
            <el-table-column label="结束时间" align="center" />
            <el-table-column label="分摊用量" align="center" />
            <el-table-column label="状态" align="center" />
            <el-table-column label="绑定账单" align="center" />
          </el-table>
        </div>
      </el-card>
      <el-card shadow="never" v-if="bottomChange == 1">
        <template #header>
          <div class="flex justify-between items-center">
            <span>抄表记录</span>
            <el-button type="primary" plain>抄表</el-button>
          </div>
        </template>
        <div class="min-h-[200px] width">
          <el-table>
            <el-table-column label="本次计费起始时间" align="center" />
            <el-table-column label="本次抄录时间" align="center" />
            <el-table-column label="上次抄录读表" align="center" />
            <el-table-column label="本次抄录读表" align="center" />
            <el-table-column label="水表用量" align="center" />
            <el-table-column label="负责人" align="center" />
            <el-table-column label="抄表快照" align="center" />
            <el-table-column label="操作" align="center" />
          </el-table>
        </div>
      </el-card>
      <el-card shadow="never" v-if="bottomChange == 3">
        <template #header>
          <span>充值记录</span>
        </template>
        <div class="min-h-[200px] width">
          <el-table>
            <el-table-column label="充值金额" align="center" />
            <el-table-column label="充值度数" align="center" />
            <el-table-column label="充值时间" align="center" />
            <el-table-column label="充值状态" align="center" />
            <el-table-column label="流水号" align="center" />
            <el-table-column label="账单号" align="center" />
          </el-table>
        </div>
      </el-card>
      <el-card shadow="never" v-if="bottomChange == 4">
        <template #header>
          <div class="flex justify-between items-center">
            <span>读表记录</span>
            <div>
              <el-button @click="rotateBox">
                <div id="refreshBtn" class="mr-[4px]">
                  <Icon icon="ep:refresh" />
                </div>
                刷新
              </el-button>
              <el-button type="primary" plain @click="callbackRecord">立即读表</el-button>
            </div>
          </div>
        </template>
        <div class="min-h-[200px] width">
          <el-table :data="callbackRecordList" v-loading="loading">
            <el-table-column label="读表时间" align="center" prop="saveTime" />
            <el-table-column label="读表用量" align="center" prop="reading" />
            <el-table-column label="剩余用量" align="center" prop="remaining" />
            <el-table-column label="通断状态" align="center" prop="isOff" />
            <el-table-column label="操作时间" align="center" prop="saveTime" />
            <el-table-column label="操作" align="center">
              <template #default="scope">
                <el-button
                  v-if="scope.row.isSq"
                  @click="callbackRecordabandon(scope.row.id)"
                  type="danger"
                  plain
                  >舍弃</el-button
                >
                <span v-else>--</span>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <Pagination
            :total="callbackRecordtotal"
            v-model:page="queryParams.pageNo"
            v-model:limit="queryParams.pageSize"
            @pagination="getList"
          />
        </div>
      </el-card>
      <el-card shadow="never" v-if="bottomChange == 5">
        <template #header>
          <span>操作记录</span>
        </template>
        <div class="min-h-[200px] width">
          <el-table>
            <el-table-column label="原因" align="center" prop="reason" />
            <el-table-column label="处理人" align="center" prop="operateName" />
            <el-table-column label="操作类型" align="center" prop="status">
              <template #default="scope">
                <span>关闸</span>
              </template>
            </el-table-column>
            <el-table-column label="操作时间" align="center" />
          </el-table>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { TablemanageApi } from '@/api/bus/WaterElectricity/Tablemanage/index'
import { ElMessage, ElMessageBox } from 'element-plus'

const message = useMessage()

let rotationAngle = 0
const rotateBox = () => {
  const box = document.getElementById('refreshBtn')
  rotationAngle += 360 // 每次点击增加360度
  box.style.transform = `rotate(${rotationAngle}deg)`
  getCallbackRecord()
}
const rowData = ref({})
const detailDate = ref({})
function open(row: any) {
  rowData.value = row
  TablemanageApi.getTablemanageDetail({
    id: row.id
  }).then((res) => {
    detailDate.value = res
    getList()
  })
}
defineExpose({ open })
const emit = defineEmits(['goback'])
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  energyId: undefined
})
const callbackRecordtotal = ref(0)
/***
 * 读表记录
 */

const loading = ref(false)
const callbackRecordList = ref([])
const getCallbackRecord = () => {
  loading.value = true
  queryParams.value.energyId = rowData.value.id
  TablemanageApi.getcallbackRecordPage(queryParams.value).then((res) => {
    setTimeout(() => {
      loading.value = false
    }, 300)
    callbackRecordList.value = res.list
    callbackRecordtotal.value = res.total
  })
}
//舍弃记录
const callbackRecordabandon = (id) => {
  ElMessageBox.confirm('确认要舍弃最新的一条吗?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    message.success('操作成功')
  })
}
//立即读表
const callbackRecord = () => {
  ElMessageBox.confirm('确认要立即读表吗?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    TablemanageApi.callbackNow({
      energyId: rowData.value.id
    }).then(() => {
      message.success('操作成功')
      getCallbackRecord()
    })
  })
}
const getList = () => {
  getCallbackRecord()
}
onMounted(() => {
  getList()
})
/**
 * 操作记录
 */

/**
 * 顶部底部切换
 */
const topChange = ref(0)
const bottomChange = ref(0)
const clickTop = (index) => {
  topChange.value = index
}
const clickBottom = (index) => {
  bottomChange.value = index
}
</script>
<style lang="scss" scoped>
.active {
  font-size: 18px;
  color: #1890ff;
  padding-bottom: 10px;
  border-bottom: 2.5px solid #1890ff;
  cursor: pointer;
  user-select: none;
}
.unactive {
  font-size: 18px;
  padding-bottom: 10px;
  border-bottom: 2.5px solid #fff;
  cursor: pointer;
  user-select: none;
}
.leftActive {
  text-align: center;
  margin: 10px 0;
  border-right: 2px solid #1890ff;
  padding: 6px 0;
  color: #1890ff;
  font-size: 15px;
  cursor: pointer;
  user-select: none;
}
.leftunActive {
  text-align: center;
  margin: 10px 0;
  border-right: 2px solid #f5f7f9;
  padding: 6px 0;
  font-size: 15px;
  cursor: pointer;
  user-select: none;
}
.width {
  width: calc(100% - 10px);
  box-sizing: border-box;
}
#refreshBtn {
  transition: transform 0.5s ease-in-out;
  user-select: none;
}
</style>
