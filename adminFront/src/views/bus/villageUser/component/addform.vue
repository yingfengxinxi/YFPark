<template>
  <el-drawer v-model="show_drawer" size="900px" @closed="emit('update:show', false)">
    <template #header>
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo !border-none villageMenu row_menu"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="0">基本信息</el-menu-item>
        <el-menu-item index="1" :disabled="props.type == 'add'">用户详细信息</el-menu-item>
      </el-menu>
    </template>
    <template #default>
      <div v-show="activeIndex == 0">
        <el-form label-position="top" :model="creat_form" ref="formRef">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item
                label="姓名:"
                prop="name"
                :rules="[{ required: true, message: '请输入姓名', trigger: 'blur' }]"
              >
                <el-input v-model="creat_form.name" placeholder="请输入姓名" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="手机号:"
                prop="phone"
                :rules="[
                  { required: true, message: '请输入手机号', trigger: 'blur' },
                  {
                    pattern: /^1[3456789]\d{9}$/,
                    message: '手机号格式错误',
                    trigger: 'blur'
                  }
                ]"
              >
                <el-input v-model="creat_form.phone" placeholder="请输入手机号" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="所属租客"
                prop="ownerId"
                :rules="[{ required: true, message: '请选择所属租客', trigger: 'blur' }]"
              >
                <el-select
                  v-model="creat_form.ownerId"
                  placeholder="请选择所属租客"
                  filterable
                  remote
                  reserve-keyword
                  :remote-method="getOwnerList"
                >
                  <el-option
                    v-for="item in ownerIdList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="是否为默认租客住户联系人:"
                prop="isDefault"
                :rules="[
                  { required: true, message: '请选择是否为默认租客住户联系人', trigger: 'blur' }
                ]"
              >
                <el-select
                  v-model="creat_form.isDefault"
                  placeholder="请选择是否为默认租客住户联系人"
                >
                  <el-option label="是" :value="1" />
                  <el-option label="否" :value="0" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12" v-if="props.type == 'add'">
              <el-form-item
                label="身份:"
                prop="villageType"
                :rules="[{ required: true, message: '请选择身份', trigger: 'blur' }]"
              >
                <el-select v-model="creat_form.villageType" placeholder="请选择身份">
                  <el-option
                    v-for="dict in getStrDictOptions(DICT_TYPE.TENANTUSERIDENTITY)"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱:">
                <el-input v-model="creat_form.email" placeholder="请输入邮箱" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="证件类型:">
                <el-select v-model="creat_form.idcardType">
                  <el-option label="身份证" value="idcard" />
                  <el-option label="港澳台通行证" value="gangaotai" />
                  <el-option label="护照" value="passport" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="证件号码:">
                <el-input
                  v-model="creat_form.idcard"
                  placeholder="请输入证件号码"
                  @change="idcard_change"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="出生日期:">
                <div style="display: flex; gap: 8px">
                  <el-input v-model="creat_form.birthYear" placeholder="出生年">
                    <template #append>年</template>
                  </el-input>
                  <el-input v-model="creat_form.birthMonth" placeholder="出生月">
                    <template #append>月</template>
                  </el-input>
                  <el-input v-model="creat_form.birthDay" placeholder="出生日">
                    <template #append>日</template>
                  </el-input>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="性别:"
                prop="sex"
                :rules="[{ required: true, message: '请选择性别', trigger: 'blur' }]"
              >
                <el-select v-model="creat_form.sex">
                  <el-option label="男" :value="1" />
                  <el-option label="女" :value="2" />
                  <el-option label="未知" :value="0" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态:">
                <el-select
                  v-model="creat_form.status"
                  placeholder="请选择状态"
                  clearable
                  :disabled="props.type == 'add'"
                >
                  <el-option
                    v-for="dict in getStrDictOptions(DICT_TYPE.USERAUDITSTATUS)"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="人脸照片">
                <el-upload
                  class="uploader_BOX"
                  :on-change="faceChange"
                  :show-file-list="false"
                  :auto-upload="false"
                >
                  <img v-if="creat_form.photo" :src="creat_form.photo" alt="" class="img" />
                  <img src="../image/plus.png" v-else class="plus" alt="" />
                </el-upload>
              </el-form-item>

              <el-form-item label="证件照片:">
                <div class="uploader_zjzp">
                  <el-upload
                    class="uploader_BOX"
                    :on-change="zjzp_renxiangChange"
                    :show-file-list="false"
                    :auto-upload="false"
                  >
                    <img
                      v-if="creat_form.idcardImg.front_url"
                      :src="creat_form.photo"
                      alt=""
                      class="img"
                    />
                    <div v-if="!creat_form.idcardImg.front_url" class="zjzp_title"
                      >上传身份证人面像</div
                    >
                    <img
                      src="../image/renxiang.png"
                      v-if="!creat_form.idcardImg.front_url"
                      class="plus"
                      alt=""
                    />
                  </el-upload>
                  <el-upload
                    class="uploader_BOX"
                    :on-change="zjzp_guohuiChange"
                    :show-file-list="false"
                    :auto-upload="false"
                  >
                    <img
                      v-if="creat_form.idcardImg.back_url"
                      :src="creat_form.photo"
                      alt=""
                      class="img"
                    />
                    <div v-if="!creat_form.idcardImg.back_url" class="zjzp_title"
                      >上传身份证国徽像</div
                    >
                    <img
                      src="../image/guohui.png"
                      v-if="!creat_form.idcardImg.back_url"
                      class="plus"
                      alt=""
                    />
                  </el-upload>
                  <el-upload
                    class="uploader_BOX"
                    :on-change="zjzp_shouchiChange"
                    :show-file-list="false"
                    :auto-upload="false"
                  >
                    <img
                      v-if="creat_form.idcardImg.hand_url"
                      :src="creat_form.photo"
                      alt=""
                      class="img"
                    />
                    <div v-if="!creat_form.idcardImg.hand_url" class="zjzp_title"
                      >上传手持身份证像</div
                    >
                    <img
                      src="../image/shouchi.png"
                      v-if="!creat_form.idcardImg.hand_url"
                      class="plus"
                      alt=""
                    />
                  </el-upload>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div v-show="activeIndex == 1">
        <el-form label-position="top" :model="active2" ref="active2_ref">
          <el-row :gutter="20">
            <template v-for="(item, index) in changeData" :key="index">
              <el-col :span="item.inputName == '技能证书' ? 24 : 8" v-if="item.isEnable == 1">
                <el-form-item
                  :label="item.inputNameCus"
                  :prop="`${item.fieldName}`"
                  :rules="[
                    {
                      required: item.isRequired == 1 ? true : false,
                      message: `请输入${item.inputName}`,
                      trigger: 'blur'
                    }
                  ]"
                >
                  <el-input
                    v-if="item.inputType == 'text' || item.inputType == 'number'"
                    v-model="active2[`${item.fieldName}`]"
                    :placeholder="`请输入${item.inputName}`"
                  />
                  <el-select
                    v-if="item.inputType == 'select'"
                    v-model="active2[`${item.fieldName}`]"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="items in JSON.parse(item.defaultFieldValue)"
                      :key="items.id"
                      :label="items.value"
                      :value="Number(items.id)"
                    />
                  </el-select>
                  <el-select
                    v-if="item.inputType == 'multi_select'"
                    v-model="active2[`${item.fieldName}`]"
                    placeholder="请选择"
                    multiple
                  >
                    <el-option
                      v-for="items in JSON.parse(item.defaultFieldValue)"
                      :key="items.id"
                      :label="items.value"
                      :value="items.id.toString()"
                    />
                  </el-select>
                  <el-date-picker
                    v-model="active2[`${item.fieldName}`]"
                    type="date"
                    v-if="item.inputType == 'date'"
                    placeholder="选择日期"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                  />
                  <!-- 技能证书 -->
                  <el-table :data="tableData" v-if="item.inputType == 'json'">
                    <el-table-column prop="name" label="技能证书名称">
                      <template #default="{ row }">
                        <el-input v-model="row.name" />
                      </template>
                    </el-table-column>
                    <el-table-column prop="url" label="技能证书图片">
                      <template #default="{ row }">
                        <el-upload
                          class="uploader_BOX"
                          :on-change="Change_jnzs"
                          :show-file-list="false"
                          :auto-upload="false"
                          @click="now_index = row.index"
                        >
                          <img v-if="row.url" :src="row.url" alt="" class="img" />
                          <img src="../image/plus.png" v-else class="plus" alt="" />
                        </el-upload>
                      </template>
                    </el-table-column>
                    <el-table-column prop="url" label="实施部门(单位)">
                      <template #default="{ row }">
                        <el-input v-model="row.depart" />
                      </template>
                    </el-table-column>
                    <el-table-column prop="url" label="资格类别">
                      <template #default="{ row }">
                        <el-input v-model="row.skill_type" />
                      </template>
                    </el-table-column>
                    <el-table-column prop="url" label="操作">
                      <template>
                        <el-button type="danger" size="mini" @click="detail">删除</el-button>
                      </template>
                    </el-table-column>
                    <template #append>
                      <div class="add_row" @click="add_click"> 添加 </div>
                    </template>
                  </el-table>
                </el-form-item>
              </el-col>
            </template>
          </el-row>
        </el-form>
      </div>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="emit('update:show', false)">取消</el-button>
        <el-button type="primary" @click="creat_data_form">保存</el-button>
      </div>
    </template>
  </el-drawer>
