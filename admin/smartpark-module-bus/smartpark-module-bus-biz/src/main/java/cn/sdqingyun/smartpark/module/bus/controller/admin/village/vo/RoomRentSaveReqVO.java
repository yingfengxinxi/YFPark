package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 租客在租/绑定房间新增/修改 Request VO")
@Data
public class RoomRentSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24763")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4965")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3743")
    @NotNull(message = "项目id不能为空")
    private Long villageId;

    @Schema(description = "楼宇id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28546")
    @NotNull(message = "楼宇id不能为空")
    private Long buildId;

    @Schema(description = "楼层id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1061")
    @NotNull(message = "楼层id不能为空")
    private Long layerId;

    @Schema(description = "房间id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7631")
    @NotNull(message = "房间id不能为空")
    private Long roomId;

    @Schema(description = "租客id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23794")
    @NotNull(message = "租客id不能为空")
    private Long ownerId;

    @Schema(description = "合同id", example = "7074")
    private Long contractId;

    @Schema(description = "租赁到期时间")
    private LocalDateTime expireTime;

    @Schema(description = "来源标识;1=合同;2=物业绑定", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "来源标识;1=合同;2=物业绑定不能为空")
    private Integer source;

    @Schema(description = "房间状态;0=在租;", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "房间状态;0=在租;不能为空")
    private Integer status;

    @Schema(description = "其他")
    private String extra;

}