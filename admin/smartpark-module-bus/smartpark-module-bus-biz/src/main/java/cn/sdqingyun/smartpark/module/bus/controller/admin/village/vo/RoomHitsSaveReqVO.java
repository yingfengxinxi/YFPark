package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 房间点击量新增/修改 Request VO")
@Data
public class RoomHitsSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25389")
    private Long id;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30962")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32409")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @Schema(description = "日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "日期不能为空")
    private LocalDateTime day;

    @Schema(description = "点击量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "点击量不能为空")
    private Integer hits;

}