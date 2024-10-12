<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div class="home-container">
    <el-row :gutter="15" justify="space-between">
      <el-col :span="15">
        <!-- 数字概览 -->
        <DigitalOverview :diaitalOverview="diaitalOverview" />
      </el-col>
      <el-col :span="9">
        <el-space direction="vertical" :fill="true" :size="17" class="w-100%">
          <!-- 快捷入口 -->
          <ExpressEntrance :shortcut="shortcut" />
          <!-- 智能设备 -->
          <SmartDevice :smartDeviceList="smartDeviceList" />
        </el-space>
      </el-col>
    </el-row>
    <el-row :gutter="15" justify="space-between">
      <el-col :span="15">
        <el-row :gutter="15" justify="space-between">
          <el-col :span="10">
            <!-- 房源概括 -->
            <HouseSummarized :houseSummarizedOption="houseSummarizedOption" />
          </el-col>
          <el-col :span="14">
            <el-space direction="vertical" :fill="true" :size="17" class="w-100%">
              <!-- 利率/均价 -->
              <InterestRate
                :InterestRateOption="InterestRateOption"
                :averagePriceOption="averagePriceOption"
              />
              <!-- 合同执行 -->
              <ContractExecution :contractExecutionInfo="contractExecutionInfo" />
            </el-space>
          </el-col>
        </el-row>
        <!-- 智能设备 -->
        <!-- <SmartDevice :smartDeviceList="smartDeviceList" /> -->
      </el-col>

      <el-col :span="9">
        <!-- 空置预警 -->
        <VacancyAlert :vacancyAlertOption="vacancyAlertOption" />
      </el-col>
    </el-row>
    <el-row :gutter="15" class="m-t-15px">
      <el-col :span="6">
        <!-- 近6个月出租走势/近6个月合同成交走势 -->
        <Trend :trendOption="trendOption" @select="getTrendOption" />
      </el-col>

      <el-col :span="6">
        <!-- 进行中的审批 -->
        <Approvaling :approvalingList="approvalingList" />
      </el-col>
      <el-col :span="6">
        <!-- 待发通知单租客  -->
        <Owner :ownerList="ownerList" />
      </el-col>
      <el-col :span="6">
        <!-- 招商提醒 -->
        <InvestmentReminder :investmentReminderList="investmentReminderList" />
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" setup>
import { set } from 'lodash-es'
import * as echarts from 'echarts'
import { color, EChartsOption } from 'echarts'
import DigitalOverview from './components/digitalOverview.vue'
import ExpressEntrance from './components/expressEntrance.vue'
import SmartDevice from './components/smartDevice.vue'
import HouseSummarized from './components/houseSummarized.vue'
import InterestRate from './components/InterestRate.vue'
import ContractExecution from './components/contractExecution.vue'
import VacancyAlert from './components/vacancyAlert.vue'
import Trend from './components/trend.vue'
import Approvaling from './components/approvaling.vue'
import Owner from './components/owner.vue'
import InvestmentReminder from './components/investmentReminder.vue'
import { useUserStore } from '@/store/modules/user'
import type { WorkplaceTotal, Project, Notice, Shortcut, smartDeviceListItem } from './types'
import { pieOptions, barOptions } from './echarts-data'

defineOptions({ name: 'Home' })

const { t } = useI18n()
const userStore = useUserStore()
const loading = ref(true)
const avatar = userStore.getUser.avatar
const username = userStore.getUser.nickname

const diaitalOverview = reactive({
  dueReceivable: 2652.2,
  dueAndPayable: 8690.4,
  futureReceivables: 2227.36,
  futureExpenditures: 2.2,
  werePastDue: 652.2,
  hasExpired: 8690.4,
  receivableToday: 3227.36,
  shouldBePaidToday: 2.2
})
const pieOptionsData = reactive<EChartsOption>(pieOptions) as EChartsOption

const contractExecutionInfo = ref({
  undisposedNum: 5,
  onGongNum: 5,
  rentHasBeenWithdrawnNum: 5,
  ExpiredNum: 5
})

// 获取快捷入口
let shortcut = reactive<Shortcut[]>([])

