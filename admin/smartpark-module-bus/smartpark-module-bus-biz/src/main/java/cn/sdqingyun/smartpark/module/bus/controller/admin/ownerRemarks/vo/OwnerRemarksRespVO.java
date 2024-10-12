package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerRemarks.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 租客备注信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OwnerRemarksRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20488")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "租客id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19772")
    @ExcelProperty("租客id")
    private Long ownId;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21676")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "备注内容", example = "你猜")
    @ExcelProperty("备注内容")
    private String remark;

    @Schema(description = "操作人uid", example = "13731")
    @ExcelProperty("操作人uid")
    private Long operationUid;

    @Schema(description = "操作人姓名", example = "张三")
    @ExcelProperty("操作人姓名")
    private Long operationName;

    @Schema(description = "操作时间")
    @ExcelProperty("操作时间")
    private LocalDateTime operationTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}