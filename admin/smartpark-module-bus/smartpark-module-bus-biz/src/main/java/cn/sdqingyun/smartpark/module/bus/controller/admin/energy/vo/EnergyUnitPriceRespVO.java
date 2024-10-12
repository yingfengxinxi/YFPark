package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 自定义价格标准表-阶梯单价 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyUnitPriceRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17425")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("设备表种类")
    private String type;

    @Schema(description = "机构ID", example = "13190")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "用量区间1", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("用量区间1")
    private String startUsageRange;

    @Schema(description = "用量区间2", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("用量区间2")
    private String endUsageRange;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "14560")
    @ExcelProperty("价格")
    private BigDecimal price;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "自定义价格标准表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16066")
    @ExcelProperty("自定义价格标准表id")
    private Long energyPriceId;

}