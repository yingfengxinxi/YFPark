package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 账单费用分类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillCostCategoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "310")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "费用分类名称", example = "张三")
    @ExcelProperty("费用分类名称")
    private String name;

    @Schema(description = "是否设置为保证金类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否设置为保证金类型")
    private String isBond;

    @Schema(description = "是否系统内置", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否系统内置")
    private String isRoot;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23512")
    @ExcelProperty("父级id")
    private Long parentId;

    @Schema(description = "分类层级")
    @ExcelProperty("分类层级")
    private String level;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}