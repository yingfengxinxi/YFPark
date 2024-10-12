package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 房间对应自定义价格新增/修改 Request VO")
@Data
public class EnergyRoomPriceSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13066")
    private Long id;

    @Schema(description = "机构ID", example = "28830")
    private Long orgId;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4417")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @Schema(description = "关联自定义价格标准ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22778")
    @NotNull(message = "关联自定义价格标准ID不能为空")
    private Long energyPriceId;

    @Schema(description = "设备表种类", example = "1")
    private String type;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "状态，1启动，0审核中，4禁用不能为空")
    private String status;

}