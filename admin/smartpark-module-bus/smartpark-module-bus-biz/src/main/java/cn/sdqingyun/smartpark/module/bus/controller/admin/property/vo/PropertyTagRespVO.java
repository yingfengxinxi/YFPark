package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产标签模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyTagRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13691")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "7559")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "0=未应用;1=应用", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("0=未应用;1=应用")
    private Short isApply;

    @Schema(description = "0=自定义模板;1=系统默认模板", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("0=自定义模板;1=系统默认模板")
    private Short isDefault;

    @Schema(description = "模板链接")
    @ExcelProperty("模板链接")
    private String templatePath;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "模板名称", example = "赵六")
    @ExcelProperty("模板名称")
    private String name;

    @Schema(description = "字段上限数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("字段上限数量")
    private Integer fieldLimit;

    @Schema(description = "有无logo;0=无;1=有", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("有无logo;0=无;1=有")
    private Short hasLogo;

    @Schema(description = "应用数据json")
    @ExcelProperty("应用数据json")
    private String applyJson;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "6095")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22518")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}