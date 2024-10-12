<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="head-container">
    <!-- <el-row> -->
    <!-- <el-col :span="8">
        <el-select v-model="search" placeholder="Select" size="small">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-col> -->
    <!-- <el-col :span="14"> -->
    <el-input
      v-model="deptName"
      class="mb-20px"
      clearable
      placeholder="请输入部门名称"
      size="small"
    >
      <template #prepend>
        <el-select
          v-model="search"
          placeholder="Select"
          size="small"
          style="width: 85px; height: 100%"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </template>
      <template #append>
        <el-button type="primary" class="searchBut" @click="ChangeSearch" size="small"
          >搜索</el-button
        >
      </template>
      <!-- <template #prefix>
            <Icon icon="ep:search" />
          </template> -->
    </el-input>
    <!-- </el-col> -->
    <!-- </el-row> -->
  </div>
  <div class="head-container">
    <!-- -->
    <el-tree
      :data="deptList"
      ref="treeRef"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      :props="props"
      :default-expanded-keys="idArr"
      highlight-current
      node-key="treeId"
      lazy
      :load="loadNode"
      v-loading="treeLoading"
      @node-click="handleNodeClick"
    >
      <template #default="{ node, data }">
        <span class="slot-t-node flex justify-between items-center">
          <Icon
            class="m-r-6px"
            :icon="
              node.level == 1
                ? 'fa:bank'
                : node.level == 2
                  ? 'fa-solid:building'
                  : node.level == 3
                    ? 'fa:database'
                    : 'fa-solid:door-open'
            "
            size="12"
            color="#666666"
          />
          <!-- :class="{
              'el-icon-folder': !node.type,
              'el-icon-folder-opened': node.type,
              'el-icon-user-solid': data.type
            }" -->
          <span class="flex-1">{{ node.label }}</span>
          <span
            v-if="node.level == 4 && data.roomStatus <= 30 && data.roomStatus > 20"
            class="slot-t-node-right"
          >
            <Icon class="m-l-10px" icon="ep:avatar" :size="12" color="#52c41a" />
          </span>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<script lang="ts" setup>
import { ElTree } from 'element-plus'
import * as DeptApi from '@/api/system/dept'
import { defaultProps, handleTree } from '@/utils/tree'
import { BuildApi } from '@/api/bus/village'
import { F } from 'dist-prod/assets/el-virtual-list-CdvyVBKS'

defineOptions({ name: 'SystemUserDeptTree' })
const treeLoading = ref(false)
const deptName = ref('')
const deptList = ref<Tree[]>([]) // 树形结构
const treeRef = ref<InstanceType<typeof ElTree>>()
const search = ref(1)
// const options = ref('' as any[])
const idArr = ref<number[]>([])

import { useUserStore } from '@/store/modules/user'
import { number } from 'vue-types'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const options = [
  {
    label: '资源编号',
    value: 1
  },
  {
    label: '房号',
    value: 2
  },
  {
    label: '租客名',
    value: 3
  },
  {
    label: '手机号',
    value: 4
  }
]
const props = ref(defaultProps)
/** 获得部门树 */
const getTree = async () => {
  try {
    props.value = 'treeId'
    treeLoading.value = true
    const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
    treeLoading.value = false
    deptList.value = []
    idArr.value = []
    res.villageRespVOS.forEach((element) => {
      element.treeId = element.id
      idArr.value.push(element.treeId)
      // delete element.id
      if (element.buildList) {
        element.buildList.forEach((item) => {
          item.name = item.buildName
          item.treeId = 'building' + item.id
          // delete item.id
        })
        // element.children = handleTree(element.buildList)
      } else {
        element.buildList = []
      }
    })
    deptList.value.push(...handleTree(res.villageRespVOS))
    console.log(res.villageRespVOS[0])
    emits('node-click', res.villageRespVOS[0], 1)
    nextTick(() => {
      treeRef.value.setCurrentKey(res.villageRespVOS[0].treeId)
      console.log(defaultProps, 'defaultProps', idArr)
    })
  } finally {
    treeLoading.value = false
  }
}

const getList = async () => {}

