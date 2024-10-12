package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 应用巡检计划分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatrolPlanEquipmentPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "8455")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "计划编号")
    private String planNumber;

    @Schema(description = "计划名称", example = "赵六")
    private String planName;

    @Schema(description = "所选责任岗位id", example = "25025")
    private Long stationId;


    @Schema(description = "责任岗位父级id集合(多个使用逗号隔开)", example = "25025")
    private String stationParentId;

    @Schema(description = "责任部门", example = "20940")
    private Long departmentId;

    @Schema(description = "责任部门父级id集合(多个使用逗号隔开)", example = "25025")
    private String departmentParentId;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date startDate;

    @Schema(description = "截止日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date endDate;

    @Schema(description = "日期永久标识")
    private String dateFlag;

    @Schema(description = "超时处理规则类型;1=不处理;2=自动完成;3=挂起", example = "1")
    private String timeOutType;

    @Schema(description = "任务时限/小时")
    private Integer timeLimit;

    @Schema(description = "上报工单所属应用标识")
    private String workOrderApp;

    @Schema(description = "计划周期规则")
    private String planRule;

    @Schema(description = "最近一次执行时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;

    @Schema(description = "下次执行时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date nextTime;

    @Schema(description = "计划执行结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;

    @Schema(description = "巡检顺序;1=必须一次;2=可以随机")
    private String patrolOrder;

    @Schema(description = "提醒方式及提醒规则")
    private String remindJson;

    @Schema(description = "巡检计划状态", example = "1")
    private String planStatus;

    @Schema(description = "计划启用状态;0=关闭;1=开启", example = "2")
    private String status;

    @Schema(description = "提前完成的时间数量")
    private Integer inAdvance;

    @Schema(description = "提前完成的时间单位，1是小时2是天3是分钟")
    private Integer minuteTime;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}