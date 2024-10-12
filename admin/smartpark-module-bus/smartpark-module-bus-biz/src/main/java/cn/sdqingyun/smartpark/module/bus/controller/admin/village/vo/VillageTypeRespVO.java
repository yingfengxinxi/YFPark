package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目类型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class VillageTypeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14036")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("类型名称")
    private String name;

    @Schema(description = "类型别名", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("类型别名")
    private String alias;

    @Schema(description = "类型背景图片")
    @ExcelProperty("类型背景图片")
    private String bgImg;

    @Schema(description = "类型icon图片")
    @ExcelProperty("类型icon图片")
    private String iconImg;

    @Schema(description = "服务上报的菜单及应用")
    @ExcelProperty("服务上报的菜单及应用")
    private String menu;

    @Schema(description = "受限制过滤的菜单及应用")
    @ExcelProperty("受限制过滤的菜单及应用")
    private String filterMenu;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}