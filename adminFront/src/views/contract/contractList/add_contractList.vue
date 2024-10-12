<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <div>
    <ContentWrap>
      <div class="stepBox">
        <Icon icon="ep:arrow-left" @click="emit('close')" class="cursor-pointer" />
        <div
          class="step_item"
          :class="step_select_index == index + 1 ? 'step_border' : ''"
          v-for="(item, index) in stepList"
          :key="index"
        >
          <div :class="step_select_index >= index + 1 ? 'step_number' : 'step_select_notselect'">{{
            index + 1
          }}</div>
          {{ item }}
        </div>
      </div>
    </ContentWrap>
    <div class="flex flex-justify-center gap-3" v-if="step_select_index == 1">
      <div class="w-[50%]">
        <ContentWrapBorder :header_data="'基本信息'">
          <el-form :label-position="'top'" :model="form" ref="addForm_jbxx">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item
                  label="合同编号"
                  prop="contractNumber"
                  :rules="[{ required: true, message: '请输入合同编号', trigger: 'blur' }]"
                >
                  <el-input v-model="form.contractNumber" placeholder="请输入合同编号" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="经办人"
                  prop="operatorId"
                  :rules="[{ required: true, message: '请选择经办人', trigger: 'blur' }]"
                >
                  <el-select v-model="form.operatorId" placeholder="请选择经办人">
                    <el-option
                      v-for="(item, index) in getoperatorIdList"
                      :key="index"
                      :value="item.id"
                      :label="item.nickname"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="签订日"
                  prop="signingDate"
                  :rules="[{ required: true, message: '请选择签订日', trigger: 'blur' }]"
                >
                  <el-date-picker
                    v-model="form.signingDate"
                    type="date"
                    placeholder="选择日期"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                    format="YYYY-MM-DD"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="合同开始日期"
                  prop="contractStartTime"
                  :rules="[{ required: true, message: '请选择合同开始日期', trigger: 'blur' }]"
                >
                  <el-date-picker
                    v-model="form.contractStartTime"
                    type="date"
                    placeholder="选择日期"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                    format="YYYY-MM-DD"
                    :disabled-date="disabledstartDate"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="合同结束日期"
                  prop="contractEndTime"
                  :rules="[{ required: true, message: '请选择合同结束日期', trigger: 'blur' }]"
                >
                  <el-date-picker
                    v-model="form.contractEndTime"
                    type="date"
                    placeholder="选择日期"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                    format="YYYY-MM-DD"
                    :disabled-date="disabledendDate"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="单价保留小数点"
                  prop="unitPricePoint"
                  :rules="[{ required: true, message: '请输入单价保留小数点', trigger: 'blur' }]"
                >
                  <el-input
                    :type="'number'"
                    min="0"
                    v-model="form.unitPricePoint"
                    placeholder="请输入单价保留小数点"
                  >
                    <template #append>位</template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="计算精度"
                  prop="calculationAccuracy"
                  :rules="[{ required: true, message: '请选择计算精度', trigger: 'blur' }]"
                >
                  <el-select v-model="form.calculationAccuracy" placeholder="请选择计算精度">
                    <el-option label="精度计算结果保留两位数" value="0" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="计算顺序"
                  prop="calculationOrder"
                  :rules="[{ required: true, message: '请选择计算顺序', trigger: 'blur' }]"
                >
                  <el-select v-model="form.calculationOrder" placeholder="请选择计算顺序">
                    <el-option
                      v-for="(item, index) in calculationOrderList"
                      :key="index"
                      :value="item.value"
                      :label="item.label"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="应收整数(四舍五入)"
                  prop="isReceivableInteger"
                  :rules="[{ required: true, message: '请选择应收整数', trigger: 'blur' }]"
                >
                  <el-select v-model="form.isReceivableInteger" placeholder="请选择应收整数">
                    <el-option
                      v-for="(item, index) in isReceivableIntegerList"
                      :key="index"
                      :value="item.value"
                      :label="item.label"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </ContentWrapBorder>
        <ContentWrapBorder :header_data="'租客信息'">
          <el-form :label-position="'top'" :model="form" ref="addForm_zkxx">
            <el-row :gutter="20">
              <el-col :span="8">
                <span
                  class="text-#2681F3 cursor-pointer float-right z-99 transform-translate-y-[4px]"
                  @click.stop="useradd"
                  >添加</span
                >
                <el-form-item
                  label="租客"
                  prop="ownerId"
                  :rules="[{ required: true, message: '请选择租客', trigger: 'blur' }]"
                >
                  <template #label> 租客 </template>
                  <el-select
                    v-model="form.ownerId"
                    placeholder="请选择租客"
                    @change="ownerId_change"
                    :disabled="detail_open_type == 'Stayover'"
                  >
                    <el-option
                      v-for="(item, index) in ownerIdList"
                      :key="index"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="行业">
                  <el-select
                    v-model="form.industry"
                    placeholder="请选择行业"
                    :disabled="detail_open_type == 'Stayover'"
                  >
                    <el-option
                      v-for="(item, index) in industryList"
                      :key="index"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="法人">
                  <el-input
                    v-model="form.legalPerson"
                    placeholder="请输入名称"
                    :disabled="detail_open_type == 'Stayover'"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="签订人">
                  <el-input
                    v-model="form.signedName"
                    placeholder="请输入签订人名称"
                    :disabled="detail_open_type == 'Stayover'"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </ContentWrapBorder>
        <ContentWrapBorder :header_data="'滞纳金'">
          <el-form label-position="top" :model="form" ref="addForm_znj">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item
                  label="起算天数"
                  prop="startingLateFeeDay"
                  :rules="[{ required: true, message: '请输入起算天数', trigger: 'blur' }]"
                >
                  <el-input
                    type="number"
                    min="0"
                    v-model="form.startingLateFeeDay"
                    placeholder="请输入起算天数"
                  >
                    <template #append>天</template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="滞纳金比例"
                  prop="lateFeeRatio"
                  :rules="[{ required: true, message: '请输入滞纳金比例', trigger: 'blur' }]"
                >
                  <el-input
                    type="number"
                    min="0"
                    v-model="form.lateFeeRatio"
                    placeholder="请输入滞纳金比例"
                  >
                    <template #append>%/天</template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item
                  label="滞纳金上限"
                  prop="upperLimitLateFee"
                  :rules="[{ required: true, message: '请输入滞纳金上限', trigger: 'blur' }]"
                >
                  <el-input
                    type="number"
                    min="0"
                    v-model="form.upperLimitLateFee"
                    placeholder="请输入滞纳金上限"
                  >
                    <template #append>%</template>
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </ContentWrapBorder>
      </div>
      <div class="w-[50%]">
        <ContentWrap>
          <div class="flex justify-between items-center pb-8px border-bottom">
            <div class="text-16px">{{ !change_tree_value ? '已选房源' : '请选择房源' }}</div>
            <el-button @click="change_tree" v-if="!change_tree_value" type="primary" text
              >选择房源</el-button
            >
            <el-button @click="change_tree" v-else type="primary" text>查看已选</el-button>
          </div>
          <DeptTreeSelect
            @change-data="DeptTreeSelect_data"
            ref="DeptTreeSelect_ref"
            :change_tree="change_tree_value"
          />
        </ContentWrap>
      </div>
    </div>
    <div v-if="step_select_index == 2">
      <!-- 租凭条款物业条款切换 -->
      <ContentWrap>
        <div class="row_change flex gap-10">
          <div
            class="row_item"
            v-for="(item, index) in row_change_list"
            :key="index"
            :class="row_change_index == index + 1 ? 'active' : ''"
            @click="row_change_index = index + 1"
          >
            {{ item }}
          </div>
        </div>
      </ContentWrap>
      <div>
        <div class="flex mt-6 gap-4">
          <ContentWrapBorder :header_data="'已选中房源'" class="w-[100%]">
            <el-table :data="DeptTreeSelect_data_list">
              <el-table-column prop="roomName" label="房源名称" />
              <el-table-column label="详细地址">
                <template #default="{ row }">
                  <span>{{ row.villageName }}/{{ row.buildName }}/{{ row.roomName }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="rentalArea" label="可租面积" />
            </el-table>
          </ContentWrapBorder>
          <div class="w-[100%]">
            <ContentWrap class="w-[100%]">
              <el-form label-position="top">
                <el-col>
                  <el-row>
                    <el-form-item label="租凭面积" required>
                      <el-input
                        v-model="form.leaseArea"
                        placeholder="返回上一步并选择房间即可生成"
                        disabled
                      />
                    </el-form-item>
                  </el-row>
                </el-col>
              </el-form>
            </ContentWrap>
            <ContentWrapBorder :header_data="row_change_index == 1 ? '租凭保证金' : '物业保证金'">
              <!-- 租凭条约 -->
              <el-form
                label-position="top"
                :model="row_change_data.render.bondClause"
                v-show="row_change_index == 1"
                ref="addForm_zpbzj_1"
              >
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-form-item
                      label="保证金类型"
                      prop="bondType"
                      :rules="[{ required: true, message: '请选择保证金类型', trigger: 'blur' }]"
                    >
                      <el-select
                        v-model="row_change_data.render.bondClause.bondType"
                        placeholder="请选择保证金类型"
                        clearable
                        @change="change_isEdit_zl"
                      >
                        <el-option
                          v-for="item in bzjTypeList"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      label="保证金金额"
                      prop="bondPrice"
                      :rules="[{ required: true, message: '请输入保证金金额', trigger: 'blur' }]"
                    >
                      <el-input
                        v-model="row_change_data.render.bondClause.bondPrice"
                        type="number"
                        min="0"
                        placeholder="请输入保证金金额"
                        @change="change_isEdit_zl"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="币种(单位)" required>
                      <el-input value="人民币(CNY)" disabled />
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
              <!-- 物业条约 -->
              <el-form
                label-position="top"
                :model="row_change_data.property.bondClause"
                ref="addForm_zpbzj_2"
                v-show="row_change_index == 2"
              >
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-form-item
                      label="保证金类型"
                      :rules="[{ required: true, message: '请选择保证金类型', trigger: 'blur' }]"
                      prop="bondType"
                    >
                      <el-select
                        v-model="row_change_data.property.bondClause.bondType"
                        placeholder="请选择保证金类型"
                        clearable
                        @change="change_isEdit_wy"
                      >
                        <el-option
                          v-for="item in bzjTypeList"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      label="保证金金额"
                      :rules="[{ required: true, message: '请输入保证金金额', trigger: 'blur' }]"
                      prop="bondPrice"
                    >
                      <el-input
                        v-model="row_change_data.property.bondClause.bondPrice"
                        type="number"
                        min="0"
                        placeholder="请输入保证金金额"
                        @change="change_isEdit_wy"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="币种(单位)" required>
                      <el-input value="人民币(CNY)" disabled />
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </ContentWrapBorder>
            <!-- ///税率 -->
            <ContentWrapBorder
              :header_data="row_change_index == 1 ? '租凭税率' : '物业税率'"
              class="mt-[18px]"
            >
              <el-form
                label-position="top"
                v-show="row_change_index == 1"
                :model="row_change_data.render.taxClause"
                ref="addForm_sl_1"
              >
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item
                      label="含税规则"
                      :rules="[{ required: true, message: '请选择含税规则', trigger: 'blur' }]"
                      prop="taxRule"
                    >
                      <el-select
                        v-model="row_change_data.render.taxClause.taxRule"
                        placeholder="请选择含税规则"
                        @change="change_isEdit_zl"
                      >
                        <el-option
                          v-for="(item, index) in unitPriceTaxList"
                          :key="index"
                          :label="item.label"
                          :value="item.sort"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" v-if="row_change_data.render.taxClause.taxRule != '1'">
                    <el-form-item
                      label="税率"
                      :rules="[{ required: true, message: '请输入税率', trigger: 'blur' }]"
                      prop="taxRate"
                    >
                      <el-input
                        v-model="row_change_data.render.taxClause.taxRate"
                        type="number"
                        min="0"
                        placeholder="请输入税率"
                        @change="change_isEdit_zl"
                      >
                        <template #append>%</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
              <el-form
                label-position="top"
                v-show="row_change_index == 2"
                :model="row_change_data.property.taxClause"
                ref="addForm_sl_2"
              >
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item
                      label="含税规则"
                      :rules="[{ required: true, message: '请选择含税规则', trigger: 'blur' }]"
                      prop="taxRule"
                    >
                      <el-select
                        v-model="row_change_data.property.taxClause.taxRule"
                        placeholder="请选择含税规则"
                        @change="change_isEdit_wy"
                      >
                        <el-option
                          v-for="(item, index) in unitPriceTaxList"
                          :key="index"
                          :label="item.label"
                          :value="item.sort"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" v-if="row_change_data.property.taxClause.taxRule != '1'">
                    <el-form-item
                      label="税率"
                      :rules="[{ required: true, message: '请输入税率', trigger: 'blur' }]"
                      prop="taxRate"
                    >
                      <el-input
                        v-model="row_change_data.property.taxClause.taxRate"
                        type="number"
                        min="0"
                        placeholder="请输入税率"
                        @change="change_isEdit_wy"
                      >
                        <template #append>%</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </ContentWrapBorder>
          </div>
        </div>
        <div>
          <!-- 租凭条款 -->
          <el-form
            label-position="top"
            :model="row_change_data.render"
            v-show="row_change_index == 1"
            ref="addForm_zptk_list"
          >
            <ContentWrapBorder
              :header_data="'租凭条款'"
              v-for="(item, index) in row_change_data.render.multipleClause"
              class="mt-[18px]"
              :key="index"
            >
              <el-row :gutter="20">
                <el-col :span="6">
                  <el-form-item
                    label="条款开始时间"
                    :rules="[{ required: true, message: '请选择条款开始时间', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].startLeaseDate`"
                  >
                    <el-date-picker
                      type="date"
                      placeholder="选择开始时间"
                      v-model="item.startLeaseDate"
                      style="width: 100%"
                      value-format="YYYY-MM-DD"
                      format="YYYY-MM-DD"
                      @change="change_isEdit_zl"
                      :disabled-date="disabledstartDate_step2"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="条款结束时间"
                    :rules="[{ required: true, message: '请选择条款结束时间', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].endLeaseDate`"
                  >
                    <el-date-picker
                      type="date"
                      placeholder="选择结束时间"
                      v-model="item.endLeaseDate"
                      style="width: 100%"
                      value-format="YYYY-MM-DD"
                      format="YYYY-MM-DD"
                      @change="change_isEdit_zl"
                      :disabled-date="disabledendDate_step2"
                      @click="changeTimeIndex = index"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="合同单价"
                    :rules="[{ required: true, message: '请输入合同单价', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].dMoney`"
                  >
                    <el-input
                      v-model="item.dMoney"
                      type="number"
                      min="0"
                      placeholder="请输入合同单价"
                      @change="change_isEdit_zl"
                    >
                      <template #append>
                        <el-select
                          v-model="item.contractUnitPrice"
                          style="width: 100px"
                          @change="change_isEdit_zl"
                        >
                          <el-option
                            v-for="item in contractUnitPriceList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.sort"
                          />
                        </el-select>
                      </template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="付款时间"
                    :rules="[{ required: true, message: '请输入付款时间', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].payDay`"
                  >
                    <el-input
                      v-model="item.payDay"
                      type="number"
                      min="0"
                      placeholder="请输入付款时间"
                      @change="change_isEdit_zl"
                    >
                      <template #prepend>提前</template>
                      <template #append>自然日</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="付款周期(几月一付)"
                    :rules="[{ required: true, message: '请输入付款周期', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].day`"
                  >
                    <el-input
                      type="number"
                      min="0"
                      v-model="item.day"
                      placeholder="请输入付款周期"
                      @change="change_isEdit_zl"
                    >
                      <template #append>月</template>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </ContentWrapBorder>
          </el-form>
          <!-- 物业条款 -->
          <el-form
            label-position="top"
            :model="row_change_data.property"
            ref="addForm_wytk_list"
            v-show="row_change_index == 2"
          >
            <ContentWrapBorder
              :header_data="'物业条款'"
              v-for="(item, index) in row_change_data.property.multipleClause"
              class="mt-[18px]"
              :key="index"
            >
              <el-row :gutter="20">
                <el-col :span="6">
                  <el-form-item
                    label="条款开始时间"
                    :rules="[{ required: true, message: '请选择条款开始时间', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].startLeaseDate`"
                  >
                    <el-date-picker
                      type="date"
                      placeholder="选择开始时间"
                      v-model="item.startLeaseDate"
                      style="width: 100%"
                      value-format="YYYY-MM-DD"
                      format="YYYY-MM-DD"
                      @change="change_isEdit_wy"
                      :disabled-date="disabledstartDate_step2"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="条款结束时间"
                    :rules="[{ required: true, message: '请选择条款结束时间', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].endLeaseDate`"
                  >
                    <el-date-picker
                      type="date"
                      placeholder="选择结束时间"
                      v-model="item.endLeaseDate"
                      style="width: 100%"
                      value-format="YYYY-MM-DD"
                      format="YYYY-MM-DD"
                      @change="change_isEdit_wy"
                      :disabled-date="disabledendDate_step2"
                      @click="changeTimeIndex = index"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="合同单价"
                    :rules="[{ required: true, message: '请输入合同单价', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].dMoney`"
                  >
                    <el-input
                      v-model="item.dMoney"
                      type="number"
                      min="0"
                      placeholder="请输入合同单价"
                      @change="change_isEdit_wy"
                    >
                      <template #append>
                        <el-select
                          v-model="item.contractUnitPrice"
                          style="width: 100px"
                          @change="change_isEdit_wy"
                        >
                          <el-option
                            v-for="item in contractUnitPriceList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.sort"
                          />
                        </el-select>
                      </template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="付款时间"
                    :rules="[{ required: true, message: '请输入付款时间', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].payDay`"
                  >
                    <el-input
                      v-model="item.payDay"
                      type="number"
                      min="0"
                      placeholder="请输入付款时间"
                      @change="change_isEdit_wy"
                    >
                      <template #prepend>提前</template>
                      <template #append>自然日</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="付款周期(几月一付)"
                    :rules="[{ required: true, message: '请输入付款周期', trigger: 'blur' }]"
                    :prop="`multipleClause[${index}].day`"
                  >
                    <el-input
                      type="number"
                      min="0"
                      v-model="item.day"
                      placeholder="请输入付款周期"
                      @change="change_isEdit_wy"
                    >
                      <template #append>月</template>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </ContentWrapBorder>
          </el-form>
          <!-- 增加租凭条款或物业条款 -->
          <ContentWrap
            class="flex flex-justify-center gap-4 flex-justify-center my-5 text-[14px] cursor-pointer user-select-none"
            @click="plus_list"
          >
            <Icon icon="ep:plus" class="transform-translate-y-[1px]" />
            {{ row_change_index == 1 ? '租凭条款' : '物业条款' }}
          </ContentWrap>
          <ContentWrap
            class="flex flex-justify-center gap-4 flex-justify-center my-5 text-[14px] cursor-pointer user-select-none"
            @click="get_reportDetail"
          >
            <Icon icon="ep:plus" class="transform-translate-y-[1px]" />
            {{ row_change_index == 1 ? '生成租凭合同账单' : '生成物业合同账单' }}
          </ContentWrap>
          <ContentWrapBorder :header_data="'备注'" v-if="row_change_index == 1" class="mb-[18px]">
            <el-input v-model="row_change_data.render.remarkClause" type="textarea" />
          </ContentWrapBorder>
          <ContentWrapBorder :header_data="'备注'" v-if="row_change_index == 2" class="mb-[18px]">
            <el-input v-model="row_change_data.property.remarkClause" type="textarea" />
          </ContentWrapBorder>
        </div>
      </div>
      <!-- 租凭合同账单明细 -->
      <div class="flex items-center justify-between py-4 bg-#fff px-4 text-16px">
        <span class="pl-3 border-l-solid border-l-#2681F3 border-l-4px">保证金</span>
        <span
          class="flex items-center gap-2"
          @click="getOrderBillList_bzj_show = !getOrderBillList_bzj_show"
          ><Icon :icon="!getOrderBillList_bzj_show ? 'ep:arrow-down' : 'ep:arrow-up'" />{{
            getOrderBillList_bzj_show ? '收起' : '展开'
          }}</span
        ></div
      >
      <el-table
        :data="row_change_index == 1 ? getOrderBillList_1_list_bzj : getOrderBillList_2_list_bzj"
        v-show="getOrderBillList_bzj_show"
      >
        <el-table-column align="center" label="还款开始时间" prop="payStartDate" />
        <el-table-column align="center" label="还款结束时间" prop="payEndDate" />
        <el-table-column align="center" label="税额" prop="taxAmount" />
        <el-table-column align="center" label="应收金额" prop="receivable">
          <template #default="{ row }">
            <span>{{ row.receivable + '元' }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="最终单价" prop="finalUnitPrice">
          <template #default>
            <span>--</span>
          </template>
        </el-table-column>
      </el-table>
      <div
        class="flex items-center justify-between py-4 bg-#fff px-4 text-16px mt-18px"
        :class="getOrderBillList_zj_show ? '' : 'mb-10'"
      >
        <span class="pl-3 border-l-solid border-l-#2681F3 border-l-4px">
          {{ row_change_index == 1 ? '租金' : '物业费' }}
        </span>
        <span
          class="flex items-center gap-2"
          @click="getOrderBillList_zj_show = !getOrderBillList_zj_show"
          ><Icon :icon="!getOrderBillList_bzj_show ? 'ep:arrow-down' : 'ep:arrow-up'" />{{
            getOrderBillList_zj_show ? '收起' : '展开'
          }}</span
        ></div
      >
      <el-table
        :data="row_change_index == 1 ? getOrderBillList_1_list : getOrderBillList_2_list"
        class="mb-10"
        v-show="getOrderBillList_zj_show"
      >
        <el-table-column align="center" label="还款开始时间" prop="payStartDate" />
        <el-table-column align="center" label="还款结束时间" prop="payEndDate" />
        <el-table-column align="center" label="税额" prop="taxAmount" />
        <el-table-column align="center" label="应收金额" prop="receivable">
          <template #default="{ row }">
            <span>{{ row.receivable + '元' }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="最终单价" prop="finalUnitPrice">
          <template #default="{ row }">
            <span
              >{{ row.finalUnitPrice }}
              {{ contractUnitPriceList.find((item) => item.sort == row.contractUnitPrice)?.label }}
            </span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- ///////第三步 -->
    <div v-if="step_select_index == 3" class="mb-5">
      <ContentWrapBorder :header_data="'生成合同文本'">
        <div class="flex">
          <div class="flex-1 flex items-center gap-18px">
            <el-button :loading="pdf_loading" @click="creat_contract" type="primary"
              >生成合同</el-button
            >
            <div @click="down_pdf" class="text-14px cursor-pointer" v-if="pdf_list">
              {{ form.contractNumber + '文本合同.pdf' }}
            </div>
          </div>
          <div class="flex" v-if="pdf_list">
            <el-button @click="down_pdf" type="primary" plain>下载</el-button>
            <el-button @click="delete_pdf" type="danger" plain>删除</el-button>
          </div>
        </div>
      </ContentWrapBorder>
      <ContentWrap class="mt-[18px]">
        <div class="flex flex-justify-between flex-align-center border-b-[#999] border-b-[2px]">
          <div class="text-16px">附件列表</div>
          <el-button type="primary" @click="file_upLoad_show">上传文件</el-button>
        </div>
        <el-table :data="fileData_list">
          <el-table-column align="center" label="文件名" prop="name" />
          <el-table-column align="center" label="操作时间" prop="createTime" />
          <el-table-column align="center" label="文件地址" width="600px" prop="filePath" />
          <el-table-column align="center" label="操作" prop="caozuo">
            <template #default="scope">
              <el-button type="danger" plain size="mini" @click="down_loadFile(scope)"
                >下载</el-button
              >
              <el-button type="primary" plain size="mini">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </ContentWrap>
    </div>
    <!-- 第四步,只在变更合同时出现 -->
    <div v-if="step_select_index == 4">
      <el-card shadow="never">
        <template #header>
          <span class="text-16px"
            >账单结算
            <el-tooltip
              class="box-item"
              effect="dark"
              content="退租后的账单将自动关闭，计费周期外的已结清账单不在此处显示"
              placement="top"
            >
              <Icon
                icon="fa:question-circle-o"
                class="ml-4px transform-translate-y-1px"
                color="#999999"
              />
            </el-tooltip>
          </span>
        </template>
        <el-table :data="check_list_change_data">
          <el-table-column label="费用类型" prop="costTypeName">
            <template #default="{ row }">
              <div class="flex items-center">
                <span>{{ row.costTypeName }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="计费周期" prop="id">
            <template #default="{ row }">
              <span>{{ row.payStartDateStr }} ~ {{ row.payEndDateStr }}</span>
            </template>
          </el-table-column>
          <el-table-column label="账单金额" prop="receivable" />
          <el-table-column label="应收/付金额" prop="receivables">
            <template #default="{ row }">
              <el-input v-model="row.receivables" type="number" :disabled="row.disabled" />
            </template>
          </el-table-column>
          <el-table-column label="实收/付金额" prop="receivablePayableAmount" />
          <el-table-column label="需收/付金额" prop="receivable">
            <template #default="{ row }">
              <div class="flex text-center gap-1">
                <el-tag type="success">
                  {{ row.billType == 1 ? '收款' : '付款' }}
                </el-tag>
                <span>{{ row.receivable }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="payDate">
            <template #header>
              应收/付日期
              <el-tooltip
                content="更改应付日期影响到滞纳金计算，请妥善操作；当涉及退款时，请合理设置应收/付日期"
                placement="top"
                ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
              /></el-tooltip>
            </template>
            <template #default="{ row }">
              <el-date-picker
                v-model="row.payDate"
                type="date"
                placeholder="请选择应收/付日期"
                style="width: 100%"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                :disabled="typeValue == 'view'"
              />
            </template>
          </el-table-column>
          <template #append>
            <div class="flex justify-end py-3 bg-[#FAFAFA] pr-4">
              合计需收:{{ check_list_total }}元
            </div>
          </template>
        </el-table>
      </el-card>
      <el-card shadow="never" class="my-18px">
        <template #header>
          <span class="text-16px">保证金 </span>
        </template>
        <el-table :data="security_list_change_data">
          <el-table-column label="费用类型" prop="costTypeName">
            <template #default="{ row }">
              <div class="flex items-center">
                <span>{{ row.costTypeName }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="计费周期" prop="id">
            <template #default="{ row }">
              <span>{{ row.payStartDateStr }} ~ {{ row.payEndDateStr }}</span>
            </template>
          </el-table-column>
          <el-table-column label="账单金额" prop="receivable" />
          <el-table-column label="应收/付金额" prop="receivables">
            <template #default="{ row }">
              <el-input v-model="row.receivables" type="number" :disabled="row.disabled" />
            </template>
          </el-table-column>
          <el-table-column label="实收/付金额" prop="receivablePayableAmount" />
          <el-table-column label="需收/付金额" prop="receivable">
            <template #default="{ row }">
              <div class="flex text-center gap-1">
                <el-tag type="success">
                  {{ row.billType == 1 ? '收款' : '付款' }}
                </el-tag>
                <span>{{ row.receivable }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="payDate">
            <template #header>
              应收/付日期
              <el-tooltip
                content="更改应付日期影响到滞纳金计算，请妥善操作；当涉及退款时，请合理设置应收/付日期"
                placement="top"
                ><Icon class="" icon="fa:question-circle-o" :size="12" color="#999"
              /></el-tooltip>
            </template>
            <template #default="{ row }">
              <el-date-picker
                v-model="row.payDate"
                type="date"
                placeholder="请选择应收/付日期"
                style="width: 100%"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                :disabled="typeValue == 'view'"
              />
            </template>
          </el-table-column>
          <template #append>
            <div class="flex justify-end py-3 bg-[#FAFAFA] pr-4">
              合计需收:{{ security_list_total }}元
            </div>
          </template>
        </el-table>
      </el-card>
    </div>
    <!-- //底部审核按钮 -->
    <div class="flex flex-justify-end">
      <el-button
        type="primary"
        plain
        @click="stepLast(step_select_index)"
        v-if="step_select_index != 1"
        >上一步</el-button
      >
      <el-button
        type="primary"
        @click="stepNext(step_select_index)"
        v-if="step_select_index != stepList.length"
        >下一步</el-button
      >
      <el-button
        type="primary"
        @click="submitForm"
        v-show="step_select_index == stepList.length"
        v-if="detail_open_type == '' || detail_open_type == 'copy' || detail_open_type == 'editor'"
        >{{ detail_open_type == 'editor' ? '更改' : '保存' }}</el-button
      >
      <el-button
        type="primary"
        @click="submitForm('examine')"
        v-if="step_select_index == stepList.length"
        >{{ detail_open_type == 'editor' ? '更改并审核' : '保存并审核' }}</el-button
      >
    </div>
    <!-- 上传文件 -->
    <el-drawer
      v-model="show_upLoad_drawer"
      title="上传文件"
      :closed="close_upLoad_drawer"
      size="440"
    >
      <el-form label-position="top">
        <el-form-item
          label="上传附件"
          :rules="[
            {
              required: true,
              message: '请上传附件',
              trigger: 'blur'
            }
          ]"
        >
          <el-upload :auto-upload="false" @change="file_change" :file-list="fileList">
            <div class="upload el-upload__text">
              <img src="./component/image/upload_img.png" class="File_img" />
              文件支持doc,docx,pdf,jpg,jpeg,png</div
            >
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="close_upLoad_drawer" plain>取消</el-button>
        <el-button type="primary" @click="submit_file">确定</el-button>
      </template>
    </el-drawer>
    <!-- 选择流程示例编号 -->
    <el-dialog v-model="show_select_flow" title="流程示例" width="500" :show-close="false">
      <el-form>
        <el-form-item>
          <el-select v-model="contractNumber_flow">
            <el-option
              v-for="(item, index) in contractNumber_flow_options"
              :key="index"
              :label="item.name"
              :value="item.key"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submit_closed"> 取消 </el-button>
          <el-button type="primary" @click="submit_contract_flow('examine')"> 确认 </el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 添加租客弹窗 -->
    <AddForm ref="FormRef" @success="getOwnerList('')" />
  </div>
</template>
<script setup lang="ts">
import { Api } from '@/api/contract/contractList/index'
import ContentWrapBorder from '@/views/bus/owner/component/ContentWrap_border.vue'
import { onMounted, render, ref } from 'vue'
import DeptTreeSelect from './component/DeptTree_select.vue'
import { OwnerApi } from '@/api/bus/owner/index.ts'
import { ElMessageBox } from 'element-plus'
import AddForm from '@/views/bus/owner/component/addFom.vue'
import en from 'element-plus/es/locale/lang/en'
import { formatDate } from '@/utils/formatTime'

const message = useMessage()
let step_select_index = ref(1)
const stepList = ref(['基本信息', '费用条款', '合同文本'])
const form = ref({
  contractNumber: '',
  operatorId: '',
  signingDate: '',
  contractStartTime: '',
  contractEndTime: '',
  unitPricePoint: '',
  calculationOrder: '',
  isReceivableInteger: '',
  calculationAccuracy: '0'
})
//限制选择合同开始合同结束时间
const disabledstartDate = (time) => {
  let endDate = ''
  const selectedDate = new Date(time)
  if (form.value.contractEndTime) {
    endDate = new Date(form.value.contractEndTime)
  }
  if (form.value.contractEndTime) {
    return selectedDate >= endDate
  }
}
const disabledendDate = (time) => {
  let oneMonthAfter = new Date(form.value.contractStartTime)
  const selectedDate = new Date(time)
  if (form.value.contractStartTime) {
    const startDate = new Date(form.value.contractStartTime)
    oneMonthAfter.setMonth(startDate.getMonth() + 1)
    oneMonthAfter.setDate(oneMonthAfter.getDate() - 2)
  }
  if (form.value.contractStartTime) {
    return selectedDate <= oneMonthAfter
  }
}
//生成条款时间限制
const changeTimeIndex = ref(0)
const disabledstartDate_step2 = (time) => {
  let endDate = ''
  let startDate = ''
  const selectedDate = new Date(time)
  if (form.value.contractEndTime) {
    endDate = new Date(form.value.contractEndTime)
    endDate.setDate(endDate.getDate() + 1)
  }
  if (form.value.contractStartTime) {
    startDate = new Date(form.value.contractStartTime)
    startDate.setDate(startDate.getDate() - 1)
  }
  let status = selectedDate < endDate && selectedDate > startDate
  return !status
}
const disabledendDate_step2 = (time) => {
  let startTime = ''
  let endDate = ''
  if (row_change_index.value == 1) {
    startTime = new Date(
      row_change_data.value.render.multipleClause[changeTimeIndex.value].startLeaseDate
    )
  } else {
    startTime = new Date(
      row_change_data.value.property.multipleClause[changeTimeIndex.value].startLeaseDate
    )
  }
  startTime.setMonth(startTime.getMonth() + 1)
  startTime.setDate(startTime.getDate() - 2)
  endDate = new Date(form.value.contractEndTime)
  const selectedDate = new Date(time)
  let status = selectedDate < endDate && selectedDate > startTime
  return !status
}
////////变更合同获取数据
const check_list_change_data = ref([])
const check_list_total = ref(0)
const Getcheck_list_change_data = (id: string) => {
  Api.getByContractIdRentingTerminationBillList(id).then((res) => {
    check_list_change_data.value = res
    check_list_total.value = res.reduce((total, item) => {
      item.billStatus = 1
      item.billType = 2
      item.payTime = formatDate(new Date())
      return Number(total) + Number(item.receivable)
    }, 0)
  })
}
const security_list_change_data = ref([])
const security_list_total = ref(0)
const Getsecurity_list_change_data = (id: string) => {
  Api.getByContractIdRentingTerminationBondBillList(id).then((res) => {
    security_list_change_data.value = res
    security_list_total.value = res.reduce((total, item) => {
      return Number(total) + Number(item.receivable)
    }, 0)
  })
}
//获取经办人
const getoperatorIdList = ref({
  id: '',
  name: ''
})
const getoperatorId = async () => {
  getoperatorIdList.value = await Api.getTenantIdUserInfoList()
}
//获取租客列表
const ownerIdList = ref({
  id: '',
  nickname: ''
})
const getOwnerList = async (query: string) => {
  OwnerApi.getOwnerListByName(query).then((res) => {
    ownerIdList.value = res
  })
}
const ownerId_change = (val) => {
  if (!val) return
  ownerIdList.value.forEach((item) => {
    if (item.id == val) {
      form.value.signedName = item.name
    }
  })
}
//获得行业列表
const industryList = ref({
  id: '',
  name: ''
})
const getIndustryList = async () => {
  OwnerApi.getTagIndustryList('').then((res) => {
    industryList.value = res
  })
}
//获取计算顺序
const calculationOrderList = ref({
  value: '',
  label: ''
})
const getcalculationOrder = async () => {
  Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'CALCULATION_ORDER'
  }).then((res) => {
    calculationOrderList.value = res.list
  })
}
//是否四舍五入
const isReceivableIntegerList = ref({
  value: '',
  label: ''
})
const getisReceivableInteger = async () => {
  Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'IS_RECEIVABLE_INTEGER'
  }).then((res) => {
    isReceivableIntegerList.value = res.list
  })
}
//保证金类型
const bzjTypeList = ref({
  value: '',
  label: ''
})
const getbzjType = async () => {
  Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'bond_type'
  }).then((res) => {
    bzjTypeList.value = res.list
  })
}
//单价含税
const unitPriceTaxList = ref({
  value: '',
  label: ''
})
const getunitPriceTax = async () => {
  Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'TAX_INCLUSIVE_RULES'
  }).then((res) => {
    unitPriceTaxList.value = res.list
  })
}
//合同单价单位
const contractUnitPriceList = ref()
const getcontractUnitPriceList = () => {
  Api.getProject({
    pageNo: 1,
    pageSize: 100,
    dictType: 'CONTRACT_UNIT_PRICE'
  }).then((res) => {
    contractUnitPriceList.value = res.list
  })
}
//选择楼宇数据
const DeptTreeSelect_data_list = ref([])
const orgList = ref([])
const DeptTreeSelect_data = (data: any, leaseArea: number, orgList_tree) => {
  DeptTreeSelect_data_list.value = data
  form.value.leaseArea = leaseArea
  orgList.value = orgList_tree
}
//下一步
//基本信息页
const addForm_zkxx = ref()
const addForm_jbxx = ref()
const addForm_znj = ref()
//费用条款页
const addForm_zpbzj_1 = ref()
const addForm_zpbzj_2 = ref()
const addForm_sl_1 = ref()
const addForm_sl_2 = ref()
const addForm_zptk_list = ref()
const addForm_wytk_list = ref()
const stepLast = async (index: number) => {
  if (index == 2) {
    step_select_index.value--
    setTimeout(() => {
      DeptTreeSelect_ref.value.getDeptTreeSelect(DeptTreeSelect_data_list.value)
    }, 50)
  } else if (index == 3) {
    step_select_index.value--
  } else {
    step_select_index.value--
  }
}
const stepNext = async (index: number) => {
  if (index == 1) {
    let addForm_zkxx_value = ''
    addForm_zkxx.value.validate((valid) => {
      addForm_zkxx_value = valid
    })
    let addForm_jbxx_value = ''
    addForm_jbxx.value.validate((valid) => {
      addForm_jbxx_value = valid
    })
    let addForm_znj_value = ''
    addForm_znj.value.validate((valid) => {
      addForm_znj_value = valid
    })
    setTimeout(() => {
      if (addForm_zkxx.value && addForm_jbxx.value && addForm_znj.value) {
        if (addForm_zkxx_value && addForm_jbxx_value && addForm_znj_value) {
          if (DeptTreeSelect_data_list.value.length == 0) {
            message.error('请选择房源')
            return
          }
          step_select_index.value = index + 1
        }
      }
    }, 100)
  } else if (index == 2) {
    ///////校验是否更改过费用条款
    if (
      isEdit_zl.value &&
      getOrderBillList_1_list.value.length > 0 &&
      getOrderBillList_1_list_bzj.value.length > 0
    ) {
      message.error('请重新生成租凭条款账单明细')
      pdf_list.value = ''
      return
    }
    if (
      isEdit_wy.value &&
      getOrderBillList_2_list.value.length > 0 &&
      getOrderBillList_2_list_bzj.value.length > 0
    ) {
      message.error('请重新生成物业条款账单明细')
      pdf_list.value = ''
      return
    }

    // 租凭条款校验
    let addForm_zpbzj_1_value = ''
    await addForm_zpbzj_1.value.validate(async (valid) => {
      addForm_zpbzj_1_value = valid
    })
    let addForm_sl_1_value = ''
    await addForm_sl_1.value.validate(async (valid) => {
      addForm_sl_1_value = valid
    })

    if (!addForm_zpbzj_1_value && !addForm_sl_1_value) {
      row_change_index.value = 1
      message.error('请填写完整租凭条款信息')
      return
    }
    //物业条款校验
    let addForm_zpbzj_2_value = ''
    await addForm_zpbzj_2.value.validate((valid) => {
      addForm_zpbzj_2_value = valid
    })
    let addForm_sl_2_value = ''
    await addForm_sl_2.value.validate((valid) => {
      addForm_sl_2_value = valid
    })
    if (!addForm_zpbzj_2_value && !addForm_sl_2_value) {
      row_change_index.value = 2
      message.error('请填写完整物业条款信息')
      return
    }
    //租凭条款校验
    if (now_getOrderBillList_1_index.value < row_change_data.value.render.multipleClause.length) {
      message.error('请生成租凭条款账单明细')
      row_change_index.value = 1
      return
    }
    if (now_getOrderBillList_2_index.value < row_change_data.value.property.multipleClause.length) {
      message.error('请生成物业条款账单明细')
      row_change_index.value = 2

      return
    }
    step_select_index.value = index + 1
  } else if (index == 3) {
    if (pdf_list.value == '') {
      message.error('请生成合同文本')
      return
    }
    step_select_index.value = index + 1
  }
}
//费用条款切换
const row_change_index = ref(1)
const row_change_list = ref(['租凭条款', '物业条款'])

