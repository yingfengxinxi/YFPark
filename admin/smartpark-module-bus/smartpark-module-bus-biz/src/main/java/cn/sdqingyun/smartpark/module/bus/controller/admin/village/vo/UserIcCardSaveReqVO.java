package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 住户的IC卡新增/修改 Request VO")
@Data
public class UserIcCardSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4540")
    private Long id;

    @Schema(description = "village_user用户表ID，可能为空", example = "23162")
    private Long userId;

    @Schema(description = "归属租客ID", example = "29114")
    private Long ownerId;

    @Schema(description = "IC卡号（128位加密）")
    private String icCard;

    @Schema(description = "数据状态(1使用，0禁用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "数据状态(1使用，0禁用)不能为空")
    private Integer status;

}