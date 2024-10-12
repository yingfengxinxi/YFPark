package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/7/8 14:11
 */
@Data
public class OwnerFinanceListVO extends PageParam {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "订单号")
    private String orderNumber;

    @Schema(description = "应付时间")
    private String payDate;

    @Schema(description = "应付时间")
    private String payDateStr;

    @Schema(description = "账单类型")
    private String feeType;

    @Schema(description = "状态")
    private String billStatus;

    @Schema(description = "应收金额")
    private BigDecimal receivable;

    @Schema(description = "实收金额")
    private BigDecimal receivablePayment;

    private String year;

    private String month;

    private Long ownerId;

    private Long roomId;
}
