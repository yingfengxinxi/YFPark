/**
 * 数据字典工具类
 */
import { useDictStoreWithOut } from '@/store/modules/dict'
import { ElementPlusInfoType } from '@/types/elementPlus'

const dictStore = useDictStoreWithOut()

/**
 * 获取 dictType 对应的数据字典数组
 *
 * @param dictType 数据类型
 * @returns {*|Array} 数据字典数组
 */
export interface DictDataType {
  dictType: string
  label: string
  value: string | number | boolean
  colorType: ElementPlusInfoType | ''
  cssClass: string
}

export interface NumberDictDataType extends DictDataType {
  value: number
}

export const getDictOptions = (dictType: string) => {
  return dictStore.getDictByType(dictType) || []
}

export const getIntDictOptions = (dictType: string): NumberDictDataType[] => {
  // 获得通用的 DictDataType 列表
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  // 转换成 number 类型的 NumberDictDataType 类型
  // why 需要特殊转换：避免 IDEA 在 v-for="dict in getIntDictOptions(...)" 时，el-option 的 key 会告警
  const dictOption: NumberDictDataType[] = []
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: parseInt(dict.value + '')
    })
  })
  return dictOption
}

export const getStrDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = []
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + ''
    })
  })
  return dictOption
}

export const getBoolDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = []
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + '' === 'true'
    })
  })
  return dictOption
}

/**
 * 获取指定字典类型的指定值对应的字典对象
 * @param dictType 字典类型
 * @param value 字典值
 * @return DictDataType 字典对象
 */
export const getDictObj = (dictType: string, value: any): DictDataType | undefined => {
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  for (const dict of dictOptions) {
    if (dict.value === value + '') {
      return dict
    }
  }
}

/**
 * 获得字典数据的文本展示
 *
 * @param dictType 字典类型
 * @param value 字典数据的值
 * @return 字典名称
 */
export const getDictLabel = (dictType: string, value: any): string => {
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  const dictLabel = ref('')
  dictOptions.forEach((dict: DictDataType) => {
    if (dict.value === value + '') {
      dictLabel.value = dict.label
    }
  })
  return dictLabel.value
}

/**
 * 获得字典数据的字典数据
 *
 * @param dictType 字典类型
 * @param label 字典名称
 * @return 字典数据的值
 */
export const getDictValue = (dictType: string, label: any): string => {
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  const dictValue = ref()
  dictOptions.forEach((dict: DictDataType) => {
    if (dict.label === label) {
      dictValue.value = dict.value
    }
  })
  return dictValue.value
}

export enum DICT_TYPE {
  USER_TYPE = 'user_type',
  COMMON_STATUS = 'common_status',
  TERMINAL = 'terminal', // 终端
  DATE_INTERVAL = 'date_interval', // 数据间隔

  // ========== SYSTEM 模块 ==========
  SYSTEM_USER_SEX = 'system_user_sex',
  SYSTEM_MENU_TYPE = 'system_menu_type',
  SYSTEM_ROLE_TYPE = 'system_role_type',
  SYSTEM_DATA_SCOPE = 'system_data_scope',
  SYSTEM_NOTICE_TYPE = 'system_notice_type',
  SYSTEM_LOGIN_TYPE = 'system_login_type',
  SYSTEM_LOGIN_RESULT = 'system_login_result',
  SYSTEM_SMS_CHANNEL_CODE = 'system_sms_channel_code',
  SYSTEM_SMS_TEMPLATE_TYPE = 'system_sms_template_type',
  SYSTEM_SMS_SEND_STATUS = 'system_sms_send_status',
  SYSTEM_SMS_RECEIVE_STATUS = 'system_sms_receive_status',
  SYSTEM_ERROR_CODE_TYPE = 'system_error_code_type',
  SYSTEM_OAUTH2_GRANT_TYPE = 'system_oauth2_grant_type',
  SYSTEM_MAIL_SEND_STATUS = 'system_mail_send_status',
  SYSTEM_NOTIFY_TEMPLATE_TYPE = 'system_notify_template_type',
  SYSTEM_SOCIAL_TYPE = 'system_social_type',

