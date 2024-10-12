package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 水电表预充值记录新增/修改 Request VO")
@Data
public class EnergyPreRechargeRecordSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15994")
    private Long id;

    @Schema(description = "机构iD", requiredMode = Schema.RequiredMode.REQUIRED, example = "22388")
    @NotNull(message = "机构iD不能为空")
    private Long orgId;

    @Schema(description = "水电表id", example = "987")
    private Long energyId;

    @Schema(description = "充值金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "14820")
    @NotNull(message = "充值金额不能为空")
    private BigDecimal rechargePrice;

    @Schema(description = "充值度数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "充值度数不能为空")
    private BigDecimal rechargeDegree;

    @Schema(description = "账单id", example = "13315")
    private Long billId;

    @Schema(description = "流水id", example = "8400")
    private Long billStreamId;

    @Schema(description = "流水号")
    private String streamNumber;

    @Schema(description = "账单编号")
    private String billNumber;

    @Schema(description = "设备上报返回的数据")
    private String result;

}