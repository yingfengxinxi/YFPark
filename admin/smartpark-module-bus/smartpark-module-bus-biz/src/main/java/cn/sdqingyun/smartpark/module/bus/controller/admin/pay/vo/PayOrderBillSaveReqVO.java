package cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 支付订单和账单表中间表	新增/修改 Request VO")
@Data
public class PayOrderBillSaveReqVO  extends TenantBaseDO {

    @Schema(description = "支付订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10065")
    private Long id;

    @Schema(description = "账单明细id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20386")
    @NotNull(message = "账单明细id不能为空")
    private Long billId;

    @Schema(description = "商户订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16311")
    @NotEmpty(message = "商户订单编号不能为空")
    private String merchantOrderId;

    @Schema(description = "支付金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "16778")
    @NotNull(message = "支付金额，单位：分不能为空")
    private Integer price;

}