  // ========== INFRA 模块 ==========
  INFRA_BOOLEAN_STRING = 'infra_boolean_string',
  INFRA_JOB_STATUS = 'infra_job_status',
  INFRA_JOB_LOG_STATUS = 'infra_job_log_status',
  INFRA_API_ERROR_LOG_PROCESS_STATUS = 'infra_api_error_log_process_status',
  INFRA_CONFIG_TYPE = 'infra_config_type',
  INFRA_CODEGEN_TEMPLATE_TYPE = 'infra_codegen_template_type',
  INFRA_CODEGEN_FRONT_TYPE = 'infra_codegen_front_type',
  INFRA_CODEGEN_SCENE = 'infra_codegen_scene',
  INFRA_FILE_STORAGE = 'infra_file_storage',
  INFRA_OPERATE_TYPE = 'infra_operate_type',

  // ========== BPM 模块 ==========
  BPM_MODEL_FORM_TYPE = 'bpm_model_form_type',
  BPM_TASK_CANDIDATE_STRATEGY = 'bpm_task_candidate_strategy',
  BPM_PROCESS_INSTANCE_STATUS = 'bpm_process_instance_status',
  BPM_TASK_STATUS = 'bpm_task_status',
  BPM_OA_LEAVE_TYPE = 'bpm_oa_leave_type',
  BPM_PROCESS_LISTENER_TYPE = 'bpm_process_listener_type',
  BPM_PROCESS_LISTENER_VALUE_TYPE = 'bpm_process_listener_value_type',

  // ========== PAY 模块 ==========
  PAY_CHANNEL_CODE = 'pay_channel_code', // 支付渠道编码类型
  PAY_ORDER_STATUS = 'pay_order_status', // 商户支付订单状态
  PAY_REFUND_STATUS = 'pay_refund_status', // 退款订单状态
  PAY_NOTIFY_STATUS = 'pay_notify_status', // 商户支付回调状态
  PAY_NOTIFY_TYPE = 'pay_notify_type', // 商户支付回调状态
  PAY_TRANSFER_STATUS = 'pay_transfer_status', // 转账订单状态
  PAY_TRANSFER_TYPE = 'pay_transfer_type', // 转账订单状态

  // ========== MP 模块 ==========
  MP_AUTO_REPLY_REQUEST_MATCH = 'mp_auto_reply_request_match', // 自动回复请求匹配类型
  MP_MESSAGE_TYPE = 'mp_message_type', // 消息类型

  // ========== Member 会员模块 ==========
  MEMBER_POINT_BIZ_TYPE = 'member_point_biz_type', // 积分的业务类型
  MEMBER_EXPERIENCE_BIZ_TYPE = 'member_experience_biz_type', // 会员经验业务类型

  // ========== MALL - 商品模块 ==========
  PRODUCT_SPU_STATUS = 'product_spu_status', //商品状态

  // ========== MALL - 交易模块 ==========
  EXPRESS_CHARGE_MODE = 'trade_delivery_express_charge_mode', //快递的计费方式
  TRADE_AFTER_SALE_STATUS = 'trade_after_sale_status', // 售后 - 状态
  TRADE_AFTER_SALE_WAY = 'trade_after_sale_way', // 售后 - 方式
  TRADE_AFTER_SALE_TYPE = 'trade_after_sale_type', // 售后 - 类型
  TRADE_ORDER_TYPE = 'trade_order_type', // 订单 - 类型
  TRADE_ORDER_STATUS = 'trade_order_status', // 订单 - 状态
  TRADE_ORDER_ITEM_AFTER_SALE_STATUS = 'trade_order_item_after_sale_status', // 订单项 - 售后状态
  TRADE_DELIVERY_TYPE = 'trade_delivery_type', // 配送方式
  BROKERAGE_ENABLED_CONDITION = 'brokerage_enabled_condition', // 分佣模式
  BROKERAGE_BIND_MODE = 'brokerage_bind_mode', // 分销关系绑定模式
  BROKERAGE_BANK_NAME = 'brokerage_bank_name', // 佣金提现银行
  BROKERAGE_WITHDRAW_TYPE = 'brokerage_withdraw_type', // 佣金提现类型
  BROKERAGE_RECORD_BIZ_TYPE = 'brokerage_record_biz_type', // 佣金业务类型
  BROKERAGE_RECORD_STATUS = 'brokerage_record_status', // 佣金状态
  BROKERAGE_WITHDRAW_STATUS = 'brokerage_withdraw_status', // 佣金提现状态