const getShortcut = async () => {
  const data = [
    {
      name: '楼宇管理',
      icon: 'home/managementCenter.png',
      url: '/village/managementCenter'
    },
    {
      name: '租客',
      icon: 'home/owner.png',
      url: '/owner/owner'
    },
    {
      name: '财务',
      icon: 'home/bill.png',
      url: '/bill/reportList'
    },
    {
      name: '合同',
      icon: 'home/contract.png',
      url: '/contract/contractList'
    },
    {
      name: '收银台',
      icon: 'home/billCashierList.png',
      url: '/bill/billCashierList'
    },
    {
      name: '应用',
      icon: 'home/application.png',
      url: '/application/conference/conferenceManage'
    }
  ]
  shortcut = Object.assign(shortcut, data)
}

// 用户来源
const getUserAccessSource = async () => {
  const data = [
    { value: 335, name: 'analysis.directAccess' },
    { value: 310, name: 'analysis.mailMarketing' },
    { value: 234, name: 'analysis.allianceAdvertising' },
    { value: 135, name: 'analysis.videoAdvertising' },
    { value: 1548, name: 'analysis.searchEngines' }
  ]
  set(
    pieOptionsData,
    'legend.data',
    data.map((v) => t(v.name))
  )
  pieOptionsData!.series![0].data = data.map((v) => {
    return {
      name: t(v.name),
      value: v.value
    }
  })
}
const barOptionsData = reactive<EChartsOption>(barOptions) as EChartsOption

// 周活跃量
const getWeeklyUserActivity = async () => {
  const data = [
    { value: 13253, name: 'analysis.monday' },
    { value: 34235, name: 'analysis.tuesday' },
    { value: 26321, name: 'analysis.wednesday' },
    { value: 12340, name: 'analysis.thursday' },
    { value: 24643, name: 'analysis.friday' },
    { value: 1322, name: 'analysis.saturday' },
    { value: 1324, name: 'analysis.sunday' }
  ]
  set(
    barOptionsData,
    'xAxis.data',
    data.map((v) => t(v.name))
  )
  set(barOptionsData, 'series', [
    {
      name: t('analysis.activeQuantity'),
      data: data.map((v) => v.value),
      type: 'bar'
    }
  ])
}
let smartDeviceList = reactive<smartDeviceListItem[]>([])
/** 获取智能设备 */
const getSmartDevice = async () => {
  const data = [
    {
      name: '电表',
      list: [
        {
          title: '数量',
          num: 0,
          color: '#f7b500'
        },
        {
          title: '在线',
          num: 0,
          color: '#3ac240'
        },
        {
          title: '离线',
          num: 0,
          color: '#f44745'
        }
      ]
    },
    {
      name: '水表',
      list: [
        {
          title: '数量',
          num: 1,
          color: '#f7b500'
        },
        {
          title: '在线',
          num: 1,
          color: '#3ac240'
        },
        {
          title: '离线',
          num: 0,
          color: '#f44745'
        }
      ]
    },
    {
      name: '摄像头',
      list: [
        {
          title: '数量',
          num: 2,
          color: '#f7b500'
        },
        {
          title: '在线',
          num: 1,
          color: '#3ac240'
        },
        {
          title: '离线',
          num: 2,
          color: '#f44745'
        }
      ]
    },
    {
      name: '门锁',
      list: [
        {
          title: '数量',
          num: 10,
          color: '#f7b500'
        },
        {
          title: '在线',
          num: 5,
          color: '#3ac240'
        },
        {
          title: '离线',
          num: 5,
          color: '#f44745'
        }
      ]
    },
    {
      name: '门禁',
      list: [
        {
          title: '数量',
          num: 3,
          color: '#f7b500'
        },
        {
          title: '在线',
          num: 0,
          color: '#3ac240'
        },
        {
          title: '离线',
          num: 3,
          color: '#f44745'
        }
      ]
    }
  ]
  smartDeviceList = Object.assign(smartDeviceList, data)
  console.log(smartDeviceList, '88888')
}

