package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 房间价格分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoomPricePageReqVO extends PageParam {

    @Schema(description = "房间ID", example = "18968")
    private Long roomId;

    @Schema(description = "创建日期")
    private LocalDateTime createdDay;

    @Schema(description = "建筑面积")
    private BigDecimal buildArea;

    @Schema(description = "房价（m²/天）")
    private BigDecimal squareDay;

    @Schema(description = "房价（m²/月）")
    private BigDecimal squareMonth;

    @Schema(description = "房价（天）")
    private BigDecimal day;

    @Schema(description = "房价（月）")
    private BigDecimal month;

    @Schema(description = "房价（年）")
    private BigDecimal year;

    @Schema(description = "状态（1正常，0隐藏）", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}