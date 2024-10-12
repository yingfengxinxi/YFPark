package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 自定义抄表任务分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyTaskPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "14991")
    private Long orgId;

    @Schema(description = "计划id", example = "9670")
    private Long planId;

    @Schema(description = "任务周期")
    private String taskCycle;

    @Schema(description = "任务key")
    private String taskKey;

    @Schema(description = "任务名称", example = "芋艿")
    private String taskName;

    @Schema(description = "任务编号")
    private String taskNumber;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "应开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] shouldTime;

    @Schema(description = "任务提醒时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] remindTime;

    @Schema(description = "负责岗位id")
    private String stationIds;

    @Schema(description = "责任部门id", example = "19937")
    private Long departmentId;

    @Schema(description = "岗位部门人员uids")
    private String postUids;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "超时时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] timeoutTime;

    @Schema(description = "是否超时")
    private String isTimeout;

    @Schema(description = "是否发送提醒")
    private String isRemind;

    @Schema(description = "任务状态;1=待开始;2=抄表中;", example = "2")
    private String status;

    @Schema(description = "周期状态")
    private String hasLoop;

    @Schema(description = "第三方任务id", example = "4970")
    private Long thirdTaskId;

    @Schema(description = "任务是否异常终止;")
    private String isStop;

    @Schema(description = "任务终止异常log")
    private String stopLog;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}