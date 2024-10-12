package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 智能表参数配置新增/修改 Request VO")
@Data
public class BailingOrgConfigSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "846")
    private Long id;

    @Schema(description = "机构ID", example = "8904")
    private Long orgId;

    @Schema(description = "类型【数据字典值BAILING_ORG_CONFIG】", example = "芋艿")
    private String type;

    @Schema(description = "名称", example = "芋艿")
    private String key;

    @Schema(description = "值")
    private String value;

}