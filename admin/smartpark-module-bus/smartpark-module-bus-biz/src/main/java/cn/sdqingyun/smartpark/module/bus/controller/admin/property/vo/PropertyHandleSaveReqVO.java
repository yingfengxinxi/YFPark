package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产处置单据记录新增/修改 Request VO")
@Data
public class PropertyHandleSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32249")
    private Long id;

    @Schema(description = "机构id", example = "8492")
    private Long orgId;

    @Schema(description = "发起部门id", example = "10382")
    private Long departmentId;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "单据状态;1=进行中", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "单据状态不能为空")
    private Integer status;

    @Schema(description = "处置金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "处置金额不能为空")
    private BigDecimal handleAmount;

    @Schema(description = "处置费用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "处置费用不能为空")
    private BigDecimal handleExpenses;

    @Schema(description = "处置类型", example = "1")
    private Integer handleType;

    @Schema(description = "处置原因", example = "你说的对")
    private String remark;

    @Schema(description = "发起时间")
    private LocalDateTime applyTime;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}