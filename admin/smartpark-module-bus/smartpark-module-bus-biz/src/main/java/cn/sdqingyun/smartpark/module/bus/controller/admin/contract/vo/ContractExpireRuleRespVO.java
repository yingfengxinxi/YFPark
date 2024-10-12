package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 合同到期提醒规则 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ContractExpireRuleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19099")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "合同到期规则名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("合同到期规则名称")
    private String ruleName;

    @Schema(description = "关联楼宇id，多个id用逗号隔开", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("关联楼宇id，多个id用逗号隔开")
    private String relationBuilds;

    @Schema(description = "提醒方式0=站内1=公众号")
    @ExcelProperty("提醒方式0=站内1=公众号")
    private String reminderMethod;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

}