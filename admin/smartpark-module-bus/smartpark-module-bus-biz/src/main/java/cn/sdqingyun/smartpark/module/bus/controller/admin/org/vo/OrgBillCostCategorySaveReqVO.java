package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 账单费用分类新增/修改 Request VO")
@Data
public class OrgBillCostCategorySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "310")
    private Long id;

    @Schema(description = "费用分类名称", example = "张三")
    private String name;

    @Schema(description = "是否设置为保证金类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isBond;

    @Schema(description = "是否系统内置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isRoot;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23512")
    private Long parentId;

    @Schema(description = "分类层级")
    private String level;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

}