package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 终端数据推送记录新增/修改 Request VO")
@Data
public class EnergyCallbackRecordSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9501")
    private Long id;

    @Schema(description = "机构ID", example = "31949")
    private Long orgId;

    @Schema(description = "设备编号")
    private String equipNumber;

    @Schema(description = "设备类型码")
    private String equipCode;

    @Schema(description = "表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15436")
    @NotNull(message = "表ID不能为空")
    private Long energyId;

    @Schema(description = "设备读数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备读数不能为空")
    private BigDecimal reading;

    @Schema(description = "剩余水电数")
    private BigDecimal remaining;

    @Schema(description = "电池容量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "电池容量不能为空")
    private BigDecimal battery;

    @Schema(description = "电池容量百分比", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "电池容量百分比不能为空")
    private Integer percent;

    @Schema(description = "记录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date saveTime;

    @Schema(description = "记录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date saveDate;

    @Schema(description = "推送数据json")
    private String result;

    @Schema(description = "通断状态，1断，0通", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "通断状态，1断，0通不能为空")
    private String isOff;

    @Schema(description = "异常状态，0无 1离线 2断电 3电量过低", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "异常状态，0无 1离线 2断电 3电量过低不能为空")
    private String abnormalStatus;

    private String isCompleted;
}