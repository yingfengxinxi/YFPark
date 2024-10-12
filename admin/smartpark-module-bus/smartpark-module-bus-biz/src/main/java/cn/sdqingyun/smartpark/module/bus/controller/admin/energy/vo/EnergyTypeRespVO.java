package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 表种类管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyTypeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2038")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "6410")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "表种类名称", example = "张三")
    @ExcelProperty("表种类名称")
    private String name;

    @Schema(description = "费用类型", example = "1")
    @ExcelProperty("费用类型")
    private String costType;

    @Schema(description = "费用类型文本")
    @ExcelProperty("费用类型文本")
    private String costTypeTxt;

    @Schema(description = "设备类型", example = "1")
    @ExcelProperty("设备类型")
    private String equipType;

    @Schema(description = "计费单位 如度、方等")
    @ExcelProperty("计费单位 如度、方等")
    private String unit;

    @Schema(description = "是否自动断电 0否 1是")
    @ExcelProperty("是否自动断电 0否 1是")
    private String isBroken;

    @Schema(description = "可逾期天数")
    @ExcelProperty("可逾期天数")
    private Integer overdueDay;

    @Schema(description = "是否已电控水 0否 1是", example = "2")
    @ExcelProperty("是否已电控水 0否 1是")
    private String cutType;

    @Schema(description = "提醒值")
    @ExcelProperty("提醒值")
    private BigDecimal remindValue;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}