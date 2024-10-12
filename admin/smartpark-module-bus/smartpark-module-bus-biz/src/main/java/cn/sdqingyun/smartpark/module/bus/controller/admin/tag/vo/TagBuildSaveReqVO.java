package cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 楼宇标签新增/修改 Request VO")
@Data
public class TagBuildSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14517")
    private Long id;

    @Schema(description = "机构ID", example = "25313")
    private Integer orgId;

    @Schema(description = "标签名称", example = "赵六")
    private String name;

    @Schema(description = "标签描述")
    private String descVillage;

    @Schema(description = "标签样式")
    private String color;

    @Schema(description = "用户是否可见（1可见，0不可见）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户是否可见（1可见，0不可见）不能为空")
    private Integer userShow;

    @Schema(description = "状态，1启用，0禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态，1启用，0禁用不能为空")
    private Integer status;

}