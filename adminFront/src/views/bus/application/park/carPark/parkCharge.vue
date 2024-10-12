<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="项目ID" prop="villageId">
        <el-input
          v-model="queryParams.villageId"
          placeholder="请输入项目ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="停车场ID" prop="parkId">
        <el-input
          v-model="queryParams.parkId"
          placeholder="请输入停车场ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="计费规则名称" prop="chargeName">
        <el-input
          v-model="queryParams.chargeName"
          placeholder="请输入计费规则名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['bus:parkCharge:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:parkCharge:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true">
      <el-table-column label="所属项目" align="center" prop="villageId" width="160px" />
      <el-table-column label="所属车场" align="center" prop="parkId" width="160px" />
      <el-table-column label="收费标准名称" align="center" prop="chargeName" width="160px" />
      <el-table-column label="使用车辆类型" align="center" prop="carType" width="220px"
        ><template #default="scope">
          <template v-for="(item, index) in scope.row.carType.split(',')" :key="index">
            <dict-tag :type="DICT_TYPE.FREE_CAR" :value="item" class="m-r-4px m-b-4px" />
          </template>
        </template>
      </el-table-column>
      <el-table-column label="生效日期" align="center" prop="effectiveDate" width="160px" />
      <el-table-column label="月租费用" align="center" prop="monthFee" width="160px">
        <template #default="scope"> ￥{{ scope.row.monthFee }} </template>
      </el-table-column>
      <el-table-column label="免费停车时长" align="center" prop="freeMinute" width="160px">
        <template #default="scope"> {{ scope.row.freeMinute }}分钟 </template>
      </el-table-column>
      <el-table-column label="免费出场时长" align="center" prop="freeOutMinute" width="160px">
        <template #default="scope"> {{ scope.row.freeOutMinute }}分钟 </template>
      </el-table-column>
      <el-table-column label="收费方式" align="center" prop="chargeType" width="160px">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.TEMPORARY_VEHICIE_CHARGE_TYPE" :value="scope.row.chargeType" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="280px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:parkCharge:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:parkCharge:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { ParkChargeApi, VO } from '@/api/bus/parkCharge/index'
import Form from './parkChargeForm.vue'

/** 停车场收费标准 列表 */
defineOptions({ name: 'ParkCharge' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  chargeName: undefined,
  parkId: undefined,
  villageId: undefined,
  carType: undefined,
  monthFee: undefined,
  monthOverdueType: undefined,
  monthRechargeInput: undefined,
  monthRechargePackage: undefined,
  effectiveDate: [],
  freeMinute: undefined,
  freeOutMinute: undefined,
  chargeType: undefined,
  dayMaxFee: undefined,
  chargeDetail: undefined,
  isDefault: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ParkChargeApi.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await ParkChargeApi.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await ParkChargeApi.export(queryParams)
    download.excel(data, '停车场收费标准.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
