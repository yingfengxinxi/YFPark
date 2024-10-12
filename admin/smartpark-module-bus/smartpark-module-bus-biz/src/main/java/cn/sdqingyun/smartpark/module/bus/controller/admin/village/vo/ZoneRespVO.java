package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目分区 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZoneRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13949")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "分区编号")
    @ExcelProperty("分区编号")
    private Long zoneNumber;

    @Schema(description = "分区名称", example = "李四")
    @ExcelProperty("分区名称")
    private String zoneName;

    @Schema(description = "项目ID", example = "21041")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "排序，越大越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态，0隐藏，1展示", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("数据状态，0隐藏，1展示")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}