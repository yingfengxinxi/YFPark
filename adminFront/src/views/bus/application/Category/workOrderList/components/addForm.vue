<template>
  <el-drawer title="创建工单" v-model="drawer" size="40%">
    <el-form label-position="top" :model="form" ref="formRef">
      <el-form-item label="位置类型">
        <el-select v-model="form.repairType">
          <el-option
            v-for="(item, index) in getIntDictOptions('WORK_ORDER_REPAIR_TYPE')"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="所属楼宇"
        v-if="form.repairType == 2"
        :rules="[{ required: true, message: '请选择所属楼宇', trigger: 'blur' }]"
        prop="buildId"
      >
        <el-select v-model="form.buildId" @change="selectBuild">
          <el-option
            v-for="(item, index) in BuildList"
            :key="index"
            :label="item.buildName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="对应位置" v-if="form.repairType == 2">
        <el-input v-model="form.position" placeholder="请输入对应位置" />
      </el-form-item>
      <el-form-item label="对应位置" v-if="form.repairType == 1">
        <div
          class="bg-#E6F7FF w-100% text-12px py-0px px-8px border border-solid border-#91D5FF mb-10px rounded"
          ><Icon icon="ep:question-filled" color="#1890FF" class="transform-translate-y-1.5px" />
          请先选择对应位置再选择工单类型【不能跨楼宇选择】
        </div>
        <div
          class="w-100% border-1.5px border-solid border-#DCDFE6 rounded overflow-hidden overflow-y-scroll max-h-200px h-200px min-h-200px"
        >
          <selectTree @submit="selectTreeFunc" />
        </div>
      </el-form-item>
      <el-form-item
        label="工单类型"
        :rules="[{ required: true, message: '请选择工单类型', trigger: 'blur' }]"
        prop="categoryId"
      >
        <el-cascader
          :options="categoryTree"
          v-model="categoryTreeModel"
          @change="changeTree"
          style="width: 100%"
        />
        <div class="color-#FF0000 text-12px" v-if="categoryTree.length == 0">
          暂无类型，工单大类下需要添加子类才能使用
        </div>
      </el-form-item>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="上报人">
            <el-input placeholder="请输入上报人的姓名和公司" v-model="form.name"  />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="上报人手机"
            :rules="[
              { required: true, message: '请输入上报人的手机号', trigger: 'blur' },
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
            ]"
            prop="phone"
          >
            <el-input placeholder="请输入上报人的手机号" maxlength="11" v-model="form.phone" @change="changePhone" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="上门时间"
            :rules="[{ required: true, message: '请选择上门时间', trigger: 'blur' }]"
            prop="visitTime"
          >
            <el-date-picker
              v-model="form.visitTime"
              type="datetime"
              placeholder="请选择上门时间"
              style="width: 100%"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="form.repairType == 1">
          <el-form-item label="描述标签">
            <el-select v-model="form.labelJson">
              <el-option
                v-for="(item, index) in homeList"
                :key="index"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="补充内容">
        <el-input type="textarea" v-model="form.remark" placeholder="请输入补充内容" />
      </el-form-item>
      <el-form-item label="上报内容" v-if="form.repairType == 1">
        <template #label>
          <div>
            <el-tooltip class="box-item" effect="dark" content="支持上传图片" placement="top">
              <Icon icon="ep:question-filled" class="transform-translate-y-1.5px" />
            </el-tooltip>
            上报内容
          </div>
        </template>
        <div class="flex gap-10px">
          <div v-for="(item, index) in imgList" :key="index" class="pos-relative imgBox">
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
          <div class="w-80px h-80px bg-#FAFAFA rounded">
            <el-upload
              class="uploader_BOX"
              :on-change="faceChange"
              :show-file-list="false"
              :auto-upload="false"
              accept="image/*"
            >
              <img src="@/views/bus/owner/component/image/plus.png" class="plus" alt="" />
            </el-upload>
          </div>
        </div>
      </el-form-item>
      <el-form-item label="费用支付方">
        <template #label>
          <div>
            <el-tooltip
              class="box-item"
              effect="dark"
              content="费用支付方默认默认租客承担，当租客承担时，服务人员点击结单时，自动生成一条该租户的账单，无需租客在租客端再去支付。当选择的工单类型是下单即付费的，则在后台创建的工单支付订金或实价为0"
              placement="top"
            >
              <Icon icon="ep:question-filled" class="transform-translate-y-1.5px" />
            </el-tooltip>
            费用支付方
          </div>
        </template>
        <el-radio-group v-model="form.paidPayer">
          <el-radio
            v-for="(item, index) in getIntDictOptions('WORK_ORDER_PAID_PAYER')"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-radio-group>
      </el-form-item>
      <el-form-item
        label="选择租客"
        :rules="[{ required: true, message: '请选择租客', trigger: 'blur' }]"
        prop="ownerId"
      >
        <el-select v-model="form.ownerId">
          <el-option
            v-for="(item, index) in ownerIdList"
            :key="index"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submit">提交</el-button>
      <el-button @click="drawer = false">取消</el-button>
    </template>
  </el-drawer>
