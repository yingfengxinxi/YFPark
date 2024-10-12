<template>
  <div class="h-50px bg-white flex items-center rounded px-18px text-16px" v-if="!showForm">
    表单设置
  </div>
  <ContentWrap class="min-h-500px mt-16px" v-if="!showForm">
    <div class="grid grid-cols-3 grid-gap-20px c-#929292">
      <div
        class="b-dashed b-rd-4px border-#f0f0f0 h-230px bg-#fff box-border cursor-pointer flex justify-center items-center font-size-16px"
        @click="openForm('add')"
        v-hasPermi="['bus:patrolForm:create']"
      >
        <Icon icon="ep:plus" :size="20" class="mr-5px" /> 新增巡检任务模板
      </div>

      <div
        v-loading="loading"
        v-for="(item, index) in list"
        :key="index"
        class="b-rd-4px h-230px bg-#fff box-border flex justify-between flex-col TemplateItem pt-20px"
      >
        <div class="flex px-20px">
          <Icon icon="ep:list" :size="48" color="#2681f3" class="mr-5%" />
          <div class="flex-1 overflow-hidden">
            <div
              class="c-#000000d9 font-size-16px m-b-8px whitespace-nowrap overflow-hidden text-ellipsis line-height-48px"
            >
              {{ item.title }}
            </div>
          </div>
        </div>
        <div class="text-center">创建人:{{ item.creator || '--' }}</div>
        <div
          class="flex text-center justify-between border border-solid border-#f0f0f0 b-b-none b-l-none b-r-none font-size-14px"
        >
          <div
            class="w-50% m-12px m-l-0px m-r-0px border border-solid border-#f0f0f0 b-b-none b-l-none b-t-none hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="openForm('update', item.id)"
            v-hasPermi="['bus:patrolForm:update']"
          >
            <Icon icon="ep:setting" :size="14" class="mr-5px" />编辑
          </div>
          <div
            class="w-50% m-12px m-l-0px m-r-0px hover-c-#2681f3 cursor-pointer flex flex-items-center justify-center"
            @click="handleDelete(item.id)"
            v-hasPermi="['bus:patrolForm:delete']"
          >
            <Icon icon="ep:delete" :size="14" class="mr-5px" />删除
          </div>
        </div>
      </div>
    </div>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="getList" v-if="showForm" @clone="clone" />
</template>

<script setup lang="ts">
import { formApi } from '@/api/bus/patrol/form/index'
import Form from './Form.vue'
import { onMounted } from 'vue'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const formRef = ref() // 表单弹窗
const showForm = ref(false) // 是否显示表单弹窗
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10
})
const total = ref(0)
const list = ref([])
const getList = () => {
  queryParams.application = application.value // 应用标识
  loading.value = true
  formApi.getPage(queryParams).then((res) => {
    list.value = res.list
    total.value = res.total
    loading.value = false
  })
}
// 删除
const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await formApi.delete(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch {}
}
//编辑
const openForm = (type: string, id: number) => {
  showForm.value = true
  setTimeout(() => {
    formRef.value.openForm(type, id)
  }, 50)
}
const clone = () => {
  showForm.value = false
}
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
onMounted(async () => {
  await getapplication()
  getList()
})
</script>
<style scoped lang="scss">
.TemplateItem {
  border: 1px solid rgba(0, 0, 0, 10%);
  box-shadow:
    0 0 5px #0000001a,
    0 0 5px #0000001a;
}
</style>
