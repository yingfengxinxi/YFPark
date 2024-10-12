<template>
  <ElDrawer :title="dialogTitle" v-model="dialogVisible" direction="rtl" size="900px">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="140px"
      v-loading="formLoading"
      label-position="top"
    >
      <el-card
        class="!border-rd-4px w-100% box-border font-size-14px"
        style="border: 1px solid #f0f0f0 !important"
        shadow="never"
      >
        <template #header>
          <span class="font-size-14px"> 基本信息 </span>
        </template>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属项目" prop="villageId">
              <el-select
                v-model="formData.villageId"
                placeholder="请选择所属项目"
                clearable
                @change="getParking()"
              >
                <el-option
                  v-for="item in buildingDataList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属停车场" prop="parkId">
              <el-select
                class="clear-both w-100%"
                v-model="formData.parkId"
                placeholder="请选择所属停车场"
              >
                <el-option
                  v-for="item in parkList"
                  :key="item.id"
                  :label="item.parkName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收费标准名称" prop="chargeName">
              <el-input v-model="formData.chargeName" placeholder="请输入收费标准名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="适用车辆类型" prop="carType">
              <el-select multiple v-model="formData.carType" placeholder="请选择适用车辆类型">
                <el-option
                  v-for="item in getIntDictOptions(DICT_TYPE.FREE_CAR)"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生效日期" prop="effectiveDate">
              <el-date-picker
                v-model="formData.effectiveDate"
                type="date"
                value-format="x"
                placeholder="选择生效日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="isDefault">
              <el-switch
                inline-prompt
                v-model="formData.isDefault"
                active-text="是"
                inactive-text="否"
                active-value="1"
                inactive-value="0"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>
      <el-card
        class="!border-rd-4px w-100% box-border font-size-14px m-t-10px"
        style="border: 1px solid #f0f0f0 !important"
        shadow="never"
      >
        <template #header>
          <span class="font-size-14px"> 月租车收费 </span>
        </template>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="月租每月费用" prop="monthFee">
              <template #label>
                <span class="font-size-14px m-r-4px">月租每月费用</span>
                <el-tooltip content="如果没有月租政策，此费用可不填写" placement="top"
                  ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                /></el-tooltip>
              </template>
              <el-input-number
                v-model="formData.monthFee"
                :step="1"
                :min="0"
                controls-position="right"
                placeholder="请输入月租每月费用"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item>
              <el-checkbox
                v-model="formData.monthRechargeInput"
                label="支持用户输入租赁月数"
                size="large"
                true-value="1"
                false-value="0"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <div class="flex">
              <div class="font-size-14px m-r-4px">月租车套餐</div>
              <el-button type="primary" link @click="addSetMeal('create', '', '')">增加</el-button>
            </div>
            <div
              class="m-t-20px grid grid-cols-3 grid-gap-20px"
              v-if="monthRechargePackage.length > 0"
            >
              <div
                class="b-1 b-#e6e7e8 b-solid b-rd-5px p-10px"
                v-for="(item, index) in monthRechargePackage"
                :key="index"
              >
                <div class="flex">
                  <span class="font-size-14px flex-1">{{ item.name }}</span>
                  <el-dropdown type="primary" class="m-l-6px">
                    <el-button type="primary" link>
                      更多操作 <Icon icon="ep:arrow-down"><arrow-down /></Icon>
                    </el-button>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item>
                          <el-button link @click="addSetMeal('update', item, index)">
                            编辑
                          </el-button></el-dropdown-item
                        >
                        <el-dropdown-item
                          ><el-button link @click="handleDelete(index)">
                            删除
                          </el-button></el-dropdown-item
                        >
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <div class="m-t-10px c-#bfbfbf font-size-14px">月份数：{{ item.month }}</div>
                <div class="m-t-10px c-#bfbfbf font-size-14px"> 说明：{{ item.info }}</div>
                <div class="flex justify-between items-center m-t-12px">
                  <span class="c-#1890ff">{{ item.price }}元</span>
                  <el-switch
                    v-model="item.status"
                    active-text="已启动"
                    inactive-text="已关闭"
                    :active-value="1"
                    :inactive-value="0"
                    size="small"
                    inline-prompt
                  />
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-card>
      <el-card
        class="!border-rd-4px w-100% box-border font-size-14px m-t-10px"
        style="border: 1px solid #f0f0f0 !important"
        shadow="never"
      >
        <template #header>
          <span class="font-size-14px"> 临时车政策 </span>
        </template>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="临时车免费时长（分钟）" prop="monthFee">
              <el-input-number
                v-model="formData.freeMinute"
                :step="1"
                :min="0"
                controls-position="right"
                placeholder="请输入临时车免费时长（分钟）"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="临时车免费出场时长（分钟）" prop="monthFee">
              <el-input-number
                v-model="formData.freeOutMinute"
                :step="1"
                :min="0"
                controls-position="right"
                placeholder="请输入临时车免费出场时长（分钟）"
                class="!w-100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="临时车收费标准" prop="chargeType">
              <el-select
                v-model="formData.chargeType"
                placeholder="请选择临时车收费标准"
                @change="chargeTypeChange(formData.chargeType)"
              >
                <el-option
                  v-for="item in getIntDictOptions(DICT_TYPE.TEMPORARY_VEHICIE_CHARGE_TYPE)"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form
          ref="chargeDetailRef"
          :model="chargeDetailFormData"
          :rules="chargeDetailFormRules"
          label-width="140px"
          v-loading="formLoading"
          label-position="top"
        >
          <el-row :gutter="20">
            <template v-if="formData.chargeType == '0'">
              <div class="Line_title">标准收费参数区域（计费单位：元）</div>
              <el-col :span="6" v-for="item in 24" :key="item">
                <el-form-item
                  :label="item + '小时'"
                  :prop="'chargeDetail' + item"
                  :rules="[
                    {
                      required: true,
                      validator: checkInputValid,
                      message: '必填项',
                      trigger: ['blur', 'change']
                    }
                  ]"
                >
                  <el-input-number
                    v-model="chargeDetailFormData['chargeDetail' + item]"
                    :step="1"
                    :min="0"
                    controls-position="right"
                    placeholder="请输入"
                    class="!w-100%"
                  />
                </el-form-item>
              </el-col>
            </template>
            <template v-if="formData.chargeType == '1'">
              <div class="Line_title">按次收费参数区域（计费单位：元）</div>
              <el-col :span="24">
                <el-form-item label="收费类型">
                  <el-radio-group
                    v-model="chargeDetailFormData.type"
                    @change="chargeDetailTypeChange(chargeDetailFormData.type)"
                  >
                    <el-radio label="当天只收一次费（零点前）" :value="1" />
                    <el-radio :value="2">
                      <template #default>
                        连续
                        <el-input-number
                          v-model="chargeDetailFormData.hour"
                          :step="1"
                          :min="0"
                          controls-position="right"
                          placeholder="请输入"
                          size="mini"
                        />
                        小时只收一次费
                      </template>
                    </el-radio>
                    <el-radio label="按次收费" :value="3" />
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item
                  label="收费金额"
                  prop="fee"
                  :rules="[
                    {
                      required: true,
                      message: '必填项',
                      trigger: ['blur', 'change']
                    }
                  ]"
                >
                  <el-input-number
                    class="!flex-1 m-r-10px"
                    v-model="chargeDetailFormData.fee"
                    :step="1"
                    :min="0"
                    controls-position="right"
                    placeholder="请输入"
                    size="mini"
                  />
                  元
                </el-form-item>
              </el-col>
            </template>
            <template v-if="formData.chargeType == '2'">
              <div class="Line_title">按白天夜间收费参数区域（计费单位：元）</div>
              <el-card
                class="!border-rd-4px w-100% box-border font-size-14px m-t-10px"
                style="border: 1px solid #f0f0f0 !important"
                shadow="never"
              >
                <template #header>
                  <span class="font-size-14px"> 白天 </span>
                </template>
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item
                      prop="day_begin_hour"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <template #label>
                        <span class="font-size-14px m-r-4px"> 开始（小时）</span>
                        <el-tooltip content="24小时制" placement="top"
                          ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                        /></el-tooltip>
                      </template>
                      <el-input-number
                        v-model="chargeDetailFormData.day_begin_hour"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="day_begin_minute"
                      label=" 开始（分钟）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.day_begin_minute"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="day_first_time_unit"
                      label=" 首计时单位（分钟）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.day_first_time_unit"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="day_first_fee_unit"
                      label=" 首计时单位收费额（元）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.day_first_fee_unit"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="day_time_unit"
                      label=" 计时单位（分钟）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.day_time_unit"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="day_fee_unit"
                      label=" 每计时单位收费额（元）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.day_fee_unit"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="day_fee_max"
                      label=" 最高收费（元）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <template #label>
                        <span class="font-size-14px m-r-4px">最高收费（元）</span>
                        <el-tooltip content="必填，如填0，则代表不收费" placement="top"
                          ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                        /></el-tooltip>
                      </template>
                      <el-input-number
                        v-model="chargeDetailFormData.day_fee_max"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-card>
              <el-card
                class="!border-rd-4px w-100% box-border font-size-14px m-t-10px"
                style="border: 1px solid #f0f0f0 !important"
                shadow="never"
              >
                <template #header>
                  <span class="font-size-14px"> 夜间 </span>
                </template>
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item
                      prop="day_begin_hour"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <template #label>
                        <span class="font-size-14px m-r-4px"> 开始（小时）</span>
                        <el-tooltip content="24小时制" placement="top"
                          ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                        /></el-tooltip>
                      </template>
                      <el-input-number
                        v-model="chargeDetailFormData.day_begin_hour"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="night_begin_minute"
                      label=" 开始（分钟）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.night_begin_minute"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="night_first_time_unit"
                      label=" 首计时单位（分钟）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.night_first_time_unit"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="night_first_fee_unit"
                      label=" 首计时单位收费额（元）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.night_first_fee_unit"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="night_time_unit"
                      label=" 计时单位（分钟）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.night_time_unit"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="night_fee_unit"
                      label=" 每计时单位收费额（元）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <el-input-number
                        v-model="chargeDetailFormData.night_fee_unit"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item
                      prop="night_fee_max"
                      label=" 最高收费（元）"
                      :rules="[
                        {
                          required: true,
                          message: '必填项',
                          trigger: ['blur', 'change']
                        }
                      ]"
                    >
                      <template #label>
                        <span class="font-size-14px m-r-4px">最高收费（元）</span>
                        <el-tooltip content="必填，如填0，则代表不收费" placement="top"
                          ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
                        /></el-tooltip>
                      </template>
                      <el-input-number
                        v-model="chargeDetailFormData.night_fee_max"
                        :step="1"
                        :min="0"
                        controls-position="right"
                        placeholder="请输入"
                        class="!w-100%"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-card>
            </template>
            <template v-if="formData.chargeType == '3'">
              <div class="Line_title">按设定时间收费参数区域（计费单位：元） </div>
              <el-col :span="12">
                <el-form-item
                  prop="first_time_unit"
                  label=" 首计时单位（分钟）"
                  :rules="[
                    {
                      required: true,
                      message: '必填项',
                      trigger: ['blur', 'change']
                    }
                  ]"
                >
                  <el-input-number
                    v-model="chargeDetailFormData.first_time_unit"
                    :step="1"
                    :min="0"
                    controls-position="right"
                    placeholder="请输入"
                    class="!w-100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="first_fee_unit"
                  label=" 首计时单位收费额（元）"
                  :rules="[
                    {
                      required: true,
                      message: '必填项',
                      trigger: ['blur', 'change']
                    }
                  ]"
                >
                  <el-input-number
                    v-model="chargeDetailFormData.first_fee_unit"
                    :step="1"
                    :min="0"
                    controls-position="right"
                    placeholder="请输入"
                    class="!w-100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="time_unit"
                  label=" 计时单位（分钟）"
                  :rules="[
                    {
                      required: true,
                      message: '必填项',
                      trigger: ['blur', 'change']
                    }
                  ]"
                >
                  <el-input-number
                    v-model="chargeDetailFormData.time_unit"
                    :step="1"
                    :min="0"
                    controls-position="right"
                    placeholder="请输入"
                    class="!w-100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="fee_unit"
                  label=" 每计时单位收费额（元）"
                  :rules="[
                    {
                      required: true,
                      message: '必填项',
                      trigger: ['blur', 'change']
                    }
                  ]"
                >
                  <el-input-number
                    v-model="chargeDetailFormData.fee_unit"
                    :step="1"
                    :min="0"
                    controls-position="right"
                    placeholder="请输入"
                    class="!w-100%"
                  />
                </el-form-item>
              </el-col>
            </template>
          </el-row>
        </el-form>
      </el-card>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>

  <setMeal ref="SetMealRef" @success="getSetmealItem" />
