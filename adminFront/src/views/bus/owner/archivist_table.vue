<template>
  <ContentWrap>
    <div class="row">
      <div>
        <Icon icon="ep:arrow-left" class="mr-5px hover" @click="emit('back')" />
        归档租客
      </div>
      <div>
        <el-form
          class="-mb-15px"
          :model="queryParams"
          ref="queryFormRef"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="租客名称">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入租客名称"
              clearable
              @keyup.enter="handleQuery"
              class="!w-240px"
            />
            <el-input style="display: none" />
          </el-form-item>
          <el-form-item>
            <el-button @click="handleQuery"
              ><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button
            >
            <el-button @click="resetQuery"
              ><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </div>
  </ContentWrap>
  <ContentWrap>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo !border-none villageMenu row_menu"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="0">企业租客</el-menu-item>
      <el-menu-item index="1">个人租客</el-menu-item>
    </el-menu>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="租客名称" fixed width="200px" prop="name" align="center" />
      <el-table-column label="租客编码" width="200px" align="center" prop="tenantNo" />
      <el-table-column label="租客标签" width="400px" align="center" prop="tagInfo">
        <template #default="scope">
          <span v-for="(item, index) in industry" :key="index">
            <span v-if="scope.row.tagInfo.includes(item.id.toString())">{{ item.name }}</span>
            &nbsp;
          </span>
        </template>
      </el-table-column>
      <el-table-column
        label="营业期限"
        width="200px"
        align="center"
        prop="businessInfoBusinessTerm"
        v-if="activeIndex == '0'"
      />
      <el-table-column
        label="成立日期"
        width="200px"
        align="center"
        prop="businessInfoFoundingTime"
        v-if="activeIndex == '0'"
      />
      <el-table-column label="所属地区" width="200px" align="center" prop="tagInfo" />
      <el-table-column label="组织机构代码" width="200px" align="center" prop="orgId" />
      <el-table-column label="行业分类" width="200px" align="center" prop="industryId">
        <template #default="scope">
          <span v-for="(item, index) in Owner" :key="index">
            <span v-if="scope.row.industryId == item.id">{{ item.name }}</span>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="联系人" width="200px" align="center" prop="contactId" />
      <el-table-column label="证件号码" width="200px" align="center" prop="certificateNumber" />
      <el-table-column label="租客电话" width="200px" align="center" prop="tel" />
      <el-table-column label="所属园区" width="200px" align="center" prop="villageName" />
      <el-table-column label="操作" fixed="right" width="200px" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click.stop="emit('change', scope.row.id)"
            v-hasPermi="['bus:owner:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click.stop="emit('del', scope.row.id)"
            v-hasPermi="['bus:owner:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
</template>
<script setup lang="ts">
import { OwnerApi, OwnerVO } from '@/api/bus/owner'
import { onMounted } from 'vue'
import { TagOwnerApi } from '@/api/bus/tag/owner'
const message = useMessage() // 消息弹窗
const emit = defineEmits(['back', 'change', 'del'])
const activeIndex = ref('0')
const handleSelect = (key: string) => {
  activeIndex.value = key
  getList()
}
const props = withDefaults(
  defineProps<{
    getlist: number
  }>(),
  {
    getlist: 0
  }
)
const list = ref<OwnerVO[]>([])
const total = ref()
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  isPersonal: '0',
  keyword: '',
  isArchive: '1',
  name: ''
})
// const table_row_click = (row: OwnerVO) => {
//   emit('change', row)
// }
watch(
  () => props.getlist,
  () => {
    getList()
  }
)
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}
const resetQuery = () => {
  queryParams.value.name = ''
  handleQuery()
}
const getList = async () => {
  queryParams.value.isPersonal = activeIndex
  const data = await OwnerApi.getOwnerPage(queryParams.value)
  list.value = data.list
  total.value = data.total
  list.value.forEach((item) => {
    item.tagInfo = item.tagInfo.split(',')
  })
}
const Owner = ref([])
const industry = ref([])
onMounted(async () => {
  await OwnerApi.getTagIndustryList('').then((res) => {
    Owner.value = res
  })
  //租客标签
  await TagOwnerApi.getTagOwnerList('').then((res) => {
    industry.value = res
  })
  setTimeout(() => {
    getList()
  }, 1000)
})
</script>
<style lang="scss" scoped>
.row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;
  font-weight: 600;
}
.hover {
  cursor: pointer;
}
</style>