  // ========== MALL - 营销模块 ==========
  PROMOTION_DISCOUNT_TYPE = 'promotion_discount_type', // 优惠类型
  PROMOTION_PRODUCT_SCOPE = 'promotion_product_scope', // 营销的商品范围
  PROMOTION_COUPON_TEMPLATE_VALIDITY_TYPE = 'promotion_coupon_template_validity_type', // 优惠劵模板的有限期类型
  PROMOTION_COUPON_STATUS = 'promotion_coupon_status', // 优惠劵的状态
  PROMOTION_COUPON_TAKE_TYPE = 'promotion_coupon_take_type', // 优惠劵的领取方式
  PROMOTION_ACTIVITY_STATUS = 'promotion_activity_status', // 优惠活动的状态
  PROMOTION_CONDITION_TYPE = 'promotion_condition_type', // 营销的条件类型枚举
  PROMOTION_BARGAIN_RECORD_STATUS = 'promotion_bargain_record_status', // 砍价记录的状态
  PROMOTION_COMBINATION_RECORD_STATUS = 'promotion_combination_record_status', // 拼团记录的状态
  PROMOTION_BANNER_POSITION = 'promotion_banner_position', // banner 定位

  // ========== CRM - 客户管理模块 ==========
  CRM_AUDIT_STATUS = 'crm_audit_status', // CRM 审批状态
  CRM_BIZ_TYPE = 'crm_biz_type', // CRM 业务类型
  CRM_BUSINESS_END_STATUS_TYPE = 'crm_business_end_status_type', // CRM 商机结束状态类型
  CRM_RECEIVABLE_RETURN_TYPE = 'crm_receivable_return_type', // CRM 回款的还款方式
  CRM_CUSTOMER_INDUSTRY = 'crm_customer_industry', // CRM 客户所属行业
  CRM_CUSTOMER_LEVEL = 'crm_customer_level', // CRM 客户级别
  CRM_CUSTOMER_SOURCE = 'crm_customer_source', // CRM 客户来源
  CRM_PRODUCT_STATUS = 'crm_product_status', // CRM 商品状态
  CRM_PERMISSION_LEVEL = 'crm_permission_level', // CRM 数据权限的级别
  CRM_PRODUCT_UNIT = 'crm_product_unit', // CRM 产品单位
  CRM_FOLLOW_UP_TYPE = 'crm_follow_up_type', // CRM 跟进方式

  // ========== ERP - 企业资源计划模块  ==========
  ERP_AUDIT_STATUS = 'erp_audit_status', // ERP 审批状态
  ERP_STOCK_RECORD_BIZ_TYPE = 'erp_stock_record_biz_type', // 库存明细的业务类型

  // ========== SETTING - 企业设置模块  ==========
  SETTING_TAG_STATUS = 'setting_tag_status', // 企业标签状态

  //  ========== VILLAGE - 建筑楼宇模块  ==========
  PROPERTYRIGHTNATURE = 'PropertyRightNature', // 产权性质
  DECORATION = 'DECORATION', //装修情况
  RESOURCESTATE = 'ResourceState', //资源状态
  HOUSETYPE = 'houseType', // 房源类型
  PRICEUNIT = 'priceUnit', // 价格单位
  VILLAGEPHONESTATUS = 'village_phone_status', // 项目电话状态

