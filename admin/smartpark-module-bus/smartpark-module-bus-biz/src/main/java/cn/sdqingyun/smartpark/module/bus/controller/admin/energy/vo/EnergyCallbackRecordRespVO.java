package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 终端数据推送记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyCallbackRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9501")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "31949")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String equipNumber;

    @Schema(description = "设备类型码")
    @ExcelProperty("设备类型码")
    private String equipCode;

    @Schema(description = "表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15436")
    @ExcelProperty("表ID")
    private Long energyId;

    @Schema(description = "设备读数【读表用量】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备读数")
    private BigDecimal reading;

    @Schema(description = "剩余水电数【剩余用量】")
    @ExcelProperty("剩余水电数")
    private BigDecimal remaining;

    @Schema(description = "电池容量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("电池容量")
    private BigDecimal battery;

    @Schema(description = "电池容量百分比", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("电池容量百分比")
    private Integer percent;

    @Schema(description = "记录时间【读表时间】")
    @ExcelProperty("记录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date saveTime;

    @Schema(description = "记录时间")
    @ExcelProperty("记录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date saveDate;

    @Schema(description = "推送数据json")
    @ExcelProperty("推送数据json")
    private String result;

    @Schema(description = "通断状态，1断，0通", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("通断状态，1断，0通")
    private String isOff;
    private String isOffName;

    @Schema(description = "异常状态，0无 1离线 2断电 3电量过低", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("异常状态，0无 1离线 2断电 3电量过低")
    private String abnormalStatus;

    private String isCompleted;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "舍弃按钮是否显示true显示false不显示", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isSq;

}