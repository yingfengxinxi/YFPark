<template>
  <div>
    <el-drawer
      v-model="drawer"
      :title="type == 'editor' ? '编辑场地' : '新增场地'"
      @closed="closed"
    >
      <el-form label-position="top" ref="drawer_ref" :model="form">
        <el-form-item
          label="会议室名称"
          :rules="[{ required: true, message: '请输入分类名称', trigger: 'blur' }]"
          prop="name"
        >
          <el-input v-model="form.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="会议室照片">
          <div>
            <div>
              <el-upload
                class="uploader_BOX"
                :on-change="image_change"
                :show-file-list="false"
                :auto-upload="false"
              >
                <img v-if="form.images" :src="form.images" alt="" class="img" />
                <img src="@/views/bus/owner/component/image/plus.png" v-else class="plus" alt="" />
              </el-upload>
            </div>
            <div class="text-12px text-#999"> 建议尺寸200px*200px </div>
          </div>
        </el-form-item>
        <el-form-item>
          <template #label>
            <span>会议室地址</span>
            <el-tooltip content="如果不填写则读取项目位置" placement="top">
              <Icon class="transform-translate-y-2px ml-1" icon="fa:question-circle-o" />
            </el-tooltip>
          </template>
          <el-cascader
            ref="mycascader"
            v-model="form.city"
            :options="areaList"
            :props="defaultProps"
            class="w-1/1"
            clearable
            filterable
            placeholder="请选择城市"
            @change="changeCasc()"
          />
          <el-input placeholder="请输入详细地址" class="mt-2" v-model="form.xxdz" />
        </el-form-item>
        <el-form-item label="设施信息">
          <div class="bg-#F7F7F7 p-10px w-full line">
            <el-checkbox-group v-model="form.checkList">
              <el-row :gutter="20">
                <el-col :span="6" v-for="(item, index) in checkList" :key="index">
                  <el-checkbox-group v-model="form.facilities">
                    <el-checkbox :label="item" :value="item" />
                  </el-checkbox-group>
                </el-col>
              </el-row>
            </el-checkbox-group>
            <el-row :gutter="20" style="height: 20px">
              <el-col :span="6" style="height: 20px; line-height: 25px">
                <el-popover
                  :visible="addcheckList"
                  placement="top"
                  :width="300"
                  ref="popoverRef"
                  :teleported="true"
                >
                  <p>新增设施类型将同步至该分类下所有场地设置</p>
                  <div class="flex gap-8px">
                    <el-input
                      placeholder="请输入设备类型"
                      size="small"
                      v-model="addcheckListValue"
                    />
                    <el-button size="small" type="primary" @click="addcheckListValue_func">
                      确认
                    </el-button>
                  </div>
                  <template #reference>
                    <el-button type="primary" @click="addcheckList = true" text
                      >+添加设施类型</el-button
                    >
                  </template>
                </el-popover>
              </el-col>
            </el-row>
          </div>
        </el-form-item>
        <el-form-item
          label="计费规则"
          :rules="[
            {
              required: true,
              message: '请选择计费规则',
              trigger: 'blur'
            }
          ]"
          prop="billRuleId"
        >
          <el-select v-model="form.billRuleId">
            <el-option
              v-for="(item, index) in getresvbillrule_list"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="下单通知人">
          <div>
            <div class="flex gap-5px mb-3 flex-wrap" v-if="userSelectData.length > 0">
              <span v-for="(item, index) in userSelectData" :key="index" class="userSpan">
                {{ item.nickname }}
                <Icon
                  icon="ep:close"
                  color="#666666"
                  class="del_icon"
                  @click="UserSelect_del(index)"
                />
              </span>
            </div>
            <div>
              <el-button @click="addnotifier">添加</el-button>
            </div>
          </div>
        </el-form-item>
        <el-form-item
          label="排序"
          :rules="[
            {
              required: true,
              message: '请输入会议室容纳人数',
              trigger: 'blur'
            }
          ]"
          prop="sort"
        >
          <el-input placeholder="请输入会议室容纳人数" v-model="form.sort" />
        </el-form-item>
        <el-form-item label="会议室介绍">
          <el-input type="textarea" placeholder="请输入会议室描述" v-model="form.description" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="submit">保存</el-button>
        <el-button @click="() => (drawer = false)">取消</el-button>
      </template>
    </el-drawer>
  </div>
  <UserSelect ref="UserSelect_ref" @submit="selectData" />
</template>
<script lang="ts" setup>
import UserSelect from '@/views/bus/application/conference/conferenceSettings/compontent/userSelect.vue'
import * as AreaApi from '@/api/system/area'
import { OwnerApi } from '@/api/bus/owner'
import { defaultProps } from '@/utils/tree'
import { onMounted } from 'vue'
import { conferenceApi } from '@/api/conference/index'
const message = useMessage() // 消息弹窗

