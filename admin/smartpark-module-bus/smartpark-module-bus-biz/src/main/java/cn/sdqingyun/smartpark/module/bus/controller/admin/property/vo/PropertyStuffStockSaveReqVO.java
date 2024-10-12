package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 耗材即时库存新增/修改 Request VO")
@Data
public class PropertyStuffStockSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18952")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29345")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "耗材物料id", example = "18539")
    private Long stuffId;

    @Schema(description = "仓库id;需同步该信息", requiredMode = Schema.RequiredMode.REQUIRED, example = "19709")
    @NotNull(message = "仓库id;需同步该信息不能为空")
    private Long depositoryId;

    @Schema(description = "入库流程编号")
    private String processCode;

    @Schema(description = "可用库存")
    private BigDecimal usableNum;

    @Schema(description = "冻结库存")
    private BigDecimal frozenNum;

    @Schema(description = "总库存")
    private BigDecimal totalNum;

    @Schema(description = "总金额", example = "15952")
    private BigDecimal totalPrice;

    @Schema(description = "耗材定价", example = "30366")
    private BigDecimal chargePrice;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "其他")
    private String extra;

    @Schema(description = "是否达到安全库存上限;默认安全", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否达到安全库存上限;默认安全不能为空")
    private Short isStockUp;

    @Schema(description = "是否达到安全库存下限;默认安全", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否达到安全库存下限;默认安全不能为空")
    private Short isStockLower;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "24099")
    @NotNull(message = "操作人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "4194")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}