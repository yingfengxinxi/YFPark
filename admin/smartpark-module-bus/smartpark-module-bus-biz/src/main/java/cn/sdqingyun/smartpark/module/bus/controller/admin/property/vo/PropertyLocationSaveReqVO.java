package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 位置新增/修改 Request VO")
@Data
public class PropertyLocationSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11084")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "位置编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "位置编码不能为空")
    private String number;

    @Schema(description = "位置名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "巡检")
    @NotNull(message = "位置名称不能为空")
    private String name;

    @Schema(description = "位置级别字符串，逗号拼接上级id")
    private String level;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26703")
    @NotNull(message = "父级id不能为空")
    private Long parentId;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示，0隐藏", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "显示状态，2禁用 1显示，0隐藏不能为空")
    private Integer status;

}