const drawer = ref(false)
let form = ref({
  name: '',
  images: '',
  address: '', //场地地址
  city: [], //城市
  xxdz: '',
  facilities: [],
  billRuleId: '',
  sort: '',
  description: '',
  status: '1'
})
const id = ref<any>('') //分类id
const type = ref<any>('')
const vallageId = ref<any>('') //项目id
function open(facilities, ids, typevalue, vallage, row) {
  drawer.value = true
  const cache = JSON.parse(facilities)
  type.value = typevalue
  checkList.value = cache.facilities
  vallageId.value = vallage
  id.value = ids
  userSelectData.value = []
  if (typevalue === 'editor') {
    conferenceApi.getresvplaceListId(row.id).then((res) => {
      form.value = res
      const facilityArr = JSON.parse(res.facilityArr)
      form.value.facilities = facilityArr.facilities
      //地址
      const addList = JSON.parse(res.addressRest)
      form.value.city = addList.city
      form.value.xxdz = addList.xxdz
      form.value.districtName = addList.districtName
      //通知人
      userSelectData.value = JSON.parse(res.notifierData)
    })
  } else {
    form.value = {
      name: '',
      images: '',
      address: '', //场地地址
      city: [], //城市
      xxdz: '',
      facilities: [],
      billRuleId: '',
      sort: '',
      description: '',
      status: '1'
    }
  }
}
//重置表单
const popoverRef = ref()
const drawer_ref = ref()
const closed = () => {
  console.log('closed')
  drawer.value = false
  drawer_ref.value.resetFields()
  addcheckList.value = false
}
defineExpose({ open })
//上传图片
const image_change = (res) => {
  const fileForm = new FormData()
  fileForm.append('file', res.raw)
  OwnerApi.uploadFile(fileForm).then((res) => {
    form.value.images = res.data
  })
}
//加载地区
const areaList = ref<string[]>([])
const initData = async () => {
  // 加载区域数据
  areaList.value = await AreaApi.getAreaTree()
}
//选择设施信息
const checkList = ref<any[]>([])
const addcheckList = ref(false)
const mycascader = ref()

const changeCasc = () => {
  form.value.districtName = mycascader.value.getCheckedNodes()[0].pathLabels.join(',')
}

onMounted(() => {
  initData()
  getresvbillrule_func()
})
//添加设置类型
const addcheckListValue = ref('')
const addcheckListValue_func = () => {
  if (addcheckListValue.value) {
    checkList.value.push(addcheckListValue.value)
    //获取会议室分类
    conferenceApi.getcategory(id.value).then((res) => {
      const cache = {
        facilities: checkList.value,
        place_count: '2'
      }
      res.cache = JSON.stringify(cache)
      conferenceApi.updatecategory(res).then((res) => {})
    })
  }
  addcheckList.value = false
}
const submit = () => {
  drawer_ref.value.validate((valid) => {
    if (valid) {
      form.value.address = form.value.districtName + '-' + form.value.xxdz
      const notifier = userSelectData.value.map((item) => item.id)
      const dzData = {
        address: form.value.address,
        city: form.value.city,
        xxdz: form.value.xxdz,
        districtName: form.value.districtName
      }
      const facilityArr = {
        facilities: form.value.facilities,
        place_count: '2'
      }
      form.value.facilityArr = JSON.stringify(facilityArr)
      form.value.villageId = vallageId.value
      form.value.facilities = []
      form.value.categoryId = id.value
      form.value.appSign = 'library'
      form.value.notifier = notifier.join(',')
      form.value.notifierData = JSON.stringify(userSelectData.value)
      form.value.addressRest = JSON.stringify(dzData)

      if (form.value.id) {
        conferenceApi.updateresvplace(form.value).then((res) => {
          closed()
          emit('success')
          message.success('编辑成功')
        })
      } else {
        conferenceApi.createresvplace(form.value).then((res) => {
          closed()
          emit('success')
          message.success('新增成功')
        })
      }
    }
  })
}
//获取计费规则列表
const getresvbillrule_list = ref<any[]>([])
const getresvbillrule_func = async () => {
  const data = await conferenceApi.getresvbillrule()
  getresvbillrule_list.value = data
}

//增加通知人
const UserSelect_ref = ref<any>()
const addnotifier = () => {
  UserSelect_ref.value.open(userSelectData.value)
}
const userSelectData = ref<any>([])
const selectData = (data) => {
  userSelectData.value = data
}
const UserSelect_del = (index) => {
  userSelectData.value.splice(index, 1)
}
const emit = defineEmits(['success'])
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
</style>
