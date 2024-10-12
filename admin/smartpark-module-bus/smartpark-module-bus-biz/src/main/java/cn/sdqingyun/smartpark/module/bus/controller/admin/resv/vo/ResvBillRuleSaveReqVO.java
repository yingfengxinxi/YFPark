package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 预约收费规则新增/修改 Request VO")
@Data
public class ResvBillRuleSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28877")
    private Long id;

    @Schema(description = "机构ID", example = "64")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String appSign;

    @Schema(description = "规则名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "规则名称不能为空")
    private String name;

    @Schema(description = "所属项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14589")
    @NotEmpty(message = "所属项目ID不能为空")
    private String villageId;

    @Schema(description = "收费标准", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "收费标准不能为空")
    private String chargeStandard;

    @Schema(description = "是否启用多时间收费", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否启用多时间收费不能为空")
    private Integer isMultiTimeCharge;

    @Schema(description = "多时间收费收费标准", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "多时间收费收费标准不能为空")
    private String multiTimeChargeStandard;

    @Schema(description = "状态: 1为开启 0为关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态: 1为开启 0为关闭不能为空")
    private Integer status;

}