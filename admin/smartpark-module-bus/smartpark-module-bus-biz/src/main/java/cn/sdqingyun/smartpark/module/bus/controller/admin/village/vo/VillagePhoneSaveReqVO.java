package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目电话新增/修改 Request VO")
@Data
public class VillagePhoneSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32410")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18628")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4993")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23819")
    @NotNull(message = "分类ID不能为空")
    private Long catId;

    @Schema(description = "号码名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "号码名称不能为空")
    private String phoneName;

    @Schema(description = "号码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "号码不能为空")
    private String phone;

    @Schema(description = "排序值", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "状态（1正常，0隐藏）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态（1正常，0隐藏）不能为空")
    private Integer status;

    @Schema(description = "紧急电话（1是，0否）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "紧急电话（1是，0否）不能为空")
    private Integer urgent;

}