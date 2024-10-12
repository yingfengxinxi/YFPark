package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 自定义抄表任务 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyTaskRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3755")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "14991")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9670")
    @ExcelProperty("计划id")
    private Long planId;

    @Schema(description = "任务周期")
    @ExcelProperty("任务周期")
    private String taskCycle;

    @Schema(description = "任务key")
    @ExcelProperty("任务key")
    private String taskKey;

    @Schema(description = "任务名称", example = "芋艿")
    @ExcelProperty("任务名称")
    private String taskName;

    @Schema(description = "任务编号")
    @ExcelProperty("任务编号")
    private String taskNumber;

    @Schema(description = "开始时间")
    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @Schema(description = "应开始时间")
    @ExcelProperty("应开始时间")
    private LocalDateTime shouldTime;

    @Schema(description = "任务提醒时间")
    @ExcelProperty("任务提醒时间")
    private LocalDateTime remindTime;

    @Schema(description = "负责岗位id")
    @ExcelProperty("负责岗位id")
    private String stationIds;

    @Schema(description = "责任部门id", example = "19937")
    @ExcelProperty("责任部门id")
    private Long departmentId;

    @Schema(description = "岗位部门人员uids")
    @ExcelProperty("岗位部门人员uids")
    private String postUids;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @Schema(description = "超时时间")
    @ExcelProperty("超时时间")
    private LocalDateTime timeoutTime;

    @Schema(description = "是否超时", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否超时")
    private String isTimeout;

    @Schema(description = "是否发送提醒", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否发送提醒")
    private String isRemind;

    @Schema(description = "任务状态;1=待开始;2=抄表中;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("任务状态;1=待开始;2=抄表中;")
    private String status;

    @Schema(description = "周期状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("周期状态")
    private String hasLoop;

    @Schema(description = "第三方任务id", example = "4970")
    @ExcelProperty("第三方任务id")
    private Long thirdTaskId;

    @Schema(description = "任务是否异常终止;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("任务是否异常终止;")
    private String isStop;

    @Schema(description = "任务终止异常log")
    @ExcelProperty("任务终止异常log")
    private String stopLog;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}