package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 工单应用管理员信息新增/修改 Request VO")
@Data
public class WorkOrderAdminSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29400")
    private Long id;

    @Schema(description = "机构ID", example = "3210")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "1=管理员;2=客服工作人员", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "1=管理员;2=客服工作人员不能为空")
    private String role;

    @Schema(description = "用户uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22093")
    private Long uid;

    @Schema(description = "岗位id集合", example = "19009")
    private String postId;

    @Schema(description = "姓名", example = "张三")
    private String name;

}