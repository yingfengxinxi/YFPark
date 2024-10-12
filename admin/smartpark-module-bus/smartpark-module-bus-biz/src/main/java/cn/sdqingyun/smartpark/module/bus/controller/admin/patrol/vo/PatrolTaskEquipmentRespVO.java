package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.TaskUserLogDO;
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

@Schema(description = "管理后台 - 应用巡检任务 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolTaskEquipmentRespVO {

    @Schema(description="执行结果")
    @ExcelProperty("执行结果")
    private String statusName;

    @Schema(description = "计划名称", example = "王五")
    @ExcelProperty("计划名称")
    private String taskName;

    @Schema(description = "任务编号")
    @ExcelProperty("任务编号")
    private String taskNumber;

    @Schema(description = "开始时间")
    @ExcelProperty("开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date startTime;

    @Schema(description = "应开始时间")
    @ExcelProperty("应开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date shouldTime;

    @Schema(description = "负责岗位-负责部门")
    @ExcelProperty("负责岗位-负责部门")
    private String stationDepartmentName;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;


    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18555")
    private Long id;

    @Schema(description = "机构ID", example = "13571")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26190")
    private Long planId;

    @Schema(description = "巡检顺序;1=必须依次;2=可以随机", requiredMode = Schema.RequiredMode.REQUIRED)
    private String patrolOrder;

    @Schema(description = "任务周期")
    private String taskCycle;

    @Schema(description = "任务key")
    private String taskKey;

    @Schema(description = "任务提醒时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date remindTime;

    @Schema(description = "负责岗位id", example = "5925")
    private Long stationId;


    @Schema(description = "责任岗位父级id集合(多个使用逗号隔开)", example = "25025")
    private String stationParentId;

    @Schema(description = "责任部门", example = "20940")
    private Long departmentId;

    @Schema(description = "责任部门父级id集合(多个使用逗号隔开)", example = "25025")
    private String departmentParentId;

    @Schema(description = "岗位部门人员uids")
    private String postUids;



    @Schema(description = "超时时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;

    @Schema(description = "是否超时", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isTimeout;

    @Schema(description = "是否发送提醒", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isRemind;

    @Schema(description = "任务状态;1=待开始;2=巡检中;", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;


    @Schema(description = "周期状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private String hasLoop;

    @Schema(description = "第三方任务id", example = "19961")
    private String thirdTaskId;

    @Schema(description = "任务是否异常终止;", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isStop;

    @Schema(description = "任务终止异常log")
    private String stopLog;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "变更执行人集合")
    private List<TaskUserLogDO> changePersonnelList;

}