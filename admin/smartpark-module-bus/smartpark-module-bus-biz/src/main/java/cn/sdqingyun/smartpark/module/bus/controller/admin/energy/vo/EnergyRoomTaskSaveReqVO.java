package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 房间对应抄表任务新增/修改 Request VO")
@Data
public class EnergyRoomTaskSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20114")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14024")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @Schema(description = "关联抄表任务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22420")
    @NotNull(message = "关联抄表任务ID不能为空")
    private Long energyTaskId;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "状态，1启动，0审核中，4禁用不能为空")
    private String status;

}