package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目集合表，方便快速选择新增/修改 Request VO")
@Data
public class VillageCollectionSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30355")
    private Long id;

    @Schema(description = "机构ID", example = "938")
    private Long orgId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1582")
    @NotNull(message = "用户ID不能为空")
    private Long uid;

    @Schema(description = "集合名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "集合名称不能为空")
    private String collectionName;

    @Schema(description = "集合下的楼宇列表")
    private String collectionBuild;

    @Schema(description = "项目类型", example = "2")
    private String villageType;

}