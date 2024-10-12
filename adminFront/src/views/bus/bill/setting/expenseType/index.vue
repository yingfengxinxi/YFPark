<template>
  <div class="page">
    <el-card shadow="never">
      <div class="text-20px font-600"> 费用类型设置 </div>
      <div class="pt-8px text-14px">
        用户可以根据自身业务需求自定义不同类型的费用类型,还可以将同类型的费用放至同一指定分类下,以便更好地进行费用管理
      </div>
    </el-card>
    <div class="flex justify-between mt-18px w-100% bg-white p-18px box">
      <div class="w-20% pos-relative">
        <div class="text-16px font-600"> 费用分类 </div>
        <el-input placeholder="请输入分类" class="mt-18px" v-model="typename">
          <template #append>
            <Icon icon="ep:search" @click="orgBillCostCategory" />
          </template>
        </el-input>
        <div class="list flex flex-col pt-8px">
          <div
            class="p-4px text-14px cursor-pointer flex justify-between py-10px"
            @click="selecttable({ id: '' })"
            :class="{ active: form.categoryId == '' }"
            >所有</div
          >
          <div
            v-for="(item, index) in orgBillCostCategoryList"
            :key="index"
            class="p-4px text-14px cursor-pointer flex justify-between py-10px"
            @click="selecttable(item)"
            :class="{ active: form.categoryId == item.id }"
          >
            {{ item.name }}
            <div class="pos-relative">
              <el-dropdown style="border: none !important">
                <span>
                  <Icon
                    icon="ep:more-filled"
                    color="#999"
                    class="transform-rotate-45 transform-translate-y-2px"
                    v-if="item.isRoot == '0'"
                  />
                </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item>
                      <el-button
                        type="danger"
                        text
                        @click="delType(item)"
                        v-hasPermi="['bus:orgBillCostCategory:delete']"
                        >删 除</el-button
                      >
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <el-button
                        type="primary"
                        text
                        @click="changeType(item)"
                        v-hasPermi="['bus:orgBillCostCategory:update']"
                        >编 辑</el-button
                      >
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
        </div>
        <div
          class="pos-absolute pos-bottom--18px pos-left--18px addtype flex items-center justify-center"
          @click="addType"
          v-hasPermi="['bus:orgBillCostCategory:create']"
          ><Icon icon="ep:plus" class="mr-4px" /> 添加分类
        </div>
      </div>
      <div class="w-78% rightBox">
        <div class="flex items-center justify-between">
          <el-input
            placeholder="请输入费用名称"
            class="w-50px"
            style="width: 240px"
            v-model="form.name"
          >
            <template #append>
              <Icon icon="ep:search" @click="getCostName" />
            </template>
          </el-input>
          <el-button
            type="primary"
            class="ml-18px"
            @click="addTypeItem"
            v-hasPermi="['bus:orgBillCostType:create']"
            ><Icon icon="ep:plus" class="mr-4px" /> 新建费用类型
          </el-button>
        </div>
        <el-table :data="getCostNameList">
          <el-table-column label="费用类型" align="center" prop="costType">
            <template #default="{ row }">
              {{ orgBillCostCategoryList.find((item) => item.id == row.categoryId)?.name }}
            </template>
          </el-table-column>
          <el-table-column label="所属分类" align="center" prop="name" />
          <el-table-column align="center" prop="isImportant">
            <template #header>
              <div class="flex items-center gap-1 justify-center"
                >特别重要

                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="特别重要的费用类型，若欠费将会影响业务的使用。例如门锁、人脸门禁等将会失效。"
                  placement="top"
                >
                  <Icon icon="ep:info-filled" />
                </el-tooltip> </div
            ></template>
            <template #default="{ row }">
              {{ row.isImportant == '1' ? '是' : '否' }}
            </template>
          </el-table-column>
          <el-table-column label="排序" align="center" prop="sort" />
          <el-table-column label="状态" align="center" prop="status">
            <template #default="{ row }">
              <el-switch
                v-model="row.status"
                active-value="1"
                inactive-value="2"
                @change="changeStatus(row)"
              />
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="{ row }">
              <el-button
                type="primary"
                text
                size="mini"
                @click="changeTypeitem(row.id)"
                v-hasPermi="['bus:orgBillCostType:update']"
              >
                编辑
              </el-button>
              <el-button
                type="danger"
                text
                size="mini"
                @click="delTypeitem(row.id)"
                v-hasPermi="['bus:orgBillCostType:delete']"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="total"
          v-model:page="form.pageNo"
          v-model:limit="form.pageSize"
          @pagination="getCostName"
        />
      </div>
    </div>
  </div>
  <!-- 新镇费用分类 -->
  <AddType ref="addType_ref" @success="orgBillCostCategory" />
  <!-- 新增费用类型 -->
  <AddTypeItem ref="addTypeItem_ref" @success="getCostName" />
