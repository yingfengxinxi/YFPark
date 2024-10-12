<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<script lang="tsx">
import { defineComponent, PropType, ref } from 'vue'
import { isHexColor } from '@/utils/color'
import { ElTag } from 'element-plus'
import { DictDataType, getDictOptions } from '@/utils/dict'

export default defineComponent({
  name: 'DictText',
  props: {
    type: {
      type: String as PropType<string>,
      required: true
    },
    value: {
      type: [String, Number, Boolean] as PropType<string | number | boolean>,
      required: true
    }
  },
  setup(props) {
    const dictData = ref<DictDataType>()
    const getDictObj = (dictType: string, value: string) => {
      const dictOptions = getDictOptions(dictType)
      dictOptions.forEach((dict: DictDataType) => {
        if (dict.value === value) {
          if (dict.colorType + '' === 'primary' || dict.colorType + '' === 'default') {
            dict.colorType = ''
          }
          dictData.value = dict
        }
      })
    }
    const rederDictText = () => {
      if (!props.type) {
        return null
      }
      // 解决自定义字典标签值为零时标签不渲染的问题
      if (props.value === undefined || props.value === null) {
        return null
      }
      getDictObj(props.type, props.value.toString())
      // 添加标签的文字颜色为白色，解决自定义背景颜色时标签文字看不清的问题
      return <el-text>{dictData.value?.label}</el-text>
    }
    return () => rederDictText()
  }
})
</script>