const Refresh = async (
  type?: string,
  levelNum?: number,
  detailInfo?: any,
  villageId?: number,
  buildId?: number,
  layerId?: number
) => {
  console.log(levelNum, 'levelNum')
  if (type == 'create') {
    const node = {
      level: levelNum,
      data: detailInfo
    }
    if (levelNum == 1) {
      getTree()
    } else if (levelNum == 2) {
      let VillageIndex = deptList.value.findIndex((item) => item.id == villageId)
      let index = deptList.value[VillageIndex].buildList.findIndex((item) => item.id == buildId)
      let dataArray = await BuildApi.getLayerList({
        buildId: node.data.id,
        pageNo: 1,
        pageSize: 10
      })
      if (dataArray.length) {
        dataArray.forEach((element) => {
          element.name = element.layerName
          element.treeId = 'layer' + element.id
        })
        nextTick(() => {
          treeRef.value.updateKeyChildren(
            deptList.value[VillageIndex].buildList[index].treeId,
            dataArray
          )
        })
      }
    } else if (levelNum == 3) {
      let VillageIndex = deptList.value.findIndex((item) => item.id == villageId)
      let buildIndex = deptList.value[VillageIndex].buildList.findIndex(
        (item) => item.id == buildId
      )
      let index = deptList.value[VillageIndex].buildList[buildIndex].layerList.findIndex(
        (item) => item.id == layerId
      )
      let dataArray = await BuildApi.getRoomListByLayerId({
        layerId: node.data.id,
        pageNo: 1,
        pageSize: 10
      })
      if (dataArray.length) {
        dataArray.forEach((element) => {
          element.name = element.roomName
          element.treeId = 'room' + element.id
        })
        nextTick(() => {
          treeRef.value.updateKeyChildren(
            deptList.value[VillageIndex].buildList[buildIndex].layerList[index].treeId,
            dataArray
          )
        })
      }
    }
  } else if (type == 'delete') {
    if (levelNum == 1) {
      getTree()
    }
    if (levelNum == 2) {
      await getTree()
      let VillageIndex = deptList.value.findIndex((item) => item.id == villageId)
      emits('node-click', deptList.value[VillageIndex], levelNum - 1)
      nextTick(() => {
        treeRef.value.setCurrentKey(deptList.value[VillageIndex].treeId)
      })
    }
    if (levelNum == 3) {
      let VillageIndex = deptList.value.findIndex((item) => item.id == villageId)
      let BuildIndex = deptList.value[VillageIndex].buildList.findIndex(
        (item) => item.id == buildId
      )
      let dataArray = await BuildApi.getLayerList({
        buildId: buildId,
        pageNo: 1,
        pageSize: 10
      })
      if (dataArray.length) {
        dataArray.forEach((element) => {
          element.name = element.layerName
          element.treeId = 'layer' + element.id
        })
        emits('node-click', deptList.value[VillageIndex].buildList[BuildIndex], levelNum - 1)
        nextTick(() => {
          treeRef.value.updateKeyChildren(
            deptList.value[VillageIndex].buildList[BuildIndex].treeId,
            dataArray
          )
          treeRef.value.setCurrentKey(deptList.value[VillageIndex].buildList[BuildIndex].treeId)
        })
      }
    }
  }
}

const SetName = (
  levelNum?: number,
  id?: number,
  name?: string,
  villageId?: number,
  buildId?: number,
  layerId?: number
) => {
  console.log(levelNum, 'levelNum')
  if (levelNum == 1) {
    let index = deptList.value.findIndex((item) => item.treeId == id)
    deptList.value[index].name = name
  } else if (levelNum == 2) {
    let VillageIndex = deptList.value.findIndex((item) => item.id == villageId)
    let index = deptList.value[VillageIndex].buildList.findIndex((item) => item.treeId == id)
    deptList.value[VillageIndex].buildList[index].name = name
  } else if (levelNum == 3) {
    let VillageIndex = deptList.value.findIndex((item) => item.id == villageId)
    let BuildIndex = deptList.value[VillageIndex].buildList.findIndex((item) => item.id == buildId)
    console.log(id, 'id', deptList.value[VillageIndex].buildList[BuildIndex].layerList)
    let index = deptList.value[VillageIndex].buildList[BuildIndex].layerList.findIndex(
      (item) => item.treeId == id
    )
    deptList.value[VillageIndex].buildList[BuildIndex].layerList[index].name = name
  } else if (levelNum == 4) {
    let VillageIndex = deptList.value.findIndex((item) => item.id == villageId)
    let BuildIndex = deptList.value[VillageIndex].buildList.findIndex((item) => item.id == buildId)
    let LayerIndex = deptList.value[VillageIndex].buildList[BuildIndex].layerList.findIndex(
      (item) => item.id == layerId
    )
    let index = deptList.value[VillageIndex].buildList[BuildIndex].layerList[
      LayerIndex
    ].roomList.findIndex((item) => item.treeId == id)
    deptList.value[VillageIndex].buildList[BuildIndex].layerList[LayerIndex].roomList[index].name =
      name
  }
}

const loadNode = async (node: Node, resolve?: (data: Tree[]) => void) => {
  console.log(node.data, 'node.data')
  // resolve(deptList.value)
  if (node.level === 0) {
    return resolve(deptList.value)
  } else if (node.level > 0 && node.level < 2) {
    //默认展开的层级,需要默认几层就判断一下.
    return resolve(node.data.buildList) //核心是这里,每次展开的时候loadNode方法就会调用一次,只需要把node.data.[这里是默认的child字段]  加载到resolve方法里就可以了.就可以实现默认加载N级,之后再使用懒加载
  } else if (node.level >= 2 && node.level < 3) {
    let dataArray = await BuildApi.getLayerList({
      buildId: node.data.id,
      pageNo: 1,
      pageSize: 10
    })
    if (dataArray.length) {
      dataArray.forEach((element) => {
        element.name = element.layerName
        element.treeId = 'layer' + element.id
      })
      node.data.layerList = dataArray
    }
    return resolve(dataArray)
  } else if (node.level >= 3) {
    let dataArray = await BuildApi.getRoomListByLayerId({
      layerId: node.data.id,
      pageNo: 1,
      pageSize: 10
    })
    if (dataArray.length) {
      dataArray.forEach((element) => {
        element.name = element.roomName
        element.treeId = 'room' + element.id
        element.leaf = true
      })
      node.data.roomList = dataArray
    }
    return resolve(dataArray)
  }
}

/** 基于名字过滤 */
const filterNode = (name: string, data: Tree) => {
  if (!name) return true
  return data.name.includes(name)
}

const ChangeSearch = async () => {
  await getTree()
}

/** 处理部门被点击 */
const handleNodeClick = async (row: { [key: string]: any }, e) => {
  emits('node-click', row, e.level)
}
const emits = defineEmits(['node-click'])

/** 监听deptName */
watch(deptName, (val) => {
  treeRef.value!.filter(val)
})

defineExpose({ getTree, SetName, Refresh }) // 提供 getTree ,用于更新

/** 初始化 */
onMounted(async () => {
  await getTree()
})
</script>

<style lang="scss" scoped>
.searchBut {
  background: var(--el-color-primary) !important;
  color: var(--el-color-white) !important;
}
</style>
