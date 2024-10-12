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
public enum OperateTypeEnum {
    // 资产操作类型
    PUT_DEPOSITORY("put_depository", "资产入库"),
    HANDOUT_PROPERTY("handout_property", "资产派发"),
    RETURN_PROPERTY("return_property", "资产退库"),
    LENDOUT_PROPERTY("lendout_property", "资产借出"),
    REVERT_PROPERTY("revert_property", "资产归还"),
    RECEIVE_PROPERTY("receive_property", "资产领用"),
    BORROW_PROPERTY("borrow_property", "资产借用"),
    REPORT_LOSS_PROPERTY("report_loss_property", "资产报失"),
    TRANSFER_PROPERTY("transfer_property", "资产调拨"),
    CHANGE_PROPERTY("change_property", "变更领用人"),
    HANDOVER_PROPERTY("handover_property", "交接他人"),
    REPAIR_PROPERTY("repair_property", "资产维修"),
    REPORT_REPAIR_PROPERTY("report_repair_property", "资产报修"),
    RETREAT_PROPERTY("retreat_property", "资产退还"),
    PROPERTY_MAINTAIN("property_maintain", "资产保养"),
    HANDLE_PROPERTY("handle_property", "资产处置"),

    // 耗材操作类型
    STUFF_STOCK_ENTER("stuff_stock_enter", "耗材入库"),
    STUFF_HAND_OUT("stuff_hand_out", "耗材派发"),
    STUFF_RETREAT_OUT("stuff_retreat_out", "耗材退还"),
    STUFF_TRANSFER("stuff_transfer", "耗材调拨"),
    STUFF_HANDLE("stuff_handle", "耗材处置"),
    STUFF_ADJUST("stuff_adjust", "耗材调整"),
    STUFF_RECEIVE("stuff_receive", "耗材领用"),
    STUFF_RETURN("stuff_return", "耗材退还"),
    STUFF_STOCK_RECEIVE("stuff_stock_receive", "库存耗材领用");

    private final String code;
    private final String description;

    public static OperateTypeEnum fromCode(String code) {
        for (OperateTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    public static String getDescriptionByCode(String code) {
        return fromCode(code).getDescription();
    }
}
