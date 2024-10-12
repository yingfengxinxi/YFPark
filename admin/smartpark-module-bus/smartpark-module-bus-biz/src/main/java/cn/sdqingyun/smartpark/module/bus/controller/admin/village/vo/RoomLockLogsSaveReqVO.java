package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 项目房间锁定日志新增/修改 Request VO")
@Data
public class RoomLockLogsSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28066")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2271")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16030")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "状态(1锁定，0取消锁定)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态(1锁定，0取消锁定)不能为空")
    private Integer status;

    @Schema(description = "原因", example = "不香")
    private String reason;

    @Schema(description = "操作时间")
    private LocalDateTime lockTime;

    @Schema(description = "操作人uid", example = "21443")
    private Long operationUid;

}