</template>
<script lang="ts" setup>
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import selectTree from './selectTree.vue'
import { OwnerApi } from '@/api/bus/owner'
import { workOrderProposeApi } from '@/api/bus/Category/workOrderList/index'
import { CategoryApi } from '@/api/bus/Category/category/index'
import { categoryLabelApi } from '@/api/bus/Category/CategoryLabel/index'
import { BuildApi } from '@/api/bus/village/building'
import { ref } from 'vue'
const applicationValue = ref()
const drawer = ref(false)
const message = useMessage() // 消息弹窗
const form = reactive({
  repairType: 1, //报修类型
  position: '', //对应位置
  categoryId: '', //工单大类id
  subcatId: '', //子类id
  name: '', //上报人
  phone: '', //上报人手机
  visitTime: '', //上门时间
  labelJson: '', //描述标签
  paidPayer: 1, //费用支付方
  ownerId: '', //租客id
  remark: '' //补充内容
})
const homeList = ref([])
const imgList = ref<string[]>([])
const delImage = (index: number) => {
  imgList.value.splice(index, 1)
}
//选择位置
const TreeList = ref([])
const selectTreeFunc = (data) => {
  TreeList.value = data
  form.villageId = data[0].villageId
  form.buildId = data[0].buildId
  getcategoryList(data[0].buildId)
}
//上传logo
const faceChange = (file: any) => {
  const faceimg = new FormData()
  faceimg.append('file', file.raw)
  OwnerApi.uploadFile(faceimg).then((res) => {
    imgList.value.push(res.data)
  })
}
//获取工单类型
const categoryTreeModel = ref('')
const categoryTree = ref([])
const getcategoryList = (id) => {
  CategoryApi.getList({
    application: applicationValue.value,
    buildBind: id
  }).then((res) => {
    const list = []
    res.forEach((item) => {
      list.push({
        value: item.id,
        label: item.name,
        children: item.categorySubcatList.map((subcat) => {
          return {
            value: subcat.id,
            label: subcat.name
          }
        })
      })
    })
    categoryTree.value = list
  })
}
const changeTree = (val) => {
  form.categoryId = val[0]
  form.subcatId = val[1]
  getLabelList(val[1])
}
//获得标签列表
const getLabelList = (id) => {
  categoryLabelApi
    .getList({
      application: applicationValue.value,
      subcatId: id
    })
    .then((res) => {
      homeList.value = res
    })
}
//获得租客列表
const ownerIdList = ref([])
const getOwnerList = async (query: string) => {
  OwnerApi.getOwnerByTel(query).then((res) => {
    ownerIdList.value = res
  })
}
const emit = defineEmits(['submit'])
const formRef = ref()
const submit = () => {
  formRef.value.validate((valid) => {
    if (!valid) {
      return
    }
    const dizhi = TreeList.value.map((item) => item.name).join(',')
    if (form.repairType == 1) {
      form.position = dizhi
    }
    workOrderProposeApi
      .create({
        application: applicationValue.value,
        ...form,
        roomIds: TreeList.value.map((item) => item.id).join(','),
        images: imgList.value.join(',')
      })
      .then(() => {
        drawer.value = false
        emit('submit')
        message.success('创建成功')
      })
  })
}
//获取楼宇列表
const BuildList = ref([])
const getBuildList = async () => {
  BuildApi.getBuildingList().then((res) => {
    BuildList.value = res
  })
}
function show(application: string) {
  drawer.value = true
  applicationValue.value = application
  resetForm()
}
// 选择楼宇
const BuildId = ref('')
const selectBuild = (val) => {
  BuildList.value.forEach((item) => {
    if (item.id === val) {
      getcategoryList(item.id)
      form.villageId = item.villageId
      form.buildId = item.id
    }
  })
}
// 获取租客列表
const changePhone = (val) => {
  getOwnerList(val)
}
defineExpose({ show }) // 提供 open 方法，用于打开弹窗

onMounted(() => {
  getBuildList()
})
const resetForm = () => {
  form.repairType = 1
  form.position = ''
  form.categoryId = ''
  form.subcatId = ''
  form.name = ''
  form.phone = ''
  form.visitTime = ''
  form.labelJson = ''
  form.paidPayer = 1
  form.ownerId = ''
  form.remark = ''
  imgList.value = []
  categoryTreeModel.value = ''
}
</script>
<style scoped lang="scss">
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
.pos-del {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: none;
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
