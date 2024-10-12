<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 列表 -->
  <ContentWrap>
    <div class="flex justify-between items-center">
      <span class="text-[16px]"> 管理员设置 </span>
      <el-button @click="openForm('create')" type="primary">
        <Icon icon="ep:plus" />
        添加人员</el-button
      >
    </div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
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
  <userSelect ref="formRef" @submit="submit" />
</template>

<script setup lang="ts">
import userSelect from '@/views/bus/application/conference/conferenceSettings/compontent/userSelect.vue'
// import { Api, VO } from '@/api/bus/patrol/admin'
import { EnergyAdmin } from '@/api/bus/WaterElectricity/EnergyAdmin'

/** 资产管理子应用管理员配置 列表 */
defineOptions({ name: 'PatrolAdmin' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<VO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  uid: undefined,
  departmentId: undefined,
  level: undefined,
  name: undefined,
  avatar: undefined,
  phone: undefined,
  status: undefined,
  lastRole: undefined,
  lastTime: [],
  createTime: []
})

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await EnergyAdmin.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string) => {
  formRef.value.open([], 'dan')
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await EnergyAdmin.delete(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
//添加数据
const submit = async (res: any) => {
  EnergyAdmin.create({
    uid: res.id,
    name: res.nickname,
    status: 1,
    role: 1
  }).then(() => {
    message.success('添加成功')
    getList()
  })
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
