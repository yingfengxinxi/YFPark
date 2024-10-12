package cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 机构用户自定义操作配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SpercialSettingRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17588")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13108")
    @ExcelProperty("用户ID")
    private Long uid;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "243")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "自定义类型", example = "2")
    @ExcelProperty("自定义类型")
    private String type;

    @Schema(description = "用户自定义配置内容json，JSON串({buildId: [1011, 1021, 1031], villageId: [101, 102, 103]})")
    @ExcelProperty("用户自定义配置内容")
    private String content;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}