<template>
  <div class="page mb-18px p-10px bg-white">
    <el-select v-model="vallageId" class="w-280px" style="width: 280px" @change="getcategoryType">
      <el-option
        v-for="(item, index) in project_list"
        :key="index"
        :label="item.name"
        :value="item.id"
      />
    </el-select>
  </div>
  <div class="conference_page">
    <el-button class="addconference_button" type="primary" @click="addConference_Category"
      >添加会议室分类</el-button
    >
    <el-tabs
      v-model="headerSelect"
      class="demo-tabs p-b"
      @tab-click="changeHeaderSelect"
      v-if="categoryType_list.length"
    >
      <el-tab-pane
        v-for="(item, index) in categoryType_list"
        :key="index"
        :label="item.name"
        :name="index"
      >
        <div class="pane_detail">
          <div class="flex justify-between items-center">
            <div class="flex gap-16px">
              <span class="font-600">{{ item.name }}</span>
              <el-tag type="primary" v-if="item.status == '1'">开启</el-tag>
              <el-tag type="danger" v-else-if="item.status == '0'">停用</el-tag>
            </div>
            <div>
              <el-button type="primary" plain @click="show_img(item.qrCode)">预定二维码</el-button>
              <el-button type="primary" plain @click="changeCategory_status(item)">
                {{ item.status == '1' ? '停用' : '开启' }}
              </el-button>
              <el-button type="primary" plain @click="addConference_Category(item.id, 'editor')"
                >编辑</el-button
              >
              <el-button type="danger" plain @click="deletefun(item.id)">删除</el-button>
            </div>
          </div>
          <div class="row flex justify-between items-center mt-18px">
            <div class="text-14px text-center w-full border-right"
              >每日预约时间范围:&nbsp;{{ item.reservationRule_js.timeframe.start_time }}~{{
                item.reservationRule_js.timeframe.end_time
              }}</div
            >
            <div class="text-14px text-center w-full border-right"
              >预约时长间隔:&nbsp;{{ item.reservationRule_js.interval }}小时</div
            >
            <div class="text-14px text-center w-full border-right"
              >可提前{{ item.reservationRule_js.earliest_time }}天预定</div
            >
            <div class="text-14px text-center w-full border-right"
              >开放当天可与{{ item.reservationRule_js.booking_time }}开始预定</div
            >
            <div class="text-14px text-center w-full">最多{{ item.capacity }}人进场</div>
          </div>
        </div>
        <div class="mt-28px">
          <el-row :gutter="18">
            <el-col :span="6">
              <el-input placeholder="搜索会议室名称" v-model="form.name">
                <template #append>
                  <Icon icon="ep:search" @click="getresvplace_func" />
                </template>
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-select v-model="form.status" placeholder="状态" @change="getresvplace_func">
                <el-option label="开启" value="1" />
                <el-option label="停用" value="0" />
              </el-select>
            </el-col>
            <el-col :span="14">
              <div class="flex justify-end w-full">
                <el-button type="primary" @click="addConference_Add(item.cache, item.id, 'add')"
                  >添加会议室</el-button
                >
              </div>
            </el-col>
          </el-row>
          <el-table class="mt-18px" :data="getresvplace_list">
            <el-table-column prop="name" label="会议室名称" align="center" />
            <el-table-column prop="address" label="具体位置" align="center" />
            <el-table-column prop="sort" label="排序" align="center" />
            <el-table-column prop="status" label="会议室状态" align="center">
              <template #default="{ row }">
                <el-tag v-if="row.status == '1'" type="success">开启</el-tag>
                <el-tag v-else-if="row.status == '0'" type="danger">停用</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              prop="rentalArea"
              fixed="right"
              width="240px"
              label="操作"
              align="center"
            >
              <template #default="{ row }">
                <el-button type="primary" text plain @click="changstatusfun_list(row)">{{
                  row.status == 1 ? '停用' : '开启'
                }}</el-button>
                <el-button
                  type="primary"
                  text
                  plain
                  @click="addConference_Add(item.cache, item.id, 'editor', row)"
                  >编辑</el-button
                >
                <el-button type="danger" text plain @click="deletefun_list(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-empty description="暂无数据" v-else />
  </div>
  <!-- 会议室分类 -->
  <Category ref="Category_ref" @getList="getcategoryType()" />
  <!-- 添加会议室 -->
  <Add ref="Add_ref" @success="addSuccess" />
  <!-- 预定二维码 -->
  <el-dialog v-model="show_img_dialog" title="预定二维码" width="500" :before-close="handleClose">
    <div class="text-center">
      <img :src="imgUrl" alt="" class="w-120px h-120px" />
    </div>
    <div class="text-center mt-20px">
      <el-button type="primary" text @click="downLoad_img"> 下载 </el-button>
    </div>
  </el-dialog>
</template>
<script lang="ts" setup>
import { conferenceApi } from '@/api/conference/index'
import { FloorApi } from '@/api/bus/village/floor'
import Category from './component/conference_category.vue'
import Add from './component/conference_add.vue'
import { onMounted } from 'vue'
import { ElMessageBox } from 'element-plus'
import { status } from 'nprogress'
const message = useMessage() // 消息弹窗

