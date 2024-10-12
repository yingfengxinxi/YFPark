<!--
 * This file is part of Qingyun SmartPark.  
 *   
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
-->
<template>
  <el-dialog v-model="investmenShow" title="选择部门/岗位">
    <el-alert
      title="由于人员会频繁因为离职、请假、调离等原因变动，所以系统不采取选择人员的方式，而是采取了部门+岗位双配置的方式。未来只要新员工归属到此部门中的此岗位，则会自动被选择，无需手动选择人员。"
      type="info"
      closable
      class="custom-alert"
    />
    <el-row :gutter="20" class="m-t-20px">
      <el-col :span="12">
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <span class="font-size-14px"> 选择部门 </span>
          </template>
          <el-tree
            :data="[]"
            :props="{ label: 'name', children: 'children' }"
            node-key="id"
            default-expand-all
            :expand-on-click-node="false"
          />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <span class="font-size-14px"> 选择岗位 </span>
          </template>
          <el-tree
            :data="[]"
            :props="{ label: 'name', children: 'children' }"
            node-key="id"
            default-expand-all
            :expand-on-click-node="false"
          />
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card
          class="!border-rd-4px w-100% box-border font-size-14px m-t-20px"
          style="border: 1px solid #f0f0f0 !important"
          shadow="never"
        >
          <template #header>
            <span class="font-size-14px">
              人员预览<el-tooltip
                content="列表展示的是此时的人员情况。后续会随着部门/岗位下人员的变化（新增、调离、离职等），调用的人员数据会自动变化。"
                placement="top"
              >
                <Icon class="m-l-5px" icon="fa:question-circle-o" />
              </el-tooltip>
            </span>
          </template>
          <el-row :gutter="20">
            <el-col :span="4" class="!flex flex-col flex-items-center m-b-20px">
              <!-- <el-avatar
                v-if="UserDetail.userInfo.avatar !== ''"
                size="large"
                style="width: 60px; height: 60px; line-height: 60px; font-size: 24px"
                :src="UserDetail.userInfo.avatar"
              >
              </el-avatar> -->

              <!-- <el-avatar
                v-else
                size="large"
                style="width: 60px; height: 60px; line-height: 60px; font-size: 24px"
              >
                {{ UserDetail.userInfo.realName.charAt(0) }}
              </el-avatar> -->
              <el-avatar
                size="large"
                class="!bg-#1890ff m-0-auto"
                style="width: 32px; height: 32px; line-height: 40px; font-size: 14px"
              >
                于
              </el-avatar>
              <div class="text-center mt-10px">于得涛</div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="investmenShow = false">取消</el-button>
        <el-button type="primary" @click="submitContract()"> 确定 </el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
const investmenShow = ref(false)
defineOptions({ name: 'InvestmenDialog' })
/** 打开抽屉 */
const open = async (type: string, id?: number) => {
  console.log('888')
  investmenShow.value = true
}
const submitContract = () => {
  investmenShow.value = false
  emits('success', investmenShow.value)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style lang="scss" scoped>
.custom-alert {
  background-color: #f0f9ff;
  border: 1px solid #a3d6ff;
  color: #000000d9; /* 如果需要也可以改变文字颜色 */
  padding-right: 30px;
}
</style>
