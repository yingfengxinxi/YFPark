<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<script lang="ts" setup>
import { VillageTypeApi } from '@/api/bus/tag/villageTypeList/index'
import { BuildApi } from '@/api/bus/village'
import { UpdateSpercialSetting } from '@/api/login'
const message = useMessage()
import { CACHE_KEY, useCache, deleteUserCache } from '@/hooks/web/useCache'
const { wsCache } = useCache()

defineOptions({ name: 'VillageTyep' })

import { useUserStore } from '@/store/modules/user'
import { get } from 'http'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)

const villageTypeValue1 = ref()
const villageTypeList = ref([])
const drawer = ref(false)
const user = userStore.getUser
const villagelist = ref([])
const buildList = ref([])
const contentList = ref([])

const buildIds = ref([])
const villageIds = ref([])
const contentIds = ref([])
const chooseBuilds = ref([])
const deptIdList = ref([] as any[])

const emit = defineEmits(['setDrawer'])
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const handleClose = async () => {
  const data = ref([])
  console.log(villageIds, 'villageIds')
  villageIds.value.forEach((element) => {
    let list = ref({
      id: element,
      type: villageTypeValue1.value,
      build: [],
      checkedBuild: []
    })
    chooseBuilds.value.forEach((item) => {
      if (item.villageId == element) {
        list.value.build.push(item.id)
      }
    })
    console.log(chooseBuilds.value, 'chooseBuilds.value')
    data.value.push(list.value)
  })

  contentList.value.forEach((element) => {
    if (element.collectionBuild.length) {
      element.collectionBuild.forEach((items) => {
        chooseBuilds.value.forEach((e) => {
          if (e.id == items) {
            let index = data.value.findIndex((item) => item.id == e.villageId)
            data.value[index].checkedBuild.push(element.id)
          }
        })
      })
    }
  })
  console.log(buildIds.value, '1111')
  if (!buildIds.value.length) {
    return message.error('请先选择楼宇')
  }
  let list = await UpdateSpercialSetting({
    id: deptIdList.value.length ? deptIdList.value[0].id : '',
    uid: user.id,
    type: 'deptIdList',
    content: JSON.stringify(data.value)
  })
  deptIdList.value[0] = list
  userStore.setDeptIdList(deptIdList.value)
  emit('setDrawer', !drawer.value)
}

onMounted(() => {})
</script>