const headerSelect = ref<number>(0)
const vallageId = ref<string>('')
const changeHeaderSelect = (index: number) => {
  headerSelect.value = index.props.name
  getresvplace_func()
}
// 添加会议室分类
const Category_ref = ref<HTMLElement>()
const addConference_Category = (id, type) => {
  Category_ref.value.open(id, type, vallageId.value)
}

// 添加会议室
const Add_ref = ref<HTMLElement>()
const addConference_Add = (facilities, id, type, row) => {
  Add_ref.value.open(facilities, id, type, vallageId.value, row)
}
//获得会议室分类列表
const categoryType_list = ref<any>([])
const getcategoryType = async () => {
  const data = await conferenceApi.getcategoryType({
    villageId: vallageId.value,
    appSign: form.value.appSign
  })
  categoryType_list.value = data.list
  categoryType_list.value.map((res) => {
    res.reservationRule_js = JSON.parse(res.reservationRule)
  })
  getresvplace_func()
}
//获得项目列表
const project_list = ref<any>([])
const getProjectList = async () => {
  project_list.value = await FloorApi.getVillageList()
  vallageId.value = project_list.value[0].id || ''
  getcategoryType()
}
//展示预定二维码
const show_img_dialog = ref(false)
const imgUrl = ref('')
const show_img = (url) => {
  show_img_dialog.value = true
  imgUrl.value = url
}
const downLoad_img = async () => {
  try {
    const response = await fetch(imgUrl.value)
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    const blob = await response.blob()
    const url = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = '预定二维码.png' // 设置下载文件名
    document.body.appendChild(a) // 将链接添加到文档中
    a.click() // 模拟点击链接
    a.remove() // 点击后移除链接
    window.URL.revokeObjectURL(url) // 释放 URL 对象
  } catch (error) {
    console.error('下载文件时发生错误:', error)
  }
}
//删除会议室分类
const deletefun = (id) => {
  // console.log(id)
  ElMessageBox.confirm('此操作将永久删除该分类, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    conferenceApi.deletecategory(id).then((res) => {
      message.success('删除成功')
      getcategoryType()
      headerSelect.value = 0
    })
  })
}
//停用会议室分类
const changeCategory_status = (data) => {
  let title = ''
  if (status == 1) {
    title = '此操作将停用该分类, 是否继续?'
  } else {
    title = '此操作将开启该分类, 是否继续?'
  }
  ElMessageBox.confirm(title, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    if (data.status == 1) {
      data.status = 0
    } else {
      data.status = 1
    }
    conferenceApi.updatecategory(data).then((res) => {
      message.success('修改成功')
      getcategoryType()
    })
  })
}
onMounted(() => {
  getProjectList()
  // getcategoryType()
})
const form = ref({
  appSign: 'metting',
  pageNo: 1,
  pageSize: 10,
  name: '',
  status: ''
})
//获取会议室列表
const getresvplace_list = ref<any[]>([])
const getresvplace_func = async () => {
  if (categoryType_list.value[headerSelect.value]?.id) {
    const data = await conferenceApi.getresvplaceList({
      appSign: form.value.appSign,
      pageNo: form.value.pageNo,
      pageSize: form.value.pageSize,
      name: form.value.name,
      status: form.value.status,
      villageId: vallageId.value,
      categoryId: categoryType_list.value[headerSelect.value].id
    })
    getresvplace_list.value = data.list
  }
}
//删除会议室
const deletefun_list = (id) => {
  ElMessageBox.confirm('此操作将永久删除该分类, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    conferenceApi.deleteresvplace(id).then((res) => {
      message.success('删除成功')
      getresvplace_func()
    })
  })
}
//停用会议室
const changstatusfun_list = (row) => {
  const title =
    row.status == 1 ? '此操作将停用该会议室, 是否继续?' : '此操作将开启该会议室, 是否继续?'
  ElMessageBox.confirm(title, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    //获取会议室详情
    conferenceApi.getresvplaceListId(row.id).then((res) => {
      res.status = res.status == 1 ? 0 : 1
      conferenceApi.updateresvplace(res).then((res) => {
        message.success('修改成功')
        getresvplace_func()
      })
    })
  })
}
//添加会议室成功
const addSuccess = () => {
  getresvplace_func()
  getcategoryType()
}
</script>
<style lang="scss" scoped>
.conference_page {
  background-color: #fff;
  padding: 0 20px;
  box-sizing: border-box;
  position: relative;
  :deep(.el-tabs__header) {
    --el-tabs-header-height: 50px;
    max-width: calc(100% - 140px);
  }
  :deep(.el-tabs__nav-next) {
    line-height: 55px;
  }
  :deep(.el-tabs__nav-prev) {
    line-height: 55px;
  }

  .addconference_button {
    position: absolute;
    right: 10px;
    top: 10px;
    z-index: 999;
  }
}
.pane_detail {
  background-color: #f3f6f9;
  padding: 10px;
}
.border-right {
  border-right: 1px solid #dddddd;
}
</style>
