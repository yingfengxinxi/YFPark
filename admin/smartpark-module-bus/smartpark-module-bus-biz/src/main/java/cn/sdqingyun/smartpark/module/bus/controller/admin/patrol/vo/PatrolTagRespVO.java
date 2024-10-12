package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 巡检标签模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolTagRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26444")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "应用标识EQUIPMENT_INSPECTION=巡检;SECURITY_INSPECTION=安防", example = "292")
    private String application;
    @Schema(description = "机构Id", example = "292")
    @ExcelProperty("机构Id")
    private Long orgId;

    @Schema(description = "模板id(数据字典PATROL_TAG_TEMPLATE)", example = "9132")
    @ExcelProperty("模板id(数据字典PATROL_TAG_TEMPLATE)")
    private Long templateId;

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
    private String hasLogo;

    @Schema(description = "应用数据json")
    @ExcelProperty("应用数据json")
    private String applyJson;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}