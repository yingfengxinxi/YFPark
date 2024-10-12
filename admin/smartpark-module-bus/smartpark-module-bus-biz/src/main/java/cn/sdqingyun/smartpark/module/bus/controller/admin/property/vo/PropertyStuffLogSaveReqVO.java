package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 资产耗材业务记录新增/修改 Request VO")
@Data
public class PropertyStuffLogSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10521")
    private Long id;

    @Schema(description = "机构id", example = "28132")
    private Long orgId;

    @Schema(description = "耗材id", example = "26088")
    private Long stuffId;

    @Schema(description = "仓库id", example = "4563")
    private Long depositoryId;

    @Schema(description = "用户uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "8490")
    @NotNull(message = "用户uid不能为空")
    private Long userId;

    @Schema(description = "业务")
    private String business;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "使用数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "使用数量不能为空")
    private BigDecimal num;

    @Schema(description = "附加信息")
    private String extraData;

    @Schema(description = "创建人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22713")
    @NotNull(message = "创建人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "9085")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}