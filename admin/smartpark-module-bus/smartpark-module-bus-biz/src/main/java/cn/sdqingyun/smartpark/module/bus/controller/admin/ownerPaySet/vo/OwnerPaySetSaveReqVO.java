package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerPaySet.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 企业自动缴费费用配置新增/修改 Request VO")
@Data
public class OwnerPaySetSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32365")
    private Long id;

    @Schema(description = "机构id", example = "27029")
    private Long orgId;

    @Schema(description = "所属项目id", example = "6931")
    private Long villageId;

    @Schema(description = "楼宇id", example = "4744")
    private Long buildId;

    @Schema(description = "租客id", example = "12599")
    private Long ownerId;

    @Schema(description = "自动缴费费用类型设置")
    private String costTypes;

}