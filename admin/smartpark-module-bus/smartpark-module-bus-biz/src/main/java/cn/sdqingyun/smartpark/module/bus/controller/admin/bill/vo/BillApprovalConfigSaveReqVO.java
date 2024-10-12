package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 账单业务审批配置新增/修改 Request VO")
@Data
public class BillApprovalConfigSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6011")
    private Long id;

    @Schema(description = "机构id", example = "532")
    private Long orgId;

    @Schema(description = "业务类型adjust_approve调整金", example = "2")
    private String type;

    @Schema(description = "是否开启审批;0=否;1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否开启审批;0=否;1=是不能为空")
    private String isUse;

    @Schema(description = "其他信息")
    private String extra;

}