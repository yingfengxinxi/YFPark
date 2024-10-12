package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约订单退款新增/修改 Request VO")
@Data
public class ResvBookingOrderRefundSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16681")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "预约ID", example = "3063")
    private Long bookingId;

    @Schema(description = "预约订单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "790")
    @NotNull(message = "预约订单ID不能为空")
    private Long orderId;

    @Schema(description = "退款订单号")
    private String orderNo;

    @Schema(description = "退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退款金额不能为空")
    private BigDecimal amount;

    @Schema(description = "实际退款金额")
    private BigDecimal actualAmount;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "998")
    @NotNull(message = "租客ID不能为空")
    private Long ownerId;

    @Schema(description = "处理人")
    private Long handler;

    @Schema(description = "处理人名称", example = "赵六")
    private String handlerName;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "退款类型 1 全部退款 2 部分退款", example = "1")
    private Integer refundType;

    @Schema(description = "0未处理 1已处理 2驳回", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "0未处理 1已处理 2驳回不能为空")
    private Integer status;

    @Schema(description = "退款错误日志")
    private String errorMsg;

}