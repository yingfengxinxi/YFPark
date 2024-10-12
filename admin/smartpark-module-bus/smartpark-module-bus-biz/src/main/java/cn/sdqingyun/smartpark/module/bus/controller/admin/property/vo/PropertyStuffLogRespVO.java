package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产耗材业务记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10521")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "28132")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "耗材id", example = "26088")
    @ExcelProperty("耗材id")
    private Long stuffId;

    @Schema(description = "仓库id", example = "4563")
    @ExcelProperty("仓库id")
    private Long depositoryId;

    @Schema(description = "用户uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "8490")
    @ExcelProperty("用户uid")
    private Long userId;

    @Schema(description = "业务")
    @ExcelProperty("业务")
    private String business;

    @Schema(description = "类型", example = "2")
    @ExcelProperty("类型")
    private String type;

    @Schema(description = "使用数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("使用数量")
    private BigDecimal num;

    @Schema(description = "附加信息")
    @ExcelProperty("附加信息")
    private String extraData;

    @Schema(description = "创建人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22713")
    @ExcelProperty("创建人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "9085")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}