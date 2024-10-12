package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目房间锁定日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoomLockLogsRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28066")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2271")
    @ExcelProperty("房间ID")
    private Long roomId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16030")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "状态(1锁定，0取消锁定)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态(1锁定，0取消锁定)")
    private Integer status;

    @Schema(description = "原因", example = "不香")
    @ExcelProperty("原因")
    private String reason;

    @Schema(description = "操作时间")
    @ExcelProperty("操作时间")
    private LocalDateTime lockTime;

    @Schema(description = "操作人uid", example = "21443")
    @ExcelProperty("操作人uid")
    private Long operationUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}