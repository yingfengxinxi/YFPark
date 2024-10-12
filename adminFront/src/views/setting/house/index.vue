<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="common-layout">
    <el-container>
      <leftMenu />
      <el-main class="!-pl--160px">
        <ContentWrap>
          <!-- 搜索工作栏 -->
          <el-form
            class="-mb-15px"
            :model="queryParams"
            ref="queryFormRef"
            :inline="true"
            label-width="68px"
          >
            <el-form-item label="标签名称" prop="name">
              <el-input
                v-model="queryParams.name"
                placeholder="请输入标签名称"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="标签描述" prop="descVillage">
              <el-input
                v-model="queryParams.descVillage"
                placeholder="请输入标签描述"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="queryParams.status"
                placeholder="请选择状态"
                clearable
                class="!w-240px"
              >
                <el-option
                  v-for="dict in statusList"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button @click="handleQuery"
                ><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button
              >
              <el-button @click="resetQuery"
                ><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button
              >
              <el-button
                type="primary"
                plain
                @click="openForm('create')"
                v-hasPermi="['bus:tag-house:create']"
              >
                <Icon icon="ep:plus" class="mr-5px" /> 新增
              </el-button>
              <el-button
                type="success"
                plain
                @click="handleExport"
                :loading="exportLoading"
                v-hasPermi="['bus:tag-house:export']"
              >
                <Icon icon="ep:download" class="mr-5px" /> 导出
              </el-button>
            </el-form-item>
          </el-form>
        </ContentWrap>

        <!-- 列表 -->
        <ContentWrap>
          <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
            <el-table-column label="标签名称" align="center" prop="name" />
            <el-table-column label="标签描述" align="center" prop="descVillage" />
            <el-table-column label="状态" align="center" prop="status">
              <template #default="scope">
                <el-switch
                  v-model="scope.row.status"
                  size="small"
                  :active-value="1"
                  :inactive-value="0"
                  @change="handleStatusChange(scope.row)"
                />
              </template>
            </el-table-column>
            <el-table-column
              label="创建时间"
              align="center"
              prop="createTime"
              :formatter="dateFormatter"
              width="180px"
            />
            <el-table-column label="操作" align="center">
              <template #default="scope">
                <el-button
                  link
                  type="primary"
                  @click="openForm('update', scope.row.id)"
                  v-hasPermi="['bus:tag-house:update']"
                >
                  编辑
                </el-button>
                <el-button
                  link
                  type="danger"
                  @click="handleDelete(scope.row.id)"
                  v-hasPermi="['bus:tag-house:delete']"
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
      </el-main>
    </el-container>
  </div>
  <!-- 表单弹窗：添加/修改 -->
  <TagHouseForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { TagHouseApi, TagHouseVO } from '@/api/bus/tag/house/index'
import TagHouseForm from './TagHouseForm.vue'
import leftMenu from '../components/leftMenu/index.vue'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { CommonStatusEnum } from '@/utils/constants'

/** 房源标签 列表 */
defineOptions({ name: 'TagHouse' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<TagHouseVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const statusList = ref([] as any[])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  name: undefined,
  descVillage: undefined,
  color: undefined,
  userShow: undefined,
  status: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

// 查询状态字典
const getStatusList = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.SETTING_TAG_STATUS)
    statusList.value = data
  } finally {
  }
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await TagHouseApi.getTagHousePage(queryParams)
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
    await TagHouseApi.deleteTagHouse(id)
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
    const data = await TagHouseApi.exportTagHouse(queryParams)
    download.excel(data, '房源标签.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 修改用户状态 */
const handleStatusChange = async (row: any) => {
  try {
    // 修改状态的二次确认
    const text = row.status === CommonStatusEnum.ENABLE ? '禁用' : '开启'
    await message.confirm('确认要"' + text + '""' + row.name + '"标签吗?')
    // 发起修改状态
    await TagHouseApi.updateTagHouse({ id: row.id, status: row.status, userShow: 1 })
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status =
      row.status === CommonStatusEnum.ENABLE ? CommonStatusEnum.DISABLE : CommonStatusEnum.ENABLE
  }
}
/** 初始化 **/
onMounted(() => {
  getList()
  getStatusList()
})
</script>
