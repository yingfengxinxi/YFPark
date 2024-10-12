package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 账单收据编号规则新增/修改 Request VO")
@Data
public class OrgBillReceiptRuleSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12810")
    private Long id;

    @Schema(description = "收据编号规则名称", example = "李四")
    private String name;

    @Schema(description = "收据编号前缀")
    private String prefix;

    @Schema(description = "开始编号")
    private String startNumber;

    @Schema(description = "结束编号")
    private String endNumber;

    @Schema(description = "应用楼宇id,多个楼宇使用逗号隔开(1,2,3)")
    private String buildBind;

}