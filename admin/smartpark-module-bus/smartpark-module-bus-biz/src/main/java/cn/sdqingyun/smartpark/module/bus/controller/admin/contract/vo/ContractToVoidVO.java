package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/8/8 13:05
 */
@Data
public class ContractToVoidVO {

    //合同id
    private Integer contractId;

    //作废原因
    private String content;

    //是否作废账单
    private String isToVoidBill;

    //是否关闭流水
    private String iSCloseFlow;
}
