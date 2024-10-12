package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目绘制数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class VillageDrawRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11936")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "10948")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "项目id", example = "14290")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "绘制内容")
    @ExcelProperty("绘制内容")
    private String drawInfo;

    @Schema(description = "设置")
    @ExcelProperty("设置")
    private String settingInfo;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}