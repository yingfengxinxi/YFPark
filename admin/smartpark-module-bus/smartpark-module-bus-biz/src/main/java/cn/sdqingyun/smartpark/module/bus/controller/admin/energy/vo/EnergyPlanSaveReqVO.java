package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 自定义抄表计划新增/修改 Request VO")
@Data
public class EnergyPlanSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15773")
    private Long id;

    @Schema(description = "机构ID", example = "24411")
    private Long orgId;

    @Schema(description = "计划编号")
    private String planNumber;

    @Schema(description = "计划名称", example = "张三")
    private String planName;

    @Schema(description = "所选责任岗位id")
    private String stationIds;

    @Schema(description = "责任部门", example = "20214")
    private Long departmentId;

    @Schema(description = "任务时限/小时", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "任务时限/小时不能为空")
    private Integer timeLimit;

    @Schema(description = "抄表范围json", example = "1")
    private String energyType;

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

    @Schema(description = "项目ID")
    private Long villageId;

    @Schema(description = "楼宇ID")
    private Long buildId;

    @Schema(description = "绑定楼层信息，1,2,3")
    private String layerIds;

    @Schema(description = "绑定房间信息，1,2,3")
    private String roomIds;

    @Schema(description = "提醒方式及提醒规则")
    private String remindJson;

    @Schema(description = "自定义抄表计划状态[数据字典ENERGY_PLAN_STATUS]1=待开始;2=进行中;3=已完成;4=超期;5=超期未处理;6=挂起", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String planStatus;

    @Schema(description = "计划启用状态;0=关闭;1=开启", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String status;

}