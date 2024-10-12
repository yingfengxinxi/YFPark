package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产保养设置新增/修改 Request VO")
@Data
public class PropertyMaintainSetSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23386")
    private Long id;

    @Schema(description = "机构id", example = "12099")
    private Long orgId;

    @Schema(description = "保养项目名称", example = "张三")
    private String maintainVillageName;

    @Schema(description = "创建人名称")
    private String creatorName;

    @Schema(description = "状态 0禁用 1启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态 0禁用 1启用不能为空")
    private Long status;

}