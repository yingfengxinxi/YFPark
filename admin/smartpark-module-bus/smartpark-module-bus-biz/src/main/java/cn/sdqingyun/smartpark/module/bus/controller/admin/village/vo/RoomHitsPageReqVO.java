package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 房间点击量分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoomHitsPageReqVO extends PageParam {

    @Schema(description = "项目ID", example = "30962")
    private Long villageId;

    @Schema(description = "房间ID", example = "32409")
    private Long roomId;

    @Schema(description = "日期")
    private LocalDateTime day;

    @Schema(description = "点击量")
    private Integer hits;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}