package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产耗材分类新增/修改 Request VO")
@Data
public class PropertyStuffCategorySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30313")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "分类编码")
    private String number;

    @Schema(description = "分类名称", example = "李四")
    private String name;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23274")
    @NotNull(message = "父级id不能为空")
    private Long parentId;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "上下层级关系")
    private String level;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示，0隐藏", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "显示状态，2禁用 1显示，0隐藏不能为空")
    private Integer status;

    @Schema(description = "是否禁用操作标志")
    private Boolean flag;
}