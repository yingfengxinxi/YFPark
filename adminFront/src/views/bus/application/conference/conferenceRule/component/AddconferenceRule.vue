<template>
  <div>
    <el-drawer v-model="drawer" title="新建计费规则" size="40%">
      <el-form label-position="top" :model="form" ref="drawer_ref">
        <el-form-item
          label="计费规则名称"
          :rules="[
            {
              required: true,
              message: '请输入分类名称',
              trigger: 'blur'
            }
          ]"
          prop="name"
        >
          <el-input v-model="form.name" placeholder="请输入计费规则名称" />
        </el-form-item>
        <el-form-item
          label="所属项目"
          :rules="[
            {
              required: true,
              message: '请选择所属项目',
              trigger: 'blur'
            }
          ]"
          prop="villageId"
        >
          <el-select v-model="form.villageId" placeholder="请选择所属项目" multiple>
            <el-option
              v-for="(item, index) in projectList"
              :key="index"
              :label="item.name"
              :value="item.id.toString()"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          :rules="[
            {
              required: true,
              message: '请选择收费标准',
              trigger: 'blur'
            }
          ]"
        >
          <template #label>
            <span class="label">
              收费标准
              <el-checkbox
                class="m-l-20px !h-22px checkbox"
                v-model="form.isMultiTimeCharge"
                label="启用特殊日期价格"
                size="large"
                :true-value="1"
                :false-value="0"
              />
            </span>
          </template>
          <div class="bg-#F7F7F7 p-18px w-100%">
            <el-form ref="sfbz_form_ref" :model="sfbz_form">
              <el-row :gutter="20">
                <el-col :span="12">
                  <el-form-item
                    label="默认计算首时单位(分钟)"
                    :rules="[
                      {
                        required: true,
                        message: '请输入默认计算首时单位',
                        trigger: 'blur'
                      }
                    ]"
                    prop="first_billing_duration"
                  >
                    <el-input
                      type="number"
                      placeholder="请输入"
                      v-model="sfbz_form.first_billing_duration"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item
                    label="默认计算首时单位收费额(元)"
                    :rules="[
                      {
                        required: true,
                        message: '请输入默认计算首时单位收费额',
                        trigger: 'blur'
                      }
                    ]"
                    prop="first_billing_price"
                  >
                    <el-input
                      type="number"
                      placeholder="请输入"
                      v-model="sfbz_form.first_billing_price"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="mt-28px">
                  <el-form-item
                    label="默认计时单位(分钟)"
                    :rules="[
                      {
                        required: true,
                        message: '请输入默认计时单位',
                        trigger: 'blur'
                      }
                    ]"
                    prop="billing_duration"
                  >
                    <el-input
                      type="number"
                      placeholder="请输入"
                      v-model="sfbz_form.billing_duration"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="mt-28px">
                  <el-form-item
                    label="默认每计时单位收费额(元)"
                    :rules="[
                      {
                        required: true,
                        message: '请输入默认每计时单位收费额',
                        trigger: 'blur'
                      }
                    ]"
                    prop="billing_price"
                  >
                    <el-input
                      type="number"
                      placeholder="请输入"
                      v-model="sfbz_form.billing_price"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <div class="mt-28px text-#606266 text-14px" v-if="form.isMultiTimeCharge == '1'"
              >特殊日期设置</div
            >
            <div v-if="form.isMultiTimeCharge == '1'">
              <div
                class="mt-18px flex gap-10px border-bottom pb-10px relative"
                v-for="(item, index) in dataList"
                :key="index"
              >
                <Icon
                  icon="ep:remove"
                  color="red"
                  class="absolute_remove"
                  @click="remove_list(index)"
                  v-if="index > 0"
                />
                <!-- {{ item }} -->
                <div class="w-33%">
                  <div class="text-center"> 星期 </div>
                  <el-checkbox-group v-model="item.week">
                    <el-checkbox v-for="city in item.week" :key="city" :label="city" :value="city">
                      {{ city }}
                    </el-checkbox>
                  </el-checkbox-group>
                  <div class="cursor-pointer" @click="selevt_day(index)"> +选择 </div>
                </div>
                <div class="w-33%">
                  <div>
                    <div> 计首计时单位(分钟) </div>
                    <div>
                      <el-input
                        placeholder="请输入"
                        v-model="item.charge_standard.first_billing_duration"
                      />
                    </div>
                  </div>
                  <div>
                    <div> 计时单位(分钟) </div>
                    <div>
                      <el-input
                        placeholder="请输入"
                        v-model="item.charge_standard.billing_duration"
                      />
                    </div>
                  </div>
                </div>
                <div class="w-33%">
                  <div>
                    <div> 首计时单位收费额(元) </div>
                    <div>
                      <el-input
                        placeholder="请输入"
                        v-model="item.charge_standard.first_billing_price"
                      />
                    </div>
                  </div>
                  <div>
                    <div> 计时单位收费额(元) </div>
                    <div>
                      <el-input placeholder="请输入" v-model="item.charge_standard.billing_price" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- <div
              class="flex justify-center items-center mt-20px color-#1890FF cursor-pointer"
              @click="plus_list"
              v-if="form.isPersonal"
            >
              <Icon icon="ep:plus" color="#1890FF" />
              添加
            </div> -->
          </div>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="status" active-value="1" inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div>
          <el-button @click="drawer = false"> 取消 </el-button>
          <el-button type="primary" @click="submit"> 确定 </el-button>
        </div>
      </template>
    </el-drawer>
  </div>
  <!-- 特殊日期价格选择日期 -->
  <el-dialog v-model="dialogVisible" title="设施信息" width="500">
    <!-- 多选 -->
    <el-checkbox-group v-model="checkedCities">
      <el-checkbox v-for="city in cities" :key="city" :label="city" :value="city">
        {{ city }}
      </el-checkbox>
    </el-checkbox-group>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit_day"> 确认 </el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import { FloorApi } from '@/api/bus/village/floor.ts'
