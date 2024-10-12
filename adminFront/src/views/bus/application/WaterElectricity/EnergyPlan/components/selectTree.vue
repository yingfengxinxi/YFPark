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
      node-key="id"
      lazy
      :load="loadNode"
      v-loading="treeLoading"
      show-checkbox
      class="stafftree"
      v-show="show_tree"
      default-expand-all
      @check="handleNodeClick"
      check-strictly
    >
      <template #default="{ node }">
        <div class="slot-t-node flex w-full">
          <div v-if="node.level == 3 && node.disabled && !node.checked" class="no_select"> </div>
          <div v-if="node.level == 3 && node.disabled && node.checked" class="no_select">
            <Icon icon="fa-solid:check" color="#999" size="12" />
          </div>
          <Icon
            class="m-r-[6px]"
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
    <div v-show="!show_tree">
      <el-table :data="selectList">
        <el-table-column label="楼层">
          <template #default="scope">
            {{ scope.row.villageName + '/' + scope.row.buildName + '/' + scope.row.layerName }}
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ElTree } from 'element-plus'
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
let show_tree = ref(true)
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
        element.children = undefined

        if (!element.haveRoom) {
          element.disabled = true
        }
      })
    }
    return resolve(dataArray)
  } else if (node.level >= 3) {
    return resolve([])
  }
}
//点击多选
const emit = defineEmits(['submitTree'])

const mapselectList = (arr) => {
  let buildId = ''
  arr.forEach((event, index) => {
    if (index == 0) {
      buildId = event.buildId
    } else {
      if (buildId != event.buildId) {
        message.error('不能跨楼宇选择')
        selectList.value = []
        treeRef.value!.setCheckedKeys([], false)
        return
      }
    }
  })
}
const handleNodeClick = async (res, data) => {
  await mapselectList(data.checkedNodes)
  selectList.value = data.checkedNodes
  emit('submitTree', data.checkedNodes)
}
defineExpose({ select }) // 提供 open 方法，用于打开弹窗
const props = withDefaults(
  defineProps<{
    changeTree: boolean
  }>(),
  {
    changeTree: true
  }
)
watch(
  () => props.changeTree,
  async (newVal) => {
    show_tree.value = newVal
  }
)
/** 初始化 */
onMounted(async () => {
  await getTree()
})
function select(data) {
  if (treeRef.value) {
    treeRef.value!.setCheckedKeys(data, true)
    setTimeout(async () => {
      await data.forEach((event) => {
        const node = treeRef.value!.getNode(event)
        selectList.value.push(node.data)
      })
      emit('submitTree', selectList.value)
    }, 1000)
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