</template>
<script setup lang="ts">
import { ParkChargeApi, VO } from '@/api/bus/parkCharge/index'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { ParkApi } from '@/api/bus/park'
import SetMeal from './component/setMeal.vue'
/** 停车场收费标准 表单 */
defineOptions({ name: 'Form' })

import { BuildApi } from '@/api/bus/village'
import { useUserStore } from '@/store/modules/user'
import { Edit } from '@element-plus/icons-vue/dist/types'
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const buildingDataList = ref([] as any[])
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const parkList = ref([])

const checkInputValid = (rules, value, callback) => {
  if (!value) {
    callback(new Error('必填项'))
  } else {
    callback()
  }
}

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  chargeName: undefined,
  parkId: undefined,
  villageId: undefined,
  carType: undefined,
  monthFee: undefined,
  monthOverdueType: undefined,
  monthRechargeInput: undefined,
  monthRechargePackage: undefined,
  effectiveDate: undefined,
  freeMinute: undefined,
  freeOutMinute: undefined,
  chargeType: undefined,
  dayMaxFee: undefined,
  chargeDetail: undefined,
  isDefault: undefined
})

const monthRechargePackage = ref([
  {
    name: '11111',
    month: 1,
    price: 1,
    info: 1,
    status: 1
  }
])

