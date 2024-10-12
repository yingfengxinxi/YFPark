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
public enum ProptryAuditStatusEnum {

    STATUS_1(1, "待审批(审批中)"),
    STATUS_2(2, "审批通过(已完结)"),
    STATUS_3(3, "审批不通过"),
    STATUS_4(4, "已撤回(关闭申请)");

    private final Integer code;
    private final String name;
}
