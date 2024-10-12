package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 机构账单调整新增/修改 Request VO")
@Data
public class OrgBillAdjustSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23795")
    private Long id;

    @Schema(description = "账单id", example = "21124")
    private Long billId;

    @Schema(description = "账单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String billType;

    @Schema(description = "调整日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date adjustDate;

    @Schema(description = "调整类型1=调增2=调减", example = "2")
    private String adjustType;

    @Schema(description = "调整方式0=按金额调整1=按比例调整")
    private String adjustMode;

    @Schema(description = "调整前账单金额")
    private BigDecimal adjustBeforeAmount;

    @Schema(description = "调整比例")
    private String adjustProportion;

    @Schema(description = "调整金额", example = "30281")
    private BigDecimal adjustPrice;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "作废日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date cancelAdjustDate;

    @Schema(description = "调整状态;1=调整待审核;2=调整已审核;3=作废调整待审核;4=作废已审核;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String adjustStatus;

}