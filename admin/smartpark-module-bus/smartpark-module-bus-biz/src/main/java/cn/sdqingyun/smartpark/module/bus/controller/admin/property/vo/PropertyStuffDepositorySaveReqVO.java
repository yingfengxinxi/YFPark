package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 耗材档案信息新增/修改 Request VO")
@Data
public class PropertyStuffDepositorySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14116")
    private Long id;

    @Schema(description = "机构id")
    private Long orgId;

    @Schema(description = "仓库编号")
    private String number;

    @Schema(description = "仓库名称", example = "李四")
    private String name;

    @Schema(description = "上级仓库id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25605")
    @NotNull(message = "上级仓库id不能为空")
    private Long parentId;

    @Schema(description = "耗材仓库状态;1=启用,0=禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "耗材仓库状态;1=启用,0=禁用不能为空")
    private Short status;

    @Schema(description = "上下层级关系")
    private String level;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "序号")
    private Integer sort;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}