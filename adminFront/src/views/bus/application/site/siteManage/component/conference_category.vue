<template>
  <div>
    <el-drawer
      v-model="drawer"
      :title="formtype === 'editor' ? '编辑场地分类' : '新建场地分类'"
      @closed="closed"
    >
      <el-form label-position="top" ref="drawer_ref" :model="form">
        <el-form-item
          label="分类名称"
          :rules="[{ required: true, message: '请输入分类名称', trigger: 'blur' }]"
          prop="name"
        >
          <el-input v-model="form.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="分类照片">
          <div>
            <div>
              <el-upload
                class="uploader_BOX"
                :on-change="image_change"
                :show-file-list="false"
                :auto-upload="false"
              >
                <img v-if="form.imageUrl" :src="form.imageUrl" alt="" class="img" />
                <img src="@/views/bus/owner/component/image/plus.png" v-else class="plus" alt="" />
              </el-upload>
            </div>
            <div class="text-12px text-#999"> 建议尺寸200px*200px </div>
          </div>
        </el-form-item>
        <el-form-item
          label="所属项目"
          :rules="[
            {
              required: true,
              message: '请选择所属项目',
              trigger: 'change'
            }
          ]"
        >
          <el-select v-model="form.villageId" disabled>
            <el-option
              v-for="(item, index) in projectList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="预定时间限制">
          <div class="bg-#F7F7F7 p-10px w-full line">
            <div>
              最早可提前<el-input-number
                v-model="form.reservationRule.earliest_time"
                class="mx-2"
                style="width: 120px"
              />天预定
            </div>
            <div>
              每日最早可于
              <el-time-picker
                v-model="form.reservationRule.booking_time"
                class="mx-2"
                style="width: 120px"
                format="HH:mm"
                value-format="HH:mm"
              />开始预定
            </div>
            <div>
              每日可预订时间范围<el-time-picker
                v-model="form.reservationRule.timeframe.start_time"
                class="mx-2"
                style="width: 120px"
                format="HH:mm"
                value-format="HH:mm"
              />至<el-time-picker
                v-model="form.reservationRule.timeframe.end_time"
                class="mx-2"
                style="width: 120px"
                format="HH:mm"
                value-format="HH:mm"
              />
            </div>
            <div>
              预定时长间隔<el-select
                v-model="form.reservationRule.interval_num"
                style="width: 100px"
                class="mx-2"
                @change="select_interval_num"
              >
                <el-option
                  v-for="item in selectList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                /> </el-select
            ></div>
            <div>
              最多进场人数<el-input-number
                v-model="form.capacity"
                style="width: 120px"
                class="mx-2"
              />人
            </div>
          </div>
        </el-form-item>
        <el-form-item label="联系人电话">
          <el-input v-model="form.contactMobile" placeholder="请输入联系人电话" />
        </el-form-item>
        <el-form-item
          label="排序"
          :rules="[{ required: true, message: '请输入排序', trigger: 'blur' }]"
          prop="sort"
        >
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="详情介绍">
          <Editor v-model:modelValue="form.description" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="submit">保存</el-button>
        <el-button @click="() => (drawer = false)">取消</el-button>
      </template>
    </el-drawer>
  </div>
</template>
<script lang="ts" setup>
import { FloorApi } from '@/api/bus/village/floor.ts'
import { conferenceApi } from '@/api/conference/index'
import Editor from '@/components/Editor/src/Editor.vue'
import { OwnerApi } from '@/api/bus/owner'
const message = useMessage() // 消息弹窗
const drawer = ref(false)
let form = ref({
  name: '',
  desc: '',
  appSign: 'library',
  imageUrl: '',
  companyDesc: '',
  villageId: '',
  contactMobile: '',
  sort: '',
  description: '',
  status: 1,
  capacity: 10,
  reservationRule: {
    timeframe: {
      start_time: '06:00',
      end_time: '22:00'
    },
    interval: 1,
    booking_time: '06:00',
    interval_unit: 'hour',
    interval_num: '60',
    earliest_time: 30
  },
  cache: ''
})
const facilities = ref(['电视', '视频会议设备', '签到板', '电话', '白板', '投影仪'])
const formtype = ref('')
async function open(id, type, vallageId_value) {
  console.log(vallageId_value)

  formtype.value = type
  drawer.value = true
  await resetForm()
  form.value.villageId = vallageId_value

  if (type === 'editor') {
    conferenceApi.getcategory(id).then((res) => {
      form.value = res
      form.value.reservationRule = JSON.parse(res.reservationRule)
    })
  }
}
//重置表单
const drawer_ref = ref()
const closed = () => {
  console.log('closed')
  drawer.value = false
  drawer_ref.value.resetFields()
}
defineExpose({ open })
//上传图片
const image_change = (res) => {
  const fileForm = new FormData()
  fileForm.append('file', res.raw)
  OwnerApi.uploadFile(fileForm).then((res) => {
    form.value.imageUrl = res.data
  })
}

const selectList = ref([
  {
    value: '30',
    label: '30分钟',
    interval_unit: 'minute',
    interval: 30
  },
  {
    value: '60',
    label: '1小时',
    interval_unit: 'hour',
    interval: 1
  },
  {
    value: '120',
    label: '2小时',
    interval_unit: 'hour',
    interval: 2
  },
  {
    value: '180',
    label: '3小时',
    interval_unit: 'hour',
    interval: 3
  },
  {
    value: '360',
    label: '6小时',
    interval_unit: 'hour',
    interval: 6
  },
  {
    value: '720',
    label: '12小时',
    interval_unit: 'hour',
    interval: 12
  }
])
//获得项目列表
const projectList = ref([])
const getProjectList = () => {
  FloorApi.getVillageList().then((res) => {
    projectList.value = res
  })
}
//选择间隔时间
const select_interval_num = (val, data) => {
  console.log(val)
  selectList.value.map((item) => {
    if (val === item.value) {
      form.value.reservationRule.interval = item.interval
      form.value.reservationRule.interval_unit = item.interval_unit
    }
  })
}
//提交
const emit = defineEmits(['getList'])
const submit = () => {
  drawer_ref.value.validate(async (valid) => {
    if (valid) {
      if (formtype.value === 'editor') {
        const data = {
          ...form.value,
          reservationRule: JSON.stringify(form.value.reservationRule)
        }
        conferenceApi.updatecategory(data).then((res) => {
          drawer.value = false
          emit('getList')
          message.success('修改成功')
        })
        return
      } else {
        const sblist = {
          facilities: facilities.value,
          place_count: 1
        }
        const data = {
          ...form.value,
          reservationRule: JSON.stringify(form.value.reservationRule),
          cache: JSON.stringify(sblist)
        }
        conferenceApi.createcategory(data).then((res) => {
          drawer.value = false
          emit('getList')
          message.success('添加成功')
        })
      }
    }
  })
}
//重置表单
const resetForm = () => {
  form.value = {
    name: '',
    desc: '',
    appSign: 'library',
    imageUrl: '',
    companyDesc: '',
    villageId: '',
    contactMobile: '',
    sort: '',
    description: '',
    status: 1,
    capacity: 10,
    reservationRule: {
      timeframe: {
        start_time: '06:00',
        end_time: '22:00'
      },
      interval: 1,
      booking_time: '06:00',
      interval_unit: 'hour',
      interval_num: '60',
      earliest_time: 30
    }
  }
}
onMounted(() => {
  getProjectList()
})
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
</style>