  //  ========== BILL 财务  ==========
  RECEIPTEXAMPLEURL = 'RECEIPT_EXAMPLE_URL', // 下载模版
  RECEIPTINFORMATION = 'RECEIPT_INFORMATION', // 收据信息
  PAYMENTRECIPIENT = 'PAYMENT_RECIPIENT', // 交收款方
  PROPERTYINFORMATION = 'PROPERTY_INFORMATION', // 房源信息
  BILLINFORMATION = 'BILL_INFORMATION', // 账单信息
  NOTIFICAIONTEMPLATEJB = 'NOTIFICAION_TEMPLATE_JB', //通知单模版基本信息
  NOTIFICAIONTEMPLATEOWNER = 'NOTIFICAION_TEMPLATE_OWNER', //通知单模版租客信息
  NOTIFICAIONTEMPLATEROOM = 'NOTIFICAION_TEMPLATE_ROOM', //通知单模版房源信息
  NOTIFICAIONTEMPLATEACCOUNT = 'NOTIFICAION_TEMPLATE_ACCOUNT', //通知单模版账户信息
  NOTIFICAIONTEMPLATEOTHER = 'NOTIFICAION_TEMPLATE_OTHER', // 通知单模版其他信息
  NOTICE_SMS_SEND_STATUS = 'notice_sms_send_status', //收款通知短信发送状态
  CHARGE_TYPE = 'CHARGE_TYPE', // 收款类型
  CARD_NUMBER = 'CARD_NUMBER', // 车牌号
  FREE_CAR = 'FREE_CAR', //车辆类型
  VEHICLE_COLOR = 'VEHICLE_COLOR', // 车辆颜色
  PARK_CAR_LOG_TYPE = 'PARK_CAR_LOG_TYPE', //车辆操作类型
  STORE_PACKAGE = 'STORE_PACKAGE', //储值缴费金额
  ADJUST_TYPE = 'ADJUST_TYPE', //机构账单调整 - 调整类型
  ADJUST_MODE = 'ADJUST_MODE', //机构账单调整 - 调整方式
  ADJUST_STATUS = 'ADJUST_STATUS', //机构账单调整 - 调整状态
  BILL_STATUS = 'BILL_STATUS', //机构账单调整 - 账单状态
  LOAN_TYPE = 'LOAN_TYPE', //新建收支流水 - 借贷标
  CURRENCY = 'CURRENCY', //币种
  RECEIPT_STATUS = 'RECEIPT_STATUS', //收据状态
  MATCH_STATUS = 'MATCH_STATUS', // 匹配状态
  BILL_SOURCE = 'BILL_SOURCE', // 账单来源

  //  ========== PARK 停车场 ==========
  PARK_TYPE = 'PARK_TYPE', // 停车场类型
  TEMP_CAR = 'TEMP_CAR', // 临时车政策
  MORE_CAR = 'MORE_CAR', // 一户多车政策
  //  ========== OWNER 租客  ==========
  USERAUDITSTATUS = 'USER_AUDIT_STATUS', // 租客信息
  TENANTUSERIDENTITY = 'TENANT_USER_IDENTITY', //用户身份
  NOTICE_TYPE = 'NOTICE_TYPE', //催缴通知方式
  BUILD_TYPE = 'BUILD_TYPE', //缴费通知单生成方式'
  NOTIFICAION_TEXT = 'NOTIFICAION_TEXT', //通知单文本模板
  REMIT_TYPE = 'REMIT_TYPE', //汇款方式
  TEMPORARY_VEHICIE_CHARGE_TYPE = 'TEMPORARY_VEHICIE_CHARGE_TYPE', //收费方式(收费标准)

  //  ========== CONTRACT 合同  ==========
  BILL_TYPE = 'BILL_TYPE', // 账单类型
  RETREAT_TYPE = 'RETREAT_TYPE', //退租类型
  CONTRACT_STATUS = 'CONTRACT_STATUS', //合同状态
  //设备巡检
  //  ========== DEVICE_INSPECTION  ==========
  PATROL_FORM = 'PATROL_FORM', //巡检表单

  //  ========== PROPERTY 资产管理 ==========
  PROPERTYSTATUS = 'PROPERTYSTATUS', // 资产状态
  PROPERTY_AUDIT_STATUS = 'PROPERTY_AUDIT_STATUS', //资产管理审批
  PROPERTY_TYPE = 'PROPERTY_TYPE', // 资产操作类型
  PROPERTY_SHOW_STATUS = 'PROPERTY_SHOW_STATUS', // 资产显示状态
  PROPERTY_PURCHASETYPE = 'PROPERTY_PURCHASETYPE', //购置方式
  DISPOSEEOF_TYPE = 'DISPOSEEOF_TYPE', //处置类型
  PROPERTY_TAG_TEMPLATE = 'PROPERTY_TAG_TEMPLATE', //标签模版字段

  //  ========== STUFF 耗材管理 ==========
  STUFF_COST = 'STUFF_COST', // 耗材成本计算方式
  STUFF_HAS_RETURN = 'STUFF_HAS_RETURN' // 耗材是否可退库
}
