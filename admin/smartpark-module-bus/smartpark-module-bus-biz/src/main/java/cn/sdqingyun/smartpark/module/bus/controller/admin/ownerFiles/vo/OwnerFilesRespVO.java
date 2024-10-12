package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerFiles.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 租客附件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OwnerFilesRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24730")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15447")
    @ExcelProperty("租客ID")
    private Long ownerId;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23533")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "用户服务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12906")
    @ExcelProperty("用户服务ID")
    private Long uid;

    @Schema(description = "文件名", example = "芋艿")
    @ExcelProperty("文件名")
    private String name;

    @Schema(description = "文件网址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://xxx")
    @ExcelProperty("文件网址")
    private String url;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}