//费用条款绑定数据
const row_change_data = ref({
  render: {
    bondClause: {
      bondType: 'bond_2',
      bondPrice: ''
    },
    taxClause: {
      taxRule: 0,
      taxRate: '0'
    },
    multipleClause: [
      {
        startLeaseDate: '', //租凭开始时间
        endLeaseDate: '', //租凭结束时间
        dMoney: '', //单价
        payDay: '', //付款时间
        day: '', //付款周期
        yearDays: '', //年天数
        contractUnitPrice: 0
      }
    ],
    remarkClause: '' //备注
  },
  property: {
    bondClause: {
      bondType: 'bond_3',
      bondPrice: ''
    },
    taxClause: {
      taxRule: 0,
      taxRate: '0'
    },
    multipleClause: [
      {
        startLeaseDate: '', //租凭开始时间
        endLeaseDate: '', //租凭结束时间
        dMoney: '', //单价
        payDay: '', //付款时间
        day: '', //付款周期
        yearDays: '', //年天数
        contractUnitPrice: 0
      }
    ],
    remarkClause: '' //备注
  }
})

const plus_list = async () => {
  if (row_change_index.value == 1) {
    //校验当前租凭条款是否填写完整
    await addForm_zptk_list.value.validate((valid) => {
      if (!valid) {
        message.error('请填写完整租凭条款信息并生成账单明细')
        return
      }
    })

    //校验是否生成账单明细
    if (now_getOrderBillList_1_index.value < row_change_data.value.render.multipleClause.length) {
      message.error('请生成账单明细')
      return
    }
    row_change_data.value.render.multipleClause.push({
      startLeaseDate: '', //租凭开始时间
      endLeaseDate: '', //租凭结束时间
      dMoney: '', //单价
      payDay: '', //付款时间
      day: '', //付款周期
      yearDays: '', //年天数
      contractUnitPrice: 0
    })
  } else if (row_change_index.value == 2) {
    if (now_getOrderBillList_2_index.value < row_change_data.value.property.multipleClause.length) {
      message.error('请填写物业条款并生成账单明细')
      return
    }
    row_change_data.value.property.multipleClause.push({
      startLeaseDate: '', //租凭开始时间
      endLeaseDate: '', //租凭结束时间
      dMoney: '', //单价
      payDay: '', //付款时间
      day: '', //付款周期
      yearDays: '', //年天数
      contractUnitPrice: 0
    })
  }
}
//提交创建合同
const submitForm = (type) => {
  if (pdf_list.value == '') {
    message.error('请生成合同文本')
    return
  }
  if (type == 'examine') {
    show_select_flow.value = true
  } else {
    const buildId = DeptTreeSelect_data_list.value[0].buildId
    const roomNumber = DeptTreeSelect_data_list.value.map((item) => item.roomName).join(',')
    let parkId = ''

    if (detail_open_type.value == 'copy' || detail_open_type.value == 'Stayover') {
      parkId = form.value.parkId
    } else {
      if (DeptTreeSelect_data_list.value[0].villageId) {
        parkId = DeptTreeSelect_data_list.value[0].villageId
      } else {
        parkId = DeptTreeSelect_data_list.value[0].parkId
      }
    }

    fileData_list.value.forEach((item) => {
      item.annexSource = '1'
    })
    const clauseTypes_list = [
      {
        clauseType: '1',
        ...row_change_data.value.render,
        reportDetail: []
      },
      {
        clauseType: '2',
        ...row_change_data.value.property,
        reportDetail: []
      }
    ]
    //合并账单明细
    getOrderBillList_1_list_bzj.value.forEach((item) => {
      clauseTypes_list[0].reportDetail.push(item)
    })
    getOrderBillList_2_list_bzj.value.forEach((item) => {
      clauseTypes_list[1].reportDetail.push(item)
    })
    getOrderBillList_1_list.value.forEach((item) => {
      clauseTypes_list[0].reportDetail.push(item)
    })
    getOrderBillList_2_list.value.forEach((item) => {
      clauseTypes_list[1].reportDetail.push(item)
    })
    const checkedBuild = []
    DeptTreeSelect_data_list.value.forEach((item) => {
      checkedBuild.push({
        id: item.id,
        parkId: item.villageId || item.parkId,
        contractId: '',
        buildId: item.buildId,
        villageRoomId: item.roomName,
        villageName: item.villageName,
        roomName: item.roomName,
        roomId: item.roomNumber,
        roomNumber: item.roomNumber,
        rentalArea: item.rentalArea,
        buildName: item.buildName,
        layerName: item.layerName,
        level_key: item.level_key,
        level1_key: item.level1_key,
        level2_key: item.level2_key
      })
    })
    //创建合同状态
    let status = ''
    if (type == 'examine') {
      status = '16'
    } else {
      status = '0'
    }

    if (detail_open_type.value == '' || detail_open_type.value == 'copy') {
      setTimeout(() => {
        Api.createContract({
          ...form.value,
          ownerId: form.value.ownerId,
          buildId: Number(buildId),
          roomNumber: roomNumber,
          clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
          checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合',
          contractAnnex: JSON.stringify(fileData_list.value), //文件集合
          parkId: parkId,
          calculationAccuracy: '0',
          pdfFileUrl: pdf_list.value,
          status: status
        }).then((res) => {
          message.success('创建成功')
          setTimeout(() => {
            emit('close')
            emit('getList')
          }, 2000)
        })
      }, 1000)
    } else if (detail_open_type.value == 'Stayover') {
      Api.renewalLease({
        ...form.value,
        ownerId: form.value.ownerId,
        buildId: Number(buildId),
        roomNumber: roomNumber,
        clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
        checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合
        contractAnnex: JSON.stringify(fileData_list.value), //文件集合
        parkId: parkId,
        calculationAccuracy: '0',
        id: form.value.id,
        pdfFileUrl: pdf_list.value
      }).then((res) => {
        category_type.value = 'contractAdd'
        getcontractNumber_flow_options()
        show_select_flow.value = true
        contract_id.value = res
      })
    } else if (detail_open_type.value == 'editor') {
      Api.updateContrat({
        ...form.value,
        ownerId: form.value.ownerId,
        buildId: Number(buildId),
        roomNumber: roomNumber,
        clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
        checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合
        contractAnnex: JSON.stringify(fileData_list.value), //文件集合
        parkId: parkId,
        calculationAccuracy: '0',
        id: form.value.id,
        pdfFileUrl: pdf_list.value,
        status: status
      }).then((res) => {
        message.success('编辑成功')
        setTimeout(() => {
          emit('close')
          emit('getList')
        }, 2000)
      })
    } else if (detail_open_type.value == 'change') {
      Api.contractchangeSubmit({
        ...form.value,
        ownerId: form.value.ownerId,
        buildId: Number(buildId),
        roomNumber: roomNumber,
        clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
        checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合
        contractAnnex: JSON.stringify(fileData_list.value), //文件集合
        parkId: parkId,
        calculationAccuracy: '0',
        id: form.value.id,
        pdfFileUrl: pdf_list.value,
        status: status
      }).then((res) => {
        message.success('变更成功')
        setTimeout(() => {
          emit('close')
          emit('getList')
        }, 2000)
      })
    }
  }
}
//生成账单明细列表
const getOrderBillList_1_list = ref([])
const getOrderBillList_2_list = ref([])
//生成账单保证金列表
const getOrderBillList_1_list_bzj = ref([])
const getOrderBillList_2_list_bzj = ref([])
//生成账单明细次数
let now_getOrderBillList_1_index = ref(0)
let now_getOrderBillList_2_index = ref(0)
//是否展示列表
const getOrderBillList_bzj_show = ref(true)
const getOrderBillList_zj_show = ref(true)
//是否已经编辑过生成的账单明细
const isEdit_zl = ref(false)
const isEdit_wy = ref(false)
const change_isEdit_zl = () => {
  isEdit_zl.value = true
}
const change_isEdit_wy = () => {
  isEdit_wy.value = true
}
///获取账单明细
const get_reportDetail = async () => {
  if (row_change_index.value == 1) {
    //校验租客条款是否填写完整
    await addForm_zptk_list.value.validate((valid) => {
      if (valid) {
        getOrderBillList_1_list.value = []
        getOrderBillList_1_list_bzj.value = []
        row_change_data.value.render.multipleClause.forEach((item: any, index: number) => {
          Api.getOrderBillList({
            count: getOrderBillList_1_list.value.length + 1,
            termType: '1',
            feeType: '1',
            leaseStartDate: item.startLeaseDate,
            leaseEndDate: item.endLeaseDate,
            day: item.day,
            bondMoney: row_change_data.value.render.bondClause.bondPrice,
            payDay: item.payDay,
            unitPricePoint: form.value.unitPricePoint,
            calculationOrder: form.value.calculationOrder,
            taxInclusiveRules: row_change_data.value.render.taxClause.taxRule,
            taxInclusiveValue:
              row_change_data.value.render.taxClause.taxRule == 1
                ? '0'
                : row_change_data.value.render.taxClause.taxRate,
            totalArea: form.value.leaseArea,
            dMoney: item.dMoney,
            isReceivableInteger: form.value.isReceivableInteger,
            contractUnitPrice: item.contractUnitPrice
          }).then(async (res) => {
            message.success('生成成功')
            isEdit_zl.value = false
            await res.dataList.forEach((item) => {
              // getOrderBillList_1_list.value.push(item)
              item.contractUnitPrice = res.contractUnitPrice
            })
            if (index > 0) {
              setTimeout(() => {
                getOrderBillList_1_list_bzj.value.push(res.dataList[0])
                delete res.dataList[0]
                res.dataList.forEach((item) => {
                  getOrderBillList_1_list.value.push(item)
                })
                now_getOrderBillList_1_index.value++
              }, index * 50)
            } else {
              getOrderBillList_1_list_bzj.value.push(res.dataList[0])
              delete res.dataList[0]
              getOrderBillList_1_list.value = res.dataList
              now_getOrderBillList_1_index.value =
                row_change_data.value.render.multipleClause.length
            }
          })
        })
      } else {
        message.error('请填写完整租凭条款信息')
        return
      }
    })
  } else {
    //校验物业条款是否填写完整
    await addForm_wytk_list.value.validate((valid) => {
      if (valid) {
        getOrderBillList_2_list.value = []
        getOrderBillList_2_list_bzj.value = []
        row_change_data.value.property.multipleClause.forEach((item: any, index: number) => {
          Api.getOrderBillList({
            count: getOrderBillList_2_list.value.length + 1,
            termType: '2',
            feeType: '1',
            leaseStartDate: item.startLeaseDate,
            leaseEndDate: item.endLeaseDate,
            day: item.day,
            bondMoney: row_change_data.value.property.bondClause.bondPrice,
            payDay: item.payDay,
            unitPricePoint: form.value.unitPricePoint,
            calculationOrder: form.value.calculationOrder,
            taxInclusiveRules: row_change_data.value.property.taxClause.taxRule,
            taxInclusiveValue:
              row_change_data.value.property.taxClause.taxRule == 1
                ? '0'
                : row_change_data.value.property.taxClause.taxRate,
            totalArea: form.value.leaseArea,
            dMoney: item.dMoney,
            isReceivableInteger: form.value.isReceivableInteger,
            contractUnitPrice: item.contractUnitPrice
          }).then(async (res) => {
            message.success('生成成功')
            isEdit_wy.value = false
            await res.dataList.forEach((item) => {
              item.contractUnitPrice = res.contractUnitPrice
            })
            if (index > 0) {
              setTimeout(() => {
                getOrderBillList_2_list_bzj.value.push(res.dataList[0])
                delete res.dataList[0]
                res.dataList.forEach((item) => {
                  getOrderBillList_2_list.value.push(item)
                })
                now_getOrderBillList_2_index.value++
              }, index * 50)
            } else {
              getOrderBillList_2_list_bzj.value.push(res.dataList[0])
              delete res.dataList[0]
              getOrderBillList_2_list.value = res.dataList
              now_getOrderBillList_2_index.value =
                row_change_data.value.property.multipleClause.length
            }
          })
        })
      } else {
        message.error('请填写完整物业条款信息')
        return
      }
    })
  }
}
//上传文件列表
const show_upLoad_drawer = ref(false)
const fileData_list = ref([])
const fileList = ref([])
const file_upLoad_show = () => {
  show_upLoad_drawer.value = true
}
const close_upLoad_drawer = () => {
  show_upLoad_drawer.value = false
}
const file_change = (file: any) => {
  nowFile.value = file
  console.log(file, 'file')
}
let nowFile = ref([])
const submit_file = () => {
  if (nowFile.value.length == 0) {
    message.error('请上传文件')
    return
  }
  OwnerApi.uploadFile({
    file: nowFile.value.raw
  }).then((res) => {
    fileData_list.value.push({
      name: nowFile.value.name,
      createTime: new Date().toLocaleDateString(),
      filePath: res.data
    })
    show_upLoad_drawer.value = false
    message.success('上传成功')
  })
}
const down_loadFile = async (row: any) => {
  try {
    const response = await fetch(row.filePath)
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    const blob = await response.blob()
    const url = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = row.name // 设置下载文件名
    document.body.appendChild(a) // 将链接添加到文档中
    a.click() // 模拟点击链接
    a.remove() // 点击后移除链接
    window.URL.revokeObjectURL(url) // 释放 URL 对象
  } catch (error) {
    console.error('下载文件时发生错误:', error)
  }
}
//复制功能回显树状图
const DeptTreeSelect_ref = ref()
//复制功能
const detail_open_type = ref('')
const change_room = ref(false)
const detail_open = async (id?: string, type?: string, time?: any, changeRoom?: boolean) => {
  /**
   * copy 复制
   * Stayover 续租
   * change 变更
   */
  detail_open_type.value = type
  Api.contractgetId(id).then((res) => {
    //step1
    form.value = res
    form.value.calculationAccuracy = '0'
    form.value.industry = Number(form.value.industry)
    //step2
    let clauseTypes = JSON.parse(res.clauseTypes)
    row_change_data.value.render.bondClause = JSON.parse(clauseTypes[0].bondClause)
    row_change_data.value.render.taxClause = JSON.parse(clauseTypes[0].taxClause)
    row_change_data.value.render.multipleClause = JSON.parse(clauseTypes[0].multipleClause)
    row_change_data.value.render.remarkClause = clauseTypes[0].remarkClause
    row_change_data.value.property.bondClause = JSON.parse(clauseTypes[1].bondClause)
    row_change_data.value.property.taxClause = JSON.parse(clauseTypes[1].taxClause)
    row_change_data.value.property.multipleClause = JSON.parse(clauseTypes[1].multipleClause)
    row_change_data.value.property.remarkClause = clauseTypes[1].remarkClause

    //选择的房源信息
    //回显树状选择
    //租凭合同账单明细
    if (type == 'copy') {
      form.value.contractNumber = ''
      delete form.value.id
    } else if (type == 'Stayover') {
      category_type.value = 'contractAdd'
      getcontractNumber_flow_options()
      change_tree_value.value = false
      form.value.contractNumber = ''
      form.value.contractStartTime = time[0]
      form.value.contractEndTime = time[1]
      DeptTreeSelect_data_list.value = JSON.parse(res.checkedBuild)
      DeptTreeSelect_ref.value.getDeptTreeSelect(DeptTreeSelect_data_list.value)
      row_change_data.value.render.multipleClause.forEach((item) => {
        item.startLeaseDate = time[0]
        item.endLeaseDate = time[1]
      })
      row_change_data.value.property.multipleClause.forEach((item) => {
        item.startLeaseDate = time[0]
        item.endLeaseDate = time[1]
      })
    } else if (type == 'editor') {
      change_tree_value.value = false
      DeptTreeSelect_data_list.value = JSON.parse(res.checkedBuild)
      DeptTreeSelect_ref.value.getDeptTreeSelect(DeptTreeSelect_data_list.value)
      pdf_list.value = res.pdfFileUrl
      const clauseTypes = JSON.parse(res.clauseTypes)
      const clauseTypes0 = JSON.parse(clauseTypes[0].reportDetail)
      const clauseTypes1 = JSON.parse(clauseTypes[1].reportDetail)
      clauseTypes0.forEach((item) => {
        if (item.feeType == '1') {
          getOrderBillList_1_list_bzj.value.push(item)
        } else {
          getOrderBillList_1_list.value.push(item)
        }
      })
      clauseTypes1.forEach((item) => {
        if (item.feeType == '1') {
          getOrderBillList_2_list_bzj.value.push(item)
        } else {
          getOrderBillList_2_list.value.push(item)
        }
      })
      now_getOrderBillList_1_index.value = row_change_data.value.render.multipleClause.length
      now_getOrderBillList_2_index.value = row_change_data.value.property.multipleClause.length
      delete form.value.status
    } else if (type == 'change') {
      category_type.value = 'contractUpdate'
      getcontractNumber_flow_options()
      change_room.value = changeRoom
      change_tree_value.value = false
      form.value.contractEndTime = ''
      form.value.contractStartTime = time
      row_change_data.value.render.multipleClause.forEach((item) => {
        item.startLeaseDate = time
        item.endLeaseDate = ''
      })
      row_change_data.value.property.multipleClause.forEach((item) => {
        item.startLeaseDate = time
        item.endLeaseDate = ''
      })
      Getcheck_list_change_data(id)
      Getsecurity_list_change_data(id)
      stepList.value.push('退租结算')
      DeptTreeSelect_data_list.value = JSON.parse(res.checkedBuild)
      DeptTreeSelect_ref.value.getDeptTreeSelect(DeptTreeSelect_data_list.value)
    }
    //step3
    fileData_list.value = JSON.parse(form.value.contractAnnex)
  })
}
defineExpose({ detail_open, addRoomToSelect })
//合同文本
const pdf_list = ref('')
//生成合同文本
const pdf_loading = ref(false)
const creat_contract = () => {
  pdf_loading.value = true
  const buildId = DeptTreeSelect_data_list.value[0].buildId
  const roomNumber = DeptTreeSelect_data_list.value.map((item) => item.roomName).join(',')
  let parkId = ''
  if (detail_open_type.value == 'copy' || detail_open_type.value == 'Stayover') {
    parkId = form.value.parkId
  } else {
    if (DeptTreeSelect_data_list.value[0].villageId) {
      parkId = DeptTreeSelect_data_list.value[0].villageId
    } else {
      parkId = DeptTreeSelect_data_list.value[0].parkId
    }
  }

  fileData_list.value.forEach((item) => {
    item.annexSource = '1'
  })
  const clauseTypes_list = [
    {
      clauseType: '1',
      ...row_change_data.value.render,
      reportDetail: []
    },
    {
      clauseType: '2',
      ...row_change_data.value.property,
      reportDetail: []
    }
  ]
  getOrderBillList_1_list.value.forEach((item) => {
    clauseTypes_list[0].reportDetail.push(item)
  })
  getOrderBillList_2_list.value.forEach((item) => {
    clauseTypes_list[1].reportDetail.push(item)
  })

  const checkedBuild = []
  DeptTreeSelect_data_list.value.forEach((item) => {
    checkedBuild.push({
      parkId: item.villageId || item.parkId,
      buildId: item.buildId,
      villageRoomId: item.roomName,
      roomName: item.roomName,
      rentalArea: item.rentalArea,
      buildName: item.buildName,
      layerName: item.layerName,
      villageName: item.villageName,
      selectId: item.id || item.selectId,
      id: item.id
    })
  })
  message.warning('正在生成合同文本，请稍后...')
  setTimeout(() => {
    Api.createContractPdf({
      ...form.value,
      ownerId: form.value.ownerId,
      buildId: Number(buildId),
      roomNumber: roomNumber,
      clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
      checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合
      contractAnnex: JSON.stringify(fileData_list.value), //文件集合
      parkId: parkId,
      calculationAccuracy: '0'
    })
      .then((res) => {
        pdf_loading.value = false
        message.success('合同文本生成成功')
        pdf_list.value = res
      })
      .catch(() => {
        pdf_loading.value = false
        message.error('合同文本生成失败')
      })
  }, 1500)
}
//删除合同文本
const delete_pdf = () => {
  ElMessageBox.confirm('确认删除合同文本?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    pdf_list.value = ''
  })
}
//下载合同文本
const down_pdf = () => {
  // window.open(pdf_list.value)
  down_loadFile({ filePath: pdf_list.value, name: `${form.value.contractNumber}合同文本.pdf` })
}
//切换选择树
let change_tree_value = ref(true)
const change_tree = (index: number) => {
  if (detail_open_type.value == 'Stayover') {
    message.error('续租合同不可更改房源')
    return
  } else if (detail_open_type.value == 'change' && !change_room.value) {
    message.error('该房间有未支付的账单，不可更改房源')
    return
  }
  change_tree_value.value = !change_tree_value.value
}
const category_type = ref('contractSave')
//选择流程示例编号
const contractNumber_flow_options = ref([])
const contractNumber_flow = ref('')
const show_select_flow = ref(false)
const close_select_flow = () => {
  show_select_flow.value = false
}
const getcontractNumber_flow_options = () => {
  Api.getcontract_flow({
    pageNo: 1,
    pageSize: 10,
    category: category_type.value
  }).then((res) => {
    contractNumber_flow_options.value = res
  })
}
const contract_id = ref('')
const submit_contract_flow = (type) => {
  if (contractNumber_flow.value == '') {
    message.error('请选择流程示例编号')
    return
  }
  const buildId = DeptTreeSelect_data_list.value[0].buildId
  const roomNumber = DeptTreeSelect_data_list.value.map((item) => item.roomName).join(',')
  let parkId = ''
  if (detail_open_type.value == 'copy' || detail_open_type.value == 'Stayover') {
    parkId = form.value.parkId
  } else {
    if (DeptTreeSelect_data_list.value[0].villageId) {
      parkId = DeptTreeSelect_data_list.value[0].villageId
    } else {
      parkId = DeptTreeSelect_data_list.value[0].parkId
    }
  }

  fileData_list.value.forEach((item) => {
    item.annexSource = '1'
  })
  const clauseTypes_list = [
    {
      clauseType: '1',
      ...row_change_data.value.render,
      reportDetail: []
    },
    {
      clauseType: '2',
      ...row_change_data.value.property,
      reportDetail: []
    }
  ]
  //合并账单明细
  getOrderBillList_1_list_bzj.value.forEach((item) => {
    clauseTypes_list[0].reportDetail.push(item)
  })
  getOrderBillList_2_list_bzj.value.forEach((item) => {
    clauseTypes_list[1].reportDetail.push(item)
  })
  getOrderBillList_1_list.value.forEach((item) => {
    clauseTypes_list[0].reportDetail.push(item)
  })
  getOrderBillList_2_list.value.forEach((item) => {
    clauseTypes_list[1].reportDetail.push(item)
  })
  if (check_list_change_data.value.length > 0) {
    check_list_change_data.value.forEach((item) => {
      if (item.feeType == '1') {
        clauseTypes_list[1].reportDetail.push(item)
      } else if (item.feeType == '7') {
        clauseTypes_list[0].reportDetail.push(item)
      }
    })
  }
  const checkedBuild = []
  DeptTreeSelect_data_list.value.forEach((item) => {
    checkedBuild.push({
      id: item.id,
      parkId: item.villageId || item.parkId,
      contractId: '',
      buildId: item.buildId,
      villageRoomId: item.roomName,
      villageName: item.villageName,
      roomName: item.roomName,
      roomId: item.roomNumber,
      roomNumber: item.roomNumber,
      rentalArea: item.rentalArea,
      buildName: item.buildName,
      layerName: item.layerName,
      level_key: item.level_key,
      level1_key: item.level1_key,
      level2_key: item.level2_key
    })
  })
  //创建合同状态
  let status = ''
  if (type == 'examine') {
    status = '16'
  } else {
    status = '0'
  }
  if (detail_open_type.value == '' || detail_open_type.value == 'copy') {
    setTimeout(() => {
      Api.createContract({
        ...form.value,
        ownerId: form.value.ownerId,
        buildId: Number(buildId),
        roomNumber: roomNumber,
        clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
        checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合',
        contractAnnex: JSON.stringify(fileData_list.value), //文件集合
        parkId: parkId,
        calculationAccuracy: '0',
        pdfFileUrl: pdf_list.value,
        status: status
      }).then((res) => {
        Api.createContractFlow({
          contractId: res,
          contractNumber: form.value.contractNumber,
          processDefinitionKey: contractNumber_flow.value
        }).then((res) => {
          message.success('发起审核成功')
          close_select_flow()
          setTimeout(() => {
            emit('close')
            emit('getList')
          }, 2000)
        })
      })
    }, 1000)
  } else if (detail_open_type.value == 'Stayover') {
    Api.renewalLease({
      ...form.value,
      ownerId: form.value.ownerId,
      buildId: Number(buildId),
      roomNumber: roomNumber,
      clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
      checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合
      contractAnnex: JSON.stringify(fileData_list.value), //文件集合
      parkId: parkId,
      calculationAccuracy: '0',
      id: form.value.id,
      pdfFileUrl: pdf_list.value
    }).then((res) => {
      Api.createContractFlow({
        contractId: res,
        contractNumber: form.value.contractNumber,
        processDefinitionKey: contractNumber_flow.value
      }).then((res) => {
        message.success('发起审核成功')
        close_select_flow()
        setTimeout(() => {
          emit('close')
          emit('getList')
        }, 2000)
      })
    })
  } else if (detail_open_type.value == 'editor') {
    Api.updateContrat({
      ...form.value,
      ownerId: form.value.ownerId,
      buildId: Number(buildId),
      roomNumber: roomNumber,
      clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
      checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合
      contractAnnex: JSON.stringify(fileData_list.value), //文件集合
      parkId: parkId,
      calculationAccuracy: '0',
      id: form.value.id,
      pdfFileUrl: pdf_list.value,
      status: status
    }).then((res) => {
      Api.createContractFlow({
        contractId: form.value.id,
        contractNumber: form.value.contractNumber,
        processDefinitionKey: contractNumber_flow.value
      }).then((res) => {
        message.success('发起审核成功')
        close_select_flow()
        setTimeout(() => {
          emit('close')
          emit('getList')
        }, 2000)
      })
    })
  } else if (detail_open_type.value == 'change') {
    Api.contractchangeSubmit({
      ...form.value,
      ownerId: form.value.ownerId,
      buildId: Number(buildId),
      roomNumber: roomNumber,
      clauseTypes: JSON.stringify(clauseTypes_list), //物业条款集合
      checkedBuild: JSON.stringify(checkedBuild), //楼宇资源集合
      contractAnnex: JSON.stringify(fileData_list.value), //文件集合
      parkId: parkId,
      calculationAccuracy: '0',
      id: form.value.id,
      pdfFileUrl: pdf_list.value,
      status: status
    }).then((res) => {
      Api.createContractFlow({
        contractId: form.value.id,
        contractNumber: form.value.contractNumber,
        processDefinitionKey: contractNumber_flow.value
      }).then((res) => {
        message.success('变更审核发起成功')
        close_select_flow()
        setTimeout(() => {
          emit('close')
          emit('getList')
        }, 2000)
      })
    })
  }
}
//创建成功但取消审批
const submit_closed = () => {
  show_select_flow.value = false
}
//添加租客
const FormRef = ref()
const useradd = () => {
  FormRef.value.open('add')
}

