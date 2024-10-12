package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 账单费用类型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillCostTypeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18059")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "费用分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10176")
    @ExcelProperty("费用分类id")
    private Long categoryId;

    @Schema(description = "是否为保证金类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否为保证金类型")
    private String isBond;

    @Schema(description = "是否系统内置", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否系统内置")
    private String isRoot;

    @Schema(description = "必须缴费的项目，否则影响业务使用", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("必须缴费的项目，否则影响业务使用")
    private String isImportant;

    @Schema(description = "费用类型名称", example = "芋艿")
    @ExcelProperty("费用类型名称")
    private String name;

    @Schema(description = "费用类型", example = "2")
    @ExcelProperty("费用类型")
    private String costType;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "费用状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("费用状态")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}