const chargeDetailFormData = ref({
  chargeDetail1: undefined,
  chargeDetail2: undefined,
  chargeDetail3: undefined,
  chargeDetail4: undefined,
  chargeDetail5: undefined,
  chargeDetail6: undefined,
  chargeDetail7: undefined,
  chargeDetail8: undefined,
  chargeDetail9: undefined,
  chargeDetail10: undefined,
  chargeDetail11: undefined,
  chargeDetail12: undefined,
  chargeDetail13: undefined,
  chargeDetail14: undefined,
  chargeDetail15: undefined,
  chargeDetail16: undefined,
  chargeDetail17: undefined,
  chargeDetail18: undefined,
  chargeDetail19: undefined,
  chargeDetail20: undefined,
  chargeDetail21: undefined,
  chargeDetail22: undefined,
  chargeDetail23: undefined,
  chargeDetail24: undefined,
  type: undefined,
  hour: 0,
  fee: undefined,
  day_begin_hour: undefined,
  day_begin_minute: undefined,
  day_time_unit: undefined,
  day_fee_unit: undefined,
  day_first_time_unit: undefined,
  day_first_fee_unit: undefined,
  day_fee_max: undefined,
  night_begin_hour: undefined,
  night_begin_minute: undefined,
  night_time_unit: undefined,
  night_fee_unit: undefined,
  night_first_time_unit: undefined,
  night_first_fee_unit: undefined,
  night_fee_max: undefined,
  time_unit: undefined,
  fee_unit: undefined,
  first_time_unit: undefined,
  first_fee_unit: undefined
})

