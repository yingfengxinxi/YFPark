package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 巡检表单设置新增/修改 Request VO")
@Data
public class PatrolFormSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19272")
    private Long id;

    @Schema(description = "机构Id", example = "26573")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "表单内容")
    private String content;

    @Schema(description = "是否为默认配置0=否1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isDefault;

}