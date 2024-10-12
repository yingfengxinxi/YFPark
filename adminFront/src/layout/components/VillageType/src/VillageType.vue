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
/** 获得部门树 */

const getTree = async (type) => {
  try {
    treeLoading.value = true
    contentList.value = []
    buildList.value = []
    villagelist.value = []
    buildIds.value = []
    villageIds.value = []
    contentIds.value = []
    chooseBuilds.value = []
    const res = await BuildApi.getVillagePageExDate({ type: type })
    treeLoading.value = false
    contentList.value = res.villageCollectionRespVOS
    buildList.value = res.villageBuildList
    villagelist.value = res.villageList
    contentList.value.forEach((element) => {
      element.collectionBuild = JSON.parse(element.collectionBuild)
    })
    if (deptIdList.value[0]) {
      let content = JSON.parse(deptIdList.value[0].content)
      content.forEach((element) => {
        villagelist.value.forEach((item) => {
          if (item.id == element.id) {
            villageIds.value.push(element.id)
          }
        })
        element.build.forEach((item) => {
          let dataList = buildList
          dataList.value.forEach((items) => {
            if (items.id == item) {
              buildIds.value.push(items.id)
              chooseBuilds.value.push(items)
            }
          })
        })
        element.checkedBuild.forEach((item) => {
          contentIds.value.push(item)
        })
      })
      contentIds.value = Array.from(new Set(contentIds.value))
      getCheckContent()
      getCheckVillage()
      await removal()
    }
  } finally {
    treeLoading.value = false
  }
}

const getCheckVillage = async () => {
  villagelist.value.forEach((element) => {
    let index = villageIds.value.findIndex((item) => item == element.id)
    if (index >= 0) {
      element.checked = true
    } else {
      element.checked = false
    }
  })
}

const getCheckContent = async () => {
  contentList.value.forEach((element) => {
    let index = contentIds.value.findIndex((item) => item == element.id)
    if (index >= 0) {
      element.checked = true
    } else {
      element.checked = false
    }
  })
}
const treeLoading = ref(false)
if (userStore.getvillageType) {
  villageTypeValue1.value = unref(villageTypeValue)
  deptIdList.value = userStore.getDeptIdList

  getTree(villageTypeValue1.value)

  // buildIds.value
}
// watch(villageTypeValue, (newValue, OldValue, onCleanup) => {
//   console.log('数据变化了', `新值是：${newValue}`, `旧值是${OldValue}`)
//   onCleanup(() => {
//     console.log('清除副作用')
//   })
// })

const openVillageType = (type?: Bollean) => {
  drawer.value = type
  if (userStore.getvillageType) {
    villageTypeValue1.value = unref(villageTypeValue)
    deptIdList.value = userStore.getDeptIdList

    getTree(villageTypeValue1.value)

    // buildIds.value
  }
}

const getVillageType = async () => {
  const res = await VillageTypeApi.getVillageTypeNopage()
  villageTypeList.value = res
  // if (!villageTypeValue.value) {
  //   userStore.setVillageType(villageTypeList.value[0].alias)
  // }
}

const emit = defineEmits(['setDrawer'])
defineExpose({ openVillageType }) // 提供 open 方法，用于打开弹窗
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

const changeSelect = async (val) => {
  // userStore.setVillageType(val)
  await getTree(villageTypeValue1.value)
}
const changeBuild = (value, e) => {
  console.log(value, 'value', e)
}
// 更改集合
const changeContent = async (item, index) => {
  if (item.checked) {
    item.checked = false
    contentIds.value.splice(contentIds.value.indexOf(item.id), 1)
    item.collectionBuild.forEach((element) => {
      buildList.value.forEach((buildItem) => {
        if (element == buildItem.id) {
          buildIds.value.splice(buildIds.value.indexOf(element), 1)
          chooseBuilds.value.splice(chooseBuilds.value.indexOf(buildItem), 1)
          villageIds.value.splice(villageIds.value.indexOf(buildItem.villageId), 1)
        }
      })
    })
  } else {
    item.checked = true
    contentIds.value.push(item.id)
    item.collectionBuild.forEach((element) => {
      buildList.value.forEach((buildItem) => {
        if (element == buildItem.id) {
          buildIds.value.push(element)
          chooseBuilds.value.push(buildItem)
          villageIds.value.push(buildItem.villageId)
        }
      })
    })
  }
  await getCheckVillage()
  await removal()
}
// 更改项目
const changeVillage = async (item, index) => {
  if (item.checked) {
    item.checked = false
    villageIds.value.splice(villageIds.value.indexOf(item.id), 1)
    item.buildList.forEach((element) => {
      buildIds.value.splice(buildIds.value.indexOf(element.id), 1)
      chooseBuilds.value.splice(chooseBuilds.value.indexOf(element), 1)
      contentList.value.forEach((contentItem) => {
        contentItem.collectionBuild.forEach((contentBuildItem) => {
          if (element.id == contentBuildItem) {
            contentIds.value.splice(contentIds.value.indexOf(contentItem.id), 1)
          }
        })
      })
    })
  } else {
    item.checked = true
    villageIds.value.push(item.id)
    item.buildList.forEach((element) => {
      buildIds.value.push(element.id)
      chooseBuilds.value.push(element)
      contentList.value.forEach((contentItem) => {
        contentItem.collectionBuild.forEach((contentBuildItem) => {
          if (element.id == contentBuildItem) {
            contentIds.value.push(contentItem.id)
          }
        })
      })
    })
  }
  await getCheckContent()
  await removal()
}

const removal = async () => {
  buildIds.value = Array.from(new Set(buildIds.value))
  villageIds.value = Array.from(new Set(villageIds.value))
  contentIds.value = Array.from(new Set(contentIds.value))
  chooseBuilds.value = Array.from(new Set(chooseBuilds.value))
}

const GetItem = async (item, indexs) => {
  let index = buildIds.value.indexOf(item.id)
  if (index < 0) {
    villageIds.value.push(item.villageId)
    // contentIds.value.push()
    contentList.value.some((element) => {
      if (element.collectionBuild.length) {
        element.collectionBuild.forEach((items) => {
          if (items == item.id) {
            contentIds.value.push(element.id)
            return true
          }
        })
      }
    })
    contentIds.value = Array.from(new Set(contentIds.value))
    chooseBuilds.value.push(item)
  } else {
    const indexVillage = villageIds.value.indexOf(item.villageId)
    const idContent = ref(0)
    contentList.value.some((element) => {
      if (element.collectionBuild.length) {
        element.collectionBuild.forEach((items) => {
          if (items == item.id) {
            idContent.value = element.id
            return true
          }
        })
      }
    })

    const indexContent = contentIds.value.indexOf(idContent.value)
    villageIds.value.splice(indexVillage, 1)
    contentIds.value.splice(indexContent.value, 1)
    chooseBuilds.value.splice(indexs)
  }
}

onMounted(() => {
  getVillageType()
})
</script>

<template>
  <div class="VillageTypeBox">
    <ElDrawer
      class="!bg-#f4f4f4"
      v-model="drawer"
      :z-index="200"
      direction="ltr"
      size="600px"
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