//录入合同
function addRoomToSelect(data: any) {
  DeptTreeSelect_data_list.value = data
  DeptTreeSelect_ref.value.getDeptTreeSelect(data)
  console.log(data, 'data')
}
onMounted(() => {
  getoperatorId()
  getcalculationOrder()
  getisReceivableInteger()
  getOwnerList('')
  getbzjType()
  getunitPriceTax()
  getIndustryList()
  getcontractUnitPriceList()
  getcontractNumber_flow_options()
})
const emit = defineEmits(['close', 'getList'])
</script>
<style lang="scss" scoped>
.stepBox {
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  position: relative;
  .step_border {
    border-bottom: 2px solid #2681f3;
  }
  .step_item {
    width: 33%;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
    font-size: 14px;
    padding-bottom: 10px;
    .step_number {
      width: 24px;
      height: 24px;
      border-radius: 50%;
      background-color: #2681f3;
      color: #fff;
      line-height: 24px;
      text-align: center;
      font-size: 12px;
    }
    .step_select_notselect {
      width: 24px;
      height: 24px;
      border-radius: 50%;
      background-color: #fff;
      border: 1px solid #c0c0c0;
      color: #c0c0c0;
      line-height: 24px;
      text-align: center;
      font-size: 12px;
    }
  }
}
.row_change {
  .row_item {
    color: #111;
    font-size: 14px;
    font-weight: 400;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
    cursor: pointer;
  }
  .active {
    color: #2681f3;
    font-weight: 600;
  }
}
.upload {
  border: 1px dashed #d9d9d9;

  width: 400px;
  height: 180px;
  text-align: center;
  border-radius: 6px;
  background-color: #fafafa;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .el-upload__text {
    color: #818181;
    font-size: 14px;
    position: absolute;
    bottom: 20px;
    width: 100%;
  }
  .File_img {
    width: 100px;
    height: 100px;
  }
}
.border-bottom {
  border-bottom: 1px solid #e8e8e8;
  margin-bottom: 10px;
}
</style>
