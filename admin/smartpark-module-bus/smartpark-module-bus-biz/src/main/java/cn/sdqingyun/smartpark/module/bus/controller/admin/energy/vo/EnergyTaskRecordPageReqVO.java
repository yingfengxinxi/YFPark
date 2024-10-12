package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 自定义抄表任务记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyTaskRecordPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "30135")
    private Long orgId;

    @Schema(description = "计划id", example = "15672")
    private Long planId;

    @Schema(description = "任务id", example = "31056")
    private Long taskId;

    @Schema(description = "自定义表id", example = "31340")
    private Long energyId;

    @Schema(description = "第三方任务id", example = "8056")
    private Long thirdTaskId;

    @Schema(description = "抄录数据json")
    private String extraData;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}