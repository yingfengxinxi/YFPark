package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产分类新增/修改 Request VO")
@Data
public class PropertyCategorySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28768")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "分类编码不能为空")
    private String number;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "社会类")
    @NotNull(message = "分类名称不能为空")
    private String name;

    @Schema(description = "分类级别字符串，逗号拼接上级id")
    private String level;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14183")
    @NotNull(message = "父级id不能为空")
    private Long parentId;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示，0隐藏", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "显示状态，2禁用 1显示，0隐藏不能为空")
    private Integer status;

    @Schema(description = "是否禁用操作标志")
    private Boolean flag;
}