</template>
<script setup lang="ts">
import { VillageUserApi } from '@/api/bus/villageUser'
import { OwnerApi } from '@/api/bus/owner'
import { UserFieldExtendApi } from '@/api/bus/userFieldExtend'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'

const props = withDefaults(
  defineProps<{
    show: boolean
    type: 'add' | 'edit' | ''
    id: string | number
    ownerId: string | number
  }>(),
  {
    show: false,
    type: '',
    id: '',
    ownerId: ''
  }
)
const tableData: any = ref([])
const changeData: any = ref([])
const activeIndex = ref('0')
const show_drawer = ref(props.show)
const emit = defineEmits(['update:show', 'updata:type', 'submit'])
const creat_form = ref({
  name: '', // 姓名
  phone: '', // 手机号
  ownerId: '', // 所属租客
  villageType: '', // 身份
  email: '', // 邮箱
  idcardType: 'idcard', // 证件类型
  idcard: '', // 证件号码
  birthYear: '', // 出生年
  birthMonth: '', // 出生月
  birthDay: '', // 出生日
  sex: null, // 性别
  status: '', // 状态
  photo: '', // 人脸照片
  isDefault: '', // 是否为默认租客住户联系人

  idcardImg: {
    front_url: '', // 身份证正面
    back_url: '', // 身份证反面
    hand_url: '' // 手持身份证
  }
})
const contactName_options = ref([])
watch(
  () => props.show,
  (val) => {
    getUserFieldExtendInfo()
    console.log('显示', props)
    activeIndex.value = '0'
    show_drawer.value = val
    creat_form.value = {
      idcardImg: {}
    }
    tableData.value = []
    if (props.type == 'add') {
      creat_form.value.status = '1'
      if (props.ownerId) {
        creat_form.value.ownerId = props.ownerId
      }
    }
    if (props.id && props.type == 'edit') {
      console.log(props.id, 'props.id')
      VillageUserApi.getVillageUser(props.id).then((res) => {
        //基本信息列表
        creat_form.value = {
          id: res?.id,
          name: res?.name || '',
          phone: res?.phone || '',
          ownerId: res?.ownerId || '',
          villageType: res?.villageType || '',
          email: res?.email || '',
          idcardType: res?.idcardType || '',
          idcard: res?.idcard || '',
          birthYear: res?.birthYear || '',
          birthMonth: res?.birthMonth || '',
          birthDay: res?.birthDay || '',
          sex: res.sex,
          status: res?.status || '',
          photo: res?.photo || '',
          isDefault: res?.isDefault || '',
          userId: res?.userId || '',
          villageId: res?.villageId || '0'
        }
        const idcardImg = JSON.parse(res.idcardImg)
        creat_form.value.idcardImg = {
          front_url: idcardImg?.front_url || '',
          back_url: idcardImg?.back_url || '',
          hand_url: idcardImg?.hand_url || ''
        }
        // 通过villageUserId获得用户信息扩展
        console.log(res.userId)
        VillageUserApi.getUserExtendsByUser({
          villageUserId: res.userId
        }).then((even) => {
          if (!even) {
            return
          }
          active2.value = even
          if (even?.skillCertificate) {
            tableData.value = JSON.parse(even.skillCertificate)
          }
          active2.value.chronic = even.chronic.split(',')
        })
        console.log(creat_form.value)
      })
    }
  }
)
watch(
  () => props.ownerId,
  (val) => {
    creat_form.value.ownerId = val
  },
  {
    immediate: true,
    deep: true
  }
)
const handleSelect = (key: string, keyPath: string[]) => {
  activeIndex.value = key
}
const faceChange = (file: any) => {
  const faceimg = new FormData()
  faceimg.append('file', file.raw)
  OwnerApi.uploadFile(faceimg).then((res) => {
    creat_form.value.photo = res.data
  })
}
const zjzp_renxiangChange = (file: any) => {
  const zjzp_renxiang = new FormData()
  zjzp_renxiang.append('file', file.raw)
  OwnerApi.uploadFile(zjzp_renxiang).then((res) => {
    creat_form.value.idcardImg.front_url = res.data
  })
}
const zjzp_guohuiChange = (file: any) => {
  const zjzp_guohui = new FormData()
  zjzp_guohui.append('file', file.raw)
  OwnerApi.uploadFile(zjzp_guohui).then((res) => {
    creat_form.value.idcardImg.back_url = res.data
  })
}
const zjzp_shouchiChange = (file: any) => {
  const zjzp_shouchi = new FormData()
  zjzp_shouchi.append('file', file.raw)
  OwnerApi.uploadFile(zjzp_shouchi).then((res) => {
    creat_form.value.idcardImg.hand_url = res.data
  })
}
const formRef = ref()
const active2_ref = ref()
const creat_data_form = async () => {
  await formRef.value.validate((valid) => {
    if (valid) {
      if (props.type == 'edit') {
        active2_ref.value.validate((valid2) => {
          if (valid2) {
            active2.value.ownerId = creat_form.value.ownerId || ''
            active2.value.villageUserId = creat_form.value.userId || ''
            active2.value.villageId = creat_form.value.villageId || '0'
            if (typeof active2.value.chronic != 'string') {
              active2.value.chronic = active2.value.chronic.join(',')
            }
            active2.value.skillCertificate = JSON.stringify(tableData.value)
            if (active2.value.id) {
              UserFieldExtendApi.updateUserFieldExtendList(active2.value).then((res) => {
                console.log(res)
              })
            } else {
              UserFieldExtendApi.createUserFieldExtendList(active2.value).then((res) => {
                console.log(res)
              })
            }
            emit('submit', creat_form)
          }
        })
      } else {
        emit('submit', creat_form)
      }
    } else {
      activeIndex.value = '0'
    }
  })
}
const active2 = ref({})
const idcard_change = (val) => {
  console.log([...val].length)
  if ([...val].length == 18) {
    if (creat_form.value.idcardType == 'idcard') {
      const year = val.slice(6, 10)
      const month = val.slice(10, 12)
      const day = val.slice(12, 14)
      creat_form.value.birthYear = year
      creat_form.value.birthMonth = month
      creat_form.value.birthDay = day
      console.log(creat_form.value)
    }
  }
}
const now_index = ref('')
const add_click = () => {
  tableData.value.push({
    name: '',
    url: '',
    depart: '',
    skill_type: '',
    index: tableData.value.length
  })
}
const detail = (index) => {
  tableData.value.splice(index, 1)
}
const Change_jnzs = (file: any) => {
  const faceimg = new FormData()
  faceimg.append('file', file.raw)
  OwnerApi.uploadFile(faceimg).then((res) => {
    tableData.value[now_index.value].url = res.data
  })
}
const getUserFieldExtendInfo = async () => {
  const res = await UserFieldExtendApi.getUserFieldExtendList({})
  if (res) {
    changeData.value = res
  }
}
//获取租客列表
const ownerIdList = ref([])
const getOwnerList = async (query: string) => {
  OwnerApi.getOwnerListByName(query).then((res) => {
    ownerIdList.value = res
  })
}
onMounted(() => {
  getOwnerList('')
})
</script>
<style lang="scss" scoped>
.villageMenu .el-menu-item:hover,
.villageMenu .el-menu-item:focus {
  background: none;
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
.add_row {
  text-align: center;
}
</style>
