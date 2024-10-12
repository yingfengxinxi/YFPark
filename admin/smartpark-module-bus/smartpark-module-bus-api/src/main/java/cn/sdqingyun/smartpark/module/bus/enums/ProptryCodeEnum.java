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
public enum ProptryCodeEnum {

    ZCQD("KJ-", "资产清单"),
    ZCRK("ZCRK", "资产入库"),
    ZCPF("ZCPF", "资产派发"),
    ZCTK("ZCTK", "资产退库"),
    ZCJC("ZCJC", "资产借出"),
    ZCGH("ZCGH", "资产归还"),
    ZCBG("ZCBG", "资产变更领用人"),
    ZCDB("ZCDB", "资产调拨"),
    ZCWX("ZCWX", "资产维修"),
    ZCCL("ZCCL", "资产处置"),
    ZCBY("ZCBY", "资产保养"),
    HCRK("HCRK", "耗材入库"),
    HCPF("HCPF", "耗材派发"),
    HCTK("HCTK", "耗材退库"),
    HCDB("HCDB", "耗材调拨"),
    HCCZ("HCCZ", "耗材处置"),
    HCTZ("KCTZ", "库存调整"),
    CANCEL("ZCPD", "资产盘点");

    private final String code;
    private final String name;
}
