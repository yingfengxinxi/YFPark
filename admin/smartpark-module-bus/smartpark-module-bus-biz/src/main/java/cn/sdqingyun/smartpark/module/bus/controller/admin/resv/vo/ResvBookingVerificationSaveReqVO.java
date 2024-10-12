package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约核销新增/修改 Request VO")
@Data
public class ResvBookingVerificationSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31308")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "预约ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21438")
    @NotNull(message = "预约ID不能为空")
    private Long bookingId;

    @Schema(description = "核销码")
    private String code;

    @Schema(description = "核销状态 0未核销 1已核销")
    private Integer status;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "处理人")
    private Long handler;

    @Schema(description = "处理人名称", example = "赵六")
    private String handlerName;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

}