const chargeTypeChange = (val) => {
  if (val == 1 && !chargeDetailFormData.value.type) {
    chargeDetailFormData.value.type = 1
  }
}
const chargeDetailTypeChange = (val) => {
  chargeDetailFormData.value.hour = 0
}
const formRules = reactive({
  chargeName: [{ required: true, message: '收费标准名称不能为空', trigger: 'blur' }],
  parkId: [{ required: true, message: '停车场ID不能为空', trigger: 'blur' }],
  villageId: [{ required: true, message: '项目ID不能为空', trigger: 'blur' }],
  carType: [
    { required: true, message: '适用车的类型（1蓝牌、黄牌等，参照D1）不能为空', trigger: 'change' }
  ],
  monthOverdueType: [
    {
      required: true,
      message: '月租车到期后类型（0临时车（可能不允许进入），1储值车）不能为空',
      trigger: 'change'
    }
  ],
  monthRechargeInput: [
    {
      required: true,
      message: '月租车自主缴费能输入月数（0不允许，1允许）不能为空',
      trigger: 'blur'
    }
  ],
  effectiveDate: [{ required: true, message: '生效日期不能为空', trigger: 'blur' }],
  freeMinute: [{ required: true, message: '临时车免费时长（分钟）不能为空', trigger: 'blur' }],
  freeOutMinute: [
    { required: true, message: '临时车免费出场时长（分钟）不能为空', trigger: 'blur' }
  ],
  chargeType: [
    {
      required: true,
      message: '临时车0标准收费，1按次收费，2按白天夜间收费，3按设定时间收费不能为空',
      trigger: 'change'
    }
  ],
  dayMaxFee: [{ required: true, message: '临时车每日最高费用（0）不限不能为空', trigger: 'blur' }],
  chargeDetail: [{ required: true, message: '临时车收费细则不能为空', trigger: 'blur' }],
  isDefault: [{ required: true, message: '是否默认,1是0否不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

const SetMealRef = ref()
// 月租车套餐
const addSetMeal = (type, info, index) => {
  let data = JSON.parse(JSON.stringify(info))
  SetMealRef.value.open(type, data, index)
}

const handleDelete = (index) => {
  monthRechargePackage.value.splice(index, 1)
}

const getSetmealItem = (type, data, index) => {
  if (type == 'create') {
    monthRechargePackage.value.push(data)
  } else {
    monthRechargePackage.value[index] = data
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  getTree()
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const data = await ParkChargeApi.get(id)
      if (data.carType) {
        data.carType = data.carType.split(',')
        data.carType.forEach((item: any, index: number) => {
          data.carType[index] = Number(item)
        })
      }
      data.chargeType = Number(data.chargeType)
      formData.value = data
      monthRechargePackage.value = JSON.parse(data.monthRechargePackage)
      chargeDetailFormData.value = JSON.parse(formData.value.chargeDetail)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 获得楼宇 */
const getTree = async () => {
  try {
    const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
    buildingDataList.value = res.villageList
    formData.value.villageId = res.villageList[0].id
    getParking()
  } finally {
  }
}

// 根据项目id获取停车场
const getParking = async () => {
  try {
    const res = await ParkApi.getParkingList(formData.value.villageId)
    parkList.value = res.list
    formData.value.parkId = res.list.length ? res.list[0].id : null
  } finally {
  }
}
const chargeDetailRef = ref()
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await chargeDetailRef.value.validate()
  await formRef.value.validate()
  console.log(formData.value.chargeType)
  if (formData.value.chargeType == 1) {
    if (chargeDetailFormData.value.hour == 0 && chargeDetailFormData.value.type == 2) {
      message.error('请输入连续小时数')
      return
    }
  }

  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as VO

    data.monthRechargePackage = JSON.stringify(monthRechargePackage.value)
    data.chargeDetail = JSON.stringify(chargeDetailFormData.value)
    data.carType = formData.value.carType.join(',')
    if (formType.value === 'create') {
      await ParkChargeApi.create(data)
      message.success(t('common.createSuccess'))
    } else {
      await ParkChargeApi.update(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    chargeName: undefined,
    parkId: undefined,
    villageId: undefined,
    carType: undefined,
    monthFee: undefined,
    monthOverdueType: undefined,
    monthRechargeInput: undefined,
    monthRechargePackage: undefined,
    effectiveDate: undefined,
    freeMinute: undefined,
    freeOutMinute: undefined,
    chargeType: undefined,
    dayMaxFee: undefined,
    chargeDetail: undefined,
    isDefault: undefined
  }
  formRef.value?.resetFields()
  monthRechargePackage.value = []
}
</script>
<style>
.el-input-number .el-input__inner {
  text-align: left;
}
</style>
