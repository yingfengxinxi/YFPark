<template>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="原始名称" align="center" prop="inputName" />
      <el-table-column label="自定义名称" align="center" prop="inputNameCus">
        <template #default="{ row }">
          <el-input
            v-model="row.inputNameCus"
            :disabled="!row.isAllowModifie"
            @change="tablechange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="输入框类型" align="center" prop="inputType">
        <template #default="{ row }">
          <div v-if="row.inputType == 'text'"> 输入框 </div>
          <div class="hover" v-else-if="row.inputType == 'select'" @click="open_select(row)">
            下拉框
          </div>
          <div v-else-if="row.inputType == 'radio'"> 单选框 </div>
          <div v-else-if="row.inputType == 'checkbox'"> 复选框 </div>
          <div v-else-if="row.inputType == 'textarea'"> 多行文本 </div>
          <div v-else-if="row.inputType == 'date'"> 日期 </div>
          <div v-else-if="row.inputType == 'number'"> 时间 </div>
          <div v-else-if="row.inputType == 'multi_select'" class="hover" @click="open_select(row)">
            多选下拉框
          </div>
          <div v-else-if="row.inputType == 'json'" class="hover" @click="open_table(row)">
            自定义多字段项
          </div>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isEnable">
        <template #default="{ row }">
          <el-switch
            v-model="row.isEnable"
            inline-prompt
            active-text="启用"
            inactive-text="禁用"
            :active-value="1"
            :inactive-value="0"
            @change="tablechange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="是否必填" align="center" prop="isRequired">
        <template #default="{ row }">
          <el-switch
            v-model="row.isRequired"
            inline-prompt
            active-text="必填"
            inactive-text="非必填"
            :active-value="1"
            :inactive-value="0"
            @change="tablechange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort">
        <template #default="{ row }">
          <el-input-number
            v-model="row.sort"
            class="mx-4"
            controls-position="right"
            @change="tablechange(row)"
          />
        </template>
      </el-table-column>
    </el-table>
  </ContentWrap>
  <!-- 自定义多字段选项弹窗 -->
  <el-dialog :title="dialogTitle" v-model="dialogVisible" width="50%">
    <el-table :data="data">
      <el-table-column label="字段名称" prop="field_name" align="center" />
      <el-table-column label="原始名称" prop="input_name" align="center" />
      <el-table-column label="输入框类型" prop="input_type" align="center" />
      <el-table-column label="是否必填" prop="is_required" align="center">
        <template #default="{ row }">
          {{ row.is_required == 1 ? '是' : '否' }}
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { UserFieldExtendApi, UserFieldExtendVO } from '@/api/bus/userFieldExtend'
import UserFieldExtendForm from './UserFieldExtendForm.vue'
import { split } from 'lodash-es'

/** 用户扩展信息自定义系统设置 列表 */
defineOptions({ name: 'UserFieldExtend' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<UserFieldExtendVO[]>([]) // 列表的数据

const exportLoading = ref(false) // 导出的加载中
const getList = async () => {
  loading.value = true
  try {
    const data = await UserFieldExtendApi.getUserFieldExtendPage({
      pageNo: 1,
      pageSize: 10
    })
    list.value = data
  } catch (error) {
    console.error(error)
  }
  loading.value = false
}
const open_select = (row) => {
  if (typeof row.defaultFieldValue == 'string') {
    row.defaultFieldValue = JSON.parse(row.defaultFieldValue)
  }

  let html = ''
  row.defaultFieldValue.map((item) => {
    html += `<span class="open_select_span">${item.value}</span>`
  })
  ElMessageBox.alert(`<div class="spanbox"><div>可选项</div>${html}</div>`, row.inputName, {
    dangerouslyUseHTMLString: true
  })
}
// 自定义多字段项
const data = ref([])
const columns = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const open_table = (row) => {
  if (typeof row.jsonFields == 'string') {
    row.jsonFields = JSON.parse(row.jsonFields)
  }
  data.value = row.jsonFields
  console.log(data.value)

  dialogTitle.value = row.inputName
  dialogVisible.value = true
}
const tablechange = async (row) => {
  if (typeof row.defaultFieldValue != 'string') {
    row.defaultFieldValue = JSON.stringify(row.defaultFieldValue)
  }
  if (typeof row.jsonFields != 'string') {
    row.jsonFields = JSON.stringify(row.jsonFields)
  }
  await UserFieldExtendApi.updateUserFieldExtend(row).then(() => {
    message.success('修改成功')
  })
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
<style lang="scss" scoped>
.hover {
  cursor: pointer;
  color: #1890ff;
}
.open_select_span {
  margin: 0 10px;
}
</style>
<style lang="scss">
.spanbox {
  .open_select_span {
    margin: 0 6px;
  }
}
</style>
