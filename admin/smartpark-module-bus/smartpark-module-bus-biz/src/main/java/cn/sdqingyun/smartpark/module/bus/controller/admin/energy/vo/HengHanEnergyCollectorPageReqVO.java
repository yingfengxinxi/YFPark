package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 采集器电表关联表（一对多的关系）分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HengHanEnergyCollectorPageReqVO extends PageParam {

    @Schema(description = "机构iD", example = "29633")
    private Long orgId;

    @Schema(description = "采集器id", example = "16013")
    private Long collectorId;

    @Schema(description = "水电表id、关联energy表id", example = "31107")
    private Long energyId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}