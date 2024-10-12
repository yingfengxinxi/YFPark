<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->

<script lang="ts" setup>
import { PropType } from 'vue'
import { propTypes } from '@/utils/propTypes'
import { useDesign } from '@/hooks/web/useDesign'
import { useAppStore } from '@/store/modules/app'

defineOptions({ name: 'ColorRadioPicker' })

const { getPrefixCls } = useDesign()

const prefixCls = getPrefixCls('color-radio-picker')

const props = defineProps({
  schema: {
    type: Array as PropType<string[]>,
    default: () => []
  },
  modelValue: propTypes.string.def('')
})

const emit = defineEmits(['update:modelValue', 'change'])

const colorVal = ref(props.modelValue)
const appStore = useAppStore()
const layout = computed(() => appStore.getLayout)

watch(
  () => props.modelValue,
  (val: string) => {
    if (val === unref(colorVal)) return
    colorVal.value = val
  }
)

// 监听
watch(
  () => colorVal.value,
  (val: string) => {
    emit('update:modelValue', val)
    emit('change', val)
  }
)
</script>

<template>
  <div :class="prefixCls" class="flex flex-wrap flex-justify-between">
    <template v-for="(item, i) in schema" :key="`radio-${i}`">
      <template v-if="item.length > 13 && layout != 'topLeftBottom'"> </template>
      <span
        :class="{ 'is-active': colorVal === item }"
        v-else
        :style="{
          background: item.length > 13 ? `linear-gradient(to right,  ${item})` : `${item}`
        }"
        class="mb-5px h-20px w-20px cursor-pointer border-2px border-gray-300 rounded-2px border-solid text-center leading-20px"
        @click="colorVal = item"
      >
        <Icon v-if="colorVal === item" :size="16" color="#fff" icon="ep:check" />
      </span>
    </template>
  </div>
</template>

<style lang="scss" scoped>
$prefix-cls: #{$namespace}-color-radio-picker;

.#{$prefix-cls} {
  .is-active {
    border-color: var(--el-color-primary);
  }
}
</style>
