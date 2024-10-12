package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

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

@Schema(description = "管理后台 - 自定义抄表计划 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyPlanRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15773")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "24411")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "计划编号")
    @ExcelProperty("计划编号")
    private String planNumber;

    @Schema(description = "计划名称", example = "张三")
    @ExcelProperty("计划名称")
    private String planName;

    @Schema(description = "所选责任岗位id")
    @ExcelProperty("所选责任岗位id")
    private String stationIds;
    private String stationDepartmentName;

    @Schema(description = "责任部门", example = "20214")
    @ExcelProperty("责任部门")
    private Long departmentId;

    @Schema(description = "任务时限/小时", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("任务时限/小时")
    private Integer timeLimit;

    @Schema(description = "抄表范围json", example = "1")
    @ExcelProperty("抄表范围json")
    private String energyType;

    @Schema(description = "计划周期规则")
    @ExcelProperty("计划周期规则")
    private String planRule;

    @Schema(description = "最近一次执行时间")
    @ExcelProperty("最近一次执行时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;

    @Schema(description = "下次执行时间")
    @ExcelProperty("下次执行时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date nextTime;

    @Schema(description = "计划执行结束时间")
    @ExcelProperty("计划执行结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;

    @Schema(description = "项目ID")
    private Long villageId;
    private String villageName;

    @Schema(description = "楼宇ID")
    private Long buildId;
    private String buildName;

    @Schema(description = "绑定楼层信息，1,2,3")
    private String layerIds;

    private String layerName;

    @Schema(description = "绑定房间信息，1,2,3")
    private String roomIds;
    private String roomName;

    @Schema(description = "提醒方式及提醒规则")
    @ExcelProperty("提醒方式及提醒规则")
    private String remindJson;

    @Schema(description = "自定义抄表计划状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("自定义抄表计划状态")
    private String planStatus;
    private String planStatusName;

    @Schema(description = "计划启用状态;0=关闭;1=开启", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("计划启用状态;0=关闭;1=开启")
    private String status;
    private String statusName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}