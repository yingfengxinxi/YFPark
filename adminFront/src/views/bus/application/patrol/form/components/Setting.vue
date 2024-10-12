<template>
  <div class="w-100% pos-relative">
    <defaultRadiosetting
      :item="settingItem"
      v-if="settingItem?.value == 'defaultRadio'"
      ref="defaultRadiosetting_ref"
      @change="changeItem"
    />
    <numbersetting
      :item="settingItem"
      v-else-if="settingItem?.value == 'number'"
      ref="numbersetting_ref"
      @change="changeItem"
    />
    <timesetting
      :item="settingItem"
      v-else-if="settingItem?.value == 'time'"
      @change="changeItem"
      ref="timesetting_ref"
    />
    <datesetting
      :item="settingItem"
      v-else-if="settingItem?.value == 'date'"
      @change="changeItem"
      ref="datesetting_ref"
    />
    <checksetting
      :item="settingItem"
      v-else-if="settingItem?.value == 'check'"
      @change="changeItem"
      ref="checksetting_ref"
    />
    <Imagesetting
      :item="settingItem"
      v-else-if="settingItem?.value == 'image'"
      @change="changeItem"
      ref="imagesetting_ref"
    />
    <Inputsetting
      :item="settingItem"
      v-else-if="settingItem?.value == 'input'"
      @change="changeItem"
      ref="inputsetting_ref"
    />
    <radiosetting
      :item="settingItem"
      v-else-if="settingItem?.value == 'radio'"
      @change="changeItem"
      ref="radiosetting_ref"
    />
  </div>
</template>
<script lang="ts" setup>
//引用组件
import defaultRadiosetting from './defaultRadio/defaultRadiosetting.vue'
import numbersetting from './number/numbersetting.vue'
import timesetting from './time/timesetting.vue'
import datesetting from './date/datesetting.vue'
import checksetting from './check/checksetting.vue'
import Imagesetting from './image/imagesetting.vue'
import Inputsetting from './input/inputsetting.vue'
import radiosetting from './radio/radiosetting.vue'
import { defineExpose, ref } from 'vue'
//右侧设置项
const settingItem = ref<any>(null)
const defaultRadiosetting_ref = ref<any>(null)
const numbersetting_ref = ref<any>(null)
const timesetting_ref = ref<any>(null)
const datesetting_ref = ref<any>(null)
const checksetting_ref = ref<any>(null)
const imagesetting_ref = ref<any>(null)
const inputsetting_ref = ref<any>(null)
const radiosetting_ref = ref<any>(null)
function setItem(item: any) {
  settingItem.value = item
  setTimeout(() => {
    if (item.value == 'number') numbersetting_ref.value.setItem(item)
    if (item.value == 'defaultRadio') defaultRadiosetting_ref.value.setItem(item)
    if (item.value == 'time') timesetting_ref.value.setItem(item)
    if (item.value == 'date') datesetting_ref.value.setItem(item)
    if (item.value == 'check') checksetting_ref.value.setItem(item)
    if (item.value == 'image') imagesetting_ref.value.setItem(item)
    if (item.value == 'input') inputsetting_ref.value.setItem(item)
    if (item.value == 'radio') radiosetting_ref.value.setItem(item)
  }, 50)
}
const changeItem = (item) => {
  emit('changeItem', item)
}
defineExpose({ setItem })
const emit = defineEmits(['changeItem'])
</script>
