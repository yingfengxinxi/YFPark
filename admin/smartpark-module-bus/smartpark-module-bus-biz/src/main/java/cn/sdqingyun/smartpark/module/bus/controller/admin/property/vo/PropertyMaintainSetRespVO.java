package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产保养设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyMaintainSetRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23386")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "12099")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "保养项目名称", example = "张三")
    @ExcelProperty("保养项目名称")
    private String maintainVillageName;

    @Schema(description = "创建人名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("创建人名称")
    private String creatorName;

    @Schema(description = "状态 0禁用 1启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态 0禁用 1启用")
    private Long status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}