/** 房源概括 */
const houseSummarizedOption = ref<EChartsOption>({
  legend: {
    // show: false,
    icon: 'circle', // 修改形状
    orient: 'horizontal', //设置图例显示方向为横向
    x: 'center',
    y: 'bottom',
    temHeight: 6, // 修改icon图形大小
    itemGap: 10, // 修改间距
    padding: [0, 0, 0, 0],
    //格式化图例文本
    formatter(name) {
      let count = 0
      houseSummarizedOption.value.series[0].data.forEach((item) => {
        count = count + item.value
      })
      const val = houseSummarizedOption.value.series[0].data.filter((item) => {
        return item.name === name
      })
      // 确保 count 是有效的数字
      if (typeof count === 'number' && count !== 0) {
        const percentage = (val[0].value / count) * 100
        return name + ' ' + val[0].value + ' ' + percentage.toFixed(2) + '%'
      } else {
        return name
      }
    }
  },
  title: {
    text: '总房间数',
    subtext: 25,
    textStyle: {
      fontSize: 18,
      color: '#4C4C4C',
      fontWeight: 400
    },
    subtextStyle: {
      fontSize: 18,
      color: '#000000',
      fontWeight: 600
    },
    left: 'center',
    top: '30%'
  },
  tooltip: {
    trigger: 'item',
    formatter: '<br/>{b}: {c} ({d}%)'
  },
  color: ['#F9666C', '#FEA637', '#2D93E5', '#14CBB9', '#7B79F3'],
  series: [
    {
      type: 'pie',
      radius: ['50%', '65%'],
      center: ['50%', '39%'],
      avoidLabelOverlap: false,
      padAngle: 4,
      itemStyle: {
        borderRadius: 2
      },
      label: {
        show: false
        // formatter: '{b}: {d}%'
      },
      data: [
        {
          value: 2,
          name: '空置'
        },
        {
          value: 5,
          name: '已锁定'
        },
        {
          value: 8,
          name: '已出租'
        },
        {
          value: 5,
          name: '已逾期'
        },
        {
          value: 5,
          name: '已到期'
        }
      ]
    }
  ]
})

const averagePriceOption = ref<EChartsOption>({
  title: {
    text: '973.15', // 圆环中间数字
    textStyle: {
      color: '#2678FF',
      fontSize: 15
    },
    itemGap: 10,
    left: 'center',
    top: 'center'
  },
  angleAxis: {
    max: 100, // 总数
    clockwise: false, // 逆时针
    // 隐藏刻度线
    axisLine: {
      show: false
    },
    axisTick: {
      show: false
    },
    axisLabel: {
      show: false
    },
    splitLine: {
      show: false
    }
  },
  radiusAxis: {
    type: 'category',
    // 隐藏刻度线
    axisLine: {
      show: false
    },
    axisTick: {
      show: false
    },
    axisLabel: {
      show: false
    },
    splitLine: {
      show: false
    }
  },
  polar: {
    center: ['50%', '50%'],
    radius: '140%' // 图形大小
  },
  series: [
    {
      type: 'bar',
      data: [
        {
          name: '交易结果成功数',
          value: 37,
          itemStyle: {
            normal: {
              color: '#2678FF'
            }
          }
        }
      ],
      coordinateSystem: 'polar',
      roundCap: true,
      barWidth: 10,
      barGap: '-100%',
      z: 2
    },
    {
      // 底色环
      type: 'bar',
      data: [
        {
          value: 100,
          itemStyle: {
            color: '#E2E2E2'
          }
        }
      ],
      coordinateSystem: 'polar',
      roundCap: true,
      barWidth: 10,
      barGap: '-100%',
      z: 1
    }
  ]
})

