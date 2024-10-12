package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

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
 * @Date 2024/7/22 15:45
 */
@Data
public class BillingStatementVO {

    @Schema(description = "账单id", example = "1")
    private Long billId;


    @Schema(description = "项目", example = "1")
    private String villageName;


    @Schema(description = "楼宇", example = "1")
    private String buildName;


    @Schema(description = "房号", example = "1")
    private String roomNumber;


    @Schema(description = "租客", example = "1")
    private String ownerName;

    @Schema(description = "费用类型", example = "1")
    private String costName;


    @Schema(description = "应收(包含违约金)", example = "1")
    private BigDecimal receivable;


    @Schema(description = "实收", example = "1")
    private BigDecimal canReceiptAmount;

    @Schema(description = "开据金额", example = "1")
    private BigDecimal applyReceiptAmount;

    @Schema(description = "应收日期", example = "1")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

}
