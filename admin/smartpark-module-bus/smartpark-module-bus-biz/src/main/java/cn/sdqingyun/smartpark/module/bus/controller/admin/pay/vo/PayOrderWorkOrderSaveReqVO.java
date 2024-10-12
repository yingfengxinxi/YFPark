package cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 支付订单和工单订单记录中间新增/修改 Request VO")
@Data
public class PayOrderWorkOrderSaveReqVO  extends TenantBaseDO {

    @Schema(description = "支付订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13372")
    private Long id;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String application;

    @Schema(description = "付款记录id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31793")
    @NotNull(message = "付款记录id不能为空")
    private Long workorderProposeOrderId;

    @Schema(description = "商户订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18348")
    @NotEmpty(message = "商户订单编号不能为空")
    private String merchantOrderId;

    @Schema(description = "支付金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "19403")
    @NotNull(message = "支付金额，单位：分不能为空")
    private Integer price;

    @Schema(description = "订单类型0=支付1=退款", example = "1")
    private String orderType;

}