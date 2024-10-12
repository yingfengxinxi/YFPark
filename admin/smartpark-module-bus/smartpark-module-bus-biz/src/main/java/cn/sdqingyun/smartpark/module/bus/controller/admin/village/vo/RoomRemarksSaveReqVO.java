package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 房源操作记录新增/修改 Request VO")
@Data
public class RoomRemarksSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27187")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27302")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2428")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "备注内容", example = "你猜")
    private String remark;

}