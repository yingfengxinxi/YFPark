/*
 * This file is part of Qingyun SmartPark.
 *
 * @description  青云智慧园区
 * @link  https://sdqingyun.cn
 * @contact  https://sdqingyun.cn 7*12 9:00-21:00
 */
import { store } from '@/store'
import { defineStore } from 'pinia'
import { getAccessToken, removeToken } from '@/utils/auth'
import { CACHE_KEY, useCache, deleteUserCache } from '@/hooks/web/useCache'
import { getInfo, loginOut, getSpercialSetting } from '@/api/login'
import { get } from 'http'
const message = useMessage() // 消息弹窗

const { wsCache } = useCache()

interface UserVO {
  id: number
  avatar: string
  nickname: string
  deptId: number
}

interface UserInfoVO {
  // USER 缓存
  permissions: string[]
  roles: string[]
  isSetUser: boolean
  user: UserVO
  villageType: string
  deptIdList: any[]
}

export const useUserStore = defineStore('admin-user', {
  state: (): UserInfoVO => ({
    permissions: [],
    roles: [],
    isSetUser: false,
    user: {
      id: 0,
      avatar: '',
      nickname: '',
      deptId: 0
    },
    deptIdList: wsCache.get(CACHE_KEY.DEPTID_LIST) || [],
    villageType: wsCache.get(CACHE_KEY.VILLAGE_TYPE) || '' // 默认园区类型
  }),
  getters: {
    getvillageType(): string {
      return this.villageType
    },
    getDeptIdList(): any[] {
      return this.deptIdList
    },
    getPermissions(): string[] {
      return this.permissions
    },
    getRoles(): string[] {
      return this.roles
    },
    getIsSetUser(): boolean {
      return this.isSetUser
    },
    getUser(): UserVO {
      return this.user
    }
  },
  actions: {
    async setVillageType(payload) {
      if (payload) {
        this.villageType = payload
        wsCache.set(CACHE_KEY.VILLAGE_TYPE, payload)
        console.log(payload, 'payload')
      }
    },
    async setDeptIdList(payload) {
      if (payload) {
        this.deptIdList = payload
        wsCache.set(CACHE_KEY.DEPTID_LIST, payload)
        console.log(payload, 'payload')
      }
    },
    async GetVillageType() {
      // if (payload) {
      //   this.villageType = payload
      //   wsCache.set(CACHE_KEY.VILLAGE_TYPE, payload)
      //   console.log(payload, 'payload')
      // }
      // if (!userInfo) {
      const data = {
        pageNo: 1,
        pageSize: 100,
        uid: this.user.id,
        type: 'deptIdList'
      }
      this.deptIdList = await getSpercialSetting(data)
      console.log(this.deptIdList, 'this.deptIdList')
      if (!this.deptIdList.length) {
        message.error('请先选择项目类型')
      } else {
        console.log(JSON.parse(this.deptIdList[0].content)[0].type, '2345678')
        this.villageType = JSON.parse(this.deptIdList[0].content)[0].type
        console.log(this.villageType, 'this.villageType')
        wsCache.set(CACHE_KEY.VILLAGE_TYPE, JSON.parse(this.deptIdList[0].content)[0].type)
        wsCache.set(CACHE_KEY.DEPTID_LIST, this.deptIdList)
        console.log(CACHE_KEY)
      }
      // }
    },
    async setUserInfoAction() {
      if (!getAccessToken()) {
        this.resetState()
        return null
      }
      let userInfo = wsCache.get(CACHE_KEY.USER)
      if (!userInfo) {
        userInfo = await getInfo()
      }
      this.permissions = userInfo.permissions
      this.roles = userInfo.roles
      this.user = userInfo.user
      this.isSetUser = true
      wsCache.set(CACHE_KEY.USER, userInfo)
      wsCache.set(CACHE_KEY.ROLE_ROUTERS, userInfo.menus)
    },
    async setUserAvatarAction(avatar: string) {
      const userInfo = wsCache.get(CACHE_KEY.USER)
      // NOTE: 是否需要像`setUserInfoAction`一样判断`userInfo != null`
      this.user.avatar = avatar
      userInfo.user.avatar = avatar
      wsCache.set(CACHE_KEY.USER, userInfo)
    },
    async setUserNicknameAction(nickname: string) {
      const userInfo = wsCache.get(CACHE_KEY.USER)
      // NOTE: 是否需要像`setUserInfoAction`一样判断`userInfo != null`
      this.user.nickname = nickname
      userInfo.user.nickname = nickname
      wsCache.set(CACHE_KEY.USER, userInfo)
    },
    async loginOut() {
      await loginOut()
      removeToken()
      deleteUserCache() // 删除用户缓存
      this.resetState()
    },
    resetState() {
      this.permissions = []
      this.roles = []
      this.isSetUser = false
      this.user = {
        id: 0,
        avatar: '',
        nickname: '',
        deptId: 0
      }
    }
  }
})

export const useUserStoreWithOut = () => {
  return useUserStore(store)
}
