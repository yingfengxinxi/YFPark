package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 工单子类费用预约时段设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryFeeSetReservationDatePageReqVO extends PageParam {

    @Schema(description = "机构id", example = "21084")
    private Long orgId;

    @Schema(description = "工单子类费用设置表id", example = "9973")
    private Long feeSetId;

    @Schema(description = "设置可预约日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date orderTime;


    @Schema(description = "周")
    private String week;

    @Schema(description = "每日开放预约开始时间")
    private String openStartTime;

    @Schema(description = "每日开放预约结束时间")
    private String openEndTime;

    @Schema(description = "设置间隔时段")
    private Integer num;

    @Schema(description = "设置间隔时段单位")
    private String unit;

    @Schema(description = "间隔时段预约上限数")
    private Integer orderLimit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}