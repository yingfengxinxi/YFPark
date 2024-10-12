package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 合同操作日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ContractOperateLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7748")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "基础合同,合同主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27256")
    @ExcelProperty("基础合同,合同主表id")
    private Integer contractId;

    @Schema(description = "操作内容")
    @ExcelProperty("操作内容")
    private String operateContent;
    /**
     * 合同类型
     */
    @Schema(description = "操作类型")
    private String operateType;
    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

    @Schema(description = "创建人")
    private String creator;

}