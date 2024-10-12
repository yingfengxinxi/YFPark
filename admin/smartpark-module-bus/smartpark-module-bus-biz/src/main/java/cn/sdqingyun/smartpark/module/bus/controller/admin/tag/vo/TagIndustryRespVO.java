package cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 行业分类标签 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TagIndustryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3326")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "6079")
    @ExcelProperty("机构ID")
    private Integer orgId;

    @Schema(description = "标签名称", example = "张三")
    @ExcelProperty("标签名称")
    private String name;

    @Schema(description = "标签描述")
    @ExcelProperty("标签描述")
    private String descVillage;

    @Schema(description = "标签样式")
    @ExcelProperty("标签样式")
    private String color;

    @Schema(description = "状态，1启用，0禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态，1启用，0禁用")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}