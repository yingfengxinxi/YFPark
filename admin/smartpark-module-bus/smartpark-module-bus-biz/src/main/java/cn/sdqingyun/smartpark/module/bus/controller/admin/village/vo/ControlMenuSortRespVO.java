package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目租控管理菜单排序 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ControlMenuSortRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23297")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18964")
    @ExcelProperty("菜单ID")
    private Long menuId;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27310")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "排序值，值越高越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序值，值越高越前")
    private Long sort;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}