<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-drawer :title="dialogTitle" v-model="dialogVisible" size="760">
    <el-form
      ref="formRef"
      :model="formData"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item
            label="名称"
            prop="name"
            :rules="[{ required: true, message: '名称不能为空', trigger: 'blur' }]"
          >
            <el-input v-model="formData.name" placeholder="请输入名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="生效时间"
            :rules="[{ required: true, message: '生效时间不能为空', trigger: 'blur' }]"
            prop="effectDate"
          >
            <el-date-picker
              v-model="formData.effectDate"
              type="date"
              placeholder="请选择生效时间"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="价格类型"
            prop="isStagePrice"
            :rules="[{ required: true, message: '请选择价格类型', trigger: 'blur' }]"
          >
            <el-radio-group v-model="formData.isStagePrice">
              <el-radio-button label="普通价" value="0" />
              <el-radio-button label="阶梯价" value="1" />
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="税率"
            prop="taxRate"
            :rules="[{ required: true, message: '税率不能为空', trigger: 'blur' }]"
          >
            <el-input v-model="formData.taxRate" type="number" placeholder="请输入税率">
              <template #append>%</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="formData.isStagePrice == '1'">
          <el-form-item label="单价标准" required>
            <div class="flex justify-end w-full">
              <el-button type="primary mb-10px" @click="addunitPriceTable">添加</el-button>
            </div>
            <el-table :data="unitPriceTable">
              <el-table-column label="用量区间" align="center" width="400">
                <template #default="scope">
                  <div class="flex items-center gap-[10px]">
                    <el-input
                      v-model="scope.row.startUsageRange"
                      type="number"
                      :min="
                        scope.$index === 0 ? 0 : unitPriceTable[scope.$index - 1].endUsageRange + 1
                      "
                      :max="unitPriceTable[scope.$index].endUsageRange - 1"
                      placeholder="请输入开始用量"
                    >
                      <template #append>{{ energyValue.unit }}</template>
                    </el-input>
                    <span> ~ </span>
                    <el-input
                      v-model="scope.row.endUsageRange"
                      :max="
                        scope.$index === unitPriceTable.length - 1
                          ? 9999999
                          : unitPriceTable[scope.$index + 1].startUsageRange
                      "
                      :min="unitPriceTable[scope.$index].startUsageRange + 1"
                      type="number"
                      placeholder="请输入结束用量"
                    >
                      <template #append>{{ energyValue.unit }}</template>
                    </el-input>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="单价" align="center" width="200">
                <template #default="scope">
                  <el-input v-model="scope.row.price">
                    <template #append>元/{{ energyValue.unit }}</template>
                  </el-input>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="100">
                <template #default="scope">
                  <el-button
                    type="text"
                    @click="unitPriceTable.splice(scope.$index, 1)"
                    v-if="scope.$index !== 0"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="formData.isStagePrice == '0'">
          <el-form-item
            label="单价标准"
            prop="unitPrice"
            :rules="[{ required: true, message: '单价标准不能为空', trigger: 'blur' }]"
          >
            <el-input v-model="formData.unitPrice" placeholder="请输入单价标准">
              <template #append>元/{{ energyValue.unit }}</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="滞纳金起算天数" prop="startDay">
            <el-input v-model="formData.startDay" type="number" placeholder="请输入倍率">
              <template #append>天</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="滞纳金比例" prop="ratio">
            <el-input v-model="formData.ratio" type="number" placeholder="请输入倍率">
              <template #append>%/天</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="滞纳金上限" prop="toplimit">
            <el-input v-model="formData.toplimit" type="number" placeholder="请输入倍率">
              <template #append>%</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <!-- 选择房间 -->
    <div class="text-[14px] color-[#606266]"> 使用范围 </div>
    <div class="border-[#E4E7ED] border border-solid mt-[18px] min-h-[300px]">
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
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import { energyPriceApi, VO } from '@/api/bus/WaterElectricity/EnergyPrice/index.ts'
import DeptTree_select from '@/views/contract/contractList/component/DeptTree_select.vue'
/** 自定义价格标准 表单 */
defineOptions({ name: 'Form' })
const changeTree = ref(true) // 是否切换楼层
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const energyValue = ref([]) // 能源类型
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  type: undefined,
  name: undefined,
  isStagePrice: '0',
  startDay: undefined,
  taxRate: undefined,
  unitPrice: undefined,
  toplimit: undefined
})

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const deptTree_selectRef = ref() // 选择房间的树形选择器
const open = async (type: string, energyRow: any, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  energyValue.value = energyRow
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await energyPriceApi.get(id)
      roomList.value = JSON.parse(formData.value.builds)
      setTimeout(() => {
        deptTree_selectRef.value.getDeptTreeSelect(roomList.value)
      }, 50)
      if (formData.value.isStagePrice === '1') {
        unitPriceTable.value = JSON.parse(formData.value.unitPrice)
      }
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()

  // 提交请求
  formLoading.value = true

  try {
    const data = JSON.parse(JSON.stringify(formData.value))
    data.villageId = roomList.value[0].villageId
    data.buildId = roomList.value[0].buildId
    data.roomIds = roomList.value.map((item: any) => item.id).join(',')
    data.builds = JSON.stringify(roomList.value)
    if (formData.value.isStagePrice === '1') {
      data.unitPrice = JSON.stringify(unitPriceTable.value)
    }
    if (formType.value === 'create') {
      data.type = energyValue.value.equipType
      await energyPriceApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await energyPriceApi.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const roomList = ref([]) // 房间列表
const changeData = (data: any) => {
  roomList.value = data
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    type: 1,
    name: undefined,
    isStagePrice: '0',
    startDay: undefined,
    taxRate: undefined,
    unitPrice: undefined,
    toplimit: undefined
  }
  formRef.value?.resetFields()
  unitPriceTable.value = [
    {
      startUsageRange: 0,
      endUsageRange: 100,
      price: 0
    }
  ]
}
const unitPriceTable = ref([
  {
    startUsageRange: 0,
    endUsageRange: 100,
    price: 0
  }
])
const addunitPriceTable = () => {
  unitPriceTable.value.push({
    startUsageRange:
      Number(unitPriceTable.value[unitPriceTable.value.length - 1].endUsageRange) + 1,
    endUsageRange:
      Number(unitPriceTable.value[unitPriceTable.value.length - 1].endUsageRange) + 100,
    price: 0
  })
}
</script>
