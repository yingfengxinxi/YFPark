<template>
  <div>
    <ContentWrap class="flex items-center" v-if="!tableShow">
      <span class="text-16px font-600">人员管理</span>
    </ContentWrap>
    <!-- 列表 -->
    <ContentWrap v-if="!tableShow">
      <div class="bg-#fff flex justify-between items-center">
        <div class="flex gap-10px h-40px w-300px">
          <div class="w-40px h-40px bg-#FF4000 rounded pos-relative">
            <Icon
              icon="fa:user-circle"
              :size="30"
              color="#fff"
              class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%"
            />
          </div>
          <div class="h-100% flex flex-col justify-between">
            <span class="text-#262626 text-14px font-600">管理员</span>
            <span class="text-12px">管理员拥有全部权限</span>
          </div>
        </div>
        <el-button type="primary" text @click="PageList(dataList[0])">
          {{ dataList[0].count }}
        </el-button>
        <el-button
          type="primary"
          text
          @click="addUser(dataList[0])"
          v-hasPermi="['bus:workOrderAdmin:create']"
        >
          添加人员
        </el-button>
      </div>
      <div class="w-100% h-1px bg-#E6E6E6 my-18px"></div>
      <div class="bg-#fff flex justify-between items-center">
        <div class="flex gap-10px h-40px w-300px">
          <div class="w-40px h-40px bg-#00C9FF rounded pos-relative">
            <Icon
              icon="fa:whatsapp"
              :size="30"
              color="#fff"
              class="pos-absolute pos-top-50% pos-left-50% transform-translate--50%"
            />
          </div>
          <div class="h-100% flex flex-col justify-between">
            <span class="text-#262626 text-14px font-600">客服工作人员</span>
            <span class="text-12px">可执行工单中心的派单、回复操作</span>
          </div>
        </div>
        <el-button type="primary" text @click="PageList(dataList[1])">
          {{ dataList[1].count }}
        </el-button>
        <el-button
          type="primary"
          text
          @click="addUser(dataList[1])"
          v-hasPermi="['bus:workOrderAdmin:create']"
        >
          添加人员
        </el-button>
      </div>
    </ContentWrap>
    <Form ref="formRef" @success="getList" />
    <Table ref="tableRef" @success="backTable" v-show="tableShow" />
  </div>
</template>
<script lang="ts" setup>
//管理员操作
import { workOrderAdminApi } from '@/api/bus/Category/UserSstting/index'
//添加弹窗
import Form from './subpage/Form.vue'
//表格
import Table from './subpage/page.vue'
const message = useMessage() // 消息弹窗

const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
const dataList = ref([
  {
    count: 0
  },
  {
    count: 0
  }
])
onMounted(async () => {
  await getapplication()
  getList()
})
const getList = () => {
  workOrderAdminApi
    .getadminCensus({
      application: application.value
    })
    .then((res) => {
      dataList.value = res
      dataList.value.forEach((item) => {
        item.application = application.value
      })
    })
}
const formRef = ref()
const addUser = (res) => {
  formRef.value.openForm(res, application.value)
}
const tableRef = ref()
const PageList = (res) => {
  tableShow.value = true
  tableRef.value.openForm(res, application.value)
}
const tableShow = ref(false)
const backTable = () => {
  tableShow.value = false
  getList()
}
</script>
