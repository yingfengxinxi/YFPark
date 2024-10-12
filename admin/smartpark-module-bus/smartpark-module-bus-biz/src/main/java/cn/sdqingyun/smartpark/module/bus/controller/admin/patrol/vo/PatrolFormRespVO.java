package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 巡检表单设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolFormRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19272")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构Id", example = "26573")
    @ExcelProperty("机构Id")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "表单内容")
    @ExcelProperty("表单内容")
    private String content;

    @Schema(description = "是否为默认配置0=否1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否为默认配置0=否1=是")
    private String isDefault;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}