package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 税率规则配置新增/修改 Request VO")
@Data
public class OrgTaxRuleSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28295")
    private Long id;

    @Schema(description = "费用类型", example = "1")
    private String costType;

    @Schema(description = "税收分类编码ID", example = "19620")
    private Long taxCodeId;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "状态;1=开启;0=关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String status;

}