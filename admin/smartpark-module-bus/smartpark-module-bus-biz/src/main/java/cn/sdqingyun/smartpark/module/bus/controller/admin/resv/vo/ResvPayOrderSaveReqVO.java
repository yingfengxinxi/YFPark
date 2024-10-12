package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 订单支付新增/修改 Request VO")
@Data
public class ResvPayOrderSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16162")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "订单ID(支持多个订单)")
    private String orderIds;

    @Schema(description = "实际支付金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "实际支付金额不能为空")
    private BigDecimal payAmount;

    @Schema(description = "支付订单号")
    private String payOrderNo;

    @Schema(description = "支付方式")
    private String payMethod;

    @Schema(description = "支付方式文本")
    private String payMethodTxt;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付状态 0待支付 1支付成功")
    private Integer payStatus;

    @Schema(description = "第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19672")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17834")
    @NotNull(message = "租客ID不能为空")
    private Long ownerId;

    @Schema(description = "pay服务响应冗余")
    private String responseParams;

}