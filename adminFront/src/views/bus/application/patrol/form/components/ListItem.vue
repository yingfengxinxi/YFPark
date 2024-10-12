<template>
  <div class="">
    <div
      class="w-100% pos-relative z-36"
      @click="clickItem(element)"
      :class="index == nowIndex ? 'active' : ''"
    >
      <span class="pos-absolute pos-top-0 pos-right-0 z-999">
        <Icon
          icon="ep:close"
          @click="delItem()"
          v-if="index == nowIndex && element.value != 'defaultRadio'"
        />
      </span>
      <defaultRadio v-if="element.value == 'defaultRadio'" :data="element" />
      <Number v-else-if="element.value == 'number'" :data="element" />
      <Time v-else-if="element.value == 'time'" :data="element" />
      <date v-else-if="element.value == 'date'" :data="element" />
      <check v-else-if="element.value == 'check'" :data="element" />
      <Image v-else-if="element.value == 'image'" :data="element" />
      <Input v-else-if="element.value == 'input'" :data="element" />
      <Radio v-else-if="element.value == 'radio'" :data="element" />
    </div>
  </div>
</template>
<script lang="ts" setup>
import { defineEmits } from 'vue'
//组件
import defaultRadio from './defaultRadio/defaultRadio.vue'
import Number from './number/number.vue'
import Time from './time/time.vue'
import date from './date/date.vue'
import check from './check/check.vue'
import Image from './image/image.vue'
import Input from './input/input.vue'
import Radio from './radio/radio.vue'
const props = defineProps<{
  element: any
  index: number
  nowIndex: number
}>()
const emit = defineEmits(['select'])

const clickItem = (item: any) => {
  emit('select', item, props.index)
}
const delItem = () => {
  emit('delItem', props.index)
}
</script>
<style lang="scss" scoped>
.active {
  border: 2px solid #409eff;
  border-radius: 4px;
  box-sizing: border-box;
}
</style>
