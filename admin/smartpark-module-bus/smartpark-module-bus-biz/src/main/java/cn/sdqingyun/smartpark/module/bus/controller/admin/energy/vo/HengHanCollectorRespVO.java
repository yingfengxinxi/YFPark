package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

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

@Schema(description = "管理后台 - 网关管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class HengHanCollectorRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5370")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构iD", example = "26575")
    @ExcelProperty("机构iD")
    private Long orgId;

    @Schema(description = "网关设备号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18444")
    @ExcelProperty("网关设备号")
    private String cid;

    @Schema(description = "项目id", example = "4822")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "网关名称", example = "赵六")
    @ExcelProperty("网关名称")
    private String name;

    @Schema(description = "网关型号", example = "1")
    @ExcelProperty("网关型号")
    private String type;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    @Schema(description = "最后心跳时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastHeartTime;

}