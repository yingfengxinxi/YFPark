package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目单元 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UnitRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17252")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "单元编号")
    @ExcelProperty("单元编号")
    private Long unitNumber;

    @Schema(description = "单元名称", example = "李四")
    @ExcelProperty("单元名称")
    private String unitName;

    @Schema(description = "楼栋ID", example = "7648")
    @ExcelProperty("楼栋ID")
    private Long buildId;

    @Schema(description = "分区ID", example = "13430")
    @ExcelProperty("分区ID")
    private Long zoneId;

    @Schema(description = "项目ID", example = "23241")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "排序，越大越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("数据状态(1使用，0隐藏)")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}