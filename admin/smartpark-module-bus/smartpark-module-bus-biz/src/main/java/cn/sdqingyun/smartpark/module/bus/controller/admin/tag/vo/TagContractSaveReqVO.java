package cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 合同标签新增/修改 Request VO")
@Data
public class TagContractSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28680")
    private Long id;

    @Schema(description = "机构ID", example = "12968")
    private Integer orgId;

    @Schema(description = "标签名称", example = "赵六")
    private String name;

    @Schema(description = "标签描述")
    private String descVillage;

    @Schema(description = "标签样式")
    private String color;

    @Schema(description = "状态，1启用，0禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态，1启用，0禁用不能为空")
    private Integer status;

}