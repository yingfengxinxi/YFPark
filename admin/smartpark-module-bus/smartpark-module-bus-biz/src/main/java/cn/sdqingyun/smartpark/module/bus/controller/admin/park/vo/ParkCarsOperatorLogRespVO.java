package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 车辆操作日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ParkCarsOperatorLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7226")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "0添加、1修改、2删除、3缴费，关联订单、4导入【数据字典PARK_CAR_LOG_TYPE】", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("0添加、1修改、2删除、3缴费，关联订单、4导入")
    private String type;

    @Schema(description = "车辆id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17587")
    @ExcelProperty("车辆id")
    private Long carsId;

    @Schema(description = "订单id", example = "22830")
    @ExcelProperty("订单id")
    private String orderId;

    @Schema(description = "修改前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("修改前")
    private String before;

    @Schema(description = "修改后", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("修改后")
    private String after;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

    @Schema(description = "操作人")
    private String creatorName;
    @Schema(description = "操作人Id")
    private String creator;

    @Schema(description = "操作内容")
    @TableField(exist = false)
    private String content;

}