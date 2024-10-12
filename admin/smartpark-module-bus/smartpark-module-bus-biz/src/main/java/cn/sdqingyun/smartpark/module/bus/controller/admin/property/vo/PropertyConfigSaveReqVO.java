package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产配置信息新增/修改 Request VO")
@Data
public class PropertyConfigSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14513")
    private Long id;

    @Schema(description = "机构id", example = "922")
    private Long orgId;

    @Schema(description = "绑定的项目楼宇信息villageId",requiredMode = Schema.RequiredMode.REQUIRED, example = "1,2")
    @NotNull(message = "绑定的项目楼宇信息不能为空")
    private String buildBind;

    @Schema(description = "资产编码规则",requiredMode = Schema.RequiredMode.REQUIRED, example = "{ \"condition_txt\": \"gt\", \"condition_num\": 6, \"condition_start\": 4}")
    @NotNull(message = "资产编码规则不能为空")
    private String numberRule;

    @Schema(description = "附属信息json;")
    private String extra;

    @Schema(description = "创建人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}