package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 自定义抄表任务日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyTaskLogPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "25383")
    private Long orgId;

    @Schema(description = "计划id", example = "9130")
    private Long planId;

    @Schema(description = "计划key")
    private String planKey;

    @Schema(description = "周期")
    private String crontab;

    @Schema(description = "规则")
    private String rule;

    @Schema(description = "生成时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] buildTime;

    @Schema(description = "任务执行时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] taskTime;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "执行顺序")
    private Integer sort;

    @Schema(description = "累计执行")
    private Integer total;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}