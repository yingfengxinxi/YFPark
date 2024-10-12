package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 机构账单收支流水附件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillAnnexRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22340")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "附件唯一标识", example = "8246")
    @ExcelProperty("附件唯一标识")
    private String objectId;

    @Schema(description = "流水id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10068")
    @ExcelProperty("流水id")
    private Long sourceId;

    @Schema(description = "父级id", example = "16007")
    @ExcelProperty("父级id")
    private Long parentId;

    @Schema(description = "置顶排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("置顶排序")
    private Long sort;

    @Schema(description = "文件名/文件夹", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("文件名/文件夹")
    private String name;

    @Schema(description = "账单操作类型;1=账单附件;2=流水附件", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("账单操作类型;1=账单附件;2=流水附件")
    private String type;

    @Schema(description = "1=pc;2=phone", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("1=pc;2=phone")
    private String annexSource;

    @Schema(description = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("文件路径")
    private String filePath;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}