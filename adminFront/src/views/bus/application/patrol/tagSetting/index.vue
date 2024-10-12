<template>
  <div class="flex page">
    <div class="w-30% bg-white px-10px pt-10px box-border">
      <div class="pb-4px border-#409eff border-b-3px border-solid border-0 w-4em mb-10px"
        >选择模板</div
      >
      <el-row :gutter="10">
        <el-col :span="12" v-for="(item, index) in templateList" :key="index" class="mt-3px">
          <img
            :src="item.value"
            alt=""
            class="w-100% h-80px"
            :class="item.label == selectData.name ? 'active' : 'unactive'"
            @click="active_template(index, item)"
          />

          <div class="text-center text-12px">{{ item.label }}</div>
        </el-col>
      </el-row>
    </div>
    <div class="w-40% pos-relative">
      <div
        class="flex w-340px bg-white pos-absolute pos-top-50% pos-left-50% transform-translate--50% items-center p-10px flex-wrap gap-10px"
      >
        <div class="w-130px">
          <img src="./image/qrcode.png" alt="" class="w-130px h-130px" />
        </div>
        <div class="flex-1 flex flex-col justify-around">
          <div v-if="selectData.hasLogo == '0'">
            <img
              :src="selectData.applyJson.logo"
              class="w-80px h-40px"
              v-if="selectData.applyJson.logo"
            />
            <img src="./image/logo.png" class="w-80px h-40px" v-else />
          </div>
          <div v-if="selectData.applyJson.fields.length == 0">
            <div
              v-for="(item, index) in selectData.fieldLimit"
              :key="index"
              class="h-20px text-14px"
              >字段名称:****</div
            >
          </div>
          <div v-else>
            <div
              v-for="(item, index) in selectData.applyJson.fields"
              :key="index"
              class="h-20px text-14px"
              >{{ item.name || item.label }}:****</div
            >
          </div>
        </div>
        <div class="w-100% text-center mt-20px pos-absolute pos-bottom--30px">
          {{ selectData.name }}
        </div>
      </div>
    </div>
    <div class="w-30% bg-white p-10px box-border overflow-y-scroll pos-relative pb-100px">
      <div class="pb-4px border-#409eff border-b-3px border-solid border-0 w-4em mb-10px"
        >编辑标签</div
      >
      <div class="flex justify-between pos-relative">
        <div class="w-40%">
          <div v-for="(item, index) in tagList" :key="index">
            <div
              v-if="selectData.applyJson.fields.findIndex((tag) => tag.value == item.value) === -1"
              @click="pushselectValue(item)"
              class="bg-#F5F7F9 text-14px px-16px py-8px mt-10px rounded cursor-pointer notselect"
            >
              {{ item.label }}
            </div></div
          >
        </div>
        <div class="pos-absolute pos-top-50% pos-left-50% transform-translate-[-50%]">
          <Icon icon="ep:switch" />
        </div>
        <div class="w-40%">
          <div v-for="(item, index) in selectData.applyJson.fields" :key="index">
            <div
              class="bg-#F5F7F9 text-14px px-16px py-8px mt-10px rounded cursor-pointer notselect"
              @click="delselectValue(index)"
            >
              {{ item.label }}
            </div></div
          >
        </div>
      </div>
      <!-- logo -->
      <div
        class="pb-4px border-#409eff border-b-3px border-solid border-0 w-5em mb-10px mt-30px"
        v-if="selectData.hasLogo == '0'"
        >设置logo</div
      >
      <el-upload
        class="uploader_BOX mb-10px"
        :on-change="faceChange"
        :show-file-list="false"
        :auto-upload="false"
        v-if="selectData.hasLogo == '0'"
      >
        <img v-if="selectData.applyJson.logo" :src="selectData.applyJson.logo" alt="" class="img" />
        <img src="@/views/bus/owner/component/image/plus.png" v-else class="plus" alt="" />
      </el-upload>
      <el-alert
        title="建议上传尺寸为720*280像素且不大于2m的jpg或png图片"
        type="warning"
        :closable="false"
        v-if="selectData.hasLogo == '0'"
      />
      <!-- 打印设置 -->
      <div class="pb-4px border-#409eff border-b-3px border-solid border-0 w-5em mb-10px mt-30px"
        >打印设置</div
      >
      <el-radio-group v-model="selectData.applyJson.paper_type">
        <el-radio
          v-for="(item, index) in paper_type"
          :key="index"
          :label="item.label"
          :value="item.value"
          >{{ item.label }}</el-radio
        >
      </el-radio-group>
      <div class="mt-20px" v-if="selectData.applyJson.paper_type == '0'">
        <el-form>
          <el-form-item label="标签宽度(mm):">
            <el-input-number
              v-model="selectData.applyJson.tag_width"
              controls-position="right"
              :min="1"
            />
          </el-form-item>
          <el-form-item label="标签高度(mm):">
            <el-input-number
              v-model="selectData.applyJson.tag_height"
              controls-position="right"
              :min="1"
            />
          </el-form-item>
        </el-form>
      </div>
      <div class="submit pos-fixed pos-bottom-18px pos-right-40px h-40px bg-#fff flex justify-end">
        <el-button type="primary" @click="submit">保存</el-button>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { settingApi } from '@/api/bus/patrol/tagSetting'