<template>
  <div class="VillageTypeBox">
    <ElDrawer
      class="!bg-#f4f4f4"
      v-model="drawer"
      :z-index="200"
      direction="ltr"
      size="700px"
      :show-close="false"
      custom-class="adrawer"
      :before-close="handleClose"
    >
      <template #header>
        <ContentWrap class="!m-0 !p-0">
          <div class="flex">
            <div class="flex-1 flex items-center">
              <div
                class="iconBox w-30px h-30px line-height-30px bg-#2681f3 text-center m-r-6px b-rd-4px"
              >
                <Icon class="" icon="fa:bank" size="14" color="#fff" />
              </div>
              <div class="flex1 m-l-10px">
                <div class="font-size-16px fw-bold c-#000000a6">{{ user.nickname }}</div>
                <div class="font-size-12px c-#999 m-t-5px"
                  >共计 <Icon class="m-r-6px" icon="fa-solid:building" size="14" color="#999" />{{
                    buildList.length
                  }}</div
                >
              </div>
            </div>
            <el-select
              v-model="villageTypeValue1"
              placeholder="Select"
              style="width: 200px"
              popper-class="select_popper"
              @change="changeSelect($event)"
            >
              <el-option
                v-for="item in villageTypeList"
                :key="item.alias"
                :label="item.name"
                :value="item.alias"
              />
            </el-select>
          </div>
        </ContentWrap>
      </template>
      <el-row :gutter="10" class="m-t-10px w-100%">
        <el-col :span="12" class="">
          <div class="h-[calc(100vh-90px)]">
            <ContentWrap class="!m-0 !p-0 h-[calc((100vh-100px)/2)]">
              <div class="title">园区</div>
              <div class="h-[calc((100vh-200px)/2)]">
                <el-scrollbar class="list" v-loading="treeLoading">
                  <div
                    class="item p-10px p-t-12px p-b-12px flex items-center"
                    v-for="(item, index) in villagelist"
                    :key="index"
                  >
                    <div
                      v-if="item.checked"
                      class="iconBox w-30px h-30px line-height-30px bg-#2681f3 text-center m-r-6px b-rd-4px cursor-pointer"
                      @click="changeVillage(item, index)"
                    >
                      <Icon class="" icon="ep:select" size="14" color="#fff" />
                    </div>
                    <div
                      v-else
                      class="iconBox w-30px h-30px line-height-30px bg-#13c2c2 text-center m-r-6px b-rd-4px cursor-pointer"
                      @click="changeVillage(item, index)"
                    >
                      <Icon class="" icon="fa:bank" size="14" color="#fff" />
                    </div>
                    <div class="flex1 m-l-10px">
                      <div class="font-size-14px c-#000">{{ item.name }}</div>
                      <div class="font-size-12px c-#999 m-t-5px"
                        ><Icon class="m-r-6px" icon="fa-solid:building" size="14" color="#999" />
                        <span v-if="item.buildList">{{ item.buildList.length || 0 }}</span>
                        <span v-else>0</span>
                      </div>
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </ContentWrap>

            <ContentWrap class="!m-0 !p-0 h-[calc((100vh-100px)/2)] !m-t-10px">
              <div class="title">建筑集合</div>

              <div class="h-[calc((100vh-200px)/2)]">
                <el-scrollbar class="list" v-loading="treeLoading">
                  <div
                    class="item p-10px p-t-12px p-b-12px flex items-center"
                    v-for="(item, index) in contentList"
                    :key="index"
                  >
                    <div
                      v-if="item.checked"
                      class="iconBox w-30px h-30px line-height-30px bg-#2681f3 text-center m-r-6px b-rd-4px cursor-pointer"
                      @click="changeContent(item, index)"
                    >
                      <Icon class="" icon="ep:select" size="14" color="#fff" />
                    </div>
                    <div
                      v-else
                      class="iconBox w-30px h-30px line-height-30px bg-#13c2c2 text-center m-r-6px b-rd-4px cursor-pointer"
                      @click="changeContent(item, index)"
                    >
                      <Icon class="" icon="ep:office-building" size="14" color="#fff" />
                    </div>
                    <div class="flex1 m-l-10px">
                      <div class="font-size-14px c-#000">{{ item.collectionName }}</div>
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </ContentWrap>
          </div>
        </el-col>
        <el-col :span="12" class="">
          <ContentWrap class="!m-0 !p-0 h-[calc(100vh-90px)]">
            <div class="title">建筑</div>
            <div class="h-[calc(100vh-150px)]">
              <el-scrollbar class="list" v-loading="treeLoading">
                <el-checkbox-group v-model="buildIds" @change="changeBuild">
                  <el-checkbox
                    v-for="(item, index) in buildList"
                    :key="index"
                    :value="item.id"
                    class="w-100% p-10px p-t-12px p-b-12px m-r-0px"
                    @click="GetItem(item, index)"
                  >
                    <template #default>
                      <div class="item flex items-center">
                        <div
                          class="iconBox w-30px h-30px line-height-30px bg-#f4b91e text-center m-r-6px b-rd-4px"
                        >
                          <Icon class="" icon="fa-solid:building" size="14" color="#fff" />
                        </div>
                        <div class="flex1 m-l-10px">
                          <div class="font-size-14px c-#000">{{ item.buildName }}</div>
                        </div>
                      </div>
                    </template>
                  </el-checkbox>
                </el-checkbox-group>
              </el-scrollbar>
            </div>
          </ContentWrap>
        </el-col>
      </el-row>
    </ElDrawer>
  </div>
</template>
<style scoped lang="scss">
.select_popper {
  z-index: 99999 !important;
}
.VillageTypeBox :deep(.el-drawer__header) {
  background-color: rgb(255, 255, 255);
  padding: 0;
  margin: 0;
  height: 70px;
}
.VillageTypeBox :deep(.el-drawer__body) {
  padding: 0;
}

.VillageTypeBox :deep(.el-drawer__header) .el-card {
  border-radius: 0 !important;
}

.VillageTypeBox :deep(.el-card__body) {
  padding: 10px !important;
}

.VillageTypeBox :deep(.el-drawer__title) {
  font-size: 20px;
}

.VillageTypeBox .title {
  border-bottom: 1px solid #f1f1f1;
  height: 40px;
  line-height: 40px;
  font-weight: bold;
  padding: 0 10px;
  font-size: 14px;
  color: #000000a6;
}
</style>
