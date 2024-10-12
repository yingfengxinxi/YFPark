<template>
  <div class="h-50px bg-white mb-15px flex items-center justify-between px-8px box-border rounded">
    <div class="flex items-center gap-18px">
      <Icon icon="ep:arrow-left" @click="emit('clone')" />
      <span>{{ type == 'update' ? '编辑表单' : '新建表单' }}</span>
    </div>
    <el-button type="primary" @click="submit">保存</el-button>
  </div>
  <div class="flex justify-between page">
    <div class="left w-30% bg-white shadow_right">
      <el-row :gutter="8" class="p-10px pt-2px">
        <el-col
          :span="12"
          v-for="(item, index) in formList"
          :key="index"
          class="mt-10px"
          @click="pushList(item)"
          v-show="item.label != ''"
        >
          <div
            class="flex justify-between p-10px rounded border-#DDDDDD border-1 border-solid text-14px"
          >
            {{ item.label }}
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="w-40% pos-relative">
      <Phone
        class="pos-absolute pos-top-50% pos-left-50% transform-translate-[-50%]"
        ref="phone_ref"
        @select="selectPhone"
      />
    </div>
    <div class="right w-30% bg-white shadow_right">
      <Setting ref="Setting_ref" @changeItem="changeItem" />
    </div>
  </div>
</template>
<script lang="ts" setup>
import Phone from './phone.vue'
import Setting from './components/Setting.vue'
import { Api } from '@/api/contract/contractList/index'
import { formApi } from '@/api/bus/patrol/form/index'
const formList = ref([])
const type = ref('')
const detailID = ref(0)
const submitValue = ref('')
const emit = defineEmits(['clone', 'success'])
onMounted(async () => {
  await getapplication()
  Api.getProject({
    dictType: 'PATROL_FORM',
    pageNo: 1,
    pageSize: 100
  }).then(async (res) => {
    await res.list.forEach((item: any, index) => {
      if (item.value == 'defaultRadio') {
        if (type.value == 'add') {
          pushList(item)
        }
        item.label = ''
      }
    })
    formList.value = res.list
  })
})
const phone_ref = ref<any>(null)
const pushList = (res: any) => {
  const data = JSON.parse(JSON.stringify(res))
  phone_ref.value.pushList(data)
}
const Setting_ref = ref<any>(null)
const selectPhone = (res: any) => {
  if (Setting_ref.value) {
    Setting_ref.value.setItem(res)
  }
}
const changeItem = (item: any) => {
  phone_ref.value.changeList(item)
}
const submit = async () => {
  const list = JSON.parse(JSON.stringify(phone_ref.value.list))
  let submit = true
  await list.forEach(async (item: any, index) => {
    if (typeof item.remark === 'string') {
      item.remark = JSON.parse(item.remark)
    }
    if (item.value == 'check') {
      if (item.remark.setting.options.length != 0) {
        item.remark.setting.options.forEach((option: any, index: number) => {
          if (option.name == '') {
            submit = false
            ElMessage({
              type: 'error',
              message: '多选下拉框选项不能为空'
            })
            return
          }
        })
      }
    } else if (item.value == 'radio') {
      if (item.remark.setting.setDefault) {
        if (item.remark.setting.defaultValue == '') {
          submit = false
          ElMessage({
            type: 'error',
            message: '单选下拉框默认值不能为空'
          })
          return
        }
      }
      if (item.remark.setting.options.length != 0) {
        item.remark.setting.options.forEach((option: any, index: number) => {
          if (option.name == '') {
            submit = false
            ElMessage({
              type: 'error',
              message: '单选下拉框选项不能为空'
            })
            return
          }
        })
      }
    } else if (item.value == 'input') {
      if (item.remark.setting.setDefault) {
        if (item.remark.setting.defaultValue == '') {
          submit = false
          ElMessage({
            type: 'error',
            message: '单选下拉框默认值不能为空'
          })
          return
        }
      }
    }
  })
  list.forEach((item: any, index: number) => {
    item.remark = JSON.stringify(item.remark)
  })
  if (submit) {
    const title = type.value == 'update' ? '编辑表单' : '新建表单'
    ElMessageBox.prompt('表单名称', title, {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      inputPattern: /\S/,
      inputErrorMessage: '请输入表单名称',
      inputValue: submitValue.value
    }).then(({ value }) => {
      if (type.value == 'update') {
        formApi
          .update({
            id: detailID.value,
            title: value,
            content: JSON.stringify(list),
            isDefault: 0,
            application: application.value
          })
          .then((res) => {
            emit('clone')
            emit('success')
            ElMessage({
              type: 'success',
              message: '编辑成功'
            })
          })
        return
      } else if (type.value == 'add') {
        formApi
          .create({
            title: value,
            content: JSON.stringify(list),
            isDefault: 0,
            application: application.value
          })
          .then((res) => {
            emit('clone')
            emit('success')
            ElMessage({
              type: 'success',
              message: '新建成功'
            })
          })
      }
    })
  }
}
/////////
function openForm(params: type, id: number) {
  type.value = params
  submitValue.value = ''
  detailID.value = 0
  if (params == 'update') {
    formApi.get(id).then((res) => {
      const data = JSON.parse(res.content)
      submitValue.value = res.title
      detailID.value = res.id
      data.forEach((item: any, index: number) => {
        pushList(item)
        item.label = ''
      })
    })
  }
}
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
defineExpose({ openForm })
</script>
<style lang="scss" scoped>
.page {
  height: calc(100vh - 190px);
}
.shadow_right {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
