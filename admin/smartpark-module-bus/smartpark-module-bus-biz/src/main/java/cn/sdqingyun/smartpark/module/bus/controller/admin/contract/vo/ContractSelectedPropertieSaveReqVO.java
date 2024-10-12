package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.math.BigDecimal;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 合同中选中房源新增/修改 Request VO")
@Data
public class ContractSelectedPropertieSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6151")
    private Long id;

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27164")
    @NotNull(message = "合同id不能为空")
    private Long contractId;

    @Schema(description = "园区id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8477")
    @NotNull(message = "园区id不能为空")
    private Long parkId;

    @Schema(description = "楼宇id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27591")
    @NotNull(message = "楼宇id不能为空")
    private Long buildId;

    @Schema(description = "房间id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24755")
    @NotNull(message = "房间id不能为空")
    private Long villageRoomId;

    @Schema(description = "已选房源租赁面积")
    private BigDecimal rentalArea;

}