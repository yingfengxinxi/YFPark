package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 应用巡检计划绑定巡检点分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatrolPlanPositionPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "29430")
    private Long orgId;

    @Schema(description = "计划id", example = "10478")
    private Long planId;

    @Schema(description = "巡检点id", example = "66")
    private Long positionId;

    @Schema(description = "是否扫码打卡0=否1=是")
    private String isScanCodeCheck;

    @Schema(description = "是否NFC打卡0=否1=是")
    private String isNfcClock;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}