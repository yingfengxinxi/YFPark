<template>
  <ContentWrap class="page m-10px" v-if="ownerId">
    <div class="flex justify-between items-center">
      <div class="c-#000000a6 fw-600 font-size-15px">{{ ownerInfo?.name }}</div>
      <div
        class="c-#2681f3 hover-c-#52a5ff font-size-14px cursor-pointer"
        @click="TenantDetailsBtn(ownerInfo.id, ownerInfo.isPersonal)"
        >租客详情</div
      >
    </div>
    <div class="flex justify-between items-center m-t-10px">
      <div class="text-14px">租客员工列表</div>
      <el-button type="primary" @click="add_data_button">
        <Icon icon="ep:plus" />
        租客员工
      </el-button>
    </div>
    <el-table :data="table_list">
      <el-table-column label="姓名" width="200" align="center" prop="name" />

      <el-table-column
        label="身份"
        align="center"
        prop="type"
        :formatter="tableColumnEmptyPlaceholder"
      >
        <template #default="scope">
          {{
            getStrDictOptions(DICT_TYPE.TENANTUSERIDENTITY).find(
              (item) => item.value == scope.row.type
            )?.label || '--'
          }}
        </template>
      </el-table-column>
      <el-table-column
        label="电话号码"
        width="200"
        align="center"
        prop="phone"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column
        label="邮箱"
        width="200"
        align="center"
        prop="email"
        :formatter="tableColumnEmptyPlaceholder"
      />
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
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
  </ContentWrap>
  <el-empty class="bg-#fff m-10px" :image-size="80" description="暂无数据" v-else />

  <TenantDetails ref="TenantDetailsRef" />
  <!-- 添加修改抽屉 -->
  <Addform
    ref="FormRef"
    v-model:show="show_drawer"
    :type="menu_type"
    @submit="creat_data"
    :id="drawer_data_id"
    :ownerId="ownerId"
  />
</template>
<script setup lang="ts">
import { OwnerApi } from '@/api/bus/owner'
import TenantDetails from '@/views/village/managementCenter/TenantDetails.vue'
import { VillageUserApi } from '@/api/bus/villageUser'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import Addform from '@/views/bus/villageUser/component/addform.vue'
import { tableColumnEmptyPlaceholder } from '@/utils/index'
const message = useMessage() // 消息弹窗

const props = defineProps({
  roomId: {
    type: Number,
    default: 0
  },
  info: {
    type: Object,
    default: () => {}
  },
  Id: {
    type: Number,
    default: 0
  }
})
const ownerInfo = ref({})
const roomId = ref(props.roomId)
const ownerId = ref(0)
const OwnerLoading = ref(false)
const getOwnerInfo = async () => {
  OwnerLoading.value = true
  try {
    const data = await OwnerApi.getByRoomIdOwnerList(roomId.value)
    if (data.length) {
      ownerInfo.value = data[0]
      ownerId.value = data[0].id
      getUserList()
    }
  } finally {
    OwnerLoading.value = false
  }
}
//获得租客员工
const table_list = ref([])
const getUserList = async () => {
  try {
    const data = await VillageUserApi.getVillageUserPage({
      ownerId: ownerId.value
    })
    table_list.value = data.list
  } catch (error) {
    console.log(error)
  }
}
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
const show_drawer = ref(false)
const menu_type = ref('')
const drawer_data_id = ref(0)
const openForm = (type: string, id?: number) => {
  show_drawer.value = true
  menu_type.value = 'edit'
  drawer_data_id.value = id
}
const add_data_button = () => {
  menu_type.value = 'add'
  show_drawer.value = true
}
const creat_data = async (res) => {
  let creat_form = JSON.parse(JSON.stringify(res.value))
  creat_form.idcardImg = JSON.stringify(creat_form.idcardImg)

  if (menu_type.value == 'add') {
    VillageUserApi.createVillageUser(creat_form).then(() => {
      show_drawer.value = false
      // getList()
      getUserList()
    })
  } else {
    VillageUserApi.updateVillageUser(creat_form).then(() => {
      show_drawer.value = false
      // getList()
      getUserList()
    })
  }
}
watch(
  () => props.roomId,
  (val) => {
    roomId.value = val

    if (JSON.stringify(val)) {
      getOwnerInfo()
    }
  },
  {
    immediate: true,
    deep: true
  }
)
watch(
  () => props.Id,
  (val) => {
    if (JSON.stringify(val)) {
      ownerId.value = val
      getOwnerInfo()
    }
  },
  {
    immediate: true,
    deep: true
  }
)
//租客详情
const TenantDetailsRef = ref()
const TenantDetailsBtn = (id: number, type: number) => {
  TenantDetailsRef.value.open(id, type)
}
onMounted(() => {})
</script>
