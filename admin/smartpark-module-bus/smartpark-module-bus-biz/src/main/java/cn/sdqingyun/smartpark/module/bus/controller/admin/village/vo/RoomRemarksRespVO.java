package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

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

@Schema(description = "管理后台 - 房源操作记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoomRemarksRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27187")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27302")
    @ExcelProperty("房间ID")
    private Long roomId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2428")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "备注内容", example = "你猜")
    @ExcelProperty("备注内容")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

    @ExcelProperty("创建人")
    private String creator;

}