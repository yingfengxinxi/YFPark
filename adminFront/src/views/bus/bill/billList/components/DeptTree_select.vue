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
      :default-expanded-keys="buildList"
      highlight-current
      node-key="level_key"
      lazy
      :load="loadNode"
      v-loading="treeLoading"
      show-checkbox
      class="stafftree"
      @check="handleCheckChange"
      v-if="show_tree"
    >
      <template #default="{ node, data }">
        <div class="slot-t-node flex w-full">
          <!-- 123123 -->
          <div v-if="node.level == 4 && node.disabled && !node.checked" class="no_select"> </div>
          <div v-if="node.level == 4 && node.disabled && node.checked" class="no_select">
            <Icon icon="fa-solid:check" color="#999" size="12" />
          </div>
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
            :size="12"
            color="#666666"
          />

          <div class="flex justify-between items-center w-full">
            <span>{{ node.label }}</span>
            <span v-if="data.rentalArea">{{ data?.rentalArea + '㎡' }}</span>
          </div>
        </div>
      </template>
    </el-tree>
    <!-- <el-table :data="selectList" v-if="!show_tree">
      <el-table-column prop="roomName" label="房源名称" />
      <el-table-column label="详细地址">
        <template #default="{ row }">
          <span>{{ row.villageName }}/{{ row.buildName }}/{{ row.roomName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="rentalArea" label="可租面积" />
    </el-table> -->
  </div>
</template>

<script lang="ts" setup>
import { ElTree } from 'element-plus'
import { defaultProps } from '@/utils/tree'
import { BuildApi } from '@/api/bus/village'
import { nextTick } from 'vue'
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

const show = (node) => {
  console.log(node.checked, '=========')
}

let show_tree = ref(false)
/** 获得部门树 */
const getTree = async () => {
  treeLoading.value = true
  //获取第一层数据
  const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
  treeLoading.value = false
  res.villageRespVOS.forEach((res, index) => {
    deptList.value.push({
      id: res.id,
      disabled: true,
      name: res.name,
      buildList: [],
      level: 0,
      level_key: `0-${res.id}`
    })
    buildList.value.push(`0-${res.id}`)
    //获取第二层数据
    if (res.buildList.length == 0) {
      return
    }
    res.buildList.forEach((even, evenIndex) => {
      deptList.value[index].buildList.push({
        id: even.id,
        disabled: true,
        name: even.buildName,
        orgId: even.orgId,
        buildNumber: even.buildNumber,
        level: 1,
        level_key: `1-${res.id}`
      })
    })
  })
}

const loadNode = async (node: Node, resolve: (data: Tree[]) => void) => {
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
        element.level = 2
        element.level_key = `2-${element.id}`
        element.level1_key = node.data.level_key
        element.disabled = true
      })
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
        element.level = 3
        element.level_key = `3-${element.id}`
        element.level1_key = node.data.level1_key
        element.level2_key = node.data.level_key
        element.disabled = true
        //面接为0,禁止选中
        // if (selectList.value.length > 0) {
        //   selectList.value.forEach((item) => {
        //     if (item.id == element.id) {
        //       element.disabled = true
        //     } else if (element.roomStatus == 10) {
        //       element.disabled = true
        //     } else {
        //       element.disabled = true
        //     }
        //   })
        // } else {
        //   if (element.roomStatus == 10) {
        //     element.disabled = true
        //   } else {
        //     element.disabled = true
        //   }
        // }
      })
    }
    return resolve(dataArray)
  }
}
//点击多选
const emit = defineEmits(['changeData'])
const mapselectList = (arr) => {
  let villageId = ''
  arr.forEach((event, index) => {
    if (index == 0) {
      villageId = event.villageId
    } else {
      if (villageId != event.villageId) {
        message.error('只能选择同一个园区的房间')
        selectList.value = []
        treeRef.value!.setCheckedKeys([], false)
        return
      }
    }
  })
}
function containsObject(arr, obj) {
  return arr.some((item) => item.id === obj.id && item.name === obj.name)
}
const handleCheckChange = async (res, data) => {
  selectList.value = []
  if (data.checkedNodes == '') {
    selectList.value = []
    emit('changeData', selectList.value)
    return
  }
  //总面积
  let rentalAreaTotal = 0
  // 获取楼层下的房间
  await data.checkedNodes.forEach(async (event) => {
    if (event.level == 2) {
      BuildApi.getRoomListByLayerId({
        layerId: event.id,
        pageNo: 1,
        pageSize: 10
      }).then(async (level_event) => {
        if (level_event.length == 0) {
          message.error('该楼层下没有房间')
          selectList.value = []
          treeRef.value!.setCheckedKeys([], false)
          return
        }
        await level_event.forEach((level_event) => {
          if (level_event.roomStatus == 10 && !containsObject(selectList.value, level_event)) {
            level_event.level1_key = event.level1_key
            level_event.level2_key = event.level_key
            level_event.level_key = `3-${level_event.id}`
            selectList.value.push(level_event)
            mapselectList(selectList.value)
          }
        })
      })
    } else if (event.level == 3) {
      if (!containsObject(selectList.value, event)) {
        selectList.value.push(event)
        mapselectList(selectList.value)
      }
    }
  })
  //只能选中同一个园区的房间
  if (selectList.value.length > 0) {
    selectList.value.map((event) => {
      rentalAreaTotal += event.rentalArea
    })
  }
  setTimeout(() => {
    emit('changeData', selectList.value, rentalAreaTotal)
  }, 100)
}
//回显
const getDeptTreeSelect = async (data, change) => {
  selectList.value = data
  let selectIdList = []
  let showKeyList = []
  await data.forEach((event) => {
    selectIdList.push(event.level_key)
    if (!showKeyList.includes(event.level1_key)) {
      showKeyList.push(event.level1_key)
    }
    if (!showKeyList.includes(event.level2_key)) {
      showKeyList.push(event.level2_key)
    }
  })
  setTree(selectIdList, showKeyList)
}
defineExpose({ getDeptTreeSelect, getTree })
const props = withDefaults(
  defineProps<{
    change_tree: boolean
  }>(),
  {
    change_tree: false
  }
)
watch(
  () => props.change_tree,
  async (newVal) => {
    setTimeout(() => {
      show_tree.value = newVal
    }, 50)
    if (newVal) {
      getDeptTreeSelect(selectList.value, true)
    }
  }
)
/** 初始化 */
onMounted(async () => {
  await getTree()
})
const setTree = async (selectIdList, showKeyList) => {
  try {
    await showKeyList.forEach((event) => {
      buildList.value.push(event)
    })

    if (selectIdList.length > 0) {
      await nextTick()
      setTimeout(() => {
        treeRef.value!.setCheckedKeys(selectIdList, false)
      }, 100)
    }
  } catch (error) {
    console.log('error')
  }
}
</script>

<style lang="scss" scoped>
.searchBut {
  background: var(--el-color-primary) !important;
  color: var(--el-color-white) !important;
}
</style>
<style scoped>
::v-deep .stafftree .el-checkbox__input.is-disabled {
  display: none;
}
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
