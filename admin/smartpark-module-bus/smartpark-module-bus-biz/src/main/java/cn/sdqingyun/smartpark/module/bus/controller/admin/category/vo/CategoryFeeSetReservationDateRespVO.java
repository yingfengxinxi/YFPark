package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 工单子类费用预约时段设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CategoryFeeSetReservationDateRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22728")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "21084")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "工单子类费用设置表id", example = "9973")
    @ExcelProperty("工单子类费用设置表id")
    private Long feeSetId;

    @Schema(description = "设置可预约日期")
    @ExcelProperty("设置可预约日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date orderTime;


    @Schema(description = "周")
    @ExcelProperty("周")
    private String week;

    @Schema(description = "每日开放预约开始时间")
    @ExcelProperty("每日开放预约开始时间")
    private String openStartTime;

    @Schema(description = "每日开放预约结束时间")
    @ExcelProperty("每日开放预约结束时间")
    private String openEndTime;

    @Schema(description = "设置间隔时段")
    @ExcelProperty("设置间隔时段")
    private Integer num;

    @Schema(description = "设置间隔时段单位")
    @ExcelProperty("设置间隔时段单位")
    private String unit;

    @Schema(description = "间隔时段预约上限数")
    @ExcelProperty("间隔时段预约上限数")
    private Integer orderLimit;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}