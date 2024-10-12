package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 自定义抄表管理员新增/修改 Request VO")
@Data
public class EnergyAdminSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10516")
    private Long id;

    @Schema(description = "机构ID", example = "1291")
    private Long orgId;

    @Schema(description = "用户uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "5104")
    @NotNull(message = "用户uid不能为空")
    private Long uid;

    @Schema(description = "管理员姓名", example = "张三")
    private String name;

    @Schema(description = "0=管理员,1=超级管理员;", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "0=管理员,1=超级管理员;不能为空")
    private String role;

    @Schema(description = "状态;0=禁用,1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "状态;0=禁用,1=启用不能为空")
    private String status;

}