</template>
<script lang="ts" setup>
import { Api, VO } from '@/api/contract/contractList/index'
import AddType from './addType.vue'
import AddTypeItem from './addTypeitem.vue'
import { ElMessage, ElMessageBox } from 'element-plus'
const typename = ref('')
//获取费用账单分类
const orgBillCostCategoryList = ref([])

const orgBillCostCategory = () => {
  Api.getCostTypeList({
    name: typename.value
  }).then((res: any) => {
    orgBillCostCategoryList.value = res
  })
}
//获得费用名称
const getCostNameList = ref([])
const getCostName = () => {
  Api.getCostNameList(form.value).then((res: any) => {
    getCostNameList.value = res.list
    total.value = res.total
  })
}
//添加分类
const addType_ref = ref()
const addType = () => {
  addType_ref.value.open()
}
//删除分类
const delType = (item: any) => {
  ElMessageBox.confirm('是否确认删除此分类?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    Api.deleteCostType(item.id).then((res: any) => {
      ElMessage.success('删除成功')
      if (res) {
        orgBillCostCategory()
      }
    })
  })
}
//编辑分类
const changeType = (item: any) => {
  addType_ref.value.open(item.id)
}
onMounted(() => {
  orgBillCostCategory() //获得账单分类
  getCostName() //获得费用名称
})
// ====================================
// 右侧费用类型
const form = ref({
  name: '',
  pageNo: 1,
  pageSize: 10,
  categoryId: ''
})
const total = ref(0)
const addTypeItem_ref = ref()
const addTypeItem = () => {
  addTypeItem_ref.value.open()
}
const changeTypeitem = (id: number) => {
  addTypeItem_ref.value.open(id)
}
const delTypeitem = (id: number) => {
  ElMessageBox.confirm('是否确认删除此费用类型?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    Api.deleteCostName(id).then((res: any) => {
      ElMessage.success('删除成功')
      if (res) {
        getCostName()
      }
    })
  })
}
const changeStatus = (row: any) => {
  Api.updateCostName(row).then((res: any) => {
    if (res) {
      ElMessage.success('操作成功')
    }
  })
}
const selecttable = (item: any) => {
  if (form.value.categoryId == item.id) {
    form.value.categoryId = ''
    getCostName()
    return
  }
  form.value.categoryId = item.id
  getCostName()
}
</script>
<style lang="scss" scoped>
.list {
  overflow: auto;
  max-height: calc(100vh - 395px);
  overflow-x: hidden;
}
.box {
  border-radius: 8px;
  max-height: calc(100vh - 275px);
  min-height: calc(100vh - 275px);
  overflow: hidden;
  max-width: calc(100% - 40px);
}
.addtype {
  text-align: center;
  color: #409eff;
  font-size: 14px;
  width: 100%;
  padding: 10px;
  cursor: pointer;
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
}
.rightBox {
  overflow-y: scroll;
}
.rightBox::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}
.active {
  background: #cceaff;
}
</style>
<style>
.setupStyle .el-dropdown-link:focus {
  outline: none;
}
</style>
