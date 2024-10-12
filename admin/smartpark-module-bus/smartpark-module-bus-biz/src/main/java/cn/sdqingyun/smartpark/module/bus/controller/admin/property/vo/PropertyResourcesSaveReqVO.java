package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产静态资源管理新增/修改 Request VO")
@Data
public class PropertyResourcesSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30480")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4077")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "资源业务类型", example = "2")
    private Integer type;

    @Schema(description = "资源第三方链接", example = "https://xxx")
    private String url;

    @Schema(description = "hash唯一标识")
    private String ossHash;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "30046")
    @NotNull(message = "操作人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "24667")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}