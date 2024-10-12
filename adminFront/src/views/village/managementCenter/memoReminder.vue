<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="memoReminder">
    <ContentWrap title="备忘提醒" class="m-10px">
      <template #header>
        <div class="flex-1 flex justify-end items-center">
          <el-button @click="addRemark()">
            <Icon icon="ep:plus" color="#00000040" class="m-r-6px" />
            添加备忘
          </el-button>
        </div></template
      >
      <el-table
        :data="RemarkList"
        border
        v-loading="RemarkLoading"
        :header-cell-style="{
          color: '#000000d9',
          fontSize: '14px',
          fontWeight: '500',
          backgroundColor: '#fafafa'
        }"
      >
        <el-table-column label="内容" prop="remark" />
        <el-table-column label="操作人" prop="creator" />
        <el-table-column label="操作时间" prop="createTime" :formatter="dateFormatter" />
      </el-table>
      <Pagination
        :total="RemarkTotal"
        v-model:page="RemarkParams.pageNo"
        v-model:limit="RemarkParams.pageSize"
        @pagination="getRemarkList"
      />
    </ContentWrap>
  </div>
  <el-dialog title="添加备忘" width="600px" v-model="visible" @close="handleCancel">
    <el-input placeholder="请输入备忘内容" type="textarea" rows="4" v-model="value" />
    <template #footer>
      <div class="dialog-footer">
        <el-button size="small" @click="handleCancel">取 消</el-button>
        <el-button size="small" type="primary" @click="handleOk" :loading="confirmLoading"
          >确 定</el-button
        >
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
defineOptions({ name: 'MemoReminder' })
import { dateFormatter } from '@/utils/formatTime'
import { roomRemarkApi } from '@/api/bus/roomRemarks'
const message = useMessage() // 消息弹窗
const props = defineProps({
  info: {
    type: Object,
    required: true,
    default: () => {}
  },
  roomId: {
    type: Number,
    required: true,
    default: () => undefined
  }
})

const RemarkParams = reactive({
  pageNo: 1,
  pageSize: 10
})
const RemarkList = ref([])
const RemarkLoading = ref(false)
const RemarkTotal = ref(0)
const value = ref('')
const visible = ref(false)
const confirmLoading = ref(false)

/**
 * 添加备忘
 */
const handleOk = async () => {
  if (!value.value) return message.error('请输入备忘内容')
  try {
    confirmLoading.value = true
    await roomRemarkApi.create({
      remark: value.value,
      roomId: props.roomId,
      villageId: props.info.villageId
    })
    visible.value = false
    getRemarkList()
  } catch (error) {
  } finally {
    confirmLoading.value = false
  }
}

/**
 * 添加备忘按钮
 */
const addRemark = () => {
  value.value = ''
  visible.value = true
}

/**
 * 取消按钮
 */
const handleCancel = () => {
  visible.value = false
}
/**
 * 获取备忘列表
 */
const getRemarkList = async () => {
  try {
    RemarkLoading.value = true
    const data = await roomRemarkApi.getPage({
      ...RemarkParams,
      roomId: props.roomId
    })
    RemarkList.value = data.list
    RemarkTotal.value = data.total
  } catch (error) {
  } finally {
    RemarkLoading.value = false
  }
}

watch(
  () => props.roomId,
  (val) => {
    console.log('监听id')
    if (val) getRemarkList()
  }
)
onMounted(() => {
  console.log('初始化', props.roomId)
  getRemarkList()
})
</script>