const InterestRateOption = ref<EChartsOption>({
  grid: {
    left: '20px',
    right: '10px',
    bottom: '0%',
    top: '10px',
    containLabel: true
  },
  xAxis: {
    show: false,
    type: 'value'
  },
  yAxis: [
    {
      type: 'category',
      inverse: true,
      offset: 50,
      // 左边文字间距和颜色
      axisLabel: {
        show: true,
        align: 'left',
        textStyle: {
          color: '#333333'
        },
        formatter: function (value, index) {
          let str = '{title|' + value + '}'
          return str
        },
        rich: {
          title: {
            color: '#333333'
          }
        }
      },
      // 轴线
      axisTick: {
        show: false
      },
      axisLine: {
        show: false
      },
      data: ['出租率', '空置率', '计租率']
    },
    {
      type: 'category',
      inverse: true,
      // offset: ,
      axisTick: 'none',
      axisLine: 'none',
      show: true,
      axisLabel: {
        textStyle: {
          color: '#ffffff',
          fontSize: '10'
        },
        formatter: function (value, index) {
          // let str = '{title|' + value + '次}'
          let percentage = (value / InterestRateOption.value.series[1].data[index]) * 100
          console.log(percentage)
          let str = '{title|' + percentage.toFixed(2) + '%}'
          return str
        },
        rich: {
          title: {
            color: '#333333'
          }
        }
      },
      data: [619, 241, 174]
    }
  ],
  series: [
    {
      name: '值',
      type: 'bar',
      zlevel: 1,
      showBackground: true,
      backgroundStyle: {
        color: '#E5EFFF',
        borderRadius: 13
      },
      barWidth: 14,

      itemStyle: {
        normal: {
          barBorderRadius: 13,
          color: '#0766F7'
        }
      },
      data: [519, 114, 174]
    },
    {
      name: '背景',
      type: 'bar',
      barWidth: 14,
      barGap: '-100%',
      data: [1000, 1000, 1000],
      itemStyle: {
        normal: {
          color: '#334F78',
          barBorderRadius: 13
        }
      }
    }
  ]
})

/** 空置房间数据 */
const vacancyAlertOption = ref<EChartsOption>({
  legend: {
    show: false
  },
  grid: {
    left: '10px',
    right: '10px',
    bottom: '10px',
    top: '28px',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: ['0-30天', '31-60天', '61-90天', '91-150天', '151-210天', '210天以上'],
    axisTick: {
      //刻度
      show: false
    }
  },
  yAxis: {
    type: 'value',
    name: '单位/间',
    nameTextStyle: {
      color: '#999999'
    },
    splitLine: {
      show: true,
      lineStyle: {
        color: '#EEEFF3',
        type: 'dashed'
      }
    }
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110],
      type: 'bar',
      barWidth: 20,
      itemStyle: {
        normal: {
          //这里设置柱形图圆角 [左上角，右上角，右下角，左下角]
          barBorderRadius: [88, 88, 0, 0],
          color: new echarts.graphic.LinearGradient(
            0,
            0,
            0,
            1,
            [
              {
                offset: 0,
                color: '#0E6BF7' // 0% 处的颜色
              },
              {
                offset: 1,
                color: 'rgba(24, 144, 255,0)' // 100% 处的颜色
              }
            ],
            false
          )
        }
      }
    }
  ]
})
const trendOption = ref<EChartsOption>({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#6a7985'
      }
    }
  },
  legend: {
    data: ['续住房', '新租房'],
    top: 0,
    right: '10%',
    icon: 'circle' // 修改形状
  },
  grid: {
    left: '0px',
    right: '10px',
    bottom: '10px',
    top: '34px',
    containLabel: true
  },
  xAxis: [
    {
      type: 'category',
      boundaryGap: false,
      axisLabel: {
        show: true,
        color: '#86909C'
      },
      axisLine: {
        //坐标轴轴线相关设置。数学上的x轴
        show: true,
        lineStyle: {
          color: '#86909C'
        }
      },
      splitLine: {
        show: false,
        lineStyle: {
          color: '#192a44'
        }
      },
      axisTick: {
        //刻度
        show: false
      }
    }
  ],
  yAxis: [
    {
      type: 'value',
      name: '单位/间',
      nameTextStyle: {
        color: '#999999'
      },
      splitLine: {
        show: true,
        lineStyle: {
          color: '#EEEFF3',
          type: 'dashed'
        }
      }
    }
  ],
  series: [
    {
      name: '续住房',
      type: 'line',
      stack: 'Total',
      smooth: true,
      symbol: 'circle', // 默认是空心圆（中间是白色的），改成实心圆
      showAllSymbol: true,
      symbolSize: 0,
      lineStyle: {
        normal: {
          width: 2,
          color: 'rgba(43, 122, 255, 1)' // 线条颜色
        },
        borderColor: 'rgba(43, 122, 255, 1)'
      },
      showSymbol: false,
      areaStyle: {
        opacity: 0.8,
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          {
            offset: 0,
            color: 'rgba(43, 122, 255, .4)'
          },
          {
            offset: 1,
            color: 'rgba(43, 122, 255, 0)'
          }
        ]),
        shadowColor: 'rgba(43, 122, 255, 0.5)', //阴影颜色
        shadowBlur: 20 //shadowBlur设图形阴影的模糊大小。配合shadowColor,shadowOffsetX/Y, 设置图形的阴影效果。
      },
      emphasis: {
        focus: 'series'
      },
      data: [140, 232, 101, 264, 90, 340, 250]
    },
    {
      name: '新租房',
      type: 'line',
      stack: 'Total',
      smooth: true,
      symbol: 'circle', // 默认是空心圆（中间是白色的），改成实心圆
      showAllSymbol: true,
      symbolSize: 0,
      lineStyle: {
        normal: {
          width: 2,
          color: 'rgba(20, 201, 201, 1)' // 线条颜色
        },
        borderColor: 'rgba(20, 201, 201, 1)'
      },
      showSymbol: false,
      areaStyle: {
        opacity: 0.8,
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          {
            offset: 0,
            color: 'RGBA(20, 201, 201, .4)'
          },
          {
            offset: 1,
            color: 'RGBA(20, 201, 201, 0)'
          }
        ]),
        shadowColor: 'rgba(20, 201, 201, 0.5)', //阴影颜色
        shadowBlur: 20 //shadowBlur设图形阴影的模糊大小。配合shadowColor,shadowOffsetX/Y, 设置图形的阴影效果。
      },
      emphasis: {
        focus: 'series'
      },
      data: [990, 0, 111, 234, 220, 340, 310]
    }
  ]
})
const getTrendOption = async () => {}

