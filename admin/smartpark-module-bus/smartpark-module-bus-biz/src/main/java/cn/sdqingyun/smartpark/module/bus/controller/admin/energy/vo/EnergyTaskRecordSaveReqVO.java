package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 自定义抄表任务记录新增/修改 Request VO")
@Data
public class EnergyTaskRecordSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15308")
    private Long id;

    @Schema(description = "机构ID", example = "30135")
    private Long orgId;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15672")
    @NotNull(message = "计划id不能为空")
    private Long planId;

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31056")
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    @Schema(description = "自定义表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31340")
    @NotNull(message = "自定义表id不能为空")
    private Long energyId;

    @Schema(description = "第三方任务id", example = "8056")
    private Long thirdTaskId;

    @Schema(description = "抄录数据json")
    private String extraData;

}