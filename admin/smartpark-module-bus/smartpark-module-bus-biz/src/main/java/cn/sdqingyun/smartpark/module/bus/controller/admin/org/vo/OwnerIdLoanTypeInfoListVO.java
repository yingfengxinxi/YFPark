package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/7/4 11:51
 */
@Data
public class OwnerIdLoanTypeInfoListVO {

    @Schema(description = "汇款方式")
    private String remitTypeName;
    private String accountId;
    @Schema(description = "收款账户")
    private String accountName;
    @Schema(description = "收款金额/支出金额")
    private BigDecimal money;
    @Schema(description = "收款笔数/支出笔数")
    private Integer count;
}
