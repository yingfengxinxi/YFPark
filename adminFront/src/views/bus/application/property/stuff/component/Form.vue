<template>
  <ElDrawer
    :title="
      dialogTitle == 'stuff_stock_enter'
        ? '耗材入库'
        : dialogTitle == 'stuff_hand_out'
          ? '耗材派发'
          : dialogTitle == 'stuff_retreat_out'
            ? '耗材退库'
            : dialogTitle == 'stuff_transfer'
              ? '耗材调拨'
              : dialogTitle == 'stuff_adjust'
                ? '库存调整'
                : dialogTitle == 'stuff_handle'
                  ? '耗材处置'
                  : ''
    "
    v-model="dialogVisible"
    size="60%"
    @close="closeDialog"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
      label-position="top"
      :disabled="formType == 'detail'"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="单据编号">
            <template v-if="dialogTitle == 'stuff_stock_enter'">
              <el-input placeholder="编码自动生成" v-model="formData.number" disabled />
            </template>
            <template v-else>
              <el-input placeholder="编码自动生成" v-model="formData.processNumber" disabled />
            </template> </el-form-item
        ></el-col>
        <!-- 入库 -->
        <template v-if="dialogTitle == 'stuff_stock_enter'">
          <el-col :span="12">
            <el-form-item label="入库仓库" prop="depositoryId">
              <el-tree-select
                v-model="formData.depositoryId"
                :data="stockTree"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="入库处理人" prop="enterUid">
              <el-select v-model="formData.enterUid" placeholder="请选择" disabled>
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>

          <el-col :span="12">
            <el-form-item label="入库时间" prop="enterTime">
              <el-date-picker
                v-model="formData.enterTime"
                type="date"
                value-format="x"
                placeholder="选择"
                class="!w-1/1"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商" prop="supplier">
              <el-input
                v-model="formData.supplier"
                placeholder="请输入"
                class="!w-1/1"
              /> </el-form-item
          ></el-col>

          <el-col :span="12">
            <el-form-item label="合计金额">
              <el-input
                placeholder="根据物料列表自动结算"
                v-model="formData.totalPrice"
                disabled
              /> </el-form-item
          ></el-col>
        </template>
        <!-- 派发 -->
        <template v-if="dialogTitle == 'stuff_hand_out'">
          <el-col :span="12">
            <el-form-item label="领用人" prop="receiveUid">
              <el-select v-model="formData.receiveUid" placeholder="请选择">
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="领用部门">
              <el-tree-select
                v-model="formData.departmentId"
                :data="deptTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
                @node-click="handleNodeClickDepartment"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="出库仓库" prop="depositoryId">
              <el-tree-select
                v-model="formData.depositoryId"
                :data="stockTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="派发处理人" prop="muserUid">
              <el-select v-model="formData.muserUid" placeholder="请选择" disabled>
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="派发时间" prop="handoutTime">
              <el-date-picker
                v-model="formData.handoutTime"
                type="date"
                value-format="x"
                placeholder="选择"
                class="!w-1/1"
              />
            </el-form-item>
          </el-col>
        </template>
        <!-- 退库 -->
        <template v-if="dialogTitle == 'stuff_retreat_out'">
          <el-col :span="12">
            <el-form-item label="退库人" prop="retreatUid">
              <el-select v-model="formData.retreatUid" placeholder="请选择">
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="退库后使用部门">
              <el-tree-select
                v-model="formData.departmentId"
                :data="deptTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
                @node-click="handleNodeClickDepartment"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="入库仓库" prop="depositoryId">
              <el-tree-select
                v-model="formData.depositoryId"
                :data="stockTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="退库处理人" prop="muserUid">
              <el-select v-model="formData.muserUid" placeholder="请选择" disabled>
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="退库时间" prop="retreatDate">
              <el-date-picker
                v-model="formData.retreatDate"
                type="date"
                value-format="x"
                placeholder="选择"
                class="!w-1/1"
              />
            </el-form-item>
          </el-col>
        </template>
        <!-- 调拨 -->
        <template v-if="dialogTitle == 'stuff_transfer'">
          <el-col :span="12">
            <el-form-item label="调出管理员" prop="outAdminUid">
              <el-select v-model="formData.outAdminUid" placeholder="请选择">
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="调入管理员" prop="inAdminUid">
              <el-select v-model="formData.inAdminUid" placeholder="请选择">
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>

          <el-col :span="12">
            <el-form-item label="调出仓库" prop="outDepositoryId">
              <el-tree-select
                v-model="formData.outDepositoryId"
                :data="stockTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="调入仓库" prop="inDepositoryId">
              <el-tree-select
                v-model="formData.inDepositoryId"
                :data="stockTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="合并数量">
              <el-input
                placeholder="按本次调拨数量自动计算"
                disabled
                v-model="formData.totalNumAj"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="合并金额">
              <el-input
                placeholder="按本次调拨数量自动计算"
                disabled
                v-model="formData.totalPriceNum"
              /> </el-form-item
          ></el-col>
        </template>
        <!-- 调整 -->
        <template v-if="dialogTitle == 'stuff_adjust'">
          <el-col :span="12">
            <el-form-item label="调整处理人" prop="muserUid">
              <el-select v-model="formData.muserUid" placeholder="请选择" disabled>
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>

          <el-col :span="12">
            <el-form-item label="调整仓库" prop="depositoryId">
              <el-tree-select
                v-model="formData.depositoryId"
                :data="stockTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="调整时间" prop="adjustTime">
              <el-date-picker
                v-model="formData.adjustTime"
                type="date"
                value-format="x"
                placeholder="选择"
                class="!w-1/1"
              />
            </el-form-item>
          </el-col>
        </template>
        <!-- 处置 -->
        <template v-if="dialogTitle == 'stuff_handle'">
          <el-col :span="12">
            <el-form-item label="处置发起人" prop="muserUid">
              <el-select v-model="formData.muserUid" placeholder="请选择" disabled>
                <el-option
                  v-for="item in userOptions"
                  :key="item.id"
                  :label="item.nickname"
                  :value="item.id"
                />
              </el-select> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="发起部门" prop="departmentId">
              <el-tree-select
                v-model="formData.departmentId"
                :data="deptTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
                @node-click="handleNodeClickDepartment"
              /> </el-form-item
          ></el-col>

          <el-col :span="12">
            <el-form-item label="处置仓库" prop="depositoryId">
              <el-tree-select
                v-model="formData.depositoryId"
                :data="stockTree"
                :props="defaultProps"
                filterable
                default-expand-all
                check-strictly
                node-key="id"
                placeholder="请选择"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="发起时间" prop="launchTime">
              <el-date-picker
                v-model="formData.launchTime"
                type="date"
                value-format="x"
                placeholder="选择"
                class="!w-1/1"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合并金额">
              <el-input
                placeholder="按本次调拨数量自动计算"
                disabled
                v-model="formData.totalPriceNum"
              /> </el-form-item
          ></el-col>
        </template>
        <el-col :span="24">
          <el-form-item :label="dialogTitle == 'stuff_handle' ? '处置原因' : '备注'" prop="remark">
            <el-input
              placeholder="请输入"
              v-model="formData.remark"
              type="textarea"
              rows="4"
              class="!w-1/1"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <ContentWrap title="物料列表" class="!mt-4">
        <template #header>
          <div class="flex-1 flex justify-end items-center" v-if="formType != 'detail'">
            <el-button type="primary" @click="addAssets()">
              <Icon icon="ep:plus" class="m-r-6px" />
              添加物料
            </el-button>
          </div>
        </template>
        <el-table
          :data="assetsList"
          v-loading="loading"
          border
          :header-cell-style="{
            color: '#000000d9',
            fontSize: '14px',
            fontWeight: '500',
            backgroundColor: '#fafafa'
          }"
        >
          <el-table-column label="物料名称" prop="name" width="140" />
          <el-table-column label="物料编码" prop="number" width="140" />
          <!-- 入库 -->
          <template v-if="dialogTitle == 'stuff_stock_enter'">
            <el-table-column label="计量单位" prop="meterUnit" width="140" />
            <el-table-column label="入库单价" prop="price" width="140">
              <template #header>
                <div class="flex items-center">
                  <Icon icon="ep:edit" class="mr-5px" /> 入库单价
                </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.price"
                  class="!w-1/1"
                  @change="getTotalPrice(scope, scope.row.num)"
                />
              </template>
            </el-table-column>
            <el-table-column label="数量" prop="num" width="140">
              <template #header>
                <div class="flex items-center"> <Icon icon="ep:edit" class="mr-5px" /> 数量 </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.num"
                  class="!w-1/1"
                  @change="getTotalPrice(scope, scope.row.num)"
                />
              </template>
            </el-table-column>
            <el-table-column label="总价" prop="totalPrice" width="140">
              <template #header>
                <div class="flex items-center"> <Icon icon="ep:edit" class="mr-5px" /> 总价 </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.totalPrice"
                  class="!w-1/1"
                  @change="getPrice(scope)"
                />
              </template>
            </el-table-column>
            <el-table-column label="物料照片" prop="modelName" width="140">
              <template #default="scope">
                <span v-if="scope.row.imageUrl">
                  <el-image
                    preview-teleported="true"
                    style="width: 60px; height: 60px"
                    :src="scope.row.imageUrl"
                    :zoom-rate="1.2"
                    :max-scale="7"
                    :min-scale="0.2"
                    :preview-src-list="[scope.row.imageUrl]"
                    :initial-index="0"
                    fit="cover"
                /></span>
                <span v-else>--</span>
              </template>
            </el-table-column>
          </template>
          <!-- 派发 -->
          <template v-else-if="dialogTitle == 'stuff_hand_out'">
            <el-table-column label="计量单位" prop="name" width="140" />
            <el-table-column label="可用库存" prop="totalNum" width="140">
              <template #default="scope">
                {{ formType != 'detail' ? scope.row.totalNum : scope.row.num }}
              </template>
            </el-table-column>
            <el-table-column label="单价" prop="price" width="140" />
            <el-table-column label="派发数量" prop="num" width="140">
              <template #header>
                <div class="flex items-center">
                  <Icon icon="ep:edit" class="mr-5px" /> 派发数量
                </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  :max="scope.row.totalNum"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.num"
                  class="!w-1/1"
                  @change="getTotalPrice(scope, scope.row.num)"
                />
              </template>
            </el-table-column>
            <el-table-column label="总价" prop="totalPrice" width="140">
              <template #default="scope">
                {{ scope.row.totalPrice }}
              </template>
            </el-table-column>
            <el-table-column label="品牌" prop="brand" width="140" />
            <el-table-column label="规格型号" prop="modelName" width="140" />

            <el-table-column label="物料照片" prop="modelName" width="140">
              <template #default="scope">
                <span v-if="scope.row.imageUrl">
                  <el-image
                    preview-teleported="true"
                    style="width: 60px; height: 60px"
                    :src="scope.row.imageUrl"
                    :zoom-rate="1.2"
                    :max-scale="7"
                    :min-scale="0.2"
                    :preview-src-list="[scope.row.imageUrl]"
                    :initial-index="0"
                    fit="cover"
                /></span>
                <span v-else>--</span>
              </template>
            </el-table-column>
          </template>
          <!-- 退库 -->
          <template v-else-if="dialogTitle == 'stuff_retreat_out'">
            <el-table-column label="计量单位" prop="name" width="140" />
            <el-table-column label="可退库存" prop="num" width="140" />
            <el-table-column label="退库数量" prop="num" width="140">
              <template #header>
                <div class="flex items-center">
                  <Icon icon="ep:edit" class="mr-5px" /> 退库数量
                </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  :max="scope.row.num"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.num"
                  class="!w-1/1"
                />
              </template>
            </el-table-column>
            <el-table-column label="品牌" prop="brand" width="140" />
            <el-table-column label="规格型号" prop="modelName" width="140" />

            <el-table-column label="物料照片" prop="modelName" width="140">
              <template #default="scope">
                <span v-if="scope.row.imageUrl">
                  <el-image
                    preview-teleported="true"
                    style="width: 60px; height: 60px"
                    :src="scope.row.imageUrl"
                    :zoom-rate="1.2"
                    :max-scale="7"
                    :min-scale="0.2"
                    :preview-src-list="[scope.row.imageUrl]"
                    :initial-index="0"
                    fit="cover"
                /></span>
                <span v-else>--</span>
              </template>
            </el-table-column>
          </template>
          <!-- 调拨 -->
          <template v-else-if="dialogTitle == 'stuff_transfer'">
            <el-table-column label="物料分类" prop="categoryName" width="140" />
            <el-table-column label="可用库存" prop="totalNum" width="140">
              <template #default="scope">
                {{ scope.row.totalNum || scope.row.stock.totalNum }}
              </template>
            </el-table-column>

            <el-table-column label="单价" prop="price" width="140" />
            <el-table-column label="本次调拨数量" prop="num" width="140">
              <template #header>
                <div class="flex items-center">
                  <Icon icon="ep:edit" class="mr-5px" /> 本次调拨数量
                </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  :max="scope.row.totalNum"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.num"
                  class="!w-1/1"
                  @change="getTotalPrice(scope, scope.row.num)"
                  @blur="getTotalPriceNum()"
                />
              </template>
            </el-table-column>
            <el-table-column label="本次调拨金额" prop="totalPrice" width="140" />
          </template>
          <!-- 处置 -->
          <template v-else-if="dialogTitle == 'stuff_handle'">
            <el-table-column label="物料分类" prop="categoryName" width="140" />
            <el-table-column label="可用库存" prop="totalNum" width="140">
              <template #default="scope">
                {{ scope.row.stock.totalNum }}
              </template>
            </el-table-column>
            <el-table-column label="单价" prop="price" width="140" />
            <el-table-column label="本次处置数量" prop="num" width="140">
              <template #header>
                <div class="flex items-center">
                  <Icon icon="ep:edit" class="mr-5px" /> 本次处置数量
                </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  :max="scope.row.totalNum"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.num"
                  class="!w-1/1"
                  @change="getTotalPrice(scope, scope.row.num)"
                  @blur="getTotalPriceNum()"
                />
              </template>
            </el-table-column>
            <el-table-column label="本次处置金额" prop="totalPrice" width="140" />
          </template>
          <!-- 调整 -->
          <template v-else-if="dialogTitle == 'stuff_adjust'">
            <el-table-column label="物料分类" prop="categoryName" width="140" />
            <el-table-column label="调整前数量" prop="handoutNum" width="140" />
            <el-table-column label="调整前单价" prop="retreatNum" width="140" />
            <el-table-column label="调整前总价" prop="totalPrice" width="140">
              <template #default="scope">
                {{ (scope.row.handoutNum * scope.row.retreatNum).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="本次调整数量" prop="num" width="140">
              <template #default="scope">
                {{ scope.row.num - scope.row.handoutNum }}
              </template>
            </el-table-column>
            <el-table-column label="本次调整单价" prop="price" width="140">
              <template #default="scope">
                {{ scope.row.price - scope.row.retreatNum }}
              </template>
            </el-table-column>
            <el-table-column label="本次调整总价" prop="totalPrice" width="140">
              <template #default="scope">
                {{
                  Number(scope.row.totalPrice) - Number(scope.row.handoutNum * scope.row.retreatNum)
                }}
              </template>
            </el-table-column>
            <el-table-column label="调整后数量" prop="num" width="140">
              <template #header>
                <div class="flex items-center">
                  <Icon icon="ep:edit" class="mr-5px" /> 调整后数量
                </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.num"
                  class="!w-1/1"
                  @change="getPrice(scope)"
                />
              </template>
            </el-table-column>
            <el-table-column label="调整后单价" prop="price" width="140" />
            <el-table-column label="调整后总价" prop="totalPrice" width="140">
              <template #header>
                <div class="flex items-center">
                  <Icon icon="ep:edit" class="mr-5px" /> 调整后总价
                </div>
              </template>
              <template #default="scope">
                <el-input-number
                  :min="0"
                  controls-position="right"
                  :step="1"
                  v-model="scope.row.totalPrice"
                  class="!w-1/1"
                  @change="getPrice(scope)"
                />
              </template>
            </el-table-column>
          </template>
          <el-table-column label="操作" width="100" fixed="right" v-if="formType != 'detail'">
            <template #default="scope">
              <el-button text type="primary" @click="deleteAssetsItem(scope.row, scope.$index)"
                >移除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          v-if="formType == 'detail'"
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </ContentWrap>
      <StatusArrayList ref="listRef" :userOptions="userOptions" @success="submitListForm" />
    </el-form>
    <template #footer v-if="formType != 'detail'">
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </ElDrawer>
</template>
<script setup lang="ts">
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { PropertyStuffDepositoryApi, PropertyStuffDepositoryVO } from '@/api/bus/stuff/depository'
import { BuildApi } from '@/api/bus/village'
import { ElDrawer } from 'element-plus'
import { useUserStore } from '@/store/modules/user'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { PropertyStuffStockApi, PropertyStuffStockVO } from '@/api/bus/stuff/stock'
import StatusArrayList from './statusArrayList.vue'
import { PropertyStuffCategoryApi, PropertyStuffCategoryVO } from '@/api/bus/stuff/category'
import { PropertyStuffProcessApi } from '@/api/bus/stuff/process'

/** 派发 表单 */
defineOptions({ name: 'CreatestuffHandOutPropertyForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const catrgoryList = ref<PropertyStuffCategoryVO[]>([])
const deptTree = ref() // 部门树形结构
const stockTree = ref() //仓库树结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('stuff_stock_enter') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  number: undefined,
  processNumber: undefined,
  id: undefined,
  enterUid: undefined,
  receiveUid: undefined,
  retreatUid: undefined,
  outAdminUid: undefined,
  inAdminUid: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  depositoryId: undefined,
  departmentName: undefined,
  outDepositoryId: undefined,
  inDepositoryId: undefined,
  totalNum: undefined,
  totalNumAj: undefined,
  totalPriceNum: undefined,
  totalPrice: undefined,
  departmentId: undefined,
  enterTime: undefined,
  adjustTime: undefined,
  launchTime: undefined,
  handoutTime: undefined,
  retreatDate: undefined,
  supplier: undefined,
  villageId: undefined,
  buildId: undefined,
  roomId: undefined,
  propertyIds: undefined,
  remark: undefined
})
const roomName = ref()
const formRules = reactive({
  enterUid: [{ required: true, message: '入库处理人不能为空', trigger: 'blur' }],
  depositoryId: [{ required: true, message: '仓库不能为空', trigger: 'blur' }],
  receiveUid: [{ required: true, message: '领用人不能为空', trigger: 'blur' }],
  departmentId: [{ required: true, message: '部门不能为空', trigger: 'blur' }],
  retreatUid: [{ required: true, message: '退库人不能为空', trigger: 'blur' }],
  muserUid: [{ required: true, message: '处理人不能为空', trigger: 'blur' }],
  inAdminUid: [{ required: true, message: '调入管理员不能为空', trigger: 'blur' }],
  outAdminUid: [{ required: true, message: '调出管理员不能为空', trigger: 'blur' }],
  outDepositoryId: [{ required: true, message: '调出仓库不能为空', trigger: 'blur' }],
  inDepositoryId: [{ required: true, message: '调入仓库不能为空', trigger: 'blur' }],
  launchTime: [{ required: true, message: '时间不能为空', trigger: 'blur' }],
  adjustTime: [{ required: true, message: '调整时间不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const treeLoading = ref(false)
const userStore = useUserStore()
const villageTypeValue = computed(() => userStore.getvillageType)
const deptList = ref([])
const idArr = ref<number[]>([])
const assetsList = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  processCode: undefined
})
const total = ref(0)
const loading = ref(false)
const listRef = ref()
/** 打开弹窗 */
const open = async (title?: string, type?: any, id?: number, row?: any) => {
  dialogTitle.value = title || dialogTitle.value
  formType.value = type
  dialogVisible.value = true
  assetsList.value = []
  resetForm()
  if (type == 'detail') {
    formData.value = JSON.parse(JSON.stringify(row))
    formData.value.totalNumAj = formData.value.totalNum
    formData.value.totalPriceNum = formData.value.totalPrice
    queryParams.processCode = id
    await getList()
  }
  // 获得部门树
  stockTree.value = await PropertyStuffDepositoryApi.getPropertyStuffDepositoryTree()

  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())

  // 获得用户列表
  userOptions.value = await UserApi.getSimpleUserList()
  if (dialogTitle.value == 'stuff_stock_enter') {
    formData.value.enterUid = userStore.getUser.id
  } else {
    formData.value.muserUid = userStore.getUser.id
  }
  getTree()

  console.log(formData.value)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const getList = async () => {
  try {
    loading.value = true
    const res = await PropertyStuffProcessApi.getPropertyStuffProcessPage(queryParams)
    assetsList.value = res.list
    total.value = res.total
  } finally {
    loading.value = false
  }
}
const handleNodeClickDepartment = (item) => {
  console.log(item)
  if (item.name) {
    formData.value.departmentName = item.name
  } else {
    formData.value.departmentName = undefined
  }
  console.log(formData.value)
}

/** 总价 */
const getTotalPrice = async (scope, num) => {
  if (scope.row.price && num) scope.row.totalPrice = (num * scope.row.price).toFixed(2)
}

const getTotalPriceNum = async () => {
  formData.value.totalNumAj = undefined
  formData.value.totalPriceNum = undefined
  assetsList.value.forEach((item) => {
    if (item.num) {
      // item.totalPrice = (item.num * item.price)
      formData.value.totalNumAj = item.num + (formData.value.totalNumAj || 0)
      formData.value.totalPriceNum =
        Number(item.totalPrice) + Number(formData.value.totalPriceNum || 0)
    }
  })
}

/** 单价 */
const getPrice = async (scope) => {
  if (scope.row.totalPrice && scope.row.num)
    scope.row.price = (scope.row.totalPrice / scope.row.num).toFixed(2)
}

const closeDialog = () => {
  listRef.value.resetSelect()
  console.log(listRef.value, '物料列表')
  dialogVisible.value = false
}

const addAssets = async () => {
  if (dialogTitle.value == 'stuff_stock_enter') {
    listRef.value.open(dialogTitle.value)
  }
  if (dialogTitle.value == 'stuff_hand_out') {
    if (!formData.value.receiveUid) {
      return message.warning('领用人不能为空')
    }
    if (!formData.value.depositoryId) {
      return message.warning('仓库不能为空')
    }
    listRef.value.open(dialogTitle.value, {
      receiveUid: formData.value.receiveUid,
      depositoryId: formData.value.depositoryId
    })
  }
  if (dialogTitle.value == 'stuff_retreat_out') {
    if (!formData.value.retreatUid) {
      return message.warning('退库人不能为空')
    }
    if (!formData.value.depositoryId) {
      return message.warning('仓库不能为空')
    }
    listRef.value.open(dialogTitle.value, {
      receiveUid: formData.value.receiveUid,
      depositoryId: formData.value.depositoryId
    })
  }
  if (dialogTitle.value == 'stuff_transfer') {
    if (!formData.value.outDepositoryId) {
      return message.warning('调出仓库不能为空')
    }
    listRef.value.open(dialogTitle.value, {
      depositoryId: formData.value.outDepositoryId
    })
  }
  if (dialogTitle.value == 'stuff_handle') {
    if (!formData.value.depositoryId) {
      return message.warning('处置仓库不能为空')
    }
    listRef.value.open(dialogTitle.value, {
      depositoryId: formData.value.depositoryId
    })
  }
  if (dialogTitle.value == 'stuff_adjust') {
    if (!formData.value.depositoryId) {
      return message.warning('调整仓库不能为空')
    }
    listRef.value.open(dialogTitle.value, {
      depositoryId: formData.value.depositoryId
    })
  }
}

/** 删除资产 */
const deleteAssetsItem = (row, index) => {
  assetsList.value.splice(index, 1)
}

const submitListForm = async (list) => {
  assetsList.value = [...new Set(list)]
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  console.log(assetsList.value)
  // 校验表单
  await formRef.value.validate()

  if (!assetsList.value.length) {
    message.warning('请先选择物料')
    return
  }
  try {
    let data = assetsList.value.map((item) => {
      return {
        ...item,
        stuffId: item.stuffId ? item.stuffId : item.id,
        cuserUid: dialogTitle.value == 'stuff_stock_enter' ? undefined : item.id,
        id: undefined,
        status: undefined,
        extra: formData.value.remark,
        muserUid: formData.value.muserUid,
        depositoryId: formData.value.depositoryId,
        departmentName: formData.value.departmentName,
        supplier: formData.value.supplier,
        enterUid: formData.value.enterUid,
        enterTime: formData.value.enterTime,
        adjustTime: formData.value.adjustTime,
        launchTime: formData.value.launchTime,
        remark: formData.value.remark,
        processType: dialogTitle.value,
        receiveUid: formData.value.receiveUid,
        retreatUid: formData.value.retreatUid,
        departmentId: formData.value.departmentId,
        handoutTime: formData.value.handoutTime,
        retreatDate: formData.value.retreatDate,
        outAdminUid: formData.value.outAdminUid,
        inAdminUid: formData.value.inAdminUid,
        outDepositoryId: formData.value.outDepositoryId,
        inDepositoryId: formData.value.inDepositoryId,
        totalNumAj: formData.value.totalNumAj,
        totalPriceNum: formData.value.totalPriceNum
      }
    })
    data.forEach((item) => {
      if (dialogTitle.value == 'stuff_stock_enter') {
        if (!item.price) {
          message.warning('请完善物料单价')
          throw new Error('End Loop')
        }
        if (!item.num) {
          message.warning('请完善物料数量')
          throw new Error('End Loop')
        }
        if (!item.totalPrice) {
          message.warning('请完善物料总价')
          throw new Error('End Loop')
        }
      } else if (dialogTitle.value == 'stuff_hand_out') {
        if (!item.num) {
          message.warning('请完善派发数量')
          throw new Error('End Loop')
        }
      } else if (dialogTitle.value == 'stuff_retreat_out') {
        if (!item.num) {
          message.warning('请完善退库数量')
          throw new Error('End Loop')
        }
      } else if (dialogTitle.value == 'stuff_transfer') {
        if (!item.num) {
          message.warning('请完善调拨数量')
          throw new Error('End Loop')
        }
      } else if (dialogTitle.value == 'stuff_handle') {
        if (!item.num) {
          message.warning('请完善处置数量')
          throw new Error('End Loop')
        }
      } else if (dialogTitle.value == 'stuff_adjust') {
        if (!item.num) {
          message.warning('请完善调整数量')
          throw new Error('End Loop')
        }
      }
    })
    formLoading.value = true
    try {
      // const data = formData.value
      // data.propertyIds = assetsList.value.map((item) => item.id).join(',')
      // data.type = 1

      await PropertyStuffStockApi.savePropertyStuff(data)
      message.success(t('common.updateSuccess'))

      dialogVisible.value = false
      // 发送操作成功的事件
      emit('success')
    } finally {
      formLoading.value = false
    }
  } catch (e) {
    return
  }
  // 提交请求
}

const handleNodeClick = async (data: any) => {
  console.log('handleNodeClick', data)
  if (data.layerId) {
    nextTick(() => {
      roomName.value =
        data.villageName + ' - ' + data.buildName + ' - ' + data.layerName + ' - ' + data.name
    })
    formData.value.roomId = data.id
    formData.value.buildId = data.buildId
    formData.value.villageId = data.villageId
  }
}

const props = ref({
  label: 'name',
  children: 'children',
  isLeaf: 'leaf',
  value: 'treeId'
})
/** 获得部门树 */
const getTree = async () => {
  try {
    console.log('getTree', props.value)
    treeLoading.value = true
    const res = await BuildApi.getVillagePage({ type: villageTypeValue.value })
    treeLoading.value = false
    deptList.value = []
    idArr.value = []
    res.villageRespVOS.forEach((element) => {
      element.treeId = element.id
      idArr.value.push(element.treeId)
      // delete element.id
      if (element.buildList) {
        element.buildList.forEach((item) => {
          item.name = item.buildName
          item.treeId = 'building' + item.id
          // delete item.id
        })
        // element.children = handleTree(element.buildList)
      } else {
        element.buildList = []
      }
    })
    deptList.value.push(...handleTree(res.villageRespVOS))
    console.log(deptList.value, 'deptList.value')
  } finally {
    treeLoading.value = false
  }
}

const loadNode = async (node: Node, resolve?: (data: Tree[]) => void) => {
  console.log(node.data, 'node.data')
  // resolve(deptList.value)
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
        element.treeId = 'layer' + element.id
      })
      node.data.layerList = dataArray
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
        element.treeId = 'room' + element.id
        element.leaf = true
      })
      node.data.roomList = dataArray
    }
    return resolve(dataArray)
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    number: undefined,
    processNumber: undefined,
    id: undefined,
    enterUid: undefined,
    receiveUid: undefined,
    cuserUid: undefined,
    muserUid: undefined,
    retreatUid: undefined,
    departmentName: undefined,
    outAdminUid: undefined,
    inAdminUid: undefined,
    depositoryId: undefined,
    outDepositoryId: undefined,
    inDepositoryId: undefined,
    totalNum: undefined,
    totalNumAj: undefined,
    totalPriceNum: undefined,
    totalPrice: undefined,
    departmentId: undefined,
    enterTime: undefined,
    adjustTime: undefined,
    launchTime: undefined,
    handoutTime: undefined,
    retreatDate: undefined,
    supplier: undefined,
    villageId: undefined,
    buildId: undefined,
    roomId: undefined,
    propertyIds: undefined,
    remark: undefined
  }
  formRef.value?.resetFields()
}
</script>
