package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 房间价格 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoomPriceRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32631")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18968")
    @ExcelProperty("房间ID")
    private Long roomId;

    @Schema(description = "创建日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建日期")
    private LocalDateTime createdDay;

    @Schema(description = "建筑面积", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("建筑面积")
    private BigDecimal buildArea;

    @Schema(description = "房价（m²/天）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("房价（m²/天）")
    private BigDecimal squareDay;

    @Schema(description = "房价（m²/月）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("房价（m²/月）")
    private BigDecimal squareMonth;

    @Schema(description = "房价（天）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("房价（天）")
    private BigDecimal day;

    @Schema(description = "房价（月）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("房价（月）")
    private BigDecimal month;

    @Schema(description = "房价（年）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("房价（年）")
    private BigDecimal year;

    @Schema(description = "状态（1正常，0隐藏）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态（1正常，0隐藏）")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}