import { conferenceApi } from '@/api/conference/index'
const message = useMessage()
const status = ref('1')
const sfbz_form = ref({
  billing_price: '',
  billing_duration: '',
  first_billing_price: '',
  first_billing_duration: ''
})
const form = reactive({
  name: '',
  isPersonal: '',
  isMultiTimeCharge: '0'
})
function open(id, type) {
  resetData()
  drawer.value = true
  if (type == 'EDITOR') {
    conferenceApi.resvbillruleget(id).then((res) => {
      form.id = res.id
      form.name = res.name
      form.isMultiTimeCharge = Number(res.isMultiTimeCharge)
      form.villageId = res.villageId.split(',')
      status.value = res.status.toString()
      sfbz_form.value = JSON.parse(res.chargeStandard)
      dataList.value = JSON.parse(res.multiTimeChargeStandard)
    })
  }
}
const drawer = ref(false)
defineExpose({ open })
const dataList = ref([
  {
    week: [],
    charge_standard: {
      billing_price: '',
      billing_duration: '',
      first_billing_price: '',
      first_billing_duration: ''
    }
  }
])
//添加删除
const plus_list = () => {
  dataList.value.push({
    week: [],
    charge_standard: {
      billing_price: '',
      billing_duration: '',
      first_billing_price: '',
      first_billing_duration: ''
    }
  })
}
const remove_list = (index) => {
  dataList.value.splice(index, 1)
}
const selevt_day = (index) => {
  selectIndex.value = index
  checkedCities.value = dataList.value[index].week
  dialogVisible.value = true
}
//特殊日期价格选择日期
const selectIndex = ref(null)
const dialogVisible = ref(false)
const cities = ref(['周一', '周二', '周三', '周四', '周五', '周六', '周日'])

const checkedCities = ref([])
const submit_day = () => {
  dataList.value[selectIndex.value].week = checkedCities.value
  dialogVisible.value = false
}
onMounted(() => {
  getProjectList()
})
//获得项目列表
const projectList = ref([])
const getProjectList = () => {
  FloorApi.getVillageList().then((res) => {
    projectList.value = res
  })
}
const drawer_ref = ref()
const sfbz_form_ref = ref()

const submit = async () => {
  let form1 = ''
  let form2 = ''
  await drawer_ref.value.validate((valid) => {
    form1 = valid
  })
  await sfbz_form_ref.value.validate((valid) => {
    form2 = valid
  })
  console.log(form1, form2)

  if (form1 && form2) {
    let submitData = true
    if (form.isMultiTimeCharge == '1') {
      await dataList.value.forEach((item) => {
        if (
          item.week.length == 0 ||
          item.first_billing_duration == '' ||
          item.billing_duration == '' ||
          item.first_billing_price == '' ||
          item.billing_price == ''
        ) {
          message.error('请将特殊日期信息补充完整')
          submitData = false
        }
      })
    }
    setTimeout(() => {
      if (submitData) {
        dataList.value.forEach((item) => {
          item.weeks = []
          //判断是否选择了周
          item.week.map((res) => {
            if (res == '周一') {
              item.weeks.push('1')
            } else if (res == '周二') {
              item.weeks.push('2')
            } else if (res == '周三') {
              item.weeks.push('3')
            } else if (res == '周四') {
              item.weeks.push('4')
            } else if (res == '周五') {
              item.weeks.push('5')
            } else if (res == '周六') {
              item.weeks.push('6')
            } else if (res == '周日') {
              item.weeks.push('0')
            }
          })
        })

        if (form.id) {
          console.log(sfbz_form.value, '=====================')
          conferenceApi
            .resvbillruleupdate({
              appSign: 'metting',
              id: form.id,
              name: form.name,
              villageId: form.villageId.join(','),
              isMultiTimeCharge: form.isMultiTimeCharge,
              multiTimeChargeStandard: JSON.stringify(dataList.value),
              chargeStandard: JSON.stringify(sfbz_form.value),
              status: status.value
            })
            .then((res) => {
              message.success('修改成功')
              drawer.value = false
              emit('getList')
            })
        } else {
          conferenceApi
            .resvbillrulecreate({
              appSign: 'metting',
              name: form.name,
              villageId: form.villageId.join(','),
              isMultiTimeCharge: form.isMultiTimeCharge,
              multiTimeChargeStandard: JSON.stringify(dataList.value),
              chargeStandard: JSON.stringify(sfbz_form.value),
              status: status.value
            })
            .then((res) => {
              message.success('添加成功')
              drawer.value = false
              emit('getList')
            })
        }
      }
    }, 100)
  }
}
const resetData = () => {
  form.name = ''
  form.isPersonal = ''
  form.isMultiTimeCharge = '0'
  form.villageId = ''
  sfbz_form.value = {
    billing_price: '',
    billing_duration: '',
    first_billing_price: '',
    first_billing_duration: ''
  }

  dataList.value = [
    {
      week: [],
      charge_standard: {
        billing_price: '',
        billing_duration: '',
        first_billing_price: '',
        first_billing_duration: ''
      }
    }
  ]
  status.value = '1'
}
const emit = defineEmits(['getList'])
</script>
<style lang="scss" scoped>
.label {
  width: calc(100% - 20px);
  display: inline-block;
  position: relative;
  .checkbox {
    position: absolute;
    right: -20px;
    z-index: 999;
  }
}
.border-bottom {
  border-bottom: 1px solid #e7e7e7;
}
.absolute_remove {
  position: absolute;
  right: 0;
  top: 0;
  cursor: pointer;
}
</style>
