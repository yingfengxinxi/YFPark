package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产静态资源管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyResourcesRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30480")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4077")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "资源业务类型", example = "2")
    @ExcelProperty("资源业务类型")
    private Integer type;

    @Schema(description = "资源第三方链接", example = "https://xxx")
    @ExcelProperty("资源第三方链接")
    private String url;

    @Schema(description = "hash唯一标识")
    @ExcelProperty("hash唯一标识")
    private String ossHash;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "30046")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "24667")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}