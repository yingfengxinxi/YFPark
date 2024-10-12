package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产盘点操作日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyInventoryLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17922")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26796")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "操作人", requiredMode = Schema.RequiredMode.REQUIRED, example = "1866")
    @ExcelProperty("操作人")
    private Long operatorId;

    @Schema(description = "操作人姓名", example = "赵六")
    @ExcelProperty("操作人姓名")
    private String operatorName;

    @Schema(description = "盘点清单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5646")
    @ExcelProperty("盘点清单id")
    private Long inventoryId;

    @Schema(description = "盘点记录id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26664")
    @ExcelProperty("盘点记录id")
    private Long recordId;

    @Schema(description = "操作类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("操作类型")
    private String type;

    @Schema(description = "操作内容")
    @ExcelProperty("操作内容")
    private String content;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}