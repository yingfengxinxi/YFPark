package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 巡检标签模板新增/修改 Request VO")
@Data
public class PatrolTagSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26444")
    private Long id;

    @Schema(description = "机构Id", example = "292")
    private Long orgId;

    @Schema(description = "应用标识EQUIPMENT_INSPECTION=巡检;SECURITY_INSPECTION=安防", example = "292")
    private String application;

    @Schema(description = "模板id(数据字典PATROL_TAG_TEMPLATE)", example = "9132")
    private Long templateId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模板名称", example = "赵六")
    private String name;

    @Schema(description = "字段上限数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字段上限数量不能为空")
    private Integer fieldLimit;

    @Schema(description = "有无logo;0=无;1=有", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "有无logo;0=无;1=有不能为空")
    private String hasLogo;

    @Schema(description = "应用数据json")
    private String applyJson;

}