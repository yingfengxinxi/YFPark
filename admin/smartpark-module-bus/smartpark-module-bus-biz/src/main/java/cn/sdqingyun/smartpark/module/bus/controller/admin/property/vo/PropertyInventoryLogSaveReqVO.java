package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产盘点操作日志新增/修改 Request VO")
@Data
public class PropertyInventoryLogSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17922")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26796")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "操作人", requiredMode = Schema.RequiredMode.REQUIRED, example = "1866")
    @NotNull(message = "操作人不能为空")
    private Long operatorId;

    @Schema(description = "操作人姓名", example = "赵六")
    private String operatorName;

    @Schema(description = "盘点清单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5646")
    @NotNull(message = "盘点清单id不能为空")
    private Long inventoryId;

    @Schema(description = "盘点记录id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26664")
    @NotNull(message = "盘点记录id不能为空")
    private Long recordId;

    @Schema(description = "操作类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "操作类型不能为空")
    private String type;

    @Schema(description = "操作内容")
    private String content;

}