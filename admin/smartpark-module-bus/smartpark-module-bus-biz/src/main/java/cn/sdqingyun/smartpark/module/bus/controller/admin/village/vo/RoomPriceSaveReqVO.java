package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 房间价格新增/修改 Request VO")
@Data
public class RoomPriceSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32631")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18968")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @Schema(description = "创建日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "创建日期不能为空")
    private LocalDateTime createdDay;

    @Schema(description = "建筑面积", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "建筑面积不能为空")
    private BigDecimal buildArea;

    @Schema(description = "房价（m²/天）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房价（m²/天）不能为空")
    private BigDecimal squareDay;

    @Schema(description = "房价（m²/月）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房价（m²/月）不能为空")
    private BigDecimal squareMonth;

    @Schema(description = "房价（天）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房价（天）不能为空")
    private BigDecimal day;

    @Schema(description = "房价（月）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房价（月）不能为空")
    private BigDecimal month;

    @Schema(description = "房价（年）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房价（年）不能为空")
    private BigDecimal year;

    @Schema(description = "状态（1正常，0隐藏）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态（1正常，0隐藏）不能为空")
    private Integer status;

}