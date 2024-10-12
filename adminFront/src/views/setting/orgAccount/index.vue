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
            <el-form-item label="条目名称" prop="name">
              <el-input
                v-model="queryParams.name"
                placeholder="请输入条目名称"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="收款公司" prop="company">
              <el-input
                v-model="queryParams.company"
                placeholder="请输入收款公司"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="开户银行" prop="bank">
              <el-input
                v-model="queryParams.bank"
                placeholder="请输入开户银行"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
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
                v-hasPermi="['bus:org-account::create']"
              >
                <Icon icon="ep:plus" class="mr-5px" /> 新增
              </el-button>
              <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:org-account::export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
            </el-form-item>
          </el-form>
        </ContentWrap>

        <!-- 列表 -->
        <ContentWrap>
          <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
            <el-table-column label="编号" align="center" prop="id" />
            <el-table-column label="条目名称" align="center" prop="name" />
            <el-table-column label="收款公司" align="center" prop="company" />
            <el-table-column label="开户银行" align="center" prop="bank" />
            <el-table-column label="银行账号" align="center" prop="bankAccount" />
            <el-table-column label="总分类账科目" align="center" prop="subject" />
            <el-table-column label="应用的楼宇" align="center" prop="buildName" />
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
            <el-table-column label="操作" align="center">
              <template #default="scope">
                <el-button
                  link
                  type="primary"
                  @click="openForm('update', scope.row.id)"
                  v-hasPermi="['bus:org-account::update']"
                >
                  编辑
                </el-button>
                <el-button
                  link
                  type="danger"
                  @click="handleDelete(scope.row.id)"
                  v-hasPermi="['bus:org-account::delete']"
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
  <Form ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import leftMenu from '../components/leftMenu/index.vue'
import { Api, VO } from '@/api/bus/tag/orgAccount'
import Form from './Form.vue'
import { CommonStatusEnum } from '@/utils/constants'

/** 收支账户配置 列表 */
defineOptions({ name: 'OrgAccount' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  company: undefined,
  bank: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await Api.getPage(queryParams)
    data.list.forEach((item: any) => {
      item.status = Number(item.status)
    })
    list.value = data.list
    total.value = data.total
    console.log(list.value)
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
    await Api.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 修改状态 */
const handleStatusChange = async (row: any) => {
  try {
    // 修改状态的二次确认
    const text = row.status === CommonStatusEnum.ENABLE ? '关闭' : '开启'
    await message.confirm('确认要"' + text + '""' + row.name + '"吗?')
    // 发起修改状态
    await Api.update({ id: row.id, status: row.status, userShow: 1 })
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status =
      row.status === CommonStatusEnum.ENABLE ? CommonStatusEnum.DISABLE : CommonStatusEnum.ENABLE
  }
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await Api.export(queryParams)
    download.excel(data, '收支账户配置.xls')
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
