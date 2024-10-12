package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 应用巡检计划绑定巡检点新增/修改 Request VO")
@Data
public class PatrolPlanPositionSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25001")
    private Long id;

    @Schema(description = "机构ID", example = "29430")
    private Long orgId;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10478")
    private Long planId;

    @Schema(description = "巡检点id", requiredMode = Schema.RequiredMode.REQUIRED, example = "66")
    @NotNull(message = "巡检点id不能为空")
    private Long positionId;

    @Schema(description = "巡检点编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "66")
    private String number;

    @Schema(description = "巡检点名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "66")
    private String name;

    @Schema(description = "资产位置", requiredMode = Schema.RequiredMode.REQUIRED, example = "66")
    private String positionName;

    @Schema(description = "是否扫码打卡0=否1=是")
    private String isScanCodeCheck;

    @Schema(description = "是否NFC打卡0=否1=是")
    private String isNfcClock;

    @Schema(description = "排序")
    private Integer sort;

}