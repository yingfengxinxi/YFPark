package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 自定义价格标准表-阶梯单价新增/修改 Request VO")
@Data
public class EnergyUnitPriceSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17425")
    private Long id;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "设备表种类不能为空")
    private String type;

    @Schema(description = "机构ID", example = "13190")
    private Long orgId;

    @Schema(description = "用量区间1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "用量区间1不能为空")
    private String startUsageRange;

    @Schema(description = "用量区间2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "用量区间2不能为空")
    private String endUsageRange;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "14560")
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @Schema(description = "自定义价格标准表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16066")
    @NotNull(message = "自定义价格标准表id不能为空")
    private Long energyPriceId;

}