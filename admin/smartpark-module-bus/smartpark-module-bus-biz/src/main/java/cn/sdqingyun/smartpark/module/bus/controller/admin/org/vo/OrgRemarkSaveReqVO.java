package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 机构业务备注新增/修改 Request VO")
@Data
public class OrgRemarkSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8170")
    private Long id;

    @Schema(description = "机构ID", example = "8767")
    private Long orgId;

    @Schema(description = "业务id;合同id/账单id/等等", example = "25190")
    private Long businessId;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "业务类型1账单备注", example = "2")
    private String type;

}