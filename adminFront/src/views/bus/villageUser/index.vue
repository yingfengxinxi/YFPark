<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form label-position="top" :model="form" ref="queryFormRef" class="queryForm">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="员工姓名:">
            <el-input v-model="form.name" placeholder="请输入员工姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="员工手机号:">
            <el-input v-model="form.phone" placeholder="请输入员工手机号" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所属租客:">
            <el-select v-model="form.ownerId" placeholder="请选择所属租客">
              <el-option
                v-for="dict in onwerIdList"
                :key="dict.id"
                :label="dict.name"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所属建筑:">
            <el-select v-model="form.buildId" placeholder="请选择所属建筑">
              <el-option
                v-for="dict in buildIdList"
                :key="dict.id"
                :label="dict.buildName"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="用户身份:">
            <el-select v-model="form.type" placeholder="请选择用户身份">
              <el-option
                v-for="dict in UserTypeList"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="审核状态:">
            <el-select v-model="form.status" placeholder="请选择审核状态">
              <el-option
                v-for="dict in AuditStatusList"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
            <!-- <el-input v-model="form.status" placeholder="请选择审核状态" /> -->
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <div class="search_box">
            <el-button @click="resetQuery">重置</el-button>
            <el-button type="primary" @click="handleQuery">搜索</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <div class="row">
      <div> 租客员工列表 </div>
      <div>
        <!-- <el-button>入住二维码</el-button> -->
        <el-button type="primary" plain>IC卡管理</el-button>
        <el-button
          type="primary"
          plain
          @click="Import_show = true"
          v-hasPermi="['bus:village-user:import']"
          >租客员工导入</el-button
        >
        <!-- <el-button>租客配置 </el-button> -->
        <el-button type="primary" @click="add_data_button" v-hasPermi="['bus:village-user:create']"
          >添加租客员工</el-button
        >
      </div>
    </div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="所属租客" align="center" prop="ownerName" />
      <el-table-column label="员工姓名" align="center" prop="name" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <!-- 0+房主，10+家属，20+租户，30+物业人员，40+服务人员，50+访客 -->
      <el-table-column label="身份" align="center" prop="type">
        <template #default="scope">
          {{
            getStrDictOptions(DICT_TYPE.TENANTUSERIDENTITY).find(
              (item) => item.value == scope.row.type
            )?.label
          }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <template v-for="dict in AuditStatusList" :key="dict.value">
            <span v-if="scope.row.status == dict.value"> {{ dict.label }}</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary"> IC卡 </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['bus:village-user:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['bus:village-user:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="form.pageNo"
      v-model:limit="form.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 添加修改抽屉 -->
  <Addform
    ref="FormRef"
    v-model:show="show_drawer"
    :type="menu_type"
    @submit="creat_data"
    :id="drawer_data_id"
  />
  <!-- 文件上传 -->
  <Import
    :title="'导入租客员工'"
    v-model:show="Import_show"
    @down-loadmb="downLoad_mb"
    @submit="import_submit"
  />
  <!-- IC卡列表 -->
  <ICcrudList v-if="false" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { VillageUserApi, VillageUserVO } from '@/api/bus/villageUser'
import { OwnerApi } from '@/api/bus/owner/index'
import { BuildingApi } from '@/api/bus/village/building'
import VillageUserForm from './VillageUserForm.vue'
import { F } from 'dist-prod/assets/el-virtual-list-CdvyVBKS'
import Import from '../owner/component/import.vue'
import Addform from './component/addform.vue'
import { type } from '../../../types/auto-imports'
import ICcrudList from './component/ICcrudList.vue'

/** 项目用户/租客 列表 */
defineOptions({ name: 'VillageUser' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { status } from 'nprogress'

const loading = ref(true) // 列表的加载中
const list = ref<VillageUserVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
let form = ref({
  name: '', // 员工姓名
  phone: '', // 员工手机号
  ownerId: '', // 所属租客
  buildId: '', // 所属建筑
  type: '', // 用户身份
  status: '', // 审核状态
  pageNo: 1,
  pageSize: 10
})
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await VillageUserApi.getVillageUserPage(form.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  form.value.pageNo = 1
  getList()
}
const queryFormRef = ref()
/** 重置按钮操作 */
const resetQuery = () => {
  form.value = {
    name: '', // 员工姓名
    phone: '', // 员工手机号
    zk: '', // 所属租客
    ssjz: '', // 所属建筑
    yhsf: '', // 用户身份
    shzt: '', // 审核状态
    pageNo: 1,
    pageSize: 10
  }
  handleQuery()
}

/** 添加/修改操作 */
const FormRef = ref()
const openForm = (type: string, id?: number) => {
  show_drawer.value = true
  menu_type.value = 'edit'
  drawer_data_id.value = id
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await VillageUserApi.deleteVillageUser(id)
    message.success('删除成功')
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
    const data = await VillageUserApi.exportVillageUser(queryParams)
    download.excel(data, '项目用户/租客.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
//抽屉数据
const show_drawer = ref(false)
const drawer_data_id = ref({})
const menu_type = ref('')
const activeIndex = ref('0')
const handleSelect = (key: string, keyPath: string[]) => {
  activeIndex.value = key
  getList()
}
const contactName_options = ref([])
const remoteMethod = (val) => {
  VillageUserApi.getOwnerListByName(val).then((res) => {
    contactName_options.value = res
  })
}

const creat_data = async (res) => {
  let creat_form = JSON.parse(JSON.stringify(res.value))
  creat_form.idcardImg = JSON.stringify(creat_form.idcardImg)

  if (menu_type.value == 'add') {
    VillageUserApi.createVillageUser(creat_form).then(() => {
      show_drawer.value = false
      getList()
    })
  } else {
    VillageUserApi.updateVillageUser(creat_form).then(() => {
      show_drawer.value = false
      getList()
    })
  }
}
const add_data_button = () => {
  menu_type.value = 'add'
  show_drawer.value = true
}
//文件上传
const Import_show = ref(false)
const downLoad_mb = () => {
  VillageUserApi.get_import_template().then((res) => {
    download.excel(res, '租客员工模板.xls')
  })
}
const import_submit = (res) => {
  const formData = new FormData()
  formData.append('file', res.raw)
  VillageUserApi.Fileimport(formData).then((res) => {
    message.success('文件上传成功')
  })
}
const AuditStatusList = ref([] as any[])
const UserTypeList = ref([] as any[])
const buildIdList = ref([] as any[])
const onwerIdList = ref([] as any[])
/** 租客员工状态字典 */
const getUserAuditStatus = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.USERAUDITSTATUS)
    console.log(data, 'data')
    AuditStatusList.value = data
  } finally {
  }
}
/** 用户身份 */
const geUserTypeListStatus = async () => {
  try {
    const data = await getStrDictOptions(DICT_TYPE.TENANTUSERIDENTITY)
    console.log(data, 'data')
    UserTypeList.value = data
  } finally {
  }
}

/** 所属租客 */
const getOwnerList = async () => {
  try {
    const data = await OwnerApi.getOwnerList()
    console.log(data, 'data')
    onwerIdList.value = data
  } finally {
  }
}

/**所属建筑 */
const getBuildList = async () => {
  try {
    const data = await BuildingApi.getBuildList({ pageNo: 1, pageSize: 100 })
    console.log(data, 'data')
    buildIdList.value = data
  } finally {
  }
}
/** 初始化 **/
onMounted(() => {
  getList()
  getUserAuditStatus()
  geUserTypeListStatus()
  getBuildList()
  getOwnerList()
})
</script>
<style lang="scss" scoped>
.search_box {
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  height: 100%;
  padding-bottom: 18px;
  box-sizing: border-box;
}
.row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.uploader_BOX {
  background: #f7f7f7;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 110px;
  height: 110px;
  display: flex;
  justify-content: center;
  align-items: center;
  .img {
    width: 100%;
    height: 100%;
  }
  .plus {
    width: 40px;
    height: 40px;
  }
}
.uploader_zjzp {
  display: flex;
  gap: 16px;
  align-items: center;
  .uploader_BOX {
    width: 160px;
    height: 110px;
    position: relative;
    .plus {
      width: 90px;
      height: 60.43px;
    }
    .zjzp_title {
      text-align: center;
      color: #666;
      position: absolute;
      bottom: 0;
      z-index: 999;
    }
  }
}
</style>
