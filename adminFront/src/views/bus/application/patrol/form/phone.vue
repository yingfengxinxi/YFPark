<template>
  <div
    class="w-275px h-467px bg-white rounded-2 shadow-md p-15px box-border pt-30px no-select pos-relative"
  >
    <div
      class="flex items-center justify-center pos-absolute pos-top-10px gap-8px pos-left-50% transform-translate-x--50%"
    >
      <div class="bg-black w-30px h-10px rounded-2"> </div>
      <div class="bg-black w-10px h-10px rounded-2"> </div>
    </div>
    <div
      class="bg-#F7F7F7 w-100% h-100% rounded-2 border-#DDDDDD border-1 border-solid overflow-y-scroll overflow-x-hidden scroll"
    >
      <draggable
        :animation="200"
        v-model="list"
        item-key="value"
        :force-fallback="true"
        filter=".result"
        @end="onEnd"
      >
        <template #item="{ element, index }">
          <div :class="element.value == 'defaultRadio' ? '' : ''">
            <ListItem
              :element="element"
              :index="index"
              @select="selectPhone"
              @delItem="delItem"
              :nowIndex="nowIndex"
            />
          </div>
        </template>
      </draggable>
    </div>
  </div>
</template>
<script lang="ts" setup>
import draggable from 'vuedraggable'
import ListItem from './components/ListItem.vue'
const list = ref<any>([])
function pushList(res: any) {
  list.value.push(res)
}
function changeList(res: any) {
  list.value[nowIndex.value] = res
}
defineExpose({ pushList, changeList, list })
const emit = defineEmits(['select'])
const nowIndex = ref<number>(-1)
const selectPhone = (res: any, index: number) => {
  if (nowIndex.value == index) {
    nowIndex.value = -1
    emit('select', {})
    return
  }
  nowIndex.value = index
  emit('select', res)
}
const delItem = (index: number) => {
  list.value.splice(index, 1)
  emit('select', {})
  nowIndex.value = -1
}
const onEnd = (res: any, index: number) => {
  nowIndex.value = -1
  emit('select', {})
}
</script>
<style lang="scss" scoped>
.no-select {
  user-select: none; /* 禁止选中文本 */
  -webkit-user-select: none; /* Safari 和 Chrome */
  -moz-user-select: none; /* Firefox */
  -ms-user-select: none; /* Internet Explorer 和 Edge */
}
.scroll::-webkit-scrollbar {
  display: none; /* Chrome Safari */
}

.scroll {
  scrollbar-width: none; /* firefox */
  -ms-overflow-style: none; /* IE 10+ */
  overflow-x: hidden;
  overflow-y: auto;
}
</style>
