package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 流水删除历史新增/修改 Request VO")
@Data
public class BillStreamDeleteHistorySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17718")
    private Long id;

    @Schema(description = "流水id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13960")
    @NotNull(message = "流水id不能为空")
    private Integer streamId;

    @Schema(description = "审批id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21888")
    @NotNull(message = "审批id不能为空")
    private Integer approvalId;

    @Schema(description = "审批状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "审批状态不能为空")
    private Integer approvalStatus;

    @Schema(description = "0新建1审批回调", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "0新建1审批回调不能为空")
    private Integer type;

    @Schema(description = "审批结束时间")
    private LocalDateTime approvalTime;

}