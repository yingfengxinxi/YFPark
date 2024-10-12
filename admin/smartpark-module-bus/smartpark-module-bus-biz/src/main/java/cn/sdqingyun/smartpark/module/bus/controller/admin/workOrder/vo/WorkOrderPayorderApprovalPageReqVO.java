package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 工单付款单审批记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkOrderPayorderApprovalPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "8102")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "单据编号")
    private String approveNumber;

    @Schema(description = "1=待审批(审批中);2=审批通过(已完结);3=审批不通过;4=已撤回(关闭申请);", example = "2")
    private String status;

    @Schema(description = "审批业务类型", example = "1")
    private String processType;

    @Schema(description = "审批内容")
    private String content;

    @Schema(description = "审批应用流程id", example = "729")
    private Long approvalId;

    @Schema(description = "审批结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date approveOvertime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}