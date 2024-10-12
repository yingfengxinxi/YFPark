<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <!-- 新建楼层 -->
  <el-drawer v-model="editGardenShow" direction="rtl" size="900px" :title="title">
    <template #default>
      <div>
        <div class="flex justify-between">
          楼层管理
          <el-button type="primary" @click="AddFloorClick()">
            <Icon class="mr-5px" icon="ep:plus" />添加楼层</el-button
          >
        </div>
        <el-table
          :data="List"
          border
          style="width: 100%"
          class="m-t-20px"
          v-loading="formLoading"
          :header-cell-style="{ fontSize: '14px', color: '#000000d9', fontWeight: 'initial' }"
        >
          <el-table-column label="Date" prop="layerNumber">
            <template #header>
              楼层数字编号
              <el-tooltip
                content="用于楼层列表进行排序，以及未来智能设备等第三方对接使用。地下室使用负数表示"
                placement="top"
              >
                <Icon class="" icon="fa:question-circle-o" />
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="楼层名称" prop="layerName" />
          <el-table-column label="楼层可视化">
            <template #default="scope">
              <template v-if="scope.row.threeDimensionalId">
                <el-button type="primary" size="small" @click="AddFloorClick()">
                  添加楼层</el-button
                >
                <el-button type="primary" size="small" @click="AddFloorClick()">
                  添加楼层</el-button
                >
              </template>
              <template v-else> -- </template>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <!-- <template #header>
        <el-input v-model="search" size="small" placeholder="Type to search" />
      </template> -->
            <template #default="scope">
              <el-button
                link
                size="small"
                type="primary"
                @click="editFloor(scope.$index, scope.row)"
              >
                编辑
              </el-button>
              <el-button link size="small" type="danger" @click="handleDelete(scope.row.id)">
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
        <AddFloor ref="AddFloorRef" @success="getList" />
      </div>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
defineOptions({ name: 'AddFloor' })
const message = useMessage() // 消息弹窗
import { FloorApi } from '@/api/bus/village/floor'
const { t } = useI18n() // 国际化
const editGardenShow = ref(false)
const title = ref('楼层管理')
import AddFloor from './AddFloor.vue'
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const total = ref(0)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  buildId: null,
  villageId: null
})
const detailInfo = ref({})
/** 打开抽屉 */
const open = async (id?: number, village?: number, form?: any) => {
  console.log(status, 'status', id)

  editGardenShow.value = true
  queryParams.buildId = id
  queryParams.villageId = village
  detailInfo.value = form
  console.log(form, 'form')
  title.value = detailInfo.value.buildName + ' - 楼层管理'
  getList()
}

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

const AddFloorRef = ref()
const AddFloorClick = () => {
  AddFloorRef.value.open('create', queryParams.buildId, queryParams.villageId, '')
}

const editFloor = (index: number, row: any) => {
  AddFloorRef.value.open(
    'updated',
    queryParams.buildId,
    queryParams.villageId,
    JSON.parse(JSON.stringify(row))
  )
}
const List = ref([])
const getList = async () => {
  try {
    formLoading.value = true
    const data = await FloorApi.getlayerPage(queryParams)
    console.log(data, 'data')
    List.value = data.list
    total.value = data.total
    console.log(List.value, 'lit')
  } finally {
    formLoading.value = false
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await FloorApi.deletelayer(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(() => {})
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style scoped lang="scss">
::v-deep .el-drawer.rtl {
  background: #6aabc5;
}

.investmen-personne {
  width: 100%;
  // border: 1px solid #d9d9d9;
  border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
  padding: 5px 18px 4px 5px;
  cursor: pointer;
  line-height: 1.7;
  position: relative;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color)) inset;
}

.closeCircle {
  position: absolute;
  right: 2px;
  top: 0;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 16px;
  cursor: pointer;
}
</style>
<style>
.el-input-number .el-input__inner {
  text-align: left !important;
}
</style>
