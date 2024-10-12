<template>
  <ElDrawer title="档案信息" v-model="dialogVisible" size="800px">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="140px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="物料编码" prop="number">
            <el-input
              v-model="formData.number"
              placeholder="已开自动编码"
              disabled
            /> </el-form-item
        ></el-col>
        <el-col :span="12"
          ><el-form-item label="物料名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入物料名称" /> </el-form-item
        ></el-col>
        <el-col :span="12">
          <el-form-item label="物料分类" prop="categoryId">
            <el-tree-select
              v-model="formData.categoryId"
              :data="catrgoryList"
              :render-after-expand="false"
              :props="{ label: 'name', value: 'id', children: 'children' }"
              placeholder="请选择上级分类"
              clearable
              :check-strictly="true"
              @node-click="handleAreaNodeClick"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商品条码" prop="barCode">
            <el-input v-model="formData.barCode" placeholder="请输入商品条码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="品牌" prop="brand">
            <el-input v-model="formData.brand" placeholder="请输入品牌" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="规格型号" prop="modelName">
            <el-input v-model="formData.modelName" placeholder="请输入规格型号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="小数位数(数量)" prop="quantityDigit">
            <template #lable>
              小数位数(数量)
              <el-tooltip content="计量耗材数量小数点后的有效位数" placement="top">
                <Icon class="" icon="fa:question-circle-o" />
              </el-tooltip>
            </template>
            <el-input-number
              :min="0"
              v-model="formData.quantityDigit"
              placeholder="请输入小数位数(数量)"
              :step="1"
              controls-position="right"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="小数位数(单价)" prop="priceDigit">
            <template #lable>
              小数位数(单价)
              <el-tooltip content="计量耗材数量小数点后的有效位数" placement="bottom">
                <Icon class="" icon="fa:question-circle-o" />
              </el-tooltip>
            </template>
            <el-input-number
              :min="0"
              v-model="formData.priceDigit"
              placeholder="请输入小数位数(单价)"
              :step="1"
              controls-position="right"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="成本计算方法" prop="computeMethod">
            <template #lable>
              成本计算方法
              <el-tooltip content="" placement="bottom">
                <template #content>
                  适用场景：出库单价按照耗材库存时间顺序加权平均计算
                  耗材入库：物料单价为入库时填写的单价 库存平均单价：当前剩余总金额/剩余数量
                  耗材出库：出库的物料单价=库存平均单价
                </template>
                <Icon class="" icon="fa:question-circle-o" />
              </el-tooltip>
            </template>
            <el-select
              v-model="formData.computeMethod"
              placeholder="请选择成本计算方法"
              clearable
              disabled
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.STUFF_COST)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计量单位" prop="meterUnit">
            <el-input v-model="formData.meterUnit" placeholder="请输入计量单位" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否固定入库单价" prop="lockPrice">
            <el-select
              v-model="formData.lockPrice"
              placeholder="请选择是否固定入库单价"
              clearable
              class="!w-1/1"
              disabled
            >
              <el-option label="否" :value="0" />
              <el-option label="是" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="安全库存上限" prop="safeStockUp">
            <el-input-number
              :min="0"
              v-model="formData.safeStockUp"
              placeholder="请输入安全库存上限数量"
              :step="1"
              class="!w-1/1"
              controls-position="right"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="安全库存下限" prop="safeStockLower">
            <template #lable>
              安全库存下限
              <el-tooltip content="库存数量如果低于设置的下限值，则预警提醒" placement="bottom">
                <Icon class="" icon="fa:question-circle-o" />
              </el-tooltip>
            </template>
            <el-input-number
              :min="0"
              v-model="formData.safeStockLower"
              placeholder="请输入安全库存下限数量"
              :step="1"
              class="!w-1/1"
              controls-position="right"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="限领数量(人/月)" prop="receiveLimit">
            <el-input-number
              :min="0"
              v-model="formData.receiveLimit"
              placeholder="请输入限领数量(人/月)"
              :step="1"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否允许退库" prop="hasReturn">
            <el-select
              v-model="formData.lockPrice"
              placeholder="请选择是否允许退库"
              clearable
              class="!w-1/1"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.STUFF_HAS_RETURN)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="formData.remark" placeholder="请输入备注" />
          </el-form-item>
        </el-col>
        <el-form-item label="物料照片" prop="imageUrl">
          <UploadImgs
            v-model="formData.imageUrl"
            placeholder="请输入秒杀轮播图"
            class="min-w-80px"
            height="60px"
            width="60px"
          />
        </el-form-item>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { PropertyStuffApi, PropertyStuffVO } from '@/api/bus/stuff/index'
import { PropertyStuffCategoryApi, PropertyStuffCategoryVO } from '@/api/bus/stuff/category'

/** 耗材档案信息 表单 */
defineOptions({ name: 'PropertyStuffForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const catrgoryList = ref<PropertyStuffCategoryVO[]>([])
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orgId: undefined,
  number: undefined,
  name: undefined,
  categoryId: undefined,
  barCode: undefined,
  brand: undefined,
  modelName: undefined,
  meterUnit: undefined,
  quantityDigit: undefined,
  priceDigit: undefined,
  computeMethod: 1,
  lockPrice: 0,
  price: undefined,
  safeStockUp: undefined,
  safeStockLower: undefined,
  receiveLimit: undefined,
  hasReturn: undefined,
  imageUrl: undefined,
  status: undefined,
  remark: undefined,
  cuserUid: undefined,
  muserUid: undefined
})
const formRules = reactive({
  name: [{ required: true, message: '物料名称不能为空', trigger: 'blur' }],
  categoryId: [{ required: true, message: '物料分类不能为空', trigger: 'blur' }],
  quantityDigit: [{ required: true, message: '小数位数(数量)不能为空', trigger: 'blur' }],
  priceDigit: [{ required: true, message: '小数位数(单价)不能为空', trigger: 'blur' }]
  // computeMethod: [
  //   { required: true, message: '成本计算方法;1=加权平均,2=批次管理不能为空', trigger: 'blur' }
  // ],
  // status: [{ required: true, message: '耗材状态;1=启用;0=禁用不能为空', trigger: 'blur' }],
  // cuserUid: [{ required: true, message: '操作人uid不能为空', trigger: 'blur' }],
  // muserUid: [{ required: true, message: '修改人uid不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  catrgoryList.value = await PropertyStuffCategoryApi.getPropertyStuffCategoryTree()
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await PropertyStuffApi.getPropertyStuff(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const handleAreaNodeClick = (data) => {
  if (!data.level) {
    formData.value.level = 0
  } else {
    formData.value.level = String(Number(data.level) + 1)
  }
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as PropertyStuffVO
    if (formType.value === 'create') {
      data.status = 1
      await PropertyStuffApi.createPropertyStuff(data)
      message.success(t('common.createSuccess'))
    } else {
      await PropertyStuffApi.updatePropertyStuff(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    orgId: undefined,
    number: undefined,
    name: undefined,
    categoryId: undefined,
    barCode: undefined,
    brand: undefined,
    modelName: undefined,
    meterUnit: undefined,
    quantityDigit: undefined,
    priceDigit: undefined,
    computeMethod: 1,
    lockPrice: 0,
    price: undefined,
    safeStockUp: undefined,
    safeStockLower: undefined,
    receiveLimit: undefined,
    hasReturn: undefined,
    imageUrl: undefined,
    status: undefined,
    remark: undefined,
    cuserUid: undefined,
    muserUid: undefined
  }
  formRef.value?.resetFields()
}
</script>
