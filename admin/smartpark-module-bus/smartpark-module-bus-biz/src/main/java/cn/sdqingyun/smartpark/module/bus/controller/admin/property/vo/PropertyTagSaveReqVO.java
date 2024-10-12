package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产标签模板新增/修改 Request VO")
@Data
public class PropertyTagSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13691")
    private Long id;

    @Schema(description = "机构ID", example = "7559")
    private Long orgId;

    @Schema(description = "0=未应用;1=应用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "0=未应用;1=应用不能为空")
    private Short isApply;

    @Schema(description = "0=自定义模板;1=系统默认模板", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "0=自定义模板;1=系统默认模板不能为空")
    private Short isDefault;

    @Schema(description = "模板链接")
    private String templatePath;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模板名称", example = "赵六")
    private String name;

    @Schema(description = "字段上限数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字段上限数量不能为空")
    private Integer fieldLimit;

    @Schema(description = "有无logo;0=无;1=有", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "有无logo;0=无;1=有不能为空")
    private Short hasLogo;

    @Schema(description = "应用数据json")
    private String applyJson;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}