package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 账单开据记录新增/修改 Request VO")
@Data
public class OrgBillReceiptLogSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22715")
    private Long id;

    @Schema(description = "开票批次凭证")
    private String taskKey;

    @Schema(description = "收据模板id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4161")
    @NotNull(message = "收据模板id不能为空")
    private Long templateId;

    @Schema(description = "所选账单id集合")
    private String billIds;

    @Schema(description = "所选子账单id集合")
    private String subbillIds;

    @Schema(description = "收据账单信息(包含合并账单)")
    private String receiptJson;

    @Schema(description = "本次选择的收据账单组数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "本次选择的收据账单组数不能为空")
    private Integer chooseNum;

    @Schema(description = "本次选择的账单开据金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "本次选择的账单开据金额不能为空")
    private BigDecimal chooseAmount;

}