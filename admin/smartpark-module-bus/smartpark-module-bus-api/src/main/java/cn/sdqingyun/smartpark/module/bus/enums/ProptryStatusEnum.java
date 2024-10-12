package cn.sdqingyun.smartpark.module.bus.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * CRM 的审批状态
 *
 * @author 赤焰
 */
@RequiredArgsConstructor
@Getter
public enum ProptryStatusEnum {

    STATUS_0(0, "入库中"),
    STATUS_1(1, "空闲"),
    STATUS_2(2, "在用"),
    STATUS_3(3, "借用"),
    STATUS_4(4, "已处置"),
    STATUS_5(5, "已报失"),
    STATUS_6(6, "派发中"),
    STATUS_7(7, "退库中"),
    STATUS_8(8, "借出中"),
    STATUS_9(9, "归还中"),
    STATUS_10(10, "维修中"),
    STATUS_11(11, "处置中"),
    STATUS_12(12, "报失中"),
    STATUS_13(13, "调拨中"),
    STATUS_14(14, "领用人变更中"),
    STATUS_15(15, "领用申请中"),
    STATUS_16(16, "借用申请中"),
    STATUS_17(17, "交接中"),
    STATUS_18(18, "报修中"),
    STATUS_19(19, "保养中");

    private final Integer code;
    private final String name;
}
