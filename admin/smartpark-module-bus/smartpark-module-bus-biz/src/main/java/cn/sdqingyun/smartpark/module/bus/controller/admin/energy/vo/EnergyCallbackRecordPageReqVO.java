package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 终端数据推送记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyCallbackRecordPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "31949")
    private Long orgId;

    @Schema(description = "设备编号")
    private String equipNumber;

    @Schema(description = "设备类型码")
    private String equipCode;

    @Schema(description = "表ID", example = "15436")
    private Long energyId;

    @Schema(description = "设备读数")
    private BigDecimal reading;

    @Schema(description = "剩余水电数")
    private BigDecimal remaining;

    @Schema(description = "电池容量")
    private BigDecimal battery;

    @Schema(description = "电池容量百分比")
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

    @Schema(description = "通断状态，1断，0通")
    private String isOff;

    @Schema(description = "异常状态，0无 1离线 2断电 3电量过低", example = "1")
    private String abnormalStatus;

    private String isCompleted;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}