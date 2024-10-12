package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * @Author lvzy
 * @Date 2024/6/24 11:40
 */
@Data
public class ContractIdCompyOrderBillListVO {

    @Schema(description = "费用类型", example = "2")
    private String feeType;

    @Schema(description = "应收金额")
    private String receivable;

    @Schema(description = "实收金额")
    private String ssReceivable;

    @Schema(description = "园区名称")
    private String parkName;

    @Schema(description = "楼层/房号")
    private String buildName;

    @Schema(description = "应收日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    @Schema(description = "税率")
    private String taxInclusiveValue;

    @Schema(description = "税额")
    private String taxAmount;

    @Schema(description = "状态0=待付款1=已付款2=预期")
    private String billStatus;

    private String roomNumber;
}
