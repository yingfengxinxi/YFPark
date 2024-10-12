package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 账单费用类型新增/修改 Request VO")
@Data
public class OrgBillCostTypeSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18059")
    private Long id;

    @Schema(description = "费用分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10176")
    @NotNull(message = "费用分类id不能为空")
    private Long categoryId;

    @Schema(description = "是否为保证金类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isBond;

    @Schema(description = "是否系统内置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isRoot;

    @Schema(description = "必须缴费的项目，否则影响业务使用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "必须缴费的项目，否则影响业务使用不能为空")
    private String isImportant;

    @Schema(description = "费用类型名称", example = "芋艿")
    private String name;

    @Schema(description = "费用类型", example = "2")
    private String costType;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "费用状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "费用状态不能为空")
    private String status;

}