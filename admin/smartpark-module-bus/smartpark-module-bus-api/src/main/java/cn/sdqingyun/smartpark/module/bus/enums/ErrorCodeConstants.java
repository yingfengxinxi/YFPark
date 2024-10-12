package cn.sdqingyun.smartpark.module.bus.enums;

import cn.sdqingyun.smartpark.framework.common.exception.ErrorCode;

/**
 * Bus 错误码枚举类
 * <p>
 * bpm 系统，使用 1-009-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 学生  ==========
    ErrorCode DEMO_STUDENT_NOT_EXISTS = new ErrorCode(2_001_001_001, "学生不存在");
    // ========== 机构  ==========
    ErrorCode ORG_NOT_EXISTS = new ErrorCode(2_001_001_002, "机构不存在");
    // ========== 楼宇标签  ==========
    ErrorCode TAG_BUILD_NOT_EXISTS = new ErrorCode(2_001_001_003, "楼宇标签不存在");
    // ========== 合同标签  ==========
    ErrorCode TAG_CONTRACT_NOT_EXISTS = new ErrorCode(2_001_001_004, "合同标签不存在");
    // ========== 房源标签  ==========
    ErrorCode TAG_HOUSE_NOT_EXISTS = new ErrorCode(2_001_001_005, "房源标签不存在");
    // ========== 行业分类标签  ==========
    ErrorCode TAG_INDUSTRY_NOT_EXISTS = new ErrorCode(2_001_001_006, "行业分类标签不存在");
    // ========== 租客/业主标签标签  ==========
    ErrorCode TAG_OWNER_NOT_EXISTS = new ErrorCode(2_001_001_007, "租客/业主标签不存在");
    // ========== 退租原因标签  ==========
    ErrorCode TAG_TERMINATION_NOT_EXISTS = new ErrorCode(2_001_001_008, "退租原因标签不存在");
    // ========== 项目标签  ==========
    ErrorCode TAG_VILLAGE_NOT_EXISTS = new ErrorCode(2_001_001_009, "项目标签不存在");
    // ========== 机构合同费用条款附加  ==========
    ErrorCode EXPENSE_CLAUSE_NOT_EXISTS = new ErrorCode(2_001_001_010, "机构合同费用条款附加不存在");
    // ========== 机构合同  ==========
    ErrorCode _NOT_EXISTS = new ErrorCode(2_001_001_011, "机构合同不存在");
    // ========== 租客信息  ==========
    ErrorCode OWNER_NOT_EXISTS = new ErrorCode(2_001_001_012, "租客信息不存在");
    // ========== 项目楼栋  ==========
    ErrorCode BUILD_NOT_EXISTS = new ErrorCode(2_001_001_013, "项目楼栋不存在");
    // ========== 项目租控管理菜单  ==========
    ErrorCode CONTROL_MENU_NOT_EXISTS = new ErrorCode(2_001_001_014, "项目租控管理菜单不存在");
    // ========== 项目租控管理菜单排序  ==========
    ErrorCode CONTROL_MENU_SORT_NOT_EXISTS = new ErrorCode(2_001_001_015, "项目租控管理菜单排序不存在");
    // ========== 项目IC卡表（可能会绑定人员，因不同设备需要而定）  ==========
    ErrorCode ICCARD_NOT_EXISTS = new ErrorCode(2_001_001_016, "项目IC卡表（可能会绑定人员，因不同设备需要而定）不存在");
    // ========== 项目楼层  ==========
    ErrorCode LAYER_NOT_EXISTS = new ErrorCode(2_001_001_017, "项目楼层不存在");
    // ========== 项目电话类型  ==========
    ErrorCode PHONE_CATEGORY_NOT_EXISTS = new ErrorCode(2_001_001_018, "项目电话类型不存在");
    // ========== 项目房间  ==========
    ErrorCode ROOM_NOT_EXISTS = new ErrorCode(2_001_001_019, "项目房间不存在");
    // ========== 房间点击量  ==========
    ErrorCode ROOM_HITS_NOT_EXISTS = new ErrorCode(2_001_001_020, "房间点击量不存在");
    // ========== 项目房间锁定日志  ==========
    ErrorCode ROOM_LOCK_LOGS_NOT_EXISTS = new ErrorCode(2_001_001_021, "项目房间锁定日志不存在");
    // ========== 招商中心装修页  ==========
    ErrorCode ROOM_PAGE_NOT_EXISTS = new ErrorCode(2_001_001_022, "招商中心装修页不存在");
    // ========== 房间价格  ==========
    ErrorCode ROOM_PRICE_NOT_EXISTS = new ErrorCode(2_001_001_023, "房间价格不存在");
    // ========== 房源操作记录  ==========
    ErrorCode ROOM_REMARKS_NOT_EXISTS = new ErrorCode(2_001_001_024, "房源操作记录不存在");
    // ========== 租客在租/绑定房间  ==========
    ErrorCode ROOM_RENT_NOT_EXISTS = new ErrorCode(2_001_001_025, "租客在租/绑定房间不存在");
    // ========== 第三方数据对接（目前用于智慧社区系统，全功能版）  ==========
    ErrorCode THIRD_BUTT_NOT_EXISTS = new ErrorCode(2_001_001_026, "第三方数据对接（目前用于智慧社区系统，全功能版）不存在");
    // ========== 项目单元  ==========
    ErrorCode UNIT_NOT_EXISTS = new ErrorCode(2_001_001_027, "项目单元不存在");
    // ========== 用户信息扩展  ==========
    ErrorCode USER_EXTENDS_NOT_EXISTS = new ErrorCode(2_001_001_028, "用户信息扩展不存在");
    // ========== 用户扩展信息自定义系统设置  ==========
    ErrorCode USER_FIELD_EXTEND_NOT_EXISTS = new ErrorCode(2_001_001_029, "用户扩展信息自定义系统设置不存在");
    // ========== 住户的IC卡  ==========
    ErrorCode USER_IC_CARD_NOT_EXISTS = new ErrorCode(2_001_001_030, "住户的IC卡不存在");
    // ========== 用户扩展信息自定义系统设置  ==========
    ErrorCode USER_SYSTEM_FIELD_EXTEND_NOT_EXISTS = new ErrorCode(2_001_001_031, "用户扩展信息自定义系统设置不存在");
    // ========== 社区  ==========
    ErrorCode VILLAGE_NOT_EXISTS = new ErrorCode(2_001_001_032, "社区不存在");
    // ========== 项目集合表，方便快速选择  ==========
    ErrorCode VILLAGE_COLLECTION_NOT_EXISTS = new ErrorCode(2_001_001_033, "项目集合表，方便快速选择不存在");
    ErrorCode VILLAGE_COLLECTION_BUILD_INVALID = new ErrorCode(2_101_001_033, "项目集合数据转换异常，请稍后重试");
    // ========== 项目绘制数据  ==========
    ErrorCode VILLAGE_DRAW_NOT_EXISTS = new ErrorCode(2_001_001_034, "项目绘制数据不存在");
    // ========== 项目电话  ==========
    ErrorCode VILLAGE_PHONE_NOT_EXISTS = new ErrorCode(2_001_001_035, "项目电话不存在");
    // ========== 项目类型  ==========
    ErrorCode VILLAGE_TYPE_NOT_EXISTS = new ErrorCode(2_001_001_036, "项目类型不存在");
    // ========== 项目类型字典  ==========
    ErrorCode VILLAGE_TYPE_DICT_NOT_EXISTS = new ErrorCode(2_001_001_037, "项目类型字典不存在");
    // ========== 项目用户/租客  ==========
    ErrorCode VILLAGE_USER_NOT_EXISTS = new ErrorCode(2_001_001_038, "项目用户/租客不存在");
    // ========== 项目分区  ==========
    ErrorCode ZONE_NOT_EXISTS = new ErrorCode(2_001_001_039, "项目分区不存在");

    // ========== 项目相关错误代码 ==========
    ErrorCode VILLAGE_NOT_EXISTS_NAME = new ErrorCode(2_001_001_040, "项目名称已经存在，不能重复添加");
    ErrorCode BUS_ORG_NOT_EXISTS = new ErrorCode(2_001_001_041, "获取企业信息失败，请稍后重试");
    ErrorCode NUMBER_OF_BUILDINGS = new ErrorCode(2_101_001_41, "楼宇数量不能超过3，请手动添加楼宇后在“房源管理”中使用excel表格导入房源。");
    ErrorCode CREATE_A_PROJECT = new ErrorCode(2_201_001_41, "项目创建失败，请重试");

    // ========== 合同账单  ==========
    ErrorCode ORDER_BILL_NOT_EXISTS = new ErrorCode(2_001_001_040, "合同账单不存在");
    // ========== 合同中选中房源  ==========
    ErrorCode SELECTED_PROPERTIE_NOT_EXISTS = new ErrorCode(2_001_001_041, "合同中选中房源不存在");

    // ========== 租客联系人  ==========
    ErrorCode CONTACTS_NOT_EXISTS = new ErrorCode(2_001_001_042, "租客联系人不存在");

    // ========== 机构合同附件  ==========
    ErrorCode ANNEX_NOT_EXISTS = new ErrorCode(2_001_001_043, "机构合同附件不存在");

    // ========== 合同操作日志  ==========
    ErrorCode OPERATE_LOG_NOT_EXISTS = new ErrorCode(2_001_001_044, "合同操作日志不存在");
    ErrorCode CONTRACT_NUMBER_NOT_EXISTS = new ErrorCode(2_001_001_044, "当前合同编号已存在请勿重复添加");

    // ========== 合同到期提醒规则  ==========
    ErrorCode EXPIRE_RULE_NOT_EXISTS = new ErrorCode(2_001_001_045, "合同到期提醒规则不存在");
    ErrorCode EXPIRE_RULE_RELATION_NAME_NOT_EXISTS = new ErrorCode(2_001_001_046, "已添加过该名称规则");
    ErrorCode EXPIRE_RULE_RELATION_BUILD_NOT_EXISTS = new ErrorCode(2_001_001_047, "当前存在项目楼宇已设置到期提醒,请检查");
    // ========== 机构用户自定义操作配置  ==========
    ErrorCode SPERCIAL_SETTING_NOT_EXISTS = new ErrorCode(2_001_001_048, "机构用户自定义操作配置不存在");


    // ========== 机构合同退租  ==========
    ErrorCode CONTRACT_RETREAT_NOT_EXISTS = new ErrorCode(2_001_001_049, "机构合同退租不存在");

    // ========== 收款通知记录 ==========
    ErrorCode BILL_NOTICE_NOT_EXISTS = new ErrorCode(2_001_001_050, "收款通知记录不存在");

    // ========== 机构收款账户  ==========
    ErrorCode ORG_ACCOUNT_DO_NOT_EXISTS = new ErrorCode(2_001_001_051, "机构收款账户不存在");

    // ========== 收款流水记录  ==========
    ErrorCode BILL_STREAM_NOT_EXISTS = new ErrorCode(2_001_001_052, "收款流水记录不存在");

    // ========== 收款流水记录删除记录表  ==========
    ErrorCode BILL_STREAM_DELETE_HISTORY_NOT_EXISTS = new ErrorCode(2_001_001_053, "收款流水记录删除记录表不存在");

    // ========== 租客附件  ==========
    ErrorCode OWNER_FILES_NOT_EXISTS = new ErrorCode(2_001_001_054, "租客附件不存在");
    // ========== 企业自动缴费费用配置  ==========
    ErrorCode OWNER_PAY_SET_NOT_EXISTS = new ErrorCode(2_001_001_055, "企业自动缴费费用配置不存在");
    ErrorCode OWNER_REMARKS_NOT_EXISTS = new ErrorCode(2_001_001_056, "租客备注信息不存在");
    ErrorCode BILL_ANNEX_NOT_EXISTS = new ErrorCode(2_001_001_057, "账单流水附加信息不存在");

    ErrorCode BILL_STREAM_MIDDLE_NOT_EXISTS = new ErrorCode(2_001_001_058, "账单流水匹配账单不存在");

    ErrorCode BILL_ADJUST_NOT_EXISTS = new ErrorCode(2_001_001_058, "机构账单调整信息不存在");


    // ========== 机构楼宇售方信息(发票设置)  ==========
    ErrorCode SELLER_NOT_EXISTS = new ErrorCode(2_001_001_059, "机构楼宇售方信息(发票设置)不存在");


    // ========== 账单费用类型 ==========
    ErrorCode COST_TYPE_NOT_EXISTS = new ErrorCode(2_001_001_060, "账单费用类型不存在");


    // ========== 税收分类编码配置  ==========
    ErrorCode TAX_CODE_NOT_EXISTS = new ErrorCode(2_001_001_061, "税收分类编码配置不存在");


    // ========== 税率规则配置  ==========
    ErrorCode TAX_RULE_NOT_EXISTS = new ErrorCode(2_001_001_062, "税率规则配置不存在");


    // ========== 账单费用分类  ==========
    ErrorCode COST_CATEGORY_NOT_EXISTS = new ErrorCode(2_001_001_063, "账单费用分类不存在");

    ErrorCode INVOICE_TEMPLATE_NOT_EXISTS = new ErrorCode(2_001_001_064, "发票模板配置不存在");


    // ========== 收据收款方信息  ==========
    ErrorCode RECEIPT_SELLER_NOT_EXISTS = new ErrorCode(2_001_001_065, "收据收款方信息不存在");

    // ========== 收据模板  ==========
    ErrorCode RECEIPT_TEMPLATE_NOT_EXISTS = new ErrorCode(2_001_001_066, "收据模板不存在");

    // ========== 账单收据编号规则 ==========
    ErrorCode RECEIPT_RULE_NOT_EXISTS = new ErrorCode(2_001_001_067, "账单收据编号规则不存在");

    // ========== 收费标准  ==========
    ErrorCode CHARGE_NOT_EXISTS = new ErrorCode(2_001_001_068, "收费标准不存在");

    ErrorCode BILL_INVOICE_NOT_EXISTS = new ErrorCode(2_001_001_069, "发票记录不存在");

    // ========== 合同审批  ==========
    ErrorCode CONTRACT_LEAVE_NOT_EXISTS = new ErrorCode(2_001_001_070, "合同审批不存在");

    ErrorCode INCOME_NOT_EXISTS = new ErrorCode(2_001_001_069, "机构收入记录不存在");
    ErrorCode INCOME_CONFG_NOT_EXISTS = new ErrorCode(2_001_001_070, "收支确认配置不存在");
    ErrorCode BILL_RECEIPT_LOG_NOT_EXISTS = new ErrorCode(2_001_001_071, "账单开据记录表不存在");
    ErrorCode BILL_RECEIPT_NOT_EXISTS = new ErrorCode(2_001_001_072, "机构账单收据表不存在");

    ErrorCode BILL_NOTICE_TEMPLATE_NOT_EXISTS = new ErrorCode(2_001_001_073, "账单缴费通知单模板表不存在");

    ErrorCode PARK_CARS_NOT_EXISTS = new ErrorCode(2_001_001_073, "月租车白名单不存在");

    ErrorCode PARK_NOT_EXISTS = new ErrorCode(2_001_001_073, "停车场不存在");

    //----------------------预约场地相关-------------------------
    // ========== 预约应用 ==========
    ErrorCode RESV_APPLICATION_NOT_EXISTS = new ErrorCode(2_001_001_074, "预约应用不存在");
    // ========== 预约收费规则 ==========
    ErrorCode RESV_BILL_RULE_NOT_EXISTS = new ErrorCode(2_001_001_075, "预约收费规则不存在");
    // ========== 预约 ==========
    ErrorCode RESV_BOOKING_NOT_EXISTS = new ErrorCode(2_001_001_076, "预约不存在");
    // ========== 预约订单 ==========
    ErrorCode RESV_BOOKING_ORDER_NOT_EXISTS = new ErrorCode(2_001_001_077, "预约订单不存在");
    // ========== 预约订单退款 ==========
    ErrorCode RESV_BOOKING_ORDER_REFUND_NOT_EXISTS = new ErrorCode(2_001_001_078, "预约订单退款不存在");
    ErrorCode RESV_BOOKING_ORDER_REFUND_REF_EXISTS = new ErrorCode(2_001_001_078, "预约订单退款已处理无需再次处理");
    // ========== 预约核销 ==========
    ErrorCode RESV_BOOKING_VERIFICATION_NOT_EXISTS = new ErrorCode(2_001_001_079, "预约核销不存在");
    // ========== 订单支付 ==========
    ErrorCode RESV_PAY_ORDER_NOT_EXISTS = new ErrorCode(2_001_001_080, "订单不存在");
    ErrorCode RESV_PAY_ORDER_REF_EXISTS = new ErrorCode(2_001_001_080, "发起退款失败，订单未支付");
    // ========== 预约场地 ==========
    ErrorCode RESV_PLACE_NOT_EXISTS = new ErrorCode(2_001_001_081, "预约场地不存在");
    // ========== 预约场地分类  ==========
    ErrorCode RESV_PLACE_CATEGORY_NOT_EXISTS = new ErrorCode(2_001_001_082, "预约场地分类不存在");
    // ========== 车场缴费规则  ==========
    ErrorCode PARK_CHARGE_NOT_EXISTS = new ErrorCode(2_001_001_083, "车场缴费规则不存在");

    ErrorCode PARK_CARS_OPERATOR_LOG_NOT_EXISTS = new ErrorCode(2_001_001_084, "车辆日志不存在");

    ErrorCode PARK_CAR_ORDER_NOT_EXISTS = new ErrorCode(2_001_001_085, "车辆订单不存在");

    ErrorCode CONTRACT_TEMPLATE_NOT_EXISTS = new ErrorCode(2_001_001_085, "机构合同模板配置表不存在");

    ErrorCode PAY_ORDER_BILL_NOT_EXISTS = new ErrorCode(2_001_001_086, "支付订单和账单表中间表不存在");

    ErrorCode PATROL_PLAN_EQUIPMENT_NOT_EXISTS = new ErrorCode(2_001_001_087, "应用巡检计划表不存在");

    ErrorCode PATROL_TASK_EQUIPMENT_NOT_EXISTS = new ErrorCode(2_001_001_088, " 应用巡检任务表不存在");

    ErrorCode PATROL_TASK_EQUIPMENT_LOG_NOT_EXISTS = new ErrorCode(2_001_001_088, " 应用巡检任务日志表不存在");

    ErrorCode PROPERTY_STUFF_TRANSFER_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_OPERATE_LOG_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_REVERT_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_DEPOSITORY_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_INVENTORY_RECORD_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_RETREAT_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_CHANGE_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_HANDLE_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_LENDOUT_DEPOSITORY_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_HANDLE_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_DEPOSITORY_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_PROCESS_DATA_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_LENDOUT_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_LOCATION_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_INVENTORY_LOG_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_STOCK_RECEIVE_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_HANDOUT_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_HANDOUT_DEPOSITORY_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_REPAIR_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_APPROVE_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_ENTER_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_STOCK_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_MAINTAIN_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_MAINTAIN_SET_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_RETURN_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_TRANSFER_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_CATEGORY_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_REVERT_DEPOSITORY_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_LOG_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_RECEIVE_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_RESOURCES_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_INVENTORY_LIST_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_INVENTORY_EMPLOYEE_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_CONFIG_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_HANDOUT_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_ADJUST_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_TAG_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_CATEGORY_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");
    ErrorCode PROPERTY_STUFF_PROCESS_NOT_EXISTS = new ErrorCode(2001001089, "资产管理相关信息不存在");

    ErrorCode PATROL_ADMIN_NOT_EXISTS = new ErrorCode(2001001090, "产管理子应用管理员配置不存在");

    ErrorCode PATROL_FORM_NOT_EXISTS = new ErrorCode(2001001091, "巡检表单设置不存在");

    ErrorCode PATROL_LOCATION_NOT_EXISTS = new ErrorCode(2001001092, "位置不存在");
    ErrorCode PATROL_POSITION_NOT_EXISTS = new ErrorCode(2001001093, "巡检点位数据不存在");

    // ========== 装修模板 1-013-017-000 ==========
    ErrorCode DIY_TEMPLATE_NOT_EXISTS = new ErrorCode(2001001094, "装修模板不存在");
    ErrorCode DIY_TEMPLATE_NAME_USED = new ErrorCode(2001001095, "装修模板名称({})已经被使用");
    ErrorCode DIY_TEMPLATE_USED_CANNOT_DELETE = new ErrorCode(2001001096, "不能删除正在使用的装修模板");

    // ========== 装修页面 1-013-018-000 ==========
    ErrorCode DIY_PAGE_NOT_EXISTS = new ErrorCode(2001001097, "装修页面不存在");
    ErrorCode DIY_PAGE_NAME_USED = new ErrorCode(2001001098, "装修页面名称({})已经被使用");
}
