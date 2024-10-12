package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * @Author lvzy
 * @Date 2024/9/1 12:32
 */
@Data
public class WarnRecordStaticVO extends PageParam {


    @Schema(description = "任务id", example = "1167")
    private Long taskId;

    private String application;

    @ExcelProperty("巡检点名称")
    private String position;

    @ExcelProperty("计划名称")
    private String planName;


    @ExcelProperty("巡检时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private String time;


    @ExcelProperty("巡检人")
    private String name;

    @ExcelProperty("整体改进人")
    private String launchName;

    @ExcelProperty("状态")
    private String statusName;

    private Long planId;

    private String type;

    private String status;

    private String startDate;

    private String endDate;

    private Long tenantId;
}
