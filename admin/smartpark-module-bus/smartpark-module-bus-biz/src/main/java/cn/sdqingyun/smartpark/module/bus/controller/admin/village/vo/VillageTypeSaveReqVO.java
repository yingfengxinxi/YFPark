package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目类型新增/修改 Request VO")
@Data
public class VillageTypeSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14036")
    private Long id;

    @Schema(description = "类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "类型名称不能为空")
    private String name;

    @Schema(description = "类型别名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "类型别名不能为空")
    private String alias;

    @Schema(description = "类型背景图片")
    private String bgImg;

    @Schema(description = "类型icon图片")
    private String iconImg;

    @Schema(description = "服务上报的菜单及应用")
    private String menu;

    @Schema(description = "受限制过滤的菜单及应用")
    private String filterMenu;

}