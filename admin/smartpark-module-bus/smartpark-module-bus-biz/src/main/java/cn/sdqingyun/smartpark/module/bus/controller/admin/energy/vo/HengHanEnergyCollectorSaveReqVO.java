package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 采集器电表关联表（一对多的关系）新增/修改 Request VO")
@Data
public class HengHanEnergyCollectorSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12712")
    private Long id;

    @Schema(description = "机构iD", requiredMode = Schema.RequiredMode.REQUIRED, example = "29633")
    @NotNull(message = "机构iD不能为空")
    private Long orgId;

    @Schema(description = "采集器id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16013")
    @NotNull(message = "采集器id不能为空")
    private Long collectorId;

    @Schema(description = "水电表id、关联energy表id", example = "31107")
    private Long energyId;

}