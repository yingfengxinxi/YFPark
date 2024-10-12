package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单分类标签配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CategoryLabelRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7185")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "16063")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "标签名称", example = "王五")
    @ExcelProperty("标签名称")
    private String name;

    @Schema(description = "工单子类id", example = "20282")
    @ExcelProperty("工单子类id")
    private Long subcatId;

    @Schema(description = "排序值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序值")
    private Integer sort;

    @Schema(description = "最近操作时间")
    @ExcelProperty("最近操作时间")
    private LocalDateTime lastTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "使用频率")
    private Integer usageFrequency;

}