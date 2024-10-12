package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * @Author lvzy
 * @Date 2024/7/26 15:11
 */
@Data
public class BillTypeContractIdBillVO {

    private Long billId;

    @Schema(description = "账单金额/应收/付款金额")
    private BigDecimal receivable;

    @Schema(description = "实收/付金额")
    private BigDecimal receivablePayment;

    @Schema(description = "逾期金额")
    private BigDecimal lateFee;


    @Schema(description = "需收/付金额")
    private BigDecimal collectedAmount;


    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private String payStartDate;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private String payEndDate;

    @Schema(description = "应收/付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private String payDate;


    @Schema(description = "逾期状态")
    private String isLateFee;

    @Schema(description = "调增金额")
    private BigDecimal jiaAdjustPrice;


    @Schema(description = "调减金额")
    private BigDecimal jianAdjustPrice;

}
