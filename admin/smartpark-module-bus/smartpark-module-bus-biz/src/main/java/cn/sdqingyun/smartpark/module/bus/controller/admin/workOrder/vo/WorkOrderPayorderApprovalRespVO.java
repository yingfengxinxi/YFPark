package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

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

@Schema(description = "管理后台 - 工单付款单审批记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WorkOrderPayorderApprovalRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3539")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "8102")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String approveNumber;

    @Schema(description = "1=待审批(审批中);2=审批通过(已完结);3=审批不通过;4=已撤回(关闭申请);", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("1=待审批(审批中);2=审批通过(已完结);3=审批不通过;4=已撤回(关闭申请);")
    private String status;

    @Schema(description = "审批业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("审批业务类型")
    private String processType;

    @Schema(description = "审批内容")
    @ExcelProperty("审批内容")
    private String content;

    @Schema(description = "审批应用流程id", example = "729")
    @ExcelProperty("审批应用流程id")
    private Long approvalId;

    @Schema(description = "审批结束时间")
    @ExcelProperty("审批结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date approveOvertime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}