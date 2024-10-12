package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 电表操作id关联表、回调用新增/修改 Request VO")
@Data
public class HengHanEnergyOprSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17994")
    private Long id;

    @Schema(description = "机构iD", example = "22160")
    private Long orgId;

    @Schema(description = "操作ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18341")
    @NotNull(message = "操作ID不能为空")
    private Long oprId;

    @Schema(description = "电表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14502")
    @NotNull(message = "电表id不能为空")
    private Long energyId;

    @Schema(description = "操作类型、read（抄表） open（开闸） close(关闸)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "操作类型、read（抄表） open（开闸） close(关闸)不能为空")
    private String type;

}