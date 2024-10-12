package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 流水删除历史分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BillStreamDeleteHistoryPageReqVO extends PageParam {

    @Schema(description = "流水id", example = "13960")
    private Integer streamId;

    @Schema(description = "审批id", example = "21888")
    private Integer approvalId;

    @Schema(description = "审批状态", example = "1")
    private Integer approvalStatus;

    @Schema(description = "0新建1审批回调", example = "1")
    private Integer type;

    @Schema(description = "审批结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] approvalTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}