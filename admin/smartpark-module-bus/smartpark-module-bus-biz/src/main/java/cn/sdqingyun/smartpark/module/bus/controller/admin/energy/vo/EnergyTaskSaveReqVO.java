package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 自定义抄表任务新增/修改 Request VO")
@Data
public class EnergyTaskSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3755")
    private Long id;

    @Schema(description = "机构ID", example = "14991")
    private Long orgId;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9670")
    @NotNull(message = "计划id不能为空")
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
    private LocalDateTime startTime;

    @Schema(description = "应开始时间")
    private LocalDateTime shouldTime;

    @Schema(description = "任务提醒时间")
    private LocalDateTime remindTime;

    @Schema(description = "负责岗位id")
    private String stationIds;

    @Schema(description = "责任部门id", example = "19937")
    private Long departmentId;

    @Schema(description = "岗位部门人员uids")
    private String postUids;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "超时时间")
    private LocalDateTime timeoutTime;

    @Schema(description = "是否超时", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否超时不能为空")
    private String isTimeout;

    @Schema(description = "是否发送提醒", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否发送提醒不能为空")
    private String isRemind;

    @Schema(description = "任务状态;1=待开始;2=抄表中;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "任务状态;1=待开始;2=抄表中;不能为空")
    private String status;

    @Schema(description = "周期状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "周期状态不能为空")
    private String hasLoop;

    @Schema(description = "第三方任务id", example = "4970")
    private Long thirdTaskId;

    @Schema(description = "任务是否异常终止;", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "任务是否异常终止;不能为空")
    private String isStop;

    @Schema(description = "任务终止异常log")
    private String stopLog;

}