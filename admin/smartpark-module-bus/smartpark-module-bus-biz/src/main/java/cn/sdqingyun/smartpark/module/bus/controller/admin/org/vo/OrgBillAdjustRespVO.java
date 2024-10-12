package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构账单调整 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillAdjustRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23795")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "账单id", example = "21124")
    @ExcelProperty("账单id")
    private Long billId;

    @Schema(description = "账单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("账单类型")
    private String billType;

    @Schema(description = "调整日期")
    @ExcelProperty("调整日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date adjustDate;

    @Schema(description = "调整类型", example = "2")
    @ExcelProperty("调整类型")
    private String adjustType;

    @Schema(description = "调整方式")
    @ExcelProperty("调整方式")
    private String adjustMode;

    @Schema(description = "调整前账单金额")
    @ExcelProperty("调整前账单金额")
    private BigDecimal adjustBeforeAmount;

    @Schema(description = "调整比例")
    @ExcelProperty("调整比例")
    private String adjustProportion;

    @Schema(description = "调整金额", example = "30281")
    @ExcelProperty("调整金额")
    private BigDecimal adjustPrice;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "作废日期")
    @ExcelProperty("作废日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date cancelAdjustDate;

    @Schema(description = "调整状态;1=调整待审核;2=调整已审核;3=作废调整待审核;4=作废已审核;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("调整状态;1=调整待审核;2=调整已审核;3=作废调整待审核;4=作废已审核;")
    private String adjustStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

}