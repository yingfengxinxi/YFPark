package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 税收分类编码配置新增/修改 Request VO")
@Data
public class OrgTaxCodeSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7993")
    private Long id;

    @Schema(description = "商品/服务名称", example = "李四")
    private String name;

    @Schema(description = "税收编号")
    private String taxCode;

    @Schema(description = "税率")
    private BigDecimal taxRate;

}