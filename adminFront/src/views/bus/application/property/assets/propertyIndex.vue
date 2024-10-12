<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="资产编码" prop="propertyNumber">
        <el-input
          v-model="queryParams.propertyNumber"
          placeholder="请输入资产编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产分类" prop="type">
        <el-tree-select
          v-model="queryParams.type"
          :data="locationList"
          :render-after-expand="false"
          :props="{ label: 'name', value: 'id', children: 'children' }"
          placeholder="请选择资产分类"
          clearable
          :check-strictly="true"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入资产名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="资产状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择资产状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.PROPERTYSTATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备序列号" prop="deviceCode">
        <el-input
          v-model="queryParams.deviceCode"
          placeholder="请输入设备序列号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="管理员" prop="adminId">
        <el-select
          v-model="queryParams.adminId"
          placeholder="请选择管理员"
          class="!w-240px"
          clearable
        >
          <el-option
            v-for="item in userOptions"
            :key="item.id"
            :label="item.nickname"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所在位置" prop="positionId">
        <el-tree-select
          v-model="queryParams.positionId"
          :data="positionList"
          :render-after-expand="false"
          :props="{ label: 'name', value: 'id', children: 'children' }"
          placeholder="请选择所在位置"
          clearable
          :check-strictly="true"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="使用人" prop="userId">
        <el-select
          v-model="queryParams.userId"
          placeholder="请选择使用人"
          class="!w-240px"
          clearable
        >
          <el-option
            v-for="item in userOptions"
            :key="item.id"
            :label="item.nickname"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="使用部门" prop="departmentId">
        <el-tree-select
          v-model="queryParams.departmentId"
          :data="deptTree"
          :props="defaultProps"
          filterable
          check-strictly
          node-key="id"
          clearable
          placeholder="请选择部门"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="领用日期" prop="receiveTime">
        <el-date-picker
          v-model="queryParams.receiveTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="保养到期时间" prop="maintainTime">
        <el-date-picker
          v-model="queryParams.maintainTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['bus:property:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['bus:property:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <div v-if="selectedRow.length">
      <el-button type="primary" plain @click="handleBatch('handleCreateHandoutProperty1')"
        >派发</el-button
      >
      <el-button type="primary" plain @click="handleBatch('handleCreateHandoutProperty2')"
        >退库</el-button
      >
      <el-button type="primary" plain @click="handleBatch('handleLend')">借出</el-button>
      <el-button type="primary" plain @click="handleBatch('handleReturn')">归还</el-button>
      <el-button type="primary" plain @click="handleBatch('handleAllot')">调拨</el-button>
      <el-button type="primary" plain @click="handleBatch('handleDisposeOf')">处置</el-button>
      <el-button type="primary" plain @click="handleBatch('onPrintConfirm')">打印标签</el-button>
    </div>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" fixed />
      <el-table-column
        label="资产编码"
        align="center"
        prop="propertyNumber"
        :formatter="tableColumnEmptyPlaceholder"
        width="160"
      >
        <template #default="scope">
          <span
            :class="scope.row.propertyNumber ? `c-[var(--el-color-primary)] cursor-pointer` : ''"
            @click="handlePropertyDetail(scope.row.id)"
            >{{ scope.row.propertyNumber || '--' }}</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="资产名称"
        align="center"
        prop="name"
        :formatter="tableColumnEmptyPlaceholder"
        width="160"
      />
      <el-table-column
        label="资产分类"
        align="center"
        prop="categoryName"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="资产状态" align="center" prop="status" width="160">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PROPERTYSTATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="品牌"
        align="center"
        prop="brand"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="所在位置"
        align="center"
        prop="positionName"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />

      <el-table-column label="入库时间" align="center" prop="stockTime" width="160px">
        <template #default="scope">
          <div v-if="scope.row.stockTime">{{
            dateFormatter2(scope.row, 'stockTime', scope.row.stockTime)
          }}</div>
          <div v-else>--</div>
        </template>
      </el-table-column>
      <el-table-column
        label="型号"
        align="center"
        prop="modelName"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="设备序列号"
        align="center"
        prop="deviceCode"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="管理员"
        align="center"
        prop="adminName"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="购置时间" align="center" prop="purchaseTime" width="160px">
        <template #default="scope">
          <div v-if="scope.row.purchaseTime">{{
            dateFormatter2(scope.row, 'purchaseTime', scope.row.purchaseTime)
          }}</div>
          <div v-else>--</div>
        </template>
      </el-table-column>
      <el-table-column
        label="购置方式"
        align="center"
        prop="purchaseType"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      >
        <template #default="scope">
          <dict-text :type="DICT_TYPE.PROPERTY_PURCHASETYPE" :value="scope.row.purchaseType" />
        </template>
      </el-table-column>
      <el-table-column
        label="购置金额(含税)"
        align="center"
        prop="purchaseAmount"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="预计使用期限(月)"
        align="center"
        prop="expectMonths"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="资产照片"
        align="center"
        prop="imageUrl"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      >
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
      <el-table-column
        label="使用人"
        align="center"
        prop="userName"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      >
        <template #default="scope">
          <template v-if="scope.row.userId">
            <template v-for="user in userOptions" :key="user.id">
              <span v-if="user.id === scope.row.userId">{{ user.nickname }}</span>
            </template>
          </template>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column
        label="目标房源"
        align="center"
        prop="buildBind"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      >
        <template #default="scope">
          <span v-if="scope.row.buildBind"
            >{{ JSON.parse(scope.row.buildBind).buildName }}/{{
              JSON.parse(scope.row.buildBind).layerName
            }}/{{ JSON.parse(scope.row.buildBind).roomName }}</span
          >
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column
        label="使用部门"
        align="center"
        prop="departmentName"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="领用日期"
        align="center"
        prop="receiveTime"
        :formatter="dateFormatter"
        width="160px"
      >
        <template #default="scope">
          <div v-if="scope.row.receiveTime">{{
            dateFormatter(scope.row, 'receiveTime', scope.row.receiveTime)
          }}</div>
          <div v-else>--</div>
        </template>
      </el-table-column>
      <el-table-column
        label="保养到期时间"
        align="center"
        prop="maintainTime"
        :formatter="dateFormatter"
        width="160px"
      >
        <template #default="scope">
          <div v-if="scope.row.maintainTime">{{
            dateFormatter(scope.row, 'maintainTime', scope.row.maintainTime)
          }}</div>
          <div v-else>--</div>
        </template>
      </el-table-column>
      <el-table-column
        label="保养说明"
        align="center"
        prop="maintainInfo"
        width="160"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="预计折旧期限(月)"
        align="center"
        prop="depreciationMonths"
        width="160"
      />

      <el-table-column label="操作" align="center" width="160" fixed="right">
        <template #default="scope">
          <div class="flex items-center">
            <el-button
              link
              type="primary"
              @click="handleCreateHandoutProperty(1, scope.row)"
              v-hasPermi="['bus:property:update']"
            >
              派发
            </el-button>
            <el-button
              link
              type="primary"
              @click="handleCreateHandoutProperty(2, scope.row)"
              v-hasPermi="['bus:property:update']"
            >
              退库
            </el-button>
            <el-dropdown trigger="hover" @command="(command) => handleCommand(command, scope.row)">
              <el-button text type="primary"> <Icon icon="fa-solid:ellipsis-v" /> </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item v-if="checkPermi(['bus:property:create'])" command="handleLend">
                    <el-button link type="primary"> 借出 </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['bus:property:create'])"
                    command="handleReturn"
                  >
                    <el-button link type="primary"> 归还 </el-button>
                  </el-dropdown-item>
                  <!-- <el-dropdown-item
                    v-if="checkPermi(['bus:property:create'])"
                    command="extendedReturn"
                  >
                    <el-button link type="primary"> 延长归还 </el-button>
                  </el-dropdown-item> -->

                  <el-dropdown-item
                    v-if="checkPermi(['bus:property:update'])"
                    command="handleUpdate"
                  >
                    <el-button link type="primary"> 修改 </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['bus:property:create'])"
                    command="handleAllot"
                  >
                    <el-button link type="primary"> 调拨 </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['bus:property:create'])"
                    command="handleDisposeOf"
                  >
                    <el-button link type="primary"> 处置 </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['bus:property:delete'])"
                    command="handleDelete"
                  >
                    <el-button link type="danger"> 删除 </el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <PropertyForm
    ref="formRef"
    :locationList="locationList"
    :positionList="positionList"
    @success="getList"
  />
  <!-- 派发 -->
  <CreateHandoutPropertyForm ref="createHandoutPropertyFormRef" @success="getList" />
  <!-- 退库 -->
  <ReturnInventoryForm ref="returnInventoryFormRef" @success="getList" />
  <!-- 借用/归还 -->
  <LendReturn ref="LendReturnFormRef" @success="getList" />
  <!-- 调拨 -->
  <AllotForm ref="allotFormRef" @success="getList" />
  <!-- 处置 -->
  <DisposeOfForm ref="disposeOfFormRef" @success="getList" />

  <!-- 标签打印 -->
  <div id="mypdf" style="position: absolute; z-index: -999; width: 100%; height: 1000px">
    <div v-if="tagTemplate?.applyJson?.paper_type == '0'">
      <div v-for="(item, index) in selectedRow" :key="index">
        <div
          style="
            page-break-after: always;
            box-sizing: border-box;
            border: 1px solid #999;
            margin-top: 10px;
            margin-left: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: white;
          "
          :style="{
            width: `${tagTemplate.applyJson.tag_width - 5}mm`,
            height: `${tagTemplate.applyJson.tag_height - 6}mm`
          }"
        >
          <div style="padding-left: 1px">
            <Qrcode :text="item.number" />
          </div>
          <div style="flex: 1; padding-right: 10px; box-sizing: border-box">
            <img
              v-if="tagTemplate.hasLogo == '0'"
              :src="tagTemplate.applyJson.logo"
              style="width: 100px; height: 55px"
            />
            <div
              v-for="(items, indexs) in tagTemplate.applyJson.fields"
              :key="indexs"
              style="display: flex; flex-warp: wrap"
            >
              <span style="white-space: nowrap">{{ items.label }}</span
              >:
              <span style="word-break: break-all; white-space: pre-wrap">{{
                item[items.value] || '--'
              }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="tagTemplate?.applyJson?.paper_type == '1'"
      style="display: grid; gap: 10px; grid-template-columns: repeat(2, 1fr); margin-right: 15px"
    >
      <div
        v-for="(item, index) in selectedRow"
        :key="index"
        style="height: 200px"
        :class="index % 10 == 0 ? 'section' : ''"
      >
        <div
          style="
            box-sizing: border-box;
            border: 1px solid #999;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: white;
            height: 100%;
          "
        >
          <div style="padding-left: 1px">
            <Qrcode :text="item.number" :width="180" />
          </div>
          <div style="flex: 1; padding-right: 10px; box-sizing: border-box">
            <img
              v-if="tagTemplate.hasLogo == '0'"
              :src="tagTemplate.applyJson.logo"
              style="width: 100px; height: 55px"
            />
            <div
              v-for="(items, indexs) in tagTemplate.applyJson.fields"
              :key="indexs"
              style="display: flex; flex-warp: wrap"
            >
              <span style="white-space: nowrap">{{ items.label }}</span
              >:
              <span style="word-break: break-all; white-space: pre-wrap">{{
                item[items.value] || '--'
              }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="tagTemplate?.applyJson?.paper_type == '2'"
      style="display: grid; gap: 10px; grid-template-columns: repeat(3, 1fr); margin-right: 15px"
    >
      <div
        v-for="(item, index) in selectedRow"
        :key="index"
        style="height: 120px"
        :class="index % 24 == 0 ? 'section' : ''"
      >
        <div
          style="
            box-sizing: border-box;
            border: 1px solid #999;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: white;
            width: 100%;
            height: 100%;
          "
        >
          <div style="padding-left: 1px">
            <Qrcode :text="item.number" :width="100" />
          </div>
          <div style="flex: 1; padding-right: 10px; box-sizing: border-box">
            <img
              v-if="tagTemplate.hasLogo == '0'"
              :src="tagTemplate.applyJson.logo"
              style="width: 80px; height: 45px"
            />
            <div
              v-for="(items, indexs) in tagTemplate.applyJson.fields"
              :key="indexs"
              style="display: flex; flex-warp: wrap"
            >
              <span style="white-space: nowrap; font-size: 12px">{{ items.label }}</span>
              <div style="word-break: break-all; white-space: pre-wrap; font-size: 12px">{{
                item[items.value] || '--'
              }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <ElDialog title="延长归还时间" v-model="extendedReturnShow">
    延长时间至:
    <el-date-picker
      type="date"
      v-model="extendedReturnForm.expectRevertTime"
      format="YYYY-MM-DD"
      clearable
    />
    <template #footer>
      <el-button type="" @click="extendedReturnShow = false">取消</el-button>
      <el-button type="primary" @click="requestReturn">确定</el-button>
    </template>
  </ElDialog>
</template>

<script setup lang="ts">
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import PropertyForm from './PropertyForm.vue'
import CreateHandoutPropertyForm from './component/createHandoutPropertyForm.vue'
import ReturnInventoryForm from './component/returnInventoryForm.vue'
import LendReturn from './component/lendReturn.vue'
import AllotForm from './component/allotForm.vue'
import DisposeOfForm from './component/disposeOfForm.vue'
import { DICT_TYPE, getIntDictOptions, getDictLabel } from '@/utils/dict'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import printJS from 'print-js'
import { PropertyTagApi } from '@/api/bus/property/setting/propertyTag'
import { PropertyApi, PropertyVO } from '@/api/bus/property/property'
import { PropertyLocationApi } from '@/api/bus/property/setting/propertyLocation'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
import { checkPermi } from '@/utils/permission'
import { ElDialog } from 'element-plus'
/** 资产管理 列表 */
defineOptions({ name: 'PropertyIndex' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const deptTree = ref() // 部门树形结构
const userOptions = ref<UserApi.UserVO[]>([]) // 用户列表
const loading = ref(true) // 列表的加载中
const list = ref<PropertyVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: undefined,
  propertyNumber: undefined,
  labelLink: undefined,
  type: undefined,
  name: undefined,
  status: undefined,
  brand: undefined,
  modelName: undefined,
  deviceCode: undefined,
  processCode: undefined,
  adminId: undefined,
  adminUid: undefined,
  villageId: undefined,
  buildId: undefined,
  roomId: undefined,
  buildBind: undefined,
  positionId: undefined,
  positionName: undefined,
  purchaseTime: [],
  purchaseType: undefined,
  purchaseAmount: undefined,
  stockTime: [],
  expectMonths: undefined,
  remark: undefined,
  imageHash: undefined,
  imageUrl: undefined,
  userId: undefined,
  departmentId: undefined,
  receiveTime: [],
  maintainTime: [],
  maintainInfo: undefined,
  depreciationMonths: undefined,
  cuserUid: undefined,
  muserUid: undefined,
  knowledgeBase: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await PropertyApi.getPropertyPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
//打印标签
/**
 * 确认打印
 */
const onPrintConfirm = async () => {
  await Getprinttemplate()
  if (selectedRow.value.length === 0) {
    message.error('请选择要打印的数据')
    return
  }
  printJS({
    printable: 'mypdf', // 要打印内容的id
    type: 'html', // 可以打印html,img详细的可以在官方文档https://printjs.crabbly.com/中查询
    targetStyles: ['*'],
    style: `@page{
      size:auto;margin: 4mm;
    
    }
      @media print {
        .section {
          page-break-before: always !important;
        }
      }
    `, // 打印的内容是没有css样式的，此处需要string类型的css样式
    scanStyles: false,
    onLoadingEnd() {
      // showPrintNode.value = false
    }
  })
}
//获取打印模板
const tagTemplate = ref({
  applyJson: {
    fields: [],
    tag_width: 0,
    tag_height: 0,
    paper_type: '0'
  }
})
const printList = ref([])
const Getprinttemplate = async () => {
  const res = await PropertyTagApi.getPropertyTagPage({
    pageNo: 1,
    pageSize: 100
  })
  if (res.list.length) {
    tagTemplate.value = res.list[0]
    tagTemplate.value.applyJson = JSON.parse(tagTemplate.value.applyJson)
  }
}

/** 资产详情 */
const handlePropertyDetail = async (id: number) => {
  openForm('detail', id)
}

const selectedRow = ref([] as PropertyVO[])
const handleSelectionChange = (val: PropertyVO[]) => {
  console.log(val)
  selectedRow.value = val
}

/**获取分类列表 */
const locationList = ref([])
const getLocationList = async () => {
  const data = await PropertyApi.getPropertyTypeTree({})
  locationList.value = data
}
/** 派发-1 / 退库-2  */
const createHandoutPropertyFormRef = ref()
const returnInventoryFormRef = ref()
const handleCreateHandoutProperty = async (type, row) => {
  if (row.status != 1 && type == 1) {
    message.error('请选择【空闲】状态的资产')
    return
  }

  if (row.status != 2 && type == 2) {
    message.error('请选择【在用】状态的资产')
    return
  }
  let listArray = row ? [row] : []
  if (type == 1) {
    createHandoutPropertyFormRef.value.open('create', listArray)
  } else {
    returnInventoryFormRef.value.open('create', listArray)
  }
}

/** 借出 */
const LendReturnFormRef = ref()
const handleLend = (row: PropertyVO) => {
  if (row.status != 1) {
    message.error('请选择【空闲】状态的资产')
    return
  }
  let listArray = row ? [row] : []
  LendReturnFormRef.value.open(1, 'create', listArray)
}

/** 归还 */
const handleReturn = (row: PropertyVO) => {
  if (row.status != 3) {
    message.error('请选择【借用】状态的资产')
    return
  }
  let listArray = row ? [row] : []
  LendReturnFormRef.value.open(2, 'create', listArray)
}

/** 延长归还 */
const extendedReturnShow = ref(false)
const extendedReturnForm = ref({})
const extendedReturn = (row: PropertyVO) => {
  if (row.status != 3) {
    message.error('请选择【借用】状态的资产')
    return
  }
  extendedReturnForm.value = row
  extendedReturnForm.value.expectRevertTime = ''
  extendedReturnShow.value = true
}
const requestReturn = async () => {
  // await PropertyApi.createLendoutProperty(extendedReturnForm.value)
}
/** 调拨 */
const allotFormRef = ref()
const handleAllot = (row: PropertyVO) => {
  if (row.status != 1) {
    message.error('请选择【空闲】状态的资产')
    return
  }
  let listArray = row ? [row] : []
  allotFormRef.value.open('create', listArray)
}
/** 处置 */
const disposeOfFormRef = ref()
const handleDisposeOf = (row: PropertyVO) => {
  if (row.status != 1) {
    message.error('请选择【空闲】状态的资产')
    return
  }
  let listArray = row ? [row] : []
  disposeOfFormRef.value.open('2', 'create', listArray)
}
/** 操作分发 */
const handleCommand = (command: string, row: PropertyVO) => {
  switch (command) {
    case 'handleDelete':
      handleDelete(row.id)
      break
    case 'handleUpdate':
      openForm('update', row.id)
      break
    case 'handleLend':
      handleLend(row) // 借出
      break
    case 'handleReturn':
      handleReturn(row)
      break
    case 'extendedReturn':
      extendedReturn(row)
      break
    case 'handleAllot':
      handleAllot(row) // 调拨
      break
    case 'handleDisposeOf':
      handleDisposeOf(row) // 处置
      break
    default:
      break
  }
}

/** 批量处理 */
const handleBatch = async (type) => {
  // 获取选中的行
  if (selectedRow.value.length == 0) {
    message.error('请选择要操作的行')
    return
  }
  if (!selectedRow.value.every((item) => item.status === selectedRow.value[0].status)) {
    message.error('请选择相同状态的行')
    return
  }
  switch (type) {
    case 'handleCreateHandoutProperty1':
      console.log(selectedRow.value[0].status)
      if (selectedRow.value[0].status != 1) {
        message.error('请选择【空闲】状态的资产')
        return
      }
      createHandoutPropertyFormRef.value.open('create', selectedRow.value)
      break
    case 'handleCreateHandoutProperty2':
      if (selectedRow.value[0].status != 2) {
        message.error('请选择【在用】状态的资产')
        return
      }
      returnInventoryFormRef.value.open('create', selectedRow.value)
      break
    case 'handleLend':
      if (selectedRow.value[0].status != 1) {
        message.error('请选择【空闲】状态的资产')
        return
      }
      LendReturnFormRef.value.open(1, 'create', selectedRow.value)
      break
    case 'handleReturn':
      if (selectedRow.value[0].status != 3) {
        message.error('请选择【借用】状态的资产')
        return
      }
      LendReturnFormRef.value.open(2, 'create', selectedRow.value)
      break
    case 'handleAllot':
      if (selectedRow.value[0].status != 1) {
        message.error('请选择【空闲】状态的资产')
        return
      }
      allotFormRef.value.open('create', selectedRow.value)
      break
    case 'handleDisposeOf':
      if (selectedRow.value[0].status != 1) {
        message.error('请选择【空闲】状态的资产')
        return
      }
      disposeOfFormRef.value.open('2', 'create', selectedRow.value)
      break
    case 'onPrintConfirm':
      onPrintConfirm()
      break
    default:
      break
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PropertyApi.deleteProperty(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await PropertyApi.exportProperty(queryParams)
    download.excel(data, '资产管理.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
/** 获得位置列表 */
const positionList = ref([])
const getPositionList = async () => {
  const data = await PropertyLocationApi.getPropertyPositionTree({})
  positionList.value = data
}
/** 激活时 **/
onActivated(() => {
  getList()
})
/** 初始化 **/
onMounted(async () => {
  // 获得部门树
  deptTree.value = handleTree(await DeptApi.getSimpleDeptList())
  userOptions.value = await UserApi.getSimpleUserList()
  getPositionList()
  getLocationList()
  getList()
})
</script>
