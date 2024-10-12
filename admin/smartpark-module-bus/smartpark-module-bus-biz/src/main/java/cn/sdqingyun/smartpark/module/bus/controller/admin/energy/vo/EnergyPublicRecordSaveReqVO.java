package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 分摊记录新增/修改 Request VO")
@Data
public class EnergyPublicRecordSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19762")
    private Long id;

    @Schema(description = "类型 1分表 2总表", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "类型 1分表 2总表不能为空")
    private String type;

    @Schema(description = "机构ID", example = "11549")
    private Long orgId;

    @Schema(description = "项目ID", example = "29293")
    private Long villageId;

    @Schema(description = "楼宇ID", example = "30034")
    private Long buildId;

    @Schema(description = "关联自定义表ID", example = "2877")
    private Long energyId;

    @Schema(description = "父级ID", example = "22164")
    private Long parentEnergyId;

    @Schema(description = "表名称", example = "王五")
    private String name;

    @Schema(description = "起始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "分摊用量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分摊用量不能为空")
    private BigDecimal publicUse;

    @Schema(description = "分摊金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分摊金额不能为空")
    private BigDecimal publicAmount;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "状态，1启动，0审核中，4禁用不能为空")
    private String status;

    @Schema(description = "绑定账单")
    private String bindBill;

    @Schema(description = "账单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25916")
    @NotNull(message = "账单ID不能为空")
    private Long billId;

}