import { Api } from '@/api/contract/contractList/index'
import { OwnerApi } from '@/api/bus/owner'
import { getBoolDictOptions, getIntDictOptions, getStrDictOptions } from '@/utils/dict'
import logoImg from '@/assets/imgs/logo.png'
const tagList = ref([])
const message = useMessage() // 消息弹窗
const pushselectValue = (item) => {
  if (selectData.value.applyJson.fields.length >= selectData.value.fieldLimit) {
    message.error(`该模板最多可选择${selectData.value.fieldLimit}个标签`)
    return
  }
  selectData.value.applyJson.fields.push(item)
}
const application = ref('')
const route = useRoute() // 路由对象
//获取应用标识
const getapplication = () => {
  const applicationName = route.name
  application.value = applicationName.split('=')[1]
}
const delselectValue = (index) => {
  selectData.value.applyJson.fields.splice(index, 1)
}
const active = ref(-1)
const active_template = async (index: number, item: any) => {
  active.value = index
  const remark = JSON.parse(item.remark)
  selectData.value.hasLogo = remark.hasLogo
  selectData.value.fieldLimit = Number(remark.fieldLimit)
  selectData.value.name = item.label
  selectData.value.applyJson.fields = []
}
const logo_url = ref('')
// 模板列表
const templateList = ref([])
const GetTemplateList = async () => {
  const res = await Api.getProject({
    dictType: 'PATROL_TAG_TEMPLATE',
    pageNo: 1,
    pageSize: 100
  })
  templateList.value = res.list
}
//获取现有标签
const GetTagList = async () => {
  const res = await settingApi.getTemplateList({
    application: application.value
  })
  selectData.value = res[0]
  selectData.value.applyJson = JSON.parse(selectData.value.applyJson)
  // selectData.value.applyJson = JSON.parse(
  //   '{"fields":[],"tag_width":1,"tag_height":1,"paper_type":1}'
  // )
}
const paper_type = ref({})
onMounted(async () => {
  await getapplication()
  GetTemplateList()
  GetTagList()
  tagList.value = await getStrDictOptions('PATROL_TAG_TEMPLATE_1')
  //获取纸张类型
  paper_type.value = await getIntDictOptions('PAPER_TYPE')
})
//上传logo
const faceChange = (file: any) => {
  const faceimg = new FormData()
  faceimg.append('file', file.raw)
  OwnerApi.uploadFile(faceimg).then((res) => {
    selectData.value.applyJson.logo = res.data
  })
}
////////////
// 现在所选择的模板data
const selectData = ref({
  hasLogo: 1,
  fieldLimit: 1,
  name: '',
  logo: '',
  applyJson: {
    fields: [],
    tag_width: 0,
    tag_height: 0,
    paper_type: 0
  }
})
const submit = () => {
  if (selectData.value.applyJson.fields.length == 0) {
    message.error('请至少选择一个标签')
    return
  }
  const data = {
    id: selectData.value.id,
    applyJson: JSON.stringify(selectData.value.applyJson),
    fieldLimit: selectData.value.fieldLimit,
    hasLogo: selectData.value.hasLogo,
    templateId: selectData.value.templateId,
    name: selectData.value.name,
    application: application.value
  }
  settingApi.updateTemplate(data).then(() => {
    message.success('保存成功')
  })
}
</script>
<style lang="scss" scoped>
.page {
  height: calc(100vh - 125px);
}
.active {
  border: 2px solid #409eff;
  border-radius: 5px;
}
.unactive {
  border: 2px solid #e7e7e7;
  border-radius: 5px;
}
.uploader_BOX {
  background: #f7f7f7;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 110px;
  height: 110px;
  display: flex;
  justify-content: center;
  align-items: center;
  .img {
    width: 100%;
    height: 100%;
  }
  .plus {
    width: 40px;
    height: 40px;
  }
}
.notselect {
  user-select: none;
}
</style>
