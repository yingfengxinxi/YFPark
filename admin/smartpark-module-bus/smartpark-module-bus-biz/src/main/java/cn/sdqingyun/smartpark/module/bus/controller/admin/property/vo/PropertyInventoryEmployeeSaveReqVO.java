package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产盘点员工记录新增/修改 Request VO")
@Data
public class PropertyInventoryEmployeeSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22856")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24926")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "盘点清单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8453")
    @NotNull(message = "盘点清单id不能为空")
    private Long inventoryId;

    @Schema(description = "盘点员工id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8609")
    @NotNull(message = "盘点员工id不能为空")
    private Long userId;

    @Schema(description = "资产分类id")
    private String typeIds;

    @Schema(description = "部门id")
    private String departmentIds;

    @Schema(description = "位置id")
    private String positionIds;

    @Schema(description = "支付状态", example = "2")
    private String propertyStatus;

    @Schema(description = "管理员id", example = "13174")
    private String adminId;

    @Schema(description = "购置方式", example = "1")
    private String purchaseType;

    @Schema(description = "完成状态 1是0否", example = "2")
    private Integer status;

    @Schema(description = "是否是全部权限", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否是全部权限不能为空")
    private Integer isAll;

    @Schema(description = "每个用户的范围回显冗余字段")
    private String exterData;

}