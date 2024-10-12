<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="bg-white rounded px-[18px] text-[16px] p-[20px] pb-0">
    <div class="flex items-center justify-between">
      <div>
        <div class="text-[20px] font-semibold">价格标准</div>
        <div class="text-[14px] mt-[30px]">
          用于在抄表后按价格标准自动生成缴费账单，同一使用范围的价格标准仅能存在一个。
        </div>
        <div class="text-[14px] mt-[10px]">
          注意，相互关联的总表分表只有拥有相同抄表周期才能计算公摊用量。</div
        >
      </div>
      <img
        src="https://gtlt.sc.yichang.gov.cn/public_web/static/images/energy/banner.jpg"
        alt=""
        class="w-[189px] h-[135px]"
      />
    </div>
    <div class="flex gap-[30px]">
      <span
        v-for="(item, index) in energyTypeList"
        :key="index"
        class="text-[15px] pb-[6px] cursor-pointer"
        :class="index == headerSelect ? 'active' : ''"
        @click="changeheaderSelect(index)"
        >{{ item?.name }}</span
      >
    </div>
  </div>
  <div class="mt-[20px]">
    <ContentWrap class="min-h-[500px]">
      <div class="grid grid-cols-4 grid-gap-[20px] c-[#929292]">
        <div
          class="b-dashed b-rd-[4px] border-[#f0f0f0] border-solid h-[200px] bg-[#fff] box-border cursor-pointer flex justify-center items-center font-size-[16px]"
          @click="openForm('create')"
        >
          <Icon icon="ep:plus" :size="20" class="mr-[5px]" /> 添加{{
            energyTypeList[headerSelect]?.name
          }}标准
        </div>

        <div
          v-loading="loading"
          v-for="(item, index) in list"
          :key="index"
          class="b-rd-[4px] h-[200px] bg-[#fff] box-border flex justify-between flex-col TemplateItem pt-[20px]"
        >
          <div class="flex px-[20px]">
            <Icon icon="ep:clock" :size="48" color="#929292" class="mr-[5%]" />
            <div class="flex-1 overflow-hidden">
              <div class="c-[#000000d9] font-size-[16px]">
                {{ item.name }}
                <div
                  class="c-[#929292] font-size-[14px] m-b-[8px] line-clamp-2"
                  v-if="item.isStagePrice == 0"
                >
                  价格:{{ item.unitPrice + '元/' + energyTypeList[headerSelect]?.unit }}
                </div>
                <div class="c-[#929292] font-size-[14px] m-b-[8px] line-clamp-2" v-else>
                  <el-tooltip
                    class="box-item"
                    effect="dark"
                    :content="item.unitPriceName"
                    placement="top"
                  >
                    价格: {{ item.unitPriceName }}
                  </el-tooltip>
                </div>
              </div>
            </div>
          </div>
          <div class="text-center text-[14px] line-clamp-2">
            <el-tooltip
              class="box-item"
              effect="dark"
              :content="item.villageName + '/' + item.buildName + '/' + item.roomName"
              placement="top"
            >
              使用范围:{{ item.villageName + '/' + item.buildName + '/' + item.roomName }}
            </el-tooltip>
          </div>
          <div
            class="flex text-center justify-between border border-solid border-[#f0f0f0] b-b-none b-l-none b-r-none font-size-[14px]"
          >
            <div
              class="w-[50%] m-[12px] m-l-[0px] m-r-[0px] border border-solid border-[#f0f0f0] b-b-none b-l-none b-t-none hover-c-[#2681f3] cursor-pointer flex flex-items-center justify-center"
              @click="openForm('update', item.id)"
            >
              <Icon icon="ep:setting" :size="14" class="mr-[5px]" />编辑
            </div>
            <div
              class="w-[50%] m-[12px] m-l-[0px] m-r-[0px] hover-c-[#2681f3] cursor-pointer flex flex-items-center justify-center"
              @click="handleDelete(item.id)"
            >
              <Icon icon="ep:delete" :size="14" class="mr-[5px]" />删除
            </div>
          </div>
        </div>
      </div>
      <!-- 分页 -->
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getTable"
      />
    </ContentWrap>
  </div>

  <!-- 表单弹窗：添加/修改 -->
  <Form ref="formRef" @success="getTable" />
</template>

<script setup lang="ts">
import { energyPriceApi, VO } from '@/api/bus/WaterElectricity/EnergyPrice/index.ts'
import { energyType } from '@/api/bus/WaterElectricity/EnergyType/index.ts'
import Form from './Form.vue'
import { onMounted } from 'vue'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const formRef = ref() // 表单弹窗
const showForm = ref(false) // 是否显示表单弹窗
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  type: ''
})
const headerSelect = ref(0) // 表种类选择
onMounted(async () => {
  await getenergyTypeList()
})
const total = ref(0)
const list = ref([])
const getTable = () => {
  loading.value = true
  console.log(energyTypeList.value)
  queryParams.value.type = energyTypeList.value[headerSelect.value].equipType
  energyPriceApi.getPage(queryParams.value).then((res) => {
    list.value = res.list
    total.value = res.total
    loading.value = false
    list.value.forEach((item) => {
      if (item.isStagePrice == 1) {
        const unitPriceList = JSON.parse(item.unitPrice)
        let unitPriceTitle = ''
        unitPriceList.forEach((unitPrice) => {
          unitPriceTitle =
            unitPriceTitle +
            unitPrice.startUsageRange +
            '-' +
            unitPrice.endUsageRange +
            ' ' +
            unitPrice.price +
            '元/' +
            energyTypeList.value[headerSelect.value]?.unit +
            ','
        })
        item.unitPriceName = unitPriceTitle
      }
    })
  })
}
// 删除
const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await energyPriceApi.delete(id)
    message.success(t('common.delSuccess'))
    await getTable()
  } catch {}
}
//编辑
const openForm = (type: string, id: number) => {
  showForm.value = true
  setTimeout(() => {
    formRef.value.open(type, energyTypeList.value[headerSelect.value], id)
  }, 50)
}
//获得表种类列表
const energyTypeList = ref([])
const getenergyTypeList = () => {
  energyType.getList().then((res) => {
    energyTypeList.value = res
    queryParams.value.type = res[0].equipType
    getTable()
  })
}
// 表种类选择
const changeheaderSelect = (index: number) => {
  headerSelect.value = index
  getTable()
}
</script>
<style scoped lang="scss">
.TemplateItem {
  border: 1px solid rgba(0, 0, 0, 10%);
  box-shadow:
    0 0 5px #0000001a,
    0 0 5px #0000001a;
}
.active {
  border-bottom: 2px solid #2681f3;
}
</style>
