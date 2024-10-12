<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="page">
    <ContentWrap>
      <div class="row">
        <div>
          <Icon
            icon="ep:arrow-left"
            @click="emit('back', false)"
            class="mr-5px cursor-pointer hover:c-[var(--el-color-primary)]"
          />
          {{ table_detail_data.name }}
        </div>
        <div>
          <el-button type="primary" plain size="mini" @click="emit('change', props.detail_id)"
            >编辑</el-button
          >
          <el-button type="danger" plain size="mini" @click="emit('del', props.detail_id)"
            >删除</el-button
          >
        </div>
      </div>
      <el-row :gutter="21" class="el_layout_row">
        <el-col class="row-el-col" :span="7"> 联系人：{{ table_detail_data.name || '--' }} </el-col>
        <el-col class="row-el-col" :span="7">
          行业分类：
          <span v-for="(item, index) in industry_option" :key="index">
            <span v-if="table_detail_data.industryId == item.id"> {{ item.name }} </span>
          </span>
        </el-col>
        <el-col class="row-el-col" :span="7">
          租客标签：
          <span v-for="(item, index) in tagInfo_option" :key="index">
            <span v-if="table_detail_data.tagInfo.includes(item.id.toString())">
              <span
                class="border-1px inline-block b-solid b-rd-6px border-gray m-r-10px p-10px p-t-2px p-b-2px"
                :style="{
                  background: item.color.bgColor,
                  borderColor: item.color.borderColor,
                  fontSize: '14px'
                }"
                >{{ item.name }}</span
              >
            </span>
          </span>
        </el-col>
        <el-col class="row-el-col" :span="7"> 邮箱：{{ table_detail_data.email || '--' }} </el-col>
        <el-col class="row-el-col" :span="7">
          租客编码：{{ table_detail_data.tenantNo || '--' }}
        </el-col>
        <el-col class="row-el-col" :span="7"> 所属房源：-- </el-col>
      </el-row>
    </ContentWrap>
    <ContentWrap>
      <div class="select_change">
        <div
          class="select_item"
          v-for="(item, index) in active_type == 0 ? selectList1 : selectList2"
          :key="index"
          :class="activeIndex == item.value ? 'active' : ''"
          @click="handleSelect(item.value)"
        >
          {{ item.label }}
        </div>
      </div>
    </ContentWrap>
    <div class="activeIndex0" v-if="activeIndex == 0">
      <el-card class="min-height" shadow="never">
        <template #header><span class="text-14px">账户信息</span> </template>
        <div class="min-height">
          <el-row :gutter="21">
            <el-col :span="21" class="ContentWrap_border_col">
              开户银行：{{ table_detail_data.bank || '--' }}
            </el-col>
            <el-col :span="21" class="ContentWrap_border_col">
              账号：{{ table_detail_data.account || '--' }}
            </el-col>
            <el-col :span="21" class="ContentWrap_border_col">
              电话：{{ table_detail_data.tel || '--' }}
            </el-col>
            <el-col :span="21" class="ContentWrap_border_col">
              纳税人识别号：{{ table_detail_data.businessInfo?.taxpayerIdNumber || '--' }}
            </el-col>
            <el-col :span="21" class="ContentWrap_border_col">
              <!-- 默认发票类型:{{ table_detail_data.invoiceType }} -->
              默认发票类型:
              <span v-for="(item, index) in invoiceType_option" :key="index">
                <span v-if="table_detail_data.invoiceType == item.value"> {{ item.label }} </span>
              </span>
            </el-col>
            <el-col :span="21" class="ContentWrap_border_col"> 默认发票模板：-- </el-col>
          </el-row>
        </div>
      </el-card>

      <el-card class="min-height" shadow="never">
        <template #header><span class="text-14px">企业信息</span> </template>
        <div class="min-height">
          <el-row :gutter="21">
            <el-col :span="7" class="ContentWrap_border_col">
              统一社会信用代码：
              {{ table_detail_data.businessInfo?.unifiedSocialCreditCode || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              纳税人识别号：
              {{ table_detail_data.businessInfo?.taxpayerIdNumber || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              注册号：
              {{ table_detail_data.businessInfo?.registrationNumber || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              组织机构代码：
              {{ table_detail_data.businessInfo?.organizationCode || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              法定代表人：
              {{ table_detail_data.businessInfo?.legalPerson || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              邮箱：
              {{ table_detail_data.businessInfo?.email || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              国籍：{{ table_detail_data.businessInfo?.birthCountry || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              注册资本(万)：
              {{ table_detail_data.businessInfo?.registeredCapital || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              经营状态：
              {{ table_detail_data.businessInfo?.operatingState || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              登记机关：
              {{ table_detail_data.businessInfo?.registrationAuthority || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              公司类型：
              {{ table_detail_data.businessInfo?.companyType || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              人员规模：
              {{ table_detail_data.businessInfo?.staffSize || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              英文名：
              {{ table_detail_data.businessInfo?.englishName || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              成立日期：
              {{ table_detail_data.businessInfo?.business_info_founding_time || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              核准日期：
              {{ table_detail_data.businessInfo?.approvalTime || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              营业期限：
              {{ table_detail_data.businessInfo?.business_info_business_term || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              所属地区：
              {{ table_detail_data.businessInfo?.district || '--' }}
            </el-col>

            <el-col :span="7" class="ContentWrap_border_col">
              注册地址：
              {{ table_detail_data.businessInfo?.address || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              实缴资本(万)：
              {{ table_detail_data.businessInfo?.actualCapital || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              国税编号：
              {{ table_detail_data.businessInfo?.nationalTaxNumber || '--' }}
            </el-col>
            <el-col :span="7" class="ContentWrap_border_col">
              地税编号：
              {{ table_detail_data.businessInfo?.localTaxNumber || '--' }}
            </el-col>
            <el-col :span="21" class="ContentWrap_border_col">
              所属行业：
              {{ table_detail_data.businessInfo?.industry || '--' }}
            </el-col>
            <el-col :span="21" class="ContentWrap_border_col">
              经营范围：
              {{ table_detail_data.businessInfo?.businessScope || '--' }}
            </el-col>
          </el-row>
        </div>
      </el-card>
    </div>
    <div class="activeIndex1" v-show="activeIndex == 1">
      <UserContracList ref="UserContracList_ref" />
    </div>
    <div class="activeIndex2" v-if="activeIndex == 2">
      <ContentWrap>
        <el-menu
          :default-active="active_zd"
          class="el-menu-demo !border-none villageMenu row_menu"
          mode="horizontal"
          @select="active_zd_func"
        >
          <el-menu-item index="0">收款账单</el-menu-item>
          <el-menu-item index="1">付款账单</el-menu-item>
        </el-menu>
        <el-table>
          <el-table-column label="费用类型" width="200px" align="center" prop="name" />
          <el-table-column label="账单金额" width="200px" align="center" prop="name" />
          <el-table-column label="应收金额" width="200px" align="center" prop="name" />
          <el-table-column label="实收金额" width="200px" align="center" prop="name" />
          <el-table-column label="需收金额" width="200px" align="center" prop="name" />
          <el-table-column label="楼宇名称" width="200px" align="center" prop="name" />
          <el-table-column label="楼号/房号" width="200px" align="center" prop="name" />
          <el-table-column label="应收日期" width="200px" align="center" prop="name" />
          <el-table-column label="税率" width="200px" align="center" prop="name" />
          <el-table-column label="税额" width="200px" align="center" prop="name" />
          <el-table-column label="逾期状态" width="200px" align="center" prop="name" />
          <el-table-column label="结清状态" width="200px" align="center" prop="name" />
        </el-table>
      </ContentWrap>
    </div>
    <div class="activeIndex3" v-if="activeIndex == 3">
      <el-table>
        <el-table-column label="文件名称" align="center" prop="name" />
        <el-table-column label="上传人" align="center" prop="name" />
        <el-table-column label="上传时间" align="center" prop="name" />
        <el-table-column label="操作" align="center" prop="name" />
      </el-table>
    </div>
  </div>
</template>
<script lang="ts" setup>
import ContentWrap_border from './component/ContentWrap_border.vue'
import { OwnerApi, OwnerVO } from '@/api/bus/owner'
import TagIdArr from '@/components/TagIdArr/src/TagIdArr.vue'
import { TagOwnerApi } from '@/api/bus/tag/owner'
import UserContracList from './component/user_getcontracList.vue'
import { set } from 'lodash-es'
import { watch } from 'vue'
const industry_option = ref([])
const tagInfo_option = ref([])
const selectList1 = ref([
  { label: '详情', value: '0' },
  { label: '合同', value: '1' },
  { label: '账单', value: '2' },
  { label: '附件', value: '3' }
])
const selectList2 = ref([
  { label: '合同', value: '1' },
  { label: '账单', value: '2' },
  { label: '附件', value: '3' }
])

const activeIndex = ref(0)
const handleSelect = (key: string) => {
  activeIndex.value = key
}
const active_zd = ref('0')

const active_zd_func = (key: string) => {
  active_zd.value = key
}
const UserContracList_ref = ref(null)
const props = withDefaults(
  defineProps<{
    table_detail_data: object
    active_type: string
    detail_id: string
  }>(),
  {}
)

watch(
  () => props.active_type,
  (val) => {
    if (JSON.stringify(val)) {
      activeIndex.value = Number(props.active_type)
      setTimeout(() => {
        // if (UserContracList_ref.value) {
        //   UserContracList_ref.value.getList(props.detail_id)
        // }
      }, 50)
    }
  },
  {
    immediate: true,
    deep: true
  }
)

//默认发票模板
const invoiceType_option = ref([
  { label: '增值税普通发票', value: '0' },
  { label: '增值税专用发票', value: '1' },
  { label: '增值税电子普通发票', value: '1' }
])

/** 获取行业分类 */
const getTagIndustryList = async (name) => {
  const data = await OwnerApi.getTagIndustryList(name)
  industry_option.value = data
}
/** 获取行业标签 */
const getTagOwnerList = async (name) => {
  const data = await TagOwnerApi.getTagOwnerList(name)
  tagInfo_option.value = data
  tagInfo_option.value.forEach((item) => {
    if (item.color) {
      item.color = JSON.parse(item.color)
    } else {
      item.color = JSON.stringify([])
    }
  })
}
onMounted(() => {
  getTagIndustryList('')
  getTagOwnerList('')
})
const emit = defineEmits(['back', 'change', 'del'])

function getContracList(detailId: string) {
  if (UserContracList_ref.value) {
    UserContracList_ref.value.getList(detailId)
  }
}
defineExpose({ getContracList })
</script>
<style lang="scss" scoped>
.page {
  .row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 20px;
    font-weight: 600;
  }
  .el_layout_row {
    font-size: 14px;
  }

  .row-el-col {
    margin-top: 16px;
  }
  .select_change {
    display: flex;
    gap: 30px;
    .select_item {
      padding-bottom: 6px;
      cursor: pointer;
      font-size: 16px;
    }
    .active {
      border-bottom: 3px solid #409eff;
    }
  }
  .ContentWrap_border_col {
    font-size: 14px;
    padding: 6px 0;
  }
}
.activeIndex0 {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 20px;
  .min-height {
    min-height: 400px;
  }
}
</style>
