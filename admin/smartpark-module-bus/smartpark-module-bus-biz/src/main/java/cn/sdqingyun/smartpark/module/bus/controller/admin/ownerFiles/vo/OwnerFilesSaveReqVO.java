package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerFiles.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 租客附件新增/修改 Request VO")
@Data
public class OwnerFilesSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24730")
    private Long id;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15447")
    @NotNull(message = "租客ID不能为空")
    private Long ownerId;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23533")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    @Schema(description = "用户服务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12906")
    @NotNull(message = "用户服务ID不能为空")
    private Long uid;

    @Schema(description = "文件名", example = "芋艿")
    private String name;

    @Schema(description = "文件网址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://xxx")
    @NotEmpty(message = "文件网址不能为空")
    private String url;

}