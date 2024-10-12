<template>
  <div class="page">
    <ContentWrap_border :header_data="'血型'">
      <Blood :data="echastsData.blood_type" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'学历'">
      <Educational :data="echastsData.education" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'政治面貌'">
      <Political :data="echastsData.political_status" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'民族'">
      <Nation :data="echastsData.nation" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'护照等级'">
      <Passport :data="echastsData.care_level" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'婚姻状况'">
      <Matrimony :data="echastsData.marital_status" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'住房性质'">
      <Trusteeship :data="echastsData.house_type" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'居住情况'">
      <Reside :data="echastsData.living_conditions" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'服务类型'">
      <Service :data="echastsData.custody" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'失能情况'">
      <Disability :data="echastsData.failure" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'残疾情况'">
      <Disability2 :data="echastsData.chronic" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'离退休人员'">
      <Retire :data="echastsData.retiree" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'社区重点人群'">
      <Community :data="echastsData.nature_employment" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'是否重点儿童'">
      <Children :data="echastsData.foucus_children" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'是否低保户'">
      <Subsistence :data="echastsData.is_dibaohu" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'参保类型'">
      <Type :data="echastsData.insurance_type" />
    </ContentWrap_border>
    <ContentWrap_border :header_data="'信仰宗教'">
      <Religion :data="echastsData.religious_belief" />
    </ContentWrap_border>
  </div>
</template>
<script setup lang="ts">
import {
  Educational,
  Blood,
  Political,
  Nation,
  Passport,
  Matrimony,
  Trusteeship,
  Reside,
  Service,
  Disability,
  Disability2,
  Retire,
  Community,
  Subsistence,
  Type,
  Children,
  Religion
} from './component/component'
import { userCoountApi } from '@/api/bus/userCoount/index.ts'

import ContentWrap_border from '@/views/bus/owner/component/ContentWrap_border.vue'
import { onMounted } from 'vue'
const echastsData = ref([])
onMounted(async () => {
  userCoountApi
    .getCountUserExtends({
      villageId: ''
    })
    .then((res) => {
      console.log(res, '===========')
      echastsData.value = res
    })
})
</script>
<style lang="scss" scoped>
.page {
  display: grid;
  grid-template-columns: calc(33.33% - 16px) calc(33.33% - 16px) calc(33.33% - 16px);
  gap: 16px;
}
</style>
