<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="head-container">
    <el-tree
      :data="deptList"
      ref="treeRef"
      :expand-on-click-node="false"
      :props="defaultProps"
      highlight-current
      node-key="id"
      lazy
      :load="loadNode"
      v-loading="treeLoading"
      show-checkbox
      class="stafftree"
      @check="handleCheckChange"
      :default-expand-all="true"
    >
      <template #default="{ node }">
        <div class="slot-t-node flex w-full">
          <Icon
            class="m-r-6px transform-translate-y-5px"
            :icon="
              node.level == 1
                ? 'fa:bank'
                : node.level == 2
                  ? 'fa-solid:building'
                  : node.level == 3
                    ? 'fa:database'
                    : 'fa-solid:door-open'
            "
            :size="12"
            color="#666666"
          />
          <div class="flex justify-between items-center w-full">
            <span>{{ node.label }}</span>
          </div>
        </div>
      </template>
    </el-tree>
  </div>
</template>

<script lang="ts" setup>
import { defaultProps } from '@/utils/tree'
import { BuildApi } from '@/api/bus/village'
defineOptions({ name: 'SystemUserDeptTree' })
const treeLoading = ref(false)
const deptList = ref<Tree[]>([]) // 树形结构
const treeRef = ref<InstanceType<typeof ElTree>>()
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const selectList = ref<string[]>([])
const buildList = ref<string[]>([])
const message = useMessage() // 消息弹窗
const getTree = async () => {
  treeLoading.value = true
  const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
  treeLoading.value = false
  res.villageRespVOS.forEach((res, index) => {
    deptList.value.push({
      id: res.id,
      disabled: true,
      name: res.name,
      buildList: [],
      level: 0
    })
    buildList.value.push(res.id)
    if (res.buildList.length == 0) {
      return
    }
    res.buildList.forEach((even, evenIndex) => {
      deptList.value[index].buildList.push({
        id: even.id,
        name: even.buildName,
        orgId: even.orgId,
        buildNumber: even.buildNumber,
        level: 1
      })
    })
  })
}

const loadNode = async (node: Node, resolve: (data: Tree[]) => void) => {
  if (node.level === 0) {
    return resolve(deptList.value)
  } else if (node.level > 0 && node.level < 2) {
    return resolve(node.data.buildList)
  } else if (node.level >= 2 && node.level < 3) {
    let dataArray = await BuildApi.getLayerList({
      buildId: node.data.id,
      pageNo: 1,
      pageSize: 100
    })
    if (dataArray.length) {
      dataArray.forEach((element) => {
        element.name = element.layerName
        element.level = 2
      })
    }
    return resolve(dataArray)
  } else if (node.level == 3) {
    let dataArray = await BuildApi.getRoomListByLayerId({
      layerId: node.data.id,
      pageNo: 1,
      pageSize: 100
    })
    if (dataArray.length) {
      dataArray.forEach((element) => {
        element.name = element.roomName
        element.level = 3
      })
    }
    return resolve(dataArray)
  } else if (node.level >= 4) {
    return resolve([])
  }
}

/** 初始化 */
onMounted(async () => {
  await getTree()
})
const buildId = ref(-1)
const handleCheckChange = async (data: any, checked: any) => {
  // 选择同一楼宇下的房间
  if (checked.checkedNodes.length > 0) {
    checked.checkedNodes.forEach((item) => {
      if (buildId.value == -1) {
        buildId.value = item.buildId
        return
      }
      if (buildId.value != item.buildId) {
        message.error('只能选择同一楼宇下的房间')
        treeRef.value.setCheckedNodes([])
        return
      }
    })
  } else {
    buildId.value = -1
  }
  //筛选出房间
  let list = []
  await checked.checkedNodes.forEach((item) => {
    if (item.level == 3) {
      list.push({
        id: item.id,
        name: item.name,
        buildId: item.buildId,
        villageId: item.villageId
      })
    }
  })
  emit('submit', list)
}
const emit = defineEmits(['submit'])
</script>
<style scoped>
::v-deep .stafftree .el-checkbox__input.is-disabled {
  display: none;
}
.no_select {
  width: 13px;
  height: 13px;
  border: 1px solid #dcdfe6;
  background: #f5f7fa;
  border-radius: 2px;
  transform: translateX(-8px);
}
</style>
