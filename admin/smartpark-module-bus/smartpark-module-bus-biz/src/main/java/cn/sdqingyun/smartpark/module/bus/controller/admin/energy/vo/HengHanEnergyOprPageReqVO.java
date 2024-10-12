package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 电表操作id关联表、回调用分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HengHanEnergyOprPageReqVO extends PageParam {

    @Schema(description = "机构iD", example = "22160")
    private Long orgId;

    @Schema(description = "操作ID", example = "18341")
    private Long oprId;

    @Schema(description = "电表id", example = "14502")
    private Long energyId;

    @Schema(description = "操作类型、read（抄表） open（开闸） close(关闸)", example = "1")
    private String type;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}