/** 进行中的审批列表 */
const approvalingList = reactive([
  {
    name: '合同新建审批',
    time: '2024-09-06 22:03:07',
    status: '我发起待用户审批',
    created: '于得涛',
    village: '日照职业技术学院/智日照职业技术学院/智日照职业技术...'
  },
  {
    name: '合同新建审批',
    time: '2024-09-06 22:03:07',
    status: '我发起待用户审批',
    created: '于得涛',
    village: '日照职业技术学院/智日照职业技术学院/智日照职业技术...'
  },
  {
    name: '合同新建审批',
    time: '2024-09-06 22:03:07',
    status: '我发起待用户审批',
    created: '于得涛',
    village: '日照职业技术学院/智日照职业技术学院/智日照职业技术...'
  }
])

let ownerList = reactive([])

const getOwner = async () => {
  ownerList.splice(0)
  // const res = await getOwnerList()
  let res = [
    {
      name: '山东萤丰信息技术有限公司',
      num: 30.3
    },
    {
      name: '于得涛',
      num: 176316.55
    },
    {
      name: '山东青云有限公司',
      num: 1859880.24
    },
    {
      name: '日照航海工程职业学院',
      num: 176316.55
    },
    {
      name: '山东萤丰信息技术有限公司',
      num: 176316.55
    },
    {
      name: '于得涛',
      num: 176316.55
    },
    {
      name: '山东青云有限公司',
      num: 614915.76
    },
    {
      name: '山东青云有限公司',
      num: 614915.76
    }
  ]
  ownerList = Object.assign(ownerList, res)
}

/** 招商提醒 */
let investmentReminderList = reactive([])
const getInvestmentReminder = async () => {
  investmentReminderList.splice(0)
  // const res = await getInvestmentReminderList()
  let res = [
    {
      name: '牛家伟',
      type: '初次接触',
      time: '2024-09-06 22:03:07'
    },
    {
      name: '牛家伟',
      type: '初次接触',
      time: '2024-09-03 12:03:07'
    },
    {
      name: '牛家伟',
      type: '初次接触',
      time: '2024-09-23 16:03:12'
    },
    {
      name: '牛家伟',
      type: '初次接触',
      time: '2024-09-16 14:11:43'
    },
    {
      name: '牛家伟',
      type: '初次接触',
      time: '2024-09-26 15:03:01'
    },
    {
      name: '牛家伟',
      type: '初次接触',
      time: '2024-09-01 11:03:07'
    },
    {
      name: '牛家伟',
      type: '初次接触',
      time: '2024-09-21 12:05:07'
    }
  ]
  investmentReminderList = Object.assign(investmentReminderList, res)
}
const getAllApi = async () => {
  await Promise.all([
    getShortcut(),
    getSmartDevice(),
    getTrendOption(),
    getOwner(),
    getInvestmentReminder()
  ])
  loading.value = false
}

getAllApi()
</script>
